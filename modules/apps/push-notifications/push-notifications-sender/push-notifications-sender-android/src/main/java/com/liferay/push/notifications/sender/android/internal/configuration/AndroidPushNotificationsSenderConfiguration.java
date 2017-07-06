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

package com.liferay.push.notifications.sender.android.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Andrea Di Giorgi
 */
@ExtendedObjectClassDefinition(category = "other")
@Meta.OCD(
	id = "com.liferay.push.notifications.sender.android.internal.configuration.AndroidPushNotificationsSenderConfiguration",
	localization = "content/Language",
	name = "android-push-notifications-sender-configuration-name"
)
public interface AndroidPushNotificationsSenderConfiguration {

	@Meta.AD(
		description = "android-api-key-description",
		name = "android-api-key-name", required = false
	)
	public String apiKey();

	@Meta.AD(
		description = "retries-description", name = "retries-name",
		required = false
	)
	public int retries();

}