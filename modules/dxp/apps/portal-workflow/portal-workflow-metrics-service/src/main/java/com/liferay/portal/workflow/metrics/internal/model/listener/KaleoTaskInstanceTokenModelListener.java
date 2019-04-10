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
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.metrics.internal.petra.executor.WorkflowMetricsPortalExecutor;
import com.liferay.portal.workflow.metrics.internal.search.index.TokenWorkflowMetricsIndexer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoTaskInstanceTokenModelListener
	extends BaseModelListener<KaleoTaskInstanceToken> {

	@Override
	public void onAfterCreate(KaleoTaskInstanceToken kaleoTaskInstanceToken) {
		_workflowMetricsPortalExecutor.execute(
			() -> _tokenWorkflowMetricsIndexer.addDocument(
				_tokenWorkflowMetricsIndexer.createDocument(
					kaleoTaskInstanceToken)));
	}

	@Override
	public void onAfterRemove(KaleoTaskInstanceToken kaleoTaskInstanceToken) {
		_workflowMetricsPortalExecutor.execute(
			() -> _tokenWorkflowMetricsIndexer.deleteDocument(
				_tokenWorkflowMetricsIndexer.createDocument(
					kaleoTaskInstanceToken)));
	}

	@Override
	public void onAfterUpdate(KaleoTaskInstanceToken kaleoTaskInstanceToken) {
		_workflowMetricsPortalExecutor.execute(
			() -> _tokenWorkflowMetricsIndexer.updateDocument(
				_tokenWorkflowMetricsIndexer.createDocument(
					kaleoTaskInstanceToken)));
	}

	@Reference
	private TokenWorkflowMetricsIndexer _tokenWorkflowMetricsIndexer;

	@Reference
	private WorkflowMetricsPortalExecutor _workflowMetricsPortalExecutor;

}