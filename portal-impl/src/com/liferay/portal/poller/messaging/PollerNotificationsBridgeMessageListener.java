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

package com.liferay.portal.poller.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.notifications.ChannelHubManagerUtil;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.NotificationEventFactoryUtil;
import com.liferay.portal.kernel.notifications.UnknownChannelException;
import com.liferay.portal.kernel.poller.PollerHeader;
import com.liferay.portal.kernel.poller.PollerResponse;

/**
 * @author Edward Han
 */
public class PollerNotificationsBridgeMessageListener
	extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		Object messagePayload = message.getPayload();

		if (!(messagePayload instanceof PollerResponse)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Received message with payload not of type PollerResponse");
			}

			return;
		}

		PollerResponse pollerResponse = (PollerResponse)messagePayload;

		if (pollerResponse.isEmpty()) {
			return;
		}

		PollerHeader pollerHeader = pollerResponse.getPollerHeader();

		NotificationEvent notificationEvent =
			NotificationEventFactoryUtil.createNotificationEvent(
				System.currentTimeMillis(),
				PollerNotificationsBridgeMessageListener.class.getName(),
				pollerResponse.toJSONObject());

		try {
			ChannelHubManagerUtil.sendNotificationEvent(
				pollerHeader.getCompanyId(), pollerHeader.getUserId(),
				notificationEvent);
		}
		catch (UnknownChannelException uce) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to complete processing because user session ended",
					uce);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PollerNotificationsBridgeMessageListener.class);

}