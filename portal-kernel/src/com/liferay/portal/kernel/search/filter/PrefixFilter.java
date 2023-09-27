/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.filter;

import com.liferay.petra.string.StringBundler;

/**
 * @author Michael C. Han
 */
public class PrefixFilter extends BaseFilter {

	public PrefixFilter(String field, String prefix) {
		_field = field;
		_prefix = prefix;
	}

	@Override
	public <T> T accept(FilterVisitor<T> filterVisitor) {
		return filterVisitor.visit(this);
	}

	public String getField() {
		return _field;
	}

	public String getPrefix() {
		return _prefix;
	}

	@Override
	public int getSortOrder() {
		return 5;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{(", _field, "=", _prefix, "), ", super.toString(), "}");
	}

	private final String _field;
	private final String _prefix;

}