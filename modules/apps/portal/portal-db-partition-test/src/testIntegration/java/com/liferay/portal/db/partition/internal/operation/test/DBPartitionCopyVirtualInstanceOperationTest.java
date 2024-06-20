/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.operation.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author István András Dézsi
 */
@RunWith(Arquillian.class)
public class DBPartitionCopyVirtualInstanceOperationTest
	extends BaseVirtualInstanceOperationTestCase {

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
					"name=T\"testName\"\nvirtualHostname=",
					"T\"testVirtualHostname\"\nwebId=",
					"T\"testWebId\"\nsourceCompanyId=L\"", COMPANY_IDS[0],
					"\"\ndestinationCompanyId=L\"", COMPANY_IDS[1], "\"\n"));

			assertLog(
				logCapture,
				"Virtual instance with company ID " + COMPANY_IDS[1] +
					" already exists");
		}

		assertConfigurationIsDeletedAfterDeploy(_PID);
	}

	private static final String _PID =
		"com.liferay.portal.db.partition.internal.configuration." +
			"DBPartitionCopyVirtualInstanceConfiguration";

}