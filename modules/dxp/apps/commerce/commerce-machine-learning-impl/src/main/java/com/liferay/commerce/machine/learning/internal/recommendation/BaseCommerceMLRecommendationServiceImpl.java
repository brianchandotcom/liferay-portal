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

package com.liferay.commerce.machine.learning.internal.recommendation;

import com.liferay.commerce.machine.learning.internal.recommendation.constants.CommerceMLRecommendationField;
import com.liferay.commerce.machine.learning.recommendation.CommerceMLRecommendation;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
public abstract class BaseCommerceMLRecommendationServiceImpl
	<T extends CommerceMLRecommendation> {

	protected T addCommerceMLRecommendation(
			T model, String indexName, String documentType)
		throws PortalException {

		Document document = toDocument(model);

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			indexName, document);

		indexDocumentRequest.setType(documentType);

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

	protected T getBaseCommerceMLRecommendationModel(
		T commerceMLRecommendation, Document document) {

		commerceMLRecommendation.setCompanyId(
			GetterUtil.getLong(document.get(Field.COMPANY_ID)));

		commerceMLRecommendation.setCreateDate(
			_getDate(document.get(Field.CREATE_DATE)));

		commerceMLRecommendation.setJobId(
			document.get(CommerceMLRecommendationField.JOB_ID));

		commerceMLRecommendation.setRecommendedEntryClassPK(
			GetterUtil.getLong(
				document.get(
					CommerceMLRecommendationField.RECOMMENDED_ENTRY_CLASS_PK)));

		commerceMLRecommendation.setScore(
			GetterUtil.getFloat(
				document.get(CommerceMLRecommendationField.SCORE)));

		return commerceMLRecommendation;
	}

	protected Document getBaseDocument(T commerceMLRecommend) {
		Document document = new DocumentImpl();

		document.addNumber(
			Field.COMPANY_ID, commerceMLRecommend.getCompanyId());
		document.addDate(
			Field.CREATE_DATE, commerceMLRecommend.getCreateDate());
		document.addText(
			CommerceMLRecommendationField.JOB_ID,
			commerceMLRecommend.getJobId());
		document.addNumber(
			CommerceMLRecommendationField.RECOMMENDED_ENTRY_CLASS_PK,
			commerceMLRecommend.getRecommendedEntryClassPK());
		document.addNumber(
			CommerceMLRecommendationField.SCORE,
			commerceMLRecommend.getScore());

		return document;
	}

	protected long getHash(Object... values) {
		StringBuilder sb = new StringBuilder(values.length);

		for (Object value : values) {
			sb.append(value);
		}

		return HashUtil.hash(values.length, sb.toString());
	}

	protected List<T> getSearchResults(
		SearchSearchRequest searchSearchRequest) {

		SearchSearchResponse searchSearchResponse = searchEngineAdapter.execute(
			searchSearchRequest);

		return toModelList(searchSearchResponse.getHits());
	}

	protected SearchSearchRequest getSearchSearchRequest(
		String indexName, long companyId, long entryClassPK) {

		SearchSearchRequest searchRequest = new SearchSearchRequest();

		searchRequest.setIndexNames(new String[] {indexName});

		searchRequest.setSize(Integer.valueOf(DEFAULT_FETCH_SIZE));

		TermFilter companyTermFilter = new TermFilter(
			Field.COMPANY_ID, String.valueOf(companyId));

		TermFilter entryClassPKTermFilter = new TermFilter(
			Field.ENTRY_CLASS_PK, String.valueOf(entryClassPK));

		BooleanFilter booleanFilter = new BooleanFilter();

		booleanFilter.add(companyTermFilter, BooleanClauseOccur.MUST);
		booleanFilter.add(entryClassPKTermFilter, BooleanClauseOccur.MUST);

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		booleanQuery.setPreBooleanFilter(booleanFilter);

		searchRequest.setQuery(booleanQuery);

		searchRequest.setStats(Collections.emptyMap());

		return searchRequest;
	}

	protected abstract Document toDocument(T model);

	protected abstract T toModel(Document document);

	protected List<T> toModelList(Hits hits) {
		List<Document> documents = _getDocumentList(hits);

		return toModelList(documents);
	}

	protected List<T> toModelList(List<Document> documents) {
		Stream<Document> documentsStream = documents.stream();

		return documentsStream.map(
			this::toModel
		).collect(
			Collectors.toList()
		);
	}

	protected static final int DEFAULT_FETCH_SIZE = 10;

	@Reference
	protected volatile SearchEngineAdapter searchEngineAdapter;

	private Date _getDate(String dateString) {
		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			_INDEX_DATE_FORMAT_PATTERN);

		try {
			return dateFormat.parse(dateString);
		}
		catch (ParseException parseException) {
		}

		return null;
	}

	private List<Document> _getDocumentList(Hits hits) {
		List<Document> list = new ArrayList<>(hits.toList());

		Map<String, Hits> groupedHits = hits.getGroupedHits();

		for (Map.Entry<String, Hits> entry : groupedHits.entrySet()) {
			list.addAll(_getDocumentList(entry.getValue()));
		}

		return list;
	}

	private static final String _INDEX_DATE_FORMAT_PATTERN =
		"yyyy-MM-dd'T'HH:mm:ss.SSSX";

}