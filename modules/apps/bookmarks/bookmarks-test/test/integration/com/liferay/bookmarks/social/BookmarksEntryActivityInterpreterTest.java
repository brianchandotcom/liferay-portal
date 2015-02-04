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

package com.liferay.bookmarks.social;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.bookmarks.service.BookmarksEntryServiceUtil;
import com.liferay.bookmarks.util.BookmarksTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.test.BaseSocialActivityInterpreterTestCase;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Zsolt Berentey
 */
@Sync
public class BookmarksEntryActivityInterpreterTest
	extends BaseSocialActivityInterpreterTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	protected void addActivities() throws Exception {
		_entry = BookmarksTestUtil.addEntry(group.getGroupId(), true);
	}

	@Override
	protected SocialActivityInterpreter getActivityInterpreter() {
		return new BookmarksEntryActivityInterpreter();
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
		BookmarksEntryLocalServiceUtil.moveEntryToTrash(
			TestPropsValues.getUserId(), _entry.getEntryId());
	}

	@Override
	protected void renameModels() throws Exception {
		_entry.setName(RandomTestUtil.randomString());

		serviceContext.setCommand(Constants.UPDATE);

		BookmarksEntryServiceUtil.updateEntry(
			_entry.getEntryId(), serviceContext.getScopeGroupId(),
			_entry.getFolderId(), _entry.getName(), _entry.getUrl(),
			_entry.getUrl(), serviceContext);
	}

	@Override
	protected void restoreModelsFromTrash() throws Exception {
		BookmarksEntryLocalServiceUtil.restoreEntryFromTrash(
			TestPropsValues.getUserId(), _entry.getEntryId());
	}

	private BookmarksEntry _entry;

}