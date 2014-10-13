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

package com.liferay.portal.template;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.theme.ThemeLoader;
import com.liferay.portal.theme.ThemeLoaderFactory;

import java.io.File;
import java.io.IOException;

import java.net.URL;

/**
 * @author Tina Tian
 */
public class ThemeResourceParser extends URLResourceParser {

	@Override
	public URL getURL(String templateId) throws IOException {
		int pos = templateId.indexOf(TemplateConstants.THEME_LOADER_SEPARATOR);

		if (pos == -1) {
			return null;
		}

		String servletContextName = templateId.substring(0, pos);

		ThemeLoader themeLoader = ThemeLoaderFactory.getThemeLoader(
			servletContextName);

		if (themeLoader == null) {
			_log.error(
				templateId + " is not valid because " + servletContextName +
					" does not map to a theme loader");

			return null;
		}

		String templateName = templateId.substring(
			pos + TemplateConstants.THEME_LOADER_SEPARATOR.length());

		String themesPath = themeLoader.getThemesPath();

		if (templateName.startsWith(themesPath)) {
			templateId = templateName.substring(themesPath.length());
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				templateId + " is associated with the theme loader " +
					servletContextName + " " + themeLoader);
		}

		File fileStorage = themeLoader.getFileStorage();

		return new File(fileStorage, templateId).toURI().toURL();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ThemeResourceParser.class);

}