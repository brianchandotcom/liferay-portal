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

package com.liferay.notifications.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author István András Dézsi
 */
@ExtendedObjectClassDefinition(category = "user-notifications")
@Meta.OCD(
	id = "com.liferay.notifications.internal.configuration.UserNotificationConfiguration",
	localization = "content/Language",
	name = "user-notification-configuration-name"
)
public interface UserNotificationConfiguration {

	@Meta.AD(
		deflt = "-1",
		description = "user-notification-event-check-interval-key-description",
		name = "user-notification-event-check-interval", required = false
	)
	public int userNotificationEventCheckInterval();

	@Meta.AD(
		description = "user-notification-event-days-limit-key-description",
		name = "user-notification-event-days-limit", required = false
	)
	public int userNotificationEventDaysLimit();

}