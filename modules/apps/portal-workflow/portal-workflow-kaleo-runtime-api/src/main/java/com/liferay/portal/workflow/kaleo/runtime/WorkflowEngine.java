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

package com.liferay.portal.workflow.kaleo.runtime;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstance;

import java.io.InputStream;
import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 * @author Marcellus Tavares
 */
@ProviderType
public interface WorkflowEngine {

	public void deleteWorkflowDefinition(
			String name, int version, ServiceContext serviceContext)
		throws WorkflowException;

	public void deleteWorkflowInstance(
			long workflowInstanceId, ServiceContext serviceContext)
		throws WorkflowException;

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #deployWorkflowDefinition(String, String, InputStream,
	 *             ServiceContext)}
	 */
	@Deprecated
	public WorkflowDefinition deployWorkflowDefinition(
			String title, InputStream inputStream,
			ServiceContext serviceContext)
		throws WorkflowException;

	public default WorkflowDefinition deployWorkflowDefinition(
			String title, String name, InputStream inputStream,
			ServiceContext serviceContext)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public ExecutionContext executeTimerWorkflowInstance(
			long kaleoTimerInstanceTokenId, ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws WorkflowException;

	public List<String> getNextTransitionNames(
			long workflowInstanceId, ServiceContext serviceContext)
		throws WorkflowException;

	public WorkflowInstance getWorkflowInstance(
			long workflowInstanceId, ServiceContext serviceContext)
		throws WorkflowException;

	public int getWorkflowInstanceCount(
			Long userId, String assetClassName, Long assetClassPK,
			Boolean completed, ServiceContext serviceContext)
		throws WorkflowException;

	public int getWorkflowInstanceCount(
			Long userId, String[] assetClassNames, Boolean completed,
			ServiceContext serviceContext)
		throws WorkflowException;

	public int getWorkflowInstanceCount(
			String workflowDefinitionName, int workflowDefinitionVersion,
			boolean completed, ServiceContext serviceContext)
		throws WorkflowException;

	public List<WorkflowInstance> getWorkflowInstances(
			Long userId, String assetClassName, Long assetClassPK,
			Boolean completed, int start, int end,
			OrderByComparator<WorkflowInstance> orderByComparator,
			ServiceContext serviceContext)
		throws WorkflowException;

	public List<WorkflowInstance> getWorkflowInstances(
			Long userId, String[] assetClassNames, Boolean completed, int start,
			int end, OrderByComparator<WorkflowInstance> orderByComparator,
			ServiceContext serviceContext)
		throws WorkflowException;

	public List<WorkflowInstance> getWorkflowInstances(
			String workflowDefinitionName, int workflowDefinitionVersion,
			boolean completed, int start, int end,
			OrderByComparator<WorkflowInstance> orderByComparator,
			ServiceContext serviceContext)
		throws WorkflowException;

	public default WorkflowDefinition saveWorkflowDefinition(
			String title, String name, byte[] bytes,
			ServiceContext serviceContext)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #search(Long,
	 *             String, String, String, String, String, Boolean, int, int,
	 *             OrderByComparator, ServiceContext)}
	 */
	@Deprecated
	public List<WorkflowInstance> search(
			Long userId, String assetClassName, String nodeName,
			String kaleoDefinitionName, Boolean completed, int start, int end,
			OrderByComparator<WorkflowInstance> orderByComparator,
			ServiceContext serviceContext)
		throws WorkflowException;

	public default List<WorkflowInstance> search(
			Long userId, String assetClassName, String assetTitle,
			String assetDescription, String nodeName,
			String kaleoDefinitionName, Boolean completed, int start, int end,
			OrderByComparator<WorkflowInstance> orderByComparator,
			ServiceContext serviceContext)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #searchCount(Long,
	 *             String, String, String, String, String, Boolean,
	 *             ServiceContext)}
	 */
	@Deprecated
	public int searchCount(
			Long userId, String assetClassName, String nodeName,
			String kaleoDefinitionName, Boolean completed,
			ServiceContext serviceContext)
		throws WorkflowException;

	public default int searchCount(
			Long userId, String assetClassName, String assetTitle,
			String assetDescription, String nodeName,
			String kaleoDefinitionName, Boolean completed,
			ServiceContext serviceContext)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public WorkflowInstance signalWorkflowInstance(
			long workflowInstanceId, String transitionName,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws WorkflowException;

	public WorkflowInstance startWorkflowInstance(
			String workflowDefinitionName, Integer workflowDefinitionVersion,
			String transitionName, Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws WorkflowException;

	public WorkflowInstance updateContext(
			long workflowInstanceId, Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws WorkflowException;

	public void validateWorkflowDefinition(InputStream inputStream)
		throws WorkflowException;

}