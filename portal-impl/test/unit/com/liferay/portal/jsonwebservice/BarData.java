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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.kernel.json.JSON;

/**
 * @author Igor Spasic
 */
public class BarData {

	@JSON
	public int[] getArray() {
		return _array;
	}

	@JSON(include = false)
	public String getSecret() {
		return _secret;
	}

	public String getValue() {
		return _value;
	}

	public void setArray(int[] array) {
		_array = array;
	}

	public void setSecret(String secret) {
		_secret = secret;
	}

	public void setValue(String value) {
		_value = value;
	}

	private int[] _array = {1, 2, 3};
	private String _secret = "secret";
	private String _value = "value";

}