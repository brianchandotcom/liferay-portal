/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.workflow;

import com.liferay.portal.kernel.util.OrderByComparator;

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
			long companyId, long userId, String title, String name,
			byte[] bytes)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public default WorkflowDefinition deployWorkflowDefinition(
			long companyId, long userId, String title, String name,
			String scope, byte[] bytes)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

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
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public WorkflowDefinition getWorkflowDefinition(
			long companyId, String name, int version)
		throws WorkflowException;

	public List<WorkflowDefinition> getWorkflowDefinitions(
			long companyId, String name, int start, int end,
			OrderByComparator<WorkflowDefinition> orderByComparator)
		throws WorkflowException;

	public default int getWorkflowDefinitionsCount(long companyId, String name)
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
			long companyId, long userId, String title, String name,
			byte[] bytes)
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
			long companyId, long userId, String title, String name,
			String scope, byte[] bytes)
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