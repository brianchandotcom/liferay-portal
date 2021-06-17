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

package com.liferay.shielded.container.internal.proxy;

import java.io.IOException;

import java.util.function.Supplier;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Shuyang Zhou
 */
public class ServletWrapper implements Servlet {

	public ServletWrapper(
		ProxyFactory proxyFactory, Supplier<? extends Servlet> servletSupplier,
		ServletContext servletContext) {

		_proxyFactory = proxyFactory;
		_servletSupplier = servletSupplier;

		_servletContext = servletContext;

		_classLoader = _servletContext.getClassLoader();
	}

	@Override
	public void destroy() {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(_classLoader);

		try {
			Servlet servlet = _servletSupplier.get();

			servlet.destroy();
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public ServletConfig getServletConfig() {
		Servlet servlet = _servletSupplier.get();

		return servlet.getServletConfig();
	}

	@Override
	public String getServletInfo() {
		Servlet servlet = _servletSupplier.get();

		return servlet.getServletInfo();
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(_classLoader);

		try {
			_servletConfig = _proxyFactory.createASMWrapper(
				_servletContext.getClassLoader(), ServletConfig.class,
				new ServletConfigDelegate(_servletContext), servletConfig);

			Servlet servlet = _servletSupplier.get();

			servlet.init(_servletConfig);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public void service(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(_classLoader);

		try {
			Servlet servlet = _servletSupplier.get();

			servlet.service(servletRequest, servletResponse);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private final ClassLoader _classLoader;
	private final ProxyFactory _proxyFactory;
	private ServletConfig _servletConfig;
	private final ServletContext _servletContext;
	private final Supplier<? extends Servlet> _servletSupplier;

}