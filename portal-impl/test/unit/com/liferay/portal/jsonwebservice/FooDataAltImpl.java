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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.kernel.json.JSON;

/**
 * @author Igor Spasic
 */
public class FooDataAltImpl implements FooData {

	public int[] getArray() {
		return _array;
	}

	public int getHeight() {
		return _height;
	}

	public int getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	@Override
	public String getValue() {
		return _value;
	}

	public void setArray(int... array) {
		_array = array;
	}

	public void setHeight(int height) {
		_height = height;
	}

	@Override
	public void setId(int id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setValue(String value) {
		_value = value;
	}

	@JSON(include = true)
	private int[] _array;

	private int _height = 177;
	private int _id = -1;
	private String _name = "John Doe";

	@JSON(include = false)
	private String _value;

}