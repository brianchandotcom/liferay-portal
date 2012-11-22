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

package com.liferay.portlet.blogs.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import java.io.InputStream;

/**
 * @author Zsolt Berentey
 */
public class BlogsTestUtil {

	public static BlogsEntry addBlogsEntry(
			Group group, long userId, boolean approved)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

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

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			userId, title, description, content, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			allowPingbacks, allowTrackbacks, trackbacks, smallImage,
			smallImageURL, smallImageFileName, smallImageInputStream,
			serviceContext);

		if (approved) {
			BlogsEntryLocalServiceUtil.updateStatus(
				GetterUtil.getLong(PrincipalThreadLocal.getName()),
				blogsEntry.getEntryId(), WorkflowConstants.STATUS_APPROVED,
				serviceContext);
		}

		return blogsEntry;
	}

}