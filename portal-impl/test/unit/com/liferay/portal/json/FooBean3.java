/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json;

import com.liferay.petra.string.StringBundler;

import java.util.Arrays;

/**
 * @author Miguel Pastor
 */
public class FooBean3 {

	public double[] getDoubleArray() {
		return _doubleArray;
	}

	public int[] getIntegerArray() {
		return _integerArray;
	}

	public long[] getLongArray() {
		return _longArray;
	}

	public void setDoubleArray(double[] doubleArray) {
		_doubleArray = doubleArray;
	}

	public void setIntegerArray(int[] integerArray) {
		_integerArray = integerArray;
	}

	public void setLongArray(long[] longArray) {
		_longArray = longArray;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{doubleArray=", Arrays.toString(_doubleArray), ", integerArray=",
			Arrays.toString(_integerArray), ", longArray=",
			Arrays.toString(_longArray), "}");
	}

	private double[] _doubleArray;
	private int[] _integerArray;
	private long[] _longArray;

}