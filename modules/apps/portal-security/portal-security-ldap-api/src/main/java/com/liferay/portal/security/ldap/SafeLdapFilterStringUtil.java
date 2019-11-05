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

package com.liferay.portal.security.ldap;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Tomas Polesovsky
 */
public class SafeLdapFilterStringUtil {

	public static String rfc2254Escape(String value) {
		return rfc2254Escape(value, false);
	}

	public static String rfc2254Escape(String value, boolean preserveStar) {
		if (!preserveStar) {
			return StringUtil.replace(
				value, _RFC2254_ESCAPE_KEYS, _RFC2254_ESCAPE_VALUES);
		}

		return StringUtil.replace(
			value, ArrayUtil.remove(_RFC2254_ESCAPE_KEYS, StringPool.STAR),
			ArrayUtil.remove(_RFC2254_ESCAPE_VALUES, "\\2a"));
	}

	private static final String[] _RFC2254_ESCAPE_KEYS = {
		StringPool.BACK_SLASH, StringPool.CLOSE_PARENTHESIS,
		StringPool.NULL_CHAR, StringPool.OPEN_PARENTHESIS, StringPool.STAR,
		StringPool.EQUAL, StringPool.GREATER_THAN, StringPool.LESS_THAN,
		StringPool.TILDE
	};

	private static final String[] _RFC2254_ESCAPE_VALUES = {
		"\\5c", "\\29", "\\00", "\\28", "\\2a", "\\3d", "\\3e", "\\3c", "\\7e"
	};

}