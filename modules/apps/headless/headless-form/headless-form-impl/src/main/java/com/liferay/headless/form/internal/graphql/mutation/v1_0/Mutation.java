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

package com.liferay.headless.form.internal.graphql.mutation.v1_0;

import com.liferay.headless.form.dto.v1_0.FormContext;
import com.liferay.headless.form.dto.v1_0.FormDocument;
import com.liferay.headless.form.dto.v1_0.FormRecord;
import com.liferay.headless.form.resource.v1_0.FormDocumentResource;
import com.liferay.headless.form.resource.v1_0.FormRecordResource;
import com.liferay.headless.form.resource.v1_0.FormResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setFormResourceComponentServiceObjects(
		ComponentServiceObjects<FormResource>
			formResourceComponentServiceObjects) {

		_formResourceComponentServiceObjects =
			formResourceComponentServiceObjects;
	}

	public static void setFormDocumentResourceComponentServiceObjects(
		ComponentServiceObjects<FormDocumentResource>
			formDocumentResourceComponentServiceObjects) {

		_formDocumentResourceComponentServiceObjects =
			formDocumentResourceComponentServiceObjects;
	}

	public static void setFormRecordResourceComponentServiceObjects(
		ComponentServiceObjects<FormRecordResource>
			formRecordResourceComponentServiceObjects) {

		_formRecordResourceComponentServiceObjects =
			formRecordResourceComponentServiceObjects;
	}

	@GraphQLField
	public FormContext createFormEvaluateContext(
			@GraphQLName("formId") Long formId,
			@GraphQLName("formContext") FormContext formContext)
		throws Exception {

		return _applyComponentServiceObjects(
			_formResourceComponentServiceObjects,
			this::_populateResourceContext,
			formResource -> formResource.postFormEvaluateContext(
				formId, formContext));
	}

	@GraphQLField
	@GraphQLName(
		value = "postFormFormDocumentFormIdMultipartBody", description = "null"
	)
	public FormDocument createFormFormDocument(
			@GraphQLName("formId") Long formId,
			@GraphQLName("multipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_formResourceComponentServiceObjects,
			this::_populateResourceContext,
			formResource -> formResource.postFormFormDocument(
				formId, multipartBody));
	}

	@GraphQLField
	public boolean deleteFormDocument(
			@GraphQLName("formDocumentId") Long formDocumentId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_formDocumentResourceComponentServiceObjects,
			this::_populateResourceContext,
			formDocumentResource -> formDocumentResource.deleteFormDocument(
				formDocumentId));

		return true;
	}

	@GraphQLField
	public Response deleteFormDocumentBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_formDocumentResourceComponentServiceObjects,
			this::_populateResourceContext,
			formDocumentResource ->
				formDocumentResource.deleteFormDocumentBatch(
					callbackURL, object));
	}

	@GraphQLField
	public FormRecord updateFormRecord(
			@GraphQLName("formRecordId") Long formRecordId,
			@GraphQLName("formRecord") FormRecord formRecord)
		throws Exception {

		return _applyComponentServiceObjects(
			_formRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			formRecordResource -> formRecordResource.putFormRecord(
				formRecordId, formRecord));
	}

	@GraphQLField
	public Response updateFormRecordBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_formRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			formRecordResource -> formRecordResource.putFormRecordBatch(
				callbackURL, object));
	}

	@GraphQLField
	public FormRecord createFormFormRecord(
			@GraphQLName("formId") Long formId,
			@GraphQLName("formRecord") FormRecord formRecord)
		throws Exception {

		return _applyComponentServiceObjects(
			_formRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			formRecordResource -> formRecordResource.postFormFormRecord(
				formId, formRecord));
	}

	@GraphQLField
	public Response createFormFormRecordBatch(
			@GraphQLName("formId") Long formId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_formRecordResourceComponentServiceObjects,
			this::_populateResourceContext,
			formRecordResource -> formRecordResource.postFormFormRecordBatch(
				formId, callbackURL, object));
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

	private void _populateResourceContext(FormResource formResource)
		throws Exception {

		formResource.setContextAcceptLanguage(_acceptLanguage);
		formResource.setContextCompany(_company);
		formResource.setContextHttpServletRequest(_httpServletRequest);
		formResource.setContextHttpServletResponse(_httpServletResponse);
		formResource.setContextUriInfo(_uriInfo);
		formResource.setContextUser(_user);
		formResource.setGroupLocalService(_groupLocalService);
		formResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			FormDocumentResource formDocumentResource)
		throws Exception {

		formDocumentResource.setContextAcceptLanguage(_acceptLanguage);
		formDocumentResource.setContextCompany(_company);
		formDocumentResource.setContextHttpServletRequest(_httpServletRequest);
		formDocumentResource.setContextHttpServletResponse(
			_httpServletResponse);
		formDocumentResource.setContextUriInfo(_uriInfo);
		formDocumentResource.setContextUser(_user);
		formDocumentResource.setGroupLocalService(_groupLocalService);
		formDocumentResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(FormRecordResource formRecordResource)
		throws Exception {

		formRecordResource.setContextAcceptLanguage(_acceptLanguage);
		formRecordResource.setContextCompany(_company);
		formRecordResource.setContextHttpServletRequest(_httpServletRequest);
		formRecordResource.setContextHttpServletResponse(_httpServletResponse);
		formRecordResource.setContextUriInfo(_uriInfo);
		formRecordResource.setContextUser(_user);
		formRecordResource.setGroupLocalService(_groupLocalService);
		formRecordResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<FormResource>
		_formResourceComponentServiceObjects;
	private static ComponentServiceObjects<FormDocumentResource>
		_formDocumentResourceComponentServiceObjects;
	private static ComponentServiceObjects<FormRecordResource>
		_formRecordResourceComponentServiceObjects;

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