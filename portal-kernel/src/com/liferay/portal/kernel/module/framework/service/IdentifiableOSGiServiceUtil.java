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

package com.liferay.portal.kernel.module.framework.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
public class IdentifiableOSGiServiceUtil {

	public static IdentifiableOSGiService getIdentifiableOSGiService(
		String osgiServiceIdentifier) {

		return _identifiableOSGiServices.get(osgiServiceIdentifier);
	}

	private IdentifiableOSGiServiceUtil() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IdentifiableOSGiServiceUtil.class);

	private static final Map<String, IdentifiableOSGiService>
		_identifiableOSGiServices = new ConcurrentHashMap<>();
	private static final ServiceTracker
		<IdentifiableOSGiService, IdentifiableOSGiService> _serviceTracker;

	private static class IdentifiableOSGiServiceServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<IdentifiableOSGiService, IdentifiableOSGiService> {

		@Override
		public IdentifiableOSGiService addingService(
			ServiceReference<IdentifiableOSGiService> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			IdentifiableOSGiService identifiableOSGiService =
				registry.getService(serviceReference);

			try {
				_identifiableOSGiServices.put(
					identifiableOSGiService.getOSGiServiceIdentifier(),
					identifiableOSGiService);
			}
			catch (UnsupportedOperationException uoe) {

				// LPS-89569

				if (_log.isDebugEnabled()) {
					_log.debug(uoe, uoe);
				}

				return null;
			}

			return identifiableOSGiService;
		}

		@Override
		public void modifiedService(
			ServiceReference<IdentifiableOSGiService> serviceReference,
			IdentifiableOSGiService identifiableOSGiService) {

			_identifiableOSGiServices.put(
				identifiableOSGiService.getOSGiServiceIdentifier(),
				identifiableOSGiService);
		}

		@Override
		public void removedService(
			ServiceReference<IdentifiableOSGiService> serviceReference,
			IdentifiableOSGiService identifiableOSGiService) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_identifiableOSGiServices.remove(
				identifiableOSGiService.getOSGiServiceIdentifier());
		}

	}

	static {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			IdentifiableOSGiService.class,
			new IdentifiableOSGiServiceServiceTrackerCustomizer());

		_serviceTracker.open();
	}

}