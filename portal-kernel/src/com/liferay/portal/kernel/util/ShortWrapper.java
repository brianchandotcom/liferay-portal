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
public class ShortWrapper
	extends PrimitiveWrapper implements Comparable<ShortWrapper> {

	public static final Class<?> TYPE = Short.TYPE;

	public ShortWrapper() {
		this((short)0);
	}

	public ShortWrapper(short value) {
		_value = value;
	}

	@Override
	public int compareTo(ShortWrapper shortWrapper) {
		if (shortWrapper == null) {
			return 1;
		}

		if (getValue() > shortWrapper.getValue()) {
			return 1;
		}
		else if (getValue() < shortWrapper.getValue()) {
			return -1;
		}

		return 0;
	}

	public short decrement() {
		return --_value;
	}

	public short getValue() {
		return _value;
	}

	public short increment() {
		return ++_value;
	}

	public void setValue(short value) {
		_value = value;
	}

	private short _value;

}