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
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.exception.DuplicateObjectFieldExternalReferenceCodeException;
import com.liferay.object.exception.ObjectDefinitionStatusException;
import com.liferay.object.exception.ObjectFieldBusinessTypeException;
import com.liferay.object.exception.ObjectFieldDBTypeException;
import com.liferay.object.exception.ObjectFieldDefaultValueException;
import com.liferay.object.exception.ObjectFieldLabelException;
import com.liferay.object.exception.ObjectFieldNameException;
import com.liferay.object.exception.ObjectFieldRelationshipTypeException;
import com.liferay.object.exception.ObjectFieldStateException;
import com.liferay.object.exception.RequiredObjectFieldException;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeServicesTracker;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.object.service.ObjectStateTransitionLocalService;
import com.liferay.object.service.ObjectViewLocalService;
import com.liferay.object.service.base.ObjectFieldLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.service.persistence.ObjectEntryPersistence;
import com.liferay.object.service.persistence.ObjectFieldPersistence;
import com.liferay.object.service.persistence.ObjectFieldSettingPersistence;
import com.liferay.object.service.persistence.ObjectLayoutColumnPersistence;
import com.liferay.object.service.persistence.ObjectRelationshipPersistence;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

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
			String businessType, String dbType, String defaultValue,
			boolean indexed, boolean indexedAsKeyword, String indexedLanguageId,
			Map<Locale, String> labelMap, String name, boolean required,
			boolean state, List<ObjectFieldSetting> objectFieldSettings)
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
			name + StringPool.UNDERLINE, dbTableName, dbType, defaultValue,
			indexed, indexedAsKeyword, indexedLanguageId, labelMap, name,
			required, state, false);

		if (objectDefinition.isApproved() &&
			!Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION)) {

			runSQL(
				DynamicObjectDefinitionTable.getAlterTableAddColumnSQL(
					dbTableName, objectField.getDBColumnName(), dbType));
		}

		_addOrUpdateObjectFieldSettings(objectField, objectFieldSettings);

		_objectStateFlowLocalService.addDefaultObjectStateFlow(objectField);

		return objectField;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectField addOrUpdateSystemObjectField(
			long userId, long objectDefinitionId, String businessType,
			String dbColumnName, String dbTableName, String dbType,
			String defaultValue, boolean indexed, boolean indexedAsKeyword,
			String indexedLanguageId, Map<Locale, String> labelMap, String name,
			boolean required, boolean state)
		throws PortalException {

		ObjectField existingObjectField = objectFieldPersistence.fetchByODI_N(
			objectDefinitionId, name);

		if (existingObjectField == null) {
			return addSystemObjectField(
				userId, objectDefinitionId, businessType, dbColumnName,
				dbTableName, dbType, defaultValue, indexed, indexedAsKeyword,
				indexedLanguageId, labelMap, name, required, state);
		}

		_validateLabel(labelMap);

		existingObjectField.setLabelMap(labelMap, LocaleUtil.getSiteDefault());

		return objectFieldPersistence.update(existingObjectField);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectField addSystemObjectField(
			long userId, long objectDefinitionId, String businessType,
			String dbColumnName, String dbTableName, String dbType,
			String defaultValue, boolean indexed, boolean indexedAsKeyword,
			String indexedLanguageId, Map<Locale, String> labelMap, String name,
			boolean required, boolean state)
		throws PortalException {

		name = StringUtil.trim(name);

		if (Validator.isNull(dbColumnName)) {
			dbColumnName = name;
		}

		return _addObjectField(
			userId, 0, objectDefinitionId, businessType, dbColumnName,
			dbTableName, dbType, defaultValue, indexed, indexedAsKeyword,
			indexedLanguageId, labelMap, name, required, state, true);
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

	@Override
	public void deleteObjectFieldByObjectDefinitionId(Long objectDefinitionId)
		throws PortalException {

		for (ObjectField objectField :
				_objectFieldPersistence.findByObjectDefinitionId(
					objectDefinitionId)) {

			if (Validator.isNotNull(objectField.getRelationshipType())) {
				continue;
			}

			objectFieldPersistence.remove(objectField);

			_objectFieldSettingPersistence.removeByObjectFieldId(
				objectField.getObjectFieldId());
		}
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
	public List<ObjectField> getActiveObjectFields(
			List<ObjectField> objectFields)
		throws PortalException {

		List<ObjectField> activeObjectFields = new ArrayList<>();

		for (ObjectField objectField : objectFields) {
			objectField.setObjectFieldSettings(
				_objectFieldSettingPersistence.findByObjectFieldId(
					objectField.getObjectFieldId()));

			if (Validator.isNotNull(objectField.getRelationshipType())) {
				ObjectRelationship objectRelationship =
					_objectRelationshipPersistence.fetchByObjectFieldId2(
						objectField.getObjectFieldId());

				ObjectDefinition objectDefinition =
					_objectDefinitionPersistence.findByPrimaryKey(
						objectRelationship.getObjectDefinitionId1());

				if (objectDefinition.isActive()) {
					activeObjectFields.add(objectField);
				}
			}
			else {
				activeObjectFields.add(objectField);
			}
		}

		return activeObjectFields;
	}

	@Override
	public List<ObjectField> getCustomObjectFields(long objectFieldId) {
		List<ObjectField> objectFields = _objectFieldPersistence.findByODI_S(
			objectFieldId, false);

		for (ObjectField objectField : objectFields) {
			objectField.setObjectFieldSettings(
				_objectFieldSettingPersistence.findByObjectFieldId(
					objectField.getObjectFieldId()));
		}

		return objectFields;
	}

	@Override
	public List<ObjectField> getListTypeDefinitionObjectFields(
		long listTypeDefinitionId, boolean state) {

		return objectFieldPersistence.findByLTDI_S(listTypeDefinitionId, state);
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

		ObjectField objectField = objectFieldPersistence.findByODI_N(
			objectDefinitionId, name);

		objectField.setObjectFieldSettings(
			_objectFieldSettingPersistence.findByObjectFieldId(
				objectField.getObjectFieldId()));

		return objectField;
	}

	@Override
	public List<ObjectField> getObjectFields(long objectDefinitionId) {
		List<ObjectField> objectFields =
			objectFieldPersistence.findByObjectDefinitionId(objectDefinitionId);

		for (ObjectField objectField : objectFields) {
			objectField.setObjectFieldSettings(
				_objectFieldSettingPersistence.findByObjectFieldId(
					objectField.getObjectFieldId()));
		}

		return objectFields;
	}

	@Override
	public List<ObjectField> getObjectFields(
		long objectDefinitionId, String dbTableName) {

		List<ObjectField> objectFields = objectFieldPersistence.findByODI_DTN(
			objectDefinitionId, dbTableName);

		for (ObjectField objectField : objectFields) {
			objectField.setObjectFieldSettings(
				_objectFieldSettingPersistence.findByObjectFieldId(
					objectField.getObjectFieldId()));
		}

		return objectFields;
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

	@Override
	public Table getTable(long objectDefinitionId, String name)
		throws PortalException {

		// TODO Cache this across the cluster with proper invalidation when the
		// object definition or its object fields are updated

		ObjectField objectField = getObjectField(objectDefinitionId, name);

		if (Objects.equals(
				objectField.getDBTableName(),
				ObjectEntryTable.INSTANCE.getTableName())) {

			return ObjectEntryTable.INSTANCE;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.fetchByPrimaryKey(objectDefinitionId);

		if (Objects.equals(
				objectField.getDBTableName(),
				objectDefinition.getDBTableName())) {

			return new DynamicObjectDefinitionTable(
				objectDefinition,
				objectFieldLocalService.getObjectFields(
					objectDefinitionId, objectDefinition.getDBTableName()),
				objectDefinition.getDBTableName());
		}

		return new DynamicObjectDefinitionTable(
			objectDefinition,
			objectFieldLocalService.getObjectFields(
				objectDefinitionId, objectDefinition.getExtensionDBTableName()),
			objectDefinition.getExtensionDBTableName());
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectField updateCustomObjectField(
			long objectFieldId, String externalReferenceCode,
			long listTypeDefinitionId, String businessType, String dbType,
			String defaultValue, boolean indexed, boolean indexedAsKeyword,
			String indexedLanguageId, Map<Locale, String> labelMap, String name,
			boolean required, boolean state,
			List<ObjectFieldSetting> objectFieldSettings,
			ObjectStateFlow objectStateFlow)
		throws PortalException {

		ObjectField objectField = objectFieldPersistence.findByPrimaryKey(
			objectFieldId);

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectField.getObjectDefinitionId());

		if (objectDefinition.isSystem()) {
			throw new ObjectDefinitionStatusException();
		}

		_validateExternalReferenceCode(
			objectField.getObjectFieldId(), objectField.getCompanyId(),
			externalReferenceCode, objectField.getObjectDefinitionId());
		_validateLabel(labelMap);

		objectField.setExternalReferenceCode(externalReferenceCode);
		objectField.setLabelMap(labelMap, LocaleUtil.getSiteDefault());

		if (objectDefinition.isApproved()) {
			objectField = objectFieldPersistence.update(objectField);

			_addOrUpdateObjectFieldSettings(objectField, objectFieldSettings);

			_objectStateTransitionLocalService.updateObjectStateTransitions(
				objectStateFlow);

			return objectField;
		}

		_validateDefaultValue(
			businessType, defaultValue, listTypeDefinitionId, state);
		_validateIndexed(
			businessType, dbType, indexed, indexedAsKeyword, indexedLanguageId);
		_validateState(required, state);

		if (Validator.isNotNull(objectField.getRelationshipType())) {
			if (!Objects.equals(objectField.getDBType(), dbType) ||
				!Objects.equals(objectField.getName(), name)) {

				throw new ObjectFieldRelationshipTypeException(
					"Object field relationship name and DB type cannot be " +
						"changed");
			}
		}
		else {
			_validateName(objectFieldId, objectDefinition, name, false);
		}

		_setBusinessTypeAndDBType(businessType, dbType, objectField);

		objectField.setListTypeDefinitionId(listTypeDefinitionId);
		objectField.setDBColumnName(name + StringPool.UNDERLINE);
		objectField.setDefaultValue(defaultValue);
		objectField.setIndexed(indexed);
		objectField.setIndexedAsKeyword(indexedAsKeyword);
		objectField.setIndexedLanguageId(indexedLanguageId);
		objectField.setName(name);
		objectField.setRequired(required);
		objectField.setState(state);

		objectField = objectFieldPersistence.update(objectField);

		_addOrUpdateObjectFieldSettings(objectField, objectFieldSettings);

		_objectStateTransitionLocalService.updateObjectStateTransitions(
			objectStateFlow);

		return objectField;
	}

	@Override
	public ObjectField updateObjectField(
			long userId, long objectDefinitionId, long objectFieldId,
			String externalReferenceCode, long listTypeDefinitionId,
			String businessType, String dbColumnName, String dbTableName,
			String dbType, String defaultValue, boolean indexed,
			boolean indexedAsKeyword, String indexedLanguageId,
			Map<Locale, String> labelMap, String name, boolean required,
			boolean state, boolean system,
			List<ObjectFieldSetting> objectFieldSettings,
			ObjectStateFlow objectStateFlow)
		throws PortalException {

		if (system) {
			return objectFieldLocalService.addOrUpdateSystemObjectField(
				userId, objectDefinitionId, businessType, dbColumnName,
				dbTableName, dbType, defaultValue, indexed, indexedAsKeyword,
				indexedLanguageId, labelMap, name, required, state);
		}

		return objectFieldLocalService.updateCustomObjectField(
			objectFieldId, externalReferenceCode, listTypeDefinitionId,
			businessType, dbType, defaultValue, indexed, indexedAsKeyword,
			indexedLanguageId, labelMap, name, required, state,
			objectFieldSettings, objectStateFlow);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectField updateRequired(long objectFieldId, boolean required)
		throws PortalException {

		ObjectField objectField = objectFieldPersistence.findByPrimaryKey(
			objectFieldId);

		objectField.setRequired(required);

		return objectFieldPersistence.update(objectField);
	}

	private ObjectField _addObjectField(
			long userId, long listTypeDefinitionId, long objectDefinitionId,
			String businessType, String dbColumnName, String dbTableName,
			String dbType, String defaultValue, boolean indexed,
			boolean indexedAsKeyword, String indexedLanguageId,
			Map<Locale, String> labelMap, String name, boolean required,
			boolean state, boolean system)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		_validateDefaultValue(
			businessType, defaultValue, listTypeDefinitionId, state);
		_validateIndexed(
			businessType, dbType, indexed, indexedAsKeyword, indexedLanguageId);
		_validateLabel(labelMap);
		_validateName(0, objectDefinition, name, system);
		_validateState(required, state);

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
		objectField.setDefaultValue(defaultValue);
		objectField.setIndexed(indexed);
		objectField.setIndexedAsKeyword(indexedAsKeyword);
		objectField.setIndexedLanguageId(indexedLanguageId);
		objectField.setLabelMap(labelMap, LocaleUtil.getSiteDefault());
		objectField.setName(name);
		objectField.setRelationshipType(null);
		objectField.setRequired(required);
		objectField.setState(state);
		objectField.setSystem(system);

		return objectFieldPersistence.update(objectField);
	}

	private void _addOrUpdateObjectFieldSettings(
			ObjectField objectField,
			List<ObjectFieldSetting> objectFieldSettings)
		throws PortalException {

		ObjectFieldBusinessType objectFieldBusinessType =
			_objectFieldBusinessTypeServicesTracker.getObjectFieldBusinessType(
				objectField.getBusinessType());

		objectFieldBusinessType.validateObjectFieldSettings(
			objectField.getName(), objectFieldSettings);

		List<ObjectFieldSetting> oldObjectFieldSettings =
			_objectFieldSettingPersistence.findByObjectFieldId(
				objectField.getObjectFieldId());

		for (ObjectFieldSetting oldObjectFieldSetting :
				oldObjectFieldSettings) {

			Stream<ObjectFieldSetting> stream = objectFieldSettings.stream();

			Optional<ObjectFieldSetting> objectFieldSettingOptional =
				stream.filter(
					newObjectFieldSetting -> Objects.equals(
						newObjectFieldSetting.getName(),
						oldObjectFieldSetting.getName())
				).findFirst();

			if (!objectFieldSettingOptional.isPresent()) {
				_objectFieldSettingPersistence.remove(oldObjectFieldSetting);
			}
		}

		for (ObjectFieldSetting newObjectFieldSetting : objectFieldSettings) {
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

	private void _deleteFileEntries(long objectDefinitionId, String name) {
		List<ObjectEntry> objectEntries =
			_objectEntryPersistence.findByObjectDefinitionId(
				objectDefinitionId);

		for (ObjectEntry objectEntry : objectEntries) {
			Map<String, Serializable> values = objectEntry.getValues();

			try {
				_dlFileEntryLocalService.deleteFileEntry(
					GetterUtil.getLong(values.get(name)));
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
				objectField.getDBTableName()) &&
			!Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP)) {

			throw new RequiredObjectFieldException();
		}

		objectField = objectFieldPersistence.remove(objectField);

		if (objectDefinition.getAccountEntryRestrictedObjectFieldId() ==
				objectField.getObjectFieldId()) {

			objectDefinition.setAccountEntryRestrictedObjectFieldId(0);
			objectDefinition.setAccountEntryRestricted(false);

			objectDefinition = _objectDefinitionPersistence.update(
				objectDefinition);
		}

		String objectFieldSettingFileSource = StringPool.BLANK;

		if (Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

			ObjectFieldSetting objectFieldSetting =
				_objectFieldSettingPersistence.fetchByOFI_N(
					objectField.getObjectFieldId(), "fileSource");

			objectFieldSettingFileSource = objectFieldSetting.getValue();
		}

		_objectFieldSettingPersistence.removeByObjectFieldId(
			objectField.getObjectFieldId());

		_objectLayoutColumnPersistence.removeByObjectFieldId(
			objectField.getObjectFieldId());

		if (objectField.isState()) {
			_objectStateFlowLocalService.deleteObjectFieldObjectStateFlow(
				objectField.getObjectFieldId());
		}

		_objectViewLocalService.unassociateObjectField(objectField);

		if ((Objects.equals(
				objectDefinition.getExtensionDBTableName(),
				objectField.getDBTableName()) ||
			 (objectDefinition.isApproved() &&
			  Objects.equals(
				  objectField.getBusinessType(),
				  ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP))) &&
			!Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION)) {

			if (Objects.equals(objectFieldSettingFileSource, "userComputer")) {
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

	private void _validateDefaultValue(
			String businessType, String defaultValue, long listTypeDefinitionId,
			boolean state)
		throws PortalException {

		if (Validator.isNull(defaultValue)) {
			return;
		}

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-152677"))) {
			throw new UnsupportedOperationException();
		}

		if (!Objects.equals(
				ObjectFieldConstants.BUSINESS_TYPE_PICKLIST, businessType)) {

			throw new ObjectFieldDefaultValueException(
				StringBundler.concat(
					"Object field can only have a default type when the ",
					"business type is \"",
					ObjectFieldConstants.BUSINESS_TYPE_PICKLIST, "\""));
		}

		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.fetchListTypeEntry(
				listTypeDefinitionId, defaultValue);

		if (listTypeEntry == null) {
			throw new ObjectFieldDefaultValueException(
				StringBundler.concat(
					"Default value \"", defaultValue, "\" is not a list entry ",
					"in list definition ", listTypeDefinitionId));
		}

		if (!state) {
			throw new ObjectFieldStateException(
				"Object field default value can only be set when the " +
					"picklist is a state");
		}
	}

	private void _validateExternalReferenceCode(
			long objectFieldId, long companyId, String externalReferenceCode,
			long objectDefinitionId)
		throws PortalException {

		if (Validator.isNull(externalReferenceCode)) {
			return;
		}

		ObjectField objectField = objectFieldPersistence.fetchByC_ERC_ODI(
			companyId, externalReferenceCode, objectDefinitionId);

		if ((objectField != null) &&
			(objectField.getObjectFieldId() != objectFieldId)) {

			throw new DuplicateObjectFieldExternalReferenceCodeException();
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
			  !Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_CLOB) &&
			  !Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_STRING)) ||
			 indexedAsKeyword) &&
			!Validator.isBlank(indexedLanguageId)) {

			throw new ObjectFieldDBTypeException(
				"Indexed language ID can only be applied with type \"Clob\" " +
					"or \"String\" that is not indexed as a keyword");
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
			long objectFieldId, ObjectDefinition objectDefinition, String name,
			boolean system)
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

		if ((!system &&
			 _reservedNames.contains(StringUtil.toLowerCase(name))) ||
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

	private void _validateState(boolean required, boolean state)
		throws PortalException {

		if (state && !required) {
			throw new ObjectFieldStateException(
				"Object field must be required when the state is true");
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
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectEntryPersistence _objectEntryPersistence;

	@Reference
	private ObjectFieldBusinessTypeServicesTracker
		_objectFieldBusinessTypeServicesTracker;

	@Reference
	private ObjectFieldPersistence _objectFieldPersistence;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Reference
	private ObjectFieldSettingPersistence _objectFieldSettingPersistence;

	@Reference
	private ObjectLayoutColumnPersistence _objectLayoutColumnPersistence;

	@Reference
	private ObjectRelationshipPersistence _objectRelationshipPersistence;

	@Reference
	private ObjectStateFlowLocalService _objectStateFlowLocalService;

	@Reference
	private ObjectStateTransitionLocalService
		_objectStateTransitionLocalService;

	@Reference
	private ObjectViewLocalService _objectViewLocalService;

	private final Set<String> _reservedNames = SetUtil.fromArray(
		"actions", "companyid", "createdate", "creator", "datecreated",
		"datemodified", "externalreferencecode", "groupid", "id",
		"lastpublishdate", "modifieddate", "statusbyuserid", "statusbyusername",
		"statusdate", "userid", "username");

	@Reference
	private UserLocalService _userLocalService;

}