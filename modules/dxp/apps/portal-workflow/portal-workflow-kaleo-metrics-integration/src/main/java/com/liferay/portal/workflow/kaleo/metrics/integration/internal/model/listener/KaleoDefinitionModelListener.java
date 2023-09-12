/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.metrics.integration.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.workflow.kaleo.metrics.integration.internal.helper.IndexerHelper;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.metrics.search.index.ProcessWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.search.index.WorkflowMetricsIndex;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(service = ModelListener.class)
public class KaleoDefinitionModelListener
	extends BaseModelListener<KaleoDefinition> {

	@Override
	public void onAfterCreate(KaleoDefinition kaleoDefinition)
		throws ModelListenerException {

		if (!_workflowMetricsIndex.exists(kaleoDefinition.getCompanyId())) {
			return;
		}

		_processWorkflowMetricsIndexer.addProcess(
			_indexerHelper.createAddProcessRequest(0L, kaleoDefinition));
	}

	@Override
	public void onAfterUpdate(
			KaleoDefinition originalKaleoDefinition,
			KaleoDefinition kaleoDefinition)
		throws ModelListenerException {

		if (!_workflowMetricsIndex.exists(
				originalKaleoDefinition.getCompanyId())) {

			return;
		}

		_processWorkflowMetricsIndexer.updateProcess(
			_indexerHelper.createUpdateProcessRequest(kaleoDefinition));
	}

	@Override
	public void onBeforeRemove(KaleoDefinition kaleoDefinition)
		throws ModelListenerException {

		if (!_workflowMetricsIndex.exists(kaleoDefinition.getCompanyId())) {
			return;
		}

		_processWorkflowMetricsIndexer.deleteProcess(
			_indexerHelper.createDeleteProcessRequest(kaleoDefinition));
	}

	@Reference
	private IndexerHelper _indexerHelper;

	@Reference
	private ProcessWorkflowMetricsIndexer _processWorkflowMetricsIndexer;

	@Reference(target = "(workflow.metrics.index.entity.name=process)")
	private WorkflowMetricsIndex _workflowMetricsIndex;

}