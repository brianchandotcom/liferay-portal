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

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

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

				return _getCategoryResource().deleteCategory(
					categoryId);
	}
	@GraphQLInvokeDetached
	public Category putCategory(
	@GraphQLName("category-id") Long categoryId,@GraphQLName("Category") Category category)
			throws Exception {

				return _getCategoryResource().putCategory(
					categoryId,category);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public Category postCategoryCategory(
	@GraphQLName("category-id") Long categoryId,@GraphQLName("Category") Category category)
			throws Exception {

				return _getCategoryResource().postCategoryCategory(
					categoryId,category);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public Category postVocabularyCategory(
	@GraphQLName("vocabulary-id") Long vocabularyId,@GraphQLName("Category") Category category)
			throws Exception {

				return _getCategoryResource().postVocabularyCategory(
					vocabularyId,category);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public Keyword postContentSpaceKeyword(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("Keyword") Keyword keyword)
			throws Exception {

				return _getKeywordResource().postContentSpaceKeyword(
					contentSpaceId,keyword);
	}
	@GraphQLInvokeDetached
	public boolean deleteKeyword(
	@GraphQLName("keyword-id") Long keywordId)
			throws Exception {

				return _getKeywordResource().deleteKeyword(
					keywordId);
	}
	@GraphQLInvokeDetached
	public Keyword putKeyword(
	@GraphQLName("keyword-id") Long keywordId,@GraphQLName("Keyword") Keyword keyword)
			throws Exception {

				return _getKeywordResource().putKeyword(
					keywordId,keyword);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public UserAccount postUserAccount(
	@GraphQLName("UserAccount") UserAccount userAccount)
			throws Exception {

				return _getUserAccountResource().postUserAccount(
					userAccount);
	}
	@GraphQLInvokeDetached
	public boolean deleteUserAccount(
	@GraphQLName("user-account-id") Long userAccountId)
			throws Exception {

				return _getUserAccountResource().deleteUserAccount(
					userAccountId);
	}
	@GraphQLInvokeDetached
	public UserAccount putUserAccount(
	@GraphQLName("user-account-id") Long userAccountId,@GraphQLName("UserAccount") UserAccount userAccount)
			throws Exception {

				return _getUserAccountResource().putUserAccount(
					userAccountId,userAccount);
	}
	@GraphQLField
	@GraphQLInvokeDetached
	public Vocabulary postContentSpaceVocabulary(
	@GraphQLName("content-space-id") Long contentSpaceId,@GraphQLName("Vocabulary") Vocabulary vocabulary)
			throws Exception {

				return _getVocabularyResource().postContentSpaceVocabulary(
					contentSpaceId,vocabulary);
	}
	@GraphQLInvokeDetached
	public boolean deleteVocabulary(
	@GraphQLName("vocabulary-id") Long vocabularyId)
			throws Exception {

				return _getVocabularyResource().deleteVocabulary(
					vocabularyId);
	}
	@GraphQLInvokeDetached
	public Vocabulary putVocabulary(
	@GraphQLName("vocabulary-id") Long vocabularyId,@GraphQLName("Vocabulary") Vocabulary vocabulary)
			throws Exception {

				return _getVocabularyResource().putVocabulary(
					vocabularyId,vocabulary);
	}

	private static CategoryResource _getCategoryResource() {
			return _categoryResourceServiceTracker.getService();
	}

	private static final ServiceTracker<CategoryResource, CategoryResource>
			_categoryResourceServiceTracker;
	private static KeywordResource _getKeywordResource() {
			return _keywordResourceServiceTracker.getService();
	}

	private static final ServiceTracker<KeywordResource, KeywordResource>
			_keywordResourceServiceTracker;
	private static UserAccountResource _getUserAccountResource() {
			return _userAccountResourceServiceTracker.getService();
	}

	private static final ServiceTracker<UserAccountResource, UserAccountResource>
			_userAccountResourceServiceTracker;
	private static VocabularyResource _getVocabularyResource() {
			return _vocabularyResourceServiceTracker.getService();
	}

	private static final ServiceTracker<VocabularyResource, VocabularyResource>
			_vocabularyResourceServiceTracker;

		static {
			Bundle bundle = FrameworkUtil.getBundle(Mutation.class);

				ServiceTracker<CategoryResource, CategoryResource>
					categoryResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							CategoryResource.class, null);

				categoryResourceServiceTracker.open();

				_categoryResourceServiceTracker =
					categoryResourceServiceTracker;
				ServiceTracker<KeywordResource, KeywordResource>
					keywordResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							KeywordResource.class, null);

				keywordResourceServiceTracker.open();

				_keywordResourceServiceTracker =
					keywordResourceServiceTracker;
				ServiceTracker<UserAccountResource, UserAccountResource>
					userAccountResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							UserAccountResource.class, null);

				userAccountResourceServiceTracker.open();

				_userAccountResourceServiceTracker =
					userAccountResourceServiceTracker;
				ServiceTracker<VocabularyResource, VocabularyResource>
					vocabularyResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							VocabularyResource.class, null);

				vocabularyResourceServiceTracker.open();

				_vocabularyResourceServiceTracker =
					vocabularyResourceServiceTracker;
	}

}