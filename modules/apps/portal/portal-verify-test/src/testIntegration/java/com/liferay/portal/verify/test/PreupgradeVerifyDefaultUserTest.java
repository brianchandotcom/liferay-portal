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
import com.liferay.portal.verify.PreupgradeVerifyDefaultUser;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.test.util.BaseVerifyProcessTestCase;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author István András Dézsi
 */
@RunWith(Arquillian.class)
public class PreupgradeVerifyDefaultUserTest extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testVerifyDefaultAdminUser() throws Exception {
		User defaultAdminUser = UserTestUtil.getAdminUser(
			TestPropsValues.getCompanyId());

		Role administratorRole = _roleLocalService.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ADMINISTRATOR);

		try {
			_userLocalService.deleteRoleUser(
				administratorRole.getRoleId(), defaultAdminUser);

			super.testVerify();
		}
		catch (Exception exception) {
			_verifyException(exception, "Default admin user not found");
		}
		finally {
			_userLocalService.addRoleUser(
				administratorRole.getRoleId(), defaultAdminUser);
		}
	}

	@Test
	public void testVerifyDefaultGuestUser() throws Exception {
		DB db = DBManagerUtil.getDB();

		User defaultGuestUser = _userLocalService.getGuestUser(
			TestPropsValues.getCompanyId());

		try {
			db.runSQL(
				StringBundler.concat(
					"update User_ set type_ = ", UserConstants.TYPE_REGULAR,
					" where userId = ", defaultGuestUser.getUserId()));

			super.testVerify();
		}
		catch (Exception exception) {
			_verifyException(exception, "Default guest user not found");
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
		return new PreupgradeVerifyDefaultUser();
	}

	private void _verifyException(Exception exception, String expectedMessage)
		throws Exception {

		String message = exception.getMessage();

		Assert.assertTrue(message.contains(expectedMessage));
	}

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}