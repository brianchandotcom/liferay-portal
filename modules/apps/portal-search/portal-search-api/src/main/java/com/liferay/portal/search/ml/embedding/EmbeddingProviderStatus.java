/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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