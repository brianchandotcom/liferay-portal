/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.configuration.persistence.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mariano Álvaro Sáiz
 */
@RunWith(Arquillian.class)
public class DBPartitionVirtualInstanceExtractionConfigurationModelListenerTest
	extends BaseConfigurationModelListenerTestCase {

	@Test
	public void testConfigurationIsDeletedAfterDeploy() throws Exception {
		testConfigurationIsDeletedAfterDeploy(_PID, "webId=T\"testWebId\"\n");
	}

	private static final String _PID =
		"com.liferay.portal.db.partition.internal.configuration." +
			"DBPartitionVirtualInstanceExtractionConfiguration";

}