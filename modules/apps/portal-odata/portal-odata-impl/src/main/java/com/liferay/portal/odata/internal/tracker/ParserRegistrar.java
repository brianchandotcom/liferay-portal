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

package com.liferay.portal.odata.internal.tracker;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.sort.SortParser;
import com.liferay.portal.odata.sort.SortParserProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * Registers a new {@code FilterParser} and {@code SortParser} for every
 * registered entity model.
 *
 * @author Cristina González
 * @author Preston Crary
 */
@Component(immediate = true, service = {})
public class ParserRegistrar {

	@Activate
	public void activate(BundleContext bundleContext) {
		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, EntityModel.class,
			new EntityModelTrackerCustomizer(
				bundleContext, _filterParserProvider, _sortParserProvider));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	@Reference
	private FilterParserProvider _filterParserProvider;

	private ServiceTracker<?, ?> _serviceTracker;

	@Reference
	private SortParserProvider _sortParserProvider;

	private static class EntityModelTrackerCustomizer
		implements ServiceTrackerCustomizer
			<EntityModel, ParserServiceRegistrations> {

		@Override
		public ParserServiceRegistrations addingService(
			ServiceReference<EntityModel> serviceReference) {

			EntityModel entityModel = _bundleContext.getService(
				serviceReference);

			ParserServiceRegistrations parserServiceRegistrations =
				new ParserServiceRegistrations(
					serviceReference.getProperty("entity.model.name"));

			try {
				parserServiceRegistrations.register(
					_bundleContext, FilterParser.class,
					_filterParserProvider.provide(entityModel));

				parserServiceRegistrations.register(
					_bundleContext, SortParser.class,
					_sortParserProvider.provide(entityModel));
			}
			catch (Throwable t) {
				parserServiceRegistrations.unregister();

				_bundleContext.ungetService(serviceReference);

				throw t;
			}

			return parserServiceRegistrations;
		}

		@Override
		public void modifiedService(
			ServiceReference<EntityModel> serviceReference,
			ParserServiceRegistrations parserServiceRegistrations) {

			if (!Objects.equals(
					serviceReference.getProperty("entity.model.name"),
					parserServiceRegistrations._entityModelName)) {

				removedService(serviceReference, parserServiceRegistrations);

				addingService(serviceReference);
			}
		}

		@Override
		public void removedService(
			ServiceReference<EntityModel> serviceReference,
			ParserServiceRegistrations parserServiceRegistrations) {

			_bundleContext.ungetService(serviceReference);

			parserServiceRegistrations.unregister();
		}

		private EntityModelTrackerCustomizer(
			BundleContext bundleContext,
			FilterParserProvider filterParserProvider,
			SortParserProvider sortParserProvider) {

			_bundleContext = bundleContext;
			_filterParserProvider = filterParserProvider;
			_sortParserProvider = sortParserProvider;
		}

		private final BundleContext _bundleContext;
		private final FilterParserProvider _filterParserProvider;
		private final SortParserProvider _sortParserProvider;

	}

	private static class ParserServiceRegistrations {

		public <T> void register(
			BundleContext bundleContext, Class<T> clazz, T parser) {

			ServiceRegistration<?> serviceRegistration =
				bundleContext.registerService(
					clazz, parser,
					new HashMapDictionary<String, Object>() {
						{
							put("entity.model.name", _entityModelName);
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

		private ParserServiceRegistrations(Object entityModelName) {
			_entityModelName = entityModelName;
		}

		private final Object _entityModelName;
		private final List<ServiceRegistration<?>> _serviceRegistrations =
			new ArrayList<>();

	}

}