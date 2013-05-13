/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.notifications;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.NotificationEventFactory;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class NotificationEventFactoryImpl implements NotificationEventFactory {

	@Override
	public NotificationEvent createNotificationEvent(
		long timestamp, String type, JSONObject payloadJSONObject) {

		return new NotificationEvent(timestamp, type, payloadJSONObject);
	}

}