/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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