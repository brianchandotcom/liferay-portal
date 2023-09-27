/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.query;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.search.query.DateRangeTermQuery;
import com.liferay.portal.search.query.QueryVisitor;

import java.util.TimeZone;

/**
 * @author Michael C. Han
 */
public class DateRangeTermQueryImpl
	extends RangeTermQueryImpl implements DateRangeTermQuery {

	public DateRangeTermQueryImpl(
		String field, boolean includesLower, boolean includesUpper,
		String startDate, String endDate) {

		super(field, includesLower, includesUpper, startDate, endDate);
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	@Override
	public String getDateFormat() {
		return _dateFormat;
	}

	@Override
	public int getSortOrder() {
		return 25;
	}

	@Override
	public TimeZone getTimeZone() {
		return _timeZone;
	}

	@Override
	public void setDateFormat(String dateFormat) {
		_dateFormat = dateFormat;
	}

	@Override
	public void setTimeZone(TimeZone timeZone) {
		_timeZone = timeZone;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{(", super.toString(), "), ", _dateFormat, ", ", _timeZone, ")}");
	}

	private static final long serialVersionUID = 1L;

	private String _dateFormat = "yyyyMMddHHmmss";
	private TimeZone _timeZone = TimeZoneUtil.getDefault();

}