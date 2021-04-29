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

package com.liferay.object.internal.resource;

import com.liferay.object.dto.ObjectEntry;
import com.liferay.object.internal.dto.converter.ObjectEntryDTOConverter;
import com.liferay.object.internal.odata.entity.ObjectEntryEntityModel;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.resource.ObjectEntryResource;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier de Arcos
 */
@Component(scope = ServiceScope.PROTOTYPE, service = ObjectEntryResource.class)
public class ObjectEntryResourceImpl
	implements EntityModelResource, ObjectEntryResource {

	public ObjectEntryResourceImpl(
		ObjectDefinition objectDefinition,
		ObjectEntryDTOConverter objectEntryDTOConverter,
		ObjectEntryLocalService objectEntryLocalService,
		ObjectFieldLocalService objectFieldLocalService) {

		_objectDefinition = objectDefinition;
		_objectEntryDTOConverter = objectEntryDTOConverter;
		_objectEntryLocalService = objectEntryLocalService;
		_objectFieldLocalService = objectFieldLocalService;
	}

	@DELETE
	@Override
	@Path("/{objectEntryId}")
	@Produces({"application/json", "application/xml"})
	public void deleteObjectEntry(
			@NotNull @PathParam("objectEntryId") long objectEntryId)
		throws Exception {

		_objectEntryLocalService.deleteObjectEntry(objectEntryId);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap<?, ?> multivaluedMap) {
		if (_entityModel == null) {
			_entityModel = new ObjectEntryEntityModel(
				_objectFieldLocalService.getObjectFields(
					_objectDefinition.getObjectDefinitionId()));
		}

		return _entityModel;
	}

	@GET
	@Override
	@Path("/")
	@Produces({"application/json", "application/xml"})
	public Page<ObjectEntry> getObjectEntriesPage(
			@Context Aggregation aggregation, @Context Filter filter,
			@Context Pagination pagination, @Context Sort[] sorts,
			@QueryParam("search") String search)
		throws Exception {

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
			filter, com.liferay.object.model.ObjectEntry.class, search,
			pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setCompanyId(_contextCompany.getCompanyId());
			},
			sorts,
			document -> _toObjectEntry(
				_objectEntryLocalService.getObjectEntry(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@GET
	@Override
	@Path("/{objectEntryId}")
	@Produces({"application/json", "application/xml"})
	public ObjectEntry getObjectEntry(
			@NotNull @PathParam("objectEntryId") long objectEntryId)
		throws Exception {

		return _toObjectEntry(
			_objectEntryLocalService.getObjectEntry(objectEntryId));
	}

	@Consumes({"application/json", "application/xml"})
	@Override
	@Path("/sites/{siteId}")
	@POST
	@Produces({"application/json", "application/xml"})
	public ObjectEntry postObjectEntry(
			@PathParam("siteId") Long siteId, ObjectEntry objectEntry)
		throws Exception {

		return _toObjectEntry(
			_objectEntryLocalService.addObjectEntry(
				_contextUser.getUserId(), siteId,
				_objectDefinition.getObjectDefinitionId(),
				objectEntry.getProperties(), new ServiceContext()));
	}

	@Consumes({"application/json", "application/xml"})
	@Override
	@Path("/{objectEntryId}")
	@Produces({"application/json", "application/xml"})
	@PUT
	public ObjectEntry putObjectEntry(
			@NotNull @PathParam("objectEntryId") long objectEntryId,
			ObjectEntry objectEntry)
		throws Exception {

		return _toObjectEntry(
			_objectEntryLocalService.updateObjectEntry(
				_contextUser.getUserId(), objectEntryId,
				objectEntry.getProperties(), new ServiceContext()));
	}

	private ObjectEntry _toObjectEntry(
			com.liferay.object.model.ObjectEntry objectEntry)
		throws Exception {

		return _objectEntryDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				_contextAcceptLanguage.isAcceptAllLanguages(),
				Collections.emptyMap(), null, _contextHttpServletRequest,
				objectEntry.getObjectEntryId(),
				_contextAcceptLanguage.getPreferredLocale(), _contextUriInfo,
				_contextUser),
			objectEntry);
	}

	@Context
	private AcceptLanguage _contextAcceptLanguage;

	@Context
	private Company _contextCompany;

	@Context
	private HttpServletRequest _contextHttpServletRequest;

	@Context
	private UriInfo _contextUriInfo;

	@Context
	private User _contextUser;

	private ObjectEntryEntityModel _entityModel;
	private final ObjectDefinition _objectDefinition;
	private final ObjectEntryDTOConverter _objectEntryDTOConverter;

	// Should be the remote service to check permissions

	private final ObjectEntryLocalService _objectEntryLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;

}