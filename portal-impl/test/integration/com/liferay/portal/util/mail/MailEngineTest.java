/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util.mail;

import com.dumbster.smtp.SmtpMessage;

import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.MailServiceTestUtil;
import com.liferay.util.mail.MailEngine;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MailEngineTest {

	@Before
	public void setUp() {
		MailServiceTestUtil.start();
	}

	@After
	public void tearDown() {
		MailServiceTestUtil.stop();
	}

	@Test
	public void testSendMail() throws Exception {
		MailEngine.send(
			MailServiceTestUtil.createMailMessage(
				"sender@here.com", "receiver@here.com", "Test", "Test Body",
				true));

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());

		List<SmtpMessage> messages = MailServiceTestUtil.findMessagesBySubject(
			"Test");

		Assert.assertEquals(1, messages.size());

		messages = MailServiceTestUtil.findMessagesByDestination(
			"receiver@here.com");

		Assert.assertEquals(1, messages.size());

		messages = MailServiceTestUtil.findMessagesByBody("Test Body");

		Assert.assertEquals(1, messages.size());
	}

}