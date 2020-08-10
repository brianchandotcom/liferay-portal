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

package com.liferay.app.builder.rest.resource.v1_0;

import com.liferay.app.builder.rest.dto.v1_0.App;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Locale;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.annotation.versioning.ProviderType;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/app-builder/v1.0
 *
 * @author Gabriel Albuquerque
 * @generated
 */
@Generated("")
@ProviderType
public interface AppResource {

	public static Builder builder() {
		return FactoryHolder.factory.create();
	}

	public Page<App> getAppsPage(
			Boolean active, String[] deploymentTypes, String keywords,
			String scope, Long[] userIds, Pagination pagination, Sort[] sorts)
		throws Exception;

	public void deleteApp(Long appId) throws Exception;

	public Response deleteAppBatch(String callbackURL, Object object)
		throws Exception;

	public App getApp(Long appId) throws Exception;

	public App putApp(Long appId, App app) throws Exception;

	public Response putAppBatch(String callbackURL, Object object)
		throws Exception;

	public Response putAppDeploy(Long appId) throws Exception;

	public Response putAppUndeploy(Long appId) throws Exception;

	public Page<App> getDataDefinitionAppsPage(
			Long dataDefinitionId, String keywords, String scope,
			Pagination pagination, Sort[] sorts)
		throws Exception;

	public App postDataDefinitionApp(Long dataDefinitionId, App app)
		throws Exception;

	public Page<App> getSiteAppsPage(
			Long siteId, String keywords, String scope, Pagination pagination,
			Sort[] sorts)
		throws Exception;

	public default void setContextAcceptLanguage(
		AcceptLanguage contextAcceptLanguage) {
	}

	public void setContextCompany(
		com.liferay.portal.kernel.model.Company contextCompany);

	public default void setContextHttpServletRequest(
		HttpServletRequest contextHttpServletRequest) {
	}

	public default void setContextHttpServletResponse(
		HttpServletResponse contextHttpServletResponse) {
	}

	public default void setContextUriInfo(UriInfo contextUriInfo) {
	}

	public void setContextUser(
		com.liferay.portal.kernel.model.User contextUser);

	public void setGroupLocalService(GroupLocalService groupLocalService);

	public void setRoleLocalService(RoleLocalService roleLocalService);

	public static class FactoryHolder {

		public static volatile Factory factory;

	}

	@ProviderType
	public interface Builder {

		public AppResource build();

		public Builder checkPermissions(boolean checkPermissions);

		public Builder httpServletRequest(
			HttpServletRequest httpServletRequest);

		public Builder preferredLocale(Locale preferredLocale);

		public Builder user(com.liferay.portal.kernel.model.User user);

	}

	@ProviderType
	public interface Factory {

		public Builder create();

	}

}