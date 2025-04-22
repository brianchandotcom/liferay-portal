/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.operation.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author István András Dézsi
 */
@RunWith(Arquillian.class)
public class DBPartitionCopyPortalInstanceOperationTest
	extends BasePortalInstanceOperationTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseDBPartitionTestCase.setUpClass();

		_company = _companyLocalService.fetchCompanyByVirtualHost(
			TestPropsValues.COMPANY_WEB_ID);
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Override
	public String getComponentName() {
		return "CopyPortalInstanceOperation";
	}

	@FeatureFlags("LPD-11342")
	@Test
	public void testDeployConfiguration() throws Exception {
		long[] companyIds = PortalInstancePool.getCompanyIds();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					PortalInstancePool.getDefaultCompanyId())) {

			deployConfiguration(
				_PID,
				HashMapDictionaryBuilder.<String, Object>put(
					"name", "testName"
				).put(
					"sourceCompanyId", _company.getCompanyId()
				).put(
					"virtualHostname", "testVirtualHostname"
				).put(
					"webId", "testWebId"
				).build());

			Assert.assertEquals(
				companyIds.length + 1,
				PortalInstancePool.getCompanyIds().length);

			assertConfigurationIsDeletedAfterDeploy(_PID);
		}
		finally {
			Company company = _companyLocalService.fetchCompanyByVirtualHost(
				"testVirtualHostname");

			if (company != null) {
				_companyLocalService.deleteCompany(company);
			}
		}
	}

	@FeatureFlags("LPD-11342")
	@Test
	public void testDeployConfigurationExistingDestinationCompanyIdWithFF()
		throws Exception {

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
					"sourceCompanyId", _company.getCompanyId()
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
	public void testDeployConfigurationExistingDestinationCompanyIdWithoutFF()
		throws Exception {

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
					"destinationCompanyId",
					PortalInstancePool.getDefaultCompanyId()
				).put(
					"name", "testName"
				).put(
					"sourceCompanyId", _company.getCompanyId()
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

	@FeatureFlags("LPD-11342")
	@Test
	public void testDeployConfigurationFile() throws Exception {
		long[] companyIds = PortalInstancePool.getCompanyIds();

		try {
			deployConfigurationFile(
				_PID,
				StringBundler.concat(
					"name=\"testName\"\nsourceCompanyId=L\"",
					_company.getCompanyId(), "\"\nvirtualHostname=",
					"\"testVirtualHostname\"\nwebId=\"testWebId\"\n"));

			Assert.assertEquals(
				companyIds.length + 1,
				PortalInstancePool.getCompanyIds().length);

			assertConfigurationFileIsDeletedAfterDeploy(_PID);
		}
		finally {
			Company company = _companyLocalService.fetchCompanyByVirtualHost(
				"testVirtualHostname");

			if (company != null) {
				_companyLocalService.deleteCompany(company);
			}
		}
	}

	@FeatureFlags("LPD-11342")
	@Test
	public void testDeployConfigurationFileExistingDestinationCompanyIdWithFF()
		throws Exception {

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
					_company.getCompanyId(), "\"\nvirtualHostname=",
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
	public void testDeployConfigurationFileExistingDestinationCompanyIdWithoutFF()
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.instances.internal.operation." +
					"BasePortalInstanceOperation",
				LoggerTestUtil.ERROR)) {

			deployConfigurationFile(
				_PID,
				StringBundler.concat(
					"destinationCompanyId=L\"",
					PortalInstancePool.getDefaultCompanyId(), "\"\n",
					"name=\"testName\"\nsourceCompanyId=L\"",
					_company.getCompanyId(), "\"\nvirtualHostname=",
					"\"testVirtualHostname\"\nwebId=\"testWebId\"\n"));

			assertLogException(
				logCapture, "Feature flag LPD-11342 is disabled");
		}

		assertConfigurationFileIsDeletedAfterDeploy(_PID);
	}

	private static final String _PID =
		"com.liferay.portal.instances.internal.configuration." +
			"CopyPortalInstanceConfiguration";

	private static Company _company;

	@Inject
	private static CompanyLocalService _companyLocalService;

}