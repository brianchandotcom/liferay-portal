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

package com.liferay.petra.json.web.service.client.model;

/**
 * @author Igor Beslic
 */
public class ResponseBody {

	public String getParameter1() {
		return _parameter1;
	}

	public String getParameter2() {
		return _parameter2;
	}

	public String getParameter3() {
		return _parameter3;
	}

	public void setParameter1(String parameter1) {
		_parameter1 = parameter1;
	}

	public void setParameter2(String parameter2) {
		_parameter2 = parameter2;
	}

	public void setParameter3(String parameter3) {
		_parameter3 = parameter3;
	}

	private String _parameter1;
	private String _parameter2;
	private String _parameter3;

}