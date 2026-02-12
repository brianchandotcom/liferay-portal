/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import com.liferay.petra.string.StringBundler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class TermsQuery extends Query {

	public TermsQuery(String field) {
		_field = field;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	public void addValue(Object value) {
		_values.add(value);
	}

	public void addValues(Object... values) {
		Collections.addAll(_values, values);
	}

	public String getField() {
		return _field;
	}

	public String[] getValues() {
		return _values.toArray(new String[0]);
	}

	public boolean isEmpty() {
		return _values.isEmpty();
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{(", _field, "=", _values, "), ", super.toString(), "}");
	}

	private static final long serialVersionUID = 1L;

	private final String _field;
	private final Set<Object> _values = new HashSet<>();

}