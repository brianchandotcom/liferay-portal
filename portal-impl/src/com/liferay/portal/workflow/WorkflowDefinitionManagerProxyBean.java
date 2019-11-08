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

package com.liferay.portal.workflow;

import com.liferay.portal.kernel.messaging.proxy.BaseProxyBean;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowException;

import java.util.List;

/**
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 */
@OSGiBeanProperties(
	property = "proxy.bean=true", service = WorkflowDefinitionManager.class
)
public class WorkflowDefinitionManagerProxyBean
	extends BaseProxyBean implements WorkflowDefinitionManager {

	@Override
	public WorkflowDefinition deployWorkflowDefinition(
		long companyId, long userId, String title, String name, byte[] bytes) {

		throw new UnsupportedOperationException();
	}

	@Override
	public int getActiveWorkflowDefinitionCount(long companyId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getActiveWorkflowDefinitionCount(long companyId, String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<WorkflowDefinition> getActiveWorkflowDefinitions(
		long companyId, int start, int end,
		OrderByComparator<WorkflowDefinition> orderByComparator) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<WorkflowDefinition> getActiveWorkflowDefinitions(
		long companyId, String name, int start, int end,
		OrderByComparator<WorkflowDefinition> orderByComparator) {

		throw new UnsupportedOperationException();
	}

	@Override
	public WorkflowDefinition getLatestWorkflowDefinition(
		long companyId, String name) {

		throw new UnsupportedOperationException();
	}

	@Override
	public int getLatestWorkflowDefinitionCount(long companyId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<WorkflowDefinition> getLatestWorkflowDefinitions(
		long companyId, int start, int end,
		OrderByComparator<WorkflowDefinition> orderByComparator) {

		throw new UnsupportedOperationException();
	}

	@Override
	public WorkflowDefinition getWorkflowDefinition(
		long companyId, String name, int version) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public int getWorkflowDefinitionCount(long companyId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getWorkflowDefinitionCount(long companyId, String name) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public List<WorkflowDefinition> getWorkflowDefinitions(
		long companyId, int start, int end,
		OrderByComparator<WorkflowDefinition> orderByComparator) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<WorkflowDefinition> getWorkflowDefinitions(
		long companyId, String name, int start, int end,
		OrderByComparator<WorkflowDefinition> orderByComparator) {

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
	@Override
	public WorkflowDefinition saveWorkflowDefinition(
			long companyId, long userId, String title, String name,
			byte[] bytes)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void undeployWorkflowDefinition(
		long companyId, long userId, String name, int version) {

		throw new UnsupportedOperationException();
	}

	@Override
	public WorkflowDefinition updateActive(
		long companyId, long userId, String name, int version, boolean active) {

		throw new UnsupportedOperationException();
	}

	@Override
	public WorkflowDefinition updateTitle(
		long companyId, long userId, String name, int version, String title) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void validateWorkflowDefinition(byte[] bytes) {
		throw new UnsupportedOperationException();
	}

}