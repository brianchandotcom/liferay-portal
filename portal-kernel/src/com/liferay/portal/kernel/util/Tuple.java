/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Tuple)) {
			return false;
		}

		Tuple tuple = (Tuple)object;

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