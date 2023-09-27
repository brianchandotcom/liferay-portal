/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;
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

import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class NotificationUtil {

	public static void sendEmail(
		String body, String senderName, String subject,
		String recipientEmailAddress) {

		sendEmail(
			JenkinsResultsParserUtil.combine(
				senderName, "@", JenkinsResultsParserUtil.getHostName(null)),
			senderName, recipientEmailAddress, subject, body);
	}

	public static void sendEmail(
		String senderEmailAddress, String senderName,
		String recipientEmailAddress, String subject, String body) {

		sendEmail(
			senderEmailAddress, senderName, recipientEmailAddress, subject,
			body, null);
	}

	public static void sendEmail(
		String senderEmailAddress, String senderName,
		String recipientEmailAddress, String subject, String body,
		String attachmentFileName) {

		body = JenkinsResultsParserUtil.redact(body);
		subject = JenkinsResultsParserUtil.redact(subject);

		Properties sessionProperties = System.getProperties();

		sessionProperties.put("mail.smtp.auth", "true");
		sessionProperties.put("mail.smtp.port", 587);
		sessionProperties.put("mail.smtp.starttls.enable", "true");
		sessionProperties.put("mail.transport.protocol", "smtp");

		Session session = Session.getDefaultInstance(sessionProperties);

		MimeMessage mimeMessage = new MimeMessage(session);

		try {
			mimeMessage.setFrom(
				new InternetAddress(senderEmailAddress, senderName));
			mimeMessage.setRecipients(
				Message.RecipientType.TO, recipientEmailAddress);
			mimeMessage.setSubject(subject);

			Multipart multipart = new MimeMultipart();

			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setContent(body, "text/plain");

			multipart.addBodyPart(messageBodyPart);

			if ((attachmentFileName != null) &&
				!attachmentFileName.equals("")) {

				BodyPart attachmentBodyPart = new MimeBodyPart();

				DataSource source = new FileDataSource(attachmentFileName);

				attachmentBodyPart.setDataHandler(new DataHandler(source));

				File attachmentFile = new File(attachmentFileName);

				attachmentBodyPart.setFileName(attachmentFile.getName());

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

			System.out.println("Email sent to: " + recipientEmailAddress);

			transport.close();
		}
		catch (IOException | MessagingException exception) {
			System.out.println("Unable to send email.");
			System.out.println(exception.getMessage());

			exception.printStackTrace();
		}
	}

	public static void sendSlackNotification(
		String body, String channelName, String subject) {

		sendSlackNotification(
			body, channelName, ":liferay-ci:", subject, "Liferay CI");
	}

	public static void sendSlackNotification(
		String body, String channelName, String iconEmoji, String subject,
		String username) {

		body = JenkinsResultsParserUtil.redact(body);
		subject = JenkinsResultsParserUtil.redact(subject);

		String text = body;

		if (subject == null) {
			subject = "";
		}
		else {
			subject = subject.trim();

			if (!subject.isEmpty()) {
				subject = JenkinsResultsParserUtil.combine(
					"*", subject, "*\n\n");

				text = JenkinsResultsParserUtil.combine(
					subject, "> ", body.replaceAll("\n", "\n> "));
			}
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"channel", channelName
		).put(
			"icon_emoji", iconEmoji
		).put(
			"text", text
		).put(
			"username", username
		);

		try {
			Properties properties = JenkinsResultsParserUtil.getBuildProperties(
				true);

			JenkinsResultsParserUtil.toString(
				properties.getProperty("slack.webhook.url"),
				jsonObject.toString());
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	static {
		Thread thread = Thread.currentThread();

		thread.setContextClassLoader(Message.class.getClassLoader());
	}

}