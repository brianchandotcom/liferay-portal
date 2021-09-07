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

package com.liferay.object.service.impl;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.base.ObjectRelationshipLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectRelationship",
	service = AopService.class
)
public class ObjectRelationshipLocalServiceImpl
	extends ObjectRelationshipLocalServiceBaseImpl {

	@Override
	public ObjectRelationship addObjectRelationship(
			long userId, Map<Locale, String> labelMap, String name,
			long objectDefinitionId1, long objectDefinitionId2, String type)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		long objectRelationshipId = counterLocalService.increment();

		ObjectRelationship objectRelationship =
			objectRelationshipPersistence.create(objectRelationshipId);

		objectRelationship.setCompanyId(user.getCompanyId());
		objectRelationship.setUserId(user.getUserId());
		objectRelationship.setUserName(user.getFullName());
		objectRelationship.setLabelMap(labelMap);
		objectRelationship.setName(name);
		objectRelationship.setObjectDefinitionId1(objectDefinitionId1);
		objectRelationship.setObjectDefinitionId2(objectDefinitionId2);
		objectRelationship.setType(type);

		objectRelationship = objectRelationshipPersistence.update(
			objectRelationship);

		if (type.equals(ObjectDefinitionConstants.RELATIONSHIP_ONE_TO_ONE) ||
			type.equals(ObjectDefinitionConstants.RELATIONSHIP_MANY_TO_ONE)) {

			ObjectField objectField = _addRelationshipField(
				userId, name, objectDefinitionId1);

			objectRelationship.setObjectDefinitionId1FieldId(
				objectField.getObjectFieldId());
		}

		if (type.equals(ObjectDefinitionConstants.RELATIONSHIP_ONE_TO_ONE) ||
			type.equals(ObjectDefinitionConstants.RELATIONSHIP_ONE_TO_MANY)) {

			ObjectField objectField = _addRelationshipField(
				userId, name, objectDefinitionId2);

			objectRelationship.setObjectDefinitionId2FieldId(
				objectField.getObjectFieldId());
		}

		return objectRelationshipPersistence.update(objectRelationship);
	}

	@Override
	public List<ObjectRelationship> getObjectRelationships(
		long objectDefinitionId1, int start, int end) {

		return objectRelationshipPersistence.findByObjectDefinitionId1(
			objectDefinitionId1, start, end);
	}

	private ObjectField _addRelationshipField(
			long userId, String relationshipName, long objectDefinitionId)
		throws PortalException {

		ObjectDefinition objectDefinition2 =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		String fieldName = objectDefinition2.getPKObjectFieldName();

		if (fieldName.startsWith("c_")) {
			fieldName = fieldName.substring(2);
		}

		fieldName = StringUtil.trim(relationshipName) + fieldName;

		return _objectFieldLocalService.addCustomObjectField(
			userId, 0, objectDefinitionId, false, false, null,
			objectDefinition2.getLabelMap(), fieldName, false, "Long");
	}

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private UserLocalService _userLocalService;

}