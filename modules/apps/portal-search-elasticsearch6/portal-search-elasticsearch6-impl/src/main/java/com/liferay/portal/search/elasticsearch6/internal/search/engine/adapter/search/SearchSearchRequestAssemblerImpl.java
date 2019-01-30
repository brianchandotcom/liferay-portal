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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.search;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.Stats;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch6.internal.groupby.GroupByTranslator;
import com.liferay.portal.search.elasticsearch6.internal.highlight.HighlighterTranslator;
import com.liferay.portal.search.elasticsearch6.internal.sort.SortTranslator;
import com.liferay.portal.search.elasticsearch6.internal.stats.StatsTranslator;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.legacy.stats.StatsRequestBuilderFactory;
import com.liferay.portal.search.stats.StatsRequest;
import com.liferay.portal.search.stats.StatsRequestBuilder;

import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = SearchSearchRequestAssembler.class)
public class SearchSearchRequestAssemblerImpl
	implements SearchSearchRequestAssembler {

	@Override
	public void assemble(
		SearchRequestBuilder searchRequestBuilder,
		SearchSearchRequest searchSearchRequest) {

		_commonSearchRequestBuilderAssembler.assemble(
			searchRequestBuilder, searchSearchRequest);

		addGroupBy(searchRequestBuilder, searchSearchRequest);

		if (searchSearchRequest.isHighlightEnabled()) {
			_highlighterTranslator.translate(
				searchRequestBuilder,
				searchSearchRequest.getHighlightFieldNames(),
				searchSearchRequest.isHighlightRequireFieldMatch(),
				searchSearchRequest.getHighlightFragmentSize(),
				searchSearchRequest.getHighlightSnippetSize(),
				searchSearchRequest.isLuceneSyntax());
		}

		addPagination(
			searchRequestBuilder, searchSearchRequest.getStart(),
			searchSearchRequest.getSize());
		addPreference(searchRequestBuilder, searchSearchRequest);
		addSelectedFields(
			searchRequestBuilder, searchSearchRequest.getSelectedFieldNames());
		addStats(searchRequestBuilder, searchSearchRequest.getStats());

		_sortTranslator.translate(
			searchRequestBuilder, searchSearchRequest.getSorts());

		searchRequestBuilder.setTrackScores(
			searchSearchRequest.isScoreEnabled());
	}

	protected void addGroupBy(
		SearchRequestBuilder searchRequestBuilder,
		SearchSearchRequest searchSearchRequest) {

		GroupBy groupBy = searchSearchRequest.getGroupBy();

		if (groupBy == null) {
			return;
		}

		_groupByTranslator.translate(
			searchRequestBuilder, groupBy, searchSearchRequest.getSorts(),
			searchSearchRequest.getLocale(),
			searchSearchRequest.getSelectedFieldNames(),
			searchSearchRequest.getHighlightFieldNames(),
			searchSearchRequest.isHighlightEnabled(),
			searchSearchRequest.isHighlightRequireFieldMatch(),
			searchSearchRequest.getHighlightFragmentSize(),
			searchSearchRequest.getHighlightSnippetSize(),
			searchSearchRequest.getStart(), searchSearchRequest.getSize());
	}

	protected void addPagination(
		SearchRequestBuilder searchRequestBuilder, int start, int size) {

		searchRequestBuilder.setFrom(start);
		searchRequestBuilder.setSize(size);
	}

	protected void addPreference(
		SearchRequestBuilder searchRequestBuilder,
		SearchSearchRequest searchSearchRequest) {

		String preference = searchSearchRequest.getPreference();

		if (!Validator.isBlank(preference)) {
			searchRequestBuilder.setPreference(preference);
		}
	}

	protected void addSelectedFields(
		SearchRequestBuilder searchRequestBuilder,
		String[] selectedFieldNames) {

		if (ArrayUtil.isEmpty(selectedFieldNames)) {
			searchRequestBuilder.addStoredField(StringPool.STAR);
		}
		else {
			searchRequestBuilder.storedFields(selectedFieldNames);
		}
	}

	protected void addStats(
		SearchRequestBuilder searchRequestBuilder,
		Map<String, Stats> statsMap) {

		if (!MapUtil.isEmpty(statsMap)) {
			statsMap.forEach(
				(key, stats) -> _statsTranslator.populateRequest(
					searchRequestBuilder, translate(stats)));
		}
	}

	@Reference(unbind = "-")
	protected void setCommonSearchRequestBuilderAssembler(
		CommonSearchRequestBuilderAssembler
			commonSearchRequestBuilderAssembler) {

		_commonSearchRequestBuilderAssembler =
			commonSearchRequestBuilderAssembler;
	}

	@Reference(unbind = "-")
	protected void setGroupByTranslator(GroupByTranslator groupByTranslator) {
		_groupByTranslator = groupByTranslator;
	}

	@Reference(unbind = "-")
	protected void setHighlighterTranslator(
		HighlighterTranslator highlighterTranslator) {

		_highlighterTranslator = highlighterTranslator;
	}

	@Reference(unbind = "-")
	protected void setSortTranslator(SortTranslator sortTranslator) {
		_sortTranslator = sortTranslator;
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

	protected StatsRequest translate(Stats stats) {
		StatsRequestBuilder statsRequestBuilder =
			_statsRequestBuilderFactory.getStatsRequestBuilder(stats);

		return statsRequestBuilder.build();
	}

	private CommonSearchRequestBuilderAssembler
		_commonSearchRequestBuilderAssembler;
	private GroupByTranslator _groupByTranslator;
	private HighlighterTranslator _highlighterTranslator;
	private SortTranslator _sortTranslator;
	private StatsRequestBuilderFactory _statsRequestBuilderFactory;
	private StatsTranslator _statsTranslator;

}