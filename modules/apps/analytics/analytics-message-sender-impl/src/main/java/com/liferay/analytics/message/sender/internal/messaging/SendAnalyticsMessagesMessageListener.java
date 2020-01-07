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
import com.liferay.analytics.message.storage.model.AnalyticsMessage;
import com.liferay.analytics.message.storage.service.AnalyticsMessageLocalService;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationTracker;
import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;

import java.nio.charset.StandardCharsets;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(
	immediate = true, service = SendAnalyticsMessagesMessageListener.class
)
public class SendAnalyticsMessagesMessageListener extends BaseMessageListener {

	@Activate
	@Modified
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
		for (long companyId : _analyticsMessageLocalService.getCompanyIds()) {
			_process(companyId);
		}
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private void _process(long companyId) throws Exception {
		AnalyticsConfiguration analyticsConfiguration =
			_analyticsConfigurationTracker.getAnalyticsConfiguration(companyId);

		if (analyticsConfiguration.liferayAnalyticsEndpointURL() == null) {
			return;
		}

		while (true) {
			List<AnalyticsMessage> analyticsMessages =
				_analyticsMessageLocalService.getAnalyticsMessages(
					companyId, 0, _BATCH_SIZE);

			if (analyticsMessages.isEmpty()) {
				return;
			}

			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

			for (AnalyticsMessage analyticsMessage : analyticsMessages) {
				String json = new String(
					StreamUtil.toByteArray(
						_analyticsMessageLocalService.openBodyInputStream(
							analyticsMessage.getAnalyticsMessageId())),
					StandardCharsets.UTF_8);

				jsonArray.put(JSONFactoryUtil.createJSONObject(json));
			}

			_analyticsMessageSenderClient.send(jsonArray.toString(), companyId);

			_analyticsMessageLocalService.deleteAnalyticsMessages(
				analyticsMessages);
		}
	}

	private static final int _BATCH_SIZE = 100;

	@Reference
	private AnalyticsConfigurationTracker _analyticsConfigurationTracker;

	@Reference
	private AnalyticsMessageLocalService _analyticsMessageLocalService;

	@Reference
	private AnalyticsMessageSenderClient _analyticsMessageSenderClient;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}