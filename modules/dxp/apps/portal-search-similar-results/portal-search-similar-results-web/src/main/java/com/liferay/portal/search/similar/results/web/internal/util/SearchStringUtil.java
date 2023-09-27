/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.internal.util;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

/**
 * @author André de Oliveira
 */
public class SearchStringUtil {

	public static String maybe(String s) {
		s = StringUtil.trim(s);

		if (Validator.isBlank(s)) {
			return null;
		}

		return s;
	}

	public static void requireEquals(String expected, String actual) {
		if (!Objects.equals(expected, actual)) {
			throw new RuntimeException(actual + " != " + expected);
		}
	}

	public static void requireStartsWith(String expected, String actual) {
		if (!StringUtil.startsWith(actual, expected)) {
			throw new RuntimeException(actual + " /= " + expected);
		}
	}

	public static String[] splitAndUnquote(String s) {
		if (Validator.isBlank(s)) {
			return new String[0];
		}

		return TransformUtil.transform(
			StringUtil.split(s, CharPool.COMMA),
			string -> StringUtil.unquote(string.trim()), String.class);
	}

}