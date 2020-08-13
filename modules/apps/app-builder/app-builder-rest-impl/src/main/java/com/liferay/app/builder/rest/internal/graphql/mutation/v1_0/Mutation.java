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

package com.liferay.app.builder.rest.internal.graphql.mutation.v1_0;

import com.liferay.app.builder.rest.dto.v1_0.App;
import com.liferay.app.builder.rest.resource.v1_0.AppResource;
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

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Gabriel Albuquerque
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setAppResourceComponentServiceObjects(
		ComponentServiceObjects<AppResource>
			appResourceComponentServiceObjects) {

		_appResourceComponentServiceObjects =
			appResourceComponentServiceObjects;
	}

	@GraphQLField
	public boolean deleteApp(@GraphQLName("appId") Long appId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_appResourceComponentServiceObjects, this::_populateResourceContext,
			appResource -> appResource.deleteApp(appId));

		return true;
	}

	@GraphQLField
	public Response deleteAppBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_appResourceComponentServiceObjects, this::_populateResourceContext,
			appResource -> appResource.deleteAppBatch(callbackURL, object));
	}

	@GraphQLField
	public App updateApp(
			@GraphQLName("appId") Long appId, @GraphQLName("app") App app)
		throws Exception {

		return _applyComponentServiceObjects(
			_appResourceComponentServiceObjects, this::_populateResourceContext,
			appResource -> appResource.putApp(appId, app));
	}

	@GraphQLField
	public Response updateAppBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_appResourceComponentServiceObjects, this::_populateResourceContext,
			appResource -> appResource.putAppBatch(callbackURL, object));
	}

	@GraphQLField
	public Response updateAppDeploy(@GraphQLName("appId") Long appId)
		throws Exception {

		return _applyComponentServiceObjects(
			_appResourceComponentServiceObjects, this::_populateResourceContext,
			appResource -> appResource.putAppDeploy(appId));
	}

	@GraphQLField
	public Response updateAppUndeploy(@GraphQLName("appId") Long appId)
		throws Exception {

		return _applyComponentServiceObjects(
			_appResourceComponentServiceObjects, this::_populateResourceContext,
			appResource -> appResource.putAppUndeploy(appId));
	}

	@GraphQLField
	public App createDataDefinitionApp(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("app") App app)
		throws Exception {

		return _applyComponentServiceObjects(
			_appResourceComponentServiceObjects, this::_populateResourceContext,
			appResource -> appResource.postDataDefinitionApp(
				dataDefinitionId, app));
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

	private void _populateResourceContext(AppResource appResource)
		throws Exception {

		appResource.setContextAcceptLanguage(_acceptLanguage);
		appResource.setContextCompany(_company);
		appResource.setContextHttpServletRequest(_httpServletRequest);
		appResource.setContextHttpServletResponse(_httpServletResponse);
		appResource.setContextUriInfo(_uriInfo);
		appResource.setContextUser(_user);
		appResource.setGroupLocalService(_groupLocalService);
		appResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<AppResource>
		_appResourceComponentServiceObjects;

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