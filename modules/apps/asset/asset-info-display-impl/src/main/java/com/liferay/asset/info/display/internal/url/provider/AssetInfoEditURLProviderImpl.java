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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = AssetInfoEditURLProvider.class)
public class AssetInfoEditURLProviderImpl implements AssetInfoEditURLProvider {

	@Override
	public String getURL(
		String className, long classPK, HttpServletRequest httpServletRequest) {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if (assetRendererFactory == null) {
			return StringPool.BLANK;
		}

		try {
			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(classPK);

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

			String redirect = ParamUtil.getString(
				httpServletRequest, "redirect");

			if (Validator.isNull(redirect)) {
				Layout layout = themeDisplay.getLayout();

				if (layout.isTypeAssetDisplay()) {
					redirect = themeDisplay.getURLCurrent();
				}
				else {
					String mode = ParamUtil.getString(
						_portal.getOriginalServletRequest(httpServletRequest),
						"p_l_mode", Constants.VIEW);

					redirect = _http.setParameter(
						_portal.getLayoutRelativeURL(layout, themeDisplay),
						"p_l_mode", mode);
				}
			}

			PortletURL editAssetEntryURL = assetRenderer.getURLEdit(
				httpServletRequest, LiferayWindowState.NORMAL, redirect);

			if (editAssetEntryURL == null) {
				return StringPool.BLANK;
			}

			editAssetEntryURL.setParameter(
				"portletResource", assetRendererFactory.getPortletId());

			return editAssetEntryURL.toString();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetInfoEditURLProviderImpl.class);

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

}