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
import com.liferay.portal.kernel.notifications.ChannelException;
import com.liferay.portal.kernel.notifications.ChannelHubManagerUtil;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.NotificationEventFactoryUtil;
import com.liferay.portal.kernel.notifications.UnknownChannelException;
import com.liferay.portal.kernel.poller.PollerHeader;
import com.liferay.portal.kernel.poller.PollerResponse;
import com.liferay.portal.kernel.poller.PollerResponseHandler;

/**
 * @author Edward Han
 */
public class NotificationsPollerResponseHandler
	implements PollerResponseHandler {

	@Override
	public void handle(PollerResponse pollerResponse) {
		if (pollerResponse.isEmpty()) {
			return;
		}

		PollerHeader pollerHeader = pollerResponse.getPollerHeader();

		NotificationEvent notificationEvent =
			NotificationEventFactoryUtil.createNotificationEvent(
				System.currentTimeMillis(),
				NotificationsPollerResponseHandler.class.getName(),
				pollerResponse.toJSONObject());

		try {
			ChannelHubManagerUtil.sendNotificationEvent(
				pollerHeader.getCompanyId(), pollerHeader.getUserId(),
				notificationEvent);
		}
		catch (UnknownChannelException unknownChannelException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to complete processing because user session ended",
					unknownChannelException);
			}
		}
		catch (ChannelException channelException) {
			_log.error(channelException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationsPollerResponseHandler.class);

}