/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.filter;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.TimeZoneUtil;

import java.util.TimeZone;

/**
 * @author Michael C. Han
 */
public class DateRangeTermFilter extends RangeTermFilter {

	public DateRangeTermFilter(
		String field, boolean includesLower, boolean includesUpper,
		String startDate, String endDate) {

		super(field, includesLower, includesUpper, startDate, endDate);
	}

	public String getDateFormat() {
		return _dateFormat;
	}

	@Override
	public int getSortOrder() {
		return 25;
	}

	public TimeZone getTimeZone() {
		return _timeZone;
	}

	public void setDateFormat(String dateFormat) {
		_dateFormat = dateFormat;
	}

	public void setTimeZone(TimeZone timeZone) {
		_timeZone = timeZone;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{(", super.toString(), "), ", _dateFormat, ", ", _timeZone, ")}");
	}

	private String _dateFormat = "yyyyMMddHHmmss";
	private TimeZone _timeZone = TimeZoneUtil.getDefault();

}