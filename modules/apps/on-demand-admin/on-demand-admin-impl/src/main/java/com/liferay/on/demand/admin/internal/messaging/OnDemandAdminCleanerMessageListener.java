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

package com.liferay.on.demand.admin.internal.messaging;

import com.liferay.on.demand.admin.internal.configuration.OnDemandAdminConfiguration;
import com.liferay.on.demand.admin.manager.OnDemandAdminManager;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.Time;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	configurationPid = "com.liferay.portal.instances.on.demand.admin.internal.configuration.OnDemandAdminConfiguration",
	service = MessageListener.class
)
public class OnDemandAdminCleanerMessageListener extends BaseMessageListener {

	@Activate
	protected void activate(Map<String, Object> properties) {
		_onDemandAdminConfiguration = ConfigurableUtil.createConfigurable(
			OnDemandAdminConfiguration.class, properties);

		int cleanUpInterval = _onDemandAdminConfiguration.cleanUpInterval();

		Trigger trigger = _triggerFactory.createTrigger(
			OnDemandAdminCleanerMessageListener.class.getName(),
			OnDemandAdminCleanerMessageListener.class.getName(),
			new Date(
				System.currentTimeMillis() + (cleanUpInterval * Time.HOUR)),
			null, cleanUpInterval, TimeUnit.HOUR);

		_schedulerEngineHelper.register(
			this,
			new SchedulerEntryImpl(
				OnDemandAdminCleanerMessageListener.class.getName(), trigger),
			DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		int cleanUpInterval = _onDemandAdminConfiguration.cleanUpInterval();

		_onDemandAdminManager.cleanUpOnDemandAdminUsers(
			new Date(
				System.currentTimeMillis() - (Time.HOUR * cleanUpInterval)));
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		deactivate();

		activate(properties);
	}

	private volatile OnDemandAdminConfiguration _onDemandAdminConfiguration;

	@Reference
	private OnDemandAdminManager _onDemandAdminManager;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}