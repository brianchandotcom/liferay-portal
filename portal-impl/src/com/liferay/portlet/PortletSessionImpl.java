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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class PortletSessionImpl implements LiferayPortletSession {

	public static String getPortletScope(String portletName, long plid) {
		StringBundler sb = new StringBundler(4);

		sb.append(PORTLET_SCOPE_NAMESPACE);
		sb.append(portletName);
		sb.append(LAYOUT_SEPARATOR);
		sb.append(plid);

		return sb.toString();
	}

	public static String getPortletScopeName(
		String portletName, long plid, String name) {

		StringBundler sb = new StringBundler(6);

		sb.append(PORTLET_SCOPE_NAMESPACE);
		sb.append(portletName);
		sb.append(LAYOUT_SEPARATOR);
		sb.append(plid);
		sb.append(StringPool.QUESTION);
		sb.append(name);

		return sb.toString();
	}

	public PortletSessionImpl(
		HttpServletRequest request, String portletName,
		PortletContext portletContext, String portalSessionId, long plid) {

		_session = request.getSession();
		_portletName = portletName;
		_portletContext = portletContext;
		_creationTime = System.currentTimeMillis();
		_lastAccessedTime = _creationTime;
		_interval = _session.getMaxInactiveInterval();
		_new = true;
		_invalid = false;
		_portalSessionId = portalSessionId;
		_plid = plid;
	}

	public Object getAttribute(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (_invalid) {
			throw new IllegalStateException();
		}

		return _session.getAttribute(_getPortletScopeName(name));
	}

	public Object getAttribute(String name, int scope) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (_invalid) {
			throw new IllegalStateException();
		}

		if (scope == PortletSession.PORTLET_SCOPE) {
			name = _getPortletScopeName(name);
		}

		return _session.getAttribute(name);
	}

	public Map<String, Object> getAttributeMap() {
		return getAttributeMap(PortletSession.PORTLET_SCOPE);
	}

	public Map<String, Object> getAttributeMap(int scope) {
		Map<String, Object> map = new HashMap<String, Object>();

		Enumeration<String> enu = _getAttributeNames(scope, false);

		String portletScope = getPortletScope(_portletName, _plid);

		int portletScopeLength = portletScope.length();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			Object value = _session.getAttribute(name);

			map.put(name.substring(portletScopeLength + 1), value);
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
		if (_invalid) {
			throw new IllegalStateException();
		}

		return _creationTime;
	}

	public HttpSession getHttpSession() {
		return _session;
	}

	public String getId() {
		return _session.getId();
	}

	public long getLastAccessedTime() {
		return _lastAccessedTime;
	}

	public int getMaxInactiveInterval() {
		return _interval;
	}

	public String getPortalSessionId() {
		return _portalSessionId;
	}

	public PortletContext getPortletContext() {
		return _portletContext;
	}

	public void invalidate() {
		if (_invalid) {
			throw new IllegalStateException();
		}

		_session.invalidate();

		_invalid = true;
	}

	public boolean isNew() {
		if (_invalid) {
			throw new IllegalStateException();
		}

		return _new;
	}

	public boolean isValid() {
		return !_invalid;
	}

	public void removeAttribute(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (_invalid) {
			throw new IllegalStateException();
		}

		_session.removeAttribute(_getPortletScopeName(name));
	}

	public void removeAttribute(String name, int scope) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (_invalid) {
			throw new IllegalStateException();
		}

		if (scope == PortletSession.PORTLET_SCOPE) {
			name = _getPortletScopeName(name);
		}

		_session.removeAttribute(name);
	}

	public void setAttribute(String name, Object value) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (_invalid) {
			throw new IllegalStateException();
		}

		_session.setAttribute(_getPortletScopeName(name), value);
	}

	public void setAttribute(String name, Object value, int scope) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		if (_invalid) {
			throw new IllegalStateException();
		}

		if (scope == PortletSession.PORTLET_SCOPE) {
			name = _getPortletScopeName(name);
		}

		_session.setAttribute(name, value);
	}

	public void setHttpSession(HttpSession session) {
		_session = session;
	}

	public void setLastAccessedTime(long lastAccessedTime) {
		_lastAccessedTime = lastAccessedTime;
		_new = false;
	}

	public void setMaxInactiveInterval(int interval) {
		_interval = interval;
	}

	private Enumeration<String> _getAttributeNames(
		int scope, boolean removePrefix) {

		if (_invalid) {
			throw new IllegalStateException();
		}

		if (scope == PortletSession.PORTLET_SCOPE) {
			List<String> attributeNames = new ArrayList<String>();

			String portletScope = getPortletScope(_portletName, _plid);

			int portletScopeLength = portletScope.length();

			Enumeration<String> enu = _session.getAttributeNames();

			while (enu.hasMoreElements()) {
				String name = enu.nextElement();

				if ((name.length() > (portletScopeLength + 1)) &&
					(name.charAt(portletScopeLength) == CharPool.QUESTION) &&
					name.startsWith(portletScope)) {

					if (removePrefix) {
						name = name.substring(portletScopeLength + 1);
					}

					attributeNames.add(name);
				}
			}

			return Collections.enumeration(attributeNames);
		}
		else {
			return _session.getAttributeNames();
		}
	}

	private String _getPortletScopeName(String name) {
		return getPortletScopeName(_portletName, _plid, name);
	}

	private HttpSession _session;
	private String _portletName;
	private PortletContext _portletContext;
	private long _creationTime;
	private long _lastAccessedTime;
	private int _interval;
	private boolean _new;
	private boolean _invalid;
	private String _portalSessionId;
	private long _plid;

}