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

import com.liferay.osb.faro.web.internal.constants.FaroMessageDestinationNames;
import com.liferay.osb.faro.web.internal.messaging.destination.creator.DestinationCreator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;

import java.util.Date;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(
	property = "destination.name=" + FaroMessageDestinationNames.FARO_MONTHLY_EMAIL_REPORT_MESSAGE_PROCESSOR,
	service = MessageListener.class
)
public class SendMonthlyEmailReportMessageListener
	extends BaseEmailReportMessageListener {

	@Activate
	protected void activate(BundleContext bundleContext) {
		try {
			_destinationCreator.createDestination(
				bundleContext, _destinationFactory,
				FaroMessageDestinationNames.
					FARO_MONTHLY_EMAIL_REPORT_MESSAGE_PROCESSOR);

			Class<?> clazz = getClass();

			_trigger = triggerFactory.createTrigger(
				clazz.getName(), clazz.getName(), new Date(), null,
				"0 0 0 1 * ?");

			schedulerEngineHelper.schedule(
				_trigger, StorageType.PERSISTED, null,
				FaroMessageDestinationNames.
					FARO_MONTHLY_EMAIL_REPORT_MESSAGE_PROCESSOR,
				null);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	@Deactivate
	protected void deactivate() {
		try {
			if (_destinationCreator != null) {
				_destinationCreator.removeDestination();

				_destinationCreator = null;
			}

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
		return "monthly";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SendMonthlyEmailReportMessageListener.class);

	private DestinationCreator _destinationCreator = new DestinationCreator();

	@Reference
	private DestinationFactory _destinationFactory;

	private Trigger _trigger;

}