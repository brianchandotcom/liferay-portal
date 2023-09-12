/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.impl;

import com.liferay.info.collection.provider.RelatedInfoItemCollectionProvider;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.DuplicateObjectRelationshipException;
import com.liferay.object.exception.NoSuchObjectRelationshipException;
import com.liferay.object.exception.ObjectRelationshipEdgeException;
import com.liferay.object.exception.ObjectRelationshipNameException;
import com.liferay.object.exception.ObjectRelationshipParameterObjectFieldIdException;
import com.liferay.object.exception.ObjectRelationshipReverseException;
import com.liferay.object.exception.ObjectRelationshipTypeException;
import com.liferay.object.internal.dao.db.ObjectDBManagerUtil;
import com.liferay.object.internal.info.collection.provider.RelatedInfoCollectionProviderFactory;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectFolderItem;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.model.ObjectRelationshipTable;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionTableUtil;
import com.liferay.object.petra.sql.dsl.DynamicObjectRelationshipMappingTable;
import com.liferay.object.relationship.util.ObjectRelationshipUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectFolderItemLocalService;
import com.liferay.object.service.base.ObjectRelationshipLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.service.persistence.ObjectFieldPersistence;
import com.liferay.object.service.persistence.ObjectLayoutTabPersistence;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnection;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.sql.Connection;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
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

		ObjectDefinition objectDefinition1 =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectRelationship.getObjectDefinitionId1());

		_validateObjectEntryId(objectDefinition1, primaryKey1);

		ObjectDefinition objectDefinition2 =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectRelationship.getObjectDefinitionId2());

		if (Objects.equals(
				objectRelationship.getType(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

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

			FinderCacheUtil.clearDSLQueryCache(
				objectRelationship.getDBTableName());

			return;
		}

		ObjectField objectField2 = _objectFieldLocalService.getObjectField(
			objectRelationship.getObjectFieldId2());

		if (objectDefinition2.isUnmodifiableSystemObject()) {
			_objectEntryLocalService.insertIntoOrUpdateExtensionTable(
				userId, objectRelationship.getObjectDefinitionId2(),
				primaryKey2,
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

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectRelationship createManyToManyObjectRelationshipTable(
			long userId, ObjectRelationship objectRelationship)
		throws PortalException {

		if (Validator.isNotNull(objectRelationship.getDBTableName())) {
			return objectRelationshipLocalService.updateObjectRelationship(
				objectRelationship);
		}

		ObjectDefinition objectDefinition1 =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectRelationship.getObjectDefinitionId1());
		ObjectDefinition objectDefinition2 =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectRelationship.getObjectDefinitionId2());

		if (!objectDefinition1.isApproved() ||
			!objectDefinition2.isApproved()) {

			return objectRelationshipLocalService.updateObjectRelationship(
				objectRelationship);
		}

		User user = _userLocalService.getUser(userId);

		objectRelationship.setDBTableName(
			StringBundler.concat(
				"R_", user.getCompanyId(), objectDefinition1.getShortName(),
				"_", objectDefinition2.getShortName(), "_",
				objectRelationship.getName()));

		objectRelationship =
			objectRelationshipLocalService.updateObjectRelationship(
				objectRelationship);

		ObjectRelationship reverseObjectRelationship =
			fetchReverseObjectRelationship(objectRelationship, true);

		reverseObjectRelationship.setDBTableName(
			objectRelationship.getDBTableName());

		objectRelationshipLocalService.updateObjectRelationship(
			reverseObjectRelationship);

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
				pkObjectFieldDBColumnName1, ", ", pkObjectFieldDBColumnName2,
				"))"));

		Connection connection = _currentConnection.getConnection(
			objectRelationshipPersistence.getDataSource());

		ObjectDBManagerUtil.createIndexMetadata(
			pkObjectFieldDBColumnName1, connection,
			objectRelationship.getDBTableName(), false);
		ObjectDBManagerUtil.createIndexMetadata(
			pkObjectFieldDBColumnName2, connection,
			objectRelationship.getDBTableName(), false);

		return objectRelationship;
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

		if (objectRelationship.isEdge()) {
			throw new ObjectRelationshipEdgeException(
				"Edge object relationships cannot be deleted");
		}

		if (objectRelationship.isReverse()) {
			throw new ObjectRelationshipReverseException(
				"Reverse object relationships cannot be deleted");
		}

		objectRelationship = objectRelationshipPersistence.remove(
			objectRelationship);

		_deleteObjectFields(
			objectRelationship.getObjectDefinitionId1(), objectRelationship);
		_deleteObjectFields(
			objectRelationship.getObjectDefinitionId2(), objectRelationship);

		ObjectDefinition objectDefinition1 =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectRelationship.getObjectDefinitionId1());

		_objectFolderItemLocalService.deleteObjectFolderItem(
			objectRelationship.getObjectDefinitionId2(),
			objectDefinition1.getObjectFolderId());

		ObjectDefinition objectDefinition2 =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectRelationship.getObjectDefinitionId2());

		_objectFolderItemLocalService.deleteObjectFolderItem(
			objectRelationship.getObjectDefinitionId1(),
			objectDefinition2.getObjectFolderId());

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

			if (Validator.isNotNull(objectRelationship.getDBTableName())) {
				runSQL("drop table " + objectRelationship.getDBTableName());
			}

			ObjectRelationship reverseObjectRelationship =
				fetchReverseObjectRelationship(objectRelationship, true);

			_objectLayoutTabPersistence.removeByObjectRelationshipId(
				reverseObjectRelationship.getObjectRelationshipId());

			objectRelationshipPersistence.remove(
				reverseObjectRelationship.getObjectRelationshipId());

			ServiceRegistration<?> serviceRegistration =
				_serviceRegistrations.get(
					_getServiceRegistrationKey(reverseObjectRelationship));

			if (serviceRegistration != null) {
				serviceRegistration.unregister();

				_serviceRegistrations.remove(
					_getServiceRegistrationKey(reverseObjectRelationship));
			}

			Indexer<ObjectRelationship> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(
					ObjectRelationship.class);

			indexer.delete(reverseObjectRelationship);
		}

		ServiceRegistration<?> serviceRegistration = _serviceRegistrations.get(
			_getServiceRegistrationKey(objectRelationship));

		if (serviceRegistration != null) {
			serviceRegistration.unregister();

			_serviceRegistrations.remove(
				_getServiceRegistrationKey(objectRelationship));
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

			FinderCacheUtil.clearDSLQueryCache(
				objectRelationship.getDBTableName());
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

			FinderCacheUtil.clearDSLQueryCache(
				objectRelationship.getDBTableName());
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
	public void deleteObjectRelationships(
			long objectDefinitionId1, boolean reverse)
		throws PortalException {

		for (ObjectRelationship objectRelationship :
				objectRelationshipPersistence.findByODI1_R(
					objectDefinitionId1, reverse)) {

			objectRelationshipLocalService.deleteObjectRelationship(
				objectRelationship);
		}
	}

	@Override
	public ObjectRelationship fetchObjectRelationshipByObjectDefinitionId(
		long objectDefinitionId, String name) {

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
					ObjectRelationshipTable.INSTANCE.name.eq(name)
				).and(
					ObjectRelationshipTable.INSTANCE.reverse.eq(false)
				)
			));

		if (objectRelationships.isEmpty()) {
			return null;
		}

		return objectRelationships.get(0);
	}

	@Override
	public ObjectRelationship fetchObjectRelationshipByObjectDefinitionId1(
		long objectDefinitionId1, String name) {

		return objectRelationshipPersistence.fetchByODI1_N_First(
			objectDefinitionId1, name, null);
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
	public List<ObjectRelationship> getAllObjectRelationships(
		long objectDefinitionId) {

		return dslQuery(
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
					ObjectRelationshipTable.INSTANCE.reverse.eq(false)
				)
			));
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
			long objectDefinitionId, String name)
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
					ObjectRelationshipTable.INSTANCE.name.eq(name)
				).and(
					ObjectRelationshipTable.INSTANCE.reverse.eq(false)
				)
			));

		if (objectRelationships.isEmpty()) {
			throw new NoSuchObjectRelationshipException(
				"No object relationship exists with the name " + name);
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
		long objectDefinitionId1, boolean edge) {

		return objectRelationshipPersistence.findByODI1_E(
			objectDefinitionId1, edge);
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
		long objectDefinitionId, String type) {

		Set<ObjectRelationship> objectRelationships = SetUtil.fromList(
			objectRelationshipPersistence.findByODI1_R_T(
				objectDefinitionId, false, type));

		objectRelationships.addAll(
			objectRelationshipPersistence.findByODI2_R_T(
				objectDefinitionId, false, type));

		return ListUtil.fromCollection(objectRelationships);
	}

	@Override
	public List<ObjectRelationship> getObjectRelationships(
		long objectDefinitionId1, String deletionType, boolean reverse) {

		return objectRelationshipPersistence.findByODI1_DT_R(
			objectDefinitionId1, deletionType, reverse);
	}

	@Override
	public List<ObjectRelationship> getObjectRelationshipsByObjectDefinitionId2(
		long objectDefinitionId2) {

		return objectRelationshipPersistence.findByObjectDefinitionId2(
			objectDefinitionId2);
	}

	@Override
	public void registerObjectRelationshipsRelatedInfoCollectionProviders(
		ObjectDefinition objectDefinition1,
		ObjectDefinitionLocalService objectDefinitionLocalService) {

		List<ObjectRelationship> objectRelationships =
			objectRelationshipLocalService.getObjectRelationships(
				objectDefinition1.getObjectDefinitionId());

		for (ObjectRelationship objectRelationship : objectRelationships) {
			if (!objectRelationship.isAllowedObjectRelationshipType(
					objectRelationship.getType())) {

				continue;
			}

			try {
				ObjectDefinition objectDefinition2 =
					objectDefinitionLocalService.getObjectDefinition(
						objectRelationship.getObjectDefinitionId2());

				_registerRelatedInfoItemCollectionProvider(
					objectDefinition1, objectDefinition2, objectRelationship);

				if (Objects.equals(
						objectRelationship.getType(),
						ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

					_registerRelatedInfoItemCollectionProvider(
						objectDefinition1, objectDefinition2,
						objectRelationshipLocalService.getObjectRelationship(
							objectRelationship.getObjectDefinitionId2(),
							objectRelationship.getName()));
				}
			}
			catch (PortalException portalException) {
				_log.error(portalException);
			}
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectRelationship updateObjectRelationship(
			long objectRelationshipId, long parameterObjectFieldId,
			String deletionType, boolean edge, Map<Locale, String> labelMap)
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
			_objectDefinitionPersistence.findByPrimaryKey(
				objectRelationship.getObjectDefinitionId1()),
			_objectDefinitionPersistence.findByPrimaryKey(
				objectRelationship.getObjectDefinitionId2()),
			parameterObjectFieldId, objectRelationship.getType());
		_validateEdge(edge, objectRelationship);

		if (Objects.equals(
				objectRelationship.getType(),
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

			ObjectRelationship reverseObjectRelationship =
				fetchReverseObjectRelationship(objectRelationship, true);

			_updateObjectRelationship(
				parameterObjectFieldId, deletionType, false, labelMap,
				reverseObjectRelationship);

			Indexer<ObjectRelationship> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(
					ObjectRelationship.class);

			indexer.reindex(reverseObjectRelationship);
		}

		objectRelationship = _updateObjectRelationship(
			parameterObjectFieldId, deletionType, edge, labelMap,
			objectRelationship);

		if ((objectRelationship.getObjectFieldId2() != 0) &&
			StringUtil.equals(
				deletionType,
				ObjectRelationshipConstants.DELETION_TYPE_DISASSOCIATE)) {

			_objectFieldLocalService.updateRequired(
				objectRelationship.getObjectFieldId2(), false);
		}

		return objectRelationship;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private ObjectField _addObjectField(
			User user, Map<Locale, String> labelMap, String name,
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2, String type)
		throws PortalException {

		ObjectField objectField = _objectFieldPersistence.create(
			counterLocalService.increment());

		objectField.setCompanyId(user.getCompanyId());
		objectField.setUserId(user.getUserId());
		objectField.setUserName(user.getFullName());
		objectField.setListTypeDefinitionId(0);
		objectField.setObjectDefinitionId(
			objectDefinition2.getObjectDefinitionId());
		objectField.setBusinessType(
			ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP);

		String dbColumnName = StringBundler.concat(
			"r_", name, "_", objectDefinition1.getPKObjectFieldName());

		objectField.setDBColumnName(dbColumnName);

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
		objectField.setReadOnly(ObjectFieldConstants.READ_ONLY_FALSE);
		objectField.setReadOnlyConditionExpression(StringPool.BLANK);
		objectField.setRelationshipType(type);
		objectField.setRequired(false);

		objectField = _objectFieldLocalService.updateObjectField(objectField);

		_objectFieldSettingLocalService.addObjectFieldSetting(
			user.getUserId(), objectField.getObjectFieldId(),
			ObjectFieldSettingConstants.NAME_OBJECT_DEFINITION_1_SHORT_NAME,
			objectDefinition1.getShortName());

		_objectFieldSettingLocalService.addObjectFieldSetting(
			user.getUserId(), objectField.getObjectFieldId(),
			ObjectFieldSettingConstants.
				NAME_OBJECT_RELATIONSHIP_ERC_OBJECT_FIELD_NAME,
			StringUtil.replaceLast(objectField.getName(), "Id", "ERC"));

		if (!objectDefinition2.isApproved()) {
			return objectField;
		}

		runSQL(
			DynamicObjectDefinitionTableUtil.getAlterTableAddColumnSQL(
				dbTableName, objectField.getDBColumnName(), "Long"));

		ObjectDBManagerUtil.createIndexMetadata(
			objectField.getDBColumnName(),
			_currentConnection.getConnection(
				objectRelationshipPersistence.getDataSource()),
			dbTableName, false);

		ObjectDefinitionLocalService objectDefinitionLocalService =
			_objectDefinitionLocalServiceSnapshot.get();

		if (objectDefinitionLocalService != null) {
			objectDefinitionLocalService.deployObjectDefinition(
				objectDefinition2);
		}

		return objectField;
	}

	private void _addObjectFolderItem(
			long userId, long objectDefinitionId, long objectFolderId)
		throws PortalException {

		ObjectFolderItem objectFolderItem =
			_objectFolderItemLocalService.fetchObjectFolderItem(
				objectDefinitionId, objectFolderId);

		if (objectFolderItem != null) {
			return;
		}

		_objectFolderItemLocalService.addObjectFolderItem(
			userId, objectDefinitionId, objectFolderId, 0, 0);
	}

	private ObjectRelationship _addObjectRelationship(
			long userId, long objectDefinitionId1, long objectDefinitionId2,
			long parameterObjectFieldId, String deletionType,
			Map<Locale, String> labelMap, String name, boolean reverse,
			String type)
		throws PortalException {

		_validateName(objectDefinitionId1, name);

		ObjectDefinition objectDefinition1 =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId1);
		ObjectDefinition objectDefinition2 =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId2);

		_validateType(
			objectDefinition1, objectDefinition2, name, parameterObjectFieldId,
			type);

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

		_addObjectFolderItem(
			userId, objectDefinition1.getObjectDefinitionId(),
			objectDefinition2.getObjectFolderId());
		_addObjectFolderItem(
			userId, objectDefinition2.getObjectDefinitionId(),
			objectDefinition1.getObjectFolderId());

		if (Objects.equals(type, ObjectRelationshipConstants.TYPE_ONE_TO_ONE) ||
			Objects.equals(
				type, ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

			ObjectField objectField = _addObjectField(
				user, objectRelationship.getLabelMap(), name, objectDefinition1,
				objectDefinition2, type);

			objectRelationship.setObjectFieldId2(
				objectField.getObjectFieldId());
		}
		else if (Objects.equals(
					type, ObjectRelationshipConstants.TYPE_MANY_TO_MANY) &&
				 !reverse) {

			_registerRelatedInfoItemCollectionProvider(
				objectDefinition1, objectDefinition2, objectRelationship);

			_addObjectRelationship(
				userId, objectDefinitionId2, objectDefinitionId1,
				parameterObjectFieldId, deletionType, labelMap, name, true,
				type);

			return objectRelationshipLocalService.
				createManyToManyObjectRelationshipTable(
					userId, objectRelationship);
		}

		_registerRelatedInfoItemCollectionProvider(
			objectDefinition1, objectDefinition2, objectRelationship);

		return objectRelationshipLocalService.updateObjectRelationship(
			objectRelationship);
	}

	private void _deleteObjectFields(
			long objectDefinitionId, ObjectRelationship objectRelationship)
		throws PortalException {

		for (ObjectField objectField :
				_objectFieldPersistence.findByObjectDefinitionId(
					objectDefinitionId)) {

			ObjectFieldSetting objectFieldSetting =
				_objectFieldSettingLocalService.fetchObjectFieldSetting(
					objectField.getObjectFieldId(), "objectRelationshipName");

			if ((objectFieldSetting != null) &&
				StringUtil.equals(
					objectFieldSetting.getValue(),
					objectRelationship.getName())) {

				_objectFieldLocalService.deleteObjectField(
					objectField.getObjectFieldId());
			}
		}
	}

	private String _getServiceRegistrationKey(
		ObjectRelationship objectRelationship) {

		return StringBundler.concat(
			objectRelationship.getCompanyId(), StringPool.POUND,
			objectRelationship.getObjectRelationshipId());
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

	private void _registerRelatedInfoItemCollectionProvider(
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2,
			ObjectRelationship objectRelationship)
		throws PortalException {

		RelatedInfoItemCollectionProvider relatedInfoItemCollectionProvider =
			_relatedInfoCollectionProviderFactory.create(
				objectDefinition1, objectDefinition2, objectRelationship);

		if (relatedInfoItemCollectionProvider == null) {
			return;
		}

		_serviceRegistrations.computeIfAbsent(
			_getServiceRegistrationKey(objectRelationship),
			serviceRegistrationKey -> _bundleContext.registerService(
				RelatedInfoItemCollectionProvider.class,
				relatedInfoItemCollectionProvider,
				HashMapDictionaryBuilder.<String, Object>put(
					"company.id", objectDefinition1.getCompanyId()
				).put(
					"item.class.name", objectDefinition1.getClassName()
				).build()));
	}

	private ObjectRelationship _updateObjectRelationship(
		long parameterObjectFieldId, String deletionType, boolean edge,
		Map<Locale, String> labelMap, ObjectRelationship objectRelationship) {

		objectRelationship.setParameterObjectFieldId(parameterObjectFieldId);
		objectRelationship.setDeletionType(deletionType);
		objectRelationship.setEdge(edge);
		objectRelationship.setLabelMap(labelMap);

		return objectRelationshipPersistence.update(objectRelationship);
	}

	private void _validateEdge(
			boolean edge, ObjectRelationship objectRelationship)
		throws PortalException {

		if (!edge) {
			return;
		}

		if (!Objects.equals(
				objectRelationship.getType(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

			throw new ObjectRelationshipEdgeException(
				"Object relationship must be one to many to be an edge of a " +
					"root context");
		}

		if (objectRelationship.isSelf()) {
			throw new ObjectRelationshipEdgeException(
				"Object relationship must not be a self-relationship to be " +
					"an edge of a root context");
		}

		ObjectDefinitionLocalService objectDefinitionLocalService =
			_objectDefinitionLocalServiceSnapshot.get();

		ObjectDefinition objectDefinition1 =
			objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId1());
		ObjectDefinition objectDefinition2 =
			objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId2());

		if (objectDefinition1.isUnmodifiableSystemObject() ||
			objectDefinition2.isUnmodifiableSystemObject()) {

			throw new ObjectRelationshipEdgeException(
				"Object relationship must not be between unmodifiable system " +
					"object definitions to be an edge of a root context");
		}
	}

	private void _validateName(long objectDefinitionId1, String name)
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
				"Name must be less than 41 characters");
		}

		int count = objectRelationshipPersistence.countByODI1_N(
			objectDefinitionId1, name);

		if (count > 0) {
			throw new DuplicateObjectRelationshipException(
				"Duplicate name " + name);
		}
	}

	private void _validateObjectEntryId(
			ObjectDefinition objectDefinition, long primaryKey)
		throws PortalException {

		if (objectDefinition.isUnmodifiableSystemObject()) {
			SystemObjectDefinitionManager systemObjectDefinitionManager =
				_systemObjectDefinitionManagerRegistry.
					getSystemObjectDefinitionManager(
						objectDefinition.getName());

			systemObjectDefinitionManager.getBaseModelExternalReferenceCode(
				primaryKey);
		}
		else {
			_objectEntryLocalService.getObjectEntry(primaryKey);
		}
	}

	private void _validateParameterObjectFieldId(
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2, long parameterObjectFieldId,
			String type)
		throws PortalException {

		String restContextPath = StringPool.BLANK;

		if (!objectDefinition1.isUnmodifiableSystemObject()) {
			restContextPath = objectDefinition1.getRESTContextPath();
		}
		else {
			SystemObjectDefinitionManager systemObjectDefinitionManager =
				_systemObjectDefinitionManagerRegistry.
					getSystemObjectDefinitionManager(
						objectDefinition1.getName());

			if (systemObjectDefinitionManager != null) {
				JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
					systemObjectDefinitionManager.
						getJaxRsApplicationDescriptor();

				restContextPath =
					jaxRsApplicationDescriptor.getRESTContextPath();
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

	private void _validateType(
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2, String name,
			long parameterObjectFieldId, String type)
		throws PortalException {

		Set<String> defaultObjectRelationshipTypes =
			ObjectRelationshipUtil.getDefaultObjectRelationshipTypes();

		if (!defaultObjectRelationshipTypes.contains(type)) {
			throw new ObjectRelationshipTypeException("Invalid type " + type);
		}

		if (Objects.equals(
				type, ObjectRelationshipConstants.TYPE_MANY_TO_MANY) ||
			Objects.equals(type, ObjectRelationshipConstants.TYPE_ONE_TO_ONE)) {

			int count = objectRelationshipPersistence.countByODI1_ODI2_N_T(
				objectDefinition2.getObjectDefinitionId(),
				objectDefinition1.getObjectDefinitionId(), name, type);

			if (count > 0) {
				throw new ObjectRelationshipTypeException(
					"Inverse type already exists");
			}
		}

		if (objectDefinition1.isUnmodifiableSystemObject()) {
			if (objectDefinition2.isUnmodifiableSystemObject()) {
				throw new ObjectRelationshipTypeException(
					"Relationships are not allowed between system objects");
			}

			SystemObjectDefinitionManager systemObjectDefinitionManager =
				_systemObjectDefinitionManagerRegistry.
					getSystemObjectDefinitionManager(
						objectDefinition1.getName());

			Set<String> allowedObjectRelationshipTypes =
				systemObjectDefinitionManager.
					getAllowedObjectRelationshipTypes();

			if (!allowedObjectRelationshipTypes.contains(type)) {
				throw new ObjectRelationshipTypeException(
					"Invalid type for system object definition " +
						objectDefinition1.getObjectDefinitionId());
			}
		}

		_validateParameterObjectFieldId(
			objectDefinition1, objectDefinition2, parameterObjectFieldId, type);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectRelationshipLocalServiceImpl.class);

	private static final Snapshot<ObjectDefinitionLocalService>
		_objectDefinitionLocalServiceSnapshot = new Snapshot<>(
			ObjectRelationshipLocalServiceImpl.class,
			ObjectDefinitionLocalService.class, null, true);

	private BundleContext _bundleContext;

	@Reference
	private CurrentConnection _currentConnection;

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
	private ObjectFolderItemLocalService _objectFolderItemLocalService;

	@Reference
	private ObjectLayoutTabPersistence _objectLayoutTabPersistence;

	@Reference
	private RelatedInfoCollectionProviderFactory
		_relatedInfoCollectionProviderFactory;

	private final Map<String, ServiceRegistration<?>> _serviceRegistrations =
		new ConcurrentHashMap<>();

	@Reference
	private SystemObjectDefinitionManagerRegistry
		_systemObjectDefinitionManagerRegistry;

	@Reference
	private UserLocalService _userLocalService;

}