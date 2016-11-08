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

package com.liferay.websocket.whiteboard.test.encode.data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Cristina González
 */
@XmlRootElement
public class Example {

	public Example() {
	}

	public Example(int number, String data) {
		_number = number;
		_data = data;
	}

	public String getData() {
		return _data;
	}

	public int getNumber() {
		return _number;
	}

	public void setData(String data) {
		_data = data;
	}

	public void setNumber(int number) {
		_number = number;
	}

	private String _data;
	private int _number;

}