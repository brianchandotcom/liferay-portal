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

package com.liferay.portal.workflow.metrics.internal.model.listener;

import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.metrics.internal.petra.executor.WorkflowMetricsPortalExecutor;
import com.liferay.portal.workflow.metrics.internal.search.index.NodeWorkflowMetricsIndexer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoNodeModelListener extends BaseModelListener<KaleoNode> {

	@Override
	public void onAfterCreate(KaleoNode kaleoNode) {
		_workflowMetricsPortalExecutor.execute(
			() -> _nodeWorkflowMetricsIndexer.addDocument(kaleoNode));
	}

	@Override
	public void onAfterRemove(KaleoNode kaleoNode) {
		_workflowMetricsPortalExecutor.execute(
			() -> _nodeWorkflowMetricsIndexer.deleteDocument(kaleoNode));
	}

	@Reference
	private NodeWorkflowMetricsIndexer _nodeWorkflowMetricsIndexer;

	@Reference
	private WorkflowMetricsPortalExecutor _workflowMetricsPortalExecutor;

}