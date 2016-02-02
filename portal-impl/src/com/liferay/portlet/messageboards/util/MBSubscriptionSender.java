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

package com.liferay.portlet.messageboards.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.Account;
import com.liferay.portal.kernel.mail.SMTPAccount;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.util.GroupSubscriptionCheckSubscriptionSender;
import com.liferay.portlet.messageboards.model.MBMailingList;
import com.liferay.portlet.messageboards.service.MBMailingListLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @author Thiago Moreira
 */
public class MBSubscriptionSender
	extends GroupSubscriptionCheckSubscriptionSender {

	public MBSubscriptionSender(String resourceName) {
		super(resourceName);
	}

	public void addMailingListSubscriber(long groupId, long categoryId) {
		if (_calledAddMailingListSubscriber) {
			throw new IllegalStateException("Method may only be called once");
		}

		_calledAddMailingListSubscriber = true;

		MBMailingList mailingList =
			MBMailingListLocalServiceUtil.fetchCategoryMailingList(
				groupId, categoryId);

		if ((mailingList == null) || !mailingList.isActive()) {
			return;
		}

		setFrom(mailingList.getOutEmailAddress(), null);
		setReplyToAddress(mailingList.getEmailAddress());

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
		subject = GetterUtil.getString(subject);
		mailId = GetterUtil.getString(mailId);

		return subject.concat(StringPool.SPACE).concat(mailId);
	}

	@Override
	protected void sendNotification(User user) throws Exception {
		sendEmailNotification(user);

		if (currentUserId == user.getUserId()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Skip notification for user " + currentUserId);
			}

			return;
		}

		sendUserNotification(user);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MBSubscriptionSender.class);

	private boolean _calledAddMailingListSubscriber;

}