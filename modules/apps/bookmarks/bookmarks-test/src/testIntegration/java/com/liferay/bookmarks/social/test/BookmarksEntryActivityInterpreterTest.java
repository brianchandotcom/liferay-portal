/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.social.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.bookmarks.service.BookmarksEntryService;
import com.liferay.bookmarks.social.BookmarksActivityKeys;
import com.liferay.bookmarks.test.util.BookmarksTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.social.activity.test.util.BaseSocialActivityInterpreterTestCase;
import com.liferay.social.kernel.model.SocialActivityConstants;
import com.liferay.social.kernel.model.SocialActivityInterpreter;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@RunWith(Arquillian.class)
public class BookmarksEntryActivityInterpreterTest
	extends BaseSocialActivityInterpreterTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();
	}

	@Override
	protected void addActivities() throws Exception {
		_entry = BookmarksTestUtil.addEntry(group.getGroupId(), true);
	}

	@Override
	protected SocialActivityInterpreter getActivityInterpreter() {
		return _socialActivityInterpreter;
	}

	@Override
	protected int[] getActivityTypes() {
		return new int[] {
			BookmarksActivityKeys.ADD_ENTRY, BookmarksActivityKeys.UPDATE_ENTRY,
			SocialActivityConstants.TYPE_MOVE_TO_TRASH,
			SocialActivityConstants.TYPE_RESTORE_FROM_TRASH
		};
	}

	@Override
	protected void moveModelsToTrash() throws Exception {
		_bookmarksEntryLocalService.moveEntryToTrash(
			TestPropsValues.getUserId(), _entry.getEntryId());
	}

	@Override
	protected void renameModels() throws Exception {
		_entry.setName(RandomTestUtil.randomString());

		serviceContext.setCommand(Constants.UPDATE);

		_bookmarksEntryService.updateEntry(
			_entry.getEntryId(), serviceContext.getScopeGroupId(),
			_entry.getFolderId(), _entry.getName(), _entry.getUrl(),
			_entry.getUrl(), serviceContext);
	}

	@Override
	protected void restoreModelsFromTrash() throws Exception {
		_bookmarksEntryLocalService.restoreEntryFromTrash(
			TestPropsValues.getUserId(), _entry.getEntryId());
	}

	@Inject
	private static BookmarksEntryLocalService _bookmarksEntryLocalService;

	@Inject
	private static BookmarksEntryService _bookmarksEntryService;

	@Inject(
		filter = "model.class.name=com.liferay.bookmarks.model.BookmarksEntry"
	)
	private static SocialActivityInterpreter _socialActivityInterpreter;

	private BookmarksEntry _entry;

}