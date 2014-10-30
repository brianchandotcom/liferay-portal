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

package com.liferay.portlet.blogs.util.test;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import java.io.InputStream;
import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

/**
 * @author Zsolt Berentey
 */
public class BlogsTestUtil {

	public static BlogsEntry addEntry(Group group, boolean approved)
		throws Exception {

		return addEntry(TestPropsValues.getUserId(), group, approved);
	}

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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");

		return addEntry(userId, title, approved, serviceContext);
	}

	public static BlogsEntry addEntry(
			long userId, Group group, String title, boolean approved,
			boolean smallImage)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setLayoutFullURL("http://localhost");

		return addEntry(userId, title, approved, smallImage, serviceContext);
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

			String subtitle = StringPool.BLANK;
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

			ImageSelector coverImageSelector = null;
			ImageSelector smallImageSelector = null;

			if (smallImage) {
				Class<?> clazz = BlogsTestUtil.class;

				ClassLoader classLoader = clazz.getClassLoader();

				InputStream inputStream = classLoader.getResourceAsStream(
					"com/liferay/portal/util/dependencies/test.jpg");

				FileEntry fileEntry = null;

				try {
					fileEntry = TempFileEntryUtil.getTempFileEntry(
						serviceContext.getScopeGroupId(), userId,
						BlogsEntry.class.getName(), "image.jpg");
				}
				catch (Exception e) {
					fileEntry = TempFileEntryUtil.addTempFileEntry(
						serviceContext.getScopeGroupId(), userId,
						BlogsEntry.class.getName(), "image.jpg", inputStream,
						MimeTypesUtil.getContentType("image.jpg"));
				}

				smallImageSelector = new ImageSelector(
					fileEntry.getFileEntryId(), StringPool.BLANK, null);
			}

			serviceContext = (ServiceContext)serviceContext.clone();

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
				userId, title, subtitle, description, content, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, allowPingbacks, allowTrackbacks, trackbacks,
				coverImageSelector, smallImageSelector, serviceContext);

			if (approved) {
				return updateStatus(entry, serviceContext);
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
		BlogsEntry expectedEntry, BlogsEntry actualEntry) {

		Assert.assertEquals(expectedEntry.getUserId(), actualEntry.getUserId());
		Assert.assertEquals(expectedEntry.getTitle(), actualEntry.getTitle());
		Assert.assertEquals(
			expectedEntry.getDescription(), actualEntry.getDescription());
		Assert.assertEquals(
			expectedEntry.getContent(), actualEntry.getContent());
		Assert.assertEquals(
			expectedEntry.getDisplayDate(), actualEntry.getDisplayDate());
		Assert.assertEquals(
			expectedEntry.isAllowPingbacks(), actualEntry.isAllowPingbacks());
		Assert.assertEquals(
			expectedEntry.isAllowTrackbacks(), actualEntry.isAllowTrackbacks());
		Assert.assertEquals(
			expectedEntry.isSmallImage(), actualEntry.isSmallImage());
	}

	public static BlogsEntry updateEntry(BlogsEntry entry, boolean approved)
		throws Exception {

		return updateEntry(entry, RandomTestUtil.randomString(), approved);
	}

	public static BlogsEntry updateEntry(
			BlogsEntry entry, String title, boolean approved)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(entry.getGroupId());

		return updateEntry(entry, title, approved, serviceContext);
	}

	public static BlogsEntry updateEntry(
			BlogsEntry entry, String title, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			serviceContext = (ServiceContext)serviceContext.clone();

			serviceContext.setCommand(Constants.UPDATE);
			serviceContext.setLayoutFullURL("http://localhost");
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			entry = BlogsEntryLocalServiceUtil.updateEntry(
				entry.getUserId(), entry.getEntryId(), title,
				entry.getSubtitle(), entry.getDescription(), entry.getContent(),
				1, 1, 2012, 12, 00, true, true, new String[0], null, null,
				serviceContext);

			if (approved) {
				return updateStatus(entry, serviceContext);
			}

			return entry;
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	protected static BlogsEntry updateStatus(
			BlogsEntry entry, ServiceContext serviceContext)
		throws Exception {

		Map<String, Serializable> workflowContext =
			new HashMap<String, Serializable>();

		workflowContext.put(WorkflowConstants.CONTEXT_URL, "http://localhost");
		workflowContext.put(
			WorkflowConstants.CONTEXT_USER_PORTRAIT_URL, "http://localhost");
		workflowContext.put(
			WorkflowConstants.CONTEXT_USER_URL, "http://localhost");

		return BlogsEntryLocalServiceUtil.updateStatus(
			entry.getUserId(), entry.getEntryId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext, workflowContext);
	}

}