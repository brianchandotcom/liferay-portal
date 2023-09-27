/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.filter.range;

/**
 * @author Adam Brandizzi
 */
public class RangeTermQueryValue {

	public RangeTermQueryValue() {
	}

	public RangeTermQueryValue(RangeTermQueryValue rangeTermQueryValue) {
		_includesLower = rangeTermQueryValue._includesLower;
		_includesUpper = rangeTermQueryValue._includesUpper;
		_lowerBound = rangeTermQueryValue._lowerBound;
		_upperBound = rangeTermQueryValue._upperBound;
	}

	public String getLowerBound() {
		return _lowerBound;
	}

	public String getUpperBound() {
		return _upperBound;
	}

	public boolean isIncludesLower() {
		return _includesLower;
	}

	public boolean isIncludesUpper() {
		return _includesUpper;
	}

	public static class Builder {

		public RangeTermQueryValue build() {
			return new RangeTermQueryValue(_rangeTermQueryValue);
		}

		public void includesLower(boolean includesLower) {
			_rangeTermQueryValue._includesLower = includesLower;
		}

		public void includesUpper(boolean includesUpper) {
			_rangeTermQueryValue._includesUpper = includesUpper;
		}

		public void lowerBound(String lowerBound) {
			_rangeTermQueryValue._lowerBound = lowerBound;
		}

		public void upperBound(String upperBound) {
			_rangeTermQueryValue._upperBound = upperBound;
		}

		private final RangeTermQueryValue _rangeTermQueryValue =
			new RangeTermQueryValue();

	}

	private boolean _includesLower;
	private boolean _includesUpper;
	private String _lowerBound;
	private String _upperBound;

}