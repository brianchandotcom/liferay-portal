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

package com.liferay.portal.search.internal.filter;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.search.filter.FilterVisitor;
import com.liferay.portal.search.filter.TermsSetFilter;

import java.util.Collections;
import java.util.List;

/**
 * @author Marco Leo
 * @author André de Oliveira
 */
public class TermsSetFilterImpl implements TermsSetFilter {

	public TermsSetFilterImpl(String fieldName, List<String> values) {
		_fieldName = fieldName;
		_values = values;
	}

	@Override
	public <T> T accept(FilterVisitor<T> filterVisitor) {
		return filterVisitor.visit(this);
	}

	@Override
	public String getExecutionOption() {
		return null;
	}

	@Override
	public String getFieldName() {
		return _fieldName;
	}

	@Override
	public String getMinimumShouldMatchField() {
		return _minimumShouldMatchField;
	}

	@Override
	public int getSortOrder() {
		return 4;
	}

	@Override
	public List<String> getValues() {
		return Collections.unmodifiableList(_values);
	}

	@Override
	public Boolean isCached() {
		return true;
	}

	@Override
	public void setCached(Boolean cached) {
	}

	@Override
	public void setExecutionOption(String executionOption) {
	}

	public String setMinimumShouldMatchField(String minimumShouldMatchField) {
		return _minimumShouldMatchField = minimumShouldMatchField;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{(", _fieldName, "=", _values, "), (minimum_should_match_field=",
			_minimumShouldMatchField, ")}");
	}

	private final String _fieldName;
	private String _minimumShouldMatchField;
	private final List<String> _values;

}