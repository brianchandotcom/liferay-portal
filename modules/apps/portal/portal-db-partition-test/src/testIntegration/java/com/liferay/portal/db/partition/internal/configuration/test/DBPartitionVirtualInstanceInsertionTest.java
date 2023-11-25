/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.util.PortalInstances;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mariano Álvaro Sáiz
 */
@RunWith(Arquillian.class)
public class DBPartitionVirtualInstanceInsertionTest
	extends BaseVirtualInstanceOperationTestCase {

	@Override
	public String getComponentName() {
		return "DBPartitionVirtualInstanceInsertion";
	}

	@Test
	public void testDeployConfiguration() throws Exception {
		deployConfiguration(
			_PID,
			"newWebId=T\"testNewWebId\"\ncompanyId=L\"" +
				PortalInstances.getDefaultCompanyId() + "\"\n");

		verifyConfigurationIsDeletedAfterDeploy(_PID);
	}

	private static final String _PID =
		"com.liferay.portal.db.partition.internal.configuration." +
			"DBPartitionVirtualInstanceInsertionConfiguration";

}