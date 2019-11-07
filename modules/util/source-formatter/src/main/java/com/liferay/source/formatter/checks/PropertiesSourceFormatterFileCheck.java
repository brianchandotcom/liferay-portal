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

package com.liferay.source.formatter.checks;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * @author Hugo Huijser
 */
public class PropertiesSourceFormatterFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (fileName.endsWith("/source-formatter.properties")) {
			return _formatSourceFormatterProperties(fileName, content);
		}

		return content;
	}

	private String _formatSourceFormatterProperties(
			String fileName, String content)
		throws IOException {

		int level = ToolsUtil.PLUGINS_MAX_DIR_LEVEL;

		if (isPortalSource()) {
			level = ToolsUtil.PORTAL_MAX_DIR_LEVEL;
		}

		Properties properties = new Properties();

		properties.load(new StringReader(content));

		Enumeration<String> enu =
			(Enumeration<String>)properties.propertyNames();

		while (enu.hasMoreElements()) {
			String key = enu.nextElement();

			String value = properties.getProperty(key);

			if (Validator.isNull(value)) {
				continue;
			}

			List<String> propertyValues = ListUtil.fromString(
				value, StringPool.COMMA);

			if (propertyValues.size() > 1) {
				content = _sortPropertyValues(content, key, propertyValues);
			}

			if (!key.endsWith("excludes")) {
				continue;
			}

			for (String propertyFileName : propertyValues) {
				if (propertyFileName.contains(StringPool.STAR) ||
					propertyFileName.endsWith("-ext.properties") ||
					(isPortalSource() && !_hasPrivateAppsDir() &&
					 isModulesApp(propertyFileName, true))) {

					continue;
				}

				int pos = propertyFileName.indexOf(CharPool.AT);

				if (pos != -1) {
					propertyFileName = propertyFileName.substring(0, pos);
				}

				File file = getFile(propertyFileName, level);

				if (file == null) {
					addMessage(
						fileName,
						"Property value '" + propertyFileName +
							"' points to file that does not exist");
				}
			}
		}

		return content;
	}

	private synchronized boolean _hasPrivateAppsDir() {
		if (_hasPrivateAppsDir != null) {
			return _hasPrivateAppsDir;
		}

		_hasPrivateAppsDir = false;

		if (isPortalSource()) {
			return _hasPrivateAppsDir;
		}

		File dxpAppsDir = getFile(
			"modules/dxp/apps", ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (dxpAppsDir != null) {
			_hasPrivateAppsDir = true;

			return _hasPrivateAppsDir;
		}

		File privateAppsDir = getFile(
			"modules/private/apps", ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (privateAppsDir != null) {
			_hasPrivateAppsDir = true;
		}

		return _hasPrivateAppsDir;
	}

	private String _sortPropertyValues(
		String content, String propertyKey, List<String> propertyValues) {

		PropertyValueComparator comparator = new PropertyValueComparator();

		for (int i = 0; i < (propertyValues.size() - 1); i++) {
			String propertyValue = propertyValues.get(i);
			String nextPropertyValue = propertyValues.get(i + 1);

			if (comparator.compare(propertyValue, nextPropertyValue) > 0) {
				return _swapValues(
					content, propertyKey, propertyValue, nextPropertyValue);
			}
		}

		return content;
	}

	private String _swapValues(String content, int x, String s1, String s2) {
		while (true) {
			x = content.indexOf(s1, x + 1);

			if (x == -1) {
				return content;
			}

			char c = content.charAt(x - 1);

			if (!Character.isWhitespace(c) && (c != CharPool.EQUAL) &&
				(c != CharPool.COMMA)) {

				continue;
			}

			if ((x + s1.length()) < content.length()) {
				c = content.charAt(x + s1.length());

				if (!Character.isWhitespace(c) && (c != CharPool.COMMA)) {
					continue;
				}
			}

			return StringUtil.replaceFirst(content, s1, s2, x);
		}
	}

	private String _swapValues(
		String content, String propertyKey, String propertyValue,
		String nextPropertyValue) {

		int x = -1;

		while (true) {
			x = content.indexOf(propertyKey + "=");

			if (x == -1) {
				return content;
			}

			if ((x == 0) || Character.isWhitespace(content.charAt(x - 1))) {
				break;
			}
		}

		content = _swapValues(content, x, nextPropertyValue, propertyValue);
		content = _swapValues(content, x, propertyValue, nextPropertyValue);

		return content;
	}

	private static final char[][] _REVERSE_ORDER_CHARACTERS = {
		{CharPool.COLON, CharPool.PERIOD}, {CharPool.DASH, CharPool.SLASH}
	};

	private Boolean _hasPrivateAppsDir;

	private class PropertyValueComparator extends NaturalOrderStringComparator {

		@Override
		public int compare(String s1, String s2) {
			int value = super.compare(s1, s2);

			if (s1.startsWith(s2) || s2.startsWith(s1)) {
				return value;
			}

			int x = StringUtil.startsWithWeight(s1, s2);

			char c1 = s1.charAt(x);
			char c2 = s2.charAt(x);

			for (char[] array : _REVERSE_ORDER_CHARACTERS) {
				if (ArrayUtil.contains(array, c1) &&
					ArrayUtil.contains(array, c2)) {

					return -value;
				}
			}

			if ((x > 0) && (s1.charAt(x - 1) == CharPool.PERIOD)) {
				if (Character.isUpperCase(c1) && Character.isLowerCase(c2)) {
					return -1;
				}
				else if (Character.isLowerCase(c1) &&
						 Character.isUpperCase(c2)) {

					return 1;
				}
			}

			return value;
		}

	}

}