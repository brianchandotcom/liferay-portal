/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.rest.internal.graphql.mutation.v1_0;

import com.liferay.cookies.rest.dto.v1_0.CookiesConsentPreference;
import com.liferay.cookies.rest.resource.v1_0.CookiesConsentPreferenceResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineExportTaskResource;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import jakarta.annotation.Generated;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.function.BiFunction;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Christopher Kian
 * @generated
 */
@Generated("")
public class Mutation {

	public static void
		setCookiesConsentPreferenceResourceComponentServiceObjects(
			ComponentServiceObjects<CookiesConsentPreferenceResource>
				cookiesConsentPreferenceResourceComponentServiceObjects) {

		_cookiesConsentPreferenceResourceComponentServiceObjects =
			cookiesConsentPreferenceResourceComponentServiceObjects;
	}

	@GraphQLField(
		description = "Deletes the specified cookies consent preference of the user who made the request."
	)
	public boolean deleteCookiesConsentPreferenceByName(
			@GraphQLName("name") String name)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_cookiesConsentPreferenceResourceComponentServiceObjects,
			this::_populateResourceContext,
			cookiesConsentPreferenceResource ->
				cookiesConsentPreferenceResource.
					deleteCookiesConsentPreferenceByName(name));

		return true;
	}

	@GraphQLField(
		description = "Deletes all cookies consent preference entries of the user who made the request."
	)
	public boolean deleteCookiesConsentPreferences() throws Exception {
		_applyVoidComponentServiceObjects(
			_cookiesConsentPreferenceResourceComponentServiceObjects,
			this::_populateResourceContext,
			cookiesConsentPreferenceResource ->
				cookiesConsentPreferenceResource.
					deleteCookiesConsentPreferences());

		return true;
	}

	@GraphQLField(
		description = "Replaces the cookies consent preference with information sent in the request body. Any missing fields are deleted unless they are required."
	)
	public CookiesConsentPreference updateCookiesConsentPreference(
			@GraphQLName("cookiesConsentPreference") CookiesConsentPreference
				cookiesConsentPreference)
		throws Exception {

		return _applyComponentServiceObjects(
			_cookiesConsentPreferenceResourceComponentServiceObjects,
			this::_populateResourceContext,
			cookiesConsentPreferenceResource ->
				cookiesConsentPreferenceResource.putCookiesConsentPreference(
					cookiesConsentPreference));
	}

	@GraphQLField
	public Response updateCookiesConsentPreferenceBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_cookiesConsentPreferenceResourceComponentServiceObjects,
			this::_populateResourceContext,
			cookiesConsentPreferenceResource ->
				cookiesConsentPreferenceResource.
					putCookiesConsentPreferenceBatch(callbackURL, object));
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
			CookiesConsentPreferenceResource cookiesConsentPreferenceResource)
		throws Exception {

		cookiesConsentPreferenceResource.setContextAcceptLanguage(
			_acceptLanguage);
		cookiesConsentPreferenceResource.setContextCompany(_company);
		cookiesConsentPreferenceResource.setContextHttpServletRequest(
			_httpServletRequest);
		cookiesConsentPreferenceResource.setContextHttpServletResponse(
			_httpServletResponse);
		cookiesConsentPreferenceResource.setContextUriInfo(_uriInfo);
		cookiesConsentPreferenceResource.setContextUser(_user);
		cookiesConsentPreferenceResource.setGroupLocalService(
			_groupLocalService);
		cookiesConsentPreferenceResource.setRoleLocalService(_roleLocalService);

		cookiesConsentPreferenceResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		cookiesConsentPreferenceResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private static ComponentServiceObjects<CookiesConsentPreferenceResource>
		_cookiesConsentPreferenceResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, com.liferay.portal.kernel.search.Sort[]>
		_sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;
	private VulcanBatchEngineExportTaskResource
		_vulcanBatchEngineExportTaskResource;
	private VulcanBatchEngineImportTaskResource
		_vulcanBatchEngineImportTaskResource;

}