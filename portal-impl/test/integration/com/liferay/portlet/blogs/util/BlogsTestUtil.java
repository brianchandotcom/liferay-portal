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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.blogs.EntryDisplayDateException;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.model.impl.BlogsEntryImpl;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import java.util.Date;

/**
 * @author Zsolt Berentey
 */
public class BlogsTestUtil {

	public static BlogsEntry addBlogsEntry(
			long userId, Group group, boolean approved)
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
		boolean smallImage = false;
		String smallImageURL = StringPool.BLANK;

		User user = UserLocalServiceUtil.getUser(userId);

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			EntryDisplayDateException.class);

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		Date now = new Date();

		int status = WorkflowConstants.STATUS_DRAFT;

		if (approved) {
			status = WorkflowConstants.STATUS_APPROVED;
		}

		BlogsEntry blogsEntry = new BlogsEntryImpl();

		long blogsEntryId = CounterLocalServiceUtil.increment();

		blogsEntry.setEntryId(blogsEntryId);
		blogsEntry.setUuid(serviceContext.getUuid());
		blogsEntry.setGroupId(group.getGroupId());
		blogsEntry.setCompanyId(user.getCompanyId());
		blogsEntry.setUserId(user.getUserId());
		blogsEntry.setUserName(user.getFullName());
		blogsEntry.setCreateDate(serviceContext.getCreateDate(now));
		blogsEntry.setModifiedDate(serviceContext.getModifiedDate(now));
		blogsEntry.setTitle(title);
		blogsEntry.setDescription(description);
		blogsEntry.setContent(content);
		blogsEntry.setDisplayDate(displayDate);
		blogsEntry.setAllowPingbacks(allowPingbacks);
		blogsEntry.setAllowTrackbacks(allowTrackbacks);
		blogsEntry.setSmallImage(smallImage);
		blogsEntry.setSmallImageURL(smallImageURL);
		blogsEntry.setStatus(status);
		blogsEntry.setStatusDate(serviceContext.getModifiedDate(now));
		blogsEntry.setExpandoBridgeAttributes(serviceContext);

		BlogsEntryLocalServiceUtil.addBlogsEntry(blogsEntry);

		BlogsEntryLocalServiceUtil.updateAsset(
			userId, blogsEntry, new long[0], new String[0], new long[0]);

		return blogsEntry;
	}

}