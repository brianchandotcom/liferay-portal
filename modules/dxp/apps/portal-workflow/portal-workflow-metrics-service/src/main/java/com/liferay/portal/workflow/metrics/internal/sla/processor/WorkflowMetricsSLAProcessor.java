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
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.filter.DateRangeFilter;
import com.liferay.portal.search.filter.DateRangeFilterBuilder;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.sla.calendar.WorkflowMetricsSLACalendar;
import com.liferay.portal.workflow.metrics.sla.calendar.WorkflowMetricsSLACalendarTracker;
import com.liferay.portal.workflow.metrics.sla.processor.WorkfowMetricsSLAStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

			if (lastCheckLocalDateTime.isAfter(nowLocalDateTime) ||
				Objects.equals(
					workfowMetricsSLAStatus,
					WorkfowMetricsSLAStatus.COMPLETED)) {

				return Optional.empty();
			}
		}

		List<Document> documents = getDocuments(
			companyId, instanceId, lastCheckLocalDateTime);
		WorkflowMetricsSLACalendar workflowMetricsSLACalendar =
			_workflowMetricsSLACalendarTracker.getWorkflowMetricsSLACalendar(
				workflowMetricsSLADefinition.getCalendarKey());
		WorkflowMetricsSLAStopwatch workflowMetricsSLAStopwatch =
			_createWorkflowMetricsSLAStopwatch(
				documents, createLocalDateTime, lastCheckLocalDateTime,
				startNodeId, workflowMetricsSLADefinition,
				workfowMetricsSLAStatus);

		if (!workflowMetricsSLAStopwatch.isEmpty()) {
			List<TaskInterval> taskIntervals = _toTaskIntervals(
				documents, lastCheckLocalDateTime, nowLocalDateTime);

			for (TaskInterval taskInterval : taskIntervals) {
				elapsedTime += _computeElapsedTime(
					taskInterval.getEndLocalDateTime(),
					taskInterval.getStartLocalDateTime(),
					workflowMetricsSLACalendar, workflowMetricsSLAStopwatch);
			}

			workfowMetricsSLAStatus =
				workflowMetricsSLAStopwatch.getWorkfowMetricsSLAStatus();
		}

		return _createOptionalWorkflowMetricsSLAProcessResult(
			companyId, elapsedTime, instanceId, nowLocalDateTime,
			workflowMetricsSLACalendar, workflowMetricsSLADefinition,
			workfowMetricsSLAStatus);
	}

	protected WorkflowMetricsSLAProcessResult
		fetchLastWorkflowMetricsSLAProcessResult(
			WorkflowMetricsSLADefinition workflowMetricsSLADefinition,
			long instanceId) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			"workflow-metrics-sla-process-results");
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
								addRequiredTerm("deleted", false);
								addRequiredTerm("instanceId", instanceId);
							}
						});
				}
			});

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
			document -> new WorkflowMetricsSLAProcessResult() {
				{
					setCompanyId(workflowMetricsSLADefinition.getCompanyId());
					setElapsedTime(document.getLong("elapsedTime"));
					setInstanceId(instanceId);
					setLastCheckLocalDateTime(
						LocalDateTime.parse(
							document.getString("lastCheckDate"),
							_dateTimeFormatter));
					setOnTime(
						GetterUtil.getBoolean(document.getValue("onTime")));
					setOverdueLocalDateTime(
						LocalDateTime.parse(
							document.getString("overdueDate"),
							_dateTimeFormatter));
					setProcessId(workflowMetricsSLADefinition.getProcessId());
					setRemainingTime(document.getLong("remainingTime"));
					setSLADefinitionId(
						workflowMetricsSLADefinition.
							getWorkflowMetricsSLADefinitionId());
					setWorkfowMetricsSLAStatus(
						WorkfowMetricsSLAStatus.valueOf(
							document.getString("status")));
				}
			}
		).orElse(
			null
		);
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
		searchSearchRequest.setSize(10000);

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
		).collect(
			Collectors.toList()
		);
	}

	protected static class TaskInterval {

		public LocalDateTime getEndLocalDateTime() {
			return _endLocalDateTime;
		}

		public LocalDateTime getStartLocalDateTime() {
			return _startLocalDateTime;
		}

		public void setEndLocalDateTime(LocalDateTime endLocalDateTime) {
			_endLocalDateTime = endLocalDateTime;
		}

		public void setStartLocalDateTime(LocalDateTime startLocalDateTime) {
			_startLocalDateTime = startLocalDateTime;
		}

		private LocalDateTime _endLocalDateTime;
		private LocalDateTime _startLocalDateTime;

	}

	private long _computeElapsedTime(
		LocalDateTime endLocalDateTime, LocalDateTime starLocalDateTime,
		WorkflowMetricsSLACalendar workflowMetricsSLACalendar,
		WorkflowMetricsSLAStopwatch workflowMetricsSLAStopwatch) {

		long elapsedTime = 0;

		for (TaskInterval taskInterval :
				workflowMetricsSLAStopwatch.getTaskIntervals()) {

			if (endLocalDateTime.isBefore(taskInterval._startLocalDateTime) ||
				starLocalDateTime.isAfter(taskInterval._endLocalDateTime)) {

				continue;
			}

			Duration duration = workflowMetricsSLACalendar.getDuration(
				_getMaxLocalDateTime(
					starLocalDateTime, taskInterval._startLocalDateTime),
				_getMinLocalDateTime(
					endLocalDateTime, taskInterval._endLocalDateTime));

			elapsedTime += duration.toMillis();
		}

		return elapsedTime;
	}

	private DateRangeFilter _createCompletionDateRangeFilter(
		LocalDateTime lastCheckLocalDateTime) {

		DateRangeFilterBuilder completionDateRangeFilterBuilder =
			_filterBuilders.dateRangeFilterBuilder();

		completionDateRangeFilterBuilder.setFieldName("completionDate");
		completionDateRangeFilterBuilder.setFrom(
			_dateTimeFormatter.format(lastCheckLocalDateTime));

		return completionDateRangeFilterBuilder.build();
	}

	private Optional<WorkflowMetricsSLAProcessResult>
		_createOptionalWorkflowMetricsSLAProcessResult(
			long companyId, long elapsedTime, long instanceId,
			LocalDateTime nowLocalDateTime,
			WorkflowMetricsSLACalendar workflowMetricsSLACalendar,
			WorkflowMetricsSLADefinition workflowMetricsSLADefinition,
			WorkfowMetricsSLAStatus workfowMetricsSLAStatus) {

		return Optional.of(
			new WorkflowMetricsSLAProcessResult() {
				{
					setCompanyId(companyId);
					setElapsedTime(elapsedTime);
					setInstanceId(instanceId);
					setLastCheckLocalDateTime(nowLocalDateTime);
					setOnTime(
						elapsedTime <=
							workflowMetricsSLADefinition.getDuration());

					long remainingTime =
						workflowMetricsSLADefinition.getDuration() -
							elapsedTime;

					setOverdueLocalDateTime(
						workflowMetricsSLACalendar.getOverdueLocalDateTime(
							nowLocalDateTime,
							Duration.ofMillis(remainingTime)));

					setProcessId(workflowMetricsSLADefinition.getProcessId());
					setRemainingTime(remainingTime);
					setSLADefinitionId(
						workflowMetricsSLADefinition.
							getWorkflowMetricsSLADefinitionId());
					setWorkfowMetricsSLAStatus(workfowMetricsSLAStatus);
				}
			});
	}

	private WorkflowMetricsSLAStopwatch _createWorkflowMetricsSLAStopwatch(
		List<Document> documents, LocalDateTime createDateLocalDateTime,
		LocalDateTime lastCheckLocalDateTime, long startNodeId,
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition,
		WorkfowMetricsSLAStatus workfowMetricsSLAStatus) {

		WorkflowMetricsSLAStopwatch workflowMetricsSLAStopwatch =
			new WorkflowMetricsSLAStopwatch(workfowMetricsSLAStatus);

		Map<Long, String> startTimeMarkers = _getTimeMarkers(
			StringUtil.split(workflowMetricsSLADefinition.getStartNodeKeys()));

		if (Objects.equals(
				workfowMetricsSLAStatus, WorkfowMetricsSLAStatus.RUNNING)) {

			workflowMetricsSLAStopwatch.run(lastCheckLocalDateTime);
		}
		else if (Objects.equals(
					workfowMetricsSLAStatus, WorkfowMetricsSLAStatus.NEW) &&
				 startTimeMarkers.containsKey(startNodeId)) {

			workflowMetricsSLAStopwatch.run(createDateLocalDateTime);
		}

		Map<Long, String> pauseTimeMarkers = _getTimeMarkers(
			StringUtil.split(workflowMetricsSLADefinition.getPauseNodeKeys()));
		Map<Long, String> stopTimeMarkers = _getTimeMarkers(
			StringUtil.split(workflowMetricsSLADefinition.getStopNodeKeys()));

		Iterator<Document> iterator = documents.iterator();

		while (iterator.hasNext() && !workflowMetricsSLAStopwatch.isStopped()) {
			Document document = iterator.next();

			long taskId = document.getLong("taskId");

			TaskInterval taskInterval = _toTaskInterval(
				document, lastCheckLocalDateTime, null);

			if (pauseTimeMarkers.containsKey(taskId) &&
				!stopTimeMarkers.containsKey(taskId)) {

				workflowMetricsSLAStopwatch.pause(
					taskInterval._startLocalDateTime);

				if (taskInterval._endLocalDateTime != null) {
					workflowMetricsSLAStopwatch.run(
						taskInterval._endLocalDateTime);
				}
			}

			if (startTimeMarkers.containsKey(taskId)) {
				if (Objects.equals(startTimeMarkers.get(taskId), "enter")) {
					workflowMetricsSLAStopwatch.run(
						taskInterval._startLocalDateTime);
				}
				else if (Objects.equals(
							startTimeMarkers.get(taskId), "leave") &&
						 (taskInterval._endLocalDateTime != null)) {

					workflowMetricsSLAStopwatch.run(
						taskInterval._endLocalDateTime);
				}
			}

			if (stopTimeMarkers.containsKey(taskId)) {
				if (Objects.equals(stopTimeMarkers.get(taskId), "enter")) {
					workflowMetricsSLAStopwatch.stop(
						taskInterval._startLocalDateTime);
				}
				else if (Objects.equals(stopTimeMarkers.get(taskId), "leave") &&
						 (taskInterval._endLocalDateTime != null)) {

					workflowMetricsSLAStopwatch.stop(
						taskInterval._endLocalDateTime);
				}
			}
		}

		return workflowMetricsSLAStopwatch;
	}

	private LocalDateTime _getMaxLocalDateTime(
		LocalDateTime localDateTime1, LocalDateTime localDateTime2) {

		if (localDateTime1.isAfter(localDateTime2)) {
			return localDateTime1;
		}

		return localDateTime2;
	}

	private LocalDateTime _getMinLocalDateTime(
		LocalDateTime localDateTime1, LocalDateTime localDateTime2) {

		if (localDateTime1.isBefore(localDateTime2)) {
			return localDateTime1;
		}

		return localDateTime2;
	}

	private Map<Long, String> _getTimeMarkers(List<String> nodeKeys) {
		Map<Long, String> map = new HashMap<>(nodeKeys.size());

		for (String nodeKey : nodeKeys) {
			List<String> parts = StringUtil.split(nodeKey, CharPool.COLON);

			long nodeId = GetterUtil.getLong(parts.get(0));

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
			taskInterval._endLocalDateTime = nowLocalDateTime;
		}
		else {
			taskInterval._endLocalDateTime = LocalDateTime.parse(
				document.getString("completionDate"), _dateTimeFormatter);
		}

		LocalDateTime createDateLocalDateTime = LocalDateTime.parse(
			document.getString("createDate"), _dateTimeFormatter);

		if ((lastCheckLocalDateTime != null) &&
			lastCheckLocalDateTime.isAfter(createDateLocalDateTime)) {

			taskInterval._startLocalDateTime = lastCheckLocalDateTime;
		}
		else {
			taskInterval._startLocalDateTime = createDateLocalDateTime;
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

		for (Document document : documents) {
			TaskInterval topTaskInterval = taskIntervals.peek();

			TaskInterval taskInterval = _toTaskInterval(
				document, lastCheckLocalDateTime, nowLocalDateTime);

			LocalDateTime topEndLocalDateTime =
				topTaskInterval.getEndLocalDateTime();

			if (topEndLocalDateTime.isBefore(
					taskInterval.getStartLocalDateTime())) {

				taskIntervals.push(taskInterval);
			}
			else if (topEndLocalDateTime.isBefore(
						taskInterval.getEndLocalDateTime())) {

				topTaskInterval._endLocalDateTime =
					taskInterval.getEndLocalDateTime();
			}
		}

		return taskIntervals;
	}

	private final DateTimeFormatter _dateTimeFormatter =
		DateTimeFormatter.ofPattern(
			PropsUtil.get(PropsKeys.INDEX_DATE_FORMAT_PATTERN));

	@Reference
	private FilterBuilders _filterBuilders;

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

	@Reference
	private Sorts _sorts;

	@Reference
	private WorkflowMetricsSLACalendarTracker
		_workflowMetricsSLACalendarTracker;

}