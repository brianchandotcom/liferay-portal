/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.manager;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowException;

import java.util.List;

/**
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 */
public interface WorkflowDefinitionManager {

	public default WorkflowDefinition deployWorkflowDefinition(
			byte[] bytes, long companyId, String externalReferenceCode,
			long groupId, String name, String scope, String title, long userId)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public default WorkflowDefinition deployWorkflowDefinition(
			String externalReferenceCode, long companyId, long userId,
			String title, String name, byte[] bytes)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public List<WorkflowDefinition> getActiveWorkflowDefinitions(
			int end, int start)
		throws WorkflowException;

	public List<WorkflowDefinition> getActiveWorkflowDefinitions(
			long companyId, String name, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException;

	public default int getActiveWorkflowDefinitionsCount(long companyId)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public default WorkflowDefinition getLatestWorkflowDefinition(
			long companyId, String name)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public default List<WorkflowDefinition> getLatestWorkflowDefinitions(
			Boolean active, long companyId, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator,
			String scope, int start, long userId)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public default int getLatestWorkflowDefinitionsCount(
			Boolean active, long companyId)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public default WorkflowDefinition getWorkflowDefinition(
			long workflowDefinitionId)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	public default WorkflowDefinition getWorkflowDefinition(
			long companyId, String externalReferenceCode)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	public WorkflowDefinition getWorkflowDefinition(
			long companyId, String name, int version)
		throws PortalException;

	public default int getWorkflowDefinitionsCount(long companyId, String name)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public default List<WorkflowDefinition> liberalGetActiveWorkflowDefinitions(
			long companyId, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator, int start)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public default WorkflowDefinition liberalGetLatestWorkflowDefinition(
			long companyId, String name)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public default List<WorkflowDefinition> liberalGetLatestWorkflowDefinitions(
			long companyId, String scope, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public default WorkflowDefinition liberalGetWorkflowDefinition(
			long companyId, String name, int version)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	public default List<WorkflowDefinition> liberalGetWorkflowDefinitions(
			long companyId, String name, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public default WorkflowDefinition saveWorkflowDefinition(
			byte[] bytes, long companyId, String externalReferenceCode,
			long groupId, String name, String scope, String title, long userId)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public default WorkflowDefinition saveWorkflowDefinition(
			String externalReferenceCode, long companyId, long userId,
			String title, String name, byte[] bytes)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public void undeployWorkflowDefinition(
			long companyId, long userId, String name, int version)
		throws WorkflowException;

	public WorkflowDefinition updateActive(
			long companyId, long userId, String name, int version,
			boolean active)
		throws WorkflowException;

	public void validateWorkflowDefinition(byte[] bytes)
		throws WorkflowException;

}