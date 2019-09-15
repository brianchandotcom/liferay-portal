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
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

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

	@GraphQLField
	public boolean deleteKeyword(@GraphQLName("keywordId") Long keywordId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> keywordResource.deleteKeyword(keywordId));

		return true;
	}

	@GraphQLField
	public Keyword updateKeyword(
			@GraphQLName("keywordId") Long keywordId,
			@GraphQLName("keyword") Keyword keyword)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> keywordResource.putKeyword(keywordId, keyword));
	}

	@GraphQLField
	public Keyword createSiteKeyword(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("siteKey") String siteKey,
			@GraphQLName("keyword") Keyword keyword)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> keywordResource.postSiteKeyword(
				siteId, keyword));
	}

	@GraphQLField
	public TaxonomyCategory createTaxonomyCategoryTaxonomyCategory(
			@GraphQLName("parentTaxonomyCategoryId") Long
				parentTaxonomyCategoryId,
			@GraphQLName("taxonomyCategory") TaxonomyCategory taxonomyCategory)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyCategoryResource ->
				taxonomyCategoryResource.postTaxonomyCategoryTaxonomyCategory(
					parentTaxonomyCategoryId, taxonomyCategory));
	}

	@GraphQLField
	public boolean deleteTaxonomyCategory(
			@GraphQLName("taxonomyCategoryId") Long taxonomyCategoryId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyCategoryResource ->
				taxonomyCategoryResource.deleteTaxonomyCategory(
					taxonomyCategoryId));

		return true;
	}

	@GraphQLField
	public TaxonomyCategory patchTaxonomyCategory(
			@GraphQLName("taxonomyCategoryId") Long taxonomyCategoryId,
			@GraphQLName("taxonomyCategory") TaxonomyCategory taxonomyCategory)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyCategoryResource ->
				taxonomyCategoryResource.patchTaxonomyCategory(
					taxonomyCategoryId, taxonomyCategory));
	}

	@GraphQLField
	public TaxonomyCategory updateTaxonomyCategory(
			@GraphQLName("taxonomyCategoryId") Long taxonomyCategoryId,
			@GraphQLName("taxonomyCategory") TaxonomyCategory taxonomyCategory)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyCategoryResource ->
				taxonomyCategoryResource.putTaxonomyCategory(
					taxonomyCategoryId, taxonomyCategory));
	}

	@GraphQLField
	public TaxonomyCategory createTaxonomyVocabularyTaxonomyCategory(
			@GraphQLName("taxonomyVocabularyId") Long taxonomyVocabularyId,
			@GraphQLName("taxonomyCategory") TaxonomyCategory taxonomyCategory)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyCategoryResource ->
				taxonomyCategoryResource.postTaxonomyVocabularyTaxonomyCategory(
					taxonomyVocabularyId, taxonomyCategory));
	}

	@GraphQLField
	public TaxonomyVocabulary createSiteTaxonomyVocabulary(
			@GraphQLName("siteId") Long siteId,
			@GraphQLName("siteKey") String siteKey,
			@GraphQLName("taxonomyVocabulary") TaxonomyVocabulary
				taxonomyVocabulary)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyVocabularyResource ->
				taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
					siteId, taxonomyVocabulary));
	}

	@GraphQLField
	public boolean deleteTaxonomyVocabulary(
			@GraphQLName("taxonomyVocabularyId") Long taxonomyVocabularyId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyVocabularyResource ->
				taxonomyVocabularyResource.deleteTaxonomyVocabulary(
					taxonomyVocabularyId));

		return true;
	}

	@GraphQLField
	public TaxonomyVocabulary patchTaxonomyVocabulary(
			@GraphQLName("taxonomyVocabularyId") Long taxonomyVocabularyId,
			@GraphQLName("taxonomyVocabulary") TaxonomyVocabulary
				taxonomyVocabulary)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxonomyVocabularyResource ->
				taxonomyVocabularyResource.patchTaxonomyVocabulary(
					taxonomyVocabularyId, taxonomyVocabulary));
	}

	@GraphQLField
	public TaxonomyVocabulary updateTaxonomyVocabulary(
			@GraphQLName("taxonomyVocabularyId") Long taxonomyVocabularyId,
			@GraphQLName("taxonomyVocabulary") TaxonomyVocabulary
				taxonomyVocabulary)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects,
			this::_populateResourceContext,
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
	private Company _company;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private UriInfo _uriInfo;
	private User _user;

}