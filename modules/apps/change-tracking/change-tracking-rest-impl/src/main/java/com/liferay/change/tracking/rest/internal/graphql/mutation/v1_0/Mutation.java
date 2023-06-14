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

package com.liferay.change.tracking.rest.internal.graphql.mutation.v1_0;

import com.liferay.change.tracking.rest.dto.v1_0.CTCollection;
import com.liferay.change.tracking.rest.resource.v1_0.CTCollectionResource;
import com.liferay.change.tracking.rest.resource.v1_0.CTProcessResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineExportTaskResource;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import java.util.Date;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author David Truong
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setCTCollectionResourceComponentServiceObjects(
		ComponentServiceObjects<CTCollectionResource>
			ctCollectionResourceComponentServiceObjects) {

		_ctCollectionResourceComponentServiceObjects =
			ctCollectionResourceComponentServiceObjects;
	}

	public static void setCTProcessResourceComponentServiceObjects(
		ComponentServiceObjects<CTProcessResource>
			ctProcessResourceComponentServiceObjects) {

		_ctProcessResourceComponentServiceObjects =
			ctProcessResourceComponentServiceObjects;
	}

	@GraphQLField
	public Response createCTCollectionsPageExportBatch(
			@GraphQLName("status") Integer[] status,
			@GraphQLName("search") String search,
			@GraphQLName("sort") String sortsString,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_ctCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			ctCollectionResource ->
				ctCollectionResource.postCTCollectionsPageExportBatch(
					status, search,
					_sortsBiFunction.apply(ctCollectionResource, sortsString),
					callbackURL, contentType, fieldNames));
	}

	@GraphQLField
	public CTCollection createCTCollection(
			@GraphQLName("ctCollection") CTCollection ctCollection)
		throws Exception {

		return _applyComponentServiceObjects(
			_ctCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			ctCollectionResource -> ctCollectionResource.postCTCollection(
				ctCollection));
	}

	@GraphQLField
	public Response createCTCollectionBatch(
			@GraphQLName("ctCollection") CTCollection ctCollection,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_ctCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			ctCollectionResource -> ctCollectionResource.postCTCollectionBatch(
				ctCollection, callbackURL, object));
	}

	@GraphQLField
	public boolean deleteCTCollection(
			@GraphQLName("ctCollectionId") Long ctCollectionId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_ctCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			ctCollectionResource -> ctCollectionResource.deleteCTCollection(
				ctCollectionId));

		return true;
	}

	@GraphQLField
	public Response deleteCTCollectionBatch(
			@GraphQLName("ctCollectionId") Long ctCollectionId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_ctCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			ctCollectionResource ->
				ctCollectionResource.deleteCTCollectionBatch(
					ctCollectionId, callbackURL, object));
	}

	@GraphQLField
	public CTCollection patchCTCollection(
			@GraphQLName("ctCollectionId") Long ctCollectionId,
			@GraphQLName("ctCollection") CTCollection ctCollection)
		throws Exception {

		return _applyComponentServiceObjects(
			_ctCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			ctCollectionResource -> ctCollectionResource.patchCTCollection(
				ctCollectionId, ctCollection));
	}

	@GraphQLField
	public CTCollection updateCTCollection(
			@GraphQLName("ctCollectionId") Long ctCollectionId,
			@GraphQLName("ctCollection") CTCollection ctCollection)
		throws Exception {

		return _applyComponentServiceObjects(
			_ctCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			ctCollectionResource -> ctCollectionResource.putCTCollection(
				ctCollectionId, ctCollection));
	}

	@GraphQLField
	public Response updateCTCollectionBatch(
			@GraphQLName("ctCollectionId") Long ctCollectionId,
			@GraphQLName("ctCollection") CTCollection ctCollection,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_ctCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			ctCollectionResource -> ctCollectionResource.putCTCollectionBatch(
				ctCollectionId, ctCollection, callbackURL, object));
	}

	@GraphQLField
	public boolean createCTCollectionCheckout(
			@GraphQLName("ctCollectionId") Long ctCollectionId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_ctCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			ctCollectionResource ->
				ctCollectionResource.postCTCollectionCheckout(ctCollectionId));

		return true;
	}

	@GraphQLField
	public boolean createCTCollectionPublish(
			@GraphQLName("ctCollectionId") Long ctCollectionId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_ctCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			ctCollectionResource ->
				ctCollectionResource.postCTCollectionPublish(ctCollectionId));

		return true;
	}

	@GraphQLField
	public boolean createCTCollectionSchedulePublish(
			@GraphQLName("ctCollectionId") Long ctCollectionId,
			@GraphQLName("publishDate") Date publishDate)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_ctCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			ctCollectionResource ->
				ctCollectionResource.postCTCollectionSchedulePublish(
					ctCollectionId, publishDate));

		return true;
	}

	@GraphQLField
	public Response createCTProcessesPageExportBatch(
			@GraphQLName("status") Integer[] status,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("sort") String sortsString,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_ctProcessResourceComponentServiceObjects,
			this::_populateResourceContext,
			ctProcessResource ->
				ctProcessResource.postCTProcessesPageExportBatch(
					status, search,
					_filterBiFunction.apply(ctProcessResource, filterString),
					_sortsBiFunction.apply(ctProcessResource, sortsString),
					callbackURL, contentType, fieldNames));
	}

	@GraphQLField
	public boolean createCTProcessRevert(
			@GraphQLName("ctProcessId") Long ctProcessId,
			@GraphQLName("description") String description,
			@GraphQLName("name") String name)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_ctProcessResourceComponentServiceObjects,
			this::_populateResourceContext,
			ctProcessResource -> ctProcessResource.postCTProcessRevert(
				ctProcessId, description, name));

		return true;
	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(
			CTCollectionResource ctCollectionResource)
		throws Exception {

		ctCollectionResource.setContextAcceptLanguage(_acceptLanguage);
		ctCollectionResource.setContextCompany(_company);
		ctCollectionResource.setContextHttpServletRequest(_httpServletRequest);
		ctCollectionResource.setContextHttpServletResponse(
			_httpServletResponse);
		ctCollectionResource.setContextUriInfo(_uriInfo);
		ctCollectionResource.setContextUser(_user);
		ctCollectionResource.setGroupLocalService(_groupLocalService);
		ctCollectionResource.setRoleLocalService(_roleLocalService);

		ctCollectionResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		ctCollectionResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private void _populateResourceContext(CTProcessResource ctProcessResource)
		throws Exception {

		ctProcessResource.setContextAcceptLanguage(_acceptLanguage);
		ctProcessResource.setContextCompany(_company);
		ctProcessResource.setContextHttpServletRequest(_httpServletRequest);
		ctProcessResource.setContextHttpServletResponse(_httpServletResponse);
		ctProcessResource.setContextUriInfo(_uriInfo);
		ctProcessResource.setContextUser(_user);
		ctProcessResource.setGroupLocalService(_groupLocalService);
		ctProcessResource.setRoleLocalService(_roleLocalService);

		ctProcessResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		ctProcessResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private static ComponentServiceObjects<CTCollectionResource>
		_ctCollectionResourceComponentServiceObjects;
	private static ComponentServiceObjects<CTProcessResource>
		_ctProcessResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;
	private VulcanBatchEngineExportTaskResource
		_vulcanBatchEngineExportTaskResource;
	private VulcanBatchEngineImportTaskResource
		_vulcanBatchEngineImportTaskResource;

}