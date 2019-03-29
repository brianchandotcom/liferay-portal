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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskLocalService;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = NodeWorkflowMetricsIndexer.class)
public class NodeWorkflowMetricsIndexer
	extends BaseWorkflowMetricsIndexer<KaleoNode> {

	@Override
	protected Document createDocument(KaleoNode kaleoNode) {
		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsNode",
			digest(
				kaleoNode.getCompanyId(),
				kaleoNode.getKaleoDefinitionVersionId(),
				kaleoNode.getPrimaryKey()));
		document.addKeyword("companyId", kaleoNode.getCompanyId());
		document.addDateSortable("createDate", kaleoNode.getCreateDate());
		document.addKeyword("deleted", false);
		document.addKeyword("initial", kaleoNode.isInitial());
		document.addDateSortable("modifiedDate", kaleoNode.getModifiedDate());
		document.addKeyword("name", kaleoNode.getName());

		Long nodeId = getNodeId(kaleoNode);

		if (nodeId != null) {
			document.addKeyword("nodeId", nodeId);
		}

		Long kaleoDefinitionId = getKaleoDefinitionId(
			kaleoNode.getKaleoDefinitionVersionId());

		if (kaleoDefinitionId != null) {
			document.addKeyword("processId", kaleoDefinitionId);
		}

		document.addKeyword("terminal", kaleoNode.isTerminal());
		document.addKeyword("type", kaleoNode.getType());

		return document;
	}

	@Override
	protected String getIndexName() {
		return "workflow-metrics-nodes";
	}

	@Override
	protected String getIndexType() {
		return "WorkflowMetricsNodeType";
	}

	protected Long getNodeId(KaleoNode kaleoNode) {
		if (Objects.equals(kaleoNode.getType(), NodeType.TASK.name())) {
			try {
				KaleoTask kaleoTask =
					_kaleoTaskLocalService.getKaleoNodeKaleoTask(
						kaleoNode.getKaleoNodeId());

				return kaleoTask.getKaleoTaskId();
			}
			catch (Exception e) {
				return null;
			}
		}

		return kaleoNode.getKaleoNodeId();
	}

	@Override
	protected void populateIndex() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_kaleoNodeLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			(KaleoNode kaleoNode) -> addDocument(kaleoNode));

		actionableDynamicQuery.performActions();
	}

	@Reference
	private KaleoNodeLocalService _kaleoNodeLocalService;

	@Reference
	private KaleoTaskLocalService _kaleoTaskLocalService;

}