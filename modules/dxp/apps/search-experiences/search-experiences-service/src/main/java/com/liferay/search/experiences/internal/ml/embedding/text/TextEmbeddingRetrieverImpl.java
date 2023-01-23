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

package com.liferay.search.experiences.internal.ml.embedding.text;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.search.ml.embedding.EmbeddingProviderInformation;
import com.liferay.portal.search.ml.embedding.EmbeddingProviderStatus;
import com.liferay.search.experiences.configuration.SemanticSearchConfiguration;
import com.liferay.search.experiences.ml.embedding.text.TextEmbeddingRetriever;
import com.liferay.search.experiences.rest.dto.v1_0.EmbeddingProviderConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Petteri Karttunen
 */
@Component(
	configurationPid = "com.liferay.search.experiences.configuration.SemanticSearchConfiguration",
	enabled = false,
	service = {EmbeddingProviderInformation.class, TextEmbeddingRetriever.class}
)
public class TextEmbeddingRetrieverImpl implements TextEmbeddingRetriever {

	@Override
	public List<String> getAvailableProviderNames() {
		return ListUtil.fromCollection(
			_textEmbeddingProviderServiceTrackerMap.keySet());
	}

	@Override
	public EmbeddingProviderStatus getEmbeddingProviderStatus(
		String configurationJSON) {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-163688"))) {
			return null;
		}

		EmbeddingProviderConfiguration embeddingProviderConfiguration;

		try {
			embeddingProviderConfiguration =
				EmbeddingProviderConfiguration.unsafeToDTO(configurationJSON);
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
				embeddingProviderConfiguration, "hello liferay");

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
		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-163688"))) {
			return new EmbeddingProviderStatus[0];
		}

		List<EmbeddingProviderStatus> embeddingProviderStatuses =
			new ArrayList<>();

		for (String configurationJSON :
				_semanticSearchConfiguration.
					textEmbeddingProviderConfigurations()) {

			embeddingProviderStatuses.add(
				getEmbeddingProviderStatus(configurationJSON));
		}

		return embeddingProviderStatuses.toArray(
			new EmbeddingProviderStatus[0]);
	}

	@Override
	public Double[] getTextEmbedding(String providerName, String text) {
		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-163688"))) {
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

		_semanticSearchConfiguration = ConfigurableUtil.createConfigurable(
			SemanticSearchConfiguration.class, properties);

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

		for (String configurationJSON :
				_semanticSearchConfiguration.
					textEmbeddingProviderConfigurations()) {

			EmbeddingProviderConfiguration embeddingProviderConfiguration =
				EmbeddingProviderConfiguration.toDTO(configurationJSON);

			if (providerName.equals(
					embeddingProviderConfiguration.getProviderName())) {

				return embeddingProviderConfiguration;
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TextEmbeddingRetrieverImpl.class);

	private volatile SemanticSearchConfiguration _semanticSearchConfiguration;
	private ServiceTrackerMap<String, TextEmbeddingProvider>
		_textEmbeddingProviderServiceTrackerMap;

}