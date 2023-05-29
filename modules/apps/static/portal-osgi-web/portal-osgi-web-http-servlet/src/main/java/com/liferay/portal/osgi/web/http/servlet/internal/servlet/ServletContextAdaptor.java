/*******************************************************************************
 * Copyright (c) 2005, 2016 Cognos Incorporated, IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cognos Incorporated - initial API and implementation
 *     IBM Corporation - bug fixes and enhancements
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 *     Juan Gonzalez <juan.gonzalez@liferay.com> - Bug 486412
 *******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.servlet;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.osgi.web.http.servlet.internal.context.ContextController;
import com.liferay.portal.osgi.web.http.servlet.internal.context.DispatchTargets;
import com.liferay.portal.osgi.web.http.servlet.internal.context.ProxyContext;
import com.liferay.portal.osgi.web.http.servlet.internal.util.Const;
import com.liferay.portal.osgi.web.http.servlet.internal.util.EventListeners;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.net.URL;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Cognos Incorporated
 * @author IBM Corporation
 * @author Raymond Augé
 * @author Juan González
 */
public class ServletContextAdaptor {

	public ServletContextAdaptor(
		ContextController contextController, Bundle bundle,
		ServletContextHelper servletContextHelper,
		EventListeners eventListeners,
		AccessControlContext accessControlContext) {

		_contextController = contextController;
		_bundle = bundle;
		_servletContextHelper = servletContextHelper;
		_eventListeners = eventListeners;

		_proxyContext = contextController.getProxyContext();

		_servletContext = _proxyContext.getServletContext();

		_accessControlContext = accessControlContext;

		BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

		_classLoader = bundleWiring.getClassLoader();
	}

	public void addFilter(String arg1, Class<? extends Filter> arg2) {
		throw new UnsupportedOperationException();
	}

	public void addFilter(String arg1, Filter arg2) {
		throw new UnsupportedOperationException();
	}

	public void addFilter(String arg1, String arg2) {
		throw new UnsupportedOperationException();
	}

	public void addListener(Class<?> arg1) {
		throw new UnsupportedOperationException();
	}

	public void addListener(EventListener arg1) {
		throw new UnsupportedOperationException();
	}

	public void addListener(String arg1) {
		throw new UnsupportedOperationException();
	}

	public void addServlet(String arg1, Class<? extends Servlet> arg2) {
		throw new UnsupportedOperationException();
	}

	public void addServlet(String arg1, Servlet arg2) {
		throw new UnsupportedOperationException();
	}

	public void addServlet(String arg1, String arg2) {
		throw new UnsupportedOperationException();
	}

	public void createFilter(Class<?> arg1) {
		throw new UnsupportedOperationException();
	}

	public void createListener(Class<?> arg1) {
		throw new UnsupportedOperationException();
	}

	public void createServlet(Class<?> arg1) {
		throw new UnsupportedOperationException();
	}

	public ServletContext createServletContext() {
		Class<?> clazz = getClass();

		return (ServletContext)ProxyUtil.newProxyInstance(
			clazz.getClassLoader(), new Class<?>[] {ServletContext.class},
			new AdaptorInvocationHandler());
	}

