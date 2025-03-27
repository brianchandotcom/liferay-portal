/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.operation.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.FeatureFlags;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mariano Álvaro Sáiz
 */
@RunWith(Arquillian.class)
public class DBPartitionExtractPortalInstanceOperationTest
	extends BasePortalInstanceOperationTestCase {

	@Override
	public String getComponentName() {
		return "ExtractPortalInstanceOperation";
	}

	@FeatureFlags("LPD-11342")
	@Test
	public void testDeployConfigurationWithFF() throws Exception {
		_testDeployConfiguration(
			"Portal instance with company ID 0 does not exist");
	}

	@Test
	public void testDeployConfigurationWithoutFF() throws Exception {
		_testDeployConfiguration("Feature flag LPD-11342 is disabled");
	}

	private void _testDeployConfiguration(String message) throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.instances.internal.operation." +
					"ExtractPortalInstanceOperation",
				LoggerTestUtil.ERROR)) {

			deployConfiguration(_PID, "extractCompanyId=L\"0\"\n");

			assertLog(logCapture, message);
		}

		assertConfigurationIsDeletedAfterDeploy(_PID);
	}

	private static final String _PID =
		"com.liferay.portal.instances.internal.configuration." +
			"ExtractPortalInstanceConfiguration";

}