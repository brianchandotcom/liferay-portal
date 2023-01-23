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
		EmbeddingProviderStatus embeddingProviderStatus) {

		_embeddingVectorDimensions =
			embeddingProviderStatus._embeddingVectorDimensions;
		_errorMessage = embeddingProviderStatus._errorMessage;
		_providerName = embeddingProviderStatus._providerName;
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

	public static class EmbeddingProviderStatusBuilder {

		public EmbeddingProviderStatusBuilder() {
			_embeddingProviderStatus = new EmbeddingProviderStatus();
		}

		public EmbeddingProviderStatusBuilder(
			EmbeddingProviderStatus embeddingProviderStatus) {

			_embeddingProviderStatus = embeddingProviderStatus;
		}

		public EmbeddingProviderStatus build() {
			return new EmbeddingProviderStatus(_embeddingProviderStatus);
		}

		public EmbeddingProviderStatusBuilder embeddingVectorDimensions(
			int embeddingVectorDimensions) {

			_embeddingProviderStatus._embeddingVectorDimensions =
				embeddingVectorDimensions;

			return this;
		}

		public EmbeddingProviderStatusBuilder errorMessage(
			String errorMessage) {

			_embeddingProviderStatus._errorMessage = errorMessage;

			return this;
		}

		public EmbeddingProviderStatusBuilder providerName(
			String providerName) {

			_embeddingProviderStatus._providerName = providerName;

			return this;
		}

		private final EmbeddingProviderStatus _embeddingProviderStatus;

	}

	private EmbeddingProviderStatus() {
	}

	private int _embeddingVectorDimensions;
	private String _errorMessage;
	private String _providerName;

}