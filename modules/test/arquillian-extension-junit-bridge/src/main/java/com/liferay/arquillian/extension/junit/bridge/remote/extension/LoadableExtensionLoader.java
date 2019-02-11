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

import com.liferay.arquillian.extension.junit.bridge.LiferayArquillianJUnitBridgeExtension;

import org.jboss.arquillian.core.api.Injector;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.impl.loadable.ServiceRegistry;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.core.spi.ServiceLoader;
import org.jboss.arquillian.core.spi.context.Context;
import org.jboss.arquillian.core.spi.event.ManagerProcessing;

/**
 * @author Matthew Tambara
 */
public class LoadableExtensionLoader {

	public void load(@Observes final ManagerProcessing managerProcessing) {
		LoadableExtension loadableExtension =
			new LiferayArquillianJUnitBridgeExtension();

		ServiceRegistry serviceRegistry = new ServiceRegistry(
			_injectorInstance.get());

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

		_serviceLoaderInstanceProducer.set(serviceRegistry.getServiceLoader());
	}

	@Inject
	private Instance<Injector> _injectorInstance;

	@ApplicationScoped
	@Inject
	private InstanceProducer<ServiceLoader> _serviceLoaderInstanceProducer;

}