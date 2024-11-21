/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.operation.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author István András Dézsi
 */
@RunWith(Arquillian.class)
public class DBPartitionCopyVirtualInstanceOperationTest
	extends BaseVirtualInstanceOperationTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseDBPartitionTestCase.setUpClass();

		_company = CompanyLocalServiceUtil.fetchCompanyByVirtualHost(
			TestPropsValues.COMPANY_WEB_ID);
	}

	@Override
	public String getComponentName() {
		return "DBPartitionCopyVirtualInstanceOperation";
	}

	@Test
	public void testDeployConfiguration() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.db.partition.internal.operation." +
					"DBPartitionCopyVirtualInstanceOperation",
				LoggerTestUtil.ERROR)) {

			deployConfiguration(
				_PID,
				StringBundler.concat(
					"destinationPartitionCompanyId=L\"",
					PortalInstancePool.getDefaultCompanyId(), "\"\n",
					"name=\"testName\"\nsourcePartitionCompanyId=L\"",
					_company.getCompanyId(), "\"\nvirtualHostname=",
					"\"testVirtualHostname\"\nwebId=\"testWebId\"\n"),
				false);
			assertLog(
				logCapture,
				"Virtual instance with company ID " +
					PortalInstancePool.getDefaultCompanyId() +
						" already exists");
		}

		assertConfigurationIsDeletedAfterDeploy(_PID);
	}

	@Test
	public void testDeployConfigurationWithoutDestinationCompanyId()
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.db.partition.internal.operation." +
					"DBPartitionCopyVirtualInstanceOperation",
				LoggerTestUtil.ERROR)) {

			long[] companyIds = PortalInstancePool.getCompanyIds();

			deployConfiguration(
				_PID,
				StringBundler.concat(
					"name=\"testName\"\nsourcePartitionCompanyId=L\"",
					_company.getCompanyId(), "\"\nvirtualHostname=",
					"\"testVirtualHostname\"\nwebId=\"testWebId\"\n"),
				true);

			Assert.assertEquals(
				companyIds.length + 1,
				PortalInstancePool.getCompanyIds().length);

			Assert.assertTrue(
				logCapture.getLogEntries(
				).isEmpty());
		}

		assertConfigurationIsDeletedAfterDeploy(_PID);
	}

	private static final String _PID =
		"com.liferay.portal.db.partition.internal.configuration." +
			"DBPartitionCopyVirtualInstanceConfiguration";

	private static Company _company;

}