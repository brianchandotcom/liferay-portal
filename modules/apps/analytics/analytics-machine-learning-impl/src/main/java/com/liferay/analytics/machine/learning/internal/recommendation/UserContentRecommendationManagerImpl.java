/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.internal.recommendation;

import com.liferay.analytics.machine.learning.content.UserContentRecommendation;
import com.liferay.analytics.machine.learning.content.UserContentRecommendationManager;
import com.liferay.analytics.machine.learning.internal.recommendation.search.RecommendationField;
import com.liferay.analytics.machine.learning.internal.search.api.RecommendationIndexer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(service = UserContentRecommendationManager.class)
public class UserContentRecommendationManagerImpl
	implements UserContentRecommendationManager {

	@Override
	public UserContentRecommendation addUserContentRecommendation(
			UserContentRecommendation userContentRecommendation)
		throws PortalException {

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			_recommendationIndexer.getIndexName(
				userContentRecommendation.getCompanyId()),
			_toDocument(userContentRecommendation));

		IndexDocumentResponse indexDocumentResponse =
			_searchEngineAdapter.execute(indexDocumentRequest);

		if ((indexDocumentResponse.getStatus() < 200) ||
			(indexDocumentResponse.getStatus() >= 300)) {

			throw new PortalException(
				"Index request return status: " +
					indexDocumentResponse.getStatus());
		}

		return userContentRecommendation;
	}

	@Override
	public List<UserContentRecommendation> getUserContentRecommendations(
			long[] assetCategoryIds, long companyId, long userId)
		throws PortalException {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			new String[] {_recommendationIndexer.getIndexName(companyId)});

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		booleanQuery.setPreBooleanFilter(
			new BooleanFilter() {
				{
					add(
						new TermFilter(
							Field.COMPANY_ID, String.valueOf(companyId)),
						BooleanClauseOccur.MUST);
					add(
						new TermFilter(
							Field.ENTRY_CLASS_PK, String.valueOf(userId)),
						BooleanClauseOccur.MUST);
				}
			});

		if (assetCategoryIds != null) {
			for (long assetCategoryId : assetCategoryIds) {
				TermQuery categoryIdTermQuery = new TermQueryImpl(
					Field.ASSET_CATEGORY_IDS, String.valueOf(assetCategoryId));

				booleanQuery.add(categoryIdTermQuery, BooleanClauseOccur.MUST);
			}
		}

		searchSearchRequest.setQuery(booleanQuery);

		searchSearchRequest.setSize(_SEARCH_SEARCH_REQUEST_SIZE);

		Sort sort = SortFactoryUtil.create(
			RecommendationField.SCORE, Sort.FLOAT_TYPE, true);

		searchSearchRequest.setSorts(new Sort[] {sort});

		searchSearchRequest.setStats(Collections.emptyMap());

		return _getUserContentRecommendations(searchSearchRequest);
	}

	private Date _getDate(String dateString) {
		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSSX");

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

	private long _getHash(Object... values) {
		StringBundler sb = new StringBundler(values.length);

		for (Object value : values) {
			sb.append(value);
		}

		return HashUtil.hash(values.length, sb.toString());
	}

	private List<UserContentRecommendation> _getUserContentRecommendations(
		SearchSearchRequest searchSearchRequest) {

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		return TransformUtil.transform(
			_getDocuments(searchSearchResponse.getHits()),
			this::_toUserContentRecommendation);
	}

	private Document _toDocument(
		UserContentRecommendation userContentRecommendation) {

		Document document = new DocumentImpl();

		document.addNumber(
			Field.ASSET_CATEGORY_IDS,
			userContentRecommendation.getAssetCategoryIds());
		document.addDate(
			Field.CREATE_DATE, userContentRecommendation.getCreateDate());
		document.addNumber(
			Field.COMPANY_ID, userContentRecommendation.getCompanyId());
		document.addNumber(
			Field.ENTRY_CLASS_PK, userContentRecommendation.getEntryClassPK());
		document.addNumber(
			RecommendationField.RECOMMENDED_ENTRY_CLASS_PK,
			userContentRecommendation.getRecommendedEntryClassPK());
		document.addNumber(
			RecommendationField.SCORE, userContentRecommendation.getScore());
		document.addText(
			RecommendationField.JOB_ID, userContentRecommendation.getJobId());
		document.addKeyword(
			Field.UID,
			String.valueOf(
				_getHash(
					userContentRecommendation.getEntryClassPK(),
					userContentRecommendation.getRecommendedEntryClassPK())));

		return document;
	}

	private UserContentRecommendation _toUserContentRecommendation(
		Document document) {

		UserContentRecommendation userContentRecommendation =
			new UserContentRecommendation();

		userContentRecommendation.setAssetCategoryIds(
			GetterUtil.getLongValues(
				document.getValues(Field.ASSET_CATEGORY_IDS)));
		userContentRecommendation.setCompanyId(
			GetterUtil.getLong(document.get(Field.COMPANY_ID)));
		userContentRecommendation.setCreateDate(
			_getDate(document.get(Field.CREATE_DATE)));
		userContentRecommendation.setEntryClassPK(
			GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)));
		userContentRecommendation.setJobId(
			document.get(RecommendationField.JOB_ID));
		userContentRecommendation.setRecommendedEntryClassPK(
			GetterUtil.getLong(
				document.get(RecommendationField.RECOMMENDED_ENTRY_CLASS_PK)));
		userContentRecommendation.setScore(
			GetterUtil.getFloat(document.get(RecommendationField.SCORE)));

		return userContentRecommendation;
	}

	private static final int _SEARCH_SEARCH_REQUEST_SIZE = 10;

	private static final Log _log = LogFactoryUtil.getLog(
		UserContentRecommendationManagerImpl.class);

	@Reference(
		target = "(component.name=com.liferay.analytics.machine.learning.internal.recommendation.search.UserContentRecommendationIndexer)"
	)
	private RecommendationIndexer _recommendationIndexer;

	@Reference
	private volatile SearchEngineAdapter _searchEngineAdapter;

}