	public void declareRoles(String... arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof ServletContext) ||
			!ProxyUtil.isProxyClass(object.getClass())) {

			return false;
		}

		InvocationHandler invocationHandler = ProxyUtil.getInvocationHandler(
			object);

		if (!(invocationHandler instanceof AdaptorInvocationHandler)) {
			return false;
		}

		AdaptorInvocationHandler adaptorInvocationHandler =
			(AdaptorInvocationHandler)invocationHandler;

		return _contextController.equals(
			adaptorInvocationHandler.getContextController());
	}

	public Object getAttribute(String attributeName) {
		if (attributeName.equals("osgi-bundlecontext")) {
			return _bundle.getBundleContext();
		}

		Dictionary<String, Object> attributes = _getContextAttributes();

		return attributes.get(attributeName);
	}

	public Enumeration<String> getAttributeNames() {
		Dictionary<String, Object> attributes = _getContextAttributes();

		return attributes.keys();
	}

	public ClassLoader getClassLoader() {
		return _classLoader;
	}

	public String getContextPath() {
		return _contextController.getFullContextPath();
	}

	public String getInitParameter(String name) {
		Map<String, String> initParams = _contextController.getInitParams();

		return initParams.get(name);
	}

	public Enumeration<String> getInitParameterNames() {
		Map<String, String> initParams = _contextController.getInitParams();

		return Collections.enumeration(initParams.keySet());
	}

	public String getMimeType(String name) {
		String mimeType = null;

		try {
			mimeType = AccessController.doPrivileged(
				(PrivilegedExceptionAction<String>)
					() -> _servletContextHelper.getMimeType(name),
				_accessControlContext);
		}
		catch (PrivilegedActionException privilegedActionException) {
			_log.error(privilegedActionException.getException());
		}

		if (mimeType != null) {
			return mimeType;
		}

		return _servletContext.getMimeType(name);
	}

	public RequestDispatcher getNamedDispatcher(String servletName) {
		DispatchTargets dispatchTargets = _contextController.getDispatchTargets(
			servletName, null, null, null, null, null, Match.EXACT, null);

		if (dispatchTargets == null) {
			return null;
		}

		return new RequestDispatcherAdaptor(dispatchTargets, servletName);
	}

	public String getRealPath(String path) {
		try {
			return AccessController.doPrivileged(
				(PrivilegedExceptionAction<String>)
					() -> _servletContextHelper.getRealPath(path),
				_accessControlContext);
		}
		catch (PrivilegedActionException privilegedActionException) {
			_log.error(privilegedActionException.getException());
		}

		return null;
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		if (!path.startsWith(Const.SLASH)) {
			return null;
		}

		String fullContextPath = _contextController.getFullContextPath();

		if (path.startsWith(fullContextPath)) {
			path = path.substring(fullContextPath.length());
		}

		DispatchTargets dispatchTargets = _contextController.getDispatchTargets(
			path, null);

		if (dispatchTargets == null) {
			return null;
		}

		return new RequestDispatcherAdaptor(dispatchTargets, path);
	}

	public URL getResource(String name) {
		try {
			return AccessController.doPrivileged(
				(PrivilegedExceptionAction<URL>)
					() -> _servletContextHelper.getResource(name),
				_accessControlContext);
		}
		catch (PrivilegedActionException privilegedActionException) {
			_log.error(privilegedActionException.getException());
		}

		return null;
	}

	public InputStream getResourceAsStream(String name) {
		URL url = getResource(name);

		if (url != null) {
			try {
				return url.openStream();
			}
			catch (IOException ioException) {
				_log.error(ioException);
			}
		}

		return null;
	}

	public Set<String> getResourcePaths(String name) {
		if ((name == null) || !name.startsWith(Const.SLASH)) {
			return null;
		}

		try {
			return AccessController.doPrivileged(
				(PrivilegedExceptionAction<Set<String>>)
					() -> _servletContextHelper.getResourcePaths(name),
				_accessControlContext);
		}
		catch (PrivilegedActionException privilegedActionException) {
			_log.error(privilegedActionException.getException());
		}

		return null;
	}

	public String getServletContextName() {
		return _contextController.getContextName();
	}

	@Override
	public int hashCode() {
		return _contextController.hashCode();
	}

	public void removeAttribute(String attributeName) {
		List<ServletContextAttributeListener> servletContextAttributeListeners =
			_eventListeners.get(ServletContextAttributeListener.class);

		if (servletContextAttributeListeners.isEmpty()) {
			return;
		}

		Dictionary<String, Object> attributes = _getContextAttributes();

		ServletContextAttributeEvent servletContextAttributeEvent =
			new ServletContextAttributeEvent(
				_servletContextThreadLocal.get(), attributeName,
				attributes.remove(attributeName));

		for (ServletContextAttributeListener servletContextAttributeListener :
				servletContextAttributeListeners) {

			servletContextAttributeListener.attributeRemoved(
				servletContextAttributeEvent);
		}
	}

	public void setAttribute(String attributeName, Object attributeValue) {
		if (attributeValue == null) {
			removeAttribute(attributeName);

			return;
		}

		Dictionary<String, Object> attributes = _getContextAttributes();

		boolean added = false;

		if (attributes.get(attributeName) == null) {
			added = true;
		}

		attributes.put(attributeName, attributeValue);

		List<ServletContextAttributeListener> servletContextAttributeListeners =
			_eventListeners.get(ServletContextAttributeListener.class);

		if (servletContextAttributeListeners.isEmpty()) {
			return;
		}

		ServletContextAttributeEvent servletContextAttributeEvent =
			new ServletContextAttributeEvent(
				_servletContextThreadLocal.get(), attributeName,
				attributeValue);

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
	public String toString() {
		String value = _string;

		if (value == null) {
			value = StringBundler.concat(
				ServletContextAdaptor.class.getSimpleName(), "[",
				_contextController, "]");

			_string = value;
		}

		return value;
	}

	private Dictionary<String, Object> _getContextAttributes() {
		return _proxyContext.getContextAttributes(_contextController);
	}

	private Object _invoke(Object proxy, Method method, Object[] args)
		throws Throwable {

		boolean useThreadLocal = false;

		if (Objects.equals(method.getName(), "removeAttribute") ||
			Objects.equals(method.getName(), "setAttribute")) {

			useThreadLocal = true;
		}

		if (useThreadLocal) {
			_servletContextThreadLocal.set((ServletContext)proxy);
		}

		try {
			Method adaptorMethod = _contextToHandlerMethods.get(method);

			try {
				if (adaptorMethod != null) {
					return adaptorMethod.invoke(this, args);
				}

				return method.invoke(_servletContext, args);
			}
			catch (InvocationTargetException invocationTargetException) {
				throw invocationTargetException.getCause();
			}
		}
		finally {
			if (useThreadLocal) {
				_servletContextThreadLocal.remove();
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServletContextAdaptor.class.getName());

	private static final Map<Method, Method> _contextToHandlerMethods;
	private static final ThreadLocal<ServletContext>
		_servletContextThreadLocal = new ThreadLocal<>();

	static {
		_contextToHandlerMethods = new HashMap<>();

		Method[] handlerMethods =
			ServletContextAdaptor.class.getDeclaredMethods();

		for (Method handlerMethod : handlerMethods) {
			Class<?>[] parameterTypes = handlerMethod.getParameterTypes();

			try {
				_contextToHandlerMethods.put(
					ServletContext.class.getMethod(
						handlerMethod.getName(), parameterTypes),
					handlerMethod);
			}
			catch (NoSuchMethodException noSuchMethodException) {
				if (_log.isDebugEnabled()) {
					_log.debug(noSuchMethodException);
				}
			}
		}

		try {
			_contextToHandlerMethods.put(
				Object.class.getMethod("equals", Object.class),
				ServletContextAdaptor.class.getMethod("equals", Object.class));

			_contextToHandlerMethods.put(
				Object.class.getMethod("hashCode", (Class<?>[])null),
				ServletContextAdaptor.class.getMethod(
					"hashCode", (Class<?>[])null));
		}
		catch (NoSuchMethodException noSuchMethodException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchMethodException);
			}
		}
	}

	private final AccessControlContext _accessControlContext;
	private final Bundle _bundle;
	private final ClassLoader _classLoader;
	private final ContextController _contextController;
	private final EventListeners _eventListeners;
	private final ProxyContext _proxyContext;
	private final ServletContext _servletContext;
	private final ServletContextHelper _servletContextHelper;
	private String _string;

	private class AdaptorInvocationHandler implements InvocationHandler {

		public ContextController getContextController() {
			return _contextController;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			return ServletContextAdaptor.this._invoke(proxy, method, args);
		}

	}

}