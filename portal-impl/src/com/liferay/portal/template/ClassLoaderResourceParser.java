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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.net.URL;

/**
 * @author Tina Tian
 */
public class ClassLoaderResourceParser extends URLResourceParser {

	@Override
	@SuppressWarnings("deprecation")
	public URL getURL(String templateId) {
		if (templateId.contains(TemplateConstants.JOURNAL_SEPARATOR) ||
			templateId.contains(TemplateConstants.SERVLET_SEPARATOR) ||
			templateId.contains(TemplateConstants.TEMPLATE_SEPARATOR) ||
			templateId.contains(TemplateConstants.THEME_LOADER_SEPARATOR)) {

			return null;
		}

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		if (_log.isDebugEnabled()) {
			_log.debug("Loading " + templateId);
		}

		templateId = _normalizePath(templateId);

		return classLoader.getResource(templateId);
	}

	private String _normalizePath(String path) {
		StringBundler sb = new StringBundler();

		int startIndex = 0;

		if (path.startsWith(StringPool.SLASH)) {
			sb.append(StringPool.SLASH);

			startIndex = 1;
		}

		for (int i = startIndex; i < path.length(); i++) {
			if ((path.charAt(i) != CharPool.SLASH) || (i == startIndex)) {
				continue;
			}

			String pathSlice = path.substring(startIndex, i);

			if (pathSlice.equals(StringPool.DOUBLE_PERIOD)) {
				if (sb.index() < 2) {
					throw new IllegalArgumentException(
						"Unable to parse path " + path);
				}

				sb.setIndex(sb.index() - 2);
			}
			else if (!pathSlice.equals(StringPool.PERIOD)) {
				sb.append(pathSlice);
				sb.append(StringPool.SLASH);
			}

			startIndex = i + 1;
		}

		sb.append(path.substring(startIndex));

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClassLoaderResourceParser.class);

}