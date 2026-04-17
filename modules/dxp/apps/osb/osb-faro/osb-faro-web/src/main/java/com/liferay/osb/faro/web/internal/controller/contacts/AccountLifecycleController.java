/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.controller.contacts;

import com.liferay.osb.faro.engine.client.model.AccountLifecycleMetric;
import com.liferay.osb.faro.web.internal.controller.BaseFaroController;
import com.liferay.portal.kernel.model.RoleConstants;

import jakarta.annotation.security.RolesAllowed;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Ferrari
 */
@Component(service = AccountLifecycleController.class)
@Path("/{groupId}/account-lifecycle")
@Produces(MediaType.APPLICATION_JSON)
public class AccountLifecycleController extends BaseFaroController {

	@GET
	@Path("/{id}/overview")
	@RolesAllowed(RoleConstants.SITE_MEMBER)
	public List<AccountLifecycleMetric> getAccountLifecycleOverview(
			@PathParam("groupId") long groupId, @PathParam("id") String id,
			@QueryParam("country") String country,
			@QueryParam("industry") String industry,
			@QueryParam("revenue") String revenue)
		throws Exception {

		return contactsEngineClient.getAccountLifecycleOverview(
			faroProjectLocalService.getFaroProjectByGroupId(groupId), country,
			id, industry, revenue);
	}

}