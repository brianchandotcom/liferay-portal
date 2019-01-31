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
import com.liferay.change.tracking.rest.internal.exception.CTJaxRsException;
import com.liferay.change.tracking.rest.internal.exception.CannotCreateCTCollectionException;
import com.liferay.change.tracking.rest.internal.exception.CannotDeleteCTCollectionException;
import com.liferay.change.tracking.rest.internal.exception.NoSuchProductionCTCollectionException;
import com.liferay.change.tracking.rest.internal.model.collection.CTCollectionModel;
import com.liferay.change.tracking.rest.internal.model.collection.CTCollectionUpdateModel;
import com.liferay.change.tracking.rest.internal.util.CTJaxRsUtil;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Zoltan Csaszi
 * @author Daniel Kocsis
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

	@Path("/{ctCollectionId}/checkout")
	@POST
	public Response checkoutCTCollection(
			@PathParam("ctCollectionId") long ctCollectionId,
			@QueryParam("userId") long userId)
		throws PortalException {

		User user = CTJaxRsUtil.getUser(userId);

		_ctEngineManager.checkoutCTCollection(user.getUserId(), ctCollectionId);

		return _accepted();
	}

	@Consumes(MediaType.APPLICATION_JSON)
	@POST
	public CTCollectionModel createOrUpdateCTCollection(
			@QueryParam("companyId") long companyId,
			@QueryParam("userId") long userId,
			CTCollectionUpdateModel ctCollectionUpdateModel)
		throws CTJaxRsException {

		CTJaxRsUtil.checkCompany(companyId);

		User user = CTJaxRsUtil.getUser(userId);

		CTJaxRsUtil.checkChangeTrackingEnabled(companyId, _ctEngineManager);

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.createCTCollection(
				user.getUserId(), ctCollectionUpdateModel.getName(),
				ctCollectionUpdateModel.getDescription());

		return ctCollectionOptional.map(
			this::_getCTCollectionModel
		).orElseThrow(
			() ->
				new CannotCreateCTCollectionException(
					companyId, "Cannot create Change Tracking Collection")
		);
	}

	@DELETE
	@Path("/{ctCollectionId}")
	public Response deleteCTCollection(
			@PathParam("ctCollectionId") long ctCollectionId)
		throws CTJaxRsException {

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.getCTCollectionOptional(ctCollectionId);

		if (!ctCollectionOptional.isPresent()) {
			return Response.status(
				Response.Status.NOT_FOUND
			).build();
		}

		_ctEngineManager.deleteCTCollection(ctCollectionId);

		ctCollectionOptional = _ctEngineManager.getCTCollectionOptional(
			ctCollectionId);

		if (ctCollectionOptional.isPresent()) {
			throw new CannotDeleteCTCollectionException(
				ctCollectionOptional.map(
					CTCollection::getCompanyId
				).get(),
				"Cannot delete Change Tracking Collection");
		}

		return _noContent();
	}

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
			@DefaultValue(_TYPE_ALL) @QueryParam("type") String type,
			@QueryParam("limit") int limit, @QueryParam("sort") String sort)
		throws CTJaxRsException {

		List<CTCollection> ctCollections = new ArrayList<>();

		if (_TYPE_ACTIVE.equals(type)) {
			CTJaxRsUtil.getUser(userId);

			Optional<CTCollection> activeCTCollectionOptional =
				_ctEngineManager.getActiveCTCollectionOptional(userId);

			activeCTCollectionOptional.ifPresent(ctCollections::add);
		}
		else if (_TYPE_PRODUCTION.equals(type)) {
			CTJaxRsUtil.checkCompany(companyId);

			Optional<CTCollection> productionCTCollectionOptional =
				_ctEngineManager.getProductionCTCollectionOptional(companyId);

			CTCollection ctCollection =
				productionCTCollectionOptional.orElseThrow(
					() -> new NoSuchProductionCTCollectionException(
						companyId,
						"Cannot find production Change Tracking Collection"));

			ctCollections.add(ctCollection);
		}
		else if (_TYPE_ALL.equals(type)) {
			CTJaxRsUtil.checkCompany(companyId);

			ctCollections = _ctEngineManager.getCTCollections(
				companyId, _getQueryDefinition(limit, sort));
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

	@Path("/{ctCollectionId}/publish")
	@POST
	public Response publishCTCollection(
			@PathParam("ctCollectionId") long ctCollectionId,
			@QueryParam("userId") long userId)
		throws CTJaxRsException {

		User user = CTJaxRsUtil.getUser(userId);

		_ctEngineManager.publishCTCollection(user.getUserId(), ctCollectionId);

		return _accepted();
	}

	private Response _accepted() {
		Response.ResponseBuilder responseBuilder = Response.accepted();

		return responseBuilder.build();
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

	private QueryDefinition<CTCollection> _getQueryDefinition(
		int limit, String sort) {

		QueryDefinition<CTCollection> queryDefinition = new QueryDefinition<>();

		queryDefinition.setStart(0);
		queryDefinition.setEnd(CTJaxRsUtil.checkLimit(limit));

		OrderByComparator<CTCollection> orderByComparator =
			OrderByComparatorFactoryUtil.create(
				"CTCollection",
				CTJaxRsUtil.checkSortColumns(sort, _orderByColumnNames));

		queryDefinition.setOrderByComparator(orderByComparator);

		return queryDefinition;
	}

	private Response _noContent() {
		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	private static final String _TYPE_ACTIVE = "active";

	private static final String _TYPE_ALL = "all";

	private static final String _TYPE_PRODUCTION = "production";

	private static final Set<String> _orderByColumnNames = new HashSet<>(
		Arrays.asList("createDate", "modifiedDate", "name", "statusDate"));

	@Reference
	private CTEngineManager _ctEngineManager;

}