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
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author István András Dézsi
 */
@DataGuard(scope = DataGuard.Scope.NONE)
@RunWith(Arquillian.class)
public class DBPartitionCopyVirtualInstanceOperationTest
	extends BaseVirtualInstanceOperationTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseDBPartitionTestCase.setUpClass();

		_company = _companyLocalService.fetchCompanyByVirtualHost(
			TestPropsValues.COMPANY_WEB_ID);
	}

	@Override
	public String getComponentName() {
		return "DBPartitionCopyVirtualInstanceOperation";
	}

	@Test
	public void testDeployConfiguration() throws Exception {
		long[] companyIds = PortalInstancePool.getCompanyIds();

		try {
			deployConfiguration(
				_PID,
				StringBundler.concat(
					"name=\"testName\"\nsourcePartitionCompanyId=L\"",
					_company.getCompanyId(), "\"\nvirtualHostname=",
					"\"testVirtualHostname\"\nwebId=\"testWebId\"\n"));

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

	@Test
	public void testDeployConfigurationExistingDestinationCompanyId()
		throws Exception {

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
					"\"testVirtualHostname\"\nwebId=\"testWebId\"\n"));
			assertLog(
				logCapture,
				"Virtual instance with company ID " +
					PortalInstancePool.getDefaultCompanyId() +
						" already exists");
		}

		assertConfigurationIsDeletedAfterDeploy(_PID);
	}

	private static final String _PID =
		"com.liferay.portal.db.partition.internal.configuration." +
			"DBPartitionCopyVirtualInstanceConfiguration";

	private static Company _company;

	@Inject
	private static CompanyLocalService _companyLocalService;

}