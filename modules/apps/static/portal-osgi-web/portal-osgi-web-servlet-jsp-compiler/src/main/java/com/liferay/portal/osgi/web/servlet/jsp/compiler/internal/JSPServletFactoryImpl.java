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

package com.liferay.portal.osgi.web.servlet.jsp.compiler.internal;

import com.liferay.portal.osgi.web.servlet.JSPServletFactory;

import javax.servlet.Servlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = JSPServletFactory.class)
public class JSPServletFactoryImpl implements JSPServletFactory {

	@Override
	public Servlet createJSPServlet() {
		return new JspServlet();
	}

}