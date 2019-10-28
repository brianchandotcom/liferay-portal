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

package com.liferay.portal.repository.registry;

import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.capabilities.PortalCapabilityLocator;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author Shuyang Zhou
 */
public class RepositoryDefinerRegister {

	public void afterPropertiesSet() {
		final Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			PortalCapabilityLocator.class,
			new ServiceTrackerCustomizer
				<PortalCapabilityLocator,
				 ServiceRegistration<RepositoryDefiner>>() {

				@Override
				public ServiceRegistration<RepositoryDefiner> addingService(
					ServiceReference<PortalCapabilityLocator>
						serviceReference) {

					PortalCapabilityLocator portalCapabilityLocator =
						registry.getService(serviceReference);

					RepositoryDefiner repositoryDefiner =
						_repositoryDefinerFactoryBiFunction.apply(
							portalCapabilityLocator, _repositoryFactory);

					Map<String, Object> properties =
						HashMapBuilder.<String, Object>put(
							"class.name", repositoryDefiner.getClassName()
						).build();

					return registry.registerService(
						RepositoryDefiner.class, repositoryDefiner, properties);
				}

				@Override
				public void modifiedService(
					ServiceReference<PortalCapabilityLocator> serviceReference,
					ServiceRegistration<RepositoryDefiner>
						serviceRegistration) {
				}

				@Override
				public void removedService(
					ServiceReference<PortalCapabilityLocator> serviceReference,
					ServiceRegistration<RepositoryDefiner>
						serviceRegistration) {

					serviceRegistration.unregister();
				}

			});

		_serviceTracker.open();
	}

	public void destroy() {
		_serviceTracker.close();
	}

	public void setRepositoryDefinerFactoryBiFunction(
		BiFunction
			<PortalCapabilityLocator, RepositoryFactory, RepositoryDefiner>
				repositoryDefinerFactoryBiFunction) {

		_repositoryDefinerFactoryBiFunction =
			repositoryDefinerFactoryBiFunction;
	}

	public void setRepositoryFactory(RepositoryFactory repositoryFactory) {
		_repositoryFactory = repositoryFactory;
	}

	private BiFunction
		<PortalCapabilityLocator, RepositoryFactory, RepositoryDefiner>
			_repositoryDefinerFactoryBiFunction;
	private RepositoryFactory _repositoryFactory;
	private ServiceTracker
		<PortalCapabilityLocator, ServiceRegistration<RepositoryDefiner>>
			_serviceTracker;

}