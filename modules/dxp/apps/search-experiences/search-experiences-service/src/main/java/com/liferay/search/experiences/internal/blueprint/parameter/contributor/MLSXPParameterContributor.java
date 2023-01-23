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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.contributor.SXPParameterContributorDefinition;
import com.liferay.search.experiences.configuration.SemanticSearchConfiguration;
import com.liferay.search.experiences.internal.blueprint.parameter.DoubleArraySXPParameter;
import com.liferay.search.experiences.internal.blueprint.parameter.IntegerSXPParameter;
import com.liferay.search.experiences.internal.web.cache.TextEmbeddingProviderWebCacheItem;
import com.liferay.search.experiences.ml.embedding.text.TextEmbeddingRetriever;
import com.liferay.search.experiences.rest.dto.v1_0.EmbeddingProviderConfiguration;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;

import java.beans.ExceptionListener;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Petteri Karttunen
 */
public class MLSXPParameterContributor implements SXPParameterContributor {

	public MLSXPParameterContributor(
		ConfigurationProvider configurationProvider, Language language,
		TextEmbeddingRetriever textEmbeddingRetriever) {

		_configurationProvider = configurationProvider;
		_language = language;
		_textEmbeddingRetriever = textEmbeddingRetriever;
	}

	@Override
	public void contribute(
		ExceptionListener exceptionListener, SearchContext searchContext,
		SXPBlueprint sxpBlueprint, Set<SXPParameter> sxpParameters) {

		_addTextEmbeddingParameters(
			exceptionListener, searchContext, sxpParameters);
	}

	@Override
	public String getSXPParameterCategoryNameKey() {
		return "ml";
	}

	@Override
	public List<SXPParameterContributorDefinition>
		getSXPParameterContributorDefinitions(long companyId, Locale locale) {

		return _getTextEmbeddingParameterContributorDefinitions(
			companyId, locale);
	}

	private void _addTextEmbeddingParameters(
		ExceptionListener exceptionListener, SearchContext searchContext,
		Set<SXPParameter> sxpParameters) {

		SemanticSearchConfiguration semanticSearchConfiguration =
			_getSemanticSearchConfiguration(searchContext.getCompanyId());

		EmbeddingProviderConfiguration embeddingProviderConfiguration =
			_getEmbeddingProviderConfiguration(
				exceptionListener, semanticSearchConfiguration);

		if (embeddingProviderConfiguration == null) {
			return;
		}

		sxpParameters.add(
			new IntegerSXPParameter(
				"ml.text_embeddings.vector_dimensions", true,
				embeddingProviderConfiguration.getEmbeddingVectorDimensions()));

		Double[] textEmbedding = TextEmbeddingProviderWebCacheItem.get(
			exceptionListener, embeddingProviderConfiguration.getProviderName(),
			semanticSearchConfiguration.textEmbeddingCacheTimeout(),
			_textEmbeddingRetriever, searchContext.getKeywords());

		if (ArrayUtil.isEmpty(textEmbedding)) {
			return;
		}

		sxpParameters.add(
			new DoubleArraySXPParameter(
				"ml.text_embeddings.keywords_embedding", true, textEmbedding));
	}

	private EmbeddingProviderConfiguration _getEmbeddingProviderConfiguration(
		ExceptionListener exceptionListener,
		SemanticSearchConfiguration semanticSearchConfiguration) {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-163688")) ||
			!semanticSearchConfiguration.textEmbeddingsEnabled()) {

			return null;
		}

		try {
			for (String configurationJSON :
					semanticSearchConfiguration.
						textEmbeddingProviderConfigurations()) {

				return EmbeddingProviderConfiguration.unsafeToDTO(
					configurationJSON);
			}
		}
		catch (Exception exception) {
			if (exceptionListener != null) {
				exceptionListener.exceptionThrown(exception);
			}

			return ReflectionUtil.throwException(exception);
		}

		return null;
	}

	private EmbeddingProviderConfiguration _getEmbeddingProviderConfiguration(
		SemanticSearchConfiguration semanticSearchConfiguration) {

		return _getEmbeddingProviderConfiguration(
			null, semanticSearchConfiguration);
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

	private List<SXPParameterContributorDefinition>
		_getTextEmbeddingParameterContributorDefinitions(
			long companyId, Locale locale) {

		EmbeddingProviderConfiguration embeddingProviderConfiguration =
			_getEmbeddingProviderConfiguration(
				_getSemanticSearchConfiguration(companyId));

		if (embeddingProviderConfiguration == null) {
			return Collections.emptyList();
		}

		return Arrays.asList(
			new SXPParameterContributorDefinition(
				IntegerSXPParameter.class,
				StringBundler.concat(
					_language.get(locale, "text-embedding-vector-dimensions"),
					" (", embeddingProviderConfiguration.getProviderName(),
					")"),
				"ml.text_embeddings.vector_dimensions"),
			new SXPParameterContributorDefinition(
				DoubleArraySXPParameter.class,
				StringBundler.concat(
					_language.get(locale, "keywords-embedding"), " (",
					embeddingProviderConfiguration.getProviderName(), ")"),
				"ml.text_embeddings.keywords_embedding"));
	}

	private final ConfigurationProvider _configurationProvider;
	private final Language _language;
	private final TextEmbeddingRetriever _textEmbeddingRetriever;

}