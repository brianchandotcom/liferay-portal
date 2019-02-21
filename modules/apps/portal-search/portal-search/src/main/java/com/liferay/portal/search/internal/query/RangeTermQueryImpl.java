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

package com.liferay.portal.search.internal.query;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.search.query.QueryVisitor;
import com.liferay.portal.search.query.RangeTermQuery;

/**
 * @author Michael C. Han
 */
public class RangeTermQueryImpl
	extends BaseQueryImpl implements RangeTermQuery {

	public RangeTermQueryImpl(
		String field, boolean includesLower, boolean includesUpper) {

		_field = field;
		_includesLower = includesLower;
		_includesUpper = includesUpper;

		setOperators(includesLower, includesUpper);
	}

	public RangeTermQueryImpl(
		String field, boolean includesLower, boolean includesUpper,
		Object lowerBound, Object upperBound) {

		_field = field;
		_includesLower = includesLower;
		_includesUpper = includesUpper;
		_lowerBound = lowerBound;
		_upperBound = upperBound;

		setOperators(includesLower, includesUpper);
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	public String getField() {
		return _field;
	}

	public Object getLowerBound() {
		return _lowerBound;
	}

	public RangeTermQuery.Operator getLowerBoundOperator() {
		return _lowerBoundOperator;
	}

	public int getSortOrder() {
		return 20;
	}

	public Object getUpperBound() {
		return _upperBound;
	}

	public Operator getUpperBoundOperator() {
		return _upperBoundOperator;
	}

	public boolean isIncludesLower() {
		return _includesLower;
	}

	public boolean isIncludesUpper() {
		return _includesUpper;
	}

	public void setLowerBound(Object lowerBound) {
		_lowerBound = lowerBound;
	}

	public void setUpperBound(Object upperBound) {
		_upperBound = upperBound;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(9);

		sb.append("{(");
		sb.append(_lowerBound);
		sb.append(_lowerBoundOperator);
		sb.append(_field);
		sb.append(_upperBoundOperator);
		sb.append(_upperBound);
		sb.append("), ");
		sb.append(super.toString());
		sb.append("}");

		return sb.toString();
	}

	protected void setOperators(boolean includesLower, boolean includesUpper) {
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

	private static final long serialVersionUID = 1L;

	private final String _field;
	private final boolean _includesLower;
	private final boolean _includesUpper;
	private Object _lowerBound;
	private Operator _lowerBoundOperator;
	private Object _upperBound;
	private Operator _upperBoundOperator;

}