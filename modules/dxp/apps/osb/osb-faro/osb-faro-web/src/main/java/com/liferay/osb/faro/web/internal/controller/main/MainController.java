/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.controller.main;

import com.liferay.osb.faro.web.internal.controller.BaseFaroController;
import com.liferay.osb.faro.web.internal.controller.FaroControllerRegistry;
import com.liferay.osb.faro.web.internal.model.display.FaroResultsDisplay;
import com.liferay.osb.faro.web.internal.param.FaroParam;
import com.liferay.osb.faro.web.internal.search.FaroSearchContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Kong
 */
@Component(service = MainController.class)
@Path("/{groupId}")
@Produces(MediaType.APPLICATION_JSON)
public class MainController extends BaseFaroController {

	@GET
	@Path("/entities")
	@RolesAllowed(RoleConstants.SITE_MEMBER)
	public List<FaroResultsDisplay> search(
			@PathParam("groupId") long groupId,
			@QueryParam("faroSearchContexts") FaroParam<List<FaroSearchContext>>
				faroSearchContextsFaroParam)
		throws Exception {

		return _faroControllerRegistry.search(
			groupId, faroSearchContextsFaroParam.getValue());
	}

	@Path("/entities/search")
	@POST
	@RolesAllowed(RoleConstants.SITE_MEMBER)
	public List<FaroResultsDisplay> searchByForm(
			@PathParam("groupId") long groupId,
			@FormParam("faroSearchContexts") FaroParam<List<FaroSearchContext>>
				faroSearchContextsFaroParam)
		throws Exception {

		return search(groupId, faroSearchContextsFaroParam);
	}

	@Path("/engine")
	@POST
	@RolesAllowed(StringPool.BLANK)
	public void setEngineURL(
		@FormParam("contactsEngineURL") String contactsEngineURL) {

		if (Validator.isNotNull(contactsEngineURL)) {
			contactsEngineClient.setEngineURL(contactsEngineURL);
		}
	}

	@Reference
	private FaroControllerRegistry _faroControllerRegistry;

}