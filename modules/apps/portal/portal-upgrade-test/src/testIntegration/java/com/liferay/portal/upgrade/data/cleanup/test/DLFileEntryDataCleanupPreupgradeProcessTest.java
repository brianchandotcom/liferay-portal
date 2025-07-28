/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.data.cleanup.DLFileEntryDataCleanupPreupgradeProcess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author István András Dézsi
 */
@RunWith(Arquillian.class)
public class DLFileEntryDataCleanupPreupgradeProcessTest
	extends DLFileEntryDataCleanupPreupgradeProcess {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testUpgrade() throws Exception {
		long fileEntryId1 = RandomTestUtil.nextLong();
		long fileEntryId2 = RandomTestUtil.nextLong();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				DLFileEntryDataCleanupPreupgradeProcess.class.getName(),
				LoggerTestUtil.INFO)) {

			runSQL(
				StringBundler.concat(
					"insert into DLFileEntry (",
					"mvccVersion, ctCollectionId, fileEntryId, groupId) ",
					"values (0, 0, ", fileEntryId1, ", ",
					RandomTestUtil.nextLong(), ")"));
			runSQL(
				StringBundler.concat(
					"insert into DLFileEntry (",
					"mvccVersion, ctCollectionId, fileEntryId, groupId, name) ",
					"values (0, 0, ", fileEntryId2, ", ",
					RandomTestUtil.nextLong(), ", '')"));

			upgrade();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			List<String> logMessages = new ArrayList<>();

			for (LogEntry logEntry : logEntries) {
				logMessages.add(logEntry.getMessage());
			}

			Assert.assertTrue(
				logMessages.contains(
					StringBundler.concat(
						"DLFileEntry with ID ", fileEntryId1,
						" has been deleted because name was null")));

			Assert.assertTrue(
				logMessages.contains(
					StringBundler.concat(
						"DLFileEntry with ID ", fileEntryId2,
						" has been deleted because name was empty")));
		}
		finally {
			runSQL(
				"delete from DLFileEntry where fileEntryId = " + fileEntryId1);
			runSQL(
				"delete from DLFileEntry where fileEntryId = " + fileEntryId2);
		}
	}

}