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

package com.liferay.portal.kernel.test.util;

import com.dumbster.smtp.MailMessage;
import com.dumbster.smtp.SmtpServer;
import com.dumbster.smtp.SmtpServerFactory;
import com.dumbster.smtp.mailstores.RollingMailStore;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Manuel de la Peña
 * @author José Manuel Navarro
 */
public class MailServiceTestUtil {

	public static void clearMessages() {
		_smtpServer.clearMessages();
	}

	public static int getInboxSize() {
		return _smtpServer.getEmailCount();
	}

	public static MailMessage getLastMailMessage() {
		if (_smtpServer.getEmailCount() > 0) {
			return _smtpServer.getMessage(_smtpServer.getEmailCount() - 1);
		}

		throw new IndexOutOfBoundsException(
			"There are no messages in the inbox");
	}

	public static List<MailMessage> getMailMessages(
		String headerName, String headerValue) {

		List<MailMessage> mailMessages = new ArrayList<>();

		for (int i = 0; i < _smtpServer.getEmailCount(); ++i) {
			MailMessage message = _smtpServer.getMessage(i);

			if (headerName.equals("Body")) {
				String body = message.getBody();

				if (body.equals(headerValue)) {
					mailMessages.add(message);
				}
			}
			else {
				String messageHeaderValue = message.getFirstHeaderValue(
					headerName);

				if (messageHeaderValue.equals(headerValue)) {
					mailMessages.add(message);
				}
			}
		}

		return mailMessages;
	}

	public static boolean lastMailMessageContains(String text) {
		MailMessage mailMessage = getLastMailMessage();

		String bodyMailMessage = mailMessage.getBody();

		return bodyMailMessage.contains(text);
	}

	public static void start() {
		if (_smtpServer != null) {
			throw new IllegalStateException("Server is already running");
		}

		_smtpServer = new SmtpServer();

		_smtpServer.setMailStore(
			new RollingMailStore() {

				@Override
				public void addMessage(MailMessage message) {
					try {
						List<MailMessage> receivedMail =
							ReflectionTestUtil.getFieldValue(
								this, "receivedMail");

						receivedMail.add(message);

						if (getEmailCount() > 100) {
							receivedMail.remove(0);
						}
					}
					catch (Exception e) {
						throw new RuntimeException(e);
					}
				}

			});
		_smtpServer.setPort(
			GetterUtil.getInteger(
				PropsUtil.get(PropsKeys.MAIL_SESSION_MAIL_SMTP_PORT)));
		_smtpServer.setThreaded(false);

		try {
			ReflectionTestUtil.invoke(
				SmtpServerFactory.class, "startServerThread",
				new Class<?>[] {SmtpServer.class}, _smtpServer);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void stop() {
		if ((_smtpServer != null) && _smtpServer.isStopped()) {
			throw new IllegalStateException("Server is already stopped");
		}

		_smtpServer.stop();

		_smtpServer = null;
	}

	private static SmtpServer _smtpServer;

}