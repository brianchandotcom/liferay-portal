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

package com.liferay.object.internal.related.models;

import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.RequiredObjectRelationshipException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.Serializable;

import java.util.List;
import java.util.Objects;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectEntry1to1ObjectRelatedModelsProviderImpl
	implements ObjectRelatedModelsProvider<ObjectEntry> {

	public ObjectEntry1to1ObjectRelatedModelsProviderImpl(
		ObjectDefinition objectDefinition,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		_objectEntryLocalService = objectEntryLocalService;
		_objectFieldLocalService = objectFieldLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;

		_className = objectDefinition.getClassName();
	}

	@Override
	public void deleteRelatedModel(
			long userId, long groupId, long objectRelationshipId,
			long primaryKey)
		throws PortalException {

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.getObjectRelationship(
				objectRelationshipId);

		List<ObjectEntry> relatedModels = getRelatedModels(
			groupId, objectRelationshipId, primaryKey, 0, 1);

		if (relatedModels.isEmpty()) {
			return;
		}

		ObjectEntry objectEntry = relatedModels.get(0);

		if (Objects.equals(
				objectRelationship.getDeletionType(),
				ObjectRelationshipConstants.DELETION_TYPE_CASCADE)) {

			_objectEntryLocalService.deleteObjectEntry(
				objectEntry.getObjectEntryId());
		}
		else if (Objects.equals(
					objectRelationship.getDeletionType(),
					ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE)) {

			_objectEntryLocalService.updateObjectEntry(
				userId, objectEntry.getObjectEntryId(),
				HashMapBuilder.<String, Serializable>put(
					() -> {
						ObjectField objectField =
							_objectFieldLocalService.getObjectField(
								objectRelationship.getObjectFieldId2());

						return objectField.getName();
					},
					0
				).build(),
				new ServiceContext());
		}
		else if (Objects.equals(
					objectRelationship.getDeletionType(),
					ObjectRelationshipConstants.DELETION_TYPE_PREVENT)) {

			throw new RequiredObjectRelationshipException(
				StringBundler.concat(
					"Object relationship ",
					objectRelationship.getObjectRelationshipId(),
					" does not allow deletes"));
		}
	}

	@Override
	public void disassociateRelatedModels(
			long userId, long objectRelationshipId, long primaryKey1,
			long primaryKey2)
		throws PortalException {

		_objectEntryLocalService.updateObjectEntry(
			userId, primaryKey1,
			HashMapBuilder.<String, Serializable>put(
				() -> {
					ObjectRelationship objectRelationship =
						_objectRelationshipLocalService.getObjectRelationship(
							objectRelationshipId);

					ObjectField objectField =
						_objectFieldLocalService.getObjectField(
							objectRelationship.getObjectFieldId2());

					return objectField.getName();
				},
				0
			).build(),
			new ServiceContext());
	}

	@Override
	public String getClassName() {
		return _className;
	}

	@Override
	public String getObjectRelationshipType() {
		return ObjectRelationshipConstants.TYPE_ONE_TO_ONE;
	}

	@Override
	public List<ObjectEntry> getRelatedModels(
			long groupId, long objectRelationshipId, long primaryKey, int start,
			int end)
		throws PortalException {

		return _objectEntryLocalService.getOneToManyRelatedObjectEntries(
			groupId, objectRelationshipId, primaryKey, 0, 1);
	}

	@Override
	public int getRelatedModelsCount(
			long groupId, long objectRelationshipId, long primaryKey)
		throws PortalException {

		List<ObjectEntry> relatedModels = getRelatedModels(
			groupId, objectRelationshipId, primaryKey, 0, 1);

		return relatedModels.size();
	}

	private final String _className;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;

}