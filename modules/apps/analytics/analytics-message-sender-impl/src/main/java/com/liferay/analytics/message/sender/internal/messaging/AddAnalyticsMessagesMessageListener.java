/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.messaging;

import com.liferay.analytics.message.sender.constants.AnalyticsMessagesDestinationNames;
import com.liferay.analytics.message.sender.constants.AnalyticsMessagesProcessorCommand;
import com.liferay.analytics.message.sender.model.listener.EntityModelListener;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationRegistry;
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
		if (!_analyticsConfigurationRegistry.isActive()) {
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
	private AnalyticsConfigurationRegistry _analyticsConfigurationRegistry;

}