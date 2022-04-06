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
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Michael C. Han
 */
public class DestinationStatisticsManagerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testRegisterMBean() throws Exception {
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

		Destination destination = Mockito.mock(Destination.class);

		Mockito.when(
			destination.getName()
		).thenReturn(
			"test"
		);

		ObjectName objectName = new ObjectName(
			"com.liferay.portal.messaging:classification=" +
				"messaging_destination,name=MessagingDestinationStatistics-" +
					destination.getName());

		mBeanServer.registerMBean(
			new DestinationStatisticsManager(destination), objectName);

		Assert.assertTrue(mBeanServer.isRegistered(objectName));
	}

}