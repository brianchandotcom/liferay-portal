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

				return (boolean)_getCategoryResourceImpl().deleteCategory(
					categoryId);
	}
	@GraphQLInvokeDetached
	public CategoryImpl putCategory(
	@GraphQLName("category-id") Long categoryId,@GraphQLName("Category") CategoryImpl categoryImpl)
			throws Exception {

				return (CategoryImpl)_getCategoryResourceImpl().putCategory(
					categoryId,categoryImpl);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public CategoryImpl postCategoryCategory(
	@GraphQLName("category-id") Long categoryId,@GraphQLName("Category") CategoryImpl categoryImpl)
			throws Exception {

				return (CategoryImpl)_getCategoryResourceImpl().postCategoryCategory(
					categoryId,categoryImpl);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public CategoryImpl postVocabularyCategory(
	@GraphQLName("vocabulary-id") Long vocabularyId,@GraphQLName("Category") CategoryImpl categoryImpl)
			throws Exception {

				return (CategoryImpl)_getCategoryResourceImpl().postVocabularyCategory(
					vocabularyId,categoryImpl);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public KeywordImpl postContentSpaceKeyword(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("Keyword") KeywordImpl keywordImpl)
			throws Exception {

				return (KeywordImpl)_getKeywordResourceImpl().postContentSpaceKeyword(
					contentSpaceId,keywordImpl);
	}
	@GraphQLInvokeDetached
	public boolean deleteKeyword(
	@GraphQLName("keyword-id") Long keywordId)
			throws Exception {

				return (boolean)_getKeywordResourceImpl().deleteKeyword(
					keywordId);
	}
	@GraphQLInvokeDetached
	public KeywordImpl putKeyword(
	@GraphQLName("keyword-id") Long keywordId,@GraphQLName("Keyword") KeywordImpl keywordImpl)
			throws Exception {

				return (KeywordImpl)_getKeywordResourceImpl().putKeyword(
					keywordId,keywordImpl);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public UserAccountImpl postUserAccount(
	@GraphQLName("UserAccount") UserAccountImpl userAccountImpl)
			throws Exception {

				return (UserAccountImpl)_getUserAccountResourceImpl().postUserAccount(
					userAccountImpl);
	}
	@GraphQLInvokeDetached
	public boolean deleteUserAccount(
	@GraphQLName("user-account-id") Long userAccountId)
			throws Exception {

				return (boolean)_getUserAccountResourceImpl().deleteUserAccount(
					userAccountId);
	}
	@GraphQLInvokeDetached
	public UserAccountImpl putUserAccount(
	@GraphQLName("user-account-id") Long userAccountId,@GraphQLName("UserAccount") UserAccountImpl userAccountImpl)
			throws Exception {

				return (UserAccountImpl)_getUserAccountResourceImpl().putUserAccount(
					userAccountId,userAccountImpl);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public VocabularyImpl postContentSpaceVocabulary(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("Vocabulary") VocabularyImpl vocabularyImpl)
			throws Exception {

				return (VocabularyImpl)_getVocabularyResourceImpl().postContentSpaceVocabulary(
					contentSpaceId,vocabularyImpl);
	}
	@GraphQLInvokeDetached
	public boolean deleteVocabulary(
	@GraphQLName("vocabulary-id") Long vocabularyId)
			throws Exception {

				return (boolean)_getVocabularyResourceImpl().deleteVocabulary(
					vocabularyId);
	}
	@GraphQLInvokeDetached
	public VocabularyImpl putVocabulary(
	@GraphQLName("vocabulary-id") Long vocabularyId,@GraphQLName("Vocabulary") VocabularyImpl vocabularyImpl)
			throws Exception {

				return (VocabularyImpl)_getVocabularyResourceImpl().putVocabulary(
					vocabularyId,vocabularyImpl);
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