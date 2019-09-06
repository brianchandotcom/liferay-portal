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

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.metrics.internal.petra.executor.WorkflowMetricsPortalExecutor;
import com.liferay.portal.workflow.metrics.internal.search.index.ProcessWorkflowMetricsIndexer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoDefinitionModelListener
	extends BaseModelListener<KaleoDefinition> {

	@Override
	public void onAfterCreate(KaleoDefinition kaleoDefinition)
		throws ModelListenerException {

		_workflowMetricsPortalExecutor.execute(
			() -> _processWorkflowMetricsIndexer.addDocument(
				_processWorkflowMetricsIndexer.createDocument(
					kaleoDefinition)));
	}

	@Override
	public void onAfterUpdate(KaleoDefinition kaleoDefinition)
		throws ModelListenerException {

		_workflowMetricsPortalExecutor.execute(
			() -> _processWorkflowMetricsIndexer.updateDocument(
				_processWorkflowMetricsIndexer.createDocument(
					kaleoDefinition)));
	}

	@Override
	public void onBeforeRemove(KaleoDefinition kaleoDefinition)
		throws ModelListenerException {

		_workflowMetricsPortalExecutor.execute(
			() -> _processWorkflowMetricsIndexer.deleteDocument(
				_processWorkflowMetricsIndexer.createDocument(
					kaleoDefinition)));
	}

	@Reference
	private ProcessWorkflowMetricsIndexer _processWorkflowMetricsIndexer;

	@Reference
	private WorkflowMetricsPortalExecutor _workflowMetricsPortalExecutor;

}