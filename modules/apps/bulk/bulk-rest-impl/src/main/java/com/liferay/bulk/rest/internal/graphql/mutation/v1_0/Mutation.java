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

package com.liferay.bulk.rest.internal.graphql.mutation.v1_0;

import com.liferay.bulk.rest.dto.v1_0.DocumentSelection;
import com.liferay.bulk.rest.dto.v1_0.Keyword;
import com.liferay.bulk.rest.dto.v1_0.MessageSelection;
import com.liferay.bulk.rest.dto.v1_0.Vocabulary;
import com.liferay.bulk.rest.resource.v1_0.CategoryResource;
import com.liferay.bulk.rest.resource.v1_0.KeywordResource;
import com.liferay.bulk.rest.resource.v1_0.MessageSelectionResource;
import com.liferay.bulk.rest.resource.v1_0.VocabularyResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.vulcan.pagination.Page;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Collection;

import javax.annotation.Generated;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Alejandro Tardín
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

	public static void setMessageSelectionResourceComponentServiceObjects(
		ComponentServiceObjects<MessageSelectionResource>
			messageSelectionResourceComponentServiceObjects) {

		_messageSelectionResourceComponentServiceObjects =
			messageSelectionResourceComponentServiceObjects;
	}

	public static void setVocabularyResourceComponentServiceObjects(
		ComponentServiceObjects<VocabularyResource>
			vocabularyResourceComponentServiceObjects) {

		_vocabularyResourceComponentServiceObjects =
			vocabularyResourceComponentServiceObjects;
	}

	@GraphQLInvokeDetached
	public boolean patchCategoryBatch(
			@GraphQLName("DocumentSelection") DocumentSelection
				documentSelection)
		throws Exception {

		return _applyComponentServiceObjects(
			_categoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			categoryResource -> categoryResource.patchCategoryBatch(
				documentSelection));
	}

	@GraphQLInvokeDetached
	public boolean putCategoryBatch(
			@GraphQLName("DocumentSelection") DocumentSelection
				documentSelection)
		throws Exception {

		return _applyComponentServiceObjects(
			_categoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			categoryResource -> categoryResource.putCategoryBatch(
				documentSelection));
	}

	@GraphQLInvokeDetached
	public boolean patchKeywordBatch(
			@GraphQLName("DocumentSelection") DocumentSelection
				documentSelection)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> keywordResource.patchKeywordBatch(
				documentSelection));
	}

	@GraphQLInvokeDetached
	public boolean putKeywordBatch(
			@GraphQLName("DocumentSelection") DocumentSelection
				documentSelection)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> keywordResource.putKeywordBatch(
				documentSelection));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Keyword> postKeywordCommonPage(
			@GraphQLName("DocumentSelection") DocumentSelection
				documentSelection)
		throws Exception {

		return _applyComponentServiceObjects(
			_keywordResourceComponentServiceObjects,
			this::_populateResourceContext,
			keywordResource -> {
				Page paginationPage = keywordResource.postKeywordCommonPage(
					documentSelection);

				return paginationPage.getItems();
			});
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageSelection postKeywordMessageSelection(
			@GraphQLName("DocumentSelection") DocumentSelection
				documentSelection)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageSelectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageSelectionResource ->
				messageSelectionResource.postKeywordMessageSelection(
					documentSelection));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageSelection postVocabularyMessageSelection(
			@GraphQLName("DocumentSelection") DocumentSelection
				documentSelection)
		throws Exception {

		return _applyComponentServiceObjects(
			_messageSelectionResourceComponentServiceObjects,
			this::_populateResourceContext,
			messageSelectionResource ->
				messageSelectionResource.postVocabularyMessageSelection(
					documentSelection));
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Vocabulary> postContentSpaceVocabularyCommonPage(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("DocumentSelection") DocumentSelection
				documentSelection)
		throws Exception {

		return _applyComponentServiceObjects(
			_vocabularyResourceComponentServiceObjects,
			this::_populateResourceContext,
			vocabularyResource -> {
				Page paginationPage =
					vocabularyResource.postContentSpaceVocabularyCommonPage(
						contentSpaceId, documentSelection);

				return paginationPage.getItems();
			});
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
		throws Exception {

		categoryResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(KeywordResource keywordResource)
		throws Exception {

		keywordResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(
			MessageSelectionResource messageSelectionResource)
		throws Exception {

		messageSelectionResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private void _populateResourceContext(VocabularyResource vocabularyResource)
		throws Exception {

		vocabularyResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));
	}

	private static ComponentServiceObjects<CategoryResource>
		_categoryResourceComponentServiceObjects;
	private static ComponentServiceObjects<KeywordResource>
		_keywordResourceComponentServiceObjects;
	private static ComponentServiceObjects<MessageSelectionResource>
		_messageSelectionResourceComponentServiceObjects;
	private static ComponentServiceObjects<VocabularyResource>
		_vocabularyResourceComponentServiceObjects;

}