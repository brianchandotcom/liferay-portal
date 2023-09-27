/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.param;

/**
 * @author Matthew Kong
 */
public class FaroParam<T> {

	public FaroParam() {
	}

	public FaroParam(T value) {
		_value = value;
	}

	public T getValue() {
		return _value;
	}

	public void setValue(T value) {
		_value = value;
	}

	private T _value;

}