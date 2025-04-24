/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.operation.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.FeatureFlags;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author István András Dézsi
 */
@RunWith(Arquillian.class)
public class DBPartitionCopyPortalInstanceOperationTest
	extends BasePortalInstanceOperationTestCase {

	@Override
	public String getComponentName() {
		return "CopyPortalInstanceOperation";
	}

	@FeatureFlags("LPD-11342")
	@Test
	public void testDeployConfigurationFileWithFF() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.instances.internal.operation." +
					"CopyPortalInstanceOperation",
				LoggerTestUtil.ERROR)) {

			deployConfigurationFile(
				_PID,
				StringBundler.concat(
					"destinationCompanyId=L\"",
					PortalInstancePool.getDefaultCompanyId(), "\"\n",
					"name=\"testName\"\nsourceCompanyId=L\"",
					TestPropsValues.getCompanyId(), "\"\nvirtualHostname=",
					"\"testVirtualHostname\"\nwebId=\"testWebId\"\n"));

			assertLog(
				logCapture,
				"Portal instance with company ID " +
					PortalInstancePool.getDefaultCompanyId() +
						" already exists");
		}

		assertConfigurationFileIsDeletedAfterDeploy(_PID);
	}

	@Test
	public void testDeployConfigurationFileWithoutFF() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.instances.internal.operation." +
					"BasePortalInstanceOperation",
				LoggerTestUtil.ERROR)) {

			deployConfigurationFile(
				_PID,
				StringBundler.concat(
					"name=\"testName\"\nsourceCompanyId=L\"",
					TestPropsValues.getCompanyId(), "\"\nvirtualHostname=",
					"\"testVirtualHostname\"\nwebId=\"testWebId\"\n"));

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
					"CopyPortalInstanceOperation",
				LoggerTestUtil.ERROR)) {

			deployConfiguration(
				_PID,
				HashMapDictionaryBuilder.<String, Object>put(
					"destinationCompanyId",
					PortalInstancePool.getDefaultCompanyId()
				).put(
					"name", "testName"
				).put(
					"sourceCompanyId", TestPropsValues.getCompanyId()
				).put(
					"virtualHostname", "testVirtualHostname"
				).put(
					"webId", "testWebId"
				).build());

			assertLog(
				logCapture,
				"Portal instance with company ID " +
					PortalInstancePool.getDefaultCompanyId() +
						" already exists");

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
					"name", "testName"
				).put(
					"sourceCompanyId", TestPropsValues.getCompanyId()
				).put(
					"virtualHostname", "testVirtualHostname"
				).put(
					"webId", "testWebId"
				).build());

			assertLogException(
				logCapture, "Feature flag LPD-11342 is disabled");

			assertConfigurationIsDeletedAfterDeploy(_PID);
		}
	}

	private static final String _PID =
		"com.liferay.portal.instances.internal.configuration." +
			"CopyPortalInstanceConfiguration";

}