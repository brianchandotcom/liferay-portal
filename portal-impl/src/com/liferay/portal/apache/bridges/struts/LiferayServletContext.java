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

package com.liferay.portal.apache.bridges.struts;

import com.liferay.portal.kernel.util.ContextPathUtil;

import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;

/**
 * @author Michael Young
 */
public class LiferayServletContext implements ServletContext {

	public LiferayServletContext(ServletContext servletContext) {
		_servletContext = servletContext;

		if (_servletContext.getMajorVersion() >= 3) {
			_isVersion3 = true;
		}
	}

	public Dynamic addFilter(
		String filterName, Class<? extends Filter> filterClass) {

		if (!_isVersion3) {
			return null;
		}

		return _servletContext.addFilter(filterName, filterClass);
	}

	public Dynamic addFilter(String filterName, Filter filter) {
		if (!_isVersion3) {
			return null;
		}

		return _servletContext.addFilter(filterName, filter);
	}

	public Dynamic addFilter(String filterName, String className) {
		if (!_isVersion3) {
			return null;
		}

		return _servletContext.addFilter(filterName, className);
	}

	public void addListener(Class<? extends EventListener> listenerClass) {
		if (!_isVersion3) {
			return;
		}

		_servletContext.addListener(listenerClass);
	}

	public void addListener(String className) {
		if (!_isVersion3) {
			return;
		}

		_servletContext.addListener(className);
	}

	public <T extends EventListener> void addListener(T eventListener) {
		if (!_isVersion3) {
			return;
		}

		_servletContext.addListener(eventListener);
	}

	public javax.servlet.ServletRegistration.Dynamic addServlet(
		String servletName, java.lang.Class<? extends Servlet> servletClass) {

		if (!_isVersion3) {
			return null;
		}

		return _servletContext.addServlet(servletName, servletClass);
	}

	public javax.servlet.ServletRegistration.Dynamic addServlet(
		String servletName, Servlet servlet) {

		if (!_isVersion3) {
			return null;
		}

		return _servletContext.addServlet(servletName, servlet);
	}

	public javax.servlet.ServletRegistration.Dynamic addServlet(
		String servletName, String className) {

		if (!_isVersion3) {
			return null;
		}

		return _servletContext.addServlet(servletName, className);
	}

	public <T extends Filter> T createFilter(Class<T> filterClass)
		throws ServletException {

		if (!_isVersion3) {
			return null;
		}

		return _servletContext.createFilter(filterClass);
	}

	public <T extends EventListener> T createListener(
			Class<T> listenerClass)
		throws ServletException {

		if (!_isVersion3) {
			return null;
		}

		return _servletContext.createListener(listenerClass);
	}

	public <T extends Servlet> T createServlet(Class<T> servletClass)
		throws ServletException {

		if (!_isVersion3) {
			return null;
		}

		return _servletContext.createServlet(servletClass);
	}

	public void declareRoles(String... roleNames) {
		if (!_isVersion3) {
			return;
		}

		_servletContext.declareRoles(roleNames);
	}

	public Object getAttribute(String name) {
		return _servletContext.getAttribute(name);
	}

	public Enumeration<String> getAttributeNames() {
		return _servletContext.getAttributeNames();
	}

	public ClassLoader getClassLoader() {
		if (!_isVersion3) {
			return null;
		}

		return _servletContext.getClassLoader();
	}

	public ServletContext getContext(String uriPath) {
		ServletContext servletContext = _servletContext.getContext(uriPath);

		if (servletContext == _servletContext) {
			return this;
		}
		else {
			return servletContext;
		}
	}

	public String getContextPath() {
		return ContextPathUtil.getContextPath(_servletContext);
	}

	public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
		if (!_isVersion3) {
			return null;
		}

