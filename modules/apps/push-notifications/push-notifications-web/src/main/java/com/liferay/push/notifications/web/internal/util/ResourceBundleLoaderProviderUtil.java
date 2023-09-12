/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.push.notifications.web.internal.util;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.resource.bundle.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.push.notifications.sender.PushNotificationsSender;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Andrea Di Giorgi
 */
public class ResourceBundleLoaderProviderUtil {

	public static ResourceBundleLoader getResourceBundleLoader(
		String platform) {

		ResourceBundleLoader resourceBundleLoader =
			_serviceTrackerMap.getService(platform);

		if (resourceBundleLoader == null) {
			return ResourceBundleLoaderUtil.getPortalResourceBundleLoader();
		}

		return new AggregateResourceBundleLoader(
			resourceBundleLoader,
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader());
	}

	private static final ServiceTrackerMap<String, ResourceBundleLoader>
		_serviceTrackerMap;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ResourceBundleLoaderProviderUtil.class);

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundle.getBundleContext(), PushNotificationsSender.class,
			"platform",
			new ServiceTrackerCustomizer
				<PushNotificationsSender, ResourceBundleLoader>() {

				@Override
				public ResourceBundleLoader addingService(
					ServiceReference<PushNotificationsSender>
						serviceReference) {

					Bundle bundle = serviceReference.getBundle();

					return ResourceBundleLoaderUtil.
						getResourceBundleLoaderByBundleSymbolicName(
							bundle.getSymbolicName());
				}

				@Override
				public void modifiedService(
					ServiceReference<PushNotificationsSender> serviceReference,
					ResourceBundleLoader resourceBundleLoader) {
				}

				@Override
				public void removedService(
					ServiceReference<PushNotificationsSender> serviceReference,
					ResourceBundleLoader resourceBundleLoader) {
				}

			});
	}

}