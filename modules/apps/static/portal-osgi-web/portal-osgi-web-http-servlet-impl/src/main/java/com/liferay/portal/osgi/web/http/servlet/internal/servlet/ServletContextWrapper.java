/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.servlet;

import com.liferay.petra.string.StringPool;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextAttributeEvent;
import jakarta.servlet.ServletContextAttributeListener;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.SessionCookieConfig;
import jakarta.servlet.SessionTrackingMode;
import jakarta.servlet.descriptor.JspConfigDescriptor;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.equinox.http.servlet.internal.context.ContextController;
import org.eclipse.equinox.http.servlet.internal.context.DispatchTargets;
import org.eclipse.equinox.http.servlet.internal.context.ServletContextHelperDataContext;
import org.eclipse.equinox.http.servlet.internal.servlet.Match;
import org.eclipse.equinox.http.servlet.internal.servlet.RequestDispatcherAdaptor;
import org.eclipse.equinox.http.servlet.internal.util.EventListeners;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Dante Wang
 */
public class ServletContextWrapper implements ServletContext {

	public ServletContextWrapper(
		Bundle bundle, ContextController contextController,
		ServletContextHelper servletContextHelper,
		ServletContextHelperDataContext servletContextHelperDataContext) {

		_bundle = bundle;

		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		_classLoader = bundleWiring.getClassLoader();

		_contextController = contextController;
		_servletContextHelper = servletContextHelper;
		_servletContextHelperDataContext = servletContextHelperDataContext;

		_servletContext = servletContextHelperDataContext.getServletContext();
	}

	@Override
	public FilterRegistration.Dynamic addFilter(
		String filterName, Class<? extends Filter> filterClass) {

		throw new UnsupportedOperationException();
	}

	@Override
	public FilterRegistration.Dynamic addFilter(
		String filterName, Filter filter) {

		throw new UnsupportedOperationException();
	}

