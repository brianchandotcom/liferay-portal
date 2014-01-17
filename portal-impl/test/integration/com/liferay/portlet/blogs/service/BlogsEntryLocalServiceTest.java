/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.service;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.OrganizationTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.util.BlogsTestUtil;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class BlogsEntryLocalServiceTest {

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();

		queryStatusInTrash = new QueryDefinition(
				WorkflowConstants.STATUS_IN_TRASH, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		queryStatusNotInTrash = new QueryDefinition(
				WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		queryStatusApproved = new QueryDefinition(
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(group);
	}

	@Test
	public void testAddEntryNotSmallImage() throws Exception {
		int initialCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
			group.getGroupId(), queryStatusApproved);

		BlogsEntry blogsEntry = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);

		int actualCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
			group.getGroupId(), queryStatusApproved);

		Assert.assertEquals(initialCount + 1, actualCount);

		BlogsEntry blogsEntryObtained =
			BlogsEntryLocalServiceUtil.getBlogsEntry(blogsEntry.getEntryId());

		BlogsTestUtil.assertEqualEntry(blogsEntry, blogsEntryObtained);
	}

	@Test
	public void testAddEntryResourcesEntry() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		serviceContext.setGroupPermissions(
			new String[] {ActionKeys.ADD_DISCUSSION});
		serviceContext.setGuestPermissions(new String[] {ActionKeys.VIEW});

		BlogsEntry blogsEntry = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);

		BlogsEntryLocalServiceUtil.addEntryResources(blogsEntry, true, true);
	}

	@Test
	public void testAddEntryResourcesEntryId() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		serviceContext.setGroupPermissions(
			new String[] {ActionKeys.ADD_DISCUSSION});
		serviceContext.setGuestPermissions(new String[] {ActionKeys.VIEW});

		BlogsEntry blogsEntry = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);

		BlogsEntryLocalServiceUtil.addEntryResources(
			blogsEntry.getEntryId(), true, true);
	}

	@Test
	public void testAddEntryResourcesEntryIdListPermissions() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		BlogsEntry blogsEntry = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);

		BlogsEntryLocalServiceUtil.addEntryResources(
			blogsEntry.getEntryId(), serviceContext.getGroupPermissions(),
			serviceContext.getGuestPermissions());
	}

	@Test
	public void testAddEntryResourcesEntryListPermissions() throws Exception {
		BlogsEntry blogsEntry = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);

		BlogsEntryLocalServiceUtil.addEntryResources(
			blogsEntry, new String[] {ActionKeys.ADD_DISCUSSION},
			new String[] {ActionKeys.VIEW});
	}

	@Test
	public void testGetCompanyEntriesCountInTrash() throws Exception {
		User user = TestPropsValues.getUser();

		int initialCount =
			BlogsEntryLocalServiceUtil.getCompanyEntriesCount(
				user.getCompanyId(), new Date(), queryStatusInTrash);

		addEntryTrashAndEntryNotTrash(user);

		int actualCount =
			BlogsEntryLocalServiceUtil.getCompanyEntriesCount(
				user.getCompanyId(), new Date(), queryStatusInTrash);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test
	public void testGetCompanyEntriesCountNotInTrash() throws Exception {
		User user = TestPropsValues.getUser();

		int initialCount = BlogsEntryLocalServiceUtil.getCompanyEntriesCount(
			user.getCompanyId(), new Date(), queryStatusNotInTrash);

		addEntryTrashAndEntryNotTrash(user);

		int actualCount =
			BlogsEntryLocalServiceUtil.getCompanyEntriesCount(
				user.getCompanyId(), new Date(), queryStatusNotInTrash);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test
	public void testGetCompanyEntriesInTrash() throws Exception {
		User user = TestPropsValues.getUser();

		List<BlogsEntry> companyEntries =
			BlogsEntryLocalServiceUtil.getCompanyEntries(
				user.getCompanyId(), new Date(), queryStatusInTrash);

		int initialCount = companyEntries.size();

		addEntryTrashAndEntryNotTrash(user);

		List<BlogsEntry> companyEntriesInTrash =
			BlogsEntryLocalServiceUtil.getCompanyEntries(
				user.getCompanyId(), new Date(), queryStatusInTrash);

		Assert.assertEquals(initialCount + 1, companyEntriesInTrash.size());

		for (BlogsEntry companyEntry : companyEntriesInTrash) {
			if (WorkflowConstants.STATUS_IN_TRASH != companyEntry.getStatus()) {
				Assert.fail(
					"The blogEntry " + companyEntry.getEntryId() +
						" is not in trash");
			}
		}
	}

	@Test
	public void testGetCompanyEntriesNotInTrash() throws Exception {
		User user = TestPropsValues.getUser();

		List<BlogsEntry> companyEntries =
			BlogsEntryLocalServiceUtil.getCompanyEntries(
				user.getCompanyId(), new Date(), queryStatusNotInTrash);

		int initialCount = companyEntries.size();

		addEntryTrashAndEntryNotTrash(user);

		List<BlogsEntry> companyEntriesNotInTrash =
			BlogsEntryLocalServiceUtil.getCompanyEntries(
				user.getCompanyId(), new Date(), queryStatusNotInTrash);

		Assert.assertEquals(initialCount + 1, companyEntriesNotInTrash.size());

		for (BlogsEntry companyEntry : companyEntriesNotInTrash) {
			if (WorkflowConstants.STATUS_IN_TRASH == companyEntry.getStatus()) {
				Assert.fail(
					"The blogEntry " + companyEntry.getEntryId() +
					" is in trash");
			}
		}
	}

	@Test
	public void testGetEntriesPrevAndNextCenterElement() throws Exception {
		BlogsEntry entryPrevious = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);

		BlogsEntry entryCenter = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);

		BlogsEntry entryNext = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);

		BlogsEntry[] entriesPrevAndNextForCenter =
			BlogsEntryLocalServiceUtil.getEntriesPrevAndNext(
				entryCenter.getEntryId());

		Assert.assertNotNull(
			"The previous element for the blog " + entryCenter.getEntryId() +
			" should be " + entryPrevious.getEntryId() + " but is null instead",
			entriesPrevAndNextForCenter[0]);

		Assert.assertNotNull(
			"The center element for the blog " + entryCenter.getEntryId() +
			" should be " + entryCenter.getEntryId() + " but is null instead",
			entriesPrevAndNextForCenter[1]);

		Assert.assertNotNull(
			"The next element for the blog " + entryCenter.getEntryId() +
			" should be " + entryNext.getEntryId() + " but is null instead",
			entriesPrevAndNextForCenter[2]);

		Assert.assertEquals(
			"The left element " + entriesPrevAndNextForCenter[0].getEntryId() +
				" should be " + entryPrevious.getEntryId(),
			entriesPrevAndNextForCenter[0].getEntryId(),
			entryPrevious.getEntryId());

		Assert.assertEquals(
			"The right element " + entriesPrevAndNextForCenter[2].getEntryId() +
			" should be " + entryPrevious.getEntryId(),
			entriesPrevAndNextForCenter[2].getEntryId(),
			entryNext.getEntryId());

		Assert.assertEquals(
			"The center element " +
			entriesPrevAndNextForCenter[1].getEntryId() +
			" should be " + entryCenter.getEntryId(),
			entriesPrevAndNextForCenter[1].getEntryId(),
			entryCenter.getEntryId());
	}

	@Test
	public void testGetEntriesPrevAndNextTopLeftElement() throws Exception {
		BlogsEntry entryPrevious = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);

		BlogsEntry entryCenter = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);

		BlogsTestUtil.addEntry(TestPropsValues.getUserId(), group, true);

		BlogsEntry[] entriesPrevAndNextForTopLeft =
			BlogsEntryLocalServiceUtil.getEntriesPrevAndNext(
				entryPrevious.getEntryId());

		Assert.assertNull(
			"The previous element for the blog " + entryPrevious.getEntryId() +
			" should be null", entriesPrevAndNextForTopLeft[0]);

		Assert.assertNotNull(
			"The center element for the blog " + entryPrevious.getEntryId() +
			" should be " + entryPrevious.getEntryId() +
			" but is null instead",entriesPrevAndNextForTopLeft[1]);

		Assert.assertNotNull(
			"The next element for the blog " + entryCenter.getEntryId() +
			" should be " + entryCenter.getEntryId() + " but is null instead",
			entriesPrevAndNextForTopLeft[2]);

		Assert.assertEquals(
			"The right element " +
			entriesPrevAndNextForTopLeft[2].getEntryId() +
			" should be " + entryCenter.getEntryId(),
			entriesPrevAndNextForTopLeft[2].getEntryId(),
			entryCenter.getEntryId());

		Assert.assertEquals(
			"The center element " +
			entriesPrevAndNextForTopLeft[1].getEntryId() +
			" should be " + entryPrevious.getEntryId(),
			entriesPrevAndNextForTopLeft[1].getEntryId(),
			entryPrevious.getEntryId());
	}

	@Test
	public void testGetEntriesPrevAndNextTopRightElement() throws Exception {
		BlogsTestUtil.addEntry(TestPropsValues.getUserId(), group, true);

		BlogsEntry entryCenter = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);

		BlogsEntry entryNext = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);

		BlogsEntry[] entriesPrevAndNextForTopLeft =
			BlogsEntryLocalServiceUtil.getEntriesPrevAndNext(
				entryNext.getEntryId());

		Assert.assertNull(
			"The next element for the blog " +
			entryNext.getEntryId() + " should be null",
			entriesPrevAndNextForTopLeft[2]);

		Assert.assertNotNull(
			"The center element for the blog " + entryNext.getEntryId() +
			" should be " + entryNext.getEntryId() + " but is null instead",
			entriesPrevAndNextForTopLeft[1]);

		Assert.assertNotNull(
			"The previous element for the blog " + entryNext.getEntryId() +
			" should be " + entryCenter.getEntryId() + " but is null instead",
			entriesPrevAndNextForTopLeft[0]);

		Assert.assertEquals(
			"The left element " + entriesPrevAndNextForTopLeft[0].getEntryId() +
			" should be " + entryCenter.getEntryId(),
			entriesPrevAndNextForTopLeft[0].getEntryId(),
			entryCenter.getEntryId());

		Assert.assertEquals(
			"The center element " +
			entriesPrevAndNextForTopLeft[1].getEntryId() + " should be " +
			entryNext.getEntryId(),
			entriesPrevAndNextForTopLeft[1].getEntryId(),
			entryNext.getEntryId());
	}

	@Test
	public void testGetEntryByUrlAndGroup() throws Exception {
		BlogsEntry entry = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);

		BlogsEntry entryObtained = BlogsEntryLocalServiceUtil.getEntry(
			entry.getGroupId(), entry.getUrlTitle());

		BlogsTestUtil.assertEqualEntry(entry, entryObtained);
	}

	@Test
	public void testGetGroupEntriesCompany() throws Exception {
		User user = TestPropsValues.getUser();

		List<BlogsEntry> groupEntries =
			BlogsEntryLocalServiceUtil.getGroupsEntries(
				user.getCompanyId(), group.getGroupId(), new Date(),
				queryStatusInTrash);

		int initialCount = groupEntries.size();

		addEntryTrashAndEntryNotTrash(user);

		List<BlogsEntry> groupEntriesInTrash =
			BlogsEntryLocalServiceUtil.getGroupsEntries(
				user.getCompanyId(), group.getGroupId(), new Date(),
				queryStatusInTrash);

		Assert.assertEquals(initialCount + 1, groupEntriesInTrash.size());

		for (BlogsEntry groupEntry : groupEntriesInTrash) {
			if (WorkflowConstants.STATUS_IN_TRASH != groupEntry.getStatus()) {
				Assert.fail(
					"The blogEntry " + groupEntry.getEntryId() +
						" is not in trash");
			}

			Assert.assertNotEquals(groupEntry.getCompanyId(), 0);

			if (groupEntry.getCompanyId() != user.getCompanyId()) {
				Assert.fail(
					"The companyId of the BlogEntry" + groupEntry.getEntryId() +
					" should be " + user.getCompanyId() + " bus is " +
					groupEntry.getCompanyId() + " instead");
			}
		}
	}

	@Test
	public void testGetGroupEntriesCountInTrashNoDisplayDate()
		throws Exception {
			User user = TestPropsValues.getUser();

			int initialCount =
				BlogsEntryLocalServiceUtil.getGroupEntriesCount(
					group.getGroupId(), queryStatusInTrash);

			addEntryTrashAndEntryNotTrash(user);

			int actualCount =
				BlogsEntryLocalServiceUtil.getGroupEntriesCount(
					group.getGroupId(), queryStatusInTrash);

			Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test
	public void testGetGroupEntriesCountInTrashWithDisplayDate()
		throws Exception {
			User user = TestPropsValues.getUser();

			int initialCount =
				BlogsEntryLocalServiceUtil.getGroupEntriesCount(
					group.getGroupId(), new Date(), queryStatusInTrash);

			addEntryTrashAndEntryNotTrash(user);

			int actualCount =
				BlogsEntryLocalServiceUtil.getGroupEntriesCount(
					group.getGroupId(), new Date(), queryStatusInTrash);

			Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test
	public void testGetGroupEntriesCountNotInTrashNoDisplayDate()
		throws Exception {
			User user = TestPropsValues.getUser();

			int initialCount =
				BlogsEntryLocalServiceUtil.getGroupEntriesCount(
					group.getGroupId(), queryStatusNotInTrash);

			addEntryTrashAndEntryNotTrash(user);

			int actualCount =
				BlogsEntryLocalServiceUtil.getGroupEntriesCount(
					group.getGroupId(), queryStatusNotInTrash);

			Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test
	public void testGetGroupEntriesCountNotInTrashWithDisplayDate()
		throws Exception {
			User user = TestPropsValues.getUser();

			int initialCount =
				BlogsEntryLocalServiceUtil.getGroupEntriesCount(
					group.getGroupId(), new Date(), queryStatusNotInTrash);

			addEntryTrashAndEntryNotTrash(user);

			int actualCount =
				BlogsEntryLocalServiceUtil.getGroupEntriesCount(
					group.getGroupId(), new Date(), queryStatusNotInTrash);

			Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test
	public void testGetGroupEntriesInTrashNoDisplayDate() throws Exception {
		User user = TestPropsValues.getUser();

		List<BlogsEntry> groupEntries =
			BlogsEntryLocalServiceUtil.getGroupEntries(
				group.getGroupId(), queryStatusInTrash);

		int initialCount = groupEntries.size();

		addEntryTrashAndEntryNotTrash(user);

		List<BlogsEntry> groupEntriesInTrash =
			BlogsEntryLocalServiceUtil.getGroupEntries(
				group.getGroupId(), queryStatusInTrash);

		Assert.assertEquals(initialCount + 1, groupEntriesInTrash.size());

		for (BlogsEntry groupEntry : groupEntriesInTrash) {
			if (WorkflowConstants.STATUS_IN_TRASH != groupEntry.getStatus()) {
				Assert.fail(
					"The blogEntry " + groupEntry.getEntryId() +
						" is not in trash");
			}
		}
	}

	@Test
	public void testGetGroupEntriesInTrashWithDisplayDate() throws Exception {
		User user = TestPropsValues.getUser();

		List<BlogsEntry> groupEntries =
			BlogsEntryLocalServiceUtil.getGroupEntries(
				group.getGroupId(), new Date(), queryStatusInTrash);

		int initialCount = groupEntries.size();

		addEntryTrashAndEntryNotTrash(user);

		List<BlogsEntry> groupEntriesInTrash =
			BlogsEntryLocalServiceUtil.getGroupEntries(
				group.getGroupId(), new Date(), queryStatusInTrash);

		Assert.assertEquals(initialCount + 1, groupEntriesInTrash.size());

		for (BlogsEntry groupEntry : groupEntriesInTrash) {
			if (WorkflowConstants.STATUS_IN_TRASH != groupEntry.getStatus()) {
				Assert.fail(
					"The blogEntry " + groupEntry.getEntryId() +
						" is not in trash");
			}
		}
	}

	@Test
	public void testGetGroupEntriesNotInTrashNoDisplayDate() throws Exception {
		User user = TestPropsValues.getUser();

		List<BlogsEntry> groupEntries =
			BlogsEntryLocalServiceUtil.getGroupEntries(
				group.getGroupId(), queryStatusNotInTrash);

		int initialCount = groupEntries.size();

		addEntryTrashAndEntryNotTrash(user);

		List<BlogsEntry> groupEntriesNotInTrash =
			BlogsEntryLocalServiceUtil.getGroupEntries(
				group.getGroupId(), queryStatusNotInTrash);

		Assert.assertEquals(initialCount + 1, groupEntriesNotInTrash.size());

		for (BlogsEntry groupEntry : groupEntriesNotInTrash) {
			if (WorkflowConstants.STATUS_IN_TRASH == groupEntry.getStatus()) {
				Assert.fail(
					"The blogEntry " + groupEntry.getEntryId() +
						" is in trash");
			}
		}
	}

	@Test
	public void testGetGroupEntriesNotInTrashWithDisplayDate()
		throws Exception {
			User user = TestPropsValues.getUser();

			List<BlogsEntry> groupEntries =
				BlogsEntryLocalServiceUtil.getGroupEntries(
					group.getGroupId(), new Date(), queryStatusNotInTrash);

			int initialCount = groupEntries.size();

			addEntryTrashAndEntryNotTrash(user);

			List<BlogsEntry> groupEntriesNotInTrash =
				BlogsEntryLocalServiceUtil.getGroupEntries(
					group.getGroupId(), new Date(), queryStatusNotInTrash);

			Assert.assertEquals(
				initialCount + 1, groupEntriesNotInTrash.size());

			for (BlogsEntry groupEntry : groupEntriesNotInTrash) {
				if (
						WorkflowConstants.STATUS_IN_TRASH ==
							groupEntry.getStatus()) {
								Assert.fail(
									"The blogEntry " + groupEntry.getEntryId() +
									" is in trash");
				}
			}
	}

	@Test
	public void testGetGroupUserEntriesCountInTrash() throws Exception {
		User user = TestPropsValues.getUser();

		int initialCount =
			BlogsEntryLocalServiceUtil.getGroupUserEntriesCount(
				group.getGroupId(), user.getUserId(), new Date(),
				queryStatusInTrash);

		addEntryTrashAndEntryNotTrash(user);

		int actualCount =
			BlogsEntryLocalServiceUtil.getGroupUserEntriesCount(
				group.getGroupId(), user.getUserId(), new Date(),
				queryStatusInTrash);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test
	public void testGetGroupUserEntriesCountNotInTrash() throws Exception {
		User user = TestPropsValues.getUser();

		int initialCount =
			BlogsEntryLocalServiceUtil.getGroupUserEntriesCount(
				group.getGroupId(), user.getUserId(), new Date(),
				queryStatusNotInTrash);

		addEntryTrashAndEntryNotTrash(user);

		int actualCount =
			BlogsEntryLocalServiceUtil.getGroupUserEntriesCount(
				group.getGroupId(), user.getUserId(), new Date(),
				queryStatusNotInTrash);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test
	public void testGetGroupUserEntriesInTrash() throws Exception {
		User user = TestPropsValues.getUser();

		List<BlogsEntry> groupEntries =
			BlogsEntryLocalServiceUtil.getGroupUserEntries(
				group.getGroupId(), user.getUserId(), new Date(),
				queryStatusInTrash);

		int initialCount = groupEntries.size();

		addEntryTrashAndEntryNotTrash(user);

		List<BlogsEntry> groupEntriesInTrash =
			BlogsEntryLocalServiceUtil.getGroupUserEntries(
				group.getGroupId(), user.getUserId(), new Date(),
				queryStatusInTrash);

		Assert.assertEquals(initialCount + 1, groupEntriesInTrash.size());

		for (BlogsEntry groupEntry : groupEntriesInTrash) {
			if (WorkflowConstants.STATUS_IN_TRASH != groupEntry.getStatus()) {
				Assert.fail(
					"The blogEntry " + groupEntry.getEntryId() +
						" is not in trash");
			}
		}
	}

	@Test
	public void testGetGroupUserEntriesNotInTrash() throws Exception {
		User user = TestPropsValues.getUser();

		List<BlogsEntry> groupEntries =
			BlogsEntryLocalServiceUtil.getGroupUserEntries(
				group.getGroupId(), user.getUserId(), new Date(),
				queryStatusNotInTrash);

		int initialCount = groupEntries.size();

		addEntryTrashAndEntryNotTrash(user);

		List<BlogsEntry> groupEntriesNotInTrash =
			BlogsEntryLocalServiceUtil.getGroupUserEntries(
				group.getGroupId(), user.getUserId(), new Date(),
				queryStatusNotInTrash);

		Assert.assertEquals(initialCount + 1, groupEntriesNotInTrash.size());

		for (BlogsEntry groupEntry : groupEntriesNotInTrash) {
			if (WorkflowConstants.STATUS_IN_TRASH == groupEntry.getStatus()) {
				Assert.fail(
					"The blogEntry " + groupEntry.getEntryId() +
						" is in trash");
			}
		}
	}

	@Test
	public void testGetOrganizationEntriesCountInTrash() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();
		User user = UserTestUtil.addOrganizationOwnerUser(organization);

		int initialCount =
			BlogsEntryLocalServiceUtil.getOrganizationEntriesCount(
				organization.getOrganizationId(), new Date(),
				queryStatusInTrash);

		addEntryTrashAndEntryNotTrash(user);

		int actualCount =
			BlogsEntryLocalServiceUtil.getOrganizationEntriesCount(
				organization.getOrganizationId(), new Date(),
				queryStatusInTrash);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test
	public void testGetOrganizationEntriesCountNotInTrash() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();
		User user = UserTestUtil.addOrganizationOwnerUser(organization);

		int initialCount =
			BlogsEntryLocalServiceUtil.getOrganizationEntriesCount(
				organization.getOrganizationId(), new Date(),
				queryStatusNotInTrash);

		addEntryTrashAndEntryNotTrash(user);

		int actualCount =
			BlogsEntryLocalServiceUtil.getOrganizationEntriesCount(
				organization.getOrganizationId(), new Date(),
				queryStatusNotInTrash);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test
	public void testGetOrganizationEntriesInTrash() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();
		User user = UserTestUtil.addOrganizationOwnerUser(organization);

		List<BlogsEntry> groupEntries =
			BlogsEntryLocalServiceUtil.getOrganizationEntries(
				organization.getOrganizationId(), new Date(),
				queryStatusInTrash);

		int initialCount = groupEntries.size();

		addEntryTrashAndEntryNotTrash(user);

		List<BlogsEntry> groupEntriesInTrash =
			BlogsEntryLocalServiceUtil.getOrganizationEntries(
				organization.getOrganizationId(), new Date(),
				queryStatusInTrash);

		Assert.assertEquals(initialCount + 1, groupEntriesInTrash.size());

		for (BlogsEntry groupEntry : groupEntriesInTrash) {
			if (WorkflowConstants.STATUS_IN_TRASH != groupEntry.getStatus()) {
				Assert.fail(
					"The blogEntry " + groupEntry.getEntryId() +
						" is not in trash");
			}
		}
	}

	@Test
	public void testGetOrganizationEntriesNotInTrash() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();
		User user = UserTestUtil.addOrganizationOwnerUser(organization);

		List<BlogsEntry> groupEntries =
			BlogsEntryLocalServiceUtil.getOrganizationEntries(
				organization.getOrganizationId(), new Date(),
				queryStatusNotInTrash);

		int initialCount = groupEntries.size();

		addEntryTrashAndEntryNotTrash(user);

		List<BlogsEntry> groupEntriesNotInTrash =
			BlogsEntryLocalServiceUtil.getOrganizationEntries(
				organization.getOrganizationId(), new Date(),
				queryStatusNotInTrash);

		Assert.assertEquals(initialCount + 1, groupEntriesNotInTrash.size());

		for (BlogsEntry groupEntry : groupEntriesNotInTrash) {
			if (WorkflowConstants.STATUS_IN_TRASH == groupEntry.getStatus()) {
				Assert.fail(
					"The blogEntry " + groupEntry.getEntryId() +
						" is in trash");
			}
		}
	}

	@Test
	public void testSubscribe() throws Exception {
		int initialCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				TestPropsValues.getUserId());

		BlogsEntryLocalServiceUtil.subscribe(
			TestPropsValues.getUserId(), group.getGroupId());

		int actualCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				TestPropsValues.getUserId());

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test
	public void testUnsubscribe() throws Exception {
		int initialCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				TestPropsValues.getUserId());

		BlogsEntryLocalServiceUtil.subscribe(
			TestPropsValues.getUserId(), group.getGroupId());

		BlogsEntryLocalServiceUtil.unsubscribe(
			TestPropsValues.getUserId(), group.getGroupId());

		int actualCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				TestPropsValues.getUserId());

		Assert.assertEquals(initialCount, actualCount);
	}

	@Test
	public void testUpdateResources() throws Exception {
		BlogsEntry blogsEntry = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);

		BlogsEntryLocalServiceUtil.updateEntryResources(
			blogsEntry, new String[] {ActionKeys.ADD_DISCUSSION},
			null);
	}

	protected BlogsEntry[] addEntryTrashAndEntryNotTrash(User user)
		throws Exception {
			BlogsEntry[] blogs = new BlogsEntry[2];

			blogs[0] = BlogsTestUtil.addEntry(user.getUserId(), group, true);

			BlogsEntryLocalServiceUtil.moveEntryToTrash(
				user.getUserId(), blogs[0]);

			blogs[1] = BlogsTestUtil.addEntry(user.getUserId(), group, true);

			return blogs;
	}

	protected Group group;
	protected QueryDefinition queryStatusApproved;
	protected QueryDefinition queryStatusInTrash;
	protected QueryDefinition queryStatusNotInTrash;

}