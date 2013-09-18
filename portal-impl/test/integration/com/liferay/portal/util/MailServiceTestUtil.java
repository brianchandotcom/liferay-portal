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

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;

import com.liferay.portal.kernel.mail.MailMessage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * @author Manuel de la Peña
 */
public class MailServiceTestUtil {

	public static MailMessage createMailMessage(
			String from, String to, String subject, String body, boolean isHtml)
		throws AddressException {

		InternetAddress fromInternetAddress = new InternetAddress(from);

		InternetAddress toInternetAddress = new InternetAddress(to);

		return new MailMessage(
			fromInternetAddress, toInternetAddress, subject, body, isHtml);
	}

	public static List<SmtpMessage> findMessagesByBody(String body) {
		Iterator<SmtpMessage> emailIter = _server.getReceivedEmail();

		List<SmtpMessage> messages = new ArrayList<SmtpMessage>();

		while (emailIter.hasNext()) {
			SmtpMessage email = emailIter.next();

			if (email.getBody().equals(body)) {
				messages.add(email);
			}
		}

		return messages;
	}

	public static List<SmtpMessage> findMessagesByDestination(String to) {
		return findMessagesByHeader("To", to);
	}

	public static List<SmtpMessage> findMessagesBySubject(String subject) {
		return findMessagesByHeader("Subject", subject);
	}

	public static int getInboxSize() {
		return _server.getReceivedEmailSize();
	}

	public static void start() {
		if (_server != null) {
			throw new IllegalStateException("Server is already running");
		}

		_server = SimpleSmtpServer.start(
			PropsValues.MAIL_SESSION_MAIL_SMTP_PORT);
	}

	public static void stop() {
		if ((_server != null) && _server.isStopped()) {
			throw new IllegalStateException("Server is already stopped");
		}

		_server.stop();

		_server = null;
	}

	protected static List<SmtpMessage> findMessagesByHeader(
		String header, String value) {

		Iterator<SmtpMessage> emailIter = _server.getReceivedEmail();

		List<SmtpMessage> messages = new ArrayList<SmtpMessage>();

		while (emailIter.hasNext()) {
			SmtpMessage email = emailIter.next();

			if (email.getHeaderValue(header).equals(value)) {
				messages.add(email);
			}
		}

		return messages;
	}

	private static SimpleSmtpServer _server;

}