/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * @author Samuel Kong
 */
public class CSVUtil {

	public static String encode(Object obj) {
		return encode(String.valueOf(obj));
	}

	public static String encode(String s) {
		if (s == null) {
			return null;
		}

		if ((s.indexOf(StringPool.COMMA) < 0) &&
			(s.indexOf(StringPool.QUOTE) < 0) &&
			(s.indexOf(StringPool.NEW_LINE) < 0) &&
			(s.indexOf(StringPool.RETURN) < 0)) {
			return s;
		}

		StringBundler sb = new StringBundler(3);

		sb.append(StringPool.QUOTE);
		sb.append(
			StringUtil.replace(s, StringPool.QUOTE, StringPool.DOUBLE_QUOTE));
		sb.append(StringPool.QUOTE);

		return sb.toString();
	}

}