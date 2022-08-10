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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Marcos Martins
 */
public class NumberUtil {

	public static int getDecimalSeparatorIndex(String value) {
		return StringUtil.indexOfAny(value, _DECIMAL_SEPARATORS);
	}

	public static boolean hasDecimalSeparator(String value) {
		if (getDecimalSeparatorIndex(value) == -1) {
			return false;
		}

		return true;
	}

	private static final char[] _DECIMAL_SEPARATORS = {
		CharPool.ARABIC_DECIMAL_SEPARATOR, CharPool.COMMA, CharPool.PERIOD
	};

}