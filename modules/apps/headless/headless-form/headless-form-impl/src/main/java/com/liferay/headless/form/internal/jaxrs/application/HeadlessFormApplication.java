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

package com.liferay.headless.form.internal.jaxrs.application;

import javax.annotation.Generated;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Component;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(
	property = {
		"auth.verifier.auth.verifier.BasicAuthHeaderAuthVerifier.urls.includes=/*",
		"auth.verifier.auth.verifier.OAuth2RestAuthVerifier.urls.includes=/*",
		"auth.verifier.auth.verifier.PortalSessionAuthVerifier.urls.includes=/*",
		"auth.verifier.guest.allowed=true",
		"oauth2.scope.checker.type=annotations",
		"osgi.jaxrs.application.base=/headless-form",
		"osgi.jaxrs.extension.select=(osgi.jaxrs.name=Liferay.OAuth2)",
		"osgi.jaxrs.extension.select=(osgi.jaxrs.name=vulcan.AcceptLanguageContextProvider)",
		"osgi.jaxrs.extension.select=(osgi.jaxrs.name=vulcan.JSONMessageBodyReader)",
		"osgi.jaxrs.extension.select=(osgi.jaxrs.name=vulcan.JSONMessageBodyWriter)",
		"osgi.jaxrs.extension.select=(osgi.jaxrs.name=vulcan.PaginationContextProvider)",
		"osgi.jaxrs.name=headless-form-application.rest"
	},
	service = Application.class
)
@Generated("")
public class HeadlessFormApplication extends Application {
}