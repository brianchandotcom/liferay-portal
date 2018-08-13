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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.kernel.util.Time;

import org.apache.commons.lang.time.StopWatch;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "destination.name=" + DestinationNames.SUBSCRIPTION_SENDER,
	service = MessageListener.class
)
public class SubscriptionSenderMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		SubscriptionSender subscriptionSender =
			(SubscriptionSender)message.getPayload();

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Sending notifications for {mailId=" +
					subscriptionSender.getMailId() + "}");
		}

		subscriptionSender.flushNotifications();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Sending notifications for {mailId=",
					subscriptionSender.getMailId(), "} completed in ",
					stopWatch.getTime() / Time.SECOND, " seconds"));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SubscriptionSenderMessageListener.class);

}