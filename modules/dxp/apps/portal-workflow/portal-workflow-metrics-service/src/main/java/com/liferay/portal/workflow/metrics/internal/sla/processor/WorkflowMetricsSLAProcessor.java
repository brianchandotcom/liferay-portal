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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.Collections;
import java.util.List;
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

	public WorkflowMetricsSLAProcessResult process(
		long companyId, long instanceId, LocalDateTime nowLocalDateTime,
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		List<TaskInterval> taskIntervals = _getTaskIntervals(
			companyId, nowLocalDateTime, instanceId);

		long elapsedTime = 0;

		for (TaskInterval taskInterval : taskIntervals) {
			Duration duration = Duration.between(
				taskInterval.getStartDateLocalDateTime(),
				taskInterval.getEndDateLocalDateTime());

			elapsedTime += duration.toMillis();
		}

		long remainingTime =
			workflowMetricsSLADefinition.getDuration() - elapsedTime;

		return new WorkflowMetricsSLAProcessResult(
			companyId, elapsedTime, instanceId, nowLocalDateTime,
			elapsedTime <= workflowMetricsSLADefinition.getDuration(),
			nowLocalDateTime.plus(remainingTime, ChronoUnit.MILLIS),
			workflowMetricsSLADefinition.getProcessId(), remainingTime,
			workflowMetricsSLADefinition.getWorkflowMetricsSLADefinitionId());
	}

	protected List<Document> getDocuments(long companyId, long instanceId) {
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
								addRequiredTerm("completed", false);
								addRequiredTerm("deleted", false);
								addRequiredTerm("instanceId", instanceId);
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

	private List<TaskInterval> _getTaskIntervals(
		long companyId, LocalDateTime nowLocalDateTime, long instanceId) {

		List<Document> documents = getDocuments(companyId, instanceId);

		if (documents.isEmpty()) {
			return Collections.emptyList();
		}

		return _toNonoverlapingTaskIntervals(nowLocalDateTime, documents);
	}

	private LocalDateTime _toLocalDateTime(
		Document document, String fieldName) {

		String dateString = (String)document.getFieldValue(fieldName);

		return LocalDateTime.parse(
			dateString,
			DateTimeFormatter.ofPattern(_INDEX_DATE_FORMAT_PATTERN));
	}

	private Stack<TaskInterval> _toNonoverlapingTaskIntervals(
		LocalDateTime nowLocalDateTime, List<Document> documents) {

		Stack<TaskInterval> taskIntervals = new Stack<>();

		taskIntervals.push(_toTaskInterval(documents.get(0), nowLocalDateTime));

		for (int i = 1; i < documents.size(); i++) {
			TaskInterval topTaskInterval = taskIntervals.peek();

			TaskInterval taskInterval = _toTaskInterval(
				documents.get(i), nowLocalDateTime);

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

	private TaskInterval _toTaskInterval(
		Document document, LocalDateTime nowLocalDateTime) {

		TaskInterval taskInterval = new TaskInterval();

		if (Validator.isNull(document.getFieldValue("completionDate"))) {
			taskInterval._endDateLocalDateTime = nowLocalDateTime;
		}
		else {
			taskInterval._endDateLocalDateTime = _toLocalDateTime(
				document, "completionDate");
		}

		taskInterval._startDateLocalDateTime = _toLocalDateTime(
			document, "createDate");

		return taskInterval;
	}

	private static final String _INDEX_DATE_FORMAT_PATTERN = PropsUtil.get(
		PropsKeys.INDEX_DATE_FORMAT_PATTERN);

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

	@Reference
	private Sorts _sorts;

	private class TaskInterval {

		public LocalDateTime getEndDateLocalDateTime() {
			return _endDateLocalDateTime;
		}

		public LocalDateTime getStartDateLocalDateTime() {
			return _startDateLocalDateTime;
		}

		private LocalDateTime _endDateLocalDateTime;
		private LocalDateTime _startDateLocalDateTime;

	}

}