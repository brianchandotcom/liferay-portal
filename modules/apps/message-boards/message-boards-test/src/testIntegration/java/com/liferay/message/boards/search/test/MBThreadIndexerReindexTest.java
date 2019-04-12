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

package com.liferay.message.boards.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luan Maoski
 */
@RunWith(Arquillian.class)
public class MBThreadIndexerReindexTest {

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
		setUpMBFixture();
		setUpMBThreadIndexerFixture();
	}

	@Test
	public void testReindexMBThread() throws Exception {
		User user = userSearchFixture.addUser(
			RandomTestUtil.randomString(), _group);

		MBMessage mbMessage = mbFixture.createMBMessageWithCategory(
			RandomTestUtil.randomString(), user.getUserId());

		MBThread thread = mbMessage.getThread();

		String searchTerm = thread.getUserName();

		mbThreadIndexerFixture.searchOnlyOne(searchTerm);

		Document document = mbThreadIndexerFixture.searchOnlyOne(searchTerm);

		mbThreadIndexerFixture.deleteDocument(document);

		mbThreadIndexerFixture.searchNoOne(searchTerm);

		mbThreadIndexerFixture.reindex(thread.getCompanyId());

		mbThreadIndexerFixture.searchOnlyOne(searchTerm);
	}

	@Test
	public void testReindexMBThreadWithDefaultCategory() throws Exception {
		User user = userSearchFixture.addUser(
			RandomTestUtil.randomString(), _group);

		MBMessage mbMessage = mbFixture.createMBMessage(
			user.getUserId(), MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		MBThread mbThread = mbMessage.getThread();

		String searchTerm = mbThread.getUserName();

		mbThreadIndexerFixture.searchOnlyOne(searchTerm);

		Document document = mbThreadIndexerFixture.searchOnlyOne(searchTerm);

		mbThreadIndexerFixture.deleteDocument(document);

		mbThreadIndexerFixture.searchNoOne(searchTerm);

		mbThreadIndexerFixture.reindex(mbMessage.getCompanyId());

		mbThreadIndexerFixture.searchOnlyOne(searchTerm);
	}

	@Test
	public void testReindexMBThreadWithDiscussion() throws Exception {
		User user = userSearchFixture.addUser(
			RandomTestUtil.randomString(), _group);

		MBMessage mbMessage = mbFixture.createMBMessage(
			user.getUserId(), MBCategoryConstants.DISCUSSION_CATEGORY_ID);

		MBThread mbThread = mbMessage.getThread();

		String searchTerm = mbThread.getUserName();

		mbThreadIndexerFixture.searchOnlyOne(searchTerm);

		Document document = mbThreadIndexerFixture.searchOnlyOne(searchTerm);

		mbThreadIndexerFixture.deleteDocument(document);

		mbThreadIndexerFixture.searchNoOne(searchTerm);

		mbThreadIndexerFixture.reindex(mbMessage.getCompanyId());

		mbThreadIndexerFixture.searchOnlyOne(searchTerm);
	}

	protected void setUpMBFixture() throws Exception {
		mbFixture = new MBFixture(_group, _user);

		_mbMessages = mbFixture.getMbMessages();

		_mbThreads = mbFixture.getMbThreads();
	}

	protected void setUpMBThreadIndexerFixture() {
		mbThreadIndexerFixture = new IndexerFixture<>(MBThread.class);
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

	protected MBFixture mbFixture;
	protected IndexerFixture<MBThread> mbThreadIndexerFixture;
	protected UserSearchFixture userSearchFixture;

	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<MBMessage> _mbMessages;

	@DeleteAfterTestRun
	private List<MBThread> _mbThreads;

	private User _user;

	@DeleteAfterTestRun
	private List<User> _users;

}