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

package com.liferay.portal.kernel.frontend.icons;

import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Bryce Osterhaus
 */
public class FrontendIconsUtil {

	public static String getBasePath() {
		String pathContext = PortalUtil.getPathContext();

		return pathContext + _ICONS_BASE_PATH;
	}

	public static String getSpritemap(long siteId) {
		return StringBundler.concat(
			getBasePath(), "/site/", String.valueOf(siteId), ".svg");
	}

	public static String getSpritemap(String name) {
		return StringBundler.concat(getBasePath(), "/pack/", name, ".svg");
	}

	public static String getSpritemap(ThemeDisplay themeDisplay) {
		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-145112"))) {
			return themeDisplay.getPathThemeImages() + "/clay/icons.svg";
		}

		Theme theme = themeDisplay.getTheme();

		if (theme.isControlPanelTheme()) {
			return getSystemSpritemap();
		}

		return getSpritemap(themeDisplay.getSiteGroupId());
	}

	public static String getSystemIconPackName() {
		return _SYSTEM_ICON_PACK_NAME;
	}

	public static String getSystemSpritemap() {
		return getSpritemap(_SYSTEM_ICON_PACK_NAME);
	}

	private static final String _ICONS_BASE_PATH = "/o/icons";

	private static final String _SYSTEM_ICON_PACK_NAME = "clay";

}