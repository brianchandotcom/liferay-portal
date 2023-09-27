/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json;

import com.liferay.petra.string.StringBundler;

/**
 * @author Miguel Pastor
 */
public class FooBean5 {

	public double getDoubleValue() {
		return _doubleValue;
	}

	public int getIntegerValue() {
		return _integerValue;
	}

	public long getLongValue() {
		return _longValue;
	}

	public void setDoubleValue(double doubleValue) {
		_doubleValue = doubleValue;
	}

	public void setIntegerValue(int integerValue) {
		_integerValue = integerValue;
	}

	public void setLongValue(long longValue) {
		_longValue = longValue;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{doubleValue=", _doubleValue, ", integerValue=", _integerValue,
			", longValue=", _longValue, "}");
	}

	private double _doubleValue;
	private int _integerValue;
	private long _longValue;

}