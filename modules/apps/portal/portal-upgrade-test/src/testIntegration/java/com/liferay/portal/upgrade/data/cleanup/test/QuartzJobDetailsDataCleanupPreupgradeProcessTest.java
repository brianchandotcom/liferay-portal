/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.data.cleanup.QuartzJobDetailsDataCleanupPreupgradeProcess;

import java.util.ArrayList;
import java.util.List;

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
public class QuartzJobDetailsDataCleanupPreupgradeProcessTest
	extends QuartzJobDetailsDataCleanupPreupgradeProcess {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		if (DBPartition.isPartitionEnabled()) {
			_safeCloseable = CompanyThreadLocal.setCompanyIdWithSafeCloseable(
				PortalInstancePool.getDefaultCompanyId());
		}
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		if (_safeCloseable != null) {
			_safeCloseable.close();
		}
	}

	@Test
	public void testUpgrade() throws Exception {
		String jobName = RandomTestUtil.randomString();

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				QuartzJobDetailsDataCleanupPreupgradeProcess.class.getName(),
				LoggerTestUtil.INFO)) {

			runSQL(
				StringBundler.concat(
					"insert into QUARTZ_JOB_DETAILS (SCHED_NAME, JOB_NAME, ",
					"JOB_GROUP, JOB_CLASS_NAME, IS_DURABLE, IS_NONCONCURRENT, ",
					"IS_UPDATE_DATA, REQUESTS_RECOVERY) values ('",
					"PersistedQuartzSchedulerEngineInstance', '", jobName,
					"', '", RandomTestUtil.randomString(), "', '",
					"com.liferay.portal.scheduler.quartz.internal.job.",
					"MessageSenderJob', [$TRUE$], [$FALSE$], [$FALSE$], ",
					"[$FALSE$])"));

			upgrade();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			List<String> logMessages = new ArrayList<>();

			for (LogEntry logEntry : logEntries) {
				logMessages.add(logEntry.getMessage());
			}

			Assert.assertTrue(
				logMessages.contains(
					StringBundler.concat(
						"Deleted Quartz job detail for job ", jobName,
						" from QUARTZ_JOB_DETAILS table because JOB_DATA ",
						"column was null")));
		}
		finally {
			runSQL(
				"delete from QUARTZ_JOB_DETAILS where JOB_NAME = '" + jobName +
					"'");
		}
	}

	private static SafeCloseable _safeCloseable;

}