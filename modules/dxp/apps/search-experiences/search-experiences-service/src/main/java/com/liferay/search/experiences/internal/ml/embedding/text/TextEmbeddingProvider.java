/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.ml.embedding.text;

import com.liferay.search.experiences.rest.dto.v1_0.EmbeddingProviderConfiguration;

/**
 * @author Petteri Karttunen
 */
public interface TextEmbeddingProvider {

	public Double[] getEmbedding(
		EmbeddingProviderConfiguration embeddingProviderConfiguration,
		String text);

}