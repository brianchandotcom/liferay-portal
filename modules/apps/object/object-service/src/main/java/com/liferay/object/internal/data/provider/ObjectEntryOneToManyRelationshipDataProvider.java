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

package com.liferay.object.internal.data.provider;

import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.data.provider.RelationshipDataProvider;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Marco Leo
 */
public class ObjectEntryOneToManyRelationshipDataProvider
	implements RelationshipDataProvider<ObjectEntry> {

	public ObjectEntryOneToManyRelationshipDataProvider(
		ObjectDefinition objectDefinition,
		ObjectEntryLocalService objectEntryLocalService) {

		_objectEntryLocalService = objectEntryLocalService;

		_className = objectDefinition.getClassName();
		_objectDefinitionId = objectDefinition.getObjectDefinitionId();
	}

	public String getClassName() {
		return _className;
	}

	public String getObjectRelationshipType() {
		return ObjectRelationshipConstants.TYPE_ONE_TO_MANY;
	}

	public List<ObjectEntry> getRelatedEntities(
			long groupId, long relatedPrimaryKey, long objectRelationshipId,
			int start, int end)
		throws PortalException {

		return _objectEntryLocalService.getOneToManyRelatedObjectEntries(
			groupId, relatedPrimaryKey, objectRelationshipId, start, end);
	}

	private final String _className;
	private final long _objectDefinitionId;
	private final ObjectEntryLocalService _objectEntryLocalService;

}