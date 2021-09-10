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
import com.liferay.object.exception.DuplicateObjectRelationshipException;
import com.liferay.object.exception.ObjectRelationshipNameException;
import com.liferay.object.exception.ObjectRelationshipTypeException;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.base.ObjectRelationshipLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.service.persistence.ObjectFieldPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
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
			long userId, long objectDefinitionId1, long objectDefinitionId2,
			Map<Locale, String> labelMap, String name, String type)
		throws PortalException {

		_validate(objectDefinitionId1, objectDefinitionId2, name, type);

		ObjectRelationship objectRelationship =
			objectRelationshipPersistence.create(
				counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		objectRelationship.setCompanyId(user.getCompanyId());
		objectRelationship.setUserId(user.getUserId());
		objectRelationship.setUserName(user.getFullName());

		objectRelationship.setObjectDefinitionId1(objectDefinitionId1);
		objectRelationship.setObjectDefinitionId2(objectDefinitionId2);
		objectRelationship.setLabelMap(labelMap);
		objectRelationship.setName(name);
		objectRelationship.setType(type);

		if (Objects.equals(type, ObjectRelationshipConstants.TYPE_ONE_TO_ONE) ||
			Objects.equals(
				type, ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

			ObjectField objectField = _addObjectField(
				user, name, objectDefinitionId1, objectDefinitionId2);

			objectRelationship.setObjectFieldId2(
				objectField.getObjectFieldId());
		}
		else if (Objects.equals(
					type, ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

			ObjectDefinition objectDefinition1 =
				_objectDefinitionPersistence.findByPrimaryKey(
					objectDefinitionId1);
			ObjectDefinition objectDefinition2 =
				_objectDefinitionPersistence.findByPrimaryKey(
					objectDefinitionId2);

			objectRelationship.setDBTableName(
				StringBundler.concat(
					"R_", user.getCompanyId(), objectDefinition1.getShortName(),
					"_", objectDefinition2.getShortName(), "_", name));

			runSQL(
				StringBundler.concat(
					"create table ", objectRelationship.getDBTableName(), " (",
					objectDefinition1.getPKObjectFieldDBColumnName(),
					" LONG not null,",
					objectDefinition2.getPKObjectFieldDBColumnName(),
					" LONG not null, primary key (",
					objectDefinition1.getPKObjectFieldDBColumnName(), ", ",
					objectDefinition2.getPKObjectFieldDBColumnName(), "))"));
		}

		return objectRelationshipPersistence.update(objectRelationship);
	}

	@Override
	public void addObjectRelationshipMappingTableValues(
			long objectRelationshipId, long primaryKey1, long primaryKey2)
		throws PortalException {

		ObjectRelationship objectRelationship =
			objectRelationshipPersistence.findByPrimaryKey(
				objectRelationshipId);

		ObjectDefinition objectDefinition1 =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectRelationship.getObjectDefinitionId1());
		ObjectDefinition objectDefinition2 =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectRelationship.getObjectDefinitionId2());

		runSQL(
			StringBundler.concat(
				"insert into ", objectRelationship.getDBTableName(), " (",
				objectDefinition1.getPKObjectFieldDBColumnName(), " , ",
				objectDefinition2.getPKObjectFieldDBColumnName(), ") values (",
				primaryKey1, ", ", primaryKey2, ")"));
	}

	@Override
	public ObjectRelationship deleteObjectRelationship(
			long objectRelationshipId)
		throws PortalException {

		ObjectRelationship objectRelationship =
			objectRelationshipPersistence.findByPrimaryKey(
				objectRelationshipId);

		return deleteObjectRelationship(objectRelationship);
	}

	@Override
	public ObjectRelationship deleteObjectRelationship(
			ObjectRelationship objectRelationship)
		throws PortalException {

		// TODO When should we allow an object relationship to be deleted?

		objectRelationship = objectRelationshipPersistence.remove(
			objectRelationship);

		if (Objects.equals(
				objectRelationship.getType(),
				ObjectRelationshipConstants.TYPE_ONE_TO_ONE) ||
			Objects.equals(
				objectRelationship.getType(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

			ObjectField objectField = _objectFieldPersistence.remove(
				objectRelationship.getObjectFieldId2());

			runSQL(
				DynamicObjectDefinitionTable.getAlterTableDropColumnSQL(
					objectField.getDBTableName(),
					objectField.getDBColumnName()));
		}
		else if (Objects.equals(
					objectRelationship.getType(),
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

			runSQL("drop table " + objectRelationship.getDBTableName());
		}

		return objectRelationship;
	}

	@Override
	public List<ObjectRelationship> getObjectRelationships(
		long objectDefinitionId1, int start, int end) {

		return objectRelationshipPersistence.findByObjectDefinitionId1(
			objectDefinitionId1, start, end);
	}

	private ObjectField _addObjectField(
			User user, String name, long objectDefinitionId1,
			long objectDefinitionId2)
		throws PortalException {

		ObjectField objectField = _objectFieldPersistence.create(
			counterLocalService.increment());

		objectField.setCompanyId(user.getCompanyId());
		objectField.setUserId(user.getUserId());
		objectField.setUserName(user.getFullName());
		objectField.setListTypeDefinitionId(0);
		objectField.setObjectDefinitionId(objectDefinitionId2);

		ObjectDefinition objectDefinition1 =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId1);

		String dbColumnName = StringBundler.concat(
			"r_", name, "_", objectDefinition1.getPKObjectFieldName());

		objectField.setDBColumnName(dbColumnName);

		ObjectDefinition objectDefinition2 =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId2);

		String dbTableName = objectDefinition2.getDBTableName();

		if (objectDefinition2.isApproved()) {
			dbTableName = objectDefinition2.getExtensionDBTableName();
		}

		objectField.setDBTableName(dbTableName);

		objectField.setIndexed(false);
		objectField.setIndexedAsKeyword(false);
		objectField.setIndexedLanguageId(null);
		objectField.setLabelMap(
			objectDefinition1.getLabelMap(), LocaleUtil.getSiteDefault());
		objectField.setName(dbColumnName);
		objectField.setRelationship(true);
		objectField.setRequired(false);
		objectField.setType("Long");

		objectField = _objectFieldPersistence.update(objectField);

		if (objectDefinition2.isApproved()) {
			runSQL(
				DynamicObjectDefinitionTable.getAlterTableAddColumnSQL(
					dbTableName, objectField.getDBColumnName(), "Long"));
		}

		return objectField;
	}

	private void _validate(
			long objectDefinitionId1, long objectDefinitionId2, String name,
			String type)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new ObjectRelationshipNameException("Name is null");
		}

		char[] nameCharArray = name.toCharArray();

		for (char c : nameCharArray) {
			if (!Validator.isChar(c) && !Validator.isDigit(c)) {
				throw new ObjectRelationshipNameException(
					"Name must only contain letters and digits");
			}
		}

		if (!Character.isLowerCase(nameCharArray[0])) {
			throw new ObjectRelationshipNameException(
				"The first character of a name must be a lower case letter");
		}

		if (nameCharArray.length > 41) {
			throw new ObjectRelationshipNameException(
				"Names must be less than 41 characters");
		}

		ObjectRelationship objectRelationship =
			objectRelationshipPersistence.fetchByODI1_N(
				objectDefinitionId1, name);

		if (objectRelationship != null) {
			throw new DuplicateObjectRelationshipException(
				"Duplicate name " + name);
		}

		if (!Objects.equals(
				type, ObjectRelationshipConstants.TYPE_MANY_TO_MANY) &&
			!Objects.equals(
				type, ObjectRelationshipConstants.TYPE_ONE_TO_MANY) &&
			!Objects.equals(
				type, ObjectRelationshipConstants.TYPE_ONE_TO_ONE)) {

			throw new ObjectRelationshipTypeException("Invalid type " + type);
		}

		if (Objects.equals(
				type, ObjectRelationshipConstants.TYPE_MANY_TO_MANY) ||
			Objects.equals(type, ObjectRelationshipConstants.TYPE_ONE_TO_ONE)) {

			int count = objectRelationshipPersistence.countByODI1_ODI2_N_T(
				objectDefinitionId2, objectDefinitionId1, name, type);

			if (count > 0) {
				throw new ObjectRelationshipTypeException(
					"Inverse type already exists");
			}
		}
	}

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectFieldPersistence _objectFieldPersistence;

	@Reference
	private UserLocalService _userLocalService;

}