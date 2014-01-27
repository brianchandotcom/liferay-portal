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

/**
 * @author Cristina González
 * @author Manuel de la Peña
 */
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
		BlogsEntry originalEntry = testAddEntry(false);

		BlogsEntry actualEntry = BlogsEntryLocalServiceUtil.getBlogsEntry(
			originalEntry.getEntryId());

		BlogsTestUtil.assertEqualEntry(originalEntry, actualEntry);
	}

	@Test
	public void testAddEntryWithSmallImage() throws Exception {
		BlogsEntry originalEntry = testAddEntry(true);

		BlogsEntry actualEntry = BlogsEntryLocalServiceUtil.getBlogsEntry(
			originalEntry.getEntryId());

		BlogsTestUtil.assertEqualEntry(originalEntry, actualEntry);
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
	public void testGetDiscussionMessageDisplay() throws Exception {
		BlogsEntry entry = addEntry(false);

		try {
			MBMessageLocalServiceUtil.getDiscussionMessageDisplay(
				TestPropsValues.getUserId(), _group.getGroupId(),
				BlogsEntry.class.getName(), entry.getEntryId(),
				WorkflowConstants.STATUS_ANY);
		}
		catch (Exception e) {
			Assert.fail(
				"The initial discussion has not been found for the blog " +
					"entry " + entry.getEntryId());
		}
	}

	@Test
	public void testGetEntriesPrevAndNextCenterElement() throws Exception {
		BlogsEntry entryPrev = addEntry(false);

		BlogsEntry entryCenter = addEntry(false);

		BlogsEntry entryNext = addEntry(false);

		BlogsEntry[] entriesPrevAndNextForCenter =
			BlogsEntryLocalServiceUtil.getEntriesPrevAndNext(
				entryCenter.getEntryId());

		Assert.assertNotNull(
			"The previous element of the blog entry " +
				entryCenter.getEntryId() + " should be " +
					entryPrev.getEntryId() + " but is null instead",
			entriesPrevAndNextForCenter[0]);

		Assert.assertNotNull(
			"The center element of the blog entry " + entryCenter.getEntryId() +
				" should be " + entryCenter.getEntryId() +
					" but is null instead",
			entriesPrevAndNextForCenter[1]);

		Assert.assertNotNull(
			"The next element of the blog entry " + entryCenter.getEntryId() +
				" should be " + entryNext.getEntryId() + " but is null instead",
			entriesPrevAndNextForCenter[2]);

		Assert.assertEquals(
			"The previous element of the blog entry" +
				entryCenter.getEntryId() + " should be " +
					entryPrev.getEntryId(),
			entriesPrevAndNextForCenter[0].getEntryId(),
			entryPrev.getEntryId());

		Assert.assertEquals(
			"The center element of the blog entry " + entryCenter.getEntryId() +
				" should be " + entryCenter.getEntryId(),
			entriesPrevAndNextForCenter[1].getEntryId(),
			entryCenter.getEntryId());

		Assert.assertEquals(
			"The next element of the blog entry " + entryCenter.getEntryId() +
				" should be " + entryNext.getEntryId(),
			entriesPrevAndNextForCenter[2].getEntryId(),
			entryNext.getEntryId());
	}

	@Test
	public void testGetEntriesPrevAndNextTopLeftElement() throws Exception {
		BlogsEntry entryPrev = addEntry(false);

		BlogsEntry entryCenter = addEntry(false);

		addEntry(false);

		BlogsEntry[] entriesPrevAndNextForTopLeft =
			BlogsEntryLocalServiceUtil.getEntriesPrevAndNext(
				entryPrev.getEntryId());

		Assert.assertNull(
			"The previous element of the blog entry " + entryPrev.getEntryId() +
				" should be null",
			entriesPrevAndNextForTopLeft[0]);

		Assert.assertNotNull(
			"The center element of the blog entry " + entryPrev.getEntryId() +
				" should be " + entryPrev.getEntryId() + " but is null instead",
			entriesPrevAndNextForTopLeft[1]);

		Assert.assertNotNull(
			"The next element of the blog entry " + entryPrev.getEntryId() +
				" should be " + entryCenter.getEntryId() +
					" but is null instead",
			entriesPrevAndNextForTopLeft[2]);

		Assert.assertEquals(
			"The center element of the blog entry " + entryPrev.getEntryId() +
				" should be " + entryPrev.getEntryId(),
			entriesPrevAndNextForTopLeft[1].getEntryId(),
			entryPrev.getEntryId());

		Assert.assertEquals(
			"The next element of the blog entry " + entryPrev.getEntryId() +
				" should be " + entryCenter.getEntryId(),
			entriesPrevAndNextForTopLeft[2].getEntryId(),
			entryCenter.getEntryId());
	}

	@Test
	public void testGetEntriesPrevAndNextTopRightElement() throws Exception {
		addEntry(false);

		BlogsEntry entryCenter = addEntry(false);

		BlogsEntry entryNext = addEntry(false);

		BlogsEntry[] entriesPrevAndNextForTopRight =
			BlogsEntryLocalServiceUtil.getEntriesPrevAndNext(
				entryNext.getEntryId());

		Assert.assertNull(
			"The next element of the blog entry " + entryNext.getEntryId() +
				" should be null",
			entriesPrevAndNextForTopRight[2]);

		Assert.assertNotNull(
			"The center element of the blog entry " + entryNext.getEntryId() +
				" should be " + entryNext.getEntryId() + " but is null instead",
			entriesPrevAndNextForTopRight[1]);

		Assert.assertNotNull(
			"The previous element of the blog entry " + entryNext.getEntryId() +
				" should be " + entryCenter.getEntryId() +
					" but is null instead",
			entriesPrevAndNextForTopRight[0]);

		Assert.assertEquals(
			"The previous element of the blog entry " + entryNext.getEntryId() +
				" should be " + entryCenter.getEntryId(),
			entriesPrevAndNextForTopRight[0].getEntryId(),
			entryCenter.getEntryId());

		Assert.assertEquals(
			"The center element of the blog entry" + entryNext.getEntryId() +
				" should be " + entryNext.getEntryId(),
			entriesPrevAndNextForTopRight[1].getEntryId(),
			entryNext.getEntryId());
	}

	@Test
	public void testGetEntryByGroupAndUrlTitle() throws Exception {
		BlogsEntry originalEntry = addEntry(false);

		BlogsEntry actualEntry = BlogsEntryLocalServiceUtil.getEntry(
			originalEntry.getGroupId(), originalEntry.getUrlTitle());

		BlogsTestUtil.assertEqualEntry(originalEntry, actualEntry);
	}

	@Test
	public void testGetGroupEntriesCountInTrashWithDisplayDate()
		throws Exception {

		testGetGroupEntriesCount(true, true);
	}

	@Test
	public void testGetGroupEntriesCountInTrashWithoutDisplayDate()
		throws Exception {

		testGetGroupEntriesCount(true, false);
	}

	@Test
	public void testGetGroupEntriesCountNotInTrashWithDisplayDate()
		throws Exception {

		testGetGroupEntriesCount(false, true);
	}

	@Test
	public void testGetGroupEntriesCountNotInTrashWithoutDisplayDate()
		throws Exception {

		testGetGroupEntriesCount(false, false);
	}

	@Test
	public void testGetGroupEntriesInTrashWithDisplayDate() throws Exception {
		testGetGroupEntries(true, true);
	}

	@Test
	public void testGetGroupEntriesInTrashWithoutDisplayDate()
		throws Exception {

		testGetGroupEntries(true, false);
	}

	@Test
	public void testGetGroupEntriesNotInTrashWithDisplayDate()
		throws Exception {

		testGetGroupEntries(false, true);
	}

	@Test
	public void testGetGroupEntriesNotInTrashWithoutDisplayDate()
		throws Exception {

		testGetGroupEntries(false, false);
	}

	@Test
	public void testGetGroupsEntries() throws Exception {
		List<BlogsEntry> groupsEntries =
			BlogsEntryLocalServiceUtil.getGroupsEntries(
				_user.getCompanyId(), _group.getGroupId(), new Date(),
				_queryStatusInTrash);

		int initialCount = groupsEntries.size();

		addEntry(false);

		addEntry(true);

		List<BlogsEntry> groupsEntriesInTrash =
			BlogsEntryLocalServiceUtil.getGroupsEntries(
				_user.getCompanyId(), _group.getGroupId(), new Date(),
				_queryStatusInTrash);

		Assert.assertEquals(initialCount + 1, groupsEntriesInTrash.size());

		for (BlogsEntry groupsEntry : groupsEntriesInTrash) {
			if (WorkflowConstants.STATUS_IN_TRASH != groupsEntry.getStatus()) {
				Assert.fail(
					"The blog entry " + groupsEntry.getEntryId() +
						" is not in trash");
			}

			if (groupsEntry.getCompanyId() != _user.getCompanyId()) {
				Assert.fail(
					"The companyId of the blog entry " +
						groupsEntry.getEntryId() + " should be " +
							_user.getCompanyId() + " but is " +
								groupsEntry.getCompanyId() + " instead");
			}
		}
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
	public void testUpdateEntryResources() throws Exception {
		BlogsEntry entry = addEntry(false);

		BlogsEntryLocalServiceUtil.updateEntryResources(
			entry, new String[] {ActionKeys.ADD_DISCUSSION}, null);
	}

	protected BlogsEntry addEntry(boolean statusInTrash) throws Exception {
		return addEntry(TestPropsValues.getUserId(), statusInTrash);
	}

	protected BlogsEntry addEntry(long userId, boolean statusInTrash)
		throws Exception {

		BlogsEntry entry = BlogsTestUtil.addEntry(userId, _group, true);

		if (statusInTrash) {
			entry = BlogsEntryLocalServiceUtil.moveEntryToTrash(userId, entry);
		}

		return entry;
	}

	protected void assertBlogsEntriesStatus(
		List<BlogsEntry> entries, boolean statusInTrash) {

		for (BlogsEntry entry : entries) {
			if (statusInTrash &&
				(WorkflowConstants.STATUS_IN_TRASH != entry.getStatus())) {

				Assert.fail(
					"The blogs entry " + entry.getEntryId() +
						" should be in trash");
			}
			else if (!statusInTrash &&
					 (WorkflowConstants.STATUS_IN_TRASH == entry.getStatus())) {

				Assert.fail(
					"The blogs entry " + entry.getEntryId() +
						" shouldn't be in trash");
			}
		}
	}

	protected BlogsEntry testAddEntry(boolean smallImage) throws Exception {
		int initialCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
			_group.getGroupId(), _queryStatusApproved);

		BlogsEntry entry = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), _group, true, smallImage);

		int actualCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
			_group.getGroupId(), _queryStatusApproved);

		Assert.assertEquals(initialCount + 1, actualCount);

		if (smallImage) {
			Assert.assertTrue(entry.isSmallImage());
		}
		else {
			Assert.assertFalse(entry.isSmallImage());
		}

		return entry;
	}

	protected void testGetCompanyEntries(boolean statusInTrash)
		throws Exception {

		QueryDefinition queryDefinition = _queryStatusInTrash;

		if (!statusInTrash) {
			queryDefinition = _queryStatusNotInTrash;
		}

		List<BlogsEntry> initialEntries =
			BlogsEntryLocalServiceUtil.getCompanyEntries(
				_user.getCompanyId(), new Date(), queryDefinition);

		int initialCount = initialEntries.size();

		addEntry(false);

		addEntry(true);

		List<BlogsEntry> actualEntries =
			BlogsEntryLocalServiceUtil.getCompanyEntries(
				_user.getCompanyId(), new Date(), queryDefinition);

		Assert.assertEquals(initialCount + 1, actualEntries.size());

		assertBlogsEntriesStatus(actualEntries, statusInTrash);
	}

	protected void testGetCompanyEntriesCount(boolean statusInTrash)
		throws Exception {

		QueryDefinition queryDefinition = _queryStatusInTrash;

		if (!statusInTrash) {
			queryDefinition = _queryStatusNotInTrash;
		}

		int initialCount = BlogsEntryLocalServiceUtil.getCompanyEntriesCount(
			_user.getCompanyId(), new Date(), queryDefinition);

		addEntry(false);

		addEntry(true);

		int actualCount = BlogsEntryLocalServiceUtil.getCompanyEntriesCount(
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

		List<BlogsEntry> initialEntries = null;

		if (displayDate) {
			initialEntries = BlogsEntryLocalServiceUtil.getGroupEntries(
				_group.getGroupId(), new Date(), queryDefinition);
		}
		else {
			initialEntries = BlogsEntryLocalServiceUtil.getGroupEntries(
				_group.getGroupId(), queryDefinition);
		}

		int initialCount = initialEntries.size();

		addEntry(false);

		addEntry(true);

		List<BlogsEntry> actualEntries = null;

		if (displayDate) {
			actualEntries = BlogsEntryLocalServiceUtil.getGroupEntries(
				_group.getGroupId(), new Date(), queryDefinition);
		}
		else {
			actualEntries = BlogsEntryLocalServiceUtil.getGroupEntries(
				_group.getGroupId(), queryDefinition);
		}

		Assert.assertEquals(initialCount + 1, actualEntries.size());

		assertBlogsEntriesStatus(actualEntries, statusInTrash);
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

		addEntry(false);

		addEntry(true);

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

		List<BlogsEntry> initialEntries =
			BlogsEntryLocalServiceUtil.getGroupUserEntries(
				_group.getGroupId(), _user.getUserId(), new Date(),
				queryDefinition);

		int initialCount = initialEntries.size();

		addEntry(false);

		addEntry(true);

		List<BlogsEntry> actualEntries =
			BlogsEntryLocalServiceUtil.getGroupUserEntries(
				_group.getGroupId(), _user.getUserId(), new Date(),
				queryDefinition);

		Assert.assertEquals(initialCount + 1, actualEntries.size());

		assertBlogsEntriesStatus(actualEntries, statusInTrash);
	}

	protected void testGetGroupUserEntriesCount(boolean statusInTrash)
		throws Exception {

		QueryDefinition queryDefinition = _queryStatusInTrash;

		if (!statusInTrash) {
			queryDefinition = _queryStatusNotInTrash;
		}

		int initialCount = BlogsEntryLocalServiceUtil.getGroupUserEntriesCount(
			_group.getGroupId(), _user.getUserId(), new Date(),
			queryDefinition);

		addEntry(false);

		addEntry(true);

		int actualCount = BlogsEntryLocalServiceUtil.getGroupUserEntriesCount(
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

		List<BlogsEntry> initialEntries =
			BlogsEntryLocalServiceUtil.getOrganizationEntries(
				organization.getOrganizationId(), new Date(), queryDefinition);

		int initialCount = initialEntries.size();

		addEntry(user.getUserId(), false);

		addEntry(user.getUserId(), true);

		List<BlogsEntry> actualEntries =
			BlogsEntryLocalServiceUtil.getOrganizationEntries(
				organization.getOrganizationId(), new Date(), queryDefinition);

		Assert.assertEquals(initialCount + 1, actualEntries.size());

		assertBlogsEntriesStatus(actualEntries, statusInTrash);
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

		addEntry(user.getUserId(), false);

		addEntry(user.getUserId(), true);

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