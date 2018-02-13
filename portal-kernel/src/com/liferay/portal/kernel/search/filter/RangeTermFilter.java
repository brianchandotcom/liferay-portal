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

package com.liferay.portal.kernel.search.filter;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Michael C. Han
 */
public class RangeTermFilter extends BaseFilter {

	public RangeTermFilter(
		String field, boolean includesLower, boolean includesUpper) {

		_field = field;
		_includesLower = includesLower;
		_includesUpper = includesUpper;

		setOperators(includesLower, includesUpper);
	}

	public RangeTermFilter(
		String field, boolean includesLower, boolean includesUpper,
		String lowerBound, String upperBound) {

		_field = field;
		_includesLower = includesLower;
		_includesUpper = includesUpper;
		_lowerBound = lowerBound;
		_upperBound = upperBound;

		setOperators(includesLower, includesUpper);
	}

	@Override
	public <T> T accept(FilterVisitor<T> filterVisitor) {
		return filterVisitor.visit(this);
	}

	public String getField() {
		return _field;
	}

	public String getLowerBound() {
		return _lowerBound;
	}

	public Operator getLowerBoundOperator() {
		return _lowerBoundOperator;
	}

	@Override
	public int getSortOrder() {
		return 20;
	}

	public String getUpperBound() {
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

	public void setLowerBound(String lowerBound) {
		_lowerBound = lowerBound;
	}

	public void setUpperBound(String upperBound) {
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

	public enum Operator {

		GT, GTE, LT, LTE;

		@Override
		public String toString() {
			String name = name();

			if (name.equals(GT.name())) {
				return StringPool.GREATER_THAN;
			}
			else if (name.equals(GTE.name())) {
				return StringPool.GREATER_THAN_OR_EQUAL;
			}
			else if (name.equals(LT.name())) {
				return StringPool.LESS_THAN;
			}
			else if (name.equals(LTE.name())) {
				return StringPool.GREATER_THAN_OR_EQUAL;
			}

			return StringPool.BLANK;
		}

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

	private final String _field;
	private final boolean _includesLower;
	private final boolean _includesUpper;
	private String _lowerBound;
	private Operator _lowerBoundOperator;
	private String _upperBound;
	private Operator _upperBoundOperator;

}