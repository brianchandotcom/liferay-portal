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

import com.liferay.notification.internal.term.evaluator.DefaultNotificationTermEvaluator;
import com.liferay.notification.term.evaluator.NotificationTermEvaluator;
import com.liferay.notification.term.evaluator.NotificationTermEvaluatorTracker;
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
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gustavo Lima
 */
@Component(service = NotificationTermEvaluatorTracker.class)
public class NotificationTermEvaluatorTrackerImpl
	implements NotificationTermEvaluatorTracker {

	@Override
	public List<NotificationTermEvaluator> getNotificationTermEvaluators(
		String className) {

		return _getNotificationTermEvaluators(className, _serviceTrackerMap);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, NotificationTermEvaluator.class, "class.name",
			ServiceTrackerCustomizerFactory.serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private List<NotificationTermEvaluator> _getNotificationTermEvaluators(
		String key,
		ServiceTrackerMap
			<String,
			 List
				 <ServiceTrackerCustomizerFactory.ServiceWrapper
					 <NotificationTermEvaluator>>> serviceTrackerMap) {

		if (key == null) {
			return Collections.singletonList(_defaultNotificationTermEvaluator);
		}

		List
			<ServiceTrackerCustomizerFactory.ServiceWrapper
				<NotificationTermEvaluator>> notificationTermEvaluatorWrappers =
					serviceTrackerMap.getService(key);

		if (notificationTermEvaluatorWrappers == null) {
			return Collections.singletonList(_defaultNotificationTermEvaluator);
		}

		List<NotificationTermEvaluator> notificationTermEvaluators =
			new ArrayList<>();

		for (ServiceTrackerCustomizerFactory.ServiceWrapper
				<NotificationTermEvaluator> tableActionProviderServiceWrapper :
					notificationTermEvaluatorWrappers) {

			notificationTermEvaluators.add(
				tableActionProviderServiceWrapper.getService());
		}

		return notificationTermEvaluators;
	}

	@Reference
	private DefaultNotificationTermEvaluator _defaultNotificationTermEvaluator;

	private ServiceTrackerMap
		<String,
		 List
			 <ServiceTrackerCustomizerFactory.ServiceWrapper
				 <NotificationTermEvaluator>>> _serviceTrackerMap;

}