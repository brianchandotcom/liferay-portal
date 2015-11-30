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

package com.liferay.blogs.web.messaging;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.blogs.configuration.BlogsConfiguration;
import com.liferay.portal.kernel.messaging.BaseSchedulerEntryMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;
import com.liferay.portlet.blogs.service.BlogsEntryLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zsolt Berentey
 */
@Component(
	configurationPid = "com.liferay.blogs.configuration.BlogsConfiguration",
	immediate = true, service = CheckEntryMessageListener.class
)
public class CheckEntryMessageListener
	extends BaseSchedulerEntryMessageListener {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_blogsConfiguration = Configurable.createConfigurable(
			BlogsConfiguration.class, properties);

		schedulerEntryImpl.setTrigger(
			TriggerFactoryUtil.createTrigger(
				getEventListenerClass(), getEventListenerClass(),
				_blogsConfiguration.entryCheckInterval(), TimeUnit.MINUTE));

		_schedulerEngineHelper.register(
			this, schedulerEntryImpl, DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		_blogsEntryLocalService.checkEntries();
	}

	@Reference(unbind = "-")
	protected void setBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
	}

	@Reference(
		target = "(destination.name=" + DestinationNames.SCHEDULER_DISPATCH + ")",
		unbind = "-"
	)
	protected void setDestination(Destination destination) {
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setSchedulerEngineHelper(
		SchedulerEngineHelper schedulerEngineHelper) {

		_schedulerEngineHelper = schedulerEngineHelper;
	}

	@Reference(unbind = "-")
	protected void setTriggerFactory(TriggerFactory triggerFactory) {
	}

	private volatile BlogsConfiguration _blogsConfiguration;
	private volatile BlogsEntryLocalService _blogsEntryLocalService;
	private volatile SchedulerEngineHelper _schedulerEngineHelper;

}