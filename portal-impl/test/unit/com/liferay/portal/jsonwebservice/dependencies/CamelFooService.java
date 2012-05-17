/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.jsonwebservice.dependencies;

import com.liferay.portal.kernel.jsonwebservice.JSONWebService;

/**
 * @author Igor Spasic
 */
public class CamelFooService {

	@JSONWebService("cool-new-world")
	public static void braveNewWorld() {
	}

	public static void hello() {
	}

	public static void helloWorld() {
	}

	@JSONWebService(method = "POST")
	public static String post(String value) {
		return "post " + value;
	}

}