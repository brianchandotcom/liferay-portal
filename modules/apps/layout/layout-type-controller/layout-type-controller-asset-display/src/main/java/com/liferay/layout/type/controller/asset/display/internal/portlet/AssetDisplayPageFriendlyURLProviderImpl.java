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

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.display.page.util.AssetDisplayPageHelper;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.layout.type.controller.asset.display.internal.constants.AssetDisplayPageFriendlyURLResolverConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

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

		InfoDisplayContributor infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(
				assetEntry.getClassName());

		if (infoDisplayContributor == null) {
			return null;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(
			_portal.getGroupFriendlyURL(
				themeDisplay.getLayoutSet(), themeDisplay));

		AssetRenderer assetRenderer = assetEntry.getAssetRenderer();

		sb.append(
			AssetDisplayPageFriendlyURLResolverConstants.
				ASSET_DISPLAY_PAGE_URL_SEPARATOR);

		String urlTitle = null;

		if (assetRenderer != null) {
			urlTitle = assetRenderer.getUrlTitle(themeDisplay.getLocale());
		}

		if (Validator.isNotNull(urlTitle)) {
			sb.append(infoDisplayContributor.getInfoURLSeparator());
			sb.append(StringPool.SLASH);
			sb.append(assetRenderer.getUrlTitle(themeDisplay.getLocale()));
		}
		else {
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
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private Portal _portal;

}