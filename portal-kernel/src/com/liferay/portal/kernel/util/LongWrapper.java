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
public class LongWrapper
	extends PrimitiveWrapper implements Comparable<LongWrapper> {

	public static final Class<?> TYPE = Long.TYPE;

	public LongWrapper() {
		this(0L);
	}

	public LongWrapper(long value) {
		_value = value;
	}

	@Override
	public int compareTo(LongWrapper longWrapper) {
		if (longWrapper == null) {
			return 1;
		}

		if (getValue() > longWrapper.getValue()) {
			return 1;
		}
		else if (getValue() < longWrapper.getValue()) {
			return -1;
		}

		return 0;
	}

	public long decrement() {
		return --_value;
	}

	public long getValue() {
		return _value;
	}

	public long increment() {
		return ++_value;
	}

	public void setValue(long value) {
		_value = value;
	}

	private long _value;

}