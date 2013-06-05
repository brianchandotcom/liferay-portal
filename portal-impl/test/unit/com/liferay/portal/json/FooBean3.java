/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.json;

import com.liferay.portal.kernel.util.StringBundler;

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
		StringBundler sb = new StringBundler(7);

		sb.append("{doubleArray=");
		sb.append(Arrays.toString(_doubleArray));
		sb.append(", integerArray=");
		sb.append(Arrays.toString(_integerArray));
		sb.append(", longArray=");
		sb.append(Arrays.toString(_longArray));
		sb.append("}");

		return sb.toString();
	}

	private double[] _doubleArray;
	private int[] _integerArray;
	private long[] _longArray;

}