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

package com.liferay.on.demand.admin.internal.scheduler;

import com.liferay.on.demand.admin.internal.configuration.OnDemandAdminConfiguration;
import com.liferay.on.demand.admin.manager.OnDemandAdminManager;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.util.Time;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	configurationPid = "com.liferay.on.demand.admin.internal.configuration.OnDemandAdminConfiguration",
	service = SchedulerJobConfiguration.class
)
public class OnDemandAdminCleanerSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> {
			int cleanUpInterval = _onDemandAdminConfiguration.cleanUpInterval();

			_onDemandAdminManager.cleanUpOnDemandAdminUsers(
				new Date(
					System.currentTimeMillis() -
						(Time.HOUR * cleanUpInterval)));
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_onDemandAdminConfiguration.cleanUpInterval(), TimeUnit.HOUR);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_onDemandAdminConfiguration = ConfigurableUtil.createConfigurable(
			OnDemandAdminConfiguration.class, properties);
	}

	private volatile OnDemandAdminConfiguration _onDemandAdminConfiguration;

	@Reference
	private OnDemandAdminManager _onDemandAdminManager;

}