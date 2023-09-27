/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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