		return _servletContext.getDefaultSessionTrackingModes();
	}

	public int getEffectiveMajorVersion() {
		if (!_isVersion3) {
			return 0;
		}

		return _servletContext.getEffectiveMajorVersion();
	}

	public int getEffectiveMinorVersion() {
		if (!_isVersion3) {
			return 0;
		}

		return _servletContext.getEffectiveMinorVersion();
	}

	public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
		if (!_isVersion3) {
			return null;
		}

		return _servletContext.getEffectiveSessionTrackingModes();
	}

	public FilterRegistration getFilterRegistration(String filterName) {
		if (!_isVersion3) {
			return null;
		}

		return _servletContext.getFilterRegistration(filterName);
	}

	public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
		if (!_isVersion3) {
			return null;
		}

		return _servletContext.getFilterRegistrations();
	}

	public String getInitParameter(String name) {
		return _servletContext.getInitParameter(name);
	}

	public Enumeration<String> getInitParameterNames() {
		return _servletContext.getInitParameterNames();
	}

	public JspConfigDescriptor getJspConfigDescriptor() {
		if (!_isVersion3) {
			return null;
		}

		return _servletContext.getJspConfigDescriptor();
	}

	public int getMajorVersion() {
		return _servletContext.getMajorVersion();
	}

	public String getMimeType(String file) {
		return _servletContext.getMimeType(file);
	}

	public int getMinorVersion() {
		return _servletContext.getMinorVersion();
	}

	public RequestDispatcher getNamedDispatcher(String name) {
		RequestDispatcher requestDispatcher =
			_servletContext.getNamedDispatcher(name);

		if (requestDispatcher != null) {
			requestDispatcher = new LiferayRequestDispatcher(
				requestDispatcher, name);
		}

		return requestDispatcher;
	}

	public String getRealPath(String path) {
		return _servletContext.getRealPath(path);
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(path);

		if (requestDispatcher != null) {
			requestDispatcher = new LiferayRequestDispatcher(
				requestDispatcher, path);
		}

		return requestDispatcher;
	}

	public URL getResource(String path) throws MalformedURLException {
		return _servletContext.getResource(path);
	}

	public InputStream getResourceAsStream(String path) {
		return _servletContext.getResourceAsStream(path);
	}

	public Set<String> getResourcePaths(String path) {
		return _servletContext.getResourcePaths(path);
	}

	public String getServerInfo() {
		return _servletContext.getServerInfo();
	}

	/**
	 * @deprecated try {@link #getServletRegistration(String)}
	 */
	public Servlet getServlet(String name) {
		return null;
	}

	public String getServletContextName() {
		return _servletContext.getServletContextName();
	}

	/**
	 * @deprecated try {@link #getServletRegistrations()}
	 */
	public Enumeration<String> getServletNames() {
		return Collections.enumeration(new ArrayList<String>());
	}

	public ServletRegistration getServletRegistration(String servletName) {
		if (!_isVersion3) {
			return null;
		}

		return _servletContext.getServletRegistration(servletName);
	}

	public Map<String, ? extends ServletRegistration> getServletRegistrations() {
		if (!_isVersion3) {
			return null;
		}

		return _servletContext.getServletRegistrations();
	}

	/**
	 * @deprecated try {@link #getServletRegistrations()}
	 */
	public Enumeration<Servlet> getServlets() {
		return Collections.enumeration(new ArrayList<Servlet>());
	}

	public SessionCookieConfig getSessionCookieConfig() {
		if (!_isVersion3) {
			return null;
		}

		return _servletContext.getSessionCookieConfig();
	}

	/**
	 * @deprecated {@link #log(String, Throwable)}
	 */
	public void log(Exception exception, String message) {
		_servletContext.log(message, exception);
	}

	public void log(String message) {
		_servletContext.log(message);
	}

	public void log(String message, Throwable throwable) {
		_servletContext.log(message, throwable);
	}

	public void removeAttribute(String name) {
		_servletContext.removeAttribute(name);
	}

	public void setAttribute(String name, Object value) {
		_servletContext.setAttribute(name, value);
	}

	public boolean setInitParameter(String name, String value) {
		if (!_isVersion3) {
			return false;
		}

		return _servletContext.setInitParameter(name, value);
	}

	public void setSessionTrackingModes(Set<SessionTrackingMode> modes) {
		if (!_isVersion3) {
			return;
		}

		_servletContext.setSessionTrackingModes(modes);
	}

	@Override
	public String toString() {
		return _servletContext.toString();
	}

	private boolean _isVersion3;
	private ServletContext _servletContext;

}