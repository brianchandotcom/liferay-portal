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

package com.liferay.bookmarks.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.bookmarks.uad.constants.BookmarksUADConstants;
import com.liferay.bookmarks.uad.test.BookmarksEntryUADEntityTestHelper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.test.util.BaseUADEntityAnonymizerTestCase;
import com.liferay.user.associated.data.test.util.WhenHasStatusByUserIdField;

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Noah Sherrill
 */
@RunWith(Arquillian.class)
public class BookmarksEntryUADEntityAnonymizerTest
	extends BaseUADEntityAnonymizerTestCase
	implements WhenHasStatusByUserIdField {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public Object addDataObjectWithStatusByUserId(
			long userId, long statusByUserId)
		throws Exception {

		BookmarksEntry bookmarksEntry =
			_bookmarksEntryUADEntityTestHelper.addDataObjectWithStatusByUserId(
				userId, statusByUserId);

		_bookmarksEntries.add(bookmarksEntry);

		return bookmarksEntry;
	}

	@Override
	protected Object addDataObject(long userId) throws Exception {
		BookmarksEntry bookmarksEntry =
			_bookmarksEntryUADEntityTestHelper.addBookmarksEntry(userId);

		_bookmarksEntries.add(bookmarksEntry);

		return bookmarksEntry;
	}

	@Override
	protected long getDataObjectId(Object dataObject) {
		BookmarksEntry bookmarksEntry = (BookmarksEntry)dataObject;

		return bookmarksEntry.getEntryId();
	}

	@Override
	protected String getUADRegistryKey() {
		return BookmarksUADConstants.BOOKMARKS_ENTRY;
	}

	@Override
	protected boolean isDataObjectAutoAnonymized(long dataObjectId, User user)
		throws Exception {

		BookmarksEntry bookmarksEntry = _bookmarksEntryLocalService.getEntry(
			dataObjectId);

		if ((user.getUserId() != bookmarksEntry.getStatusByUserId()) &&
			!StringUtil.equals(
				user.getScreenName(), bookmarksEntry.getStatusByUserName()) &&
			(user.getUserId() != bookmarksEntry.getUserId()) &&
			!StringUtil.equals(
				user.getFullName(), bookmarksEntry.getUserName())) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isDataObjectDeleted(long dataObjectId) {
		if (_bookmarksEntryLocalService.fetchBookmarksEntry(dataObjectId) ==
				null) {

			return true;
		}

		return false;
	}

	@DeleteAfterTestRun
	private final List<BookmarksEntry> _bookmarksEntries = new ArrayList<>();

	@Inject
	private BookmarksEntryLocalService _bookmarksEntryLocalService;

	@Inject
	private BookmarksEntryUADEntityTestHelper
		_bookmarksEntryUADEntityTestHelper;

}