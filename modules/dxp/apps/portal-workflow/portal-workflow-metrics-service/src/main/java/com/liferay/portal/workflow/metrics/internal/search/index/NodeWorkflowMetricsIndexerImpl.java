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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.metrics.search.index.NodeWorkflowMetricsIndexer;

import java.util.Date;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true,
	service = {
		NodeWorkflowMetricsIndexer.class, NodeWorkflowMetricsIndexerImpl.class
	}
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

		documentBuilder.setString(
			Field.UID, digest(companyId, nodeId)
		).setLong(
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
			"version", processVersion
		);

		Document document = documentBuilder.build();

		workflowMetricsPortalExecutor.execute(() -> addDocument(document));

		return document;
	}

	@Override
	public void deleteNode(long companyId, long nodeId) {
		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setString(Field.UID, digest(companyId, nodeId));

		workflowMetricsPortalExecutor.execute(
			() -> deleteDocument(documentBuilder));
	}

	@Override
	public String getIndexName() {
		return "workflow-metrics-nodes";
	}

	@Override
	public String getIndexType() {
		return "WorkflowMetricsNodeType";
	}

	@Override
	public void reindex(long companyId) throws PortalException {
		_reindexIndexWithKaleoNode(companyId);
		_reindexIndexWithKaleoTask(companyId);
	}

	@Override
	protected void addDocument(Document document) {
		super.addDocument(document);

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		if (Objects.equals(document.getString("type"), "TASK")) {
			bulkDocumentRequest.addBulkableDocumentRequest(
				new IndexDocumentRequest(
					_slaTaskResultWorkflowMetricsIndexer.getIndexName(),
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
					_taskWorkflowMetricsIndexerImpl.getIndexName(),
					_createWorkflowMetricsTaskDocument(
						document.getLong("companyId"),
						document.getLong("processId"),
						document.getLong("nodeId"), document.getString("name"),
						document.getString("version"))) {

					{
						setType(_taskWorkflowMetricsIndexerImpl.getIndexType());
					}
				});
		}

		bulkDocumentRequest.addBulkableDocumentRequest(
			new IndexDocumentRequest(getIndexName(), document) {
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

		documentBuilder.setString(
			Field.UID, digest(companyId, processId, processVersion, nodeId)
		).setLong(
			"companyId", companyId
		).setValue(
			"completed", false
		).setValue(
			"deleted", false
		).setLong(
			"instanceId", 0L
		).setValue(
			"instanceCompleted", false
		).setLong(
			"processId", processId
		).setLong(
			"nodeId", nodeId
		).setString(
			"name", name
		).setLong(
			"taskId", 0L
		).setString(
			"version", processVersion
		);

		return documentBuilder.build();
	}

	private void _reindexIndexWithKaleoNode(long companyId)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			kaleoNodeLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property companyIdProperty = PropertyFactoryUtil.forName(
					"companyId");

				dynamicQuery.add(companyIdProperty.eq(companyId));

				Property typeProperty = PropertyFactoryUtil.forName("type");

				dynamicQuery.add(typeProperty.eq(NodeType.STATE.name()));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(KaleoNode kaleoNode) -> workflowMetricsPortalExecutor.execute(
				() -> {
					KaleoDefinitionVersion kaleoDefinitionVersion =
						getKaleoDefinitionVersion(
							kaleoNode.getKaleoDefinitionVersionId());

					if (Objects.isNull(kaleoDefinitionVersion)) {
						return;
					}

					addNode(
						kaleoNode.getCompanyId(), kaleoNode.getCreateDate(),
						kaleoNode.isInitial(), kaleoNode.getModifiedDate(),
						kaleoNode.getName(), kaleoNode.getKaleoNodeId(),
						kaleoNode.getKaleoDefinitionId(),
						kaleoDefinitionVersion.getVersion(),
						kaleoNode.isTerminal(), kaleoNode.getType());
				}));

		actionableDynamicQuery.performActions();
	}

	private void _reindexIndexWithKaleoTask(long companyId)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			kaleoTaskLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property companyIdProperty = PropertyFactoryUtil.forName(
					"companyId");

				dynamicQuery.add(companyIdProperty.eq(companyId));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(KaleoTask kaleoTask) -> workflowMetricsPortalExecutor.execute(
				() -> {
					KaleoDefinitionVersion kaleoDefinitionVersion =
						getKaleoDefinitionVersion(
							kaleoTask.getKaleoDefinitionVersionId());

					if (Objects.isNull(kaleoDefinitionVersion)) {
						return;
					}

					addNode(
						kaleoTask.getCompanyId(), kaleoTask.getCreateDate(),
						false, kaleoTask.getModifiedDate(), kaleoTask.getName(),
						kaleoTask.getKaleoTaskId(),
						kaleoTask.getKaleoDefinitionId(),
						kaleoDefinitionVersion.getVersion(), false,
						NodeType.TASK.name());
				}));

		actionableDynamicQuery.performActions();
	}

	@Reference
	private SLATaskResultWorkflowMetricsIndexer
		_slaTaskResultWorkflowMetricsIndexer;

	@Reference
	private TaskWorkflowMetricsIndexerImpl _taskWorkflowMetricsIndexerImpl;

}