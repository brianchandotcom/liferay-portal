/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.controller.api;

import com.liferay.oauth2.provider.scope.RequiresNoScope;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.web.internal.context.GroupInfo;
import com.liferay.osb.faro.web.internal.controller.BaseFaroController;
import com.liferay.osb.faro.web.internal.util.JSONUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.net.URI;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(service = RecommendationController.class)
@Path("/recommendations")
@Produces(MediaType.APPLICATION_JSON)
@RequiresNoScope
public class RecommendationController extends BaseFaroController {

	@GET
	@Path("{any:.*}")
	public Map<?, ?> get(@Context GroupInfo groupInfo, @Context UriInfo uriInfo)
		throws Exception {

		FaroProject faroProject =
			faroProjectLocalService.getFaroProjectByGroupId(
				groupInfo.getGroupId());

		return contactsEngineClient.get(
			faroProject, _createHeaders(uriInfo.getBaseUri()),
			"/api/" + uriInfo.getPath(), uriInfo.getQueryParameters(),
			Map.class);
	}

	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{any:.*}")
	@POST
	public Map<?, ?> post(
			@Context GroupInfo groupInfo, String requestBody,
			@Context UriInfo uriInfo)
		throws Exception {

		FaroProject faroProject =
			faroProjectLocalService.getFaroProjectByGroupId(
				groupInfo.getGroupId());

		return contactsEngineClient.post(
			faroProject, _createHeaders(uriInfo.getBaseUri()),
			"/api/" + uriInfo.getPath(), uriInfo.getQueryParameters(),
			JSONUtil.readValue(requestBody, Map.class), Map.class);
	}

	private Map<String, String> _createHeaders(URI baseURI) {
		return HashMapBuilder.put(
			"X-Forwarded-Host", baseURI.getHost()
		).put(
			"X-Forwarded-Port", String.valueOf(baseURI.getPort())
		).put(
			"X-Forwarded-Proto", baseURI.getScheme()
		).build();
	}

}