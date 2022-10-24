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

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.DuplicateObjectRelationshipException;
import com.liferay.object.exception.NoSuchObjectRelationshipException;
import com.liferay.object.exception.ObjectRelationshipNameException;
import com.liferay.object.exception.ObjectRelationshipParameterObjectFieldIdException;
import com.liferay.object.exception.ObjectRelationshipReverseException;
import com.liferay.object.exception.ObjectRelationshipTypeException;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectRelationshipMappingTable;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.model.ObjectRelationshipTable;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.base.ObjectRelationshipLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.service.persistence.ObjectFieldPersistence;
import com.liferay.object.service.persistence.ObjectLayoutTabPersistence;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadataTracker;
import com.liferay.object.util.ObjectRelationshipUtil;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

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

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectRelationship addObjectRelationship(
			long userId, long objectDefinitionId1, long objectDefinitionId2,
			long parameterObjectFieldId, String deletionType,
			Map<Locale, String> labelMap, String name, String type)
		throws PortalException {

		return _addObjectRelationship(
			userId, objectDefinitionId1, objectDefinitionId2,
			parameterObjectFieldId, deletionType, labelMap, name, false, type);
	}

	@Override
	public void addObjectRelationshipMappingTableValues(
			long userId, long objectRelationshipId, long primaryKey1,
			long primaryKey2, ServiceContext serviceContext)
		throws PortalException {

		ObjectRelationship objectRelationship =
			objectRelationshipPersistence.findByPrimaryKey(
				objectRelationshipId);

		ObjectDefinition objectDefinition2 =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectRelationship.getObjectDefinitionId2());

		if (Objects.equals(
				objectRelationship.getType(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

			ObjectDefinition objectDefinition1 =
				_objectDefinitionPersistence.findByPrimaryKey(
					objectRelationship.getObjectDefinitionId1());

			if (_hasManyToManyObjectRelationshipMappingTableValues(
					objectDefinition1, objectDefinition2, objectRelationship,
					primaryKey1, primaryKey2)) {

				return;
			}

			Map<String, String> pkObjectFieldDBColumnNames =
				ObjectRelationshipUtil.getPKObjectFieldDBColumnNames(
					objectDefinition1, objectDefinition2,
					objectRelationship.isReverse());

			runSQL(
				StringBundler.concat(
					"insert into ", objectRelationship.getDBTableName(), " (",
					pkObjectFieldDBColumnNames.get(
						"pkObjectFieldDBColumnName1"),
					", ",
					pkObjectFieldDBColumnNames.get(
						"pkObjectFieldDBColumnName2"),
					") values (", primaryKey1, ", ", primaryKey2, ")"));

			return;
		}

		ObjectField objectField2 = _objectFieldLocalService.getObjectField(
			objectRelationship.getObjectFieldId2());

		if (objectDefinition2.isSystem()) {
			_objectEntryLocalService.insertIntoOrUpdateExtensionTable(
				objectRelationship.getObjectDefinitionId2(), primaryKey2,
				HashMapBuilder.<String, Serializable>put(
					objectField2.getName(), primaryKey1
				).build());
		}
		else {
			_objectEntryLocalService.updateObjectEntry(
				userId, primaryKey2,
				HashMapBuilder.<String, Serializable>put(
					objectField2.getName(), primaryKey1
				).build(),
				serviceContext);
		}
	}

	@Override
	public ObjectRelationship deleteObjectRelationship(
			long objectRelationshipId)
		throws PortalException {

		ObjectRelationship objectRelationship =
			objectRelationshipPersistence.findByPrimaryKey(
				objectRelationshipId);

		return objectRelationshipLocalService.deleteObjectRelationship(
			objectRelationship);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public ObjectRelationship deleteObjectRelationship(
			ObjectRelationship objectRelationship)
		throws PortalException {

		// TODO When should we allow an object relationship to be deleted?

		if (objectRelationship.isReverse()) {
			throw new ObjectRelationshipReverseException(
				"Reverse object relationships cannot be deleted");
		}

		objectRelationship = objectRelationshipPersistence.remove(
			objectRelationship);

		_objectLayoutTabPersistence.removeByObjectRelationshipId(
			objectRelationship.getObjectRelationshipId());

		if (Objects.equals(
				objectRelationship.getType(),
				ObjectRelationshipConstants.TYPE_ONE_TO_ONE) ||
			Objects.equals(
				objectRelationship.getType(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

			_objectFieldLocalService.deleteRelationshipTypeObjectField(
				objectRelationship.getObjectFieldId2());
		}
		else if (Objects.equals(
					objectRelationship.getType(),
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

			runSQL("drop table " + objectRelationship.getDBTableName());

			ObjectRelationship reverseObjectRelationship =
				fetchReverseObjectRelationship(objectRelationship, true);

			_objectLayoutTabPersistence.removeByObjectRelationshipId(
				reverseObjectRelationship.getObjectRelationshipId());

			objectRelationshipPersistence.remove(
				reverseObjectRelationship.getObjectRelationshipId());

			Indexer<ObjectRelationship> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(
					ObjectRelationship.class);

			indexer.delete(reverseObjectRelationship);
		}

		return objectRelationship;
	}

	@Override
	public void deleteObjectRelationshipMappingTableValues(
			long objectRelationshipId, long primaryKey1)
		throws PortalException {

		ObjectRelationship objectRelationship =
			objectRelationshipPersistence.findByPrimaryKey(
				objectRelationshipId);

		if (Objects.equals(
				objectRelationship.getType(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

			Map<String, String> pkObjectFieldDBColumnNames =
				ObjectRelationshipUtil.getPKObjectFieldDBColumnNames(
					_objectDefinitionPersistence.findByPrimaryKey(
						objectRelationship.getObjectDefinitionId1()),
					_objectDefinitionPersistence.findByPrimaryKey(
						objectRelationship.getObjectDefinitionId2()),
					objectRelationship.isReverse());

			runSQL(
				StringBundler.concat(
					"delete from ", objectRelationship.getDBTableName(),
					" where ",
					pkObjectFieldDBColumnNames.get(
						"pkObjectFieldDBColumnName1"),
					" = ", primaryKey1));
		}
	}

	@Override
	public void deleteObjectRelationshipMappingTableValues(
			long objectRelationshipId, long primaryKey1, long primaryKey2)
		throws PortalException {

		ObjectRelationship objectRelationship =
			objectRelationshipPersistence.findByPrimaryKey(
				objectRelationshipId);

		if (Objects.equals(
				objectRelationship.getType(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

			ObjectDefinition objectDefinition1 =
				_objectDefinitionPersistence.findByPrimaryKey(
					objectRelationship.getObjectDefinitionId1());
			ObjectDefinition objectDefinition2 =
				_objectDefinitionPersistence.findByPrimaryKey(
					objectRelationship.getObjectDefinitionId2());

			Map<String, String> pkObjectFieldDBColumnNames =
				ObjectRelationshipUtil.getPKObjectFieldDBColumnNames(
					objectDefinition1, objectDefinition2,
					objectRelationship.isReverse());

			runSQL(
				StringBundler.concat(
					"delete from ", objectRelationship.getDBTableName(),
					" where ",
					pkObjectFieldDBColumnNames.get(
						"pkObjectFieldDBColumnName1"),
					" = ", primaryKey1, " and ",
					pkObjectFieldDBColumnNames.get(
						"pkObjectFieldDBColumnName2"),
					" = ", primaryKey2));
		}
	}

	@Override
	public void deleteObjectRelationships(long objectDefinitionId1)
		throws PortalException {

		for (ObjectRelationship objectRelationship :
				objectRelationshipPersistence.findByObjectDefinitionId1(
					objectDefinitionId1)) {

			objectRelationshipLocalService.deleteObjectRelationship(
				objectRelationship);
		}
	}

	@Override
	public ObjectRelationship fetchObjectRelationshipByObjectDefinitionId(
			long objectDefinitionId, String name)
		throws Exception {

		return objectRelationshipPersistence.fetchByODI1_N_First(
			objectDefinitionId, name, null);
	}

	@Override
	public ObjectRelationship fetchObjectRelationshipByObjectFieldId2(
		long objectFieldId2) {

		return objectRelationshipPersistence.fetchByObjectFieldId2(
			objectFieldId2);
	}

	@Override
	public ObjectRelationship fetchReverseObjectRelationship(
		ObjectRelationship objectRelationship, boolean reverse) {

		return objectRelationshipPersistence.fetchByODI1_ODI2_N_R_T(
			objectRelationship.getObjectDefinitionId2(),
			objectRelationship.getObjectDefinitionId1(),
			objectRelationship.getName(), reverse,
			objectRelationship.getType());
	}

	@Override
	public ObjectRelationship getObjectRelationship(
			long objectDefinitionId1, String name)
		throws PortalException {

		try {
			return ObjectRelationshipUtil.getObjectRelationship(
				objectRelationshipPersistence.findByODI1_N(
					objectDefinitionId1, name));
		}
		catch (NoSuchObjectRelationshipException
					noSuchObjectRelationshipException) {

			throw new NoSuchObjectRelationshipException(
				String.format(
					"No ObjectRelationship exists with the key " +
						"{objectDefinitionId1=%s, name=%s}",
					objectDefinitionId1, name),
				noSuchObjectRelationshipException);
		}
	}

	@Override
	public ObjectRelationship getObjectRelationshipByObjectDefinitionId(
			long objectDefinitionId, String objectRelationshipName)
		throws Exception {

		List<ObjectRelationship> objectRelationships = dslQuery(
			DSLQueryFactoryUtil.select(
			).from(
				ObjectRelationshipTable.INSTANCE
			).where(
				Predicate.withParentheses(
					ObjectRelationshipTable.INSTANCE.objectDefinitionId1.eq(
						objectDefinitionId
					).or(
						ObjectRelationshipTable.INSTANCE.objectDefinitionId2.eq(
							objectDefinitionId)
					)
				).and(
					ObjectRelationshipTable.INSTANCE.name.eq(
						objectRelationshipName)
				).and(
					ObjectRelationshipTable.INSTANCE.reverse.eq(false)
				)
			));

		if (objectRelationships.isEmpty()) {
			throw new NoSuchObjectRelationshipException(
				"No ObjectRelationship exists with the name " +
					objectRelationshipName);
		}

		return objectRelationships.get(0);
	}

	@Override
	public List<ObjectRelationship> getObjectRelationships(
		long objectDefinitionId1) {

		return objectRelationshipPersistence.findByObjectDefinitionId1(
			objectDefinitionId1);
	}

	@Override
	public List<ObjectRelationship> getObjectRelationships(
		long objectDefinitionId1, int start, int end) {

		return objectRelationshipPersistence.findByObjectDefinitionId1(
			objectDefinitionId1, start, end);
	}

	@Override
	public List<ObjectRelationship> getObjectRelationships(
		long objectDefinitionId1, long objectDefinition2, String type) {

		return objectRelationshipPersistence.findByODI1_ODI2_T(
			objectDefinitionId1, objectDefinition2, type);
	}

	@Override
	public List<ObjectRelationship> getObjectRelationships(
		long objectDefinitionId1, String deletionType, boolean reverse) {

		return objectRelationshipPersistence.findByODI1_DT_R(
			objectDefinitionId1, deletionType, reverse);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectRelationship updateObjectRelationship(
			long objectRelationshipId, long parameterObjectFieldId,
			String deletionType, Map<Locale, String> labelMap)
		throws PortalException {

		if (Validator.isNull(deletionType)) {
			deletionType = ObjectRelationshipConstants.DELETION_TYPE_PREVENT;
		}

		ObjectRelationship objectRelationship =
			objectRelationshipPersistence.findByPrimaryKey(
				objectRelationshipId);

		if (objectRelationship.isReverse()) {
			throw new ObjectRelationshipReverseException(
				"Reverse object relationships cannot be updated");
		}

		_validateParameterObjectFieldId(
			objectRelationship.getObjectDefinitionId1(),
			objectRelationship.getObjectDefinitionId2(), parameterObjectFieldId,
			objectRelationship.getType());

		if (Objects.equals(
				objectRelationship.getType(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

			ObjectRelationship reverseObjectRelationship =
				fetchReverseObjectRelationship(objectRelationship, true);

			_updateObjectRelationship(
				parameterObjectFieldId, deletionType, labelMap,
				reverseObjectRelationship);

			Indexer<ObjectRelationship> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(
					ObjectRelationship.class);

			indexer.reindex(reverseObjectRelationship);
		}

		objectRelationship = _updateObjectRelationship(
			parameterObjectFieldId, deletionType, labelMap, objectRelationship);

		if ((objectRelationship.getObjectFieldId2() != 0) &&
			StringUtil.equals(
				deletionType,
				ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE)) {

			_objectFieldLocalService.updateRequired(
				objectRelationship.getObjectFieldId2(), false);
		}

		return objectRelationship;
	}

	private ObjectField _addObjectField(
			User user, Map<Locale, String> labelMap, String name,
			long objectDefinitionId1, long objectDefinitionId2, String type)
		throws PortalException {

		ObjectField objectField = _objectFieldPersistence.create(
			counterLocalService.increment());

		objectField.setCompanyId(user.getCompanyId());
		objectField.setUserId(user.getUserId());
		objectField.setUserName(user.getFullName());
		objectField.setListTypeDefinitionId(0);
		objectField.setObjectDefinitionId(objectDefinitionId2);
		objectField.setBusinessType(
			ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP);

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

		objectField.setDBType(ObjectFieldConstants.DB_TYPE_LONG);
		objectField.setIndexed(true);
		objectField.setIndexedAsKeyword(false);
		objectField.setIndexedLanguageId(null);
		objectField.setLabelMap(labelMap, LocaleUtil.getSiteDefault());
		objectField.setName(dbColumnName);
		objectField.setRelationshipType(type);
		objectField.setRequired(false);

		objectField = _objectFieldLocalService.updateObjectField(objectField);

		_objectFieldSettingLocalService.addObjectFieldSetting(
			user.getUserId(), objectField.getObjectFieldId(),
			ObjectFieldSettingConstants.OBJECT_DEFINITION_1_SHORT_NAME,
			objectDefinition1.getShortName());

		_objectFieldSettingLocalService.addObjectFieldSetting(
			user.getUserId(), objectField.getObjectFieldId(),
			ObjectFieldSettingConstants.OBJECT_RELATIONSHIP_ERC_FIELD_NAME,
			StringUtil.replaceLast(objectField.getName(), "Id", "ERC"));

		if (objectDefinition2.isApproved()) {
			runSQL(
				DynamicObjectDefinitionTable.getAlterTableAddColumnSQL(
					dbTableName, objectField.getDBColumnName(), "Long"));

			if (_objectDefinitionLocalService != null) {
				_objectDefinitionLocalService.deployObjectDefinition(
					objectDefinition2);
			}
		}

		return objectField;
	}

	private ObjectRelationship _addObjectRelationship(
			long userId, long objectDefinitionId1, long objectDefinitionId2,
			long parameterObjectFieldId, String deletionType,
			Map<Locale, String> labelMap, String name, boolean reverse,
			String type)
		throws PortalException {

		_validate(
			objectDefinitionId1, objectDefinitionId2, parameterObjectFieldId,
			name, type);

		ObjectRelationship objectRelationship =
			objectRelationshipPersistence.create(
				counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		objectRelationship.setCompanyId(user.getCompanyId());
		objectRelationship.setUserId(user.getUserId());
		objectRelationship.setUserName(user.getFullName());

		objectRelationship.setObjectDefinitionId1(objectDefinitionId1);
		objectRelationship.setObjectDefinitionId2(objectDefinitionId2);
		objectRelationship.setParameterObjectFieldId(parameterObjectFieldId);
		objectRelationship.setDeletionType(
			GetterUtil.getString(
				deletionType,
				ObjectRelationshipConstants.DELETION_TYPE_PREVENT));
		objectRelationship.setLabelMap(labelMap);
		objectRelationship.setName(name);
		objectRelationship.setReverse(reverse);
		objectRelationship.setType(type);

		if (Objects.equals(type, ObjectRelationshipConstants.TYPE_ONE_TO_ONE) ||
			Objects.equals(
				type, ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

			ObjectField objectField = _addObjectField(
				user, objectRelationship.getLabelMap(), name,
				objectDefinitionId1, objectDefinitionId2, type);

			objectRelationship.setObjectFieldId2(
				objectField.getObjectFieldId());
		}
		else if (Objects.equals(
					type, ObjectRelationshipConstants.TYPE_MANY_TO_MANY) &&
				 !reverse) {

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

			Map<String, String> pkObjectFieldDBColumnNames =
				ObjectRelationshipUtil.getPKObjectFieldDBColumnNames(
					objectDefinition1, objectDefinition2, false);

			String pkObjectFieldDBColumnName1 = pkObjectFieldDBColumnNames.get(
				"pkObjectFieldDBColumnName1");
			String pkObjectFieldDBColumnName2 = pkObjectFieldDBColumnNames.get(
				"pkObjectFieldDBColumnName2");

			runSQL(
				StringBundler.concat(
					"create table ", objectRelationship.getDBTableName(), " (",
					pkObjectFieldDBColumnName1, " LONG not null,",
					pkObjectFieldDBColumnName2, " LONG not null, primary key (",
					pkObjectFieldDBColumnName1, ", ",
					pkObjectFieldDBColumnName2, "))"));

			ObjectRelationship reverseObjectRelationship =
				_addObjectRelationship(
					userId, objectDefinitionId2, objectDefinitionId1,
					parameterObjectFieldId, deletionType, labelMap, name, true,
					type);

			reverseObjectRelationship.setDBTableName(
				objectRelationship.getDBTableName());

			objectRelationshipLocalService.updateObjectRelationship(
				reverseObjectRelationship);
		}

		return objectRelationshipLocalService.updateObjectRelationship(
			objectRelationship);
	}

	private boolean _hasManyToManyObjectRelationshipMappingTableValues(
		ObjectDefinition objectDefinition1, ObjectDefinition objectDefinition2,
		ObjectRelationship objectRelationship, long primaryKey1,
		long primaryKey2) {

		Map<String, String> pkObjectFieldDBColumnNames =
			ObjectRelationshipUtil.getPKObjectFieldDBColumnNames(
				objectDefinition1, objectDefinition2,
				objectRelationship.isReverse());

		DynamicObjectRelationshipMappingTable
			dynamicObjectRelationshipMappingTable =
				new DynamicObjectRelationshipMappingTable(
					pkObjectFieldDBColumnNames.get(
						"pkObjectFieldDBColumnName1"),
					pkObjectFieldDBColumnNames.get(
						"pkObjectFieldDBColumnName2"),
					objectRelationship.getDBTableName());

		Column<DynamicObjectRelationshipMappingTable, Long> primaryKeyColumn1 =
			dynamicObjectRelationshipMappingTable.getPrimaryKeyColumn1();
		Column<DynamicObjectRelationshipMappingTable, Long> primaryKeyColumn2 =
			dynamicObjectRelationshipMappingTable.getPrimaryKeyColumn2();

		int count = dslQueryCount(
			DSLQueryFactoryUtil.count(
			).from(
				dynamicObjectRelationshipMappingTable
			).where(
				primaryKeyColumn1.eq(
					primaryKey1
				).and(
					primaryKeyColumn2.eq(primaryKey2)
				)
			));

		if (count > 0) {
			return true;
		}

		return false;
	}

	private ObjectRelationship _updateObjectRelationship(
		long parameterObjectFieldId, String deletionType,
		Map<Locale, String> labelMap, ObjectRelationship objectRelationship) {

		objectRelationship.setParameterObjectFieldId(parameterObjectFieldId);
		objectRelationship.setDeletionType(deletionType);
		objectRelationship.setLabelMap(labelMap);

		return objectRelationshipPersistence.update(objectRelationship);
	}

	private void _validate(
			long objectDefinitionId1, long objectDefinitionId2,
			long parameterObjectFieldId, String name, String type)
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

		int count = objectRelationshipPersistence.countByODI1_N(
			objectDefinitionId1, name);

		if (count > 0) {
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

		ObjectDefinition objectDefinition1 =
			_objectDefinitionPersistence.fetchByPrimaryKey(objectDefinitionId1);
		ObjectDefinition objectDefinition2 =
			_objectDefinitionPersistence.fetchByPrimaryKey(objectDefinitionId2);

		if (objectDefinition1.isSystem() && objectDefinition2.isSystem()) {
			throw new ObjectRelationshipTypeException(
				"Relationships are not allowed between system objects");
		}

		if (objectDefinition1.isSystem() &&
			Objects.equals(type, ObjectRelationshipConstants.TYPE_ONE_TO_ONE)) {

			throw new ObjectRelationshipTypeException(
				"Invalid type for system object definition " +
					objectDefinitionId1);
		}

		if (Objects.equals(
				type, ObjectRelationshipConstants.TYPE_MANY_TO_MANY) ||
			Objects.equals(type, ObjectRelationshipConstants.TYPE_ONE_TO_ONE)) {

			count = objectRelationshipPersistence.countByODI1_ODI2_N_T(
				objectDefinitionId2, objectDefinitionId1, name, type);

			if (count > 0) {
				throw new ObjectRelationshipTypeException(
					"Inverse type already exists");
			}
		}

		_validateParameterObjectFieldId(
			objectDefinitionId1, objectDefinitionId2, parameterObjectFieldId,
			type);
	}

	private void _validateParameterObjectFieldId(
			long objectDefinitionId1, long objectDefinitionId2,
			long parameterObjectFieldId, String type)
		throws PortalException {

		ObjectDefinition objectDefinition1 =
			_objectDefinitionPersistence.fetchByPrimaryKey(objectDefinitionId1);

		String restContextPath = StringPool.BLANK;

		if (!objectDefinition1.isSystem()) {
			restContextPath = objectDefinition1.getRESTContextPath();
		}
		else {
			SystemObjectDefinitionMetadata systemObjectDefinitionMetadata =
				_systemObjectDefinitionMetadataTracker.
					getSystemObjectDefinitionMetadata(
						objectDefinition1.getName());

			if (systemObjectDefinitionMetadata != null) {
				restContextPath =
					systemObjectDefinitionMetadata.getRESTContextPath();
			}
		}

		boolean parameterRequired = restContextPath.matches(".*/\\{\\w+}/.*");

		if ((parameterObjectFieldId == 0) && parameterRequired) {
			throw new ObjectRelationshipParameterObjectFieldIdException(
				"Object definition " + objectDefinition1.getName() +
					" requires a parameter object field ID");
		}

		if (parameterObjectFieldId > 0) {
			if (!Objects.equals(
					type, ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

				throw new ObjectRelationshipParameterObjectFieldIdException(
					"Object relationship type " + type +
						" does not allow a parameter object field ID");
			}

			if (!parameterRequired) {
				throw new ObjectRelationshipParameterObjectFieldIdException(
					"Object definition " + objectDefinition1.getName() +
						" does not allow a parameter object field ID");
			}

			ObjectField objectField = _objectFieldLocalService.fetchObjectField(
				parameterObjectFieldId);

			if (objectField == null) {
				throw new ObjectRelationshipParameterObjectFieldIdException(
					"Parameter object field ID " + parameterObjectFieldId +
						" does not exist");
			}

			ObjectDefinition objectDefinition2 =
				_objectDefinitionPersistence.fetchByPrimaryKey(
					objectDefinitionId2);

			if (objectDefinition2.getObjectDefinitionId() !=
					objectField.getObjectDefinitionId()) {

				throw new ObjectRelationshipParameterObjectFieldIdException(
					StringBundler.concat(
						"Parameter object field ID ", parameterObjectFieldId,
						" does not belong to object definition ",
						objectDefinition2.getName()));
			}

			if (!Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP)) {

				throw new ObjectRelationshipParameterObjectFieldIdException(
					"Parameter object field ID " + parameterObjectFieldId +
						" does not belong to a relationship object field");
			}
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectFieldPersistence _objectFieldPersistence;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Reference
	private ObjectLayoutTabPersistence _objectLayoutTabPersistence;

	@Reference
	private SystemObjectDefinitionMetadataTracker
		_systemObjectDefinitionMetadataTracker;

	@Reference
	private UserLocalService _userLocalService;

}