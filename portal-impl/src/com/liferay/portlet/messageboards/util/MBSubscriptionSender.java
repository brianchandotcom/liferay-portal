/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.Account;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.mail.SMTPAccount;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.SubscriptionSender;
import com.liferay.portlet.messageboards.NoSuchMailingListException;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.service.MBMailingListLocalServiceUtil;

import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 * @author Thiago Moreira
 */
public class MBSubscriptionSender extends SubscriptionSender {

	public void addMailingListSubscriber(long groupId, long categoryId)
		throws PortalException, SystemException {

		if (_calledAddMailingListSubscriber) {
			throw new IllegalArgumentException();
		}

		_calledAddMailingListSubscriber = true;

		MBMailingList mailingList = null;

		try {
			mailingList = MBMailingListLocalServiceUtil.getCategoryMailingList(
				groupId, categoryId);
		}
		catch (NoSuchMailingListException nsmle) {
			return;
		}

		if (!mailingList.isActive()) {
			return;
		}

		setFrom(mailingList.getOutEmailAddress(), null);

		if (mailingList.isOutCustom()) {
			String protocol = Account.PROTOCOL_SMTP;

			if (mailingList.isOutUseSSL()) {
				protocol = Account.PROTOCOL_SMTPS;
			}

			SMTPAccount smtpAccount = (SMTPAccount)Account.getInstance(
				protocol, mailingList.getOutServerPort());

			smtpAccount.setHost(mailingList.getOutServerName());
			smtpAccount.setUser(mailingList.getOutUserName());
			smtpAccount.setPassword(mailingList.getOutPassword());

			setSMTPAccount(smtpAccount);
		}

		setSubject(getMailingListSubject(subject, mailId));

		addRuntimeSubscribers(
			mailingList.getEmailAddress(), mailingList.getEmailAddress());
	}

	protected String getMailingListSubject(String subject, String mailId) {
		return subject.concat(StringPool.SPACE).concat(mailId);
	}

	@Override
	protected void processMailMessage(MailMessage mailMessage, Locale locale)
		throws Exception {

		super.processMailMessage(mailMessage, locale);

		if (htmlFormat) {
			try {
				String processedBody = BBCodeTranslatorUtil.getHTML(
					mailMessage.getBody());

				mailMessage.setBody(processedBody);
			}
			catch (Exception e) {
				_log.error(
					"Could not parse message " + mailId + " " + e.getMessage());
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MBSubscriptionSender.class);

	private boolean _calledAddMailingListSubscriber;

}