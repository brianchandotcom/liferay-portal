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
import com.liferay.bookmarks.util.test.BookmarksTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
@Sync
public class BookmarksEntryResourceBlockLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_bookmarksEntry1 = BookmarksTestUtil.addEntry(
			_group.getGroupId(), true);
		_bookmarksEntry2 = BookmarksTestUtil.addEntry(
			_group.getGroupId(), true);

		_role1 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		ResourceBlockLocalServiceUtil.addIndividualScopePermission(
			_bookmarksEntry1.getCompanyId(), _bookmarksEntry1.getGroupId(),
			BookmarksEntry.class.getName(), _bookmarksEntry1,
			_role1.getRoleId(), ActionKeys.VIEW);

		ResourceBlockLocalServiceUtil.addIndividualScopePermission(
			_bookmarksEntry2.getCompanyId(), _bookmarksEntry2.getGroupId(),
			BookmarksEntry.class.getName(), _bookmarksEntry2,
			_role1.getRoleId(), ActionKeys.VIEW);

		_role2 = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		ResourceBlockLocalServiceUtil.addIndividualScopePermission(
			_bookmarksEntry1.getCompanyId(), _bookmarksEntry1.getGroupId(),
			BookmarksEntry.class.getName(), _bookmarksEntry1,
			_role2.getRoleId(), ActionKeys.VIEW);
	}

	@Test
	public void testGetRoles() throws Exception {
		List<Role> roles = ResourceBlockLocalServiceUtil.getRoles(
			BookmarksEntry.class.getName(), _bookmarksEntry1.getEntryId(),
			ActionKeys.VIEW);

		Assert.assertTrue(roles.contains(_role1));
		Assert.assertTrue(roles.contains(_role2));

		roles = ResourceBlockLocalServiceUtil.getRoles(
			BookmarksEntry.class.getName(), _bookmarksEntry2.getEntryId(),
			ActionKeys.VIEW);

		Assert.assertTrue(roles.contains(_role1));
		Assert.assertFalse(roles.contains(_role2));
	}

	private BookmarksEntry _bookmarksEntry1;
	private BookmarksEntry _bookmarksEntry2;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Role _role1;

	@DeleteAfterTestRun
	private Role _role2;

}