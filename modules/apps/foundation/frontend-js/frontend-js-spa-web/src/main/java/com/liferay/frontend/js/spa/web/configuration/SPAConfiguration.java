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

package com.liferay.frontend.js.spa.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Bruno Basto
 */
@ExtendedObjectClassDefinition(category = "foundation")
@Meta.OCD(
	id = "com.liferay.frontend.js.spa.web.configuration.SPAConfiguration",
	localization = "content/Language", name = "spa.configuration.name"
)
public interface SPAConfiguration {

	@Meta.AD(
		deflt = "-1", description = "cache.expiration.time.description",
		name = "cache.expiration.time.name", required = true
	)
	public String cacheExpirationTime();

	@Meta.AD(
		deflt = "${server-property://com.liferay.portal/javascript.single.page.application.timeout}",
		description = "request.timeout.description",
		name = "request.timeout.name", required = true
	)
	public String requestTimeout();

	@Meta.AD(
		deflt = "30000", description = "user.notification.timeout.description",
		name = "user.notification.timeout.name", required = true
	)
	public String userNotificationTimeout();

}