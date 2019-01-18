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

package com.liferay.oauth2.provider.rest.internal.endpoint.json;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.rs.security.oauth2.provider.OAuthJSONProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(component.name=com.liferay.oauth2.provider.rest.internal.endpoint.OAuth2EndpointApplication)",
		"osgi.jaxrs.extension=true", "osgi.jaxrs.name=OAuthJSONProvider"
	},
	scope = ServiceScope.PROTOTYPE,
	service = {MessageBodyReader.class, MessageBodyWriter.class}
)
@Consumes("application/json")
@Produces("application/json")
@Provider
public class OAuthJSONProviderComponent extends OAuthJSONProvider {
}