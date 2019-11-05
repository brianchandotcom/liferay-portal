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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.Collections;

/**
 * @author Tomas Polesovsky
 */
public class SafeLdapFilterConstraints {

	public static SafeLdapFilter approx(String key, Object value) {
		return new SafeLdapFilter(
			_concat(
				SafeLdapFilterStringUtil.rfc2254Escape(key), "~=",
				SafeLdapFilter.ARGUMENT_PLACEHOLDER),
			Collections.singletonList(value));
	}

	public static SafeLdapFilter eq(String key, Object value) {
		return new SafeLdapFilter(
			_concat(
				SafeLdapFilterStringUtil.rfc2254Escape(key), StringPool.EQUAL,
				SafeLdapFilter.ARGUMENT_PLACEHOLDER),
			Collections.singletonList(value));
	}

	public static SafeLdapFilter ex(String key) {
		return new SafeLdapFilter(
			_concat(
				SafeLdapFilterStringUtil.rfc2254Escape(key), StringPool.EQUAL,
				StringPool.STAR),
			Collections.emptyList());
	}

	public static SafeLdapFilter ge(String key, Object value) {
		return new SafeLdapFilter(
			_concat(
				SafeLdapFilterStringUtil.rfc2254Escape(key),
				StringPool.GREATER_THAN_OR_EQUAL,
				SafeLdapFilter.ARGUMENT_PLACEHOLDER),
			Collections.singletonList(value));
	}

	public static SafeLdapFilter le(String key, Object value) {
		return new SafeLdapFilter(
			_concat(
				SafeLdapFilterStringUtil.rfc2254Escape(key),
				StringPool.LESS_THAN_OR_EQUAL,
				SafeLdapFilter.ARGUMENT_PLACEHOLDER),
			Collections.singletonList(value));
	}

	public static SafeLdapFilter substring(String key, String value) {
		return new SafeLdapFilter(
			_concat(
				SafeLdapFilterStringUtil.rfc2254Escape(key), StringPool.EQUAL,
				SafeLdapFilterStringUtil.rfc2254Escape(value, true)),
			Collections.emptyList());
	}

	private static StringBundler _concat(String key, String op, String value) {
		StringBundler sb = new StringBundler(5);

		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(key);
		sb.append(op);
		sb.append(value);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb;
	}

}