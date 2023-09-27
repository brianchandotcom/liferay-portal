/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.rest.internal.endpoint.redirect;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HtmlUtil;

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
				JSONUtil.put(
					"code", HtmlUtil.escapeJS(code)
				).put(
					"error", HtmlUtil.escapeJS(error)
				),
				", document.location.href);</script></body></html>")
		).build();
	}

}