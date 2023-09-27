/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.bookmarks.uad.test.BookmarksEntryUADTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseUADAnonymizerTestCase;
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
public class BookmarksEntryUADAnonymizerTest
	extends BaseUADAnonymizerTestCase<BookmarksEntry>
	implements WhenHasStatusByUserIdField<BookmarksEntry> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public BookmarksEntry addBaseModelWithStatusByUserId(
			long userId, long statusByUserId)
		throws Exception {

		BookmarksEntry bookmarksEntry =
			BookmarksEntryUADTestUtil.addBookmarksEntryWithStatusByUserId(
				_bookmarksEntryLocalService, userId, statusByUserId);

		_bookmarksEntries.add(bookmarksEntry);

		return bookmarksEntry;
	}

	@Override
	protected BookmarksEntry addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected BookmarksEntry addBaseModel(
			long userId, boolean deleteAfterTestRun)
		throws Exception {

		BookmarksEntry bookmarksEntry =
			BookmarksEntryUADTestUtil.addBookmarksEntry(
				_bookmarksEntryLocalService, userId);

		if (deleteAfterTestRun) {
			_bookmarksEntries.add(bookmarksEntry);
		}

		return bookmarksEntry;
	}

	@Override
	protected UADAnonymizer<BookmarksEntry> getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		BookmarksEntry bookmarksEntry = _bookmarksEntryLocalService.getEntry(
			baseModelPK);

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
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_bookmarksEntryLocalService.fetchBookmarksEntry(baseModelPK) ==
				null) {

			return true;
		}

		return false;
	}

	@DeleteAfterTestRun
	private final List<BookmarksEntry> _bookmarksEntries = new ArrayList<>();

	@Inject
	private BookmarksEntryLocalService _bookmarksEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.bookmarks.uad.anonymizer.BookmarksEntryUADAnonymizer"
	)
	private UADAnonymizer<BookmarksEntry> _uadAnonymizer;

}