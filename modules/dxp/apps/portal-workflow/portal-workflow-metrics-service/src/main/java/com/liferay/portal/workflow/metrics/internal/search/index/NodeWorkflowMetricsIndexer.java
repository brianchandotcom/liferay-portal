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
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalService;

import java.util.Date;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = NodeWorkflowMetricsIndexer.class)
public class NodeWorkflowMetricsIndexer extends BaseWorkflowMetricsIndexer {

	@Override
	public void addDocument(Document document) {
		if (searchEngineAdapter == null) {
			return;
		}

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		if (Objects.equals(
				GetterUtil.getString(document.get("type")),
				NodeType.TASK.name())) {

			bulkDocumentRequest.addBulkableDocumentRequest(
				new IndexDocumentRequest(
					_slaTaskResultWorkflowMetricsIndexer.getIndexName(),
					_creatWorkflowMetricsSLATaskResultDocument(
						GetterUtil.getLong(document.get("companyId")),
						GetterUtil.getLong(document.get("processId")),
						GetterUtil.getLong(document.get("nodeId")),
						GetterUtil.getString(document.get("name")))) {

					{
						setType(
							_slaTaskResultWorkflowMetricsIndexer.
								getIndexType());
					}
				});

			bulkDocumentRequest.addBulkableDocumentRequest(
				new IndexDocumentRequest(
					_tokenWorkflowMetricsIndexer.getIndexName(),
					_createWorkflowMetricsTokenDocument(
						GetterUtil.getLong(document.get("companyId")),
						GetterUtil.getLong(document.get("processId")),
						GetterUtil.getLong(document.get("nodeId")),
						GetterUtil.getString(document.get("name")),
						GetterUtil.getString(document.get("version")))) {

					{
						setType(_tokenWorkflowMetricsIndexer.getIndexType());
					}
				});
		}

		bulkDocumentRequest.addBulkableDocumentRequest(
			new IndexDocumentRequest(getIndexName(), document) {
				{
					setType(getIndexType());
				}
			});

		searchEngineAdapter.execute(bulkDocumentRequest);
	}

	public Document createDocument(KaleoNode kaleoNode) {
		return _createDocument(
			kaleoNode.getCompanyId(), kaleoNode.getCreateDate(),
			kaleoNode.isInitial(), kaleoNode.getKaleoDefinitionVersionId(),
			kaleoNode.getModifiedDate(), kaleoNode.getName(),
			kaleoNode.getKaleoNodeId(), kaleoNode.isTerminal(),
			kaleoNode.getType());
	}

	public Document createDocument(KaleoTask kaleoTask) {
		return _createDocument(
			kaleoTask.getCompanyId(), kaleoTask.getCreateDate(), false,
			kaleoTask.getKaleoDefinitionVersionId(),
			kaleoTask.getModifiedDate(), kaleoTask.getName(),
			kaleoTask.getKaleoTaskId(), false, NodeType.TASK.name());
	}

	@Override
	protected String getIndexName() {
		return "workflow-metrics-nodes";
	}

	@Override
	protected String getIndexType() {
		return "WorkflowMetricsNodeType";
	}

	@Override
	protected void populateIndex() throws PortalException {
		_populateIndexWithKaleoNode();
		_populateIndexWithKaleoTask();
	}

	private Document _createDocument(
		long companyId, Date createDate, boolean initial,
		long kaleoDefinitionVersionId, Date modifiedDate, String name,
		long nodeId, boolean terminal, String type) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsNode",
			digest(companyId, kaleoDefinitionVersionId, nodeId));
		document.addKeyword("companyId", companyId);
		document.addDateSortable("createDate", createDate);
		document.addKeyword("deleted", false);
		document.addKeyword("initial", initial);
		document.addDateSortable("modifiedDate", modifiedDate);
		document.addKeyword("name", name);
		document.addKeyword("nodeId", nodeId);

		KaleoDefinition kaleoDefinition = getKaleoDefinition(
			kaleoDefinitionVersionId);

		if (kaleoDefinition != null) {
			document.addKeyword(
				"processId", kaleoDefinition.getKaleoDefinitionId());
		}

		document.addKeyword("terminal", terminal);
		document.addKeyword("type", type);

		KaleoDefinitionVersion kaleoDefinitionVersion =
			getKaleoDefinitionVersion(kaleoDefinitionVersionId);

		if (kaleoDefinitionVersion != null) {
			document.addKeyword("version", kaleoDefinitionVersion.getVersion());
		}

		return document;
	}

	private Document _createWorkflowMetricsTokenDocument(
		long companyId, long processId, long taskId, String taskName,
		String version) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsToken", digest(companyId, processId, 0, taskId, 0));
		document.addKeyword("companyId", companyId);
		document.addKeyword("completed", false);
		document.addKeyword("deleted", false);
		document.addKeyword("instanceId", 0);
		document.addKeyword("processId", processId);
		document.addKeyword("taskId", taskId);
		document.addKeyword("taskName", taskName);
		document.addKeyword("tokenId", 0);
		document.addKeyword("version", version);

		return document;
	}

	private Document _creatWorkflowMetricsSLATaskResultDocument(
		long companyId, long processId, long taskId, String taskName) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsSLATaskResult",
			digest(companyId, 0, processId, 0, taskId));
		document.addKeyword("companyId", companyId);
		document.addKeyword("deleted", false);
		document.addKeyword("instanceId", 0);
		document.addKeyword("processId", processId);
		document.addKeyword("slaDefinitionId", 0);
		document.addKeyword("taskId", taskId);
		document.addKeyword("taskName", taskName);

		return document;
	}

	private void _populateIndexWithKaleoNode() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_kaleoNodeLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property typeProperty = PropertyFactoryUtil.forName("type");

				dynamicQuery.add(typeProperty.eq(NodeType.STATE.name()));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(KaleoNode kaleoNode) -> addDocument(createDocument(kaleoNode)));

		actionableDynamicQuery.performActions();
	}

	private void _populateIndexWithKaleoTask() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_kaleoTaskLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(KaleoTask kaleoTask) -> addDocument(createDocument(kaleoTask)));

		actionableDynamicQuery.performActions();
	}

	@Reference
	private KaleoNodeLocalService _kaleoNodeLocalService;

	@Reference
	private KaleoTaskLocalService _kaleoTaskLocalService;

	@Reference
	private SLATaskResultWorkflowMetricsIndexer
		_slaTaskResultWorkflowMetricsIndexer;

	@Reference
	private TokenWorkflowMetricsIndexer _tokenWorkflowMetricsIndexer;

}