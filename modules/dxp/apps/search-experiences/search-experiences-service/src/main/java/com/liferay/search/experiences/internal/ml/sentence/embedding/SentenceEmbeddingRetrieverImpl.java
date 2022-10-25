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

package com.liferay.search.experiences.internal.ml.sentence.embedding;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.search.experiences.configuration.SemanticSearchConfiguration;
import com.liferay.search.experiences.ml.sentence.embedding.SentenceEmbeddingRetriever;

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
	enabled = false, immediate = true,
	service = SentenceEmbeddingRetriever.class
)
public class SentenceEmbeddingRetrieverImpl
	implements SentenceEmbeddingRetriever {

	@Override
	public Double[] getSentenceEmbedding(
		SemanticSearchConfiguration semanticSearchConfiguration, String text) {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-163688"))) {
			return new Double[0];
		}

		SentenceTransformer sentenceTransformer =
			_sentenceTransformerServiceTrackerMap.getService(
				semanticSearchConfiguration.sentenceTransformProvider());

		if (sentenceTransformer == null) {
			return new Double[0];
		}

		return sentenceTransformer.getSentenceEmbedding(
			semanticSearchConfiguration, text);
	}

	@Override
	public Double[] getSentenceEmbedding(String text) {
		return getSentenceEmbedding(_semanticSearchConfiguration, text);
	}

	@Activate
	protected void activate(
		Map<String, Object> properties, BundleContext bundleContext) {

		_semanticSearchConfiguration = ConfigurableUtil.createConfigurable(
			SemanticSearchConfiguration.class, properties);

		_sentenceTransformerServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, SentenceTransformer.class,
				"search.experiences.sentence.transformer.name");
	}

	@Deactivate
	protected void deactivate() {
		_sentenceTransformerServiceTrackerMap.close();
	}

	private volatile SemanticSearchConfiguration _semanticSearchConfiguration;
	private ServiceTrackerMap<String, SentenceTransformer>
		_sentenceTransformerServiceTrackerMap;

}