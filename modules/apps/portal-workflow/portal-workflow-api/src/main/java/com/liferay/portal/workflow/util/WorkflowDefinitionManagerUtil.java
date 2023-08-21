/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.util;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;

import java.util.List;

/**
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 * @author Raymond Augé
 */
public class WorkflowDefinitionManagerUtil {

	public static WorkflowDefinition deployWorkflowDefinition(
			long companyId, long userId, String title, String name,
			byte[] bytes)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return workflowDefinitionManager.deployWorkflowDefinition(
			companyId, userId, title, name, bytes);
	}

	public static List<WorkflowDefinition> getActiveWorkflowDefinitions(
			long companyId, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return workflowDefinitionManager.getActiveWorkflowDefinitions(
			companyId, start, end, orderByComparator);
	}

	public static int getActiveWorkflowDefinitionsCount(long companyId)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return workflowDefinitionManager.getActiveWorkflowDefinitionsCount(
			companyId);
	}

	public static WorkflowDefinition getLatestWorkflowDefinition(
			long companyId, String name)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return workflowDefinitionManager.getLatestWorkflowDefinition(
			companyId, name);
	}

	public static List<WorkflowDefinition> getLatestWorkflowDefinitions(
			long companyId, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return workflowDefinitionManager.getLatestWorkflowDefinitions(
			companyId, start, end, orderByComparator);
	}

	public static WorkflowDefinition getWorkflowDefinition(
			long companyId, String name, int version)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return workflowDefinitionManager.getWorkflowDefinition(
			companyId, name, version);
	}

	public static List<WorkflowDefinition> getWorkflowDefinitions(
			long companyId, String name, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return workflowDefinitionManager.getWorkflowDefinitions(
			companyId, name, start, end, orderByComparator);
	}

	public static int getWorkflowDefinitionsCount(long companyId, String name)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return workflowDefinitionManager.getWorkflowDefinitionsCount(
			companyId, name);
	}

	/**
	 * Saves a workflow definition without activating it or validating its data.
	 * To save the definition, validate its data, and activate it, use {@link
	 * #deployWorkflowDefinition(long, long, String, String, byte[])} instead.
	 *
	 * @param  companyId the company ID of the workflow definition
	 * @param  userId the ID of the user saving the workflow definition
	 * @param  title the workflow definition's title
	 * @param  name the workflow definition's name
	 * @param  bytes the data saved as the workflow definition's content
	 * @return the workflow definition
	 * @throws WorkflowException if there was an issue saving the workflow
	 *         definition
	 */
	public static WorkflowDefinition saveWorkflowDefinition(
			long companyId, long userId, String title, String name,
			byte[] bytes)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return workflowDefinitionManager.saveWorkflowDefinition(
			companyId, userId, title, name, bytes);
	}

	public static WorkflowDefinition updateActive(
			long companyId, long userId, String name, int version,
			boolean active)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return workflowDefinitionManager.updateActive(
			companyId, userId, name, version, active);
	}

	private static final Snapshot<WorkflowDefinitionManager>
		_workflowDefinitionManagerSnapshot = new Snapshot<>(
			WorkflowDefinitionManagerUtil.class,
			WorkflowDefinitionManager.class);

}