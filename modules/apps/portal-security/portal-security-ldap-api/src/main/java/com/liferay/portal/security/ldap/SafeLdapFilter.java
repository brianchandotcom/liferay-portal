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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.ldap.validator.LDAPFilterException;
import com.liferay.portal.security.ldap.validator.LDAPFilterValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Tomas Polesovsky
 */
public class SafeLdapFilter {

	public static SafeLdapFilter approx(String key, Object value) {
		return new SafeLdapFilter(
			_concat(rfc2254Escape(key), "~=", ARGUMENT_PLACEHOLDER),
			Collections.singletonList(value));
	}

	public static SafeLdapFilter eq(String key, Object value) {
		return new SafeLdapFilter(
			_concat(rfc2254Escape(key), StringPool.EQUAL, ARGUMENT_PLACEHOLDER),
			Collections.singletonList(value));
	}

	public static SafeLdapFilter ex(String key) {
		return new SafeLdapFilter(
			_concat(rfc2254Escape(key), StringPool.EQUAL, StringPool.STAR),
			Collections.emptyList());
	}

	public static SafeLdapFilter fromUnsafeFilter(
			String unsafeFilter, LDAPFilterValidator ldapFilterValidator)
		throws LDAPFilterException {

		ldapFilterValidator.validate(unsafeFilter);

		return new SafeLdapFilter(
			new StringBundler(unsafeFilter), Collections.emptyList());
	}

	public static SafeLdapFilter ge(String key, Object value) {
		return new SafeLdapFilter(
			_concat(
				rfc2254Escape(key), StringPool.GREATER_THAN_OR_EQUAL,
				ARGUMENT_PLACEHOLDER),
			Collections.singletonList(value));
	}

	public static SafeLdapFilter le(String key, Object value) {
		return new SafeLdapFilter(
			_concat(
				rfc2254Escape(key), StringPool.LESS_THAN_OR_EQUAL,
				ARGUMENT_PLACEHOLDER),
			Collections.singletonList(value));
	}

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

	public static SafeLdapFilter substring(String key, String value) {
		return new SafeLdapFilter(
			_concat(
				rfc2254Escape(key), StringPool.EQUAL,
				rfc2254Escape(value, true)),
			Collections.emptyList());
	}

	public SafeLdapFilter and(SafeLdapFilter... safeLdapFilters) {
		if ((safeLdapFilters == null) || (safeLdapFilters.length == 0)) {
			return this;
		}

		StringBundler sb = new StringBundler();

		List<Object> arguments = new ArrayList<>(_arguments);

		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(StringPool.AMPERSAND);

		sb.append(_filterSB);

		for (SafeLdapFilter safeLdapFilter : safeLdapFilters) {
			sb.append(safeLdapFilter._filterSB);

			arguments.addAll(safeLdapFilter._arguments);
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return new SafeLdapFilter(sb, arguments);
	}

	public String getFilterString() {
		if (_generatedFilter != null) {
			return _generatedFilter;
		}

		StringBundler sb = new StringBundler(
			_filterSB.length() + _arguments.size() * 2);

		int placeholderPos = 0;

		for (int i = 0; i < _filterSB.index(); i++) {
			String string = _filterSB.stringAt(i);

			if (Objects.equals(string, ARGUMENT_PLACEHOLDER)) {
				sb.append(StringPool.OPEN_CURLY_BRACE);
				sb.append(placeholderPos);
				sb.append(StringPool.CLOSE_CURLY_BRACE);
				placeholderPos++;
			}
			else {
				sb.append(string);
			}
		}

		_generatedFilter = sb.toString();

		return _generatedFilter;
	}

	public Object[] getArguments() {
		return _arguments.toArray();
	}

	public SafeLdapFilter not() {
		StringBundler sb = new StringBundler(_filterSB.index() + 3);

		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(StringPool.EXCLAMATION);
		sb.append(_filterSB);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		return new SafeLdapFilter(sb, new ArrayList<>(_arguments));
	}

	public SafeLdapFilter or(SafeLdapFilter... safeLdapFilters) {
		if ((safeLdapFilters == null) || (safeLdapFilters.length == 0)) {
			return this;
		}

		StringBundler sb = new StringBundler();

		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(StringPool.PIPE);

		sb.append(_filterSB);

		List<Object> arguments = new ArrayList<>(_arguments);

		for (SafeLdapFilter safeLdapFilter : safeLdapFilters) {
			sb.append(safeLdapFilter._filterSB);

			arguments.addAll(safeLdapFilter._arguments);
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return new SafeLdapFilter(sb, arguments);
	}

	@Override
	public String toString() {
		return StringBundler.concat(getFilterString(), " ", _arguments);
	}

	protected SafeLdapFilter(StringBundler filterSB, List<Object> arguments) {
		_filterSB = filterSB;
		_arguments = arguments;
	}

	protected StringBundler getFilterStringBundler() {
		return _filterSB;
	}

	protected static final String ARGUMENT_PLACEHOLDER = "(PLACEHOLDER)";

	private static StringBundler _concat(String key, String op, String value) {
		StringBundler sb = new StringBundler(5);

		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(key);
		sb.append(op);
		sb.append(value);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb;
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

	private final List<Object> _arguments;
	private final StringBundler _filterSB;
	private String _generatedFilter;

}