/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.search;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.Stats;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.internal.groupby.GroupByTranslator;
import com.liferay.portal.search.elasticsearch7.internal.highlight.HighlightTranslator;
import com.liferay.portal.search.elasticsearch7.internal.highlight.HighlighterTranslator;
import com.liferay.portal.search.elasticsearch7.internal.query.QueryToQueryBuilderTranslator;
import com.liferay.portal.search.elasticsearch7.internal.sort.SortTranslator;
import com.liferay.portal.search.elasticsearch7.internal.stats.StatsTranslator;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.groupby.GroupByRequest;
import com.liferay.portal.search.legacy.groupby.GroupByRequestFactory;
import com.liferay.portal.search.legacy.stats.StatsRequestBuilderFactory;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortFieldTranslator;
import com.liferay.portal.search.stats.StatsRequest;
import com.liferay.portal.search.stats.StatsRequestBuilder;

import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = SearchSearchRequestAssembler.class)
public class SearchSearchRequestAssemblerImpl
	implements SearchSearchRequestAssembler {

	@Override
	public void assemble(
		SearchSourceBuilder searchSourceBuilder,
		SearchSearchRequest searchSearchRequest, SearchRequest searchRequest) {

		_commonSearchSourceBuilderAssembler.assemble(
			searchSourceBuilder, searchSearchRequest, searchRequest);

		setFetchSource(searchSourceBuilder, searchSearchRequest);
		setGroupBy(searchSourceBuilder, searchSearchRequest);
		setGroupByRequests(searchSourceBuilder, searchSearchRequest);
		setHighlighter(searchSourceBuilder, searchSearchRequest);
		setPagination(searchSourceBuilder, searchSearchRequest);
		setPreference(searchRequest, searchSearchRequest);
		setSorts(searchSourceBuilder, searchSearchRequest);
		setStats(searchSourceBuilder, searchSearchRequest);
		setStoredFields(searchSourceBuilder, searchSearchRequest);
		setTrackScores(searchSourceBuilder, searchSearchRequest);
		setVersion(searchSourceBuilder, searchSearchRequest);

		searchRequest.source(searchSourceBuilder);
	}

	@Reference(unbind = "-")
	protected void setCommonSearchSourceBuilderAssembler(
		CommonSearchSourceBuilderAssembler commonSearchSourceBuilderAssembler) {

		_commonSearchSourceBuilderAssembler =
			commonSearchSourceBuilderAssembler;
	}

	protected void setFetchSource(
		SearchSourceBuilder searchSourceBuilder,
		SearchSearchRequest searchSearchRequest) {

		if ((searchSearchRequest.getFetchSource() != null) ||
			(searchSearchRequest.getFetchSourceExcludes() != null) ||
			(searchSearchRequest.getFetchSourceIncludes() != null)) {

			if (searchSearchRequest.getFetchSource() == null) {
				searchSourceBuilder.fetchSource(true);
			}
			else {
				searchSourceBuilder.fetchSource(
					searchSearchRequest.getFetchSource());
			}

			searchSourceBuilder.fetchSource(
				searchSearchRequest.getFetchSourceIncludes(),
				searchSearchRequest.getFetchSourceExcludes());
		}
	}

	protected void setGroupBy(
		SearchSourceBuilder searchSourceBuilder,
		SearchSearchRequest searchSearchRequest) {

		if (searchSearchRequest.getGroupBy() != null) {
			_groupByTranslator.translate(
				searchSourceBuilder,
				translate(searchSearchRequest.getGroupBy()),
				searchSearchRequest.getLocale(),
				searchSearchRequest.getSelectedFieldNames(),
				searchSearchRequest.getHighlightFieldNames(),
				searchSearchRequest.isHighlightEnabled(),
				searchSearchRequest.isHighlightRequireFieldMatch(),
				searchSearchRequest.getHighlightFragmentSize(),
				searchSearchRequest.getHighlightSnippetSize());
		}
	}

	@Reference(unbind = "-")
	protected void setGroupByRequestFactory(
		GroupByRequestFactory groupByRequestFactory) {

		_groupByRequestFactory = groupByRequestFactory;
	}

	protected void setGroupByRequests(
		SearchSourceBuilder searchSourceBuilder,
		SearchSearchRequest searchSearchRequest) {

		List<GroupByRequest> groupByRequests =
			searchSearchRequest.getGroupByRequests();

		if (ListUtil.isNotEmpty(groupByRequests)) {
			groupByRequests.forEach(
				groupByRequest -> _groupByTranslator.translate(
					searchSourceBuilder, groupByRequest,
					searchSearchRequest.getLocale(),
					searchSearchRequest.getSelectedFieldNames(),
					searchSearchRequest.getHighlightFieldNames(),
					searchSearchRequest.isHighlightEnabled(),
					searchSearchRequest.isHighlightRequireFieldMatch(),
					searchSearchRequest.getHighlightFragmentSize(),
					searchSearchRequest.getHighlightSnippetSize()));
		}
	}

	@Reference(unbind = "-")
	protected void setGroupByTranslator(GroupByTranslator groupByTranslator) {
		_groupByTranslator = groupByTranslator;
	}

	protected void setHighlighter(
		SearchSourceBuilder searchSourceBuilder,
		SearchSearchRequest searchSearchRequest) {

		if (searchSearchRequest.getHighlight() != null) {
			searchSourceBuilder.highlighter(
				_highlightTranslator.translate(
					searchSearchRequest.getHighlight(),
					_queryToQueryBuilderTranslator));
		}
		else if (searchSearchRequest.isHighlightEnabled()) {
			_highlighterTranslator.translate(
				searchSourceBuilder,
				searchSearchRequest.getHighlightFieldNames(),
				searchSearchRequest.isHighlightRequireFieldMatch(),
				searchSearchRequest.getHighlightFragmentSize(),
				searchSearchRequest.getHighlightSnippetSize(),
				searchSearchRequest.isLuceneSyntax());
		}
	}

	@Reference(unbind = "-")
	protected void setHighlighterTranslator(
		HighlighterTranslator highlighterTranslator) {

		_highlighterTranslator = highlighterTranslator;
	}

	protected void setPagination(
		SearchSourceBuilder searchSourceBuilder,
		SearchSearchRequest searchSearchRequest) {

		if (searchSearchRequest.getStart() != null) {
			searchSourceBuilder.from(searchSearchRequest.getStart());
		}

		if (searchSearchRequest.getSize() != null) {
			searchSourceBuilder.size(searchSearchRequest.getSize());
		}
	}

	protected void setPreference(
		SearchRequest searchRequest, SearchSearchRequest searchSearchRequest) {

		String preference = searchSearchRequest.getPreference();

		if (!Validator.isBlank(preference)) {
			searchRequest.preference(preference);
		}
	}

	@Reference(unbind = "-")
	protected void setQueryToQueryBuilderTranslator(
		QueryToQueryBuilderTranslator queryToQueryBuilderTranslator) {

		_queryToQueryBuilderTranslator = queryToQueryBuilderTranslator;
	}

	@Reference(unbind = "-")
	protected void setSortFieldTranslator(
		SortFieldTranslator<SortBuilder> sortFieldTranslator) {

		_sortFieldTranslator = sortFieldTranslator;
	}

	protected void setSorts(
		SearchSourceBuilder searchSourceBuilder,
		SearchSearchRequest searchSearchRequest) {

		for (Sort sort : searchSearchRequest.getSorts()) {
			searchSourceBuilder.sort(_sortFieldTranslator.translate(sort));
		}

		_sortTranslator.translate(
			searchSourceBuilder, searchSearchRequest.getSorts71());
	}

	@Reference(unbind = "-")
	protected void setSortTranslator(SortTranslator sortTranslator) {
		_sortTranslator = sortTranslator;
	}

	protected void setStats(
		SearchSourceBuilder searchSourceBuilder,
		SearchSearchRequest searchSearchRequest) {

		Map<String, Stats> statsMap = searchSearchRequest.getStats();

		if (!MapUtil.isEmpty(statsMap)) {
			statsMap.forEach(
				(key, stats) -> _statsTranslator.populateRequest(
					searchSourceBuilder, translate(stats)));
		}
	}

	@Reference(unbind = "-")
	protected void setStatsRequestBuilderFactory(
		StatsRequestBuilderFactory statsRequestBuilderFactory) {

		_statsRequestBuilderFactory = statsRequestBuilderFactory;
	}

	@Reference(unbind = "-")
	protected void setStatsTranslator(StatsTranslator statsTranslator) {
		_statsTranslator = statsTranslator;
	}

	protected void setStoredFields(
		SearchSourceBuilder searchSourceBuilder,
		SearchSearchRequest searchSearchRequest) {

		String[] selectedFieldNames =
			searchSearchRequest.getSelectedFieldNames();

		if (!ArrayUtil.isEmpty(selectedFieldNames)) {
			searchSourceBuilder.storedFields(
				ListUtil.fromArray(selectedFieldNames));
		}
		else {
			searchSourceBuilder.storedField(StringPool.STAR);
		}
	}

	protected void setTrackScores(
		SearchSourceBuilder searchSourceBuilder,
		SearchSearchRequest searchSearchRequest) {

		if (searchSearchRequest.getScoreEnabled() != null) {
			searchSourceBuilder.trackScores(
				searchSearchRequest.getScoreEnabled());
		}
	}

	protected void setVersion(
		SearchSourceBuilder searchSourceBuilder,
		SearchSearchRequest searchSearchRequest) {

		if (searchSearchRequest.getVersion() != null) {
			searchSourceBuilder.version(searchSearchRequest.getVersion());
		}
	}

	protected GroupByRequest translate(GroupBy groupBy) {
		return _groupByRequestFactory.getGroupByRequest(groupBy);
	}

	protected StatsRequest translate(Stats stats) {
		StatsRequestBuilder statsRequestBuilder =
			_statsRequestBuilderFactory.getStatsRequestBuilder(stats);

		return statsRequestBuilder.build();
	}

	private CommonSearchSourceBuilderAssembler
		_commonSearchSourceBuilderAssembler;
	private GroupByRequestFactory _groupByRequestFactory;
	private GroupByTranslator _groupByTranslator;
	private HighlighterTranslator _highlighterTranslator;
	private final HighlightTranslator _highlightTranslator =
		new HighlightTranslator();
	private QueryToQueryBuilderTranslator _queryToQueryBuilderTranslator;
	private SortFieldTranslator<SortBuilder> _sortFieldTranslator;
	private SortTranslator _sortTranslator;
	private StatsRequestBuilderFactory _statsRequestBuilderFactory;
	private StatsTranslator _statsTranslator;

}