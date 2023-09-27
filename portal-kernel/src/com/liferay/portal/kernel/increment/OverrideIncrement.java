/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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