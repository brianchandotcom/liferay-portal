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

package com.liferay.structured.content.apio.internal.architect.filter;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.structured.content.apio.architect.entity.EntityModel;
import com.liferay.structured.content.apio.architect.filter.FilterParser;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * <code>FilterParserRegistry</code> registry of FilterParsers. This class
 * register a new FilterParser for every EntityModel registered.
 *
 * @author Cristina González
 * @review
 */
@Component(immediate = true, service = FilterParserRegistry.class)
public class FilterParserRegistry {

	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, EntityModel.class,
			new EntityModelTrackerCustomizer(_bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private BundleContext _bundleContext;
	private ServiceTracker
		<EntityModel, ServiceRegistration<FilterParser>> _serviceTracker;

	private class EntityModelTrackerCustomizer
		implements ServiceTrackerCustomizer
			<EntityModel, ServiceRegistration<FilterParser>> {

		public EntityModelTrackerCustomizer(BundleContext bundleContext) {
			_bundleContext = bundleContext;
		}

		@Override
		public ServiceRegistration<FilterParser> addingService(
			ServiceReference<EntityModel> serviceReference) {

			EntityModel entityModel = _bundleContext.getService(
				serviceReference);

			FilterParser filterParser = new FilterParserImpl(entityModel);

			try {
				return _bundleContext.registerService(
					FilterParser.class, filterParser,
					new HashMapDictionary<String, Object>() {
						{
							put(
								"entity.model.name",
								serviceReference.getProperty(
									"entity.model.name"));
						}
					});
			}
			catch (Throwable t) {
				_bundleContext.ungetService(serviceReference);

				throw t;
			}
		}

		@Override
		public void modifiedService(
			ServiceReference<EntityModel> serviceReference,
			ServiceRegistration<FilterParser> serviceRegistration) {

			removedService(serviceReference, serviceRegistration);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<EntityModel> serviceReference,
			ServiceRegistration<FilterParser> serviceRegistration) {

			_bundleContext.ungetService(serviceReference);

			_bundleContext.ungetService(serviceRegistration.getReference());
		}

		private BundleContext _bundleContext;

	}

}