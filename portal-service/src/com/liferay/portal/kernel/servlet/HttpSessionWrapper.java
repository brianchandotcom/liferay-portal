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

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 */
public class HttpSessionWrapper implements HttpSession {

	public HttpSessionWrapper(HttpSession session) {
		_session = session;
	}

	public Object getAttribute(String name) {
		return _session.getAttribute(name);
	}

	public Enumeration<String> getAttributeNames() {
		return _session.getAttributeNames();
	}

	public long getCreationTime() {
		return _session.getCreationTime();
	}

	public String getId() {
		return _session.getId();
	}

	public long getLastAccessedTime() {
		return _session.getLastAccessedTime();
	}

	public int getMaxInactiveInterval() {
		return _session.getMaxInactiveInterval();
	}

	public ServletContext getServletContext() {
		return _session.getServletContext();
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public javax.servlet.http.HttpSessionContext getSessionContext() {
		return _session.getSessionContext();
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public Object getValue(String name) {
		return _session.getValue(name);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public String[] getValueNames() {
		return _session.getValueNames();
	}

	public HttpSession getWrappedSession() {
		return _session;
	}

	public void invalidate() {
		_session.invalidate();
	}

	public boolean isNew() {
		return _session.isNew();
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public void putValue(String name, Object value) {
		_session.putValue(name, value);
	}

	public void removeAttribute(String name) {
		_session.removeAttribute(name);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	public void removeValue(String name) {
		_session.removeValue(name);
	}

	public void setAttribute(String name, Object value) {
		_session.setAttribute(name, value);
	}

	public void setMaxInactiveInterval(int interval) {
		_session.setMaxInactiveInterval(interval);
	}

	private HttpSession _session;

}