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
import com.liferay.portal.workflow.kaleo.model.KaleoTransition;
import com.liferay.portal.workflow.metrics.internal.petra.executor.WorkflowMetricsPortalExecutor;
import com.liferay.portal.workflow.metrics.internal.search.index.TransitionWorkflowMetricsIndexer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoTransitionModelListener
	extends BaseModelListener<KaleoTransition> {

	@Override
	public void onAfterCreate(KaleoTransition kaleoTransition) {
		_workflowMetricsPortalExecutor.execute(
			() -> _transitionWorkflowMetricsIndexer.addDocument(
				_transitionWorkflowMetricsIndexer.createDocument(
					kaleoTransition)));
	}

	@Override
	public void onAfterRemove(KaleoTransition kaleoTransition) {
		_workflowMetricsPortalExecutor.execute(
			() -> _transitionWorkflowMetricsIndexer.deleteDocument(
				_transitionWorkflowMetricsIndexer.createDocument(
					kaleoTransition)));
	}

	@Reference
	private TransitionWorkflowMetricsIndexer _transitionWorkflowMetricsIndexer;

	@Reference
	private WorkflowMetricsPortalExecutor _workflowMetricsPortalExecutor;

}