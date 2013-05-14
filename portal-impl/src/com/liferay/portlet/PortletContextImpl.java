/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.security.lang.DoPrivilegedUtil;

import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Enumeration;
import java.util.Set;

import javax.portlet.PortletRequestDispatcher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Brett Randall
 */
public class PortletContextImpl implements LiferayPortletContext {

	public PortletContextImpl(Portlet portlet, ServletContext servletContext) {
		_portlet = portlet;
		_servletContext = servletContext;
		_servletContextName = GetterUtil.getString(
			_servletContext.getServletContextName());
	}

	@Override
	public Object getAttribute(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		return _servletContext.getAttribute(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return _servletContext.getAttributeNames();
	}

	@Override
	public Enumeration<String> getContainerRuntimeOptions() {
		return null;
	}

	@Override
	public String getInitParameter(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		return _servletContext.getInitParameter(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return _servletContext.getInitParameterNames();
	}

	@Override
	public int getMajorVersion() {
		return _MAJOR_VERSION;
	}

	@Override
	public String getMimeType(String file) {
		return _servletContext.getMimeType(file);
	}

	@Override
	public int getMinorVersion() {
		return _MINOR_VERSION;
	}

	@Override
	public PortletRequestDispatcher getNamedDispatcher(String name) {
		RequestDispatcher requestDispatcher = null;

		try {
			requestDispatcher = _servletContext.getNamedDispatcher(name);
		}
		catch (IllegalArgumentException iae) {
			return null;
		}

		if (requestDispatcher != null) {
			return DoPrivilegedUtil.wrap(
				new PortletRequestDispatcherImpl(
					requestDispatcher, true, this), true);
		}
		else {
			return null;
		}
	}

	@Override
	public Portlet getPortlet() {
		return _portlet;
	}

	@Override
	public String getPortletContextName() {
		return _servletContextName;
	}

	@Override
	public String getRealPath(String path) {
		return _servletContext.getRealPath(path);
	}

	@Override
	public PortletRequestDispatcher getRequestDispatcher(String path) {
		RequestDispatcher requestDispatcher = null;

		try {
			requestDispatcher = _servletContext.getRequestDispatcher(path);
		}
		catch (IllegalArgumentException iae) {
			return null;
		}

		if (requestDispatcher != null) {
			return DoPrivilegedUtil.wrap(
				new PortletRequestDispatcherImpl(
					requestDispatcher, false, this, path), true);
		}
		else {
			return null;
		}
	}

	@Override
	public URL getResource(String path) throws MalformedURLException {
		if ((path == null) || !path.startsWith(StringPool.SLASH)) {
			throw new MalformedURLException();
		}

		return _servletContext.getResource(path);
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		return _servletContext.getResourceAsStream(path);
	}

	@Override
	public Set<String> getResourcePaths(String path) {
		return _servletContext.getResourcePaths(path);
	}

	@Override
	public String getServerInfo() {
		return ReleaseInfo.getServerInfo();
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	public boolean isWARFile() {
		PortletApp portletApp = _portlet.getPortletApp();

		return portletApp.isWARFile();
	}

	@Override
	public void log(String msg) {
		if (_log.isInfoEnabled()) {
			_log.info(msg);
		}
	}

	@Override
	public void log(String msg, Throwable throwable) {
		if (_log.isInfoEnabled()) {
			_log.info(msg, throwable);
		}
	}

	@Override
	public void removeAttribute(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		_servletContext.removeAttribute(name);
	}

	@Override
	public void setAttribute(String name, Object obj) {
		if (name == null) {
			throw new IllegalArgumentException();
		}

		_servletContext.setAttribute(name, obj);
	}

	private static final int _MAJOR_VERSION = 2;

	private static final int _MINOR_VERSION = 0;

	private static Log _log = LogFactoryUtil.getLog(PortletContextImpl.class);

	private Portlet _portlet;
	private ServletContext _servletContext;
	private String _servletContextName;

}