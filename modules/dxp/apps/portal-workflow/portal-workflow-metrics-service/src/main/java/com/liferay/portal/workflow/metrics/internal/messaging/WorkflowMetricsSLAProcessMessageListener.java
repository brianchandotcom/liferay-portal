/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.internal.messaging;

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.workflow.metrics.internal.background.task.WorkflowMetricsSLAProcessBackgroundTaskHelper;
import com.liferay.portal.workflow.metrics.internal.configuration.WorkflowMetricsConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	configurationPid = "com.liferay.portal.workflow.metrics.internal.configuration.WorkflowMetricsConfiguration",
	service = SchedulerJobConfiguration.class
)
public class WorkflowMetricsSLAProcessMessageListener
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutor() {
		return () ->
			_workflowMetricsSLAProcessBackgroundTaskHelper.addBackgroundTasks(
				false);
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_workflowMetricsConfiguration.checkSLAJobInterval(),
			TimeUnit.MINUTE);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_workflowMetricsConfiguration = ConfigurableUtil.createConfigurable(
			WorkflowMetricsConfiguration.class, properties);
	}

	private WorkflowMetricsConfiguration _workflowMetricsConfiguration;

	@Reference
	private WorkflowMetricsSLAProcessBackgroundTaskHelper
		_workflowMetricsSLAProcessBackgroundTaskHelper;

}