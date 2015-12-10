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

package com.liferay.bookmarks.service.permission.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.service.permission.BookmarksEntryPermissionChecker;
import com.liferay.bookmarks.service.permission.BookmarksResourcePermissionChecker;
import com.liferay.bookmarks.util.test.BookmarksTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CompanyProviderTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.test.BasePermissionTestCase;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eric Chin
 * @author Shinn Lok
 */
@RunWith(Arquillian.class)
@Sync
public class BookmarksEntryPermissionCheckerTest
	extends BasePermissionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), CompanyProviderTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();
	}

	@Test
	public void testContains() throws Exception {
		Assert.assertTrue(
			BookmarksEntryPermissionChecker.contains(
				permissionChecker, _entry.getEntryId(), ActionKeys.VIEW));
		Assert.assertTrue(
			BookmarksEntryPermissionChecker.contains(
				permissionChecker, _subentry.getEntryId(), ActionKeys.VIEW));

		removePortletModelViewPermission();

		Assert.assertFalse(
			BookmarksEntryPermissionChecker.contains(
				permissionChecker, _entry.getEntryId(), ActionKeys.VIEW));
		Assert.assertFalse(
			BookmarksEntryPermissionChecker.contains(
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
		return BookmarksResourcePermissionChecker.RESOURCE_NAME;
	}

	private BookmarksEntry _entry;
	private BookmarksEntry _subentry;

}