/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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