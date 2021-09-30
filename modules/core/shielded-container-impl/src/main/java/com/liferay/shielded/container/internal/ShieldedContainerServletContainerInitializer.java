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

package com.liferay.shielded.container.internal;

import com.liferay.shielded.container.Ordered;
import com.liferay.shielded.container.ShieldedContainerInitializer;
import com.liferay.shielded.container.internal.proxy.ProxyFactory;
import com.liferay.shielded.container.internal.proxy.ServletContextDelegate;
import com.liferay.shielded.container.internal.session.ShieldedContainerHttpSessionListener;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author Shuyang Zhou
 */
public class ShieldedContainerServletContainerInitializer
	implements ServletContainerInitializer {

	@Override
	public void onStartup(Set<Class<?>> classes, ServletContext servletContext)
		throws ServletException {

		ClassLoader shieldedContainerClassLoader =
			_buildShieldContainerClassLoader(servletContext);

		ProxyFactory proxyFactory = new ProxyFactory(
			shieldedContainerClassLoader);

		ServletContextDelegate servletContextDelegate =
			new ServletContextDelegate(
				proxyFactory, servletContext, shieldedContainerClassLoader);

		servletContext = proxyFactory.createASMWrapper(
			shieldedContainerClassLoader, ServletContext.class,
			servletContextDelegate, servletContext);

		servletContextDelegate.setProxiedServletContext(servletContext);

		servletContext.addListener(
			new ShieldedContainerHttpSessionListener(servletContext));

		ServiceLoader<ShieldedContainerInitializer> serviceLoader =
			ServiceLoader.load(
				ShieldedContainerInitializer.class,
				shieldedContainerClassLoader);

		List<ShieldedContainerInitializer> shieldedContainerInitializers =
			new ArrayList<>();

		serviceLoader.forEach(shieldedContainerInitializers::add);

		shieldedContainerInitializers.sort(
			(sci1, sci2) -> _getOrder(sci1) - _getOrder(sci2));

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(shieldedContainerClassLoader);

		try {
			for (ShieldedContainerInitializer shieldedContainerInitializer :
					shieldedContainerInitializers) {

				shieldedContainerInitializer.initialize(servletContext);
			}
		}
		finally {
			currentThread.setContextClassLoader(classLoader);
		}
	}

	private ClassLoader _buildShieldContainerClassLoader(
			ServletContext servletContext)
		throws ServletException {

		List<URL> urls = new ArrayList<>();

		File shieldedContainerLib = new File(
			servletContext.getRealPath(
				ShieldedContainerInitializer.SHIELDED_CONTAINER_LIB));

		try {
			for (File jarFile :
					shieldedContainerLib.listFiles(
						(dir, name) -> {
							String lowercaseName = name.toLowerCase();

							return lowercaseName.endsWith(".jar");
						})) {

				URI uri = jarFile.toURI();

				urls.add(uri.toURL());
			}
		}
		catch (MalformedURLException malformedURLException) {
			throw new ServletException(
				"Unable to convert shielded container lib jar to URL",
				malformedURLException);
		}

		urls.sort(Comparator.comparing(URL::getPath));

		ClassLoader classLoader = new ShieldedContainerClassLoader(
			urls.toArray(new URL[0]), servletContext.getClassLoader());

		servletContext.setAttribute(
			ShieldedContainerClassLoader.NAME, classLoader);

		return classLoader;
	}

	private int _getOrder(Object object) {
		Class<?> clazz = object.getClass();

		Ordered ordered = clazz.getAnnotation(Ordered.class);

		if (ordered == null) {
			return 0;
		}

		return ordered.value();
	}

}