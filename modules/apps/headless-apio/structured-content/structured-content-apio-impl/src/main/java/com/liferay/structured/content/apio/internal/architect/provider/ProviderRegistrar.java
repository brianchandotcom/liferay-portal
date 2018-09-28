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

package com.liferay.structured.content.apio.internal.architect.provider;

import com.liferay.apio.architect.provider.Provider;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.structured.content.apio.architect.entity.EntityModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Cristina González
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class ProviderRegistrar {

	@Activate
	public void activate(BundleContext bundleContext) {
		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, EntityModel.class,
			new EntityModelTrackerCustomizer(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private ServiceTracker<?, ?> _serviceTracker;

	private static class EntityModelTrackerCustomizer
		implements ServiceTrackerCustomizer
			<EntityModel, ProviderServiceRegistrations> {

		@Override
		public ProviderServiceRegistrations addingService(
			ServiceReference<EntityModel> serviceReference) {

			EntityModel entityModel = _bundleContext.getService(
				serviceReference);

			ProviderServiceRegistrations providerServiceRegistrations =
				new ProviderServiceRegistrations(
					serviceReference.getProperty("entity.model.name"));

			try {
				providerServiceRegistrations.register(
					_bundleContext, new FilterProvider(entityModel), "filter");
				providerServiceRegistrations.register(
					_bundleContext, new SortProvider(entityModel), "sort");
			}
			catch (Throwable t) {
				providerServiceRegistrations.unregister();

				_bundleContext.ungetService(serviceReference);

				throw t;
			}

			return providerServiceRegistrations;
		}

		@Override
		public void modifiedService(
			ServiceReference<EntityModel> serviceReference,
			ProviderServiceRegistrations providerServiceRegistrations) {

			if (!Objects.equals(
					serviceReference.getProperty("entity.model.name"),
					providerServiceRegistrations._entityModelName)) {

				removedService(serviceReference, providerServiceRegistrations);

				addingService(serviceReference);
			}
		}

		@Override
		public void removedService(
			ServiceReference<EntityModel> serviceReference,
			ProviderServiceRegistrations providerServiceRegistrations) {

			_bundleContext.ungetService(serviceReference);

			providerServiceRegistrations.unregister();
		}

		private EntityModelTrackerCustomizer(BundleContext bundleContext) {
			_bundleContext = bundleContext;
		}

		private final BundleContext _bundleContext;

	}

	private static class ProviderServiceRegistrations {

		public void register(
			BundleContext bundleContext, Provider<?> provider, String type) {

			ServiceRegistration<?> serviceRegistration =
				bundleContext.registerService(
					Provider.class, provider,
					new HashMapDictionary<String, Object>() {
						{
							put("entity.model.name", _entityModelName);
							put("provider.type", type);
						}
					});

			_serviceRegistrations.add(serviceRegistration);
		}

		public void unregister() {
			for (ServiceRegistration<?> serviceRegistration :
					_serviceRegistrations) {

				serviceRegistration.unregister();
			}
		}

		private ProviderServiceRegistrations(Object entityModelName) {
			_entityModelName = entityModelName;
		}

		private final Object _entityModelName;
		private final List<ServiceRegistration<?>> _serviceRegistrations =
			new ArrayList<>();

	}

}