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
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.util.AssetHelper;
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

import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
public abstract class BaseAssetDisplayPageFriendlyURLResolver
	implements FriendlyURLResolver {

	@Override
	public String getActualURL(
			long companyId, long groupId, boolean privateLayout,
			String mainPath, String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		AssetDisplayContributor assetDisplayContributor =
			getAssetDisplayContributor(groupId, friendlyURL);

		AssetEntry assetEntry = getAssetEntry(groupId, friendlyURL);

		HttpServletRequest request = (HttpServletRequest)requestContext.get(
			"request");

		request.setAttribute(
			AssetDisplayWebKeys.ASSET_DISPLAY_CONTRIBUTOR,
			assetDisplayContributor);
		request.setAttribute(WebKeys.LAYOUT_ASSET_ENTRY, assetEntry);
		request.setAttribute(
			AssetDisplayWebKeys.VERSION_CLASS_PK,
			getVersionClassPK(friendlyURL));

		Locale locale = portal.getLocale(request);

		portal.setPageTitle(assetEntry.getTitle(locale), request);
		portal.setPageDescription(assetEntry.getDescription(locale), request);

		portal.setPageKeywords(
			assetHelper.getAssetKeywords(
				assetEntry.getClassName(), assetEntry.getClassPK()),
			request);

		Layout layout = _getAssetEntryLayout(assetEntry);

		return portal.getLayoutActualURL(layout, mainPath);
	}

	@Override
	public LayoutFriendlyURLComposite getLayoutFriendlyURLComposite(
			long companyId, long groupId, boolean privateLayout,
			String friendlyURL, Map<String, String[]> params,
			Map<String, Object> requestContext)
		throws PortalException {

		Layout layout = _getAssetEntryLayout(
			getAssetEntry(groupId, friendlyURL));

		return new LayoutFriendlyURLComposite(layout, friendlyURL);
	}

	protected abstract AssetDisplayContributor getAssetDisplayContributor(
			long groupId, String friendlyURL)
		throws PortalException;

	protected abstract AssetEntry getAssetEntry(
			long groupId, String friendlyURL)
		throws PortalException;

	protected long getVersionClassPK(String friendlyURL) {
		return 0;
	}

	@Reference
	protected AssetDisplayContributorTracker assetDisplayContributorTracker;

	@Reference
	protected AssetDisplayPageEntryLocalService
		assetDisplayPageEntryLocalService;

	@Reference
	protected AssetHelper assetHelper;

	@Reference
	protected FriendlyURLEntryLocalService friendlyURLEntryLocalService;

	@Reference
	protected LayoutLocalService layoutLocalService;

	@Reference
	protected LayoutPageTemplateEntryService layoutPageTemplateEntryService;

	@Reference
	protected Portal portal;

	private Layout _getAssetEntryLayout(AssetEntry assetEntry) {
		AssetDisplayPageEntry assetDisplayPageEntry =
			assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				assetEntry.getGroupId(), assetEntry.getClassNameId(),
				assetEntry.getClassPK());

		if ((assetDisplayPageEntry != null) &&
			(assetDisplayPageEntry.getType() !=
				AssetDisplayPageConstants.TYPE_DEFAULT)) {

			return layoutLocalService.fetchLayout(
				assetDisplayPageEntry.getPlid());
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryService.fetchDefaultLayoutPageTemplateEntry(
				assetEntry.getGroupId(), assetEntry.getClassNameId(),
				assetEntry.getClassTypeId());

		if (layoutPageTemplateEntry != null) {
			return layoutLocalService.fetchLayout(
				layoutPageTemplateEntry.getPlid());
		}

		return null;
	}

}