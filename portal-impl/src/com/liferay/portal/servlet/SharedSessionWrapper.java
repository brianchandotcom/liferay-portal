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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.servlet.NullSession;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 */
public class SharedSessionWrapper implements HttpSession {

	public SharedSessionWrapper(
		HttpSession portalSession, HttpSession portletSession) {

		if (portalSession == null) {
			_portalSession = new NullSession();

			if (_log.isWarnEnabled()) {
				_log.warn("Wrapped portal session is null");
			}
		}
		else {
			_portalSession = portalSession;
		}

		_portletSession = portletSession;
	}

	@Override
	public Object getAttribute(String name) {
		HttpSession session = getSessionDelegate(name);

		return session.getAttribute(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		HttpSession session = getSessionDelegate();

		Enumeration<String> namesEnu = session.getAttributeNames();

		if (session == _portletSession) {
			List<String> namesList = Collections.list(namesEnu);

			Enumeration<String> portalSessionNamesEnu =
				_portalSession.getAttributeNames();

			while (portalSessionNamesEnu.hasMoreElements()) {
				String name = portalSessionNamesEnu.nextElement();

				if (containsSharedAttribute(name)) {
					namesList.add(name);
				}
			}

			namesEnu = Collections.enumeration(namesList);
		}

		return namesEnu;
	}

	@Override
	public long getCreationTime() {
		HttpSession session = getSessionDelegate();

		return session.getCreationTime();
	}

	@Override
	public String getId() {
		HttpSession session = getSessionDelegate();

		return session.getId();
	}

	@Override
	public long getLastAccessedTime() {
		HttpSession session = getSessionDelegate();

		return session.getLastAccessedTime();
	}

	@Override
	public int getMaxInactiveInterval() {
		HttpSession session = getSessionDelegate();

		return session.getMaxInactiveInterval();
	}

	@Override
	public ServletContext getServletContext() {
		HttpSession session = getSessionDelegate();

		return session.getServletContext();
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	@Deprecated
	@Override
	public javax.servlet.http.HttpSessionContext getSessionContext() {
		HttpSession session = getSessionDelegate();

		return session.getSessionContext();
	}

	@Override
	public Object getValue(String name) {
		return getAttribute(name);
	}

	@Override
	public String[] getValueNames() {
		List<String> names = ListUtil.fromEnumeration(getAttributeNames());

		return names.toArray(new String[names.size()]);
	}

	@Override
	public void invalidate() {
		HttpSession session = getSessionDelegate();

		session.invalidate();
	}

	@Override
	public boolean isNew() {
		HttpSession session = getSessionDelegate();

		return session.isNew();
	}

	@Override
	public void putValue(String name, Object value) {
		setAttribute(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		HttpSession session = getSessionDelegate(name);

		session.removeAttribute(name);
	}

	@Override
	public void removeValue(String name) {
		removeAttribute(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		HttpSession session = getSessionDelegate(name);

		session.setAttribute(name, value);
	}

	@Override
	public void setMaxInactiveInterval(int maxInactiveInterval) {
		HttpSession session = getSessionDelegate();

		session.setMaxInactiveInterval(maxInactiveInterval);
	}

	protected boolean containsSharedAttribute(String name) {
		for (String sharedName : PropsValues.SESSION_SHARED_ATTRIBUTES) {
			if (name.startsWith(sharedName)) {
				return true;
			}
		}

		return false;
	}

	protected HttpSession getSessionDelegate() {
		if (_portletSession != null) {
			return _portletSession;
		}
		else {
			return _portalSession;
		}
	}

	protected HttpSession getSessionDelegate(String name) {
		if (_portletSession == null) {
			return _portalSession;
		}

		if (_sharedSessionAttributesExcludes.containsKey(name)) {
			return _portletSession;
		}
		else if (containsSharedAttribute(name)) {
			return _portalSession;
		}
		else {
			return _portletSession;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SharedSessionWrapper.class);

	private static final Map<String, String> _sharedSessionAttributesExcludes;

	static {
		_sharedSessionAttributesExcludes = new HashMap<String, String>();

		for (String name : PropsValues.SESSION_SHARED_ATTRIBUTES_EXCLUDES) {
			_sharedSessionAttributesExcludes.put(name, name);
		}
	}

	private final HttpSession _portalSession;
	private HttpSession _portletSession;

}