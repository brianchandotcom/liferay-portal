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

package com.liferay.mail.mailbox;

import com.liferay.mail.constants.MailPortletKeys;
import com.liferay.mail.model.Account;
import com.liferay.mail.service.AccountLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Scott Lee
 */
@Component(
	immediate = true, property = {"javax.portlet.name=" + MailPortletKeys.MAIL},
	service = MailboxFactoryUtil.class
)
public class MailboxFactoryUtil {

	public static Mailbox getMailbox(
			long userId, long accountId, String password)
		throws PortalException {

		User user = UserLocalServiceUtil.getUser(userId);
		Account account = AccountLocalServiceUtil.getAccount(accountId);

		MailboxFactory mailboxFactory = _mailboxFactories.get(
			account.getProtocol());

		if (mailboxFactory == null) {
			throw new IllegalArgumentException(
				"Invalid protocol " + account.getProtocol());
		}

		return mailboxFactory.getMailbox(user, account, password);
	}

	public static Mailbox getMailbox(long userId, String protocol)
		throws PortalException {

		User user = UserLocalServiceUtil.getUser(userId);

		MailboxFactory mailboxFactory = _mailboxFactories.get(protocol);

		if (mailboxFactory == null) {
			throw new IllegalArgumentException("Invalid protocol " + protocol);
		}

		return mailboxFactory.getMailbox(user, protocol);
	}

	public static List<MailboxFactory> getMailboxFactories() {
		Set<String> protocols = _mailboxFactories.keySet();

		List<MailboxFactory> mailboxFactories = new ArrayList<>();

		for (String protocol : protocols) {
			mailboxFactories.add(_mailboxFactories.get(protocol));
		}

		return mailboxFactories;
	}

	public void setMailboxFactories(
		Map<String, MailboxFactory> mailboxFactories) {

		_mailboxFactories = mailboxFactories;
	}

	@Reference(unbind = "-")
	protected void setMailboxFactory(MailboxFactory mailboxFactory) {
		_addMailboxFactory(
			mailboxFactory.getMailboxFactoryName(), mailboxFactory);
	}

	private void _addMailboxFactory(
		String mailboxFactoryName, MailboxFactory mailboxFactory) {

		if (_mailboxFactories == null) {
			_mailboxFactories = new ConcurrentHashMap<>();
		}

		_mailboxFactories.put(mailboxFactoryName, mailboxFactory);
	}

	private static Map<String, MailboxFactory> _mailboxFactories;

}