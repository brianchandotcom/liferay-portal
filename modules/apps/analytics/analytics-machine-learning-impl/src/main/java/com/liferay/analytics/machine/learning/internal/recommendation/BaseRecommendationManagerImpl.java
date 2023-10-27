/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.internal.recommendation;

import com.liferay.analytics.machine.learning.content.Recommendation;
import com.liferay.analytics.machine.learning.internal.recommendation.constants.RecommendationField;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
public abstract class BaseRecommendationManagerImpl<T extends Recommendation> {

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

	protected Document getDocument(T recommendation) {
		Document document = new DocumentImpl();

		document.addDate(Field.CREATE_DATE, recommendation.getCreateDate());
		document.addNumber(Field.COMPANY_ID, recommendation.getCompanyId());
		document.addNumber(
			RecommendationField.RECOMMENDED_ENTRY_CLASS_PK,
			recommendation.getRecommendedEntryClassPK());
		document.addNumber(
			RecommendationField.SCORE, recommendation.getScore());
		document.addText(RecommendationField.JOB_ID, recommendation.getJobId());

		return document;
	}

	protected long getHash(Object... values) {
		StringBundler sb = new StringBundler(values.length);

		for (Object value : values) {
			sb.append(value);
		}

		return HashUtil.hash(values.length, sb.toString());
	}

	protected T getRecommendation(T recommendation, Document document) {
		recommendation.setCompanyId(
			GetterUtil.getLong(document.get(Field.COMPANY_ID)));
		recommendation.setCreateDate(_getDate(document.get(Field.CREATE_DATE)));
		recommendation.setJobId(document.get(RecommendationField.JOB_ID));
		recommendation.setRecommendedEntryClassPK(
			GetterUtil.getLong(
				document.get(RecommendationField.RECOMMENDED_ENTRY_CLASS_PK)));
		recommendation.setScore(
			GetterUtil.getFloat(document.get(RecommendationField.SCORE)));

		return recommendation;
	}

	protected List<T> getSearchResults(
		SearchSearchRequest searchSearchRequest) {

		SearchSearchResponse searchSearchResponse = searchEngineAdapter.execute(
			searchSearchRequest);

		return toList(searchSearchResponse.getHits());
	}

	protected SearchSearchRequest getSearchSearchRequest(
		String indexName, long companyId, long entryClassPK) {

		BooleanFilter booleanFilter = new BooleanFilter() {
			{
				add(
					new TermFilter(Field.COMPANY_ID, String.valueOf(companyId)),
					BooleanClauseOccur.MUST);
				add(
					new TermFilter(
						Field.ENTRY_CLASS_PK, String.valueOf(entryClassPK)),
					BooleanClauseOccur.MUST);
			}
		};

		return new SearchSearchRequest() {
			{
				setIndexNames(new String[] {indexName});
				setQuery(
					new BooleanQueryImpl() {
						{
							setPreBooleanFilter(booleanFilter);
						}
					});
				setSize(Integer.valueOf(SEARCH_SEARCH_REQUEST_SIZE));
				setStats(Collections.emptyMap());
			}
		};
	}

	protected abstract Document toDocument(T model);

	protected List<T> toList(Hits hits) {
		return toList(_getDocuments(hits));
	}

	protected List<T> toList(List<Document> documents) {
		return TransformUtil.transform(documents, this::toModel);
	}

	protected abstract T toModel(Document document);

	protected static final int SEARCH_SEARCH_REQUEST_SIZE = 10;

	@Reference
	protected volatile SearchEngineAdapter searchEngineAdapter;

	private Date _getDate(String dateString) {
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