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

import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.petra.mail.JavaMailUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;

import javax.mail.BodyPart;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @author Sergio González
 */
public class MBMailUtil {

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
				bytes = ((String)partContent).getBytes();
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