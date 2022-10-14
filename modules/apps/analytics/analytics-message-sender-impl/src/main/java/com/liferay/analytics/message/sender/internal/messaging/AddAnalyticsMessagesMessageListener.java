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

import com.liferay.analytics.message.sender.constants.AnalyticsMessagesDestinationNames;
import com.liferay.analytics.message.sender.constants.AnalyticsMessagesProcessorCommand;
import com.liferay.analytics.message.sender.model.listener.EntityModelListener;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationTracker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ShardedModel;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(
	property = "destination.name=" + AnalyticsMessagesDestinationNames.ANALYTICS_MESSAGES_PROCESSOR,
	service = MessageListener.class
)
public class AddAnalyticsMessagesMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) {
		if (!_analyticsConfigurationTracker.isActive()) {
			return;
		}

		AnalyticsMessagesProcessorCommand analyticsMessagesProcessorCommand =
			(AnalyticsMessagesProcessorCommand)message.get("command");

		if (analyticsMessagesProcessorCommand !=
				AnalyticsMessagesProcessorCommand.ADD) {

			return;
		}

		String action = (String)message.get("action");
		EntityModelListener entityModelListener =
			(EntityModelListener)message.get("entityModelListener");

		List<? extends BaseModel> baseModels =
			(List<? extends BaseModel>)message.getPayload();

		for (BaseModel<?> baseModel : baseModels) {
			ShardedModel shardedModel = (ShardedModel)baseModel;

			entityModelListener.addAnalyticsMessage(
				action,
				entityModelListener.getAttributeNames(
					shardedModel.getCompanyId()),
				baseModel);
		}

		if (_log.isInfoEnabled()) {
			_log.info("Added " + baseModels.size() + " analytics messages");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddAnalyticsMessagesMessageListener.class);

	@Reference
	private AnalyticsConfigurationTracker _analyticsConfigurationTracker;

}