/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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