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

import com.liferay.object.exception.ObjectDefinitionStatusException;
import com.liferay.object.exception.ObjectFieldBusinessTypeException;
import com.liferay.object.exception.ObjectFieldLabelException;
import com.liferay.object.exception.ObjectFieldNameException;
import com.liferay.object.exception.ObjectFieldRelationshipTypeException;
import com.liferay.object.exception.ObjectFieldTypeException;
import com.liferay.object.exception.RequiredObjectFieldException;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.base.ObjectFieldLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.service.persistence.ObjectLayoutColumnPersistence;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
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
			String businessType, boolean indexed, boolean indexedAsKeyword,
			String indexedLanguageId, Map<Locale, String> labelMap, String name,
			boolean required, String type)
		throws PortalException {

		name = StringUtil.trim(name);

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		String dbTableName = objectDefinition.getDBTableName();

		if (objectDefinition.isApproved()) {
			dbTableName = objectDefinition.getExtensionDBTableName();
		}

		ObjectField objectField = _addObjectField(
			userId, listTypeDefinitionId, objectDefinitionId, businessType,
			name + StringPool.UNDERLINE, dbTableName, indexed, indexedAsKeyword,
			indexedLanguageId, labelMap, name, required, type);

		if (objectDefinition.isApproved()) {
			runSQL(
				DynamicObjectDefinitionTable.getAlterTableAddColumnSQL(
					dbTableName, objectField.getDBColumnName(), type));
		}

		return objectField;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectField addSystemObjectField(
			long userId, long objectDefinitionId, String businessType,
			String dbColumnName, boolean indexed, boolean indexedAsKeyword,
			String indexedLanguageId, Map<Locale, String> labelMap, String name,
			boolean required, String type)
		throws PortalException {

		name = StringUtil.trim(name);

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		if (Validator.isNull(dbColumnName)) {
			dbColumnName = name;
		}

		return _addObjectField(
			userId, 0, objectDefinitionId, businessType, dbColumnName,
			objectDefinition.getDBTableName(), indexed, indexedAsKeyword,
			indexedLanguageId, labelMap, name, required, type);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public ObjectField deleteObjectField(long objectFieldId)
		throws PortalException {

		ObjectField objectField = objectFieldPersistence.findByPrimaryKey(
			objectFieldId);

		return deleteObjectField(objectField);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public ObjectField deleteObjectField(ObjectField objectField)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectField.getObjectDefinitionId());

		if ((objectDefinition.isApproved() || objectDefinition.isSystem()) &&
			!Objects.equals(
				objectDefinition.getExtensionDBTableName(),
				objectField.getDBTableName())) {

			throw new RequiredObjectFieldException();
		}

		objectField = objectFieldPersistence.remove(objectField);

		_objectLayoutColumnPersistence.removeByObjectFieldId(
			objectField.getObjectFieldId());

		if (Objects.equals(
				objectDefinition.getExtensionDBTableName(),
				objectField.getDBTableName())) {

			runSQL(
				DynamicObjectDefinitionTable.getAlterTableDropColumnSQL(
					objectField.getDBTableName(),
					objectField.getDBColumnName()));
		}

		return objectField;
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
	public List<ObjectField> getObjectFields(
		long objectDefinitionId, String dbTableName) {

		return objectFieldPersistence.findByODI_DTN(
			objectDefinitionId, dbTableName);
	}

	@Override
	public int getObjectFieldsCount(long objectDefinitionId) {
		return objectFieldPersistence.countByObjectDefinitionId(
			objectDefinitionId);
	}

	@Override
	public int getObjectFieldsCountByListTypeDefinitionId(
		long listTypeDefinitionId) {

		return objectFieldPersistence.countByListTypeDefinitionId(
			listTypeDefinitionId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectField updateCustomObjectField(
			long objectFieldId, long listTypeDefinitionId, String businessType,
			boolean indexed, boolean indexedAsKeyword, String indexedLanguageId,
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

		_validateLabel(labelMap);

		objectField.setLabelMap(labelMap, LocaleUtil.getSiteDefault());

		if (objectDefinition.isApproved()) {
			return objectFieldPersistence.update(objectField);
		}

		_validateIndexed(indexed, indexedAsKeyword, indexedLanguageId, type);

		if (Validator.isNotNull(objectField.getRelationshipType())) {
			if (!Objects.equals(objectField.getName(), name) ||
				!Objects.equals(objectField.getType(), type)) {

				throw new ObjectFieldRelationshipTypeException(
					"Object field relationship name and type cannot be " +
						"changed");
			}
		}
		else {
			_validateBusinessType(businessType);
			_validateName(objectFieldId, objectDefinition, name);
			validateType(type);
		}

		objectField.setListTypeDefinitionId(listTypeDefinitionId);
		objectField.setBusinessType(businessType);
		objectField.setDBColumnName(name + StringPool.UNDERLINE);
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
			String businessType, String dbColumnName, String dbTableName,
			boolean indexed, boolean indexedAsKeyword, String indexedLanguageId,
			Map<Locale, String> labelMap, String name, boolean required,
			String type)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		_validateBusinessType(businessType);
		_validateIndexed(indexed, indexedAsKeyword, indexedLanguageId, type);
		_validateLabel(labelMap);
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
		objectField.setBusinessType(businessType);
		objectField.setDBColumnName(dbColumnName);
		objectField.setDBTableName(dbTableName);
		objectField.setIndexed(indexed);
		objectField.setIndexedAsKeyword(indexedAsKeyword);
		objectField.setIndexedLanguageId(indexedLanguageId);
		objectField.setLabelMap(labelMap, LocaleUtil.getSiteDefault());
		objectField.setName(name);
		objectField.setRelationshipType(null);
		objectField.setRequired(required);
		objectField.setType(type);

		return objectFieldPersistence.update(objectField);
	}

	private void _validateBusinessType(String businessType)
		throws PortalException {

		if (Validator.isNotNull(businessType) &&
			!_businessTypes.contains(businessType)) {

			throw new ObjectFieldBusinessTypeException(
				"Invalid business type " + businessType);
		}
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

	private void _validateLabel(Map<Locale, String> labelMap)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		if ((labelMap == null) || Validator.isNull(labelMap.get(locale))) {
			throw new ObjectFieldLabelException(
				"Label is null for locale " + locale.getDisplayName());
		}
	}

	private void _validateName(
			long objectFieldId, ObjectDefinition objectDefinition, String name)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new ObjectFieldNameException.MustNotBeNull();
		}

		char[] nameCharArray = name.toCharArray();

		for (char c : nameCharArray) {
			if (!Validator.isChar(c) && !Validator.isDigit(c)) {
				throw new ObjectFieldNameException.
					MustOnlyContainLettersAndDigits();
			}
		}

		if (!Character.isLowerCase(nameCharArray[0])) {
			throw new ObjectFieldNameException.MustBeginWithLowerCaseLetter();
		}

		if (nameCharArray.length > 41) {
			throw new ObjectFieldNameException.MustBeLessThan41Characters();
		}

		if (_reservedNames.contains(StringUtil.toLowerCase(name)) ||
			StringUtil.equalsIgnoreCase(
				objectDefinition.getPKObjectFieldName(), name)) {

			throw new ObjectFieldNameException.MustNotBeReserved(name);
		}

		ObjectField objectField = objectFieldPersistence.fetchByODI_N(
			objectDefinition.getObjectDefinitionId(), name);

		if ((objectField != null) &&
			(objectField.getObjectFieldId() != objectFieldId)) {

			throw new ObjectFieldNameException.MustNotBeDuplicate(name);
		}
	}

	private final Set<String> _businessTypes = SetUtil.fromArray(
		"Boolean", "Date", "Decimal", "Integer", "LongInteger", "LongText",
		"Picklist", "PrecisionDecimal", "Relationship", "Text");

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectLayoutColumnPersistence _objectLayoutColumnPersistence;

	private final Set<String> _reservedNames = SetUtil.fromArray(
		"companyid", "createdate", "groupid", "id", "lastpublishdate",
		"modifieddate", "status", "statusbyuserid", "statusbyusername",
		"statusdate", "userid", "username");
	private final Set<String> _types = SetUtil.fromArray(
		"BigDecimal", "Blob", "Boolean", "Clob", "Date", "Double", "Integer",
		"Long", "String");

	@Reference
	private UserLocalService _userLocalService;

}