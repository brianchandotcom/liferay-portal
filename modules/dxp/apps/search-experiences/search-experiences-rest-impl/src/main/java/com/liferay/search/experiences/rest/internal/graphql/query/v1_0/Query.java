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
import com.liferay.search.experiences.rest.dto.v1_0.KeywordQueryContributor;
import com.liferay.search.experiences.rest.dto.v1_0.ModelPrefilterContributor;
import com.liferay.search.experiences.rest.dto.v1_0.QueryPrefilterContributor;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.SearchableAssetName;
import com.liferay.search.experiences.rest.dto.v1_0.SearchableAssetNameDisplay;
import com.liferay.search.experiences.rest.resource.v1_0.KeywordQueryContributorResource;
import com.liferay.search.experiences.rest.resource.v1_0.ModelPrefilterContributorResource;
import com.liferay.search.experiences.rest.resource.v1_0.QueryPrefilterContributorResource;
import com.liferay.search.experiences.rest.resource.v1_0.SXPBlueprintResource;
import com.liferay.search.experiences.rest.resource.v1_0.SXPElementResource;
import com.liferay.search.experiences.rest.resource.v1_0.SearchableAssetNameDisplayResource;
import com.liferay.search.experiences.rest.resource.v1_0.SearchableAssetNameResource;

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
		setKeywordQueryContributorResourceComponentServiceObjects(
			ComponentServiceObjects<KeywordQueryContributorResource>
				keywordQueryContributorResourceComponentServiceObjects) {

		_keywordQueryContributorResourceComponentServiceObjects =
			keywordQueryContributorResourceComponentServiceObjects;
	}

	public static void
		setModelPrefilterContributorResourceComponentServiceObjects(
			ComponentServiceObjects<ModelPrefilterContributorResource>
				modelPrefilterContributorResourceComponentServiceObjects) {

		_modelPrefilterContributorResourceComponentServiceObjects =
			modelPrefilterContributorResourceComponentServiceObjects;
	}

	public static void
		setQueryPrefilterContributorResourceComponentServiceObjects(
			ComponentServiceObjects<QueryPrefilterContributorResource>
				queryPrefilterContributorResourceComponentServiceObjects) {

		_queryPrefilterContributorResourceComponentServiceObjects =
			queryPrefilterContributorResourceComponentServiceObjects;
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

	public static void setSearchableAssetNameResourceComponentServiceObjects(
		ComponentServiceObjects<SearchableAssetNameResource>
			searchableAssetNameResourceComponentServiceObjects) {

		_searchableAssetNameResourceComponentServiceObjects =
			searchableAssetNameResourceComponentServiceObjects;
	}

	public static void
		setSearchableAssetNameDisplayResourceComponentServiceObjects(
			ComponentServiceObjects<SearchableAssetNameDisplayResource>
				searchableAssetNameDisplayResourceComponentServiceObjects) {

		_searchableAssetNameDisplayResourceComponentServiceObjects =
			searchableAssetNameDisplayResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {keywordQueryContributors{items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public KeywordQueryContributorPage keywordQueryContributors()
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordQueryContributorResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordQueryContributorResource -> new KeywordQueryContributorPage(
				keywordQueryContributorResource.
					getKeywordQueryContributorsPage()));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {modelPrefilterContributors{items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ModelPrefilterContributorPage modelPrefilterContributors()
		throws Exception {

		return _applyComponentServiceObjects(
			_modelPrefilterContributorResourceComponentServiceObjects,
			this::_populateResourceContext,
			modelPrefilterContributorResource ->
				new ModelPrefilterContributorPage(
					modelPrefilterContributorResource.
						getModelPrefilterContributorsPage()));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {queryPrefilterContributors{items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public QueryPrefilterContributorPage queryPrefilterContributors()
		throws Exception {

		return _applyComponentServiceObjects(
			_queryPrefilterContributorResourceComponentServiceObjects,
			this::_populateResourceContext,
			queryPrefilterContributorResource ->
				new QueryPrefilterContributorPage(
					queryPrefilterContributorResource.
						getQueryPrefilterContributorsPage()));
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
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {sXPElement(sxpElementId: ___){description, description_i18n, elementDefinition, id, title, title_i18n}}"}' -u 'test@liferay.com:test'
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
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {searchableAssetNames{items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public SearchableAssetNamePage searchableAssetNames() throws Exception {
		return _applyComponentServiceObjects(
			_searchableAssetNameResourceComponentServiceObjects,
			this::_populateResourceContext,
			searchableAssetNameResource -> new SearchableAssetNamePage(
				searchableAssetNameResource.getSearchableAssetNamesPage()));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {searchableAssetNameLanguage(languageId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public SearchableAssetNameDisplayPage searchableAssetNameLanguage(
			@GraphQLName("languageId") String languageId)
		throws Exception {

		return _applyComponentServiceObjects(
			_searchableAssetNameDisplayResourceComponentServiceObjects,
			this::_populateResourceContext,
			searchableAssetNameDisplayResource ->
				new SearchableAssetNameDisplayPage(
					searchableAssetNameDisplayResource.
						getSearchableAssetNameLanguagePage(languageId)));
	}

	@GraphQLName("KeywordQueryContributorPage")
	public class KeywordQueryContributorPage {

		public KeywordQueryContributorPage(Page keywordQueryContributorPage) {
			actions = keywordQueryContributorPage.getActions();

			items = keywordQueryContributorPage.getItems();
			lastPage = keywordQueryContributorPage.getLastPage();
			page = keywordQueryContributorPage.getPage();
			pageSize = keywordQueryContributorPage.getPageSize();
			totalCount = keywordQueryContributorPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<KeywordQueryContributor> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ModelPrefilterContributorPage")
	public class ModelPrefilterContributorPage {

		public ModelPrefilterContributorPage(
			Page modelPrefilterContributorPage) {

			actions = modelPrefilterContributorPage.getActions();

			items = modelPrefilterContributorPage.getItems();
			lastPage = modelPrefilterContributorPage.getLastPage();
			page = modelPrefilterContributorPage.getPage();
			pageSize = modelPrefilterContributorPage.getPageSize();
			totalCount = modelPrefilterContributorPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<ModelPrefilterContributor> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("QueryPrefilterContributorPage")
	public class QueryPrefilterContributorPage {

		public QueryPrefilterContributorPage(
			Page queryPrefilterContributorPage) {

			actions = queryPrefilterContributorPage.getActions();

			items = queryPrefilterContributorPage.getItems();
			lastPage = queryPrefilterContributorPage.getLastPage();
			page = queryPrefilterContributorPage.getPage();
			pageSize = queryPrefilterContributorPage.getPageSize();
			totalCount = queryPrefilterContributorPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<QueryPrefilterContributor> items;

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

	@GraphQLName("SearchableAssetNamePage")
	public class SearchableAssetNamePage {

		public SearchableAssetNamePage(Page searchableAssetNamePage) {
			actions = searchableAssetNamePage.getActions();

			items = searchableAssetNamePage.getItems();
			lastPage = searchableAssetNamePage.getLastPage();
			page = searchableAssetNamePage.getPage();
			pageSize = searchableAssetNamePage.getPageSize();
			totalCount = searchableAssetNamePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<SearchableAssetName> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("SearchableAssetNameDisplayPage")
	public class SearchableAssetNameDisplayPage {

		public SearchableAssetNameDisplayPage(
			Page searchableAssetNameDisplayPage) {

			actions = searchableAssetNameDisplayPage.getActions();

			items = searchableAssetNameDisplayPage.getItems();
			lastPage = searchableAssetNameDisplayPage.getLastPage();
			page = searchableAssetNameDisplayPage.getPage();
			pageSize = searchableAssetNameDisplayPage.getPageSize();
			totalCount = searchableAssetNameDisplayPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<SearchableAssetNameDisplay> items;

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
			KeywordQueryContributorResource keywordQueryContributorResource)
		throws Exception {

		keywordQueryContributorResource.setContextAcceptLanguage(
			_acceptLanguage);
		keywordQueryContributorResource.setContextCompany(_company);
		keywordQueryContributorResource.setContextHttpServletRequest(
			_httpServletRequest);
		keywordQueryContributorResource.setContextHttpServletResponse(
			_httpServletResponse);
		keywordQueryContributorResource.setContextUriInfo(_uriInfo);
		keywordQueryContributorResource.setContextUser(_user);
		keywordQueryContributorResource.setGroupLocalService(
			_groupLocalService);
		keywordQueryContributorResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ModelPrefilterContributorResource modelPrefilterContributorResource)
		throws Exception {

		modelPrefilterContributorResource.setContextAcceptLanguage(
			_acceptLanguage);
		modelPrefilterContributorResource.setContextCompany(_company);
		modelPrefilterContributorResource.setContextHttpServletRequest(
			_httpServletRequest);
		modelPrefilterContributorResource.setContextHttpServletResponse(
			_httpServletResponse);
		modelPrefilterContributorResource.setContextUriInfo(_uriInfo);
		modelPrefilterContributorResource.setContextUser(_user);
		modelPrefilterContributorResource.setGroupLocalService(
			_groupLocalService);
		modelPrefilterContributorResource.setRoleLocalService(
			_roleLocalService);
	}

	private void _populateResourceContext(
			QueryPrefilterContributorResource queryPrefilterContributorResource)
		throws Exception {

		queryPrefilterContributorResource.setContextAcceptLanguage(
			_acceptLanguage);
		queryPrefilterContributorResource.setContextCompany(_company);
		queryPrefilterContributorResource.setContextHttpServletRequest(
			_httpServletRequest);
		queryPrefilterContributorResource.setContextHttpServletResponse(
			_httpServletResponse);
		queryPrefilterContributorResource.setContextUriInfo(_uriInfo);
		queryPrefilterContributorResource.setContextUser(_user);
		queryPrefilterContributorResource.setGroupLocalService(
			_groupLocalService);
		queryPrefilterContributorResource.setRoleLocalService(
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
			SearchableAssetNameResource searchableAssetNameResource)
		throws Exception {

		searchableAssetNameResource.setContextAcceptLanguage(_acceptLanguage);
		searchableAssetNameResource.setContextCompany(_company);
		searchableAssetNameResource.setContextHttpServletRequest(
			_httpServletRequest);
		searchableAssetNameResource.setContextHttpServletResponse(
			_httpServletResponse);
		searchableAssetNameResource.setContextUriInfo(_uriInfo);
		searchableAssetNameResource.setContextUser(_user);
		searchableAssetNameResource.setGroupLocalService(_groupLocalService);
		searchableAssetNameResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			SearchableAssetNameDisplayResource
				searchableAssetNameDisplayResource)
		throws Exception {

		searchableAssetNameDisplayResource.setContextAcceptLanguage(
			_acceptLanguage);
		searchableAssetNameDisplayResource.setContextCompany(_company);
		searchableAssetNameDisplayResource.setContextHttpServletRequest(
			_httpServletRequest);
		searchableAssetNameDisplayResource.setContextHttpServletResponse(
			_httpServletResponse);
		searchableAssetNameDisplayResource.setContextUriInfo(_uriInfo);
		searchableAssetNameDisplayResource.setContextUser(_user);
		searchableAssetNameDisplayResource.setGroupLocalService(
			_groupLocalService);
		searchableAssetNameDisplayResource.setRoleLocalService(
			_roleLocalService);
	}

	private static ComponentServiceObjects<KeywordQueryContributorResource>
		_keywordQueryContributorResourceComponentServiceObjects;
	private static ComponentServiceObjects<ModelPrefilterContributorResource>
		_modelPrefilterContributorResourceComponentServiceObjects;
	private static ComponentServiceObjects<QueryPrefilterContributorResource>
		_queryPrefilterContributorResourceComponentServiceObjects;
	private static ComponentServiceObjects<SXPBlueprintResource>
		_sxpBlueprintResourceComponentServiceObjects;
	private static ComponentServiceObjects<SXPElementResource>
		_sxpElementResourceComponentServiceObjects;
	private static ComponentServiceObjects<SearchableAssetNameResource>
		_searchableAssetNameResourceComponentServiceObjects;
	private static ComponentServiceObjects<SearchableAssetNameDisplayResource>
		_searchableAssetNameDisplayResourceComponentServiceObjects;

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