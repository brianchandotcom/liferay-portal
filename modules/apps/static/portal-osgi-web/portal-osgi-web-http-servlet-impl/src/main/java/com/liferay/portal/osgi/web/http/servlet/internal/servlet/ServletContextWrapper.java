/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.servlet;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.SessionCookieConfig;
import jakarta.servlet.SessionTrackingMode;
import jakarta.servlet.descriptor.JspConfigDescriptor;

import java.io.InputStream;

import java.net.URL;

import java.security.AccessControlContext;

import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;

import org.eclipse.equinox.http.servlet.internal.context.ContextController;
import org.eclipse.equinox.http.servlet.internal.context.ServletContextHelperDataContext;
import org.eclipse.equinox.http.servlet.internal.servlet.ServletContextAdaptor;

import org.osgi.framework.Bundle;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Dante Wang
 */
public class ServletContextWrapper implements ServletContext {

	public ServletContextWrapper(
		Bundle bundle, ContextController contextController,
		ServletContextHelper servletContextHelper,
		ServletContextHelperDataContext servletContextHelperDataContext,
		AccessControlContext accessControlContext) {

		_servletContextAdaptor = new ServletContextAdaptor(
			contextController, bundle, servletContextHelper,
			servletContextHelperDataContext,
			contextController.getEventListeners(), accessControlContext);

		_servletContext = servletContextHelperDataContext.getServletContext();
	}

	@Override
	public FilterRegistration.Dynamic addFilter(
		String filterName, Class<? extends Filter> filterClass) {

		_servletContextAdaptor.addFilter(filterName, filterClass);

		return null;
	}

	@Override
	public FilterRegistration.Dynamic addFilter(
		String filterName, Filter filter) {

		_servletContextAdaptor.addFilter(filterName, filter);

		return null;
	}

	@Override
	public FilterRegistration.Dynamic addFilter(
		String filterName, String className) {

		_servletContextAdaptor.addFilter(filterName, className);

		return null;
	}

	@Override
	public ServletRegistration.Dynamic addJspFile(
		String servletName, String jspFile) {

		return null;
	}

	@Override
	public void addListener(Class<? extends EventListener> listenerClass) {
		_servletContextAdaptor.addListener(listenerClass);
	}

	@Override
	public void addListener(String className) {
		_servletContextAdaptor.addListener(className);
	}

	@Override
	public <T extends EventListener> void addListener(T t) {
		_servletContextAdaptor.addListener(t);
	}

	@Override
	public ServletRegistration.Dynamic addServlet(
		String servletName, Class<? extends Servlet> servletClass) {

		_servletContextAdaptor.addServlet(servletName, servletClass);

		return null;
	}

	@Override
	public ServletRegistration.Dynamic addServlet(
		String servletName, Servlet servlet) {

		_servletContextAdaptor.addServlet(servletName, servlet);

		return null;
	}

	@Override
	public ServletRegistration.Dynamic addServlet(
		String servletName, String className) {

		_servletContextAdaptor.addServlet(servletName, className);

		return null;
	}

	@Override
	public <T extends Filter> T createFilter(Class<T> filterClass) {
		_servletContextAdaptor.createFilter(filterClass);

		return null;
	}

	@Override
	public <T extends EventListener> T createListener(Class<T> listenerClass) {
		_servletContextAdaptor.createListener(listenerClass);

		return null;
	}

	@Override
	public <T extends Servlet> T createServlet(Class<T> servletClass) {
		_servletContextAdaptor.createServlet(servletClass);

		return null;
	}

	@Override
	public void declareRoles(String... roleNames) {
		_servletContextAdaptor.declareRoles(roleNames);
	}

	@Override
	public Object getAttribute(String name) {
		return _servletContextAdaptor.getAttribute(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return _servletContextAdaptor.getAttributeNames();
	}

	@Override
	public ClassLoader getClassLoader() {
		return _servletContextAdaptor.getClassLoader();
	}

	@Override
	public ServletContext getContext(String path) {
		return _servletContext.getContext(path);
	}

	@Override
	public String getContextPath() {
		return _servletContextAdaptor.getContextPath();
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
		return _servletContextAdaptor.getInitParameter(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return _servletContextAdaptor.getInitParameterNames();
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
		return _servletContextAdaptor.getMimeType(file);
	}

	@Override
	public int getMinorVersion() {
		return _servletContext.getMinorVersion();
	}

	@Override
	public RequestDispatcher getNamedDispatcher(String servletName) {
		return _servletContextAdaptor.getNamedDispatcher(servletName);
	}

	@Override
	public String getRealPath(String path) {
		return _servletContextAdaptor.getRealPath(path);
	}

	@Override
	public String getRequestCharacterEncoding() {
		return _servletContext.getRequestCharacterEncoding();
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		return _servletContextAdaptor.getRequestDispatcher(path);
	}

	@Override
	public URL getResource(String path) {
		return _servletContextAdaptor.getResource(path);
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		return _servletContextAdaptor.getResourceAsStream(path);
	}

	@Override
	public Set<String> getResourcePaths(String path) {
		return _servletContextAdaptor.getResourcePaths(path);
	}

	@Override
	public String getResponseCharacterEncoding() {
		return _servletContext.getResponseCharacterEncoding();
	}

	@Override
	public String getServerInfo() {
		return _servletContext.getServerInfo();
	}

	@Override
	public String getServletContextName() {
		return _servletContextAdaptor.getServletContextName();
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

	@Override
	public SessionCookieConfig getSessionCookieConfig() {
		return _servletContext.getSessionCookieConfig();
	}

	@Override
	public int getSessionTimeout() {
		return _servletContext.getSessionTimeout();
	}

	@Override
	public String getVirtualServerName() {
		return _servletContext.getVirtualServerName();
	}

	@Override
	public void log(String message) {
		_servletContext.log(message);
	}

	@Override
	public void log(String message, Throwable throwable) {
		_servletContext.log(message, throwable);
	}

	@Override
	public void removeAttribute(String name) {
		_servletContextAdaptor.removeAttribute(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		_servletContextAdaptor.setAttribute(name, value);
	}

	@Override
	public boolean setInitParameter(String name, String value) {
		return _servletContext.setInitParameter(name, value);
	}

	@Override
	public void setRequestCharacterEncoding(String encoding) {
		_servletContext.setRequestCharacterEncoding(encoding);
	}

	@Override
	public void setResponseCharacterEncoding(String encoding) {
		_servletContext.setResponseCharacterEncoding(encoding);
	}

	@Override
	public void setSessionTimeout(int sessionTimeout) {
		_servletContext.setSessionTimeout(sessionTimeout);
	}

	@Override
	public void setSessionTrackingModes(
		Set<SessionTrackingMode> sessionTrackingModes) {

		_servletContext.setSessionTrackingModes(sessionTrackingModes);
	}

	private final ServletContext _servletContext;
	private final ServletContextAdaptor _servletContextAdaptor;

}