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

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.workflow.metrics.internal.sla.processor.WorkflowMetricsSLATaskResult;
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import java.sql.Timestamp;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true, service = SLATaskResultWorkflowMetricsIndexer.class
)
public class SLATaskResultWorkflowMetricsIndexer
	extends BaseSLAWorkflowMetricsIndexer {

	public Document createDocument(
		WorkflowMetricsSLATaskResult workflowMetricsSLATaskResult) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsSLATaskResult",
			digest(
				workflowMetricsSLATaskResult.getCompanyId(),
				workflowMetricsSLATaskResult.getInstanceId(),
				workflowMetricsSLATaskResult.getProcessId(),
				workflowMetricsSLATaskResult.getSLADefinitionId(),
				workflowMetricsSLATaskResult.getTaskId(),
				workflowMetricsSLATaskResult.getTokenId()));

		if (workflowMetricsSLATaskResult.getAssigneeId() != null) {
			document.addKeyword(
				"assigneeId", workflowMetricsSLATaskResult.getAssigneeId());
		}

		document.addKeyword(
			"breached", workflowMetricsSLATaskResult.isBreached());
		document.addKeyword(
			"companyId", workflowMetricsSLATaskResult.getCompanyId());

		if (workflowMetricsSLATaskResult.getCompletionLocalDateTime() != null) {
			document.addDateSortable(
				"completionDate",
				Timestamp.valueOf(
					workflowMetricsSLATaskResult.getCompletionLocalDateTime()));
		}

		if (workflowMetricsSLATaskResult.getCompletionUserId() != null) {
			document.addKeyword(
				"completionUserId",
				workflowMetricsSLATaskResult.getCompletionUserId());
		}

		document.addKeyword("deleted", false);
		document.addKeyword(
			"instanceCompleted",
			workflowMetricsSLATaskResult.isInstanceCompleted());
		document.addKeyword(
			"instanceId", workflowMetricsSLATaskResult.getInstanceId());

		if (workflowMetricsSLATaskResult.getLastCheckLocalDateTime() != null) {
			document.addDateSortable(
				"lastCheckDate",
				Timestamp.valueOf(
					workflowMetricsSLATaskResult.getLastCheckLocalDateTime()));
		}

		document.addKeyword("onTime", workflowMetricsSLATaskResult.isOnTime());
		document.addKeyword(
			"processId", workflowMetricsSLATaskResult.getProcessId());
		document.addKeyword(
			"slaDefinitionId",
			workflowMetricsSLATaskResult.getSLADefinitionId());

		WorkflowMetricsSLAStatus workflowMetricsSLAStatus =
			workflowMetricsSLATaskResult.getWorkflowMetricsSLAStatus();

		if (workflowMetricsSLAStatus != null) {
			document.addKeyword("status", workflowMetricsSLAStatus.name());
		}

		document.addKeyword("taskId", workflowMetricsSLATaskResult.getTaskId());
		document.addKeyword(
			"taskName", workflowMetricsSLATaskResult.getTaskName());
		document.addKeyword(
			"tokenId", workflowMetricsSLATaskResult.getTokenId());

		return document;
	}

	@Override
	public String getIndexName() {
		return "workflow-metrics-sla-task-results";
	}

	@Override
	public String getIndexType() {
		return "WorkflowMetricsSLATaskResultType";
	}

	@Override
	public void reindex(long companyId) {
		_creatDefaultDocuments(companyId);
	}

	protected Document creatDefaultDocument(
		long companyId, long processId, long taskId, String taskName) {

		WorkflowMetricsSLATaskResult workflowMetricsSLATaskResult =
			new WorkflowMetricsSLATaskResult();

		workflowMetricsSLATaskResult.setCompanyId(companyId);
		workflowMetricsSLATaskResult.setProcessId(processId);
		workflowMetricsSLATaskResult.setTaskId(taskId);
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
				companyId, document.getLong("processId"),
				document.getLong("nodeId"), document.getString("name"))
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