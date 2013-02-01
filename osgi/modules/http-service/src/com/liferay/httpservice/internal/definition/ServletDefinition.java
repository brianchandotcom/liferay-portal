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

package com.liferay.httpservice.internal.definition;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.Servlet;

/**
 * @author Raymond Augé
 */
public class ServletDefinition {

	public ServletDefinition() {
	}

	public ServletDefinition(
		String name, Servlet servlet, Dictionary<Object, Object> initParams) {

		_initParams = initParams;
		_name = name;
		_servlet = servlet;
	}

	public Dictionary<Object, Object> getInitParams() {
		return _initParams;
	}

	public String getName() {
		return _name;
	}

	public Servlet getServlet() {
		return _servlet;
	}

	public void setInitParam(Object key, Object value) {
		if (_initParams == null) {
			_initParams = new Hashtable<Object, Object>();
		}

		_initParams.put(key, value);
	}

	public void setInitParams(Dictionary<Object, Object> initParams) {
		_initParams = initParams;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setServlet(Servlet servlet) {
		_servlet = servlet;
	}

	private Dictionary<Object, Object> _initParams;
	private String _name;
	private Servlet _servlet;

}