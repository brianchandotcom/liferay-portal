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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLinkConstants;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.NoSuchObjectFieldException;
import com.liferay.object.exception.ObjectDefinitionScopeException;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectRelationshipMappingTable;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.related.models.ObjectRelatedModelsProviderRegistry;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.base.ObjectEntryLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.service.persistence.ObjectFieldPersistence;
import com.liferay.object.service.persistence.ObjectRelationshipPersistence;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.spi.ast.DefaultASTNodeListener;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.jdbc.postgresql.PostgreSQLJDBCUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnection;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelper;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.sort.SortFieldBuilder;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringReader;

import java.math.BigDecimal;

import java.nio.charset.StandardCharsets;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectEntry",
	service = AopService.class
)
public class ObjectEntryLocalServiceImpl
	extends ObjectEntryLocalServiceBaseImpl {

	@Override
	public ObjectEntry addObjectEntry(
			long userId, long groupId, long objectDefinitionId,
			Map<String, Serializable> values, ServiceContext serviceContext)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		_validateGroupId(groupId, objectDefinition.getScope());

		_validateValues(objectDefinitionId, values);

		long objectEntryId = counterLocalService.increment();

		_insertIntoTable(
			_getDynamicObjectDefinitionTable(objectDefinitionId), objectEntryId,
			values);
		_insertIntoTable(
			_getExtensionDynamicObjectDefinitionTable(objectDefinitionId),
			objectEntryId, values);

		ObjectEntry objectEntry = objectEntryPersistence.create(objectEntryId);

		objectEntry.setGroupId(groupId);

		User user = _userLocalService.getUser(userId);

		objectEntry.setCompanyId(user.getCompanyId());
		objectEntry.setUserId(user.getUserId());
		objectEntry.setUserName(user.getFullName());

		objectEntry.setObjectDefinitionId(objectDefinitionId);
		objectEntry.setStatus(WorkflowConstants.STATUS_DRAFT);
		objectEntry.setStatusByUserId(user.getUserId());
		objectEntry.setStatusDate(serviceContext.getModifiedDate(null));

		objectEntry = objectEntryPersistence.update(objectEntry);

		_resourceLocalService.addResources(
			objectEntry.getCompanyId(), objectEntry.getGroupId(),
			objectEntry.getUserId(), objectDefinition.getClassName(),
			objectEntry.getPrimaryKey(), false, false, false);

		updateAsset(
			serviceContext.getUserId(), objectEntry,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds(),
			serviceContext.getAssetPriority());

		_startWorkflowInstance(userId, objectEntry, serviceContext);

		_reindex(objectEntry);

		return objectEntry;
	}

	@Override
	public ObjectEntry addOrUpdateObjectEntry(
			String externalReferenceCode, long userId, long groupId,
			long objectDefinitionId, Map<String, Serializable> values,
			ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		if (groupId != 0) {
			Group group = _groupLocalService.getGroup(groupId);

			if (user.getCompanyId() != group.getCompanyId()) {
				throw new PrincipalException();
			}
		}

		ObjectEntry objectEntry = objectEntryPersistence.fetchByG_C_ERC(
			groupId, user.getCompanyId(), externalReferenceCode);

		if (objectEntry != null) {
			return updateObjectEntry(
				userId, objectEntry.getObjectEntryId(), values, serviceContext);
		}

		objectEntry = addObjectEntry(
			userId, groupId, objectDefinitionId, values, serviceContext);

		objectEntry.setExternalReferenceCode(externalReferenceCode);

		objectEntry = objectEntryPersistence.update(objectEntry);

		_reindex(objectEntry);

		return objectEntry;
	}

	@Override
	public ObjectEntry deleteObjectEntry(long objectEntryId)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryPersistence.findByPrimaryKey(
			objectEntryId);

		return objectEntryLocalService.deleteObjectEntry(objectEntry);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public ObjectEntry deleteObjectEntry(ObjectEntry objectEntry)
		throws PortalException {

		objectEntry = objectEntryPersistence.remove(objectEntry);

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectEntry.getObjectDefinitionId());

		_resourceLocalService.deleteResource(
			objectEntry.getCompanyId(), objectDefinition.getClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL, objectEntry.getObjectEntryId());

		_assetEntryLocalService.deleteEntry(
			objectDefinition.getClassName(), objectEntry.getObjectEntryId());

		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			objectEntry.getCompanyId(), objectEntry.getNonzeroGroupId(),
			objectDefinition.getClassName(), objectEntry.getObjectEntryId());

		_deleteFromTable(
			objectDefinition.getDBTableName(), objectDefinition, objectEntry);
		_deleteFromTable(
			objectDefinition.getExtensionDBTableName(), objectDefinition,
			objectEntry);

		deleteRelatedObjectEntries(
			objectEntry.getGroupId(), objectDefinition.getObjectDefinitionId(),
			objectEntry.getPrimaryKey());

		_deleteFileEntries(
			Collections.emptyMap(), objectDefinition.getObjectDefinitionId(),
			objectEntry.getValues());

		Indexer<ObjectEntry> indexer = IndexerRegistryUtil.getIndexer(
			objectDefinition.getClassName());

		indexer.delete(objectEntry);

		return objectEntry;
	}

	@Override
	public ObjectEntry deleteObjectEntry(
			String externalReferenceCode, long companyId, long groupId)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryPersistence.findByG_C_ERC(
			groupId, companyId, externalReferenceCode);

		return objectEntryLocalService.deleteObjectEntry(objectEntry);
	}

	@Override
	public void deleteRelatedObjectEntries(
			long groupId, long objectDefinitionId, long primaryKey)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		List<ObjectRelationship> objectRelationships =
			_objectRelationshipPersistence.findByObjectDefinitionId1(
				objectDefinitionId);

		for (ObjectRelationship objectRelationship : objectRelationships) {
			ObjectRelatedModelsProvider objectRelatedModelsProvider =
				_objectRelatedModelsProviderRegistry.
					getObjectRelatedModelsProvider(
						objectDefinition.getClassName(),
						objectRelationship.getType());

			objectRelatedModelsProvider.deleteRelatedModel(
				PrincipalThreadLocal.getUserId(), groupId,
				objectRelationship.getObjectRelationshipId(), primaryKey);
		}
	}

	@Override
	public List<ObjectEntry> getManyToManyRelatedObjectEntries(
			long groupId, long objectRelationshipId, long primaryKey,
			boolean reverse, int start, int end)
		throws PortalException {

		DSLQuery dslQuery = _getManyToManyRelatedObjectEntriesGroupByStep(
			groupId, objectRelationshipId, primaryKey, reverse,
			DSLQueryFactoryUtil.selectDistinct(ObjectEntryTable.INSTANCE)
		).limit(
			start, end
		);

		if (_log.isDebugEnabled()) {
			_log.debug("Get many to many related object entries: " + dslQuery);
		}

		return objectEntryPersistence.dslQuery(dslQuery);
	}

	@Override
	public int getManyToManyRelatedObjectEntriesCount(
			long groupId, long objectRelationshipId, long primaryKey,
			boolean reverse)
		throws PortalException {

		DSLQuery dslQuery = _getManyToManyRelatedObjectEntriesGroupByStep(
			groupId, objectRelationshipId, primaryKey, reverse,
			DSLQueryFactoryUtil.countDistinct(
				ObjectEntryTable.INSTANCE.objectEntryId));

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Get many to many related object entries count: " + dslQuery);
		}

		return objectEntryPersistence.dslQueryCount(dslQuery);
	}

	@Override
	public List<ObjectEntry> getObjectEntries(
			long groupId, long objectDefinitionId, int start, int end)
		throws PortalException {

		return objectEntryPersistence.findByG_ODI(
			groupId, objectDefinitionId, start, end);
	}

	@Override
	public List<ObjectEntry> getObjectEntries(
			long groupId, long objectDefinitionId, int status, int start,
			int end)
		throws PortalException {

		return objectEntryPersistence.findByG_ODI_S(
			groupId, objectDefinitionId, status, start, end);
	}

	@Override
	public int getObjectEntriesCount(long groupId, long objectDefinitionId) {
		return objectEntryPersistence.countByG_ODI(groupId, objectDefinitionId);
	}

	@Override
	public ObjectEntry getObjectEntry(
			String externalReferenceCode, long companyId, long groupId)
		throws PortalException {

		return objectEntryPersistence.findByG_C_ERC(
			groupId, companyId, externalReferenceCode);
	}

	@Override
	public List<ObjectEntry> getOneToManyRelatedObjectEntries(
			long groupId, long objectRelationshipId, long primaryKey, int start,
			int end)
		throws PortalException {

		DSLQuery dslQuery = _getOneToManyRelatedObjectEntriesGroupByStep(
			groupId, objectRelationshipId, primaryKey,
			DSLQueryFactoryUtil.selectDistinct(ObjectEntryTable.INSTANCE)
		).limit(
			start, end
		);

		if (_log.isDebugEnabled()) {
			_log.debug("Get one to many related object entries: " + dslQuery);
		}

		return objectEntryPersistence.dslQuery(dslQuery);
	}

	@Override
	public int getOneToManyRelatedObjectEntriesCount(
			long groupId, long objectRelationshipId, long primaryKey)
		throws PortalException {

		DSLQuery dslQuery = _getOneToManyRelatedObjectEntriesGroupByStep(
			groupId, objectRelationshipId, primaryKey,
			DSLQueryFactoryUtil.countDistinct(
				ObjectEntryTable.INSTANCE.objectEntryId));

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Get one to many related object entries count: " + dslQuery);
		}

		return objectEntryPersistence.dslQueryCount(dslQuery);
	}

	@Override
	public Map<String, Serializable> getValues(long objectEntryId)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryPersistence.findByPrimaryKey(
			objectEntryId);

		return getValues(objectEntry);
	}

	@Override
	public Map<String, Serializable> getValues(ObjectEntry objectEntry)
		throws PortalException {

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			_getDynamicObjectDefinitionTable(
				objectEntry.getObjectDefinitionId());
		DynamicObjectDefinitionTable extensionDynamicObjectDefinitionTable =
			_getExtensionDynamicObjectDefinitionTable(
				objectEntry.getObjectDefinitionId());

		Expression<?>[] selectExpressions = ArrayUtil.append(
			dynamicObjectDefinitionTable.getSelectExpressions(),
			extensionDynamicObjectDefinitionTable.getSelectExpressions());

		List<Object[]> rows = _list(
			DSLQueryFactoryUtil.selectDistinct(
				selectExpressions
			).from(
				dynamicObjectDefinitionTable
			).innerJoinON(
				extensionDynamicObjectDefinitionTable,
				dynamicObjectDefinitionTable.getPrimaryKeyColumn(
				).eq(
					extensionDynamicObjectDefinitionTable.getPrimaryKeyColumn()
				)
			).where(
				dynamicObjectDefinitionTable.getPrimaryKeyColumn(
				).eq(
					objectEntry.getObjectEntryId()
				)
			),
			selectExpressions);

		return _getValues(rows.get(0), selectExpressions);
	}

	@Override
	public List<Map<String, Serializable>> getValuesList(
			long objectDefinitionId, int[] statuses, int start, int end)
		throws PortalException {

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			_getDynamicObjectDefinitionTable(objectDefinitionId);
		DynamicObjectDefinitionTable extensionDynamicObjectDefinitionTable =
			_getExtensionDynamicObjectDefinitionTable(objectDefinitionId);

		Expression<?>[] selectExpressions = ArrayUtil.append(
			dynamicObjectDefinitionTable.getSelectExpressions(),
			extensionDynamicObjectDefinitionTable.getSelectExpressions());

		List<Object[]> rows = _list(
			DSLQueryFactoryUtil.selectDistinct(
				selectExpressions
			).from(
				dynamicObjectDefinitionTable
			).innerJoinON(
				ObjectEntryTable.INSTANCE,
				ObjectEntryTable.INSTANCE.objectEntryId.eq(
					dynamicObjectDefinitionTable.getPrimaryKeyColumn())
			).innerJoinON(
				extensionDynamicObjectDefinitionTable,
				extensionDynamicObjectDefinitionTable.getPrimaryKeyColumn(
				).eq(
					dynamicObjectDefinitionTable.getPrimaryKeyColumn()
				)
			).where(
				ObjectEntryTable.INSTANCE.objectDefinitionId.eq(
					objectDefinitionId
				).and(
					() -> {
						if (ArrayUtil.isEmpty(statuses)) {
							return null;
						}

						return ObjectEntryTable.INSTANCE.status.in(
							ArrayUtil.toArray(statuses));
					}
				).and(
					() -> {
						if (PermissionThreadLocal.getPermissionChecker() ==
								null) {

							return null;
						}

						return _inlineSQLHelper.getPermissionWherePredicate(
							dynamicObjectDefinitionTable.getName(),
							dynamicObjectDefinitionTable.getPrimaryKeyColumn());
					}
				)
			).limit(
				start, end
			),
			selectExpressions);

		List<Map<String, Serializable>> valuesList = new ArrayList<>(
			rows.size());

		for (Object[] objects : rows) {
			Map<String, Serializable> values = _getValues(
				objects, selectExpressions);

			valuesList.add(values);
		}

		return valuesList;
	}

	@Override
	public void insertIntoOrUpdateExtensionTable(
			long objectDefinitionId, long primaryKey,
			Map<String, Serializable> values)
		throws PortalException {

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			_getExtensionDynamicObjectDefinitionTable(objectDefinitionId);

		Column<DynamicObjectDefinitionTable, Long> primaryKeyColumn =
			dynamicObjectDefinitionTable.getPrimaryKeyColumn();

		int count = objectEntryPersistence.dslQueryCount(
			DSLQueryFactoryUtil.count(
			).from(
				dynamicObjectDefinitionTable
			).where(
				primaryKeyColumn.eq(primaryKey)
			));

		if (count > 0) {
			_updateTable(dynamicObjectDefinitionTable, primaryKey, values);
		}
		else {
			_insertIntoTable(dynamicObjectDefinitionTable, primaryKey, values);
		}
	}

	@Override
	public BaseModelSearchResult<ObjectEntry> searchObjectEntries(
			long groupId, long objectDefinitionId, String keywords, int cur,
			int delta)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		SearchRequestBuilder searchRequestBuilder =
			_searchRequestBuilderFactory.builder();

		searchRequestBuilder.entryClassNames(
			objectDefinition.getClassName()
		).emptySearchEnabled(
			true
		).from(
			cur
		).size(
			delta
		).sorts(
			_sorts.field(Field.ENTRY_CLASS_PK, SortOrder.ASC)
		).withSearchContext(
			searchContext -> {
				searchContext.setAttribute(
					Field.STATUS, WorkflowConstants.STATUS_ANY);
				searchContext.setAttribute(
					"objectDefinitionId",
					objectDefinition.getObjectDefinitionId());
				searchContext.setCompanyId(objectDefinition.getCompanyId());

				if (objectScopeProvider.isGroupAware()) {
					searchContext.setGroupIds(new long[] {groupId});
				}
				else {
					searchContext.setGroupIds(new long[] {0});
				}

				searchContext.setKeywords(keywords);
			}
		);

		SearchResponse searchResponse = _searcher.search(
			searchRequestBuilder.build());

		SearchHits searchHits = searchResponse.getSearchHits();

		List<SearchHit> searchHitsList = searchHits.getSearchHits();

		Stream<SearchHit> stream = searchHitsList.stream();

		List<ObjectEntry> objectEntries = stream.map(
			searchHit -> {
				Document document = searchHit.getDocument();

				long objectEntryId = document.getLong(Field.ENTRY_CLASS_PK);

				return objectEntryPersistence.fetchByPrimaryKey(objectEntryId);
			}
		).collect(
			Collectors.toList()
		);

		return new BaseModelSearchResult<>(
			objectEntries, searchResponse.getTotalHits());
	}

	@Override
	public void updateAsset(
			long userId, ObjectEntry objectEntry, long[] assetCategoryIds,
			String[] assetTagNames, long[] assetLinkEntryIds, Double priority)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectEntry.getObjectDefinitionId());

		String title = StringPool.BLANK;

		try {
			title = objectEntry.getTitleValue();
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}

		AssetEntry assetEntry = _assetEntryLocalService.updateEntry(
			userId, objectEntry.getNonzeroGroupId(),
			objectEntry.getCreateDate(), objectEntry.getModifiedDate(),
			objectDefinition.getClassName(), objectEntry.getObjectEntryId(),
			objectEntry.getUuid(), 0, assetCategoryIds, assetTagNames, true,
			objectEntry.isApproved(), null, null, null, null,
			ContentTypes.TEXT_PLAIN, title,
			String.valueOf(objectEntry.getObjectEntryId()), null, null, null, 0,
			0, priority);

		_assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);
	}

	@Override
	public ObjectEntry updateObjectEntry(
			long userId, long objectEntryId, Map<String, Serializable> values,
			ServiceContext serviceContext)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryPersistence.findByPrimaryKey(
			objectEntryId);

		_validateValues(objectEntry.getObjectDefinitionId(), values);

		Map<String, Serializable> transientValues = objectEntry.getValues();

		objectEntry.setTransientValues(transientValues);

		_updateTable(
			_getDynamicObjectDefinitionTable(
				objectEntry.getObjectDefinitionId()),
			objectEntryId, values);
		_updateTable(
			_getExtensionDynamicObjectDefinitionTable(
				objectEntry.getObjectDefinitionId()),
			objectEntryId, values);

		objectEntry.setModifiedDate(serviceContext.getModifiedDate(null));
		objectEntry.setValues(null);

		objectEntry = objectEntryPersistence.update(objectEntry);

		updateAsset(
			serviceContext.getUserId(), objectEntry,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds(),
			serviceContext.getAssetPriority());

		_startWorkflowInstance(userId, objectEntry, serviceContext);

		_deleteFileEntries(
			values, objectEntry.getObjectDefinitionId(), transientValues);

		_reindex(objectEntry);

		return objectEntry;
	}

	@Override
	public ObjectEntry updateStatus(
			long userId, long objectEntryId, int status,
			ServiceContext serviceContext)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryPersistence.findByPrimaryKey(
			objectEntryId);

		if (objectEntry.getStatus() == status) {
			return objectEntry;
		}

		objectEntry.setStatus(status);

		User user = _userLocalService.getUser(userId);

		objectEntry.setStatusByUserId(user.getUserId());
		objectEntry.setStatusByUserName(user.getFullName());

		objectEntry.setStatusDate(serviceContext.getModifiedDate(null));

		objectEntry = objectEntryPersistence.update(objectEntry);

		_reindex(objectEntry);

		return objectEntry;
	}

	private void _deleteFileEntries(
		Map<String, Serializable> newValues, long objectDefinitionId,
		Map<String, Serializable> oldValues) {

		List<ObjectField> objectFields =
			_objectFieldPersistence.findByObjectDefinitionId(
				objectDefinitionId);

		for (ObjectField objectField : objectFields) {
			String objectFieldName = objectField.getName();

			if (!Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT) ||
				Objects.equals(
					GetterUtil.getLong(newValues.get(objectFieldName)),
					GetterUtil.getLong(oldValues.get(objectFieldName)))) {

				continue;
			}

			try {
				_dlFileEntryLocalService.deleteFileEntry(
					GetterUtil.getLong(oldValues.get(objectFieldName)));
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}
			}
		}
	}

	private void _deleteFromTable(
			String dbTableName, ObjectDefinition objectDefinition,
			ObjectEntry objectEntry)
		throws PortalException {

		runSQL(
			StringBundler.concat(
				"delete from ", dbTableName, " where ",
				objectDefinition.getPKObjectFieldDBColumnName(), " = ",
				objectEntry.getObjectEntryId()));
	}

	private DynamicObjectDefinitionTable _getDynamicObjectDefinitionTable(
			long objectDefinitionId)
		throws PortalException {

		// TODO Cache this across the cluster with proper invalidation when the
		// object definition or its object fields are updated

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		return new DynamicObjectDefinitionTable(
			objectDefinition,
			_objectFieldPersistence.findByODI_DTN(
				objectDefinitionId, objectDefinition.getDBTableName()),
			objectDefinition.getDBTableName());
	}

	private DynamicObjectDefinitionTable
			_getExtensionDynamicObjectDefinitionTable(long objectDefinitionId)
		throws PortalException {

		// TODO Cache this across the cluster with proper invalidation when the
		// object definition or its object fields are updated

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		return new DynamicObjectDefinitionTable(
			objectDefinition,
			_objectFieldPersistence.findByODI_DTN(
				objectDefinitionId, objectDefinition.getExtensionDBTableName()),
			objectDefinition.getExtensionDBTableName());
	}

	private GroupByStep _getManyToManyRelatedObjectEntriesGroupByStep(
			long groupId, long objectRelationshipId, long primaryKey,
			boolean reverse, FromStep fromStep)
		throws PortalException {

		ObjectRelationship objectRelationship =
			_objectRelationshipPersistence.findByPrimaryKey(
				objectRelationshipId);

		long objectDefinitionId1 = objectRelationship.getObjectDefinitionId1();
		long objectDefinitionId2 = objectRelationship.getObjectDefinitionId2();

		if (reverse) {
			objectDefinitionId1 = objectRelationship.getObjectDefinitionId2();
			objectDefinitionId2 = objectRelationship.getObjectDefinitionId1();
		}

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			_getDynamicObjectDefinitionTable(objectDefinitionId2);
		DynamicObjectDefinitionTable extensionDynamicObjectDefinitionTable =
			_getExtensionDynamicObjectDefinitionTable(objectDefinitionId2);

		ObjectDefinition objectDefinition1 =
			_objectDefinitionPersistence.fetchByPrimaryKey(objectDefinitionId1);
		ObjectDefinition objectDefinition2 =
			_objectDefinitionPersistence.fetchByPrimaryKey(objectDefinitionId2);

		DynamicObjectRelationshipMappingTable
			dynamicObjectRelationshipMappingTable =
				new DynamicObjectRelationshipMappingTable(
					objectDefinition1.getPKObjectFieldDBColumnName(),
					objectDefinition2.getPKObjectFieldDBColumnName(),
					objectRelationship.getDBTableName());

		Column<DynamicObjectRelationshipMappingTable, Long> primaryKeyColumn1 =
			dynamicObjectRelationshipMappingTable.getPrimaryKeyColumn1();
		Column<DynamicObjectRelationshipMappingTable, Long> primaryKeyColumn2 =
			dynamicObjectRelationshipMappingTable.getPrimaryKeyColumn2();

		return fromStep.from(
			dynamicObjectDefinitionTable
		).innerJoinON(
			ObjectEntryTable.INSTANCE,
			ObjectEntryTable.INSTANCE.objectEntryId.eq(
				dynamicObjectDefinitionTable.getPrimaryKeyColumn())
		).innerJoinON(
			extensionDynamicObjectDefinitionTable,
			extensionDynamicObjectDefinitionTable.getPrimaryKeyColumn(
			).eq(
				dynamicObjectDefinitionTable.getPrimaryKeyColumn()
			)
		).innerJoinON(
			dynamicObjectRelationshipMappingTable,
			primaryKeyColumn2.eq(
				dynamicObjectDefinitionTable.getPrimaryKeyColumn())
		).where(
			ObjectEntryTable.INSTANCE.groupId.eq(
				groupId
			).and(
				ObjectEntryTable.INSTANCE.companyId.eq(
					objectRelationship.getCompanyId())
			).and(
				ObjectEntryTable.INSTANCE.objectDefinitionId.eq(
					objectDefinitionId2)
			).and(
				primaryKeyColumn1.eq(primaryKey)
			).and(
				() -> {
					if (PermissionThreadLocal.getPermissionChecker() == null) {
						return null;
					}

					return _inlineSQLHelper.getPermissionWherePredicate(
						objectDefinition2.getClassName(),
						dynamicObjectDefinitionTable.getPrimaryKeyColumn());
				}
			)
		);
	}

	private GroupByStep _getOneToManyRelatedObjectEntriesGroupByStep(
			long groupId, long objectRelationshipId, long primaryKey,
			FromStep fromStep)
		throws PortalException {

		ObjectRelationship objectRelationship =
			_objectRelationshipPersistence.findByPrimaryKey(
				objectRelationshipId);

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			_getDynamicObjectDefinitionTable(
				objectRelationship.getObjectDefinitionId2());
		DynamicObjectDefinitionTable extensionDynamicObjectDefinitionTable =
			_getExtensionDynamicObjectDefinitionTable(
				objectRelationship.getObjectDefinitionId2());
		ObjectField objectField = _objectFieldPersistence.fetchByPrimaryKey(
			objectRelationship.getObjectFieldId2());

		return fromStep.from(
			dynamicObjectDefinitionTable
		).innerJoinON(
			ObjectEntryTable.INSTANCE,
			ObjectEntryTable.INSTANCE.objectEntryId.eq(
				dynamicObjectDefinitionTable.getPrimaryKeyColumn())
		).innerJoinON(
			extensionDynamicObjectDefinitionTable,
			extensionDynamicObjectDefinitionTable.getPrimaryKeyColumn(
			).eq(
				dynamicObjectDefinitionTable.getPrimaryKeyColumn()
			)
		).where(
			ObjectEntryTable.INSTANCE.groupId.eq(
				groupId
			).and(
				ObjectEntryTable.INSTANCE.companyId.eq(
					objectRelationship.getCompanyId())
			).and(
				ObjectEntryTable.INSTANCE.objectDefinitionId.eq(
					objectRelationship.getObjectDefinitionId2())
			).and(
				() -> {
					Column<DynamicObjectDefinitionTable, Long>
						primaryKeyColumn = null;

					if (Objects.equals(
							objectField.getDBTableName(),
							dynamicObjectDefinitionTable.getName())) {

						primaryKeyColumn =
							(Column<DynamicObjectDefinitionTable, Long>)
								dynamicObjectDefinitionTable.getColumn(
									objectField.getDBColumnName());
					}
					else {
						primaryKeyColumn =
							(Column<DynamicObjectDefinitionTable, Long>)
								extensionDynamicObjectDefinitionTable.getColumn(
									objectField.getDBColumnName());
					}

					return primaryKeyColumn.eq(primaryKey);
				}
			).and(
				() -> {
					if (PermissionThreadLocal.getPermissionChecker() == null) {
						return null;
					}

					ObjectDefinition objectDefinition2 =
						_objectDefinitionPersistence.findByPrimaryKey(
							objectRelationship.getObjectDefinitionId2());

					return _inlineSQLHelper.getPermissionWherePredicate(
						objectDefinition2.getClassName(),
						dynamicObjectDefinitionTable.getPrimaryKeyColumn());
				}
			)
		);
	}

	/**
	 * @see com.liferay.portal.upgrade.util.Table#getValue
	 */
	private Object _getValue(ResultSet resultSet, String name, int sqlType)
		throws SQLException {

		if (sqlType == Types.BIGINT) {
			return resultSet.getLong(name);
		}
		else if (sqlType == Types.BLOB) {
			if (PostgreSQLJDBCUtil.isPGStatement(resultSet.getStatement())) {
				return PostgreSQLJDBCUtil.getLargeObject(resultSet, name);
			}

			return resultSet.getBytes(name);
		}
		else if (sqlType == Types.BOOLEAN) {
			return resultSet.getBoolean(name);
		}
		else if (sqlType == Types.CLOB) {
			DB db = DBManagerUtil.getDB();

			if (db.getDBType() == DBType.POSTGRESQL) {
				return resultSet.getString(name);
			}

			return resultSet.getClob(name);
		}
		else if (sqlType == Types.DATE) {
			return resultSet.getTimestamp(name);
		}
		else if (sqlType == Types.DECIMAL) {
			return resultSet.getBigDecimal(name);
		}
		else if (sqlType == Types.DOUBLE) {
			return resultSet.getDouble(name);
		}
		else if (sqlType == Types.INTEGER) {
			return resultSet.getInt(name);
		}
		else if (sqlType == Types.VARCHAR) {
			return resultSet.getString(name);
		}
		else {
			throw new IllegalArgumentException(
				"Unable to get value with SQL type " + sqlType);
		}
	}

	private String _getValue(String valueString) {
		try {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray(valueString);

			return GetterUtil.getString(jsonArray.get(0));
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}
		}

		return GetterUtil.getString(valueString);
	}

	private Map<String, Serializable> _getValues(
		Object[] objects, Expression<?>[] selectExpressions) {

		Map<String, Serializable> values = new HashMap<>();

		for (int i = 0; i < selectExpressions.length; i++) {
			Column<?, ?> column = (Column<?, ?>)selectExpressions[i];

			String columnName = column.getName();

			if (columnName.endsWith(StringPool.UNDERLINE)) {
				columnName = columnName.substring(0, columnName.length() - 1);
			}

			_putValue(column, columnName, objects[i], values);
		}

		return values;
	}

	private void _insertIntoTable(
			DynamicObjectDefinitionTable dynamicObjectDefinitionTable,
			long objectEntryId, Map<String, Serializable> values)
		throws PortalException {

		StringBundler sb = new StringBundler();

		sb.append("insert into ");
		sb.append(dynamicObjectDefinitionTable.getName());
		sb.append(" (");

		Column<DynamicObjectDefinitionTable, Long> primaryKeyColumn =
			dynamicObjectDefinitionTable.getPrimaryKeyColumn();

		sb.append(primaryKeyColumn.getName());

		int count = 1;

		List<ObjectField> objectFields =
			dynamicObjectDefinitionTable.getObjectFields();

		for (ObjectField objectField : objectFields) {
			Object value = values.get(objectField.getName());

			if (value == null) {
				if (objectField.isRequired()) {
					throw new ObjectEntryValuesException.RequiredValue(
						objectField.getName());
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						"No value was provided for object field \"" +
							objectField.getName() + "\"");
				}

				continue;
			}

			if (Objects.equals(
					objectField.getRelationshipType(),
					ObjectRelationshipConstants.TYPE_ONE_TO_ONE)) {

				_validateOneToOneInsert(
					objectField.getDBColumnName(), GetterUtil.getLong(value),
					dynamicObjectDefinitionTable);
			}

			sb.append(", ");
			sb.append(objectField.getDBColumnName());

			count++;
		}

		/*if (count == 1) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No values were provided for object entry " +
						objectEntryId);
			}
		}*/

		sb.append(") values (?");

		for (int i = 1; i < count; i++) {
			sb.append(", ?");
		}

		sb.append(")");

		String sql = sb.toString();

		if (_log.isDebugEnabled()) {
			_log.debug("SQL: " + sql);
		}

		Connection connection = _currentConnection.getConnection(
			objectEntryPersistence.getDataSource());

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql)) {

			int index = 1;

			_setColumn(preparedStatement, index++, Types.BIGINT, objectEntryId);

			for (ObjectField objectField : objectFields) {
				Object value = values.get(objectField.getName());

				if (value == null) {
					continue;
				}

				Column<?, ?> column = dynamicObjectDefinitionTable.getColumn(
					objectField.getDBColumnName());

				_setColumn(
					preparedStatement, index++, column.getSQLType(), value);
			}

			preparedStatement.executeUpdate();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private void _list(
			Connection connection, DSLQuery dslQuery, List<Object[]> results,
			Expression<?>[] selectExpressions)
		throws SQLException {

		DefaultASTNodeListener defaultASTNodeListener =
			new DefaultASTNodeListener();

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				dslQuery.toSQL(defaultASTNodeListener))) {

			List<Object> scalarValues =
				defaultASTNodeListener.getScalarValues();

			for (int i = 0; i < scalarValues.size(); i++) {
				preparedStatement.setObject(i + 1, scalarValues.get(i));
			}

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Object[] result = new Object[selectExpressions.length];

					for (int i = 0; i < selectExpressions.length; i++) {
						Column<?, ?> column =
							(Column<?, ?>)selectExpressions[i];

						String columnName = column.getName();

						result[i] = _getValue(
							resultSet, columnName, column.getSQLType());
					}

					results.add(result);
				}
			}
		}
	}

	private List<Object[]> _list(
		DSLQuery dslQuery, Expression<?>[] selectExpressions) {

		List<Object[]> results = new ArrayList<>();

		Session session = objectEntryPersistence.openSession();

		try {
			session.apply(
				connection -> _list(
					connection, dslQuery, results, selectExpressions));
		}
		finally {
			objectEntryPersistence.closeSession(session);
		}

		return results;
	}

	private void _putValue(
		Column<?, ?> column, String name, Object object,
		Map<String, Serializable> values) {

		Class<?> clazz = column.getJavaType();

		if (clazz == BigDecimal.class) {
			values.put(name, (BigDecimal)object);
		}
		else if (clazz == Blob.class) {
			byte[] bytes = null;

			if (object != null) {
				if (object instanceof Blob) {

					// Hypersonic

					Blob blob = (Blob)object;

					try {
						bytes = blob.getBytes(1, (int)blob.length());
					}
					catch (SQLException sqlException) {
						throw new SystemException(sqlException);
					}
				}
				else if (object instanceof byte[]) {

					// MySQL

					bytes = (byte[])object;
				}
				else {
					Class<?> objectClass = object.getClass();

					throw new IllegalArgumentException(
						StringBundler.concat(
							"Unable to put \"", name,
							"\" with unknown object class ",
							objectClass.getName()));
				}
			}

			values.put(name, bytes);
		}
		else if (clazz == Boolean.class) {
			if (object == null) {
				object = Boolean.FALSE;
			}

			if (object instanceof Byte) {
				Byte byteObject = (Byte)object;

				if (byteObject.intValue() == 0) {
					object = Boolean.FALSE;
				}
				else {
					object = Boolean.TRUE;
				}
			}

			values.put(name, (Boolean)object);
		}
		else if (clazz == Clob.class) {
			if (object == null) {
				values.put(name, StringPool.BLANK);
			}
			else {
				DB db = DBManagerUtil.getDB();

				if (db.getDBType() == DBType.POSTGRESQL) {
					values.put(name, (String)object);
				}
				else {
					Clob clob = (Clob)object;

					try {
						InputStream inputStream = clob.getAsciiStream();

						values.put(
							name,
							GetterUtil.getString(
								IOUtils.toString(
									inputStream, StandardCharsets.UTF_8)));
					}
					catch (IOException | SQLException exception) {
						throw new SystemException(exception);
					}
				}
			}
		}
		else if (clazz == Date.class) {
			values.put(name, (Date)object);
		}
		else if (clazz == Double.class) {
			Number number = (Number)object;

			if (number == null) {
				number = Double.valueOf(0D);
			}
			else if (!(number instanceof Double)) {
				number = number.doubleValue();
			}

			values.put(name, number);
		}
		else if (clazz == Integer.class) {
			Number number = (Number)object;

			if (number == null) {
				number = Integer.valueOf(0);
			}
			else if (!(number instanceof Integer)) {
				number = number.intValue();
			}

			values.put(name, number);
		}
		else if (clazz == Long.class) {
			Number number = (Number)object;

			if (number == null) {
				number = Long.valueOf(0L);
			}
			else if (!(number instanceof Long)) {
				number = number.longValue();
			}

			values.put(name, number);
		}
		else if (clazz == String.class) {
			values.put(name, (String)object);
		}
		else {
			throw new IllegalArgumentException(
				"Unable to put value with class " + clazz.getName());
		}
	}

	private void _reindex(ObjectEntry objectEntry) throws PortalException {
		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectEntry.getObjectDefinitionId());

		Indexer<ObjectEntry> indexer = IndexerRegistryUtil.getIndexer(
			objectDefinition.getClassName());

		indexer.reindex(
			objectDefinition.getClassName(), objectEntry.getObjectEntryId());
	}

	/**
	 * @see com.liferay.portal.upgrade.util.Table#setColumn
	 */
	private void _setColumn(
			PreparedStatement preparedStatement, int index, int sqlType,
			Object value)
		throws Exception {

		if (sqlType == Types.BIGINT) {
			preparedStatement.setLong(index, GetterUtil.getLong(value));
		}
		else if (sqlType == Types.BLOB) {
			if (PostgreSQLJDBCUtil.isPGStatement(preparedStatement)) {
				PostgreSQLJDBCUtil.setLargeObject(
					preparedStatement, index, (byte[])value);
			}
			else {
				preparedStatement.setBytes(index, (byte[])value);
			}
		}
		else if (sqlType == Types.BOOLEAN) {
			preparedStatement.setBoolean(index, GetterUtil.getBoolean(value));
		}
		else if (sqlType == Types.CLOB) {
			DB db = DBManagerUtil.getDB();

			if (db.getDBType() == DBType.POSTGRESQL) {
				preparedStatement.setString(index, String.valueOf(value));
			}
			else {
				preparedStatement.setClob(
					index, new StringReader(String.valueOf(value)));
			}
		}
		else if (sqlType == Types.DATE) {
			String valueString = GetterUtil.getString(value);

			if (value instanceof Date) {
				Date date = (Date)value;

				preparedStatement.setTimestamp(
					index, new Timestamp(date.getTime()));
			}
			else if (valueString.isEmpty()) {
				preparedStatement.setTimestamp(index, null);
			}
			else {
				Date date = DateUtil.parseDate(
					"yyyy-MM-dd", valueString, LocaleUtil.getSiteDefault());

				preparedStatement.setTimestamp(
					index, new Timestamp(date.getTime()));
			}
		}
		else if (sqlType == Types.DECIMAL) {
			preparedStatement.setBigDecimal(
				index, (BigDecimal)GetterUtil.get(value, BigDecimal.ZERO));
		}
		else if (sqlType == Types.DOUBLE) {
			preparedStatement.setDouble(index, GetterUtil.getDouble(value));
		}
		else if (sqlType == Types.INTEGER) {
			preparedStatement.setInt(index, GetterUtil.getInteger(value));
		}
		else if (sqlType == Types.VARCHAR) {
			preparedStatement.setString(index, String.valueOf(value));
		}
		else {
			throw new IllegalArgumentException(
				"Unable to set column with SQL type " + sqlType);
		}
	}

	private void _startWorkflowInstance(
			long userId, ObjectEntry objectEntry, ServiceContext serviceContext)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectEntry.getObjectDefinitionId());

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			objectEntry.getCompanyId(), objectEntry.getNonzeroGroupId(), userId,
			objectDefinition.getClassName(), objectEntry.getObjectEntryId(),
			objectEntry, serviceContext);
	}

	private void _updateTable(
			DynamicObjectDefinitionTable dynamicObjectDefinitionTable,
			long objectEntryId, Map<String, Serializable> values)
		throws PortalException {

		StringBundler sb = new StringBundler();

		sb.append("update ");
		sb.append(dynamicObjectDefinitionTable.getName());
		sb.append(" set ");

		int count = 0;

		List<ObjectField> objectFields =
			dynamicObjectDefinitionTable.getObjectFields();

		for (ObjectField objectField : objectFields) {
			Object value = values.get(objectField.getName());

			if (value == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"No value was provided for object field \"" +
							objectField.getName() + "\"");
				}

				continue;
			}

			if (Objects.equals(
					objectField.getRelationshipType(),
					ObjectRelationshipConstants.TYPE_ONE_TO_ONE)) {

				_validateOneToOneUpdate(
					objectField.getDBColumnName(), GetterUtil.getLong(value),
					dynamicObjectDefinitionTable, objectEntryId);
			}

			if (count > 0) {
				sb.append(", ");
			}

			sb.append(objectField.getDBColumnName());
			sb.append(" = ?");

			count++;
		}

		if (count == 0) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No values were provided for object entry " +
						objectEntryId);
			}

			return;
		}

		sb.append(" where ");

		Column<DynamicObjectDefinitionTable, Long> primaryKeyColumn =
			dynamicObjectDefinitionTable.getPrimaryKeyColumn();

		sb.append(primaryKeyColumn.getName());

		sb.append(" = ?");

		String sql = sb.toString();

		if (_log.isDebugEnabled()) {
			_log.debug("SQL: " + sql);
		}

		Connection connection = _currentConnection.getConnection(
			objectEntryPersistence.getDataSource());

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql)) {

			int index = 1;

			for (ObjectField objectField : objectFields) {
				Object value = values.get(objectField.getName());

				if (value == null) {
					continue;
				}

				Column<?, ?> column = dynamicObjectDefinitionTable.getColumn(
					objectField.getDBColumnName());

				_setColumn(
					preparedStatement, index++, column.getSQLType(), value);
			}

			_setColumn(preparedStatement, index++, Types.BIGINT, objectEntryId);

			preparedStatement.executeUpdate();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private void _validateGroupId(long groupId, String scope)
		throws PortalException {

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(scope);

		if (!objectScopeProvider.isValidGroupId(groupId)) {
			throw new ObjectDefinitionScopeException(
				StringBundler.concat(
					"Group ID ", groupId, " is not valid for scope \"", scope,
					"\""));
		}
	}

	private void _validateListTypeEntriesValues(
			Map<String, Serializable> values,
			Map.Entry<String, Serializable> entry, ObjectField objectField)
		throws ObjectEntryValuesException {

		List<ListTypeEntry> listTypeEntries =
			_listTypeEntryLocalService.getListTypeEntries(
				objectField.getListTypeDefinitionId());

		Stream<ListTypeEntry> stream = listTypeEntries.stream();

		String value = _getValue(String.valueOf(values.get(entry.getKey())));

		if ((!value.isEmpty() || objectField.isRequired()) &&
			!stream.anyMatch(
				listTypeEntry -> Objects.equals(
					listTypeEntry.getKey(), value))) {

			throw new ObjectEntryValuesException.ObjectFieldNotMapped(
				entry.getKey());
		}
	}

	private void _validateObjectFieldIntegerTypeSize(
			Map.Entry<String, Serializable> entry)
		throws ObjectEntryValuesException {

		Serializable entryValue = entry.getValue();

		String entryValueString = entryValue.toString();

		if (!entryValueString.isEmpty()) {
			int value = GetterUtil.getInteger(entryValue);

			if (!StringUtil.equals(String.valueOf(value), entryValueString)) {
				throw new ObjectEntryValuesException.IntegerSizeExceeded();
			}
		}
	}

	private void _validateObjectFieldLongType(
			Map.Entry<String, Serializable> entry)
		throws ObjectEntryValuesException {

		Serializable entryValue = entry.getValue();

		String entryValueString = entryValue.toString();

		if (!entryValueString.isEmpty()) {
			long value = GetterUtil.getLong(entryValue);

			if (!StringUtil.equals(
					String.valueOf(value), entryValue.toString())) {

				throw new ObjectEntryValuesException.LongSizeExceeded();
			}
			else if (value > _MAX_SAFE_LONG) {
				throw new ObjectEntryValuesException.LongMaxSizeExceeded();
			}
			else if (value < _MIN_SAFE_LONG) {
				throw new ObjectEntryValuesException.LongMinSizeExceeded();
			}
		}
	}

	private void _validateObjectFieldStringTypeLength(
			Map.Entry<String, Serializable> entry)
		throws ObjectEntryValuesException {

		String value = (String)entry.getValue();

		if ((value != null) && (value.length() > 280)) {
			throw new ObjectEntryValuesException.Exceeds280Characters();
		}
	}

	private void _validateOneToOneInsert(
			String dbColumnName, long dbColumnValue,
			DynamicObjectDefinitionTable dynamicObjectDefinitionTable)
		throws PortalException {

		if (dbColumnValue == 0) {
			return;
		}

		int count = 0;

		Connection connection = _currentConnection.getConnection(
			objectEntryPersistence.getDataSource());

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select count(*) from ",
					dynamicObjectDefinitionTable.getTableName(), " where ",
					dbColumnName, " = ", dbColumnValue));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			resultSet.next();

			count = resultSet.getInt(1);
		}
		catch (SQLException sqlException) {
			throw new SystemException(sqlException);
		}

		if (count > 0) {
			throw new ObjectEntryValuesException.OneToOneConstraintViolation(
				dynamicObjectDefinitionTable.getTableName(), dbColumnName,
				dbColumnValue);
		}
	}

	private void _validateOneToOneUpdate(
			String dbColumnName, long dbColumnValue,
			DynamicObjectDefinitionTable dynamicObjectDefinitionTable,
			long objectEntryId)
		throws PortalException {

		if (dbColumnValue == 0) {
			return;
		}

		int count = 0;

		Connection connection = _currentConnection.getConnection(
			objectEntryPersistence.getDataSource());

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select count(*) from ",
					dynamicObjectDefinitionTable.getTableName(), " where ",
					dynamicObjectDefinitionTable.getPrimaryKeyColumnName(),
					" != ", objectEntryId, " and ", dbColumnName, " = ",
					dbColumnValue));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			resultSet.next();

			count = resultSet.getInt(1);
		}
		catch (SQLException sqlException) {
			throw new SystemException(sqlException);
		}

		if (count > 0) {
			throw new ObjectEntryValuesException.OneToOneConstraintViolation(
				dynamicObjectDefinitionTable.getTableName(), dbColumnName,
				dbColumnValue);
		}
	}

	private void _validateValues(
			long objectDefinitionId, Map<String, Serializable> values)
		throws PortalException {

		for (Map.Entry<String, Serializable> entry : values.entrySet()) {
			ObjectField objectField = null;

			try {
				objectField = _objectFieldLocalService.getObjectField(
					objectDefinitionId, entry.getKey());
			}
			catch (NoSuchObjectFieldException noSuchObjectFieldException) {
				if (_log.isDebugEnabled()) {
					_log.debug(noSuchObjectFieldException);
				}

				continue;
			}

			String objectFieldDBType = objectField.getDBType();

			if (StringUtil.equals(objectFieldDBType, "Integer")) {
				_validateObjectFieldIntegerTypeSize(entry);
			}

			if (StringUtil.equals(objectFieldDBType, "Long")) {
				_validateObjectFieldLongType(entry);
			}

			if (StringUtil.equals(objectFieldDBType, "String")) {
				_validateObjectFieldStringTypeLength(entry);
			}

			if (objectField.getListTypeDefinitionId() != 0) {
				_validateListTypeEntriesValues(values, entry, objectField);
			}
		}
	}

	private static final long _MAX_SAFE_LONG = 9007199254740991L;

	private static final long _MIN_SAFE_LONG = -9007199254740991L;

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryLocalServiceImpl.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetLinkLocalService _assetLinkLocalService;

	@Reference
	private CurrentConnection _currentConnection;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InlineSQLHelper _inlineSQLHelper;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectFieldPersistence _objectFieldPersistence;

	@Reference
	private ObjectRelatedModelsProviderRegistry
		_objectRelatedModelsProviderRegistry;

	@Reference
	private ObjectRelationshipPersistence _objectRelationshipPersistence;

	@Reference
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private SortFieldBuilder _sortFieldBuilder;

	@Reference
	private Sorts _sorts;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}