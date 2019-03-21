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
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.workflow.metrics.internal.search.index.SLAProcessResultWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.sla.processor.WorkflowMetricsSLAProcessResult;
import com.liferay.portal.workflow.metrics.internal.sla.processor.WorkflowMetricsSLAProcessor;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalService;

import java.io.Serializable;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Map;
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

		Stream<Long> stream = _getInstanceIdsStream(
			workflowMetricsSLADefinition.getCompanyId(),
			workflowMetricsSLADefinition.getProcessId());

		stream.forEach(
			instanceId -> {
				WorkflowMetricsSLAProcessResult
					workflowMetricsSLAProcessResult =
						_workflowMetricsSLAProcessor.process(
							workflowMetricsSLADefinition.getCompanyId(),
							instanceId, LocalDateTime.now(),
							workflowMetricsSLADefinition);

				_slaProcessResultWorkflowMetricsIndexer.addDocument(
					workflowMetricsSLAProcessResult);
			});

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return null;
	}

	private Stream<Long> _getInstanceIdsStream(long companyId, long processId) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames("workflow-metrics-instances");
		searchSearchRequest.setQuery(
			new BooleanQueryImpl() {
				{
					setPreBooleanFilter(
						new BooleanFilter() {
							{
								addRequiredTerm("companyId", companyId);
								addRequiredTerm("completed", false);
								addRequiredTerm("deleted", false);
								addTerm(
									"instanceId", "0",
									BooleanClauseOccur.MUST_NOT);
								addRequiredTerm("processId", processId);
							}
						});
				}
			});

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		return Stream.of(
			searchHits.getSearchHits()
		).flatMap(
			List::parallelStream
		).map(
			SearchHit::getDocument
		).map(
			document -> GetterUtil.getLong(document.getFieldValue("instanceId"))
		);
	}

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

	@Reference
	private SLAProcessResultWorkflowMetricsIndexer
		_slaProcessResultWorkflowMetricsIndexer;

	@Reference
	private WorkflowMetricsSLADefinitionLocalService
		_workflowMetricsSLADefinitionLocalService;

	@Reference
	private WorkflowMetricsSLAProcessor _workflowMetricsSLAProcessor;

}