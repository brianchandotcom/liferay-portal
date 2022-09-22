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

package com.liferay.portal.search.internal;

import com.liferay.portal.kernel.search.SearchEngineConfigurator;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Shuyang Zhou
 */
@Component(service = {})
public class SearchEngineConfiguratorTracker {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = new ServiceTracker<>(
			bundleContext, SearchEngineConfigurator.class,
			new ServiceTrackerCustomizer
				<SearchEngineConfigurator, SearchEngineConfigurator>() {

				@Override
				public SearchEngineConfigurator addingService(
					ServiceReference<SearchEngineConfigurator>
						serviceReference) {

					SearchEngineConfigurator searchEngineConfigurator =
						bundleContext.getService(serviceReference);

					searchEngineConfigurator.afterPropertiesSet();

					return searchEngineConfigurator;
				}

				@Override
				public void modifiedService(
					ServiceReference<SearchEngineConfigurator> serviceReference,
					SearchEngineConfigurator searchEngineConfigurator) {
				}

				@Override
				public void removedService(
					ServiceReference<SearchEngineConfigurator> serviceReference,
					SearchEngineConfigurator searchEngineConfigurator) {

					searchEngineConfigurator.destroy();

					bundleContext.ungetService(serviceReference);
				}

			});

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private ServiceTracker<SearchEngineConfigurator, SearchEngineConfigurator>
		_serviceTracker;

}