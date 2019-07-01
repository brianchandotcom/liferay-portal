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
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.function.BiFunction;

import javax.annotation.Generated;

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

	@GraphQLField
	public Keyword getKeyword(@GraphQLName("keywordId") Long keywordId)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> keywordResource.getKeyword(keywordId));
	}

	@GraphQLField
	public KeywordPage getSiteKeywordsPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") String sort)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> new KeywordPage(
				keywordResource.getSiteKeywordsPage(
					siteId, search,
					_filterBiFunction.apply(keywordResource, filter),
					Pagination.of(page, pageSize),
					_sortBiFunction.apply(keywordResource, sort))));
	}

	@GraphQLField
	public TaxonomyCategoryPage getTaxonomyCategoryTaxonomyCategoriesPage(
			@GraphQLName("parentTaxonomyCategoryId") Long
				parentTaxonomyCategoryId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") String sort)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyCategoryResource -> new TaxonomyCategoryPage(
				taxonomyCategoryResource.
					getTaxonomyCategoryTaxonomyCategoriesPage(
						parentTaxonomyCategoryId, search,
						_filterBiFunction.apply(
							taxonomyCategoryResource, filter),
						Pagination.of(page, pageSize),
						_sortBiFunction.apply(
							taxonomyCategoryResource, sort))));
	}

	@GraphQLField
	public TaxonomyCategory getTaxonomyCategory(
			@GraphQLName("taxonomyCategoryId") Long taxonomyCategoryId)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyCategoryResource ->
				taxonomyCategoryResource.getTaxonomyCategory(
					taxonomyCategoryId));
	}

	@GraphQLField
	public TaxonomyCategoryPage getTaxonomyVocabularyTaxonomyCategoriesPage(
			@GraphQLName("taxonomyVocabularyId") Long taxonomyVocabularyId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") String sort)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyCategoryResource -> new TaxonomyCategoryPage(
				taxonomyCategoryResource.
					getTaxonomyVocabularyTaxonomyCategoriesPage(
						taxonomyVocabularyId, search,
						_filterBiFunction.apply(
							taxonomyCategoryResource, filter),
						Pagination.of(page, pageSize),
						_sortBiFunction.apply(
							taxonomyCategoryResource, sort))));
	}

	@GraphQLField
	public TaxonomyVocabularyPage getSiteTaxonomyVocabulariesPage(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") String sort)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyVocabularyResource -> new TaxonomyVocabularyPage(
				taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
					siteId, search,
					_filterBiFunction.apply(taxonomyVocabularyResource, filter),
					Pagination.of(page, pageSize),
					_sortBiFunction.apply(taxonomyVocabularyResource, sort))));
	}

	@GraphQLField
	public TaxonomyVocabulary getTaxonomyVocabulary(
			@GraphQLName("taxonomyVocabularyId") Long taxonomyVocabularyId)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyVocabularyResource ->
				taxonomyVocabularyResource.getTaxonomyVocabulary(
					taxonomyVocabularyId));
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
	}

	private void _populateResourceContext(
			TaxonomyCategoryResource taxonomyCategoryResource)
		throws Exception {

		taxonomyCategoryResource.setContextAcceptLanguage(_acceptLanguage);
		taxonomyCategoryResource.setContextCompany(_company);
	}

	private void _populateResourceContext(
			TaxonomyVocabularyResource taxonomyVocabularyResource)
		throws Exception {

		taxonomyVocabularyResource.setContextAcceptLanguage(_acceptLanguage);
		taxonomyVocabularyResource.setContextCompany(_company);
	}

	private static ComponentServiceObjects<KeywordResource>
		_keywordResourceComponentServiceObjects;
	private static ComponentServiceObjects<TaxonomyCategoryResource>
		_taxonomyCategoryResourceComponentServiceObjects;
	private static ComponentServiceObjects<TaxonomyVocabularyResource>
		_taxonomyVocabularyResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private BiFunction<Object, String, Sort[]> _sortBiFunction;
	private Company _company;

}