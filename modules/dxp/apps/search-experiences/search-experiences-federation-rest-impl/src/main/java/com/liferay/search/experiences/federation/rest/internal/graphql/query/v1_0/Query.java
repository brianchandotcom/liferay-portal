/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.federation.rest.internal.graphql.query.v1_0;

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
import com.liferay.search.experiences.federation.rest.dto.v1_0.SearchResult;
import com.liferay.search.experiences.federation.rest.resource.v1_0.SearchResultResource;

import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Bryan Engler
 * @generated
 */
@Generated("")
public class Query {

	public static void setSearchResultResourceComponentServiceObjects(
		ComponentServiceObjects<SearchResultResource>
			searchResultResourceComponentServiceObjects) {

		_searchResultResourceComponentServiceObjects =
			searchResultResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {federatedSearchResults(labels: ___, locale: ___, page: ___, query: ___, size: ___){results}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public SearchResult federatedSearchResults(
			@GraphQLName("labels") String labels,
			@GraphQLName("locale") String locale,
			@GraphQLName("query") String query,
			@GraphQLName("size") Integer size,
			@GraphQLName("page") Integer page)
		throws Exception {

		return _applyComponentServiceObjects(
			_searchResultResourceComponentServiceObjects,
			this::_populateResourceContext,
			searchResultResource ->
				searchResultResource.getFederatedSearchResults(
					labels, locale, query, size, page));
	}

	@GraphQLName("SearchResultPage")
	public class SearchResultPage {

		public SearchResultPage(Page searchResultPage) {
			actions = searchResultPage.getActions();

			items = searchResultPage.getItems();
			lastPage = searchResultPage.getLastPage();
			page = searchResultPage.getPage();
			pageSize = searchResultPage.getPageSize();
			totalCount = searchResultPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<SearchResult> items;

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
			SearchResultResource searchResultResource)
		throws Exception {

		searchResultResource.setContextAcceptLanguage(_acceptLanguage);
		searchResultResource.setContextCompany(_company);
		searchResultResource.setContextHttpServletRequest(_httpServletRequest);
		searchResultResource.setContextHttpServletResponse(
			_httpServletResponse);
		searchResultResource.setContextUriInfo(_uriInfo);
		searchResultResource.setContextUser(_user);
		searchResultResource.setGroupLocalService(_groupLocalService);
		searchResultResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<SearchResultResource>
		_searchResultResourceComponentServiceObjects;

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