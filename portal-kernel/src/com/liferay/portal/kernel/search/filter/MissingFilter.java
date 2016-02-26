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

package com.liferay.portal.kernel.search.filter;

import com.liferay.portal.kernel.util.StringBundler;

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
		StringBundler sb = new StringBundler(9);

		sb.append("{(");
		sb.append(_field);
		sb.append(", _exists=");
		sb.append(_exists);
		sb.append(", _nullValue=");
		sb.append(_nullValue);
		sb.append("), ");
		sb.append(super.toString());
		sb.append("}");

		return sb.toString();
	}

	private Boolean _exists;
	private final String _field;
	private Boolean _nullValue;

}