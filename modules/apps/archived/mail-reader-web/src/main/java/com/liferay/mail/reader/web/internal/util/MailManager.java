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

package com.liferay.mail.reader.web.internal.util;

import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.mail.reader.attachment.AttachmentHandler;
import com.liferay.mail.reader.constants.MailConstants;
import com.liferay.mail.reader.exception.MailException;
import com.liferay.mail.reader.mailbox.Mailbox;
import com.liferay.mail.reader.mailbox.MailboxFactory;
import com.liferay.mail.reader.mailbox.PasswordRetriever;
import com.liferay.mail.reader.model.Account;
import com.liferay.mail.reader.model.Attachment;
import com.liferay.mail.reader.model.Folder;
import com.liferay.mail.reader.model.MailFile;
import com.liferay.mail.reader.model.Message;
import com.liferay.mail.reader.model.MessageDisplay;
import com.liferay.mail.reader.model.MessagesDisplay;
import com.liferay.mail.reader.service.AccountLocalServiceUtil;
import com.liferay.mail.reader.service.AttachmentLocalServiceUtil;
import com.liferay.mail.reader.service.FolderLocalServiceUtil;
import com.liferay.mail.reader.service.MessageLocalServiceUtil;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.PortletConfig;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Scott Lee
 * @author Ryan Park
 * @author Peter Fellwock
 */
public class MailManager {

	public static MailManager getInstance(HttpServletRequest httpServletRequest)
		throws PortalException {

		User user = PortalUtil.getUser(httpServletRequest);

		if (user == null) {
			return null;
		}

		PortletConfig portletConfig =
			(PortletConfig)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		return new MailManager(
			user, new PasswordRetriever(httpServletRequest), portletConfig);
	}

	public MailManager(
		User user, PasswordRetriever passwordRetriever,
		PortletConfig portletConfig) {

		_user = user;
		_passwordRetriever = passwordRetriever;
		_portletConfig = portletConfig;
	}

	public JSONObject addAccount(
			String address, String personalName, String protocol,
			String incomingHostName, int incomingPort, boolean incomingSecure,
			String outgoingHostName, int outgoingPort, boolean outgoingSecure,
			String login, String password, boolean savePassword,
			String signature, boolean useSignature, String folderPrefix,
			boolean defaultSender)
		throws PortalException {

		try {
			Mailbox mailbox = _getMailbox(_user, protocol);

			Account account = mailbox.addAccount(
				address, personalName, protocol, incomingHostName, incomingPort,
				incomingSecure, outgoingHostName, outgoingPort, outgoingSecure,
				login, password, savePassword, signature, useSignature,
				folderPrefix, defaultSender);

			if (!savePassword) {
				_passwordRetriever.setPassword(
					account.getAccountId(), password);
			}

			mailbox = _getMailbox(
				_user, account,
				_passwordRetriever.getPassword(account.getAccountId()));

			mailbox.updateFolders();

			synchronizeAccount(account.getAccountId());

			return createJSONResult("success", "account-has-been-created");
		}
		catch (MailException me) {
			if (me.getType() == MailException.ACCOUNT_ALREADY_EXISTS) {
				return createJSONResult(
					"failure",
					"an-account-with-the-same-address-already-exists");
			}

			_log.error(me, me);

			return createJSONResult("failure", "unable-to-add-account");
		}
	}

	public Message addDraft(long accountId) throws PortalException {
		Account account = AccountLocalServiceUtil.getAccount(accountId);

		return MessageLocalServiceUtil.addMessage(
			_user.getUserId(), account.getDraftFolderId(), account.getAddress(),
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, new Date(),
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, 0,
			ContentTypes.TEXT_HTML_UTF8);
	}

