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

package com.liferay.oauth2.provider.rest.internal.endpoint.redirect;

import com.liferay.petra.string.StringBundler;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;

/**
 * @author Raymond Augé
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.OAuth2.Application)",
		"osgi.jaxrs.name=Liferay.Authorization.Redirect",
		"osgi.jaxrs.resource=true"
	},
	service = OAuth2ProviderApplicationRedirect.class
)
@Path("/redirect")
public class OAuth2ProviderApplicationRedirect {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response redirect(
		@DefaultValue("") @Encoded @QueryParam("code") String code,
		@DefaultValue("") @Encoded @QueryParam("error") String error) {

		return Response.ok(
			StringBundler.concat(
				"<html><head><title>Liferay OAuth2 Redirect</title></head>",
				"<body><script type=\"text/javascript\">window.postMessage(",
				"{code: \"", code, "\", error: \"", error,
				"\"}, document.location.href);</script></body></html>")
		).build();
	}

}