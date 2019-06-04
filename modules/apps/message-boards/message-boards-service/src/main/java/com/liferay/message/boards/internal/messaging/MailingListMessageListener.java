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

package com.liferay.message.boards.internal.messaging;

import com.liferay.mail.kernel.model.Account;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.internal.util.MBMailMessage;
import com.liferay.message.boards.internal.util.MBMailUtil;
import com.liferay.message.boards.internal.util.MailingListThreadLocal;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBMessageService;
import com.liferay.petra.mail.MailEngine;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.security.permission.PermissionCheckerUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Thiago Moreira
 */
@Component(
	immediate = true,
	property = "destination.name=" + DestinationNames.MESSAGE_BOARDS_MAILING_LIST,
	service = MessageListener.class
)
public class MailingListMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(
			com.liferay.portal.kernel.messaging.Message message)
		throws Exception {

		MailingListRequest mailingListRequest =
			(MailingListRequest)message.getPayload();

		Store store = null;

		Folder folder = null;

		Message[] messages = null;

		try {
			store = getStore(mailingListRequest);

			store.connect();

			folder = getFolder(store);

			messages = folder.getMessages();

			processMessages(mailingListRequest, messages);
		}
		finally {
			if ((folder != null) && folder.isOpen()) {
				try {
					folder.setFlags(
						messages, new Flags(Flags.Flag.DELETED), true);
				}
				catch (Exception e) {
				}

				try {
					folder.close(true);
				}
				catch (Exception e) {
				}
			}

			if ((store != null) && store.isConnected()) {
				try {
					store.close();
				}
				catch (MessagingException me) {
				}
			}
		}
	}

	protected Folder getFolder(Store store) throws Exception {
		Folder folder = store.getFolder("INBOX");

		if (!folder.exists()) {
			throw new MessagingException("Inbox not found");
		}

		folder.open(Folder.READ_WRITE);

		return folder;
	}

	protected Store getStore(MailingListRequest mailingListRequest)
		throws Exception {

		String protocol = mailingListRequest.getInProtocol();
		String host = mailingListRequest.getInServerName();
		int port = mailingListRequest.getInServerPort();
		String user = mailingListRequest.getInUserName();
		String password = mailingListRequest.getInPassword();

		Account account = Account.getInstance(protocol, port);

		account.setHost(host);
		account.setPort(port);
		account.setUser(user);
		account.setPassword(password);

		Session session = MailEngine.getSession(account);

		URLName urlName = new URLName(
			protocol, host, port, StringPool.BLANK, user, password);

		return session.getStore(urlName);
	}

	protected void processMessage(
			MailingListRequest mailingListRequest, Message mailMessage)
		throws Exception {

		if (MBMailUtil.hasMailIdHeader(mailMessage)) {
			return;
		}

		String from = null;

		Address[] addresses = mailMessage.getFrom();

		if (ArrayUtil.isNotEmpty(addresses)) {
			Address address = addresses[0];

			if (address instanceof InternetAddress) {
				InternetAddress internetAddress = (InternetAddress)address;

				from = internetAddress.getAddress();
			}
			else {
				from = address.toString();
			}
		}

		long companyId = mailingListRequest.getCompanyId();
		long groupId = mailingListRequest.getGroupId();

		long categoryId = mailingListRequest.getCategoryId();

		if (_log.isDebugEnabled()) {
			_log.debug("Category id " + categoryId);
		}

		boolean anonymous = false;

		User user = _userLocalService.fetchUserByEmailAddress(companyId, from);

		if (user == null) {
			if (!mailingListRequest.isAllowAnonymous()) {
				return;
			}

			anonymous = true;

			user = _userLocalService.getUserById(
				companyId, mailingListRequest.getUserId());
		}

		long parentMessageId = MBMailUtil.getParentMessageId(mailMessage);

		if (_log.isDebugEnabled()) {
			_log.debug("Parent message id " + parentMessageId);
		}

		MBMessage parentMessage = null;

		if (parentMessageId > 0) {
			parentMessage = _mbMessageLocalService.fetchMBMessage(
				parentMessageId);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Parent message " + parentMessage);
		}

		MBMailMessage mbMailMessage = new MBMailMessage();

		MBMailUtil.collectPartContent(mailMessage, mbMailMessage);

		PermissionCheckerUtil.setThreadValues(user);

		MailingListThreadLocal.setSourceMailingList(true);

		String subject = MBMailUtil.getSubjectWithoutMessageId(mailMessage);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		String portletId = PortletProviderUtil.getPortletId(
			MBMessage.class.getName(), PortletProvider.Action.VIEW);

		serviceContext.setLayoutFullURL(
			_portal.getLayoutFullURL(groupId, portletId));

		serviceContext.setScopeGroupId(groupId);

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			mbMailMessage.getInputStreamOVPs();

		try {
			if (parentMessage == null) {
				_mbMessageService.addMessage(
					groupId, categoryId, subject, mbMailMessage.getBody(),
					MBMessageConstants.DEFAULT_FORMAT, inputStreamOVPs,
					anonymous, 0.0, true, serviceContext);
			}
			else {
				_mbMessageService.addMessage(
					parentMessage.getMessageId(), subject,
					mbMailMessage.getBody(), MBMessageConstants.DEFAULT_FORMAT,
					inputStreamOVPs, anonymous, 0.0, true, serviceContext);
			}
		}
		finally {
			for (ObjectValuePair<String, InputStream> inputStreamOVP :
					inputStreamOVPs) {

				try (InputStream inputStream = inputStreamOVP.getValue()) {
				}
				catch (IOException ioe) {
					if (_log.isWarnEnabled()) {
						_log.warn(ioe, ioe);
					}
				}
			}
		}
	}

	protected void processMessages(
			MailingListRequest mailingListRequest, Message[] messages)
		throws Exception {

		for (Message message : messages) {
			try {
				processMessage(mailingListRequest, message);
			}
			finally {
				PermissionCheckerUtil.setThreadValues(null);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MailingListMessageListener.class);

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBMessageService _mbMessageService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}