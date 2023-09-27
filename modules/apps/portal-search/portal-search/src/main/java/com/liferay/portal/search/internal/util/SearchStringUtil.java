/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.util;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author André de Oliveira
 */
public class SearchStringUtil {

	public static String maybe(String string) {
		string = StringUtil.trim(string);

		if (Validator.isBlank(string)) {
			return null;
		}

		return string;
	}

	public static String requireEquals(String expected, String actual) {
		if (!Objects.equals(expected, actual)) {
			throw new RuntimeException(actual + " != " + expected);
		}

		return actual;
	}

	public static String[] splitAndUnquote(String string) {
		if (string == null) {
			return new String[0];
		}

		List<String> list = new ArrayList<>();

		for (String part : StringUtil.split(string.trim(), CharPool.COMMA)) {
			list.add(StringUtil.unquote(part.trim()));
		}

		return list.toArray(new String[0]);
	}

}