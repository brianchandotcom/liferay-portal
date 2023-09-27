/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.messaging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class SynchronousDestinationDBPartitionMessageBusInterceptorTest
	extends BaseDBPartitionMessageBusInterceptorTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		setUpClass(DestinationConfiguration.DESTINATION_TYPE_SYNCHRONOUS);
	}

}