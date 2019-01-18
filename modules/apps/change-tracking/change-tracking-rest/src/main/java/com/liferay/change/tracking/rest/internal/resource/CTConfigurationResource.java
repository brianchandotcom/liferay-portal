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

import com.liferay.change.tracking.CTEngineManager;
import com.liferay.change.tracking.configuration.CTConfiguration;
import com.liferay.change.tracking.rest.internal.model.configuration.CTConfigurationModel;
import com.liferay.change.tracking.rest.internal.model.configuration.CTConfigurationUpdateModel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Máté Thurzó
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=change-tracking-application)",
		"osgi.jaxrs.resource=true"
	},
	scope = ServiceScope.PROTOTYPE, service = CTConfigurationResource.class
)
@Path("/configurations")
public class CTConfigurationResource {

	@GET
	@Path("/{companyId}")
	@Produces(MediaType.APPLICATION_JSON)
	public CTConfigurationModel getCtConfiguration(
			@PathParam("companyId") long companyId)
		throws PortalException {

		_companyLocalService.getCompany(companyId);

		return _getCTConfigurationModel(companyId);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CTConfigurationModel> getCTConfigurations()
		throws PortalException {

		List<CTConfigurationModel> companies = new ArrayList<>();

		for (Company company : _companyLocalService.getCompanies()) {
			companies.add(_getCTConfigurationModel(company.getCompanyId()));
		}

		return companies;
	}

	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{companyId}")
	@Produces(MediaType.APPLICATION_JSON)
	@PUT
	public CTConfigurationModel updateCtConfiguration(
			@PathParam("companyId") long companyId, @Context User user,
			CTConfigurationUpdateModel ctConfigurationUpdateModel)
		throws PortalException {

		_companyLocalService.getCompany(companyId);

		_updateChangeTrackingEnabled(
			companyId, user, ctConfigurationUpdateModel);

		return _getCTConfigurationModel(companyId);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC, unbind = "_removeCTConfiguration"
	)
	private void _addCTConfiguration(CTConfiguration<?, ?> ctConfiguration) {
		_ctConfigurations.add(ctConfiguration);
	}

	private CTConfigurationModel _getCTConfigurationModel(long companyId)
		throws PortalException {

		Set<String> supportedContentTypeLanguageKeys = new HashSet<>();
		Set<String> supportedContentTypes = new HashSet<>();

		Stream<CTConfiguration<?, ?>> stream = _ctConfigurations.stream();

		stream.forEach(
			ctConfiguration -> {
				supportedContentTypeLanguageKeys.add(
					ctConfiguration.getContentTypeLanguageKey());
				supportedContentTypes.add(ctConfiguration.getContentType());
			});

		CTConfigurationModel.Builder builder = CTConfigurationModel.forCompany(
			companyId);

		return builder.setChangeTrackingEnabled(
			_ctEngineManager.isChangeTrackingEnabled(companyId)
		).setSupportedContentTypeLanguageKeys(
			supportedContentTypeLanguageKeys
		).setSupportedContentTypes(
			supportedContentTypes
		).build();
	}

	private void _removeCTConfiguration(CTConfiguration<?, ?> ctConfiguration) {
		_ctConfigurations.remove(ctConfiguration);
	}

	private void _updateChangeTrackingEnabled(
		long companyId, User user,
		CTConfigurationUpdateModel ctConfigurationUpdateModel) {

		boolean changeTrackingEnabled =
			_ctEngineManager.isChangeTrackingEnabled(companyId);
		boolean setChangeTrackingEnabled =
			ctConfigurationUpdateModel.isChangeTrackingEnabled();

		if (changeTrackingEnabled && !setChangeTrackingEnabled) {
			_ctEngineManager.disableChangeTracking(companyId);
		}
		else if (!changeTrackingEnabled && setChangeTrackingEnabled) {
			_ctEngineManager.enableChangeTracking(companyId, user.getUserId());
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	private final Set<CTConfiguration<?, ?>> _ctConfigurations =
		new HashSet<>();

	@Reference
	private CTEngineManager _ctEngineManager;

}