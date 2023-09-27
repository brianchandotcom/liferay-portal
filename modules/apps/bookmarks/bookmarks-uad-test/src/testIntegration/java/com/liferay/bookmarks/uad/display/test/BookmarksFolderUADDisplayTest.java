/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.uad.display.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.service.BookmarksFolderLocalService;
import com.liferay.bookmarks.uad.test.BookmarksFolderUADTestUtil;
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
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class BookmarksFolderUADDisplayTest
	extends BaseUADDisplayTestCase<BookmarksFolder> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected BookmarksFolder addBaseModel(long userId) throws Exception {
		BookmarksFolder bookmarksFolder =
			BookmarksFolderUADTestUtil.addBookmarksFolder(
				_bookmarksFolderLocalService, userId);

		_bookmarksFolders.add(bookmarksFolder);

		return bookmarksFolder;
	}

	@Override
	protected UADDisplay<BookmarksFolder> getUADDisplay() {
		return _uadDisplay;
	}

	@Inject
	private BookmarksFolderLocalService _bookmarksFolderLocalService;

	@DeleteAfterTestRun
	private final List<BookmarksFolder> _bookmarksFolders = new ArrayList<>();

	@Inject(
		filter = "component.name=com.liferay.bookmarks.uad.display.BookmarksFolderUADDisplay"
	)
	private UADDisplay<BookmarksFolder> _uadDisplay;

}