	public JSONObject addFolder(long accountId, String displayName)
		throws PortalException {

		try {
			Mailbox mailbox = _getMailbox(
				_user, AccountLocalServiceUtil.getAccount(accountId),
				_passwordRetriever.getPassword(accountId));

			mailbox.addFolder(displayName);

			return createJSONResult("success", "folder-has-been-created");
		}
		catch (MailException me) {
			if (me.getType() == MailException.FOLDER_ALREADY_EXISTS) {
				return createJSONResult(
					"failure", "a-folder-with-the-same-name-already-exists");
			}

			_log.error(me, me);

			return createJSONResult("failure", "unable-to-create-folder");
		}
	}

	public JSONObject checkMessages(long accountId, long folderId)
		throws PortalException {

		try {
			Mailbox mailbox = _getMailbox(
				_user, AccountLocalServiceUtil.getAccount(accountId),
				_passwordRetriever.getPassword(accountId));

			if (mailbox.hasNewMessages(folderId)) {
				mailbox.synchronizeFolder(folderId);

				return createJSONResult("success", StringPool.BLANK, "true");
			}

			return createJSONResult("success", StringPool.BLANK, "false");
		}
		catch (MailException me) {
			return createJSONResult("failure", StringPool.BLANK);
		}
	}

	public JSONObject deleteAccount(long accountId) throws PortalException {
		try {
			Mailbox mailbox = _getMailbox(
				_user, AccountLocalServiceUtil.getAccount(accountId),
				_passwordRetriever.getPassword(accountId));

			_passwordRetriever.removePassword(accountId);

			mailbox.deleteAccount();

			return createJSONResult("success", "account-has-been-deleted");
		}
		catch (MailException me) {
			_log.error(me, me);

			return createJSONResult("failure", "unable-to-delete-account");
		}
	}

	public JSONObject deleteAttachment(long attachmentId)
		throws PortalException {

		try {
			Attachment attachment = AttachmentLocalServiceUtil.getAttachment(
				attachmentId);

			Mailbox mailbox = _getMailbox(
				_user,
				AccountLocalServiceUtil.getAccount(attachment.getAccountId()),
				_passwordRetriever.getPassword(attachment.getAccountId()));

			mailbox.deleteAttachment(attachmentId);

			return createJSONResult("success", "attachment-has-been-deleted");
		}
		catch (MailException me) {
			_log.error(me, me);

			return createJSONResult("failure", "unable-to-delete-attachment");
		}
	}

	public JSONObject deleteFolder(long folderId) throws PortalException {
		try {
			Folder folder = FolderLocalServiceUtil.getFolder(folderId);

			Mailbox mailbox = _getMailbox(
				_user,
				AccountLocalServiceUtil.getAccount(folder.getAccountId()),
				_passwordRetriever.getPassword(folder.getAccountId()));

			mailbox.deleteFolder(folderId);

			return createJSONResult("success", "folder-has-been-deleted");
		}
		catch (MailException me) {
			if (me.getType() == MailException.FOLDER_REQUIRED) {
				return createJSONResult(
					"failure",
					"this-is-a-required-folder-and-can-not-be-deleted");
			}
			else if (me.getType() == MailException.FOLDER_DELETE_FAILED) {
				return createJSONResult(
					"failure",
					"the-mail-server-will-not-allow-this-folder-to-be-deleted");
			}

			_log.error(me, me);

			return createJSONResult("failure", "unable-to-delete-folder");
		}
	}

	public JSONObject deleteMessages(long[] messageIds) throws PortalException {
		try {
			if (messageIds.length == 0) {
				return createJSONResult("failure", "no-messages-selected");
			}

			Message message = MessageLocalServiceUtil.getMessage(messageIds[0]);

			Account account = AccountLocalServiceUtil.getAccount(
				message.getAccountId());

			if (account.getDraftFolderId() == message.getFolderId()) {
				for (long messageId : messageIds) {
					MessageLocalServiceUtil.deleteMessage(messageId);
				}

				return createJSONResult(
					"success", "drafts-have-been-discarded");
			}

			Mailbox mailbox = _getMailbox(
				_user,
				AccountLocalServiceUtil.getAccount(message.getAccountId()),
				_passwordRetriever.getPassword(message.getAccountId()));

			mailbox.deleteMessages(message.getFolderId(), messageIds);

			return createJSONResult("success", "messages-have-been-deleted");
		}
		catch (MailException me) {
			_log.error(me, me);

			return createJSONResult("failure", "unable-to-delete-messages");
		}
	}

