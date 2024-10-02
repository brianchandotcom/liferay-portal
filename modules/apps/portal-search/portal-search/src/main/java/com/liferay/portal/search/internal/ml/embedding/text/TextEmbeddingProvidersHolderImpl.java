/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.ml.embedding.text;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(service = TextEmbeddingProvidersHolder.class)
public class TextEmbeddingProvidersHolderImpl
	implements TextEmbeddingProvidersHolder {

	@Override
	public void addTextEmbeddingProvider(
		String name, TextEmbeddingProvider textEmbeddingProvider) {

		if (_log.isDebugEnabled()) {
			_log.debug("Enabling " + name);
		}

		_textEmbeddingProviders.put(name, textEmbeddingProvider);
	}

	@Override
	public TextEmbeddingProvider getTextEmbeddingProvider(String name) {
		return _textEmbeddingProviders.get(name);
	}

	@Override
	public List<String> getTextEmbeddingProviderNames() {
		return ListUtil.fromCollection(_textEmbeddingProviders.keySet());
	}

	@Override
	public void removeTextEmbeddingProvider(String name) {
		if (!_textEmbeddingProviders.containsKey(name)) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Removing " + name);
		}

		_textEmbeddingProviders.remove(name);
	}

	@Activate
	protected void activate(
		Map<String, Object> properties, BundleContext bundleContext) {

		String[] disabledProviders = (String[])properties.get(
			"disabledProviders");

		addTextEmbeddingProvider(
			disabledProviders, "huggingFaceInferenceAPI",
			new HuggingFaceInferenceAPITextEmbeddingProvider());
		addTextEmbeddingProvider(
			disabledProviders, "huggingFaceInferenceEndpoint",
			new HuggingFaceInferenceEndpointTextEmbeddingProvider());
		addTextEmbeddingProvider(
			disabledProviders, "openai", new OpenAITextEmbeddingProvider());
		addTextEmbeddingProvider(
			disabledProviders, "txtai", new TXTAITextEmbeddingProvider());
	}

	protected void addTextEmbeddingProvider(
		String[] disabledProviders, String name,
		TextEmbeddingProvider textEmbeddingProvider) {

		if (ArrayUtil.contains(disabledProviders, name)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Disabling " + name);
			}

			return;
		}

		addTextEmbeddingProvider(name, textEmbeddingProvider);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TextEmbeddingProvidersHolderImpl.class);

	private final Map<String, TextEmbeddingProvider> _textEmbeddingProviders =
		new ConcurrentHashMap<>();

}