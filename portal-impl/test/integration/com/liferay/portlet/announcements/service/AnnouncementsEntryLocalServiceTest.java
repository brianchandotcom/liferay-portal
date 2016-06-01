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

package com.liferay.portlet.announcements.service;

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.kernel.service.AnnouncementsEntryLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Christopher Kian
 */
@Sync
public class AnnouncementsEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testDeleteGroupAnnouncements() throws Exception {
		Group group = GroupTestUtil.addGroup();

		AnnouncementsEntry entry = addAnnouncementsEntry(
			group.getClassNameId(), group.getGroupId());

		Assert.assertNotNull(
			AnnouncementsEntryLocalServiceUtil.fetchAnnouncementsEntry(
				entry.getEntryId()));

		GroupLocalServiceUtil.deleteGroup(group);

		Assert.assertNull(
			AnnouncementsEntryLocalServiceUtil.fetchAnnouncementsEntry(
				entry.getEntryId()));
	}

	@Test
	public void testDeleteOrganizationAnnouncements() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		long classNameId = ClassNameLocalServiceUtil.getClassNameId(
			Organization.class);

		AnnouncementsEntry entry = addAnnouncementsEntry(
			classNameId, organization.getOrganizationId());

		Assert.assertNotNull(
			AnnouncementsEntryLocalServiceUtil.fetchAnnouncementsEntry(
				entry.getEntryId()));

		OrganizationLocalServiceUtil.deleteOrganization(organization);

		Assert.assertNull(
			AnnouncementsEntryLocalServiceUtil.fetchAnnouncementsEntry(
				entry.getEntryId()));
	}

	@Test
	public void testDeleteOrganizationGroupAnnouncements() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		Group group = organization.getGroup();

		long classNameId = ClassNameLocalServiceUtil.getClassNameId(
			Group.class);

		AnnouncementsEntry entry = addAnnouncementsEntry(
			classNameId, group.getGroupId());

		Assert.assertNotNull(
			AnnouncementsEntryLocalServiceUtil.fetchAnnouncementsEntry(
				entry.getEntryId()));

		OrganizationLocalServiceUtil.deleteOrganization(organization);

		Assert.assertNull(
			AnnouncementsEntryLocalServiceUtil.fetchAnnouncementsEntry(
				entry.getEntryId()));
	}

	@Test
	public void testDeleteRoleAnnouncements() throws Exception {
		deleteRoleAnnouncements(RoleConstants.TYPE_ORGANIZATION);
		deleteRoleAnnouncements(RoleConstants.TYPE_REGULAR);
		deleteRoleAnnouncements(RoleConstants.TYPE_SITE);
	}

	@Test
	public void testDeleteUserGroupAnnouncements() throws Exception {
		UserGroup userGroup = UserGroupTestUtil.addUserGroup();

		long classNameId = ClassNameLocalServiceUtil.getClassNameId(
			UserGroup.class);

		AnnouncementsEntry entry = addAnnouncementsEntry(
			classNameId, userGroup.getUserGroupId());

		Assert.assertNotNull(
			AnnouncementsEntryLocalServiceUtil.fetchAnnouncementsEntry(
				entry.getEntryId()));

		UserGroupLocalServiceUtil.deleteUserGroup(userGroup);

		Assert.assertNull(
			AnnouncementsEntryLocalServiceUtil.fetchAnnouncementsEntry(
				entry.getEntryId()));
	}

	protected AnnouncementsEntry addAnnouncementsEntry(
			long classNameId, long classPK)
		throws Exception {

		return AnnouncementsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), classNameId, classPK,
			StringUtil.randomString(), StringUtil.randomString(),
			"http://localhost", "general", 1, 1, 1990, 1, 1, false, 1, 1, 3000,
			1, 1, 1, false);
	}

	protected void deleteRoleAnnouncements(int roleType) throws Exception {
		Role role = RoleTestUtil.addRole(roleType);

		AnnouncementsEntry entry = addAnnouncementsEntry(
			role.getClassNameId(), role.getRoleId());

		Assert.assertNotNull(
			AnnouncementsEntryLocalServiceUtil.fetchAnnouncementsEntry(
				entry.getEntryId()));

		RoleLocalServiceUtil.deleteRole(role);

		Assert.assertNull(
			AnnouncementsEntryLocalServiceUtil.fetchAnnouncementsEntry(
				entry.getEntryId()));
	}

}