	public JSONObject flagMessages(long[] messageIds, int flag, boolean value)
		throws PortalException {

		try {
			if (messageIds.length == 0) {
				return createJSONResult("failure", "no-messages-selected");
			}

			Message message = MessageLocalServiceUtil.getMessage(messageIds[0]);

			Mailbox mailbox = _getMailbox(
				_user,
				AccountLocalServiceUtil.getAccount(message.getAccountId()),
				_passwordRetriever.getPassword(message.getAccountId()));

			mailbox.updateFlags(message.getFolderId(), messageIds, flag, value);

			return createJSONResult("success", "messages-have-been-flagged");
		}
		catch (MailException me) {
			if (me.getType() == MailException.MESSAGE_INVALID_FLAG) {
				return createJSONResult(
					"failure", "this-flag-is-not-supported");
			}

			_log.error(me, me);

			return createJSONResult("failure", "unable-to-flag-messages");
		}
	}

	public List<Account> getAccounts() {
		return AccountLocalServiceUtil.getAccounts(_user.getUserId());
	}

	public long getAccountUnreadMessagesCount(long accountId) {
		return MessageLocalServiceUtil.getAccountUnreadMessagesCount(accountId);
	}

	public AttachmentHandler getAttachment(long attachmentId)
		throws IOException, PortalException {

		Attachment attachment = AttachmentLocalServiceUtil.getAttachment(
			attachmentId);

		Mailbox mailbox = _getMailbox(
			_user,
			AccountLocalServiceUtil.getAccount(attachment.getAccountId()),
			_passwordRetriever.getPassword(attachment.getAccountId()));

		return mailbox.getAttachment(attachmentId);
	}

	public JSONObject getDefaultAccountsJSONObject() {
		JSONObject gmailAccountJSONObject = JSONUtil.put(
			"address", "@gmail.com"
		).put(
			"descriptionLanguageKey",
			"please-enable-imap-in-you-gmail-settings-for-mail-to-work"
		).put(
			"folderPrefix", ""
		).put(
			"hideSettings", true
		).put(
			"incomingHostName", "imap.gmail.com"
		).put(
			"incomingPort", 993
		).put(
			"incomingSecure", true
		).put(
			"outgoingHostName", "smtp.gmail.com"
		).put(
			"outgoingPort", 465
		).put(
			"outgoingSecure", true
		).put(
			"protocol", "imap"
		).put(
			"titleLanguageKey", "gmail-account"
		).put(
			"useLocalPartAsLogin", true
		);

		JSONObject customMailAccontJSONObject = JSONUtil.put(
			"address", ""
		).put(
			"descriptionLanguageKey", ""
		).put(
			"folderPrefix", ""
		).put(
			"hideSettings", false
		).put(
			"incomingHostName", ""
		).put(
			"incomingPort", 110
		).put(
			"incomingSecure", false
		).put(
			"outgoingHostName", ""
		).put(
			"outgoingPort", 25
		).put(
			"outgoingSecure", false
		).put(
			"protocol", "imap"
		).put(
			"titleLanguageKey", "custom-mail-account"
		).put(
			"useLocalPartAsLogin", false
		);

		return JSONUtil.put(
			"accounts",
			JSONUtil.putAll(
				gmailAccountJSONObject, customMailAccontJSONObject));
	}

