/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.template;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.template.TemplateConstants;
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
	property = "lang.type=" + TemplateConstants.LANG_TYPE_FTL,
	service = TemplateResourceParser.class
)
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
					servletContextName, " ", themeLoader));
		}

		File file = new File(themeLoader.getFileStorage(), templateId);

		URI uri = file.toURI();

		return uri.toURL();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ThemeResourceParser.class);

}