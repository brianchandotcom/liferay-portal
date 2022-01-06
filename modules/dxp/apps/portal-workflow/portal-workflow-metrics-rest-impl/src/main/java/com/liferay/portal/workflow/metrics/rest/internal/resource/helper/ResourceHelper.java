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
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.FilterAggregationResult;
import com.liferay.portal.search.aggregation.metrics.ScriptedMetricAggregation;
import com.liferay.portal.search.aggregation.metrics.ScriptedMetricAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.BucketScriptPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.BucketScriptPipelineAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.BucketSortPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.GapPolicy;
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
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import java.io.IOException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ResourceHelper.class)
public class ResourceHelper {

	public ScriptedMetricAggregation createBreachedScriptedMetricAggregation() {
		ScriptedMetricAggregation scriptedMetricAggregation =
			_aggregations.scriptedMetric("breachedInstanceCount");

		scriptedMetricAggregation.setCombineScript(
			_workflowMetricsSlaCombineScript);
		scriptedMetricAggregation.setInitScript(_workflowMetricsSlaInitScript);
		scriptedMetricAggregation.setMapScript(_workflowMetricsSlaMapScript);
		scriptedMetricAggregation.setReduceScript(
			_workflowMetricsSlaBreachedReduceScript);

		return scriptedMetricAggregation;
	}

	public BucketScriptPipelineAggregation
		createBucketScriptPipelineAggregation() {

		BucketScriptPipelineAggregation bucketScriptPipelineAggregation =
			_aggregations.bucketScript(
				"breachedInstancePercentage",
				_workflowMetricsSlaBreachedInstancePercentageScript);

		bucketScriptPipelineAggregation.addBucketPath(
			"breachedInstanceCount", "breached>breachedInstanceCount.value");
		bucketScriptPipelineAggregation.addBucketPath(
			"instanceCount", "countFilter>instanceCount.value");

		return bucketScriptPipelineAggregation;
	}

	public BucketSortPipelineAggregation createBucketSortPipelineAggregation(
		FieldSort fieldSort, Pagination pagination) {

		BucketSortPipelineAggregation bucketSortPipelineAggregation =
			_aggregations.bucketSort("sort");

		FieldSort keyFieldSort = _sorts.field("_key");

		keyFieldSort.setSortOrder(SortOrder.ASC);

		bucketSortPipelineAggregation.addSortFields(fieldSort, keyFieldSort);

		bucketSortPipelineAggregation.setFrom(pagination.getStartPosition());
		bucketSortPipelineAggregation.setGapPolicy(GapPolicy.INSTANT_ZEROS);
		bucketSortPipelineAggregation.setSize(pagination.getPageSize());

		return bucketSortPipelineAggregation;
	}

	public BooleanQuery createInstanceCompletedBooleanQuery(
		boolean instanceCompleted) {

		BooleanQuery booleanQuery = createMustNotBooleanQuery();

		return booleanQuery.addMustQueryClauses(
			_queries.term("instanceCompleted", instanceCompleted));
	}

