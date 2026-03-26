/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.rest.internal.graphql.query.v1_0;

import com.liferay.cookies.rest.dto.v1_0.CookiesConsentPreference;
import com.liferay.cookies.rest.resource.v1_0.CookiesConsentPreferenceResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;

import jakarta.annotation.Generated;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.ws.rs.core.UriInfo;

import java.util.Map;
import java.util.function.BiFunction;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Christopher Kian
 * @generated
 */
@Generated("")
public class Query {

	public static void
		setCookiesConsentPreferenceResourceComponentServiceObjects(
			ComponentServiceObjects<CookiesConsentPreferenceResource>
				cookiesConsentPreferenceResourceComponentServiceObjects) {

		_cookiesConsentPreferenceResourceComponentServiceObjects =
			cookiesConsentPreferenceResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {cookiesConsentPreferenceByName(name: ___){domain, expirationDate, id, name, userId, value}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the specified cookies consent preference of the user who made the request."
	)
	public CookiesConsentPreference cookiesConsentPreferenceByName(
			@GraphQLName("name") String name)
		throws Exception {

		return _applyComponentServiceObjects(
			_cookiesConsentPreferenceResourceComponentServiceObjects,
			this::_populateResourceContext,
			cookiesConsentPreferenceResource ->
				cookiesConsentPreferenceResource.
					getCookiesConsentPreferenceByName(name));
	}

	@GraphQLName("CookiesConsentPreferencePage")
	public class CookiesConsentPreferencePage {

		public CookiesConsentPreferencePage(Page cookiesConsentPreferencePage) {
			actions = cookiesConsentPreferencePage.getActions();

			items = cookiesConsentPreferencePage.getItems();
			lastPage = cookiesConsentPreferencePage.getLastPage();
			page = cookiesConsentPreferencePage.getPage();
			pageSize = cookiesConsentPreferencePage.getPageSize();
			totalCount = cookiesConsentPreferencePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<CookiesConsentPreference> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

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
		cookiesConsentPreferenceResource.setResourceActionLocalService(
			_resourceActionLocalService);
		cookiesConsentPreferenceResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		cookiesConsentPreferenceResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<CookiesConsentPreferenceResource>
		_cookiesConsentPreferenceResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction
		<Object, String, com.liferay.portal.kernel.search.filter.Filter>
			_filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private ResourceActionLocalService _resourceActionLocalService;
	private ResourcePermissionLocalService _resourcePermissionLocalService;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, com.liferay.portal.kernel.search.Sort[]>
		_sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}