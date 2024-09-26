/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal.manager.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
				"com.liferay.client.extension.type.internal.manager." +
					"CETManagerImpl",
				LoggerTestUtil.WARN)) {

			String externalReferenceCode = RandomTestUtil.randomString();

			Assert.assertNull(
				_cetManager.getCET(
					TestPropsValues.getCompanyId(), externalReferenceCode));

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"No CET found for external reference code " +
					externalReferenceCode,
				logEntry.getMessage());
		}
	}

	@Rule
	public final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Inject
	private CETManager _cetManager;

}