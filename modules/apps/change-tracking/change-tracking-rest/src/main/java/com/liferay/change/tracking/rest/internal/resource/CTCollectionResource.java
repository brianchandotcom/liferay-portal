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
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.rest.internal.exception.NoSuchProductionCTCollectionException;
import com.liferay.change.tracking.rest.internal.model.collection.CTCollectionModel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Zoltan Csaszi
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=change-tracking-application)",
		"osgi.jaxrs.resource=true"
	},
	scope = ServiceScope.PROTOTYPE, service = CTCollectionResource.class
)
@Path("/collections")
public class CTCollectionResource {

	@GET
	@Path("/{ctCollectionId}")
	@Produces(MediaType.APPLICATION_JSON)
	public CTCollectionModel getCTCollectionModel(
		@PathParam("ctCollectionId") long ctCollectionId) {

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.getCTCollectionOptional(ctCollectionId);

		CTCollection ctCollection = ctCollectionOptional.orElseThrow(
			() -> new IllegalArgumentException(
				"Unable to find change tracking collection with id " +
					ctCollectionId));

		return _getCTCollectionModel(ctCollection);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CTCollectionModel> getCTCollectionModels(
			@QueryParam("companyId") long companyId,
			@QueryParam("userId") long userId,
			@DefaultValue(_TYPE_ALL) @QueryParam("type") String type)
		throws PortalException {

		List<CTCollection> ctCollections = new ArrayList<>();

		if (_TYPE_ACTIVE.equals(type)) {
			_userLocalService.getUser(userId);

			Optional<CTCollection> activeCTCollectionOptional =
				_ctEngineManager.getActiveCTCollectionOptional(userId);

			activeCTCollectionOptional.ifPresent(ctCollections::add);
		}
		else if (_TYPE_PRODUCTION.equals(type)) {
			_companyLocalService.getCompany(companyId);

			Optional<CTCollection> productionCTCollectionOptional =
				_ctEngineManager.getProductionCTCollectionOptional(companyId);

			CTCollection ctCollection =
				productionCTCollectionOptional.orElseThrow(
					NoSuchProductionCTCollectionException::new);

			ctCollections.add(ctCollection);
		}
		else if (_TYPE_ALL.equals(type)) {
			_companyLocalService.getCompany(companyId);

			ctCollections = _ctEngineManager.getCTCollections(companyId);
		}
		else {
			throw new IllegalArgumentException(
				"Invalid type parameter value: " + type +
					". The valid options are: all, active and production.");
		}

		Stream<CTCollection> ctCollectionStream = ctCollections.stream();

		return ctCollectionStream.map(
			this::_getCTCollectionModel
		).collect(
			Collectors.toList()
		);
	}

	private CTCollectionModel _getCTCollectionModel(CTCollection ctCollection) {
		if (ctCollection == null) {
			return CTCollectionModel.EMPTY_CT_COLLECTION_MODEL;
		}

		List<CTEntry> ctEntries = _ctEngineManager.getCTEntries(
			ctCollection.getCtCollectionId());

		Stream<CTEntry> ctEntryStream = ctEntries.stream();

		Map<Integer, Long> ctEntriesChangeTypes = ctEntryStream.collect(
			Collectors.groupingBy(
				CTEntry::getChangeType, Collectors.counting()));

		CTCollectionModel.Builder builder = CTCollectionModel.forCTCollection(
			ctCollection);

		builder.setAdditionCount(
			ctEntriesChangeTypes.getOrDefault(
				CTConstants.CT_CHANGE_TYPE_ADDITION, 0L));
		builder.setDeletionCount(
			ctEntriesChangeTypes.getOrDefault(
				CTConstants.CT_CHANGE_TYPE_DELETION, 0L));
		builder.setModificationCount(
			ctEntriesChangeTypes.getOrDefault(
				CTConstants.CT_CHANGE_TYPE_MODIFICATION, 0L));

		return builder.build();
	}

	private static final String _TYPE_ACTIVE = "active";

	private static final String _TYPE_ALL = "all";

	private static final String _TYPE_PRODUCTION = "production";

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CTEngineManager _ctEngineManager;

	@Reference
	private UserLocalService _userLocalService;

}