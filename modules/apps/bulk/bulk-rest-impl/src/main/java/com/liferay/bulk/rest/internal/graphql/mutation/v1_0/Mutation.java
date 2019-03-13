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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Collection;

import javax.annotation.Generated;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class Mutation {

	@GraphQLInvokeDetached
	public boolean patchCategoryBatch(
			@GraphQLName("DocumentSelection") DocumentSelection
				documentSelection)
		throws Exception {

		CategoryResource categoryResource = _createCategoryResource();

		return categoryResource.patchCategoryBatch(documentSelection);
	}

	@GraphQLInvokeDetached
	public boolean putCategoryBatch(
			@GraphQLName("DocumentSelection") DocumentSelection
				documentSelection)
		throws Exception {

		CategoryResource categoryResource = _createCategoryResource();

		return categoryResource.putCategoryBatch(documentSelection);
	}

	@GraphQLInvokeDetached
	public boolean patchKeywordBatch(
			@GraphQLName("DocumentSelection") DocumentSelection
				documentSelection)
		throws Exception {

		KeywordResource keywordResource = _createKeywordResource();

		return keywordResource.patchKeywordBatch(documentSelection);
	}

	@GraphQLInvokeDetached
	public boolean putKeywordBatch(
			@GraphQLName("DocumentSelection") DocumentSelection
				documentSelection)
		throws Exception {

		KeywordResource keywordResource = _createKeywordResource();

		return keywordResource.putKeywordBatch(documentSelection);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Keyword> postKeywordCommonPage(
			@GraphQLName("DocumentSelection") DocumentSelection
				documentSelection)
		throws Exception {

		KeywordResource keywordResource = _createKeywordResource();

		return keywordResource.postKeywordCommonPage(documentSelection);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageSelection postKeywordMessageSelection(
			@GraphQLName("DocumentSelection") DocumentSelection
				documentSelection)
		throws Exception {

		MessageSelectionResource messageSelectionResource =
			_createMessageSelectionResource();

		return messageSelectionResource.postKeywordMessageSelection(
			documentSelection);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public MessageSelection postVocabularyMessageSelection(
			@GraphQLName("DocumentSelection") DocumentSelection
				documentSelection)
		throws Exception {

		MessageSelectionResource messageSelectionResource =
			_createMessageSelectionResource();

		return messageSelectionResource.postVocabularyMessageSelection(
			documentSelection);
	}

	@GraphQLField
	@GraphQLInvokeDetached
	public Collection<Vocabulary> postContentSpaceVocabularyCommonPage(
			@GraphQLName("content-space-id") Long contentSpaceId,
			@GraphQLName("DocumentSelection") DocumentSelection
				documentSelection)
		throws Exception {

		VocabularyResource vocabularyResource = _createVocabularyResource();

		return vocabularyResource.postContentSpaceVocabularyCommonPage(
			contentSpaceId, documentSelection);
	}

	private static CategoryResource _createCategoryResource() throws Exception {
		CategoryResource categoryResource =
			_categoryResourceServiceTracker.getService();

		categoryResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return categoryResource;
	}

	private static final ServiceTracker<CategoryResource, CategoryResource>
		_categoryResourceServiceTracker;

	private static KeywordResource _createKeywordResource() throws Exception {
		KeywordResource keywordResource =
			_keywordResourceServiceTracker.getService();

		keywordResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return keywordResource;
	}

	private static final ServiceTracker<KeywordResource, KeywordResource>
		_keywordResourceServiceTracker;

	private static MessageSelectionResource _createMessageSelectionResource()
		throws Exception {

		MessageSelectionResource messageSelectionResource =
			_messageSelectionResourceServiceTracker.getService();

		messageSelectionResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return messageSelectionResource;
	}

	private static final ServiceTracker
		<MessageSelectionResource, MessageSelectionResource>
			_messageSelectionResourceServiceTracker;

	private static VocabularyResource _createVocabularyResource()
		throws Exception {

		VocabularyResource vocabularyResource =
			_vocabularyResourceServiceTracker.getService();

		vocabularyResource.setContextCompany(
			CompanyLocalServiceUtil.getCompany(
				CompanyThreadLocal.getCompanyId()));

		return vocabularyResource;
	}

	private static final ServiceTracker<VocabularyResource, VocabularyResource>
		_vocabularyResourceServiceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(Mutation.class);

		ServiceTracker<CategoryResource, CategoryResource>
			categoryResourceServiceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), CategoryResource.class, null);

		categoryResourceServiceTracker.open();

		_categoryResourceServiceTracker = categoryResourceServiceTracker;
		ServiceTracker<KeywordResource, KeywordResource>
			keywordResourceServiceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), KeywordResource.class, null);

		keywordResourceServiceTracker.open();

		_keywordResourceServiceTracker = keywordResourceServiceTracker;
		ServiceTracker<MessageSelectionResource, MessageSelectionResource>
			messageSelectionResourceServiceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), MessageSelectionResource.class,
				null);

		messageSelectionResourceServiceTracker.open();

		_messageSelectionResourceServiceTracker =
			messageSelectionResourceServiceTracker;
		ServiceTracker<VocabularyResource, VocabularyResource>
			vocabularyResourceServiceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), VocabularyResource.class, null);

		vocabularyResourceServiceTracker.open();

		_vocabularyResourceServiceTracker = vocabularyResourceServiceTracker;
	}

}