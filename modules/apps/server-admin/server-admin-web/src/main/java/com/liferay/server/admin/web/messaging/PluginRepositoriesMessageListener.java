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

package com.liferay.server.admin.web.messaging;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.messaging.BaseSchedulerEntryMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.server.admin.web.configuration.PluginRepositoriesConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 * @author Philip Jones
 */
@Component(immediate = true, service = PluginRepositoriesMessageListener.class)
public class PluginRepositoriesMessageListener
	extends BaseSchedulerEntryMessageListener {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		PluginRepositoriesConfiguration pluginRepositoriesConfiguration =
			Configurable.createConfigurable(
				PluginRepositoriesConfiguration.class, properties);

		if (!pluginRepositoriesConfiguration.enabled()) {
			return;
		}

		schedulerEntryImpl.setTrigger(
			TriggerFactoryUtil.createTrigger(
				getEventListenerClass(), getEventListenerClass(),
				pluginRepositoriesConfiguration.interval(),
				pluginRepositoriesConfiguration.timeUnit()));

		_schedulerEngineHelper.register(
			this, schedulerEntryImpl, DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		SearchEngineHelperUtil.initialize(CompanyConstants.SYSTEM);

		PluginPackageUtil.reloadRepositories();
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

	private SchedulerEngineHelper _schedulerEngineHelper;

}