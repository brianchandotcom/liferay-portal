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

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import org.osgi.framework.BundleContext;

/**
 * @author Tina Tian
 */
public class IdentifiableOSGiServiceUtil {

	public static IdentifiableOSGiService getIdentifiableOSGiService(
		String osgiServiceIdentifier) {

		return _identifiableOSGiServices.getService(osgiServiceIdentifier);
	}

	private IdentifiableOSGiServiceUtil() {
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	private static final ServiceTrackerMap<String, IdentifiableOSGiService>
		_identifiableOSGiServices = ServiceTrackerMapFactory.openSingleValueMap(
			_bundleContext, IdentifiableOSGiService.class, null,
			(serviceReference, emitter) -> {
				IdentifiableOSGiService identifiableOSGiService =
					_bundleContext.getService(serviceReference);

				emitter.emit(
					identifiableOSGiService.getOSGiServiceIdentifier());

				_bundleContext.ungetService(serviceReference);
			});

}