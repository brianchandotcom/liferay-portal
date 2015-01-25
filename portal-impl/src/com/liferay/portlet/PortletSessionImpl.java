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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.LiferayPortletSession;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

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
		HttpSession session, PortletContext portletContext, String portletName,
		long plid) {

		this.session = session;
		this.portletContext = portletContext;

		StringBundler sb = new StringBundler(5);

		sb.append(PORTLET_SCOPE_NAMESPACE);
		sb.append(portletName);
		sb.append(LAYOUT_SEPARATOR);
		sb.append(plid);
		sb.append(StringPool.QUESTION);

		scopePrefix = sb.toString();
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

		if (scope == PORTLET_SCOPE) {
			name = scopePrefix.concat(name);
		}

		return session.getAttribute(name);
	}

	@Override
	public Map<String, Object> getAttributeMap() {
		return getAttributeMap(PortletSession.PORTLET_SCOPE);
	}

	@Override
	public Map<String, Object> getAttributeMap(int scope) {
		if (scope == PORTLET_SCOPE) {
			return new PortletSessionAttributeMap(session, scopePrefix);
		}

		return new PortletSessionAttributeMap(session);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return getAttributeNames(PORTLET_SCOPE);
	}

	@Override
	public Enumeration<String> getAttributeNames(int scope) {
		if (scope != PORTLET_SCOPE) {
			return session.getAttributeNames();
		}

		List<String> attributeNames = new ArrayList<>();

		Enumeration<String> enu = session.getAttributeNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.startsWith(scopePrefix)) {
				name = name.substring(scopePrefix.length());

				attributeNames.add(name);
			}
		}

		return Collections.enumeration(attributeNames);
	}

	@Override
	public long getCreationTime() {
		return session.getCreationTime();
	}

	public HttpSession getHttpSession() {
		return session;
	}

	@Override
	public String getId() {
		return session.getId();
	}

	@Override
	public long getLastAccessedTime() {
		return session.getLastAccessedTime();
	}

	@Override
	public int getMaxInactiveInterval() {
		return session.getMaxInactiveInterval();
	}

	@Override
	public PortletContext getPortletContext() {
		return portletContext;
	}

	@Override
	public void invalidate() {
		session.invalidate();
	}

	@Override
	public boolean isNew() {
		return session.isNew();
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

		session.removeAttribute(name);
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

		session.setAttribute(name, value);
	}

	@Override
	public void setHttpSession(HttpSession session) {
		this.session = session;
	}

	@Override
	public void setMaxInactiveInterval(int interval) {
		session.setMaxInactiveInterval(interval);
	}

	protected final PortletContext portletContext;
	protected final String scopePrefix;
	protected HttpSession session;

}