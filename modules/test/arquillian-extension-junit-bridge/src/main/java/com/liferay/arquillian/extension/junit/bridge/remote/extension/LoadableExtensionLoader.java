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

package com.liferay.arquillian.extension.junit.bridge.remote.extension;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.jboss.arquillian.core.api.Injector;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.impl.loadable.JavaSPIExtensionLoader;
import org.jboss.arquillian.core.impl.loadable.SecurityActions;
import org.jboss.arquillian.core.impl.loadable.ServiceRegistry;
import org.jboss.arquillian.core.spi.ExtensionLoader;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.core.spi.ServiceLoader;
import org.jboss.arquillian.core.spi.context.Context;
import org.jboss.arquillian.core.spi.event.ManagerProcessing;

/**
 * @author Matthew Tambara
 */
public class LoadableExtensionLoader {

	public LoadableExtensionLoader() {
		this(new JavaSPIExtensionLoader());
	}

	public void load(@Observes final ManagerProcessing managerProcessing) {
		ExtensionLoader extensionLoader = _locateExtensionLoader();

		ServiceRegistry serviceRegistry = new ServiceRegistry(
			_injectorInstance.get());

		Collection<LoadableExtension> loadableExtensions =
			extensionLoader.load();

		for (LoadableExtension loadableExtension : loadableExtensions) {
			loadableExtension.register(
				new LoadableExtension.ExtensionBuilder() {

					@Override
					public LoadableExtension.ExtensionBuilder context(
						Class<? extends Context> context) {

						managerProcessing.context(context);

						return this;
					}

					@Override
					public LoadableExtension.ExtensionBuilder observer(
						Class<?> handler) {

						managerProcessing.observer(handler);

						return this;
					}

					@Override
					public <T> LoadableExtension.ExtensionBuilder override(
						Class<T> service, Class<? extends T> oldServiceImpl,
						Class<? extends T> newServiceImpl) {

						serviceRegistry.overrideService(
							service, oldServiceImpl, newServiceImpl);

						return this;
					}

					@Override
					public <T> LoadableExtension.ExtensionBuilder service(
						Class<T> service, Class<? extends T> impl) {

						serviceRegistry.addService(service, impl);

						return this;
					}

				});
		}

		_serviceLoaderInstanceProducer.set(serviceRegistry.getServiceLoader());
	}

	private LoadableExtensionLoader(
		JavaSPIExtensionLoader javaSPIExtensionLoader) {

		_javaSPIExtensionLoader = javaSPIExtensionLoader;
	}

	private ExtensionLoader _locateExtensionLoader() {
		Collection<ExtensionLoader> loaders = Collections.emptyList();

		if (SecurityActions.getThreadContextClassLoader() != null) {
			loaders = _javaSPIExtensionLoader.all(
				SecurityActions.getThreadContextClassLoader(),
				ExtensionLoader.class);
		}

		if (loaders.isEmpty()) {
			loaders = _javaSPIExtensionLoader.all(
				LoadableExtensionLoader.class.getClassLoader(),
				ExtensionLoader.class);
		}

		if (loaders.size() > 1) {
			throw new IllegalArgumentException(
				"Multiple ExtensionLoader's found on classpath: " +
					_toString(loaders));
		}

		if (loaders.size() == 1) {
			Iterator<ExtensionLoader> iterator = loaders.iterator();

			return iterator.next();
		}

		return _javaSPIExtensionLoader;
	}

	private String _toString(Collection<ExtensionLoader> extensionLoaders) {
		StringBuilder sb = new StringBuilder();

		for (ExtensionLoader loader : extensionLoaders) {
			Class<?> clazz = loader.getClass();

			sb.append(clazz.getName());

			sb.append(", ");
		}

		return sb.toString();
	}

	@Inject
	private Instance<Injector> _injectorInstance;

	private JavaSPIExtensionLoader _javaSPIExtensionLoader;

	@ApplicationScoped
	@Inject
	private InstanceProducer<ServiceLoader> _serviceLoaderInstanceProducer;

}