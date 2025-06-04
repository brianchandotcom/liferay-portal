/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.verify.PreupgradeVerifyCompanyUsers;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.test.util.BaseVerifyProcessTestCase;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author István András Dézsi
 */
@RunWith(Arquillian.class)
public class PreupgradeVerifyCompanyUsersTest
	extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_companyId = TestPropsValues.getCompanyId();
	}

	@Test
	public void testVerifyCompanyAdminUser() throws Exception {
		User defaultAdminUser = UserTestUtil.getAdminUser(_companyId);

		Role administratorRole = _roleLocalService.getRole(
			_companyId, RoleConstants.ADMINISTRATOR);

		try {
			_userLocalService.deleteRoleUser(
				administratorRole.getRoleId(), defaultAdminUser);

			super.testVerify();
		}
		catch (Exception exception) {
			Assert.assertEquals(
				"No admin user found for company " + _companyId,
				exception.getMessage());
		}
		finally {
			_userLocalService.addRoleUser(
				administratorRole.getRoleId(), defaultAdminUser);
		}
	}

	@Test
	public void testVerifyCompanyGuestUser() throws Exception {
		DB db = DBManagerUtil.getDB();

		User defaultGuestUser = _userLocalService.getGuestUser(_companyId);

		try {
			db.runSQL(
				StringBundler.concat(
					"update User_ set type_ = ", UserConstants.TYPE_REGULAR,
					" where userId = ", defaultGuestUser.getUserId()));

			super.testVerify();
		}
		catch (Exception exception) {
			Assert.assertEquals(
				"No guest user found for company " + _companyId,
				exception.getMessage());
		}
		finally {
			db.runSQL(
				StringBundler.concat(
					"update User_ set type_ = ", UserConstants.TYPE_GUEST,
					" where userId = ", defaultGuestUser.getUserId()));
		}
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new PreupgradeVerifyCompanyUsers();
	}

	private static long _companyId;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}