	public BooleanQuery createMustNotBooleanQuery() {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		return booleanQuery.addMustNotQueryClauses(
			_queries.term("status", WorkflowMetricsSLAStatus.NEW.name()));
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

	public ScriptedMetricAggregation
		createOnTimeTaskByAssigneeScriptedMetricAggregation() {

		ScriptedMetricAggregation scriptedMetricAggregation =
			_aggregations.scriptedMetric("taskCount");

		scriptedMetricAggregation.setCombineScript(
			_workflowMetricsSlaTaskAssigneeCombineScript);
		scriptedMetricAggregation.setInitScript(
			_workflowMetricsSlaTaskAssigneeInitScript);
		scriptedMetricAggregation.setMapScript(
			_workflowMetricsSlaTaskAssigneeMapScript);
		scriptedMetricAggregation.setReduceScript(
			_workflowMetricsSlaTaskAssigneeOnTimeReduceScript);

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

	public ScriptedMetricAggregation
		createOverdueTaskByAssigneeScriptedMetricAggregation() {

		ScriptedMetricAggregation scriptedMetricAggregation =
			_aggregations.scriptedMetric("taskCount");

		scriptedMetricAggregation.setCombineScript(
			_workflowMetricsSlaTaskAssigneeCombineScript);
		scriptedMetricAggregation.setInitScript(
			_workflowMetricsSlaTaskAssigneeInitScript);
		scriptedMetricAggregation.setMapScript(
			_workflowMetricsSlaTaskAssigneeMapScript);
		scriptedMetricAggregation.setReduceScript(
			_workflowMetricsSlaTaskAssigneeOverdueReduceScript);

		return scriptedMetricAggregation;
	}

	public Script createScript(Class<?> clazz, String resourceName)
		throws IOException {

		return _scripts.script(
			StringUtil.read(
				clazz.getResourceAsStream("dependencies/" + resourceName)));
	}

	public BooleanQuery createTasksBooleanQuery(
		long companyId, boolean instanceCompleted) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addFilterQueryClauses(
			_queries.term(
				"_index",
				_taskWorkflowMetricsIndexNameBuilder.getIndexName(companyId)));

		booleanQuery.addMustQueryClauses(
			_queries.term("instanceCompleted", instanceCompleted));

		return booleanQuery.addMustNotQueryClauses(
			_queries.term("instanceId", 0));
	}

	public ScriptedMetricAggregation
		creatInstanceCountScriptedMetricAggregation(
			List<Long> assigneeIds, Boolean completed, Date dateEnd,
			Date dateStart, List<String> taskNames) {

		ScriptedMetricAggregation scriptedMetricAggregation =
			_aggregations.scriptedMetric("instanceCount");

		scriptedMetricAggregation.setCombineScript(
			_workflowMetricsInstanceCountCombineScript);
		scriptedMetricAggregation.setInitScript(
			_workflowMetricsInstanceCountInitScript);
		scriptedMetricAggregation.setMapScript(
			_workflowMetricsInstanceCountMapScript);
		scriptedMetricAggregation.setParameters(
			HashMapBuilder.<String, Object>put(
				"assigneeIds",
				() -> Optional.ofNullable(
					assigneeIds
				).filter(
					ListUtil::isNotEmpty
				).map(
					List::parallelStream
				).map(
					stream -> stream.map(
						String::valueOf
					).collect(
						Collectors.toList()
					)
				).orElse(
					null
				)
			).put(
				"assigneeType", Role.class.getName()
			).put(
				"completed", () -> completed
			).put(
				"endDate",
				() -> Optional.ofNullable(
					dateEnd
				).map(
					Date::getTime
				).orElse(
					null
				)
			).put(
				"startDate",
				() -> Optional.ofNullable(
					dateStart
				).map(
					Date::getTime
				).orElse(
					null
				)
			).put(
				"taskNames",
				() -> Optional.ofNullable(
					taskNames
				).filter(
					ListUtil::isNotEmpty
				).orElse(
					null
				)
			).build());
		scriptedMetricAggregation.setReduceScript(
			_workflowMetricsInstanceCountReduceScript);

		return scriptedMetricAggregation;
	}

	public ScriptedMetricAggregation creatTaskCountScriptedMetricAggregation(
		List<Long> assigneeIds, List<String> slaStatuses,
		List<String> taskNames) {

		ScriptedMetricAggregation scriptedMetricAggregation =
			_aggregations.scriptedMetric("taskCount");

		scriptedMetricAggregation.setCombineScript(
			_workflowMetricsTaskCountCombineScript);
		scriptedMetricAggregation.setInitScript(
			_workflowMetricsTaskCountInitScript);
		scriptedMetricAggregation.setMapScript(
			_workflowMetricsTaskCountMapScript);
		scriptedMetricAggregation.setParameters(
			HashMapBuilder.<String, Object>put(
				"assigneeIds",
				() -> Optional.ofNullable(
					assigneeIds
				).filter(
					ListUtil::isNotEmpty
				).map(
					List::parallelStream
				).map(
					stream -> stream.map(
						String::valueOf
					).collect(
						Collectors.toList()
					)
				).orElse(
					null
				)
			).put(
				"assigneeType", Role.class.getName()
			).put(
				"slaStatuses",
				() -> Optional.ofNullable(
					slaStatuses
				).filter(
					ListUtil::isNotEmpty
				).orElse(
					null
				)
			).put(
				"taskNames",
				() -> Optional.ofNullable(
					taskNames
				).filter(
					ListUtil::isNotEmpty
				).orElse(
					null
				)
			).build());
		scriptedMetricAggregation.setReduceScript(
			_workflowMetricsTaskCountReduceScript);

		return scriptedMetricAggregation;
	}

	public long getBreachedInstanceCount(Bucket bucket) {
		FilterAggregationResult filterAggregationResult =
			(FilterAggregationResult)bucket.getChildAggregationResult(
				"breached");

		ScriptedMetricAggregationResult scriptedMetricAggregationResult =
			(ScriptedMetricAggregationResult)
				filterAggregationResult.getChildAggregationResult(
					"breachedInstanceCount");

		return GetterUtil.getLong(scriptedMetricAggregationResult.getValue());
	}

	public double getBreachedInstancePercentage(Bucket bucket) {
		BucketScriptPipelineAggregationResult
			bucketScriptPipelineAggregationResult =
				(BucketScriptPipelineAggregationResult)
					bucket.getChildAggregationResult(
						"breachedInstancePercentage");

		return bucketScriptPipelineAggregationResult.getValue();
	}

	public String getDate(Date date) {
		try {
			return DateUtil.getDate(
				date, "yyyyMMddHHmmss", LocaleUtil.getDefault());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}

			return null;
		}
	}

