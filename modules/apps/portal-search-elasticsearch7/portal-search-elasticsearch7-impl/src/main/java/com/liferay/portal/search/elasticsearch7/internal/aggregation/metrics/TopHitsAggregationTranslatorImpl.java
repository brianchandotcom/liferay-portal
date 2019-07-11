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

package com.liferay.portal.search.elasticsearch7.internal.aggregation.metrics;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.aggregation.AggregationTranslator;
import com.liferay.portal.search.aggregation.metrics.TopHitsAggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.highlight.HighlightTranslator;
import com.liferay.portal.search.elasticsearch7.internal.script.ScriptTranslator;
import com.liferay.portal.search.query.QueryTranslator;
import com.liferay.portal.search.script.ScriptField;
import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortFieldTranslator;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.TopHitsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = TopHitsAggregationTranslator.class)
public class TopHitsAggregationTranslatorImpl
	implements TopHitsAggregationTranslator {

	@Override
	public TopHitsAggregationBuilder translate(
		TopHitsAggregation topHitsAggregation,
		AggregationTranslator<AggregationBuilder> aggregationTranslator,
		PipelineAggregationTranslator<PipelineAggregationBuilder>
			pipelineAggregationTranslator) {

		TopHitsAggregationBuilder topHitsAggregationBuilder =
			AggregationBuilders.topHits(topHitsAggregation.getName());

		if (topHitsAggregation.getExplain() != null) {
			topHitsAggregationBuilder.explain(topHitsAggregation.getExplain());
		}

		if (ListUtil.isNotEmpty(topHitsAggregation.getSelectedFields())) {
			List<String> selectedFields =
				topHitsAggregation.getSelectedFields();

			selectedFields.forEach(topHitsAggregationBuilder::docValueField);
		}

		if (topHitsAggregation.getFetchSource() != null) {
			topHitsAggregationBuilder.fetchSource(
				topHitsAggregation.getFetchSource());

			if (topHitsAggregation.getFetchSource() &&
				((topHitsAggregation.getFetchSourceInclude() != null) ||
				 (topHitsAggregation.getFetchSourceExclude() != null))) {

				topHitsAggregationBuilder.fetchSource(
					topHitsAggregation.getFetchSourceInclude(),
					topHitsAggregation.getFetchSourceExclude());
			}
		}

		if (topHitsAggregation.getFrom() != null) {
			topHitsAggregationBuilder.from(topHitsAggregation.getFrom());
		}

		if (topHitsAggregation.getHighlight() != null) {
			HighlightBuilder highlightBuilder = _highlightTranslator.translate(
				topHitsAggregation.getHighlight(), _queryTranslator);

			topHitsAggregationBuilder.highlighter(highlightBuilder);
		}

		if (topHitsAggregation.getScriptFields() != null) {
			List<ScriptField> scriptFields =
				topHitsAggregation.getScriptFields();

			List<SearchSourceBuilder.ScriptField>
				searchSourceBuilderScriptFields = new ArrayList<>(
					scriptFields.size());

			scriptFields.forEach(
				scriptField -> {
					Script script = _scriptTranslator.translate(
						scriptField.getScript());

					SearchSourceBuilder.ScriptField
						searchSourceBuilderScriptField =
							new SearchSourceBuilder.ScriptField(
								scriptField.getField(), script,
								scriptField.isIgnoreFailure());

					searchSourceBuilderScriptFields.add(
						searchSourceBuilderScriptField);
				});

			topHitsAggregationBuilder.scriptFields(
				searchSourceBuilderScriptFields);
		}

		if (topHitsAggregation.getSize() != null) {
			topHitsAggregationBuilder.size(topHitsAggregation.getSize());
		}

		if (ListUtil.isNotEmpty(topHitsAggregation.getSortFields())) {
			List<Sort> sorts = topHitsAggregation.getSortFields();

			List<SortBuilder<?>> sortBuilders = new ArrayList<>(sorts.size());

			sorts.forEach(
				sort -> sortBuilders.add(_sortFieldTranslator.translate(sort)));

			topHitsAggregationBuilder.sorts(sortBuilders);
		}

		topHitsAggregationBuilder.storedFields();

		if (topHitsAggregation.getTrackScores() != null) {
			topHitsAggregationBuilder.trackScores(
				topHitsAggregation.getTrackScores());
		}

		if (topHitsAggregation.getVersion() != null) {
			topHitsAggregationBuilder.version(topHitsAggregation.getVersion());
		}

		return topHitsAggregationBuilder;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setQueryTranslator(
		QueryTranslator<QueryBuilder> queryTranslator) {

		_queryTranslator = queryTranslator;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setSortFieldTranslator(
		SortFieldTranslator<SortBuilder> sortFieldTranslator) {

		_sortFieldTranslator = sortFieldTranslator;
	}

	private final HighlightTranslator _highlightTranslator =
		new HighlightTranslator();
	private QueryTranslator<QueryBuilder> _queryTranslator;
	private final ScriptTranslator _scriptTranslator = new ScriptTranslator();
	private SortFieldTranslator<SortBuilder> _sortFieldTranslator;

}