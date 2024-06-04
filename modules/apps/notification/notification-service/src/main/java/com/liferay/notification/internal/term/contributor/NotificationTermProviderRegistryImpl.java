/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.term.contributor;

import com.liferay.notification.term.contributor.NotificationTermProviderRegistry;
import com.liferay.notification.term.contributor.NotificationTermProvider;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Luca Pellizzon
 */
@Component(service = NotificationTermProviderRegistry.class)
public class NotificationTermProviderRegistryImpl
	implements NotificationTermProviderRegistry {

	@Override
	public List<NotificationTermProvider> getNotificationTermProviders(
		String className) {

		List<NotificationTermProvider> notificationTermProviders =
			new ArrayList<>();

		List
			<ServiceTrackerCustomizerFactory.ServiceWrapper
				<NotificationTermProvider>> notificationTermEvaluatorWrappers =
					_serviceTrackerMap.getService(className);

		for (ServiceTrackerCustomizerFactory.ServiceWrapper
				<NotificationTermProvider> tableActionProviderServiceWrapper :
					notificationTermEvaluatorWrappers) {

			notificationTermProviders.add(
				tableActionProviderServiceWrapper.getService());
		}

		return notificationTermProviders;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, NotificationTermProvider.class, "class.name",
			ServiceTrackerCustomizerFactory.serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap
		<String,
		 List
			 <ServiceTrackerCustomizerFactory.ServiceWrapper
				 <NotificationTermProvider>>> _serviceTrackerMap;

}