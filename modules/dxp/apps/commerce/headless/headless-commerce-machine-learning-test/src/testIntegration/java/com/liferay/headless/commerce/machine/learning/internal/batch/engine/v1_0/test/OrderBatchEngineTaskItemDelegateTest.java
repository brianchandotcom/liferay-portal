/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.machine.learning.internal.batch.engine.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.batch.engine.BatchEngineTaskItemDelegate;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.pagination.Pagination;
import com.liferay.headless.commerce.machine.learning.dto.v1_0.Order;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Danny Situ
 */
@RunWith(Arquillian.class)
public class OrderBatchEngineTaskItemDelegateTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_batchEngineTaskItemDelegate, "contextCompany",
			CompanyLocalServiceUtil.getCompany(TestPropsValues.getCompanyId()));
		ReflectionTestUtil.setFieldValue(
			_batchEngineTaskItemDelegate, "contextUser",
			TestPropsValues.getUser());

		_permissionChecker = PermissionThreadLocal.getPermissionChecker();
		_user = UserTestUtil.addUser();
	}

	@After
	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_permissionChecker);
	}

	@Test
	public void testRead() throws Exception {
		_testReadWithAdministratorUser();
		_testReadWithAnalyticsAdministratorUser();
		_testReadWithRegularUser();
	}

	private Page<Order> _read() throws Exception {
		return _batchEngineTaskItemDelegate.read(
			null, Pagination.of(1, 1), null, Collections.emptyMap(), null);
	}

	private void _testReadWithAdministratorUser() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		Assert.assertNotNull(_read());
	}

	private void _testReadWithAnalyticsAdministratorUser() throws Exception {
		User user = UserTestUtil.addUser();

		Role role = _roleLocalService.getRole(
			user.getCompanyId(), RoleConstants.ANALYTICS_ADMINISTRATOR);

		_userLocalService.addRoleUser(role.getRoleId(), user.getUserId());

		UserTestUtil.setUser(user);

		Assert.assertNotNull(_read());
	}

	private void _testReadWithRegularUser() throws Exception {
		UserTestUtil.setUser(_user);

		try {
			_read();

			Assert.fail();
		}
		catch (PrincipalException.MustHavePermission principalException) {
			Assert.assertNotNull(principalException);
		}
	}

	@Inject(
		filter = "component.name=com.liferay.headless.commerce.machine.learning.internal.batch.engine.v1_0.OrderBatchEngineTaskItemDelegate"
	)
	private BatchEngineTaskItemDelegate<Order> _batchEngineTaskItemDelegate;

	private PermissionChecker _permissionChecker;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}