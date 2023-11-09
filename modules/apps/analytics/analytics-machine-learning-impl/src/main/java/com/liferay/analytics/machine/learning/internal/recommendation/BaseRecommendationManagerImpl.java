/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.internal.recommendation;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
public abstract class BaseRecommendationManagerImpl<T> {

	protected T addRecommendation(T model, String indexName)
		throws PortalException {

		Document document = toDocument(model);

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			indexName, document);

		IndexDocumentResponse indexDocumentResponse =
			searchEngineAdapter.execute(indexDocumentRequest);

		if ((indexDocumentResponse.getStatus() < 200) ||
			(indexDocumentResponse.getStatus() >= 300)) {

			throw new PortalException(
				String.format(
					"Index request return status: %d",
					indexDocumentResponse.getStatus()));
		}

		return model;
	}

	protected Date getDate(String dateString) {
		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			_INDEX_DATE_FORMAT_PATTERN);

		try {
			return dateFormat.parse(dateString);
		}
		catch (ParseException parseException) {
			if (_log.isDebugEnabled()) {
				_log.debug(parseException);
			}
		}

		return null;
	}

	protected long getHash(Object... values) {
		StringBundler sb = new StringBundler(values.length);

		for (Object value : values) {
			sb.append(value);
		}

		return HashUtil.hash(values.length, sb.toString());
	}

	protected List<T> getSearchResults(
		SearchSearchRequest searchSearchRequest) {

		SearchSearchResponse searchSearchResponse = searchEngineAdapter.execute(
			searchSearchRequest);

		return TransformUtil.transform(
			_getDocuments(searchSearchResponse.getHits()), this::toModel);
	}

	protected long getSearchResultsCount(
		SearchSearchRequest searchSearchRequest) {

		SearchSearchResponse searchSearchResponse = searchEngineAdapter.execute(
			searchSearchRequest);

		return searchSearchResponse.getCount();
	}

	protected abstract Document toDocument(T model);

	protected abstract T toModel(Document document);

	@Reference
	protected volatile SearchEngineAdapter searchEngineAdapter;

	private List<Document> _getDocuments(Hits hits) {
		List<Document> documents = new ArrayList<>(hits.toList());

		Map<String, Hits> groupedHits = hits.getGroupedHits();

		for (Map.Entry<String, Hits> entry : groupedHits.entrySet()) {
			documents.addAll(_getDocuments(entry.getValue()));
		}

		return documents;
	}

	private static final String _INDEX_DATE_FORMAT_PATTERN =
		"yyyy-MM-dd'T'HH:mm:ss.SSSX";

	private static final Log _log = LogFactoryUtil.getLog(
		BaseRecommendationManagerImpl.class);

}