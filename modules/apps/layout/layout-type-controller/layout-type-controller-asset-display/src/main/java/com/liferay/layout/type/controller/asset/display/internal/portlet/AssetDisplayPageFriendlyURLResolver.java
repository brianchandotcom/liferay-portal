/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.type.controller.asset.display.internal.portlet;

import com.liferay.asset.display.contributor.AssetDisplayContributor;
import com.liferay.asset.display.contributor.AssetDisplayContributorTracker;
import com.liferay.asset.display.contributor.constants.AssetDisplayWebKeys;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryService;
import com.liferay.asset.util.AssetHelper;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.type.controller.asset.display.internal.constants.AssetDisplayPageFriendlyURLResolverConstants;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURLComposite;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = FriendlyURLResolver.class)
public class AssetDisplayPageFriendlyURLResolver
	implements FriendlyURLResolver {

	@Override
	public String getActualURL(
			long companyId, long groupId, boolean privateLayout,
			String mainPath, String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		HttpServletRequest request = (HttpServletRequest)requestContext.get(
			"request");

		request.setAttribute(
			AssetDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR,
			_getAssetDisplayContributor(groupId, friendlyURL));

		AssetEntry assetEntry = _getAssetEntry(groupId, friendlyURL);

		request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, assetEntry);

		request.setAttribute(
			AssetDisplayWebKeys.VERSION_CLASS_PK,
			_getVersionClassPK(friendlyURL));

		Locale locale = _portal.getLocale(request);

		_portal.setPageTitle(assetEntry.getTitle(locale), request);
		_portal.setPageDescription(assetEntry.getDescription(locale), request);

		_portal.setPageKeywords(
			_assetHelper.getAssetKeywords(
				assetEntry.getClassName(), assetEntry.getClassPK()),
			request);

		Layout layout = _getAssetEntryLayout(assetEntry);

		return _portal.getLayoutActualURL(layout, mainPath);
	}

	@Override
	public LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
			long companyId, long groupId, boolean privateLayout,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		Layout layout = _getAssetEntryLayout(
			_getAssetEntry(groupId, friendlyURL));

		return new LayoutFriendlyURLComposite(layout, friendlyURL);
	}

	@Override
	public String getURLSeparator() {
		return AssetDisplayPageFriendlyURLResolverConstants.
			ASSET_DISPLAY_PAGE_URL_SEPARATOR;
	}

	private AssetDisplayContributor _getAssetDisplayContributor(
			long groupId, String friendlyURL)
		throws PortalException {

		String assetURLSeparator = _getAssetURLSeparator(friendlyURL);

		AssetDisplayContributor assetDisplayContributor = null;

		if (Validator.isNotNull(assetURLSeparator)) {
			assetDisplayContributor =
				_assetDisplayContributorTracker.
					getAssetDisplayContributorByAssetURLSeparator(
						assetURLSeparator);
		}
		else {
			AssetEntry assetEntry = _getAssetEntry(groupId, friendlyURL);

			assetDisplayContributor =
				_assetDisplayContributorTracker.getAssetDisplayContributor(
					assetEntry.getClassName());
		}

		if (assetDisplayContributor == null) {
			throw new PortalException(
				"Display page is not available for " + assetURLSeparator);
		}

		return assetDisplayContributor;
	}

	private AssetEntry _getAssetEntry(long groupId, String friendlyURL)
		throws PortalException {

		long assetEntryId = _getAssetEntryId(friendlyURL);

		if (assetEntryId > 0) {
			return _assetEntryService.getEntry(assetEntryId);
		}

		AssetDisplayContributor assetDisplayContributor =
			_getAssetDisplayContributor(groupId, friendlyURL);

		String className = assetDisplayContributor.getClassName();

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(
					_portal.getClassNameId(className));

		AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
			groupId, _getUrlTitle(friendlyURL));

		return assetRendererFactory.getAssetEntry(
			className, assetRenderer.getClassPK());
	}

	private long _getAssetEntryId(String friendlyURL) {
		List<String> paths = StringUtil.split(friendlyURL, CharPool.SLASH);

		String assetEntryId = paths.get(1);

		if (Validator.isNumber(assetEntryId)) {
			return GetterUtil.getLong(assetEntryId);
		}

		return 0;
	}

	private Layout _getAssetEntryLayout(AssetEntry assetEntry) {
		AssetDisplayPageEntry assetDisplayPageEntry =
			_assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				assetEntry.getGroupId(), assetEntry.getClassNameId(),
				assetEntry.getClassPK());

		if (assetDisplayPageEntry == null) {
			return null;
		}

		if (assetDisplayPageEntry.getType() !=
				AssetDisplayPageConstants.TYPE_DEFAULT) {

			return _layoutLocalService.fetchLayout(
				assetDisplayPageEntry.getPlid());
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryService.fetchDefaultLayoutPageTemplateEntry(
				assetEntry.getGroupId(), assetEntry.getClassNameId(),
				assetEntry.getClassTypeId());

		if (layoutPageTemplateEntry != null) {
			return _layoutLocalService.fetchLayout(
				layoutPageTemplateEntry.getPlid());
		}

		return null;
	}

	private String _getAssetURLSeparator(String friendlyURL) {
		List<String> paths = StringUtil.split(friendlyURL, CharPool.SLASH);

		String assetURLSeparator = paths.get(1);

		if (Validator.isNumber(assetURLSeparator)) {
			return StringPool.BLANK;
		}

		return assetURLSeparator;
	}

	private String _getUrlTitle(String friendlyURL) {
		List<String> paths = StringUtil.split(friendlyURL, CharPool.SLASH);

		if (paths.size() < 3) {
			return StringPool.BLANK;
		}

		if (Validator.isNumber(paths.get(1))) {
			return StringPool.BLANK;
		}

		return paths.get(2);
	}

	private long _getVersionClassPK(String friendlyURL) {
		List<String> paths = StringUtil.split(friendlyURL, CharPool.SLASH);

		if (Validator.isNumber(paths.get(1))) {
			if (paths.size() < 3) {
				return 0;
			}

			return GetterUtil.getLong(paths.get(2));
		}

		if (paths.size() < 4) {
			return 0;
		}

		return GetterUtil.getLong(paths.get(3));
	}

	@Reference
	private AssetDisplayContributorTracker _assetDisplayContributorTracker;

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Reference
	private AssetEntryService _assetEntryService;

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private Portal _portal;

}