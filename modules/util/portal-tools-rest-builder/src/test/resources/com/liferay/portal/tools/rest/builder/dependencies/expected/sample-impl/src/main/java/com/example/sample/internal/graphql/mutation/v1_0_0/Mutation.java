/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.example.sample.internal.graphql.mutation.v1_0_0;

import com.example.sample.dto.v1_0_0.PolymorphicSchema;
import com.example.sample.resource.v1_0_0.PolymorphicSchemaResource;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author John Doe
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setPolymorphicSchemaResourceComponentServiceObjects(
		ComponentServiceObjects<PolymorphicSchemaResource>
			polymorphicSchemaResourceComponentServiceObjects) {

		_polymorphicSchemaResourceComponentServiceObjects =
			polymorphicSchemaResourceComponentServiceObjects;
	}

	@GraphQLField
	public PolymorphicSchema createPolymorphicSchema(
			@GraphQLName("polymorphicSchema") PolymorphicSchema
				polymorphicSchema)
		throws Exception {

		return _applyComponentServiceObjects(
			_polymorphicSchemaResourceComponentServiceObjects,
			this::_populateResourceContext,
			polymorphicSchemaResource ->
				polymorphicSchemaResource.postPolymorphicSchema(
					polymorphicSchema));
	}

	@GraphQLField
	public Response createPolymorphicSchemaBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_polymorphicSchemaResourceComponentServiceObjects,
			this::_populateResourceContext,
			polymorphicSchemaResource ->
				polymorphicSchemaResource.postPolymorphicSchemaBatch(
					callbackURL, object));
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
			PolymorphicSchemaResource polymorphicSchemaResource)
		throws Exception {

		polymorphicSchemaResource.setContextAcceptLanguage(_acceptLanguage);
		polymorphicSchemaResource.setContextCompany(_company);
		polymorphicSchemaResource.setContextHttpServletRequest(
			_httpServletRequest);
		polymorphicSchemaResource.setContextHttpServletResponse(
			_httpServletResponse);
		polymorphicSchemaResource.setContextUriInfo(_uriInfo);
		polymorphicSchemaResource.setContextUser(_user);
		polymorphicSchemaResource.setGroupLocalService(_groupLocalService);
		polymorphicSchemaResource.setRoleLocalService(_roleLocalService);

		polymorphicSchemaResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private static ComponentServiceObjects<PolymorphicSchemaResource>
		_polymorphicSchemaResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;
	private VulcanBatchEngineImportTaskResource
		_vulcanBatchEngineImportTaskResource;

}