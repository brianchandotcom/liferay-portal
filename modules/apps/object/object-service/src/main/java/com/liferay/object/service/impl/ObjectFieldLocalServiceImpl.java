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

import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.exception.ObjectDefinitionStatusException;
import com.liferay.object.exception.ObjectFieldBusinessTypeException;
import com.liferay.object.exception.ObjectFieldDBTypeException;
import com.liferay.object.exception.ObjectFieldLabelException;
import com.liferay.object.exception.ObjectFieldNameException;
import com.liferay.object.exception.ObjectFieldRelationshipTypeException;
import com.liferay.object.exception.RequiredObjectFieldException;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeServicesTracker;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectViewLocalService;
import com.liferay.object.service.base.ObjectFieldLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.service.persistence.ObjectEntryPersistence;
import com.liferay.object.service.persistence.ObjectFieldSettingPersistence;
import com.liferay.object.service.persistence.ObjectLayoutColumnPersistence;
import com.liferay.object.service.persistence.ObjectViewColumnPersistence;
import com.liferay.object.service.persistence.ObjectViewPersistence;
import com.liferay.object.service.persistence.ObjectViewSortColumnPersistence;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

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
			String businessType, String dbType, boolean indexed,
			boolean indexedAsKeyword, String indexedLanguageId,
			Map<Locale, String> labelMap, String name, boolean required,
			List<ObjectFieldSetting> objectFieldSettings)
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
			name + StringPool.UNDERLINE, dbTableName, dbType, indexed,
			indexedAsKeyword, indexedLanguageId, labelMap, name, required);

		if (objectDefinition.isApproved()) {
			runSQL(
				DynamicObjectDefinitionTable.getAlterTableAddColumnSQL(
					dbTableName, objectField.getDBColumnName(), dbType));
		}

		_addOrUpdateObjectFieldSettings(objectFieldSettings, objectField);

		return objectField;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectField addSystemObjectField(
			long userId, long objectDefinitionId, String businessType,
			String dbColumnName, String dbType, boolean indexed,
			boolean indexedAsKeyword, String indexedLanguageId,
			Map<Locale, String> labelMap, String name, boolean required)
		throws PortalException {

		name = StringUtil.trim(name);

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		if (Validator.isNull(dbColumnName)) {
			dbColumnName = name;
		}

		return _addObjectField(
			userId, 0, objectDefinitionId, businessType, dbColumnName,
			objectDefinition.getDBTableName(), dbType, indexed,
			indexedAsKeyword, indexedLanguageId, labelMap, name, required);
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

		if (Validator.isNotNull(objectField.getRelationshipType())) {
			throw new ObjectFieldRelationshipTypeException(
				"Object field cannot be deleted because it has a " +
					"relationship type");
		}

		return _deleteObjectField(objectField);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public ObjectField deleteRelationshipTypeObjectField(long objectFieldId)
		throws PortalException {

		ObjectField objectField = objectFieldPersistence.findByPrimaryKey(
			objectFieldId);

		if (Validator.isNull(objectField.getRelationshipType())) {
			throw new ObjectFieldRelationshipTypeException(
				"Object field cannot be deleted because it does not have a " +
					"relationship type");
		}

		return _deleteObjectField(objectField);
	}

	@Override
	public ObjectField fetchObjectField(long objectDefinitionId, String name) {
		return objectFieldPersistence.fetchByODI_N(objectDefinitionId, name);
	}

	@Override
	public ObjectField getObjectField(long objectFieldId)
		throws PortalException {

		ObjectField objectField = objectFieldPersistence.findByPrimaryKey(
			objectFieldId);

		objectField.setObjectFieldSettings(
			_objectFieldSettingPersistence.findByObjectFieldId(objectFieldId));

		return objectField;
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
			String dbType, boolean indexed, boolean indexedAsKeyword,
			String indexedLanguageId, Map<Locale, String> labelMap, String name,
			boolean required, List<ObjectFieldSetting> objectFieldSettings)
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
			objectField = objectFieldPersistence.update(objectField);

			_addOrUpdateObjectFieldSettings(objectFieldSettings, objectField);

			return objectField;
		}

		_validateIndexed(
			businessType, dbType, indexed, indexedAsKeyword, indexedLanguageId);

		if (Validator.isNotNull(objectField.getRelationshipType())) {
			if (!Objects.equals(objectField.getDBType(), dbType) ||
				!Objects.equals(objectField.getName(), name)) {

				throw new ObjectFieldRelationshipTypeException(
					"Object field relationship name and DB type cannot be " +
						"changed");
			}
		}
		else {
			_validateName(objectFieldId, objectDefinition, name);
		}

		String oldBusinessType = objectField.getBusinessType();

		_setBusinessTypeAndDBType(businessType, dbType, objectField);

		objectField.setListTypeDefinitionId(listTypeDefinitionId);
		objectField.setDBColumnName(name + StringPool.UNDERLINE);
		objectField.setIndexed(indexed);
		objectField.setIndexedAsKeyword(indexedAsKeyword);
		objectField.setIndexedLanguageId(indexedLanguageId);
		objectField.setName(name);
		objectField.setRequired(required);

		objectField = objectFieldPersistence.update(objectField);

		if (!Objects.equals(objectField.getBusinessType(), oldBusinessType)) {
			_objectFieldSettingPersistence.removeByObjectFieldId(objectFieldId);
		}

		_addOrUpdateObjectFieldSettings(objectFieldSettings, objectField);

		return objectField;
	}

	private ObjectField _addObjectField(
			long userId, long listTypeDefinitionId, long objectDefinitionId,
			String businessType, String dbColumnName, String dbTableName,
			String dbType, boolean indexed, boolean indexedAsKeyword,
			String indexedLanguageId, Map<Locale, String> labelMap, String name,
			boolean required)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		_validateIndexed(
			businessType, dbType, indexed, indexedAsKeyword, indexedLanguageId);
		_validateLabel(labelMap);
		_validateName(0, objectDefinition, name);

		ObjectField objectField = objectFieldPersistence.create(
			counterLocalService.increment());

		_setBusinessTypeAndDBType(businessType, dbType, objectField);

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
		objectField.setRelationshipType(null);
		objectField.setRequired(required);

		return objectFieldPersistence.update(objectField);
	}

	private void _addOrUpdateObjectFieldSettings(
			List<ObjectFieldSetting> newObjectFieldSettings,
			ObjectField objectField)
		throws PortalException {

		ObjectFieldBusinessType objectFieldBusinessType =
			_objectFieldBusinessTypeServicesTracker.getObjectFieldBusinessType(
				objectField.getBusinessType());

		objectFieldBusinessType.validateObjectFieldSettings(
			objectField.getName(), newObjectFieldSettings);

		for (ObjectFieldSetting newObjectFieldSetting :
				newObjectFieldSettings) {

			ObjectFieldSetting oldObjectFieldSetting =
				_objectFieldSettingPersistence.fetchByOFI_N(
					objectField.getObjectFieldId(),
					newObjectFieldSetting.getName());

			if (oldObjectFieldSetting == null) {
				_objectFieldSettingLocalService.addObjectFieldSetting(
					objectField.getUserId(), objectField.getObjectFieldId(),
					newObjectFieldSetting.getName(),
					newObjectFieldSetting.getValue());
			}
			else {
				_objectFieldSettingLocalService.updateObjectFieldSetting(
					oldObjectFieldSetting.getObjectFieldSettingId(),
					newObjectFieldSetting.getValue());
			}
		}
	}

	private void _deleteFileEntries(
		long objectDefinitionId, String objectFieldName) {

		List<ObjectEntry> objectEntries =
			_objectEntryPersistence.findByObjectDefinitionId(
				objectDefinitionId);

		for (ObjectEntry objectEntry : objectEntries) {
			Map<String, Serializable> values = objectEntry.getValues();

			try {
				_dlFileEntryLocalService.deleteFileEntry(
					GetterUtil.getLong(values.get(objectFieldName)));
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}
			}
		}
	}

	private ObjectField _deleteObjectField(ObjectField objectField)
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

		_objectFieldSettingPersistence.removeByObjectFieldId(
			objectField.getObjectFieldId());

		_objectLayoutColumnPersistence.removeByObjectFieldId(
			objectField.getObjectFieldId());

		_objectViewLocalService.unassociateObjectField(objectField);

		if (Objects.equals(
				objectDefinition.getExtensionDBTableName(),
				objectField.getDBTableName())) {

			if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

				_deleteFileEntries(
					objectField.getObjectDefinitionId(), objectField.getName());
			}

			runSQL(
				DynamicObjectDefinitionTable.getAlterTableDropColumnSQL(
					objectField.getDBTableName(),
					objectField.getDBColumnName()));
		}

		return objectField;
	}

	private void _setBusinessTypeAndDBType(
			String businessType, String dbType, ObjectField objectField)
		throws PortalException {

		ObjectFieldBusinessType objectFieldBusinessType =
			_objectFieldBusinessTypeServicesTracker.getObjectFieldBusinessType(
				GetterUtil.getString(businessType));

		Set<String> objectFieldDBTypes =
			_objectFieldBusinessTypeServicesTracker.getObjectFieldDBTypes();

		if (objectFieldBusinessType != null) {
			objectField.setBusinessType(businessType);
			objectField.setDBType(objectFieldBusinessType.getDBType());
		}
		else if (objectFieldDBTypes.contains(dbType) &&
				 _businessTypes.containsKey(dbType)) {

			objectField.setBusinessType(_businessTypes.get(dbType));
			objectField.setDBType(dbType);
		}
		else {
			if (!businessType.isEmpty()) {
				throw new ObjectFieldBusinessTypeException(
					"Invalid business type " + businessType);
			}

			throw new ObjectFieldDBTypeException("Invalid DB type " + dbType);
		}
	}

	private void _validateIndexed(
			String businessType, String dbType, boolean indexed,
			boolean indexedAsKeyword, String indexedLanguageId)
		throws PortalException {

		if (indexed &&
			Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_BLOB)) {

			throw new ObjectFieldDBTypeException("Blob type is not indexable");
		}

		if (((!Objects.equals(
				businessType, ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT) &&
			  !Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_STRING)) ||
			 indexedAsKeyword) &&
			!Validator.isBlank(indexedLanguageId)) {

			throw new ObjectFieldDBTypeException(
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

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectFieldLocalServiceImpl.class);

	private final Map<String, String> _businessTypes = HashMapBuilder.put(
		"BigDecimal", "PrecisionDecimal"
	).put(
		"Blob", "LargeFile"
	).put(
		"Boolean", "Boolean"
	).put(
		"Clob", "LongText"
	).put(
		"Date", "Date"
	).put(
		"Double", "Decimal"
	).put(
		"Integer", "Integer"
	).put(
		"Long", "LongInteger"
	).put(
		"String", "Text"
	).build();

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectEntryPersistence _objectEntryPersistence;

	@Reference
	private ObjectFieldBusinessTypeServicesTracker
		_objectFieldBusinessTypeServicesTracker;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Reference
	private ObjectFieldSettingPersistence _objectFieldSettingPersistence;

	@Reference
	private ObjectLayoutColumnPersistence _objectLayoutColumnPersistence;

	@Reference
	private ObjectViewColumnPersistence _objectViewColumnPersistence;

	@Reference
	private ObjectViewLocalService _objectViewLocalService;

	@Reference
	private ObjectViewPersistence _objectViewPersistence;

	@Reference
	private ObjectViewSortColumnPersistence _objectViewSortColumnPersistence;

	private final Set<String> _reservedNames = SetUtil.fromArray(
		"actions", "companyid", "createdate", "creator", "datecreated",
		"datemodified", "externalreferencecode", "groupid", "id",
		"lastpublishdate", "modifieddate", "status", "statusbyuserid",
		"statusbyusername", "statusdate", "userid", "username");

	@Reference
	private UserLocalService _userLocalService;

}