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

import java.io.Serializable;

import java.util.Arrays;

/**
 * @author Alexander Chow
 */
public class Tuple implements Serializable {

	public Tuple(Object... array) {
		_array = array;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Tuple)) {
			return false;
		}

		Tuple tuple = (Tuple)obj;

		return Arrays.equals(_array, tuple._array);
	}

	public Object getObject(int i) {
		return _array[i];
	}

	public int getSize() {
		return _array.length;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(_array);
	}

	@Override
	public String toString() {
		return Arrays.toString(_array);
	}

	private final Object[] _array;

}