/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;

import java.io.InputStream;

import java.util.List;

/**
 * @author Micha Kiener
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 */
public class WorkflowDefinitionManagerProxyBean
	extends BaseProxyBean implements WorkflowDefinitionManager {

	@Override
	public WorkflowDefinition deployWorkflowDefinition(
		long companyId, long userId, String title, InputStream inputStream) {

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
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<WorkflowDefinition> getActiveWorkflowDefinitions(
		long companyId, String name, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	@Override
	public WorkflowDefinition getLatestKaleoDefinition(
		long companyId, String name) {

		throw new UnsupportedOperationException();
	}

	@Override
	public WorkflowDefinition getWorkflowDefinition(
		long companyId, String name, int version) {

		throw new UnsupportedOperationException();
	}

	@Override
	public int getWorkflowDefinitionCount(long companyId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getWorkflowDefinitionCount(long companyId, String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<WorkflowDefinition> getWorkflowDefinitions(
		long companyId, int start, int end,
		OrderByComparator orderByComparator) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<WorkflowDefinition> getWorkflowDefinitions(
		long companyId, String name, int start, int end,
		OrderByComparator orderByComparator) {

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
	public void validateWorkflowDefinition(InputStream inputStream) {
		throw new UnsupportedOperationException();
	}

}