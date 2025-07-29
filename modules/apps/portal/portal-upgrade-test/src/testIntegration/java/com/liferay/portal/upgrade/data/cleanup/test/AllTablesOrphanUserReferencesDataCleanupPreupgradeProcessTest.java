/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.AllTablesOrphanUserReferencesDataCleanupPreupgradeProcess;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.rule.Inject;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class AllTablesOrphanUserReferencesDataCleanupPreupgradeProcessTest
	extends BaseOrphanReferencesDataCleanupPreupgradeProcessTestCase {

	@Before
	public void setUp() throws Exception {
		_companyId = PortalInstancePool.getDefaultCompanyId();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(_companyId)) {

			_adminUser = UserTestUtil.getAdminUser(_companyId);
		}

		_userId = RandomTestUtil.nextLong();
	}

	@Override
	protected UnsafeRunnable<Exception> getInsertDataUnsafeRunnable() {
		return () -> {
			db.runSQL(
				connection,
				StringBundler.concat(
					"insert into Layout (mvccVersion, ctCollectionId, plid, ",
					"groupId, companyId, userId, layoutId) values (0, 0, ",
					RandomTestUtil.nextLong(), ", ", RandomTestUtil.nextLong(),
					", ", _companyId, ", ", _userId, ", ",
					RandomTestUtil.nextLong(), ")"));

			db.runSQL(
				connection,
				StringBundler.concat(
					"insert into Users_Roles (companyId, roleId, userId, ",
					"ctCollectionId) values (", _companyId, ", ",
					RandomTestUtil.nextLong(), ", ", _userId, ", 0)"));
		};
	}

	@Override
	protected UnsafeConsumer<LogCapture, Exception>
		getLogAssertionUnsafeConsumer() {

		return logCapture -> {
			List<LogEntry> logEntries = logCapture.getLogEntries();

			List<String> logMessages = new ArrayList<>();

			for (LogEntry logEntry : logEntries) {
				logMessages.add(logEntry.getMessage());
			}

			Assert.assertTrue(
				logMessages.contains(
					StringBundler.concat(
						"1 orphan entries from table ",
						dbInspector.normalizeName("Layout"),
						" have been updated to value ", _adminUser.getUserId(),
						" because value ", _userId,
						" was not found in the origin table ",
						dbInspector.normalizeName("User_"), " and column ",
						dbInspector.normalizeName("userId"))));
			Assert.assertTrue(
				logMessages.contains(
					StringBundler.concat(
						"1 orphan entries from table ",
						dbInspector.normalizeName("Users_Roles"),
						" have been deleted because value ", _userId,
						" was not found in the origin table ",
						dbInspector.normalizeName("User_"), " and column ",
						dbInspector.normalizeName("userId"))));
		};
	}

	@Override
	protected UpgradeProcess getUpgradeProcess() {
		return new AllTablesOrphanUserReferencesDataCleanupPreupgradeProcess();
	}

	private User _adminUser;
	private long _companyId;

	@Inject
	private CompanyLocalService _companyLocalService;

	private long _userId;

}