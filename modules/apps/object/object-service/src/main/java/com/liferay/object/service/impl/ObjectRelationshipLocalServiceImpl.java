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
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.DuplicateObjectRelationshipException;
import com.liferay.object.exception.ObjectRelationshipNameException;
import com.liferay.object.exception.ObjectRelationshipReverseException;
import com.liferay.object.exception.ObjectRelationshipTypeException;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.base.ObjectRelationshipLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.service.persistence.ObjectFieldPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
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
			String deletionType, Map<Locale, String> labelMap, String name,
			String type)
		throws PortalException {

		return _addObjectRelationship(
			userId, objectDefinitionId1, objectDefinitionId2, deletionType,
			labelMap, name, false, type);
	}

	@Override
	public void addObjectRelationshipMappingTableValues(
			long userId, long objectRelationshipId, long primaryKey1,
			long primaryKey2, ServiceContext serviceContext)
		throws PortalException {

		ObjectRelationship objectRelationship =
			objectRelationshipLocalService.getObjectRelationship(
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

			runSQL(
				StringBundler.concat(
					"insert into ", objectRelationship.getDBTableName(), " (",
					objectDefinition1.getPKObjectFieldDBColumnName(), " , ",
					objectDefinition2.getPKObjectFieldDBColumnName(),
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

		return deleteObjectRelationship(objectRelationship);
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

			objectRelationshipPersistence.remove(
				reverseObjectRelationship.getObjectRelationshipId());
		}

		return objectRelationship;
	}

	@Override
	public void deleteObjectRelationshipMappingTableValues(
			long objectRelationshipId, long primaryKey1)
		throws PortalException {

		ObjectRelationship objectRelationship =
			objectRelationshipLocalService.getObjectRelationship(
				objectRelationshipId);

		if (Objects.equals(
				objectRelationship.getType(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

			ObjectDefinition objectDefinition1 =
				_objectDefinitionPersistence.findByPrimaryKey(
					objectRelationship.getObjectDefinitionId1());

			runSQL(
				StringBundler.concat(
					"delete from ", objectRelationship.getDBTableName(),
					" where ", objectDefinition1.getPKObjectFieldDBColumnName(),
					" = ", primaryKey1));
		}
	}

	@Override
	public void deleteObjectRelationshipMappingTableValues(
			long objectRelationshipId, long primaryKey1, long primaryKey2)
		throws PortalException {

		ObjectRelationship objectRelationship =
			objectRelationshipLocalService.getObjectRelationship(
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

			runSQL(
				StringBundler.concat(
					"delete from ", objectRelationship.getDBTableName(),
					" where ", objectDefinition1.getPKObjectFieldDBColumnName(),
					" = ", primaryKey1, " and ",
					objectDefinition2.getPKObjectFieldDBColumnName(), " = ",
					primaryKey2));
		}
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

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectRelationship updateObjectRelationship(
			long objectRelationshipId, String deletionType,
			Map<Locale, String> labelMap)
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

		objectRelationship.setDeletionType(deletionType);
		objectRelationship.setLabelMap(labelMap);

		return objectRelationshipPersistence.update(objectRelationship);
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
			String deletionType, Map<Locale, String> labelMap, String name,
			boolean reverse, String type)
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

			runSQL(
				StringBundler.concat(
					"create table ", objectRelationship.getDBTableName(), " (",
					objectDefinition1.getPKObjectFieldDBColumnName(),
					" LONG not null,",
					objectDefinition2.getPKObjectFieldDBColumnName(),
					" LONG not null, primary key (",
					objectDefinition1.getPKObjectFieldDBColumnName(), ", ",
					objectDefinition2.getPKObjectFieldDBColumnName(), "))"));

			ObjectRelationship reverseObjectRelationship =
				_addObjectRelationship(
					userId, objectDefinitionId2, objectDefinitionId1,
					deletionType, labelMap, name, true, type);

			reverseObjectRelationship.setDBTableName(
				objectRelationship.getDBTableName());

			objectRelationshipLocalService.updateObjectRelationship(
				reverseObjectRelationship);
		}

		return objectRelationshipLocalService.updateObjectRelationship(
			objectRelationship);
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

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.fetchByPrimaryKey(objectDefinitionId1);

		if (objectDefinition.isSystem() &&
			!Objects.equals(
				type, ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

			throw new ObjectRelationshipTypeException(
				"Invalid type for system object definition " +
					objectDefinitionId1);
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
	private UserLocalService _userLocalService;

}