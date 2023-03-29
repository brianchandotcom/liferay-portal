/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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