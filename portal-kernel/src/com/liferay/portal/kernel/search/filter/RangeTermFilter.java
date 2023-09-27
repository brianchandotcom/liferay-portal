/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.filter;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

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
		return StringBundler.concat(
			"{(", _lowerBound, _lowerBoundOperator, _field, _upperBoundOperator,
			_upperBound, "), ", super.toString(), "}");
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
				return StringPool.LESS_THAN_OR_EQUAL;
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