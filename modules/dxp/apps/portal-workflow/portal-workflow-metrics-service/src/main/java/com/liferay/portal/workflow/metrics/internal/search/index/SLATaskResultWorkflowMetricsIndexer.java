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
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.workflow.metrics.internal.sla.processor.WorkflowMetricsSLAProcessResult;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true, service = SLATaskResultWorkflowMetricsIndexer.class
)
public class SLATaskResultWorkflowMetricsIndexer
	extends SLAProcessResultWorkflowMetricsIndexer {

	public Document createDocument(
		long taskId, String taskName,
		WorkflowMetricsSLAProcessResult workflowMetricsSLAProcessResult) {

		Document document = createDocument(workflowMetricsSLAProcessResult);

		document.addUID(
			"WorkflowMetricsSLATaskResult",
			digest(
				workflowMetricsSLAProcessResult.getCompanyId(),
				workflowMetricsSLAProcessResult.getInstanceId(),
				workflowMetricsSLAProcessResult.getProcessId(),
				workflowMetricsSLAProcessResult.getSLADefinitionId(), taskId));
		document.addKeyword("taskId", taskId);
		document.addKeyword("taskName", taskName);

		return document;
	}

	public void updateDocuments(
		Map<Long, String> taskNames,
		WorkflowMetricsSLAProcessResult workflowMetricsSLAProcessResult) {

		if (searchEngineAdapter == null) {
			return;
		}

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(getIndexName());

		BooleanQuery booleanQuery = queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addMustQueryClauses(
				queries.term(
					"companyId",
					workflowMetricsSLAProcessResult.getCompanyId()),
				queries.term(
					"instanceId",
					workflowMetricsSLAProcessResult.getInstanceId()),
				queries.term(
					"processId",
					workflowMetricsSLAProcessResult.getProcessId()),
				queries.term(
					"slaDefinitionId",
					workflowMetricsSLAProcessResult.getSLADefinitionId())));

		searchSearchRequest.setSelectedFieldNames(Field.UID);

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		Stream.of(
			searchEngineAdapter.execute(searchSearchRequest)
		).map(
			SearchSearchResponse::getSearchHits
		).map(
			SearchHits::getSearchHits
		).flatMap(
			List::stream
		).map(
			SearchHit::getDocument
		).map(
			document -> new UpdateDocumentRequest(
				getIndexName(), document.getString(Field.UID),
				new DocumentImpl() {
					{
						addKeyword("deleted", true);
						addKeyword(Field.UID, document.getString(Field.UID));
					}
				}) {

				{
					setType(getIndexType());
				}
			}
		).forEach(
			bulkDocumentRequest::addBulkableDocumentRequest
		);

		taskNames.forEach(
			(taskId, taskName) ->
				bulkDocumentRequest.addBulkableDocumentRequest(
					new IndexDocumentRequest(
						getIndexName(),
						createDocument(
							taskId, taskName,
							workflowMetricsSLAProcessResult)) {

						{
							setType(getIndexType());
						}
					}));

		searchEngineAdapter.execute(bulkDocumentRequest);
	}

	@Override
	protected String getIndexName() {
		return "workflow-metrics-sla-task-results";
	}

	@Override
	protected String getIndexType() {
		return "WorkflowMetricsSLATaskResultType";
	}

}