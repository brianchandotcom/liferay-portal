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
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
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
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(group);
	}

	@Test
	public void testAddEntryNotSmallImage() throws Exception {
		QueryDefinition queryDefinitionApproved = new QueryDefinition(
			WorkflowConstants.STATUS_APPROVED);
		queryDefinitionApproved.setStart(QueryUtil.ALL_POS);
		queryDefinitionApproved.setEnd(QueryUtil.ALL_POS);

		int initialCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
			group.getGroupId(), queryDefinitionApproved);

		BlogsEntry blogsEntry = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);

		int actualCount = BlogsEntryLocalServiceUtil.getGroupEntriesCount(
			group.getGroupId(), queryDefinitionApproved);

		Assert.assertEquals(initialCount + 1, actualCount);

		BlogsEntry blogsEntryRecovered =
			BlogsEntryLocalServiceUtil.getBlogsEntry(blogsEntry.getEntryId());

		Assert.assertEquals(
			blogsEntry.getUserId(), blogsEntryRecovered.getUserId());
		Assert.assertEquals(
			blogsEntry.getTitle(), blogsEntryRecovered.getTitle());
		Assert.assertEquals(
			blogsEntry.getDescription(), blogsEntryRecovered.getDescription());
		Assert.assertEquals(
			blogsEntry.getContent(), blogsEntryRecovered.getContent());
		Assert.assertEquals(
			blogsEntry.getDisplayDate(), blogsEntryRecovered.getDisplayDate());
		Assert.assertEquals(
			blogsEntry.isAllowPingbacks(),
			blogsEntryRecovered.isAllowPingbacks());
		Assert.assertEquals(
			blogsEntry.isAllowTrackbacks(),
			blogsEntryRecovered.isAllowTrackbacks());
		Assert.assertEquals(
			blogsEntry.isSmallImage(), blogsEntryRecovered.isSmallImage());
	}

	@Test
	public void testGetCompanyEntriesCountInTrash() throws Exception {
		User user = TestPropsValues.getUser();

		QueryDefinition queryDefinitionNotInTrash = new QueryDefinition(
			WorkflowConstants.STATUS_IN_TRASH);
		queryDefinitionNotInTrash.setStart(QueryUtil.ALL_POS);
		queryDefinitionNotInTrash.setEnd(QueryUtil.ALL_POS);

		int initialCount =
			BlogsEntryLocalServiceUtil.getCompanyEntriesCount(
				user.getCompanyId(), new Date(), queryDefinitionNotInTrash);

		BlogsEntry entryInTrash = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);

		BlogsEntryLocalServiceUtil.moveEntryToTrash(
			user.getUserId(), entryInTrash);

		BlogsTestUtil.addEntry(user.getUserId(), group, true);

		int actualCount =
			BlogsEntryLocalServiceUtil.getCompanyEntriesCount(
				user.getCompanyId(), new Date(), queryDefinitionNotInTrash);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test
	public void testGetCompanyEntriesCountNotInTrash() throws Exception {
		User user = TestPropsValues.getUser();

		QueryDefinition queryDefinitionNotInTrash = new QueryDefinition(
			WorkflowConstants.STATUS_ANY);
		queryDefinitionNotInTrash.setStart(QueryUtil.ALL_POS);
		queryDefinitionNotInTrash.setEnd(QueryUtil.ALL_POS);

		int initialCount = BlogsEntryLocalServiceUtil.getCompanyEntriesCount(
			user.getCompanyId(), new Date(), queryDefinitionNotInTrash);

		BlogsEntry entryInTrash = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);

		BlogsEntryLocalServiceUtil.moveEntryToTrash(
			user.getUserId(), entryInTrash);

		BlogsTestUtil.addEntry(TestPropsValues.getUserId(), group, true);

		int actualCount =
			BlogsEntryLocalServiceUtil.getCompanyEntriesCount(
				user.getCompanyId(), new Date(), queryDefinitionNotInTrash);

		Assert.assertEquals(initialCount + 1, actualCount);
	}

	@Test
	public void testGetCompanyEntriesInTrash() throws Exception {
		User user = TestPropsValues.getUser();

		QueryDefinition queryDefinitionNotInTrash = new QueryDefinition(
			WorkflowConstants.STATUS_IN_TRASH);
		queryDefinitionNotInTrash.setStart(QueryUtil.ALL_POS);
		queryDefinitionNotInTrash.setEnd(QueryUtil.ALL_POS);

		List<BlogsEntry> companyEntries =
			BlogsEntryLocalServiceUtil.getCompanyEntries(
				user.getCompanyId(), new Date(), queryDefinitionNotInTrash);

		int initialCount = companyEntries.size();

		BlogsEntry entryInTrash = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);

		BlogsEntryLocalServiceUtil.moveEntryToTrash(
			user.getUserId(), entryInTrash);

		BlogsTestUtil.addEntry(TestPropsValues.getUserId(), group, true);

		List<BlogsEntry> companyEntriesInTrash =
			BlogsEntryLocalServiceUtil.getCompanyEntries(
				user.getCompanyId(), new Date(), queryDefinitionNotInTrash);

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

		QueryDefinition queryDefinitionNotInTrash = new QueryDefinition(
			WorkflowConstants.STATUS_ANY);
		queryDefinitionNotInTrash.setStart(QueryUtil.ALL_POS);
		queryDefinitionNotInTrash.setEnd(QueryUtil.ALL_POS);

		List<BlogsEntry> companyEntries =
			BlogsEntryLocalServiceUtil.getCompanyEntries(
				user.getCompanyId(), new Date(), queryDefinitionNotInTrash);

		int initialCount = companyEntries.size();

		BlogsEntry entryInTrash = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);

		BlogsEntryLocalServiceUtil.moveEntryToTrash(
			user.getUserId(), entryInTrash);

		BlogsTestUtil.addEntry(TestPropsValues.getUserId(), group, true);

		List<BlogsEntry> companyEntriesNotInTrash =
			BlogsEntryLocalServiceUtil.getCompanyEntries(
				user.getCompanyId(), new Date(), queryDefinitionNotInTrash);

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
		
		Assert.assertNotNull("The previous element for the blog " + 
			entryCenter.getEntryId() + " should be " +
			entryPrevious.getEntryId() + " but is null instead",
			entriesPrevAndNextForCenter[0]);

		Assert.assertNotNull("The center element for the blog " +
			entryCenter.getEntryId() + " should be " +
			entryCenter.getEntryId() + " but is null instead",
			entriesPrevAndNextForCenter[1]);

		Assert.assertNotNull("The next element for the blog " +
			entryCenter.getEntryId() + " should be " + entryNext.getEntryId() +
			" but is null instead", entriesPrevAndNextForCenter[2]);

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
			"The center element " + entriesPrevAndNextForCenter[1].getEntryId() +
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

		BlogsEntry entryNext = BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), group, true);

		BlogsEntry[] entriesPrevAndNextForTopLeft =
			BlogsEntryLocalServiceUtil.getEntriesPrevAndNext(
				entryPrevious.getEntryId());

		Assert.assertNull("The previous element for the blog " +
			entryPrevious.getEntryId() + " should be null", entriesPrevAndNextForTopLeft[0]);

		Assert.assertNotNull("The center element for the blog " +
			entryPrevious.getEntryId() + " should be " + entryPrevious.getEntryId() +
			" but is null instead",entriesPrevAndNextForTopLeft[1]);

		Assert.assertNotNull("The next element for the blog " +
			entryCenter.getEntryId() + " should be " + entryCenter.getEntryId() +
			" but is null instead",entriesPrevAndNextForTopLeft[2]);

		Assert.assertEquals(
			"The right element " + entriesPrevAndNextForTopLeft[2].getEntryId() +
				" should be " + entryCenter.getEntryId(),
			entriesPrevAndNextForTopLeft[2].getEntryId(),
			entryCenter.getEntryId());

		Assert.assertEquals(
			"The center element " + entriesPrevAndNextForTopLeft[1].getEntryId() +
				" should be " + entryPrevious.getEntryId(),
			entriesPrevAndNextForTopLeft[1].getEntryId(),
			entryPrevious.getEntryId());

	}



	protected static final long DEFAULT_PARENT_CONTAINER_MODEL_ID = 0;

	protected Group group;

}