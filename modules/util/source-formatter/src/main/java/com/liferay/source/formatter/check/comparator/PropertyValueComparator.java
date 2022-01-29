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

package com.liferay.source.formatter.check.comparator;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Hugo Huijser
 */
public class PropertyValueComparator extends NaturalOrderStringComparator {

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
			else if (Character.isLowerCase(c1) && Character.isUpperCase(c2)) {
				return 1;
			}
		}

		return value;
	}

	private static final char[][] _REVERSE_ORDER_CHARACTERS = {
		{CharPool.COLON, CharPool.PERIOD}, {CharPool.DASH, CharPool.SLASH}
	};

}