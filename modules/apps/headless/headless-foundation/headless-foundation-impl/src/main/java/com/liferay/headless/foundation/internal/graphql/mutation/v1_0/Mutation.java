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
import com.liferay.headless.foundation.internal.dto.v1_0.CategoryImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.KeywordImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.UserAccountImpl;
import com.liferay.headless.foundation.internal.dto.v1_0.VocabularyImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.CategoryResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.KeywordResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.UserAccountResourceImpl;
import com.liferay.headless.foundation.internal.resource.v1_0.VocabularyResourceImpl;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Mutation {

	@GraphQLInvokeDetached
	public boolean deleteCategory(
	@GraphQLName("category-id") Long categoryId)
			throws Exception {

				return _getCategoryResourceImpl().deleteCategory(
					categoryId);
	}

	@GraphQLInvokeDetached
	public CategoryImpl putCategory(
	@GraphQLName("category-id") Long categoryId,@GraphQLName("Category") Category category)
			throws Exception {

				return _getCategoryResourceImpl().putCategory(
					categoryId,category);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public CategoryImpl postCategoryCategory(
	@GraphQLName("category-id") Long categoryId,@GraphQLName("Category") Category category)
			throws Exception {

				return _getCategoryResourceImpl().postCategoryCategory(
					categoryId,category);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public CategoryImpl postVocabularyCategory(
	@GraphQLName("vocabulary-id") Long vocabularyId,@GraphQLName("Category") Category category)
			throws Exception {

				return _getCategoryResourceImpl().postVocabularyCategory(
					vocabularyId,category);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public KeywordImpl postContentSpaceKeyword(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("Keyword") Keyword keyword)
			throws Exception {

				return _getKeywordResourceImpl().postContentSpaceKeyword(
					contentSpaceId,keyword);
	}

	@GraphQLInvokeDetached
	public boolean deleteKeyword(
	@GraphQLName("keyword-id") Long keywordId)
			throws Exception {

				return _getKeywordResourceImpl().deleteKeyword(
					keywordId);
	}

	@GraphQLInvokeDetached
	public KeywordImpl putKeyword(
	@GraphQLName("keyword-id") Long keywordId,@GraphQLName("Keyword") Keyword keyword)
			throws Exception {

				return _getKeywordResourceImpl().putKeyword(
					keywordId,keyword);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public UserAccountImpl postUserAccount(
	@GraphQLName("UserAccount") UserAccount userAccount)
			throws Exception {

				return _getUserAccountResourceImpl().postUserAccount(
					userAccount);
	}

	@GraphQLInvokeDetached
	public boolean deleteUserAccount(
	@GraphQLName("user-account-id") Long userAccountId)
			throws Exception {

				return _getUserAccountResourceImpl().deleteUserAccount(
					userAccountId);
	}

	@GraphQLInvokeDetached
	public UserAccountImpl putUserAccount(
	@GraphQLName("user-account-id") Long userAccountId,@GraphQLName("UserAccount") UserAccount userAccount)
			throws Exception {

				return _getUserAccountResourceImpl().putUserAccount(
					userAccountId,userAccount);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public VocabularyImpl postContentSpaceVocabulary(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("Vocabulary") Vocabulary vocabulary)
			throws Exception {

				return _getVocabularyResourceImpl().postContentSpaceVocabulary(
					contentSpaceId,vocabulary);
	}

	@GraphQLInvokeDetached
	public boolean deleteVocabulary(
	@GraphQLName("vocabulary-id") Long vocabularyId)
			throws Exception {

				return _getVocabularyResourceImpl().deleteVocabulary(
					vocabularyId);
	}

	@GraphQLInvokeDetached
	public VocabularyImpl putVocabulary(
	@GraphQLName("vocabulary-id") Long vocabularyId,@GraphQLName("Vocabulary") Vocabulary vocabulary)
			throws Exception {

				return _getVocabularyResourceImpl().putVocabulary(
					vocabularyId,vocabulary);
	}

	private static CategoryResourceImpl _getCategoryResourceImpl() {
			return new CategoryResourceImpl();
	}
	private static KeywordResourceImpl _getKeywordResourceImpl() {
			return new KeywordResourceImpl();
	}
	private static UserAccountResourceImpl _getUserAccountResourceImpl() {
			return new UserAccountResourceImpl();
	}
	private static VocabularyResourceImpl _getVocabularyResourceImpl() {
			return new VocabularyResourceImpl();
	}

}