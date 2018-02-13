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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.theme.ThemeLoader;
import com.liferay.portal.theme.ThemeLoaderFactory;

import java.io.File;
import java.io.IOException;

import java.net.URI;
import java.net.URL;

/**
 * @author Tina Tian
 */
@OSGiBeanProperties(
	property = {
		"lang.type=" + TemplateConstants.LANG_TYPE_FTL,
		"lang.type=" + TemplateConstants.LANG_TYPE_VM
	},
	service = TemplateResourceParser.class
)
public class ThemeResourceParser extends URLResourceParser {

	@Override
	public URL getURL(String templateId) throws IOException {
		int pos = templateId.indexOf(TemplateConstants.THEME_LOADER_SEPARATOR);

		if (pos == -1) {
			return null;
		}

		if (templateId.endsWith(
				StringPool.PERIOD + TemplateConstants.LANG_TYPE_VM)) {

			StringBundler sb = new StringBundler(4);

			sb.append("Velocity is no longer supported for themes. Please ");
			sb.append("update template ");
			sb.append(templateId);
			sb.append(" to use FreeMarker.");

			if (_log.isWarnEnabled()) {
				_log.warn(sb.toString());
			}
		}

		String servletContextName = templateId.substring(0, pos);

		ThemeLoader themeLoader = ThemeLoaderFactory.getThemeLoader(
			servletContextName);

		if (themeLoader == null) {
			_log.error(
				StringBundler.concat(
					templateId, " is not valid because ", servletContextName,
					" does not map to a theme loader"));

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
				StringBundler.concat(
					templateId, " is associated with the theme loader ",
					servletContextName, " ", String.valueOf(themeLoader)));
		}

		File fileStorage = themeLoader.getFileStorage();

		File file = new File(fileStorage, templateId);

		URI uri = file.toURI();

		return uri.toURL();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ThemeResourceParser.class);

}