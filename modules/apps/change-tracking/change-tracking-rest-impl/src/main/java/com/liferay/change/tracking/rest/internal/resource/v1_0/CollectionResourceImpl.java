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

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.rest.dto.v1_0.Collection;
import com.liferay.change.tracking.rest.internal.jaxrs.exception.CannotDeleteCollectionException;
import com.liferay.change.tracking.rest.resource.v1_0.CollectionResource;
import com.liferay.portal.kernel.exception.NoSuchModelException;

import java.util.Map;
import java.util.Optional;

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
public class CollectionResourceImpl extends BaseCollectionResourceImpl {

	@Override
	public Response deleteCollection(Long collectionId) throws Exception {
		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.getCTCollectionOptional(collectionId);

		if (!ctCollectionOptional.isPresent()) {
			return Response.status(
				Response.Status.NOT_FOUND
			).build();
		}

		_ctEngineManager.deleteCTCollection(collectionId);

		ctCollectionOptional = _ctEngineManager.getCTCollectionOptional(
			collectionId);

		if (ctCollectionOptional.isPresent()) {
			throw new CannotDeleteCollectionException(
				"Unable to delete change tracking collection with id " +
					collectionId);
		}

		return _noContent();
	}

	@Override
	public Collection getCollection(Long collectionId) throws Exception {
		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.getCTCollectionOptional(collectionId);

		CTCollection ctCollection = ctCollectionOptional.orElseThrow(
			() -> new NoSuchModelException(
				"Unable to find change tracking collection with id " +
					collectionId));

		return _getCollection(ctCollection);
	}

	private Collection _getCollection(CTCollection ctCollection) {
		Map<Integer, Long> ctEntriesChangeTypes =
			_ctEngineManager.getCTCollectionChangeTypeCounts(
				ctCollection.getCtCollectionId());

		Collection collection = new Collection();

		collection.setAdditionCount(
			ctEntriesChangeTypes.getOrDefault(
				CTConstants.CT_CHANGE_TYPE_ADDITION, 0L));
		collection.setCollectionId(ctCollection.getCtCollectionId());
		collection.setCompanyId(ctCollection.getCompanyId());
		collection.setDeletionCount(
			ctEntriesChangeTypes.getOrDefault(
				CTConstants.CT_CHANGE_TYPE_DELETION, 0L));
		collection.setDescription(ctCollection.getDescription());
		collection.setModificationCount(
			ctEntriesChangeTypes.getOrDefault(
				CTConstants.CT_CHANGE_TYPE_MODIFICATION, 0L));
		collection.setName(ctCollection.getName());
		collection.setStatusByUserName(ctCollection.getStatusByUserName());
		collection.setStatusDate(ctCollection.getStatusDate());

		return collection;
	}

	private Response _noContent() {
		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Reference
	private CTEngineManager _ctEngineManager;

}