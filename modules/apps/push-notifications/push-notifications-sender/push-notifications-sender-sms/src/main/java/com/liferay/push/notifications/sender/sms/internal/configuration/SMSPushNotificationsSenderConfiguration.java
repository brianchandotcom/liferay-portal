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

package com.liferay.push.notifications.sender.sms.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Andrea Di Giorgi
 */
@ExtendedObjectClassDefinition(category = "other")
@Meta.OCD(
	id = "com.liferay.push.notifications.sender.sms.internal.configuration.SMSPushNotificationsSenderConfiguration",
	localization = "content/Language",
	name = "sms-push-notifications-sender-configuration-name"
)
public interface SMSPushNotificationsSenderConfiguration {

	@Meta.AD(name = "account.sid.name", required = false)
	public String accountSID();

	@Meta.AD(name = "authentication.token.name", required = false)
	public String authToken();

	@Meta.AD(name = "number.name", required = false)
	public String number();

	@Meta.AD(name = "status.callback.name", required = false)
	public String statusCallback();

}