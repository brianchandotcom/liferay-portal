/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.runtime.integration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.search.WorkflowModelSearchResult;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.comparator.WorkflowComparatorFactory;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
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

		try (ServiceRegistrationHolder serviceRegistrationHolder =
				registryWorkflowHandler(workflowDefinition.getName())) {

			Class<?> clazz = getClass();

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				TestPropsValues.getCompanyId(), 0, TestPropsValues.getUserId(),
				clazz.getName(), 1, null, new ServiceContext());

			WorkflowInstanceLink workflowInstanceLink =
				workflowInstanceLinkLocalService.getWorkflowInstanceLink(
					TestPropsValues.getCompanyId(), 0, clazz.getName(), 1);

			WorkflowInstance workflowInstance =
				workflowInstanceManager.getWorkflowInstance(
					workflowInstanceLink.getCompanyId(),
					workflowInstanceLink.getWorkflowInstanceId());

			_kaleoInstanceLocalService.completeKaleoInstance(
				workflowInstance.getWorkflowInstanceId());

			try {
				WorkflowHandlerRegistryUtil.startWorkflowInstance(
					TestPropsValues.getCompanyId(), 0,
					TestPropsValues.getUserId(), clazz.getName(), 2, null,
					new ServiceContext());

				Assert.assertEquals(
					1,
					workflowInstanceManager.searchCount(
						TestPropsValues.getCompanyId(),
						TestPropsValues.getUserId(), null, StringPool.BLANK,
						StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
						workflowDefinition.getName(), false));
				Assert.assertEquals(
					1,
					workflowInstanceManager.searchCount(
						TestPropsValues.getCompanyId(),
						TestPropsValues.getUserId(), null, StringPool.BLANK,
						StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
						workflowDefinition.getName(), true));
				Assert.assertEquals(
					2,
					workflowInstanceManager.searchCount(
						TestPropsValues.getCompanyId(),
						TestPropsValues.getUserId(), null, StringPool.BLANK,
						StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
						workflowDefinition.getName(), null));
			}
			finally {
				workflowInstanceManager.updateActive(
					TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
					workflowInstance.getWorkflowInstanceId(), false);
			}
		}
	}

	@Test
	public void testSearchWorkflowInstancesWhenThereAreActiveParallelTasks()
		throws Exception {

		try (ServiceRegistrationHolder serviceRegistrationHolder =
				registryWorkflowHandler(
					_workflowDefinitionManager.deployWorkflowDefinition(
						TestPropsValues.getCompanyId(),
						TestPropsValues.getUserId(),
						RandomTestUtil.randomString(),
						RandomTestUtil.randomString(),
						FileUtil.getBytes(
							getResourceInputStream(
								"join-xor-workflow-definition.xml"))))) {

			Class<?> clazz = getClass();

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				TestPropsValues.getCompanyId(), 0, TestPropsValues.getUserId(),
				clazz.getName(), 1, null, new ServiceContext());

			WorkflowModelSearchResult<WorkflowInstance>
				workflowModelSearchResult =
					workflowInstanceManager.searchWorkflowInstances(
						TestPropsValues.getCompanyId(),
						TestPropsValues.getUserId(), null, StringPool.BLANK,
						StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
						StringPool.BLANK, null, true, 0, 1,
						_workflowComparatorFactory.
							getInstanceCompletedComparator(false));

			List<WorkflowInstance> workflowInstances =
				workflowModelSearchResult.getWorkflowModels();

			Assert.assertEquals(
				workflowInstances.toString(), 1, workflowInstances.size());
		}
	}

	@Test
	public void testSearchWorkflowInstancesWhenThereAreInactiveInstances()
		throws Exception {

		try (ServiceRegistrationHolder serviceRegistrationHolder =
				registryWorkflowHandler()) {

			Class<?> clazz = getClass();

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				TestPropsValues.getCompanyId(), 0, TestPropsValues.getUserId(),
				clazz.getName(), 1, null, new ServiceContext());

			WorkflowModelSearchResult<WorkflowInstance>
				workflowModelSearchResult =
					workflowInstanceManager.searchWorkflowInstances(
						TestPropsValues.getCompanyId(),
						TestPropsValues.getUserId(), true, StringPool.BLANK,
						StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
						StringPool.BLANK, null, true, 0, 1,
						_workflowComparatorFactory.
							getInstanceCompletedComparator(false));

			List<WorkflowInstance> workflowInstances =
				workflowModelSearchResult.getWorkflowModels();

			Assert.assertEquals(
				workflowInstances.toString(), 1, workflowInstances.size());

			WorkflowInstance workflowInstance = workflowInstances.get(0);

			workflowInstanceManager.updateActive(
				TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
				workflowInstance.getWorkflowInstanceId(), false);

			workflowModelSearchResult =
				workflowInstanceManager.searchWorkflowInstances(
					TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
					true, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
					StringPool.BLANK, StringPool.BLANK, null, true, 0, 1,
					_workflowComparatorFactory.getInstanceCompletedComparator(
						false));

			workflowInstances = workflowModelSearchResult.getWorkflowModels();

			Assert.assertEquals(
				workflowInstances.toString(), 0, workflowInstances.size());

			workflowModelSearchResult =
				workflowInstanceManager.searchWorkflowInstances(
					TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
					null, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
					StringPool.BLANK, StringPool.BLANK, null, true, 0, 1,
					_workflowComparatorFactory.getInstanceCompletedComparator(
						false));

			workflowInstances = workflowModelSearchResult.getWorkflowModels();

			Assert.assertEquals(
				workflowInstances.toString(), 1, workflowInstances.size());
		}
	}

	@Test
	public void testSearchWorkflowInstancesWhenThereIsAnUnregisteredHandler()
		throws Exception {

		try (ServiceRegistrationHolder serviceRegistrationHolder =
				registryWorkflowHandler()) {

			Class<?> clazz = getClass();

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				TestPropsValues.getCompanyId(), 0, TestPropsValues.getUserId(),
				clazz.getName(), 1, null, new ServiceContext());

			WorkflowModelSearchResult<WorkflowInstance>
				workflowModelSearchResult =
					workflowInstanceManager.searchWorkflowInstances(
						TestPropsValues.getCompanyId(),
						TestPropsValues.getUserId(), null, StringPool.BLANK,
						StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
						StringPool.BLANK, null, true, 0, 1,
						_workflowComparatorFactory.
							getInstanceCompletedComparator(false));

			List<WorkflowInstance> workflowInstances =
				workflowModelSearchResult.getWorkflowModels();

			Assert.assertEquals(
				workflowInstances.toString(), 1, workflowInstances.size());
		}

		WorkflowModelSearchResult<WorkflowInstance> workflowModelSearchResult =
			workflowInstanceManager.searchWorkflowInstances(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				null, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK, null, true, 0, 1,
				_workflowComparatorFactory.getInstanceCompletedComparator(
					false));

		List<WorkflowInstance> workflowInstances =
			workflowModelSearchResult.getWorkflowModels();

		Assert.assertEquals(
			workflowInstances.toString(), 0, workflowInstances.size());
	}

	@Test
	public void testSearchWorkflowInstancesWhenTwoUsersSubmitAnEntry()
		throws Exception {

		try (ServiceRegistrationHolder serviceRegistrationHolder =
				registryWorkflowHandler()) {

			Class<?> clazz = getClass();

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				TestPropsValues.getCompanyId(), 0, TestPropsValues.getUserId(),
				clazz.getName(), 1, null, new ServiceContext());

			User user = UserTestUtil.addUser(TestPropsValues.getCompanyId());

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				TestPropsValues.getCompanyId(), 0, user.getUserId(),
				clazz.getName(), 2, null, new ServiceContext());

			WorkflowModelSearchResult<WorkflowInstance>
				workflowModelSearchResult =
					workflowInstanceManager.searchWorkflowInstances(
						TestPropsValues.getCompanyId(), user.getUserId(), null,
						StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
						StringPool.BLANK, StringPool.BLANK, null, true, 0, 2,
						_workflowComparatorFactory.
							getInstanceCompletedComparator(false));

			List<WorkflowInstance> workflowInstances =
				workflowModelSearchResult.getWorkflowModels();

			Assert.assertEquals(
				workflowInstances.toString(), 1, workflowInstances.size());
		}
	}

	@Test
	public void testUpdateActive() throws Exception {
		try (ServiceRegistrationHolder serviceRegistrationHolder =
				registryWorkflowHandler()) {

			Class<?> clazz = getClass();

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				TestPropsValues.getCompanyId(), 0, TestPropsValues.getUserId(),
				clazz.getName(), 1, null, new ServiceContext());

			WorkflowInstanceLink workflowInstanceLink =
				workflowInstanceLinkLocalService.getWorkflowInstanceLink(
					TestPropsValues.getCompanyId(), 0, clazz.getName(), 1);

			WorkflowInstance workflowInstance =
				workflowInstanceManager.getWorkflowInstance(
					workflowInstanceLink.getCompanyId(),
					workflowInstanceLink.getWorkflowInstanceId());

			Assert.assertTrue(workflowInstance.isActive());

			workflowInstance = workflowInstanceManager.updateActive(
				TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
				workflowInstance.getWorkflowInstanceId(), false);

			Assert.assertFalse(workflowInstance.isActive());
		}
	}

	@Inject
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

	@Inject
	private WorkflowComparatorFactory _workflowComparatorFactory;

	@Inject
	private WorkflowDefinitionManager _workflowDefinitionManager;

}