	@Override
	public FilterRegistration.Dynamic addFilter(
		String filterName, String className) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ServletRegistration.Dynamic addJspFile(
		String servletName, String jspFile) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void addListener(Class<? extends EventListener> listenerClass) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addListener(String className) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends EventListener> void addListener(T t) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ServletRegistration.Dynamic addServlet(
		String servletName, Class<? extends Servlet> servletClass) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ServletRegistration.Dynamic addServlet(
		String servletName, Servlet servlet) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ServletRegistration.Dynamic addServlet(
		String servletName, String className) {

		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends Filter> T createFilter(Class<T> filterClass) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends EventListener> T createListener(Class<T> listenerClass) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends Servlet> T createServlet(Class<T> servletClass) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void declareRoles(String... roleNames) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ServletContextWrapper)) {
			return false;
		}

		ServletContextWrapper otherServletContextWrapper =
			(ServletContextWrapper)other;

		return _contextController.equals(
			otherServletContextWrapper._contextController);
	}

	@Override
	public Object getAttribute(String name) {
		if (name.equals("osgi-bundlecontext")) {
			return _bundle.getBundleContext();
		}

		Dictionary<String, Object> attributes =
			_servletContextHelperDataContext.getContextAttributes();

		return attributes.get(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		Dictionary<String, Object> contextAttributes =
			_servletContextHelperDataContext.getContextAttributes();

		return contextAttributes.keys();
	}

	@Override
	public ClassLoader getClassLoader() {
		return _classLoader;
	}

	@Override
	public ServletContext getContext(String path) {
		return _servletContext.getContext(path);
	}

	@Override
	public String getContextPath() {
		return _contextController.getFullContextPath();
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
		Map<String, String> initParams = _contextController.getInitParams();

		return initParams.get(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		Map<String, String> initParams = _contextController.getInitParams();

		return Collections.enumeration(initParams.keySet());
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
		String mimeType = _servletContextHelper.getMimeType(file);

		if (mimeType != null) {
			return mimeType;
		}

		return _servletContext.getMimeType(file);
	}

	@Override
	public int getMinorVersion() {
		return _servletContext.getMinorVersion();
	}

	@Override
	public RequestDispatcher getNamedDispatcher(String servletName) {
		DispatchTargets dispatchTargets = _contextController.getDispatchTargets(
			servletName, null, null, null, null, null, Match.EXACT);

		if (dispatchTargets == null) {
			return null;
		}

		return new RequestDispatcherAdaptor(dispatchTargets, servletName);
	}

	@Override
	public String getRealPath(String path) {
		return _servletContextHelper.getRealPath(path);
	}

	@Override
	public String getRequestCharacterEncoding() {
		return _servletContext.getRequestCharacterEncoding();
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		if (!path.startsWith(StringPool.SLASH)) {
			return null;
		}

		String fullContextPath = _contextController.getFullContextPath();

		if (path.startsWith(fullContextPath)) {
			path = path.substring(fullContextPath.length());
		}

		DispatchTargets dispatchTargets = _contextController.getDispatchTargets(
			path);

		if (dispatchTargets == null) {
			return null;
		}

		return new RequestDispatcherAdaptor(dispatchTargets, path);
	}

	@Override
	public URL getResource(String path) {
		return _servletContextHelper.getResource(path);
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		URL url = getResource(path);

		if (url == null) {
			return null;
		}

		try {
			return url.openStream();
		}
		catch (IOException ioException) {
			_servletContext.log(ioException.getMessage(), ioException);
		}

		return null;
	}

	@Override
	public Set<String> getResourcePaths(String path) {
		if ((path == null) || !path.startsWith(StringPool.SLASH)) {
			return null;
		}

		return _servletContextHelper.getResourcePaths(path);
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
		return _contextController.getContextName();
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
	public int hashCode() {
		return _contextController.hashCode();
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
		Dictionary<String, Object> contextAttributes =
			_servletContextHelperDataContext.getContextAttributes();

		Object attributeValue = contextAttributes.remove(name);

		EventListeners eventListeners = _contextController.getEventListeners();

		List<ServletContextAttributeListener> servletContextAttributeListeners =
			eventListeners.get(ServletContextAttributeListener.class);

		if (servletContextAttributeListeners.isEmpty()) {
			return;
		}

		ServletContextAttributeEvent servletContextAttributeEvent =
			new ServletContextAttributeEvent(this, name, attributeValue);

		for (ServletContextAttributeListener servletContextAttributeListener :
				servletContextAttributeListeners) {

			servletContextAttributeListener.attributeRemoved(
				servletContextAttributeEvent);
		}
	}

	@Override
	public void setAttribute(String name, Object value) {
		if (value == null) {
			removeAttribute(name);

			return;
		}

		Dictionary<String, Object> contextAttributes =
			_servletContextHelperDataContext.getContextAttributes();

		boolean added = false;

		if (contextAttributes.get(name) == null) {
			added = true;
		}

		contextAttributes.put(name, value);

		EventListeners eventListeners = _contextController.getEventListeners();

		List<ServletContextAttributeListener> servletContextAttributeListeners =
			eventListeners.get(ServletContextAttributeListener.class);

		if (servletContextAttributeListeners.isEmpty()) {
			return;
		}

		ServletContextAttributeEvent servletContextAttributeEvent =
			new ServletContextAttributeEvent(this, name, value);

		for (ServletContextAttributeListener servletContextAttributeListener :
				servletContextAttributeListeners) {

			if (added) {
				servletContextAttributeListener.attributeAdded(
					servletContextAttributeEvent);
			}
			else {
				servletContextAttributeListener.attributeReplaced(
					servletContextAttributeEvent);
			}
		}
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

	private final Bundle _bundle;
	private final ClassLoader _classLoader;
	private final ContextController _contextController;
	private final ServletContext _servletContext;
	private final ServletContextHelper _servletContextHelper;
	private final ServletContextHelperDataContext
		_servletContextHelperDataContext;

}