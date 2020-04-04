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

import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.workflow.metrics.search.index.NodeWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;

import java.util.Date;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true, property = "workflow.metrics.index.entity.name=node",
	service = {NodeWorkflowMetricsIndexer.class, WorkflowMetricsIndex.class}
)
public class NodeWorkflowMetricsIndexerImpl
	extends BaseWorkflowMetricsIndexer implements NodeWorkflowMetricsIndexer {

	@Override
	public Document addNode(
		long companyId, Date createDate, boolean initial, Date modifiedDate,
		String name, long nodeId, long processId, String processVersion,
		boolean terminal, String type) {

		if (searchEngineAdapter == null) {
			return null;
		}

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setLong(
			"companyId", companyId
		).setDate(
			"createDate", formatDate(createDate)
		).setValue(
			"deleted", false
		).setValue(
			"initial", initial
		).setDate(
			"modifiedDate", formatDate(modifiedDate)
		).setString(
			"name", name
		).setLong(
			"nodeId", nodeId
		).setLong(
			"processId", processId
		).setValue(
			"terminal", terminal
		).setString(
			"type", type
		).setString(
			"uid", digest(companyId, nodeId)
		).setString(
			"version", processVersion
		);

		Document document = documentBuilder.build();

		workflowMetricsPortalExecutor.execute(() -> addDocument(document));

		return document;
	}

	@Override
	public void deleteNode(long companyId, long nodeId) {
		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setLong(
			"companyId", companyId
		).setLong(
			"nodeId", nodeId
		).setString(
			"uid", digest(companyId, nodeId)
		);

		workflowMetricsPortalExecutor.execute(
			() -> deleteDocument(documentBuilder));
	}

	@Override
	public String getIndexName(long companyId) {
		return _nodeWorkflowMetricsIndexNameBuilder.getIndexName(companyId);
	}

	@Override
	public String getIndexType() {
		return "WorkflowMetricsNodeType";
	}

	@Override
	protected void addDocument(Document document) {
		super.addDocument(document);

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		if (Objects.equals(document.getString("type"), "TASK")) {
			bulkDocumentRequest.addBulkableDocumentRequest(
				new IndexDocumentRequest(
					_slaTaskResultWorkflowMetricsIndexer.getIndexName(
						document.getLong("companyId")),
					_slaTaskResultWorkflowMetricsIndexer.creatDefaultDocument(
						document.getLong("companyId"),
						document.getLong("nodeId"),
						document.getLong("processId"),
						document.getString("name"))) {

					{
						setType(
							_slaTaskResultWorkflowMetricsIndexer.
								getIndexType());
					}
				});

			bulkDocumentRequest.addBulkableDocumentRequest(
				new IndexDocumentRequest(
					_taskWorkflowMetricsIndex.getIndexName(
						document.getLong("companyId")),
					_createWorkflowMetricsTaskDocument(
						document.getLong("companyId"),
						document.getLong("processId"),
						document.getLong("nodeId"), document.getString("name"),
						document.getString("version"))) {

					{
						setType(_taskWorkflowMetricsIndex.getIndexType());
					}
				});
		}

		bulkDocumentRequest.addBulkableDocumentRequest(
			new IndexDocumentRequest(
				getIndexName(document.getLong("companyId")), document) {

				{
					setType(getIndexType());
				}
			});

		if (PortalRunMode.isTestMode()) {
			bulkDocumentRequest.setRefresh(true);
		}

		searchEngineAdapter.execute(bulkDocumentRequest);
	}

	private Document _createWorkflowMetricsTaskDocument(
		long companyId, long processId, long nodeId, String name,
		String processVersion) {

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setLong(
			"companyId", companyId
		).setValue(
			"completed", false
		).setValue(
			"deleted", false
		).setValue(
			"instanceCompleted", false
		).setLong(
			"instanceId", 0L
		).setString(
			"name", name
		).setLong(
			"nodeId", nodeId
		).setLong(
			"processId", processId
		).setLong(
			"taskId", 0L
		).setString(
			"uid", digest(companyId, processId, processVersion, nodeId)
		).setString(
			"version", processVersion
		);

		return documentBuilder.build();
	}

	@Reference(target = "(workflow.metrics.index.entity.name=node)")
	private WorkflowMetricsIndexNameBuilder
		_nodeWorkflowMetricsIndexNameBuilder;

	@Reference
	private SLATaskResultWorkflowMetricsIndexer
		_slaTaskResultWorkflowMetricsIndexer;

	@Reference(target = "(workflow.metrics.index.entity.name=task)")
	private WorkflowMetricsIndex _taskWorkflowMetricsIndex;

}