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
import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.display.page.util.AssetDisplayPageHelper;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.layout.type.controller.asset.display.internal.constants.AssetDisplayPageFriendlyURLResolverConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = AssetDisplayPageFriendlyURLProvider.class)
public class AssetDisplayPageFriendlyURLProviderImpl
	implements AssetDisplayPageFriendlyURLProvider {

	@Override
	public String getFriendlyURL(
			AssetEntry assetEntry, ThemeDisplay themeDisplay)
		throws PortalException {

		if (!AssetDisplayPageHelper.hasAssetDisplayPage(
				themeDisplay.getScopeGroupId(), assetEntry)) {

			return null;
		}

		AssetDisplayContributor assetDisplayContributor =
			_assetDisplayContributorTracker.getAssetDisplayContributor(
				assetEntry.getClassName());

		if (assetDisplayContributor == null) {
			return null;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(
			_portal.getGroupFriendlyURL(
				themeDisplay.getLayoutSet(), themeDisplay));

		FriendlyURLEntry mainFriendlyURLEntry =
			_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
				assetEntry.getClassNameId(), assetEntry.getClassPK());

		if (mainFriendlyURLEntry != null) {
			sb.append(
				AssetDisplayPageFriendlyURLResolverConstants.
					ASSET_DISPLAY_PAGE_URL_SEPARATOR);
			sb.append(assetDisplayContributor.getFriendlyURLShortcut());
			sb.append(StringPool.SLASH);
			sb.append(
				mainFriendlyURLEntry.getUrlTitle(themeDisplay.getLanguageId()));
		}
		else {
			sb.append(
				AssetDisplayPageFriendlyURLResolverConstants.
					ASSET_DISPLAY_PAGE_URL_SEPARATOR);
			sb.append(assetEntry.getEntryId());
		}

		return sb.toString();
	}

	@Override
	public String getFriendlyURL(
			String className, long classPK, ThemeDisplay themeDisplay)
		throws PortalException {

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			className, classPK);

		if (assetEntry != null) {
			return getFriendlyURL(assetEntry, themeDisplay);
		}

		return StringPool.BLANK;
	}

	@Reference
	private AssetDisplayContributorTracker _assetDisplayContributorTracker;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private Portal _portal;

}