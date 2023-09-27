/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		description = "user-notification-event-check-interval-key-description",
		name = "user-notification-event-check-interval"
	)
	public int userNotificationEventCheckInterval();

	@Meta.AD(
		description = "user-notification-event-days-limit-key-description",
		name = "user-notification-event-days-limit"
	)
	public int userNotificationEventDaysLimit();

}