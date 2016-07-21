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

package com.liferay.mail.reader.web.portlet.action;

import com.liferay.mail.reader.mailbox.MailboxFactory;
import com.liferay.mail.reader.mailbox.MailboxFactoryUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Scott Lee
 * @author Peter Fellwock
 */
@Component(
	immediate = true, property = {"key=application.startup.events"},
	service = LifecycleAction.class
)
public class StartupAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		try {
			initializeMailboxFactories();
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected void initializeMailboxFactories() throws PortalException {
		List<MailboxFactory> mailboxFactories =
			MailboxFactoryUtil.getMailboxFactories();

		for (MailboxFactory mailboxFactory : mailboxFactories) {
			mailboxFactory.initialize();
		}
	}

	@Reference(unbind = "-")
	protected void setMailboxFactoryUtil(
		MailboxFactoryUtil mailboxFactoryUtil) {
	}

}