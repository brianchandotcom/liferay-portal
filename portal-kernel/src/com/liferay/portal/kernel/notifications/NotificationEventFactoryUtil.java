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

package com.liferay.portal.kernel.notifications;

import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Edward Han
 * @author Raymond Augé
 */
public class NotificationEventFactoryUtil {

	public static NotificationEvent createNotificationEvent(
		long timestamp, String type, JSONObject payloadJSONObject) {

		return getNotificationEventFactory().createNotificationEvent(
			timestamp, type, payloadJSONObject);
	}

	public static NotificationEventFactory getNotificationEventFactory() {
		return _notificationEventFactory;
	}

	public void setNotificationEventFactory(
		NotificationEventFactory notificationEventFactory) {

		_notificationEventFactory = notificationEventFactory;
	}

	private static NotificationEventFactory _notificationEventFactory;

}