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

package com.liferay.search.experiences.rest.internal.graphql.query.v1_0;

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
import com.liferay.search.experiences.rest.dto.v1_0.KeywordQueryContributors;
import com.liferay.search.experiences.rest.dto.v1_0.ModelPrefilterContributors;
import com.liferay.search.experiences.rest.dto.v1_0.QueryPrefilterContributors;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.SearchableAssetNames;
import com.liferay.search.experiences.rest.dto.v1_0.SearchableAssetNamesDisplay;
import com.liferay.search.experiences.rest.resource.v1_0.KeywordQueryContributorsResource;
import com.liferay.search.experiences.rest.resource.v1_0.ModelPrefilterContributorsResource;
import com.liferay.search.experiences.rest.resource.v1_0.QueryPrefilterContributorsResource;
import com.liferay.search.experiences.rest.resource.v1_0.SXPBlueprintResource;
import com.liferay.search.experiences.rest.resource.v1_0.SXPElementResource;
import com.liferay.search.experiences.rest.resource.v1_0.SearchableAssetNamesDisplayResource;
import com.liferay.search.experiences.rest.resource.v1_0.SearchableAssetNamesResource;

import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class Query {

	public static void
		setKeywordQueryContributorsResourceComponentServiceObjects(
			ComponentServiceObjects<KeywordQueryContributorsResource>
				keywordQueryContributorsResourceComponentServiceObjects) {

		_keywordQueryContributorsResourceComponentServiceObjects =
			keywordQueryContributorsResourceComponentServiceObjects;
	}

	public static void
		setModelPrefilterContributorsResourceComponentServiceObjects(
			ComponentServiceObjects<ModelPrefilterContributorsResource>
				modelPrefilterContributorsResourceComponentServiceObjects) {

		_modelPrefilterContributorsResourceComponentServiceObjects =
			modelPrefilterContributorsResourceComponentServiceObjects;
	}

	public static void
		setQueryPrefilterContributorsResourceComponentServiceObjects(
			ComponentServiceObjects<QueryPrefilterContributorsResource>
				queryPrefilterContributorsResourceComponentServiceObjects) {

		_queryPrefilterContributorsResourceComponentServiceObjects =
			queryPrefilterContributorsResourceComponentServiceObjects;
	}

	public static void setSXPBlueprintResourceComponentServiceObjects(
		ComponentServiceObjects<SXPBlueprintResource>
			sxpBlueprintResourceComponentServiceObjects) {

		_sxpBlueprintResourceComponentServiceObjects =
			sxpBlueprintResourceComponentServiceObjects;
	}

	public static void setSXPElementResourceComponentServiceObjects(
		ComponentServiceObjects<SXPElementResource>
			sxpElementResourceComponentServiceObjects) {

		_sxpElementResourceComponentServiceObjects =
			sxpElementResourceComponentServiceObjects;
	}

	public static void setSearchableAssetNamesResourceComponentServiceObjects(
		ComponentServiceObjects<SearchableAssetNamesResource>
			searchableAssetNamesResourceComponentServiceObjects) {

		_searchableAssetNamesResourceComponentServiceObjects =
			searchableAssetNamesResourceComponentServiceObjects;
	}

	public static void
		setSearchableAssetNamesDisplayResourceComponentServiceObjects(
			ComponentServiceObjects<SearchableAssetNamesDisplayResource>
				searchableAssetNamesDisplayResourceComponentServiceObjects) {

		_searchableAssetNamesDisplayResourceComponentServiceObjects =
			searchableAssetNamesDisplayResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {keywordQueryContributors{classNames}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public KeywordQueryContributors keywordQueryContributors()
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordQueryContributorsResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordQueryContributorsResource ->
				keywordQueryContributorsResource.getKeywordQueryContributors());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {modelPrefilterContributors{classNames}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ModelPrefilterContributors modelPrefilterContributors()
		throws Exception {

		return _applyComponentServiceObjects(
			_modelPrefilterContributorsResourceComponentServiceObjects,
			this::_populateResourceContext,
			modelPrefilterContributorsResource ->
				modelPrefilterContributorsResource.
					getModelPrefilterContributors());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {queryPrefilterContributors{classNames}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public QueryPrefilterContributors queryPrefilterContributors()
		throws Exception {

		return _applyComponentServiceObjects(
			_queryPrefilterContributorsResourceComponentServiceObjects,
			this::_populateResourceContext,
			queryPrefilterContributorsResource ->
				queryPrefilterContributorsResource.
					getQueryPrefilterContributors());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {sXPBlueprints(page: ___, pageSize: ___, search: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public SXPBlueprintPage sXPBlueprints(
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_sxpBlueprintResourceComponentServiceObjects,
			this::_populateResourceContext,
			sxpBlueprintResource -> new SXPBlueprintPage(
				sxpBlueprintResource.getSXPBlueprintsPage(
					search, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {sXPBlueprint(sxpBlueprintId: ___){configuration, description, id, title}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public SXPBlueprint sXPBlueprint(
			@GraphQLName("sxpBlueprintId") Long sxpBlueprintId)
		throws Exception {

		return _applyComponentServiceObjects(
			_sxpBlueprintResourceComponentServiceObjects,
			this::_populateResourceContext,
			sxpBlueprintResource -> sxpBlueprintResource.getSXPBlueprint(
				sxpBlueprintId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {sXPElements(page: ___, pageSize: ___, search: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public SXPElementPage sXPElements(
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_sxpElementResourceComponentServiceObjects,
			this::_populateResourceContext,
			sxpElementResource -> new SXPElementPage(
				sxpElementResource.getSXPElementsPage(
					search, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {sXPElement(sxpElementId: ___){description, id, title}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public SXPElement sXPElement(@GraphQLName("sxpElementId") Long sxpElementId)
		throws Exception {

		return _applyComponentServiceObjects(
			_sxpElementResourceComponentServiceObjects,
			this::_populateResourceContext,
			sxpElementResource -> sxpElementResource.getSXPElement(
				sxpElementId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {searchableAssetNames{classNames}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public SearchableAssetNames searchableAssetNames() throws Exception {
		return _applyComponentServiceObjects(
			_searchableAssetNamesResourceComponentServiceObjects,
			this::_populateResourceContext,
			searchableAssetNamesResource ->
				searchableAssetNamesResource.getSearchableAssetNames());
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {searchableAssetNameLanguage(languageId: ___){searchableAssetNames}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public SearchableAssetNamesDisplay searchableAssetNameLanguage(
			@GraphQLName("languageId") String languageId)
		throws Exception {

		return _applyComponentServiceObjects(
			_searchableAssetNamesDisplayResourceComponentServiceObjects,
			this::_populateResourceContext,
			searchableAssetNamesDisplayResource ->
				searchableAssetNamesDisplayResource.
					getSearchableAssetNameLanguage(languageId));
	}

	@GraphQLName("KeywordQueryContributorsPage")
	public class KeywordQueryContributorsPage {

		public KeywordQueryContributorsPage(Page keywordQueryContributorsPage) {
			actions = keywordQueryContributorsPage.getActions();

			items = keywordQueryContributorsPage.getItems();
			lastPage = keywordQueryContributorsPage.getLastPage();
			page = keywordQueryContributorsPage.getPage();
			pageSize = keywordQueryContributorsPage.getPageSize();
			totalCount = keywordQueryContributorsPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<KeywordQueryContributors> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ModelPrefilterContributorsPage")
	public class ModelPrefilterContributorsPage {

		public ModelPrefilterContributorsPage(
			Page modelPrefilterContributorsPage) {

			actions = modelPrefilterContributorsPage.getActions();

			items = modelPrefilterContributorsPage.getItems();
			lastPage = modelPrefilterContributorsPage.getLastPage();
			page = modelPrefilterContributorsPage.getPage();
			pageSize = modelPrefilterContributorsPage.getPageSize();
			totalCount = modelPrefilterContributorsPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<ModelPrefilterContributors> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("QueryPrefilterContributorsPage")
	public class QueryPrefilterContributorsPage {

		public QueryPrefilterContributorsPage(
			Page queryPrefilterContributorsPage) {

			actions = queryPrefilterContributorsPage.getActions();

			items = queryPrefilterContributorsPage.getItems();
			lastPage = queryPrefilterContributorsPage.getLastPage();
			page = queryPrefilterContributorsPage.getPage();
			pageSize = queryPrefilterContributorsPage.getPageSize();
			totalCount = queryPrefilterContributorsPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<QueryPrefilterContributors> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("SXPBlueprintPage")
	public class SXPBlueprintPage {

		public SXPBlueprintPage(Page sxpBlueprintPage) {
			actions = sxpBlueprintPage.getActions();

			items = sxpBlueprintPage.getItems();
			lastPage = sxpBlueprintPage.getLastPage();
			page = sxpBlueprintPage.getPage();
			pageSize = sxpBlueprintPage.getPageSize();
			totalCount = sxpBlueprintPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<SXPBlueprint> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("SXPElementPage")
	public class SXPElementPage {

		public SXPElementPage(Page sxpElementPage) {
			actions = sxpElementPage.getActions();

			items = sxpElementPage.getItems();
			lastPage = sxpElementPage.getLastPage();
			page = sxpElementPage.getPage();
			pageSize = sxpElementPage.getPageSize();
			totalCount = sxpElementPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<SXPElement> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("SearchableAssetNamesPage")
	public class SearchableAssetNamesPage {

		public SearchableAssetNamesPage(Page searchableAssetNamesPage) {
			actions = searchableAssetNamesPage.getActions();

			items = searchableAssetNamesPage.getItems();
			lastPage = searchableAssetNamesPage.getLastPage();
			page = searchableAssetNamesPage.getPage();
			pageSize = searchableAssetNamesPage.getPageSize();
			totalCount = searchableAssetNamesPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<SearchableAssetNames> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("SearchableAssetNamesDisplayPage")
	public class SearchableAssetNamesDisplayPage {

		public SearchableAssetNamesDisplayPage(
			Page searchableAssetNamesDisplayPage) {

			actions = searchableAssetNamesDisplayPage.getActions();

			items = searchableAssetNamesDisplayPage.getItems();
			lastPage = searchableAssetNamesDisplayPage.getLastPage();
			page = searchableAssetNamesDisplayPage.getPage();
			pageSize = searchableAssetNamesDisplayPage.getPageSize();
			totalCount = searchableAssetNamesDisplayPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<SearchableAssetNamesDisplay> items;

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
			KeywordQueryContributorsResource keywordQueryContributorsResource)
		throws Exception {

		keywordQueryContributorsResource.setContextAcceptLanguage(
			_acceptLanguage);
		keywordQueryContributorsResource.setContextCompany(_company);
		keywordQueryContributorsResource.setContextHttpServletRequest(
			_httpServletRequest);
		keywordQueryContributorsResource.setContextHttpServletResponse(
			_httpServletResponse);
		keywordQueryContributorsResource.setContextUriInfo(_uriInfo);
		keywordQueryContributorsResource.setContextUser(_user);
		keywordQueryContributorsResource.setGroupLocalService(
			_groupLocalService);
		keywordQueryContributorsResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ModelPrefilterContributorsResource
				modelPrefilterContributorsResource)
		throws Exception {

		modelPrefilterContributorsResource.setContextAcceptLanguage(
			_acceptLanguage);
		modelPrefilterContributorsResource.setContextCompany(_company);
		modelPrefilterContributorsResource.setContextHttpServletRequest(
			_httpServletRequest);
		modelPrefilterContributorsResource.setContextHttpServletResponse(
			_httpServletResponse);
		modelPrefilterContributorsResource.setContextUriInfo(_uriInfo);
		modelPrefilterContributorsResource.setContextUser(_user);
		modelPrefilterContributorsResource.setGroupLocalService(
			_groupLocalService);
		modelPrefilterContributorsResource.setRoleLocalService(
			_roleLocalService);
	}

	private void _populateResourceContext(
			QueryPrefilterContributorsResource
				queryPrefilterContributorsResource)
		throws Exception {

		queryPrefilterContributorsResource.setContextAcceptLanguage(
			_acceptLanguage);
		queryPrefilterContributorsResource.setContextCompany(_company);
		queryPrefilterContributorsResource.setContextHttpServletRequest(
			_httpServletRequest);
		queryPrefilterContributorsResource.setContextHttpServletResponse(
			_httpServletResponse);
		queryPrefilterContributorsResource.setContextUriInfo(_uriInfo);
		queryPrefilterContributorsResource.setContextUser(_user);
		queryPrefilterContributorsResource.setGroupLocalService(
			_groupLocalService);
		queryPrefilterContributorsResource.setRoleLocalService(
			_roleLocalService);
	}

	private void _populateResourceContext(
			SXPBlueprintResource sxpBlueprintResource)
		throws Exception {

		sxpBlueprintResource.setContextAcceptLanguage(_acceptLanguage);
		sxpBlueprintResource.setContextCompany(_company);
		sxpBlueprintResource.setContextHttpServletRequest(_httpServletRequest);
		sxpBlueprintResource.setContextHttpServletResponse(
			_httpServletResponse);
		sxpBlueprintResource.setContextUriInfo(_uriInfo);
		sxpBlueprintResource.setContextUser(_user);
		sxpBlueprintResource.setGroupLocalService(_groupLocalService);
		sxpBlueprintResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(SXPElementResource sxpElementResource)
		throws Exception {

		sxpElementResource.setContextAcceptLanguage(_acceptLanguage);
		sxpElementResource.setContextCompany(_company);
		sxpElementResource.setContextHttpServletRequest(_httpServletRequest);
		sxpElementResource.setContextHttpServletResponse(_httpServletResponse);
		sxpElementResource.setContextUriInfo(_uriInfo);
		sxpElementResource.setContextUser(_user);
		sxpElementResource.setGroupLocalService(_groupLocalService);
		sxpElementResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			SearchableAssetNamesResource searchableAssetNamesResource)
		throws Exception {

		searchableAssetNamesResource.setContextAcceptLanguage(_acceptLanguage);
		searchableAssetNamesResource.setContextCompany(_company);
		searchableAssetNamesResource.setContextHttpServletRequest(
			_httpServletRequest);
		searchableAssetNamesResource.setContextHttpServletResponse(
			_httpServletResponse);
		searchableAssetNamesResource.setContextUriInfo(_uriInfo);
		searchableAssetNamesResource.setContextUser(_user);
		searchableAssetNamesResource.setGroupLocalService(_groupLocalService);
		searchableAssetNamesResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			SearchableAssetNamesDisplayResource
				searchableAssetNamesDisplayResource)
		throws Exception {

		searchableAssetNamesDisplayResource.setContextAcceptLanguage(
			_acceptLanguage);
		searchableAssetNamesDisplayResource.setContextCompany(_company);
		searchableAssetNamesDisplayResource.setContextHttpServletRequest(
			_httpServletRequest);
		searchableAssetNamesDisplayResource.setContextHttpServletResponse(
			_httpServletResponse);
		searchableAssetNamesDisplayResource.setContextUriInfo(_uriInfo);
		searchableAssetNamesDisplayResource.setContextUser(_user);
		searchableAssetNamesDisplayResource.setGroupLocalService(
			_groupLocalService);
		searchableAssetNamesDisplayResource.setRoleLocalService(
			_roleLocalService);
	}

	private static ComponentServiceObjects<KeywordQueryContributorsResource>
		_keywordQueryContributorsResourceComponentServiceObjects;
	private static ComponentServiceObjects<ModelPrefilterContributorsResource>
		_modelPrefilterContributorsResourceComponentServiceObjects;
	private static ComponentServiceObjects<QueryPrefilterContributorsResource>
		_queryPrefilterContributorsResourceComponentServiceObjects;
	private static ComponentServiceObjects<SXPBlueprintResource>
		_sxpBlueprintResourceComponentServiceObjects;
	private static ComponentServiceObjects<SXPElementResource>
		_sxpElementResourceComponentServiceObjects;
	private static ComponentServiceObjects<SearchableAssetNamesResource>
		_searchableAssetNamesResourceComponentServiceObjects;
	private static ComponentServiceObjects<SearchableAssetNamesDisplayResource>
		_searchableAssetNamesDisplayResourceComponentServiceObjects;

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