/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.trash;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.portlet.trash.service.TrashEntryServiceUtil;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class BlogsEntryTrashHandlerTest {

	@Before
	public void setUp() {
		FinderCacheUtil.clearCache();
	}

	@Test
	@Transactional
	public void testDeleteBlogsEntry() {
		try {
			ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

			Group group = ServiceTestUtil.addGroup(
				"BlogsEntryTrashHandlerTest#testDeleteBlogsEntry");

			serviceContext.setScopeGroupId(group.getGroupId());

			BlogsEntry blogsEntry = addBlogsEntry(serviceContext);

			int initialBlogsEntriesCount = getBlogsEntriesNotInTrashCount(
				blogsEntry.getGroupId());
			int initialTrashEntriesCount = getTrashEntriesCount(
				group.getGroupId());

			BlogsEntryServiceUtil.moveEntryToTrash(blogsEntry.getEntryId());

			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					blogsEntry.getModelClassName());

			trashHandler.deleteTrashEntry(blogsEntry.getEntryId());

			int finalBlogsEntriesCount = getBlogsEntriesNotInTrashCount(
				blogsEntry.getGroupId());

			Assert.assertEquals(
				initialBlogsEntriesCount, finalBlogsEntriesCount + 1);

			int finalTrashItemsCount = getTrashEntriesCount(
				blogsEntry.getGroupId());

			Assert.assertEquals(initialTrashEntriesCount, finalTrashItemsCount);
		}
		catch (Exception e) {
			Assert.fail(
				"Unexpected error testing deleting blogs entry " +
					e.getMessage());
		}
	}

	@Test
	@Transactional
	public void testMoveBlogsEntryToTrash() {
		try {
			ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

			Group group = ServiceTestUtil.addGroup(
				"BlogsEntryTrashHandlerTest#testMoveBlogsEntryToTrash");

			serviceContext.setScopeGroupId(group.getGroupId());

			BlogsEntry blogsEntry = addBlogsEntry(serviceContext);

			int initialTrashEntriesCount = getTrashEntriesCount(
				group.getGroupId());

			BlogsEntryServiceUtil.moveEntryToTrash(blogsEntry.getEntryId());

			Assert.assertEquals(
				WorkflowConstants.STATUS_IN_TRASH, blogsEntry.getStatus());

			TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(
				BlogsEntry.class.getName(), blogsEntry.getEntryId());

			Assert.assertEquals(
				blogsEntry.getEntryId(), trashEntry.getClassPK());

			int finalTrashEntriesCount = getTrashEntriesCount(
				group.getGroupId());

			Assert.assertEquals(
				initialTrashEntriesCount + 1, finalTrashEntriesCount);
		}

		catch(Exception e) {
			Assert.fail(
				"Unexpected error moving blogs entry to trash " +
					e.getMessage());
		}
	}

	@Test
	@Transactional
	public void testRestoreBlogsEntryFromTrash() {
		try {
			ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

			Group group = ServiceTestUtil.addGroup(
				"BlogsEntryTrashHandlerTest#testRestoreBlogsEntryFromTrash");

			serviceContext.setScopeGroupId(group.getGroupId());

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			BlogsEntry blogsEntry = addBlogsEntry(serviceContext);

			int oldStatus = blogsEntry.getStatus();

			BlogsEntryServiceUtil.moveEntryToTrash(blogsEntry.getEntryId());

			int initialBlogsEntriesCount = getBlogsEntriesNotInTrashCount(
				blogsEntry.getGroupId());

			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					blogsEntry.getModelClassName());

			trashHandler.restoreTrashEntry(blogsEntry.getEntryId());

			blogsEntry = BlogsEntryServiceUtil.getEntry(
				blogsEntry.getEntryId());

			Assert.assertEquals(oldStatus, blogsEntry.getStatus());

			int finalBlogsEntriesCount = getBlogsEntriesNotInTrashCount(
				blogsEntry.getGroupId());

			Assert.assertEquals(
				initialBlogsEntriesCount + 1, finalBlogsEntriesCount);
		}

		catch(Exception e) {
			Assert.fail(
				"Unexpected error restoring blogsEntry from Trash: " +
					e.getMessage());
		}
	}

	protected BlogsEntry addBlogsEntry(ServiceContext serviceContext)
		throws Exception {

		String title = "Title";
		String description = "Description";
		String content = "Content";
		int displayDateMonth = 1;
		int displayDateDay = 1;
		int displayDateYear = 2012;
		int displayDateHour = 12;
		int displayDateMinute = 0;
		boolean allowPingbacks = true;
		boolean allowTrackbacks = true;
		String[] trackbacks = new String[0];
		boolean smallImage = false;
		String smallImageURL = StringPool.BLANK;
		String smallImageFileName = StringPool.BLANK;
		InputStream smallImageInputStream = null;

		return BlogsEntryServiceUtil.addEntry(
			title, description, content, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute, allowPingbacks,
			allowTrackbacks, trackbacks, smallImage, smallImageURL,
			smallImageFileName, smallImageInputStream, serviceContext);
	}

	protected int getBlogsEntriesNotInTrashCount(long groupId)
		throws Exception {

		return BlogsEntryServiceUtil.getGroupEntriesCount(
			groupId, WorkflowConstants.STATUS_ANY);
	}

	protected int getTrashEntriesCount(long groupId) throws Exception {
		Object[] result = TrashEntryServiceUtil.getEntries(groupId);

		return (Integer)result[1];
	}

}