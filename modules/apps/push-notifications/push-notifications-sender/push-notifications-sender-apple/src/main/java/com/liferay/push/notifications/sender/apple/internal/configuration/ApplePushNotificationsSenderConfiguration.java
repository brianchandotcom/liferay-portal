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

package com.liferay.push.notifications.sender.apple.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Andrea Di Giorgi
 */
@ExtendedObjectClassDefinition(category = "other")
@Meta.OCD(
	id = "com.liferay.push.notifications.sender.apple.internal.configuration.ApplePushNotificationsSenderConfiguration",
	localization = "content/Language",
	name = "apple-push-notifications-sender-configuration-name"
)
public interface ApplePushNotificationsSenderConfiguration {

	@Meta.AD(
		description = "certificate-password-description",
		name = "certificate-password-name", required = false
	)
	public String certificatePassword();

	@Meta.AD(
		description = "certificate-path-description",
		name = "certificate-path-name", required = false
	)
	public String certificatePath();

	@Meta.AD(
		description = "sandbox-description", name = "sandbox-name",
		required = false
	)
	public boolean sandbox();

}