/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.remote.json.web.service.web.internal;

import com.liferay.petra.string.StringBundler;

/**
 * @author Igor Spasic
 */
public class FooDataImpl implements FooData {

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

	@Override
	public String toString() {
		return StringBundler.concat(
			"h=", _height, "/id=", _id, "/n=", _name, "/v=", _value);
	}

	private int _height = 177;
	private int _id = -1;
	private String _name = "John Doe";
	private String _value = "foo!";

}