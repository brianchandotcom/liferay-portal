/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.atom;

import com.liferay.portal.kernel.atom.AtomRequestContext;
import com.liferay.portal.kernel.util.GetterUtil;

import org.apache.abdera.protocol.server.RequestContext;

/**
 * @author Igor Spasic
 */
public class AtomRequestContextImpl implements AtomRequestContext {

	public AtomRequestContextImpl(RequestContext requestContext) {
		_requestContext = requestContext;
	}

	public Object getContainerAttribute(String name) {
		return _requestContext.getAttribute(
			RequestContext.Scope.CONTAINER, name);
	}

	public String getHeader(String name) {
		return _requestContext.getHeader(name);
	}

	public int getIntParameter(String name) {
		String value = _requestContext.getParameter(name);

		return GetterUtil.getInteger(value);
	}

	public int getIntParameter(String name, int defaultValue) {
		String value = _requestContext.getParameter(name);

		return GetterUtil.getInteger(value, defaultValue);
	}

	public long getLongParameter(String name) {
		String value = _requestContext.getParameter(name);

		return GetterUtil.getLong(value);
	}

	public long getLongParameter(String name, long defaultValue) {
		String value = _requestContext.getParameter(name);

		return GetterUtil.getLong(value, defaultValue);
	}

	public String getParameter(String name) {
		return _requestContext.getParameter(name);
	}

	public String getParameter(String name, String defaultValue) {
		String value = _requestContext.getParameter(name);

		return GetterUtil.getString(value, defaultValue);
	}

	public Object getRequestAttribute(String name) {
		return _requestContext.getAttribute(RequestContext.Scope.REQUEST, name);
	}

	public String getResolvedUri() {
		return _requestContext.getResolvedUri().toString();
	}

	public Object getSessionAttribute(String name) {
		return _requestContext.getAttribute(RequestContext.Scope.SESSION, name);
	}

	public String getTargetBasePath() {
		return _requestContext.getTargetBasePath();
	}

	public void setContainerAttribute(String name, Object value) {
		_requestContext.setAttribute(
			RequestContext.Scope.CONTAINER, name, value);
	}

	public void setRequestAttribute(String name, Object value) {
		_requestContext.setAttribute(RequestContext.Scope.REQUEST, name, value);
	}

	public void setSessionAttribute(String name, Object value) {
		_requestContext.setAttribute(RequestContext.Scope.SESSION, name, value);
	}

	private RequestContext _requestContext;

}