/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.controller.main;

import com.liferay.osb.faro.engine.client.model.PageExperience;
import com.liferay.osb.faro.web.internal.controller.BaseFaroController;
import com.liferay.osb.faro.web.internal.model.display.FaroFDSResultsDisplay;
import com.liferay.portal.kernel.model.RoleConstants;

import jakarta.annotation.security.RolesAllowed;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;

/**
 * @author Thiago Buarque
 */
@Component(service = PageExperienceController.class)
@Path("/{groupId}/page-experiences")
@Produces(MediaType.APPLICATION_JSON)
public class PageExperienceController extends BaseFaroController {

	@GET
	@RolesAllowed(RoleConstants.SITE_MEMBER)
	public FaroFDSResultsDisplay<PageExperience> getPageExperiences(
			@PathParam("groupId") long groupId,
			@QueryParam("canonicalUrl") String canonicalUrl,
			@QueryParam("channelId") String channelId,
			@QueryParam("keywords") String keywords,
			@DefaultValue("1") @QueryParam("page") int page,
			@QueryParam("pageTitle") String pageTitle,
			@DefaultValue("20") @QueryParam("size") int size,
			@QueryParam("sort") String sortString)
		throws Exception {

		return new FaroFDSResultsDisplay(
			contactsEngineClient.getPageExperiences(
				faroProjectLocalService.getFaroProjectByGroupId(groupId),
				canonicalUrl, channelId, keywords, page, pageTitle, size,
				sortString),
			page, size);
	}

}