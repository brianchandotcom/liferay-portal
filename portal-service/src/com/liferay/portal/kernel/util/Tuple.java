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

package com.liferay.portal.kernel.util;

import java.io.Serializable;

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

		if (tuple._array.length != _array.length) {
			return false;
		}

		for (int i = 0; i < _array.length; i++) {
			if ((tuple._array != null) && (_array[i] != null) &&
				!_array[i].equals(tuple._array[i])) {

				return false;
			}
			else if ((tuple._array[i] == null) || (_array[i] == null)) {
				return false;
			}
		}

		return true;
	}

	public Object getObject(int i) {
		return _array[i];
	}

	public int getSize() {
		return _array.length;
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		for (int i = 0; i < _array.length; i++) {
			hashCode = hashCode ^ _array[i].hashCode();
		}

		return hashCode;
	}

	private Object[] _array;

}