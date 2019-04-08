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

package com.liferay.asset.display.page.portlet;

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryService;
import com.liferay.asset.util.AssetHelper;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURLComposite;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = FriendlyURLResolver.class)
public class BaseAssetDisplayPageFriendlyURLResolver
	implements FriendlyURLResolver {

	@Override
	public String getActualURL(
			long companyId, long groupId, boolean privateLayout,
			String mainPath, String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		HttpServletRequest request = (HttpServletRequest)requestContext.get(
			"request");

		InfoDisplayContributor infoDisplayContributor =
			_getInfoDisplayContributor(friendlyURL);

		request.setAttribute(
			InfoDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR,
			infoDisplayContributor);

		request.setAttribute(
			InfoDisplayWebKeys.VERSION_CLASS_PK,
			_getVersionClassPK(friendlyURL));

		AssetEntry assetEntry = _getAssetEntry(
			infoDisplayContributor, groupId, friendlyURL);

		request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, assetEntry);

		Locale locale = _portal.getLocale(request);

		_portal.setPageDescription(assetEntry.getDescription(locale), request);

		_portal.setPageKeywords(
			_assetHelper.getAssetKeywords(
				assetEntry.getClassName(), assetEntry.getClassPK()),
			request);

		_portal.setPageTitle(assetEntry.getTitle(locale), request);

		Layout layout = _getAssetEntryLayout(assetEntry);

		return _portal.getLayoutActualURL(layout, mainPath);
	}

	@Override
	public LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
			long companyId, long groupId, boolean privateLayout,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		InfoDisplayContributor infoDisplayContributor =
			_getInfoDisplayContributor(friendlyURL);

		Layout layout = _getAssetEntryLayout(
			_getAssetEntry(infoDisplayContributor, groupId, friendlyURL));

		return new LayoutFriendlyURLComposite(layout, friendlyURL);
	}

	@Override
	public String getURLSeparator() {
		return "/a/";
	}

	private AssetEntry _getAssetEntry(
			InfoDisplayContributor infoDisplayContributor, long groupId,
			String friendlyURL)
		throws PortalException {

		String className = infoDisplayContributor.getClassName();

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(
					_portal.getClassNameId(className));

		AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
			groupId, _getUrlTitle(friendlyURL));

		return assetRendererFactory.getAssetEntry(
			className, assetRenderer.getClassPK());
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

	private InfoDisplayContributor _getInfoDisplayContributor(
			String friendlyURL)
		throws PortalException {

		String infoURLSeparator = _getInfoURLSeparator(friendlyURL);

		InfoDisplayContributor infoDisplayContributor =
			_infoDisplayContributorTracker.
				getInfoDisplayContributorByURLSeparator(infoURLSeparator);

		if (infoDisplayContributor == null) {
			throw new PortalException(
				"Info display contributor is not available for " +
					infoURLSeparator);
		}

		return infoDisplayContributor;
	}

	private String _getInfoURLSeparator(String friendlyURL) {
		List<String> paths = StringUtil.split(friendlyURL, CharPool.SLASH);

		return CharPool.SLASH + paths.get(0) + CharPool.SLASH;
	}

	private String _getUrlTitle(String friendlyURL) {
		List<String> paths = StringUtil.split(friendlyURL, CharPool.SLASH);

		return paths.get(1);
	}

	private long _getVersionClassPK(String friendlyURL) {
		List<String> paths = StringUtil.split(friendlyURL, CharPool.SLASH);

		if (paths.size() < 3) {
			return 0;
		}

		return GetterUtil.getLong(paths.get(2));
	}

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Reference
	private AssetEntryService _assetEntryService;

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private Portal _portal;

}