	public List<Folder> getFolders(
			long accountId, boolean includeRequiredFolders,
			boolean includeNonRequiredFolders)
		throws PortalException {

		List<Folder> folders = FolderLocalServiceUtil.getFolders(accountId);

		List<Folder> requiredFolders = new ArrayList<>();
		List<Folder> nonRequiredFolders = new ArrayList<>();

		Account account = AccountLocalServiceUtil.getAccount(accountId);

		for (Folder folder : folders) {
			if ((folder.getFolderId() == account.getInboxFolderId()) ||
				(folder.getFolderId() == account.getDraftFolderId()) ||
				(folder.getFolderId() == account.getSentFolderId()) ||
				(folder.getFolderId() == account.getTrashFolderId())) {

				requiredFolders.add(folder);
			}
			else {
				nonRequiredFolders.add(folder);
			}
		}

		if (includeRequiredFolders && includeNonRequiredFolders) {
			requiredFolders.addAll(nonRequiredFolders);

			// Required folders at front of list

			return requiredFolders;
		}
		else if (includeNonRequiredFolders) {
			return nonRequiredFolders;
		}

		return requiredFolders;
	}

	public long getFolderUnreadMessagesCount(long folderId) {
		return MessageLocalServiceUtil.getFolderUnreadMessagesCount(folderId);
	}

	public Account getInitialAccount() {
		List<Account> accounts = AccountLocalServiceUtil.getAccounts(
			_user.getUserId());

		if (accounts.isEmpty()) {
			return null;
		}

		return accounts.get(0);
	}

	public MessageDisplay getMessageDisplay(long messageId)
		throws PortalException {

		Message message = MessageLocalServiceUtil.getMessage(messageId);

		if (Validator.isNull(message.getBody())) {
			Mailbox mailbox = _getMailbox(
				_user,
				AccountLocalServiceUtil.getAccount(message.getAccountId()),
				_passwordRetriever.getPassword(message.getAccountId()));

			mailbox.synchronizeMessage(message.getMessageId());

			message = MessageLocalServiceUtil.getMessage(messageId);
		}

		return new MessageDisplay(
			message, AttachmentLocalServiceUtil.getAttachments(messageId), 0);
	}

	public MessageDisplay getMessageDisplay(
			long folderId, int messageNumber, String orderByField,
			String orderByType, String keywords)
		throws PortalException {

		if (folderId <= 0) {
			return null;
		}

		MessagesDisplay messagesDisplay = getMessagesDisplay(
			folderId, messageNumber, 1, orderByField, orderByType, keywords);

		List<Message> messages = messagesDisplay.getMessages();

		Message message = messages.get(0);

		List<Attachment> attachments =
			AttachmentLocalServiceUtil.getAttachments(message.getMessageId());

		return new MessageDisplay(
			message, attachments, messagesDisplay.getMessageCount());
	}

	public MessagesDisplay getMessagesDisplay(
			long folderId, int pageNumber, int messagesPerPage,
			String orderByField, String orderByType, String keywords)
		throws PortalException {

		Folder folder = FolderLocalServiceUtil.getFolder(folderId);

		if (folderId != 0) {
			Mailbox mailbox = _getMailbox(
				_user,
				AccountLocalServiceUtil.getAccount(folder.getAccountId()),
				_passwordRetriever.getPassword(folder.getAccountId()));

			return mailbox.getMessagesDisplay(
				folderId, keywords, pageNumber, messagesPerPage, orderByField,
				orderByType);
		}

		return new MessagesDisplay(
			new ArrayList<Message>(), pageNumber, messagesPerPage, 0);
	}

	public String getPassword(long accountId) throws PortalException {
		return _passwordRetriever.getPassword(accountId);
	}

	public void markAsRead(long accountId, long folderId, long messageId)
		throws PortalException {

		com.liferay.portal.kernel.messaging.Message message =
			new com.liferay.portal.kernel.messaging.Message();

		message.put("command", "flag");

		message.put("userId", _user.getUserId());
		message.put("accountId", accountId);
		message.put("password", _passwordRetriever.getPassword(accountId));
		message.put("folderId", folderId);
		message.put("messageId", messageId);
		message.put("flag", MailConstants.FLAG_SEEN);
		message.put("flagValue", true);

		MessageBusUtil.sendMessage(DestinationNames.MAIL_SYNCHRONIZER, message);
	}

