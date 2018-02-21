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

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;

/**
 * @author Samuel Kong
 */
public class CSVUtil {

	public static String encode(Object obj) {
		Class<?> clazz = obj.getClass();

		if (!clazz.isArray()) {
			return encode(String.valueOf(obj));
		}

		Object[] array = (Object[])obj;

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