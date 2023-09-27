/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.repository.registry;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.capabilities.PortalCapabilityLocator;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.function.BiFunction;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Shuyang Zhou
 */
public class RepositoryDefinerRegister {

	public void afterPropertiesSet() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_serviceTracker = new ServiceTracker<>(
			bundleContext, PortalCapabilityLocator.class,
			new ServiceTrackerCustomizer
				<PortalCapabilityLocator,
				 ServiceRegistration<RepositoryDefiner>>() {

				@Override
				public ServiceRegistration<RepositoryDefiner> addingService(
					ServiceReference<PortalCapabilityLocator>
						serviceReference) {

					PortalCapabilityLocator portalCapabilityLocator =
						bundleContext.getService(serviceReference);

					RepositoryDefiner repositoryDefiner =
						_repositoryDefinerFactoryBiFunction.apply(
							portalCapabilityLocator, _repositoryFactory);

					return bundleContext.registerService(
						RepositoryDefiner.class, repositoryDefiner,
						MapUtil.singletonDictionary(
							"class.name", repositoryDefiner.getClassName()));
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

					bundleContext.ungetService(serviceReference);
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