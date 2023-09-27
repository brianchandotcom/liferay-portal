/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

/**
 * @author Brian Wing Shun Chan
 */
public class FloatWrapper
	extends PrimitiveWrapper implements Comparable<FloatWrapper> {

	public static final Class<?> TYPE = Float.TYPE;

	public FloatWrapper() {
		this(0F);
	}

	public FloatWrapper(float value) {
		_value = value;
	}

	@Override
	public int compareTo(FloatWrapper floatWrapper) {
		if (floatWrapper == null) {
			return 1;
		}

		if (getValue() > floatWrapper.getValue()) {
			return 1;
		}
		else if (getValue() < floatWrapper.getValue()) {
			return -1;
		}

		return 0;
	}

	public float decrement() {
		return --_value;
	}

	public float getValue() {
		return _value;
	}

	public float increment() {
		return ++_value;
	}

	public void setValue(float value) {
		_value = value;
	}

	private float _value;

}