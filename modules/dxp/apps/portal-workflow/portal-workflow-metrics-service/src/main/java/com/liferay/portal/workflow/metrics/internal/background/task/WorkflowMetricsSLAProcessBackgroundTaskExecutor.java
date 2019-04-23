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

package com.liferay.portal.workflow.metrics.internal.background.task;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.workflow.metrics.internal.search.index.SLAProcessResultWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.search.index.SLATaskResultWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.sla.processor.WorkflowMetricsSLAProcessResult;
import com.liferay.portal.workflow.metrics.internal.sla.processor.WorkflowMetricsSLAProcessor;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalService;
import com.liferay.portal.workflow.metrics.sla.processor.WorkfowMetricsSLAStatus;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = "background.task.executor.class.name=com.liferay.portal.workflow.metrics.internal.background.task.WorkflowMetricsSLAProcessBackgroundTaskExecutor",
	service = BackgroundTaskExecutor.class
)
public class WorkflowMetricsSLAProcessBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long workflowMetricsSLADefinitionId = MapUtil.getLong(
			taskContextMap, "workflowMetricsSLADefinitionId");

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			_workflowMetricsSLADefinitionLocalService.
				fetchWorkflowMetricsSLADefinition(
					workflowMetricsSLADefinitionId);

		long startNodeId = _getStartNodeId(
			workflowMetricsSLADefinition.getCompanyId(),
			workflowMetricsSLADefinition.getProcessId());

		Map<Long, LocalDateTime> createLocalDateTimes =
			_getCreateLocalDateTimes(
				workflowMetricsSLADefinition.getCompanyId(),
				workflowMetricsSLADefinition.getProcessId());

		createLocalDateTimes.forEach(
			(instanceId, createLocalDateTime) -> {
				Optional<WorkflowMetricsSLAProcessResult> optional =
					_workflowMetricsSLAProcessor.process(
						workflowMetricsSLADefinition.getCompanyId(),
						createLocalDateTime, instanceId, LocalDateTime.now(),
						startNodeId, workflowMetricsSLADefinition);

				optional.ifPresent(
					workflowMetricsSLAProcessResult -> {
						_slaProcessResultWorkflowMetricsIndexer.addDocument(
							_slaProcessResultWorkflowMetricsIndexer.
								createDocument(
									workflowMetricsSLAProcessResult));

						Map<Long, String> taskNames = _getTaskNames(
							workflowMetricsSLADefinition.getCompanyId(),
							instanceId,
							workflowMetricsSLADefinition.getProcessId());

						taskNames.forEach(
							(taskId, taskName) -> _addDocument(
								taskId, taskName,
								workflowMetricsSLAProcessResult));
					});
			});

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return null;
	}

	private void _addDocument(
		Long taskId, String taskName,
		WorkflowMetricsSLAProcessResult workflowMetricsSLAProcessResult) {

		_slaTaskResultWorkflowMetricsIndexer.addDocument(
			_slaTaskResultWorkflowMetricsIndexer.createDocument(
				taskId, taskName, workflowMetricsSLAProcessResult));
	}

	private BooleanQuery _createInstancesBooleanQuery(
		long companyId, long processId) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(_queries.term("instanceId", "0"));

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", companyId),
			_queries.term("completed", false), _queries.term("deleted", false),
			_queries.term("processId", processId));
	}

	private Map<Long, LocalDateTime> _getCreateLocalDateTimes(
		long companyId, long processId) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames("workflow-metrics-instances");
		searchSearchRequest.setQuery(
			_createInstancesBooleanQuery(companyId, processId));
		searchSearchRequest.setSize(10000);

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		return Stream.of(
			searchHits.getSearchHits()
		).flatMap(
			List::parallelStream
		).map(
			SearchHit::getDocument
		).collect(
			Collectors.toMap(
				document -> document.getLong("instanceId"),
				document -> LocalDateTime.parse(
					document.getString("createDate"),
					DateTimeFormatter.ofPattern(_INDEX_DATE_FORMAT_PATTERN)))
		);
	}

	private long _getStartNodeId(long companyId, long processId) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames("workflow-metrics-nodes");

		BooleanQuery booleanQuery = _queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addMustQueryClauses(
				_queries.term("companyId", companyId),
				_queries.term("completed", false),
				_queries.term("deleted", false),
				_queries.term("processId", processId)));

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		return Stream.of(
			searchHits.getSearchHits()
		).flatMap(
			List::parallelStream
		).map(
			SearchHit::getDocument
		).findFirst(
		).map(
			document -> document.getLong("nodeId")
		).orElse(
			0L
		);
	}

	private Map<Long, String> _getTaskNames(
		long companyId, long instanceId, long processId) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames("workflow-metrics-tokens");

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(_queries.term("instanceId", "0"));

		booleanQuery.addMustQueryClauses(
			_queries.term("companyId", companyId),
			_queries.term("completed", false), _queries.term("deleted", false),
			_queries.term("instanceId", instanceId),
			_queries.term("processId", processId));

		searchSearchRequest.setQuery(booleanQuery);

		searchSearchRequest.setSize(10000);

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		return Stream.of(
			searchHits.getSearchHits()
		).flatMap(
			List::parallelStream
		).map(
			SearchHit::getDocument
		).collect(
			Collectors.toMap(
				document -> document.getLong("taskId"),
				document -> document.getString("taskName"))
		);
	}

	private static final String _INDEX_DATE_FORMAT_PATTERN = PropsUtil.get(
		PropsKeys.INDEX_DATE_FORMAT_PATTERN);

	@Reference
	private Queries _queries;

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

	@Reference
	private SLAProcessResultWorkflowMetricsIndexer
		_slaProcessResultWorkflowMetricsIndexer;

	@Reference
	private SLATaskResultWorkflowMetricsIndexer
		_slaTaskResultWorkflowMetricsIndexer;

	@Reference
	private WorkflowMetricsSLADefinitionLocalService
		_workflowMetricsSLADefinitionLocalService;

	@Reference
	private WorkflowMetricsSLAProcessor _workflowMetricsSLAProcessor;

}