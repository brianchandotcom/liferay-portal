/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Tomas Polesovsky
 */
public class SafeLdapFilter {

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

	public Object[] getArguments() {
		return _arguments.toArray();
	}

	public String getFilterString() {
		if (_generatedFilter != null) {
			return _generatedFilter;
		}

		StringBundler sb = new StringBundler(
			_filterSB.length() + (_arguments.size() * 2));

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

	protected static final String ARGUMENT_PLACEHOLDER = "{PLACEHOLDER}";

	private final List<Object> _arguments;
	private final StringBundler _filterSB;
	private String _generatedFilter;

}