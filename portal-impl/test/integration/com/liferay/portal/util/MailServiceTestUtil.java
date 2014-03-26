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

package com.liferay.portal.util;

import com.dumbster.smtp.MailMessage;
import com.dumbster.smtp.ServerOptions;
import com.dumbster.smtp.SmtpServer;
import com.dumbster.smtp.SmtpServerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Manuel de la Peña
 * @author José Manuel Navarro
 */
public class MailServiceTestUtil {

	public static int getInboxSize() {
		return _smtpServer.getEmailCount();
	}

	public static List<MailMessage> getMessages(
		String headerName, String headerValue) {

		List<MailMessage> messages = new ArrayList<MailMessage>();

		for (int i = 0; i < _smtpServer.getEmailCount(); ++i) {
			MailMessage message = _smtpServer.getMessage(i);

			if (headerName.equals("Body")) {
				String body = message.getBody();

				if (body.equals(headerValue)) {
					messages.add(message);
				}
			}
			else {
				String messageHeaderValue = message.getFirstHeaderValue(
					headerName);

				if (messageHeaderValue.equals(headerValue)) {
					messages.add(message);
				}
			}
		}

		return messages;
	}

	public static void start() {
		if (_smtpServer != null) {
			throw new IllegalStateException("Server is already running");
		}

		ServerOptions opts = new ServerOptions();
		opts.port = PropsValues.MAIL_SESSION_MAIL_SMTP_PORT;

		_smtpServer = SmtpServerFactory.startServer(opts);
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