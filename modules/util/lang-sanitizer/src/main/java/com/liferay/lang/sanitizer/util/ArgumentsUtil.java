/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.lang.sanitizer.util;

import java.util.Objects;

/**
 * @author Seiphon Wang
 */
public class ArgumentsUtil {

	public static String getValue(
		String[] args, String key, String defaultValue) {

		for (String arg : args) {
			int pos = arg.indexOf('=');

			if (pos <= 0) {
				continue;
			}

			String curKey = arg.substring(0, pos);

			if (Objects.equals(key, curKey.trim())) {
				String curValue = arg.substring(pos + 1);

				return curValue.trim();
			}
		}

		return defaultValue;
	}

}