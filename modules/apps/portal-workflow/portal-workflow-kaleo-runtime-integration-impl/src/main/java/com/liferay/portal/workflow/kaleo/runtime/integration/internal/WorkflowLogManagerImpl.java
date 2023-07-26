/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.integration.internal;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowLog;
import com.liferay.portal.workflow.kaleo.KaleoWorkflowModelConverter;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.liferay.portal.workflow.kaleo.runtime.util.comparator.KaleoLogOrderByComparator;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalService;
import com.liferay.portal.workflow.manager.WorkflowLogManager;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = WorkflowLogManager.class)
public class WorkflowLogManagerImpl implements WorkflowLogManager {

	@Override
	public int getWorkflowLogCountByWorkflowInstance(
			long companyId, long workflowInstanceId, List<Integer> logTypes)
		throws WorkflowException {

		try {
			return _kaleoLogLocalService.getKaleoInstanceKaleoLogsCount(
				companyId, workflowInstanceId, logTypes);
		}
		catch (Exception exception) {
			throw new WorkflowException(exception);
		}
	}

	@Override
	public int getWorkflowLogCountByWorkflowTask(
			long companyId, long workflowTaskId, List<Integer> logTypes)
		throws WorkflowException {

		try {
			return _kaleoLogLocalService.
				getKaleoTaskInstanceTokenKaleoLogsCount(
					companyId, workflowTaskId, logTypes);
		}
		catch (Exception exception) {
			throw new WorkflowException(exception);
		}
	}

	@Override
	public List<WorkflowLog> getWorkflowLogsByWorkflowInstance(
			long companyId, long workflowInstanceId, List<Integer> logTypes,
			int start, int end,
			OrderByComparator<WorkflowLog> orderByComparator)
		throws WorkflowException {

		try {
			List<KaleoLog> kaleoLogs =
				_kaleoLogLocalService.getKaleoInstanceKaleoLogs(
					companyId, workflowInstanceId, logTypes, start, end,
					KaleoLogOrderByComparator.getOrderByComparator(
						orderByComparator, _kaleoWorkflowModelConverter));

			return _toWorkflowLogs(kaleoLogs);
		}
		catch (Exception exception) {
			throw new WorkflowException(exception);
		}
	}

	@Override
	public List<WorkflowLog> getWorkflowLogsByWorkflowTask(
			long companyId, long workflowTaskId, List<Integer> logTypes,
			int start, int end,
			OrderByComparator<WorkflowLog> orderByComparator)
		throws WorkflowException {

		try {
			List<KaleoLog> kaleoLogs =
				_kaleoLogLocalService.getKaleoTaskInstanceTokenKaleoLogs(
					companyId, workflowTaskId, logTypes, start, end,
					KaleoLogOrderByComparator.getOrderByComparator(
						orderByComparator, _kaleoWorkflowModelConverter));

			return _toWorkflowLogs(kaleoLogs);
		}
		catch (Exception exception) {
			throw new WorkflowException(exception);
		}
	}

	private List<WorkflowLog> _toWorkflowLogs(List<KaleoLog> kaleoLogs) {
		List<WorkflowLog> workflowLogs = new ArrayList<>(kaleoLogs.size());

		for (KaleoLog kaleoLog : kaleoLogs) {
			workflowLogs.add(
				_kaleoWorkflowModelConverter.toWorkflowLog(kaleoLog));
		}

		return workflowLogs;
	}

	@Reference
	private KaleoLogLocalService _kaleoLogLocalService;

	@Reference
	private KaleoWorkflowModelConverter _kaleoWorkflowModelConverter;

}