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
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tina Tian
 */
public class ClassLoaderResourceParser extends URLResourceParser {

	public ClassLoaderResourceParser() {
		Class<?> clazz = getClass();

		_classLoader = clazz.getClassLoader();
	}

	public ClassLoaderResourceParser(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	@Override
	@SuppressWarnings("deprecation")
	public URL getURL(String templateId) {
		if (templateId.contains(TemplateConstants.JOURNAL_SEPARATOR) ||
			templateId.contains(TemplateConstants.SERVLET_SEPARATOR) ||
			templateId.contains(TemplateConstants.TEMPLATE_SEPARATOR) ||
			templateId.contains(TemplateConstants.THEME_LOADER_SEPARATOR)) {

			return null;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Loading " + templateId);
		}

		templateId = normalizePath(templateId);

		return _classLoader.getResource(templateId);
	}

	protected static String normalizePath(String path) {
		List<String> elements = new ArrayList<>();

		boolean absolutePath = false;

		int previousIndex = -1;

		for (int index;
			 (index = path.indexOf(CharPool.SLASH, previousIndex + 1)) != -1;
			 previousIndex = index) {

			if ((previousIndex + 1) == index) {

				// Starts with "/"

				if (previousIndex == -1) {
					absolutePath = true;

					continue;
				}

				// "//" is illegal

				throw new IllegalArgumentException(
					"Unable to parse path " + path);
			}

			String pathElement = path.substring(previousIndex + 1, index);

			// "." needs no handling

			if (pathElement.equals(StringPool.PERIOD)) {
				continue;
			}

			// ".." pops up stack

			if (pathElement.equals(StringPool.DOUBLE_PERIOD)) {
				if (elements.isEmpty()) {
					throw new IllegalArgumentException(
						"Unable to parse path " + path);
				}

				elements.remove(elements.size() - 1);

				continue;
			}

			// Others push down stack

			elements.add(pathElement);
		}

		if (previousIndex == -1) {
			elements.add(path);
		}
		else if ((previousIndex + 1) < path.length()) {
			elements.add(path.substring(previousIndex + 1));
		}

		String normalizedPath = StringUtil.merge(elements, StringPool.SLASH);

		if (absolutePath) {
			normalizedPath = StringPool.SLASH.concat(normalizedPath);
		}

		return normalizedPath;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClassLoaderResourceParser.class);

	private final ClassLoader _classLoader;

}