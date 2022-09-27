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

package com.liferay.notification.internal.term;

import com.liferay.notification.term.contributor.NotificationTermContributor;
import com.liferay.notification.term.contributor.NotificationTermContributorRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Gustavo Lima
 */
@Component(service = NotificationTermContributorRegistry.class)
public class NotificationTermContributorRegistryImpl
	implements NotificationTermContributorRegistry {

	@Override
	public List<NotificationTermContributor>
		getNotificationTermContributorsByNotificationTermContributorKey(
			String key) {

		return _getNotificationTermContributors(
			key, _serviceTrackerMapByNotificationTermContributorKey);
	}

	@Override
	public List<NotificationTermContributor>
		getNotificationTermContributorsByNotificationTypeKey(String key) {

		return _getNotificationTermContributors(
			key, _serviceTrackerMapByNotificationTypeKey);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMapByNotificationTermContributorKey =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, NotificationTermContributor.class,
				"notification.term.contributor.key",
				ServiceTrackerCustomizerFactory.
					<NotificationTermContributor>serviceWrapper(bundleContext));
		_serviceTrackerMapByNotificationTypeKey =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, NotificationTermContributor.class,
				"notification.type.key",
				ServiceTrackerCustomizerFactory.
					<NotificationTermContributor>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMapByNotificationTermContributorKey.close();

		_serviceTrackerMapByNotificationTypeKey.close();
	}

	private List<NotificationTermContributor> _getNotificationTermContributors(
		String key,
		ServiceTrackerMap
			<String,
			 List
				 <ServiceTrackerCustomizerFactory.ServiceWrapper
					 <NotificationTermContributor>>> serviceTrackerMap) {

		List
			<ServiceTrackerCustomizerFactory.ServiceWrapper
				<NotificationTermContributor>>
					notificationTermContributorWrappers =
						serviceTrackerMap.getService(key);

		if (notificationTermContributorWrappers == null) {
			return Collections.emptyList();
		}

		List<NotificationTermContributor> notificationTermContributors =
			new ArrayList<>();

		for (ServiceTrackerCustomizerFactory.ServiceWrapper
				<NotificationTermContributor>
					tableActionProviderServiceWrapper :
						notificationTermContributorWrappers) {

			notificationTermContributors.add(
				tableActionProviderServiceWrapper.getService());
		}

		return notificationTermContributors;
	}

	private ServiceTrackerMap
		<String,
		 List
			 <ServiceTrackerCustomizerFactory.ServiceWrapper
				 <NotificationTermContributor>>>
					_serviceTrackerMapByNotificationTermContributorKey;
	private ServiceTrackerMap
		<String,
		 List
			 <ServiceTrackerCustomizerFactory.ServiceWrapper
				 <NotificationTermContributor>>>
					_serviceTrackerMapByNotificationTypeKey;

}