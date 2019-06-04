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

package com.liferay.message.boards.internal.util;

import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.petra.mail.JavaMailUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @author Sergio González
 */
public class MBMailUtil {

	public static final String MESSAGE_POP_PORTLET_PREFIX = "mb_message.";

	public static void collectPartContent(
			Part part, MBMailMessage mbMailMessage)
		throws Exception {

		Object partContent = _getPartContent(part);

		String contentType = StringUtil.toLowerCase(part.getContentType());

		if ((part.getDisposition() != null) &&
			StringUtil.equalsIgnoreCase(
				part.getDisposition(), MimeMessage.ATTACHMENT)) {

			if (_log.isDebugEnabled()) {
				_log.debug("Processing attachment");
			}

			byte[] bytes = null;

			if (partContent instanceof String) {
				String s = (String)partContent;

				bytes = s.getBytes();
			}
			else if (partContent instanceof InputStream) {
				bytes = JavaMailUtil.getBytes(part);
			}

			mbMailMessage.addBytes(part.getFileName(), bytes);
		}
		else {
			if (partContent instanceof MimeMultipart) {
				MimeMultipart mimeMultipart = (MimeMultipart)partContent;

				for (int i = 0; i < mimeMultipart.getCount(); i++) {
					BodyPart curPart = mimeMultipart.getBodyPart(i);

					collectPartContent(curPart, mbMailMessage);
				}
			}
			else if (partContent instanceof String) {
				Map<String, Object> options = new HashMap<>();

				options.put("emailPartToMBMessageBody", Boolean.TRUE);

				String messageBody = SanitizerUtil.sanitize(
					0, 0, 0, MBMessage.class.getName(), 0, contentType,
					Sanitizer.MODE_ALL, (String)partContent, options);

				if (contentType.startsWith(ContentTypes.TEXT_HTML)) {
					mbMailMessage.setHtmlBody(messageBody);
				}
				else {
					mbMailMessage.setPlainBody(messageBody);
				}
			}
		}
	}

	public static long getCategoryId(String messageIdString) {
		String[] parts = _getMessageIdStringParts(messageIdString);

		return GetterUtil.getLong(parts[0]);
	}

	public static long getMessageId(String messageIdString) {
		String[] parts = _getMessageIdStringParts(messageIdString);

		return GetterUtil.getLong(parts[1]);
	}

	public static int getMessageIdStringOffset() {
		if (PropsValues.POP_SERVER_SUBDOMAIN.length() == 0) {
			return 1;
		}

		return 0;
	}

	public static long getParentMessageId(Message message) throws Exception {
		long parentMessageId = -1;

		String parentMessageIdString = getParentMessageIdString(message);

		if (parentMessageIdString != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Parent header " + parentMessageIdString);
			}

			parentMessageId = getMessageId(parentMessageIdString);

			if (_log.isDebugEnabled()) {
				_log.debug("Parent message id " + parentMessageId);
			}
		}

		return parentMessageId;
	}

	public static String getParentMessageIdString(Message message)
		throws Exception {

		// If the previous block failed, try to get the parent message ID from
		// the "References" header as explained in
		// http://cr.yp.to/immhf/thread.html. Some mail clients such as Yahoo!
		// Mail use the "In-Reply-To" header, so we check that as well.

		String parentHeader = null;

		String[] references = message.getHeader("References");

		if (ArrayUtil.isNotEmpty(references)) {
			String reference = references[0];

			int x = reference.lastIndexOf(
				StringPool.LESS_THAN + MESSAGE_POP_PORTLET_PREFIX);

			if (x > -1) {
				int y = reference.indexOf(StringPool.GREATER_THAN, x);

				parentHeader = reference.substring(x, y + 1);
			}
		}

		if (parentHeader == null) {
			String[] inReplyToHeaders = message.getHeader("In-Reply-To");

			if (ArrayUtil.isNotEmpty(inReplyToHeaders)) {
				parentHeader = inReplyToHeaders[0];
			}
		}

		if (Validator.isNull(parentHeader) ||
			!parentHeader.startsWith(MESSAGE_POP_PORTLET_PREFIX, 1)) {

			parentHeader = _getParentMessageIdFromSubject(message);
		}

		return parentHeader;
	}

	public static String getReplyToAddress(
		long categoryId, long messageId, String mx,
		String defaultMailingListAddress) {

		if (PropsValues.POP_SERVER_SUBDOMAIN.length() <= 0) {
			return defaultMailingListAddress;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(MESSAGE_POP_PORTLET_PREFIX);
		sb.append(categoryId);
		sb.append(StringPool.PERIOD);
		sb.append(messageId);
		sb.append(StringPool.AT);
		sb.append(PropsValues.POP_SERVER_SUBDOMAIN);
		sb.append(StringPool.PERIOD);
		sb.append(mx);

		return sb.toString();
	}

	public static String getSubjectForEmail(MBMessage message)
		throws Exception {

		String subject = message.getSubject();

		if (subject.startsWith(MBMessageConstants.MESSAGE_SUBJECT_PREFIX_RE)) {
			return subject;
		}

		return MBMessageConstants.MESSAGE_SUBJECT_PREFIX_RE +
			message.getSubject();
	}

	public static String getSubjectWithoutMessageId(Message message)
		throws Exception {

		String subject = message.getSubject();

		String parentMessageId = _getParentMessageIdFromSubject(message);

		if (Validator.isNotNull(parentMessageId)) {
			int pos = subject.indexOf(parentMessageId);

			if (pos != -1) {
				subject = subject.substring(0, pos);
			}
		}

		return subject;
	}

	public static boolean hasMailIdHeader(Message message) throws Exception {
		String[] messageIds = message.getHeader("Message-ID");

		if (messageIds == null) {
			return false;
		}

		for (String messageId : messageIds) {
			if (Validator.isNotNull(PropsValues.POP_SERVER_SUBDOMAIN) &&
				messageId.contains(PropsValues.POP_SERVER_SUBDOMAIN)) {

				return true;
			}
		}

		return false;
	}

	private static String[] _getMessageIdStringParts(String messageIdString) {
		int start =
			messageIdString.indexOf(MESSAGE_POP_PORTLET_PREFIX) +
				MESSAGE_POP_PORTLET_PREFIX.length();

		int end = messageIdString.indexOf(CharPool.AT);

		return StringUtil.split(
			messageIdString.substring(start, end), CharPool.PERIOD);
	}

	private static String _getParentMessageIdFromSubject(Message message)
		throws Exception {

		if (message.getSubject() == null) {
			return null;
		}

		String parentMessageId = null;

		String subject = message.getSubject();

		int pos = subject.lastIndexOf(CharPool.LESS_THAN);

		if (pos != -1) {
			parentMessageId = subject.substring(pos);
		}

		return parentMessageId;
	}

	private static Object _getPartContent(Part part) throws Exception {

		// See LPS-56173

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(Part.class.getClassLoader());

			return part.getContent();
		}
		finally {
			currentThread.setContextClassLoader(classLoader);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(MBMailUtil.class);

}