/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.ml.embedding.text;

import java.util.List;

/**
 * @author Petteri Karttunen
 */
public interface TextEmbeddingProvidersHolder {

	public void addTextEmbeddingProvider(
		String name, TextEmbeddingProvider textEmbeddingProvider);

	public TextEmbeddingProvider getTextEmbeddingProvider(String name);

	public List<String> getTextEmbeddingProviderNames();

	public void removeTextEmbeddingProvider(String name);

}