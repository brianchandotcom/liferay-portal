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

package com.liferay.bookmarks.uad.display.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.uad.constants.BookmarksUADConstants;
import com.liferay.bookmarks.uad.test.BookmarksEntryUADTestHelper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.test.util.BaseUADDisplayTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Noah Sherrill
 */
@RunWith(Arquillian.class)
public class BookmarksEntryUADDisplayTest
	extends BaseUADDisplayTestCase<BookmarksEntry> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected BookmarksEntry addBaseModel(long userId) throws Exception {
		BookmarksEntry bookmarksEntry =
			_bookmarksEntryUADTestHelper.addBookmarksEntry(userId);

		_bookmarksEntries.add(bookmarksEntry);

		return bookmarksEntry;
	}

	@Override
	protected String getApplicationName() {
		return BookmarksUADConstants.APPLICATION_NAME;
	}

	@Override
	protected UADDisplay getUADDisplay() {
		return _uadDisplay;
	}

	@DeleteAfterTestRun
	private final List<BookmarksEntry> _bookmarksEntries = new ArrayList<>();

	@Inject
	private BookmarksEntryUADTestHelper _bookmarksEntryUADTestHelper;

	@Inject(filter = "component.name=*.BookmarksEntryUADDisplay")
	private UADDisplay _uadDisplay;

}