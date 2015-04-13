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

package com.liferay.portal.kernel.increment;

/**
 * @author Shuyang Zhou
 */
public abstract class OverrideIncrement<T extends Comparable<T>>
	implements Increment<T> {

	public OverrideIncrement(T value) {
		this.value = value;
	}

	@Override
	public void decrease(T delta) {
		if (value.compareTo(delta) > 0) {
			value = delta;
		}
	}

	@Override
	public OverrideIncrement<T> decreaseForNew(T delta) {
		if (value.compareTo(delta) < 0) {
			delta = value;
		}

		return createOverrideIncrement(delta);
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void increase(T delta) {
		if (value.compareTo(delta) < 0) {
			value = delta;
		}
	}

	@Override
	public OverrideIncrement<T> increaseForNew(T delta) {
		if (value.compareTo(delta) > 0) {
			delta = value;
		}

		return createOverrideIncrement(delta);
	}

	@Override
	public void setValue(T value) {
		this.value = value;
	}

	protected abstract OverrideIncrement<T> createOverrideIncrement(T value);

	protected T value;

}