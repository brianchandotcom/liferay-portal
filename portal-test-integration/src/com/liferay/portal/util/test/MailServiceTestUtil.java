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

package com.liferay.portal.util.test;

import com.dumbster.smtp.MailMessage;

import com.liferay.portal.test.mail.impl.MailMessageImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Manuel de la Peña
 * @author José Manuel Navarro
 * @deprecated As of 2.1.0, replaced by {@link
 *             com.liferay.portal.test.mail.MailServiceTestUtil}
 */
@Deprecated
public class MailServiceTestUtil {

	public static void clearMessages() {
		com.liferay.portal.test.mail.MailServiceTestUtil.clearMessages();
	}

	public static int getInboxSize() {
		return com.liferay.portal.test.mail.MailServiceTestUtil.getInboxSize();
	}

	public static MailMessage getLastMailMessage() {
		MailMessageImpl mailMessageImpl =
			(MailMessageImpl)
				com.liferay.portal.test.mail.MailServiceTestUtil.
					getLastMailMessage();

		return mailMessageImpl.getMailMessage();
	}

	public static List<MailMessage> getMailMessages(
		String headerName, String headerValue) {

		List<com.liferay.portal.test.mail.MailMessage> liferayMailMessages =
			com.liferay.portal.test.mail.MailServiceTestUtil.getMailMessages(
				headerName, headerValue);

		List<MailMessage> mailMessages = new ArrayList<>(
			liferayMailMessages.size());

		for (com.liferay.portal.test.mail.MailMessage mailMessage :
				liferayMailMessages) {

			mailMessages.add(((MailMessageImpl)mailMessage).getMailMessage());
		}

		return mailMessages;
	}

	public static boolean lastMailMessageContains(String text) {
		return com.liferay.portal.test.mail.MailServiceTestUtil.
			lastMailMessageContains(text);
	}

	public static void start() throws Exception {
		com.liferay.portal.test.mail.MailServiceTestUtil.start();
	}

	public static void stop() throws Exception {
		com.liferay.portal.test.mail.MailServiceTestUtil.stop();
	}

}