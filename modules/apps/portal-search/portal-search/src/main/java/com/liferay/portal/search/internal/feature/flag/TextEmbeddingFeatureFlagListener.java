/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.feature.flag;

import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;
import com.liferay.portal.search.internal.ml.embedding.text.TextEmbeddingProvidersHolder;
import com.liferay.portal.search.internal.ml.embedding.text.VertexAITextEmbeddingProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	property = "featureFlagKey=LPD-31789", service = FeatureFlagListener.class
)
public class TextEmbeddingFeatureFlagListener implements FeatureFlagListener {

	@Override
	public void onValue(
		long companyId, String featureFlagKey, boolean enabled) {

		if (enabled) {
			_textEmbeddingProvidersHolder.addTextEmbeddingProvider(
				"vertexAI", new VertexAITextEmbeddingProvider());
		}
		else {
			_textEmbeddingProvidersHolder.removeTextEmbeddingProvider(
				"vertexAI");
		}
	}

	@Reference
	private TextEmbeddingProvidersHolder _textEmbeddingProvidersHolder;

}