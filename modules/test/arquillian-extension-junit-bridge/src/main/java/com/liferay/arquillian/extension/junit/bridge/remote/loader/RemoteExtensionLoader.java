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

package com.liferay.arquillian.extension.junit.bridge.remote.loader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.jboss.arquillian.container.test.impl.SecurityActions;
import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.core.spi.ExtensionLoader;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.core.spi.Validate;

/**
 * @author Matthew Tambara
 */
public class RemoteExtensionLoader implements ExtensionLoader {

	@Override
	public Collection<LoadableExtension> load() {
		List<LoadableExtension> extensions = new ArrayList<>();

		Collection<RemoteLoadableExtension> loaded = Collections.emptyList();

		if (SecurityActions.getThreadContextClassLoader() != null) {
			loaded = _all(
				SecurityActions.getThreadContextClassLoader(),
				RemoteLoadableExtension.class);
		}

		if (loaded.isEmpty()) {
			loaded = _all(
				RemoteExtensionLoader.class.getClassLoader(),
				RemoteLoadableExtension.class);
		}

		for (RemoteLoadableExtension extension : loaded) {
			extensions.add(extension);
		}

		return extensions;
	}

	private <T> Collection<T> _all(
		ClassLoader classLoader, Class<T> serviceClass) {

		Validate.notNull(classLoader, "ClassLoader must be provided");
		Validate.notNull(serviceClass, "ServiceClass must be provided");

		return _createInstances(_load(serviceClass, classLoader));
	}

	private <T> T _createInstance(Class<? extends T> serviceImplClass) {
		try {
			return SecurityActions.newInstance(
				serviceImplClass, new Class<?>[0], new Object[0]);
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Could not create a new instance of Service implementation " +
					serviceImplClass.getName(),
				e);
		}
	}

	private <T> Set<T> _createInstances(Set<Class<? extends T>> providers) {
		Set<T> providerImpls = new LinkedHashSet<>();

		for (Class<? extends T> serviceClass : providers) {
			providerImpls.add(_createInstance(serviceClass));
		}

		return providerImpls;
	}

	private <T> Set<Class<? extends T>> _load(
		Class<T> serviceClass, ClassLoader loader) {

		String serviceFile = _SERVICES + "/" + serviceClass.getName();

		LinkedHashSet<Class<? extends T>> providers = new LinkedHashSet<>();
		LinkedHashSet<Class<? extends T>> vetoedProviders =
			new LinkedHashSet<>();

		try {
			Enumeration<URL> enumeration = loader.getResources(serviceFile);

			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();

				InputStream is = url.openStream();

				BufferedReader bufferedReader = null;

				try {
					bufferedReader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));

					String line = bufferedReader.readLine();

					while (null != line) {
						line = _skipCommentAndTrim(line);

						if (line.length() > 0) {
							try {
								boolean mustBeVetoed = line.startsWith("!");

								if (mustBeVetoed) {
									line = line.substring(1);
								}

								Class<? extends T> provider =
									loader.loadClass(line).asSubclass(
										serviceClass);

								if (mustBeVetoed) {
									vetoedProviders.add(provider);
								}

								if (vetoedProviders.contains(provider)) {
									providers.remove(provider);
								}
								else {
									providers.add(provider);
								}
							}
							catch (ClassCastException cce) {
								throw new IllegalStateException(
									"Service " + line + " does not implement " +
										"expected type " +
											serviceClass.getName());
							}
						}

						line = bufferedReader.readLine();
					}
				}
				finally {
					if (bufferedReader != null) {
						bufferedReader.close();
					}
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Could not load services for " + serviceClass.getName(), e);
		}

		return providers;
	}

	private String _skipCommentAndTrim(String line) {
		int comment = line.indexOf('#');

		if (comment > -1) {
			line = line.substring(0, comment);
		}

		line = line.trim();

		return line;
	}

	private static final String _SERVICES = "META-INF/services";

}