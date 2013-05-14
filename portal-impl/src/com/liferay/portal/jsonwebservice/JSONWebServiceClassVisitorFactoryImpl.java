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

import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceClassVisitor;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceClassVisitorFactory;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Raymond Augé
 */
@DoPrivileged
public class JSONWebServiceClassVisitorFactoryImpl
	implements JSONWebServiceClassVisitorFactory {

	@Override
	public JSONWebServiceClassVisitor create(InputStream inputStream)
		throws IOException {

		return new JSONWebServiceClassVisitorImpl(inputStream);
	}

}