	public JSONObject moveMessages(long folderId, long[] messageIds)
		throws PortalException {

		try {
			if (messageIds.length == 0) {
				return createJSONResult("failure", "no-messages-selected");
			}

			Folder folder = FolderLocalServiceUtil.getFolder(folderId);

			Account account = AccountLocalServiceUtil.getAccount(
				folder.getAccountId());

			if ((account.getDraftFolderId() == folderId) ||
				(account.getSentFolderId() == folderId)) {

				throw new MailException(
					MailException.FOLDER_INVALID_DESTINATION);
			}

			Message message = MessageLocalServiceUtil.getMessage(messageIds[0]);

			Mailbox mailbox = _getMailbox(
				_user,
				AccountLocalServiceUtil.getAccount(message.getAccountId()),
				_passwordRetriever.getPassword(message.getAccountId()));

			mailbox.moveMessages(folderId, messageIds);

			return createJSONResult("success", "messages-have-been-moved");
		}
		catch (MailException me) {
			if (me.getType() == MailException.FOLDER_INVALID_DESTINATION) {
				return createJSONResult(
					"failure", "cannot-move-messages-to-this-folder");
			}

			_log.error(me, me);

			return createJSONResult("failure", "unable-to-move-messages");
		}
	}

	public JSONObject renameFolder(long folderId, String displayName)
		throws PortalException {

		Folder folder = FolderLocalServiceUtil.getFolder(folderId);

		try {
			Mailbox mailbox = _getMailbox(
				_user,
				AccountLocalServiceUtil.getAccount(folder.getAccountId()),
				_passwordRetriever.getPassword(folder.getAccountId()));

			mailbox.renameFolder(folderId, displayName);

			return createJSONResult("success", "folder-renamed-successfully");
		}
		catch (MailException me) {
			if (me.getType() == MailException.FOLDER_RENAME_FAILED) {
				return createJSONResult(
					"failure", "cannot-move-messages-to-this-folder");
			}
			else if (me.getType() == MailException.FOLDER_ALREADY_EXISTS) {
				return createJSONResult(
					"failure", "a-folder-with-the-same-name-already-exists");
			}

			_log.error(me, me);

			return createJSONResult("failure", "unable-to-rename-folder");
		}
	}

	public JSONObject saveDraft(
			long accountId, long messageId, String to, String cc, String bcc,
			String subject, String body, List<MailFile> mailFiles)
		throws PortalException {

		try {
			Mailbox mailbox = _getMailbox(
				_user, AccountLocalServiceUtil.getAccount(accountId),
				_passwordRetriever.getPassword(accountId));

			Message message = mailbox.saveDraft(
				accountId, messageId, to, cc, bcc, subject, body, mailFiles);

			return createJSONResult(
				"success", "saved-successfully",
				String.valueOf(message.getMessageId()));
		}
		catch (MailException me) {
			if (me.getType() == MailException.MESSAGE_HAS_NO_RECIPIENTS) {
				return createJSONResult(
					"failure", "please-specify-at-least-one-recipient");
			}
			else if (me.getType() == MailException.MESSAGE_INVALID_ADDRESS) {
				return createJSONResult(
					"failure",
					"please-make-sure-the-following-address-is-properly-" +
						"formatted",
					me.getValue());
			}

			_log.error(me, me);

			return createJSONResult("failure", "unable-to-save-draft");
		}
	}

	public JSONObject sendMessage(
			long accountId, long messageId, String to, String cc, String bcc,
			String subject, String body, List<MailFile> mailFiles)
		throws PortalException {

		try {
			Mailbox mailbox = _getMailbox(
				_user, AccountLocalServiceUtil.getAccount(accountId),
				_passwordRetriever.getPassword(accountId));

			Message message = mailbox.saveDraft(
				accountId, messageId, to, cc, bcc, subject, body, mailFiles);

			mailbox.sendMessage(accountId, message.getMessageId());

			return createJSONResult("success", "sent-successfully");
		}
		catch (FileSizeException fse) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(fse, fse);
			}

