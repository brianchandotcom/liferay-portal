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
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;
import com.liferay.portal.workflow.metrics.sla.calendar.WorkflowMetricsSLACalendar;
import com.liferay.portal.workflow.metrics.sla.calendar.WorkflowMetricsSLACalendarTracker;
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

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

	public Optional<WorkflowMetricsSLAInstanceResult> process(
		long companyId, LocalDateTime completionLocalDateTime,
		LocalDateTime createLocalDateTime, long instanceId,
		LocalDateTime nowLocalDateTime, long startNodeId,
		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion) {

		WorkflowMetricsSLAInstanceResult lastWorkflowMetricsSLAInstanceResult =
			fetchLastWorkflowMetricsSLAInstanceResult(
				workflowMetricsSLADefinitionVersion, instanceId);

		long elapsedTime = 0;
		LocalDateTime lastCheckLocalDateTime = null;
		WorkflowMetricsSLAStatus workflowMetricsSLAStatus =
			WorkflowMetricsSLAStatus.NEW;

		if (lastWorkflowMetricsSLAInstanceResult != null) {
			elapsedTime = lastWorkflowMetricsSLAInstanceResult.getElapsedTime();

			lastCheckLocalDateTime =
				lastWorkflowMetricsSLAInstanceResult.
					getLastCheckLocalDateTime();
			workflowMetricsSLAStatus =
				lastWorkflowMetricsSLAInstanceResult.
					getWorkflowMetricsSLAStatus();

			if (lastCheckLocalDateTime.isAfter(nowLocalDateTime) ||
				Objects.equals(
					workflowMetricsSLAStatus,
					WorkflowMetricsSLAStatus.COMPLETED)) {

				return Optional.empty();
			}
		}

		List<Document> documents = getDocuments(
			companyId, instanceId, lastCheckLocalDateTime);
		WorkflowMetricsSLACalendar workflowMetricsSLACalendar =
			_workflowMetricsSLACalendarTracker.getWorkflowMetricsSLACalendar(
				workflowMetricsSLADefinitionVersion.getCalendarKey());
		WorkflowMetricsSLAStopwatch workflowMetricsSLAStopwatch =
			_createWorkflowMetricsSLAStopwatch(
				documents, createLocalDateTime, lastCheckLocalDateTime,
				startNodeId, workflowMetricsSLADefinitionVersion,
				workflowMetricsSLAStatus);

		LocalDateTime endLocalDateTime = nowLocalDateTime;

		if (completionLocalDateTime != null) {
			endLocalDateTime = completionLocalDateTime;
		}

		if (!workflowMetricsSLAStopwatch.isEmpty()) {
			List<TaskInterval> taskIntervals = _toTaskIntervals(
				documents, lastCheckLocalDateTime, nowLocalDateTime);

			for (TaskInterval taskInterval : taskIntervals) {
				endLocalDateTime = taskInterval.getEndLocalDateTime();

				elapsedTime += _computeElapsedTime(
					endLocalDateTime, taskInterval.getStartLocalDateTime(),
					workflowMetricsSLACalendar, workflowMetricsSLAStopwatch);
			}

			workflowMetricsSLAStatus =
				workflowMetricsSLAStopwatch.getWorkflowMetricsSLAStatus();

			if (completionLocalDateTime != null) {
				workflowMetricsSLAStatus = WorkflowMetricsSLAStatus.COMPLETED;
			}

			if (Objects.equals(
					workflowMetricsSLAStatus,
					WorkflowMetricsSLAStatus.RUNNING)) {

				Duration duration = workflowMetricsSLACalendar.getDuration(
					endLocalDateTime, nowLocalDateTime);

				elapsedTime += duration.toMillis();

				endLocalDateTime = nowLocalDateTime;
			}
			else if (Objects.equals(
						workflowMetricsSLAStatus,
						WorkflowMetricsSLAStatus.COMPLETED)) {

				Duration duration = workflowMetricsSLACalendar.getDuration(
					endLocalDateTime, completionLocalDateTime);

				elapsedTime += duration.toMillis();

				endLocalDateTime = completionLocalDateTime;
			}
		}

		return Optional.of(
			_createWorkflowMetricsSLAInstanceResult(
				companyId, completionLocalDateTime, documents, elapsedTime,
				endLocalDateTime, instanceId, nowLocalDateTime,
				workflowMetricsSLACalendar, workflowMetricsSLADefinitionVersion,
				workflowMetricsSLAStatus));
	}

	protected WorkflowMetricsSLAInstanceResult
		fetchLastWorkflowMetricsSLAInstanceResult(
			WorkflowMetricsSLADefinitionVersion
				workflowMetricsSLADefinitionVersion,
			long instanceId) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			"workflow-metrics-sla-instance-results");
		searchSearchRequest.setQuery(
			new BooleanQueryImpl() {
				{
					setPreBooleanFilter(
						new BooleanFilter() {
							{
								addRequiredTerm(
									"companyId",
									workflowMetricsSLADefinitionVersion.
										getCompanyId());
								addRequiredTerm("deleted", false);
								addRequiredTerm("instanceId", instanceId);
								addRequiredTerm(
									"slaDefinitionId",
									workflowMetricsSLADefinitionVersion.
										getWorkflowMetricsSLADefinitionId());
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
			document -> new WorkflowMetricsSLAInstanceResult() {
				{
					setCompanyId(
						workflowMetricsSLADefinitionVersion.getCompanyId());
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
					setProcessId(
						workflowMetricsSLADefinitionVersion.getProcessId());
					setRemainingTime(document.getLong("remainingTime"));
					setSLADefinitionId(
						workflowMetricsSLADefinitionVersion.
							getWorkflowMetricsSLADefinitionId());
					setWorkflowMetricsSLAStatus(
						WorkflowMetricsSLAStatus.valueOf(
							document.getString("status")));
				}
			}
		).orElseGet(
			() -> null
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

	protected boolean isBreached(
		Document document, LocalDateTime nowLocalDateTime,
		LocalDateTime overdueLocalDateTime) {

		if (nowLocalDateTime.isBefore(overdueLocalDateTime)) {
			return false;
		}

		LocalDateTime createDateLocalDateTime = LocalDateTime.parse(
			document.getString("createDate"), _dateTimeFormatter);

		if (createDateLocalDateTime.isAfter(overdueLocalDateTime)) {
			return false;
		}

		if (Validator.isNull(document.getValue("completionDate"))) {
			return true;
		}

		LocalDateTime completionDateLocalDateTime = LocalDateTime.parse(
			document.getString("completionDate"), _dateTimeFormatter);

		if (completionDateLocalDateTime.isAfter(overdueLocalDateTime)) {
			return true;
		}

		return false;
	}

	protected boolean isOnTime(
		Document document, LocalDateTime nowLocalDateTime,
		LocalDateTime overdueLocalDateTime) {

		if (nowLocalDateTime.isBefore(overdueLocalDateTime)) {
			return true;
		}

		LocalDateTime createDateLocalDateTime = LocalDateTime.parse(
			document.getString("createDate"), _dateTimeFormatter);

		if (createDateLocalDateTime.isAfter(overdueLocalDateTime)) {
			return false;
		}

		if (Validator.isNull(document.getValue("completionDate"))) {
			return false;
		}

		LocalDateTime completionDateLocalDateTime = LocalDateTime.parse(
			document.getString("completionDate"), _dateTimeFormatter);

		if (completionDateLocalDateTime.isBefore(overdueLocalDateTime) ||
			Objects.equals(completionDateLocalDateTime, overdueLocalDateTime)) {

			return true;
		}

		return false;
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

	private WorkflowMetricsSLAInstanceResult
		_createWorkflowMetricsSLAInstanceResult(
			long companyId, LocalDateTime completionLocalDateTime,
			List<Document> documents, long elapsedTime,
			LocalDateTime endLocalDateTime, long instanceId,
			LocalDateTime nowLocalDateTime,
			WorkflowMetricsSLACalendar workflowMetricsSLACalendar,
			WorkflowMetricsSLADefinitionVersion
				workflowMetricsSLADefinitionVersion,
			WorkflowMetricsSLAStatus workflowMetricsSLAStatus) {

		WorkflowMetricsSLAInstanceResult workflowMetricsSLAInstanceResult =
			new WorkflowMetricsSLAInstanceResult() {
				{
					setCompanyId(companyId);

					if (completionLocalDateTime != null) {
						setCompletionLocalDateTime(completionLocalDateTime);
					}

					setElapsedTime(elapsedTime);
					setInstanceId(instanceId);
					setLastCheckLocalDateTime(nowLocalDateTime);
					setOnTime(
						elapsedTime <=
							workflowMetricsSLADefinitionVersion.getDuration());

					long remainingTime =
						workflowMetricsSLADefinitionVersion.getDuration() -
							elapsedTime;

					setOverdueLocalDateTime(
						workflowMetricsSLACalendar.getOverdueLocalDateTime(
							endLocalDateTime,
							Duration.ofMillis(remainingTime)));

					setProcessId(
						workflowMetricsSLADefinitionVersion.getProcessId());
					setRemainingTime(remainingTime);
					setSLADefinitionId(
						workflowMetricsSLADefinitionVersion.
							getWorkflowMetricsSLADefinitionId());
					setWorkflowMetricsSLAStatus(workflowMetricsSLAStatus);
				}
			};

		workflowMetricsSLAInstanceResult.setWorkflowMetricsSLATaskResults(
			_createWorkflowMetricsSLATaskResults(
				documents, completionLocalDateTime != null, nowLocalDateTime,
				workflowMetricsSLAInstanceResult));

		return workflowMetricsSLAInstanceResult;
	}

	private WorkflowMetricsSLAStopwatch _createWorkflowMetricsSLAStopwatch(
		List<Document> documents, LocalDateTime createDateLocalDateTime,
		LocalDateTime lastCheckLocalDateTime, long startNodeId,
		WorkflowMetricsSLADefinitionVersion workflowMetricsSLADefinitionVersion,
		WorkflowMetricsSLAStatus workflowMetricsSLAStatus) {

		WorkflowMetricsSLAStopwatch workflowMetricsSLAStopwatch =
			new WorkflowMetricsSLAStopwatch(workflowMetricsSLAStatus);

		if (Objects.equals(
				workflowMetricsSLAStatus, WorkflowMetricsSLAStatus.STOPPED)) {

			return workflowMetricsSLAStopwatch;
		}

		Map<Long, String> startTimeMarkers = _getTimeMarkers(
			StringUtil.split(
				workflowMetricsSLADefinitionVersion.getStartNodeKeys()));

		if (Objects.equals(
				workflowMetricsSLAStatus, WorkflowMetricsSLAStatus.NEW) &&
			startTimeMarkers.containsKey(startNodeId)) {

			workflowMetricsSLAStopwatch.run(createDateLocalDateTime);
		}
		else if (Objects.equals(
					workflowMetricsSLAStatus,
					WorkflowMetricsSLAStatus.RUNNING)) {

			workflowMetricsSLAStopwatch.run(lastCheckLocalDateTime);
		}

		Map<Long, String> pauseTimeMarkers = _getTimeMarkers(
			StringUtil.split(
				workflowMetricsSLADefinitionVersion.getPauseNodeKeys()));
		Map<Long, String> stopTimeMarkers = _getTimeMarkers(
			StringUtil.split(
				workflowMetricsSLADefinitionVersion.getStopNodeKeys()));

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

	private WorkflowMetricsSLATaskResult _createWorkflowMetricsSLATaskResult(
		Document document, boolean instanceCompleted,
		LocalDateTime nowLocalDateTime,
		WorkflowMetricsSLAInstanceResult workflowMetricsSLAInstanceResult) {

		return new WorkflowMetricsSLATaskResult() {
			{
				setAssigneeId(document.getLong("assigneeId"));
				setBreached(
					WorkflowMetricsSLAProcessor.this.isBreached(
						document, nowLocalDateTime,
						workflowMetricsSLAInstanceResult.
							getOverdueLocalDateTime()));
				setCompanyId(workflowMetricsSLAInstanceResult.getCompanyId());

				if (Validator.isNotNull(document.getString("completionDate"))) {
					setCompletionLocalDateTime(
						LocalDateTime.parse(
							document.getString("completionDate"),
							_dateTimeFormatter));
				}

				if (Validator.isNotNull(document.getLong("completionUserId"))) {
					setCompletionUserId(document.getLong("completionUserId"));
				}

				setInstanceCompleted(instanceCompleted);
				setInstanceId(workflowMetricsSLAInstanceResult.getInstanceId());
				setLastCheckLocalDateTime(
					workflowMetricsSLAInstanceResult.
						getLastCheckLocalDateTime());
				setOnTime(
					WorkflowMetricsSLAProcessor.this.isOnTime(
						document, nowLocalDateTime,
						workflowMetricsSLAInstanceResult.
							getOverdueLocalDateTime()));
				setProcessId(workflowMetricsSLAInstanceResult.getProcessId());
				setSLADefinitionId(
					workflowMetricsSLAInstanceResult.getSLADefinitionId());
				setTaskId(document.getLong("taskId"));
				setTaskName(document.getString("taskName"));
				setTokenId(document.getLong("tokenId"));
				setWorkflowMetricsSLAStatus(
					_getWorkflowMetricsSLAStatus(
						document, workflowMetricsSLAInstanceResult));
			}
		};
	}

	private List<WorkflowMetricsSLATaskResult>
		_createWorkflowMetricsSLATaskResults(
			List<Document> documents, boolean instanceCompleted,
			LocalDateTime nowLocalDateTime,
			WorkflowMetricsSLAInstanceResult workflowMetricsSLAInstanceResult) {

		Stream<Document> stream = documents.stream();

		return stream.map(
			document -> _createWorkflowMetricsSLATaskResult(
				document, instanceCompleted, nowLocalDateTime,
				workflowMetricsSLAInstanceResult)
		).collect(
			Collectors.toList()
		);
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
		Map<Long, String> map = new HashMap<>();

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

	private WorkflowMetricsSLAStatus _getWorkflowMetricsSLAStatus(
		Document document,
		WorkflowMetricsSLAInstanceResult workflowMetricsSLAInstanceResult) {

		if (Objects.equals(
				workflowMetricsSLAInstanceResult.getWorkflowMetricsSLAStatus(),
				WorkflowMetricsSLAStatus.NEW)) {

			return WorkflowMetricsSLAStatus.NEW;
		}

		if (GetterUtil.getBoolean(document.getBoolean("completed")) ||
			(workflowMetricsSLAInstanceResult.getCompletionLocalDateTime() !=
				null) ||
			Objects.equals(
				workflowMetricsSLAInstanceResult.getWorkflowMetricsSLAStatus(),
				WorkflowMetricsSLAStatus.COMPLETED)) {

			return WorkflowMetricsSLAStatus.COMPLETED;
		}

		return WorkflowMetricsSLAStatus.RUNNING;
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