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

package com.liferay.change.tracking.rest.internal.resource;

import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.rest.internal.exception.JaxRsCTEngineException;
import com.liferay.change.tracking.rest.internal.model.configuration.CTUserConfigurationModel;
import com.liferay.change.tracking.rest.internal.model.configuration.CTUserConfigurationUpdateModel;
import com.liferay.change.tracking.rest.internal.util.CTJaxRsUtil;
import com.liferay.change.tracking.service.CTPreferencesLocalService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Gergely Mathe
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.Change.Tracking.REST)",
		"osgi.jaxrs.resource=true"
	},
	scope = ServiceScope.PROTOTYPE, service = CTUserConfigurationResource.class
)
@Path("/configurations/{companyId}/user")
public class CTUserConfigurationResource {

	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public CTUserConfigurationModel getCTUserConfigurationModel(
			@PathParam("companyId") long companyId,
			@PathParam("userId") long userId)
		throws JaxRsCTEngineException {

		CTJaxRsUtil.checkCompany(companyId);

		CTJaxRsUtil.getUser(userId);

		return _getCTUserConfigurationModel(companyId, userId);
	}

	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@PUT
	public CTUserConfigurationModel updateCTConfiguration(
			@PathParam("companyId") long companyId,
			@PathParam("userId") long userId,
			CTUserConfigurationUpdateModel ctUserConfigurationUpdateModel)
		throws JaxRsCTEngineException {

		CTJaxRsUtil.checkCompany(companyId);

		CTJaxRsUtil.getUser(userId);

		_updateCheckoutCTCollectionConfirmationEnabled(
			companyId, userId, ctUserConfigurationUpdateModel);

		return _getCTUserConfigurationModel(companyId, userId);
	}

	private CTUserConfigurationModel _getCTUserConfigurationModel(
		long companyId, long userId) {

		CTUserConfigurationModel.Builder builder =
			CTUserConfigurationModel.forCompanyAndUser(companyId, userId);

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(companyId, userId);

		boolean confirmationEnabled = true;

		if (ctPreferences == null) {
			confirmationEnabled = ctPreferences.isConfirmationEnabled();
		}

		return builder.setCheckoutCTCollectionConfirmationEnabled(
			confirmationEnabled
		).build();
	}

	private void _updateCheckoutCTCollectionConfirmationEnabled(
		long companyId, long userId,
		CTUserConfigurationUpdateModel ctUserConfigurationUpdateModel) {

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.getCTPreferences(companyId, userId);

		ctPreferences.setConfirmationEnabled(
			ctUserConfigurationUpdateModel.
				isCheckoutCTCollectionConfirmationEnabled());

		_ctPreferencesLocalService.updateCTPreferences(ctPreferences);
	}

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

}