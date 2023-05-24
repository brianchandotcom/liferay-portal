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

package com.liferay.change.tracking.rest.internal.graphql.query.v1_0;

import com.liferay.change.tracking.rest.dto.v1_0.Publication;
import com.liferay.change.tracking.rest.resource.v1_0.PublicationResource;
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

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author David Truong
 * @generated
 */
@Generated("")
public class Query {

	public static void setPublicationResourceComponentServiceObjects(
		ComponentServiceObjects<PublicationResource>
			publicationResourceComponentServiceObjects) {

		_publicationResourceComponentServiceObjects =
			publicationResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {publications(page: ___, pageSize: ___, search: ___, sorts: ___, status: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public PublicationPage publications(
			@GraphQLName("status") Integer[] status,
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_publicationResourceComponentServiceObjects,
			this::_populateResourceContext,
			publicationResource -> new PublicationPage(
				publicationResource.getPublicationsPage(
					status, search, Pagination.of(page, pageSize),
					_sortsBiFunction.apply(publicationResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {publication(id: ___){actions, dateCreated, dateModified, dateScheduled, description, id, name, ownerName, status}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Publication publication(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_publicationResourceComponentServiceObjects,
			this::_populateResourceContext,
			publicationResource -> publicationResource.getPublication(id));
	}

	@GraphQLName("PublicationPage")
	public class PublicationPage {

		public PublicationPage(Page publicationPage) {
			actions = publicationPage.getActions();

			items = publicationPage.getItems();
			lastPage = publicationPage.getLastPage();
			page = publicationPage.getPage();
			pageSize = publicationPage.getPageSize();
			totalCount = publicationPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected java.util.Collection<Publication> items;

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
	}

	private static ComponentServiceObjects<PublicationResource>
		_publicationResourceComponentServiceObjects;

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