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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * This class ensures that portlet attributes and parameters are private to the
 * portlet.
 * </p>
 *
 * @author Brian Myunghun Kim
 */
public class NamespaceServletRequest extends DynamicServletRequest {

	public static Set<String> reservedAttrs = new HashSet<String>() {
		{
			add(JavaConstants.JAVAX_PORTLET_CONFIG);
			add(JavaConstants.JAVAX_PORTLET_PORTLET);
			add(JavaConstants.JAVAX_PORTLET_REQUEST);
			add(JavaConstants.JAVAX_PORTLET_RESPONSE);
			add(JavaConstants.JAVAX_SERVLET_FORWARD_CONTEXT_PATH);
			add(JavaConstants.JAVAX_SERVLET_FORWARD_PATH_INFO);
			add(JavaConstants.JAVAX_SERVLET_FORWARD_QUERY_STRING);
			add(JavaConstants.JAVAX_SERVLET_FORWARD_REQUEST_URI);
			add(JavaConstants.JAVAX_SERVLET_FORWARD_SERVLET_PATH);
			add(JavaConstants.JAVAX_SERVLET_INCLUDE_CONTEXT_PATH);
			add(JavaConstants.JAVAX_SERVLET_INCLUDE_PATH_INFO);
			add(JavaConstants.JAVAX_SERVLET_INCLUDE_QUERY_STRING);
			add(JavaConstants.JAVAX_SERVLET_INCLUDE_REQUEST_URI);
			add(JavaConstants.JAVAX_SERVLET_INCLUDE_SERVLET_PATH);
			add(MimeResponse.MARKUP_HEAD_ELEMENT);
			add(PortletRequest.LIFECYCLE_PHASE);
		}
	};

	public NamespaceServletRequest(
		HttpServletRequest httpServletRequest, String attrNamespace,
		String paramNamespace) {

		this(httpServletRequest, attrNamespace, paramNamespace, true);
	}

	public NamespaceServletRequest(
		HttpServletRequest httpServletRequest, String attrNamespace,
		String paramNamespace, boolean inherit) {

		super(httpServletRequest, inherit);

		_attrNamespace = attrNamespace;
		_paramNamespace = paramNamespace;
	}

	@Override
	public Object getAttribute(String name) {
		Object value = super.getAttribute(_attrNamespace + name);

		if (value == null) {
			value = super.getAttribute(name);
		}

		return value;
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		List<String> names = new ArrayList<>();

		Enumeration<String> enu = super.getAttributeNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.startsWith(_attrNamespace)) {
				names.add(name.substring(_attrNamespace.length()));
			}
			else if (_isReservedParam(name)) {
				names.add(name);
			}
		}

		return Collections.enumeration(names);
	}

	@Override
	public String getParameter(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		String value = super.getParameter(name);

		if (value == null) {
			value = super.getParameter(_paramNamespace + name);
		}

		return value;
	}

	@Override
	public void removeAttribute(String name) {
		if (_isReservedParam(name)) {
			super.removeAttribute(name);
		}
		else {
			super.removeAttribute(_attrNamespace + name);
		}
	}

	@Override
	public void setAttribute(String name, Object value) {
		if (_isReservedParam(name)) {
			super.setAttribute(name, value);
		}
		else {
			super.setAttribute(_attrNamespace + name, value);
		}
	}

	public void setAttribute(
		String name, Object value, boolean privateRequestAttribute) {

		if (!privateRequestAttribute) {
			super.setAttribute(name, value);
		}
		else {
			setAttribute(name, value);
		}
	}

	@Override
	protected void injectInto(DynamicServletRequest dynamicServletRequest) {
		dynamicServletRequest.setRequest(
			new NamespaceServletRequest(
				(HttpServletRequest)getRequest(), _attrNamespace,
				_paramNamespace));
	}

	private boolean _isReservedParam(String name) {
		if (reservedAttrs.contains(name)) {
			return true;
		}

		for (String requestSharedAttribute :
				PropsValues.REQUEST_SHARED_ATTRIBUTES) {

			if (name.startsWith(requestSharedAttribute)) {
				return true;
			}
		}

		return false;
	}

	private final String _attrNamespace;
	private final String _paramNamespace;

}