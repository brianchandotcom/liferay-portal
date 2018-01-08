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

import com.liferay.announcements.kernel.exception.EntryDisplayDateException;
import com.liferay.announcements.kernel.exception.EntryExpirationDateException;
import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.kernel.model.AnnouncementsFlagConstants;
import com.liferay.announcements.kernel.service.AnnouncementsEntryLocalServiceUtil;
import com.liferay.announcements.kernel.service.AnnouncementsFlagLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Christopher Kian
 * @author Hugo Huijser
 */
public class AnnouncementsEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testDeleteGroupAnnouncements() throws Exception {
		Group group = GroupTestUtil.addGroup();

		AnnouncementsEntry entry = addEntry(
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

		AnnouncementsEntry entry = addEntry(
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

		AnnouncementsEntry entry = addEntry(classNameId, group.getGroupId());

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

		AnnouncementsEntry entry = addEntry(
			classNameId, userGroup.getUserGroupId());

		Assert.assertNotNull(
			AnnouncementsEntryLocalServiceUtil.fetchAnnouncementsEntry(
				entry.getEntryId()));

		UserGroupLocalServiceUtil.deleteUserGroup(userGroup);

		Assert.assertNull(
			AnnouncementsEntryLocalServiceUtil.fetchAnnouncementsEntry(
				entry.getEntryId()));
	}

	@Test
	public void testGetEntries() throws Exception {
		Group group = GroupTestUtil.addGroup();

		AnnouncementsEntry entry1 = addEntry(
			group.getClassNameId(), group.getGroupId());
		AnnouncementsEntry entry2 = addEntry(
			group.getClassNameId(), group.getGroupId());
		AnnouncementsEntry entry3 = addEntry(
			group.getClassNameId(), group.getGroupId());

		AnnouncementsFlagLocalServiceUtil.addFlag(
			TestPropsValues.getUserId(), entry1.getEntryId(),
			AnnouncementsFlagConstants.HIDDEN);
		AnnouncementsFlagLocalServiceUtil.addFlag(
			TestPropsValues.getUserId(), entry2.getEntryId(),
			AnnouncementsFlagConstants.HIDDEN);

		LinkedHashMap<Long, long[]> scopes = new LinkedHashMap<>();

		scopes.put(
			PortalUtil.getClassNameId(Group.class.getName()),
			new long[] {group.getGroupId()});

		List<AnnouncementsEntry> hiddenEntries =
			AnnouncementsEntryLocalServiceUtil.getEntries(
				TestPropsValues.getUserId(), scopes, false,
				AnnouncementsFlagConstants.HIDDEN, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(hiddenEntries.toString(), 2, hiddenEntries.size());

		List<AnnouncementsEntry> notHiddenEntries =
			AnnouncementsEntryLocalServiceUtil.getEntries(
				TestPropsValues.getUserId(), scopes, false,
				AnnouncementsFlagConstants.NOT_HIDDEN, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		Assert.assertEquals(
			notHiddenEntries.toString(), 1, notHiddenEntries.size());

		AnnouncementsEntry entry = notHiddenEntries.get(0);

		Assert.assertEquals(entry.getEntryId(), entry3.getEntryId());
	}

	protected AnnouncementsEntry addEntry(long classNameId, long classPK)
		throws Exception {

		User user = TestPropsValues.getUser();

		Date displayDate = PortalUtil.getDate(
			1, 1, 1990, 1, 1, user.getTimeZone(),
			EntryDisplayDateException.class);
		Date expirationDate = PortalUtil.getDate(
			1, 1, 3000, 1, 1, user.getTimeZone(),
			EntryExpirationDateException.class);

		return AnnouncementsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), classNameId, classPK,
			StringUtil.randomString(), StringUtil.randomString(),
			"http://localhost", "general", displayDate, expirationDate, 1,
			false);
	}

	protected void deleteRoleAnnouncements(int roleType) throws Exception {
		Role role = RoleTestUtil.addRole(roleType);

		AnnouncementsEntry entry = addEntry(
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