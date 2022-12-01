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

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntryOrganizationRelTable;
import com.liferay.account.model.AccountEntryTable;
import com.liferay.account.model.AccountEntryUserRelTable;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLinkConstants;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.configuration.ObjectConfiguration;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectFieldValidationConstants;
import com.liferay.object.constants.ObjectFilterConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.NoSuchObjectFieldException;
import com.liferay.object.exception.ObjectDefinitionScopeException;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.field.util.ObjectFieldFormulaEvaluatorUtil;
import com.liferay.object.internal.action.util.ObjectActionThreadLocal;
import com.liferay.object.internal.filter.parser.ObjectFilterParser;
import com.liferay.object.internal.filter.parser.ObjectFilterParserServiceRegistry;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectDefinitionTable;
import com.liferay.object.internal.petra.sql.dsl.DynamicObjectRelationshipMappingTable;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryTable;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectFilter;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.model.ObjectState;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.related.models.ObjectRelatedModelsProviderRegistry;
import com.liferay.object.relationship.util.ObjectRelationshipUtil;
import com.liferay.object.rest.petra.sql.dsl.expression.FilterPredicateFactory;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.object.service.ObjectStateLocalService;
import com.liferay.object.service.base.ObjectEntryLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.service.persistence.ObjectFieldPersistence;
import com.liferay.object.service.persistence.ObjectFieldSettingPersistence;
import com.liferay.object.service.persistence.ObjectRelationshipPersistence;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadataRegistry;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.expression.ScalarDSLQueryAlias;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.petra.sql.dsl.query.sort.OrderByExpression;
import com.liferay.petra.sql.dsl.spi.ast.DefaultASTNodeListener;
import com.liferay.petra.sql.dsl.spi.expression.Scalar;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.dao.jdbc.postgresql.PostgreSQLJDBCUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.CurrentConnection;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.OrganizationTable;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.Users_OrgsTable;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelper;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
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

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(
	configurationPid = "com.liferay.object.configuration.ObjectConfiguration",
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

		User user = _userLocalService.getUser(userId);

		_validateValues(
			user.isDefaultUser(), objectDefinitionId, null,
			objectDefinition.getPortletId(), serviceContext, userId, values);

		_fillBusinessTypePicklistDefaultValue(
			_objectFieldLocalService.getObjectFields(objectDefinitionId),
			values);

		long objectEntryId = counterLocalService.increment();

		_insertIntoTable(
			_getDynamicObjectDefinitionTable(objectDefinitionId), objectEntryId,
			values);
		_insertIntoTable(
			_getExtensionDynamicObjectDefinitionTable(objectDefinitionId),
			objectEntryId, values);

		ObjectEntry objectEntry = objectEntryPersistence.create(objectEntryId);

		_setExternalReferenceCode(objectEntry, values);

		objectEntry.setGroupId(groupId);
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

		ObjectActionThreadLocal.clearObjectActionIds();

		return objectEntry;
	}

	@Override
	public ObjectEntry addObjectEntry(
			String externalReferenceCode, long userId,
			ObjectDefinition objectDefinition)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryPersistence.create(
			counterLocalService.increment());

		objectEntry.setExternalReferenceCode(externalReferenceCode);

		User user = _userLocalService.getUser(userId);

		objectEntry.setCompanyId(user.getCompanyId());
		objectEntry.setUserId(user.getUserId());
		objectEntry.setUserName(user.getFullName());

		objectEntry.setObjectDefinitionId(
			objectDefinition.getObjectDefinitionId());
		objectEntry.setStatus(WorkflowConstants.STATUS_DRAFT);
		objectEntry.setStatusDate(new Date());

		return objectEntryPersistence.updateImpl(objectEntry);
	}

	@Override
	public void addOrUpdateExtensionDynamicObjectDefinitionTableValues(
			long userId, ObjectDefinition objectDefinition, long primaryKey,
			Map<String, Serializable> values, ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		_validateValues(
			user.isDefaultUser(), objectDefinition.getObjectDefinitionId(),
			null, objectDefinition.getClassName(), serviceContext, userId,
			values);

		insertIntoOrUpdateExtensionTable(
			objectDefinition.getObjectDefinitionId(), primaryKey, values);

		_clearExtensionDynamicObjectDefinitionTableCache(
			objectDefinition.getObjectDefinitionId(), primaryKey);
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

		ObjectEntry objectEntry = null;

		if (Validator.isNotNull(externalReferenceCode)) {
			objectEntry = objectEntryPersistence.fetchByERC_C_ODI(
				externalReferenceCode, user.getCompanyId(), objectDefinitionId);

			if (objectEntry != null) {
				return updateObjectEntry(
					userId, objectEntry.getObjectEntryId(), values,
					serviceContext);
			}
		}

		objectEntry = addObjectEntry(
			userId, groupId, objectDefinitionId, values, serviceContext);

		if (Validator.isNotNull(externalReferenceCode)) {
			objectEntry.setExternalReferenceCode(externalReferenceCode);

			objectEntry = objectEntryPersistence.update(objectEntry);
		}

		_reindex(objectEntry);

		return objectEntry;
	}

	@Override
	public void deleteExtensionDynamicObjectDefinitionTableValues(
			ObjectDefinition objectDefinition, long primaryKey)
		throws PortalException {

		Map<String, Serializable> extensionDynamicObjectDefinitionTableValues =
			getExtensionDynamicObjectDefinitionTableValues(
				objectDefinition, primaryKey);

		_deleteFromTable(
			objectDefinition.getExtensionDBTableName(),
			objectDefinition.getPKObjectFieldDBColumnName(), primaryKey);

		deleteRelatedObjectEntries(
			0, objectDefinition.getObjectDefinitionId(), primaryKey);

		_deleteFileEntries(
			Collections.emptyMap(), objectDefinition.getObjectDefinitionId(),
			extensionDynamicObjectDefinitionTableValues);

		_clearExtensionDynamicObjectDefinitionTableCache(
			objectDefinition.getObjectDefinitionId(), primaryKey);
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

		Map<String, Serializable> values = objectEntry.getValues();

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
			objectDefinition.getDBTableName(),
			objectDefinition.getPKObjectFieldDBColumnName(),
			objectEntry.getObjectEntryId());
		_deleteFromTable(
			objectDefinition.getExtensionDBTableName(),
			objectDefinition.getPKObjectFieldDBColumnName(),
			objectEntry.getObjectEntryId());

		deleteRelatedObjectEntries(
			objectEntry.getGroupId(), objectDefinition.getObjectDefinitionId(),
			objectEntry.getPrimaryKey());

		_deleteFileEntries(
			Collections.emptyMap(), objectDefinition.getObjectDefinitionId(),
			values);

		Indexer<ObjectEntry> indexer = IndexerRegistryUtil.getIndexer(
			objectDefinition.getClassName());

		indexer.delete(objectEntry);

		return objectEntry;
	}

	@Override
	public ObjectEntry deleteObjectEntry(
			String externalReferenceCode, long companyId, long groupId)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryPersistence.findByERC_G_C(
			externalReferenceCode, groupId, companyId);

		return objectEntryLocalService.deleteObjectEntry(objectEntry);
	}

	@Override
	public void deleteRelatedObjectEntries(
			long groupId, long objectDefinitionId, long primaryKey)
		throws PortalException {

		List<ObjectRelationship> objectRelationships =
			_objectRelationshipPersistence.findByObjectDefinitionId1(
				objectDefinitionId);

		for (ObjectRelationship objectRelationship : objectRelationships) {
			ObjectDefinition objectDefinition2 =
				_objectDefinitionPersistence.findByPrimaryKey(
					objectRelationship.getObjectDefinitionId2());

			ObjectRelatedModelsProvider objectRelatedModelsProvider =
				_objectRelatedModelsProviderRegistry.
					getObjectRelatedModelsProvider(
						objectDefinition2.getClassName(),
						objectRelationship.getType());

			objectRelatedModelsProvider.deleteRelatedModel(
				PrincipalThreadLocal.getUserId(), groupId,
				objectRelationship.getObjectRelationshipId(), primaryKey);
		}
	}

	@Override
	public ObjectEntry fetchObjectEntry(
		String externalReferenceCode, long objectDefinitionId) {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.fetchByPrimaryKey(objectDefinitionId);

		if (objectDefinition == null) {
			return null;
		}

		return objectEntryPersistence.fetchByERC_C_ODI(
			externalReferenceCode, objectDefinition.getCompanyId(),
			objectDefinitionId);
	}

	@Override
	public Map<Object, Long> getAggregationCounts(
			long objectDefinitionId, String aggregationTerm,
			Predicate predicate, int start, int end)
		throws PortalException {

		Map<Object, Long> aggregationCounts = new HashMap<>();

		Table table = _objectFieldLocalService.getTable(
			objectDefinitionId, aggregationTerm);

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectDefinitionId, aggregationTerm);

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			_getDynamicObjectDefinitionTable(objectDefinitionId);
		DynamicObjectDefinitionTable extensionDynamicObjectDefinitionTable =
			_getExtensionDynamicObjectDefinitionTable(objectDefinitionId);

		DSLQuery dslQuery = DSLQueryFactoryUtil.select(
			table.getColumn(objectField.getDBColumnName()),
			DSLFunctionFactoryUtil.countDistinct(
				dynamicObjectDefinitionTable.getPrimaryKeyColumn()
			).as(
				"aggregationCount"
			)
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
				predicate
			).and(
				() -> {
					if (PermissionThreadLocal.getPermissionChecker() == null) {
						return null;
					}

					return _inlineSQLHelper.getPermissionWherePredicate(
						dynamicObjectDefinitionTable.getName(),
						dynamicObjectDefinitionTable.getPrimaryKeyColumn());
				}
			)
		).groupBy(
			table.getColumn(objectField.getDBColumnName())
		).limit(
			start, end
		);

		for (Object[] values : (List<Object[]>)dslQuery(dslQuery)) {
			aggregationCounts.put(
				GetterUtil.getObject(values[0]), GetterUtil.getLong(values[1]));
		}

		return aggregationCounts;
	}

	@Override
	public Map<String, Serializable>
			getExtensionDynamicObjectDefinitionTableValues(
				ObjectDefinition objectDefinition, long primaryKey)
		throws PortalException {

		DynamicObjectDefinitionTable extensionDynamicObjectDefinitionTable =
			_getExtensionDynamicObjectDefinitionTable(
				objectDefinition.getObjectDefinitionId());

		Expression<?>[] selectExpressions = _getSelectExpressions(
			extensionDynamicObjectDefinitionTable);

		List<Object[]> rows = _list(
			_getExtensionDynamicObjectDefinitionTableSelectDSLQuery(
				extensionDynamicObjectDefinitionTable, primaryKey,
				selectExpressions),
			selectExpressions);

		if (rows.isEmpty()) {
			return new HashMap<>();
		}

		Map<String, Serializable> values = _getValues(
			rows.get(0), selectExpressions);

		values.remove(objectDefinition.getPKObjectFieldName());

		_addObjectRelationshipERCFieldValue(
			objectDefinition.getObjectDefinitionId(), values);

		return values;
	}

	@Override
	public List<ObjectEntry> getManyToManyObjectEntries(
			long groupId, long objectRelationshipId, long primaryKey,
			boolean related, boolean reverse, int start, int end)
		throws PortalException {

		DSLQuery dslQuery = _getManyToManyObjectEntriesGroupByStep(
			groupId, objectRelationshipId, primaryKey, related, reverse,
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
	public int getManyToManyObjectEntriesCount(
			long groupId, long objectRelationshipId, long primaryKey,
			boolean related, boolean reverse)
		throws PortalException {

		DSLQuery dslQuery = _getManyToManyObjectEntriesGroupByStep(
			groupId, objectRelationshipId, primaryKey, related, reverse,
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
			String externalReferenceCode, long objectDefinitionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		return objectEntryPersistence.findByERC_C_ODI(
			externalReferenceCode, objectDefinition.getCompanyId(),
			objectDefinitionId);
	}

	@Override
	public ObjectEntry getObjectEntry(
			String externalReferenceCode, long companyId, long groupId)
		throws PortalException {

		return objectEntryPersistence.findByERC_G_C(
			externalReferenceCode, groupId, companyId);
	}

	public List<ObjectEntry> getOneToManyObjectEntries(
			long groupId, long objectRelationshipId, long primaryKey,
			boolean related, int start, int end)
		throws PortalException {

		DSLQuery dslQuery = _getOneToManyObjectEntriesGroupByStep(
			groupId, objectRelationshipId, primaryKey, related,
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
	public int getOneToManyObjectEntriesCount(
			long groupId, long objectRelationshipId, long primaryKey,
			boolean related)
		throws PortalException {

		DSLQuery dslQuery = _getOneToManyObjectEntriesGroupByStep(
			groupId, objectRelationshipId, primaryKey, related,
			DSLQueryFactoryUtil.countDistinct(
				ObjectEntryTable.INSTANCE.objectEntryId));

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Get one to many related object entries count: " + dslQuery);
		}

		return objectEntryPersistence.dslQueryCount(dslQuery);
	}

	public Map<String, Object> getSystemModelAttributes(
			ObjectDefinition objectDefinition, long primaryKey)
		throws PortalException {

		if (!objectDefinition.isSystem()) {
			return new HashMap<>();
		}

		Map<String, Object> baseModelAttributes = new HashMap<>();

		PersistedModelLocalService persistedModelLocalService =
			_persistedModelLocalServiceRegistry.getPersistedModelLocalService(
				objectDefinition.getClassName());

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			_getDynamicObjectDefinitionTable(
				objectDefinition.getObjectDefinitionId());

		Column<DynamicObjectDefinitionTable, Long> primaryKeyColumn =
			dynamicObjectDefinitionTable.getPrimaryKeyColumn();

		List<BaseModel<?>> baseModels = persistedModelLocalService.dslQuery(
			DSLQueryFactoryUtil.select(
			).from(
				dynamicObjectDefinitionTable
			).where(
				primaryKeyColumn.eq(primaryKey)
			));

		if (!baseModels.isEmpty()) {
			BaseModel<?> baseModel = baseModels.get(0);

			baseModelAttributes = baseModel.getModelAttributes();
		}

		Map<String, Object> modelAttributes =
			HashMapBuilder.<String, Object>put(
				"createDate",
				GetterUtil.get(
					baseModelAttributes.get("createDate"), primaryKey)
			).put(
				"externalReferenceCode",
				GetterUtil.get(
					baseModelAttributes.get("externalReferenceCode"),
					primaryKey)
			).put(
				"modifiedDate",
				GetterUtil.get(
					baseModelAttributes.get("modifiedDate"), primaryKey)
			).put(
				"objectDefinitionId", objectDefinition.getObjectDefinitionId()
			).put(
				"uuid",
				GetterUtil.get(baseModelAttributes.get("uuid"), primaryKey)
			).build();

		for (ObjectField objectField :
				_objectFieldLocalService.getObjectFields(
					objectDefinition.getObjectDefinitionId())) {

			if (!objectField.isSystem()) {
				continue;
			}

			Object value = GetterUtil.getObject(
				baseModelAttributes.get(objectField.getDBColumnName()),
				primaryKey);

			if (value instanceof String) {
				value = _localization.getLocalization(
					(String)value, null, true);
			}

			modelAttributes.put(objectField.getName(), value);
		}

		modelAttributes.putAll(
			objectEntryLocalService.
				getExtensionDynamicObjectDefinitionTableValues(
					objectDefinition, primaryKey));

		return modelAttributes;
	}

	@Override
	public String getTitleValue(long objectDefinitionId, long primaryKey)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		if (!objectDefinition.isSystem()) {
			ObjectEntry objectEntry = getObjectEntry(primaryKey);

			return objectEntry.getTitleValue();
		}

		ObjectField titleObjectField =
			_objectFieldLocalService.fetchObjectField(
				objectDefinition.getTitleObjectFieldId());

		if (Objects.isNull(titleObjectField)) {
			titleObjectField = _objectFieldLocalService.getObjectField(
				objectDefinitionId, "id");
		}

		PersistedModelLocalService persistedModelLocalService =
			_persistedModelLocalServiceRegistry.getPersistedModelLocalService(
				objectDefinition.getClassName());

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			String.valueOf(
				persistedModelLocalService.getPersistedModel(primaryKey)));

		return jsonObject.getString(titleObjectField.getDBColumnName());
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
			_getSelectExpressions(dynamicObjectDefinitionTable),
			ArrayUtil.remove(
				_getSelectExpressions(extensionDynamicObjectDefinitionTable),
				extensionDynamicObjectDefinitionTable.getPrimaryKeyColumn()));

		List<Object[]> rows = _list(
			DSLQueryFactoryUtil.select(
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

		Map<String, Serializable> values = _getValues(
			rows.get(0), selectExpressions);

		_addObjectRelationshipERCFieldValue(
			objectEntry.getObjectDefinitionId(), values);

		return _putFormulaObjectFieldValues(
			objectEntry.getObjectDefinitionId(), values);
	}

	@Override
	public List<Map<String, Serializable>> getValuesList(
			long groupId, long companyId, long userId, long objectDefinitionId,
			Predicate predicate, String search, int start, int end,
			OrderByExpression[] orderByExpressions)
		throws PortalException {

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			_getDynamicObjectDefinitionTable(objectDefinitionId);
		DynamicObjectDefinitionTable extensionDynamicObjectDefinitionTable =
			_getExtensionDynamicObjectDefinitionTable(objectDefinitionId);

		Expression<?>[] selectExpressions = ArrayUtil.append(
			_getSelectExpressions(dynamicObjectDefinitionTable),
			ArrayUtil.remove(
				_getSelectExpressions(extensionDynamicObjectDefinitionTable),
				extensionDynamicObjectDefinitionTable.getPrimaryKeyColumn()),
			_EXPRESSIONS);

		List<Object[]> rows = _list(
			DSLQueryFactoryUtil.select(
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
						if (groupId == 0) {
							return null;
						}

						return ObjectEntryTable.INSTANCE.groupId.eq(groupId);
					}
				).and(
					_fillAccountEntriesPredicate(
						companyId, userId, objectDefinitionId)
				).and(
					_fillPredicate(objectDefinitionId, predicate, search)
				).and(
					_getPermissionWherePredicate(
						dynamicObjectDefinitionTable, groupId)
				)
			).orderBy(
				orderByExpressions
			).limit(
				start, end
			),
			selectExpressions);

		List<Map<String, Serializable>> valuesList = new ArrayList<>(
			rows.size());

		for (Object[] objects : rows) {
			valuesList.add(_getValues(objects, selectExpressions));
		}

		return valuesList;
	}

	@Override
	public int getValuesListCount(
			long groupId, long companyId, long userId, long objectDefinitionId,
			Predicate predicate, String search)
		throws PortalException {

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			_getDynamicObjectDefinitionTable(objectDefinitionId);
		DynamicObjectDefinitionTable extensionDynamicObjectDefinitionTable =
			_getExtensionDynamicObjectDefinitionTable(objectDefinitionId);

		DSLQuery dslQuery = DSLQueryFactoryUtil.countDistinct(
			ObjectEntryTable.INSTANCE.objectEntryId
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
					if (groupId == 0) {
						return null;
					}

					return ObjectEntryTable.INSTANCE.groupId.eq(groupId);
				}
			).and(
				_fillAccountEntriesPredicate(
					companyId, userId, objectDefinitionId)
			).and(
				_fillPredicate(objectDefinitionId, predicate, search)
			).and(
				_getPermissionWherePredicate(
					dynamicObjectDefinitionTable, groupId)
			)
		);

		return objectEntryPersistence.dslQueryCount(dslQuery);
	}

	@Override
	public void insertIntoOrUpdateExtensionTable(
			long objectDefinitionId, long primaryKey,
			Map<String, Serializable> values)
		throws PortalException {

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			_getExtensionDynamicObjectDefinitionTable(objectDefinitionId);

		int count = objectEntryPersistence.dslQueryCount(
			_getExtensionDynamicObjectDefinitionTableCountDSLQuery(
				dynamicObjectDefinitionTable, primaryKey));

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

		User user = _userLocalService.getUser(userId);

		ObjectEntry objectEntry = objectEntryPersistence.findByPrimaryKey(
			objectEntryId);

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectEntry.getObjectDefinitionId());

		_validateValues(
			user.isDefaultUser(), objectEntry.getObjectDefinitionId(),
			objectEntry, objectDefinition.getPortletId(), serviceContext,
			userId, values);

		Map<String, Serializable> transientValues = objectEntry.getValues();

		_updateTable(
			_getDynamicObjectDefinitionTable(
				objectEntry.getObjectDefinitionId()),
			objectEntryId, values);
		_updateTable(
			_getExtensionDynamicObjectDefinitionTable(
				objectEntry.getObjectDefinitionId()),
			objectEntryId, values);

		objectEntryPersistence.clearCache(SetUtil.fromArray(objectEntryId));

		objectEntry = objectEntryPersistence.findByPrimaryKey(objectEntryId);

		_setExternalReferenceCode(objectEntry, values);

		objectEntry.setModifiedDate(serviceContext.getModifiedDate(null));

		objectEntry.setTransientValues(transientValues);

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

		if (_skipModelListeners.get()) {
			while (objectEntry instanceof ModelWrapper) {
				ModelWrapper<ObjectEntry> modelWrapper =
					(ModelWrapper<ObjectEntry>)objectEntry;

				objectEntry = modelWrapper.getWrappedModel();
			}

			objectEntry = objectEntryPersistence.updateImpl(objectEntry);
		}
		else {
			objectEntry = objectEntryPersistence.update(objectEntry);
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.fetchByPrimaryKey(
				objectEntry.getObjectDefinitionId());

		_assetEntryLocalService.updateEntry(
			objectDefinition.getClassName(), objectEntry.getObjectEntryId(),
			null, null, true, objectEntry.isApproved());

		_reindex(objectEntry);

		return objectEntry;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_objectConfiguration = ConfigurableUtil.createConfigurable(
			ObjectConfiguration.class, properties);
	}

	private void _addFileEntry(
			DLFileEntry dlFileEntry, Map.Entry<String, Serializable> entry,
			List<ObjectFieldSetting> objectFieldSettings, String portletId,
			ServiceContext serviceContext, long userId)
		throws PortalException {

		try {
			String fileSource = null;
			boolean showFilesInDocumentsAndMedia = false;
			String storageDLFolderPath = null;

			for (ObjectFieldSetting objectFieldSetting : objectFieldSettings) {
				if (Objects.equals(
						objectFieldSetting.getName(), "fileSource")) {

					fileSource = objectFieldSetting.getValue();
				}
				else if (Objects.equals(
							objectFieldSetting.getName(),
							"showFilesInDocumentsAndMedia")) {

					showFilesInDocumentsAndMedia = GetterUtil.getBoolean(
						objectFieldSetting.getValue());
				}
				else if (Objects.equals(
							objectFieldSetting.getName(),
							"storageDLFolderPath")) {

					storageDLFolderPath = objectFieldSetting.getValue();
				}
			}

			if (Objects.equals("documentsAndMedia", fileSource)) {
				return;
			}

			DLFolder dlFileEntryFolder = dlFileEntry.getFolder();

			DLFolder dlFolder = _getDLFolder(
				dlFileEntry.getCompanyId(), dlFileEntry.getGroupId(), portletId,
				serviceContext, showFilesInDocumentsAndMedia,
				storageDLFolderPath, userId);

			if (Objects.equals(
					dlFileEntryFolder.getFolderId(), dlFolder.getFolderId())) {

				return;
			}

			String originalFileName = TempFileEntryUtil.getOriginalTempFileName(
				dlFileEntry.getFileName());

			FileEntry fileEntry = _dlAppLocalService.addFileEntry(
				null, userId, dlFolder.getRepositoryId(),
				dlFolder.getFolderId(),
				DLUtil.getUniqueFileName(
					dlFileEntry.getGroupId(), dlFolder.getFolderId(),
					originalFileName, true),
				dlFileEntry.getMimeType(),
				DLUtil.getUniqueTitle(
					dlFileEntry.getGroupId(), dlFolder.getFolderId(),
					FileUtil.stripExtension(originalFileName)),
				StringPool.BLANK, null, null, dlFileEntry.getContentStream(),
				dlFileEntry.getSize(), null, null, serviceContext);

			entry.setValue(fileEntry.getFileEntryId());
		}
		finally {
			if (dlFileEntry != null) {
				TempFileEntryUtil.deleteTempFileEntry(
					dlFileEntry.getFileEntryId());
			}
		}
	}

	private void _addObjectRelationshipERCFieldValue(
		long objectDefinitionId, Map<String, Serializable> values) {

		for (ObjectField objectField :
				_objectFieldLocalService.getObjectFields(
					objectDefinitionId, false)) {

			if (!Objects.equals(
					objectField.getRelationshipType(),
					ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

				continue;
			}

			long primaryKey = GetterUtil.getLong(
				values.get(objectField.getName()));

			if (primaryKey == 0) {
				continue;
			}

			ObjectRelationship objectRelationship =
				_objectRelationshipPersistence.fetchByObjectFieldId2(
					objectField.getObjectFieldId());

			ObjectDefinition objectDefinition =
				_objectDefinitionPersistence.fetchByPrimaryKey(
					objectRelationship.getObjectDefinitionId1());

			String objectRelationshipERCFieldName =
				ObjectFieldSettingUtil.getValue(
					ObjectFieldSettingConstants.
						NAME_OBJECT_RELATIONSHIP_ERC_FIELD_NAME,
					objectField);

			if (objectDefinition.isSystem()) {
				SystemObjectDefinitionMetadata systemObjectDefinitionMetadata =
					_systemObjectDefinitionMetadataRegistry.
						getSystemObjectDefinitionMetadata(
							objectDefinition.getName());

				try {
					values.put(
						objectRelationshipERCFieldName,
						systemObjectDefinitionMetadata.getExternalReferenceCode(
							primaryKey));
				}
				catch (PortalException portalException) {
					if (_log.isDebugEnabled()) {
						_log.debug(portalException);
					}
				}

				continue;
			}

			ObjectEntry objectEntry = objectEntryPersistence.fetchByPrimaryKey(
				primaryKey);

			if (objectEntry == null) {
				continue;
			}

			values.put(
				objectRelationshipERCFieldName,
				objectEntry.getExternalReferenceCode());
		}
	}

	private void _clearExtensionDynamicObjectDefinitionTableCache(
			long objectDefinitionId, long primaryKey)
		throws PortalException {

		DynamicObjectDefinitionTable extensionDynamicObjectDefinitionTable =
			_getExtensionDynamicObjectDefinitionTable(objectDefinitionId);

		String tableName = extensionDynamicObjectDefinitionTable.getTableName();

		_clearFinderCache(
			_getExtensionDynamicObjectDefinitionTableCountDSLQuery(
				extensionDynamicObjectDefinitionTable, primaryKey),
			primaryKey, tableName);
		_clearFinderCache(
			_getExtensionDynamicObjectDefinitionTableSelectDSLQuery(
				extensionDynamicObjectDefinitionTable, primaryKey,
				_getSelectExpressions(extensionDynamicObjectDefinitionTable)),
			primaryKey, tableName);
	}

	private void _clearFinderCache(
		DSLQuery dslQuery, long primaryKey, String tableName) {

		StringBundler sb = new StringBundler();

		dslQuery.toSQL(sb::append, new DefaultASTNodeListener());

		FinderCacheUtil.removeResult(
			new FinderPath(
				FinderPath.encodeDSLQueryCacheName(new String[] {tableName}),
				"dslQuery", sb.getStrings(), new String[0], false),
			new Long[] {primaryKey});
	}

	private void _deleteFileEntries(
		Map<String, Serializable> newValues, long objectDefinitionId,
		Map<String, Serializable> oldValues) {

		List<ObjectField> objectFields =
			_objectFieldPersistence.findByObjectDefinitionId(
				objectDefinitionId);

		for (ObjectField objectField : objectFields) {
			if (objectField.isSystem()) {
				continue;
			}

			String objectFieldName = objectField.getName();

			if (!Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT) ||
				Objects.equals(
					GetterUtil.getLong(newValues.get(objectFieldName)),
					GetterUtil.getLong(oldValues.get(objectFieldName)))) {

				continue;
			}

			ObjectFieldSetting objectFieldSetting =
				_objectFieldSettingPersistence.fetchByOFI_N(
					objectField.getObjectFieldId(), "fileSource");

			if (!Objects.equals(
					objectFieldSetting.getValue(), "userComputer")) {

				continue;
			}

			objectFieldSetting = _objectFieldSettingPersistence.fetchByOFI_N(
				objectField.getObjectFieldId(), "showFilesInDocumentsAndMedia");

			if ((objectFieldSetting != null) &&
				GetterUtil.getBoolean(objectFieldSetting.getValue())) {

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
			String dbTableName, String pkObjectFieldDBColumnName,
			long primaryKey)
		throws PortalException {

		runSQL(
			StringBundler.concat(
				"delete from ", dbTableName, " where ",
				pkObjectFieldDBColumnName, " = ", primaryKey));

		FinderCacheUtil.clearDSLQueryCache(dbTableName);
	}

	private Predicate _fillAccountEntriesPredicate(
			long companyId, long userId, long objectDefinitionId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		if (!objectDefinition.isAccountEntryRestricted()) {
			return null;
		}

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectDefinition.getAccountEntryRestrictedObjectFieldId());

		Table<?> table = _objectFieldLocalService.getTable(
			objectDefinition.getObjectDefinitionId(), objectField.getName());

		Column<?, Long> column = (Column<?, Long>)table.getColumn(
			objectField.getDBColumnName());

		JoinStep joinStep = DSLQueryFactoryUtil.select(
			AccountEntryTable.INSTANCE.accountEntryId
		).from(
			AccountEntryTable.INSTANCE
		);

		if (_roleLocalService.hasUserRole(
				userId, companyId, RoleConstants.ADMINISTRATOR, true)) {

			return column.in(
				joinStep.where(
					AccountEntryTable.INSTANCE.companyId.eq(
						companyId
					).and(
						AccountEntryTable.INSTANCE.status.eq(
							WorkflowConstants.STATUS_APPROVED)
					)));
		}

		Table<OrganizationTable> tempOrganizationTable =
			DSLQueryFactoryUtil.select(
				OrganizationTable.INSTANCE.companyId,
				OrganizationTable.INSTANCE.treePath
			).from(
				OrganizationTable.INSTANCE
			).innerJoinON(
				Users_OrgsTable.INSTANCE,
				Users_OrgsTable.INSTANCE.organizationId.eq(
					OrganizationTable.INSTANCE.organizationId)
			).where(
				Users_OrgsTable.INSTANCE.userId.eq(userId)
			).as(
				"tempOrganizationTable", OrganizationTable.INSTANCE
			);

		return column.in(
			joinStep.innerJoinON(
				AccountEntryOrganizationRelTable.INSTANCE,
				AccountEntryOrganizationRelTable.INSTANCE.accountEntryId.eq(
					AccountEntryTable.INSTANCE.accountEntryId)
			).where(
				AccountEntryOrganizationRelTable.INSTANCE.organizationId.in(
					DSLQueryFactoryUtil.selectDistinct(
						OrganizationTable.INSTANCE.organizationId
					).from(
						OrganizationTable.INSTANCE
					).innerJoinON(
						tempOrganizationTable,
						OrganizationTable.INSTANCE.companyId.eq(
							tempOrganizationTable.getColumn(
								"companyId", Long.class)
						).and(
							OrganizationTable.INSTANCE.treePath.like(
								DSLFunctionFactoryUtil.concat(
									DSLFunctionFactoryUtil.castText(
										tempOrganizationTable.getColumn(
											"treePath", String.class)),
									new Scalar<>(StringPool.PERCENT)))
						)
					)
				).and(
					_getAccountEntryWherePredicate()
				)
			).union(
				joinStep.where(
					AccountEntryTable.INSTANCE.userId.eq(
						userId
					).and(
						_getAccountEntryWherePredicate()
					))
			).union(
				joinStep.innerJoinON(
					AccountEntryUserRelTable.INSTANCE,
					AccountEntryUserRelTable.INSTANCE.accountEntryId.eq(
						AccountEntryTable.INSTANCE.accountEntryId)
				).where(
					AccountEntryUserRelTable.INSTANCE.accountUserId.eq(
						userId
					).and(
						_getAccountEntryWherePredicate()
					)
				)
			));
	}

	private void _fillBusinessTypePicklistDefaultValue(
		List<ObjectField> objectFields, Map<String, Serializable> values) {

		for (ObjectField objectField : objectFields) {
			if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_PICKLIST) &&
				!values.containsKey(objectField.getName()) &&
				Validator.isNotNull(objectField.getDefaultValue())) {

				values.put(
					objectField.getName(), objectField.getDefaultValue());
			}
		}
	}

	private Predicate _fillPredicate(
			long objectDefinitionId, Predicate predicate, String search)
		throws PortalException {

		if (Validator.isNull(search)) {
			return predicate;
		}

		List<ObjectField> objectFields =
			_objectFieldPersistence.findByODI_DBT_I(
				objectDefinitionId, "String", true);

		if (objectFields.isEmpty()) {
			return predicate;
		}

		Predicate searchPredicate = null;

		for (ObjectField objectField : objectFields) {
			Table<?> table = _objectFieldLocalService.getTable(
				objectDefinitionId, objectField.getName());

			Column<?, ?> column = table.getColumn(
				objectField.getDBColumnName());

			if (column == null) {
				continue;
			}

			Predicate likePredicate = column.like("%" + search + "%");

			if (searchPredicate == null) {
				searchPredicate = likePredicate;
			}
			else {
				searchPredicate = searchPredicate.or(likePredicate);
			}
		}

		long searchLong = GetterUtil.getLong(search);

		if (searchLong != 0L) {
			searchPredicate = searchPredicate.or(
				ObjectEntryTable.INSTANCE.objectEntryId.eq(searchLong));
		}

		if (predicate == null) {
			return searchPredicate;
		}

		return predicate.and(searchPredicate.withParentheses());
	}

	private Predicate _getAccountEntryWherePredicate() {
		return AccountEntryTable.INSTANCE.parentAccountEntryId.eq(
			AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT
		).and(
			AccountEntryTable.INSTANCE.status.eq(
				WorkflowConstants.STATUS_APPROVED)
		).and(
			AccountEntryTable.INSTANCE.type.in(
				new String[] {
					AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
					AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON
				})
		);
	}

	private DLFolder _getDLFolder(
		long companyId, long groupId, String portletId,
		ServiceContext serviceContext, boolean showFilesInDocumentsAndMedia,
		String storageDLFolderPath, long userId) {

		Long dlFolderId = null;

		if (showFilesInDocumentsAndMedia) {
			dlFolderId = _getStorageDLFolderId(
				companyId, groupId, serviceContext, storageDLFolderPath);
		}
		else {
			dlFolderId = _getObjectRepositoryFolderId(
				companyId, groupId, portletId, serviceContext, userId);
		}

		if (dlFolderId == null) {
			return null;
		}

		return _dlFolderLocalService.fetchDLFolder(dlFolderId);
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

	private DSLQuery _getExtensionDynamicObjectDefinitionTableCountDSLQuery(
		DynamicObjectDefinitionTable extensionDynamicObjectDefinitionTable,
		long primaryKey) {

		return DSLQueryFactoryUtil.count(
		).from(
			extensionDynamicObjectDefinitionTable
		).where(
			extensionDynamicObjectDefinitionTable.getPrimaryKeyColumn(
			).eq(
				primaryKey
			)
		);
	}

	private DSLQuery _getExtensionDynamicObjectDefinitionTableSelectDSLQuery(
		DynamicObjectDefinitionTable extensionDynamicObjectDefinitionTable,
		long primaryKey, Expression<?>[] selectExpressions) {

		return DSLQueryFactoryUtil.select(
			selectExpressions
		).from(
			extensionDynamicObjectDefinitionTable
		).where(
			extensionDynamicObjectDefinitionTable.getPrimaryKeyColumn(
			).eq(
				primaryKey
			)
		);
	}

	private Expression<?> _getFunctionExpression(
		Map<String, Object> objectFieldSettingsValues,
		ObjectDefinition relatedObjectDefinition,
		DynamicObjectDefinitionTable relatedDynamicObjectDefinitionTable) {

		Column<?, ?> column = null;

		String function = GetterUtil.getString(
			objectFieldSettingsValues.get("function"));

		if (!Objects.equals(function, "COUNT")) {
			column = _objectFieldLocalService.getColumn(
				relatedObjectDefinition.getObjectDefinitionId(),
				GetterUtil.getString(
					objectFieldSettingsValues.get("objectFieldName")));
		}
		else {
			column = relatedDynamicObjectDefinitionTable.getPrimaryKeyColumn();
		}

		if (function.equals("AVERAGE")) {
			return DSLFunctionFactoryUtil.avg(
				(Expression<? extends Number>)column);
		}

		if (function.equals("COUNT")) {
			return DSLFunctionFactoryUtil.count(column);
		}

		if (function.equals("MAX")) {
			return DSLFunctionFactoryUtil.max(
				(Expression<? extends Comparable>)column);
		}

		if (function.equals("MIN")) {
			return DSLFunctionFactoryUtil.min(
				(Expression<? extends Comparable>)column);
		}

		if (function.equals("SUM")) {
			return DSLFunctionFactoryUtil.sum(
				(Expression<? extends Number>)column);
		}

		throw new IllegalArgumentException("Invalid function " + function);
	}

	private GroupByStep _getManyToManyObjectEntriesGroupByStep(
			long groupId, long objectRelationshipId, long primaryKey,
			boolean related, boolean reverse, FromStep fromStep)
		throws PortalException {

		ObjectRelationship objectRelationship =
			_objectRelationshipPersistence.findByPrimaryKey(
				objectRelationshipId);

		long objectDefinitionId1 = objectRelationship.getObjectDefinitionId1();

		long objectDefinitionId2 = objectRelationship.getObjectDefinitionId2();

		DynamicObjectDefinitionTable dynamicObjectDefinitionTable =
			_getDynamicObjectDefinitionTable(objectDefinitionId2);
		DynamicObjectDefinitionTable extensionDynamicObjectDefinitionTable =
			_getExtensionDynamicObjectDefinitionTable(objectDefinitionId2);

		Column<DynamicObjectDefinitionTable, Long>
			dynamicObjectDefinitionTablePrimaryKeyColumn =
				dynamicObjectDefinitionTable.getPrimaryKeyColumn();

		ObjectDefinition objectDefinition1 =
			_objectDefinitionPersistence.fetchByPrimaryKey(objectDefinitionId1);
		ObjectDefinition objectDefinition2 =
			_objectDefinitionPersistence.fetchByPrimaryKey(objectDefinitionId2);

		Map<String, String> pkObjectFieldDBColumnNames =
			ObjectRelationshipUtil.getPKObjectFieldDBColumnNames(
				objectDefinition1, objectDefinition2, reverse);

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

		return fromStep.from(
			dynamicObjectDefinitionTable
		).innerJoinON(
			ObjectEntryTable.INSTANCE,
			ObjectEntryTable.INSTANCE.objectEntryId.eq(
				dynamicObjectDefinitionTablePrimaryKeyColumn)
		).innerJoinON(
			extensionDynamicObjectDefinitionTable,
			extensionDynamicObjectDefinitionTable.getPrimaryKeyColumn(
			).eq(
				dynamicObjectDefinitionTablePrimaryKeyColumn
			)
		).leftJoinOn(
			dynamicObjectRelationshipMappingTable,
			primaryKeyColumn2.eq(dynamicObjectDefinitionTablePrimaryKeyColumn)
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
				() -> {
					if (PermissionThreadLocal.getPermissionChecker() == null) {
						return null;
					}

					return _inlineSQLHelper.getPermissionWherePredicate(
						objectDefinition2.getClassName(),
						dynamicObjectDefinitionTablePrimaryKeyColumn);
				}
			).and(
				() -> {
					if (related) {
						return primaryKeyColumn1.eq(primaryKey);
					}

					return dynamicObjectDefinitionTablePrimaryKeyColumn.notIn(
						DSLQueryFactoryUtil.select(
							primaryKeyColumn2
						).from(
							dynamicObjectRelationshipMappingTable
						).where(
							primaryKeyColumn1.eq(primaryKey)
						));
				}
			).and(
				() -> {
					if (objectDefinition1.getObjectDefinitionId() ==
							objectDefinition2.getObjectDefinitionId()) {

						return dynamicObjectDefinitionTablePrimaryKeyColumn.neq(
							primaryKey);
					}

					return null;
				}
			)
		);
	}

	private Repository _getObjectRepository(
		long groupId, String portletId, ServiceContext serviceContext) {

		Repository repository = _portletFileRepository.fetchPortletRepository(
			groupId, portletId);

		if (repository != null) {
			return repository;
		}

		try {
			return _portletFileRepository.addPortletRepository(
				groupId, portletId, serviceContext);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	private Long _getObjectRepositoryFolderId(
		long companyId, long groupId, String portletId,
		ServiceContext serviceContext, long userId) {

		Repository repository = _getObjectRepository(
			groupId, portletId, serviceContext);

		if (repository == null) {
			return null;
		}

		DLFolder dlFolder = _dlFolderLocalService.fetchFolder(
			repository.getGroupId(), repository.getDlFolderId(),
			String.valueOf(userId));

		if (dlFolder != null) {
			return dlFolder.getFolderId();
		}

		try {
			dlFolder = _dlFolderLocalService.addFolder(
				_userLocalService.getDefaultUserId(companyId),
				repository.getGroupId(), repository.getRepositoryId(), false,
				repository.getDlFolderId(), String.valueOf(userId), null, false,
				serviceContext);

			return dlFolder.getFolderId();
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	private GroupByStep _getOneToManyObjectEntriesGroupByStep(
			long groupId, long objectRelationshipId, long primaryKey,
			boolean related, FromStep fromStep)
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

		Column<DynamicObjectDefinitionTable, Long> primaryKeyColumn =
			dynamicObjectDefinitionTable.getPrimaryKeyColumn();

		return fromStep.from(
			dynamicObjectDefinitionTable
		).innerJoinON(
			ObjectEntryTable.INSTANCE,
			ObjectEntryTable.INSTANCE.objectEntryId.eq(primaryKeyColumn)
		).innerJoinON(
			extensionDynamicObjectDefinitionTable,
			extensionDynamicObjectDefinitionTable.getPrimaryKeyColumn(
			).eq(
				primaryKeyColumn
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
					Column<DynamicObjectDefinitionTable, Long> column = null;

					if (Objects.equals(
							objectField.getDBTableName(),
							dynamicObjectDefinitionTable.getName())) {

						column =
							(Column<DynamicObjectDefinitionTable, Long>)
								dynamicObjectDefinitionTable.getColumn(
									objectField.getDBColumnName());
					}
					else {
						column =
							(Column<DynamicObjectDefinitionTable, Long>)
								extensionDynamicObjectDefinitionTable.getColumn(
									objectField.getDBColumnName());
					}

					return column.eq(related ? primaryKey : 0L);
				}
			).and(
				() -> {
					if (objectRelationship.getObjectDefinitionId1() ==
							objectRelationship.getObjectDefinitionId2()) {

						return primaryKeyColumn.neq(primaryKey);
					}

					return null;
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
						objectDefinition2.getClassName(), primaryKeyColumn);
				}
			)
		);
	}

	private Predicate _getPermissionWherePredicate(
		DynamicObjectDefinitionTable dynamicObjectDefinitionTable,
		long groupId) {

		ObjectDefinition objectDefinition =
			dynamicObjectDefinitionTable.getObjectDefinition();

		if ((PermissionThreadLocal.getPermissionChecker() == null) ||
			!_inlineSQLHelper.isEnabled(
				objectDefinition.getCompanyId(), groupId)) {

			return null;
		}

		return _inlineSQLHelper.getPermissionWherePredicate(
			objectDefinition.getClassName(),
			dynamicObjectDefinitionTable.getPrimaryKeyColumn(), groupId);
	}

	private Object _getResult(
		Object entryValues, Expression<?> selectExpression) {

		Object result = null;

		try {
			if (selectExpression instanceof Column) {
				Column<?, ?> column = (Column<?, ?>)selectExpression;

				result = _getValue(entryValues, column.getSQLType());
			}
			else if (selectExpression instanceof ScalarDSLQueryAlias) {
				ScalarDSLQueryAlias scalarDSLQueryAlias =
					(ScalarDSLQueryAlias)selectExpression;

				result = _getValue(
					entryValues, scalarDSLQueryAlias.getSQLType());

				if (result == null) {
					result = "0";
				}
			}
		}
		catch (SQLException sqlException) {
			throw new SystemException(sqlException);
		}

		return result;
	}

	private Expression<?>[] _getSelectExpressions(
			DynamicObjectDefinitionTable dynamicObjectDefinitionTable)
		throws PortalException {

		List<Expression<?>> selectExpressions = new ArrayList<>();

		for (Column<DynamicObjectDefinitionTable, ?> column :
				dynamicObjectDefinitionTable.getColumns()) {

			selectExpressions.add(column);
		}

		for (ObjectField objectField :
				dynamicObjectDefinitionTable.getObjectFields()) {

			if (!objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION)) {

				continue;
			}

			Map<String, Object> objectFieldSettingsValues = new HashMap<>();

			List<ObjectFieldSetting> objectFieldSettings =
				_objectFieldSettingLocalService.
					getObjectFieldObjectFieldSettings(
						objectField.getObjectFieldId());

			for (ObjectFieldSetting objectFieldSetting : objectFieldSettings) {
				if (StringUtil.equals(
						objectFieldSetting.getName(), "filters")) {

					objectFieldSettingsValues.put(
						objectFieldSetting.getName(),
						objectFieldSetting.getObjectFilters());
				}
				else {
					objectFieldSettingsValues.put(
						objectFieldSetting.getName(),
						objectFieldSetting.getValue());
				}
			}

			ObjectDefinition objectDefinition =
				dynamicObjectDefinitionTable.getObjectDefinition();

			ObjectRelationship objectRelationship =
				ObjectRelationshipUtil.getObjectRelationship(
					_objectRelationshipPersistence.findByODI1_N(
						objectDefinition.getObjectDefinitionId(),
						GetterUtil.getString(
							objectFieldSettingsValues.get(
								"objectRelationshipName"))));

			ObjectDefinition relatedObjectDefinition =
				_objectDefinitionPersistence.findByPrimaryKey(
					objectRelationship.getObjectDefinitionId2());

			DynamicObjectDefinitionTable relatedDynamicObjectDefinitionTable =
				new DynamicObjectDefinitionTable(
					relatedObjectDefinition,
					_objectFieldLocalService.getObjectFields(
						relatedObjectDefinition.getObjectDefinitionId()),
					relatedObjectDefinition.getDBTableName());
			DynamicObjectDefinitionTable
				relatedExtensionDynamicObjectDefinitionTable =
					new DynamicObjectDefinitionTable(
						relatedObjectDefinition,
						_objectFieldLocalService.getObjectFields(
							relatedObjectDefinition.getObjectDefinitionId()),
						relatedObjectDefinition.getExtensionDBTableName());

			JoinStep joinStep = DSLQueryFactoryUtil.select(
				_getFunctionExpression(
					objectFieldSettingsValues, relatedObjectDefinition,
					relatedDynamicObjectDefinitionTable)
			).from(
				relatedDynamicObjectDefinitionTable
			).innerJoinON(
				relatedExtensionDynamicObjectDefinitionTable,
				relatedExtensionDynamicObjectDefinitionTable.
					getPrimaryKeyColumn(
					).eq(
						relatedDynamicObjectDefinitionTable.
							getPrimaryKeyColumn()
					)
			);

			if (!relatedObjectDefinition.isSystem()) {
				joinStep = joinStep.innerJoinON(
					ObjectEntryTable.INSTANCE,
					ObjectEntryTable.INSTANCE.objectEntryId.eq(
						relatedDynamicObjectDefinitionTable.
							getPrimaryKeyColumn()));
			}

			Predicate predicate = null;

			if (Objects.equals(
					objectRelationship.getType(),
					ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

				ObjectField relatedField =
					_objectFieldLocalService.getObjectField(
						objectRelationship.getObjectFieldId2());

				Column<DynamicObjectDefinitionTable, Long>
					relatedObjectDefinitionColumn =
						(Column<DynamicObjectDefinitionTable, Long>)
							_objectFieldLocalService.getColumn(
								relatedObjectDefinition.getObjectDefinitionId(),
								relatedField.getName());

				predicate = relatedObjectDefinitionColumn.eq(
					dynamicObjectDefinitionTable.getPrimaryKeyColumn());
			}
			else if (Objects.equals(
						objectRelationship.getType(),
						ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

				DynamicObjectRelationshipMappingTable
					dynamicObjectRelationshipMappingTable =
						new DynamicObjectRelationshipMappingTable(
							objectDefinition.getPKObjectFieldDBColumnName(),
							relatedObjectDefinition.
								getPKObjectFieldDBColumnName(),
							objectRelationship.getDBTableName());

				Column<DynamicObjectRelationshipMappingTable, Long>
					primaryKeyColumn2 =
						dynamicObjectRelationshipMappingTable.
							getPrimaryKeyColumn2();

				joinStep = joinStep.innerJoinON(
					dynamicObjectRelationshipMappingTable,
					primaryKeyColumn2.eq(
						relatedDynamicObjectDefinitionTable.
							getPrimaryKeyColumn()));

				Column<DynamicObjectRelationshipMappingTable, Long>
					primaryKeyColumn1 =
						dynamicObjectRelationshipMappingTable.
							getPrimaryKeyColumn1();

				predicate = primaryKeyColumn1.eq(
					dynamicObjectDefinitionTable.getPrimaryKeyColumn());
			}

			List<String> oDataFilterStrings = TransformUtil.transform(
				(List<ObjectFilter>)objectFieldSettingsValues.get("filters"),
				objectFilter -> {
					if (StringUtil.equals(
							objectFilter.getFilterType(),
							ObjectFilterConstants.TYPE_CURRENT_USER)) {

						objectFilter.setJSON(
							JSONUtil.put(
								"currentUserId",
								PrincipalThreadLocal.getUserId()
							).toString());
					}

					ObjectFilterParser objectFilterParser =
						_objectFilterParserServiceRegistry.
							getObjectFilterParser(objectFilter.getFilterType());

					return objectFilterParser.parse(objectFilter);
				});

			for (String oDataFilterString : oDataFilterStrings) {
				predicate = predicate.and(
					_filterPredicateFactory.create(
						oDataFilterString,
						relatedObjectDefinition.getObjectDefinitionId()));
			}

			selectExpressions.add(
				DSLQueryFactoryUtil.scalarSubDSLQuery(
					joinStep.where(predicate),
					DynamicObjectDefinitionTable.getJavaClass(
						objectField.getDBType()),
					objectField.getName(),
					DynamicObjectDefinitionTable.getSQLType(
						objectField.getDBType())));
		}

		return selectExpressions.toArray(new Expression<?>[0]);
	}

	private Long _getStorageDLFolderId(
		long companyId, long groupId, ServiceContext serviceContext,
		String storageDLFolderPath) {

		long storageDLFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		for (String name :
				com.liferay.petra.string.StringUtil.split(
					storageDLFolderPath, CharPool.FORWARD_SLASH)) {

			DLFolder dlFolder = _dlFolderLocalService.fetchFolder(
				groupId, storageDLFolderId, name);

			if (dlFolder != null) {
				storageDLFolderId = dlFolder.getFolderId();

				continue;
			}

			try {
				Folder folder = _dlAppLocalService.addFolder(
					_userLocalService.getDefaultUserId(companyId), groupId,
					storageDLFolderId, name, null, serviceContext);

				storageDLFolderId = folder.getFolderId();
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}

				return null;
			}
		}

		return storageDLFolderId;
	}

	/**
	 * @see com.liferay.portal.upgrade.util.Table#getValue
	 */
	private Object _getValue(Object object, int sqlType) throws SQLException {
		if (sqlType == Types.BIGINT) {
			return GetterUtil.getLong(object);
		}
		else if (sqlType == Types.BOOLEAN) {
			return GetterUtil.getBoolean(object);
		}
		else if (sqlType == Types.CLOB) {
			return GetterUtil.getString(object);
		}
		else if ((sqlType == Types.DATE) || (sqlType == Types.TIMESTAMP)) {
			if (object == null) {
				return null;
			}

			Date date = (Date)object;

			return new Timestamp(date.getTime());
		}
		else if (sqlType == Types.DECIMAL) {
			return object;
		}
		else if (sqlType == Types.DOUBLE) {
			return GetterUtil.getDouble(object);
		}
		else if (sqlType == Types.INTEGER) {
			return GetterUtil.getInteger(object);
		}
		else if (sqlType == Types.VARCHAR) {
			return object;
		}
		else {
			throw new IllegalArgumentException(
				"Unable to get value with SQL type " + sqlType);
		}
	}

	private String _getValue(String valueString) {
		try {
			JSONArray jsonArray = _jsonFactory.createJSONArray(valueString);

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
			Expression<?> selectExpression = selectExpressions[i];

			String columnName = null;
			Class<?> javaTypeClass = null;

			if (selectExpression instanceof Column) {
				Column<?, ?> column = (Column<?, ?>)selectExpressions[i];

				columnName = column.getName();
				javaTypeClass = column.getJavaType();
			}
			else if (selectExpression instanceof ScalarDSLQueryAlias) {
				ScalarDSLQueryAlias scalarDSLQueryAlias =
					(ScalarDSLQueryAlias)selectExpressions[i];

				columnName = scalarDSLQueryAlias.getName();
				javaTypeClass = scalarDSLQueryAlias.getJavaType();
			}

			if (columnName.endsWith(StringPool.UNDERLINE)) {
				columnName = columnName.substring(0, columnName.length() - 1);
			}

			_putValue(javaTypeClass, columnName, objects[i], values);
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
			if (objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION) ||
				objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_FORMULA) ||
				!values.containsKey(objectField.getName())) {

				if (objectField.isRequired()) {
					throw new ObjectEntryValuesException.Required(
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
					objectField.getDBColumnName(),
					GetterUtil.getLong(values.get(objectField.getName())),
					dynamicObjectDefinitionTable);
			}

			sb.append(", ");
			sb.append(objectField.getDBColumnName());

			count++;
		}

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
				if (objectField.compareBusinessType(
						ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION) ||
					objectField.compareBusinessType(
						ObjectFieldConstants.BUSINESS_TYPE_FORMULA) ||
					!values.containsKey(objectField.getName())) {

					continue;
				}

				_setColumn(
					dynamicObjectDefinitionTable, index++, objectField,
					preparedStatement, values);
			}

			preparedStatement.executeUpdate();

			FinderCacheUtil.clearDSLQueryCache(
				dynamicObjectDefinitionTable.getTableName());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private List<Object[]> _list(
		DSLQuery dslQuery, Expression<?>[] selectExpressions) {

		List<Object[]> results = new ArrayList<>();

		List<Object> entriesValues = objectEntryPersistence.dslQuery(dslQuery);

		for (Object entryValues : entriesValues) {
			Object[] result = new Object[selectExpressions.length];

			if (selectExpressions.length == 1) {
				result[0] = _getResult(entryValues, selectExpressions[0]);
			}
			else {
				for (int i = 0; i < selectExpressions.length; i++) {
					result[i] = _getResult(
						((Object[])entryValues)[i], selectExpressions[i]);
				}
			}

			results.add(result);
		}

		return results;
	}

	private Map<String, Serializable> _putFormulaObjectFieldValues(
			long objectDefinitionId, Map<String, Serializable> values)
		throws PortalException {

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(objectDefinitionId);

		for (ObjectField objectField : objectFields) {
			if (!objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_FORMULA)) {

				continue;
			}

			ObjectFieldFormulaEvaluatorUtil.evaluate(
				_ddmExpressionFactory,
				_objectFieldLocalService.getObjectFields(objectDefinitionId),
				_objectFieldSettingLocalService, _userLocalService, values);
		}

		return values;
	}

	private void _putValue(
		Class<?> javaTypeClass, String name, Object object,
		Map<String, Serializable> values) {

		if (javaTypeClass == BigDecimal.class) {
			values.put(name, (BigDecimal)object);
		}
		else if (javaTypeClass == Blob.class) {
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
		else if (javaTypeClass == Boolean.class) {
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
		else if (javaTypeClass == Clob.class) {
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
		else if (javaTypeClass == Date.class) {
			values.put(name, (Date)object);
		}
		else if (javaTypeClass == Double.class) {
			Number number = (Number)object;

			if (number == null) {
				number = Double.valueOf(0D);
			}
			else if (!(number instanceof Double)) {
				number = number.doubleValue();
			}

			values.put(name, number);
		}
		else if (javaTypeClass == Integer.class) {
			Number number = (Number)object;

			if (number == null) {
				number = Integer.valueOf(0);
			}
			else if (!(number instanceof Integer)) {
				number = number.intValue();
			}

			values.put(name, number);
		}
		else if (javaTypeClass == Long.class) {
			Number number = (Number)object;

			if (number == null) {
				number = Long.valueOf(0L);
			}
			else if (!(number instanceof Long)) {
				number = number.longValue();
			}

			values.put(name, number);
		}
		else if (javaTypeClass == String.class) {
			values.put(name, (String)object);
		}
		else {
			throw new IllegalArgumentException(
				"Unable to put value with class " + javaTypeClass.getName());
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

	private void _setColumn(
			DynamicObjectDefinitionTable dynamicObjectDefinitionTable,
			int index, ObjectField objectField,
			PreparedStatement preparedStatement,
			Map<String, Serializable> values)
		throws Exception {

		Column<?, ?> column = dynamicObjectDefinitionTable.getColumn(
			objectField.getDBColumnName());

		Object value = values.get(objectField.getName());

		if (StringUtil.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST)) {

			String valueString = String.valueOf(value);

			// Remove the first [ and the last ] in
			// "[pickListEntryKey1, pickListEntryKey2, pickListEntryKey3]"

			if (StringUtil.endsWith(valueString, StringPool.CLOSE_BRACKET) &&
				StringUtil.startsWith(valueString, StringPool.OPEN_BRACKET)) {

				valueString = valueString.substring(
					1, valueString.length() - 1);
			}

			_setColumn(
				preparedStatement, index, column.getSQLType(), valueString);
		}
		else {
			_setColumn(preparedStatement, index, column.getSQLType(), value);
		}
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
				index, new BigDecimal(String.valueOf(value)));
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

	private void _setExternalReferenceCode(
			ObjectEntry objectEntry, Map<String, Serializable> values)
		throws PortalException {

		for (Map.Entry<String, Serializable> entry : values.entrySet()) {
			if (StringUtil.equals(entry.getKey(), "externalReferenceCode")) {
				String externalReferenceCode = String.valueOf(entry.getValue());

				if (Validator.isNull(externalReferenceCode)) {
					externalReferenceCode = String.valueOf(
						objectEntry.getObjectEntryId());
				}

				_validateExternalReferenceCode(
					externalReferenceCode, objectEntry.getCompanyId(),
					objectEntry.getObjectDefinitionId(),
					objectEntry.getObjectEntryId());

				objectEntry.setExternalReferenceCode(externalReferenceCode);
			}
		}
	}

	private void _startWorkflowInstance(
			long userId, ObjectEntry objectEntry, ServiceContext serviceContext)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(
				objectEntry.getObjectDefinitionId());

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			_skipModelListeners.set(true);

			WorkflowThreadLocal.setEnabled(true);

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				objectEntry.getCompanyId(), objectEntry.getNonzeroGroupId(),
				userId, objectDefinition.getClassName(),
				objectEntry.getObjectEntryId(), objectEntry, serviceContext);
		}
		finally {
			_skipModelListeners.set(false);

			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
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
			if (objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION) ||
				objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_FORMULA)) {

				continue;
			}

			if (!values.containsKey(objectField.getName())) {
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
					objectField.getDBColumnName(),
					GetterUtil.getLong(values.get(objectField.getName())),
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
				if (objectField.compareBusinessType(
						ObjectFieldConstants.BUSINESS_TYPE_AGGREGATION) ||
					objectField.compareBusinessType(
						ObjectFieldConstants.BUSINESS_TYPE_FORMULA) ||
					!values.containsKey(objectField.getName())) {

					continue;
				}

				_setColumn(
					dynamicObjectDefinitionTable, index++, objectField,
					preparedStatement, values);
			}

			_setColumn(preparedStatement, index++, Types.BIGINT, objectEntryId);

			preparedStatement.executeUpdate();

			FinderCacheUtil.clearDSLQueryCache(
				dynamicObjectDefinitionTable.getTableName());
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private void _validateExternalReferenceCode(
			String externalReferenceCode, long companyId,
			long objectDefinitionId, long objectEntryId)
		throws PortalException {

		ObjectEntry objectEntry = objectEntryPersistence.fetchByERC_C_ODI(
			externalReferenceCode, companyId, objectDefinitionId);

		if ((objectEntry != null) &&
			(objectEntry.getObjectEntryId() != objectEntryId)) {

			throw new ObjectEntryValuesException.MustNotBeDuplicate(
				externalReferenceCode);
		}
	}

	private void _validateFileExtension(
			String fileExtension, long objectFieldId, String objectFieldName)
		throws PortalException {

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingPersistence.fetchByOFI_N(
				objectFieldId, "acceptedFileExtensions");

		String acceptedFileExtensions = objectFieldSetting.getValue();

		if (!ArrayUtil.contains(
				acceptedFileExtensions.split("\\s*,\\s*"), fileExtension,
				true)) {

			throw new ObjectEntryValuesException.InvalidFileExtension(
				fileExtension, objectFieldName);
		}
	}

	private void _validateFileSize(
			boolean defaultUser, long fileSize, long objectFieldId,
			String objectFieldName)
		throws PortalException {

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingPersistence.fetchByOFI_N(
				objectFieldId, "maximumFileSize");

		long maximumFileSize = GetterUtil.getLong(
			objectFieldSetting.getValue());

		if (defaultUser &&
			(_objectConfiguration.maximumFileSizeForGuestUsers() <
				maximumFileSize)) {

			maximumFileSize =
				_objectConfiguration.maximumFileSizeForGuestUsers();
		}

		if ((maximumFileSize > 0) &&
			(fileSize > (maximumFileSize * 1024 * 1024))) {

			throw new ObjectEntryValuesException.ExceedsMaxFileSize(
				maximumFileSize, objectFieldName);
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

	private void _validateObjectStateTransition(
			Map.Entry<String, Serializable> entry, long listTypeDefinitionId,
			ObjectEntry objectEntry, long objectFieldId)
		throws PortalException {

		Map<String, Serializable> values = objectEntry.getValues();

		ListTypeEntry originalListTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				listTypeDefinitionId,
				_getValue(String.valueOf(values.get(entry.getKey()))));

		ObjectStateFlow objectStateFlow =
			_objectStateFlowLocalService.fetchObjectFieldObjectStateFlow(
				objectFieldId);

		ObjectState sourceObjectState =
			_objectStateLocalService.getObjectStateFlowObjectState(
				originalListTypeEntry.getListTypeEntryId(),
				objectStateFlow.getObjectStateFlowId());

		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				listTypeDefinitionId, String.valueOf(entry.getValue()));

		boolean invalidObjectStateTransition = true;

		for (ObjectState nextObjectState :
				_objectStateLocalService.getNextObjectStates(
					sourceObjectState.getObjectStateId())) {

			if (nextObjectState.getListTypeEntryId() ==
					listTypeEntry.getListTypeEntryId()) {

				invalidObjectStateTransition = false;
			}
		}

		if (invalidObjectStateTransition) {
			ObjectState targetObjectState =
				_objectStateLocalService.getObjectStateFlowObjectState(
					listTypeEntry.getListTypeEntryId(),
					objectStateFlow.getObjectStateFlowId());

			throw new ObjectEntryValuesException.InvalidObjectStateTransition(
				sourceObjectState, targetObjectState);
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
				dbColumnName, dbColumnValue,
				dynamicObjectDefinitionTable.getTableName());
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
				dbColumnName, dbColumnValue,
				dynamicObjectDefinitionTable.getTableName());
		}
	}

	private void _validateTextMaxLength(
			int defaultMaxLength, String objectEntryValue, long objectFieldId,
			String objectFieldName)
		throws PortalException {

		int maxLength = defaultMaxLength;

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingPersistence.fetchByOFI_N(
				objectFieldId, "maxLength");

		if (objectFieldSetting != null) {
			maxLength = GetterUtil.getInteger(objectFieldSetting.getValue());
		}

		if (objectEntryValue.length() > maxLength) {
			throw new ObjectEntryValuesException.ExceedsTextMaxLength(
				maxLength, objectFieldName);
		}
	}

	private void _validateValues(
			boolean defaultUser, long objectDefinitionId,
			ObjectEntry objectEntry, String portletId,
			ServiceContext serviceContext, long userId,
			Map<String, Serializable> values)
		throws PortalException {

		for (Map.Entry<String, Serializable> entry : values.entrySet()) {
			_validateValues(
				defaultUser, entry, objectDefinitionId, objectEntry, portletId,
				serviceContext, userId, values);
		}
	}

	private void _validateValues(
			boolean defaultUser, Map.Entry<String, Serializable> entry,
			long objectDefinitionId, ObjectEntry objectEntry, String portletId,
			ServiceContext serviceContext, long userId,
			Map<String, Serializable> values)
		throws PortalException {

		ObjectField objectField = null;

		try {
			objectField = _objectFieldLocalService.getObjectField(
				objectDefinitionId, entry.getKey());
		}
		catch (NoSuchObjectFieldException noSuchObjectFieldException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchObjectFieldException);
			}

			return;
		}

		if (Validator.isNull(values.get(objectField.getName())) &&
			objectField.isRequired()) {

			throw new ObjectEntryValuesException.Required(
				objectField.getName());
		}
		else if (StringUtil.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

			DLFileEntry dlFileEntry = _dlFileEntryLocalService.fetchDLFileEntry(
				GetterUtil.getLong(entry.getValue()));

			if (dlFileEntry != null) {
				_validateFileExtension(
					dlFileEntry.getExtension(), objectField.getObjectFieldId(),
					objectField.getName());
				_validateFileSize(
					defaultUser, dlFileEntry.getSize(),
					objectField.getObjectFieldId(), objectField.getName());

				_addFileEntry(
					dlFileEntry, entry, objectField.getObjectFieldSettings(),
					portletId, serviceContext, userId);

				return;
			}

			if (objectField.isRequired()) {
				throw new ObjectEntryValuesException.Required(
					objectField.getName());
			}
		}
		else if (StringUtil.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT)) {

			_validateTextMaxLength(
				65000, GetterUtil.getString(entry.getValue()),
				objectField.getObjectFieldId(), objectField.getName());
		}
		else if (StringUtil.equals(
					objectField.getDBType(),
					ObjectFieldConstants.DB_TYPE_INTEGER)) {

			Serializable entryValue = entry.getValue();

			String entryValueString = entryValue.toString();

			if (!entryValueString.isEmpty()) {
				int value = GetterUtil.getInteger(entryValue);

				if (!StringUtil.equals(
						String.valueOf(value), entryValueString)) {

					throw new ObjectEntryValuesException.ExceedsIntegerSize(
						9, objectField.getName());
				}
			}
		}
		else if (StringUtil.equals(
					objectField.getDBType(),
					ObjectFieldConstants.DB_TYPE_LONG)) {

			Serializable entryValue = entry.getValue();

			String entryValueString = entryValue.toString();

			if (!entryValueString.isEmpty()) {
				long value = GetterUtil.getLong(entryValue);

				if (!StringUtil.equals(
						String.valueOf(value), entryValue.toString())) {

					throw new ObjectEntryValuesException.ExceedsLongSize(
						16, objectField.getName());
				}
				else if (value > ObjectFieldValidationConstants.
							BUSINESS_TYPE_LONG_VALUE_MAX) {

					throw new ObjectEntryValuesException.ExceedsLongMaxSize(
						ObjectFieldValidationConstants.
							BUSINESS_TYPE_LONG_VALUE_MAX,
						objectField.getName());
				}
				else if (value < ObjectFieldValidationConstants.
							BUSINESS_TYPE_LONG_VALUE_MIN) {

					throw new ObjectEntryValuesException.ExceedsLongMinSize(
						ObjectFieldValidationConstants.
							BUSINESS_TYPE_LONG_VALUE_MIN,
						objectField.getName());
				}
			}
		}
		else if (StringUtil.equals(
					objectField.getDBType(),
					ObjectFieldConstants.DB_TYPE_STRING)) {

			_validateTextMaxLength(
				280, GetterUtil.getString(entry.getValue()),
				objectField.getObjectFieldId(), objectField.getName());
		}

		if (objectField.getListTypeDefinitionId() != 0) {
			List<ListTypeEntry> listTypeEntries =
				_listTypeEntryLocalService.getListTypeEntries(
					objectField.getListTypeDefinitionId());

			Stream<ListTypeEntry> stream = listTypeEntries.stream();

			String value = _getValue(
				String.valueOf(values.get(entry.getKey())));

			if ((!value.isEmpty() || objectField.isRequired()) &&
				!stream.anyMatch(
					listTypeEntry -> Objects.equals(
						listTypeEntry.getKey(), value))) {

				throw new ObjectEntryValuesException.ListTypeEntry(
					entry.getKey());
			}

			if ((objectEntry != null) && objectField.isState()) {
				_validateObjectStateTransition(
					entry, objectField.getListTypeDefinitionId(), objectEntry,
					objectField.getObjectFieldId());
			}
		}
	}

	private static final Expression<?>[] _EXPRESSIONS = {
		ObjectEntryTable.INSTANCE.objectEntryId,
		ObjectEntryTable.INSTANCE.userName,
		ObjectEntryTable.INSTANCE.createDate,
		ObjectEntryTable.INSTANCE.modifiedDate,
		ObjectEntryTable.INSTANCE.externalReferenceCode,
		ObjectEntryTable.INSTANCE.status
	};

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryLocalServiceImpl.class);

	private static final ThreadLocal<Boolean> _skipModelListeners =
		new CentralizedThreadLocal<>(
			ObjectEntryLocalServiceImpl.class + "._skipModelListeners",
			() -> false);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetLinkLocalService _assetLinkLocalService;

	@Reference
	private CurrentConnection _currentConnection;

	@Reference
	private DDMExpressionFactory _ddmExpressionFactory;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

	@Reference
	private FilterPredicateFactory _filterPredicateFactory;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InlineSQLHelper _inlineSQLHelper;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private Localization _localization;

	private volatile ObjectConfiguration _objectConfiguration;

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectFieldPersistence _objectFieldPersistence;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Reference
	private ObjectFieldSettingPersistence _objectFieldSettingPersistence;

	@Reference
	private ObjectFilterParserServiceRegistry
		_objectFilterParserServiceRegistry;

	@Reference
	private ObjectRelatedModelsProviderRegistry
		_objectRelatedModelsProviderRegistry;

	@Reference
	private ObjectRelationshipPersistence _objectRelationshipPersistence;

	@Reference
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

	@Reference
	private ObjectStateFlowLocalService _objectStateFlowLocalService;

	@Reference
	private ObjectStateLocalService _objectStateLocalService;

	@Reference
	private PersistedModelLocalServiceRegistry
		_persistedModelLocalServiceRegistry;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private Sorts _sorts;

	@Reference
	private SystemObjectDefinitionMetadataRegistry
		_systemObjectDefinitionMetadataRegistry;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}