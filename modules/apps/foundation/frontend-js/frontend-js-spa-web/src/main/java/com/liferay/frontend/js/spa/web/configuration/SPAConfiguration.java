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

import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * @author Bruno Basto
 */
@ExtendedObjectClassDefinition(category = "foundation")
@ObjectClassDefinition(
	description = "spa.configuration.description",
	id = "com.liferay.frontend.js.spa.web.configuration.SPAConfiguration",
	localization = "content/Language", name = "spa.configuration.name"
)
public @interface SPAConfiguration {

	@Meta.AD(
		description = "cache-expiration-time-description",
		name = "cache-expiration-time-name", required = false
	)
	public long cacheExpirationTime() default -1;

	@Meta.AD(
		description = "navigation-exception-selectors-description",
		name = "navigation-exception-selectors-name", required = false
	)
	public String[] navigationExceptionSelectors() default {
		":not([target=\"_blank\"])", ":not([data-senna-off])",
		":not([data-resource-href])"
	};

	@Meta.AD(
		description = "request-timeout-description",
		name = "request-timeout-name", required = false
	)
	public int requestTimeout() default 0;

	@Meta.AD(
		description = "user-notification-timeout-description",
		name = "user-notification-timeout-name", required = false
	)
	public int userNotificationTimeout() default 30000;

}