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

package com.liferay.mail.reader.search.test;

import com.liferay.mail.reader.model.Account;
import com.liferay.mail.reader.model.Folder;
import com.liferay.mail.reader.model.Message;
import com.liferay.mail.reader.service.AccountLocalServiceUtil;
import com.liferay.mail.reader.service.FolderLocalServiceUtil;
import com.liferay.mail.reader.service.MessageLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Luan Maoski
 */
public class MailReaderIndexerFixture {

	public MailReaderIndexerFixture(Group group, User user) {
		_group = group;
		_user = user;
	}

	public Account createAccount() throws Exception {
		String protocol = "imap";
		String incomingHostName = "imap.gmail.com";
		String outgoingHostName = "smtp.gmail.com";

		Account account = AccountLocalServiceUtil.addAccount(
			_user.getUserId(), "teste@liferay.com",
			RandomTestUtil.randomString(), protocol, incomingHostName, 993,
			true, outgoingHostName, 465, true, _user.getLogin(),
			_user.getPassword(), false, RandomTestUtil.randomString(), false,
			"", 0, 0, 0, 0, false);

		_accounts.add(account);

		return account;
	}

	public Folder createFolder() throws Exception {
		Account account = createAccount();

		Folder folder = FolderLocalServiceUtil.addFolder(
			_user.getUserId(), account.getAccountId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), 0);

		_folders.add(folder);

		return folder;
	}

	public Message createMessage() throws Exception {
		String sender = "test@liferay.com";
		String to = "test2@liferay.com";
		Folder folder = createFolder();

		Message message = MessageLocalServiceUtil.addMessage(
			_user.getUserId(), folder.getFolderId(), sender, to, "", "",
			new Date(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), "", 0, "");

		_messages.add(message);

		return message;
	}

	public List<Account> getAccounts() {
		return _accounts;
	}

	public List<Folder> getFolders() {
		return _folders;
	}

	public List<Message> getMessages() {
		return _messages;
	}

	public void updateDisplaySettings(Locale locale) throws Exception {
		Group group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), null, locale);

		_group.setModelAttributes(group.getModelAttributes());
	}

	private final List<Account> _accounts = new ArrayList<>();
	private final List<Folder> _folders = new ArrayList<>();
	private final Group _group;
	private final List<Message> _messages = new ArrayList<>();
	private final User _user;

}