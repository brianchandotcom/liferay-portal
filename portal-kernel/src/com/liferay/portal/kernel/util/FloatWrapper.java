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