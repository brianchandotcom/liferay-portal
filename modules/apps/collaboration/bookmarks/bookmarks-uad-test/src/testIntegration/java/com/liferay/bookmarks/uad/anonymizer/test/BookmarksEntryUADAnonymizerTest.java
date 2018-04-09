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
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.aggregator.UADAggregator;
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
	extends BaseUADAnonymizerTestCase implements WhenHasStatusByUserIdField {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public BaseModel<?> addBaseModelWithStatusByUserId(
			long userId, long statusByUserId)
		throws Exception {

		BookmarksEntry bookmarksEntry =
			_bookmarksEntryUADEntityTestHelper.
				addBookmarksEntryWithStatusByUserId(userId, statusByUserId);

		_bookmarksEntries.add(bookmarksEntry);

		return bookmarksEntry;
	}

	@Override
	protected BaseModel<?> addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected BaseModel<?> addBaseModel(long userId, boolean deleteAfterTestRun)
		throws Exception {

		BookmarksEntry bookmarksEntry =
			_bookmarksEntryUADEntityTestHelper.addBookmarksEntry(userId);

		if (deleteAfterTestRun) {
			_bookmarksEntries.add(bookmarksEntry);
		}

		return bookmarksEntry;
	}

	@Override
	protected UADAggregator getUADAggregator() {
		return _uadAggregator;
	}

	@Override
	protected UADAnonymizer getUADAnonymizer() {
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

	@Inject
	private BookmarksEntryUADEntityTestHelper
		_bookmarksEntryUADEntityTestHelper;

	@Inject(
		filter = "model.class.name=" + BookmarksUADConstants.CLASS_NAME_BOOKMARKS_ENTRY
	)
	private UADAggregator _uadAggregator;

	@Inject(
		filter = "model.class.name=" + BookmarksUADConstants.CLASS_NAME_BOOKMARKS_ENTRY
	)
	private UADAnonymizer _uadAnonymizer;

}