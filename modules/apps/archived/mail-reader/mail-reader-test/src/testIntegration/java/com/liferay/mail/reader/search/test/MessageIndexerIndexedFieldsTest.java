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
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.IndexedFieldsFixture;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luan Maoski
 */
@RunWith(Arquillian.class)
public class MessageIndexerIndexedFieldsTest {

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

		setUpMailReaderFixture();

		setUpMessageIndexerFixture();

		setUpIndexedFieldsFixture();
	}

	@Test
	public void testIndexedFields() throws Exception {
		Message message = mailReaderFixture.createMessage();

		String searchTerm = message.getSubject();

		Document document = messageIndexerFixture.searchOnlyOne(searchTerm);

		indexedFieldsFixture.postProcessDocument(document);

		Map<String, String> expected = _expectedFieldValues(message);

		FieldValuesAssert.assertFieldValues(expected, document, searchTerm);
	}

	protected void setUpIndexedFieldsFixture() {
		indexedFieldsFixture = new IndexedFieldsFixture(
			resourcePermissionLocalService, searchEngineHelper);
	}

	protected void setUpMailReaderFixture() {
		mailReaderFixture = new MailReaderIndexerFixture(_group, _user);

		_accounts = mailReaderFixture.getAccounts();
		_folders = mailReaderFixture.getFolders();
		_messages = mailReaderFixture.getMessages();
	}

	protected void setUpMessageIndexerFixture() {
		messageIndexerFixture = new IndexerFixture<>(Message.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_group = userSearchFixture.addGroup();

		_groups = userSearchFixture.getGroups();

		_user = TestPropsValues.getUser();

		_users = userSearchFixture.getUsers();
	}

	protected IndexedFieldsFixture indexedFieldsFixture;
	protected MailReaderIndexerFixture mailReaderFixture;
	protected IndexerFixture<Message> messageIndexerFixture;

	@Inject
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	protected UserSearchFixture userSearchFixture;

	private Map<String, String> _expectedFieldValues(Message message)
		throws Exception {

		Map<String, String> map = new HashMap<>();

		map.put(Field.COMPANY_ID, String.valueOf(message.getCompanyId()));

		map.put(Field.CONTENT, message.getBody());

		map.put(Field.ENTRY_CLASS_NAME, Message.class.getName());

		map.put(Field.ENTRY_CLASS_PK, String.valueOf(message.getMessageId()));

		map.put(Field.FOLDER_ID, String.valueOf(message.getFolderId()));

		map.put(Field.TITLE, message.getSubject());

		map.put(Field.USER_ID, String.valueOf(message.getUserId()));

		map.put(Field.USER_NAME, StringUtil.lowerCase(message.getUserName()));

		map.put("accountId", String.valueOf(message.getAccountId()));

		map.put(
			"remoteMessageId", String.valueOf(message.getRemoteMessageId()));

		map.put(
			Field.getSortableFieldName(Field.TITLE),
			StringUtil.lowerCase(message.getSubject()));

		indexedFieldsFixture.populateUID(
			Message.class.getName(), message.getMessageId(), map);

		_populateDates(message, map);
		_populateRoles(message, map);

		return map;
	}

	private void _populateDates(Message message, Map<String, String> map) {
		indexedFieldsFixture.populateDate(
			Field.CREATE_DATE, message.getCreateDate(), map);
		indexedFieldsFixture.populateDate(
			Field.MODIFIED_DATE, message.getModifiedDate(), map);
	}

	private void _populateRoles(Message message, Map<String, String> map)
		throws Exception {

		indexedFieldsFixture.populateRoleIdFields(
			message.getCompanyId(), Message.class.getName(),
			message.getMessageId(), message.getGroupId(), null, map);
	}

	@DeleteAfterTestRun
	private List<Account> _accounts;

	@DeleteAfterTestRun
	private List<Folder> _folders;

	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<Message> _messages;

	private User _user;

	@DeleteAfterTestRun
	private List<User> _users;

}