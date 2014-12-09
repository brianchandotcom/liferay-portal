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

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSON;

/**
 * @author Igor Spasic
 */
@JSON(strict = true)
public class Four {

	@JSON(name = "nuMber")
	public int getNumber() {
		return _number;
	}

	public long getPrivate() {
		return _private;
	}

	public String getValue() {
		return _value;
	}

	public void setNumber(int number) {
		_number = number;
	}

	public void setPrivate(long aPrivate) {
		_private = aPrivate;
	}

	public void setValue(String value) {
		_value = value;
	}

	private int _number = 173;
	private long _private = 0xCAFEBABE;

	@JSON(name = "vaLue")
	private String _value = "something";

}