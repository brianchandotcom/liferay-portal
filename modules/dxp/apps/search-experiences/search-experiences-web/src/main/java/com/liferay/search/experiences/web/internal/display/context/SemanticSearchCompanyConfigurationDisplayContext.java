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

package com.liferay.search.experiences.web.internal.display.context;

import java.util.List;
import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class SemanticSearchCompanyConfigurationDisplayContext {

	public List<String> getAvailableEmbeddingVectorDimensions() {
		return _availableEmbeddingVectorDimensions;
	}

	public Map<String, String> getAvailableLanguageDisplayNames() {
		return _availableLanguageDisplayNames;
	}

	public Map<String, String> getAvailableModelClassNames() {
		return _availableModelClassNames;
	}

	public Map<String, String> getAvailableTextEmbeddingProviders() {
		return _availableTextEmbeddingProviders;
	}

	public Map<String, String> getAvailableTextTruncationStrategies() {
		return _availableTextTruncationStrategies;
	}

	public int getTextEmbeddingCacheTimeout() {
		return _textEmbeddingCacheTimeout;
	}

	public String[] getTextEmbeddingProviderConfigurations() {
		return _textEmbeddingProviderConfigurations;
	}

	public boolean isTextEmbeddingsEnabled() {
		return _textEmbeddingsEnabled;
	}

	public void setAvailableEmbeddingVectorDimensions(
		List<String> availableEmbeddingVectorDimensions) {

		_availableEmbeddingVectorDimensions =
			availableEmbeddingVectorDimensions;
	}

	public void setAvailableLanguageDisplayNames(
		Map<String, String> availableLanguageDisplayNames) {

		_availableLanguageDisplayNames = availableLanguageDisplayNames;
	}

	public void setAvailableModelClassNames(
		Map<String, String> availableModelClassNames) {

		_availableModelClassNames = availableModelClassNames;
	}

	public void setAvailableTextEmbeddingProviders(
		Map<String, String> availableTextEmbeddingProviders) {

		_availableTextEmbeddingProviders = availableTextEmbeddingProviders;
	}

	public void setAvailableTextTruncationStrategies(
		Map<String, String> availableTextTruncationStrategies) {

		_availableTextTruncationStrategies = availableTextTruncationStrategies;
	}

	public int setTextEmbeddingCacheTimeout(int textEmbeddingCacheTimeout) {
		return _textEmbeddingCacheTimeout = textEmbeddingCacheTimeout;
	}

	public void setTextEmbeddingProviderConfigurations(
		String[] textEmbeddingProviderConfigurations) {

		_textEmbeddingProviderConfigurations =
			textEmbeddingProviderConfigurations;
	}

	public void setTextEmbeddingsEnabled(boolean textEmbeddingsEnabled) {
		_textEmbeddingsEnabled = textEmbeddingsEnabled;
	}

	private List<String> _availableEmbeddingVectorDimensions;
	private Map<String, String> _availableLanguageDisplayNames;
	private Map<String, String> _availableModelClassNames;
	private Map<String, String> _availableTextEmbeddingProviders;
	private Map<String, String> _availableTextTruncationStrategies;
	private int _textEmbeddingCacheTimeout;
	private String[] _textEmbeddingProviderConfigurations;
	private boolean _textEmbeddingsEnabled;

}