/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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