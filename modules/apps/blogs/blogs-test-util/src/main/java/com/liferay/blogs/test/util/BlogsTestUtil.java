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

package com.liferay.blogs.test.util;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.editor.EditorConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;

import java.io.Serializable;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

/**
 * @author Zsolt Berentey
 */
public class BlogsTestUtil {

	public static BlogsEntry addEntryWithWorkflow(
			long userId, String title, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
				2012, 1, 1);

			serviceContext = (ServiceContext)serviceContext.clone();

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
				userId, title, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				displayCalendar.getTime(), true, true, new String[0],
				StringPool.BLANK, null, null, serviceContext);

			if (approved) {
				return updateStatus(entry, serviceContext);
			}

			return entry;
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
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
		Assert.assertEquals(
			expectedEntry.getCoverImageFileEntryId(),
			actualEntry.getCoverImageFileEntryId());
	}

	public static String getTempBlogsEntryAttachmentFileEntryImgTag(
		long dataImageId, String url) {

		StringBundler sb = new StringBundler(7);

		sb.append("<img ");
		sb.append(EditorConstants.ATTRIBUTE_DATA_IMAGE_ID);
		sb.append("=\"");
		sb.append(dataImageId);
		sb.append("\" src=\"");
		sb.append(url);
		sb.append("\"/>");

		return sb.toString();
	}

	public static void populateNotificationsServiceContext(
			ServiceContext serviceContext, String command)
		throws Exception {

		serviceContext.setAttribute("entryURL", "http://localhost");

		if (Validator.isNotNull(command)) {
			serviceContext.setCommand(command);
		}

		serviceContext.setLayoutFullURL("http://localhost");
	}

	protected static BlogsEntry updateStatus(
			BlogsEntry entry, ServiceContext serviceContext)
		throws Exception {

		Map<String, Serializable> workflowContext = new HashMap<>();

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