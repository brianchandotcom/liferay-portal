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

import com.liferay.change.tracking.rest.dto.v1_0.Publication;
import com.liferay.change.tracking.rest.resource.v1_0.PublicationResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
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

	public static void setPublicationResourceComponentServiceObjects(
		ComponentServiceObjects<PublicationResource>
			publicationResourceComponentServiceObjects) {

		_publicationResourceComponentServiceObjects =
			publicationResourceComponentServiceObjects;
	}

	@GraphQLField
	public Response createPublicationsPageExportBatch(
			@GraphQLName("status") Integer[] status,
			@GraphQLName("search") String search,
			@GraphQLName("sort") String sortsString,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_publicationResourceComponentServiceObjects,
			this::_populateResourceContext,
			publicationResource ->
				publicationResource.postPublicationsPageExportBatch(
					status, search,
					_sortsBiFunction.apply(publicationResource, sortsString),
					callbackURL, contentType, fieldNames));
	}

	@GraphQLField
	public Publication createPublication(
			@GraphQLName("publication") Publication publication)
		throws Exception {

		return _applyComponentServiceObjects(
			_publicationResourceComponentServiceObjects,
			this::_populateResourceContext,
			publicationResource -> publicationResource.postPublication(
				publication));
	}

	@GraphQLField
	public Response createPublicationBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_publicationResourceComponentServiceObjects,
			this::_populateResourceContext,
			publicationResource -> publicationResource.postPublicationBatch(
				callbackURL, object));
	}

	@GraphQLField
	public boolean deletePublication(@GraphQLName("id") Long id)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_publicationResourceComponentServiceObjects,
			this::_populateResourceContext,
			publicationResource -> publicationResource.deletePublication(id));

		return true;
	}

	@GraphQLField
	public Response deletePublicationBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_publicationResourceComponentServiceObjects,
			this::_populateResourceContext,
			publicationResource -> publicationResource.deletePublicationBatch(
				callbackURL, object));
	}

	@GraphQLField
	public Publication patchPublication(
			@GraphQLName("id") Long id,
			@GraphQLName("publication") Publication publication)
		throws Exception {

		return _applyComponentServiceObjects(
			_publicationResourceComponentServiceObjects,
			this::_populateResourceContext,
			publicationResource -> publicationResource.patchPublication(
				id, publication));
	}

	@GraphQLField
	public Publication updatePublication(
			@GraphQLName("id") Long id,
			@GraphQLName("publication") Publication publication)
		throws Exception {

		return _applyComponentServiceObjects(
			_publicationResourceComponentServiceObjects,
			this::_populateResourceContext,
			publicationResource -> publicationResource.putPublication(
				id, publication));
	}

	@GraphQLField
	public Response updatePublicationBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_publicationResourceComponentServiceObjects,
			this::_populateResourceContext,
			publicationResource -> publicationResource.putPublicationBatch(
				callbackURL, object));
	}

	@GraphQLField
	public boolean createPublicationCheckout(@GraphQLName("id") Long id)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_publicationResourceComponentServiceObjects,
			this::_populateResourceContext,
			publicationResource -> publicationResource.postPublicationCheckout(
				id));

		return true;
	}

	@GraphQLField
	public boolean createPublicationPublish(@GraphQLName("id") Long id)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_publicationResourceComponentServiceObjects,
			this::_populateResourceContext,
			publicationResource -> publicationResource.postPublicationPublish(
				id));

		return true;
	}

	@GraphQLField
	public boolean createPublicationSchedulePublish(
			@GraphQLName("id") Long id,
			@GraphQLName("publishDate") Date publishDate)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_publicationResourceComponentServiceObjects,
			this::_populateResourceContext,
			publicationResource ->
				publicationResource.postPublicationSchedulePublish(
					id, publishDate));

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
			PublicationResource publicationResource)
		throws Exception {

		publicationResource.setContextAcceptLanguage(_acceptLanguage);
		publicationResource.setContextCompany(_company);
		publicationResource.setContextHttpServletRequest(_httpServletRequest);
		publicationResource.setContextHttpServletResponse(_httpServletResponse);
		publicationResource.setContextUriInfo(_uriInfo);
		publicationResource.setContextUser(_user);
		publicationResource.setGroupLocalService(_groupLocalService);
		publicationResource.setRoleLocalService(_roleLocalService);

		publicationResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		publicationResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private static ComponentServiceObjects<PublicationResource>
		_publicationResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
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