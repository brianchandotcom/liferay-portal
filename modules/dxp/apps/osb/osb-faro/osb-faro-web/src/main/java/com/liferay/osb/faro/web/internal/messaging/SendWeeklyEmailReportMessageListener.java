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

package com.liferay.osb.faro.web.internal.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;

import java.util.Date;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Rachael Koestartyo
 */
@Component(
	property = "destination.name=" + DestinationNames.SCHEDULER_DISPATCH,
	service = MessageListener.class
)
public class SendWeeklyEmailReportMessageListener
	extends BaseEmailReportMessageListener {

	@Activate
	protected void activate() {
		try {
			Class<?> clazz = getClass();

			_trigger = triggerFactory.createTrigger(
				clazz.getName(), clazz.getName(), new Date(), null,
				"0 0 0 ? * MON");

			schedulerEngineHelper.schedule(
				_trigger, StorageType.PERSISTED, null,
				DestinationNames.SCHEDULER_DISPATCH, null);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	@Deactivate
	protected void deactivate() {
		try {
			if (_trigger == null) {
				return;
			}

			schedulerEngineHelper.unschedule(
				_trigger.getJobName(), _trigger.getGroupName(),
				StorageType.PERSISTED);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	@Override
	protected String getFrequency() {
		return "weekly";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SendWeeklyEmailReportMessageListener.class);

	private Trigger _trigger;

}