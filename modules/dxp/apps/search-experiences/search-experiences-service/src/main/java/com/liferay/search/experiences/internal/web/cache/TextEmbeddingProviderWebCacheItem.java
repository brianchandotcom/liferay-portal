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
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;
import com.liferay.search.experiences.blueprint.exception.InvalidWebCacheItemException;
import com.liferay.search.experiences.ml.embedding.text.TextEmbeddingRetriever;

import java.beans.ExceptionListener;

/**
 * @author Petteri Karttunen
 */
public class TextEmbeddingProviderWebCacheItem implements WebCacheItem {

	public static Double[] get(
		ExceptionListener exceptionListener, String providerName,
		long refreshTime, TextEmbeddingRetriever textEmbeddingRetriever,
		String text) {

		try {
			return (Double[])WebCachePoolUtil.get(
				StringBundler.concat(
					TextEmbeddingProviderWebCacheItem.class.getName(),
					StringPool.POUND, providerName, StringPool.POUND, text),
				new TextEmbeddingProviderWebCacheItem(
					providerName, refreshTime, textEmbeddingRetriever, text));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			exceptionListener.exceptionThrown(exception);

			return new Double[0];
		}
	}

	public TextEmbeddingProviderWebCacheItem(
		String providerName, long refreshTime,
		TextEmbeddingRetriever textEmbeddingRetriever, String text) {

		_providerName = providerName;
		_refreshTime = refreshTime;
		_textEmbeddingRetriever = textEmbeddingRetriever;
		_text = text;
	}

	@Override
	public Double[] convert(String key) {
		try {
			return _textEmbeddingRetriever.getTextEmbedding(
				_providerName, _text);
		}
		catch (Exception exception) {
			throw new InvalidWebCacheItemException(exception);
		}
	}

	@Override
	public long getRefreshTime() {
		return _refreshTime;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TextEmbeddingProviderWebCacheItem.class);

	private final String _providerName;
	private final long _refreshTime;
	private final String _text;
	private final TextEmbeddingRetriever _textEmbeddingRetriever;

}