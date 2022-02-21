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

import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.object.constants.ObjectConstants;
import com.liferay.object.exception.NoSuchObjectEntryException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.internal.dto.v1_0.converter.ObjectEntryDTOConverter;
import com.liferay.object.rest.internal.resource.v1_0.ObjectEntryResourceImpl;
import com.liferay.object.rest.internal.search.aggregation.AggregationUtil;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.GroupUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.io.Serializable;

import java.text.ParseException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 */
@Component(immediate = true, service = ObjectEntryManager.class)
public class ObjectEntryManagerImpl implements ObjectEntryManager {

	@Override
	public ObjectEntry addObjectEntry(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition, ObjectEntry objectEntry,
			String scopeKey)
		throws Exception {

		return _toObjectEntry(
			dtoConverterContext, objectDefinition,
			_objectEntryService.addObjectEntry(
				_getGroupId(objectDefinition, scopeKey),
				objectDefinition.getObjectDefinitionId(),
				_toObjectValues(
					objectDefinition.getObjectDefinitionId(),
					objectEntry.getProperties(),
					dtoConverterContext.getLocale()),
				new ServiceContext()));
	}

	@Override
	public ObjectEntry addOrUpdateObjectEntry(
			DTOConverterContext dtoConverterContext,
			String externalReferenceCode, ObjectDefinition objectDefinition,
			ObjectEntry objectEntry, String scopeKey)
		throws Exception {

		return _toObjectEntry(
			dtoConverterContext, objectDefinition,
			_objectEntryService.addOrUpdateObjectEntry(
				externalReferenceCode, _getGroupId(objectDefinition, scopeKey),
				objectDefinition.getObjectDefinitionId(),
				_toObjectValues(
					objectDefinition.getObjectDefinitionId(),
					objectEntry.getProperties(),
					dtoConverterContext.getLocale()),
				new ServiceContext()));
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
				_getGroupId(objectDefinition, scopeKey));

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
			Filter filter, Pagination pagination, String search, Sort[] sorts)
		throws Exception {

		long groupId = _getGroupId(objectDefinition, scopeKey);

		Optional<UriInfo> uriInfoOptional =
			dtoConverterContext.getUriInfoOptional();

		UriInfo uriInfo = uriInfoOptional.orElse(null);

		return SearchUtil.search(
			HashMapBuilder.put(
				"create",
				ActionUtil.addAction(
					"ADD_OBJECT_ENTRY", ObjectEntryResourceImpl.class, 0L,
					"postObjectEntry", null, objectDefinition.getUserId(),
					_getObjectEntriesPermissionName(
						objectDefinition.getObjectDefinitionId()),
					groupId, uriInfo)
			).put(
				"get",
				ActionUtil.addAction(
					ActionKeys.VIEW, ObjectEntryResourceImpl.class, 0L,
					"getObjectEntriesPage", null, objectDefinition.getUserId(),
					_getObjectEntriesPermissionName(
						objectDefinition.getObjectDefinitionId()),
					groupId, uriInfo)
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
				searchContext.setAttribute("useObjectView", Boolean.TRUE);
				searchContext.setCompanyId(companyId);
				searchContext.setGroupIds(new long[] {groupId});

				SearchRequestBuilder searchRequestBuilder =
					_searchRequestBuilderFactory.builder(searchContext);

				AggregationUtil.processVulcanAggregation(
					_aggregations, _queries, searchRequestBuilder, aggregation);
			},
			sorts,
			document -> getObjectEntry(
				dtoConverterContext, objectDefinition,
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
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
				_getGroupId(objectDefinition, scopeKey));

		_checkObjectEntryObjectDefinitionId(objectDefinition, objectEntry);

		return _toObjectEntry(
			dtoConverterContext, objectDefinition, objectEntry);
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
				new ServiceContext()));
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

	private long _getGroupId(
		ObjectDefinition objectDefinition, String scopeKey) {

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		if (objectScopeProvider.isGroupAware()) {
			if (Objects.equals("site", objectDefinition.getScope())) {
				return GroupUtil.getGroupId(
					objectDefinition.getCompanyId(), scopeKey,
					_groupLocalService);
			}

			return GroupUtil.getDepotGroupId(
				scopeKey, objectDefinition.getCompanyId(),
				_depotEntryLocalService, _groupLocalService);
		}

		return 0;
	}

	private String _getObjectEntriesPermissionName(long objectDefinitionId) {
		return ObjectConstants.RESOURCE_NAME + "#" + objectDefinitionId;
	}

	private String _getObjectEntryPermissionName(long objectDefinitionId) {
		return ObjectDefinition.class.getName() + "#" + objectDefinitionId;
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

	private ObjectEntry _toObjectEntry(
			DTOConverterContext dtoConverterContext,
			ObjectDefinition objectDefinition,
			com.liferay.object.model.ObjectEntry objectEntry)
		throws Exception {

		Optional<UriInfo> uriInfoOptional =
			dtoConverterContext.getUriInfoOptional();

		UriInfo uriInfo = uriInfoOptional.orElse(null);

		DefaultDTOConverterContext defaultDTOConverterContext =
			new DefaultDTOConverterContext(
				dtoConverterContext.isAcceptAllLanguages(),
				HashMapBuilder.put(
					"delete",
					ActionUtil.addAction(
						ActionKeys.DELETE, ObjectEntryResourceImpl.class,
						objectEntry.getObjectEntryId(), "deleteObjectEntry",
						null, objectEntry.getUserId(),
						_getObjectEntryPermissionName(
							objectEntry.getObjectDefinitionId()),
						objectEntry.getGroupId(), uriInfo)
				).put(
					"get",
					ActionUtil.addAction(
						ActionKeys.VIEW, ObjectEntryResourceImpl.class,
						objectEntry.getObjectEntryId(), "getObjectEntry", null,
						objectEntry.getUserId(),
						_getObjectEntryPermissionName(
							objectEntry.getObjectDefinitionId()),
						objectEntry.getGroupId(), uriInfo)
				).put(
					"permissions",
					ActionUtil.addAction(
						ActionKeys.PERMISSIONS, ObjectEntryResourceImpl.class,
						objectEntry.getObjectEntryId(), "patchObjectEntry",
						null, objectEntry.getUserId(),
						_getObjectEntryPermissionName(
							objectEntry.getObjectDefinitionId()),
						objectEntry.getGroupId(), uriInfo)
				).put(
					"update",
					ActionUtil.addAction(
						ActionKeys.UPDATE, ObjectEntryResourceImpl.class,
						objectEntry.getObjectEntryId(), "putObjectEntry", null,
						objectEntry.getUserId(),
						_getObjectEntryPermissionName(
							objectEntry.getObjectDefinitionId()),
						objectEntry.getGroupId(), uriInfo)
				).build(),
				dtoConverterContext.getDTOConverterRegistry(),
				dtoConverterContext.getHttpServletRequest(),
				objectEntry.getObjectEntryId(), dtoConverterContext.getLocale(),
				uriInfo, dtoConverterContext.getUser());

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

	@Reference
	private Aggregations _aggregations;

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ObjectEntryDTOConverter _objectEntryDTOConverter;

	@Reference
	private ObjectEntryService _objectEntryService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

	@Reference
	private Queries _queries;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}