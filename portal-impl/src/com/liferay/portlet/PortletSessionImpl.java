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
import java.util.HashMap;
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

		_session = session;
		_portletContext = portletContext;
		_portletScope = _getPortletScope(portletName, plid);
	}

	@Override
	public Object getAttribute(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		String scopeName = _getPortletScopeName(name);

		return _session.getAttribute(scopeName);
	}

	@Override
	public Object getAttribute(String name, int scope) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (scope == PortletSession.PORTLET_SCOPE) {
			name = _getPortletScopeName(name);
		}

		return _session.getAttribute(name);
	}

	@Override
	public Map<String, Object> getAttributeMap() {
		return getAttributeMap(PortletSession.PORTLET_SCOPE);
	}

	@Override
	public Map<String, Object> getAttributeMap(int scope) {
		Map<String, Object> map = new HashMap<String, Object>();

		Enumeration<String> enu = _getAttributeNames(scope, false);

		int portletScopeLength = _portletScope.length();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			Object value = _session.getAttribute(name);

			if (scope == PortletSession.PORTLET_SCOPE) {
				if ((name.length() <= (portletScopeLength + 1)) ||
					!name.startsWith(_portletScope + StringPool.QUESTION)) {

					continue;
				}

				name = name.substring(portletScopeLength + 1);
			}

			map.put(name, value);
		}

		return map;
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return _getAttributeNames(PortletSession.PORTLET_SCOPE, true);
	}

	@Override
	public Enumeration<String> getAttributeNames(int scope) {
		return _getAttributeNames(scope, true);
	}

	@Override
	public long getCreationTime() {
		return _session.getCreationTime();
	}

	public HttpSession getHttpSession() {
		return _session;
	}

	@Override
	public String getId() {
		return _session.getId();
	}

	@Override
	public long getLastAccessedTime() {
		return _session.getLastAccessedTime();
	}

	@Override
	public int getMaxInactiveInterval() {
		return _session.getMaxInactiveInterval();
	}

	@Override
	public PortletContext getPortletContext() {
		return _portletContext;
	}

	@Override
	public void invalidate() {
		_session.invalidate();
	}

	@Override
	public boolean isNew() {
		return _session.isNew();
	}

	@Override
	public void removeAttribute(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		String scopeName = _getPortletScopeName(name);

		_session.removeAttribute(scopeName);
	}

	@Override
	public void removeAttribute(String name, int scope) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (scope == PortletSession.PORTLET_SCOPE) {
			name = _getPortletScopeName(name);
		}

		_session.removeAttribute(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		String scopeName = _getPortletScopeName(name);

		_session.setAttribute(scopeName, value);
	}

	@Override
	public void setAttribute(String name, Object value, int scope) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (scope == PortletSession.PORTLET_SCOPE) {
			name = _getPortletScopeName(name);
		}

		_session.setAttribute(name, value);
	}

	@Override
	public void setHttpSession(HttpSession session) {
		_session = session;
	}

	@Override
	public void setMaxInactiveInterval(int interval) {
		_session.setMaxInactiveInterval(interval);
	}

	private Enumeration<String> _getAttributeNames(
		int scope, boolean removePrefix) {

		if (scope != PortletSession.PORTLET_SCOPE) {
			return _session.getAttributeNames();
		}

		List<String> attributeNames = new ArrayList<String>();

		int portletScopeLength = _portletScope.length();

		Enumeration<String> enu = _session.getAttributeNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (removePrefix) {
				if ((name.length() <= (portletScopeLength + 1)) ||
					!name.startsWith(_portletScope + StringPool.QUESTION)) {

					continue;
				}

				name = name.substring(portletScopeLength + 1);
			}

			attributeNames.add(name);
		}

		return Collections.enumeration(attributeNames);
	}

	private String _getPortletScope(String portletName, long plid) {
		StringBundler sb = new StringBundler(4);

		sb.append(PORTLET_SCOPE_NAMESPACE);
		sb.append(portletName);
		sb.append(LAYOUT_SEPARATOR);
		sb.append(plid);

		return sb.toString();
	}

	private String _getPortletScopeName(String name) {
		return _portletScope.concat(StringPool.QUESTION).concat(name);
	}

	private final PortletContext _portletContext;
	private final String _portletScope;
	private HttpSession _session;

}