/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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