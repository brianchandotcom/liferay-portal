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

package com.liferay.portal.scheduler.single.internal;

import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSenderFactory;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.scheduler.BaseSchedulerEngineConfigurator;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = SingleSchedulerEngineConfigurator.class)
public class SingleSchedulerEngineConfigurator
	extends BaseSchedulerEngineConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		SchedulerEngine schedulerEngine = createSchedulerEngineProxy();

		_schedulerEngineServiceRegistration = registerSchedulerEngine(
			bundleContext, schedulerEngine);
	}

	@Deactivate
	protected void deactivate() {
		if (_schedulerEngineServiceRegistration != null) {
			_schedulerEngineServiceRegistration.unregister();
		}
	}

	@Reference(unbind = "-")
	protected void setSingleDestinationMessageSenderFactory(
		SingleDestinationMessageSenderFactory
			singleDestinationMessageSenderFactory) {
	}

	private volatile ServiceRegistration<SchedulerEngine>
		_schedulerEngineServiceRegistration;

}