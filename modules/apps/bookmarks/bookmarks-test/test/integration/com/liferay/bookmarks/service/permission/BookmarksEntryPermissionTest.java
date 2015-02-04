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

package com.liferay.bookmarks.service.permission;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.util.BookmarksTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.test.BasePermissionTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Eric Chin
 * @author Shinn Lok
 */
public class BookmarksEntryPermissionTest extends BasePermissionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testContains() throws Exception {
		Assert.assertTrue(
			BookmarksEntryPermission.contains(
				permissionChecker, _entry.getEntryId(), ActionKeys.VIEW));
		Assert.assertTrue(
			BookmarksEntryPermission.contains(
				permissionChecker, _subentry.getEntryId(), ActionKeys.VIEW));

		removePortletModelViewPermission();

		Assert.assertFalse(
			BookmarksEntryPermission.contains(
				permissionChecker, _entry.getEntryId(), ActionKeys.VIEW));
		Assert.assertFalse(
			BookmarksEntryPermission.contains(
				permissionChecker, _subentry.getEntryId(), ActionKeys.VIEW));
	}

	@Override
	protected void doSetUp() throws Exception {
		_entry = BookmarksTestUtil.addEntry(group.getGroupId(), true);

		BookmarksFolder folder = BookmarksTestUtil.addFolder(
			group.getGroupId(), RandomTestUtil.randomString());

		_subentry = BookmarksTestUtil.addEntry(
			folder.getFolderId(), true, serviceContext);
	}

	@Override
	protected String getResourceName() {
		return BookmarksPermission.RESOURCE_NAME;
	}

	private BookmarksEntry _entry;
	private BookmarksEntry _subentry;

}