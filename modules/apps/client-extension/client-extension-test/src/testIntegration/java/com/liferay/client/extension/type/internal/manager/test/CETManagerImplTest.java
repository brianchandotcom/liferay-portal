/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.manager.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Evan Thibodeau
 */
@RunWith(Arquillian.class)
public class CETManagerImplTest {

	@Test
	public void testGetCET() throws PortalException {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME_CET_MANAGER_IMPL, LoggerTestUtil.WARN)) {

			Assert.assertNull(
				_cetManager.getCET(
					TestPropsValues.getCompanyId(),
					_NONEXISTENT_EXTERNAL_REFERENCE_CODE));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"No CET found for external reference code " +
					_NONEXISTENT_EXTERNAL_REFERENCE_CODE,
				logEntry.getMessage());
		}
	}

	@Rule
	public final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	private static final String _CLASS_NAME_CET_MANAGER_IMPL =
		"com.liferay.client.extension.type.internal.manager.CETManagerImpl";

	private static final String _NONEXISTENT_EXTERNAL_REFERENCE_CODE =
		"NONEXISTENT_EXTERNAL_REFERENCE_CODE";

	@Inject
	private CETManager _cetManager;

}