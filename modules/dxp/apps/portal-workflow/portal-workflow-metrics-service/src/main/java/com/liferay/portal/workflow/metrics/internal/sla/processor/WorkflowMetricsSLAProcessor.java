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

package com.liferay.portal.workflow.metrics.internal.sla.processor;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.ExistsFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.filter.DateRangeFilterBuilder;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.workflow.metrics.internal.sla.WorkfowMetricsSLAStatus;
import com.liferay.portal.workflow.metrics.internal.util.LocalDateTimeUtil;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = WorkflowMetricsSLAProcessor.class)
public class WorkflowMetricsSLAProcessor {

	public Optional<WorkflowMetricsSLAProcessResult> process(
		long companyId, LocalDateTime createLocalDateTime, long instanceId,
		LocalDateTime nowLocalDateTime, long startNodeId,
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		WorkflowMetricsSLAProcessResult lastWorkflowMetricsSLAProcessResult =
			fetchLastWorkflowMetricsSLAProcessResult(
				workflowMetricsSLADefinition, instanceId);

		long elapsedTime = 0;
		LocalDateTime lastCheckLocalDateTime = null;
		WorkfowMetricsSLAStatus workfowMetricsSLAStatus =
			WorkfowMetricsSLAStatus.NEW;

		if (lastWorkflowMetricsSLAProcessResult != null) {
			elapsedTime = lastWorkflowMetricsSLAProcessResult.getElapsedTime();

			lastCheckLocalDateTime =
				lastWorkflowMetricsSLAProcessResult.getLastCheckLocalDateTime();

			workfowMetricsSLAStatus =
				lastWorkflowMetricsSLAProcessResult.
					getWorkfowMetricsSLAStatus();

			if (Objects.equals(
					workfowMetricsSLAStatus,
					WorkfowMetricsSLAStatus.COMPLETED) ||
				lastCheckLocalDateTime.isAfter(nowLocalDateTime)) {

				return Optional.empty();
			}
		}

		List<Document> documents = getDocuments(
			companyId, instanceId, lastCheckLocalDateTime);

		List<TaskInterval> taskIntervals = _toTaskIntervals(
			documents, lastCheckLocalDateTime, nowLocalDateTime);

		WorkflowMetricsSLAStopwatch workflowMetricsSLAStopwatch =
			_createWorkflowMetricsSLAStopwatch(
				documents, createLocalDateTime, lastCheckLocalDateTime,
				startNodeId, workflowMetricsSLADefinition,
				workfowMetricsSLAStatus);

		if (!workflowMetricsSLAStopwatch.isEmpty()) {
			for (TaskInterval taskInterval : taskIntervals) {
				elapsedTime += _computeElapsedTime(
					taskInterval, workflowMetricsSLAStopwatch);
			}

			workfowMetricsSLAStatus =
				workflowMetricsSLAStopwatch.getWorkfowMetricsSLAStatus();
		}

		WorkflowMetricsSLAProcessResult workflowMetricsSLAProcessResult =
			new WorkflowMetricsSLAProcessResult();

		workflowMetricsSLAProcessResult.setCompanyId(companyId);
		workflowMetricsSLAProcessResult.setElapsedTime(elapsedTime);
		workflowMetricsSLAProcessResult.setInstanceId(instanceId);
		workflowMetricsSLAProcessResult.setLastCheckLocalDateTime(
			nowLocalDateTime);
		workflowMetricsSLAProcessResult.setOnTime(
			elapsedTime <= workflowMetricsSLADefinition.getDuration());

		long remainingTime =
			workflowMetricsSLADefinition.getDuration() - elapsedTime;

		workflowMetricsSLAProcessResult.setOverdueLocalDateTime(
			nowLocalDateTime.plus(remainingTime, ChronoUnit.MILLIS));

		workflowMetricsSLAProcessResult.setProcessId(
			workflowMetricsSLADefinition.getProcessId());
		workflowMetricsSLAProcessResult.setRemainingTime(remainingTime);
		workflowMetricsSLAProcessResult.setSLADefinitionId(
			workflowMetricsSLADefinition.getWorkflowMetricsSLADefinitionId());
		workflowMetricsSLAProcessResult.setWorkfowMetricsSLAStatus(
			workfowMetricsSLAStatus);

		return Optional.of(workflowMetricsSLAProcessResult);
	}

