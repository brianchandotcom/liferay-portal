/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.pop.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.Account;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.pop.POPServerUtil;
import com.liferay.util.mail.MailEngine;

import java.util.List;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message.RecipientType;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

/**
 * @author Brian Wing Shun Chan
 */
public class POPNotificationsMessageListener
	extends com.liferay.portal.kernel.messaging.BaseMessageListener {

	@Override
	protected void doReceive(
			com.liferay.portal.kernel.messaging.Message message)
		throws Exception {

		try {
			pollPopServer();
		}
		finally {
			_store = null;
			_inboxFolder = null;
		}
	}

	protected String getEmailAddress(Address[] addresses) {
		if ((addresses == null) || (addresses.length == 0)) {
			return StringPool.BLANK;
		}

		InternetAddress internetAddress = (InternetAddress)addresses[0];

		return internetAddress.getAddress();
	}

	protected void initInboxFolder() throws Exception {
		if ((_inboxFolder == null) || !_inboxFolder.isOpen()) {
			initStore();

			Folder defaultFolder = _store.getDefaultFolder();

			Folder[] folders = defaultFolder.list();

			if (folders.length == 0) {
				throw new MessagingException("Inbox not found");
			}
			else {
				_inboxFolder = folders[0];

				_inboxFolder.open(Folder.READ_WRITE);
			}
		}
	}

	protected void initStore() throws Exception {
		if ((_store != null) && _store.isConnected()) {
			return;
		}

		Session session = MailEngine.getSession();

		String storeProtocol = GetterUtil.getString(
			session.getProperty("mail.store.protocol"));

		if (!storeProtocol.equals(Account.PROTOCOL_POPS)) {
			storeProtocol = Account.PROTOCOL_POP;
		}

		_store = session.getStore(storeProtocol);

		String prefix = "mail." + storeProtocol + ".";

		String host = session.getProperty(prefix + "host");

		String user = session.getProperty(prefix + "user");

		if (Validator.isNull(user)) {
			user = session.getProperty("mail.smtp.user");
		}

		String password = session.getProperty(prefix + "password");

		if (Validator.isNull(password)) {
			password = session.getProperty("mail.smtp.password");
		}

		_store.connect(host, user, password);
	}

	protected void notifyMessageListeners(
			List<MessageListener> messageListeners, Message message)
		throws Exception {

		String from = getEmailAddress(message.getFrom());
		String recipient = getEmailAddress(
			message.getRecipients(RecipientType.TO));

		if (_log.isDebugEnabled()) {
			_log.debug("From " + from);
			_log.debug("Recipient " + recipient);
		}

		for (MessageListener messageListener : messageListeners) {
			try {
				if (messageListener.accept(from, recipient, message)) {
					messageListener.deliver(from, recipient, message);
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	protected void notifyMessageListeners(Message[] messages) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Messages " + messages.length);
		}

		List<MessageListener> messageListeners = POPServerUtil.getListeners();

		for (int i = 0; i < messages.length; i++) {
			Message message = messages[i];

			if (_log.isDebugEnabled()) {
				_log.debug("Message " + message);
			}

			notifyMessageListeners(messageListeners, message);
		}
	}

	protected void pollPopServer() throws Exception {
		initInboxFolder();

		Message[] messages = _inboxFolder.getMessages();

		try {
			notifyMessageListeners(messages);
		}
		finally {
			if (_log.isDebugEnabled()) {
				_log.debug("Deleting messages");
			}

			_inboxFolder.setFlags(
				messages, new Flags(Flags.Flag.DELETED), true);

			_inboxFolder.close(true);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		POPNotificationsMessageListener.class);

	private Folder _inboxFolder;
	private Store _store;

}