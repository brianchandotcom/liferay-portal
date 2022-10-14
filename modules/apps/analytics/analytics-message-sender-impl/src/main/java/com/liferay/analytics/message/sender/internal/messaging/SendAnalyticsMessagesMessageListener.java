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

package com.liferay.analytics.message.sender.internal.messaging;

import com.liferay.analytics.message.sender.client.AnalyticsMessageSenderClient;
import com.liferay.analytics.message.sender.constants.AnalyticsMessagesDestinationNames;
import com.liferay.analytics.message.sender.constants.AnalyticsMessagesProcessorCommand;
import com.liferay.analytics.message.storage.model.AnalyticsMessage;
import com.liferay.analytics.message.storage.service.AnalyticsMessageLocalService;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationTracker;
import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.nio.charset.StandardCharsets;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(
	property = "destination.name=" + AnalyticsMessagesDestinationNames.ANALYTICS_MESSAGES_PROCESSOR,
	service = MessageListener.class
)
public class SendAnalyticsMessagesMessageListener extends BaseMessageListener {

	@Activate
	protected void activate() {
		Class<?> clazz = getClass();

		String className = clazz.getName();

		Trigger trigger = _triggerFactory.createTrigger(
			className, className, null, null, 1, TimeUnit.HOUR);

		SchedulerEntry schedulerEntry = new SchedulerEntryImpl(
			className, trigger);

		_schedulerEngineHelper.register(
			this, schedulerEntry, DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		if (_skipProcess(message)) {
			return;
		}

		_companyLocalService.forEachCompanyId(companyId -> _process(companyId));
	}

	@Override
	protected void doReceive(Message message, long companyId) throws Exception {
		if (_skipProcess(message)) {
			return;
		}

		_process(companyId);
	}

	private void _process(long companyId) throws Exception {
		while (true) {
			List<AnalyticsMessage> analyticsMessages =
				_analyticsMessageLocalService.getAnalyticsMessages(
					companyId, 0, _BATCH_SIZE);

			if (analyticsMessages.isEmpty()) {
				if (_log.isInfoEnabled()) {
					_log.info("Finished processing analytics messages");
				}

				return;
			}

			JSONArray jsonArray = _jsonFactory.createJSONArray();

			for (AnalyticsMessage analyticsMessage : analyticsMessages) {
				String json = new String(
					StreamUtil.toByteArray(
						_analyticsMessageLocalService.openBodyInputStream(
							analyticsMessage.getAnalyticsMessageId())),
					StandardCharsets.UTF_8);

				jsonArray.put(_jsonFactory.createJSONObject(json));
			}

			try {
				_analyticsMessageSenderClient.send(
					jsonArray.toString(), companyId);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Sent " + jsonArray.length() + " analytics messages");
				}
			}
			catch (Exception exception) {
				_log.error(
					"Unable to send analytics messages for company " +
						companyId,
					exception);
			}

			_analyticsMessageLocalService.deleteAnalyticsMessages(
				analyticsMessages);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Deleted " + analyticsMessages.size() +
						" analytics messages");
			}
		}
	}

	private boolean _skipProcess(Message message) {
		if (GetterUtil.getBoolean(PropsUtil.get("feature.flag.LRAC-10632")) ||
			!_analyticsConfigurationTracker.isActive()) {

			return true;
		}

		AnalyticsMessagesProcessorCommand analyticsMessagesProcessorCommand =
			(AnalyticsMessagesProcessorCommand)message.get("command");

		if ((analyticsMessagesProcessorCommand != null) &&
			(analyticsMessagesProcessorCommand !=
				AnalyticsMessagesProcessorCommand.SEND)) {

			return true;
		}

		return false;
	}

	private static final int _BATCH_SIZE = 100;

	private static final Log _log = LogFactoryUtil.getLog(
		SendAnalyticsMessagesMessageListener.class);

	@Reference
	private AnalyticsConfigurationTracker _analyticsConfigurationTracker;

	@Reference
	private AnalyticsMessageLocalService _analyticsMessageLocalService;

	@Reference
	private AnalyticsMessageSenderClient _analyticsMessageSenderClient;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}