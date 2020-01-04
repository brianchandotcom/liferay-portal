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

import com.liferay.analytics.message.sender.constants.AnalyticsMessageDestinationNames;
import com.liferay.analytics.message.sender.model.EntityModelListener;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rachael Koestartyo
 */
@Component(
	immediate = true,
	property = "destination.name=" + AnalyticsMessageDestinationNames.ADD_ANALYTICS_MESSAGES_PROCESSOR,
	service = MessageListener.class
)
public class AddAnalyticsMessagesMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) {
		EntityModelListener entityModelListener =
			(EntityModelListener)message.get("entityModelListener");

		for (BaseModel baseModel :
				(List<? extends BaseModel>)message.getPayload()) {

			entityModelListener.addAnalyticsMessage(
				false, "update", entityModelListener.getAttributeNames(),
				baseModel);
		}
	}

}