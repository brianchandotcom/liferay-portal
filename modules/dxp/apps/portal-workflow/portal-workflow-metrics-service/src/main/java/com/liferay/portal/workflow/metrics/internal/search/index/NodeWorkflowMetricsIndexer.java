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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = NodeWorkflowMetricsIndexer.class)
public class NodeWorkflowMetricsIndexer extends BaseWorkflowMetricsIndexer {

	public Document createDocument(KaleoNode kaleoNode) {
		return _createDocument(
			kaleoNode.getCompanyId(), kaleoNode.getCreateDate(),
			kaleoNode.isInitial(), kaleoNode.getKaleoDefinitionVersionId(),
			kaleoNode.getModifiedDate(), kaleoNode.getName(),
			kaleoNode.getKaleoNodeId(), kaleoNode.getPrimaryKey(),
			kaleoNode.isTerminal(), kaleoNode.getType());
	}

	public Document createDocument(KaleoTask kaleoTask) {
		return _createDocument(
			kaleoTask.getCompanyId(), kaleoTask.getCreateDate(), false,
			kaleoTask.getKaleoDefinitionVersionId(),
			kaleoTask.getModifiedDate(), kaleoTask.getName(),
			kaleoTask.getKaleoTaskId(), kaleoTask.getPrimaryKey(), false,
			NodeType.TASK.name());
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
		long nodeId, long primaryKey, boolean terminal, String type) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsNode",
			digest(companyId, kaleoDefinitionVersionId, primaryKey));
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

		if (kaleoDefinition != null) {
			document.addKeyword(
				"version",
				StringBundler.concat(
					kaleoDefinition.getVersion(), CharPool.PERIOD, 0));
		}

		return document;
	}

	private void _populateIndexWithKaleoNode() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_kaleoNodeLocalService.getActionableDynamicQuery();

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

}