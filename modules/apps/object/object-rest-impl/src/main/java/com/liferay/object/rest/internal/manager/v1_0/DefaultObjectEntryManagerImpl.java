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

package com.liferay.object.rest.internal.manager.v1_0;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.object.constants.ObjectConstants;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.exception.NoSuchObjectEntryException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.internal.dto.v1_0.converter.ObjectEntryDTOConverter;
import com.liferay.object.rest.internal.odata.entity.v1_0.ObjectEntryEntityModel;
import com.liferay.object.rest.internal.petra.sql.dsl.expression.PredicateUtil;
import com.liferay.object.rest.internal.resource.v1_0.ObjectEntryResourceImpl;
import com.liferay.object.rest.manager.v1_0.BaseObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.service.ObjectRelationshipService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.Filter;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.aggregation.bucket.NestedAggregation;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.aggregation.Facet;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.io.Serializable;

import java.text.ParseException;

import java.util.ArrayList;
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
	immediate = true,
	property = "object.entry.manager.storage.type=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
	service = ObjectEntryManager.class
)
public class DefaultObjectEntryManagerImpl
	extends BaseObjectEntryManager implements ObjectEntryManager {

	@Override
	public ObjectEntry addObjectEntry(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, ObjectEntry objectEntry,
			String scopeKey)
		throws Exception {

		return _toObjectEntry(
			dtoConverterContext, objectDefinition,
			_objectEntryService.addObjectEntry(
				getGroupId(objectDefinition, scopeKey),
				objectDefinition.getObjectDefinitionId(),
				_toObjectValues(
					objectDefinition.getObjectDefinitionId(),
					objectEntry.getProperties(),
					dtoConverterContext.getLocale()),
				_createServiceContext(
					objectEntry.getProperties(),
					dtoConverterContext.getUserId())));
	}

	@Override
	public ObjectEntry addObjectRelationshipMappingTableValues(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, String objectRelationshipName,
			long primaryKey1, long primaryKey2)
		throws Exception {

		ObjectRelationship objectRelationship =
			_objectRelationshipService.getObjectRelationship(
				objectDefinition.getObjectDefinitionId(),
				objectRelationshipName);

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
	public ObjectEntry addOrUpdateObjectEntry(
			long companyId, DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ObjectDefinition objectDefinition,
			ObjectEntry objectEntry, String scopeKey)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);

		return _toObjectEntry(
			dtoConverterContext, objectDefinition,
			_objectEntryService.addOrUpdateObjectEntry(
				externalReferenceCode, getGroupId(objectDefinition, scopeKey),
				objectDefinition.getObjectDefinitionId(),
				_toObjectValues(
					objectDefinition.getObjectDefinitionId(),
					objectEntry.getProperties(),
					dtoConverterContext.getLocale()),
				serviceContext));
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
	public void deleteObjectEntry(
			String externalReferenceCode, long companyId,
			ObjectDefinition objectDefinition, String scopeKey)
		throws Exception {

		com.liferay.object.model.ObjectEntry objectEntry =
			_objectEntryService.getObjectEntry(
				externalReferenceCode, companyId,
				getGroupId(objectDefinition, scopeKey));

		_checkObjectEntryObjectDefinitionId(objectDefinition, objectEntry);

		_objectEntryService.deleteObjectEntry(objectEntry.getObjectEntryId());
	}

	@Override
	public ObjectEntry fetchObjectEntry(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, long objectEntryId)
		throws Exception {

		com.liferay.object.model.ObjectEntry objectEntry =
			_objectEntryService.fetchObjectEntry(objectEntryId);

		if (objectEntry != null) {
			return _toObjectEntry(
				dtoConverterContext, objectDefinition, objectEntry);
		}

		return null;
	}

	@Override
	public Page<ObjectEntry> getObjectEntries(
			long companyId, ObjectDefinition objectDefinition, String scopeKey,
			Aggregation aggregation, DTOConverterContext dtoConverterContext,
			com.liferay.portal.kernel.search.filter.Filter filter,
			Pagination pagination, String search, Sort[] sorts)
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
			Pagination pagination, Predicate predicate, String search,
			Sort[] sorts)
		throws Exception {

		long groupId = getGroupId(objectDefinition, scopeKey);

		long[] accountEntryIds = null;

		if (objectDefinition.isAccountEntryRestricted()) {
			List<AccountEntry> accountEntries =
				_accountEntryLocalService.getUserAccountEntries(
					dtoConverterContext.getUserId(),
					AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT, null,
					new String[] {
						AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
						AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON
					},
					WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

			accountEntryIds = ListUtil.toLongArray(
				accountEntries, AccountEntry::getAccountEntryId);
		}

		List<Facet> facets = new ArrayList<>();

		if ((aggregation != null) &&
			(aggregation.getAggregationTerms() != null)) {

			Map<String, String> aggregationTerms =
				aggregation.getAggregationTerms();

			for (Map.Entry<String, String> entry1 :
					aggregationTerms.entrySet()) {

				List<Facet.FacetValue> facetValues = new ArrayList<>();

				Map<Object, Long> aggregationCounts =
					_objectEntryLocalService.getAggregationCounts(
						objectDefinition.getObjectDefinitionId(),
						entry1.getKey(), predicate,
						pagination.getStartPosition(),
						pagination.getEndPosition());

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
				"get",
				ActionUtil.addAction(
					ActionKeys.VIEW, ObjectEntryResourceImpl.class, 0L,
					"getObjectEntriesPage", null, objectDefinition.getUserId(),
					_getObjectEntriesPermissionName(
						objectDefinition.getObjectDefinitionId()),
					groupId, dtoConverterContext.getUriInfo())
			).build(),
			facets,
			TransformUtil.transform(
				_objectEntryLocalService.getValuesList(
					objectDefinition.getObjectDefinitionId(), groupId,
					accountEntryIds, predicate, search,
					pagination.getStartPosition(), pagination.getEndPosition()),
				values -> getObjectEntry(
					dtoConverterContext, objectDefinition,
					GetterUtil.getLong(
						values.get(objectDefinition.getPKObjectFieldName())))),
			pagination,
			_objectEntryLocalService.getValuesListCount(
				objectDefinition.getObjectDefinitionId(), groupId,
				accountEntryIds, predicate, search));
	}

	@Override
	public Page<ObjectEntry> getObjectEntries(
			long companyId, ObjectDefinition objectDefinition, String scopeKey,
			Aggregation aggregation, DTOConverterContext dtoConverterContext,
			String filterString, Pagination pagination, String search,
			Sort[] sorts)
		throws Exception {

		if (GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-153768"))) {
			return getObjectEntries(
				companyId, objectDefinition, scopeKey, aggregation,
				dtoConverterContext, pagination,
				PredicateUtil.toPredicate(
					_filterParserProvider, filterString,
					objectDefinition.getObjectDefinitionId(),
					_objectFieldLocalService),
				search, sorts);
		}

		return getObjectEntries(
			companyId, objectDefinition, scopeKey, aggregation,
			dtoConverterContext,
			_toFilter(
				filterString, dtoConverterContext.getLocale(),
				objectDefinition.getObjectDefinitionId()),
			pagination, search, sorts);
	}

	@Override
	public ObjectEntry getObjectEntry(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, long objectEntryId)
		throws Exception {

		com.liferay.object.model.ObjectEntry objectEntry =
			_objectEntryService.getObjectEntry(objectEntryId);

		_checkObjectEntryObjectDefinitionId(objectDefinition, objectEntry);

		return _toObjectEntry(
			dtoConverterContext, objectDefinition, objectEntry);
	}

	@Override
	public ObjectEntry getObjectEntry(
			DTOConverterContext dtoConverterContext,
			String externalReferenceCode, long companyId,
			ObjectDefinition objectDefinition, String scopeKey)
		throws Exception {

		com.liferay.object.model.ObjectEntry objectEntry =
			_objectEntryService.getObjectEntry(
				externalReferenceCode, companyId,
				getGroupId(objectDefinition, scopeKey));

		_checkObjectEntryObjectDefinitionId(objectDefinition, objectEntry);

		return _toObjectEntry(
			dtoConverterContext, objectDefinition, objectEntry);
	}

	@Override
	public Page<ObjectEntry> getObjectEntryRelatedObjectEntries(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, Long objectEntryId,
			String objectRelationshipName, Pagination pagination)
		throws Exception {

		com.liferay.object.model.ObjectEntry objectEntry =
			_objectEntryService.getObjectEntry(objectEntryId);

		List<ObjectEntry> objectEntries = new ArrayList<>();

		ObjectRelationship objectRelationship =
			_objectRelationshipService.getObjectRelationship(
				objectDefinition.getObjectDefinitionId(),
				objectRelationshipName);

		if (Objects.equals(
				objectRelationship.getType(),
				ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

			objectEntries = _toObjectEntries(
				dtoConverterContext,
				_objectEntryLocalService.getOneToManyRelatedObjectEntries(
					objectEntry.getGroupId(),
					objectRelationship.getObjectRelationshipId(),
					objectEntry.getObjectEntryId(),
					pagination.getStartPosition(),
					pagination.getEndPosition()));
		}
		else if (Objects.equals(
					objectRelationship.getType(),
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

			boolean reverse = objectRelationship.isReverse();

			if (reverse) {
				objectRelationship =
					_objectRelationshipLocalService.
						fetchReverseObjectRelationship(
							objectRelationship, false);
			}

			objectEntries = _toObjectEntries(
				dtoConverterContext,
				_objectEntryLocalService.getManyToManyRelatedObjectEntries(
					objectEntry.getGroupId(),
					objectRelationship.getObjectRelationshipId(),
					objectEntry.getObjectEntryId(), reverse,
					pagination.getStartPosition(),
					pagination.getEndPosition()));
		}

		return Page.of(
			HashMapBuilder.put(
				"get",
				ActionUtil.addAction(
					ActionKeys.VIEW, ObjectEntryResourceImpl.class,
					objectEntry.getObjectEntryId(),
					"getCurrentObjectEntriesObjectRelationshipNamePage", null,
					objectEntry.getUserId(),
					_getObjectEntryPermissionName(
						objectEntry.getObjectDefinitionId()),
					objectEntry.getGroupId(), dtoConverterContext.getUriInfo())
			).build(),
			objectEntries);
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

		return _toObjectEntry(
			dtoConverterContext, objectDefinition,
			_objectEntryService.updateObjectEntry(
				objectEntryId,
				_toObjectValues(
					serviceBuilderObjectEntry.getObjectDefinitionId(),
					objectEntry.getProperties(),
					dtoConverterContext.getLocale()),
				_createServiceContext(
					objectEntry.getProperties(),
					dtoConverterContext.getUserId())));
	}

	private void _checkObjectEntryObjectDefinitionId(
			ObjectDefinition objectDefinition,
			com.liferay.object.model.ObjectEntry objectEntry)
		throws Exception {

		if (objectDefinition.getObjectDefinitionId() !=
				objectEntry.getObjectDefinitionId()) {

			throw new NoSuchObjectEntryException();
		}
	}

	private ServiceContext _createServiceContext(
		Map<String, Object> properties, long userId) {

		ServiceContext serviceContext = new ServiceContext();

		if (properties.get("categoryIds") != null) {
			serviceContext.setAssetCategoryIds(
				ListUtil.toLongArray(
					(List<String>)properties.get("categoryIds"),
					Long::parseLong));
		}

		if (properties.get("tagNames") != null) {
			serviceContext.setAssetTagNames(
				ArrayUtil.toStringArray(
					(List<String>)properties.get("tagNames")));
		}

		serviceContext.setUserId(userId);

		return serviceContext;
	}

	private List<ObjectRelationship> _getManyToManyObjectRelationships(
			long objectDefinitionId)
		throws PortalException {

		List<ObjectRelationship> objectRelationships =
			_objectRelationshipLocalService.
				getObjectRelationshipsByObjectDefinitionId2(objectDefinitionId);

		return TransformUtil.transform(
			objectRelationships,
			objectRelationship -> {
				if (StringUtil.equals(
						objectRelationship.getType(),
						ObjectRelationshipConstants.TYPE_MANY_TO_MANY) &&
					!objectRelationship.isReverse()) {

					return objectRelationship;
				}

				return null;
			});
	}

	private String _getObjectEntriesPermissionName(long objectDefinitionId) {
		return ObjectConstants.RESOURCE_NAME + "#" + objectDefinitionId;
	}

	private String _getObjectEntryPermissionName(long objectDefinitionId) {
		return ObjectDefinition.class.getName() + "#" + objectDefinitionId;
	}

	private boolean _hasPreventDeletionType(long objectDefinitionId)
		throws PortalException {

		_objectRelationships = new ArrayList<>();

		_objectRelationships.addAll(
			TransformUtil.transform(
				_objectRelationshipLocalService.getObjectRelationships(
					objectDefinitionId),
				objectRelationship -> {
					if (objectRelationship.isReverse()) {
						return null;
					}

					return objectRelationship;
				}));

		_objectRelationships.addAll(
			_getManyToManyObjectRelationships(objectDefinitionId));

		for (ObjectRelationship objectRelationship : _objectRelationships) {
			if (StringUtil.equals(
					objectRelationship.getDeletionType(),
					ObjectRelationshipConstants.DELETION_TYPE_PREVENT)) {

				return true;
			}
		}

		return false;
	}

	private boolean _hasRelatedObjectEntries(
			com.liferay.object.model.ObjectEntry objectEntry)
		throws PortalException {

		for (ObjectRelationship objectRelationship : _objectRelationships) {
			int count = 0;

			if (StringUtil.equals(
					objectRelationship.getType(),
					ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

				count =
					_objectEntryLocalService.
						getOneToManyRelatedObjectEntriesCount(
							objectEntry.getGroupId(),
							objectRelationship.getObjectRelationshipId(),
							objectEntry.getPrimaryKey());
			}
			else if (StringUtil.equals(
						objectRelationship.getType(),
						ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

				count =
					_objectEntryLocalService.
						getManyToManyRelatedObjectEntriesCount(
							objectEntry.getGroupId(),
							objectRelationship.getObjectRelationshipId(),
							objectEntry.getPrimaryKey(), false);

				if (count == 0) {
					count =
						_objectEntryLocalService.
							getManyToManyRelatedObjectEntriesCount(
								objectEntry.getGroupId(),
								objectRelationship.getObjectRelationshipId(),
								objectEntry.getPrimaryKey(), true);
				}
			}

			if (count > 0) {
				return true;
			}
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

	private Date _toDate(Locale locale, String valueString) {
		if (Validator.isNull(valueString)) {
			return null;
		}

		try {
			return DateUtil.parseDate(
				"yyyy-MM-dd'T'HH:mm:ss'Z'", valueString, locale);
		}
		catch (ParseException parseException1) {
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

	private com.liferay.portal.kernel.search.filter.Filter _toFilter(
		String filterString, Locale locale, Long objectDefinitionId) {

		try {
			EntityModel entityModel = new ObjectEntryEntityModel(
				_objectFieldLocalService.getObjectFields(objectDefinitionId));

			FilterParser filterParser = _filterParserProvider.provide(
				entityModel);

			Filter oDataFilter = new Filter(filterParser.parse(filterString));

			return _expressionConvert.convert(
				oDataFilter.getExpression(), locale, entityModel);
		}
		catch (Exception exception) {
			_log.error("Invalid filter " + filterString, exception);
		}

		return null;
	}

	private List<ObjectEntry> _toObjectEntries(
			DTOConverterContext dtoConverterContext,
			List<com.liferay.object.model.ObjectEntry> objectEntries)
		throws Exception {

		return TransformUtil.transform(
			objectEntries,
			objectEntry -> {
				ObjectDefinition objectDefinition =
					_objectDefinitionLocalService.getObjectDefinition(
						objectEntry.getObjectDefinitionId());

				return _toObjectEntry(
					dtoConverterContext, objectDefinition, objectEntry);
			});
	}

	private ObjectEntry _toObjectEntry(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition,
			com.liferay.object.model.ObjectEntry objectEntry)
		throws Exception {

		DefaultDTOConverterContext defaultDTOConverterContext =
			new DefaultDTOConverterContext(
				dtoConverterContext.isAcceptAllLanguages(),
				HashMapBuilder.put(
					"delete",
					() -> {
						if (_hasPreventDeletionType(
								objectDefinition.getObjectDefinitionId()) &&
							_hasRelatedObjectEntries(objectEntry)) {

							return null;
						}

						return ActionUtil.addAction(
							ActionKeys.DELETE, ObjectEntryResourceImpl.class,
							objectEntry.getObjectEntryId(), "deleteObjectEntry",
							null, objectEntry.getUserId(),
							_getObjectEntryPermissionName(
								objectEntry.getObjectDefinitionId()),
							objectEntry.getGroupId(),
							dtoConverterContext.getUriInfo());
					}
				).put(
					"get",
					ActionUtil.addAction(
						ActionKeys.VIEW, ObjectEntryResourceImpl.class,
						objectEntry.getObjectEntryId(), "getObjectEntry", null,
						objectEntry.getUserId(),
						_getObjectEntryPermissionName(
							objectEntry.getObjectDefinitionId()),
						objectEntry.getGroupId(),
						dtoConverterContext.getUriInfo())
				).put(
					"permissions",
					ActionUtil.addAction(
						ActionKeys.PERMISSIONS, ObjectEntryResourceImpl.class,
						objectEntry.getObjectEntryId(), "patchObjectEntry",
						null, objectEntry.getUserId(),
						_getObjectEntryPermissionName(
							objectEntry.getObjectDefinitionId()),
						objectEntry.getGroupId(),
						dtoConverterContext.getUriInfo())
				).put(
					"update",
					ActionUtil.addAction(
						ActionKeys.UPDATE, ObjectEntryResourceImpl.class,
						objectEntry.getObjectEntryId(), "putObjectEntry", null,
						objectEntry.getUserId(),
						_getObjectEntryPermissionName(
							objectEntry.getObjectDefinitionId()),
						objectEntry.getGroupId(),
						dtoConverterContext.getUriInfo())
				).build(),
				dtoConverterContext.getDTOConverterRegistry(),
				dtoConverterContext.getHttpServletRequest(),
				objectEntry.getObjectEntryId(), dtoConverterContext.getLocale(),
				dtoConverterContext.getUriInfo(),
				dtoConverterContext.getUser());

		defaultDTOConverterContext.setAttribute(
			"objectDefinition", objectDefinition);

		return _objectEntryDTOConverter.toDTO(
			defaultDTOConverterContext, objectEntry);
	}

	private Map<String, Serializable> _toObjectValues(
		long objectDefinitionId, Map<String, Object> properties,
		Locale locale) {

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(objectDefinitionId);

		Map<String, Serializable> values = new HashMap<>();

		for (ObjectField objectField : objectFields) {
			String name = objectField.getName();

			Object object = properties.get(name);

			if (object == null) {
				continue;
			}

			if (Objects.equals(objectField.getDBType(), "Date")) {
				values.put(name, _toDate(locale, String.valueOf(object)));
			}

			if (objectField.getListTypeDefinitionId() != 0) {
				Map<String, String> map = (HashMap<String, String>)object;

				values.put(name, map.get("key"));
			}
			else {
				values.put(name, (Serializable)object);
			}
		}

		return values;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultObjectEntryManagerImpl.class);

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private Aggregations _aggregations;

	@Reference(
		target = "(result.class.name=com.liferay.portal.kernel.search.filter.Filter)"
	)
	private ExpressionConvert<com.liferay.portal.kernel.search.filter.Filter>
		_expressionConvert;

	@Reference
	private FilterParserProvider _filterParserProvider;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryDTOConverter _objectEntryDTOConverter;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectEntryService _objectEntryService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	private List<ObjectRelationship> _objectRelationships;

	@Reference
	private ObjectRelationshipService _objectRelationshipService;

	@Reference
	private Queries _queries;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}