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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.LiferayPortletSession;
import com.liferay.portal.kernel.util.CharPool;
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
		HttpSession httpSession, PortletContext portletContext,
		String portletName, long plid) {

		_httpSession = httpSession;
		_portletContext = portletContext;
		_portletScope = _getPortletScope(portletName, plid);
	}

	public Object getAttribute(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		String scopeName = _getPortletScopeName(name);

		return _httpSession.getAttribute(scopeName);
	}

	public Object getAttribute(String name, int scope) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (scope == PortletSession.PORTLET_SCOPE) {
			name = _getPortletScopeName(name);
		}

		return _httpSession.getAttribute(name);
	}

	public Map<String, Object> getAttributeMap() {
		return getAttributeMap(PortletSession.PORTLET_SCOPE);
	}

	public Map<String, Object> getAttributeMap(int scope) {
		Map<String, Object> map = new HashMap<String, Object>();

		Enumeration<String> enu = _getAttributeNames(scope, false);

		int portletScopeLength = _portletScope.length();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			Object value = _httpSession.getAttribute(name);

			if (scope == PortletSession.PORTLET_SCOPE) {
				name = name.substring(portletScopeLength + 1);
			}

			map.put(name, value);
		}

		return map;
	}

	public Enumeration<String> getAttributeNames() {
		return _getAttributeNames(PortletSession.PORTLET_SCOPE, true);
	}

	public Enumeration<String> getAttributeNames(int scope) {
		return _getAttributeNames(scope, true);
	}

	public long getCreationTime() {
		return _httpSession.getCreationTime();
	}

	public HttpSession getHttpSession() {
		return _httpSession;
	}

	public String getId() {
		return _httpSession.getId();
	}

	public long getLastAccessedTime() {
		return _httpSession.getLastAccessedTime();
	}

	public int getMaxInactiveInterval() {
		return _httpSession.getMaxInactiveInterval();
	}

	public PortletContext getPortletContext() {
		return _portletContext;
	}

	public void invalidate() {
		_httpSession.invalidate();
	}

	public boolean isNew() {
		return _httpSession.isNew();
	}

	public void removeAttribute(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		String scopeName = _getPortletScopeName(name);

		_httpSession.removeAttribute(scopeName);
	}

	public void removeAttribute(String name, int scope) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (scope == PortletSession.PORTLET_SCOPE) {
			name = _getPortletScopeName(name);
		}

		_httpSession.removeAttribute(name);
	}

	public void setAttribute(String name, Object value) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		String scopeName = _getPortletScopeName(name);

		_httpSession.setAttribute(scopeName, value);
	}

	public void setAttribute(String name, Object value, int scope) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (scope == PortletSession.PORTLET_SCOPE) {
			name = _getPortletScopeName(name);
		}

		_httpSession.setAttribute(name, value);
	}

	public void setHttpSession(HttpSession session) {
		_httpSession = session;
	}

	public void setMaxInactiveInterval(int interval) {
		_httpSession.setMaxInactiveInterval(interval);
	}

	private Enumeration<String> _getAttributeNames(
		int scope, boolean removePrefix) {

		if (scope == PortletSession.PORTLET_SCOPE) {
			List<String> attributeNames = new ArrayList<String>();

			int portletScopeLength = _portletScope.length();

			Enumeration<String> enu = _httpSession.getAttributeNames();

			while (enu.hasMoreElements()) {
				String name = enu.nextElement();

				if ((name.length() > (portletScopeLength + 1)) &&
					(name.charAt(portletScopeLength) == CharPool.QUESTION) &&
					name.startsWith(_portletScope)) {

					if (removePrefix) {
						name = name.substring(portletScopeLength + 1);
					}

					attributeNames.add(name);
				}
			}

			return Collections.enumeration(attributeNames);
		}
		else {
			return _httpSession.getAttributeNames();
		}
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

	private HttpSession _httpSession;
	private PortletContext _portletContext;
	private String _portletScope;

}