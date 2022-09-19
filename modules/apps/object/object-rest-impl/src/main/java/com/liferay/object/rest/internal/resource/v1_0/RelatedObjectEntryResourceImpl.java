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

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerTracker;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.ObjectRelationshipService;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadataTracker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.net.URI;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
@Component(
	factory = "com.liferay.object.rest.internal.resource.v1_0.RelatedObjectEntryResource",
	property = {"api.version=v1.0", "osgi.jaxrs.resource=true"},
	service = RelatedObjectEntryResourceImpl.class
)
public class RelatedObjectEntryResourceImpl
	extends BaseRelatedObjectEntryResourceImpl {

	@Override
	public Page<Object> getRelatedObjectEntriesPage(
			String previousPath, Long objectEntryId,
			String objectRelationshipName, Pagination pagination)
		throws Exception {

		ObjectDefinition systemObjectDefinition = _getSystemObjectDefinition(
			_getSystemObjectDefinitionMetadata(
				_getRESTContextPath(previousPath)));

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.
				getObjectRelationshipByObjectDefinitionId(
					systemObjectDefinition.getObjectDefinitionId(),
					objectRelationshipName);

		ObjectDefinition relatedObjectDefinition = _getRelatedObjectDefinition(
			systemObjectDefinition, objectRelationship);

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerTracker.getObjectEntryManager(
				systemObjectDefinition.getStorageType());

		if (relatedObjectDefinition.isSystem()) {
			return objectEntryManager.getRelatedSystemObjectEntries(
				systemObjectDefinition, objectEntryId, objectRelationshipName,
				pagination);
		}

		return (Page)objectEntryManager.getObjectEntryRelatedObjectEntries(
			_getDefaultDTOConverterContext(
				systemObjectDefinition, objectEntryId, _uriInfo),
			systemObjectDefinition, objectEntryId, objectRelationshipName,
			pagination);
	}

	@Override
	public ObjectEntry putObjectRelationshipMappingTableValues(
			String previousPath, Long objectEntryId,
			String objectRelationshipName, Long relatedObjectEntryId,
			Pagination pagination)
		throws Exception {

		ObjectDefinition systemObjectDefinition = _getSystemObjectDefinition(
			_getSystemObjectDefinitionMetadata(
				_getRESTContextPath(previousPath)));

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.
				getObjectRelationshipByObjectDefinitionId(
					systemObjectDefinition.getObjectDefinitionId(),
					objectRelationshipName);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId1());

		_objectRelationshipService.addObjectRelationshipMappingTableValues(
			objectRelationship.getObjectRelationshipId(),
			_getPrimaryKey1(
				systemObjectDefinition, objectDefinition, objectEntryId,
				relatedObjectEntryId),
			_getPrimaryKey2(
				systemObjectDefinition, objectDefinition, objectEntryId,
				relatedObjectEntryId),
			new ServiceContext());

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerTracker.getObjectEntryManager(
				systemObjectDefinition.getStorageType());

		ObjectDefinition relatedObjectDefinition = _getRelatedObjectDefinition(
			systemObjectDefinition, objectRelationship);

		return objectEntryManager.getObjectEntry(
			_getDefaultDTOConverterContext(
				relatedObjectDefinition, relatedObjectEntryId, _uriInfo),
			relatedObjectDefinition, relatedObjectEntryId);
	}

	private DefaultDTOConverterContext _getDefaultDTOConverterContext(
		ObjectDefinition objectDefinition, Long objectEntryId,
		UriInfo uriInfo) {

		DefaultDTOConverterContext defaultDTOConverterContext =
			new DefaultDTOConverterContext(
				_dtoConverterRegistry, objectEntryId,
				LocaleUtil.fromLanguageId(
					objectDefinition.getDefaultLanguageId(), true, false),
				uriInfo, null);

		defaultDTOConverterContext.setAttribute("addActions", Boolean.FALSE);

		return defaultDTOConverterContext;
	}

	private long _getPrimaryKey1(
		ObjectDefinition systemObjectDefinition,
		ObjectDefinition objectDefinition, long objectEntryId,
		long relatedObjectEntryId) {

		if (objectDefinition.getObjectDefinitionId() ==
				systemObjectDefinition.getObjectDefinitionId()) {

			return objectEntryId;
		}

		return relatedObjectEntryId;
	}

	private long _getPrimaryKey2(
		ObjectDefinition systemObjectDefinition,
		ObjectDefinition objectDefinition, long objectEntryId,
		long relatedObjectEntryId) {

		if (objectDefinition.getObjectDefinitionId() ==
				systemObjectDefinition.getObjectDefinitionId()) {

			return relatedObjectEntryId;
		}

		return objectEntryId;
	}

	private ObjectDefinition _getRelatedObjectDefinition(
			ObjectDefinition objectDefinition,
			ObjectRelationship objectRelationship)
		throws Exception {

		long objectDefinitionId1 = objectRelationship.getObjectDefinitionId1();

		if (objectDefinitionId1 != objectDefinition.getObjectDefinitionId()) {
			return _objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId1());
		}

		return _objectDefinitionLocalService.getObjectDefinition(
			objectRelationship.getObjectDefinitionId2());
	}

	private String _getRESTContextPath(ObjectDefinition objectDefinition) {
		if (objectDefinition.isSystem()) {
			SystemObjectDefinitionMetadata systemObjectDefinitionMetadata =
				_systemObjectDefinitionMetadataTracker.
					getSystemObjectDefinitionMetadata(
						objectDefinition.getName());

			return systemObjectDefinitionMetadata.getRESTContextPath();
		}

		return objectDefinition.getRESTContextPath();
	}

	private String _getRESTContextPath(String previousPath) {
		URI uri = _uriInfo.getBaseUri();

		String path = uri.getPath();

		return path.split("/")[2] + "/v1.0/" + previousPath;
	}

	private ObjectDefinition _getSystemObjectDefinition(
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		List<ObjectDefinition> systemObjectDefinitions =
			_objectDefinitionLocalService.getSystemObjectDefinitions();

		for (ObjectDefinition systemObjectDefinition :
				systemObjectDefinitions) {

			if (Objects.equals(
					systemObjectDefinition.getName(),
					systemObjectDefinitionMetadata.getName())) {

				return systemObjectDefinition;
			}
		}

		return null;
	}

	private SystemObjectDefinitionMetadata _getSystemObjectDefinitionMetadata(
		String restContextPath) {

		for (ObjectDefinition systemObjectDefinition :
				_objectDefinitionLocalService.getSystemObjectDefinitions()) {

			SystemObjectDefinitionMetadata systemObjectDefinitionMetadata =
				_systemObjectDefinitionMetadataTracker.
					getSystemObjectDefinitionMetadata(
						systemObjectDefinition.getName());

			if (StringUtil.equals(
					systemObjectDefinitionMetadata.getRESTContextPath(),
					restContextPath)) {

				return systemObjectDefinitionMetadata;
			}
		}

		return null;
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryManagerTracker _objectEntryManagerTracker;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private ObjectRelationshipService _objectRelationshipService;

	@Reference
	private SystemObjectDefinitionMetadataTracker
		_systemObjectDefinitionMetadataTracker;

	@Context
	private UriInfo _uriInfo;

}