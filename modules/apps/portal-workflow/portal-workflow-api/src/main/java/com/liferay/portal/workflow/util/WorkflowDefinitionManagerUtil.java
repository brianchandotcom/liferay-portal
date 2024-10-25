/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;
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
			String externalReferenceCode, long companyId, long userId,
			String title, String name, byte[] bytes)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return workflowDefinitionManager.deployWorkflowDefinition(
			externalReferenceCode, companyId, userId, title, name, bytes);
	}

	public static int getActiveWorkflowDefinitionsCount(long companyId)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return workflowDefinitionManager.getActiveWorkflowDefinitionsCount(
			companyId);
	}

	public static int getWorkflowDefinitionsCount(long companyId, String name)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return workflowDefinitionManager.getWorkflowDefinitionsCount(
			companyId, name);
	}

	public static List<WorkflowDefinition> liberalGetActiveWorkflowDefinitions(
			long companyId, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return workflowDefinitionManager.liberalGetActiveWorkflowDefinitions(
			companyId, start, end, orderByComparator);
	}

	public static WorkflowDefinition liberalGetLatestWorkflowDefinition(
			long companyId, String name)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return workflowDefinitionManager.liberalGetLatestWorkflowDefinition(
			companyId, name);
	}

	public static List<WorkflowDefinition> liberalGetLatestWorkflowDefinitions(
			long companyId, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return workflowDefinitionManager.liberalGetLatestWorkflowDefinitions(
			companyId, start, end, orderByComparator);
	}

	public static WorkflowDefinition liberalGetWorkflowDefinition(
			long companyId, String name, int version)
		throws PortalException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return workflowDefinitionManager.liberalGetWorkflowDefinition(
			companyId, name, version);
	}

	public static List<WorkflowDefinition> liberalGetWorkflowDefinitions(
			long companyId, String name, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return workflowDefinitionManager.liberalGetWorkflowDefinitions(
			companyId, name, start, end, orderByComparator);
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
			String externalReferenceCode, long companyId, long userId,
			String title, String name, byte[] bytes)
		throws WorkflowException {

		WorkflowDefinitionManager workflowDefinitionManager =
			_workflowDefinitionManagerSnapshot.get();

		return workflowDefinitionManager.saveWorkflowDefinition(
			externalReferenceCode, companyId, userId, title, name, bytes);
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