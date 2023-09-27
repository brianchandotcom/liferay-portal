/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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