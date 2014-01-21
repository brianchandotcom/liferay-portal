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
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

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
		_user = TestPropsValues.getUser();

		_group = GroupTestUtil.addGroup();

		_queryStatusInTrash = new QueryDefinition(
				WorkflowConstants.STATUS_IN_TRASH, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		_queryStatusNotInTrash = new QueryDefinition(
				WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		_queryStatusApproved = new QueryDefinition(
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_group);
	}

	@Test
	public void testAddEntryWithoutSmallImage() throws Exception {
		BlogsEntry entryInserted = testAddEntry(false);

		BlogsEntry entry = BlogsEntryLocalServiceUtil.getBlogsEntry(
			entryInserted.getEntryId());

		Assert.assertFalse(entry.isSmallImage());

		BlogsTestUtil.assertEqualEntry(entryInserted, entry);

		Assert.assertFalse(entry.getSmallImage());

		try {
			MBMessageLocalServiceUtil.getDiscussionMessageDisplay(
				TestPropsValues.getUserId(), _group.getGroupId(),
				BlogsEntry.class.getName(), entry.getEntryId(),
				WorkflowConstants.STATUS_ANY);
		}
		catch (Exception e) {
			Assert.fail(
				"The initial discussion has not been found for the blog " +
					entry.getEntryId());
		}
	}

	@Test
	public void testAddEntryWithSmallImage() throws Exception {
		BlogsEntry entryInserted = testAddEntry(true);

		BlogsEntry entry = BlogsEntryLocalServiceUtil.getBlogsEntry(
			entryInserted.getEntryId());

		Assert.assertTrue(entry.getSmallImage());

		BlogsTestUtil.assertEqualEntry(entryInserted, entry);
	}

	@Test
	public void testGetCompanyEntriesCountInTrash() throws Exception {
		testGetCompanyEntriesCount(true);
	}

	@Test
	public void testGetCompanyEntriesCountNotInTrash() throws Exception {
		testGetCompanyEntriesCount(false);
	}

	@Test
	public void testGetCompanyEntriesInTrash() throws Exception {
		testGetCompanyEntries(true);
	}

	@Test
	public void testGetCompanyEntriesNotInTrash() throws Exception {
		testGetCompanyEntries(false);
	}

	@Test
	public void testGetEntriesPrevAndNextCenterElement() throws Exception {
		BlogsEntry entryPrevious = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), _group, true);

		BlogsEntry entryCenter = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), _group, true);

		BlogsEntry entryNext = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), _group, true);

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
			TestPropsValues.getUserId(), _group, true);

		BlogsEntry entryCenter = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), _group, true);

		BlogsTestUtil.addEntry(TestPropsValues.getUserId(), _group, true);

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
		BlogsTestUtil.addEntry(TestPropsValues.getUserId(), _group, true);

		BlogsEntry entryCenter = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), _group, true);

		BlogsEntry entryNext = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), _group, true);

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
			TestPropsValues.getUserId(), _group, true);

		BlogsEntry entryObtained = BlogsEntryLocalServiceUtil.getEntry(
			entry.getGroupId(), entry.getUrlTitle());

		BlogsTestUtil.assertEqualEntry(entry, entryObtained);
	}

	@Test
	public void testGetGroupEntriesCompany() throws Exception {
		List<BlogsEntry> groupEntries =
			BlogsEntryLocalServiceUtil.getGroupsEntries(
				_user.getCompanyId(), _group.getGroupId(), new Date(),
				_queryStatusInTrash);

		int initialCount = groupEntries.size();

		addEntryInTrashAndEntryNotInTrash();

		List<BlogsEntry> groupEntriesInTrash =
			BlogsEntryLocalServiceUtil.getGroupsEntries(
				_user.getCompanyId(), _group.getGroupId(), new Date(),
				_queryStatusInTrash);

		Assert.assertEquals(initialCount + 1, groupEntriesInTrash.size());

		for (BlogsEntry groupEntry : groupEntriesInTrash) {
			if (WorkflowConstants.STATUS_IN_TRASH != groupEntry.getStatus()) {
				Assert.fail(
					"The blogEntry " + groupEntry.getEntryId() +
						" is not in trash");
			}

			if (groupEntry.getCompanyId() != _user.getCompanyId()) {
				Assert.fail(
					"The companyId of the BlogEntry" + groupEntry.getEntryId() +
					" should be " + _user.getCompanyId() + " bus is " +
					groupEntry.getCompanyId() + " instead");
			}
		}
	}

	@Test
	public void testGetGroupEntriesCountInTrashNoDisplayDate()
		throws Exception {

		testGetGroupEntriesCount(true, false);
	}

	@Test
	public void testGetGroupEntriesCountInTrashWithDisplayDate()
		throws Exception {

		testGetGroupEntriesCount(true, true);
	}

	@Test
	public void testGetGroupEntriesCountNotInTrashNoDisplayDate()
		throws Exception {

		testGetGroupEntriesCount(false, false);
	}

	@Test
	public void testGetGroupEntriesCountNotInTrashWithDisplayDate()
		throws Exception {

		testGetGroupEntriesCount(false, true);
	}

	@Test
	public void testGetGroupEntriesInTrashNoDisplayDate() throws Exception {
		testGetGroupEntries(true, false);
	}

	@Test
	public void testGetGroupEntriesInTrashWithDisplayDate() throws Exception {
		testGetGroupEntries(true, true);
	}

	@Test
	public void testGetGroupEntriesNotInTrashNoDisplayDate() throws Exception {
		testGetGroupEntries(false, false);
	}

	@Test
	public void testGetGroupEntriesNotInTrashWithDisplayDate()
		throws Exception {

		testGetGroupEntries(false, true);
	}

	@Test
	public void testGetGroupUserEntriesCountInTrash() throws Exception {
		testGetGroupUserEntriesCount(true);
	}

	@Test
	public void testGetGroupUserEntriesCountNotInTrash() throws Exception {
		testGetGroupUserEntriesCount(false);
	}

	@Test
	public void testGetGroupUserEntriesInTrash() throws Exception {
		testGetGroupUserEntries(true);
	}

	@Test
	public void testGetGroupUserEntriesNotInTrash() throws Exception {
		testGetGroupUserEntries(false);
	}

	@Test
	public void testGetOrganizationEntriesCountInTrash() throws Exception {
		testGetOrganizationEntriesCount(true);
	}

	@Test
	public void testGetOrganizationEntriesCountNotInTrash() throws Exception {
		testGetOrganizationEntriesCount(false);
	}

	@Test
	public void testGetOrganizationEntriesInTrash() throws Exception {
		testGetOrganizationEntries(true);
	}

	@Test
	public void testGetOrganizationEntriesNotInTrash() throws Exception {
		testGetOrganizationEntries(false);
	}

	@Test
	public void testSubscribe() throws Exception {
		int initialCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				TestPropsValues.getUserId());

		BlogsEntryLocalServiceUtil.subscribe(
			TestPropsValues.getUserId(), _group.getGroupId());

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
			TestPropsValues.getUserId(), _group.getGroupId());

		BlogsEntryLocalServiceUtil.unsubscribe(
			TestPropsValues.getUserId(), _group.getGroupId());

		int actualCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				TestPropsValues.getUserId());

		Assert.assertEquals(initialCount, actualCount);
	}

	@Test
	public void testUpdateResources() throws Exception {
		BlogsEntry blogsEntry = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), _group, true);

		BlogsEntryLocalServiceUtil.updateEntryResources(
			blogsEntry, new String[] {ActionKeys.ADD_DISCUSSION},
			null);
	}

	protected BlogsEntry[] addEntryInTrashAndEntryNotInTrash()
		throws Exception {

		return addEntryInTrashAndEntryNotInTrash(TestPropsValues.getUser());
	}

	protected BlogsEntry[] addEntryInTrashAndEntryNotInTrash(User user)
		throws Exception {

		BlogsEntry[] blogs = new BlogsEntry[2];

		blogs[0] = BlogsTestUtil.addEntry(user.getUserId(), _group, true);

		BlogsEntryLocalServiceUtil.moveEntryToTrash(user.getUserId(), blogs[0]);

		blogs[1] = BlogsTestUtil.addEntry(user.getUserId(), _group, true);

		return blogs;
	}

	protected BlogsEntry testAddEntry(boolean smallImage) throws Exception {
		int initialCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
			_group.getGroupId(), _queryStatusApproved);

		BlogsEntry entry = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), _group, true, smallImage);

		int actualCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
			_group.getGroupId(), _queryStatusApproved);

		Assert.assertEquals(initialCount + 1, actualCount);

		return entry;
	}

	protected void testGetCompanyEntries(boolean statusInTrash)
		throws Exception {

		QueryDefinition queryDefinition = _queryStatusInTrash;

		if (!statusInTrash) {
			queryDefinition = _queryStatusNotInTrash;
		}

		List<BlogsEntry> companyEntries =
			BlogsEntryLocalServiceUtil.getCompanyEntries(
				_user.getCompanyId(), new Date(), queryDefinition);

		int initialCount = companyEntries.size();

		addEntryInTrashAndEntryNotInTrash();

		List<BlogsEntry> companyEntriesNotInTrash =
			BlogsEntryLocalServiceUtil.getCompanyEntries(
				_user.getCompanyId(), new Date(), queryDefinition);

		Assert.assertEquals(initialCount + 1, companyEntriesNotInTrash.size());

		for (BlogsEntry entry : companyEntriesNotInTrash) {
			if (statusInTrash &&
				(WorkflowConstants.STATUS_IN_TRASH != entry.getStatus())) {

				Assert.fail(
					"The blogEntry " + entry.getEntryId() +
						" is not in trash");
			}
			else if (!statusInTrash &&
					 (WorkflowConstants.STATUS_IN_TRASH ==
					  entry.getStatus())) {

				Assert.fail(
					"The blogEntry " + entry.getEntryId() +
						" is in trash");
			}
		}
	}

	protected void testGetCompanyEntriesCount(boolean statusInTrash)
		throws Exception {

		QueryDefinition queryDefinition = _queryStatusInTrash;

		if (!statusInTrash) {
			queryDefinition = _queryStatusNotInTrash;
		}

		int initialCount = BlogsEntryLocalServiceUtil.getCompanyEntriesCount(
			_user.getCompanyId(), new Date(), queryDefinition);

		addEntryInTrashAndEntryNotInTrash();

		int actualCount =
			BlogsEntryLocalServiceUtil.getCompanyEntriesCount(
				_user.getCompanyId(), new Date(), queryDefinition);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	protected void testGetGroupEntries(
			boolean statusInTrash, boolean displayDate)
		throws Exception {

		QueryDefinition queryDefinition = _queryStatusInTrash;

		if (!statusInTrash) {
			queryDefinition = _queryStatusNotInTrash;
		}

		List<BlogsEntry> groupEntries = null;

		if (displayDate) {
			groupEntries = BlogsEntryLocalServiceUtil.getGroupEntries(
				_group.getGroupId(), new Date(), queryDefinition);
		}
		else {
			groupEntries = BlogsEntryLocalServiceUtil.getGroupEntries(
				_group.getGroupId(), queryDefinition);
		}

		int initialCount = groupEntries.size();

		addEntryInTrashAndEntryNotInTrash();

		List<BlogsEntry> groupEntriesNotInTrash = null;

		if (displayDate) {
			groupEntriesNotInTrash = BlogsEntryLocalServiceUtil.getGroupEntries(
				_group.getGroupId(), new Date(), queryDefinition);
		}
		else {
			groupEntriesNotInTrash = BlogsEntryLocalServiceUtil.getGroupEntries(
				_group.getGroupId(), queryDefinition);
		}

		Assert.assertEquals(initialCount + 1, groupEntriesNotInTrash.size());

		for (BlogsEntry groupEntry : groupEntriesNotInTrash) {
			if (statusInTrash &&
				(WorkflowConstants.STATUS_IN_TRASH != groupEntry.getStatus())) {

				Assert.fail(
					"The blogEntry " + groupEntry.getEntryId() +
						" is not in trash");
			}
			else if (!statusInTrash &&
					 (WorkflowConstants.STATUS_IN_TRASH ==
						groupEntry.getStatus())) {

				Assert.fail(
					"The blogEntry " + groupEntry.getEntryId() +
						" is in trash");
			}
		}
	}

	protected void testGetGroupEntriesCount(
			boolean statusInTrash, boolean displayDate)
		throws Exception {

		QueryDefinition queryDefinition = _queryStatusInTrash;

			if (!statusInTrash) {
				queryDefinition = _queryStatusNotInTrash;
			}

		int initialCount = 0;

		if (displayDate) {
			initialCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
				_group.getGroupId(), new Date(), queryDefinition);
		}
		else {
			initialCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
				_group.getGroupId(), queryDefinition);
		}

		addEntryInTrashAndEntryNotInTrash();

		int actualCount = 0;

		if (displayDate) {
			actualCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
				_group.getGroupId(), new Date(), queryDefinition);
		}
		else {
			actualCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
				_group.getGroupId(), queryDefinition);
		}

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	protected void testGetGroupUserEntries(boolean statusInTrash)
		throws Exception {

		QueryDefinition queryDefinition = _queryStatusInTrash;

		if (!statusInTrash) {
			queryDefinition = _queryStatusNotInTrash;
		}

		List<BlogsEntry> groupEntries =
			BlogsEntryLocalServiceUtil.getGroupUserEntries(
				_group.getGroupId(), _user.getUserId(), new Date(),
				queryDefinition);

		int initialCount = groupEntries.size();

		addEntryInTrashAndEntryNotInTrash();

		List<BlogsEntry> groupEntriesNotInTrash =
			BlogsEntryLocalServiceUtil.getGroupUserEntries(
				_group.getGroupId(), _user.getUserId(), new Date(),
				queryDefinition);

		Assert.assertEquals(initialCount + 1, groupEntriesNotInTrash.size());

		for (BlogsEntry groupEntry : groupEntriesNotInTrash) {
			if (statusInTrash &&
				(WorkflowConstants.STATUS_IN_TRASH != groupEntry.getStatus())) {

				Assert.fail(
					"The blogEntry " + groupEntry.getEntryId() +
						" is not in trash");
			}
			else if (!statusInTrash &&
					 (WorkflowConstants.STATUS_IN_TRASH ==
						groupEntry.getStatus())) {

				Assert.fail(
					"The blogEntry " + groupEntry.getEntryId() +
						" is in trash");
			}
		}
	}

	protected void testGetGroupUserEntriesCount(boolean statusInTrash)
		throws Exception {

		QueryDefinition queryDefinition = _queryStatusInTrash;

		if (!statusInTrash) {
			queryDefinition = _queryStatusNotInTrash;
		}

		int initialCount =
			BlogsEntryLocalServiceUtil.getGroupUserEntriesCount(
				_group.getGroupId(), _user.getUserId(), new Date(),
				queryDefinition);

		addEntryInTrashAndEntryNotInTrash();

		int actualCount =
			BlogsEntryLocalServiceUtil.getGroupUserEntriesCount(
				_group.getGroupId(), _user.getUserId(), new Date(),
				queryDefinition);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	protected void testGetOrganizationEntries(boolean statusInTrash)
		throws Exception {

		QueryDefinition queryDefinition = _queryStatusInTrash;

		if (!statusInTrash) {
			queryDefinition = _queryStatusNotInTrash;
		}

		Organization organization = OrganizationTestUtil.addOrganization();

		User user = UserTestUtil.addOrganizationOwnerUser(organization);

		List<BlogsEntry> groupEntries =
			BlogsEntryLocalServiceUtil.getOrganizationEntries(
				organization.getOrganizationId(), new Date(), queryDefinition);

		int initialCount = groupEntries.size();

		addEntryInTrashAndEntryNotInTrash(user);

		List<BlogsEntry> groupEntriesNotInTrash =
			BlogsEntryLocalServiceUtil.getOrganizationEntries(
				organization.getOrganizationId(), new Date(), queryDefinition);

		Assert.assertEquals(initialCount + 1, groupEntriesNotInTrash.size());

		for (BlogsEntry groupEntry : groupEntriesNotInTrash) {
			if (statusInTrash &&
				(WorkflowConstants.STATUS_IN_TRASH != groupEntry.getStatus())) {

				Assert.fail(
					"The blogEntry " + groupEntry.getEntryId() +
						" is not in trash");
			}
			else if (!statusInTrash &&
					 (WorkflowConstants.STATUS_IN_TRASH ==
						groupEntry.getStatus())) {

				Assert.fail(
					"The blogEntry " + groupEntry.getEntryId() +
						" is in trash");
			}
		}
	}

	protected void testGetOrganizationEntriesCount(boolean statusInTrash)
		throws Exception {

		QueryDefinition queryDefinition = _queryStatusInTrash;

		if (!statusInTrash) {
			queryDefinition = _queryStatusNotInTrash;
		}

		Organization organization = OrganizationTestUtil.addOrganization();

		User user = UserTestUtil.addOrganizationOwnerUser(organization);

		int initialCount =
			BlogsEntryLocalServiceUtil.getOrganizationEntriesCount(
				organization.getOrganizationId(), new Date(), queryDefinition);

		addEntryInTrashAndEntryNotInTrash(user);

		int actualCount =
			BlogsEntryLocalServiceUtil.getOrganizationEntriesCount(
				organization.getOrganizationId(), new Date(), queryDefinition);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	private Group _group;
	private QueryDefinition _queryStatusApproved;
	private QueryDefinition _queryStatusInTrash;
	private QueryDefinition _queryStatusNotInTrash;
	private User _user;

}