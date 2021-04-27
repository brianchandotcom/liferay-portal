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

package com.liferay.object.internal.jaxrs.resource;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.filter.Filter;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

/**
 * @author Javier de Arcos
 */
public class ObjectEntryApplication
	extends Application implements EntityModelResource {

	public ObjectEntryApplication(
		ObjectDefinition objectDefinition,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectFieldLocalService objectFieldLocalService) {

		_objectDefinition = objectDefinition;
		_objectEntryLocalService = objectEntryLocalService;
		_objectFieldLocalService = objectFieldLocalService;
	}

	@DELETE
	@Path("/{objectEntryId}")
	@Produces({"application/json", "application/xml"})
	public void deleteObjectEntry(
			@NotNull @PathParam("objectEntryId") long objectEntryId)
		throws Exception {

		_objectEntryLocalService.deleteObjectEntry(objectEntryId);
	}

	public EntityModel getEntityModel(MultivaluedMap<?, ?> multivaluedMap) {
		if (_entityModel == null) {
			_entityModel = new ObjectEntryEntityModel(
				_objectFieldLocalService.getObjectFields(
					_objectDefinition.getObjectDefinitionId()));
		}

		return _entityModel;
	}

	@GET
	@Path("/")
	@Produces({"application/json", "application/xml"})
	public Page<Map<String, Serializable>> getObjectEntriesPage(
			@Context Aggregation aggregation, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts,
			@QueryParam("search") String search)
		throws Exception {

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				_objectDefinition.getObjectDefinitionId());

		Stream<ObjectField> stream = objectFields.stream();

		List<String> fieldNames = stream.map(
			ObjectField::getName
		).collect(
			Collectors.toList()
		);

		fieldNames.add(
			StringUtil.removeSubstring(
				_objectDefinition.getDBPrimaryKeyColumnName(), "_"));

		return SearchUtil.search(
			new HashMap<>(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"objectDefinitionId",
						String.valueOf(
							_objectDefinition.getObjectDefinitionId())),
					BooleanClauseOccur.MUST);
			},
			filter, ObjectEntry.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				fieldNames.toArray(new String[0])),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setCompanyId(_contextCompany.getCompanyId());
			},
			sorts, document -> (Map)document.getFields());
	}

	@GET
	@Path("/{objectEntryId}")
	@Produces({"application/json", "application/xml"})
	public Map<String, Serializable> getObjectEntry(
			@NotNull @PathParam("objectEntryId") long objectEntryId)
		throws Exception {

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			objectEntryId);

		return objectEntry.getValues();
	}

	@Override
	public Set<Object> getSingletons() {
		return Collections.singleton(this);
	}

	@Consumes({"application/json", "application/xml"})
	@Path("/sites/{siteId}")
	@POST
	@Produces({"application/json", "application/xml"})
	public Map<String, Serializable> postObjectEntry(
			@PathParam("siteId") Long siteId, Map<String, Serializable> values)
		throws Exception {

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			_contextUser.getUserId(), siteId,
			_objectDefinition.getObjectDefinitionId(), values,
			new ServiceContext());

		return objectEntry.getValues();
	}

	@Consumes({"application/json", "application/xml"})
	@Path("/{objectEntryId}")
	@Produces({"application/json", "application/xml"})
	@PUT
	public Map<String, Serializable> putObjectEntry(
			@NotNull @PathParam("objectEntryId") long objectEntryId,
			Map<String, Serializable> values)
		throws Exception {

		ObjectEntry objectEntry = _objectEntryLocalService.updateObjectEntry(
			_contextUser.getUserId(), objectEntryId, values,
			new ServiceContext());

		return objectEntry.getValues();
	}

	@Context
	private Company _contextCompany;

	@Context
	private User _contextUser;

	private ObjectEntryEntityModel _entityModel;
	private final ObjectDefinition _objectDefinition;

	// Should be the remote service to check permissions

	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;

}