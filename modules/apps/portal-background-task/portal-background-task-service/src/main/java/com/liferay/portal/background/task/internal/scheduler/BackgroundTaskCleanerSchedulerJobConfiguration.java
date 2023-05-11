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

package com.liferay.portal.background.task.internal.scheduler;

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.background.task.internal.configuration.BackgroundTaskCleanerConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dante Wang
 */
@Component(
	configurationPid = "com.liferay.portal.background.task.internal.configuration.BackgroundTaskCleanerConfiguration",
	service = SchedulerJobConfiguration.class
)
public class BackgroundTaskCleanerSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> {
			if (!_clusterMasterExecutor.isEnabled() ||
				_clusterMasterExecutor.isMaster()) {

				_backgroundTaskManager.cleanUpBackgroundTasks();
			}
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_backgroundTaskCleanerConfiguration.interval(), TimeUnit.MINUTE);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_backgroundTaskCleanerConfiguration =
			ConfigurableUtil.createConfigurable(
				BackgroundTaskCleanerConfiguration.class, properties);
	}

	private volatile BackgroundTaskCleanerConfiguration
		_backgroundTaskCleanerConfiguration;

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private ClusterMasterExecutor _clusterMasterExecutor;

}