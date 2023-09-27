/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;

/**
 * @author Samuel Kong
 */
public class CSVUtil {

	public static String encode(Object object) {
		Class<?> clazz = object.getClass();

		if (!clazz.isArray()) {
			return encode(String.valueOf(object));
		}

		Object[] array = (Object[])object;

		return encode(StringUtil.merge(array));
	}

	public static String encode(String s) {
		if (s == null) {
			return null;
		}

		if ((s.indexOf(CharPool.COMMA) < 0) &&
			(s.indexOf(CharPool.QUOTE) < 0) &&
			(s.indexOf(CharPool.NEW_LINE) < 0) &&
			(s.indexOf(CharPool.RETURN) < 0)) {

			return s;
		}

		s = StringUtil.replace(s, CharPool.QUOTE, StringPool.DOUBLE_QUOTE);

		return StringPool.QUOTE.concat(s.concat(StringPool.QUOTE));
	}

}