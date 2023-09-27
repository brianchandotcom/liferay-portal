/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.term.evaluator;

import com.liferay.notification.term.evaluator.NotificationTermEvaluator;
import com.liferay.notification.term.evaluator.NotificationTermEvaluatorTracker;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

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

	private static final NotificationTermEvaluator
		_defaultNotificationTermEvaluator = (context, object, termName) -> {
			if (!(object instanceof Map)) {
				return termName;
			}

			Map<String, String> termValues = (Map<String, String>)object;

			return termValues.get(termName);
		};

	private ServiceTrackerMap
		<String,
		 List
			 <ServiceTrackerCustomizerFactory.ServiceWrapper
				 <NotificationTermEvaluator>>> _serviceTrackerMap;

}