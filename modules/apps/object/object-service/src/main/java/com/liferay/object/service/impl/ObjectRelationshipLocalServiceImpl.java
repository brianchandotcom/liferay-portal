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

import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.ObjectRelationshipNameException;
import com.liferay.object.exception.ObjectRelationshipTypeException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.base.ObjectRelationshipLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

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

		name = StringUtil.trim(StringUtil.toLowerCase(name));

		_validate(name, type);

		long objectRelationshipId = counterLocalService.increment();

		ObjectRelationship objectRelationship =
			objectRelationshipPersistence.create(objectRelationshipId);

		User user = _userLocalService.getUser(userId);

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

		if (Objects.equals(type, ObjectRelationshipConstants.TYPE_ONE_TO_ONE) ||
			Objects.equals(
				type, ObjectRelationshipConstants.TYPE_MANY_TO_ONE)) {

			ObjectField objectField = _addObjectField(
				userId, name, objectDefinitionId1);

			objectRelationship.setObjectFieldId1(
				objectField.getObjectFieldId());
		}

		if (Objects.equals(type, ObjectRelationshipConstants.TYPE_ONE_TO_ONE) ||
			Objects.equals(
				type, ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

			ObjectField objectField = _addObjectField(
				userId, name, objectDefinitionId2);

			objectRelationship.setObjectFieldId2(
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

	private ObjectField _addObjectField(
			long userId, String name, long objectDefinitionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		return _objectFieldLocalService.addRelationshipObjectField(
			userId, null, objectDefinitionId,
			objectDefinition.getLabelMap(), name);
	}

	private void _validate(String name, String type) throws PortalException {

		// TODO

		if (Validator.isNull(name)) {
			throw new ObjectRelationshipNameException();
		}

		if (Validator.isNull(type)) {
			throw new ObjectRelationshipTypeException();
		}
	}

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private UserLocalService _userLocalService;

}