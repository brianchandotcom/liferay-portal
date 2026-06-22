/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.test.rule.Inject;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge Avalos
 */
@RunWith(Arquillian.class)
public class TransactionFlushDBPartitionTest extends BaseDBPartitionTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = CompanyTestUtil.addCompany();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					_company.getCompanyId())) {

			_adminUser = UserTestUtil.getAdminUser(_company.getCompanyId());
		}
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		CompanyLocalServiceUtil.deleteCompany(_company.getCompanyId());
	}

	@Test
	public void testSetCompanyIdWithSafeCloseableFlushesSessionOnClose()
		throws Throwable {

		String roleName = RandomTestUtil.randomString();

		TransactionInvokerUtil.invoke(
			_transactionConfig,
			() -> {
				try (SafeCloseable safeCloseable =
						CompanyThreadLocal.setCompanyIdWithSafeCloseable(
							_company.getCompanyId())) {

					_roleLocalService.addRole(
						null, _adminUser.getUserId(), null, 0, roleName, null,
						null, RoleConstants.TYPE_REGULAR, null,
						new ServiceContext());
				}

				return null;
			});

		Role role;

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					_company.getCompanyId())) {

			role = _roleLocalService.fetchRole(
				_company.getCompanyId(), roleName);
		}

		Assert.assertNull(
			_roleLocalService.fetchRole(
				TestPropsValues.getCompanyId(), roleName));

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					_company.getCompanyId())) {

			_roleLocalService.deleteRole(role.getRoleId());
		}
	}

	@Test
	public void testSetCompanyIdWithSafeCloseableFlushesSessionOnEntry()
		throws Throwable {

		String roleName = RandomTestUtil.randomString();

		TransactionInvokerUtil.invoke(
			_transactionConfig,
			() -> {
				_roleLocalService.addRole(
					null, TestPropsValues.getUserId(), null, 0, roleName, null,
					null, RoleConstants.TYPE_REGULAR, null,
					new ServiceContext());

				try (SafeCloseable safeCloseable =
						CompanyThreadLocal.setCompanyIdWithSafeCloseable(
							_company.getCompanyId())) {

					_roleLocalService.getRoles(
						_company.getCompanyId(),
						new int[] {RoleConstants.TYPE_REGULAR});
				}

				return null;
			});

		Role role = _roleLocalService.fetchRole(
			TestPropsValues.getCompanyId(), roleName);

		_roleLocalService.deleteRole(role.getRoleId());
	}

	private static User _adminUser;
	private static Company _company;
	private static final TransactionConfig _transactionConfig;

	static {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setPropagation(Propagation.REQUIRED);
		builder.setRollbackForClasses(Exception.class);

		_transactionConfig = builder.build();
	}

	@Inject
	private RoleLocalService _roleLocalService;

}