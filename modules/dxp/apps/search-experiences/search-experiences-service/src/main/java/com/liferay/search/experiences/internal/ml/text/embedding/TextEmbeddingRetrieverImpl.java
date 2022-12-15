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

package com.liferay.search.experiences.internal.ml.text.embedding;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.search.experiences.configuration.SemanticSearchConfiguration;
import com.liferay.search.experiences.ml.text.embedding.TextEmbeddingRetriever;

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
	enabled = false, service = TextEmbeddingRetriever.class
)
public class TextEmbeddingRetrieverImpl implements TextEmbeddingRetriever {

	@Override
	public List<String> getAvailableProviderNames() {
		return ListUtil.fromCollection(
			_textEmbeddingProviderServiceTrackerMap.keySet());
	}

	@Override
	public Double[] getTextEmbedding(
		SemanticSearchConfiguration semanticSearchConfiguration, String text) {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-163688")) ||
			!semanticSearchConfiguration.textEmbeddingsEnabled()) {

			return new Double[0];
		}

		TextEmbeddingProvider textEmbeddingProvider =
			_textEmbeddingProviderServiceTrackerMap.getService(
				semanticSearchConfiguration.textEmbeddingProvider());

		if (textEmbeddingProvider == null) {
			return new Double[0];
		}

		return textEmbeddingProvider.getEmbedding(
			semanticSearchConfiguration, text);
	}

	@Override
	public Double[] getTextEmbedding(String text) {
		return getTextEmbedding(_semanticSearchConfiguration, text);
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

	private volatile SemanticSearchConfiguration _semanticSearchConfiguration;
	private ServiceTrackerMap<String, TextEmbeddingProvider>
		_textEmbeddingProviderServiceTrackerMap;

}