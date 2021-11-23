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

package com.liferay.antivirus.async.store.internal.retry;

import com.liferay.antivirus.async.store.constants.AntivirusAsyncConstants;
import com.liferay.antivirus.async.store.events.AntivirusAsyncEvent;
import com.liferay.antivirus.async.store.retry.AntivirusAsyncRetryScheduler;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;

import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.antivirus.async.store.configuration.AntivirusAsyncConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	property = {
		"antivirus.async.event=MISSING",
		"antivirus.async.event=PROCESSING_ERROR",
		"antivirus.async.event=SIZE_EXCEEDED", "antivirus.async.event=SUCCESS",
		"antivirus.async.event=VIRUS_FOUND"
	},
	service = Consumer.class
)
public class AntivirusAsyncRetryEventListener implements Consumer<Message> {

	@Override
	public void accept(Message message) {
		AntivirusAsyncEvent antivirusAsyncEvent =
			(AntivirusAsyncEvent)message.get("antivirusAsyncEvent");

		if ((antivirusAsyncEvent == AntivirusAsyncEvent.MISSING) ||
			(antivirusAsyncEvent == AntivirusAsyncEvent.SIZE_EXCEEDED) ||
			(antivirusAsyncEvent == AntivirusAsyncEvent.SUCCESS) ||
			(antivirusAsyncEvent == AntivirusAsyncEvent.VIRUS_FOUND)) {

			_deleteJob(message);
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.PROCESSING_ERROR) {
			_antivirusAsyncRetryScheduler.schedule(message);
		}
	}

	private void _deleteJob(Message message) {
		try {
			SchedulerResponse schedulerResponse =
				_schedulerEngineHelper.getScheduledJob(
					message.getString("jobName"),
					AntivirusAsyncConstants.ANTIVIRUS_GROUP_NAME,
					StorageType.PERSISTED);

			if (schedulerResponse != null) {
				_schedulerEngineHelper.delete(
					schedulerResponse.getJobName(),
					schedulerResponse.getGroupName(),
					schedulerResponse.getStorageType());
			}
		}
		catch (SchedulerException schedulerException) {
			ReflectionUtil.throwException(schedulerException);
		}
	}

	@Reference
	private AntivirusAsyncRetryScheduler _antivirusAsyncRetryScheduler;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

}