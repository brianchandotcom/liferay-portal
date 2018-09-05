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

package com.liferay.portal.apache.bridges.struts;

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
	}

	@Override
	public FilterRegistration.Dynamic addFilter(
		String filterName, Class<? extends Filter> clazz) {

		return _servletContext.addFilter(filterName, clazz);
	}

	@Override
	public FilterRegistration.Dynamic addFilter(
		String filterName, Filter filter) {

		return _servletContext.addFilter(filterName, filter);
	}

	@Override
	public FilterRegistration.Dynamic addFilter(
		String filterName, String className) {

		return _servletContext.addFilter(filterName, className);
	}

	@Override
	public void addListener(Class<? extends EventListener> clazz) {
		_servletContext.addListener(clazz);
	}

	@Override
	public void addListener(String className) {
		_servletContext.addListener(className);
	}

	@Override
	public <T extends EventListener> void addListener(T eventListener) {
		_servletContext.addListener(eventListener);
	}

	@Override
	public ServletRegistration.Dynamic addServlet(
		String servletName, Class<? extends Servlet> clazz) {

		return _servletContext.addServlet(servletName, clazz);
	}

	@Override
	public ServletRegistration.Dynamic addServlet(
		String servletName, Servlet servlet) {

		return _servletContext.addServlet(servletName, servlet);
	}

	@Override
	public ServletRegistration.Dynamic addServlet(
		String servletName, String className) {

		return _servletContext.addServlet(servletName, className);
	}

	@Override
	public <T extends Filter> T createFilter(Class<T> clazz)
		throws ServletException {

		return _servletContext.createFilter(clazz);
	}

	@Override
	public <T extends EventListener> T createListener(Class<T> clazz)
		throws ServletException {

		return _servletContext.createListener(clazz);
	}

	@Override
	public <T extends Servlet> T createServlet(Class<T> clazz)
		throws ServletException {

		return _servletContext.createServlet(clazz);
	}

	@Override
	public void declareRoles(String... roleNames) {
		_servletContext.declareRoles(roleNames);
	}

	@Override
	public Object getAttribute(String name) {
		return _servletContext.getAttribute(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return _servletContext.getAttributeNames();
	}

	@Override
	public ClassLoader getClassLoader() {
		return _servletContext.getClassLoader();
	}

	@Override
	public ServletContext getContext(String uriPath) {
		ServletContext servletContext = _servletContext.getContext(uriPath);

		if (servletContext == _servletContext) {
			return this;
		}

		return servletContext;
	}

	@Override
	public String getContextPath() {
		return _servletContext.getContextPath();
	}

	@Override
	public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
		return _servletContext.getDefaultSessionTrackingModes();
	}

	@Override
	public int getEffectiveMajorVersion() {
		return _servletContext.getEffectiveMajorVersion();
	}

	@Override
	public int getEffectiveMinorVersion() {
		return _servletContext.getEffectiveMinorVersion();
	}

	@Override
	public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
		return _servletContext.getEffectiveSessionTrackingModes();
	}

	@Override
	public FilterRegistration getFilterRegistration(String filterName) {
		return _servletContext.getFilterRegistration(filterName);
	}

	@Override
	public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
		return _servletContext.getFilterRegistrations();
	}

	@Override
	public String getInitParameter(String name) {
		return _servletContext.getInitParameter(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return _servletContext.getInitParameterNames();
	}

	@Override
	public JspConfigDescriptor getJspConfigDescriptor() {
		return _servletContext.getJspConfigDescriptor();
	}

	@Override
	public int getMajorVersion() {
		return _servletContext.getMajorVersion();
	}

	@Override
	public String getMimeType(String file) {
		return _servletContext.getMimeType(file);
	}

	@Override
	public int getMinorVersion() {
		return _servletContext.getMinorVersion();
	}

	@Override
	public RequestDispatcher getNamedDispatcher(String name) {
		RequestDispatcher requestDispatcher =
			_servletContext.getNamedDispatcher(name);

		if (requestDispatcher != null) {
			requestDispatcher = new LiferayRequestDispatcher(
				requestDispatcher, name);
		}

		return requestDispatcher;
	}

	@Override
	public String getRealPath(String path) {
		return _servletContext.getRealPath(path);
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(path);

		if (requestDispatcher != null) {
			requestDispatcher = new LiferayRequestDispatcher(
				requestDispatcher, path);
		}

		return requestDispatcher;
	}

	@Override
	public URL getResource(String path) throws MalformedURLException {
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
		return _servletContext.getServerInfo();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public Servlet getServlet(String name) {
		return null;
	}

	@Override
	public String getServletContextName() {
		return _servletContext.getServletContextName();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public Enumeration<String> getServletNames() {
		return Collections.enumeration(new ArrayList<String>());
	}

	@Override
	public ServletRegistration getServletRegistration(String servletName) {
		return _servletContext.getServletRegistration(servletName);
	}

	@Override
	public Map<String, ? extends ServletRegistration>
		getServletRegistrations() {

		return _servletContext.getServletRegistrations();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public Enumeration<Servlet> getServlets() {
		return Collections.enumeration(new ArrayList<Servlet>());
	}

	@Override
	public SessionCookieConfig getSessionCookieConfig() {
		return _servletContext.getSessionCookieConfig();
	}

	@Override
	public String getVirtualServerName() {
		return _servletContext.getVirtualServerName();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public void log(Exception exception, String message) {
		_servletContext.log(message, exception);
	}

	@Override
	public void log(String message) {
		_servletContext.log(message);
	}

	@Override
	public void log(String message, Throwable t) {
		_servletContext.log(message, t);
	}

	@Override
	public void removeAttribute(String name) {
		_servletContext.removeAttribute(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		_servletContext.setAttribute(name, value);
	}

	@Override
	public boolean setInitParameter(String name, String value) {
		return _servletContext.setInitParameter(name, value);
	}

	@Override
	public void setSessionTrackingModes(
		Set<SessionTrackingMode> sessionTrackingModes) {

		_servletContext.setSessionTrackingModes(sessionTrackingModes);
	}

	@Override
	public String toString() {
		return _servletContext.toString();
	}

	private final ServletContext _servletContext;

}