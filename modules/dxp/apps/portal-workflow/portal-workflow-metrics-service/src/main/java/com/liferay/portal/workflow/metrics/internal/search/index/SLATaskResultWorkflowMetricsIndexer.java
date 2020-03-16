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

package com.liferay.portal.workflow.metrics.internal.search.index;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.workflow.metrics.internal.sla.processor.WorkflowMetricsSLATaskResult;
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true,
	service = {
		SLATaskResultWorkflowMetricsIndexer.class, WorkflowMetricsIndex.class
	}
)
public class SLATaskResultWorkflowMetricsIndexer
	extends BaseSLAWorkflowMetricsIndexer {

	public Document createDocument(
		WorkflowMetricsSLATaskResult workflowMetricsSLATaskResult) {

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setString(
			Field.UID,
			digest(
				workflowMetricsSLATaskResult.getCompanyId(),
				workflowMetricsSLATaskResult.getInstanceId(),
				workflowMetricsSLATaskResult.getNodeId(),
				workflowMetricsSLATaskResult.getProcessId(),
				workflowMetricsSLATaskResult.getSLADefinitionId(),
				workflowMetricsSLATaskResult.getTaskId()));

		if (workflowMetricsSLATaskResult.getAssigneeId() != null) {
			documentBuilder.setLong(
				"assigneeId", workflowMetricsSLATaskResult.getAssigneeId());
		}

		documentBuilder.setValue(
			"breached", workflowMetricsSLATaskResult.isBreached()
		).setLong(
			"companyId", workflowMetricsSLATaskResult.getCompanyId()
		);

		if (workflowMetricsSLATaskResult.getCompletionLocalDateTime() != null) {
			documentBuilder.setDate(
				"completionDate",
				formatLocalDateTime(
					workflowMetricsSLATaskResult.getCompletionLocalDateTime()));
		}

		if (workflowMetricsSLATaskResult.getCompletionUserId() != null) {
			documentBuilder.setLong(
				"completionUserId",
				workflowMetricsSLATaskResult.getCompletionUserId());
		}

		documentBuilder.setValue(
			"deleted", false
		).setValue(
			"instanceCompleted",
			workflowMetricsSLATaskResult.isInstanceCompleted()
		).setLong(
			"instanceId", workflowMetricsSLATaskResult.getInstanceId()
		);

		if (workflowMetricsSLATaskResult.getLastCheckLocalDateTime() != null) {
			documentBuilder.setDate(
				"lastCheckDate",
				formatLocalDateTime(
					workflowMetricsSLATaskResult.getLastCheckLocalDateTime()));
		}

		documentBuilder.setValue(
			"onTime", workflowMetricsSLATaskResult.isOnTime()
		).setLong(
			"processId", workflowMetricsSLATaskResult.getProcessId()
		).setLong(
			"slaDefinitionId", workflowMetricsSLATaskResult.getSLADefinitionId()
		);

		WorkflowMetricsSLAStatus workflowMetricsSLAStatus =
			workflowMetricsSLATaskResult.getWorkflowMetricsSLAStatus();

		if (workflowMetricsSLAStatus != null) {
			documentBuilder.setString(
				"status", workflowMetricsSLAStatus.name());
		}

		documentBuilder.setLong(
			"nodeId", workflowMetricsSLATaskResult.getNodeId()
		).setString(
			"taskName", workflowMetricsSLATaskResult.getTaskName()
		).setLong(
			"taskId", workflowMetricsSLATaskResult.getTaskId()
		);

		return documentBuilder.build();
	}

	@Override
	public String getIndexName() {
		return "workflow-metrics-sla-task-results";
	}

	@Override
	public String getIndexType() {
		return "WorkflowMetricsSLATaskResultType";
	}

	public void reindex(long companyId) {
		_creatDefaultDocuments(companyId);
	}

	protected Document creatDefaultDocument(
		long companyId, long nodeId, long processId, String taskName) {

		WorkflowMetricsSLATaskResult workflowMetricsSLATaskResult =
			new WorkflowMetricsSLATaskResult();

		workflowMetricsSLATaskResult.setCompanyId(companyId);
		workflowMetricsSLATaskResult.setNodeId(nodeId);
		workflowMetricsSLATaskResult.setProcessId(processId);
		workflowMetricsSLATaskResult.setTaskName(taskName);

		return createDocument(workflowMetricsSLATaskResult);
	}

	private void _creatDefaultDocuments(long companyId) {
		if ((searchEngineAdapter == null) ||
			!hasIndex("workflow-metrics-nodes")) {

			return;
		}

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames("workflow-metrics-nodes");

		BooleanQuery booleanQuery = queries.booleanQuery();

		booleanQuery.addFilterQueryClauses(
			queries.term("companyId", companyId),
			queries.term("deleted", Boolean.FALSE));

		searchSearchRequest.setQuery(booleanQuery);

		searchSearchRequest.setSize(10000);

		SearchSearchResponse searchSearchResponse = searchEngineAdapter.execute(
			searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		if (searchHits.getTotalHits() == 0) {
			return;
		}

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		Stream.of(
			searchHits.getSearchHits()
		).flatMap(
			List::stream
		).map(
			SearchHit::getDocument
		).map(
			document -> creatDefaultDocument(
				companyId, document.getLong("nodeId"),
				document.getLong("processId"), document.getString("name"))
		).map(
			document -> new IndexDocumentRequest(getIndexName(), document) {
				{
					setType(getIndexType());
				}
			}
		).forEach(
			bulkDocumentRequest::addBulkableDocumentRequest
		);

		if (ListUtil.isNotEmpty(
				bulkDocumentRequest.getBulkableDocumentRequests())) {

			if (PortalRunMode.isTestMode()) {
				bulkDocumentRequest.setRefresh(true);
			}

			searchEngineAdapter.execute(bulkDocumentRequest);
		}
	}

}