/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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