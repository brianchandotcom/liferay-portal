/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.filter;

import com.liferay.portal.search.filter.RangeFilter;
import com.liferay.portal.search.filter.RangeFilterBuilder;

/**
 * @author Petteri Karttunen
 */
public class RangeFilterBuilderImpl implements RangeFilterBuilder {

	@Override
	public RangeFilter build() {
		RangeFilterImpl rangeFilterImpl = new RangeFilterImpl(_fieldName);

		rangeFilterImpl.setFormat(_format);
		rangeFilterImpl.setFrom(_from);
		rangeFilterImpl.setIncludeLower(_includeLower);
		rangeFilterImpl.setIncludeUpper(_includeUpper);
		rangeFilterImpl.setTimeZoneId(_timeZoneId);
		rangeFilterImpl.setTo(_to);

		return rangeFilterImpl;
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