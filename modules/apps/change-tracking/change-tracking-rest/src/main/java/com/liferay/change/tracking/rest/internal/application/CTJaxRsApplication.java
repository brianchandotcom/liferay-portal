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

package com.liferay.change.tracking.rest.internal.application;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;

/**
 * @author Máté Thurzó
 */
@Component(
	property = {
		"auth.verifier.auth.verifier.BasicAuthHeaderAuthVerifier.urls.includes=/*",
		"auth.verifier.auth.verifier.PortalSessionAuthVerifier.urls.includes=/*",
		"osgi.jaxrs.application.base=/change-tracking",
		"osgi.jaxrs.extension.select=(osgi.jaxrs.name=jaxb-json)",
		"osgi.jaxrs.extension.select=(osgi.jaxrs.name=Liferay.Vulcan)",
		"osgi.jaxrs.name=Liferay.Change.Tracking.REST"
	},
	service = Application.class
)
public class CTJaxRsApplication extends Application {
}