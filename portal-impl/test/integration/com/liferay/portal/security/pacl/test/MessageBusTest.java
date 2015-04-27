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

package com.liferay.portal.security.pacl.test;

import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.test.rule.PACLTestRule;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Raymond Augé
 */
public class MessageBusTest {

	@ClassRule
	@Rule
	public static final PACLTestRule paclTestRule = new PACLTestRule();

	@Before
	public void setUp() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());
	}

	@Test
	public void testListen1() throws Exception {
		Object value = MessageBusUtil.sendSynchronousMessage(
			"liferay/test_pacl_listen_failure", "Listen Failure");

		Assert.assertNull(value);
	}

	@Test
	public void testListen2() throws Exception {
		Object value = MessageBusUtil.sendSynchronousMessage(
			"liferay/test_pacl_listen_success", "Listen Success");

		Assert.assertEquals("Listen Success", value);
	}

	@Test
	public void testSend1() throws Exception {
		try {
			MessageBusUtil.sendMessage(
				"liferay/test_pacl_send_failure", "Send Failure");

			Assert.fail();
		}
		catch (SecurityException se) {
		}
	}

	@Test
	public void testSend2() throws Exception {
		MessageBusUtil.sendMessage(
			"liferay/test_pacl_send_success", "Send Success");
	}

}