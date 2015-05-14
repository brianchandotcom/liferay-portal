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

import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tina Tian
 */
public abstract class URLResourceParser implements TemplateResourceParser {

	@Override
	public TemplateResource getTemplateResource(String templateId)
		throws TemplateException {

		templateId = normalizePath(templateId);

		try {
			URL url = getURL(templateId);

			if (url == null) {
				return null;
			}

			return new URLTemplateResource(templateId, url);
		}
		catch (IOException ioe) {
			throw new TemplateException(ioe);
		}
	}

	public abstract URL getURL(String templateId) throws IOException;

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

}