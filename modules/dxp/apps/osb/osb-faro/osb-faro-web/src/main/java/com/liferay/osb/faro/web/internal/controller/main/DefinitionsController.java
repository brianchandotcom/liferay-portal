/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.controller.main;

import com.liferay.osb.faro.engine.client.model.FieldMapping;
import com.liferay.osb.faro.web.internal.controller.BaseFaroController;
import com.liferay.osb.faro.web.internal.controller.FaroController;
import com.liferay.osb.faro.web.internal.model.display.FaroResultsDisplay;
import com.liferay.osb.faro.web.internal.model.display.contacts.AttributesDisplay;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.RoleConstants;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rachael Koestartyo
 */
@Component(service = {DefinitionsController.class, FaroController.class})
@Path("/{groupId}/definitions")
@Produces(MediaType.APPLICATION_JSON)
public class DefinitionsController extends BaseFaroController {

	@GET
	@Path("/individual_attributes")
	@RolesAllowed(RoleConstants.SITE_MEMBER)
	public FaroResultsDisplay get(
			@PathParam("groupId") long groupId,
			@QueryParam("displayName") String displayName)
		throws PortalException {

		List<FieldMapping> individualAttributes =
			contactsEngineClient.getIndividualAttributes(
				faroProjectLocalService.getFaroProjectByGroupId(groupId),
				displayName);

		return new FaroResultsDisplay(
			TransformUtil.transform(
				individualAttributes, AttributesDisplay::new),
			individualAttributes.size());
	}

}