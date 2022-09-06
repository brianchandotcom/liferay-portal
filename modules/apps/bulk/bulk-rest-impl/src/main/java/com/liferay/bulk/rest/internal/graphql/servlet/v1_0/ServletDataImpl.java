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

package com.liferay.bulk.rest.internal.graphql.servlet.v1_0;

import com.liferay.bulk.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.bulk.rest.internal.graphql.query.v1_0.Query;
import com.liferay.bulk.rest.internal.resource.v1_0.KeywordResourceImpl;
import com.liferay.bulk.rest.internal.resource.v1_0.SelectionResourceImpl;
import com.liferay.bulk.rest.internal.resource.v1_0.StatusResourceImpl;
import com.liferay.bulk.rest.internal.resource.v1_0.TaxonomyCategoryResourceImpl;
import com.liferay.bulk.rest.internal.resource.v1_0.TaxonomyVocabularyResourceImpl;
import com.liferay.bulk.rest.resource.v1_0.KeywordResource;
import com.liferay.bulk.rest.resource.v1_0.SelectionResource;
import com.liferay.bulk.rest.resource.v1_0.StatusResource;
import com.liferay.bulk.rest.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.bulk.rest.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setKeywordResourceComponentServiceObjects(
			_keywordResourceComponentServiceObjects);
		Mutation.setSelectionResourceComponentServiceObjects(
			_selectionResourceComponentServiceObjects);
		Mutation.setTaxonomyCategoryResourceComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects);
		Mutation.setTaxonomyVocabularyResourceComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects);

		Query.setStatusResourceComponentServiceObjects(
			_statusResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Bulk.REST";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/bulk-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodPair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodPairs.get("mutation#" + methodName);
		}

		return _resourceMethodPairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodPairs = new HashMap<>();

	static {
		_resourceMethodPairs.put(
			"mutation#patchKeywordBatch",
			new ObjectValuePair<>(
				KeywordResourceImpl.class, "patchKeywordBatch"));
		_resourceMethodPairs.put(
			"mutation#updateKeywordBatch",
			new ObjectValuePair<>(
				KeywordResourceImpl.class, "putKeywordBatch"));
		_resourceMethodPairs.put(
			"mutation#createKeywordsCommonPage",
			new ObjectValuePair<>(
				KeywordResourceImpl.class, "postKeywordsCommonPage"));
		_resourceMethodPairs.put(
			"mutation#createBulkSelection",
			new ObjectValuePair<>(
				SelectionResourceImpl.class, "postBulkSelection"));
		_resourceMethodPairs.put(
			"mutation#patchTaxonomyCategoryBatch",
			new ObjectValuePair<>(
				TaxonomyCategoryResourceImpl.class,
				"patchTaxonomyCategoryBatch"));
		_resourceMethodPairs.put(
			"mutation#updateTaxonomyCategoryBatch",
			new ObjectValuePair<>(
				TaxonomyCategoryResourceImpl.class,
				"putTaxonomyCategoryBatch"));
		_resourceMethodPairs.put(
			"mutation#createSiteTaxonomyVocabulariesCommonPage",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"postSiteTaxonomyVocabulariesCommonPage"));
		_resourceMethodPairs.put(
			"query#status",
			new ObjectValuePair<>(StatusResourceImpl.class, "getStatus"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<KeywordResource>
		_keywordResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SelectionResource>
		_selectionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TaxonomyCategoryResource>
		_taxonomyCategoryResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TaxonomyVocabularyResource>
		_taxonomyVocabularyResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<StatusResource>
		_statusResourceComponentServiceObjects;

}