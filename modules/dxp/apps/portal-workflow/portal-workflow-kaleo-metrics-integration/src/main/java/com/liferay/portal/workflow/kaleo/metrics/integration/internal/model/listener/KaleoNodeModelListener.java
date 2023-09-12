/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.metrics.integration.internal.model.listener;

import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.metrics.integration.internal.helper.IndexerHelper;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.metrics.model.DeleteNodeRequest;
import com.liferay.portal.workflow.metrics.search.index.NodeWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.search.index.WorkflowMetricsIndex;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(service = ModelListener.class)
public class KaleoNodeModelListener extends BaseKaleoModelListener<KaleoNode> {

	@Override
	public void onAfterCreate(KaleoNode kaleoNode) {
		KaleoDefinitionVersion kaleoDefinitionVersion =
			getKaleoDefinitionVersion(kaleoNode.getKaleoDefinitionVersionId());

		if (!Objects.equals(kaleoNode.getType(), NodeType.STATE.name()) ||
			Objects.isNull(kaleoDefinitionVersion) ||
			!_workflowMetricsIndex.exists(kaleoNode.getCompanyId())) {

			return;
		}

		_nodeWorkflowMetricsIndexer.addNode(
			_indexerHelper.createAddNodeRequest(
				kaleoDefinitionVersion, kaleoNode));
	}

	@Override
	public void onAfterRemove(KaleoNode kaleoNode) {
		if (!Objects.equals(kaleoNode.getType(), NodeType.STATE.name()) ||
			!_workflowMetricsIndex.exists(kaleoNode.getCompanyId())) {

			return;
		}

		DeleteNodeRequest.Builder builder = new DeleteNodeRequest.Builder();

		_nodeWorkflowMetricsIndexer.deleteNode(
			builder.companyId(
				kaleoNode.getCompanyId()
			).nodeId(
				kaleoNode.getKaleoNodeId()
			).build());
	}

	@Reference
	private IndexerHelper _indexerHelper;

	@Reference
	private NodeWorkflowMetricsIndexer _nodeWorkflowMetricsIndexer;

	@Reference(target = "(workflow.metrics.index.entity.name=node)")
	private WorkflowMetricsIndex _workflowMetricsIndex;

}