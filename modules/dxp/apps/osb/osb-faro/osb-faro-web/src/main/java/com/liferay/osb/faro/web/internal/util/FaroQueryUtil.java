/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Gabriel Ibson
 */
public class FaroQueryUtil {

	public static String sanitizeQuery(String query) {
		if (Validator.isBlank(query)) {
			return query;
		}

		if (StringUtil.equalsIgnoreCase(query, StringPool.NULL)) {
			return "\\null";
		}

		IntStream intStream = query.codePoints();

		return intStream.mapToObj(
			c -> (char)c
		).map(
			c -> {
				if (_CHARACTERS_TO_BE_ESCAPED.indexOf(c) >= 0) {
					return "\\" + c;
				}

				return String.valueOf(c);
			}
		).collect(
			Collectors.joining()
		);
	}

	private static final String _CHARACTERS_TO_BE_ESCAPED =
		"+-=&&||><!(){}[]^\"~*?:\\/%";

}