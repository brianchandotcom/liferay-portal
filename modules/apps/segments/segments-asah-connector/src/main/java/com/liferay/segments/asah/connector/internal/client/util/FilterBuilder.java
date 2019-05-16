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

package com.liferay.segments.asah.connector.internal.client.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthew Kong
 * @author David Arques
 */
public class FilterBuilder {

	public void addFilter(String fieldName, String operator, Object value) {
		_addFilter(fieldName, operator, value, true);
	}

	public void addNullFilter(String fieldName, String operator) {
		_requiredFilters.add(FilterUtil.getNullFilter(fieldName, operator));
	}

	public String build() {
		StringBundler sb = new StringBundler();

		if (!_filters.isEmpty()) {
			_buildQueries(sb, _filters, FilterConstants.LOGICAL_OPERATOR_OR);

			if (!_requiredFilters.isEmpty()) {
				sb.append(FilterConstants.LOGICAL_OPERATOR_AND);
			}
		}

		if (!_requiredFilters.isEmpty()) {
			_buildQueries(
				sb, _requiredFilters, FilterConstants.LOGICAL_OPERATOR_AND);
		}

		return sb.toString();
	}

	private void _addFilter(
		String fieldName, String operator, Object value, boolean required) {

		if (Validator.isNull(fieldName) || Validator.isNull(operator) ||
			Validator.isNull(value)) {

			return;
		}

		if (value instanceof String) {
			value = _getValue(value);
		}

		String filter = FilterUtil.getFilter(fieldName, operator, value);

		if (required) {
			_requiredFilters.add(filter);
		}
		else {
			_filters.add(filter);
		}
	}

	private void _buildQueries(
		StringBundler sb, List<String> filterQueries, String operator) {

		sb.append(StringPool.OPEN_PARENTHESIS);

		for (String filterQuery : filterQueries) {
			sb.append(filterQuery);
			sb.append(operator);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(StringPool.CLOSE_PARENTHESIS);
	}

	private String _getValue(Object value) {
		String valueString = (String)value;

		valueString = valueString.replaceAll(
			StringPool.APOSTROPHE, StringPool.DOUBLE_APOSTROPHE);

		return valueString;
	}

	private final List<String> _filters = new ArrayList<>();
	private final List<String> _requiredFilters = new ArrayList<>();

}