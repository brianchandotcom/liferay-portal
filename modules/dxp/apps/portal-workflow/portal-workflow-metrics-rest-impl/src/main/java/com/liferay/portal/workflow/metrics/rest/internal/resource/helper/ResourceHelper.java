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

package com.liferay.portal.workflow.metrics.rest.internal.resource.helper;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.FilterAggregationResult;
import com.liferay.portal.search.aggregation.metrics.ScriptedMetricAggregation;
import com.liferay.portal.search.aggregation.metrics.ScriptedMetricAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.BucketSortPipelineAggregation;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.workflow.metrics.sla.processor.WorkfowMetricsSLAStatus;

import java.io.IOException;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ResourceHelper.class)
public class ResourceHelper {

	public BucketSortPipelineAggregation createBucketSortPipelineAggregation(
		FieldSort fieldSort, Pagination pagination) {

		BucketSortPipelineAggregation bucketSortPipelineAggregation =
			_aggregations.bucketSort("sort");

		bucketSortPipelineAggregation.addSortFields(fieldSort);
		bucketSortPipelineAggregation.setFrom(pagination.getStartPosition());
		bucketSortPipelineAggregation.setSize(pagination.getPageSize() + 1);

		return bucketSortPipelineAggregation;
	}

	public BooleanQuery createMustNotBooleanQuery() {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		return booleanQuery.addMustNotQueryClauses(
			_queries.term("status", WorkfowMetricsSLAStatus.NEW));
	}

	public ScriptedMetricAggregation createOnTimeScriptedMetricAggregation() {
		ScriptedMetricAggregation scriptedMetricAggregation =
			_aggregations.scriptedMetric("instanceCount");

		scriptedMetricAggregation.setCombineScript(
			_workflowMetricsSlaCombineScript);
		scriptedMetricAggregation.setInitScript(_workflowMetricsSlaInitScript);
		scriptedMetricAggregation.setMapScript(_workflowMetricsSlaMapScript);
		scriptedMetricAggregation.setReduceScript(
			_workflowMetricsSlaOnTimeReduceScript);

		return scriptedMetricAggregation;
	}

	public ScriptedMetricAggregation createOverdueScriptedMetricAggregation() {
		ScriptedMetricAggregation scriptedMetricAggregation =
			_aggregations.scriptedMetric("instanceCount");

		scriptedMetricAggregation.setCombineScript(
			_workflowMetricsSlaCombineScript);
		scriptedMetricAggregation.setInitScript(_workflowMetricsSlaInitScript);
		scriptedMetricAggregation.setMapScript(_workflowMetricsSlaMapScript);
		scriptedMetricAggregation.setReduceScript(
			_workflowMetricsSlaOverdueReduceScript);

		return scriptedMetricAggregation;
	}

	public Script createScript(Class<?> clazz, String resourceName) {
		try {
			return _scripts.script(
				StringUtil.read(
					clazz.getResourceAsStream("dependencies/" + resourceName)));
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);

			throw new IllegalStateException(
				"Unable to read resource " + resourceName);
		}
	}

	public String getLatestProcessVersion(long companyId, long processId) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames("workflow-metrics-processes");

		BooleanQuery booleanQuery = _queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addMustQueryClauses(
				_queries.term("companyId", companyId),
				_queries.term("processId", processId)));

		searchSearchRequest.setSelectedFieldNames("version");

		return Stream.of(
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest)
		).map(
			SearchSearchResponse::getSearchHits
		).map(
			SearchHits::getSearchHits
		).flatMap(
			List::parallelStream
		).map(
			SearchHit::getDocument
		).findFirst(
		).map(
			document -> document.getString("version")
		).orElse(
			StringPool.BLANK
		);
	}

	public long getOnTimeInstanceCount(Bucket bucket) {
		FilterAggregationResult filterAggregationResult =
			(FilterAggregationResult)bucket.getChildAggregationResult("onTime");

		ScriptedMetricAggregationResult scriptedMetricAggregationResult =
			(ScriptedMetricAggregationResult)
				filterAggregationResult.getChildAggregationResult(
					"instanceCount");

		return GetterUtil.getLong(scriptedMetricAggregationResult.getValue());
	}

	public long getOverdueInstanceCount(Bucket bucket) {
		FilterAggregationResult filterAggregationResult =
			(FilterAggregationResult)bucket.getChildAggregationResult(
				"overdue");

		ScriptedMetricAggregationResult scriptedMetricAggregationResult =
			(ScriptedMetricAggregationResult)
				filterAggregationResult.getChildAggregationResult(
					"instanceCount");

		return GetterUtil.getLong(scriptedMetricAggregationResult.getValue());
	}

	@Activate
	protected void activate() {
		_workflowMetricsSlaCombineScript = createScript(
			getClass(), "workflow-metrics-sla-combine-script.painless");
		_workflowMetricsSlaInitScript = createScript(
			getClass(), "workflow-metrics-sla-init-script.painless");
		_workflowMetricsSlaMapScript = createScript(
			getClass(), "workflow-metrics-sla-map-script.painless");
		_workflowMetricsSlaOnTimeReduceScript = createScript(
			getClass(), "workflow-metrics-sla-on-time-reduce-script.painless");
		_workflowMetricsSlaOverdueReduceScript = createScript(
			getClass(), "workflow-metrics-sla-overdue-reduce-script.painless");
	}

	private static final Log _log = LogFactoryUtil.getLog(ResourceHelper.class);

	@Reference
	private Aggregations _aggregations;

	@Reference
	private Queries _queries;

	@Reference
	private Scripts _scripts;

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

	private Script _workflowMetricsSlaCombineScript;
	private Script _workflowMetricsSlaInitScript;
	private Script _workflowMetricsSlaMapScript;
	private Script _workflowMetricsSlaOnTimeReduceScript;
	private Script _workflowMetricsSlaOverdueReduceScript;

}