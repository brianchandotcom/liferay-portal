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
import com.liferay.object.exception.ObjectDefinitionStatusException;
import com.liferay.object.exception.ObjectFieldLabelException;
import com.liferay.object.exception.ObjectFieldNameException;
import com.liferay.object.exception.ObjectFieldTypeException;
import com.liferay.object.exception.ReservedObjectFieldException;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.base.ObjectFieldLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectField addCustomObjectField(
			long userId, long listTypeDefinitionId, long objectDefinitionId,
			boolean indexed, boolean indexedAsKeyword, String indexedLanguageId,
			Map<Locale, String> labelMap, String name, boolean required,
			String type)
		throws PortalException {

		name = StringUtil.trim(name);

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		String dbTableName = objectDefinition.getDBTableName();

		if (objectDefinition.isApproved()) {
			dbTableName = objectDefinition.getExtensionDBTableName();
		}

		ObjectField objectField = _addObjectField(
			userId, listTypeDefinitionId, objectDefinitionId,
			name + StringPool.UNDERLINE, dbTableName, indexed, indexedAsKeyword,
			indexedLanguageId, labelMap, name, false, required, type);

		if (objectDefinition.isApproved()) {
			runSQL(
				DynamicObjectDefinitionTable.getAlterTableAddColumnSQL(
					dbTableName, objectField.getDBColumnName(), type));
		}

		return objectField;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectField addRelationshipObjectField(
			long userId, long objectDefinitionId, Map<Locale, String> labelMap,
			String name)
		throws PortalException {

		name = StringUtil.trim(name);

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		name = StringBundler.concat(
			"r_", name, "_", objectDefinition.getPKObjectFieldName());

		String dbTableName = objectDefinition.getDBTableName();

		if (objectDefinition.isApproved()) {
			dbTableName = objectDefinition.getExtensionDBTableName();
		}

		ObjectField objectField = _addObjectField(
			userId, 0, objectDefinitionId, name, dbTableName, false, false,
			null, labelMap, name, true, false, "Long");

		if (objectDefinition.isApproved()) {
			runSQL(
				DynamicObjectDefinitionTable.getAlterTableAddColumnSQL(
					dbTableName, objectField.getDBColumnName(), "Long"));
		}

		return objectField;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectField addSystemObjectField(
			long userId, long objectDefinitionId, String dbColumnName,
			boolean indexed, boolean indexedAsKeyword, String indexedLanguageId,
			Map<Locale, String> labelMap, String name, boolean required,
			String type)
		throws PortalException {

		name = StringUtil.trim(name);

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		if (Validator.isNull(dbColumnName)) {
			dbColumnName = name;
		}

		return _addObjectField(
			userId, 0, objectDefinitionId, dbColumnName,
			objectDefinition.getDBTableName(), indexed, indexedAsKeyword,
			indexedLanguageId, labelMap, name, false, required, type);
	}

	@Override
	public ObjectField fetchObjectField(long objectDefinitionId, String name) {
		return objectFieldPersistence.fetchByODI_N(objectDefinitionId, name);
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

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectField updateCustomObjectField(
			long objectFieldId, long listTypeDefinitionId, boolean indexed,
			boolean indexedAsKeyword, String indexedLanguageId,
			Map<Locale, String> labelMap, String name, boolean required,
			String type)
		throws PortalException {

		ObjectField objectField = objectFieldPersistence.findByPrimaryKey(
			objectFieldId);

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectField.getObjectDefinitionId());

		if (objectDefinition.isSystem()) {
			throw new ObjectDefinitionStatusException();
		}

		_validateLabel(labelMap, LocaleUtil.getSiteDefault());

		objectField.setLabelMap(labelMap, LocaleUtil.getSiteDefault());

		if (objectDefinition.isApproved()) {
			return objectFieldPersistence.update(objectField);
		}

		_validateIndexed(indexed, indexedAsKeyword, indexedLanguageId, type);
		_validateName(objectFieldId, objectDefinition, name);
		validateType(type);

		objectField.setListTypeDefinitionId(listTypeDefinitionId);
		objectField.setIndexed(indexed);
		objectField.setIndexedAsKeyword(indexedAsKeyword);
		objectField.setIndexedLanguageId(indexedLanguageId);
		objectField.setName(name);
		objectField.setRequired(required);
		objectField.setType(type);

		return objectFieldPersistence.update(objectField);
	}

	@Override
	public void validateType(String type) throws PortalException {
		if (!_types.contains(type)) {
			throw new ObjectFieldTypeException("Invalid type " + type);
		}
	}

	private ObjectField _addObjectField(
			long userId, long listTypeDefinitionId, long objectDefinitionId,
			String dbColumnName, String dbTableName, boolean indexed,
			boolean indexedAsKeyword, String indexedLanguageId,
			Map<Locale, String> labelMap, String name, boolean relationship,
			boolean required, String type)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		_validateIndexed(indexed, indexedAsKeyword, indexedLanguageId, type);
		_validateLabel(labelMap, LocaleUtil.getSiteDefault());
		_validateName(0, objectDefinition, name);
		validateType(type);

		ObjectField objectField = objectFieldPersistence.create(
			counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		objectField.setCompanyId(user.getCompanyId());
		objectField.setUserId(user.getUserId());
		objectField.setUserName(user.getFullName());

		objectField.setListTypeDefinitionId(listTypeDefinitionId);
		objectField.setObjectDefinitionId(objectDefinitionId);
		objectField.setDBColumnName(dbColumnName);
		objectField.setDBTableName(dbTableName);
		objectField.setIndexed(indexed);
		objectField.setIndexedAsKeyword(indexedAsKeyword);
		objectField.setIndexedLanguageId(indexedLanguageId);
		objectField.setLabelMap(labelMap, LocaleUtil.getSiteDefault());
		objectField.setName(name);
		objectField.setRelationship(relationship);
		objectField.setRequired(required);
		objectField.setType(type);

		return objectFieldPersistence.update(objectField);
	}

	private void _validateIndexed(
			boolean indexed, boolean indexedAsKeyword, String indexedLanguageId,
			String type)
		throws PortalException {

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

	private void _validateLabel(
			Map<Locale, String> labelMap, Locale defaultLocale)
		throws PortalException {

		if ((labelMap == null) ||
			Validator.isNull(labelMap.get(defaultLocale))) {

			throw new ObjectFieldLabelException(
				"Label is null for locale " + defaultLocale.getDisplayName());
		}
	}

	private void _validateName(
			long objectFieldId, ObjectDefinition objectDefinition, String name)
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

		if ((objectField != null) &&
			(objectField.getObjectFieldId() != objectFieldId)) {

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