/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.internal.recommendation;

import com.liferay.analytics.machine.learning.content.UserContentRecommendation;
import com.liferay.analytics.machine.learning.content.UserContentRecommendationManager;
import com.liferay.analytics.machine.learning.internal.recommendation.constants.RecommendationIndexNames;
import com.liferay.analytics.machine.learning.internal.recommendation.search.RecommendationField;
import com.liferay.analytics.machine.learning.internal.recommendation.search.RecommendationIndexer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.index.IndexNameBuilder;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(service = UserContentRecommendationManager.class)
public class UserContentRecommendationManagerImpl
	extends BaseRecommendationManagerImpl<UserContentRecommendation>
	implements UserContentRecommendationManager {

	@Override
	public UserContentRecommendation addUserContentRecommendation(
			UserContentRecommendation userContentRecommendation)
		throws PortalException {

		return addRecommendation(
			userContentRecommendation,
			_recommendationIndexer.getIndexName(
				userContentRecommendation.getCompanyId()));
	}

	@Override
	public List<UserContentRecommendation> getUserContentRecommendations(
			long[] assetCategoryIds, long companyId, long userId, int start,
			int end)
		throws PortalException {

		SearchSearchRequest searchSearchRequest = _getSearchSearchRequest(
			assetCategoryIds, companyId, userId);

		searchSearchRequest.setSize(end - start);
		searchSearchRequest.setStart(start);

		return getSearchResults(searchSearchRequest);
	}

	@Override
	public long getUserContentRecommendationsCount(
			long[] assetCategoryIds, long companyId, long userId)
		throws PortalException {

		return getSearchResultsCount(
			_getSearchSearchRequest(assetCategoryIds, companyId, userId));
	}

	@Activate
	protected void activate() {
		_recommendationIndexer = new RecommendationIndexer(
			RecommendationIndexNames.USER_CONTENT_RECOMMENDATION,
			_indexNameBuilder, _searchCapabilities, searchEngineAdapter);
	}

	@Override
	protected Document toDocument(
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
				getHash(
					userContentRecommendation.getEntryClassPK(),
					userContentRecommendation.getRecommendedEntryClassPK())));

		return document;
	}

	@Override
	protected UserContentRecommendation toModel(Document document) {
		UserContentRecommendation userContentRecommendation =
			new UserContentRecommendation();

		userContentRecommendation.setAssetCategoryIds(
			GetterUtil.getLongValues(
				document.getValues(Field.ASSET_CATEGORY_IDS)));
		userContentRecommendation.setCompanyId(
			GetterUtil.getLong(document.get(Field.COMPANY_ID)));
		userContentRecommendation.setCreateDate(
			getDate(document.get(Field.CREATE_DATE)));
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

	private SearchSearchRequest _getSearchSearchRequest(
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

		return searchSearchRequest;
	}

	private static final int _SEARCH_SEARCH_REQUEST_SIZE = 10;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	private RecommendationIndexer _recommendationIndexer;

	@Reference
	private SearchCapabilities _searchCapabilities;

}