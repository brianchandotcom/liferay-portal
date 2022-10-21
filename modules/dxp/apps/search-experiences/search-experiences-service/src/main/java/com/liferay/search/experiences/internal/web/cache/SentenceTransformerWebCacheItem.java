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

package com.liferay.search.experiences.internal.web.cache;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.search.experiences.blueprint.exception.InvalidWebCacheItemException;
import com.liferay.search.experiences.configuration.SemanticSearchConfiguration;
import com.liferay.search.experiences.internal.ml.sentence.embedding.SentenceEmbeddingRetriever;

import java.beans.ExceptionListener;

/**
 * @author Petteri Karttunen
 */
public class SentenceTransformerWebCacheItem implements WebCacheItem {

	public static Double[] get(
		ExceptionListener exceptionListener,
		SentenceEmbeddingRetriever sentenceEmbeddingRetriever,
		SemanticSearchConfiguration semanticSearchConfiguration, String text) {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-163688")) ||
			!semanticSearchConfiguration.enabled()) {

			return new Double[0];
		}

		try {
			return (Double[])WebCachePoolUtil.get(
				StringBundler.concat(
					SentenceTransformerWebCacheItem.class.getName(),
					StringPool.POUND, semanticSearchConfiguration.model(),
					StringPool.POUND, text),
				new SentenceTransformerWebCacheItem(
					sentenceEmbeddingRetriever, semanticSearchConfiguration,
					text));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			exceptionListener.exceptionThrown(exception);

			return new Double[0];
		}
	}

	public SentenceTransformerWebCacheItem(
		SentenceEmbeddingRetriever sentenceEmbeddingRetriever,
		SemanticSearchConfiguration semanticSearchConfiguration, String text) {

		_sentenceEmbeddingRetriever = sentenceEmbeddingRetriever;
		_semanticSearchConfiguration = semanticSearchConfiguration;
		_text = text;
	}

	@Override
	public Double[] convert(String key) {
		try {
			return _sentenceEmbeddingRetriever.getSentenceEmbedding(_text);
		}
		catch (Exception exception) {
			throw new InvalidWebCacheItemException(exception);
		}
	}

	@Override
	public long getRefreshTime() {
		if (_semanticSearchConfiguration.enabled()) {
			return _semanticSearchConfiguration.cacheTimeout();
		}

		return 0;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SentenceTransformerWebCacheItem.class);

	private final SemanticSearchConfiguration _semanticSearchConfiguration;
	private final SentenceEmbeddingRetriever _sentenceEmbeddingRetriever;
	private final String _text;

}