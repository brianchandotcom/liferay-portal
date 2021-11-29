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

package com.liferay.site.navigation.menu.item.display.page.internal.type.provider;

import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
 * @author Lourdes Fernández Besada
 */
@Component(
	immediate = true,
	service = DisplayPageSiteNavigationMenuItemTypeProviderTrackerImpl.class
)
public class DisplayPageSiteNavigationMenuItemTypeProviderTrackerImpl {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext,
			(Class<InfoItemDetailsProvider<?>>)
				(Class<?>)InfoItemDetailsProvider.class,
			new InfoItemDetailsProviderServiceTrackerCustomizer(
				bundleContext, _serviceRegistrations));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		for (ServiceRegistration<SiteNavigationMenuItemType>
				serviceRegistration : _serviceRegistrations.values()) {

			try {
				serviceRegistration.unregister();
			}
			catch (IllegalStateException illegalStateException) {
				_log.error(illegalStateException, illegalStateException);
			}
		}

		_serviceRegistrations.clear();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DisplayPageSiteNavigationMenuItemTypeProviderTrackerImpl.class);

	private final Map<String, ServiceRegistration<SiteNavigationMenuItemType>>
		_serviceRegistrations = new ConcurrentHashMap<>();
	private ServiceTracker
		<InfoItemDetailsProvider<?>, InfoItemDetailsProvider<?>>
			_serviceTracker;

	private class InfoItemDetailsProviderServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<InfoItemDetailsProvider<?>, InfoItemDetailsProvider<?>> {

		public InfoItemDetailsProviderServiceTrackerCustomizer(
			BundleContext bundleContext,
			Map<String, ServiceRegistration<SiteNavigationMenuItemType>>
				serviceRegistrations) {

			_bundleContext = bundleContext;

			_serviceRegistrations = serviceRegistrations;
		}

		@Override
		public InfoItemDetailsProvider<?> addingService(
			ServiceReference<InfoItemDetailsProvider<?>> serviceReference) {

			return _bundleContext.getService(serviceReference);
		}

		@Override
		public void modifiedService(
			ServiceReference<InfoItemDetailsProvider<?>> serviceReference,
			InfoItemDetailsProvider<?> infoItemDetailsProvider) {

			removedService(serviceReference, infoItemDetailsProvider);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<InfoItemDetailsProvider<?>> serviceReference,
			InfoItemDetailsProvider<?> infoItemDetailsProvider) {

			InfoItemClassDetails infoItemClassDetails =
				infoItemDetailsProvider.getInfoItemClassDetails();

			ServiceRegistration<SiteNavigationMenuItemType>
				serviceRegistration = _serviceRegistrations.remove(
					infoItemClassDetails.getClassName());

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}

		private final BundleContext _bundleContext;
		private final Map
			<String, ServiceRegistration<SiteNavigationMenuItemType>>
				_serviceRegistrations;

	}

}