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

package com.liferay.headless.foundation.internal.graphql.mutation.v1_0;

import com.liferay.headless.foundation.dto.v1_0.Category;
import com.liferay.headless.foundation.dto.v1_0.Keyword;
import com.liferay.headless.foundation.dto.v1_0.UserAccount;
import com.liferay.headless.foundation.dto.v1_0.Vocabulary;
import com.liferay.headless.foundation.resource.v1_0.CategoryResource;
import com.liferay.headless.foundation.resource.v1_0.KeywordResource;
import com.liferay.headless.foundation.resource.v1_0.UserAccountResource;
import com.liferay.headless.foundation.resource.v1_0.VocabularyResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setCategoryResourceComponentServiceObjects(
		ComponentServiceObjects<CategoryResource>
			categoryResourceComponentServiceObjects) {

		_categoryResourceComponentServiceObjects =
			categoryResourceComponentServiceObjects;
	}

	public static void setKeywordResourceComponentServiceObjects(
		ComponentServiceObjects<KeywordResource>
			keywordResourceComponentServiceObjects) {

		_keywordResourceComponentServiceObjects =
			keywordResourceComponentServiceObjects;
	}

	public static void setUserAccountResourceComponentServiceObjects(
		ComponentServiceObjects<UserAccountResource>
			userAccountResourceComponentServiceObjects) {

		_userAccountResourceComponentServiceObjects =
			userAccountResourceComponentServiceObjects;
	}

	public static void setVocabularyResourceComponentServiceObjects(
		ComponentServiceObjects<VocabularyResource>
			vocabularyResourceComponentServiceObjects) {

		_vocabularyResourceComponentServiceObjects =
			vocabularyResourceComponentServiceObjects;
	}

	@GraphQLInvokeDetached
	public boolean deleteCategory(@GraphQLName("category-id") Long categoryId)
		throws Exception {

		return _applyComponentServiceObjects(
			_categoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			categoryResource -> categoryResource.deleteCategory(categoryId));
	}

	@GraphQLInvokeDetached
	public Category putCategory(
			@GraphQLName("category-id") Long categoryId,
			@GraphQLName("Category") Category category)
		throws Exception {

		return _applyComponentServiceObjects(
			_categoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			categoryResource -> categoryResource.putCategory(
				categoryId, category));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Category postCategoryCategory(
			@GraphQLName("category-id") Long categoryId,
			@GraphQLName("Category") Category category)
		throws Exception {

		return _applyComponentServiceObjects(
			_categoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			categoryResource -> categoryResource.postCategoryCategory(
				categoryId, category));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Category postVocabularyCategory(
			@GraphQLName("vocabulary-id") Long vocabularyId,
			@GraphQLName("Category") Category category)
		throws Exception {

		return _applyComponentServiceObjects(
			_categoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			categoryResource -> categoryResource.postVocabularyCategory(
				vocabularyId, category));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Keyword postContentSpaceKeyword(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("Keyword") Keyword keyword)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> keywordResource.postContentSpaceKeyword(
				contentSpaceId, keyword));
	}

	@GraphQLInvokeDetached
	public boolean deleteKeyword(@GraphQLName("keyword-id") Long keywordId)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> keywordResource.deleteKeyword(keywordId));
	}

	@GraphQLInvokeDetached
	public Keyword putKeyword(
			@GraphQLName("keyword-id") Long keywordId,
			@GraphQLName("Keyword") Keyword keyword)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> keywordResource.putKeyword(keywordId, keyword));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public UserAccount postMediaType1UserAccount(
			@GraphQLName("UserAccount") UserAccount userAccount)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource ->
				userAccountResource.postMediaType1UserAccount(userAccount));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public UserAccount postMediaType2UserAccount(
			@GraphQLName("MultipartBody") MultipartBody multipartBody)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource ->
				userAccountResource.postMediaType2UserAccount(multipartBody));
	}

	@GraphQLInvokeDetached
	public boolean deleteUserAccount(
			@GraphQLName("user-account-id") Long userAccountId)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> userAccountResource.deleteUserAccount(
				userAccountId));
	}

	@GraphQLInvokeDetached
	public UserAccount putUserAccount(
			@GraphQLName("user-account-id") Long userAccountId,
			@GraphQLName("UserAccount") UserAccount userAccount)
		throws Exception {

		return _applyComponentServiceObjects(
			_userAccountResourceComponentServiceObjects,
			this::_populateResourceContext,
			userAccountResource -> userAccountResource.putUserAccount(
				userAccountId, userAccount));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Vocabulary postContentSpaceVocabulary(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("Vocabulary") Vocabulary vocabulary)
		throws Exception {

		return _applyComponentServiceObjects(
			_vocabularyResourceComponentServiceObjects,
			this::_populateResourceContext,
			vocabularyResource -> vocabularyResource.postContentSpaceVocabulary(
				contentSpaceId, vocabulary));
	}

	@GraphQLInvokeDetached
	public boolean deleteVocabulary(
			@GraphQLName("vocabulary-id") Long vocabularyId)
		throws Exception {

		return _applyComponentServiceObjects(
			_vocabularyResourceComponentServiceObjects,
			this::_populateResourceContext,
			vocabularyResource -> vocabularyResource.deleteVocabulary(
				vocabularyId));
	}

	@GraphQLInvokeDetached
	public Vocabulary putVocabulary(
			@GraphQLName("vocabulary-id") Long vocabularyId,
			@GraphQLName("Vocabulary") Vocabulary vocabulary)
		throws Exception {

		return _applyComponentServiceObjects(
			_vocabularyResourceComponentServiceObjects,
			this::_populateResourceContext,
			vocabularyResource -> vocabularyResource.putVocabulary(
				vocabularyId, vocabulary));
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

	private void _populateResourceContext(CategoryResource categoryResource)
		throws PortalException {

		categoryResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(KeywordResource keywordResource)
		throws PortalException {

		keywordResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			UserAccountResource userAccountResource)
		throws PortalException {

		userAccountResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(VocabularyResource vocabularyResource)
		throws PortalException {

		vocabularyResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private static ComponentServiceObjects<CategoryResource>
		_categoryResourceComponentServiceObjects;
	private static ComponentServiceObjects<KeywordResource>
		_keywordResourceComponentServiceObjects;
	private static ComponentServiceObjects<UserAccountResource>
		_userAccountResourceComponentServiceObjects;
	private static ComponentServiceObjects<VocabularyResource>
		_vocabularyResourceComponentServiceObjects;

}