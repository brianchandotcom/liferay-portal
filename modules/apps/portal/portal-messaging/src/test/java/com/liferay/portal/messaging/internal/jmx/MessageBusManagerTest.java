/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.messaging.internal.jmx;

import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
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

		ReflectionTestUtil.setFieldValue(
			messageBusManager, "_messageBus",
			ProxyFactory.newDummyInstance(MessageBus.class));

		ObjectName objectName = new ObjectName(
			"com.liferay.portal.messaging:classification=message_bus," +
				"name=MessageBusManager");

		mBeanServer.registerMBean(messageBusManager, objectName);

		Assert.assertTrue(mBeanServer.isRegistered(objectName));
	}

}