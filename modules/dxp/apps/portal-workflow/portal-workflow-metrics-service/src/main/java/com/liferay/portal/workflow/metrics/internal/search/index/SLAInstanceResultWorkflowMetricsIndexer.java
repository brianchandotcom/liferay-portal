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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.Message;
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
import com.liferay.portal.workflow.metrics.internal.messaging.WorkflowMetricsSLAProcessMessageListener;
import com.liferay.portal.workflow.metrics.internal.sla.processor.WorkflowMetricsSLAInstanceResult;
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import java.sql.Timestamp;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true, service = SLAInstanceResultWorkflowMetricsIndexer.class
)
public class SLAInstanceResultWorkflowMetricsIndexer
	extends BaseSLAWorkflowMetricsIndexer {

	public Document createDocument(
		WorkflowMetricsSLAInstanceResult workflowMetricsSLAInstanceResult) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsSLAInstanceResult",
			digest(
				workflowMetricsSLAInstanceResult.getCompanyId(),
				workflowMetricsSLAInstanceResult.getInstanceId(),
				workflowMetricsSLAInstanceResult.getProcessId(),
				workflowMetricsSLAInstanceResult.getSLADefinitionId()));
		document.addKeyword(
			"companyId", workflowMetricsSLAInstanceResult.getCompanyId());

		if (workflowMetricsSLAInstanceResult.getCompletionLocalDateTime() !=
				null) {

			document.addDateSortable(
				"completionDate",
				Timestamp.valueOf(
					workflowMetricsSLAInstanceResult.
						getCompletionLocalDateTime()));
		}

		document.addKeyword("deleted", false);
		document.addKeyword(
			"elapsedTime", workflowMetricsSLAInstanceResult.getElapsedTime());
		document.addKeyword(
			"instanceCompleted",
			workflowMetricsSLAInstanceResult.getCompletionLocalDateTime() !=
				null);
		document.addKeyword(
			"instanceId", workflowMetricsSLAInstanceResult.getInstanceId());

		if (workflowMetricsSLAInstanceResult.getLastCheckLocalDateTime() !=
				null) {

			document.addDateSortable(
				"lastCheckDate",
				Timestamp.valueOf(
					workflowMetricsSLAInstanceResult.
						getLastCheckLocalDateTime()));
		}

		document.addKeyword(
			"onTime", workflowMetricsSLAInstanceResult.isOnTime());

		if (workflowMetricsSLAInstanceResult.getOverdueLocalDateTime() !=
				null) {

			document.addDateSortable(
				"overdueDate",
				Timestamp.valueOf(
					workflowMetricsSLAInstanceResult.
						getOverdueLocalDateTime()));
		}

		document.addKeyword(
			"processId", workflowMetricsSLAInstanceResult.getProcessId());
		document.addKeyword(
			"remainingTime",
			workflowMetricsSLAInstanceResult.getRemainingTime());
		document.addKeyword(
			"slaDefinitionId",
			workflowMetricsSLAInstanceResult.getSLADefinitionId());

		WorkflowMetricsSLAStatus workflowMetricsSLAStatus =
			workflowMetricsSLAInstanceResult.getWorkflowMetricsSLAStatus();

		if (workflowMetricsSLAStatus != null) {
			document.addKeyword("status", workflowMetricsSLAStatus.name());
		}

		return document;
	}

	@Override
	public String getIndexName() {
		return "workflow-metrics-sla-instance-results";
	}

	@Override
	public String getIndexType() {
		return "WorkflowMetricsSLAInstanceResultType";
	}

	@Override
	public void reindex(long companyId) throws PortalException {
		_creatDefaultDocuments(companyId);

		if (workflowMetricsSLAProcessMessageListener == null) {
			return;
		}

		Message message = new Message();

		JSONObject payloadJSONObject = _jsonFactory.createJSONObject();

		payloadJSONObject.put("reindex", Boolean.TRUE);

		message.setPayload(payloadJSONObject);

		workflowMetricsSLAProcessMessageListener.receive(message);
	}

	protected Document creatDefaultDocument(long companyId, long processId) {
		WorkflowMetricsSLAInstanceResult workflowMetricsSLAInstanceResult =
			new WorkflowMetricsSLAInstanceResult();

		workflowMetricsSLAInstanceResult.setCompanyId(companyId);
		workflowMetricsSLAInstanceResult.setProcessId(processId);

		return createDocument(workflowMetricsSLAInstanceResult);
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected volatile WorkflowMetricsSLAProcessMessageListener
		workflowMetricsSLAProcessMessageListener;

	private void _creatDefaultDocuments(long companyId) {
		if ((searchEngineAdapter == null) ||
			!hasIndex("workflow-metrics-processes")) {

			return;
		}

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames("workflow-metrics-processes");

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
				companyId, document.getLong("processId"))
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

	@Reference
	private JSONFactory _jsonFactory;

}