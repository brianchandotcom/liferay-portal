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

package com.liferay.app.builder.rest.internal.graphql.query.v1_0;

import com.liferay.app.builder.rest.dto.v1_0.App;
import com.liferay.app.builder.rest.dto.v1_0.AppModelPermission;
import com.liferay.app.builder.rest.resource.v1_0.AppModelPermissionResource;
import com.liferay.app.builder.rest.resource.v1_0.AppResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotEmpty;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Gabriel Albuquerque
 * @generated
 */
@Generated("")
public class Query {

	public static void setAppResourceComponentServiceObjects(
		ComponentServiceObjects<AppResource>
			appResourceComponentServiceObjects) {

		_appResourceComponentServiceObjects =
			appResourceComponentServiceObjects;
	}

	public static void setAppModelPermissionResourceComponentServiceObjects(
		ComponentServiceObjects<AppModelPermissionResource>
			appModelPermissionResourceComponentServiceObjects) {

		_appModelPermissionResourceComponentServiceObjects =
			appModelPermissionResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {app(appId: ___){appDeployments, dataDefinitionId, dataLayoutId, dataListViewId, dateCreated, dateModified, id, name, siteId, status, userId}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public App app(@GraphQLName("appId") Long appId) throws Exception {
		return _applyComponentServiceObjects(
			_appResourceComponentServiceObjects, this::_populateResourceContext,
			appResource -> appResource.getApp(appId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {dataDefinitionApps(dataDefinitionId: ___, keywords: ___, page: ___, pageSize: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AppPage dataDefinitionApps(
			@GraphQLName("dataDefinitionId") Long dataDefinitionId,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_appResourceComponentServiceObjects, this::_populateResourceContext,
			appResource -> new AppPage(
				appResource.getDataDefinitionAppsPage(
					dataDefinitionId, keywords, Pagination.of(page, pageSize),
					_sortsBiFunction.apply(appResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {apps(keywords: ___, page: ___, pageSize: ___, siteKey: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AppPage apps(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("keywords") String keywords,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_appResourceComponentServiceObjects, this::_populateResourceContext,
			appResource -> new AppPage(
				appResource.getSiteAppsPage(
					Long.valueOf(siteKey), keywords,
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(appResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {appModelPermissions(roleNames: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public AppModelPermissionPage appModelPermissions(
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_appModelPermissionResourceComponentServiceObjects,
			this::_populateResourceContext,
			appModelPermissionResource -> new AppModelPermissionPage(
				appModelPermissionResource.getAppModelPermissionsPage(
					roleNames)));
	}

	@GraphQLName("AppPage")
	public class AppPage {

		public AppPage(Page appPage) {
			items = appPage.getItems();
			lastPage = appPage.getLastPage();
			page = appPage.getPage();
			pageSize = appPage.getPageSize();
			totalCount = appPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<App> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("AppModelPermissionPage")
	public class AppModelPermissionPage {

		public AppModelPermissionPage(Page appModelPermissionPage) {
			items = appModelPermissionPage.getItems();
			lastPage = appModelPermissionPage.getLastPage();
			page = appModelPermissionPage.getPage();
			pageSize = appModelPermissionPage.getPageSize();
			totalCount = appModelPermissionPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<AppModelPermission> items;

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

	private void _populateResourceContext(AppResource appResource)
		throws Exception {

		appResource.setContextAcceptLanguage(_acceptLanguage);
		appResource.setContextCompany(_company);
		appResource.setContextHttpServletRequest(_httpServletRequest);
		appResource.setContextHttpServletResponse(_httpServletResponse);
		appResource.setContextUriInfo(_uriInfo);
		appResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			AppModelPermissionResource appModelPermissionResource)
		throws Exception {

		appModelPermissionResource.setContextAcceptLanguage(_acceptLanguage);
		appModelPermissionResource.setContextCompany(_company);
		appModelPermissionResource.setContextHttpServletRequest(
			_httpServletRequest);
		appModelPermissionResource.setContextHttpServletResponse(
			_httpServletResponse);
		appModelPermissionResource.setContextUriInfo(_uriInfo);
		appModelPermissionResource.setContextUser(_user);
	}

	private static ComponentServiceObjects<AppResource>
		_appResourceComponentServiceObjects;
	private static ComponentServiceObjects<AppModelPermissionResource>
		_appModelPermissionResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private com.liferay.portal.kernel.model.Company _company;
	private com.liferay.portal.kernel.model.User _user;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private UriInfo _uriInfo;

}