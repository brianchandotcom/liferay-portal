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

package com.liferay.portal.xmlrpc.bundle.xmlrpcmethodutil;

import com.liferay.portal.kernel.xmlrpc.Method;
import com.liferay.portal.kernel.xmlrpc.Response;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE
)
public class TestMethod implements Method {

	public static String METHOD_NAME = "METHOD_NAME";

	public static String TOKEN = "TOKEN";

	@Override
	public Response execute(long companyId) {
		return null;
	}

	@Override
	public String getMethodName() {
		return METHOD_NAME;
	}

	@Override
	public String getToken() {
		return TOKEN;
	}

	@Override
	public boolean setArguments(Object[] arguments) {
		return false;
	}

}