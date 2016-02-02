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

package com.liferay.portal.pop.notifications.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.Account;
import com.liferay.portal.kernel.messaging.BaseSchedulerEntryMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.pop.MessageListener;
import com.liferay.portal.kernel.pop.MessageListenerException;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.pop.notifications.MessageListenerWrapper;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.mail.MailEngine;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = POPNotificationsMessageListener.class)
public class POPNotificationsMessageListener
	extends BaseSchedulerEntryMessageListener {

	@Activate
	@Modified
	protected void activate() {
		if (PropsValues.POP_SERVER_NOTIFICATIONS_ENABLED) {
			schedulerEntryImpl.setTrigger(
				TriggerFactoryUtil.createTrigger(
					getEventListenerClass(), getEventListenerClass(), 1,
					TimeUnit.MINUTE));

			_schedulerEngineHelper.register(
				this, schedulerEntryImpl, DestinationNames.SCHEDULER_DISPATCH);
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.RELUCTANT,
		service = MessageListener.class
	)
	protected void addMessageListener(MessageListener messageListener) {
		MessageListenerWrapper messageListenerWrapper =
			new MessageListenerWrapper(messageListener);

		_messageListenerWrappers.put(messageListener, messageListenerWrapper);
	}

	@Deactivate
	protected void deactivate() {
		if (PropsValues.POP_SERVER_NOTIFICATIONS_ENABLED) {
			_schedulerEngineHelper.unregister(this);
		}
	}

	@Override
	protected void doReceive(
			com.liferay.portal.kernel.messaging.Message message)
		throws MessagingException {

		Store store = null;

		try {
			store = getStore();

			Folder inboxFolder = getInboxFolder(store);

			if (inboxFolder == null) {
				return;
			}

			try {
				Message[] messages = inboxFolder.getMessages();

				if (messages == null) {
					return;
				}

				if (_log.isDebugEnabled()) {
					_log.debug("Deleting messages");
				}

				inboxFolder.setFlags(
					messages, new Flags(Flags.Flag.DELETED), true);

				notifyMessageListeners(messages);
			}
			finally {
				inboxFolder.close(true);
			}
		}
		finally {
			if (store != null) {
				store.close();
			}
		}
	}

	protected String getEmailAddress(Address[] addresses) {
		if (ArrayUtil.isEmpty(addresses)) {
			return StringPool.BLANK;
		}

		InternetAddress internetAddress = (InternetAddress)addresses[0];

		return internetAddress.getAddress();
	}

	protected Folder getInboxFolder(Store store) throws MessagingException {
		Folder defaultFolder = store.getDefaultFolder();

		Folder[] folders = defaultFolder.list();

		if (folders.length == 0) {
			throw new MessagingException("Inbox not found");
		}

		Folder inboxFolder = folders[0];

		inboxFolder.open(Folder.READ_WRITE);

		return inboxFolder;
	}

	protected Store getStore() throws MessagingException {
		Session session = MailEngine.getSession();

		String storeProtocol = GetterUtil.getString(
			session.getProperty("mail.store.protocol"));

		if (!storeProtocol.equals(Account.PROTOCOL_POPS)) {
			storeProtocol = Account.PROTOCOL_POP;
		}

		Store store = session.getStore(storeProtocol);

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

		store.connect(host, user, password);

		return store;
	}

	protected void notifyMessageListeners(Message[] messages)
		throws MessagingException {

		if (_log.isDebugEnabled()) {
			_log.debug("Messages " + messages.length);
		}

		for (Message message : messages) {
			if (_log.isDebugEnabled()) {
				_log.debug("Message " + message);
			}

			String from = getEmailAddress(message.getFrom());
			String recipient = getEmailAddress(
				message.getRecipients(RecipientType.TO));

			if (_log.isDebugEnabled()) {
				_log.debug("From " + from);
				_log.debug("Recipient " + recipient);
			}

			for (MessageListener messageListener :
					_messageListenerWrappers.values()) {

				try {
					if (messageListener.accept(from, recipient, message)) {
						messageListener.deliver(from, recipient, message);
					}
				}
				catch (MessageListenerException mle) {
					_log.error(mle, mle);
				}
			}
		}
	}

	protected void removeMessageListener(MessageListener messageListener) {
		_messageListenerWrappers.remove(messageListener);
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setSchedulerEngineHelper(
		SchedulerEngineHelper schedulerEngineHelper) {

		_schedulerEngineHelper = schedulerEngineHelper;
	}

	@Reference(unbind = "-")
	protected void setTriggerFactory(TriggerFactory triggerFactory) {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		POPNotificationsMessageListener.class);

	private final Map<MessageListener, MessageListenerWrapper>
		_messageListenerWrappers = new ConcurrentHashMap<>();
	private SchedulerEngineHelper _schedulerEngineHelper;

}