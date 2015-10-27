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

package com.liferay.portal.util;

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tina Tian
 */
public class IdentifiableOSGIServiceUtil {

	public static IdentifiableOSGIService getIdentifiableOSGIService(
		String osgiServiceIdentifier) {

		return _identifiableOSGIServices.get(osgiServiceIdentifier);
	}

	private IdentifiableOSGIServiceUtil() {
	}

	private static final Map<String, IdentifiableOSGIService>
		_identifiableOSGIServices = new ConcurrentHashMap<>();
	private static final
		ServiceTracker<IdentifiableOSGIService, IdentifiableOSGIService>
			_serviceTracker;

	static {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			IdentifiableOSGIService.class,
			new IdentifiableOSGIServiceServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private static class IdentifiableOSGIServiceServiceTrackerCustomizer
		implements
			ServiceTrackerCustomizer
				<IdentifiableOSGIService, IdentifiableOSGIService> {

		@Override
		public IdentifiableOSGIService addingService(
			ServiceReference<IdentifiableOSGIService> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			IdentifiableOSGIService identifiableOSGIService =
				registry.getService(serviceReference);

			_identifiableOSGIServices.put(
				identifiableOSGIService.getOSGIServiceIdentifier(),
				identifiableOSGIService);

			return identifiableOSGIService;
		}

		@Override
		public void modifiedService(
			ServiceReference<IdentifiableOSGIService> serviceReference,
			IdentifiableOSGIService identifiableOSGIService) {

			_identifiableOSGIServices.put(
				identifiableOSGIService.getOSGIServiceIdentifier(),
				identifiableOSGIService);
		}

		@Override
		public void removedService(
			ServiceReference<IdentifiableOSGIService> serviceReference,
			IdentifiableOSGIService identifiableOSGIService) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_identifiableOSGIServices.remove(
				identifiableOSGIService.getOSGIServiceIdentifier());
		}

	}

}