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

package com.liferay.portal.search.solr7.internal.groupby;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.GroupBy;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.highlight.HighlightUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.HashSet;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.GroupParams;

import org.osgi.service.component.annotations.Component;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
@Component(immediate = true, service = GroupByTranslator.class)
public class DefaultGroupByTranslator implements GroupByTranslator {

	@Override
	public void translate(
		SolrQuery solrQuery, SearchContext searchContext, int start, int end) {

		GroupBy groupBy = searchContext.getGroupBy();

		configureGroups(solrQuery, searchContext, start, end, groupBy);
	}

	protected void addHighlightedField(
		SolrQuery solrQuery, QueryConfig queryConfig, String fieldName) {

		solrQuery.addHighlightField(fieldName);

		String localizedFieldName = Field.getLocalizedName(
			queryConfig.getLocale(), fieldName);

		solrQuery.addHighlightField(localizedFieldName);
	}

	protected void addHighlights(SolrQuery solrQuery, QueryConfig queryConfig) {
		if (!queryConfig.isHighlightEnabled()) {
			return;
		}

		solrQuery.setHighlight(true);
		solrQuery.setHighlightFragsize(queryConfig.getHighlightFragmentSize());
		solrQuery.setHighlightRequireFieldMatch(
			queryConfig.isHighlightRequireFieldMatch());
		solrQuery.setHighlightSimplePost(HighlightUtil.HIGHLIGHT_TAG_CLOSE);
		solrQuery.setHighlightSimplePre(HighlightUtil.HIGHLIGHT_TAG_OPEN);
		solrQuery.setHighlightSnippets(queryConfig.getHighlightSnippetSize());

		for (String highlightFieldName : queryConfig.getHighlightFieldNames()) {
			addHighlightedField(solrQuery, queryConfig, highlightFieldName);
		}
	}

	protected void addSorts(SolrQuery solrQuery, Sort[] sorts) {
		if (ArrayUtil.isEmpty(sorts)) {
			return;
		}

		Set<String> sortFieldNames = new HashSet<>(sorts.length);

		for (Sort sort : sorts) {
			if (sort == null) {
				continue;
			}

			String sortFieldName = Field.getSortFieldName(sort, "_score");

			if (sortFieldNames.contains(sortFieldName)) {
				continue;
			}

			sortFieldNames.add(sortFieldName);

			SolrQuery.ORDER order = SolrQuery.ORDER.asc;

			if (sort.isReverse() || sortFieldName.equals("_score")) {
				order = SolrQuery.ORDER.desc;
			}

			solrQuery.addSort(new SolrQuery.SortClause(sortFieldName, order));
		}
	}

	protected void configureGroups(
		SolrQuery solrQuery, SearchContext searchContext, int start, int end,
		GroupBy groupBy) {

		solrQuery.set(GroupParams.GROUP, true);
		solrQuery.set(GroupParams.GROUP_FIELD, groupBy.getField());
		solrQuery.set(GroupParams.GROUP_FORMAT, "grouped");
		solrQuery.set(GroupParams.GROUP_TOTAL_COUNT, true);

		int groupByStart = groupBy.getStart();

		if (groupByStart == 0) {
			groupByStart = start;
		}

		solrQuery.set(GroupParams.GROUP_OFFSET, groupByStart);

		int groupBySize = groupBy.getSize();

		if (groupBySize == 0) {
			groupBySize = end - start + 1;
		}

		solrQuery.set(GroupParams.GROUP_LIMIT, groupBySize);

		addHighlights(solrQuery, searchContext.getQueryConfig());
		addSorts(solrQuery, searchContext.getSorts());
	}

}