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
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Bryce Osterhaus
 */
public class FrontendIconsUtil {

	public static String getBasePath() {
		return _ICONS_BASE_PATH;
	}

	public static String getSpritemapPath(long siteId) {
		return StringBundler.concat(
			_ICONS_BASE_PATH, "/site/", String.valueOf(siteId), ".svg");
	}

	public static String getSpritemapPath(String name) {
		return StringBundler.concat(_ICONS_BASE_PATH, "/pack/", name, ".svg");
	}

	public static String getSpritemapPath(ThemeDisplay themeDisplay) {
		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-145112"))) {
			return themeDisplay.getPathThemeImages() + "/clay/icons.svg";
		}

		Theme theme = themeDisplay.getTheme();

		if (theme.isControlPanelTheme()) {
			return getSystemSpritemapPath();
		}

		return getSpritemapPath(themeDisplay.getSiteGroupId());
	}

	public static String getSystemIconPackName() {
		return _SYSTEM_ICON_PACK;
	}

	public static String getSystemSpritemapPath() {
		return getSpritemapPath(_SYSTEM_ICON_PACK);
	}

	private static final String _ICONS_BASE_PATH = "/o/icons";

	private static final String _SYSTEM_ICON_PACK = "clay";

}