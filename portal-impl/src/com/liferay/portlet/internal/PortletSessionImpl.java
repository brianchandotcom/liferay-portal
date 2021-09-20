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

package com.liferay.portlet.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.LiferayPortletSession;
import com.liferay.portlet.PortletSessionAttributeMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletContext;
import javax.portlet.PortletSession;

import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class PortletSessionImpl implements LiferayPortletSession {

	public PortletSessionImpl(
		HttpSession httpSession, PortletContext portletContext,
		String portletName, long plid) {

		this.httpSession = httpSession;
		this.portletContext = portletContext;

		scopePrefix = StringBundler.concat(
			PORTLET_SCOPE_NAMESPACE, portletName, LAYOUT_SEPARATOR, plid,
			StringPool.QUESTION);
	}

	@Override
	public Object getAttribute(String name) {
		return getAttribute(name, PORTLET_SCOPE);
	}

	@Override
	public Object getAttribute(String name, int scope) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (_invalidated) {
			throw new IllegalStateException();
		}

		if (scope == PORTLET_SCOPE) {
			name = scopePrefix.concat(name);
		}

		return httpSession.getAttribute(name);
	}

	@Override
	public Map<String, Object> getAttributeMap() {
		return getAttributeMap(PortletSession.PORTLET_SCOPE);
	}

	@Override
	public Map<String, Object> getAttributeMap(int scope) {
		if (scope == PORTLET_SCOPE) {
			return new PortletSessionAttributeMap(httpSession, scopePrefix);
		}

		return new PortletSessionAttributeMap(httpSession);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return getAttributeNames(PORTLET_SCOPE);
	}

	@Override
	public Enumeration<String> getAttributeNames(int scope) {
		if (scope != PORTLET_SCOPE) {
			return httpSession.getAttributeNames();
		}

		List<String> attributeNames = new ArrayList<>();

		Enumeration<String> enumeration = httpSession.getAttributeNames();

		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement();

			if (name.startsWith(scopePrefix)) {
				name = name.substring(scopePrefix.length());

				attributeNames.add(name);
			}
		}

		return Collections.enumeration(attributeNames);
	}

	@Override
	public long getCreationTime() {
		if (_invalidated) {
			throw new IllegalStateException();
		}

		return httpSession.getCreationTime();
	}

	public HttpSession getHttpSession() {
		return httpSession;
	}

	@Override
	public String getId() {
		return httpSession.getId();
	}

	@Override
	public long getLastAccessedTime() {
		return httpSession.getLastAccessedTime();
	}

	@Override
	public int getMaxInactiveInterval() {
		return httpSession.getMaxInactiveInterval();
	}

	@Override
	public PortletContext getPortletContext() {
		return portletContext;
	}

	@Override
	public void invalidate() {
		_invalidated = true;

		httpSession.invalidate();
	}

	public boolean isInvalidated() {
		return _invalidated;
	}

	@Override
	public boolean isNew() {
		return httpSession.isNew();
	}

	@Override
	public void removeAttribute(String name) {
		removeAttribute(name, PORTLET_SCOPE);
	}

	@Override
	public void removeAttribute(String name, int scope) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (scope == PORTLET_SCOPE) {
			name = scopePrefix.concat(name);
		}

		httpSession.removeAttribute(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		setAttribute(name, value, PORTLET_SCOPE);
	}

	@Override
	public void setAttribute(String name, Object value, int scope) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (scope == PORTLET_SCOPE) {
			name = scopePrefix.concat(name);
		}

		httpSession.setAttribute(name, value);
	}

	@Override
	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	@Override
	public void setMaxInactiveInterval(int interval) {
		httpSession.setMaxInactiveInterval(interval);
	}

	protected HttpSession httpSession;
	protected final PortletContext portletContext;
	protected final String scopePrefix;

	private boolean _invalidated;

}