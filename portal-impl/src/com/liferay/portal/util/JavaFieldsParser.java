/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class JavaFieldsParser {

	public static String parse(ClassLoader classLoader, String s) {
		int x = s.indexOf("${");

		if (x == -1) {
			return s;
		}

		List<String> replaceFrom = new ArrayList<>();
		List<String> replaceWith = new ArrayList<>();

		while (true) {
			if (x == -1) {
				break;
			}

			int y = s.indexOf("}", x);

			if (y == -1) {
				break;
			}

			String javaSnippet = s.substring(x + 2, y);

			if (_log.isDebugEnabled()) {
				_log.debug("Java snippet " + javaSnippet);
			}

			String className = _getClassName(javaSnippet);

			if (_log.isDebugEnabled()) {
				_log.debug("Class name " + className);
			}

			if (className == null) {
				break;
			}

			Class<?> clazz = null;

			try {
				clazz = classLoader.loadClass(className);
			}
			catch (Exception exception) {
				_log.error("Unable to load class " + className, exception);

				break;
			}

			String fieldName = _getFieldName(javaSnippet);

			if (_log.isDebugEnabled()) {
				_log.debug("Field name " + fieldName);
			}

			if (fieldName == null) {
				break;
			}

			String fieldValue = null;

			try {
				Field field = clazz.getField(fieldName);

				fieldValue = String.valueOf(field.get(null));

				if (_log.isDebugEnabled()) {
					_log.debug("Field value " + fieldValue);
				}
			}
			catch (Exception exception) {
				_log.error("Unable to load field " + fieldName, exception);

				break;
			}

			replaceFrom.add(StringBundler.concat("${", javaSnippet, "}"));
			replaceWith.add(fieldValue);

			x = s.indexOf("${", y);
		}

		if (replaceFrom.isEmpty()) {
			return s;
		}

		return StringUtil.replace(
			s, replaceFrom.toArray(new String[0]),
			replaceWith.toArray(new String[0]));
	}

	private static String _getClassName(String javaSnippet) {
		int x = javaSnippet.lastIndexOf(".");

		if (x == -1) {
			return null;
		}

		return javaSnippet.substring(0, x);
	}

	private static String _getFieldName(String javaSnippet) {
		int x = javaSnippet.lastIndexOf(".");

		if (x == -1) {
			return null;
		}

		return javaSnippet.substring(x + 1);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JavaFieldsParser.class);

}