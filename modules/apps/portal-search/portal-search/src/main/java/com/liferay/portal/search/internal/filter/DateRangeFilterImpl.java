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

import com.liferay.portal.search.filter.DateRangeFilter;
import com.liferay.portal.search.filter.FilterVisitor;

/**
 * @author André de Oliveira
 */
public class DateRangeFilterImpl implements DateRangeFilter {

	public DateRangeFilterImpl(String fieldName) {
		_fieldName = fieldName;
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
	public String getFormat() {
		return _format;
	}

	@Override
	public String getFrom() {
		return _from;
	}

	@Override
	public int getSortOrder() {
		return 25;
	}

	@Override
	public String getTimeZoneId() {
		return _timeZoneId;
	}

	@Override
	public String getTo() {
		return _to;
	}

	@Override
	public Boolean isCached() {
		return true;
	}

	@Override
	public boolean isIncludeLower() {
		return _includeLower;
	}

	@Override
	public boolean isIncludeUpper() {
		return _includeUpper;
	}

	@Override
	public void setCached(Boolean cached) {
	}

	@Override
	public void setExecutionOption(String executionOption) {
	}

	public void setFormat(String format) {
		_format = format;
	}

	public void setFrom(String from) {
		_from = from;
	}

	public void setIncludeLower(boolean includeLower) {
		_includeLower = includeLower;
	}

	public void setIncludeUpper(boolean includeUpper) {
		_includeUpper = includeUpper;
	}

	public void setTimeZoneId(String timeZoneId) {
		_timeZoneId = timeZoneId;
	}

	public void setTo(String to) {
		_to = to;
	}

	private final String _fieldName;
	private String _format;
	private String _from;
	private boolean _includeLower;
	private boolean _includeUpper;
	private String _timeZoneId;
	private String _to;

}