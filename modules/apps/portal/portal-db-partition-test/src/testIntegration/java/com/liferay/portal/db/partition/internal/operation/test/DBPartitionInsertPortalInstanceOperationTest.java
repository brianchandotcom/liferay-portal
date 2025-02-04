/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.operation.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mariano Álvaro Sáiz
 */
@RunWith(Arquillian.class)
public class DBPartitionInsertPortalInstanceOperationTest
	extends BasePortalInstanceOperationTestCase {

	@Override
	public String getComponentName() {
		return "InsertPortalInstanceOperation";
	}

	@Test
	public void testDeployConfiguration() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.instances.internal.operation." +
					"InsertPortalInstanceOperation",
				LoggerTestUtil.ERROR)) {

			deployConfiguration(
				_PID,
				"newWebId=\"testNewWebId\"\ncompanyId=L\"" +
					PortalInstancePool.getDefaultCompanyId() + "\"\n");

			assertLog(
				logCapture,
				"Portal instance with company ID " +
					PortalInstancePool.getDefaultCompanyId() +
						" already exists");
		}

		assertConfigurationIsDeletedAfterDeploy(_PID);
	}

	private static final String _PID =
		"com.liferay.portal.instances.internal.configuration." +
			"InsertPortalInstanceConfiguration";

}