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
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.metrics.internal.petra.executor.WorkflowMetricsPortalExecutor;
import com.liferay.portal.workflow.metrics.internal.search.index.InstanceWorkflowMetricsIndexer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoInstanceModelListener
	extends BaseModelListener<KaleoInstance> {

	@Override
	public void onAfterCreate(KaleoInstance kaleoInstance) {
		_workflowMetricsPortalExecutor.execute(
			() -> _instanceWorkflowMetricsIndexer.addDocument(
				_instanceWorkflowMetricsIndexer.createDocument(kaleoInstance)));
	}

	@Override
	public void onAfterRemove(KaleoInstance kaleoInstance) {
		_workflowMetricsPortalExecutor.execute(
			() -> _instanceWorkflowMetricsIndexer.deleteDocument(
				_instanceWorkflowMetricsIndexer.createDocument(kaleoInstance)));
	}

	@Override
	public void onAfterUpdate(KaleoInstance kaleoInstance) {
		_workflowMetricsPortalExecutor.execute(
			() -> _instanceWorkflowMetricsIndexer.updateDocument(
				_instanceWorkflowMetricsIndexer.createDocument(kaleoInstance)));
	}

	@Reference
	private InstanceWorkflowMetricsIndexer _instanceWorkflowMetricsIndexer;

	@Reference
	private WorkflowMetricsPortalExecutor _workflowMetricsPortalExecutor;

}