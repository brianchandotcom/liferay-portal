/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.term.contributor;

import com.liferay.notification.term.contributor.NotificationTermContributor;
import com.liferay.notification.term.contributor.NotificationTermContributorTracker;
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
@Component(service = NotificationTermContributorTracker.class)
public class NotificationTermContributorTrackerImpl
	implements NotificationTermContributorTracker {

	@Override
	public List<NotificationTermContributor> getNotificationTermContributors(
		String className) {

		List<NotificationTermContributor> notificationTermContributors =
			new ArrayList<>();

		List
			<ServiceTrackerCustomizerFactory.ServiceWrapper
				<NotificationTermContributor>>
					notificationTermEvaluatorWrappers =
						_serviceTrackerMap.getService(className);

		for (ServiceTrackerCustomizerFactory.ServiceWrapper
				<NotificationTermContributor>
					tableActionProviderServiceWrapper :
						notificationTermEvaluatorWrappers) {

			notificationTermContributors.add(
				tableActionProviderServiceWrapper.getService());
		}

		return notificationTermContributors;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, NotificationTermContributor.class, "class.name",
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
				 <NotificationTermContributor>>> _serviceTrackerMap;

}