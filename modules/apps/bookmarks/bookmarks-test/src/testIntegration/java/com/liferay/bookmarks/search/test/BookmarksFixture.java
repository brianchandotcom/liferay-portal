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

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.bookmarks.test.util.BookmarksTestUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Igor Fabiano Nazar
 * @author Luan Maoski
 */
public class BookmarksFixture {

	public BookmarksFixture(Group group, User user) {
		_group = group;
		_user = user;
	}

	public BookmarksEntry createBookmarksEntry() throws Exception {
		BookmarksFolder bookmarksFolder = createBookmarksFolder();

		return createBookmarksEntry(bookmarksFolder.getFolderId());
	}

	public BookmarksEntry createBookmarksEntry(long folderId) throws Exception {
		return createBookmarksEntry(
			folderId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
	}

	public BookmarksEntry createBookmarksEntry(
			long folderId, String name, String description)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		BookmarksEntry bookmarksEntry = BookmarksEntryLocalServiceUtil.addEntry(
			_user.getUserId(), _group.getGroupId(), folderId, name,
			"https://www.liferay.com", description, serviceContext);

		_bookmarksEntries.add(bookmarksEntry);

		return bookmarksEntry;
	}

	public BookmarksEntry createBookmarksEntry(String name) throws Exception {
		BookmarksFolder bookmarksFolder = createBookmarksFolder();

		return createBookmarksEntry(
			bookmarksFolder.getFolderId(), name, RandomTestUtil.randomString());
	}

	public BookmarksFolder createBookmarksFolder() throws Exception {
		return createBookmarksFolder(RandomTestUtil.randomString());
	}

	public BookmarksFolder createBookmarksFolder(String name) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		BookmarksFolder bookmarksFolder = BookmarksTestUtil.addFolder(
			BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID, name,
			serviceContext);

		_bookmarksFolders.add(bookmarksFolder);

		return bookmarksFolder;
	}

	public List<BookmarksEntry> getBookmarksEntries() {
		return _bookmarksEntries;
	}

	public List<BookmarksFolder> getBookmarksFolders() {
		return _bookmarksFolders;
	}

	public void populateLocalizedTitles(String title, Map<String, String> map) {
		String titleLowerCase = StringUtil.lowerCase(title);

		map.put("localized_title", titleLowerCase);

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			String languageId = LocaleUtil.toLanguageId(locale);

			String key = "localized_title_" + languageId;

			map.put(key, titleLowerCase);
			map.put(key.concat("_sortable"), titleLowerCase);
		}
	}

	public void populateTreePath(String treePath, Map<String, String> map) {
		String value = String.valueOf(
			Arrays.asList(StringUtil.split(treePath, CharPool.SLASH)));

		map.put(Field.TREE_PATH, value);
	}

	public void updateDisplaySettings(Locale locale) throws Exception {
		Group group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), null, locale);

		_group.setModelAttributes(group.getModelAttributes());
	}

	private final List<BookmarksEntry> _bookmarksEntries = new ArrayList<>();
	private final List<BookmarksFolder> _bookmarksFolders = new ArrayList<>();
	private final Group _group;
	private final User _user;

}