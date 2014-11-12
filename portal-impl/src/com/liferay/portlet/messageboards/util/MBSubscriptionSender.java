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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.mail.Account;
import com.liferay.portal.kernel.mail.SMTPAccount;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.GroupSubscriptionCheckSubscriptionSender;
import com.liferay.portlet.messageboards.NoSuchMailingListException;
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

	public void addMailingListSubscriber(long groupId, long categoryId)
		throws PortalException {

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

	private boolean _calledAddMailingListSubscriber;

}