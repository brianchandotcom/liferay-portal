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
			String externalReferenceCode, long companyId, long userId,
			String title, String name, byte[] bytes)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public default WorkflowDefinition deployWorkflowDefinition(
			String externalReferenceCode, long companyId, long userId,
			String title, String name, String scope, byte[] bytes)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public List<WorkflowDefinition> getActiveWorkflowDefinitions(
			int start, int end)
		throws WorkflowException;

	public List<WorkflowDefinition> getActiveWorkflowDefinitions(
			long companyId, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
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
			Boolean active, long companyId, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public default List<WorkflowDefinition> getLatestWorkflowDefinitions(
			long companyId, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		return getLatestWorkflowDefinitions(
			null, companyId, start, end, orderByComparator);
	}

	public default int getLatestWorkflowDefinitionsCount(
			Boolean active, long companyId)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public default int getLatestWorkflowDefinitionsCount(long companyId)
		throws WorkflowException {

		return getLatestWorkflowDefinitionsCount(null, companyId);
	}

	public default WorkflowDefinition getWorkflowDefinition(
			long workflowDefinitionId)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	public WorkflowDefinition getWorkflowDefinition(
			long companyId, String name, int version)
		throws PortalException;

	public default WorkflowDefinition getWorkflowDefinition(
			String externalReferenceCode, long companyId)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	public List<WorkflowDefinition> getWorkflowDefinitions(
			long companyId, String name, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException;

	public default int getWorkflowDefinitionsCount(long companyId, String name)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public default List<WorkflowDefinition> liberalGetActiveWorkflowDefinitions(
			long companyId, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public default WorkflowDefinition liberalGetLatestWorkflowDefinition(
			long companyId, String name)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public default List<WorkflowDefinition> liberalGetLatestWorkflowDefinitions(
			long companyId, int start, int end,
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
	public default WorkflowDefinition saveWorkflowDefinition(
			String externalReferenceCode, long companyId, long userId,
			String title, String name, byte[] bytes)
		throws WorkflowException {

		throw new UnsupportedOperationException();
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
	 * @param  scope the workflow definition's scope
	 * @param  bytes the data saved as the workflow definition's content
	 * @return the workflow definition
	 * @throws WorkflowException if there was an issue saving the workflow
	 *         definition
	 */
	public default WorkflowDefinition saveWorkflowDefinition(
			String externalReferenceCode, long companyId, long userId,
			String title, String name, String scope, byte[] bytes)
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