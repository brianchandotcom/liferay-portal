/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.query;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.search.query.DateRangeTermQuery;
import com.liferay.portal.search.query.QueryVisitor;
import com.liferay.portal.search.query.RangeTermQuery;

import java.util.TimeZone;

/**
 * @author Michael C. Han
 */
public class DateRangeTermQueryImpl extends DateRangeTermQuery {

	public DateRangeTermQueryImpl(
		String field, boolean includesLower, boolean includesUpper,
		String startDate, String endDate) {

		_field = field;
		_includesLower = includesLower;
		_includesUpper = includesUpper;
		_lowerBound = startDate;
		_upperBound = endDate;

		_setOperators(includesLower, includesUpper);
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
			"{(", _toString(), "), ", _dateFormat, ", ", _timeZone, ")}");
	}

	private static final long serialVersionUID = 1L;

	private String _dateFormat = "yyyyMMddHHmmss";
	private TimeZone _timeZone = TimeZoneUtil.getDefault();

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public Object getLowerBound() {
		return _lowerBound;
	}

	@Override
	public RangeTermQuery.Operator getLowerBoundOperator() {
		return _lowerBoundOperator;
	}

	@Override
	public Object getUpperBound() {
		return _upperBound;
	}

	@Override
	public Operator getUpperBoundOperator() {
		return _upperBoundOperator;
	}

	@Override
	public boolean isIncludesLower() {
		return _includesLower;
	}

	@Override
	public boolean isIncludesUpper() {
		return _includesUpper;
	}

	@Override
	public void setLowerBound(Object lowerBound) {
		_lowerBound = lowerBound;
	}

	@Override
	public void setUpperBound(Object upperBound) {
		_upperBound = upperBound;
	}

	private String _toString() {
		return StringBundler.concat(
			"{(", _lowerBound, _lowerBoundOperator, _field, _upperBoundOperator,
			_upperBound, "), ", super.toString(), "}");
	}

	private void _setOperators(boolean includesLower, boolean includesUpper) {
		if (includesLower) {
			_lowerBoundOperator = Operator.GTE;
		}
		else {
			_lowerBoundOperator = Operator.GT;
		}

		if (includesUpper) {
			_upperBoundOperator = Operator.LTE;
		}
		else {
			_upperBoundOperator = Operator.LT;
		}
	}

	private final String _field;
	private final boolean _includesLower;
	private final boolean _includesUpper;
	private Object _lowerBound;
	private Operator _lowerBoundOperator;
	private Object _upperBound;
	private Operator _upperBoundOperator;

}