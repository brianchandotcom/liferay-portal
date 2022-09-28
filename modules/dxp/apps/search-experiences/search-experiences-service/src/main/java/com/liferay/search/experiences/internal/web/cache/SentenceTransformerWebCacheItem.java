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
import com.liferay.search.experiences.configuration.SentenceTransformerConfiguration;
import com.liferay.search.experiences.internal.ml.sentence.embedding.SentenceEmbeddingRetriever;

import java.beans.ExceptionListener;

/**
 * @author Petteri Karttunen
 */
public class SentenceTransformerWebCacheItem implements WebCacheItem {

	public static Double[] get(
		ExceptionListener exceptionListener,
		SentenceEmbeddingRetriever sentenceEmbeddingRetriever,
		SentenceTransformerConfiguration sentenceTransformerConfiguration,
		String text) {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-163688")) ||
			!sentenceTransformerConfiguration.enabled()) {

			return new Double[0];
		}

		try {
			return (Double[])WebCachePoolUtil.get(
				StringBundler.concat(
					SentenceTransformerWebCacheItem.class.getName(),
					StringPool.POUND, sentenceTransformerConfiguration.model(),
					StringPool.POUND, text),
				new SentenceTransformerWebCacheItem(
					sentenceEmbeddingRetriever,
					sentenceTransformerConfiguration, text));
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
		SentenceTransformerConfiguration sentenceTransformerConfiguration,
		String text) {

		_sentenceEmbeddingRetriever = sentenceEmbeddingRetriever;
		_sentenceTransformerConfiguration = sentenceTransformerConfiguration;
		_text = text;
	}

	@Override
	public Double[] convert(String key) {
		try {
			return _sentenceEmbeddingRetriever.getEmbedding(_text);
		}
		catch (Exception exception) {
			throw new InvalidWebCacheItemException(exception);
		}
	}

	@Override
	public long getRefreshTime() {
		if (_sentenceTransformerConfiguration.enabled()) {
			return _sentenceTransformerConfiguration.cacheTimeout();
		}

		return 0;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SentenceTransformerWebCacheItem.class);

	private final SentenceEmbeddingRetriever _sentenceEmbeddingRetriever;
	private final SentenceTransformerConfiguration
		_sentenceTransformerConfiguration;
	private final String _text;

}