/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.util;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;

import java.util.List;

/**
 * @author Pedro Leite
 */
public class WorkflowDefinitionManagerDisplayContextUtil {

	public static List<WorkflowDefinition> getActiveWorkflowDefinitions(
			long companyId, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return KaleoDefinitionResourcePermissionThreadLocal.
			getWithoutKaleoDefinitionResourcePermission(
				() -> workflowDefinitionManager.getActiveWorkflowDefinitions(
					companyId, start, end, orderByComparator));
	}

	public static WorkflowDefinition getLatestWorkflowDefinition(
			long companyId, String name)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return KaleoDefinitionResourcePermissionThreadLocal.
			getWithoutKaleoDefinitionResourcePermission(
				() -> workflowDefinitionManager.getLatestWorkflowDefinition(
					companyId, name));
	}

	public static List<WorkflowDefinition> getLatestWorkflowDefinitions(
			long companyId, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return KaleoDefinitionResourcePermissionThreadLocal.
			getWithoutKaleoDefinitionResourcePermission(
				() -> workflowDefinitionManager.getLatestWorkflowDefinitions(
					companyId, start, end, orderByComparator));
	}

	public static WorkflowDefinition getWorkflowDefinition(
			long companyId, String name, int version)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return KaleoDefinitionResourcePermissionThreadLocal.
			getWithoutKaleoDefinitionResourcePermission(
				() -> workflowDefinitionManager.getWorkflowDefinition(
					companyId, name, version));
	}

	public static List<WorkflowDefinition> getWorkflowDefinitions(
			long companyId, String name, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return KaleoDefinitionResourcePermissionThreadLocal.
			getWithoutKaleoDefinitionResourcePermission(
				() -> workflowDefinitionManager.getWorkflowDefinitions(
					companyId, name, start, end, orderByComparator));
	}

	private static final Snapshot<WorkflowDefinitionManager>
		_workflowDefinitionManagerSnapshot = new Snapshot<>(
			WorkflowDefinitionManagerDisplayContextUtil.class,
			WorkflowDefinitionManager.class);

}