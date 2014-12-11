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

package com.liferay.poshi.runner.util;

import com.sun.mail.imap.IMAPFolder;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

/**
 * @author Kwang Lee
 * @author Michael Hashimoto
 */
public class EmailCommands {

	public static void connectToEmailAccount(
			String emailAddress, String emailPassword)
		throws Exception {

		Properties imapProperties = System.getProperties();

		imapProperties.setProperty("mail.store.protocol", "imaps");

		_imapSession = Session.getInstance(imapProperties);

		Store store = _imapSession.getStore("imaps");

		store.connect("imap.gmail.com", emailAddress, emailPassword);

		_imapFolder = (IMAPFolder)store.getFolder("Inbox");

		_imapFolder.open(Folder.READ_WRITE);

		Properties smtpProperties = System.getProperties();

		smtpProperties.put("mail.smtp.auth", "true");
		smtpProperties.put("mail.smtp.host", "smtp.gmail.com");
		smtpProperties.put("mail.smtp.password", emailPassword);
		smtpProperties.put("mail.smtp.port", "587");
		smtpProperties.put("mail.smtp.starttls.enable", "true");
		smtpProperties.put("mail.smtp.user", emailAddress);

		_smtpSession = Session.getDefaultInstance(smtpProperties);

		_transport = _smtpSession.getTransport("smtp");

		_transport.connect("smtp.gmail.com", emailAddress, emailPassword);
	}

	public static void deleteAllEmails() throws Exception {
		Message[] messages = _imapFolder.getMessages();

		for (Message message : messages) {
			message.setFlag(Flags.Flag.DELETED, true);
		}

		_imapFolder.close(true);
	}

	public static String getEmailBody(int index) throws Exception {
		Message message = _imapFolder.getMessage(index);

		String body = (String)message.getContent();

		return body.trim();
	}

	public static String getEmailSubject(int index) throws Exception {
		Message message = _imapFolder.getMessage(index);

		String subject = message.getSubject();

		return subject;
	}

	public static void replyToEmail(String to, String body) throws Exception {
		Message message = _imapFolder.getMessage(1);

		Message replyMessage = message.reply(false);

		replyMessage.setRecipient(RecipientType.TO, new InternetAddress(to));
		replyMessage.setText(body);

		_transport.sendMessage(
			replyMessage, replyMessage.getRecipients(RecipientType.TO));

		_transport.close();
	}

	public static void sendEmail(String to, String subject, String body)
		throws Exception {

		Message message = new MimeMessage(_smtpSession);

		message.addRecipient(RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject);
		message.setText(body);

		message.saveChanges();

		_transport.sendMessage(message, message.getAllRecipients());

		_transport.close();
	}

	private static IMAPFolder _imapFolder;
	private static Session _imapSession;
	private static Session _smtpSession;
	private static Transport _transport;

}