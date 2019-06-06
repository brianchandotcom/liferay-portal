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

package com.liferay.headless.admin.taxonomy.internal.graphql.mutation.v1_0;

import com.liferay.headless.admin.taxonomy.dto.v1_0.Keyword;
import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.resource.v1_0.KeywordResource;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import graphql.schema.DataFetchingEnvironment;

import java.util.function.Function;

import javax.annotation.Generated;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Mutation {

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

	@GraphQLInvokeDetached
	public void deleteKeyword(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("keywordId") Long keywordId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			keywordResource -> _populateResourceContext(
				dataFetchingEnvironment, keywordResource),
			keywordResource -> keywordResource.deleteKeyword(keywordId));
	}

	@GraphQLInvokeDetached
	public Keyword putKeyword(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("keywordId") Long keywordId,
			@GraphQLName("keyword") Keyword keyword)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			keywordResource -> _populateResourceContext(
				dataFetchingEnvironment, keywordResource),
			keywordResource -> keywordResource.putKeyword(keywordId, keyword));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Keyword postSiteKeyword(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("keyword") Keyword keyword)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			keywordResource -> _populateResourceContext(
				dataFetchingEnvironment, keywordResource),
			keywordResource -> keywordResource.postSiteKeyword(
				siteId, keyword));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public TaxonomyCategory postTaxonomyCategoryTaxonomyCategory(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("parentTaxonomyCategoryId") Long
				parentTaxonomyCategoryId,
			@GraphQLName("taxonomyCategory") TaxonomyCategory taxonomyCategory)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			taxonomyCategoryResource -> _populateResourceContext(
				dataFetchingEnvironment, taxonomyCategoryResource),
			taxonomyCategoryResource ->
				taxonomyCategoryResource.postTaxonomyCategoryTaxonomyCategory(
					parentTaxonomyCategoryId, taxonomyCategory));
	}

	@GraphQLInvokeDetached
	public void deleteTaxonomyCategory(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("taxonomyCategoryId") Long taxonomyCategoryId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			taxonomyCategoryResource -> _populateResourceContext(
				dataFetchingEnvironment, taxonomyCategoryResource),
			taxonomyCategoryResource ->
				taxonomyCategoryResource.deleteTaxonomyCategory(
					taxonomyCategoryId));
	}

	@GraphQLInvokeDetached
	public TaxonomyCategory patchTaxonomyCategory(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("taxonomyCategoryId") Long taxonomyCategoryId,
			@GraphQLName("taxonomyCategory") TaxonomyCategory taxonomyCategory)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			taxonomyCategoryResource -> _populateResourceContext(
				dataFetchingEnvironment, taxonomyCategoryResource),
			taxonomyCategoryResource ->
				taxonomyCategoryResource.patchTaxonomyCategory(
					taxonomyCategoryId, taxonomyCategory));
	}

	@GraphQLInvokeDetached
	public TaxonomyCategory putTaxonomyCategory(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("taxonomyCategoryId") Long taxonomyCategoryId,
			@GraphQLName("taxonomyCategory") TaxonomyCategory taxonomyCategory)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			taxonomyCategoryResource -> _populateResourceContext(
				dataFetchingEnvironment, taxonomyCategoryResource),
			taxonomyCategoryResource ->
				taxonomyCategoryResource.putTaxonomyCategory(
					taxonomyCategoryId, taxonomyCategory));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public TaxonomyCategory postTaxonomyVocabularyTaxonomyCategory(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("taxonomyVocabularyId") Long taxonomyVocabularyId,
			@GraphQLName("taxonomyCategory") TaxonomyCategory taxonomyCategory)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			taxonomyCategoryResource -> _populateResourceContext(
				dataFetchingEnvironment, taxonomyCategoryResource),
			taxonomyCategoryResource ->
				taxonomyCategoryResource.postTaxonomyVocabularyTaxonomyCategory(
					taxonomyVocabularyId, taxonomyCategory));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public TaxonomyVocabulary postSiteTaxonomyVocabulary(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("taxonomyVocabulary") TaxonomyVocabulary
				taxonomyVocabulary)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects,
			taxonomyVocabularyResource -> _populateResourceContext(
				dataFetchingEnvironment, taxonomyVocabularyResource),
			taxonomyVocabularyResource ->
				taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
					siteId, taxonomyVocabulary));
	}

	@GraphQLInvokeDetached
	public void deleteTaxonomyVocabulary(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("taxonomyVocabularyId") Long taxonomyVocabularyId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects,
			taxonomyVocabularyResource -> _populateResourceContext(
				dataFetchingEnvironment, taxonomyVocabularyResource),
			taxonomyVocabularyResource ->
				taxonomyVocabularyResource.deleteTaxonomyVocabulary(
					taxonomyVocabularyId));
	}

	@GraphQLInvokeDetached
	public TaxonomyVocabulary patchTaxonomyVocabulary(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("taxonomyVocabularyId") Long taxonomyVocabularyId,
			@GraphQLName("taxonomyVocabulary") TaxonomyVocabulary
				taxonomyVocabulary)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects,
			taxonomyVocabularyResource -> _populateResourceContext(
				dataFetchingEnvironment, taxonomyVocabularyResource),
			taxonomyVocabularyResource ->
				taxonomyVocabularyResource.patchTaxonomyVocabulary(
					taxonomyVocabularyId, taxonomyVocabulary));
	}

	@GraphQLInvokeDetached
	public TaxonomyVocabulary putTaxonomyVocabulary(
			DataFetchingEnvironment dataFetchingEnvironment,
			@GraphQLName("taxonomyVocabularyId") Long taxonomyVocabularyId,
			@GraphQLName("taxonomyVocabulary") TaxonomyVocabulary
				taxonomyVocabulary)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects,
			taxonomyVocabularyResource -> _populateResourceContext(
				dataFetchingEnvironment, taxonomyVocabularyResource),
			taxonomyVocabularyResource ->
				taxonomyVocabularyResource.putTaxonomyVocabulary(
					taxonomyVocabularyId, taxonomyVocabulary));
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

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
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