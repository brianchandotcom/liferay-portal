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
import com.liferay.portal.search.filter.DateRangeFilterBuilder;

/**
 * @author André de Oliveira
 */
public class DateRangeFilterBuilderImpl implements DateRangeFilterBuilder {

	@Override
	public DateRangeFilter build() {
		DateRangeFilterImpl dateRangeFilterImpl = new DateRangeFilterImpl(
			_fieldName);

		dateRangeFilterImpl.setFormat(_format);
		dateRangeFilterImpl.setFrom(_from);
		dateRangeFilterImpl.setIncludeLower(_includeLower);
		dateRangeFilterImpl.setIncludeUpper(_includeUpper);
		dateRangeFilterImpl.setTimeZoneId(_timeZoneId);
		dateRangeFilterImpl.setTo(_to);

		return dateRangeFilterImpl;
	}

	@Override
	public void setFieldName(String fieldName) {
		_fieldName = fieldName;
	}

	@Override
	public void setFormat(String format) {
		_format = format;
	}

	@Override
	public void setFrom(String from) {
		_from = from;
	}

	@Override
	public void setIncludeLower(boolean includeLower) {
		_includeLower = includeLower;
	}

	@Override
	public void setIncludeUpper(boolean includeUpper) {
		_includeUpper = includeUpper;
	}

	@Override
	public void setTimeZoneId(String timeZoneId) {
		_timeZoneId = timeZoneId;
	}

	@Override
	public void setTo(String to) {
		_to = to;
	}

	private String _fieldName;
	private String _format;
	private String _from;
	private boolean _includeLower;
	private boolean _includeUpper;
	private String _timeZoneId;
	private String _to;

}