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

import com.liferay.mail.kernel.model.Account;
import com.liferay.mail.kernel.model.SMTPAccount;
import com.liferay.message.boards.model.MBMailingList;
import com.liferay.message.boards.service.MBMailingListLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.GroupSubscriptionCheckSubscriptionSender;

/**
 * @author Brian Wing Shun Chan
 * @author Thiago Moreira
 * @author Roberto Díaz
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

	public void setAnonymous(boolean anonymous) {
		_anonymous = anonymous;
	}

	public void setFullName(String fullName) {
		_fullName = fullName;
	}

	protected String getMailingListSubject(String subject, String mailId) {
		subject = GetterUtil.getString(subject);
		mailId = GetterUtil.getString(mailId);

		return subject.concat(
			StringPool.SPACE
		).concat(
			mailId
		);
	}

	@Override
	protected void populateNotificationEventJSONObject(
		JSONObject notificationEventJSONObject) {

		notificationEventJSONObject.put(
			"anonymous", _anonymous
		).put(
			"fullName", _fullName
		);

		super.populateNotificationEventJSONObject(notificationEventJSONObject);
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

	private boolean _anonymous;
	private boolean _calledAddMailingListSubscriber;
	private String _fullName;

}