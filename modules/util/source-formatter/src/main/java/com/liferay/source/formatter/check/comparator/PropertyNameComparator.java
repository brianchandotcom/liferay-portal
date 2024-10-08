/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check.comparator;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Alan Huang
 */
public class PropertyNameComparator extends NaturalOrderStringComparator {

	@Override
	public int compare(String s1, String s2) {
		int value = super.compare(s1, s2);

		if (s1.startsWith(s2) || s2.startsWith(s1)) {
			return value;
		}

		int x = StringUtil.startsWithWeight(s1, s2);

		char c1 = s1.charAt(x);
		char c2 = s2.charAt(x);

		if ((Character.isLetterOrDigit(c1) && (c2 == CharPool.STAR)) ||
			(Character.isLetterOrDigit(c2) && (c1 == CharPool.STAR))) {

			return -value;
		}

		for (char[] array : _REVERSE_ORDER_CHARACTERS) {
			if (ArrayUtil.contains(array, c1) &&
				ArrayUtil.contains(array, c2)) {

				return -value;
			}
		}

		if (x <= 0) {
			return value;
		}

		if (s1.charAt(x - 1) == CharPool.PERIOD) {
			if (Character.isUpperCase(c1) && Character.isLowerCase(c2)) {
				return -1;
			}
			else if (Character.isLowerCase(c1) && Character.isUpperCase(c2)) {
				return 1;
			}
		}

		if (s1.charAt(x - 1) == CharPool.SLASH) {
			if (c1 == CharPool.STAR) {
				return -1;
			}

			if (c2 == CharPool.STAR) {
				return 1;
			}

			if ((s1.indexOf(StringPool.PERIOD, x) == -1) ||
				(s2.indexOf(StringPool.PERIOD, x) == -1)) {

				return value;
			}

			if ((s1.indexOf(StringPool.SLASH, x) == -1) &&
				(s2.indexOf(StringPool.SLASH, x) != -1)) {

				return -1;
			}
			else if ((s1.indexOf(StringPool.SLASH, x) != -1) &&
					 (s2.indexOf(StringPool.SLASH, x) == -1)) {

				return 1;
			}
		}

		return value;
	}

	private static final char[][] _REVERSE_ORDER_CHARACTERS = {
		{CharPool.CLOSE_BRACKET, CharPool.DASH},
		{CharPool.CLOSE_BRACKET, CharPool.OPEN_PARENTHESIS},
		{CharPool.CLOSE_BRACKET, CharPool.PERIOD},
		{CharPool.OPEN_BRACKET, CharPool.PERIOD}
	};

}