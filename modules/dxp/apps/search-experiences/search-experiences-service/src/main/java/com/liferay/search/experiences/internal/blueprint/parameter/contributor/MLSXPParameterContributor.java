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

package com.liferay.search.experiences.internal.blueprint.parameter.contributor;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.contributor.SXPParameterContributorDefinition;
import com.liferay.search.experiences.configuration.SemanticSearchConfiguration;
import com.liferay.search.experiences.internal.blueprint.parameter.DoubleArraySXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.IntegerSXPParameter;
import com.liferay.search.experiences.internal.ml.sentence.embedding.SentenceEmbeddingRetriever;
import com.liferay.search.experiences.internal.web.cache.SentenceTransformerWebCacheItem;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;

import java.beans.ExceptionListener;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Petteri Karttunen
 */
public class MLSXPParameterContributor implements SXPParameterContributor {

	public MLSXPParameterContributor(
		ConfigurationProvider configurationProvider,
		SentenceEmbeddingRetriever sentenceEmbeddingRetriever) {

		_configurationProvider = configurationProvider;
		_sentenceEmbeddingRetriever = sentenceEmbeddingRetriever;
	}

	@Override
	public void contribute(
		ExceptionListener exceptionListener, SearchContext searchContext,
		SXPBlueprint sxpBlueprint, Set<SXPParameter> sxpParameters) {

		SemanticSearchConfiguration semanticSearchConfiguration =
			_getSemanticSearchConfiguration(searchContext.getCompanyId());

		sxpParameters.add(
			new IntegerSXPParameter(
				"ml.keyword_vector_dimensions", true,
				semanticSearchConfiguration.embeddingVectorDimensions()));

		Double[] sentenceEmbedding = SentenceTransformerWebCacheItem.get(
			exceptionListener, _sentenceEmbeddingRetriever,
			semanticSearchConfiguration, searchContext.getKeywords());

		if (ArrayUtil.isEmpty(sentenceEmbedding)) {
			return;
		}

		sxpParameters.add(
			new DoubleArraySXPParameter(
				"ml.keyword_vectors", true, sentenceEmbedding));
	}

	@Override
	public String getSXPParameterCategoryNameKey() {
		return "ml";
	}

	@Override
	public List<SXPParameterContributorDefinition>
		getSXPParameterContributorDefinitions(long companyId, Locale locale) {

		return Arrays.asList(
			new SXPParameterContributorDefinition(
				IntegerSXPParameter.class, "keyword-vector-dimensions",
				"ml.keyword_vector_dimensions"),
			new SXPParameterContributorDefinition(
				DoubleArraySXPParameter.class, "keyword-vectors",
				"ml.keyword_vectors"));
	}

	private SemanticSearchConfiguration _getSemanticSearchConfiguration(
		long companyId) {

		try {
			return _configurationProvider.getCompanyConfiguration(
				SemanticSearchConfiguration.class, companyId);
		}
		catch (ConfigurationException configurationException) {
			return ReflectionUtil.throwException(configurationException);
		}
	}

	private final ConfigurationProvider _configurationProvider;
	private final SentenceEmbeddingRetriever _sentenceEmbeddingRetriever;

}