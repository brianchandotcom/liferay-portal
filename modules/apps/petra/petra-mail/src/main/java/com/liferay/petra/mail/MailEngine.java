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

package com.liferay.petra.mail;

import com.liferay.mail.kernel.model.Account;
import com.liferay.mail.kernel.model.FileAttachment;
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.model.SMTPAccount;
import com.liferay.mail.kernel.service.MailServiceUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;

import java.net.SocketException;

import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import javax.mail.Address;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Jorge Ferrer
 * @author Neil Griffin
 * @author Thiago Moreira
 * @author Brett Swaim
 * @see    com.liferay.util.mail.MailEngine
 */
public class MailEngine {

	public static Session getSession() {
		return getSession(false);
	}

	public static Session getSession(Account account) {
		Properties properties = _getProperties(account);

		Session session = Session.getInstance(properties);

		if (_log.isDebugEnabled()) {
			session.setDebug(true);

			Properties sessionProperties = session.getProperties();

			sessionProperties.list(System.out);
		}

		return session;
	}

	public static Session getSession(boolean cache) {
		Session session = null;

		try {
			session = MailServiceUtil.getSession();
		}
		catch (SystemException se) {
			if (_log.isWarnEnabled()) {
				_log.warn(se, se);
			}

			session = InfrastructureUtil.getMailSession();
		}

		if (_log.isDebugEnabled()) {
			session.setDebug(true);

			Properties properties = session.getProperties();

			properties.list(System.out);
		}

		return session;
	}

	public static void send(byte[] bytes) throws MailEngineException {
		try {
			Session session = getSession();

			Message message = new MimeMessage(
				session, new UnsyncByteArrayInputStream(bytes));

			_send(session, message, null, _BATCH_SIZE);
		}
		catch (Exception e) {
			throw new MailEngineException(e);
		}
	}

	public static void send(
			InternetAddress from, InternetAddress to, String subject,
			String body)
		throws MailEngineException {

		send(
			from, new InternetAddress[] {to}, null, null, subject, body, false,
			null, null, null);
	}

	public static void send(
			InternetAddress from, InternetAddress to, String subject,
			String body, boolean htmlFormat)
		throws MailEngineException {

		send(
			from, new InternetAddress[] {to}, null, null, subject, body,
			htmlFormat, null, null, null);
	}

