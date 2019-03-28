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

package com.liferay.subscription.internal.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.subscription.service.SubscriptionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "destination.name=" + DestinationNames.SUBSCRIPTION_CLEAN_UP,
	service = MessageListener.class
)
public class CleanUpSubscriptionMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		long[] groupIds = (long[])message.get("groupIds");

		if (groupIds == null) {
			long[] userIds = (long[])message.get("userIds");

			long groupId = (Long)message.get("groupId");

			for (long userId : userIds) {
				_subscriptionLocalService.deleteSubscriptions(userId, groupId);
			}
		}
		else {
			long userId = (Long)message.get("userId");

			for (long groupId : groupIds) {
				_subscriptionLocalService.deleteSubscriptions(userId, groupId);
			}
		}
	}

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}