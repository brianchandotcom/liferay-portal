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
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.workflow.metrics.internal.search.index.util.WorkflowMetricsIndexerUtil;
import com.liferay.portal.workflow.metrics.model.AddNodeRequest;
import com.liferay.portal.workflow.metrics.model.DeleteNodeRequest;
import com.liferay.portal.workflow.metrics.search.index.NodeWorkflowMetricsIndexer;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = NodeWorkflowMetricsIndexer.class)
public class NodeWorkflowMetricsIndexerImpl
	extends BaseWorkflowMetricsIndexer implements NodeWorkflowMetricsIndexer {

	@Override
	public Document addNode(AddNodeRequest addNodeRequest) {
		if (searchEngineAdapter == null) {
			return null;
		}

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		Document document = documentBuilder.setLong(
			"companyId", addNodeRequest.getCompanyId()
		).setDate(
			"createDate", getDate(addNodeRequest.getCreateDate())
		).setValue(
			"deleted", false
		).setValue(
			"initial", addNodeRequest.getInitial()
		).setDate(
			"modifiedDate", getDate(addNodeRequest.getModifiedDate())
		).setString(
			"name", addNodeRequest.getName()
		).setString(
			Field.getSortableFieldName("name"),
			StringUtil.toLowerCase(addNodeRequest.getName())
		).setLong(
			"nodeId", addNodeRequest.getNodeId()
		).setLong(
			"processId", addNodeRequest.getProcessId()
		).setValue(
			"terminal", addNodeRequest.getTerminal()
		).setString(
			"type", addNodeRequest.getType()
		).setString(
			"uid",
			digest(addNodeRequest.getCompanyId(), addNodeRequest.getNodeId())
		).setString(
			"version", addNodeRequest.getProcessVersion()
		).build();

		workflowMetricsPortalExecutor.execute(() -> addDocument(document));

		return document;
	}

	@Override
	public void deleteNode(DeleteNodeRequest deleteNodeRequest) {
		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setLong(
			"companyId", deleteNodeRequest.getCompanyId()
		).setLong(
			"nodeId", deleteNodeRequest.getNodeId()
		).setString(
			"uid",
			digest(
				deleteNodeRequest.getCompanyId(), deleteNodeRequest.getNodeId())
		);

		workflowMetricsPortalExecutor.execute(
			() -> deleteDocument(documentBuilder));
	}

	@Override
	public String getIndexName(long companyId) {
		return _nodeWorkflowMetricsIndex.getIndexName(companyId);
	}

	@Override
	public String getIndexType() {
		return _nodeWorkflowMetricsIndex.getIndexType();
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
				_nodeWorkflowMetricsIndex.getIndexName(
					document.getLong("companyId")),
				document) {

				{
					setType(_nodeWorkflowMetricsIndex.getIndexType());
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
		).setString(
			Field.getSortableFieldName("name"), StringUtil.toLowerCase(name)
		).setLong(
			"nodeId", nodeId
		).setLong(
			"processId", processId
		).setLong(
			"taskId", 0L
		).setString(
			"uid",
			WorkflowMetricsIndexerUtil.digest(
				_taskWorkflowMetricsIndex.getIndexType(), companyId, processId,
				processVersion, nodeId)
		).setString(
			"version", processVersion
		);

		return documentBuilder.build();
	}

	@Reference(target = "(workflow.metrics.index.entity.name=node)")
	private WorkflowMetricsIndex _nodeWorkflowMetricsIndex;

	@Reference
	private SLATaskResultWorkflowMetricsIndexer
		_slaTaskResultWorkflowMetricsIndexer;

	@Reference(target = "(workflow.metrics.index.entity.name=task)")
	private WorkflowMetricsIndex _taskWorkflowMetricsIndex;

}