	public static void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			InternetAddress[] bcc, InternetAddress[] bulkAddresses,
			String subject, String body, boolean htmlFormat,
			InternetAddress[] replyTo, String messageId, String inReplyTo)
		throws MailEngineException {

		send(
			from, to, cc, bcc, bulkAddresses, subject, body, htmlFormat,
			replyTo, messageId, inReplyTo, null);
	}

	public static void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			InternetAddress[] bcc, InternetAddress[] bulkAddresses,
			String subject, String body, boolean htmlFormat,
			InternetAddress[] replyTo, String messageId, String inReplyTo,
			List<FileAttachment> fileAttachments)
		throws MailEngineException {

		send(
			from, to, cc, bcc, bulkAddresses, subject, body, htmlFormat,
			replyTo, messageId, inReplyTo, fileAttachments, null);
	}

	public static void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			InternetAddress[] bcc, InternetAddress[] bulkAddresses,
			String subject, String body, boolean htmlFormat,
			InternetAddress[] replyTo, String messageId, String inReplyTo,
			List<FileAttachment> fileAttachments, SMTPAccount smtpAccount)
		throws MailEngineException {

		send(
			from, to, cc, bcc, bulkAddresses, subject, body, htmlFormat,
			replyTo, messageId, inReplyTo, fileAttachments, smtpAccount, null);
	}

	public static void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			InternetAddress[] bcc, InternetAddress[] bulkAddresses,
			String subject, String body, boolean htmlFormat,
			InternetAddress[] replyTo, String messageId, String inReplyTo,
			List<FileAttachment> fileAttachments, SMTPAccount smtpAccount,
			InternetHeaders internetHeaders)
		throws MailEngineException {

		long startTime = System.currentTimeMillis();

		if (_log.isDebugEnabled()) {
			_log.debug("From: " + from);
			_log.debug("To: " + Arrays.toString(to));
			_log.debug("CC: " + Arrays.toString(cc));
			_log.debug("BCC: " + Arrays.toString(bcc));
			_log.debug("List Addresses: " + Arrays.toString(bulkAddresses));
			_log.debug("Subject: " + subject);
			_log.debug("Body: " + body);
			_log.debug("HTML Format: " + htmlFormat);
			_log.debug("Reply to: " + Arrays.toString(replyTo));
			_log.debug("Message ID: " + messageId);
			_log.debug("In Reply To: " + inReplyTo);

			if ((fileAttachments != null) && _log.isDebugEnabled()) {
				for (int i = 0; i < fileAttachments.size(); i++) {
					FileAttachment fileAttachment = fileAttachments.get(i);

					File file = fileAttachment.getFile();

					if (file == null) {
						continue;
					}

					_log.debug(
						StringBundler.concat(
							"Attachment ", i, " file ", file.getAbsolutePath(),
							" and file name ", fileAttachment.getFileName()));
				}
			}
		}

		try {
			InternetAddressUtil.validateAddress(from);

			if (ArrayUtil.isNotEmpty(to)) {
				InternetAddressUtil.validateAddresses(to);
			}

			if (ArrayUtil.isNotEmpty(cc)) {
				InternetAddressUtil.validateAddresses(cc);
			}

			if (ArrayUtil.isNotEmpty(bcc)) {
				InternetAddressUtil.validateAddresses(bcc);
			}

			if (ArrayUtil.isNotEmpty(replyTo)) {
				InternetAddressUtil.validateAddresses(replyTo);
			}

			if (ArrayUtil.isNotEmpty(bulkAddresses)) {
				InternetAddressUtil.validateAddresses(bulkAddresses);
			}

			Session session = null;

			if (smtpAccount == null) {
				session = getSession();
			}
			else {
				session = getSession(smtpAccount);
			}

			Message message = new LiferayMimeMessage(session);

			message.addHeader(
				"X-Auto-Response-Suppress", "AutoReply, DR, NDR, NRN, OOF, RN");

			message.setFrom(from);

			if (ArrayUtil.isNotEmpty(to)) {
				message.setRecipients(Message.RecipientType.TO, to);
			}

			if (ArrayUtil.isNotEmpty(cc)) {
				message.setRecipients(Message.RecipientType.CC, cc);
			}

			if (ArrayUtil.isNotEmpty(bcc)) {
				message.setRecipients(Message.RecipientType.BCC, bcc);
			}

			subject = GetterUtil.getString(subject);

			message.setSubject(_sanitizeCRLF(subject));

			if (ListUtil.isNotEmpty(fileAttachments)) {
				MimeMultipart rootMultipart = new MimeMultipart(
					_MULTIPART_TYPE_MIXED);

				MimeMultipart messageMultipart = new MimeMultipart(
					_MULTIPART_TYPE_ALTERNATIVE);

				MimeBodyPart messageBodyPart = new MimeBodyPart();

				messageBodyPart.setContent(messageMultipart);

				rootMultipart.addBodyPart(messageBodyPart);

				if (htmlFormat) {
					MimeBodyPart bodyPart = new MimeBodyPart();

					bodyPart.setContent(body, _TEXT_HTML);

					messageMultipart.addBodyPart(bodyPart);
				}
				else {
					MimeBodyPart bodyPart = new MimeBodyPart();

					bodyPart.setText(body);

					messageMultipart.addBodyPart(bodyPart);
				}

				for (FileAttachment fileAttachment : fileAttachments) {
					File file = fileAttachment.getFile();

					if (file == null) {
						continue;
					}

					MimeBodyPart mimeBodyPart = new MimeBodyPart();

					DataSource dataSource = new FileDataSource(file);

					mimeBodyPart.setDataHandler(new DataHandler(dataSource));

					mimeBodyPart.setDisposition(Part.ATTACHMENT);

					if (fileAttachment.getFileName() != null) {
						mimeBodyPart.setFileName(fileAttachment.getFileName());
					}
					else {
						mimeBodyPart.setFileName(file.getName());
					}

					rootMultipart.addBodyPart(mimeBodyPart);
				}

				message.setContent(rootMultipart);

				message.saveChanges();
			}
			else {
				if (htmlFormat) {
					message.setContent(body, _TEXT_HTML);
				}
				else {
					message.setContent(body, _TEXT_PLAIN);
				}
			}

			message.setSentDate(new Date());

			if (ArrayUtil.isNotEmpty(replyTo)) {
				message.setReplyTo(replyTo);
			}

			if (messageId != null) {
				message.setHeader("Message-ID", _sanitizeCRLF(messageId));
			}

			if (inReplyTo != null) {
				message.setHeader("In-Reply-To", _sanitizeCRLF(inReplyTo));
				message.setHeader("References", _sanitizeCRLF(inReplyTo));
			}

			if (internetHeaders != null) {
				Enumeration enumeration = internetHeaders.getAllHeaders();

				while (enumeration.hasMoreElements()) {
					Header header = (Header)enumeration.nextElement();

					message.setHeader(header.getName(), header.getValue());
				}
			}

			int batchSize = GetterUtil.getInteger(
				PropsUtil.get(PropsKeys.MAIL_BATCH_SIZE), _BATCH_SIZE);

			_send(session, message, bulkAddresses, batchSize);
		}
		catch (SendFailedException sfe) {
			_log.error(sfe, sfe);

			if (_isThrowsExceptionOnFailure()) {
				throw new MailEngineException(sfe);
			}
		}
		catch (Exception e) {
			throw new MailEngineException(e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Sending mail takes " +
					(System.currentTimeMillis() - startTime) + " ms");
		}
	}

	public static void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			InternetAddress[] bcc, String subject, String body)
		throws MailEngineException {

		send(from, to, cc, bcc, subject, body, false, null, null, null);
	}

	public static void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			InternetAddress[] bcc, String subject, String body,
			boolean htmlFormat, InternetAddress[] replyTo, String messageId,
			String inReplyTo)
		throws MailEngineException {

		send(
			from, to, cc, bcc, null, subject, body, htmlFormat, replyTo,
			messageId, inReplyTo, null);
	}

	public static void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			String subject, String body)
		throws MailEngineException {

		send(from, to, cc, null, subject, body, false, null, null, null);
	}

	public static void send(
			InternetAddress from, InternetAddress[] to, InternetAddress[] cc,
			String subject, String body, boolean htmlFormat)
		throws MailEngineException {

		send(from, to, cc, null, subject, body, htmlFormat, null, null, null);
	}

	public static void send(
			InternetAddress from, InternetAddress[] to, String subject,
			String body)
		throws MailEngineException {

		send(from, to, null, null, subject, body, false, null, null, null);
	}

	public static void send(
			InternetAddress from, InternetAddress[] to, String subject,
			String body, boolean htmlFormat)
		throws MailEngineException {

		send(from, to, null, null, subject, body, htmlFormat, null, null, null);
	}

	public static void send(MailMessage mailMessage)
		throws MailEngineException {

		send(
			mailMessage.getFrom(), mailMessage.getTo(), mailMessage.getCC(),
			mailMessage.getBCC(), mailMessage.getBulkAddresses(),
			mailMessage.getSubject(), mailMessage.getBody(),
			mailMessage.isHTMLFormat(), mailMessage.getReplyTo(),
			mailMessage.getMessageId(), mailMessage.getInReplyTo(),
			mailMessage.getFileAttachments(), mailMessage.getSMTPAccount(),
			mailMessage.getInternetHeaders());
	}

	public static void send(String from, String to, String subject, String body)
		throws MailEngineException {

		try {
			send(
				new InternetAddress(from), new InternetAddress(to), subject,
				body);
		}
		catch (AddressException ae) {
			throw new MailEngineException(ae);
		}
	}

	private static Address[] _getBatchAddresses(
		Address[] addresses, int index, int batchSize) {

		if ((batchSize == _BATCH_SIZE) && (index == 0)) {
			return addresses;
		}
		else if (batchSize == _BATCH_SIZE) {
			return null;
		}

		int start = index * batchSize;

		if (start > addresses.length) {
			return null;
		}

		int end = (index + 1) * batchSize;

		if (end > addresses.length) {
			end = addresses.length;
		}

		return ArrayUtil.subset(addresses, start, end);
	}

	private static Properties _getProperties(Account account) {
		Properties properties = new Properties();

		String protocol = account.getProtocol();

		properties.setProperty("mail.transport.protocol", protocol);
		properties.setProperty("mail." + protocol + ".host", account.getHost());
		properties.setProperty(
			"mail." + protocol + ".port", String.valueOf(account.getPort()));

		if (account.isRequiresAuthentication()) {
			properties.setProperty("mail." + protocol + ".auth", "true");
			properties.setProperty(
				"mail." + protocol + ".user", account.getUser());
			properties.setProperty(
				"mail." + protocol + ".password", account.getPassword());
		}

		if (account.isSecure()) {
			properties.setProperty(
				"mail." + protocol + ".socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
			properties.setProperty(
				"mail." + protocol + ".socketFactory.fallback", "false");
			properties.setProperty(
				"mail." + protocol + ".socketFactory.port",
				String.valueOf(account.getPort()));
		}

		return properties;
	}

	private static String _getSMTPProperty(Session session, String suffix) {
		String protocol = GetterUtil.getString(
			session.getProperty("mail.transport.protocol"));

		if (protocol.equals(Account.PROTOCOL_SMTPS)) {
			return session.getProperty("mail.smtps." + suffix);
		}

		return session.getProperty("mail.smtp." + suffix);
	}

	private static boolean _isThrowsExceptionOnFailure() {
		return GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.MAIL_THROWS_EXCEPTION_ON_FAILURE));
	}

	private static String _sanitizeCRLF(String text) {
		return StringUtil.replace(
			text, new char[] {CharPool.NEW_LINE, CharPool.RETURN},
			new char[] {CharPool.SPACE, CharPool.SPACE});
	}

	private static void _send(
			Session session, Message message, InternetAddress[] bulkAddresses,
			int batchSize)
		throws MailEngineException {

		try {
			boolean smtpAuth = GetterUtil.getBoolean(
				_getSMTPProperty(session, "auth"));
			String smtpHost = _getSMTPProperty(session, "host");
			int smtpPort = GetterUtil.getInteger(
				_getSMTPProperty(session, "port"), Account.PORT_SMTP);
			String user = _getSMTPProperty(session, "user");
			String password = _getSMTPProperty(session, "password");

			if (smtpAuth && Validator.isNotNull(user) &&
				Validator.isNotNull(password)) {

				String protocol = GetterUtil.getString(
					session.getProperty("mail.transport.protocol"),
					Account.PROTOCOL_SMTP);

				Transport transport = session.getTransport(protocol);

				transport.connect(smtpHost, smtpPort, user, password);

				Address[] addresses = null;

				if (ArrayUtil.isNotEmpty(bulkAddresses)) {
					addresses = bulkAddresses;
				}
				else {
					addresses = message.getAllRecipients();
				}

				for (int i = 0;; i++) {
					Address[] batchAddresses = _getBatchAddresses(
						addresses, i, batchSize);

					if (ArrayUtil.isEmpty(batchAddresses)) {
						break;
					}

					transport.sendMessage(message, batchAddresses);
				}

				transport.close();
			}
			else {
				if (ArrayUtil.isNotEmpty(bulkAddresses)) {
					int curBatch = 0;

					Address[] portion = _getBatchAddresses(
						bulkAddresses, curBatch, batchSize);

					while (ArrayUtil.isNotEmpty(portion)) {
						Transport.send(message, portion);

						curBatch++;

						portion = _getBatchAddresses(
							bulkAddresses, curBatch, batchSize);
					}
				}
				else {
					Transport.send(message);
				}
			}
		}
		catch (MessagingException me) {
			if (me.getNextException() instanceof SocketException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to connect to a valid mail server. Please " +
							"make sure one is properly configured: " +
								me.getMessage());
				}
			}
			else {
				LogUtil.log(
					_log, me, "Unable to send message: " + me.getMessage());
			}

			if (_isThrowsExceptionOnFailure()) {
				throw new MailEngineException(me);
			}
		}
	}

	private static final int _BATCH_SIZE = 0;

	private static final String _MULTIPART_TYPE_ALTERNATIVE = "alternative";

	private static final String _MULTIPART_TYPE_MIXED = "mixed";

	private static final String _TEXT_HTML = "text/html;charset=\"UTF-8\"";

	private static final String _TEXT_PLAIN = "text/plain;charset=\"UTF-8\"";

	private static final Log _log = LogFactoryUtil.getLog(MailEngine.class);

}