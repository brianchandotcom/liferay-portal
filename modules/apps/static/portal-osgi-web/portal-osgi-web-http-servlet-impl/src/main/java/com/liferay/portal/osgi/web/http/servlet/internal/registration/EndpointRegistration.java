/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.registration;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.lang.ThreadContextClassLoaderUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.osgi.web.http.servlet.internal.HttpServletEndpointController;
import com.liferay.portal.osgi.web.http.servlet.internal.context.LiferayContextController;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Objects;
import java.util.Set;

import org.eclipse.equinox.http.servlet.internal.servlet.Match;

import org.osgi.dto.DTO;
import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Dante Wang
 */
public abstract class EndpointRegistration<D extends DTO>
	extends MatchableRegistration<Servlet, D>
	implements Comparable<EndpointRegistration<?>> {

	public EndpointRegistration(
		ServiceHolder<Servlet> serviceHolder, D d,
		ServletContextHelper servletContextHelper,
		LiferayContextController liferayContextController,
		ClassLoader legacyTCCL) {

		super(serviceHolder.get(), d);

		_serviceHolder = serviceHolder;
		_servletContextHelper = servletContextHelper;
		_liferayContextController = liferayContextController;

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
	public int compareTo(EndpointRegistration<?> endpointRegistration) {
		return _serviceHolder.compareTo(endpointRegistration._serviceHolder);
	}

	@Override
	public void destroy() {
		try (SafeCloseable safeCloseable = ThreadContextClassLoaderUtil.swap(
				_classLoader)) {

			Set<EndpointRegistration<?>> endpointRegistrations =
				_liferayContextController.getEndpointRegistrations();

			endpointRegistrations.remove(this);

			HttpServletEndpointController httpServletEndpointController =
				_liferayContextController.getHttpServletEndpointController();

			Set<Object> registeredObjects =
				httpServletEndpointController.getRegisteredObjects();

			Servlet servlet = getT();

			registeredObjects.remove(servlet);

			_liferayContextController.ungetServletContextHelper(
				_serviceHolder.getBundle());

			super.destroy();

			servlet.destroy();
		}
		finally {
			_serviceHolder.release();
		}
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof EndpointRegistration<?> endpointRegistration) {
			Servlet servlet = getT();

			return servlet.equals(endpointRegistration.getT());
		}

		return false;
	}

	public abstract String getName();

	public abstract String[] getPatterns();

	public abstract long getServiceId();

	public ServletContext getServletContext() {
		Servlet servlet = getT();

		ServletConfig servletConfig = servlet.getServletConfig();

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
		try (SafeCloseable safeCloseable = ThreadContextClassLoaderUtil.swap(
				_classLoader)) {

			Servlet servlet = getT();

			servlet.init(servletConfig);
		}
	}

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
			if (doMatch(pattern, servletPath, pathInfo, extension, match)) {
				return pattern;
			}
		}

		return null;
	}

	public void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try (SafeCloseable safeCloseable = ThreadContextClassLoaderUtil.swap(
				_classLoader)) {

			Servlet servlet = getT();

			servlet.service(httpServletRequest, httpServletResponse);
		}
	}

	@Override
	public String toString() {
		String value = _toString;

		if (value == null) {
			value = StringBundler.concat(
				_SIMPLE_NAME, CharPool.OPEN_BRACKET, getD(),
				CharPool.CLOSE_BRACKET);

			_toString = value;
		}

		return value;
	}

	private static final String _SIMPLE_NAME =
		EndpointRegistration.class.getSimpleName();

	private final ClassLoader _classLoader;
	private final LiferayContextController _liferayContextController;
	private final ServiceHolder<Servlet> _serviceHolder;
	private final ServletContextHelper _servletContextHelper;
	private String _toString;

}