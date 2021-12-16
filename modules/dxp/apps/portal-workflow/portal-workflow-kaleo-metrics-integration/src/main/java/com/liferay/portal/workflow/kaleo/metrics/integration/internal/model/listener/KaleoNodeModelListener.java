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

package com.liferay.portal.workflow.kaleo.metrics.integration.internal.model.listener;

import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.metrics.integration.internal.helper.IndexerHelper;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.metrics.model.DeleteNodeRequest;
import com.liferay.portal.workflow.metrics.search.index.NodeWorkflowMetricsIndexer;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoNodeModelListener extends BaseKaleoModelListener<KaleoNode> {

	@Override
	public void onAfterCreate(KaleoNode kaleoNode) {
		if (!Objects.equals(kaleoNode.getType(), NodeType.STATE.name())) {
			return;
		}

		KaleoDefinitionVersion kaleoDefinitionVersion =
			getKaleoDefinitionVersion(kaleoNode.getKaleoDefinitionVersionId());

		if (Objects.isNull(kaleoDefinitionVersion)) {
			return;
		}

		_nodeWorkflowMetricsIndexer.addNode(
			_indexerHelper.createAddNodeRequest(
				kaleoDefinitionVersion, kaleoNode));
	}

	@Override
	public void onAfterRemove(KaleoNode kaleoNode) {
		if (!Objects.equals(kaleoNode.getType(), NodeType.STATE.name())) {
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

}