/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

/**
 * @author Brian Wing Shun Chan
 */
public class IntegerWrapper
	extends PrimitiveWrapper implements Comparable<IntegerWrapper> {

	public static final Class<?> TYPE = Integer.TYPE;

	public IntegerWrapper() {
		this(0);
	}

	public IntegerWrapper(int value) {
		_value = value;
	}

	@Override
	public int compareTo(IntegerWrapper integerWrapper) {
		if (integerWrapper == null) {
			return 1;
		}

		if (getValue() > integerWrapper.getValue()) {
			return 1;
		}
		else if (getValue() < integerWrapper.getValue()) {
			return -1;
		}

		return 0;
	}

	public int decrement() {
		return --_value;
	}

	public int getValue() {
		return _value;
	}

	public int increment() {
		return ++_value;
	}

	public void setValue(int value) {
		_value = value;
	}

	private int _value;

}