	protected WorkflowMetricsSLAProcessResult
		fetchLastWorkflowMetricsSLAProcessResult(
			WorkflowMetricsSLADefinition workflowMetricsSLADefinition,
			long instanceId) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			"workflow-metrics-sla-process-result");
		searchSearchRequest.setQuery(
			new BooleanQueryImpl() {
				{
					setPreBooleanFilter(
						new BooleanFilter() {
							{
								addRequiredTerm(
									"companyId",
									workflowMetricsSLADefinition.
										getCompanyId());

								addRequiredTerm("instanceId", instanceId);
							}
						});
				}
			});

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		List<SearchHit> searchHitList = searchHits.getSearchHits();

		if (ListUtil.isEmpty(searchHitList)) {
			return null;
		}

		SearchHit searchHit = searchHitList.get(0);

		Document document = searchHit.getDocument();

		WorkflowMetricsSLAProcessResult workflowMetricsSLAProcessResult =
			new WorkflowMetricsSLAProcessResult();

		workflowMetricsSLAProcessResult.setCompanyId(
			workflowMetricsSLADefinition.getCompanyId());

		workflowMetricsSLAProcessResult.setElapsedTime(
			document.getLong("elapsedTime"));

		workflowMetricsSLAProcessResult.setInstanceId(instanceId);

		workflowMetricsSLAProcessResult.setLastCheckLocalDateTime(
			LocalDateTimeUtil.toLocalDateTime(document, "lastCheckDate"));
		workflowMetricsSLAProcessResult.setOnTime(
			GetterUtil.getBoolean(document.getValue("onTime")));
		workflowMetricsSLAProcessResult.setOverdueLocalDateTime(
			LocalDateTimeUtil.toLocalDateTime(document, "overdueDate"));

		workflowMetricsSLAProcessResult.setProcessId(
			workflowMetricsSLADefinition.getProcessId());

		workflowMetricsSLAProcessResult.setRemainingTime(
			document.getLong("remainingTime"));

		workflowMetricsSLAProcessResult.setSLADefinitionId(
			workflowMetricsSLADefinition.getWorkflowMetricsSLADefinitionId());

		workflowMetricsSLAProcessResult.setWorkfowMetricsSLAStatus(
			WorkfowMetricsSLAStatus.valueOf(document.getString("status")));

		return workflowMetricsSLAProcessResult;
	}

	protected List<Document> getDocuments(
		long companyId, long instanceId, LocalDateTime lastCheckLocalDateTime) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.addSorts(
			_sorts.field(
				Field.getSortableFieldName(
					StringBundler.concat(
						"createDate", StringPool.UNDERLINE, "Number")),
				SortOrder.ASC));
		searchSearchRequest.setIndexNames("workflow-metrics-tokens");
		searchSearchRequest.setQuery(
			new BooleanQueryImpl() {
				{
					setPreBooleanFilter(
						new BooleanFilter() {
							{
								addRequiredTerm("companyId", companyId);
								addRequiredTerm("deleted", false);
								addRequiredTerm("instanceId", instanceId);

								if (lastCheckLocalDateTime != null) {
									add(
										_createCompletionDateRangeFilter(
											lastCheckLocalDateTime),
										BooleanClauseOccur.SHOULD);
									add(
										new BooleanFilter() {
											{
												add(
													new ExistsFilter(
														"completionDate"),
													BooleanClauseOccur.
														MUST_NOT);
											}
										},
										BooleanClauseOccur.SHOULD);
								}
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
		).collect(
			Collectors.toList()
		);
	}

	protected static class TaskInterval {

		public LocalDateTime getEndDateLocalDateTime() {
			return _endDateLocalDateTime;
		}

		public LocalDateTime getStartDateLocalDateTime() {
			return _startDateLocalDateTime;
		}

		public void setEndDateLocalDateTime(
			LocalDateTime endDateLocalDateTime) {

			_endDateLocalDateTime = endDateLocalDateTime;
		}

		public void setStartDateLocalDateTime(
			LocalDateTime startDateLocalDateTime) {

			_startDateLocalDateTime = startDateLocalDateTime;
		}

		private LocalDateTime _endDateLocalDateTime;
		private LocalDateTime _startDateLocalDateTime;

	}

	private long _computeElapsedTime(
		TaskInterval taskInterval,
		WorkflowMetricsSLAStopwatch workflowMetricsSLAStopwatch) {

		long elapsedTime = 0;

		LocalDateTime endDateLocalDateTime =
			taskInterval.getEndDateLocalDateTime();

		LocalDateTime startDateLocalDateTime =
			taskInterval.getStartDateLocalDateTime();

		for (TaskInterval timeMarkerInterval :
				workflowMetricsSLAStopwatch.getTimeMarkerIntervals()) {

			if (startDateLocalDateTime.isAfter(
					timeMarkerInterval._endDateLocalDateTime) ||
				endDateLocalDateTime.isBefore(
					timeMarkerInterval._startDateLocalDateTime)) {

				continue;
			}

			Duration duration = Duration.between(
				LocalDateTimeUtil.max(
					startDateLocalDateTime,
					timeMarkerInterval._startDateLocalDateTime),
				LocalDateTimeUtil.min(
					endDateLocalDateTime,
					timeMarkerInterval._endDateLocalDateTime));

			elapsedTime += duration.toMillis();
		}

		return elapsedTime;
	}

	private Filter _createCompletionDateRangeFilter(
		LocalDateTime lastCheckLocalDateTime) {

		DateRangeFilterBuilder completionDateRangeFilterBuilder =
			_filterBuilders.dateRangeFilterBuilder();

		completionDateRangeFilterBuilder.setFieldName("completionDate");

		DateTimeFormatter formatter = LocalDateTimeUtil.getDateTimeFormatter();

		completionDateRangeFilterBuilder.setFrom(
			formatter.format(lastCheckLocalDateTime));

		return completionDateRangeFilterBuilder.build();
	}

	private WorkflowMetricsSLAStopwatch _createWorkflowMetricsSLAStopwatch(
		List<Document> documents, LocalDateTime createDateLocalDateTime,
		LocalDateTime lastCheckLocalDateTime, long startNodeId,
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition,
		WorkfowMetricsSLAStatus workfowMetricsSLAStatus) {

		Map<Long, String> startMarkerMap = _getTimeMarkerMap(
			StringUtil.split(workflowMetricsSLADefinition.getStartNodeNames()));
		Map<Long, String> stopMarkerMap = _getTimeMarkerMap(
			StringUtil.split(workflowMetricsSLADefinition.getStopNodeNames()));
		Map<Long, String> pauseMarkerMap = _getTimeMarkerMap(
			StringUtil.split(workflowMetricsSLADefinition.getPauseNodeNames()));

		WorkflowMetricsSLAStopwatch workflowMetricsSLAStopwatch =
			new WorkflowMetricsSLAStopwatch(workfowMetricsSLAStatus);

		if (Objects.equals(
				workfowMetricsSLAStatus, WorkfowMetricsSLAStatus.RUNNING)) {

			workflowMetricsSLAStopwatch.run(lastCheckLocalDateTime);
		}
		else if (Objects.equals(
					workfowMetricsSLAStatus, WorkfowMetricsSLAStatus.NEW) &&
				 startMarkerMap.containsKey(startNodeId)) {

			workflowMetricsSLAStopwatch.run(createDateLocalDateTime);
		}

		Iterator<Document> iterator = documents.iterator();

		while (iterator.hasNext() &&
			   !workflowMetricsSLAStopwatch.isCompleted()) {

			Document document = iterator.next();

			long taskId = document.getLong("taskId");

			TaskInterval taskInterval = _toTaskInterval(
				document, lastCheckLocalDateTime, null);

			if (stopMarkerMap.containsKey(taskId)) {
				if (Objects.equals(stopMarkerMap.get(taskId), "enter")) {
					workflowMetricsSLAStopwatch.complete(
						taskInterval._startDateLocalDateTime);
				}

				if (Objects.equals(stopMarkerMap.get(taskId), "leave") &&
					(taskInterval._endDateLocalDateTime != null)) {

					workflowMetricsSLAStopwatch.complete(
						taskInterval._endDateLocalDateTime);
				}
			}
			else if (startMarkerMap.containsKey(taskId)) {
				if (Objects.equals(startMarkerMap.get(taskId), "leave") &&
					(taskInterval._endDateLocalDateTime != null)) {

					workflowMetricsSLAStopwatch.run(
						taskInterval._endDateLocalDateTime);
				}
				else if (Objects.equals(startMarkerMap.get(taskId), "enter")) {
					workflowMetricsSLAStopwatch.run(
						taskInterval._startDateLocalDateTime);
				}
			}
			else if (pauseMarkerMap.containsKey(taskId)) {
				workflowMetricsSLAStopwatch.pause(
					taskInterval._startDateLocalDateTime);

				if (taskInterval._endDateLocalDateTime != null) {
					workflowMetricsSLAStopwatch.run(
						taskInterval._endDateLocalDateTime);
				}
			}
		}

		return workflowMetricsSLAStopwatch;
	}

	private Map<Long, String> _getTimeMarkerMap(List<String> timeMarkers) {
		Map<Long, String> map = new HashMap<>(timeMarkers.size());

		for (String timeMarker : timeMarkers) {
			List<String> parts = StringUtil.split(timeMarker, CharPool.COLON);

			long nodeId = Long.valueOf(parts.get(0));

			if (parts.size() == 1) {
				map.put(nodeId, StringPool.BLANK);
			}
			else {
				map.put(nodeId, parts.get(1));
			}
		}

		return map;
	}

	private TaskInterval _toTaskInterval(
		Document document, LocalDateTime lastCheckLocalDateTime,
		LocalDateTime nowLocalDateTime) {

		TaskInterval taskInterval = new TaskInterval();

		if (Validator.isNull(document.getValue("completionDate"))) {
			taskInterval._endDateLocalDateTime = nowLocalDateTime;
		}
		else {
			taskInterval._endDateLocalDateTime =
				LocalDateTimeUtil.toLocalDateTime(document, "completionDate");
		}

		LocalDateTime createDateLocalDateTime =
			LocalDateTimeUtil.toLocalDateTime(document, "createDate");

		if ((lastCheckLocalDateTime != null) &&
			lastCheckLocalDateTime.isAfter(createDateLocalDateTime)) {

			taskInterval._startDateLocalDateTime = lastCheckLocalDateTime;
		}
		else {
			taskInterval._startDateLocalDateTime = createDateLocalDateTime;
		}

		return taskInterval;
	}

	private List<TaskInterval> _toTaskIntervals(
		List<Document> documents, LocalDateTime lastCheckLocalDateTime,
		LocalDateTime nowLocalDateTime) {

		if (ListUtil.isEmpty(documents)) {
			return Collections.emptyList();
		}

		Stack<TaskInterval> taskIntervals = new Stack<>();

		taskIntervals.push(
			_toTaskInterval(
				documents.get(0), lastCheckLocalDateTime, nowLocalDateTime));

		for (int i = 1; i < documents.size(); i++) {
			TaskInterval topTaskInterval = taskIntervals.peek();

			TaskInterval taskInterval = _toTaskInterval(
				documents.get(i), lastCheckLocalDateTime, nowLocalDateTime);

			LocalDateTime topEndLocalDateTime =
				topTaskInterval.getEndDateLocalDateTime();

			if (topEndLocalDateTime.isBefore(
					taskInterval.getStartDateLocalDateTime())) {

				taskIntervals.push(taskInterval);
			}
			else if (topEndLocalDateTime.isBefore(
						taskInterval.getEndDateLocalDateTime())) {

				topTaskInterval._endDateLocalDateTime =
					taskInterval.getEndDateLocalDateTime();
			}
		}

		return taskIntervals;
	}

	@Reference
	private FilterBuilders _filterBuilders;

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

	@Reference
	private Sorts _sorts;

}