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

import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Michael C. Han
 * @author Miguel Pastor
 */
public class MessageBusManagerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testRegisterMBean() throws Exception {
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

		MessageBusManager messageBusManager = new MessageBusManager();

		messageBusManager.setMessageBus(
			ProxyFactory.newDummyInstance(MessageBus.class));

		ObjectName objectName = new ObjectName(
			"com.liferay.portal.messaging:classification=message_bus," +
				"name=MessageBusManager");

		mBeanServer.registerMBean(messageBusManager, objectName);

		Assert.assertTrue(mBeanServer.isRegistered(objectName));
	}

}