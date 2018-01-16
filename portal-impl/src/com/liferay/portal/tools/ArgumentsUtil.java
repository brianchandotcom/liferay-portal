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

package com.liferay.portal.tools;

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
			Map<String, String> arguments, Exception e)
		throws Exception {

		String throwMainException = arguments.get("tools.throw.main.exception");

		if (GetterUtil.getBoolean(throwMainException, true)) {
			throw e;
		}

		e.printStackTrace();
	}

}