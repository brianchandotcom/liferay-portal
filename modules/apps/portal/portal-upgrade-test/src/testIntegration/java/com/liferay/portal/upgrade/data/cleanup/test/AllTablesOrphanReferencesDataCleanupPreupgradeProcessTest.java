/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.AllTablesOrphanReferencesDataCleanupPreupgradeProcess;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class AllTablesOrphanReferencesDataCleanupPreupgradeProcessTest
	extends BaseOrphanReferencesDataCleanupPreupgradeProcessTestCase {

	@Before
	public void setUp() {
		_companyId1 = RandomTestUtil.nextLong();
		_companyId2 = RandomTestUtil.nextLong();
	}

	@Override
	protected UnsafeRunnable<Exception> getInsertDataUnsafeRunnable() {
		return () -> {
			_insert(_companyId1);
			_insert(_companyId1);
			_insert(_companyId2);
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
					getExpectedMessage(
						2, "Image", "Company", "companyId", _companyId1)));
			Assert.assertTrue(
				logMessages.contains(
					getExpectedMessage(
						1, "Image", "Company", "companyId", _companyId2)));
			Assert.assertTrue(
				logMessages.contains(
					getExpectedMessage(
						2, "Portlet", "Company", "companyId", _companyId1)));
			Assert.assertTrue(
				logMessages.contains(
					getExpectedMessage(
						1, "Portlet", "Company", "companyId", _companyId2)));
		};
	}

	@Override
	protected UpgradeProcess getUpgradeProcess() {
		return new AllTablesOrphanReferencesDataCleanupPreupgradeProcess(
			"Company", "companyId");
	}

	private void _insert(long companyId) throws Exception {
		db.runSQL(
			connection,
			StringBundler.concat(
				"insert into Image (mvccVersion, ctCollectionId, imageId, ",
				"companyId) values (0, 0, ", RandomTestUtil.nextLong(), ", ",
				companyId, ")"));

		db.runSQL(
			connection,
			StringBundler.concat(
				"insert into Portlet (mvccVersion, id_, companyId, portletId, ",
				"active_) values (0, ", RandomTestUtil.nextLong(), ", ",
				companyId, ", '", RandomTestUtil.randomString(),
				"', [$FALSE$])"));
	}

	private long _companyId1;
	private long _companyId2;

}