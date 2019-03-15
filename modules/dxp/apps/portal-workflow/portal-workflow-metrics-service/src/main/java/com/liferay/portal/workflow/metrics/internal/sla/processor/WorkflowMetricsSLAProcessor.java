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
		long companyId, long instanceId, LocalDateTime now,
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		List<TaskInterval> taskIntervals = getWorkflowMetricsTaskIntervals(
			companyId, now, instanceId);

		long elapsedTime = 0;

		for (TaskInterval taskInterval : taskIntervals) {
			Duration duration = Duration.between(
				taskInterval.getStartDate(), taskInterval.getEndDate());

			elapsedTime += duration.toMillis();
		}

		long remainingTime =
			workflowMetricsSLADefinition.getDuration() - elapsedTime;

		return new WorkflowMetricsSLAProcessResult(
			companyId, instanceId, workflowMetricsSLADefinition.getProcessId(),
			workflowMetricsSLADefinition.getWorkflowMetricsSLADefinitionId(),
			elapsedTime, now,
			elapsedTime <= workflowMetricsSLADefinition.getDuration(),
			computeOverdueDate(now, remainingTime), remainingTime);
	}

	protected LocalDateTime computeOverdueDate(
		LocalDateTime checkLocalDateTime, long remainingTime) {

		return checkLocalDateTime.plus(remainingTime, ChronoUnit.MILLIS);
	}

	protected List<Document> getTokenDocuments(
		long companyId, long instanceId) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.addSorts(
			sorts.field(
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
			searchRequestExecutor.executeSearchRequest(searchSearchRequest);

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

	protected List<TaskInterval> getWorkflowMetricsTaskIntervals(
		long companyId, LocalDateTime checkLocalDateTime, long instanceId) {

		List<Document> documents = getTokenDocuments(companyId, instanceId);

		if (documents.isEmpty()) {
			return Collections.emptyList();
		}

		return toNonoverlapingIntervals(checkLocalDateTime, documents);
	}

	protected Stack<TaskInterval> toNonoverlapingIntervals(
		LocalDateTime checkLocalDateTime, List<Document> documents) {

		Stack<TaskInterval> taskIntervals = new Stack<>();

		taskIntervals.push(
			_toTaskInterval(documents.get(0), checkLocalDateTime));

		for (int i = 1; i < documents.size(); i++) {
			TaskInterval top = taskIntervals.peek();

			TaskInterval taskInterval = _toTaskInterval(
				documents.get(i), checkLocalDateTime);

			LocalDateTime topEndLocalDateTime = top.getEndDate();

			if (topEndLocalDateTime.isBefore(taskInterval.getStartDate())) {
				taskIntervals.push(taskInterval);
			}
			else if (topEndLocalDateTime.isBefore(taskInterval.getEndDate())) {
				top._endDate = taskInterval.getEndDate();
			}
		}

		return taskIntervals;
	}

	@Reference
	protected SearchRequestExecutor searchRequestExecutor;

	@Reference
	protected Sorts sorts;

	private LocalDateTime _toLocalDateTime(
		Document document, String fieldName) {

		String dateString = (String)document.getFieldValue(fieldName);

		return LocalDateTime.parse(
			dateString,
			DateTimeFormatter.ofPattern(_INDEX_DATE_FORMAT_PATTERN));
	}

	private TaskInterval _toTaskInterval(
		Document document, LocalDateTime checkLocalDateTime) {

		TaskInterval taskInterval = new TaskInterval();

		if (Validator.isNull(document.getFieldValue("completionDate"))) {
			taskInterval._endDate = checkLocalDateTime;
		}
		else {
			taskInterval._endDate = _toLocalDateTime(
				document, "completionDate");
		}

		taskInterval._startDate = _toLocalDateTime(document, "createDate");

		return taskInterval;
	}

	private static final String _INDEX_DATE_FORMAT_PATTERN = PropsUtil.get(
		PropsKeys.INDEX_DATE_FORMAT_PATTERN);

	private class TaskInterval {

		public LocalDateTime getEndDate() {
			return _endDate;
		}

		public LocalDateTime getStartDate() {
			return _startDate;
		}

		private LocalDateTime _endDate;
		private LocalDateTime _startDate;

	}

}