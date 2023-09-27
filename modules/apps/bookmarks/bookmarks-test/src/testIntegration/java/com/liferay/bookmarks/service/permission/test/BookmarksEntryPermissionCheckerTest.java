/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.service.permission.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bookmarks.constants.BookmarksConstants;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.test.util.BookmarksTestUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.security.permission.test.util.BasePermissionTestCase;
import com.liferay.portal.test.rule.Inject;
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
public class BookmarksEntryPermissionCheckerTest
	extends BasePermissionTestCase {

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

	@Test
	public void testContains() throws Exception {
		Assert.assertTrue(
			_bookmarksEntryModelResourcePermission.contains(
				permissionChecker, _entry.getEntryId(), ActionKeys.VIEW));
		Assert.assertTrue(
			_bookmarksEntryModelResourcePermission.contains(
				permissionChecker, _subentry.getEntryId(), ActionKeys.VIEW));

		removePortletModelViewPermission();

		Assert.assertFalse(
			_bookmarksEntryModelResourcePermission.contains(
				permissionChecker, _entry.getEntryId(), ActionKeys.VIEW));
		Assert.assertFalse(
			_bookmarksEntryModelResourcePermission.contains(
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
		return BookmarksConstants.RESOURCE_NAME;
	}

	@Inject(
		filter = "model.class.name=com.liferay.bookmarks.model.BookmarksEntry"
	)
	private static ModelResourcePermission<BookmarksEntry>
		_bookmarksEntryModelResourcePermission;

	private BookmarksEntry _entry;
	private BookmarksEntry _subentry;

}