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

package com.liferay.data.engine.rest.internal.graphql.mutation.v2_0;

import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.dto.v2_0.DataListView;
import com.liferay.data.engine.rest.dto.v2_0.DataRecord;
import com.liferay.data.engine.rest.dto.v2_0.DataRecordCollection;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v2_0.DataLayoutResource;
import com.liferay.data.engine.rest.resource.v2_0.DataListViewResource;
import com.liferay.data.engine.rest.resource.v2_0.DataRecordCollectionResource;
import com.liferay.data.engine.rest.resource.v2_0.DataRecordResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotEmpty;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setDataDefinitionResourceComponentServiceObjects(
		ComponentServiceObjects<DataDefinitionResource>
			dataDefinitionResourceComponentServiceObjects) {

		_dataDefinitionResourceComponentServiceObjects =
			dataDefinitionResourceComponentServiceObjects;
	}

	public static void setDataLayoutResourceComponentServiceObjects(
		ComponentServiceObjects<DataLayoutResource>
			dataLayoutResourceComponentServiceObjects) {

		_dataLayoutResourceComponentServiceObjects =
			dataLayoutResourceComponentServiceObjects;
	}

	public static void setDataListViewResourceComponentServiceObjects(
		ComponentServiceObjects<DataListViewResource>
			dataListViewResourceComponentServiceObjects) {

		_dataListViewResourceComponentServiceObjects =
			dataListViewResourceComponentServiceObjects;
	}

	public static void setDataRecordResourceComponentServiceObjects(
		ComponentServiceObjects<DataRecordResource>
			dataRecordResourceComponentServiceObjects) {

		_dataRecordResourceComponentServiceObjects =
			dataRecordResourceComponentServiceObjects;
	}

	public static void setDataRecordCollectionResourceComponentServiceObjects(
		ComponentServiceObjects<DataRecordCollectionResource>
			dataRecordCollectionResourceComponentServiceObjects) {

		_dataRecordCollectionResourceComponentServiceObjects =
			dataRecordCollectionResourceComponentServiceObjects;
	}

	@GraphQLField
	public DataDefinition createDataDefinitionByContentType(
			@GraphQLName("contentType") String contentType,
			@GraphQLName("dataDefinition") DataDefinition dataDefinition)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataDefinitionResource ->
				dataDefinitionResource.postDataDefinitionByContentType(
					contentType, dataDefinition));
	}

	@GraphQLField
	public boolean deleteDataDefinition(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataDefinitionResource ->
				dataDefinitionResource.deleteDataDefinition(dataDefinitionId));

		return true;
	}

	@GraphQLField
	public Response deleteDataDefinitionBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataDefinitionResource ->
				dataDefinitionResource.deleteDataDefinitionBatch(
					callbackURL, object));
	}

	@GraphQLField
	public DataDefinition updateDataDefinition(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("dataDefinition") DataDefinition dataDefinition)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataDefinitionResource -> dataDefinitionResource.putDataDefinition(
				dataDefinitionId, dataDefinition));
	}

	@GraphQLField
	public Response updateDataDefinitionBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataDefinitionResource ->
				dataDefinitionResource.putDataDefinitionBatch(
					callbackURL, object));
	}

	@GraphQLField
	public boolean updateDataDefinitionPermission(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("permissions")
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataDefinitionResource ->
				dataDefinitionResource.putDataDefinitionPermission(
					dataDefinitionId, permissions));

		return true;
	}

	@GraphQLField
	public DataDefinition createSiteDataDefinitionByContentType(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("dataDefinition") DataDefinition dataDefinition)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataDefinitionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataDefinitionResource ->
				dataDefinitionResource.postSiteDataDefinitionByContentType(
					Long.valueOf(siteKey), contentType, dataDefinition));
	}

	@GraphQLField
	public boolean deleteDataLayoutsDataDefinition(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource ->
				dataLayoutResource.deleteDataLayoutsDataDefinition(
					dataDefinitionId));

		return true;
	}

	@GraphQLField
	public DataLayout createDataDefinitionDataLayout(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("dataLayout") DataLayout dataLayout)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource ->
				dataLayoutResource.postDataDefinitionDataLayout(
					dataDefinitionId, dataLayout));
	}

	@GraphQLField
	public Response createDataDefinitionDataLayoutBatch(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource ->
				dataLayoutResource.postDataDefinitionDataLayoutBatch(
					dataDefinitionId, callbackURL, object));
	}

	@GraphQLField
	public boolean deleteDataLayout(
			@GraphQLName("dataLayoutId") Long dataLayoutId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource -> dataLayoutResource.deleteDataLayout(
				dataLayoutId));

		return true;
	}

	@GraphQLField
	public Response deleteDataLayoutBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource -> dataLayoutResource.deleteDataLayoutBatch(
				callbackURL, object));
	}

	@GraphQLField
	public DataLayout updateDataLayout(
			@GraphQLName("dataLayoutId") Long dataLayoutId,
			@GraphQLName("dataLayout") DataLayout dataLayout)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource -> dataLayoutResource.putDataLayout(
				dataLayoutId, dataLayout));
	}

	@GraphQLField
	public Response updateDataLayoutBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataLayoutResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataLayoutResource -> dataLayoutResource.putDataLayoutBatch(
				callbackURL, object));
	}

	@GraphQLField
	public boolean deleteDataListViewsDataDefinition(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataListViewResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataListViewResource ->
				dataListViewResource.deleteDataListViewsDataDefinition(
					dataDefinitionId));

		return true;
	}

	@GraphQLField
	public DataListView createDataDefinitionDataListView(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("dataListView") DataListView dataListView)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataListViewResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataListViewResource ->
				dataListViewResource.postDataDefinitionDataListView(
					dataDefinitionId, dataListView));
	}

	@GraphQLField
	public Response createDataDefinitionDataListViewBatch(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataListViewResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataListViewResource ->
				dataListViewResource.postDataDefinitionDataListViewBatch(
					dataDefinitionId, callbackURL, object));
	}

	@GraphQLField
	public boolean deleteDataListView(
			@GraphQLName("dataListViewId") Long dataListViewId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataListViewResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataListViewResource -> dataListViewResource.deleteDataListView(
				dataListViewId));

		return true;
	}

	@GraphQLField
	public Response deleteDataListViewBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataListViewResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataListViewResource ->
				dataListViewResource.deleteDataListViewBatch(
					callbackURL, object));
	}

	@GraphQLField
	public DataListView updateDataListView(
			@GraphQLName("dataListViewId") Long dataListViewId,
			@GraphQLName("dataListView") DataListView dataListView)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataListViewResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataListViewResource -> dataListViewResource.putDataListView(
				dataListViewId, dataListView));
	}

	@GraphQLField
	public Response updateDataListViewBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataListViewResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataListViewResource -> dataListViewResource.putDataListViewBatch(
				callbackURL, object));
	}

	@GraphQLField
	public DataRecord createDataDefinitionDataRecord(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("dataRecord") DataRecord dataRecord)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordResource ->
				dataRecordResource.postDataDefinitionDataRecord(
					dataDefinitionId, dataRecord));
	}

	@GraphQLField
	public Response createDataDefinitionDataRecordBatch(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordResource ->
				dataRecordResource.postDataDefinitionDataRecordBatch(
					dataDefinitionId, callbackURL, object));
	}

	@GraphQLField
	public DataRecord createDataRecordCollectionDataRecord(
			@GraphQLName("dataRecordCollectionId") Long dataRecordCollectionId,
			@GraphQLName("dataRecord") DataRecord dataRecord)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordResource ->
				dataRecordResource.postDataRecordCollectionDataRecord(
					dataRecordCollectionId, dataRecord));
	}

	@GraphQLField
	public Response createDataRecordCollectionDataRecordBatch(
			@GraphQLName("dataRecordCollectionId") Long dataRecordCollectionId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordResource ->
				dataRecordResource.postDataRecordCollectionDataRecordBatch(
					dataRecordCollectionId, callbackURL, object));
	}

	@GraphQLField
	public boolean deleteDataRecord(
			@GraphQLName("dataRecordId") Long dataRecordId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordResource -> dataRecordResource.deleteDataRecord(
				dataRecordId));

		return true;
	}

	@GraphQLField
	public Response deleteDataRecordBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordResource -> dataRecordResource.deleteDataRecordBatch(
				callbackURL, object));
	}

	@GraphQLField
	public DataRecord updateDataRecord(
			@GraphQLName("dataRecordId") Long dataRecordId,
			@GraphQLName("dataRecord") DataRecord dataRecord)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordResource -> dataRecordResource.putDataRecord(
				dataRecordId, dataRecord));
	}

	@GraphQLField
	public Response updateDataRecordBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordResource -> dataRecordResource.putDataRecordBatch(
				callbackURL, object));
	}

	@GraphQLField
	public DataRecordCollection createDataDefinitionDataRecordCollection(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("dataRecordCollection") DataRecordCollection
				dataRecordCollection)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordCollectionResource ->
				dataRecordCollectionResource.
					postDataDefinitionDataRecordCollection(
						dataDefinitionId, dataRecordCollection));
	}

	@GraphQLField
	public Response createDataDefinitionDataRecordCollectionBatch(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordCollectionResource ->
				dataRecordCollectionResource.
					postDataDefinitionDataRecordCollectionBatch(
						dataDefinitionId, callbackURL, object));
	}

	@GraphQLField
	public boolean deleteDataRecordCollection(
			@GraphQLName("dataRecordCollectionId") Long dataRecordCollectionId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataRecordCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordCollectionResource ->
				dataRecordCollectionResource.deleteDataRecordCollection(
					dataRecordCollectionId));

		return true;
	}

	@GraphQLField
	public Response deleteDataRecordCollectionBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordCollectionResource ->
				dataRecordCollectionResource.deleteDataRecordCollectionBatch(
					callbackURL, object));
	}

	@GraphQLField
	public DataRecordCollection updateDataRecordCollection(
			@GraphQLName("dataRecordCollectionId") Long dataRecordCollectionId,
			@GraphQLName("dataRecordCollection") DataRecordCollection
				dataRecordCollection)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordCollectionResource ->
				dataRecordCollectionResource.putDataRecordCollection(
					dataRecordCollectionId, dataRecordCollection));
	}

	@GraphQLField
	public Response updateDataRecordCollectionBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_dataRecordCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordCollectionResource ->
				dataRecordCollectionResource.putDataRecordCollectionBatch(
					callbackURL, object));
	}

	@GraphQLField
	public boolean updateDataRecordCollectionPermission(
			@GraphQLName("dataRecordCollectionId") Long dataRecordCollectionId,
			@GraphQLName("permissions")
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_dataRecordCollectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			dataRecordCollectionResource ->
				dataRecordCollectionResource.putDataRecordCollectionPermission(
					dataRecordCollectionId, permissions));

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
			DataDefinitionResource dataDefinitionResource)
		throws Exception {

		dataDefinitionResource.setContextAcceptLanguage(_acceptLanguage);
		dataDefinitionResource.setContextCompany(_company);
		dataDefinitionResource.setContextHttpServletRequest(
			_httpServletRequest);
		dataDefinitionResource.setContextHttpServletResponse(
			_httpServletResponse);
		dataDefinitionResource.setContextUriInfo(_uriInfo);
		dataDefinitionResource.setContextUser(_user);
		dataDefinitionResource.setGroupLocalService(_groupLocalService);
		dataDefinitionResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(DataLayoutResource dataLayoutResource)
		throws Exception {

		dataLayoutResource.setContextAcceptLanguage(_acceptLanguage);
		dataLayoutResource.setContextCompany(_company);
		dataLayoutResource.setContextHttpServletRequest(_httpServletRequest);
		dataLayoutResource.setContextHttpServletResponse(_httpServletResponse);
		dataLayoutResource.setContextUriInfo(_uriInfo);
		dataLayoutResource.setContextUser(_user);
		dataLayoutResource.setGroupLocalService(_groupLocalService);
		dataLayoutResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			DataListViewResource dataListViewResource)
		throws Exception {

		dataListViewResource.setContextAcceptLanguage(_acceptLanguage);
		dataListViewResource.setContextCompany(_company);
		dataListViewResource.setContextHttpServletRequest(_httpServletRequest);
		dataListViewResource.setContextHttpServletResponse(
			_httpServletResponse);
		dataListViewResource.setContextUriInfo(_uriInfo);
		dataListViewResource.setContextUser(_user);
		dataListViewResource.setGroupLocalService(_groupLocalService);
		dataListViewResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(DataRecordResource dataRecordResource)
		throws Exception {

		dataRecordResource.setContextAcceptLanguage(_acceptLanguage);
		dataRecordResource.setContextCompany(_company);
		dataRecordResource.setContextHttpServletRequest(_httpServletRequest);
		dataRecordResource.setContextHttpServletResponse(_httpServletResponse);
		dataRecordResource.setContextUriInfo(_uriInfo);
		dataRecordResource.setContextUser(_user);
		dataRecordResource.setGroupLocalService(_groupLocalService);
		dataRecordResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			DataRecordCollectionResource dataRecordCollectionResource)
		throws Exception {

		dataRecordCollectionResource.setContextAcceptLanguage(_acceptLanguage);
		dataRecordCollectionResource.setContextCompany(_company);
		dataRecordCollectionResource.setContextHttpServletRequest(
			_httpServletRequest);
		dataRecordCollectionResource.setContextHttpServletResponse(
			_httpServletResponse);
		dataRecordCollectionResource.setContextUriInfo(_uriInfo);
		dataRecordCollectionResource.setContextUser(_user);
		dataRecordCollectionResource.setGroupLocalService(_groupLocalService);
		dataRecordCollectionResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<DataDefinitionResource>
		_dataDefinitionResourceComponentServiceObjects;
	private static ComponentServiceObjects<DataLayoutResource>
		_dataLayoutResourceComponentServiceObjects;
	private static ComponentServiceObjects<DataListViewResource>
		_dataListViewResourceComponentServiceObjects;
	private static ComponentServiceObjects<DataRecordResource>
		_dataRecordResourceComponentServiceObjects;
	private static ComponentServiceObjects<DataRecordCollectionResource>
		_dataRecordCollectionResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}