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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public class ServletContextPool {

	public static void clear() {
		_instance._servletContexts.clear();
	}

	public static boolean containsKey(String servletContextName) {
		return _instance._containsKey(servletContextName);
	}

	public static ServletContext get(String servletContextName) {
		return _instance._get(servletContextName);
	}

	public static Set<String> keySet() {
		return _instance._keySet();
	}

	public static void put(
		String servletContextName, ServletContext servletContext) {

		_instance._put(servletContextName, servletContext);
	}

	public static ServletContext remove(String servletContextName) {
		return _instance._remove(servletContextName);
	}

	private ServletContextPool() {
		_servletContexts = new ConcurrentHashMap<String, ServletContext>();
	}

	private boolean _containsKey(String servletContextName) {
		if (servletContextName == null) {
			return false;
		}

		boolean value = _servletContexts.containsKey(servletContextName);

		if (_log.isDebugEnabled()) {
			_log.debug("Contains key " + servletContextName + " " + value);
		}

		return value;
	}

	private ServletContext _get(String servletContextName) {
		ServletContext servletContext = _servletContexts.get(
			servletContextName);

		if (_log.isDebugEnabled()) {
			_log.debug("Get " + servletContextName + " " + servletContext);
		}

		return servletContext;
	}

	private Set<String> _keySet() {
		return _servletContexts.keySet();
	}

	private void _put(
		String servletContextName, ServletContext servletContext) {

		if (_log.isDebugEnabled()) {
			_log.debug("Put " + servletContextName + " " + servletContext);
		}

		_servletContexts.put(servletContextName, servletContext);
	}

	private ServletContext _remove(String servletContextName) {

		// We should never remove the portal context. See LPS-12683.

		String contextPath = PortalUtil.getPathContext();

		if (contextPath.equals(servletContextName)) {
			return null;
		}

		ServletContext servletContext = _servletContexts.remove(
			servletContextName);

		if (_log.isDebugEnabled()) {
			_log.debug("Remove " + servletContextName + " " + servletContext);
		}

		return servletContext;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServletContextPool.class);

	private static final ServletContextPool _instance =
		new ServletContextPool();

	private final Map<String, ServletContext> _servletContexts;

}