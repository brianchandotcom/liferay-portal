/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

/**
 * @author Shuyang Zhou
 * @author Raymond Augé
 * @author Gregory Amerson
 * @author Hugo Huijser
 */
public class ArgumentsUtil {

	public static boolean getBoolean(
		Map<String, String> arguments, String key, boolean defaultValue) {

		String value = arguments.get(key);

		if (Validator.isNull(value) || value.startsWith("$")) {
			return defaultValue;
		}

		return GetterUtil.getBoolean(value);
	}

	public static int getInteger(
		Map<String, String> arguments, String key, int defaultValue) {

		String value = arguments.get(key);

		if (Validator.isNull(value) || value.startsWith("$")) {
			return defaultValue;
		}

		return GetterUtil.getInteger(value);
	}

	public static String getString(
		Map<String, String> arguments, String key, String defaultValue) {

		String value = arguments.get(key);

		if (Validator.isNull(value) || value.startsWith("$")) {
			return defaultValue;
		}

		return value;
	}

	public static Map<String, String> parseArguments(String[] args) {
		Map<String, String> arguments = new ArgumentsMap();

		for (String arg : args) {
			int pos = arg.indexOf('=');

			if (pos <= 0) {
				throw new IllegalArgumentException("Bad argument " + arg);
			}

			String key = StringUtil.trim(arg.substring(0, pos));
			String value = StringUtil.trim(arg.substring(pos + 1));

			if (key.startsWith("-D")) {
				key = key.substring(2);

				System.setProperty(key, value);
			}
			else {
				arguments.put(key, value);
			}
		}

		return arguments;
	}

	public static void processMainException(
			Map<String, String> arguments, Exception exception)
		throws Exception {

		String throwMainException = arguments.get("tools.throw.main.exception");

		if (GetterUtil.getBoolean(throwMainException, true)) {
			throw exception;
		}

		_log.error(exception);
	}

	private static final Log _log = LogFactoryUtil.getLog(ArgumentsUtil.class);

}