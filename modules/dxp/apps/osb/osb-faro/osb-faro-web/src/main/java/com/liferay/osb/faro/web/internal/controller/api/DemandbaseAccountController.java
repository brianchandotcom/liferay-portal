/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.controller.api;

import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.osb.faro.web.internal.context.GroupInfo;
import com.liferay.osb.faro.web.internal.controller.BaseFaroController;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rachael Koestartyo
 */
@Component(service = DemandbaseAccountController.class)
@Path("/demandbase_accounts")
@Produces(MediaType.APPLICATION_JSON)
@RequiresScope("Liferay.Analytics.Cloud.REST.accounts.write")
public class DemandbaseAccountController extends BaseFaroController {

	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{any:.*}")
	@POST
	public Object postAccount(
			@Context GroupInfo groupInfo, String requestBody,
			@Context UriInfo uriInfo)
		throws Exception {

		return null;
	}

}