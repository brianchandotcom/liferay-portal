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
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.comparator.WorkflowComparatorFactoryUtil;
import com.liferay.portal.kernel.workflow.search.WorkflowModelSearchResult;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.ServiceRegistration;

/**
 * @author Feliphe Marinho
 */
@RunWith(Arquillian.class)
public class WorkflowInstanceManagerImplTest
	extends BaseWorkflowManagerTestCase {

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

}