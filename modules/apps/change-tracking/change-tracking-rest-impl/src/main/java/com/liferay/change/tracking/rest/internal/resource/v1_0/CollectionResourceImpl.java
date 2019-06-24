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

package com.liferay.change.tracking.rest.internal.resource.v1_0;

import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.engine.CTManager;
import com.liferay.change.tracking.engine.exception.CTCollectionDescriptionCTEngineException;
import com.liferay.change.tracking.engine.exception.CTCollectionNameCTEngineException;
import com.liferay.change.tracking.exception.NoSuchCollectionException;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.rest.constant.v1_0.CollectionType;
import com.liferay.change.tracking.rest.dto.v1_0.Collection;
import com.liferay.change.tracking.rest.dto.v1_0.CollectionUpdate;
import com.liferay.change.tracking.rest.internal.dto.v1_0.util.CollectionUtil;
import com.liferay.change.tracking.rest.internal.jaxrs.exception.ChangeTrackingDisabledException;
import com.liferay.change.tracking.rest.internal.jaxrs.exception.CollectionDescriptionTooLongException;
import com.liferay.change.tracking.rest.internal.jaxrs.exception.CollectionNameTooLongException;
import com.liferay.change.tracking.rest.internal.jaxrs.exception.CollectionNameTooShortException;
import com.liferay.change.tracking.rest.internal.jaxrs.exception.CreateCollectionException;
import com.liferay.change.tracking.rest.internal.jaxrs.exception.DeleteCollectionException;
import com.liferay.change.tracking.rest.internal.odata.entity.v1_0.CollectionEntityModel;
import com.liferay.change.tracking.rest.resource.v1_0.CollectionResource;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ClearThreadLocalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Máté Thurzó
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/collection.properties",
	scope = ServiceScope.PROTOTYPE, service = CollectionResource.class
)
public class CollectionResourceImpl
	extends BaseCollectionResourceImpl implements EntityModelResource {

	@Override
	public Response deleteCollection(Long companyId, Long collectionId)
		throws Exception {

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.getCTCollectionOptional(companyId, collectionId);

		if (!ctCollectionOptional.isPresent()) {
			return Response.status(
				Response.Status.NOT_FOUND
			).build();
		}

		_ctEngineManager.deleteCTCollection(collectionId);

		ctCollectionOptional = _ctEngineManager.getCTCollectionOptional(
			companyId, collectionId);

		if (ctCollectionOptional.isPresent()) {
			throw new DeleteCollectionException(
				"Unable to delete collection " + collectionId);
		}

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Collection getCollection(Long companyId, Long collectionId)
		throws Exception {

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.getCTCollectionOptional(companyId, collectionId);

		return CollectionUtil.toCollection(
			ctCollectionOptional.orElseThrow(
				() -> new NoSuchModelException(
					"Unable to get collection " + collectionId)),
			_ctEngineManager);
	}

	@Override
	public Page<Collection> getCollectionsPage(
			CollectionType collectionType, Long companyId, Long userId,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		List<CTCollection> ctCollections = new ArrayList<>();

		if (CollectionType.ACTIVE == collectionType) {
			_userLocalService.getUser(userId);

			Optional<CTCollection> activeCTCollectionOptional =
				_ctManager.getActiveCTCollectionOptional(companyId, userId);

			activeCTCollectionOptional.ifPresent(ctCollections::add);
		}
		else if (CollectionType.ALL == collectionType) {
			_companyLocalService.getCompany(companyId);

			ctCollections = _ctManager.getCTCollections(
				companyId, userId, false, true,
				SearchUtil.getQueryDefinition(
					CTCollection.class, pagination, sorts));
		}
		else if (CollectionType.PRODUCTION == collectionType) {
			_companyLocalService.getCompany(companyId);

			Optional<CTCollection> productionCTCollectionOptional =
				_ctEngineManager.getProductionCTCollectionOptional(companyId);

			CTCollection ctCollection =
				productionCTCollectionOptional.orElseThrow(
					() -> new NoSuchCollectionException(
						"Unable to get production change tracking collection"));

			ctCollections.add(ctCollection);
		}
		else if (CollectionType.RECENT == collectionType) {
			_companyLocalService.getCompany(companyId);
			_userLocalService.getUser(userId);

			QueryDefinition<CTCollection> queryDefinition =
				SearchUtil.getQueryDefinition(
					CTCollection.class, pagination, sorts);

			queryDefinition.setStatus(WorkflowConstants.STATUS_DRAFT);

			ctCollections = _ctManager.getCTCollections(
				companyId, userId, false, false, queryDefinition);
		}

		List<Collection> collections = transform(
			ctCollections,
			ctCollection -> CollectionUtil.toCollection(
				ctCollection, _ctEngineManager));

		return Page.of(collections, pagination, collections.size());
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return new CollectionEntityModel();
	}

	@Override
	public Collection postCollection(
			Long companyId, Long userId, CollectionUpdate collectionUpdate)
		throws Exception {

		_companyLocalService.getCompany(companyId);
		_userLocalService.getUser(userId);

		if (!_ctEngineManager.isChangeTrackingEnabled(companyId)) {
			throw new ChangeTrackingDisabledException(
				"Change tracking is disabled for company " + companyId);
		}

		try {
			Optional<CTCollection> ctCollectionOptional =
				_ctEngineManager.createCTCollection(
					userId, collectionUpdate.getName(),
					collectionUpdate.getDescription());

			return ctCollectionOptional.map(
				ctCollection -> CollectionUtil.toCollection(
					ctCollection, _ctEngineManager)
			).orElseThrow(
				() -> new CreateCollectionException(
					"Unable to create collection")
			);
		}
		catch (PortalException pe) {
			if (pe instanceof CTCollectionDescriptionCTEngineException) {
				throw new CollectionDescriptionTooLongException(
					"The collection description is too long");
			}
			else if (pe instanceof CTCollectionNameCTEngineException) {
				if (Validator.isNull(pe.getMessage())) {
					throw new CollectionNameTooShortException(
						"The collection name is too short");
				}

				throw new CollectionNameTooLongException(
					"The collection name is too long");
			}
			else {
				throw new CreateCollectionException(
					"Unable to create collection");
			}
		}
	}

	@Override
	public Response postCollectionCheckout(Long collectionId, Long userId)
		throws Exception {

		_userLocalService.getUser(userId);

		_ctEngineManager.checkoutCTCollection(userId, collectionId);

		Response.ResponseBuilder responseBuilder = Response.accepted();

		return responseBuilder.build();
	}

	@Override
	public Response postCollectionPublish(
			Long collectionId, Boolean ignoreCollision, Long userId)
		throws Exception {

		_userLocalService.getUser(userId);

		_ctEngineManager.publishCTCollection(
			userId, collectionId, ignoreCollision);

		Response.ResponseBuilder responseBuilder = Response.accepted();

		return responseBuilder.build();
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CTEngineManager _ctEngineManager;

	@Reference
	private CTManager _ctManager;

	@Reference
	private UserLocalService _userLocalService;

}