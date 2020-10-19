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

package com.liferay.frontend.js.spa.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Bruno Basto
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	description = "spa-configuration-description",
	id = "com.liferay.frontend.js.spa.web.internal.configuration.SPAConfiguration",
	localization = "content/Language", name = "spa-configuration-name"
)
public @interface SPAConfiguration {

	@Meta.AD(
		deflt = "-1", description = "cache-expiration-time-description",
		name = "cache-expiration-time-name", required = false
	)
	public long cacheExpirationTime();

	@Meta.AD(
		deflt = ":not([target=\"_blank\"])|:not([data-senna-off])|:not([data-resource-href])",
		description = "navigation-exception-selectors-description",
		name = "navigation-exception-selectors-name", required = false
	)
	public String[] navigationExceptionSelectors();

	@Meta.AD(
		deflt = "0", description = "request-timeout-description",
		name = "request-timeout-name", required = false
	)
	public int requestTimeout();

	@Meta.AD(
		deflt = "30000", description = "user-notification-timeout-description",
		name = "user-notification-timeout-name", required = false
	)
	public int userNotificationTimeout();

}