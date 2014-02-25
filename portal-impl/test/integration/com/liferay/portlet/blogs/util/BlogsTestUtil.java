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

package com.liferay.portlet.blogs.util;

import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import java.io.InputStream;

import org.junit.Assert;

/**
 * @author Zsolt Berentey
 */
public class BlogsTestUtil {

	public static BlogsEntry addEntry(
			long userId, Group group, boolean approved)
		throws Exception {

		return addEntry(userId, group, "Title", approved);
	}

	public static BlogsEntry addEntry(
			long userId, Group group, boolean approved, boolean smallImage)
		throws Exception {

		return addEntry(userId, group, "Title", approved, smallImage);
	}

	public static BlogsEntry addEntry(
			long userId, Group group, String title, boolean approved)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		return addEntry(userId, title, approved, serviceContext);
	}

	public static BlogsEntry addEntry(
			long userId, Group group, String title, boolean approved,
			boolean smallImage)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		return addEntry(userId, title, approved, smallImage, serviceContext);
	}

	public static BlogsEntry addEntry(long userId, long groupId)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			groupId);

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");

		return addEntry(
			userId, ServiceTestUtil.randomString(), true, serviceContext);
	}

	public static BlogsEntry addEntry(
			long userId, long groupId, String title, boolean approved)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		return addEntry(userId, group, title, approved);
	}

	public static BlogsEntry addEntry(
			long userId, long groupId, String title, boolean approved,
			boolean smallImage)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		return addEntry(userId, group, title, approved, smallImage);
	}

	public static BlogsEntry addEntry(
			long userId, String title, boolean approved, boolean smallImage,
			ServiceContext serviceContext)
		throws Exception {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

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
			InputStream smallImageInputStream = null;
			String smallImageURL = StringPool.BLANK;
			String smallImageFileName = StringPool.BLANK;

			if (smallImage) {
				smallImageFileName = "image.jpg";
				smallImageInputStream = BlogsTestUtil.class.getResourceAsStream(
					"com/liferay/portal/util/dependencies/test.jpg");
			}

			serviceContext = (ServiceContext)serviceContext.clone();

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
				userId, title, description, content, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
				smallImage, smallImageURL, smallImageFileName,
				smallImageInputStream, serviceContext);

			if (approved) {
				entry = BlogsEntryLocalServiceUtil.updateStatus(
					userId, entry.getEntryId(),
					WorkflowConstants.STATUS_APPROVED, serviceContext);
			}

			return entry;
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	public static BlogsEntry addEntry(
			long userId, String title, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		return addEntry(userId, title, approved, false, serviceContext);
	}

	public static void assertEquals(
		BlogsEntry blogsEntry, BlogsEntry blogEntryOther) {

		Assert.assertEquals(blogsEntry.getUserId(), blogEntryOther.getUserId());
		Assert.assertEquals(blogsEntry.getTitle(), blogEntryOther.getTitle());
		Assert.assertEquals(
			blogsEntry.getDescription(), blogEntryOther.getDescription());
		Assert.assertEquals(
			blogsEntry.getContent(), blogEntryOther.getContent());
		Assert.assertEquals(
			blogsEntry.getDisplayDate(), blogEntryOther.getDisplayDate());
		Assert.assertEquals(
			blogsEntry.isAllowPingbacks(), blogEntryOther.isAllowPingbacks());
		Assert.assertEquals(
			blogsEntry.isAllowTrackbacks(), blogEntryOther.isAllowTrackbacks());
		Assert.assertEquals(
			blogsEntry.isSmallImage(), blogEntryOther.isSmallImage());
	}

}