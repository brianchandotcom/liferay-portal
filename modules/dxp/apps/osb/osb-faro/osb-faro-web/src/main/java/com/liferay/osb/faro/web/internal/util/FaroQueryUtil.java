/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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