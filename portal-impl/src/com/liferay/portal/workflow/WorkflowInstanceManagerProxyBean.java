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
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Micha Kiener
 */
@OSGiBeanProperties(
	property = "proxy.bean=true", service = WorkflowInstanceManager.class
)
public class WorkflowInstanceManagerProxyBean
	extends BaseProxyBean implements WorkflowInstanceManager {

	@Override
	public void deleteWorkflowInstance(
		long companyId, long workflowInstanceId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<String> getNextTransitionNames(
		long companyId, long userId, long workflowInstanceId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public WorkflowInstance getWorkflowInstance(
		long companyId, long workflowInstanceId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public int getWorkflowInstanceCount(
		long companyId, Long userId, String assetClassName, Long assetClassPK,
		Boolean completed) {

		throw new UnsupportedOperationException();
	}

	@Override
	public int getWorkflowInstanceCount(
		long companyId, Long userId, String[] assetClassNames,
		Boolean completed) {

		throw new UnsupportedOperationException();
	}

	@Override
	public int getWorkflowInstanceCount(
		long companyId, String workflowDefinitionName,
		Integer workflowDefinitionVersion, Boolean completed) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<WorkflowInstance> getWorkflowInstances(
		long companyId, Long userId, String assetClassName, Long assetClassPK,
		Boolean completed, int start, int end,
		OrderByComparator<WorkflowInstance> orderByComparator) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<WorkflowInstance> getWorkflowInstances(
		long companyId, Long userId, String[] assetClassNames,
		Boolean completed, int start, int end,
		OrderByComparator<WorkflowInstance> orderByComparator) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<WorkflowInstance> getWorkflowInstances(
		long companyId, String workflowDefinitionName,
		Integer workflowDefinitionVersion, Boolean completed, int start,
		int end, OrderByComparator<WorkflowInstance> orderByComparator) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #search(long, Long,
	 *             String, String, String, String, String, Boolean, int, int,
	 *             OrderByComparator)}
	 */
	@Deprecated
	@Override
	public List<WorkflowInstance> search(
		long companyId, Long userId, String assetType, String nodeName,
		String kaleoDefinitionName, Boolean completed, int start, int end,
		OrderByComparator<WorkflowInstance> orderByComparator) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<WorkflowInstance> search(
		long companyId, Long userId, String assetClassName, String assetTitle,
		String assetDescription, String nodeName, String kaleoDefinitionName,
		Boolean completed, int start, int end,
		OrderByComparator<WorkflowInstance> orderByComparator) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #searchCount(long,
	 *             Long, String, String, String, String, String, Boolean)}
	 */
	@Deprecated
	@Override
	public int searchCount(
		long companyId, Long userId, String assetType, String nodeName,
		String kaleoDefinitionName, Boolean completed) {

		throw new UnsupportedOperationException();
	}

	@Override
	public int searchCount(
		long companyId, Long userId, String assetClassName, String assetTitle,
		String assetDescription, String nodeName, String kaleoDefinitionName,
		Boolean completed) {

		throw new UnsupportedOperationException();
	}

	@Override
	public WorkflowInstance signalWorkflowInstance(
		long companyId, long userId, long workflowInstanceId,
		String transitionName, Map<String, Serializable> workflowContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public WorkflowInstance startWorkflowInstance(
		long companyId, long groupId, long userId,
		String workflowDefinitionName, Integer workflowDefinitionVersion,
		String transitionName, Map<String, Serializable> workflowContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public WorkflowInstance updateWorkflowContext(
		long companyId, long workflowInstanceId,
		Map<String, Serializable> workflowContext) {

		throw new UnsupportedOperationException();
	}

}