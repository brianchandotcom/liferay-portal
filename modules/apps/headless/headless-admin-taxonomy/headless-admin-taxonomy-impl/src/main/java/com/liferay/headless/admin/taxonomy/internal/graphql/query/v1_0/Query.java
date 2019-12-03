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

package com.liferay.headless.admin.taxonomy.internal.graphql.query.v1_0;

import com.liferay.headless.admin.taxonomy.dto.v1_0.Keyword;
import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.resource.v1_0.KeywordResource;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Query {

	public static void setKeywordResourceComponentServiceObjects(
		ComponentServiceObjects<KeywordResource>
			keywordResourceComponentServiceObjects) {

		_keywordResourceComponentServiceObjects =
			keywordResourceComponentServiceObjects;
	}

	public static void setTaxonomyCategoryResourceComponentServiceObjects(
		ComponentServiceObjects<TaxonomyCategoryResource>
			taxonomyCategoryResourceComponentServiceObjects) {

		_taxonomyCategoryResourceComponentServiceObjects =
			taxonomyCategoryResourceComponentServiceObjects;
	}

	public static void setTaxonomyVocabularyResourceComponentServiceObjects(
		ComponentServiceObjects<TaxonomyVocabularyResource>
			taxonomyVocabularyResourceComponentServiceObjects) {

		_taxonomyVocabularyResourceComponentServiceObjects =
			taxonomyVocabularyResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {keyword(keywordId: ___){creator, dateCreated, dateModified, id, keywordUsageCount, name, siteId}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves a keyword.")
	public Keyword keyword(@GraphQLName("keywordId") Long keywordId)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> keywordResource.getKeyword(keywordId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {keywords(filter: ___, page: ___, pageSize: ___, search: ___, siteId: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a Site's keywords. Results can be paginated, filtered, searched, and sorted."
	)
	public KeywordPage keywords(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("siteKey") String siteKey,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> new KeywordPage(
				keywordResource.getSiteKeywordsPage(
					siteId, search,
					_filterBiFunction.apply(keywordResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(keywordResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {taxonomyCategoryTaxonomyCategories(filter: ___, page: ___, pageSize: ___, parentTaxonomyCategoryId: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a taxonomy category's child taxonomy categories. Results can be paginated, filtered, searched, and sorted."
	)
	public TaxonomyCategoryPage taxonomyCategoryTaxonomyCategories(
			@GraphQLName("parentTaxonomyCategoryId") Long
				parentTaxonomyCategoryId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyCategoryResource -> new TaxonomyCategoryPage(
				taxonomyCategoryResource.
					getTaxonomyCategoryTaxonomyCategoriesPage(
						parentTaxonomyCategoryId, search,
						_filterBiFunction.apply(
							taxonomyCategoryResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							taxonomyCategoryResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {taxonomyCategory(taxonomyCategoryId: ___){availableLanguages, creator, dateCreated, dateModified, description, id, name, numberOfTaxonomyCategories, parentTaxonomyCategory, parentTaxonomyVocabulary, viewableBy}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves a taxonomy category.")
	public TaxonomyCategory taxonomyCategory(
			@GraphQLName("taxonomyCategoryId") Long taxonomyCategoryId)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyCategoryResource ->
				taxonomyCategoryResource.getTaxonomyCategory(
					taxonomyCategoryId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {taxonomyVocabularyTaxonomyCategories(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___, taxonomyVocabularyId: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a vocabulary's taxonomy categories. Results can be paginated, filtered, searched, and sorted."
	)
	public TaxonomyCategoryPage taxonomyVocabularyTaxonomyCategories(
			@GraphQLName("taxonomyVocabularyId") Long taxonomyVocabularyId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyCategoryResource -> new TaxonomyCategoryPage(
				taxonomyCategoryResource.
					getTaxonomyVocabularyTaxonomyCategoriesPage(
						taxonomyVocabularyId, search,
						_filterBiFunction.apply(
							taxonomyCategoryResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							taxonomyCategoryResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {taxonomyVocabularies(filter: ___, page: ___, pageSize: ___, search: ___, siteId: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a Site's taxonomy vocabularies. Results can be paginated, filtered, searched, and sorted."
	)
	public TaxonomyVocabularyPage taxonomyVocabularies(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("siteKey") String siteKey,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyVocabularyResource -> new TaxonomyVocabularyPage(
				taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
					siteId, search,
					_filterBiFunction.apply(
						taxonomyVocabularyResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(
						taxonomyVocabularyResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {taxonomyVocabulary(taxonomyVocabularyId: ___){assetTypes, availableLanguages, creator, dateCreated, dateModified, description, id, name, numberOfTaxonomyCategories, siteId, viewableBy}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves a taxonomy vocabulary.")
	public TaxonomyVocabulary taxonomyVocabulary(
			@GraphQLName("taxonomyVocabularyId") Long taxonomyVocabularyId)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyVocabularyResource ->
				taxonomyVocabularyResource.getTaxonomyVocabulary(
					taxonomyVocabularyId));
	}

	@GraphQLTypeExtension(TaxonomyVocabulary.class)
	public class GetTaxonomyVocabularyTaxonomyCategoriesPageTypeExtension {

		public GetTaxonomyVocabularyTaxonomyCategoriesPageTypeExtension(
			TaxonomyVocabulary taxonomyVocabulary) {

			_taxonomyVocabulary = taxonomyVocabulary;
		}

		@GraphQLField(
			description = "Retrieves a vocabulary's taxonomy categories. Results can be paginated, filtered, searched, and sorted."
		)
		public TaxonomyCategoryPage taxonomyCategories(
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_taxonomyCategoryResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				taxonomyCategoryResource -> new TaxonomyCategoryPage(
					taxonomyCategoryResource.
						getTaxonomyVocabularyTaxonomyCategoriesPage(
							_taxonomyVocabulary.getId(), search,
							_filterBiFunction.apply(
								taxonomyCategoryResource, filterString),
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								taxonomyCategoryResource, sortsString))));
		}

		private TaxonomyVocabulary _taxonomyVocabulary;

	}

	@GraphQLTypeExtension(TaxonomyCategory.class)
	public class GetTaxonomyCategoryTaxonomyCategoriesPageTypeExtension {

		public GetTaxonomyCategoryTaxonomyCategoriesPageTypeExtension(
			TaxonomyCategory taxonomyCategory) {

			_taxonomyCategory = taxonomyCategory;
		}

		@GraphQLField(
			description = "Retrieves a taxonomy category's child taxonomy categories. Results can be paginated, filtered, searched, and sorted."
		)
		public TaxonomyCategoryPage taxonomyCategories(
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
			throws Exception {

			return _applyComponentServiceObjects(
				_taxonomyCategoryResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				taxonomyCategoryResource -> new TaxonomyCategoryPage(
					taxonomyCategoryResource.
						getTaxonomyCategoryTaxonomyCategoriesPage(
							_taxonomyCategory.getId(), search,
							_filterBiFunction.apply(
								taxonomyCategoryResource, filterString),
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								taxonomyCategoryResource, sortsString))));
		}

		private TaxonomyCategory _taxonomyCategory;

	}

	@GraphQLName("KeywordPage")
	public class KeywordPage {

		public KeywordPage(Page keywordPage) {
			items = keywordPage.getItems();
			page = keywordPage.getPage();
			pageSize = keywordPage.getPageSize();
			totalCount = keywordPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<Keyword> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("TaxonomyCategoryPage")
	public class TaxonomyCategoryPage {

		public TaxonomyCategoryPage(Page taxonomyCategoryPage) {
			items = taxonomyCategoryPage.getItems();
			page = taxonomyCategoryPage.getPage();
			pageSize = taxonomyCategoryPage.getPageSize();
			totalCount = taxonomyCategoryPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<TaxonomyCategory> items;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("TaxonomyVocabularyPage")
	public class TaxonomyVocabularyPage {

		public TaxonomyVocabularyPage(Page taxonomyVocabularyPage) {
			items = taxonomyVocabularyPage.getItems();
			page = taxonomyVocabularyPage.getPage();
			pageSize = taxonomyVocabularyPage.getPageSize();
			totalCount = taxonomyVocabularyPage.getTotalCount();
		}

		@GraphQLField
		protected java.util.Collection<TaxonomyVocabulary> items;

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

	private void _populateResourceContext(KeywordResource keywordResource)
		throws Exception {

		keywordResource.setContextAcceptLanguage(_acceptLanguage);
		keywordResource.setContextCompany(_company);
		keywordResource.setContextHttpServletRequest(_httpServletRequest);
		keywordResource.setContextHttpServletResponse(_httpServletResponse);
		keywordResource.setContextUriInfo(_uriInfo);
		keywordResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			TaxonomyCategoryResource taxonomyCategoryResource)
		throws Exception {

		taxonomyCategoryResource.setContextAcceptLanguage(_acceptLanguage);
		taxonomyCategoryResource.setContextCompany(_company);
		taxonomyCategoryResource.setContextHttpServletRequest(
			_httpServletRequest);
		taxonomyCategoryResource.setContextHttpServletResponse(
			_httpServletResponse);
		taxonomyCategoryResource.setContextUriInfo(_uriInfo);
		taxonomyCategoryResource.setContextUser(_user);
	}

	private void _populateResourceContext(
			TaxonomyVocabularyResource taxonomyVocabularyResource)
		throws Exception {

		taxonomyVocabularyResource.setContextAcceptLanguage(_acceptLanguage);
		taxonomyVocabularyResource.setContextCompany(_company);
		taxonomyVocabularyResource.setContextHttpServletRequest(
			_httpServletRequest);
		taxonomyVocabularyResource.setContextHttpServletResponse(
			_httpServletResponse);
		taxonomyVocabularyResource.setContextUriInfo(_uriInfo);
		taxonomyVocabularyResource.setContextUser(_user);
	}

	private static ComponentServiceObjects<KeywordResource>
		_keywordResourceComponentServiceObjects;
	private static ComponentServiceObjects<TaxonomyCategoryResource>
		_taxonomyCategoryResourceComponentServiceObjects;
	private static ComponentServiceObjects<TaxonomyVocabularyResource>
		_taxonomyVocabularyResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private Company _company;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private UriInfo _uriInfo;
	private User _user;

}