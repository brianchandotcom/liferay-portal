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

package com.liferay.asset.info.display.internal.url.provider;

import com.liferay.asset.info.display.url.provider.AssetInfoEditURLProvider;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = AssetInfoEditURLProvider.class)
public class AssetInfoEditURLProviderImpl implements AssetInfoEditURLProvider {

	public String getURL(
		String className, long classPK, HttpServletRequest httpServletRequest) {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if (assetRendererFactory == null) {
			return StringPool.BLANK;
		}

		try {
			AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
				classPK);

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
		catch (Exception e) {
		}

		return StringPool.BLANK;
	}

}