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
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.util.AssetHelper;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURLComposite;
import com.liferay.portal.kernel.portlet.FriendlyURLResolver;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = FriendlyURLResolver.class)
public class AssetDisplayPageURLTitleFriendlyURLResolver
	implements FriendlyURLResolver {

	@Override
	public String getActualURL(
			long companyId, long groupId, boolean privateLayout,
			String mainPath, String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		AssetDisplayContributor assetDisplayContributor =
			_getAssetDisplayContributor(_getClassNameShortcut(friendlyURL));

		AssetEntry assetEntry = _getAssetEntry(
			groupId, _getUrlTitle(friendlyURL), assetDisplayContributor);

		HttpServletRequest request = (HttpServletRequest)requestContext.get(
			"request");

		request.setAttribute(
			AssetDisplayWebKeys.ASSET_DISPLAY_CONTRIBUTOR,
			assetDisplayContributor);
		request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, assetEntry);

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

		AssetDisplayContributor assetDisplayContributor =
			_getAssetDisplayContributor(_getClassNameShortcut(friendlyURL));

		Layout layout = _getAssetEntryLayout(
			_getAssetEntry(
				groupId, _getUrlTitle(friendlyURL), assetDisplayContributor));

		return new LayoutFriendlyURLComposite(layout, friendlyURL);
	}

	@Override
	public String getURLSeparator() {
		return "/_";
	}

	private AssetDisplayContributor _getAssetDisplayContributor(
			String classNameShortcut)
		throws PortalException {

		AssetDisplayContributor assetDisplayContributor =
			_assetDisplayContributorTracker.
				getAssetDisplayContributorByFriendlyURLShortcut(
					classNameShortcut);

		if (assetDisplayContributor == null) {
			throw new PortalException(
				"Display page not available for " + classNameShortcut);
		}

		return assetDisplayContributor;
	}

	private AssetEntry _getAssetEntry(
			long groupId, String urlTitle,
			AssetDisplayContributor assetDisplayContributor)
		throws PortalException {

		String className = assetDisplayContributor.getClassName();

		long classNameId = _portal.getClassNameId(className);

		FriendlyURLEntry friendlyURLEntry =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				groupId, classNameId, urlTitle);

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(classNameId);

		return assetRendererFactory.getAssetEntry(
			className, friendlyURLEntry.getClassPK());
	}

	private Layout _getAssetEntryLayout(AssetEntry assetEntry) {
		AssetDisplayPageEntry assetDisplayPageEntry =
			_assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				assetEntry.getGroupId(), assetEntry.getClassNameId(),
				assetEntry.getClassPK());

		if ((assetDisplayPageEntry != null) &&
			(assetDisplayPageEntry.getType() !=
				AssetDisplayPageConstants.TYPE_DEFAULT)) {

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

	private String _getClassNameShortcut(String friendlyURL) {
		String urlSeparator = getURLSeparator();

		return friendlyURL.substring(
			urlSeparator.length(), friendlyURL.lastIndexOf("/"));
	}

	private String _getUrlTitle(String friendlyURL) {
		return friendlyURL.substring(friendlyURL.lastIndexOf("/") + 1);
	}

	@Reference
	private AssetDisplayContributorTracker _assetDisplayContributorTracker;

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Reference
	private AssetHelper _assetHelper;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private Portal _portal;

}