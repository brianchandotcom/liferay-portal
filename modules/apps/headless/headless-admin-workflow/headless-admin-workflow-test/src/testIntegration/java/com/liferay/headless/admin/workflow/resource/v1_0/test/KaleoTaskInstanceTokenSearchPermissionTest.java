/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.workflow.client.dto.v1_0.ObjectReviewed;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowDefinition;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowInstance;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTask;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.ObjectReviewedTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowDefinitionTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowInstanceTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowTaskTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Nícolas Moura
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class KaleoTaskInstanceTokenSearchPermissionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_workflowDefinition = WorkflowDefinitionTestUtil.addWorkflowDefinition(
			"fork-and-join-workflow-definition.xml");
	}

	@Before
	public void setUp() throws Exception {
		ObjectReviewed objectReviewed =
			ObjectReviewedTestUtil.addObjectReviewed();

		_workflowInstance = WorkflowInstanceTestUtil.addWorkflowInstance(
			TestPropsValues.getGroupId(), objectReviewed, _workflowDefinition);
	}

	@Test
	public void testSearchExcludesInaccessibleAndIncludesAccessible()
		throws Exception {

		List<WorkflowTask> workflowTasks =
			WorkflowTaskTestUtil.getWorkflowTasks(_workflowInstance.getId());

		Assert.assertEquals(workflowTasks.toString(), 3, workflowTasks.size());

		WorkflowTask workflowTask = workflowTasks.get(0);

		long accessibleWorkflowTaskId = workflowTask.getId();

		User user = UserTestUtil.addUser();

		_workflowTaskManager.assignWorkflowTaskToUser(
			user.getCompanyId(), TestPropsValues.getUserId(),
			accessibleWorkflowTaskId, user.getUserId(),
			"Assigning to non-admin user", null, null);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(user.getCompanyId());
			serviceContext.setUserId(user.getUserId());

			List<KaleoTaskInstanceToken> kaleoTaskInstanceTokens =
				_kaleoTaskInstanceTokenLocalService.search(
					null, null, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null, serviceContext);

			Assert.assertEquals(
				kaleoTaskInstanceTokens.toString(), 1,
				kaleoTaskInstanceTokens.size());

			KaleoTaskInstanceToken kaleoTaskInstanceToken =
				kaleoTaskInstanceTokens.get(0);

			Assert.assertEquals(
				accessibleWorkflowTaskId,
				kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	private static WorkflowDefinition _workflowDefinition;

	@Inject
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	private WorkflowInstance _workflowInstance;

	@Inject
	private WorkflowTaskManager _workflowTaskManager;

}