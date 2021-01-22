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

package com.liferay.headless.admin.content.internal.graphql.query.v1_0;

import com.liferay.headless.admin.content.dto.v1_0.DisplayPageTemplate;
import com.liferay.headless.admin.content.resource.v1_0.DisplayPageTemplateResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.constraints.NotEmpty;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Query {

	public static void setDisplayPageTemplateResourceComponentServiceObjects(
		ComponentServiceObjects<DisplayPageTemplateResource>
			displayPageTemplateResourceComponentServiceObjects) {

		_displayPageTemplateResourceComponentServiceObjects =
			displayPageTemplateResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {displayPageTemplates(page: ___, pageSize: ___, siteKey: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the display page templates of a site"
	)
	public DisplayPageTemplatePage displayPageTemplates(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_displayPageTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			displayPageTemplateResource -> new DisplayPageTemplatePage(
				displayPageTemplateResource.getSiteDisplayPageTemplatesPage(
					Long.valueOf(siteKey), Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						displayPageTemplateResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {displayPageTemplate(displayPageTemplateKey: ___, siteKey: ___){actions, availableLanguages, creator, customFields, dateCreated, dateModified, displayPageTemplateKey, displayPageTemplateSettings, markedAsDefault, pageDefinition, siteId, title, uuid}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves a display page template of a site")
	public DisplayPageTemplate displayPageTemplate(
			@GraphQLName("siteKey") @NotEmpty String siteKey,
			@GraphQLName("displayPageTemplateKey") String
				displayPageTemplateKey)
		throws Exception {

		return _applyComponentServiceObjects(
			_displayPageTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			displayPageTemplateResource ->
				displayPageTemplateResource.getSiteDisplayPageTemplate(
					Long.valueOf(siteKey), displayPageTemplateKey));
	}

	@GraphQLName("DisplayPageTemplatePage")
	public class DisplayPageTemplatePage {

		public DisplayPageTemplatePage(Page displayPageTemplatePage) {
			actions = displayPageTemplatePage.getActions();

			items = displayPageTemplatePage.getItems();
			lastPage = displayPageTemplatePage.getLastPage();
			page = displayPageTemplatePage.getPage();
			pageSize = displayPageTemplatePage.getPageSize();
			totalCount = displayPageTemplatePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<DisplayPageTemplate> items;

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
			DisplayPageTemplateResource displayPageTemplateResource)
		throws Exception {

		displayPageTemplateResource.setContextAcceptLanguage(_acceptLanguage);
		displayPageTemplateResource.setContextCompany(_company);
		displayPageTemplateResource.setContextHttpServletRequest(
			_httpServletRequest);
		displayPageTemplateResource.setContextHttpServletResponse(
			_httpServletResponse);
		displayPageTemplateResource.setContextUriInfo(_uriInfo);
		displayPageTemplateResource.setContextUser(_user);
		displayPageTemplateResource.setGroupLocalService(_groupLocalService);
		displayPageTemplateResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<DisplayPageTemplateResource>
		_displayPageTemplateResourceComponentServiceObjects;

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

}