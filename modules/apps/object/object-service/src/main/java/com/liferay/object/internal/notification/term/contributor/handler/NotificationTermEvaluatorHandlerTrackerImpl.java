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

package com.liferay.object.internal.notification.term.contributor.handler;

import com.liferay.object.notification.term.evaluator.handler.NotificationTermEvaluatorHandler;
import com.liferay.object.notification.term.evaluator.handler.NotificationTermEvaluatorHandlerTracker;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Paulo Albuquerque
 */
@Component(service = NotificationTermEvaluatorHandlerTracker.class)
public class NotificationTermEvaluatorHandlerTrackerImpl
	implements NotificationTermEvaluatorHandlerTracker {

	@Override
	public NotificationTermEvaluatorHandler getNotificationTermEvaluatorHandler(
		String key) {

		return _serviceTrackerMap.getService(key);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, NotificationTermEvaluatorHandler.class,
			"notification.term.evaluator.handler.type");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, NotificationTermEvaluatorHandler>
		_serviceTrackerMap;

}