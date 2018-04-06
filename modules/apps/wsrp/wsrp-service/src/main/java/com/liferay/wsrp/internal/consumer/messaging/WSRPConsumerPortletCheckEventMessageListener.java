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

package com.liferay.wsrp.internal.consumer.messaging;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.wsrp.configuration.WSRPGroupServiceConfiguration;
import com.liferay.wsrp.service.WSRPConsumerPortletLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 * @author Peter Fellwock
 */
@Component(
	configurationPid = "com.liferay.wsrp.configuration.WSRPGroupServiceConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = "destination.name=" + DestinationNames.WSRP,
	service = MessageListener.class
)
public class WSRPConsumerPortletCheckEventMessageListener
	extends BaseMessageListener {

	@Activate
	protected void activate(Map<String, Object> properties) {
		WSRPGroupServiceConfiguration wsrpGroupServiceConfiguration =
			ConfigurableUtil.createConfigurable(
				WSRPGroupServiceConfiguration.class, properties);

		Class<?> clazz = getClass();

		String className = clazz.getName();

		Trigger trigger = _triggerFactory.createTrigger(
			className, className, null, null,
			wsrpGroupServiceConfiguration.failedConsumersCheckInterval(),
			TimeUnit.SECOND);

		SchedulerEntry schedulerEntry = new SchedulerEntryImpl(
			className, trigger);

		_schedulerEngineHelper.register(
			this, schedulerEntry, DestinationNames.SCHEDULER_DISPATCH);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		_wsrpConsumerPortletLocalService.initFailedWSRPConsumerPortlets();
	}

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

	@Reference
	private WSRPConsumerPortletLocalService _wsrpConsumerPortletLocalService;

}