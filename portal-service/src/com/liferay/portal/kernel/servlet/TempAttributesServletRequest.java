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

package com.liferay.portal.kernel.servlet;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Shuyang Zhou
 */
public class TempAttributesServletRequest extends HttpServletRequestWrapper {

	public TempAttributesServletRequest(HttpServletRequest request) {
		super(request);
	}

	@Override
	public Object getAttribute(String name) {
		Object value = _tempAttributes.get(name);

		if (value == _nullValue) {
			return null;
		}

		if (value != null) {
			return value;
		}

		return super.getAttribute(name);
	}

	@Override
	public Enumeration getAttributeNames() {
		Enumeration<String> superEnumeration = super.getAttributeNames();

		if (_tempAttributes.isEmpty()) {
			return superEnumeration;
		}

		Set<String> names = new HashSet<String>();

		while (superEnumeration.hasMoreElements()) {
			names.add(superEnumeration.nextElement());
		}

		names.addAll(_tempAttributes.keySet());

		return Collections.enumeration(names);
	}

	@Override
	public void removeAttribute(String name) {
		_tempAttributes.remove(name);

		super.removeAttribute(name);
	}

	public void setTempAttribute(String name, Object value) {
		if (value == null) {
			value = _nullValue;
		}

		_tempAttributes.put(name, value);
	}

	private static Object _nullValue = new Object();

	private Map<String, Object> _tempAttributes = new HashMap<String, Object>();

}