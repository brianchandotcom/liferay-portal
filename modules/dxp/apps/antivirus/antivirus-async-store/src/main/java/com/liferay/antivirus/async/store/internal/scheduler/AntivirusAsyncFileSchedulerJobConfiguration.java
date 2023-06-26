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

package com.liferay.antivirus.async.store.internal.scheduler;

import com.liferay.antivirus.async.store.AntivirusScannerHelper;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(
	factory = "com.liferay.antivirus.async.store.internal.scheduler.AntivirusAsyncFileSchedulerJobConfiguration",
	service = SchedulerJobConfiguration.class
)
public class AntivirusAsyncFileSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> _antivirusScannerHelper.processMessage(_message);
	}

	public String getName() {
		return _jobName;
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		TriggerConfiguration triggerConfiguration =
			TriggerConfiguration.createTriggerConfiguration(
				_retryCronExpression);

		triggerConfiguration.setStartDate(
			new Date(
				System.currentTimeMillis() + TimeUnit.SECOND.toMillis(10)));

		return triggerConfiguration;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_jobName = (String)properties.get("jobName");
		_message = (Message)properties.get("message");
		_retryCronExpression = (String)properties.get("retryCronExpression");
	}

	@Reference
	private AntivirusScannerHelper _antivirusScannerHelper;

	private String _jobName;
	private Message _message;
	private String _retryCronExpression;

}