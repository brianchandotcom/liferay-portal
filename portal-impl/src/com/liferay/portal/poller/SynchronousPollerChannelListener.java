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

package com.liferay.portal.poller;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.ChannelException;
import com.liferay.portal.kernel.notifications.ChannelHubManagerUtil;
import com.liferay.portal.kernel.notifications.ChannelListener;
import com.liferay.portal.kernel.notifications.NotificationEvent;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Edward Han
 */
public class SynchronousPollerChannelListener implements ChannelListener {

	@Override
	public void channelListenerRemoved(long channelId) {
		_countDownLatch.countDown();
	}

	public String getNotificationEvents(
			long companyId, long userId,
			JSONObject pollerResponseHeaderJSONObject, long timeout)
		throws ChannelException {

		try {
			_countDownLatch.await(timeout, TimeUnit.MILLISECONDS);
		}
		catch (InterruptedException ie) {
		}

		List<NotificationEvent> notificationEvents =
			ChannelHubManagerUtil.fetchNotificationEvents(
				companyId, userId, true);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		jsonArray.put(pollerResponseHeaderJSONObject);

		for (NotificationEvent notificationEvent : notificationEvents) {
			jsonArray.put(notificationEvent.toJSONObject());
		}

		return jsonArray.toString();
	}

	@Override
	public void notificationEventsAvailable(long channelId) {
		_countDownLatch.countDown();
	}

	private final CountDownLatch _countDownLatch = new CountDownLatch(1);

}