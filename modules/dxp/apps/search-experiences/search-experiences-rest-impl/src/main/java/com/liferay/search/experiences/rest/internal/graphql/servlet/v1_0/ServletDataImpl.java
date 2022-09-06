/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.internal.graphql.servlet.v1_0;

import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;
import com.liferay.search.experiences.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.search.experiences.rest.internal.graphql.query.v1_0.Query;
import com.liferay.search.experiences.rest.internal.resource.v1_0.FieldMappingInfoResourceImpl;
import com.liferay.search.experiences.rest.internal.resource.v1_0.KeywordQueryContributorResourceImpl;
import com.liferay.search.experiences.rest.internal.resource.v1_0.MLModelResourceImpl;
import com.liferay.search.experiences.rest.internal.resource.v1_0.ModelPrefilterContributorResourceImpl;
import com.liferay.search.experiences.rest.internal.resource.v1_0.QueryPrefilterContributorResourceImpl;
import com.liferay.search.experiences.rest.internal.resource.v1_0.SXPBlueprintResourceImpl;
import com.liferay.search.experiences.rest.internal.resource.v1_0.SXPElementResourceImpl;
import com.liferay.search.experiences.rest.internal.resource.v1_0.SXPParameterContributorDefinitionResourceImpl;
import com.liferay.search.experiences.rest.internal.resource.v1_0.SearchIndexResourceImpl;
import com.liferay.search.experiences.rest.internal.resource.v1_0.SearchResponseResourceImpl;
import com.liferay.search.experiences.rest.internal.resource.v1_0.SearchableAssetNameDisplayResourceImpl;
import com.liferay.search.experiences.rest.internal.resource.v1_0.SearchableAssetNameResourceImpl;
import com.liferay.search.experiences.rest.internal.resource.v1_0.SentenceTransformerValidationResultResourceImpl;
import com.liferay.search.experiences.rest.resource.v1_0.FieldMappingInfoResource;
import com.liferay.search.experiences.rest.resource.v1_0.KeywordQueryContributorResource;
import com.liferay.search.experiences.rest.resource.v1_0.MLModelResource;
import com.liferay.search.experiences.rest.resource.v1_0.ModelPrefilterContributorResource;
import com.liferay.search.experiences.rest.resource.v1_0.QueryPrefilterContributorResource;
import com.liferay.search.experiences.rest.resource.v1_0.SXPBlueprintResource;
import com.liferay.search.experiences.rest.resource.v1_0.SXPElementResource;
import com.liferay.search.experiences.rest.resource.v1_0.SXPParameterContributorDefinitionResource;
import com.liferay.search.experiences.rest.resource.v1_0.SearchIndexResource;
import com.liferay.search.experiences.rest.resource.v1_0.SearchResponseResource;
import com.liferay.search.experiences.rest.resource.v1_0.SearchableAssetNameDisplayResource;
import com.liferay.search.experiences.rest.resource.v1_0.SearchableAssetNameResource;
import com.liferay.search.experiences.rest.resource.v1_0.SentenceTransformerValidationResultResource;

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
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setSXPBlueprintResourceComponentServiceObjects(
			_sxpBlueprintResourceComponentServiceObjects);
		Mutation.setSXPElementResourceComponentServiceObjects(
			_sxpElementResourceComponentServiceObjects);
		Mutation.setSearchResponseResourceComponentServiceObjects(
			_searchResponseResourceComponentServiceObjects);
		Mutation.
			setSentenceTransformerValidationResultResourceComponentServiceObjects(
				_sentenceTransformerValidationResultResourceComponentServiceObjects);

		Query.setFieldMappingInfoResourceComponentServiceObjects(
			_fieldMappingInfoResourceComponentServiceObjects);
		Query.setKeywordQueryContributorResourceComponentServiceObjects(
			_keywordQueryContributorResourceComponentServiceObjects);
		Query.setMLModelResourceComponentServiceObjects(
			_mlModelResourceComponentServiceObjects);
		Query.setModelPrefilterContributorResourceComponentServiceObjects(
			_modelPrefilterContributorResourceComponentServiceObjects);
		Query.setQueryPrefilterContributorResourceComponentServiceObjects(
			_queryPrefilterContributorResourceComponentServiceObjects);
		Query.setSXPBlueprintResourceComponentServiceObjects(
			_sxpBlueprintResourceComponentServiceObjects);
		Query.setSXPElementResourceComponentServiceObjects(
			_sxpElementResourceComponentServiceObjects);
		Query.
			setSXPParameterContributorDefinitionResourceComponentServiceObjects(
				_sxpParameterContributorDefinitionResourceComponentServiceObjects);
		Query.setSearchIndexResourceComponentServiceObjects(
			_searchIndexResourceComponentServiceObjects);
		Query.setSearchableAssetNameResourceComponentServiceObjects(
			_searchableAssetNameResourceComponentServiceObjects);
		Query.setSearchableAssetNameDisplayResourceComponentServiceObjects(
			_searchableAssetNameDisplayResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Search.Experiences.REST";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/search-experiences-rest-graphql/v1_0";
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
			"mutation#createSXPBlueprint",
			new ObjectValuePair<>(
				SXPBlueprintResourceImpl.class, "postSXPBlueprint"));
		_resourceMethodPairs.put(
			"mutation#createSXPBlueprintBatch",
			new ObjectValuePair<>(
				SXPBlueprintResourceImpl.class, "postSXPBlueprintBatch"));
		_resourceMethodPairs.put(
			"mutation#createSXPBlueprintValidate",
			new ObjectValuePair<>(
				SXPBlueprintResourceImpl.class, "postSXPBlueprintValidate"));
		_resourceMethodPairs.put(
			"mutation#deleteSXPBlueprint",
			new ObjectValuePair<>(
				SXPBlueprintResourceImpl.class, "deleteSXPBlueprint"));
		_resourceMethodPairs.put(
			"mutation#deleteSXPBlueprintBatch",
			new ObjectValuePair<>(
				SXPBlueprintResourceImpl.class, "deleteSXPBlueprintBatch"));
		_resourceMethodPairs.put(
			"mutation#patchSXPBlueprint",
			new ObjectValuePair<>(
				SXPBlueprintResourceImpl.class, "patchSXPBlueprint"));
		_resourceMethodPairs.put(
			"mutation#createSXPBlueprintCopy",
			new ObjectValuePair<>(
				SXPBlueprintResourceImpl.class, "postSXPBlueprintCopy"));
		_resourceMethodPairs.put(
			"mutation#createSXPElement",
			new ObjectValuePair<>(
				SXPElementResourceImpl.class, "postSXPElement"));
		_resourceMethodPairs.put(
			"mutation#createSXPElementBatch",
			new ObjectValuePair<>(
				SXPElementResourceImpl.class, "postSXPElementBatch"));
		_resourceMethodPairs.put(
			"mutation#createSXPElementValidate",
			new ObjectValuePair<>(
				SXPElementResourceImpl.class, "postSXPElementValidate"));
		_resourceMethodPairs.put(
			"mutation#deleteSXPElement",
			new ObjectValuePair<>(
				SXPElementResourceImpl.class, "deleteSXPElement"));
		_resourceMethodPairs.put(
			"mutation#deleteSXPElementBatch",
			new ObjectValuePair<>(
				SXPElementResourceImpl.class, "deleteSXPElementBatch"));
		_resourceMethodPairs.put(
			"mutation#patchSXPElement",
			new ObjectValuePair<>(
				SXPElementResourceImpl.class, "patchSXPElement"));
		_resourceMethodPairs.put(
			"mutation#createSXPElementCopy",
			new ObjectValuePair<>(
				SXPElementResourceImpl.class, "postSXPElementCopy"));
		_resourceMethodPairs.put(
			"mutation#createSearch",
			new ObjectValuePair<>(
				SearchResponseResourceImpl.class, "postSearch"));
		_resourceMethodPairs.put(
			"mutation#createSentenceTransformerValidateConfiguration",
			new ObjectValuePair<>(
				SentenceTransformerValidationResultResourceImpl.class,
				"postSentenceTransformerValidateConfiguration"));
		_resourceMethodPairs.put(
			"query#fieldMappingInfos",
			new ObjectValuePair<>(
				FieldMappingInfoResourceImpl.class,
				"getFieldMappingInfosPage"));
		_resourceMethodPairs.put(
			"query#keywordQueryContributors",
			new ObjectValuePair<>(
				KeywordQueryContributorResourceImpl.class,
				"getKeywordQueryContributorsPage"));
		_resourceMethodPairs.put(
			"query#sentenceTransformerMLModels",
			new ObjectValuePair<>(
				MLModelResourceImpl.class,
				"getSentenceTransformerMLModelsPage"));
		_resourceMethodPairs.put(
			"query#modelPrefilterContributors",
			new ObjectValuePair<>(
				ModelPrefilterContributorResourceImpl.class,
				"getModelPrefilterContributorsPage"));
		_resourceMethodPairs.put(
			"query#queryPrefilterContributors",
			new ObjectValuePair<>(
				QueryPrefilterContributorResourceImpl.class,
				"getQueryPrefilterContributorsPage"));
		_resourceMethodPairs.put(
			"query#sXPBlueprints",
			new ObjectValuePair<>(
				SXPBlueprintResourceImpl.class, "getSXPBlueprintsPage"));
		_resourceMethodPairs.put(
			"query#sXPBlueprint",
			new ObjectValuePair<>(
				SXPBlueprintResourceImpl.class, "getSXPBlueprint"));
		_resourceMethodPairs.put(
			"query#sXPBlueprintExport",
			new ObjectValuePair<>(
				SXPBlueprintResourceImpl.class, "getSXPBlueprintExport"));
		_resourceMethodPairs.put(
			"query#sXPElements",
			new ObjectValuePair<>(
				SXPElementResourceImpl.class, "getSXPElementsPage"));
		_resourceMethodPairs.put(
			"query#sXPElement",
			new ObjectValuePair<>(
				SXPElementResourceImpl.class, "getSXPElement"));
		_resourceMethodPairs.put(
			"query#sXPElementExport",
			new ObjectValuePair<>(
				SXPElementResourceImpl.class, "getSXPElementExport"));
		_resourceMethodPairs.put(
			"query#sXPParameterContributorDefinitions",
			new ObjectValuePair<>(
				SXPParameterContributorDefinitionResourceImpl.class,
				"getSXPParameterContributorDefinitionsPage"));
		_resourceMethodPairs.put(
			"query#searchIndexes",
			new ObjectValuePair<>(
				SearchIndexResourceImpl.class, "getSearchIndexesPage"));
		_resourceMethodPairs.put(
			"query#searchableAssetNames",
			new ObjectValuePair<>(
				SearchableAssetNameResourceImpl.class,
				"getSearchableAssetNamesPage"));
		_resourceMethodPairs.put(
			"query#searchableAssetNameLanguage",
			new ObjectValuePair<>(
				SearchableAssetNameDisplayResourceImpl.class,
				"getSearchableAssetNameLanguagePage"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SXPBlueprintResource>
		_sxpBlueprintResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SXPElementResource>
		_sxpElementResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SearchResponseResource>
		_searchResponseResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SentenceTransformerValidationResultResource>
		_sentenceTransformerValidationResultResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<FieldMappingInfoResource>
		_fieldMappingInfoResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<KeywordQueryContributorResource>
		_keywordQueryContributorResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<MLModelResource>
		_mlModelResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ModelPrefilterContributorResource>
		_modelPrefilterContributorResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<QueryPrefilterContributorResource>
		_queryPrefilterContributorResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SXPParameterContributorDefinitionResource>
		_sxpParameterContributorDefinitionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SearchIndexResource>
		_searchIndexResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SearchableAssetNameResource>
		_searchableAssetNameResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SearchableAssetNameDisplayResource>
		_searchableAssetNameDisplayResourceComponentServiceObjects;

}