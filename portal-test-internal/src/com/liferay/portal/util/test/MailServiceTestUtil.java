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
import com.dumbster.smtp.SmtpServer;
import com.dumbster.smtp.SmtpServerFactory;
import com.dumbster.smtp.mailstores.RollingMailStore;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SocketUtil;
import com.liferay.portal.kernel.util.SocketUtil.ServerSocketConfigurator;

import java.io.IOException;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;

import java.nio.channels.ServerSocketChannel;

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
		MailMessage[] mailMessages = _smtpServer.getMessages();

		if (mailMessages.length > 0) {
			return mailMessages[mailMessages.length - 1];
		}

		throw new IndexOutOfBoundsException(
			"There are no messages in the inbox");
	}

	public static List<MailMessage> getMailMessages(
		String headerName, String headerValue) {

		List<MailMessage> mailMessages = new ArrayList<>();

		for (MailMessage mailMessage : _smtpServer.getMessages()) {
			if (headerName.equals("Body")) {
				String body = mailMessage.getBody();

				if (body.equals(headerValue)) {
					mailMessages.add(mailMessage);
				}
			}
			else {
				String messageHeaderValue = mailMessage.getFirstHeaderValue(
					headerName);

				if (messageHeaderValue.equals(headerValue)) {
					mailMessages.add(mailMessage);
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

	public static void start() throws Exception {
		if (_smtpServer != null) {
			throw new IllegalStateException("Server is already running");
		}

		int smtpPort = _getFreePort();

		_prefsPropsTemporarySwapper = new PrefsPropsTemporarySwapper(
			PropsKeys.MAIL_SESSION_MAIL_SMTP_PORT, smtpPort,
			PropsKeys.MAIL_SESSION_MAIL, true);

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
		_smtpServer.setPort(smtpPort);

		_smtpServer.setThreaded(false);

		ReflectionTestUtil.invoke(
			SmtpServerFactory.class, "startServerThread",
			new Class<?>[] {SmtpServer.class}, _smtpServer);

		MailServiceUtil.clearSession();
	}

	public static void stop() throws Exception {
		if ((_smtpServer != null) && _smtpServer.isStopped()) {
			throw new IllegalStateException("Server is already stopped");
		}

		_smtpServer.stop();

		_smtpServer = null;

		_prefsPropsTemporarySwapper.close();

		MailServiceUtil.clearSession();
	}

	private static int _getFreePort() throws IOException {
		try (ServerSocketChannel serverSocketChannel =
				SocketUtil.createServerSocketChannel(
					InetAddress.getLocalHost(), _START_PORT,
					new ServerSocketConfigurator() {

						@Override
						public void configure(ServerSocket serverSocket)
							throws SocketException {

							serverSocket.setReuseAddress(true);
						}

					})) {

			ServerSocket serverSocket = serverSocketChannel.socket();

			return serverSocket.getLocalPort();
		}
	}

	private static final int _START_PORT = 3241;

	private static PrefsPropsTemporarySwapper _prefsPropsTemporarySwapper;
	private static SmtpServer _smtpServer;

}