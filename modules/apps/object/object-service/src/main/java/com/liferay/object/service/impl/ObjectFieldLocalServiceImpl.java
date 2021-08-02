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

import com.liferay.object.exception.DuplicateObjectFieldException;
import com.liferay.object.exception.ObjectFieldNameException;
import com.liferay.object.exception.ObjectFieldTypeException;
import com.liferay.object.exception.ReservedObjectFieldException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.base.ObjectFieldLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.liferay.portal.kernel.workflow.WorkflowConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectField",
	service = AopService.class
)
public class ObjectFieldLocalServiceImpl
	extends ObjectFieldLocalServiceBaseImpl {

	@Override
	public ObjectField addCustomObjectField(
			long userId, long objectDefinitionId,
			boolean indexed, boolean indexedAsKeyword, String indexedLanguageId,
			String name, boolean required, String type)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		String dbTableName = objectDefinition.getDBTableName();

		if(objectDefinition.getStatus() == WorkflowConstants.STATUS_APPROVED){
			dbTableName = objectDefinition.getExtensionDBTableName();
		}

		return _addObjectField(
			userId, objectDefinitionId, name + StringPool.UNDERLINE, dbTableName, indexed,
			indexedAsKeyword, indexedLanguageId, name, required, type);

	}

	@Override
	public ObjectField addSystemObjectField(
			long userId, long objectDefinitionId, String dbColumnName,
			boolean indexed, boolean indexedAsKeyword, String indexedLanguageId,
			String name, boolean required, String type)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		if(Validator.isNull(dbColumnName)){
			dbColumnName = name;
		}

		return _addObjectField(
			userId, objectDefinitionId, dbColumnName,
			objectDefinition.getDBTableName(), indexed, indexedAsKeyword,
			indexedLanguageId, name, required, type);
	}


	private ObjectField _addObjectField(
			long userId, long objectDefinitionId, String dbColumnName, String dbTableName,
			boolean indexed, boolean indexedAsKeyword, String indexedLanguageId,
			String name, boolean required, String type)
		throws PortalException {

		name = StringUtil.trim(name);

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);



		_validateIndexed(indexed, indexedAsKeyword, indexedLanguageId, type);
		_validateName(objectDefinition, name);
		validateType(type);

		ObjectField objectField = objectFieldPersistence.create(
			counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		objectField.setCompanyId(user.getCompanyId());
		objectField.setUserId(user.getUserId());
		objectField.setUserName(user.getFullName());

		objectField.setObjectDefinitionId(objectDefinitionId);
		objectField.setDBColumnName(dbColumnName);
		objectField.setIndexed(indexed);
		objectField.setIndexedAsKeyword(indexedAsKeyword);
		objectField.setIndexedLanguageId(indexedLanguageId);
		objectField.setName(name);
		objectField.setRequired(required);
		objectField.setType(type);

		return objectFieldPersistence.update(objectField);
	}

	@Override
	public ObjectField getObjectField(long objectDefinitionId, String name)
		throws PortalException {

		return objectFieldPersistence.findByODI_N(objectDefinitionId, name);
	}

	@Override
	public List<ObjectField> getObjectFields(long objectDefinitionId) {
		return objectFieldPersistence.findByObjectDefinitionId(
			objectDefinitionId);
	}

	@Override
	public int getObjectFieldsCount(long objectDefinitionId) {
		return objectFieldPersistence.countByObjectDefinitionId(
			objectDefinitionId);
	}

	@Override
	public void validateType(String type) throws PortalException {
		if (!_types.contains(type)) {
			throw new ObjectFieldTypeException("Invalid type " + type);
		}
	}

	private void _validateIndexed(
			boolean indexed, boolean indexedAsKeyword, String indexedLanguageId,
			String type)
		throws PortalException {

		// TODO Add a test for this

		if (indexed && Objects.equals(type, "Blob")) {
			throw new ObjectFieldTypeException("Blob type is not indexable");
		}

		if ((!Objects.equals(type, "String") || indexedAsKeyword) &&
			!Validator.isBlank(indexedLanguageId)) {

			throw new ObjectFieldTypeException(
				"Indexed language ID can only be applied with type " +
					"\"String\" that is not indexed as a keyword");
		}
	}

	private void _validateName(ObjectDefinition objectDefinition, String name)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new ObjectFieldNameException("Name is null");
		}

		char[] nameCharArray = name.toCharArray();

		for (char c : nameCharArray) {
			if (!Validator.isChar(c) && !Validator.isDigit(c)) {
				throw new ObjectFieldNameException(
					"Name must only contain letters and digits");
			}
		}

		if (!Character.isLowerCase(nameCharArray[0])) {
			throw new ObjectFieldNameException(
				"The first character of a name must be a lower case letter");
		}

		if (nameCharArray.length > 41) {
			throw new ObjectFieldNameException(
				"Names must be less than 41 characters");
		}

		if (_reservedNames.contains(StringUtil.toLowerCase(name)) ||
			StringUtil.equalsIgnoreCase(
				objectDefinition.getPKObjectFieldName(), name)) {

			throw new ReservedObjectFieldException("Reserved name " + name);
		}

		ObjectField objectField = objectFieldPersistence.fetchByODI_N(
			objectDefinition.getObjectDefinitionId(), name);

		if (objectField != null) {
			throw new DuplicateObjectFieldException("Duplicate name " + name);
		}
	}

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	private final Set<String> _reservedNames = SetUtil.fromArray(
		new String[] {
			"companyid", "createdate", "groupid", "id", "lastpublishdate",
			"modifieddate", "status", "statusbyuserid", "statusbyusername",
			"statusdate", "userid", "username"
		});
	private final Set<String> _types = SetUtil.fromArray(
		new String[] {
			"BigDecimal", "Blob", "Boolean", "Date", "Double", "Integer",
			"Long", "String"
		});

	@Reference
	private UserLocalService _userLocalService;

}