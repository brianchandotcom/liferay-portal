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

package com.liferay.object.rest.internal.resource.v1_0;

import com.liferay.object.exception.NoSuchObjectRelationshipException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerTracker;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.net.URI;

import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
@Component(
	factory = "com.liferay.object.rest.internal.resource.v1_0.ObjectRelationshipResource",
	property = {"api.version=v1.0", "osgi.jaxrs.resource=true"},
	service = ObjectRelationshipResourceImpl.class
)
public class ObjectRelationshipResourceImpl
	extends BaseObjectRelationshipResourceImpl {

	@Override
	public Page<Object> getObjectRelatedObjectsPage(
			String previousPath, Long objectEntryId,
			String objectRelationshipName, Pagination pagination)
		throws Exception {

		ObjectDefinition currentObjectDefinition = _getCurrentObjectDefinition(
			objectEntryId, objectRelationshipName, _uriInfo);

		ObjectDefinition relatedObjectDefinition = _getRelatedObjectDefinition(
			_getObjectRelationshipByName(objectRelationshipName),
			currentObjectDefinition);

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerTracker.getObjectEntryManager(
				currentObjectDefinition.getStorageType());

		if (relatedObjectDefinition.isSystem()) {
			return objectEntryManager.getRelatedSystemObjectEntries(
				currentObjectDefinition, objectEntryId, objectRelationshipName,
				pagination);
		}

		return (Page)objectEntryManager.getObjectEntryRelatedObjectEntries(
			_getDefaultDTOConverterContext(
				currentObjectDefinition, objectEntryId, _uriInfo),
			currentObjectDefinition, objectEntryId, objectRelationshipName,
			pagination);
	}

	private ObjectDefinition _getCurrentObjectDefinition(
			long objectEntryId, String objectRelationshipName, UriInfo uriInfo)
		throws Exception {

		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			objectEntryId);

		if (objectEntry != null) {
			return _objectDefinitionLocalService.getObjectDefinition(
				objectEntry.getObjectDefinitionId());
		}

		ObjectRelationship objectRelationship = _getObjectRelationshipByName(
			objectRelationshipName);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId1());

		String objectDefinitionRESTContextPath =
			objectDefinition.getRESTContextPath();

		URI baseURI = uriInfo.getBaseUri();

		String path = baseURI.getPath();

		String endpointBasePath = path.split("/")[2];

		if (!objectDefinitionRESTContextPath.equals(endpointBasePath)) {
			objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectRelationship.getObjectDefinitionId2());
		}

		return objectDefinition;
	}

	private DefaultDTOConverterContext _getDefaultDTOConverterContext(
		ObjectDefinition objectDefinition, Long objectEntryId,
		UriInfo uriInfo) {

		return new DefaultDTOConverterContext(
			_dtoConverterRegistry, objectEntryId,
			LocaleUtil.fromLanguageId(
				objectDefinition.getDefaultLanguageId(), true, false),
			uriInfo, null);
	}

	private ObjectRelationship _getObjectRelationshipByName(
			String objectRelationshipName)
		throws Exception {

		List<ObjectRelationship> objectRelationships = ListUtil.filter(
			_objectRelationshipLocalService.getObjectRelationships(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS),
			objectRelationship ->
				!objectRelationship.isReverse() &&
				objectRelationship.getName(
				).equals(
					objectRelationshipName
				));

		if (objectRelationships.isEmpty()) {
			throw new NoSuchObjectRelationshipException(
				"No ObjectRelationship exists with the name " +
					objectRelationshipName);
		}

		return objectRelationships.get(0);
	}

	private ObjectDefinition _getRelatedObjectDefinition(
			ObjectRelationship objectRelationship,
			ObjectDefinition objectDefinition)
		throws Exception {

		long objectDefinitionId1 = objectRelationship.getObjectDefinitionId1();

		if (objectDefinitionId1 != objectDefinition.getObjectDefinitionId()) {
			return _objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId1());
		}

		return _objectDefinitionLocalService.getObjectDefinition(
			objectRelationship.getObjectDefinitionId2());
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectEntryManagerTracker _objectEntryManagerTracker;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Context
	private UriInfo _uriInfo;

}