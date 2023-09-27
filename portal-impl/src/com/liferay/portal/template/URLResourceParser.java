/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.template;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

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
		catch (IOException ioException) {
			throw new TemplateException(ioException);
		}
	}

	public abstract URL getURL(String templateId) throws IOException;

	@Override
	public boolean isTemplateResourceValid(String templateId, String langType) {
		if (Validator.isBlank(templateId)) {
			return true;
		}

		char[] chars = templateId.toCharArray();

		for (char c : chars) {
			if ((c == CharPool.PERCENT) || (c == CharPool.POUND) ||
				(c == CharPool.QUESTION) || (c == CharPool.SEMICOLON)) {

				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Unable to load template ", templateId,
							" because the template name contains one or more ",
							"special characters: %, #, ?, or ;"));
				}

				return false;
			}

			if (c == CharPool.BACK_SLASH) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to load template " + templateId +
							" because the template name contains a backslash");
				}

				return false;
			}
		}

		String extension = FileUtil.getExtension(templateId);

		if (!extension.equals(langType) &&
			!ArrayUtil.contains(
				TemplateConstants.ALLOWED_LANG_TYPES, extension)) {

			return false;
		}

		return true;
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
		URLResourceParser.class);

}