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

package com.liferay.search.experiences.web.internal.sentence.transformer.configuration.display.context;

import java.util.List;
import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class SentenceTransformerCompanyConfigurationDisplayContext {

	public List<String> getAvailableEmbeddingVectorDimensions() {
		return _availableEmbeddingVectorDimensions;
	}

	public Map<String, String> getAvailableEntryClassNames() {
		return _availableEntryClassNames;
	}

	public Map<String, String> getAvailableLanguages() {
		return _availableLanguages;
	}

	public Map<String, String> getAvailableSentenceTranformerProviders() {
		return _availableSentenceTranformerProviders;
	}

	public Map<String, String> getAvailableTextTruncationStrategies() {
		return _availableTextTruncationStrategies;
	}

	public int getCacheTimeout() {
		return _cacheTimeout;
	}

	public int getEmbeddingVectorDimensions() {
		return _embeddingVectorDimensions;
	}

	public List<String> getEntryClassNames() {
		return _entryClassNames;
	}

	public String getHuggingFaceAccessToken() {
		return _huggingFaceAccessToken;
	}

	public List<String> getLanguageIds() {
		return _languageIds;
	}

	public int getMaxCharacterCount() {
		return _maxCharacterCount;
	}

	public String getModel() {
		return _model;
	}

	public int getModelTimeout() {
		return _modelTimeout;
	}

	public String getSentenceTransformerProvider() {
		return _sentenceTransformerProvider;
	}

	public String getTextTruncationStrategy() {
		return _textTruncationStrategy;
	}

	public String getTxtAihost() {
		return _txtAiHost;
	}

	public boolean isEnabled() {
		return _enabled;
	}

	public boolean isEnableGPU() {
		return _enableGPU;
	}

	public void setAvailableEmbeddingVectorDimensions(
		List<String> availableEmbeddingVectorDimensions) {

		_availableEmbeddingVectorDimensions =
			availableEmbeddingVectorDimensions;
	}

	public void setAvailableEntryClassNames(
		Map<String, String> availableEntryClassNames) {

		_availableEntryClassNames = availableEntryClassNames;
	}

	public void setAvailableLanguages(Map<String, String> availableLanguages) {
		_availableLanguages = availableLanguages;
	}

	public void setAvailableSentenceTranformerProviders(
		Map<String, String> availableSentenceTranformerProviders) {

		_availableSentenceTranformerProviders =
			availableSentenceTranformerProviders;
	}

	public void setAvailableTextTruncationStrategies(
		Map<String, String> availableTextTruncationStrategies) {

		_availableTextTruncationStrategies = availableTextTruncationStrategies;
	}

	public void setCacheTimeout(int cacheTimeout) {
		_cacheTimeout = cacheTimeout;
	}

	public void setEmbeddingVectorDimensions(int embeddingVectorDimensions) {
		_embeddingVectorDimensions = embeddingVectorDimensions;
	}

	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	public void setEnableGPU(boolean useGPU) {
		_enableGPU = useGPU;
	}

	public void setEntryClassNames(List<String> entryClassNames) {
		_entryClassNames = entryClassNames;
	}

	public void setHuggingFaceAccessToken(String huggingFaceAccessToken) {
		_huggingFaceAccessToken = huggingFaceAccessToken;
	}

	public void setLanguageIds(List<String> languageIds) {
		_languageIds = languageIds;
	}

	public void setMaxCharacterCount(int maxCharacterCount) {
		_maxCharacterCount = maxCharacterCount;
	}

	public void setModel(String model) {
		_model = model;
	}

	public void setModelTimeout(int modelTimeout) {
		_modelTimeout = modelTimeout;
	}

	public void setSentenceTransformerProvider(
		String sentenceTransformerProvider) {

		_sentenceTransformerProvider = sentenceTransformerProvider;
	}

	public void setTextTruncationStrategy(String textTruncationStrategy) {
		_textTruncationStrategy = textTruncationStrategy;
	}

	public void setTxtAihost(String txtAihost) {
		_txtAiHost = txtAihost;
	}

	private List<String> _availableEmbeddingVectorDimensions;
	private Map<String, String> _availableEntryClassNames;
	private Map<String, String> _availableLanguages;
	private Map<String, String> _availableSentenceTranformerProviders;
	private Map<String, String> _availableTextTruncationStrategies;
	private int _cacheTimeout;
	private int _embeddingVectorDimensions;
	private boolean _enabled;
	private boolean _enableGPU;
	private List<String> _entryClassNames;
	private String _huggingFaceAccessToken;
	private List<String> _languageIds;
	private int _maxCharacterCount;
	private String _model;
	private int _modelTimeout;
	private String _sentenceTransformerProvider;
	private String _textTruncationStrategy;
	private String _txtAiHost;

}