			return createJSONResult("failure", "attachment-is-too-large");
		}
		catch (MailException me) {
			if (me.getType() == MailException.MESSAGE_HAS_NO_RECIPIENTS) {
				return createJSONResult(
					"failure", "please-specify-at-least-one-recipient");
			}
			else if (me.getType() == MailException.MESSAGE_INVALID_ADDRESS) {
				return createJSONResult(
					"failure",
					"please-make-sure-the-following-address-is-properly-" +
						"formatted",
					HtmlUtil.escape(me.getValue()));
			}

			_log.error(me, me);

			return createJSONResult("failure", "unable-to-send-message");
		}
	}

	public JSONObject storePassword(long accountId, String password)
		throws PortalException {

		Account account = AccountLocalServiceUtil.getAccount(accountId);

		if (!account.isSavePassword()) {
			Mailbox mailbox = _getMailbox(
				_user, AccountLocalServiceUtil.getAccount(accountId),
				_passwordRetriever.getPassword(accountId));

			try {
				mailbox.validateAccount(
					account.getIncomingHostName(), account.getIncomingPort(),
					account.isIncomingSecure(), account.getOutgoingHostName(),
					account.getOutgoingPort(), account.isOutgoingSecure(),
					account.getLogin(), password);

				_passwordRetriever.setPassword(accountId, password);

				return createJSONResult("success", "logged-in-successfully");
			}
			catch (MailException me) {
				return createJSONResult("failure", "incorrect-password");
			}
		}
		else {
			return createJSONResult(
				"success", "password-has-already-been-saved");
		}
	}

	public void synchronizeAccount(long accountId) throws PortalException {
		synchronize(accountId, 0, 0, 0, 0);
	}

	public void synchronizeFolder(long folderId) throws PortalException {
		Folder folder = FolderLocalServiceUtil.getFolder(folderId);

		synchronize(folder.getAccountId(), folderId, 0, 0, 0);
	}

	public void synchronizeMessage(long messageId) throws PortalException {
		Message message = MessageLocalServiceUtil.getMessage(messageId);

		synchronize(message.getAccountId(), 0, messageId, 0, 0);
	}

	public void synchronizePage(
			long folderId, int pageNumber, int messagesPerPage)
		throws PortalException {

		Folder folder = FolderLocalServiceUtil.getFolder(folderId);

		synchronize(
			folder.getAccountId(), folderId, 0, pageNumber, messagesPerPage);
	}

	public JSONObject updateAccount(
			long accountId, String personalName, String password,
			boolean savePassword, String signature, boolean useSignature,
			String folderPrefix, boolean defaultSender)
		throws PortalException {

		return updateAccount(
			accountId, personalName, password, savePassword, signature,
			useSignature, folderPrefix, defaultSender, 0, 0, 0, 0);
	}

	public JSONObject updateAccount(
			long accountId, String personalName, String password,
			boolean savePassword, String signature, boolean useSignature,
			String folderPrefix, boolean defaultSender, long inboxFolderId,
			long draftFolderId, long sentFolderId, long trashFolderId)
		throws PortalException {

		try {
			if (savePassword) {
				_passwordRetriever.removePassword(accountId);
			}

			if (Validator.isNull(password)) {
				String oldPassword = _passwordRetriever.getPassword(accountId);

				if (Validator.isNull(oldPassword)) {
					throw new MailException("no password");
				}

				password = oldPassword;
			}

			Mailbox mailbox = _getMailbox(
				_user, AccountLocalServiceUtil.getAccount(accountId), password);

			mailbox.updateAccount(
				accountId, personalName, password, savePassword, signature,
				useSignature, folderPrefix, defaultSender);

			if (inboxFolderId > 0) {
				mailbox.updateFolders(
					inboxFolderId, draftFolderId, sentFolderId, trashFolderId);
			}
			else {
				mailbox.updateFolders();
			}

			return createJSONResult("success", "account-has-been-updated");
		}
		catch (MailException me) {
			if (me.getType() == MailException.MESSAGE_HAS_NO_RECIPIENTS) {
				return createJSONResult(
					"failure", "please-specify-at-least-one-recipient");
			}
			else if (me.getType() == MailException.MESSAGE_INVALID_ADDRESS) {
				return createJSONResult(
					"failure",
					"please-make-sure-the-following-address-is-properly-" +
						"formatted",
					me.getValue());
			}

			_log.error(me, me);

			return createJSONResult("failure", "unable-to-update-account");
		}
	}

	protected JSONObject createJSONResult(String status, String message) {
		return createJSONResult(status, message, null);
	}

	protected JSONObject createJSONResult(
		String status, String message, String value) {

		ResourceBundle resourceBundle = _portletConfig.getResourceBundle(
			_user.getLocale());

		JSONObject jsonObject = JSONUtil.put(
			"message", LanguageUtil.get(resourceBundle, message)
		).put(
			"status", status
		);

		if (Validator.isNotNull(value)) {
			jsonObject.put("value", value);
		}

		return jsonObject;
	}

	protected void synchronize(
			long accountId, long folderId, long messageId, int pageNumber,
			int messagesPerPage)
		throws PortalException {

		String password = _passwordRetriever.getPassword(accountId);

		if (Validator.isNull(password)) {
			return;
		}

		com.liferay.portal.kernel.messaging.Message message =
			new com.liferay.portal.kernel.messaging.Message();

		message.put("command", "synchronize");

		message.put("userId", _user.getUserId());
		message.put("accountId", accountId);
		message.put("password", password);
		message.put("folderId", folderId);
		message.put("messageId", messageId);
		message.put("pageNumber", pageNumber);
		message.put("messagesPerPage", messagesPerPage);

		MessageBusUtil.sendMessage(DestinationNames.MAIL_SYNCHRONIZER, message);
	}

	private static Mailbox _getMailbox(
			User user, Account account, String password)
		throws PortalException {

		MailboxFactory mailboxFactory = _mailboxFactories.getService(
			account.getProtocol());

		if (mailboxFactory == null) {
			throw new IllegalArgumentException(
				"Invalid protocol " + account.getProtocol());
		}

		return mailboxFactory.getMailbox(user, account, password);
	}

	private static Mailbox _getMailbox(User user, String protocol)
		throws PortalException {

		MailboxFactory mailboxFactory = _mailboxFactories.getService(protocol);

		if (mailboxFactory == null) {
			throw new IllegalArgumentException("Invalid protocol " + protocol);
		}

		return mailboxFactory.getMailbox(user, protocol);
	}

	private static final Log _log = LogFactoryUtil.getLog(MailManager.class);

	private static final ServiceTrackerMap<String, MailboxFactory>
		_mailboxFactories;

	static {
		Bundle bundle = FrameworkUtil.getBundle(MailManager.class);

		final BundleContext bundleContext = bundle.getBundleContext();

		_mailboxFactories = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, MailboxFactory.class, null,
			new ServiceReferenceMapper<String, MailboxFactory>() {

				@Override
				public void map(
					ServiceReference<MailboxFactory> serviceReference,
					ServiceReferenceMapper.Emitter<String> emitter) {

					MailboxFactory mailboxFactory = bundleContext.getService(
						serviceReference);

					try {
						emitter.emit(mailboxFactory.getMailboxFactoryName());
					}
					finally {
						bundleContext.ungetService(serviceReference);
					}
				}

			});
	}

	private final PasswordRetriever _passwordRetriever;
	private final PortletConfig _portletConfig;
	private final User _user;

}