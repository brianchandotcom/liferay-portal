/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.internal.recommendation;

import com.liferay.analytics.machine.learning.content.MostViewedContentRecommendation;
import com.liferay.analytics.machine.learning.content.MostViewedContentRecommendationManager;
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
@Component(service = MostViewedContentRecommendationManager.class)
public class MostViewedContentRecommendationManagerImpl
	extends BaseRecommendationManagerImpl<MostViewedContentRecommendation>
	implements MostViewedContentRecommendationManager {

	@Override
	public MostViewedContentRecommendation addMostViewedContentRecommendation(
			MostViewedContentRecommendation mostViewedContentRecommendation)
		throws PortalException {

		return addRecommendation(
			mostViewedContentRecommendation,
			_recommendationIndexer.getIndexName(
				mostViewedContentRecommendation.getCompanyId()));
	}

	@Override
	public List<MostViewedContentRecommendation>
			getMostViewedContentRecommendations(
				long[] assetCategoryIds, long companyId, int end, int start,
				long userId)
		throws PortalException {

		SearchSearchRequest searchSearchRequest = _getSearchSearchRequest(
			assetCategoryIds, companyId);

		searchSearchRequest.setSize(end - start);
		searchSearchRequest.setStart(start);

		return getSearchResults(searchSearchRequest);
	}

	@Override
	public long getMostViewedContentRecommendationsCount(
			long[] assetCategoryIds, long companyId, long userId)
		throws PortalException {

		return getSearchResultsCount(
			_getSearchSearchRequest(assetCategoryIds, companyId));
	}

	@Activate
	protected void activate() {
		_recommendationIndexer = new RecommendationIndexer(
			RecommendationIndexNames.MOST_VIEWED_CONTENT_RECOMMENDATION,
			_indexNameBuilder, _searchCapabilities, searchEngineAdapter);
	}

	@Override
	protected Document toDocument(
		MostViewedContentRecommendation mostViewedContentRecommendation) {

		Document document = new DocumentImpl();

		document.addNumber(
			Field.ASSET_CATEGORY_IDS,
			mostViewedContentRecommendation.getAssetCategoryIds());
		document.addDate(
			Field.CREATE_DATE, mostViewedContentRecommendation.getCreateDate());
		document.addNumber(
			Field.COMPANY_ID, mostViewedContentRecommendation.getCompanyId());
		document.addText(
			RecommendationField.JOB_ID,
			mostViewedContentRecommendation.getJobId());
		document.addNumber(
			RecommendationField.RECOMMENDED_ENTRY_CLASS_PK,
			mostViewedContentRecommendation.getRecommendedEntryClassPK());
		document.addNumber(
			RecommendationField.SCORE,
			mostViewedContentRecommendation.getScore());
		document.addKeyword(
			Field.UID,
			String.valueOf(
				getHash(
					mostViewedContentRecommendation.getCompanyId(),
					mostViewedContentRecommendation.
						getRecommendedEntryClassPK())));

		return document;
	}

	@Override
	protected MostViewedContentRecommendation toModel(Document document) {
		MostViewedContentRecommendation mostViewedContentRecommendation =
			new MostViewedContentRecommendation();

		mostViewedContentRecommendation.setAssetCategoryIds(
			GetterUtil.getLongValues(
				document.getValues(Field.ASSET_CATEGORY_IDS)));
		mostViewedContentRecommendation.setCompanyId(
			GetterUtil.getLong(document.get(Field.COMPANY_ID)));
		mostViewedContentRecommendation.setCreateDate(
			getDate(document.get(Field.CREATE_DATE)));
		mostViewedContentRecommendation.setJobId(
			document.get(RecommendationField.JOB_ID));
		mostViewedContentRecommendation.setRecommendedEntryClassPK(
			GetterUtil.getLong(
				document.get(RecommendationField.RECOMMENDED_ENTRY_CLASS_PK)));
		mostViewedContentRecommendation.setScore(
			GetterUtil.getFloat(document.get(RecommendationField.SCORE)));

		return mostViewedContentRecommendation;
	}

	private SearchSearchRequest _getSearchSearchRequest(
			long[] assetCategoryIds, long companyId)
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

		Sort sort = SortFactoryUtil.create(
			RecommendationField.SCORE, Sort.FLOAT_TYPE, true);

		searchSearchRequest.setSorts(new Sort[] {sort});

		searchSearchRequest.setStats(Collections.emptyMap());

		return searchSearchRequest;
	}

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	private RecommendationIndexer _recommendationIndexer;

	@Reference
	private SearchCapabilities _searchCapabilities;

}