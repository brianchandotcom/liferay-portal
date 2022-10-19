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

import com.liferay.portal.vulcan.graphql.servlet.ServletData;
import com.liferay.search.experiences.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.search.experiences.rest.internal.graphql.query.v1_0.Query;
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