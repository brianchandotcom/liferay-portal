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
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.Stats;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch6.internal.groupby.GroupByTranslator;
import com.liferay.portal.search.elasticsearch6.internal.highlight.HighlighterTranslator;
import com.liferay.portal.search.elasticsearch6.internal.sort.SortTranslator;
import com.liferay.portal.search.elasticsearch6.internal.stats.StatsTranslator;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;

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

		commonSearchRequestBuilderAssembler.assemble(
			searchRequestBuilder, searchSearchRequest);

		Map<String, Stats> stats = searchSearchRequest.getStats();

		if (!MapUtil.isEmpty(stats)) {
			stats.forEach(
				(statsKey, stat) -> statsTranslator.translate(
					searchRequestBuilder, stat));
		}

		addGroupBy(searchRequestBuilder, searchSearchRequest);

		QueryConfig queryConfig = searchSearchRequest.getQueryConfig();

		highlighterTranslator.translate(
			searchRequestBuilder, queryConfig,
			searchSearchRequest.isLuceneSyntax());

		addPagination(
			searchRequestBuilder, searchSearchRequest.getStart(),
			searchSearchRequest.getSize());
		addPreference(searchRequestBuilder, searchSearchRequest);
		addSelectedFields(searchRequestBuilder, queryConfig);

		sortTranslator.translate(
			searchRequestBuilder, searchSearchRequest.getSorts());

		searchRequestBuilder.setTrackScores(queryConfig.isScoreEnabled());
	}

	protected void addGroupBy(
		SearchRequestBuilder searchRequestBuilder,
		SearchSearchRequest searchSearchRequest) {

		GroupBy groupBy = searchSearchRequest.getGroupBy();

		if (groupBy == null) {
			return;
		}

		groupByTranslator.translate(searchRequestBuilder, searchSearchRequest);
	}

	protected void addPagination(
		SearchRequestBuilder searchRequestBuilder, int start, int end) {

		searchRequestBuilder.setFrom(start);
		searchRequestBuilder.setSize(end - start);
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
		SearchRequestBuilder searchRequestBuilder, QueryConfig queryConfig) {

		String[] selectedFieldNames = queryConfig.getSelectedFieldNames();

		if (ArrayUtil.isEmpty(selectedFieldNames)) {
			searchRequestBuilder.addStoredField(StringPool.STAR);
		}
		else {
			searchRequestBuilder.storedFields(selectedFieldNames);
		}
	}

	@Reference
	protected CommonSearchRequestBuilderAssembler
		commonSearchRequestBuilderAssembler;

	@Reference
	protected GroupByTranslator groupByTranslator;

	@Reference
	protected HighlighterTranslator highlighterTranslator;

	@Reference
	protected SortTranslator sortTranslator;

	@Reference
	protected StatsTranslator statsTranslator;

}