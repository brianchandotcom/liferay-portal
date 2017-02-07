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

package com.liferay.portal.util.mail;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.portal.util.test.MailServiceTestUtil;
import com.liferay.util.mail.MailEngine;

import java.util.List;

import javax.mail.internet.InternetAddress;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
@Sync
public class MailEngineTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Test
	public void testSendMail() throws Exception {
		MailMessage mailMessage = new MailMessage(
			new InternetAddress("from@test.com"),
			new InternetAddress("to@test.com"), "Hello",
			"My name is Inigo Montoya.", true);

		MailEngine.send(mailMessage);

		Assert.assertEquals(1, MailServiceTestUtil.getInboxSize());

		List<com.dumbster.smtp.MailMessage> mailMessages =
			MailServiceTestUtil.getMailMessages(
				"Body", "My name is Inigo Montoya.");

		Assert.assertEquals(mailMessages.toString(), 1, mailMessages.size());

		mailMessages = MailServiceTestUtil.getMailMessages("Subject", "Hello");

		Assert.assertEquals(mailMessages.toString(), 1, mailMessages.size());

		mailMessages = MailServiceTestUtil.getMailMessages("To", "to@test.com");

		Assert.assertEquals(mailMessages.toString(), 1, mailMessages.size());
	}

}