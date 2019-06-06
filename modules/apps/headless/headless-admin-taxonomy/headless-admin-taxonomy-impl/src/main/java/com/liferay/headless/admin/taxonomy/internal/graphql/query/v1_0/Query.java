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
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import graphql.schema.DataFetchingEnvironment;

import java.util.Collection;
import java.util.function.Function;

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
	@GraphQLInvokeDetached
	public Keyword getKeyword(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("keywordId") Long keywordId)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			keywordResource -> _populateResourceContext(
				dataFetchingEnvironment, keywordResource),
			keywordResource -> keywordResource.getKeyword(keywordId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Keyword> getSiteKeywordsPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			keywordResource -> _populateResourceContext(
				dataFetchingEnvironment, keywordResource),
			keywordResource -> {
				Page paginationPage = keywordResource.getSiteKeywordsPage(
					siteId, search, filter, Pagination.of(pageSize, page),
					sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<TaxonomyCategory>
			getTaxonomyCategoryTaxonomyCategoriesPage(
				DataFetchingEnvironment dataFetchingEnvironment,
				@GraphQLName("parentTaxonomyCategoryId") Long
					parentTaxonomyCategoryId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			taxonomyCategoryResource -> _populateResourceContext(
				dataFetchingEnvironment, taxonomyCategoryResource),
			taxonomyCategoryResource -> {
				Page paginationPage =
					taxonomyCategoryResource.
						getTaxonomyCategoryTaxonomyCategoriesPage(
							parentTaxonomyCategoryId, search, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public TaxonomyCategory getTaxonomyCategory(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("taxonomyCategoryId") Long taxonomyCategoryId)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			taxonomyCategoryResource -> _populateResourceContext(
				dataFetchingEnvironment, taxonomyCategoryResource),
			taxonomyCategoryResource ->
				taxonomyCategoryResource.getTaxonomyCategory(
					taxonomyCategoryId));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<TaxonomyCategory>
			getTaxonomyVocabularyTaxonomyCategoriesPage(
				DataFetchingEnvironment dataFetchingEnvironment,
				@GraphQLName("taxonomyVocabularyId") Long taxonomyVocabularyId,
				@GraphQLName("search") String search,
				@GraphQLName("filter") Filter filter,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			taxonomyCategoryResource -> _populateResourceContext(
				dataFetchingEnvironment, taxonomyCategoryResource),
			taxonomyCategoryResource -> {
				Page paginationPage =
					taxonomyCategoryResource.
						getTaxonomyVocabularyTaxonomyCategoriesPage(
							taxonomyVocabularyId, search, filter,
							Pagination.of(pageSize, page), sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<TaxonomyVocabulary> getSiteTaxonomyVocabulariesPage(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("search") String search,
			@GraphQLName("filter") Filter filter,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page, @GraphQLName("sorts") Sort[] sorts)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects,
			taxonomyVocabularyResource -> _populateResourceContext(
				dataFetchingEnvironment, taxonomyVocabularyResource),
			taxonomyVocabularyResource -> {
				Page paginationPage =
					taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
						siteId, search, filter, Pagination.of(pageSize, page),
						sorts);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public TaxonomyVocabulary getTaxonomyVocabulary(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("taxonomyVocabularyId") Long taxonomyVocabularyId)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects,
			taxonomyVocabularyResource -> _populateResourceContext(
				dataFetchingEnvironment, taxonomyVocabularyResource),
			taxonomyVocabularyResource ->
				taxonomyVocabularyResource.getTaxonomyVocabulary(
					taxonomyVocabularyId));
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
			DataFetchingEnvironment dataFetchingEnvironment,
			KeywordResource keywordResource)
		throws Exception {

		keywordResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		keywordResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			TaxonomyCategoryResource taxonomyCategoryResource)
		throws Exception {

		taxonomyCategoryResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		taxonomyCategoryResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			DataFetchingEnvironment dataFetchingEnvironment,
			TaxonomyVocabularyResource taxonomyVocabularyResource)
		throws Exception {

		taxonomyVocabularyResource.setContextAcceptLanguage(
			_acceptLanguageFunction.apply(
				dataFetchingEnvironment.getContext()));

		taxonomyVocabularyResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	public static void setAcceptLanguageFunction(
		Function<Object, AcceptLanguage> acceptLanguageFunction) {

		_acceptLanguageFunction = acceptLanguageFunction;
	}

	private static Function<Object, AcceptLanguage> _acceptLanguageFunction;
	private static ComponentServiceObjects<KeywordResource>
		_keywordResourceComponentServiceObjects;
	private static ComponentServiceObjects<TaxonomyCategoryResource>
		_taxonomyCategoryResourceComponentServiceObjects;
	private static ComponentServiceObjects<TaxonomyVocabularyResource>
		_taxonomyVocabularyResourceComponentServiceObjects;

}