/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.searcher;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.spi.searcher.SearchRequestContributor;
import com.liferay.portal.search.tuning.rankings.web.internal.index.Ranking;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexName;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;
import com.liferay.portal.search.tuning.rankings.web.internal.searcher.helper.RankingSearchRequestHelper;

import java.util.List;
import java.util.function.Function;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(
	property = "search.request.contributor.id=com.liferay.portal.search.ranking",
	service = SearchRequestContributor.class
)
public class RankingSearchRequestContributor
	implements SearchRequestContributor {

	@Override
	public SearchRequest contribute(SearchRequest searchRequest) {
		if (isSearchEngine("Solr")) {
			return searchRequest;
		}

		RankingIndexName rankingIndexName = _getRankingIndexName(searchRequest);

		if (!rankingIndexReader.isExists(rankingIndexName)) {
			return searchRequest;
		}

		SearchContext searchContext = _getSearchContext(searchRequest);

		List<Ranking> rankings = rankingIndexReader.fetch(
			_getGroupExternalReferenceCode(searchContext.getGroupIds()),
			searchRequest.getQueryString(), rankingIndexName,
			GetterUtil.getString(
				searchContext.getAttribute(
					"search.experiences.blueprint.external.reference.code")));

		if (rankings == null) {
			return searchRequest;
		}

		SearchRequest contributeSearchRequest = contribute(
			rankings, searchRequest);

		if (contributeSearchRequest == null) {
			return searchRequest;
		}

		return contributeSearchRequest;
	}

	protected SearchRequest contribute(
		List<Ranking> rankings, SearchRequest searchRequest) {

		SearchRequestBuilder searchRequestBuilder =
			searchRequestBuilderFactory.builder(searchRequest);

		for (Ranking ranking : rankings) {
			rankingSearchRequestHelper.contribute(
				searchRequestBuilder, ranking);
		}

		return searchRequestBuilder.build();
	}

	protected boolean isSearchEngine(String engine) {
		SearchEngine searchEngine = searchEngineHelper.getSearchEngine();

		String vendor = searchEngine.getVendor();

		return vendor.equals(engine);
	}

	@Reference
	protected RankingIndexNameBuilder rankingIndexNameBuilder;

	@Reference
	protected RankingIndexReader rankingIndexReader;

	@Reference
	protected RankingSearchRequestHelper rankingSearchRequestHelper;

	@Reference
	protected SearchEngineHelper searchEngineHelper;

	@Reference
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

	private String _getGroupExternalReferenceCode(long[] groupIds) {
		if (ArrayUtil.isNotEmpty(groupIds)) {
			Group group = _groupLocalService.fetchGroup(groupIds[0]);

			if (group != null) {
				return group.getExternalReferenceCode();
			}

			return null;
		}

		return null;
	}

	private RankingIndexName _getRankingIndexName(SearchRequest searchRequest) {
		SearchRequestBuilder builder = searchRequestBuilderFactory.builder(
			searchRequest);

		long[] companyIds = new long[1];

		builder.withSearchContext(
			searchContext -> companyIds[0] = searchContext.getCompanyId());

		return rankingIndexNameBuilder.getRankingIndexName(companyIds[0]);
	}

	private SearchContext _getSearchContext(SearchRequest searchRequest) {
		SearchRequestBuilder searchRequestBuilder =
			searchRequestBuilderFactory.builder(searchRequest);

		return searchRequestBuilder.withSearchContextGet(Function.identity());
	}

	@Reference
	private GroupLocalService _groupLocalService;

}