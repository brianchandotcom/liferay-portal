/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.product.navigation.control.menu.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.frontend.taglib.clay.servlet.taglib.HorizontalCard;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

/**
 * @author Eudaldo Alonso
 */
public class AssetRendererHorizontalCard implements HorizontalCard {

	public AssetRendererHorizontalCard(
		AssetRenderer<?> assetRenderer,
		LiferayPortletRequest liferayPortletRequest) {

		_assetRenderer = assetRenderer;

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getElementClasses() {
		return "card-interactive card-interactive-secondary";
	}

	@Override
	public String getIcon() {
		try {
			return _assetRenderer.getIconCssClass();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	@Override
	public String getTitle() {
		return HtmlUtil.escape(
			StringUtil.shorten(
				_assetRenderer.getTitle(_themeDisplay.getLocale()), 60));
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetRendererHorizontalCard.class);

	private final AssetRenderer<?> _assetRenderer;
	private final ThemeDisplay _themeDisplay;

}