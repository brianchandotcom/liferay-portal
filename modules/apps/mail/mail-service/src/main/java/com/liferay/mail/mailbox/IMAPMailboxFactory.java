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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.StringPool;

import org.osgi.service.component.annotations.Component;

/**
 * @author Scott Lee
 */
@Component(
	immediate = true, property = {"javax.portlet.name=" + MailPortletKeys.MAIL},
	service = MailboxFactory.class
)
public class IMAPMailboxFactory implements MailboxFactory {

	public Mailbox getMailbox(User user, Account account, String password) {
		return new IMAPMailbox(user, account, password);
	}

	public Mailbox getMailbox(User user, String protocol) {
		return new IMAPMailbox(user, null, StringPool.BLANK);
	}

	@Override
	public String getMailboxFactoryName() {
		return "imap";
	}

	public void initialize() {
	}

}