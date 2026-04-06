/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.verify.PreupgradeVerifyCompanyUsers;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.test.util.BaseVerifyProcessTestCase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
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

		_originalCacheEnabled = ReflectionTestUtil.getAndSetFieldValue(
			PortalInstancePool.class, "_cacheEnabled", false);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ReflectionTestUtil.setFieldValue(
			PortalInstancePool.class, "_cacheEnabled", _originalCacheEnabled);
	}

	@Test
	public void testVerifyCompanyUsers() throws Exception {
		DB db = DBManagerUtil.getDB();
		Role role = _roleLocalService.getRole(
			_companyId, RoleConstants.ADMINISTRATOR);
		User user = _userLocalService.getGuestUser(_companyId);
		List<User> users = _userLocalService.getRoleUsers(role.getRoleId());

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				PreupgradeVerifyCompanyUsers.class.getName(),
				LoggerTestUtil.ERROR)) {

			try {
				_userLocalService.deleteRoleUsers(role.getRoleId(), users);

				db.runSQL(
					StringBundler.concat(
						"update User_ set type_ = ", UserConstants.TYPE_REGULAR,
						" where userId = ", user.getUserId()));

				super.testVerify();

				Assert.fail();
			}
			catch (Exception exception) {
				Set<String> expectedMessages = Set.of(
					"No admin user found was for company " + _companyId,
					"No guest user found was for company " + _companyId);

				List<LogEntry> logEntries = logCapture.getLogEntries();

				Assert.assertEquals(
					logEntries.toString(), expectedMessages.size(),
					logEntries.size());

				Set<String> verifyMessages = new HashSet<>();

				for (LogEntry entry : logEntries) {
					verifyMessages.add(entry.getMessage());
				}

				Assert.assertEquals(
					verifyMessages.toString(), expectedMessages,
					verifyMessages);
			}
			finally {
				db.runSQL(
					StringBundler.concat(
						"update User_ set type_ = ", UserConstants.TYPE_GUEST,
						" where userId = ", user.getUserId()));

				_userLocalService.addRoleUsers(role.getRoleId(), users);
			}
		}
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new PreupgradeVerifyCompanyUsers();
	}

	private static long _companyId;
	private static boolean _originalCacheEnabled;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}