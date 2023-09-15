/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.manager.v1_0;

import com.liferay.object.action.engine.ObjectActionEngine;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectConstants;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.entry.util.ObjectEntryDTOConverterUtil;
import com.liferay.object.entry.util.ObjectEntryThreadLocal;
import com.liferay.object.exception.NoSuchObjectEntryException;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeRegistry;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.related.models.ManyToOneObjectRelatedModelsProvider;
import com.liferay.object.related.models.ObjectRelatedModelsProvider;
import com.liferay.object.related.models.ObjectRelatedModelsProviderRegistry;
import com.liferay.object.relationship.util.ObjectRelationshipUtil;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.dto.v1_0.Status;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.rest.filter.parser.ObjectDefinitionFilterParser;
import com.liferay.object.rest.internal.petra.sql.dsl.expression.OrderByExpressionUtil;
import com.liferay.object.rest.internal.resource.v1_0.ObjectEntryRelatedObjectsResourceImpl;
import com.liferay.object.rest.internal.resource.v1_0.ObjectEntryResourceImpl;
import com.liferay.object.rest.internal.util.ObjectEntryValuesUtil;
import com.liferay.object.rest.manager.v1_0.BaseObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.DefaultObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.rest.manager.v1_0.ObjectRelationshipElementsParser;
import com.liferay.object.rest.manager.v1_0.ObjectRelationshipElementsParserRegistry;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.ObjectRelationshipService;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ExternalReferenceCodeModel;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.aggregation.bucket.NestedAggregation;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.aggregation.Facet;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedFieldsSupplier;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.io.Serializable;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 */
@Component(
	property = "object.entry.manager.storage.type=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
	service = ObjectEntryManager.class
)
public class DefaultObjectEntryManagerImpl
	extends BaseObjectEntryManager implements DefaultObjectEntryManager {

	@Override
	public ObjectEntry addObjectEntry(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, ObjectEntry objectEntry,
			String scopeKey)
		throws Exception {

		validateReadOnlyObjectFields(null, objectDefinition, objectEntry);

		com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry =
			_objectEntryService.addObjectEntry(
				getGroupId(objectDefinition, scopeKey),
				objectDefinition.getObjectDefinitionId(),
				_toObjectValues(
					dtoConverterContext.getUserId(), objectDefinition,
					objectEntry, dtoConverterContext.getLocale()),
				_createServiceContext(
					objectEntry, dtoConverterContext.getUserId()));

		return _toObjectEntry(
			dtoConverterContext, objectDefinition,
			_addOrUpdateNestedObjectEntries(
				dtoConverterContext, objectDefinition, objectEntry,
				_getObjectRelationships(objectDefinition, objectEntry),
				serviceBuilderObjectEntry.getPrimaryKey()));
	}

	@Override
	public ObjectEntry addObjectRelationshipMappingTableValues(
			DTOConverterContext dtoConverterContext,
			ObjectRelationship objectRelationship, long primaryKey1,
			long primaryKey2)
		throws Exception {

		_objectRelationshipService.addObjectRelationshipMappingTableValues(
			objectRelationship.getObjectRelationshipId(), primaryKey1,
			primaryKey2, new ServiceContext());

		return getObjectEntry(
			dtoConverterContext,
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId2()),
			primaryKey2);
	}

	@Override
	public Object addSystemObjectRelationshipMappingTableValues(
			ObjectDefinition objectDefinition,
			ObjectRelationship objectRelationship, long primaryKey1,
			long primaryKey2)
		throws Exception {

		_objectRelationshipService.addObjectRelationshipMappingTableValues(
			objectRelationship.getObjectRelationshipId(), primaryKey1,
			primaryKey2, new ServiceContext());

		SystemObjectDefinitionManager systemObjectDefinitionManager =
			_systemObjectDefinitionManagerRegistry.
				getSystemObjectDefinitionManager(objectDefinition.getName());

		PersistedModelLocalService persistedModelLocalService =
			_persistedModelLocalServiceRegistry.getPersistedModelLocalService(
				systemObjectDefinitionManager.getModelClassName());

		return _toDTO(
			(BaseModel<?>)persistedModelLocalService.getPersistedModel(
				primaryKey2),
			_objectEntryService.getObjectEntry(primaryKey1),
			systemObjectDefinitionManager);
	}

	@Override
	public void deleteObjectEntry(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ObjectDefinition objectDefinition,
			String scopeKey)
		throws Exception {

		com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry =
			_objectEntryService.getObjectEntry(
				externalReferenceCode, companyId,
				getGroupId(objectDefinition, scopeKey));

		_checkObjectEntryObjectDefinitionId(
			objectDefinition, serviceBuilderObjectEntry);

		_objectEntryService.deleteObjectEntry(
			serviceBuilderObjectEntry.getObjectEntryId());
	}

	@Override
	public void deleteObjectEntry(
			ObjectDefinition objectDefinition, long objectEntryId)
		throws Exception {

		_checkObjectEntryObjectDefinitionId(
			objectDefinition,
			_objectEntryService.getObjectEntry(objectEntryId));

		_objectEntryService.deleteObjectEntry(objectEntryId);
	}

	@Override
	public void disassociateRelatedModels(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition,
			ObjectRelationship objectRelationship, long primaryKey,
			ObjectDefinition relatedObjectDefinition, long userId)
		throws Exception {

		long[] relatedPrimaryKeys = TransformUtil.transformToLongArray(
			_getRelatedModels(
				objectDefinition, objectRelationship, primaryKey,
				relatedObjectDefinition),
			BaseModel::getPrimaryKeyObj);

		if (relatedPrimaryKeys.length > 0) {
			_disassociateRelatedModels(
				objectDefinition, objectRelationship, primaryKey,
				relatedPrimaryKeys, relatedObjectDefinition, userId);
		}
	}

	@Override
	public void executeObjectAction(
			DTOConverterContext dtoConverterContext, String objectActionName,
			ObjectDefinition objectDefinition, long objectEntryId)
		throws Exception {

		_executeObjectAction(
			dtoConverterContext, objectActionName, objectDefinition,
			objectEntryLocalService.getObjectEntry(objectEntryId));
	}

	@Override
	public void executeObjectAction(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, String objectActionName,
			ObjectDefinition objectDefinition, String scopeKey)
		throws Exception {

		_executeObjectAction(
			dtoConverterContext, objectActionName, objectDefinition,
			objectEntryLocalService.getObjectEntry(
				externalReferenceCode, companyId,
				getGroupId(objectDefinition, scopeKey)));
	}

	@Override
	public ObjectEntry fetchObjectEntry(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, long objectEntryId)
		throws Exception {

		com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry =
			_objectEntryService.fetchObjectEntry(objectEntryId);

		if (serviceBuilderObjectEntry == null) {
			return null;
		}

		if (objectDefinition == null) {
			objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					serviceBuilderObjectEntry.getObjectDefinitionId());
		}

		return _toObjectEntry(
			dtoConverterContext, objectDefinition, serviceBuilderObjectEntry);
	}

	@Override
	public ObjectEntry fetchRelatedManyToOneObjectEntry(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, Long objectEntryId,
			String objectRelationshipName)
		throws Exception {

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.
				getObjectRelationshipByObjectDefinitionId(
					objectDefinition.getObjectDefinitionId(),
					objectRelationshipName);

		ObjectDefinition relatedObjectDefinition = _getRelatedObjectDefinition(
			objectDefinition, objectRelationship);

		ManyToOneObjectRelatedModelsProvider objectRelatedModelsProvider =
			(ManyToOneObjectRelatedModelsProvider)
				_objectRelatedModelsProviderRegistry.
					getObjectRelatedModelsProvider(
						relatedObjectDefinition.getClassName(),
						relatedObjectDefinition.getCompanyId(),
						objectRelationship.getType());

		com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry =
			(com.liferay.object.model.ObjectEntry)
				objectRelatedModelsProvider.fetchRelatedModel(
					GroupThreadLocal.getGroupId(),
					objectRelationship.getObjectRelationshipId(),
					objectEntryId);

		if (serviceBuilderObjectEntry == null) {
			return null;
		}

		return _toObjectEntry(
			dtoConverterContext, relatedObjectDefinition,
			serviceBuilderObjectEntry);
	}

	@Override
	public Page<ObjectEntry> getObjectEntries(
			long companyId, ObjectDefinition objectDefinition, String scopeKey,
			Aggregation aggregation, DTOConverterContext dtoConverterContext,
			Expression filterExpression, Pagination pagination, String search,
			Sort[] sorts)
		throws Exception {

		Predicate predicate = _filterFactory.create(
			filterExpression, objectDefinition);

		long groupId = getGroupId(objectDefinition, scopeKey);

		int start = _getStartPosition(pagination);
		int end = _getEndPosition(pagination);

		List<Facet> facets = new ArrayList<>();

		if ((aggregation != null) &&
			(aggregation.getAggregationTerms() != null)) {

			Map<String, String> aggregationTerms =
				aggregation.getAggregationTerms();

			for (Map.Entry<String, String> entry1 :
					aggregationTerms.entrySet()) {

				List<Facet.FacetValue> facetValues = new ArrayList<>();

				Map<Object, Long> aggregationCounts =
					objectEntryLocalService.getAggregationCounts(
						groupId, objectDefinition.getObjectDefinitionId(),
						entry1.getKey(), predicate, start, end);

				for (Map.Entry<Object, Long> entry2 :
						aggregationCounts.entrySet()) {

					Long value = entry2.getValue();

					facetValues.add(
						new Facet.FacetValue(
							value.intValue(), String.valueOf(entry2.getKey())));
				}

				facets.add(new Facet(entry1.getKey(), facetValues));
			}
		}

		return Page.of(
			HashMapBuilder.put(
				"create",
				ActionUtil.addAction(
					"ADD_OBJECT_ENTRY", ObjectEntryResourceImpl.class, 0L,
					"postObjectEntry", null, objectDefinition.getUserId(),
					_getObjectEntriesPermissionName(
						objectDefinition.getObjectDefinitionId()),
					groupId, dtoConverterContext.getUriInfo())
			).put(
				"createBatch",
				ActionUtil.addAction(
					"ADD_OBJECT_ENTRY", ObjectEntryResourceImpl.class, 0L,
					"postObjectEntryBatch", null, objectDefinition.getUserId(),
					_getObjectEntriesPermissionName(
						objectDefinition.getObjectDefinitionId()),
					groupId, dtoConverterContext.getUriInfo())
			).put(
				"deleteBatch",
				ActionUtil.addAction(
					ActionKeys.DELETE, ObjectEntryResourceImpl.class, null,
					"deleteObjectEntryBatch", null,
					objectDefinition.getUserId(),
					_getObjectEntriesPermissionName(
						objectDefinition.getObjectDefinitionId()),
					groupId, dtoConverterContext.getUriInfo())
			).put(
				"get",
				ActionUtil.addAction(
					ActionKeys.VIEW, ObjectEntryResourceImpl.class, 0L,
					"getObjectEntriesPage", null, objectDefinition.getUserId(),
					_getObjectEntriesPermissionName(
						objectDefinition.getObjectDefinitionId()),
					groupId, dtoConverterContext.getUriInfo())
			).put(
				"updateBatch",
				ActionUtil.addAction(
					ActionKeys.UPDATE, ObjectEntryResourceImpl.class, null,
					"putObjectEntryBatch", null, objectDefinition.getUserId(),
					_getObjectEntriesPermissionName(
						objectDefinition.getObjectDefinitionId()),
					groupId, dtoConverterContext.getUriInfo())
			).build(),
			facets,
			TransformUtil.transform(
				objectEntryLocalService.getValuesList(
					groupId, companyId, dtoConverterContext.getUserId(),
					objectDefinition.getObjectDefinitionId(), predicate, search,
					start, end,
					OrderByExpressionUtil.getOrderByExpressions(
						objectDefinition.getObjectDefinitionId(),
						objectFieldLocalService, sorts)),
				values -> _getObjectEntry(
					dtoConverterContext, objectDefinition, values)),
			pagination,
			objectEntryLocalService.getValuesListCount(
				groupId, companyId, dtoConverterContext.getUserId(),
				objectDefinition.getObjectDefinitionId(), predicate, search));
	}

	@Override
	public Page<ObjectEntry> getObjectEntries(
			long companyId, ObjectDefinition objectDefinition, String scopeKey,
			Aggregation aggregation, DTOConverterContext dtoConverterContext,
			Filter filter, Pagination pagination, String search, Sort[] sorts)
		throws Exception {

		long groupId = getGroupId(objectDefinition, scopeKey);

		return SearchUtil.search(
			HashMapBuilder.put(
				"create",
				ActionUtil.addAction(
					"ADD_OBJECT_ENTRY", ObjectEntryResourceImpl.class, 0L,
					"postObjectEntry", null, objectDefinition.getUserId(),
					_getObjectEntriesPermissionName(
						objectDefinition.getObjectDefinitionId()),
					groupId, dtoConverterContext.getUriInfo())
			).put(
				"get",
				ActionUtil.addAction(
					ActionKeys.VIEW, ObjectEntryResourceImpl.class, 0L,
					"getObjectEntriesPage", null, objectDefinition.getUserId(),
					_getObjectEntriesPermissionName(
						objectDefinition.getObjectDefinitionId()),
					groupId, dtoConverterContext.getUriInfo())
			).build(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"objectDefinitionId",
						String.valueOf(
							objectDefinition.getObjectDefinitionId())),
					BooleanClauseOccur.MUST);
			},
			filter, objectDefinition.getClassName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setAttribute(
					Field.STATUS, WorkflowConstants.STATUS_ANY);
				searchContext.setAttribute(
					"objectDefinitionId",
					objectDefinition.getObjectDefinitionId());

				UriInfo uriInfo = dtoConverterContext.getUriInfo();

				if (uriInfo != null) {
					MultivaluedMap<String, String> queryParameters =
						uriInfo.getQueryParameters();

					searchContext.setAttribute(
						"searchByObjectView",
						queryParameters.containsKey("searchByObjectView"));
				}

				searchContext.setCompanyId(companyId);
				searchContext.setGroupIds(new long[] {groupId});

				SearchRequestBuilder searchRequestBuilder =
					_searchRequestBuilderFactory.builder(searchContext);

				_processVulcanAggregation(
					_aggregations, _queries, searchRequestBuilder, aggregation);
			},
			sorts,
			document -> getObjectEntry(
				dtoConverterContext, objectDefinition,
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
	}

	@Override
	public Page<ObjectEntry> getObjectEntries(
			long companyId, ObjectDefinition objectDefinition, String scopeKey,
			Aggregation aggregation, DTOConverterContext dtoConverterContext,
			String filterString, Pagination pagination, String search,
			Sort[] sorts)
		throws Exception {

		return getObjectEntries(
			companyId, objectDefinition, scopeKey, aggregation,
			dtoConverterContext,
			_objectDefinitionFilterParser.parse(filterString, objectDefinition),
			pagination, search, sorts);
	}

	@Override
	public ObjectEntry getObjectEntry(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, long objectEntryId)
		throws Exception {

		com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry =
			_objectEntryService.getObjectEntry(objectEntryId);

		_checkObjectEntryObjectDefinitionId(
			objectDefinition, serviceBuilderObjectEntry);

		return _toObjectEntry(
			dtoConverterContext, objectDefinition, serviceBuilderObjectEntry);
	}

	@Override
	public ObjectEntry getObjectEntry(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ObjectDefinition objectDefinition,
			String scopeKey)
		throws Exception {

		com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry =
			_objectEntryService.getObjectEntry(
				externalReferenceCode, companyId,
				getGroupId(objectDefinition, scopeKey));

		_checkObjectEntryObjectDefinitionId(
			objectDefinition, serviceBuilderObjectEntry);

		return _toObjectEntry(
			dtoConverterContext, objectDefinition, serviceBuilderObjectEntry);
	}

	@Override
	public Page<ObjectEntry> getObjectEntryRelatedObjectEntries(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, Long objectEntryId,
			String objectRelationshipName, Pagination pagination)
		throws Exception {

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.getObjectRelationship(
				objectDefinition.getObjectDefinitionId(),
				objectRelationshipName);

		ObjectDefinition relatedObjectDefinition = _getRelatedObjectDefinition(
			objectDefinition, objectRelationship);

		ObjectRelatedModelsProvider objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				relatedObjectDefinition.getClassName(),
				relatedObjectDefinition.getCompanyId(),
				objectRelationship.getType());

		if (objectDefinition.isUnmodifiableSystemObject()) {
			return _getSystemObjectRelatedObjectEntries(
				dtoConverterContext, objectDefinition, objectEntryId,
				objectRelationship, objectRelatedModelsProvider, pagination);
		}

		com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry =
			_objectEntryService.getObjectEntry(objectEntryId);

		return Page.of(
			HashMapBuilder.put(
				"get",
				ActionUtil.addAction(
					ActionKeys.VIEW,
					ObjectEntryRelatedObjectsResourceImpl.class, objectEntryId,
					"getCurrentObjectEntriesObjectRelationshipNamePage", null,
					serviceBuilderObjectEntry.getUserId(),
					_getObjectEntryPermissionName(
						objectDefinition.getObjectDefinitionId()),
					serviceBuilderObjectEntry.getGroupId(),
					dtoConverterContext.getUriInfo())
			).build(),
			_toObjectEntries(
				dtoConverterContext,
				objectRelatedModelsProvider.getRelatedModels(
					serviceBuilderObjectEntry.getGroupId(),
					objectRelationship.getObjectRelationshipId(),
					serviceBuilderObjectEntry.getPrimaryKey(), null,
					_getStartPosition(pagination),
					_getEndPosition(pagination))),
			pagination,
			objectRelatedModelsProvider.getRelatedModelsCount(
				serviceBuilderObjectEntry.getGroupId(),
				objectRelationship.getObjectRelationshipId(),
				serviceBuilderObjectEntry.getPrimaryKey(), null));
	}

	@Override
	public Page<Object> getRelatedSystemObjectEntries(
			ObjectDefinition objectDefinition, Long objectEntryId,
			String objectRelationshipName, Pagination pagination)
		throws Exception {

		ObjectRelationship objectRelationship =
			_objectRelationshipService.getObjectRelationship(
				objectDefinition.getObjectDefinitionId(),
				objectRelationshipName);

		ObjectDefinition relatedObjectDefinition = _getRelatedObjectDefinition(
			objectDefinition, objectRelationship);

		ObjectRelatedModelsProvider objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				relatedObjectDefinition.getClassName(),
				relatedObjectDefinition.getCompanyId(),
				objectRelationship.getType());

		com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry =
			_objectEntryService.getObjectEntry(objectEntryId);

		return Page.of(
			TransformUtil.transform(
				(List<BaseModel<?>>)
					objectRelatedModelsProvider.getRelatedModels(
						serviceBuilderObjectEntry.getGroupId(),
						objectRelationship.getObjectRelationshipId(),
						serviceBuilderObjectEntry.getPrimaryKey(), null,
						_getStartPosition(pagination),
						_getEndPosition(pagination)),
				baseModel -> _toDTO(
					baseModel, serviceBuilderObjectEntry,
					_systemObjectDefinitionManagerRegistry.
						getSystemObjectDefinitionManager(
							relatedObjectDefinition.getName()))),
			pagination,
			objectRelatedModelsProvider.getRelatedModelsCount(
				serviceBuilderObjectEntry.getGroupId(),
				objectRelationship.getObjectRelationshipId(),
				serviceBuilderObjectEntry.getPrimaryKey(), null));
	}

	@Override
	public String getStorageLabel(Locale locale) {
		return language.get(
			locale, ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT);
	}

	@Override
	public String getStorageType() {
		return ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT;
	}

	@Override
	public Object getSystemObjectEntry(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, long primaryKey)
		throws Exception {

		if (!objectDefinition.isUnmodifiableSystemObject()) {
			return null;
		}

		if (FeatureFlagManagerUtil.isEnabled("LPS-183882")) {
			SystemObjectDefinitionManager systemObjectDefinitionManager =
				_systemObjectDefinitionManagerRegistry.
					getSystemObjectDefinitionManager(
						objectDefinition.getName());

			return ObjectEntryDTOConverterUtil.toDTO(
				systemObjectDefinitionManager.
					getBaseModelByExternalReferenceCode(
						systemObjectDefinitionManager.
							getBaseModelExternalReferenceCode(primaryKey),
						objectDefinition.getCompanyId()),
				_dtoConverterRegistry, systemObjectDefinitionManager,
				dtoConverterContext.getUser());
		}

		return objectEntryLocalService.getSystemModelAttributes(
			objectDefinition, primaryKey);
	}

	@Override
	public ObjectEntry partialUpdateObjectEntry(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, long objectEntryId,
			ObjectEntry objectEntry)
		throws Exception {

		ObjectEntry existingObjectEntry = getObjectEntry(
			dtoConverterContext, objectDefinition, objectEntryId);

		if (objectEntry.getDateCreated() != null) {
			existingObjectEntry.setDateCreated(objectEntry.getDateCreated());
		}

		if (objectEntry.getDateModified() != null) {
			existingObjectEntry.setDateModified(objectEntry.getDateModified());
		}

		if (objectEntry.getKeywords() != null) {
			existingObjectEntry.setKeywords(objectEntry.getKeywords());
		}

		if (objectEntry.getProperties() != null) {
			Map<String, Object> properties =
				existingObjectEntry.getProperties();

			properties.putAll(objectEntry.getProperties());

			existingObjectEntry.setProperties(properties);
		}

		if (objectEntry.getStatus() != null) {
			existingObjectEntry.setStatus(objectEntry.getStatus());
		}

		if (objectEntry.getTaxonomyCategoryIds() != null) {
			existingObjectEntry.setTaxonomyCategoryIds(
				objectEntry.getTaxonomyCategoryIds());
		}

		return updateObjectEntry(
			dtoConverterContext, objectDefinition, objectEntryId,
			existingObjectEntry);
	}

	@Override
	public ObjectEntry updateObjectEntry(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, long objectEntryId,
			ObjectEntry objectEntry)
		throws Exception {

		com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry =
			_objectEntryService.getObjectEntry(objectEntryId);

		_checkObjectEntryObjectDefinitionId(
			objectDefinition, serviceBuilderObjectEntry);

		validateReadOnlyObjectFields(
			serviceBuilderObjectEntry.getExternalReferenceCode(),
			objectDefinition, objectEntry);

		serviceBuilderObjectEntry = _objectEntryService.updateObjectEntry(
			objectEntryId,
			_toObjectValues(
				dtoConverterContext.getUserId(), objectDefinition, objectEntry,
				dtoConverterContext.getLocale()),
			_createServiceContext(
				objectEntry, dtoConverterContext.getUserId()));

		return _toObjectEntry(
			dtoConverterContext, objectDefinition,
			_addOrUpdateNestedObjectEntries(
				dtoConverterContext, objectDefinition, objectEntry,
				_getObjectRelationships(objectDefinition, objectEntry),
				serviceBuilderObjectEntry.getPrimaryKey()));
	}

	@Override
	public ObjectEntry updateObjectEntry(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ObjectDefinition objectDefinition,
			ObjectEntry objectEntry, String scopeKey)
		throws Exception {

		validateReadOnlyObjectFields(
			externalReferenceCode, objectDefinition, objectEntry);

		long groupId = getGroupId(objectDefinition, scopeKey);

		ServiceContext serviceContext = _createServiceContext(
			objectEntry, dtoConverterContext.getUserId());

		serviceContext.setCompanyId(companyId);

		com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry =
			_objectEntryService.addOrUpdateObjectEntry(
				externalReferenceCode, groupId,
				objectDefinition.getObjectDefinitionId(),
				_toObjectValues(
					dtoConverterContext.getUserId(), objectDefinition,
					objectEntry, dtoConverterContext.getLocale()),
				serviceContext);

		return _toObjectEntry(
			dtoConverterContext, objectDefinition,
			_addOrUpdateNestedObjectEntries(
				dtoConverterContext, objectDefinition, objectEntry,
				_getObjectRelationships(objectDefinition, objectEntry),
				serviceBuilderObjectEntry.getPrimaryKey()));
	}

	private Map<String, String> _addAction(
			String actionName, String methodName,
			com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry,
			UriInfo uriInfo)
		throws Exception {

		Map<String, String> map = ActionUtil.addAction(
			actionName, ObjectEntryResourceImpl.class,
			serviceBuilderObjectEntry.getObjectEntryId(), methodName, null,
			serviceBuilderObjectEntry.getUserId(),
			_getObjectEntryPermissionName(
				serviceBuilderObjectEntry.getObjectDefinitionId()),
			serviceBuilderObjectEntry.getGroupId(), uriInfo);

		if (map != null) {
			return map;
		}

		return ActionUtil.addAction(
			actionName, ObjectEntryResourceImpl.class,
			serviceBuilderObjectEntry.getObjectEntryId(), methodName, null,
			_objectEntryService.getModelResourcePermission(
				serviceBuilderObjectEntry),
			uriInfo);
	}

	private com.liferay.object.model.ObjectEntry
			_addOrUpdateNestedObjectEntries(
				DTOConverterContext dtoConverterContext,
				ObjectDefinition objectDefinition, ObjectEntry objectEntry,
				Map<String, ObjectRelationship> objectRelationships,
				long primaryKey)
		throws Exception {

		Map<String, Object> properties = objectEntry.getProperties();

		for (Map.Entry<String, ObjectRelationship> entry :
				objectRelationships.entrySet()) {

			ObjectRelationship objectRelationship = objectRelationships.get(
				entry.getKey());

			ObjectDefinition relatedObjectDefinition =
				_getRelatedObjectDefinition(
					objectDefinition, objectRelationship);

			ObjectRelationshipElementsParser objectRelationshipElementsParser =
				_objectRelationshipElementsParserRegistry.
					getObjectRelationshipElementsParser(
						relatedObjectDefinition.getClassName(),
						relatedObjectDefinition.getCompanyId(),
						objectRelationship.getType());

			List<?> nestedObjectEntries =
				objectRelationshipElementsParser.parse(
					objectRelationship, properties.get(entry.getKey()));

			List<String> nestedExternalReferenceCodes = new ArrayList<>();

			if (relatedObjectDefinition.isUnmodifiableSystemObject()) {
				SystemObjectDefinitionManager systemObjectDefinitionManager =
					_systemObjectDefinitionManagerRegistry.
						getSystemObjectDefinitionManager(
							relatedObjectDefinition.getName());

				for (Object item : nestedObjectEntries) {
					Map<String, Object> nestedObjectEntry =
						(Map<String, Object>)item;

					long nestedObjectEntryId =
						systemObjectDefinitionManager.upsertBaseModel(
							String.valueOf(
								nestedObjectEntry.get("externalReferenceCode")),
							relatedObjectDefinition.getCompanyId(),
							dtoConverterContext.getUser(), nestedObjectEntry);

					_relateNestedObjectEntry(
						objectDefinition, objectRelationship, primaryKey,
						nestedObjectEntryId);

					nestedExternalReferenceCodes.add(
						systemObjectDefinitionManager.
							getBaseModelExternalReferenceCode(
								nestedObjectEntryId));
				}
			}
			else {
				ObjectEntryManager objectEntryManager =
					_objectEntryManagerRegistry.getObjectEntryManager(
						relatedObjectDefinition.getStorageType());

				boolean manyToOneObjectRelationship =
					_isManyToOneObjectRelationship(
						objectDefinition, objectRelationship,
						relatedObjectDefinition);

				for (Object item : nestedObjectEntries) {
					ObjectEntry nestedObjectEntry = (ObjectEntry)item;

					if (manyToOneObjectRelationship) {
						Map<String, Object> nestedObjectEntryProperties =
							nestedObjectEntry.getProperties();

						String objectRelationshipName = StringBundler.concat(
							"r_", objectRelationship.getName(), "_",
							objectDefinition.getPKObjectFieldName());

						nestedObjectEntryProperties.put(
							objectRelationshipName, primaryKey);
					}

					nestedObjectEntry = objectEntryManager.updateObjectEntry(
						objectDefinition.getCompanyId(), dtoConverterContext,
						nestedObjectEntry.getExternalReferenceCode(),
						relatedObjectDefinition, nestedObjectEntry,
						relatedObjectDefinition.getScope());

					if (!manyToOneObjectRelationship) {
						_relateNestedObjectEntry(
							objectDefinition, objectRelationship, primaryKey,
							nestedObjectEntry.getId());
					}

					nestedExternalReferenceCodes.add(
						nestedObjectEntry.getExternalReferenceCode());
				}
			}

			long[] toDisassociatePrimaryKeys =
				TransformUtil.transformToLongArray(
					_getRelatedModels(
						objectDefinition, objectRelationship, primaryKey,
						relatedObjectDefinition),
					relatedModel -> {
						ExternalReferenceCodeModel externalReferenceCodeModel =
							(ExternalReferenceCodeModel)relatedModel;

						if (nestedExternalReferenceCodes.contains(
								externalReferenceCodeModel.
									getExternalReferenceCode())) {

							return null;
						}

						return relatedModel.getPrimaryKeyObj();
					});

			if (toDisassociatePrimaryKeys.length > 0) {
				_disassociateRelatedModels(
					objectDefinition, objectRelationship, primaryKey,
					toDisassociatePrimaryKeys, relatedObjectDefinition,
					dtoConverterContext.getUserId());
			}

			if (properties.containsKey(entry.getKey())) {
				NestedFieldsSupplier.addFieldName(entry.getKey());
			}
		}

		return objectEntryLocalService.getObjectEntry(primaryKey);
	}

	private void _checkObjectEntryObjectDefinitionId(
			ObjectDefinition objectDefinition,
			com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry)
		throws Exception {

		if (objectDefinition.getObjectDefinitionId() !=
				serviceBuilderObjectEntry.getObjectDefinitionId()) {

			throw new NoSuchObjectEntryException();
		}
	}

	private ServiceContext _createServiceContext(
		ObjectEntry objectEntry, long userId) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		if (Validator.isNotNull(objectEntry.getTaxonomyCategoryIds())) {
			serviceContext.setAssetCategoryIds(
				ArrayUtil.toArray(objectEntry.getTaxonomyCategoryIds()));
			serviceContext.setAssetTagNames(objectEntry.getKeywords());
		}

		Map<String, Object> properties = objectEntry.getProperties();

		if (properties.get("categoryIds") != null) {
			serviceContext.setAssetCategoryIds(
				ListUtil.toLongArray(
					(List<String>)properties.get("categoryIds"),
					Long::parseLong));
		}

		if (Validator.isNotNull(objectEntry.getKeywords())) {
			serviceContext.setAssetTagNames(objectEntry.getKeywords());
		}

		if (properties.get("tagNames") != null) {
			serviceContext.setAssetTagNames(
				ArrayUtil.toStringArray(
					(List<String>)properties.get("tagNames")));
		}

		serviceContext.setUserId(userId);

		if (_isObjectEntryDraft(objectEntry.getStatus())) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		return serviceContext;
	}

	private void _disassociateRelatedModels(
			ObjectDefinition objectDefinition,
			ObjectRelationship objectRelationship, long primaryKey1,
			long[] primaryKeys2, ObjectDefinition relatedObjectDefinition,
			long userId)
		throws Exception {

		ObjectRelatedModelsProvider<?> objectRelatedModelsProvider = null;

		if (_isManyToOneObjectRelationship(
				relatedObjectDefinition, objectRelationship,
				objectDefinition)) {

			objectRelatedModelsProvider =
				_objectRelatedModelsProviderRegistry.
					getObjectRelatedModelsProvider(
						objectDefinition.getClassName(),
						objectDefinition.getCompanyId(),
						objectRelationship.getType());

			objectRelatedModelsProvider.disassociateRelatedModels(
				userId, objectRelationship.getObjectRelationshipId(),
				primaryKeys2[0], primaryKey1);
		}
		else {
			objectRelatedModelsProvider =
				_objectRelatedModelsProviderRegistry.
					getObjectRelatedModelsProvider(
						relatedObjectDefinition.getClassName(),
						relatedObjectDefinition.getCompanyId(),
						objectRelationship.getType());

			if ((objectRelationship.getObjectDefinitionId1() !=
					objectDefinition.getObjectDefinitionId()) &&
				Objects.equals(
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY,
					objectRelationship.getType())) {

				objectRelationship =
					_objectRelationshipLocalService.getObjectRelationship(
						objectDefinition.getObjectDefinitionId(),
						objectRelationship.getName());
			}

			for (long primaryKey2 : primaryKeys2) {
				objectRelatedModelsProvider.disassociateRelatedModels(
					userId, objectRelationship.getObjectRelationshipId(),
					primaryKey1, primaryKey2);
			}
		}
	}

	private void _executeObjectAction(
			DTOConverterContext dtoConverterContext, String objectActionName,
			ObjectDefinition objectDefinition,
			com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry)
		throws Exception {

		_objectEntryService.checkModelResourcePermission(
			objectDefinition.getObjectDefinitionId(),
			serviceBuilderObjectEntry.getObjectEntryId(), objectActionName);

		_objectActionEngine.executeObjectAction(
			objectActionName, ObjectActionTriggerConstants.KEY_STANDALONE,
			objectDefinition.getObjectDefinitionId(),
			JSONUtil.put(
				"classPK", serviceBuilderObjectEntry.getObjectEntryId()
			).put(
				"objectEntry",
				HashMapBuilder.putAll(
					serviceBuilderObjectEntry.getModelAttributes()
				).put(
					"values", serviceBuilderObjectEntry.getValues()
				).build()
			).put(
				"objectEntryDTO" + objectDefinition.getShortName(),
				() -> {
					dtoConverterContext.setAttribute(
						"addActions", Boolean.FALSE);

					JSONObject jsonObject = _jsonFactory.createJSONObject(
						_jsonFactory.looseSerializeDeep(
							_toObjectEntry(
								dtoConverterContext, objectDefinition,
								serviceBuilderObjectEntry)));

					return jsonObject.toMap();
				}
			),
			dtoConverterContext.getUserId());
	}

	private int _getEndPosition(Pagination pagination) {
		if (pagination != null) {
			return pagination.getEndPosition();
		}

		return QueryUtil.ALL_POS;
	}

	private BaseModel<ExternalReferenceCodeModel> _getManyToOneRelatedModel(
			ObjectRelationship objectRelationship, long primaryKey,
			ObjectDefinition relatedObjectDefinition)
		throws Exception {

		if (relatedObjectDefinition.isUnmodifiableSystemObject()) {
			ManyToOneObjectRelatedModelsProvider
				manyToOneObjectRelatedModelsProvider =
					(ManyToOneObjectRelatedModelsProvider)
						_objectRelatedModelsProviderRegistry.
							getObjectRelatedModelsProvider(
								relatedObjectDefinition.getClassName(),
								relatedObjectDefinition.getCompanyId(),
								objectRelationship.getType());

			return manyToOneObjectRelatedModelsProvider.fetchRelatedModel(
				relatedObjectDefinition.getCompanyId(),
				objectRelationship.getObjectRelationshipId(), primaryKey);
		}

		ManyToOneObjectRelatedModelsProvider objectRelatedModelsProvider =
			(ManyToOneObjectRelatedModelsProvider)
				_objectRelatedModelsProviderRegistry.
					getObjectRelatedModelsProvider(
						relatedObjectDefinition.getClassName(),
						relatedObjectDefinition.getCompanyId(),
						objectRelationship.getType());

		return objectRelatedModelsProvider.fetchRelatedModel(
			GroupThreadLocal.getGroupId(),
			objectRelationship.getObjectRelationshipId(), primaryKey);
	}

	private String _getObjectEntriesPermissionName(long objectDefinitionId) {
		return ObjectConstants.RESOURCE_NAME + "#" + objectDefinitionId;
	}

	private ObjectEntry _getObjectEntry(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, Map<String, Serializable> values)
		throws Exception {

		com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry =
			_objectEntryService.getObjectEntry(
				GetterUtil.getLong(
					values.get(objectDefinition.getPKObjectFieldName())));

		_checkObjectEntryObjectDefinitionId(
			objectDefinition, serviceBuilderObjectEntry);

		return _toObjectEntry(
			dtoConverterContext, objectDefinition, serviceBuilderObjectEntry);
	}

	private String _getObjectEntryPermissionName(long objectDefinitionId) {
		return ObjectDefinition.class.getName() + "#" + objectDefinitionId;
	}

	private Map<String, ObjectRelationship> _getObjectRelationships(
			ObjectDefinition objectDefinition, ObjectEntry objectEntry)
		throws Exception {

		Map<String, ObjectRelationship> objectRelationships = new HashMap<>();

		Map<String, Object> properties = objectEntry.getProperties();

		for (String key : properties.keySet()) {
			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.
					fetchObjectRelationshipByObjectDefinitionId(
						objectDefinition.getObjectDefinitionId(), key);

			if (objectRelationship != null) {
				objectRelationships.put(key, objectRelationship);
			}
		}

		return objectRelationships;
	}

	private List<? extends BaseModel<?>> _getRelatedModels(
			ObjectDefinition objectDefinition,
			ObjectRelationship objectRelationship, long primaryKey,
			ObjectDefinition relatedObjectDefinition)
		throws Exception {

		if (_isManyToOneObjectRelationship(
				relatedObjectDefinition, objectRelationship,
				objectDefinition)) {

			BaseModel<?> baseModel = _getManyToOneRelatedModel(
				objectRelationship, primaryKey, relatedObjectDefinition);

			if (baseModel == null) {
				return new ArrayList<>();
			}

			return Collections.singletonList(baseModel);
		}

		ObjectRelatedModelsProvider<?> objectRelatedModelsProvider =
			_objectRelatedModelsProviderRegistry.getObjectRelatedModelsProvider(
				relatedObjectDefinition.getClassName(),
				relatedObjectDefinition.getCompanyId(),
				objectRelationship.getType());

		if ((objectRelationship.getObjectDefinitionId1() !=
				objectDefinition.getObjectDefinitionId()) &&
			Objects.equals(
				ObjectRelationshipConstants.TYPE_MANY_TO_MANY,
				objectRelationship.getType())) {

			objectRelationship =
				_objectRelationshipLocalService.getObjectRelationship(
					objectDefinition.getObjectDefinitionId(),
					objectRelationship.getName());
		}

		return objectRelatedModelsProvider.getRelatedModels(
			GroupThreadLocal.getGroupId(),
			objectRelationship.getObjectRelationshipId(), primaryKey, null, -1,
			-1);
	}

	private ObjectDefinition _getRelatedObjectDefinition(
			ObjectDefinition objectDefinition,
			ObjectRelationship objectRelationship)
		throws Exception {

		ObjectDefinition relatedObjectDefinition =
			ObjectRelationshipUtil.getRelatedObjectDefinition(
				objectDefinition, objectRelationship);

		if (!relatedObjectDefinition.isActive()) {
			throw new BadRequestException(
				"Object definition " +
					relatedObjectDefinition.getObjectDefinitionId() +
						" is inactive");
		}

		return relatedObjectDefinition;
	}

	private int _getStartPosition(Pagination pagination) {
		if (pagination != null) {
			return pagination.getStartPosition();
		}

		return QueryUtil.ALL_POS;
	}

	private Page<ObjectEntry> _getSystemObjectRelatedObjectEntries(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, long objectEntryId,
			ObjectRelationship objectRelationship,
			ObjectRelatedModelsProvider objectRelatedModelsProvider,
			Pagination pagination)
		throws Exception {

		long groupId = GroupThreadLocal.getGroupId();

		SystemObjectDefinitionManager systemObjectDefinitionManager =
			_systemObjectDefinitionManagerRegistry.
				getSystemObjectDefinitionManager(objectDefinition.getName());

		PersistedModelLocalService persistedModelLocalService =
			_persistedModelLocalServiceRegistry.getPersistedModelLocalService(
				systemObjectDefinitionManager.getModelClassName());

		PersistedModel persistedModel =
			persistedModelLocalService.getPersistedModel(objectEntryId);

		if (Objects.equals(
				systemObjectDefinitionManager.getScope(),
				ObjectDefinitionConstants.SCOPE_SITE) &&
			(persistedModel instanceof GroupedModel)) {

			GroupedModel groupedModel = (GroupedModel)persistedModel;

			groupId = groupedModel.getGroupId();
		}

		return Page.of(
			Collections.emptyMap(),
			_toObjectEntries(
				dtoConverterContext,
				objectRelatedModelsProvider.getRelatedModels(
					groupId, objectRelationship.getObjectRelationshipId(),
					objectEntryId, null, _getStartPosition(pagination),
					_getEndPosition(pagination))),
			pagination,
			objectRelatedModelsProvider.getRelatedModelsCount(
				groupId, objectRelationship.getObjectRelationshipId(),
				objectEntryId, null));
	}

	private boolean _hasRelatedObjectEntries(
			ObjectDefinition objectDefinition,
			com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry)
		throws PortalException {

		for (ObjectRelationship objectRelationship :
				_objectRelationshipLocalService.getObjectRelationships(
					objectDefinition.getObjectDefinitionId(),
					ObjectRelationshipConstants.DELETION_TYPE_PREVENT, false)) {

			ObjectDefinition objectDefinition2 =
				_objectDefinitionLocalService.getObjectDefinition(
					objectRelationship.getObjectDefinitionId2());

			if (!objectDefinition2.isActive()) {
				continue;
			}

			ObjectRelatedModelsProvider objectRelatedModelsProvider =
				_objectRelatedModelsProviderRegistry.
					getObjectRelatedModelsProvider(
						objectDefinition2.getClassName(),
						objectDefinition2.getCompanyId(),
						objectRelationship.getType());

			int count = 0;

			try {
				ObjectEntryThreadLocal.setSkipObjectEntryResourcePermission(
					true);

				count = objectRelatedModelsProvider.getRelatedModelsCount(
					serviceBuilderObjectEntry.getGroupId(),
					objectRelationship.getObjectRelationshipId(),
					serviceBuilderObjectEntry.getPrimaryKey(), null);
			}
			catch (Exception exception) {
				_log.error(exception);
			}
			finally {
				ObjectEntryThreadLocal.setSkipObjectEntryResourcePermission(
					false);
			}

			if (count > 0) {
				return true;
			}
		}

		return false;
	}

	private boolean _isManyToOneObjectRelationship(
		ObjectDefinition objectDefinition,
		ObjectRelationship objectRelationship,
		ObjectDefinition relatedObjectDefinition) {

		if (Objects.equals(
				objectRelationship.getType(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY) &&
			(objectRelationship.getObjectDefinitionId1() ==
				objectDefinition.getObjectDefinitionId()) &&
			(objectRelationship.getObjectDefinitionId2() ==
				relatedObjectDefinition.getObjectDefinitionId())) {

			return true;
		}

		return false;
	}

	private boolean _isObjectEntryDraft(Status status) {
		if ((status != null) &&
			(status.getCode() == WorkflowConstants.STATUS_DRAFT)) {

			return true;
		}

		return false;
	}

	private void _processVulcanAggregation(
		Aggregations aggregations, Queries queries,
		SearchRequestBuilder searchRequestBuilder,
		Aggregation vulcanAggregation) {

		if (vulcanAggregation == null) {
			return;
		}

		Map<String, String> aggregationTerms =
			vulcanAggregation.getAggregationTerms();

		for (Map.Entry<String, String> entry : aggregationTerms.entrySet()) {
			String value = entry.getValue();

			if (!value.startsWith("nestedFieldArray")) {
				continue;
			}

			NestedAggregation nestedAggregation = aggregations.nested(
				entry.getKey(), "nestedFieldArray");

			String[] valueParts = value.split(StringPool.POUND);

			FilterAggregation filterAggregation = aggregations.filter(
				"filterAggregation",
				queries.term("nestedFieldArray.fieldName", valueParts[1]));

			filterAggregation.addChildAggregation(
				aggregations.terms(entry.getKey(), valueParts[0]));

			nestedAggregation.addChildAggregation(filterAggregation);

			searchRequestBuilder.addAggregation(nestedAggregation);
		}
	}

	private void _relateNestedObjectEntry(
			ObjectDefinition objectDefinition,
			ObjectRelationship objectRelationship, long primaryKey,
			long relatedPrimaryKey)
		throws Exception {

		long primaryKey1 = relatedPrimaryKey;
		long primaryKey2 = primaryKey;

		if (objectDefinition.getObjectDefinitionId() ==
				objectRelationship.getObjectDefinitionId1()) {

			primaryKey1 = primaryKey;
			primaryKey2 = relatedPrimaryKey;
		}

		_objectRelationshipService.addObjectRelationshipMappingTableValues(
			objectRelationship.getObjectRelationshipId(), primaryKey1,
			primaryKey2, new ServiceContext());
	}

	private Date _toDate(Locale locale, String valueString) {
		if (Validator.isNull(valueString)) {
			return null;
		}

		try {
			return DateUtil.parseDate(
				"yyyy-MM-dd'T'HH:mm:ss'Z'", valueString, locale);
		}
		catch (ParseException parseException1) {
			if (_log.isDebugEnabled()) {
				_log.debug(parseException1);
			}

			try {
				return DateUtil.parseDate("yyyy-MM-dd", valueString, locale);
			}
			catch (ParseException parseException2) {
				throw new BadRequestException(
					"Unable to parse date that does not conform to ISO-8601",
					parseException2);
			}
		}
	}

	private Object _toDTO(
			BaseModel<?> baseModel,
			com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry,
			SystemObjectDefinitionManager systemObjectDefinitionManager)
		throws Exception {

		return ObjectEntryDTOConverterUtil.toDTO(
			baseModel, _dtoConverterRegistry, systemObjectDefinitionManager,
			_userLocalService.getUser(serviceBuilderObjectEntry.getUserId()));
	}

	private List<ObjectEntry> _toObjectEntries(
		DTOConverterContext dtoConverterContext,
		List<com.liferay.object.model.ObjectEntry>
			serviceBuilderObjectEntries) {

		return TransformUtil.transform(
			serviceBuilderObjectEntries,
			serviceBuilderObjectEntry -> _toObjectEntry(
				dtoConverterContext,
				_objectDefinitionLocalService.getObjectDefinition(
					serviceBuilderObjectEntry.getObjectDefinitionId()),
				serviceBuilderObjectEntry));
	}

	private ObjectEntry _toObjectEntry(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition,
			com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry)
		throws Exception {

		Map<String, Map<String, String>> actions =
			dtoConverterContext.getActions();

		if (GetterUtil.getBoolean(
				dtoConverterContext.getAttribute("addActions"), true)) {

			if (actions == null) {
				actions = Collections.emptyMap();
			}

			actions = HashMapBuilder.create(
				actions
			).<String, Map<String, String>>put(
				"delete",
				() -> {
					if (_hasRelatedObjectEntries(
							objectDefinition, serviceBuilderObjectEntry)) {

						return null;
					}

					return _addAction(
						ActionKeys.DELETE, "deleteObjectEntry",
						serviceBuilderObjectEntry,
						dtoConverterContext.getUriInfo());
				}
			).put(
				"get",
				_addAction(
					ActionKeys.VIEW, "getObjectEntry",
					serviceBuilderObjectEntry, dtoConverterContext.getUriInfo())
			).put(
				"permissions",
				_addAction(
					ActionKeys.PERMISSIONS, "getObjectEntryPermissionsPage",
					serviceBuilderObjectEntry, dtoConverterContext.getUriInfo())
			).put(
				"replace",
				_addAction(
					ActionKeys.UPDATE, "putObjectEntry",
					serviceBuilderObjectEntry, dtoConverterContext.getUriInfo())
			).put(
				"update",
				_addAction(
					ActionKeys.UPDATE, "patchObjectEntry",
					serviceBuilderObjectEntry, dtoConverterContext.getUriInfo())
			).build();

			for (ObjectAction objectAction :
					_objectActionLocalService.getObjectActions(
						objectDefinition.getObjectDefinitionId(),
						ObjectActionTriggerConstants.KEY_STANDALONE)) {

				actions.put(
					objectAction.getName(),
					_addAction(
						objectAction.getName(),
						"putByExternalReferenceCodeObjectEntryExternal" +
							"ReferenceCodeObjectActionObjectActionName",
						serviceBuilderObjectEntry,
						dtoConverterContext.getUriInfo()));
			}
		}

		DefaultDTOConverterContext defaultDTOConverterContext =
			new DefaultDTOConverterContext(
				dtoConverterContext.isAcceptAllLanguages(), actions,
				dtoConverterContext.getDTOConverterRegistry(),
				dtoConverterContext.getHttpServletRequest(),
				serviceBuilderObjectEntry.getObjectEntryId(),
				dtoConverterContext.getLocale(),
				dtoConverterContext.getUriInfo(),
				dtoConverterContext.getUser());

		defaultDTOConverterContext.setAttribute(
			"objectDefinition", objectDefinition);

		return _objectEntryDTOConverter.toDTO(
			defaultDTOConverterContext, serviceBuilderObjectEntry);
	}

	private Map<String, Serializable> _toObjectValues(
			long userId, ObjectDefinition objectDefinition,
			ObjectEntry objectEntry, Locale locale)
		throws Exception {

		Map<String, Serializable> values = new HashMap<>();

		for (ObjectField objectField :
				objectFieldLocalService.getObjectFields(
					objectDefinition.getObjectDefinitionId())) {

			Object value = ObjectEntryValuesUtil.getValue(
				_objectDefinitionLocalService, objectEntryLocalService,
				objectField, _objectFieldBusinessTypeRegistry, userId,
				objectEntry.getProperties());

			if (Objects.equals(
					objectField.getName(), "externalReferenceCode") &&
				Validator.isNull(value) &&
				Validator.isNotNull(objectEntry.getExternalReferenceCode())) {

				values.put(
					objectField.getName(),
					(Serializable)objectEntry.getExternalReferenceCode());

				continue;
			}

			if (objectField.isLocalized()) {
				Map<String, Object> properties = objectEntry.getProperties();

				value = properties.get(objectField.getI18nObjectFieldName());

				if (value == null) {
					continue;
				}

				values.put(
					objectField.getI18nObjectFieldName(), (Serializable)value);

				continue;
			}

			if ((value == null) &&
				(!objectField.isRequired() ||
				 _isObjectEntryDraft(objectEntry.getStatus()))) {

				continue;
			}

			if (Objects.equals(
					objectField.getDBType(),
					ObjectFieldConstants.DB_TYPE_DATE)) {

				values.put(
					objectField.getName(),
					_toDate(locale, String.valueOf(value)));
			}
			else if (objectField.getListTypeDefinitionId() != 0) {
				if (value instanceof ListEntry) {
					ListEntry listEntry = (ListEntry)value;

					values.put(objectField.getName(), listEntry.getKey());
				}
				else if (value instanceof Map) {
					Map<String, String> map = (HashMap<String, String>)value;

					values.put(objectField.getName(), map.get("key"));
				}
				else {
					values.put(objectField.getName(), (Serializable)value);
				}
			}
			else {
				values.put(objectField.getName(), (Serializable)value);
			}
		}

		return values;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultObjectEntryManagerImpl.class);

	@Reference
	private Aggregations _aggregations;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference(
		target = "(filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + ")"
	)
	private FilterFactory<Predicate> _filterFactory;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectActionEngine _objectActionEngine;

	@Reference
	private ObjectActionLocalService _objectActionLocalService;

	@Reference
	private ObjectDefinitionFilterParser _objectDefinitionFilterParser;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference(
		target = "(component.name=com.liferay.object.rest.internal.dto.v1_0.converter.ObjectEntryDTOConverter)"
	)
	private DTOConverter<com.liferay.object.model.ObjectEntry, ObjectEntry>
		_objectEntryDTOConverter;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Reference
	private ObjectEntryService _objectEntryService;

	@Reference
	private ObjectFieldBusinessTypeRegistry _objectFieldBusinessTypeRegistry;

	@Reference
	private ObjectRelatedModelsProviderRegistry
		_objectRelatedModelsProviderRegistry;

	@Reference
	private ObjectRelationshipElementsParserRegistry
		_objectRelationshipElementsParserRegistry;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private ObjectRelationshipService _objectRelationshipService;

	@Reference
	private PersistedModelLocalServiceRegistry
		_persistedModelLocalServiceRegistry;

	@Reference
	private Queries _queries;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private SystemObjectDefinitionManagerRegistry
		_systemObjectDefinitionManagerRegistry;

	@Reference
	private UserLocalService _userLocalService;

}