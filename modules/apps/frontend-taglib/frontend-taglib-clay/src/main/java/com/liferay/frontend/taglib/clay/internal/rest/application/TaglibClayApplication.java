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

package com.liferay.frontend.taglib.clay.internal.rest.application;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rodolfo Roza Miranda
 * @author Iván Zaera Avellón
 */
@Component(
	immediate = true,
	property = {
		"auth.verifier.auth.verifier.PortalSessionAuthVerifier.urls.includes=/*",
		"osgi.jaxrs.application.base=/taglib-clay",
		"osgi.jaxrs.name=taglib-clay-application"
	},
	service = Application.class
)
public class TaglibClayApplication extends Application {
}