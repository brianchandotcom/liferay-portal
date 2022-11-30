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
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;

/**
 * @author Carlos Correa
 */
public class ObjectEntryRelatedObjectsResourceImpl
	extends BaseObjectEntryRelatedObjectsResourceImpl {

	public ObjectEntryRelatedObjectsResourceImpl(
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryManagerRegistry objectEntryManagerRegistry,
		ObjectRelationshipService objectRelationshipService) {

		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryManagerRegistry = objectEntryManagerRegistry;
		_objectRelationshipService = objectRelationshipService;
	}

	@Override
	public Page<Object> getCurrentObjectEntriesObjectRelationshipNamePage(
			Long currentObjectEntryId, String objectRelationshipName,
			Pagination pagination)
		throws Exception {

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerRegistry.getObjectEntryManager(
				_objectDefinition.getStorageType());

		ObjectRelationship objectRelationship =
			_objectRelationshipService.getObjectRelationship(
				_objectDefinition.getObjectDefinitionId(),
				objectRelationshipName);

		ObjectDefinition relatedObjectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId2());

		if (relatedObjectDefinition.isSystem()) {
			if (!GetterUtil.getBoolean(
					PropsUtil.get("feature.flag.LPS-162966"))) {

				throw new NotFoundException();
			}

			return objectEntryManager.getRelatedSystemObjectEntries(
				_objectDefinition, currentObjectEntryId, objectRelationshipName,
				pagination);
		}

		Page<ObjectEntry> page =
			objectEntryManager.getObjectEntryRelatedObjectEntries(
				_getDTOConverterContext(currentObjectEntryId),
				_objectDefinition, currentObjectEntryId, objectRelationshipName,
				pagination);

		return Page.of(
			page.getActions(),
			transform(
				page.getItems(),
				objectEntry -> _getRelatedObjectEntry(
					relatedObjectDefinition, objectEntry)));
	}

	@Override
	public Object putCurrentObjectEntry(
			Long currentObjectEntryId, String objectRelationshipName,
			Long relatedObjectEntryId)
		throws Exception {

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerRegistry.getObjectEntryManager(
				_objectDefinition.getStorageType());

		ObjectRelationship objectRelationship =
			_objectRelationshipService.getObjectRelationship(
				_objectDefinition.getObjectDefinitionId(),
				objectRelationshipName);

		ObjectDefinition relatedObjectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId2());

		if (relatedObjectDefinition.isSystem()) {
			if (!GetterUtil.getBoolean(
					PropsUtil.get("feature.flag.LPS-162966"))) {

				throw new NotFoundException();
			}

			return objectEntryManager.
				addSystemObjectRelationshipMappingTableValues(
					relatedObjectDefinition, objectRelationship,
					currentObjectEntryId, relatedObjectEntryId);
		}

		return _getRelatedObjectEntry(
			relatedObjectDefinition,
			objectEntryManager.addObjectRelationshipMappingTableValues(
				_getDTOConverterContext(currentObjectEntryId),
				objectRelationship, currentObjectEntryId,
				relatedObjectEntryId));
	}

	private DefaultDTOConverterContext _getDTOConverterContext(
		Long objectEntryId) {

		return new DefaultDTOConverterContext(
			contextAcceptLanguage.isAcceptAllLanguages(), null, null,
			contextHttpServletRequest, objectEntryId,
			contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
			contextUser);
	}

	private ObjectEntry _getRelatedObjectEntry(
		ObjectDefinition objectDefinition, ObjectEntry objectEntry) {

		Map<String, Map<String, String>> actions = objectEntry.getActions();

		for (Map.Entry<String, Map<String, String>> entry :
				actions.entrySet()) {

			Map<String, String> map = entry.getValue();

			if (map == null) {
				continue;
			}

			String href = map.get("href");

			map.put(
				"href",
				StringUtil.replace(
					href,
					StringUtil.lowerCaseFirstLetter(
						_objectDefinition.getPluralLabel(
							contextAcceptLanguage.getPreferredLocale())),
					StringUtil.lowerCaseFirstLetter(
						objectDefinition.getPluralLabel(
							contextAcceptLanguage.getPreferredLocale()))));
		}

		return objectEntry;
	}

	@Context
	private ObjectDefinition _objectDefinition;

	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryManagerRegistry _objectEntryManagerRegistry;
	private final ObjectRelationshipService _objectRelationshipService;

}