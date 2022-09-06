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

package com.liferay.headless.admin.taxonomy.internal.graphql.servlet.v1_0;

import com.liferay.headless.admin.taxonomy.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.admin.taxonomy.internal.graphql.query.v1_0.Query;
import com.liferay.headless.admin.taxonomy.internal.resource.v1_0.KeywordResourceImpl;
import com.liferay.headless.admin.taxonomy.internal.resource.v1_0.TaxonomyCategoryResourceImpl;
import com.liferay.headless.admin.taxonomy.internal.resource.v1_0.TaxonomyVocabularyResourceImpl;
import com.liferay.headless.admin.taxonomy.resource.v1_0.KeywordResource;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyVocabularyResource;
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
 * @author Javier Gamarra
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setKeywordResourceComponentServiceObjects(
			_keywordResourceComponentServiceObjects);
		Mutation.setTaxonomyCategoryResourceComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects);
		Mutation.setTaxonomyVocabularyResourceComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects);

		Query.setKeywordResourceComponentServiceObjects(
			_keywordResourceComponentServiceObjects);
		Query.setTaxonomyCategoryResourceComponentServiceObjects(
			_taxonomyCategoryResourceComponentServiceObjects);
		Query.setTaxonomyVocabularyResourceComponentServiceObjects(
			_taxonomyVocabularyResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Headless.Admin.Taxonomy";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-admin-taxonomy-graphql/v1_0";
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
			"mutation#createAssetLibraryKeyword",
			new ObjectValuePair<>(
				KeywordResourceImpl.class, "postAssetLibraryKeyword"));
		_resourceMethodPairs.put(
			"mutation#createAssetLibraryKeywordBatch",
			new ObjectValuePair<>(
				KeywordResourceImpl.class, "postAssetLibraryKeywordBatch"));
		_resourceMethodPairs.put(
			"mutation#updateAssetLibraryKeywordPermissionsPage",
			new ObjectValuePair<>(
				KeywordResourceImpl.class,
				"putAssetLibraryKeywordPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#deleteKeyword",
			new ObjectValuePair<>(KeywordResourceImpl.class, "deleteKeyword"));
		_resourceMethodPairs.put(
			"mutation#deleteKeywordBatch",
			new ObjectValuePair<>(
				KeywordResourceImpl.class, "deleteKeywordBatch"));
		_resourceMethodPairs.put(
			"mutation#updateKeyword",
			new ObjectValuePair<>(KeywordResourceImpl.class, "putKeyword"));
		_resourceMethodPairs.put(
			"mutation#updateKeywordBatch",
			new ObjectValuePair<>(
				KeywordResourceImpl.class, "putKeywordBatch"));
		_resourceMethodPairs.put(
			"mutation#updateKeywordSubscribe",
			new ObjectValuePair<>(
				KeywordResourceImpl.class, "putKeywordSubscribe"));
		_resourceMethodPairs.put(
			"mutation#updateKeywordUnsubscribe",
			new ObjectValuePair<>(
				KeywordResourceImpl.class, "putKeywordUnsubscribe"));
		_resourceMethodPairs.put(
			"mutation#createSiteKeyword",
			new ObjectValuePair<>(
				KeywordResourceImpl.class, "postSiteKeyword"));
		_resourceMethodPairs.put(
			"mutation#createSiteKeywordBatch",
			new ObjectValuePair<>(
				KeywordResourceImpl.class, "postSiteKeywordBatch"));
		_resourceMethodPairs.put(
			"mutation#updateSiteKeywordPermissionsPage",
			new ObjectValuePair<>(
				KeywordResourceImpl.class, "putSiteKeywordPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#createTaxonomyCategoryTaxonomyCategory",
			new ObjectValuePair<>(
				TaxonomyCategoryResourceImpl.class,
				"postTaxonomyCategoryTaxonomyCategory"));
		_resourceMethodPairs.put(
			"mutation#deleteTaxonomyCategory",
			new ObjectValuePair<>(
				TaxonomyCategoryResourceImpl.class, "deleteTaxonomyCategory"));
		_resourceMethodPairs.put(
			"mutation#deleteTaxonomyCategoryBatch",
			new ObjectValuePair<>(
				TaxonomyCategoryResourceImpl.class,
				"deleteTaxonomyCategoryBatch"));
		_resourceMethodPairs.put(
			"mutation#patchTaxonomyCategory",
			new ObjectValuePair<>(
				TaxonomyCategoryResourceImpl.class, "patchTaxonomyCategory"));
		_resourceMethodPairs.put(
			"mutation#updateTaxonomyCategory",
			new ObjectValuePair<>(
				TaxonomyCategoryResourceImpl.class, "putTaxonomyCategory"));
		_resourceMethodPairs.put(
			"mutation#updateTaxonomyCategoryBatch",
			new ObjectValuePair<>(
				TaxonomyCategoryResourceImpl.class,
				"putTaxonomyCategoryBatch"));
		_resourceMethodPairs.put(
			"mutation#updateTaxonomyCategoryPermissionsPage",
			new ObjectValuePair<>(
				TaxonomyCategoryResourceImpl.class,
				"putTaxonomyCategoryPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#createTaxonomyVocabularyTaxonomyCategory",
			new ObjectValuePair<>(
				TaxonomyCategoryResourceImpl.class,
				"postTaxonomyVocabularyTaxonomyCategory"));
		_resourceMethodPairs.put(
			"mutation#createTaxonomyVocabularyTaxonomyCategoryBatch",
			new ObjectValuePair<>(
				TaxonomyCategoryResourceImpl.class,
				"postTaxonomyVocabularyTaxonomyCategoryBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteTaxonomyVocabularyTaxonomyCategoryByExternalReferenceCode",
			new ObjectValuePair<>(
				TaxonomyCategoryResourceImpl.class,
				"deleteTaxonomyVocabularyTaxonomyCategoryByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateTaxonomyVocabularyTaxonomyCategoryByExternalReferenceCode",
			new ObjectValuePair<>(
				TaxonomyCategoryResourceImpl.class,
				"putTaxonomyVocabularyTaxonomyCategoryByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#createAssetLibraryTaxonomyVocabulary",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"postAssetLibraryTaxonomyVocabulary"));
		_resourceMethodPairs.put(
			"mutation#createAssetLibraryTaxonomyVocabularyBatch",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"postAssetLibraryTaxonomyVocabularyBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteAssetLibraryTaxonomyVocabularyByExternalReferenceCode",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"deleteAssetLibraryTaxonomyVocabularyByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateAssetLibraryTaxonomyVocabularyByExternalReferenceCode",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"putAssetLibraryTaxonomyVocabularyByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateAssetLibraryTaxonomyVocabularyPermissionsPage",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"putAssetLibraryTaxonomyVocabularyPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#createSiteTaxonomyVocabulary",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"postSiteTaxonomyVocabulary"));
		_resourceMethodPairs.put(
			"mutation#createSiteTaxonomyVocabularyBatch",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"postSiteTaxonomyVocabularyBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteSiteTaxonomyVocabularyByExternalReferenceCode",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"deleteSiteTaxonomyVocabularyByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteTaxonomyVocabularyByExternalReferenceCode",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"putSiteTaxonomyVocabularyByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateSiteTaxonomyVocabularyPermissionsPage",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"putSiteTaxonomyVocabularyPermissionsPage"));
		_resourceMethodPairs.put(
			"mutation#deleteTaxonomyVocabulary",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"deleteTaxonomyVocabulary"));
		_resourceMethodPairs.put(
			"mutation#deleteTaxonomyVocabularyBatch",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"deleteTaxonomyVocabularyBatch"));
		_resourceMethodPairs.put(
			"mutation#patchTaxonomyVocabulary",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"patchTaxonomyVocabulary"));
		_resourceMethodPairs.put(
			"mutation#updateTaxonomyVocabulary",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class, "putTaxonomyVocabulary"));
		_resourceMethodPairs.put(
			"mutation#updateTaxonomyVocabularyBatch",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"putTaxonomyVocabularyBatch"));
		_resourceMethodPairs.put(
			"mutation#updateTaxonomyVocabularyPermissionsPage",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"putTaxonomyVocabularyPermissionsPage"));
		_resourceMethodPairs.put(
			"query#assetLibraryKeywords",
			new ObjectValuePair<>(
				KeywordResourceImpl.class, "getAssetLibraryKeywordsPage"));
		_resourceMethodPairs.put(
			"query#assetLibraryKeywordPermissions",
			new ObjectValuePair<>(
				KeywordResourceImpl.class,
				"getAssetLibraryKeywordPermissionsPage"));
		_resourceMethodPairs.put(
			"query#keywordsRanked",
			new ObjectValuePair<>(
				KeywordResourceImpl.class, "getKeywordsRankedPage"));
		_resourceMethodPairs.put(
			"query#keyword",
			new ObjectValuePair<>(KeywordResourceImpl.class, "getKeyword"));
		_resourceMethodPairs.put(
			"query#keywords",
			new ObjectValuePair<>(
				KeywordResourceImpl.class, "getSiteKeywordsPage"));
		_resourceMethodPairs.put(
			"query#keywordPermissions",
			new ObjectValuePair<>(
				KeywordResourceImpl.class, "getSiteKeywordPermissionsPage"));
		_resourceMethodPairs.put(
			"query#taxonomyCategoriesRanked",
			new ObjectValuePair<>(
				TaxonomyCategoryResourceImpl.class,
				"getTaxonomyCategoriesRankedPage"));
		_resourceMethodPairs.put(
			"query#taxonomyCategoryTaxonomyCategories",
			new ObjectValuePair<>(
				TaxonomyCategoryResourceImpl.class,
				"getTaxonomyCategoryTaxonomyCategoriesPage"));
		_resourceMethodPairs.put(
			"query#taxonomyCategory",
			new ObjectValuePair<>(
				TaxonomyCategoryResourceImpl.class, "getTaxonomyCategory"));
		_resourceMethodPairs.put(
			"query#taxonomyCategoryPermissions",
			new ObjectValuePair<>(
				TaxonomyCategoryResourceImpl.class,
				"getTaxonomyCategoryPermissionsPage"));
		_resourceMethodPairs.put(
			"query#taxonomyVocabularyTaxonomyCategories",
			new ObjectValuePair<>(
				TaxonomyCategoryResourceImpl.class,
				"getTaxonomyVocabularyTaxonomyCategoriesPage"));
		_resourceMethodPairs.put(
			"query#taxonomyVocabularyTaxonomyCategoryByExternalReferenceCode",
			new ObjectValuePair<>(
				TaxonomyCategoryResourceImpl.class,
				"getTaxonomyVocabularyTaxonomyCategoryByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#assetLibraryTaxonomyVocabularies",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"getAssetLibraryTaxonomyVocabulariesPage"));
		_resourceMethodPairs.put(
			"query#assetLibraryTaxonomyVocabularyByExternalReferenceCode",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"getAssetLibraryTaxonomyVocabularyByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#assetLibraryTaxonomyVocabularyPermissions",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"getAssetLibraryTaxonomyVocabularyPermissionsPage"));
		_resourceMethodPairs.put(
			"query#taxonomyVocabularies",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"getSiteTaxonomyVocabulariesPage"));
		_resourceMethodPairs.put(
			"query#taxonomyVocabularyByExternalReferenceCode",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"getSiteTaxonomyVocabularyByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#siteTaxonomyVocabularyPermissions",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"getSiteTaxonomyVocabularyPermissionsPage"));
		_resourceMethodPairs.put(
			"query#taxonomyVocabulary",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class, "getTaxonomyVocabulary"));
		_resourceMethodPairs.put(
			"query#taxonomyVocabularyPermissions",
			new ObjectValuePair<>(
				TaxonomyVocabularyResourceImpl.class,
				"getTaxonomyVocabularyPermissionsPage"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<KeywordResource>
		_keywordResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TaxonomyCategoryResource>
		_taxonomyCategoryResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TaxonomyVocabularyResource>
		_taxonomyVocabularyResourceComponentServiceObjects;

}