	public String getLatestProcessVersion(long companyId, long processId) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			_processWorkflowMetricsIndexNameBuilder.getIndexName(companyId));

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
		).orElseGet(
			() -> StringPool.BLANK
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

	public long getOnTimeTaskCount(Bucket bucket) {
		FilterAggregationResult filterAggregationResult =
			(FilterAggregationResult)bucket.getChildAggregationResult("onTime");

		if (filterAggregationResult == null) {
			return 0L;
		}

		ScriptedMetricAggregationResult scriptedMetricAggregationResult =
			(ScriptedMetricAggregationResult)
				filterAggregationResult.getChildAggregationResult("taskCount");

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

	public long getOverdueTaskCount(Bucket bucket) {
		FilterAggregationResult filterAggregationResult =
			(FilterAggregationResult)bucket.getChildAggregationResult(
				"overdue");

		if (filterAggregationResult == null) {
			return 0L;
		}

		ScriptedMetricAggregationResult scriptedMetricAggregationResult =
			(ScriptedMetricAggregationResult)
				filterAggregationResult.getChildAggregationResult("taskCount");

		return GetterUtil.getLong(scriptedMetricAggregationResult.getValue());
	}

	@Activate
	protected void activate() throws IOException {
		_workflowMetricsInstanceCountCombineScript = createScript(
			getClass(),
			"workflow-metrics-instance-count-combine-script.painless");
		_workflowMetricsInstanceCountInitScript = createScript(
			getClass(), "workflow-metrics-instance-count-init-script.painless");
		_workflowMetricsInstanceCountMapScript = createScript(
			getClass(), "workflow-metrics-instance-count-map-script.painless");
		_workflowMetricsInstanceCountReduceScript = createScript(
			getClass(),
			"workflow-metrics-instance-count-reduce-script.painless");
		_workflowMetricsSlaBreachedInstancePercentageScript = createScript(
			getClass(),
			"workflow-metrics-sla-breached-instance-percentage-script." +
				"painless");
		_workflowMetricsSlaBreachedReduceScript = createScript(
			getClass(), "workflow-metrics-sla-breached-reduce-script.painless");
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
		_workflowMetricsSlaTaskAssigneeCombineScript = createScript(
			getClass(),
			"workflow-metrics-sla-assignee-combine-script.painless");
		_workflowMetricsSlaTaskAssigneeInitScript = createScript(
			getClass(), "workflow-metrics-sla-assignee-init-script.painless");
		_workflowMetricsSlaTaskAssigneeMapScript = createScript(
			getClass(), "workflow-metrics-sla-assignee-map-script.painless");
		_workflowMetricsSlaTaskAssigneeOnTimeReduceScript = createScript(
			getClass(),
			"workflow-metrics-sla-assignee-on-time-reduce-script.painless");
		_workflowMetricsSlaTaskAssigneeOverdueReduceScript = createScript(
			getClass(),
			"workflow-metrics-sla-assignee-overdue-reduce-script.painless");
		_workflowMetricsTaskCountCombineScript = createScript(
			getClass(), "workflow-metrics-task-count-combine-script.painless");
		_workflowMetricsTaskCountInitScript = createScript(
			getClass(), "workflow-metrics-task-count-init-script.painless");
		_workflowMetricsTaskCountMapScript = createScript(
			getClass(), "workflow-metrics-task-count-map-script.painless");
		_workflowMetricsTaskCountReduceScript = createScript(
			getClass(), "workflow-metrics-task-count-reduce-script.painless");
	}

	private static final Log _log = LogFactoryUtil.getLog(ResourceHelper.class);

	@Reference
	private Aggregations _aggregations;

	@Reference
	private Portal _portal;

	@Reference(target = "(workflow.metrics.index.entity.name=process)")
	private WorkflowMetricsIndexNameBuilder
		_processWorkflowMetricsIndexNameBuilder;

	@Reference
	private Queries _queries;

	@Reference
	private Scripts _scripts;

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

	@Reference
	private Sorts _sorts;

	@Reference(target = "(workflow.metrics.index.entity.name=task)")
	private WorkflowMetricsIndexNameBuilder
		_taskWorkflowMetricsIndexNameBuilder;

	private Script _workflowMetricsInstanceCountCombineScript;
	private Script _workflowMetricsInstanceCountInitScript;
	private Script _workflowMetricsInstanceCountMapScript;
	private Script _workflowMetricsInstanceCountReduceScript;
	private Script _workflowMetricsSlaBreachedInstancePercentageScript;
	private Script _workflowMetricsSlaBreachedReduceScript;
	private Script _workflowMetricsSlaCombineScript;
	private Script _workflowMetricsSlaInitScript;
	private Script _workflowMetricsSlaMapScript;
	private Script _workflowMetricsSlaOnTimeReduceScript;
	private Script _workflowMetricsSlaOverdueReduceScript;
	private Script _workflowMetricsSlaTaskAssigneeCombineScript;
	private Script _workflowMetricsSlaTaskAssigneeInitScript;
	private Script _workflowMetricsSlaTaskAssigneeMapScript;
	private Script _workflowMetricsSlaTaskAssigneeOnTimeReduceScript;
	private Script _workflowMetricsSlaTaskAssigneeOverdueReduceScript;
	private Script _workflowMetricsTaskCountCombineScript;
	private Script _workflowMetricsTaskCountInitScript;
	private Script _workflowMetricsTaskCountMapScript;
	private Script _workflowMetricsTaskCountReduceScript;

}