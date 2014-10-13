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

package com.liferay.portal.theme;

import com.liferay.portal.kernel.servlet.ServletContextPool;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public class ThemeLoaderFactory {

	public static boolean destroy(String servletContextName) {
		ThemeLoader themeLoader = _themeLoaders.remove(servletContextName);

		if (themeLoader == null) {
			return false;
		}

		ServletContextPool.remove(servletContextName);

		themeLoader.destroy();

		return true;
	}

	public static ThemeLoader getDefaultThemeLoader() {
		ThemeLoader themeLoader = null;

		for (Map.Entry<String, ThemeLoader> entry : _themeLoaders.entrySet()) {
			themeLoader = entry.getValue();

			break;
		}

		return themeLoader;
	}

	public static ThemeLoader getThemeLoader(String servletContextName) {
		return _themeLoaders.get(servletContextName);
	}

	public static void init(
		String servletContextName, ServletContext servletContext,
		String[] xmls) {

		ServletContextPool.put(servletContextName, servletContext);

		ThemeLoader themeLoader = new ThemeLoader(
			servletContextName, servletContext, xmls);

		_themeLoaders.put(servletContextName, themeLoader);
	}

	public static void loadThemes() {
		for (Map.Entry<String, ThemeLoader> entry : _themeLoaders.entrySet()) {
			ThemeLoader themeLoader = entry.getValue();

			themeLoader.loadThemes();
		}
	}

	private static final Map<String, ThemeLoader> _themeLoaders =
		new HashMap<String, ThemeLoader>();

}