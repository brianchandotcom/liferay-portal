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

package com.liferay.portal.kernel.jsonwebservice;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Raymond Augé
 */
public class JSONWebServiceClassVisitorFactoryUtil {

	public static JSONWebServiceClassVisitor create(InputStream inputStream)
		throws IOException {

		return getJSONWebServiceClassVisitorFactory().create(inputStream);
	}

	public static JSONWebServiceClassVisitorFactory
		getJSONWebServiceClassVisitorFactory() {

		PortalRuntimePermission.checkGetBeanProperty(
			JSONWebServiceClassVisitorFactoryUtil.class);

		return _jsonWebServiceClassVisitorFactory;
	}

	public void setJSONWebServiceClassVisitorFactory(
		JSONWebServiceClassVisitorFactory jsonWebServiceClassVisitorFactory) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_jsonWebServiceClassVisitorFactory = jsonWebServiceClassVisitorFactory;
	}

	private static JSONWebServiceClassVisitorFactory
		_jsonWebServiceClassVisitorFactory;

}