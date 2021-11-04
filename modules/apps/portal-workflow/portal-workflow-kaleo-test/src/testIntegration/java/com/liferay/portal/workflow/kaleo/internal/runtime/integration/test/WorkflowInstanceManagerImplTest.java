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

package com.liferay.portal.workflow.kaleo.internal.runtime.integration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.kernel.workflow.search.WorkflowModelSearchResult;
import com.liferay.portal.test.rule.Inject;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.ServiceRegistration;

/**
 * @author Feliphe Marinho
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class WorkflowInstanceManagerImplTest
	extends BaseWorkflowManagerTestCase {

	@Test
	public void testSearchCountWhenThereAreActiveParallelTasks()
		throws Exception {

		WorkflowDefinition workflowDefinition =
			_workflowDefinitionManager.deployWorkflowDefinition(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				FileUtil.getBytes(
					getResourceInputStream(
						"join-xor-workflow-definition.xml")));

		ServiceRegistration<WorkflowHandler<?>>
			workflowHandlerServiceRegistration = registryWorkflowHandler(
				workflowDefinition.getName());

		Class<?> clazz = getClass();

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			TestPropsValues.getCompanyId(), 0, TestPropsValues.getUserId(),
			clazz.getName(), 1, null, new ServiceContext());

		Assert.assertEquals(
			1,
			workflowInstanceManager.searchCount(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, workflowDefinition.getName(), false));

		workflowHandlerServiceRegistration.unregister();
	}

	@Test
	public void testSearchWorkflowInstancesWhenThereAreActiveParallelTasks()
		throws Exception {

		WorkflowDefinition workflowDefinition =
			_workflowDefinitionManager.deployWorkflowDefinition(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				FileUtil.getBytes(
					getResourceInputStream(
						"join-xor-workflow-definition.xml")));

		ServiceRegistration<WorkflowHandler<?>>
			workflowHandlerServiceRegistration = registryWorkflowHandler(
				workflowDefinition.getName());

		Class<?> clazz = getClass();

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			TestPropsValues.getCompanyId(), 0, TestPropsValues.getUserId(),
			clazz.getName(), 1, null, new ServiceContext());

		WorkflowModelSearchResult<WorkflowInstance> workflowModelSearchResult =
			workflowInstanceManager.searchWorkflowInstances(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK, null, true, 0, 1,
				WorkflowComparatorFactoryUtil.getInstanceCompletedComparator(
					false));

		List<WorkflowInstance> workflowInstances =
			workflowModelSearchResult.getWorkflowModels();

		Assert.assertEquals(
			workflowInstances.toString(), 1, workflowInstances.size());

		workflowHandlerServiceRegistration.unregister();
	}

	@Test
	public void testSearchWorkflowInstancesWhenThereIsAnUnregisteredHandler()
		throws Exception {

		Class<?> clazz = getClass();

		ServiceRegistration<WorkflowHandler<?>>
			workflowHandlerServiceRegistration = registryWorkflowHandler();

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			TestPropsValues.getCompanyId(), 0, TestPropsValues.getUserId(),
			clazz.getName(), 1, null, new ServiceContext());

		WorkflowModelSearchResult<WorkflowInstance> workflowModelSearchResult =
			workflowInstanceManager.searchWorkflowInstances(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK, null, true, 0, 1,
				WorkflowComparatorFactoryUtil.getInstanceCompletedComparator(
					false));

		List<WorkflowInstance> workflowInstances =
			workflowModelSearchResult.getWorkflowModels();

		Assert.assertEquals(
			workflowInstances.toString(), 1, workflowInstances.size());

		workflowHandlerServiceRegistration.unregister();

		workflowModelSearchResult =
			workflowInstanceManager.searchWorkflowInstances(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK, null, true, 0, 1,
				WorkflowComparatorFactoryUtil.getInstanceCompletedComparator(
					false));

		workflowInstances = workflowModelSearchResult.getWorkflowModels();

		Assert.assertEquals(
			workflowInstances.toString(), 0, workflowInstances.size());
	}

	@Inject
	private WorkflowDefinitionManager _workflowDefinitionManager;

}