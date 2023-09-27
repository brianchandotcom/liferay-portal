/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.filter;

import com.liferay.petra.string.StringBundler;

/**
 * @author Michael C. Han
 */
public class MissingFilter extends BaseFilter {

	public MissingFilter(String field) {
		_field = field;
	}

	@Override
	public <T> T accept(FilterVisitor<T> filterVisitor) {
		return filterVisitor.visit(this);
	}

	public String getField() {
		return _field;
	}

	@Override
	public int getSortOrder() {
		return 2;
	}

	public Boolean isExists() {
		return _exists;
	}

	public Boolean isNullValue() {
		return _nullValue;
	}

	public void setExists(boolean exists) {
		_exists = exists;
	}

	public void setNullValue(boolean nullValue) {
		_nullValue = nullValue;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{(", _field, ", _exists=", _exists, ", _nullValue=", _nullValue,
			"), ", super.toString(), "}");
	}

	private Boolean _exists;
	private final String _field;
	private Boolean _nullValue;

}