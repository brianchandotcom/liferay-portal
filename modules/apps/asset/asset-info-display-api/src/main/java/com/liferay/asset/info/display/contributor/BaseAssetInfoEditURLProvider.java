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

package com.liferay.asset.info.display.contributor;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.info.display.contributor.InfoEditURLProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class BaseAssetInfoEditURLProvider
	implements InfoEditURLProvider<AssetEntry> {

	@Override
	public String getURL(
			AssetEntry assetEntry, HttpServletRequest httpServletRequest)
		throws Exception {

		if (assetEntry == null) {
			return StringPool.BLANK;
		}

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetEntry.getClassName());

		if (assetRendererFactory == null) {
			return StringPool.BLANK;
		}

		AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
			assetEntry.getClassPK());

		if (assetRenderer == null) {
			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!assetRenderer.hasEditPermission(
				themeDisplay.getPermissionChecker())) {

			return StringPool.BLANK;
		}

		PortletURL editAssetEntryURL = assetRenderer.getURLEdit(
			httpServletRequest, LiferayWindowState.NORMAL,
			themeDisplay.getURLCurrent());

		if (editAssetEntryURL == null) {
			return StringPool.BLANK;
		}

		editAssetEntryURL.setParameter(
			"portletResource", assetRendererFactory.getPortletId());

		return editAssetEntryURL.toString();
	}

}