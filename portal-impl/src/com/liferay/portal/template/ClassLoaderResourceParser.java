/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.template;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.template.TemplateConstants;

import java.net.URL;

/**
 * @author Tina Tian
 */
@OSGiBeanProperties(
	property = {
		"lang.type=" + TemplateConstants.LANG_TYPE_FTL,
		"lang.type=" + TemplateConstants.LANG_TYPE_SOY,
		"lang.type=" + TemplateConstants.LANG_TYPE_VM
	},
	service = TemplateResourceParser.class
)
public class ClassLoaderResourceParser extends URLResourceParser {

	public ClassLoaderResourceParser() {
		Class<?> clazz = getClass();

		_classLoader = clazz.getClassLoader();
	}

	@Override
	public URL getURL(String templateId) {
		if (templateId.contains(TemplateConstants.SERVLET_SEPARATOR) ||
			templateId.contains(TemplateConstants.TEMPLATE_SEPARATOR) ||
			templateId.contains(TemplateConstants.THEME_LOADER_SEPARATOR)) {

			return null;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Loading " + templateId);
		}

		ClassLoader classLoader = _classLoader;

		int pos = templateId.indexOf(TemplateConstants.CLASS_LOADER_SEPARATOR);

		if (pos >= 0) {
			classLoader = ClassLoaderPool.getClassLoader(
				templateId.substring(0, pos));

			templateId = templateId.substring(
				pos + TemplateConstants.CLASS_LOADER_SEPARATOR.length());
		}

		return classLoader.getResource(templateId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClassLoaderResourceParser.class);

	private final ClassLoader _classLoader;

}