/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.ml.embedding.text;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.ml.embedding.EmbeddingProviderStatus;
import com.liferay.search.experiences.configuration.SemanticSearchConfiguration;
import com.liferay.search.experiences.configuration.SemanticSearchConfigurationProvider;
import com.liferay.search.experiences.ml.embedding.text.TextEmbeddingRetriever;
import com.liferay.search.experiences.rest.dto.v1_0.EmbeddingProviderConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(enabled = false, service = TextEmbeddingRetriever.class)
public class TextEmbeddingRetrieverImpl implements TextEmbeddingRetriever {

	@Override
	public List<String> getAvailableProviderNames() {
		return ListUtil.fromCollection(
			_textEmbeddingProviderServiceTrackerMap.keySet());
	}

	@Override
	public EmbeddingProviderStatus getEmbeddingProviderStatus(
		String embeddingProviderConfigurationJSON) {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-122920")) {
			return null;
		}

		EmbeddingProviderConfiguration embeddingProviderConfiguration = null;

		try {
			embeddingProviderConfiguration =
				EmbeddingProviderConfiguration.unsafeToDTO(
					embeddingProviderConfigurationJSON);
		}
		catch (Exception exception) {
			return new EmbeddingProviderStatus.EmbeddingProviderStatusBuilder(
			).errorMessage(
				exception.getMessage()
			).build();
		}

		String providerName = embeddingProviderConfiguration.getProviderName();

		try {
			TextEmbeddingProvider textEmbeddingProvider =
				_textEmbeddingProviderServiceTrackerMap.getService(
					providerName);

			if (textEmbeddingProvider == null) {
				return new EmbeddingProviderStatus.
					EmbeddingProviderStatusBuilder(
				).errorMessage(
					"Embedding provider " + providerName + " was not found"
				).providerName(
					providerName
				).build();
			}

			Double[] textEmbedding = textEmbeddingProvider.getEmbedding(
				embeddingProviderConfiguration, StringUtil.randomString());

			return new EmbeddingProviderStatus.EmbeddingProviderStatusBuilder(
			).embeddingVectorDimensions(
				textEmbedding.length
			).providerName(
				providerName
			).build();
		}
		catch (Exception exception) {
			return new EmbeddingProviderStatus.EmbeddingProviderStatusBuilder(
			).errorMessage(
				exception.getMessage()
			).providerName(
				providerName
			).build();
		}
	}

	@Override
	public EmbeddingProviderStatus[] getEmbeddingProviderStatuses() {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-122920")) {
			return new EmbeddingProviderStatus[0];
		}

		List<EmbeddingProviderStatus> embeddingProviderStatuses =
			new ArrayList<>();

		for (String textEmbeddingProviderConfigurationJSON :
				_getTextEmbeddingProviderConfigurationJSONs()) {

			embeddingProviderStatuses.add(
				getEmbeddingProviderStatus(
					textEmbeddingProviderConfigurationJSON));
		}

		return embeddingProviderStatuses.toArray(
			new EmbeddingProviderStatus[0]);
	}

	@Override
	public Double[] getTextEmbedding(String providerName, String text) {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-122920")) {
			return new Double[0];
		}

		TextEmbeddingProvider textEmbeddingProvider =
			_textEmbeddingProviderServiceTrackerMap.getService(providerName);

		if (textEmbeddingProvider == null) {
			return new Double[0];
		}

		EmbeddingProviderConfiguration embeddingProviderConfiguration =
			_getEmbeddingProviderConfiguration(providerName);

		if (embeddingProviderConfiguration == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Configuration for provider " + providerName +
						" not found");
			}

			return new Double[0];
		}

		return textEmbeddingProvider.getEmbedding(
			embeddingProviderConfiguration, text);
	}

	@Activate
	protected void activate(
		Map<String, Object> properties, BundleContext bundleContext) {

		_textEmbeddingProviderServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, TextEmbeddingProvider.class,
				"search.experiences.text.embedding.provider.name");
	}

	@Deactivate
	protected void deactivate() {
		_textEmbeddingProviderServiceTrackerMap.close();
	}

	private EmbeddingProviderConfiguration _getEmbeddingProviderConfiguration(
		String providerName) {

		for (String textEmbeddingProviderConfigurationJSON :
				_getTextEmbeddingProviderConfigurationJSONs()) {

			EmbeddingProviderConfiguration embeddingProviderConfiguration =
				EmbeddingProviderConfiguration.toDTO(
					textEmbeddingProviderConfigurationJSON);

			if (providerName.equals(
					embeddingProviderConfiguration.getProviderName())) {

				return embeddingProviderConfiguration;
			}
		}

		return null;
	}

	private String[] _getTextEmbeddingProviderConfigurationJSONs() {
		SemanticSearchConfiguration semanticSearchConfiguration =
			_semanticSearchConfigurationProvider.getCompanyConfiguration(
				CompanyThreadLocal.getCompanyId());

		return semanticSearchConfiguration.
			textEmbeddingProviderConfigurationJSONs();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TextEmbeddingRetrieverImpl.class);

	@Reference
	private SemanticSearchConfigurationProvider
		_semanticSearchConfigurationProvider;

	private ServiceTrackerMap<String, TextEmbeddingProvider>
		_textEmbeddingProviderServiceTrackerMap;

}