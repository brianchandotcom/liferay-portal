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

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @author Kenji Heigel
 */
public class NotificationUtil {

	public static void sendEmail(
		String body, String from, String subject, String to) {

		String hostname = JenkinsResultsParserUtil.getHostName(null);

		sendEmail(
			JenkinsResultsParserUtil.combine(from, "@", hostname), from, to,
			subject, body);
	}

	public static void sendEmail(
		String senderEmail, String senderName, String recipientEmail,
		String subject, String body) {

		sendEmail(senderEmail, senderName, recipientEmail, subject, body, null);
	}

	public static void sendEmail(
		String senderEmail, String senderName, String recipientEmail,
		String subject, String body, String attachmentFileName) {

		Properties sessionProperties = System.getProperties();

		sessionProperties.put("mail.smtp.auth", "true");
		sessionProperties.put("mail.smtp.port", _DEFAULT_SMTP_PORT);
		sessionProperties.put("mail.smtp.starttls.enable", "true");
		sessionProperties.put("mail.transport.protocol", "smtp");

		Session session = Session.getDefaultInstance(sessionProperties);

		MimeMessage mimeMessage = new MimeMessage(session);

		try {
			mimeMessage.setFrom(new InternetAddress(senderEmail, senderName));
			mimeMessage.setRecipient(
				Message.RecipientType.TO, new InternetAddress(recipientEmail));
			mimeMessage.setSubject(subject);

			Multipart multipart = new MimeMultipart();

			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setContent(body, "text/html");

			multipart.addBodyPart(messageBodyPart);

			if ((attachmentFileName != null) &&
				!attachmentFileName.equals("")) {

				BodyPart attachmentBodyPart = new MimeBodyPart();

				DataSource source = new FileDataSource(attachmentFileName);

				attachmentBodyPart.setDataHandler(new DataHandler(source));

				attachmentBodyPart.setFileName(attachmentFileName);

				multipart.addBodyPart(attachmentBodyPart);
			}

			mimeMessage.setContent(multipart);

			mimeMessage.saveChanges();

			Transport transport = session.getTransport();

			Properties buildProperties =
				JenkinsResultsParserUtil.getBuildProperties();

			transport.connect(
				buildProperties.getProperty("email.smtp.server"),
				buildProperties.getProperty("email.smtp.username"),
				buildProperties.getProperty("email.smtp.password"));

			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

			System.out.println("Email sent to: " + recipientEmail);

			transport.close();
		}
		catch (IOException | MessagingException e) {
			System.out.println("Unable to send email.");
			System.out.println(e.getMessage());

			e.printStackTrace();
		}
	}

	private static final int _DEFAULT_SMTP_PORT = 587;

	static {
		Thread thread = Thread.currentThread();

		thread.setContextClassLoader(Message.class.getClassLoader());
	}

}