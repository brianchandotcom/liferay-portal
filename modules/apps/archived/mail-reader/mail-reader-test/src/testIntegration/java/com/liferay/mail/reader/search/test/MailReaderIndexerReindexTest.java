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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.mail.reader.model.Account;
import com.liferay.mail.reader.model.Folder;
import com.liferay.mail.reader.model.Message;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luan Maoski
 */
@RunWith(Arquillian.class)
public class MailReaderIndexerReindexTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		setUpUserSearchFixture();

		setUpAccountIndexerFixture();

		setUpFolderIndexerFixture();

		setUpMessageIndexerFixture();

		setUpMailReaderFixture();
	}

	@Test
	public void testReindexingAccount() throws Exception {
		Locale locale = LocaleUtil.US;

		Account account = mailReaderFixture.createAccount();

		String searchTerm = _user.getFullName();

		mailReaderFixture.updateDisplaySettings(locale);

		_accountIndexerFixture.reindex(account.getCompanyId());

		Document document = _accountIndexerFixture.searchOnlyOne(
			searchTerm, locale);

		_accountIndexerFixture.deleteDocument(document);

		_accountIndexerFixture.searchNoOne(searchTerm, locale);

		_accountIndexerFixture.reindex(account.getCompanyId());

		_accountIndexerFixture.searchOnlyOne(searchTerm);
	}

	@Test
	public void testReindexingFolder() throws Exception {
		Locale locale = LocaleUtil.US;

		Folder folder = mailReaderFixture.createFolder();

		String searchTerm = _user.getFullName();

		mailReaderFixture.updateDisplaySettings(locale);

		_folderIndexerFixture.reindex(folder.getCompanyId());

		Document document = _folderIndexerFixture.searchOnlyOne(
			searchTerm, locale);

		_folderIndexerFixture.deleteDocument(document);

		_folderIndexerFixture.searchNoOne(searchTerm, locale);

		_folderIndexerFixture.reindex(folder.getCompanyId());

		_folderIndexerFixture.searchOnlyOne(searchTerm);
	}

	@Test
	public void testReindexingMessage() throws Exception {
		Locale locale = LocaleUtil.US;

		Message message = mailReaderFixture.createMessage();

		String searchTerm = message.getSubject();

		mailReaderFixture.updateDisplaySettings(locale);

		Document document = _messageIndexerFixture.searchOnlyOne(
			searchTerm, locale);

		_messageIndexerFixture.deleteDocument(document);

		_messageIndexerFixture.searchNoOne(searchTerm, locale);

		_messageIndexerFixture.reindex(message.getCompanyId());

		_messageIndexerFixture.searchOnlyOne(searchTerm);
	}

	protected void setUpAccountIndexerFixture() {
		_accountIndexerFixture = new IndexerFixture<>(Account.class);
	}

	protected void setUpFolderIndexerFixture() {
		_folderIndexerFixture = new IndexerFixture<>(Folder.class);
	}

	protected void setUpMailReaderFixture() {
		mailReaderFixture = new MailReaderIndexerFixture(_group, _user);

		_accounts = mailReaderFixture.getAccounts();
		_folders = mailReaderFixture.getFolders();
		_messages = mailReaderFixture.getMessages();
	}

	protected void setUpMessageIndexerFixture() {
		_messageIndexerFixture = new IndexerFixture<>(Message.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_group = userSearchFixture.addGroup();

		_groups = userSearchFixture.getGroups();

		_user = userSearchFixture.addUser(
			RandomTestUtil.randomString(), _group);

		_users = userSearchFixture.getUsers();
	}

	protected MailReaderIndexerFixture mailReaderFixture;
	protected UserSearchFixture userSearchFixture;

	private IndexerFixture<Account> _accountIndexerFixture;

	@DeleteAfterTestRun
	private List<Account> _accounts;

	private IndexerFixture<Folder> _folderIndexerFixture;

	@DeleteAfterTestRun
	private List<Folder> _folders;

	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	private IndexerFixture<Message> _messageIndexerFixture;

	@DeleteAfterTestRun
	private List<Message> _messages;

	private User _user;

	@DeleteAfterTestRun
	private List<User> _users;

}