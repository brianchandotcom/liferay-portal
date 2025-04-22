/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.operation.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
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
	public void testDeployConfigurationFileWithFF() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.instances.internal.operation." +
					"ExtractPortalInstanceOperation",
				LoggerTestUtil.ERROR)) {

			deployConfigurationFile(_PID, "extractCompanyId=L\"0\"\n");

			assertLog(
				logCapture, "Portal instance with company ID 0 does not exist");
		}

		assertConfigurationFileIsDeletedAfterDeploy(_PID);
	}

	@Test
	public void testDeployConfigurationFileWithoutFF() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.instances.internal.operation." +
					"BasePortalInstanceOperation",
				LoggerTestUtil.ERROR)) {

			deployConfigurationFile(_PID, "extractCompanyId=L\"0\"\n");

			assertLogException(
				logCapture, "Feature flag LPD-11342 is disabled");
		}

		assertConfigurationFileIsDeletedAfterDeploy(_PID);
	}

	@FeatureFlags("LPD-11342")
	@Test
	public void testDeployConfigurationWithFF() throws Exception {
		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					PortalInstancePool.getDefaultCompanyId());
			LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.instances.internal.operation." +
					"ExtractPortalInstanceOperation",
				LoggerTestUtil.ERROR)) {

			deployConfiguration(
				_PID,
				HashMapDictionaryBuilder.<String, Object>put(
					"extractCompanyId", 0
				).build());

			assertLog(
				logCapture, "Portal instance with company ID 0 does not exist");

			assertConfigurationIsDeletedAfterDeploy(_PID);
		}
	}

	@Test
	public void testDeployConfigurationWithoutFF() throws Exception {
		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					PortalInstancePool.getDefaultCompanyId());
			LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.instances.internal.operation." +
					"BasePortalInstanceOperation",
				LoggerTestUtil.ERROR)) {

			deployConfiguration(
				_PID,
				HashMapDictionaryBuilder.<String, Object>put(
					"extractCompanyId", 0
				).build());

			assertLogException(
				logCapture, "Feature flag LPD-11342 is disabled");

			assertConfigurationIsDeletedAfterDeploy(_PID);
		}
	}

	private static final String _PID =
		"com.liferay.portal.instances.internal.configuration." +
			"ExtractPortalInstanceConfiguration";

}