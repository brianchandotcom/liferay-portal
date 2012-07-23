/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.template.TemplateResource;

import java.net.URL;

/**
 * @author Tina Tian
 */
public class ClassLoaderResourceParser extends URLResourceParser {

	@Override
	public URL getURL(String templateId) {
		if (templateId.contains(TemplateResource.JOURNAL_SEPARATOR) ||
			templateId.contains(TemplateResource.SERVLET_SEPARATOR) ||
			templateId.contains(TemplateResource.THEME_LOADER_SEPARATOR)) {

			return null;
		}

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		if (_log.isDebugEnabled()) {
			_log.debug("Loading " + templateId);
		}

		return classLoader.getResource(templateId);
	}

	private static Log _log = LogFactoryUtil.getLog(
		ClassLoaderResourceParser.class);

}