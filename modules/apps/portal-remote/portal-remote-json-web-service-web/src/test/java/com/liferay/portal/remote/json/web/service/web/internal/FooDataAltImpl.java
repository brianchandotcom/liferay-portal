/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal;

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