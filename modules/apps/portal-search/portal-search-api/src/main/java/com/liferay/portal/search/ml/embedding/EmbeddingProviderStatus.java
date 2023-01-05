/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.ml.embedding;

/**
 * @author Petteri Karttunen
 */
public class EmbeddingProviderStatus {

	public EmbeddingProviderStatus(
		int embeddingVectorDimensions, String providerName) {

		_embeddingVectorDimensions = embeddingVectorDimensions;
		_providerName = providerName;
	}

	public EmbeddingProviderStatus(String errorMessage, String providerName) {
		_errorMessage = errorMessage;
		_providerName = providerName;
	}

	public int getEmbeddingVectorDimensions() {
		return _embeddingVectorDimensions;
	}

	public String getErrorMessage() {
		return _errorMessage;
	}

	public String getProviderName() {
		return _providerName;
	}

	private int _embeddingVectorDimensions;
	private String _errorMessage;
	private final String _providerName;

}