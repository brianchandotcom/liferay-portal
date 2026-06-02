/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.integration.internal;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.kaleo.runtime.TaskManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Roselaine Marques
 */
public class WorkflowTaskManagerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_userLocalService = Mockito.mock(UserLocalService.class);

		ReflectionTestUtil.setFieldValue(
			_workflowTaskManagerImpl, "_userLocalService", _userLocalService);

		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);

		Mockito.when(
			permissionChecker.getUserId()
		).thenReturn(
			_USER_ID
		);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(null);
	}

	@Test
	public void testAssignWorkflowTaskToUser() throws Exception {

		// User from a different company

		long assigneeUserId1 = RandomTestUtil.randomLong();

		User assigneeUser1 = Mockito.mock(User.class);

		Mockito.when(
			_userLocalService.fetchUser(assigneeUserId1)
		).thenReturn(
			assigneeUser1
		);

		long companyId = RandomTestUtil.randomLong();

		Mockito.when(
			assigneeUser1.getCompanyId()
		).thenReturn(
			companyId + 1
		);

		try {
			_workflowTaskManagerImpl.assignWorkflowTaskToUser(
				companyId, _USER_ID, RandomTestUtil.randomLong(),
				assigneeUserId1, RandomTestUtil.randomString(), null, null);

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			Assert.assertEquals(assigneeUserId1, principalException.resourceId);
			Assert.assertEquals(
				User.class.getName(), principalException.resourceName);
		}

		// User from the same company

		ReflectionTestUtil.setFieldValue(
			_workflowTaskManagerImpl, "_taskManager",
			Mockito.mock(TaskManager.class));

		long assigneeUserId2 = RandomTestUtil.randomLong();

		User assigneeUser2 = Mockito.mock(User.class);

		Mockito.when(
			_userLocalService.fetchUser(assigneeUserId2)
		).thenReturn(
			assigneeUser2
		);

		Mockito.when(
			assigneeUser2.getCompanyId()
		).thenReturn(
			companyId
		);

		_workflowTaskManagerImpl.assignWorkflowTaskToUser(
			companyId, _USER_ID, RandomTestUtil.randomLong(), assigneeUserId2,
			RandomTestUtil.randomString(), null, null);
	}

	private static final long _USER_ID = RandomTestUtil.randomLong();

	private UserLocalService _userLocalService;
	private final WorkflowTaskManagerImpl _workflowTaskManagerImpl =
		new WorkflowTaskManagerImpl();

}