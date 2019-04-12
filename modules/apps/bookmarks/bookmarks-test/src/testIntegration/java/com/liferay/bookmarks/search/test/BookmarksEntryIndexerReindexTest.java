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

package com.liferay.bookmarks.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
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

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Fabiano Nazar
 * @author Luan Maoski
 */
@RunWith(Arquillian.class)
public class BookmarksEntryIndexerReindexTest {

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

		setUpBookmarksEntryFixture();

		setUpBookmarksEntryIndexerFixture();

		setUpBookmarksFolderIndexerFixture();
	}

	@Test
	public void testReindex() throws Exception {
		BookmarksEntry bookmarksEntry = bookmarksFixture.createBookmarksEntry();

		String searchTerm = bookmarksEntry.getUserName();

		bookmarksEntryIndexerFixture.searchOnlyOne(searchTerm);

		Document document = bookmarksEntryIndexerFixture.searchOnlyOne(
			searchTerm);

		bookmarksEntryIndexerFixture.deleteDocument(document);

		bookmarksEntryIndexerFixture.searchNoOne(searchTerm);

		bookmarksEntryIndexerFixture.reindex(bookmarksEntry.getCompanyId());

		bookmarksEntryIndexerFixture.searchOnlyOne(searchTerm);
	}

	@Test
	public void testReindexEntriesInTheRootFolder() throws Exception {
		long folderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		testReindexEntriesInFolder(folderId);
	}

	@Test
	public void testReindexEntriesInTheSameFolder() throws Exception {
		BookmarksFolder bookmarksFolder =
			bookmarksFixture.createBookmarksFolder();

		testReindexEntriesInFolder(bookmarksFolder.getFolderId());
	}

	protected Document[] searchAndAssertLength(
		String searchTerm, int expected) {

		Document[] documents = bookmarksEntryIndexerFixture.search(searchTerm);

		Assert.assertEquals(
			Arrays.toString(documents), expected, documents.length);

		return documents;
	}

	protected void setUpBookmarksEntryFixture() throws Exception {
		bookmarksFixture = new BookmarksFixture(_group, _user);

		_bookmarksEntries = bookmarksFixture.getBookmarksEntries();

		_bookmarksFolders = bookmarksFixture.getBookmarksFolders();
	}

	protected void setUpBookmarksEntryIndexerFixture() {
		bookmarksEntryIndexerFixture = new IndexerFixture<>(
			BookmarksEntry.class);
	}

	protected void setUpBookmarksFolderIndexerFixture() {
		bookmarksFolderIndexerFixture = new IndexerFixture<>(
			BookmarksFolder.class);
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

	protected void testReindexEntriesInFolder(long folderId) throws Exception {
		BookmarksEntry bookmarksEntry = bookmarksFixture.createBookmarksEntry(
			folderId);

		bookmarksFixture.createBookmarksEntry(folderId);

		String searchTerm = _user.getFullName();

		Document[] documents = searchAndAssertLength(searchTerm, 2);

		bookmarksEntryIndexerFixture.deleteDocuments(documents);

		bookmarksEntryIndexerFixture.reindex(bookmarksEntry.getCompanyId());

		searchAndAssertLength(searchTerm, 2);
	}

	protected IndexerFixture<BookmarksEntry> bookmarksEntryIndexerFixture;
	protected BookmarksFixture bookmarksFixture;
	protected IndexerFixture<BookmarksFolder> bookmarksFolderIndexerFixture;
	protected UserSearchFixture userSearchFixture;

	@DeleteAfterTestRun
	private List<BookmarksEntry> _bookmarksEntries;

	@DeleteAfterTestRun
	private List<BookmarksFolder> _bookmarksFolders;

	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	private User _user;

	@DeleteAfterTestRun
	private List<User> _users;

}