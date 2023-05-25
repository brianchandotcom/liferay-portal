/*******************************************************************************
 * Copyright (c) 2014, 2015 Raymond Augé and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 ******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.registration;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.osgi.web.http.servlet.internal.HttpServiceRuntimeImpl;
import com.liferay.portal.osgi.web.http.servlet.internal.context.ContextController;
import com.liferay.portal.osgi.web.http.servlet.internal.servlet.Match;

import java.io.IOException;

import java.util.Objects;
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.dto.DTO;
import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Raymond Augé
 */
public abstract class EndpointRegistration<D extends DTO>
	extends MatchableRegistration<Servlet, D>
	implements Comparable<EndpointRegistration<?>> {

	public EndpointRegistration(
		ContextController.ServiceHolder<Servlet> serviceHolder, D d,
		ServletContextHelper servletContextHelper,
		ContextController contextController, ClassLoader legacyTCCL) {

		super(serviceHolder.get(), d);

		_serviceHolder = serviceHolder;
		_servletContextHelper = servletContextHelper;
		_contextController = contextController;

		if (legacyTCCL != null) {
			_classLoader = legacyTCCL;
		}
		else {
			Bundle bundle = serviceHolder.getBundle();

			BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

			_classLoader = bundleWiring.getClassLoader();
		}
	}

	@Override
	public int compareTo(EndpointRegistration<?> other) {
		return _serviceHolder.compareTo(other._serviceHolder);
	}

	@Override
	public void destroy() {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			Set<EndpointRegistration<?>> endpointRegistrations =
				_contextController.getEndpointRegistrations();

			endpointRegistrations.remove(this);

			HttpServiceRuntimeImpl httpServiceRuntimeImpl =
				_contextController.getHttpServiceRuntime();

			Set<Object> registeredObjects =
				httpServiceRuntimeImpl.getRegisteredObjects();

			registeredObjects.remove(getT());

			_contextController.ungetServletContextHelper(
				_serviceHolder.getBundle());

			super.destroy();

			getT().destroy();
		}
		finally {
			_contextController.destroyContextAttributes();

			currentThread.setContextClassLoader(contextClassLoader);

			_serviceHolder.release();
		}
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof EndpointRegistration)) {
			return false;
		}

		EndpointRegistration<?> endpointRegistration =
			(EndpointRegistration<?>)object;

		return getT().equals(endpointRegistration.getT());
	}

	public abstract String getName();

	public abstract String[] getPatterns();

	public abstract long getServiceId();

	public ServletContext getServletContext() {
		ServletConfig servletConfig = getT().getServletConfig();

		return servletConfig.getServletContext();
	}

	public ServletContextHelper getServletContextHelper() {
		return _servletContextHelper;
	}

	@Override
	public int hashCode() {
		return Long.hashCode(getServiceId());
	}

	public void init(ServletConfig servletConfig) throws ServletException {
		boolean initialized = false;

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			_contextController.createContextAttributes();

			getT().init(servletConfig);

			initialized = true;
		}
		finally {
			if (!initialized) {
				_contextController.destroyContextAttributes();
			}

			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public String match(
		String name, String servletPath, String pathInfo, String extension,
		Match match) {

		if (name != null) {
			if (Objects.equals(getName(), name)) {
				return name;
			}

			return null;
		}

		String[] patterns = getPatterns();

		if (patterns == null) {
			return null;
		}

		for (String pattern : patterns) {
			if (doMatch(pattern, servletPath, extension, match)) {
				return pattern;
			}
		}

		return null;
	}

	public void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_classLoader);

			getT().service(httpServletRequest, httpServletResponse);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public String toString() {
		String toString = _toString;

		if (toString == null) {
			toString = StringBundler.concat(
				EndpointRegistration.class.getSimpleName(), '[', getD(), ']');

			_toString = toString;
		}

		return toString;
	}

	private final ClassLoader _classLoader;
	private final ContextController _contextController;
	private final ContextController.ServiceHolder<Servlet> _serviceHolder;
	private final ServletContextHelper _servletContextHelper;
	private String _toString;

}