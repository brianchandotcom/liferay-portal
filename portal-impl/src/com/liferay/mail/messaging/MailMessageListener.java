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

package com.liferay.mail.messaging;

import com.liferay.mail.util.HookFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.security.auth.EmailAddressGenerator;
import com.liferay.portal.security.auth.EmailAddressGeneratorFactory;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.mail.MailEngine;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 * @author Zsolt Balogh
 */
public class MailMessageListener extends BaseMessageListener {

	protected void doMailMessage(MailMessage mailMessage) throws Exception {
		InternetAddress from = filterInternetAddress(mailMessage.getFrom());

		if (from == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Email from address is not configured email is not sent.");
			}

			return;
		}

		mailMessage.setFrom(from);

		InternetAddress[] to = filterInternetAddresses(mailMessage.getTo());

		mailMessage.setTo(to);

		InternetAddress[] cc = filterInternetAddresses(mailMessage.getCC());

		mailMessage.setCC(cc);

		InternetAddress[] bcc = filterInternetAddresses(mailMessage.getBCC());

		InternetAddress[] auditTrail = InternetAddress.parse(
			PropsValues.MAIL_AUDIT_TRAIL);

		if (auditTrail.length > 0) {
			if (ArrayUtil.isNotEmpty(bcc)) {
				for (InternetAddress internetAddress : auditTrail) {
					bcc = ArrayUtil.append(bcc, internetAddress);
				}
			}
			else {
				bcc = auditTrail;
			}
		}

		mailMessage.setBCC(bcc);

		InternetAddress[] bulkAddresses = filterInternetAddresses(
			mailMessage.getBulkAddresses());

		mailMessage.setBulkAddresses(bulkAddresses);

		InternetAddress[] replyTo = filterInternetAddresses(
			mailMessage.getReplyTo());

		mailMessage.setReplyTo(replyTo);

		if (ArrayUtil.isNotEmpty(to) || ArrayUtil.isNotEmpty(cc) ||
			ArrayUtil.isNotEmpty(bcc) || ArrayUtil.isNotEmpty(bulkAddresses)) {

			MailEngine.send(mailMessage);
		}
	}

	protected void doMethodHandler(MethodHandler methodHandler)
		throws Exception {

		methodHandler.invoke(HookFactory.getInstance());
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		Object payload = message.getPayload();

		if (payload instanceof MailMessage) {
			doMailMessage((MailMessage)payload);
		}
		else if (payload instanceof MethodHandler) {
			doMethodHandler((MethodHandler)payload);
		}
	}

	protected InternetAddress filterInternetAddress(
		InternetAddress internetAddress) {

		EmailAddressGenerator emailAddressGenerator =
			EmailAddressGeneratorFactory.getInstance();

		if (emailAddressGenerator.isFake(internetAddress.getAddress())) {
			return null;
		}

		return internetAddress;
	}

	protected InternetAddress[] filterInternetAddresses(
		InternetAddress[] internetAddresses) {

		if (internetAddresses == null) {
			return null;
		}

		List<InternetAddress> filteredInternetAddresses = new ArrayList<>(
			internetAddresses.length);

		for (InternetAddress internetAddress : internetAddresses) {
			InternetAddress filteredInternetAddress = filterInternetAddress(
				internetAddress);

			if (filteredInternetAddress != null) {
				filteredInternetAddresses.add(filteredInternetAddress);
			}
		}

		return filteredInternetAddresses.toArray(
			new InternetAddress[filteredInternetAddresses.size()]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MailMessageListener.class);

}