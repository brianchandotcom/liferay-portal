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

package com.liferay.portal.kernel.util;

/**
 * @author Brian Wing Shun Chan
 */
public class DoubleWrapper
	extends PrimitiveWrapper implements Comparable<DoubleWrapper> {

	public static final Class<?> TYPE = Double.TYPE;

	public DoubleWrapper() {
		this(0D);
	}

	public DoubleWrapper(double value) {
		_value = value;
	}

	@Override
	public int compareTo(DoubleWrapper doubleWrapper) {
		if (doubleWrapper == null) {
			return 1;
		}

		if (getValue() > doubleWrapper.getValue()) {
			return 1;
		}
		else if (getValue() < doubleWrapper.getValue()) {
			return -1;
		}

		return 0;
	}

	public double decrement() {
		return --_value;
	}

	public double getValue() {
		return _value;
	}

	public double increment() {
		return ++_value;
	}

	public void setValue(double value) {
		_value = value;
	}

	private double _value;

}