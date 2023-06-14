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

import com.liferay.antivirus.async.store.configuration.AntivirusAsyncConfiguration;
import com.liferay.antivirus.async.store.constants.AntivirusAsyncConstants;
import com.liferay.antivirus.async.store.constants.AntivirusAsyncDestinationNames;
import com.liferay.antivirus.async.store.retry.AntivirusAsyncRetryScheduler;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.util.TransientValue;

import java.time.Instant;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.antivirus.async.store.configuration.AntivirusAsyncConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = AntivirusAsyncRetryScheduler.class
)
public class AntivirusAsyncRetrySchedulerImpl
	implements AntivirusAsyncRetryScheduler {

	@Override
	public void schedule(Message message) {
		try {
			_schedule(message);
		}
		catch (SchedulerException schedulerException) {
			ReflectionUtil.throwException(schedulerException);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		AntivirusAsyncConfiguration antivirusAsyncConfiguration =
			ConfigurableUtil.createConfigurable(
				AntivirusAsyncConfiguration.class, properties);

		_retryCronExpression =
			antivirusAsyncConfiguration.retryCronExpression();
	}

	private Trigger _createTrigger(String jobName) {
		Instant now = Instant.now();

		return _triggerFactory.createTrigger(
			jobName, AntivirusAsyncConstants.SCHEDULER_GROUP_NAME_ANTIVIRUS,
			Date.from(now.plusSeconds(10)), null, _retryCronExpression);
	}

	private void _schedule(Message message) throws SchedulerException {
		String jobName = message.getString("jobName");

		SchedulerResponse schedulerResponse =
			_schedulerEngineHelper.getScheduledJob(
				jobName, AntivirusAsyncConstants.SCHEDULER_GROUP_NAME_ANTIVIRUS,
				StorageType.PERSISTED);

		if (schedulerResponse != null) {
			return;
		}

		Trigger trigger = _createTrigger(jobName);

		// Avoid a log message reporting "Unable to deserialize
		// com.liferay.portal.kernel.util.TransientValue"

		Map<String, Object> map = message.getValues();

		Set<Map.Entry<String, Object>> entrySet = map.entrySet();

		Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();

			if (entry.getValue() instanceof TransientValue) {
				iterator.remove();
			}
		}

		_schedulerEngineHelper.schedule(
			trigger, StorageType.PERSISTED, trigger.getJobName(),
			AntivirusAsyncDestinationNames.ANTIVIRUS, message);
	}

	private volatile String _retryCronExpression;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}