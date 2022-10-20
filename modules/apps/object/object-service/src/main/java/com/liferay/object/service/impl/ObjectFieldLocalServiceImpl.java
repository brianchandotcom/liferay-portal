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
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.DuplicateObjectFieldExternalReferenceCodeException;
import com.liferay.object.exception.ObjectFieldBusinessTypeException;
import com.liferay.object.exception.ObjectFieldDBTypeException;
import com.liferay.object.exception.ObjectFieldDefaultValueException;
import com.liferay.object.exception.ObjectFieldLabelException;
import com.liferay.object.exception.ObjectFieldListTypeDefinitionIdException;
import com.liferay.object.exception.ObjectFieldNameException;
import com.liferay.object.exception.ObjectFieldRelationshipTypeException;
import com.liferay.object.exception.ObjectFieldStateException;
import com.liferay.object.exception.RequiredObjectFieldException;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeTracker;
import com.liferay.object.internal.field.setting.contributor.ObjectFieldSettingContributor;
import com.liferay.object.internal.field.setting.contributor.ObjectFieldSettingContributorTracker;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.object.service.ObjectViewLocalService;
import com.liferay.object.service.base.ObjectFieldLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.service.persistence.ObjectEntryPersistence;
import com.liferay.object.service.persistence.ObjectFieldSettingPersistence;
import com.liferay.object.service.persistence.ObjectLayoutColumnPersistence;
import com.liferay.object.service.persistence.ObjectRelationshipPersistence;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.sql.dsl.Column;
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
			String externalReferenceCode, long userId,
			long listTypeDefinitionId, long objectDefinitionId,
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
			externalReferenceCode, userId, listTypeDefinitionId,
			objectDefinitionId, businessType, name + StringPool.UNDERLINE,
			dbTableName, dbType, defaultValue, indexed, indexedAsKeyword,
			indexedLanguageId, labelMap, name, required, state, false);

		if (objectDefinition.isApproved() &&
			!Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION)) {

			runSQL(
				DynamicObjectDefinitionTable.getAlterTableAddColumnSQL(
					dbTableName, objectField.getDBColumnName(), dbType));
		}

		_addOrUpdateObjectFieldSettings(objectField, null, objectFieldSettings);

		return objectField;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectField addOrUpdateCustomObjectField(
			String externalReferenceCode, long userId, long objectDefinitionId,
			long listTypeDefinitionId, String businessType, String dbType,
			String defaultValue, boolean indexed, boolean indexedAsKeyword,
			String indexedLanguageId, Map<Locale, String> labelMap, String name,
			boolean required, boolean state,
			List<ObjectFieldSetting> objectFieldSettings)
		throws PortalException {

		ObjectField existingObjectField = objectFieldPersistence.fetchByODI_N(
			objectDefinitionId, name);

		if (existingObjectField == null) {
			return objectFieldLocalService.addCustomObjectField(
				externalReferenceCode, userId, listTypeDefinitionId,
				objectDefinitionId, businessType, dbType, defaultValue, indexed,
				indexedAsKeyword, indexedLanguageId, labelMap, name, required,
				state, objectFieldSettings);
		}

		return objectFieldLocalService.updateCustomObjectField(
			externalReferenceCode, existingObjectField.getObjectFieldId(),
			listTypeDefinitionId, businessType, dbType, defaultValue, indexed,
			indexedAsKeyword, indexedLanguageId, labelMap, name, required,
			state, objectFieldSettings);
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
			null, userId, 0, objectDefinitionId, businessType, dbColumnName,
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
				objectFieldPersistence.findByObjectDefinitionId(
					objectDefinitionId)) {

			if (Validator.isNotNull(objectField.getRelationshipType())) {
				continue;
			}

			objectFieldPersistence.remove(objectField);

			_objectFieldSettingLocalService.deleteObjectFieldObjectFieldSetting(
				objectField);
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
				_objectFieldSettingLocalService.
					getObjectFieldObjectFieldSettings(
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
	public Column<?, ?> getColumn(long objectDefinitionId, String name) {
		try {
			ObjectField objectField = Optional.ofNullable(
				fetchObjectField(objectDefinitionId, name)
			).orElseGet(
				() -> _getObjectRelationshipField(objectDefinitionId, name)
			);

			if (objectField == null) {
				throw new UnsupportedOperationException(
					"Unsupported method getColumn with field name " + name);
			}

			if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT) ||
				Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_RICH_TEXT)) {

				throw new UnsupportedOperationException(
					"Unsupported operation with " +
						objectField.getBusinessType() + " field");
			}

			Table<?> table = getTable(
				objectDefinitionId, objectField.getName());

			return table.getColumn(objectField.getDBColumnName());
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	@Override
	public List<ObjectField> getCustomObjectFields(long objectFieldId) {
		List<ObjectField> objectFields = objectFieldPersistence.findByODI_S(
			objectFieldId, false);

		for (ObjectField objectField : objectFields) {
			objectField.setObjectFieldSettings(
				_objectFieldSettingLocalService.
					getObjectFieldObjectFieldSettings(
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
			_objectFieldSettingLocalService.getObjectFieldObjectFieldSettings(
				objectField.getObjectFieldId()));

		return objectField;
	}

	@Override
	public ObjectField getObjectField(long objectDefinitionId, String name)
		throws PortalException {

		ObjectField objectField = objectFieldPersistence.findByODI_N(
			objectDefinitionId, name);

		objectField.setObjectFieldSettings(
			_objectFieldSettingLocalService.getObjectFieldObjectFieldSettings(
				objectField.getObjectFieldId()));

		return objectField;
	}

	@Override
	public List<ObjectField> getObjectFields(long objectDefinitionId) {
		List<ObjectField> objectFields =
			objectFieldPersistence.findByObjectDefinitionId(objectDefinitionId);

		for (ObjectField objectField : objectFields) {
			objectField.setObjectFieldSettings(
				_objectFieldSettingLocalService.
					getObjectFieldObjectFieldSettings(
						objectField.getObjectFieldId()));
		}

		return objectFields;
	}

	@Override
	public List<ObjectField> getObjectFields(
		long objectDefinitionId, boolean system) {

		List<ObjectField> objectFields = objectFieldPersistence.findByODI_S(
			objectDefinitionId, system);

		for (ObjectField objectField : objectFields) {
			objectField.setObjectFieldSettings(
				_objectFieldSettingLocalService.
					getObjectFieldObjectFieldSettings(
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
				_objectFieldSettingLocalService.
					getObjectFieldObjectFieldSettings(
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
			String externalReferenceCode, long objectFieldId,
			long listTypeDefinitionId, String businessType, String dbType,
			String defaultValue, boolean indexed, boolean indexedAsKeyword,
			String indexedLanguageId, Map<Locale, String> labelMap, String name,
			boolean required, boolean state,
			List<ObjectFieldSetting> objectFieldSettings)
		throws PortalException {

		ObjectField oldObjectField = objectFieldPersistence.findByPrimaryKey(
			objectFieldId);

		ObjectField newObjectField = (ObjectField)oldObjectField.clone();

		_validateExternalReferenceCode(
			externalReferenceCode, newObjectField.getObjectFieldId(),
			newObjectField.getCompanyId(),
			newObjectField.getObjectDefinitionId());

		_validateDefaultValue(
			businessType, defaultValue, listTypeDefinitionId, state);
		_validateIndexed(
			businessType, dbType, indexed, indexedAsKeyword, indexedLanguageId);
		_validateLabel(labelMap);
		_validateListTypeDefinitionId(listTypeDefinitionId, businessType);

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(
				newObjectField.getObjectDefinitionId());

		if (Validator.isNotNull(newObjectField.getRelationshipType())) {
			_validateObjectRelationshipDeletionType(objectFieldId, required);

			newObjectField.setRequired(required);

			if (!Objects.equals(newObjectField.getDBType(), dbType) ||
				!Objects.equals(newObjectField.getName(), name)) {

				throw new ObjectFieldRelationshipTypeException(
					"Object field relationship name and DB type cannot be " +
						"changed");
			}
		}
		else {
			_validateName(objectFieldId, objectDefinition, name, false);
		}

		_validateState(required, state);

		newObjectField.setExternalReferenceCode(externalReferenceCode);
		newObjectField.setDefaultValue(defaultValue);
		newObjectField.setIndexed(indexed);
		newObjectField.setIndexedAsKeyword(indexedAsKeyword);
		newObjectField.setIndexedLanguageId(indexedLanguageId);
		newObjectField.setLabelMap(labelMap, LocaleUtil.getSiteDefault());

		if (objectDefinition.isApproved()) {
			newObjectField = objectFieldPersistence.update(newObjectField);

			_addOrUpdateObjectFieldSettings(
				newObjectField, oldObjectField, objectFieldSettings);

			return newObjectField;
		}

		_setBusinessTypeAndDBType(businessType, dbType, newObjectField);

		newObjectField.setListTypeDefinitionId(listTypeDefinitionId);
		newObjectField.setDBColumnName(name + StringPool.UNDERLINE);
		newObjectField.setName(name);
		newObjectField.setRequired(required);
		newObjectField.setState(state);

		newObjectField = objectFieldPersistence.update(newObjectField);

		_addOrUpdateObjectFieldSettings(
			newObjectField, oldObjectField, objectFieldSettings);

		return newObjectField;
	}

	@Override
	public ObjectField updateObjectField(
			String externalReferenceCode, long userId, long objectDefinitionId,
			long objectFieldId, long listTypeDefinitionId, String businessType,
			String dbColumnName, String dbTableName, String dbType,
			String defaultValue, boolean indexed, boolean indexedAsKeyword,
			String indexedLanguageId, Map<Locale, String> labelMap, String name,
			boolean required, boolean state, boolean system,
			List<ObjectFieldSetting> objectFieldSettings)
		throws PortalException {

		if (system) {
			return objectFieldLocalService.addOrUpdateSystemObjectField(
				userId, objectDefinitionId, businessType, dbColumnName,
				dbTableName, dbType, defaultValue, indexed, indexedAsKeyword,
				indexedLanguageId, labelMap, name, required, state);
		}

		return objectFieldLocalService.addOrUpdateCustomObjectField(
			externalReferenceCode, userId, objectDefinitionId,
			listTypeDefinitionId, businessType, dbType, defaultValue, indexed,
			indexedAsKeyword, indexedLanguageId, labelMap, name, required,
			state, objectFieldSettings);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectField updateRequired(long objectFieldId, boolean required)
		throws PortalException {

		ObjectField objectField = objectFieldPersistence.findByPrimaryKey(
			objectFieldId);

		if (StringUtil.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP)) {

			_validateObjectRelationshipDeletionType(objectFieldId, required);
		}

		objectField.setRequired(required);

		return objectFieldPersistence.update(objectField);
	}

	private ObjectField _addObjectField(
			String externalReferenceCode, long userId,
			long listTypeDefinitionId, long objectDefinitionId,
			String businessType, String dbColumnName, String dbTableName,
			String dbType, String defaultValue, boolean indexed,
			boolean indexedAsKeyword, String indexedLanguageId,
			Map<Locale, String> labelMap, String name, boolean required,
			boolean state, boolean system)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		_validateExternalReferenceCode(
			externalReferenceCode, 0, objectDefinition.getCompanyId(),
			objectDefinitionId);

		_validateDefaultValue(
			businessType, defaultValue, listTypeDefinitionId, state);
		_validateIndexed(
			businessType, dbType, indexed, indexedAsKeyword, indexedLanguageId);
		_validateLabel(labelMap);
		_validateListTypeDefinitionId(listTypeDefinitionId, businessType);
		_validateName(0, objectDefinition, name, system);
		_validateState(required, state);

		ObjectField objectField = objectFieldPersistence.create(
			counterLocalService.increment());

		objectField.setExternalReferenceCode(externalReferenceCode);

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
			ObjectField newObjectField, ObjectField oldObjectField,
			List<ObjectFieldSetting> objectFieldSettings)
		throws PortalException {

		ObjectFieldBusinessType objectFieldBusinessType =
			_objectFieldBusinessTypeTracker.getObjectFieldBusinessType(
				newObjectField.getBusinessType());

		objectFieldBusinessType.validateObjectFieldSettings(
			newObjectField.getObjectDefinitionId(), newObjectField.getName(),
			objectFieldSettings);

		List<ObjectFieldSetting> oldObjectFieldSettings =
			_objectFieldSettingPersistence.findByObjectFieldId(
				newObjectField.getObjectFieldId());

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
				_objectFieldSettingLocalService.deleteObjectFieldSetting(
					oldObjectFieldSetting.getObjectFieldSettingId());
			}
		}

		objectFieldBusinessType.predefineObjectFieldSettings(
			newObjectField, oldObjectField);

		for (ObjectFieldSetting newObjectFieldSetting : objectFieldSettings) {
			ObjectFieldSetting oldObjectFieldSetting =
				_objectFieldSettingPersistence.fetchByOFI_N(
					newObjectField.getObjectFieldId(),
					newObjectFieldSetting.getName());

			ObjectFieldSettingContributor objectFieldSettingContributor =
				_objectFieldSettingContributorTracker.
					getObjectFieldSettingContributor(
						newObjectFieldSetting.getName());

			if (oldObjectFieldSetting == null) {
				objectFieldSettingContributor.addObjectFieldSetting(
					newObjectField.getUserId(),
					newObjectField.getObjectFieldId(), newObjectFieldSetting);
			}
			else {
				objectFieldSettingContributor.updateObjectFieldSetting(
					oldObjectFieldSetting.getObjectFieldSettingId(),
					newObjectFieldSetting);
			}
		}

		newObjectField.setObjectFieldSettings(
			_objectFieldSettingLocalService.getObjectFieldObjectFieldSettings(
				newObjectField.getObjectFieldId()));
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

		if (Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

			ObjectFieldSetting objectFieldSetting =
				_objectFieldSettingPersistence.fetchByOFI_N(
					objectField.getObjectFieldId(), "fileSource");

			if (Objects.equals(objectFieldSetting.getValue(), "userComputer")) {
				List<ObjectEntry> objectEntries =
					_objectEntryPersistence.findByObjectDefinitionId(
						objectField.getObjectDefinitionId());

				for (ObjectEntry objectEntry : objectEntries) {

					// getValues must be called before deleting the object field

					Map<String, Serializable> values = objectEntry.getValues();

					try {
						_dlFileEntryLocalService.deleteFileEntry(
							GetterUtil.getLong(
								values.get(objectField.getName())));
					}
					catch (PortalException portalException) {
						if (_log.isDebugEnabled()) {
							_log.debug(portalException);
						}
					}
				}
			}
		}

		objectField = objectFieldPersistence.remove(objectField);

		if (objectDefinition.getAccountEntryRestrictedObjectFieldId() ==
				objectField.getObjectFieldId()) {

			objectDefinition.setAccountEntryRestrictedObjectFieldId(0);
			objectDefinition.setAccountEntryRestricted(false);

			objectDefinition = _objectDefinitionPersistence.update(
				objectDefinition);
		}

		_objectFieldSettingLocalService.deleteObjectFieldObjectFieldSetting(
			objectField);

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

			runSQL(
				DynamicObjectDefinitionTable.getAlterTableDropColumnSQL(
					objectField.getDBTableName(),
					objectField.getDBColumnName()));
		}

		return objectField;
	}

	private ObjectField _getObjectRelationshipField(
		long objectDefinitionId, String relationshipIdName) {

		for (ObjectField objectField : getObjectFields(objectDefinitionId)) {
			if (StringUtil.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP) &&
				StringUtil.endsWith(
					objectField.getName(),
					StringPool.UNDERLINE + relationshipIdName)) {

				return objectField;
			}
		}

		return null;
	}

	private void _setBusinessTypeAndDBType(
			String businessType, String dbType, ObjectField objectField)
		throws PortalException {

		ObjectFieldBusinessType objectFieldBusinessType =
			_objectFieldBusinessTypeTracker.getObjectFieldBusinessType(
				GetterUtil.getString(businessType));

		Set<String> objectFieldDBTypes =
			_objectFieldBusinessTypeTracker.getObjectFieldDBTypes();

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
			String externalReferenceCode, long objectFieldId, long companyId,
			long objectDefinitionId)
		throws PortalException {

		if (Validator.isNull(externalReferenceCode)) {
			return;
		}

		ObjectField objectField = objectFieldPersistence.fetchByERC_C_ODI(
			externalReferenceCode, companyId, objectDefinitionId);

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

	private void _validateListTypeDefinitionId(
			long listTypeDefinitionId, String businessType)
		throws PortalException {

		if ((listTypeDefinitionId == 0) &&
			StringUtil.equals(
				businessType, ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)) {

			throw new ObjectFieldListTypeDefinitionIdException(
				"List type definition id must not be null");
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

	private void _validateObjectRelationshipDeletionType(
			long objectFieldId, boolean required)
		throws PortalException {

		ObjectRelationship objectRelationship =
			_objectRelationshipPersistence.fetchByObjectFieldId2(objectFieldId);

		if (StringUtil.equals(
				objectRelationship.getDeletionType(),
				ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE) &&
			required) {

			throw new ObjectFieldRelationshipTypeException(
				"Object field cannot be required because the relationship " +
					"deletion type is disassociate");
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
	private ObjectFieldBusinessTypeTracker _objectFieldBusinessTypeTracker;

	@Reference
	private ObjectFieldSettingContributorTracker
		_objectFieldSettingContributorTracker;

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
	private ObjectViewLocalService _objectViewLocalService;

	private final Set<String> _reservedNames = SetUtil.fromArray(
		"actions", "companyid", "createdate", "creator", "datecreated",
		"datemodified", "externalreferencecode", "groupid", "id",
		"lastpublishdate", "modifieddate", "status", "statusbyuserid",
		"statusbyusername", "statusdate", "userid", "username");

	@Reference
	private UserLocalService _userLocalService;

}