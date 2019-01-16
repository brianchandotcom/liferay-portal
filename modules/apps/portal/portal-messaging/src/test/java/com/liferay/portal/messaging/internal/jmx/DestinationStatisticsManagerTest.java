/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.messaging.internal.jmx;

import com.liferay.portal.kernel.messaging.Destination;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Michael C. Han
 */
@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
public class DestinationStatisticsManagerTest {

	@Before
	public void setUp() throws Exception {
		_mBeanServer = ManagementFactory.getPlatformMBeanServer();
	}

	@Test
	public void testRegisterMBean() throws Exception {
		PowerMockito.when(
			_destination.getName()
		).thenReturn(
			"test"
		);

		ObjectName objectName = new ObjectName(
			"com.liferay.portal.messaging:classification=" +
				"messaging_destination,name=MessagingDestinationStatistics-" +
					_destination.getName());

		_mBeanServer.registerMBean(
			new DestinationStatisticsManager(_destination), objectName);

		Assert.assertTrue(_mBeanServer.isRegistered(objectName));
	}

	@Mock
	private Destination _destination;

	private MBeanServer _mBeanServer;

}