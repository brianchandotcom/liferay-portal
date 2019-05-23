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

package com.liferay.portal.vulcan.internal.fields.nested.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Ivica Cardic
 */
public class NestedFieldsHttpServletRequestWrapper
	extends HttpServletRequestWrapper {

	public NestedFieldsHttpServletRequestWrapper(
		String fieldName, HttpServletRequest httpServletRequest) {

		super(httpServletRequest);

		_fieldName = fieldName;
	}

	@Override
	public String getParameter(String name) {
		return super.getParameter(getNestedFieldsParameterName(name));
	}

	protected String getNestedFieldsParameterName(String name) {
		return _fieldName + "." + name;
	}

	private final String _fieldName;

}