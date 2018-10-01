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

package com.liferay.blogs.uad.test;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.Calendar;
import java.util.HashMap;

/**
 * @author William Newbury
 */
public class BlogsEntryUADTestUtil {

	public static BlogsEntry addBlogsEntry(
			BlogsEntryLocalService blogsEntryLocalService, long userId)
		throws Exception {

		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.add(Calendar.DATE, 1);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId());

		return blogsEntryLocalService.addEntry(
			userId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), calendar.getTime(), serviceContext);
	}

	public static BlogsEntry addBlogsEntryWithStatusByUserId(
			BlogsEntryLocalService blogsEntryLocalService, long userId,
			long statusByUserId)
		throws Exception {

		BlogsEntry blogsEntry = addBlogsEntry(blogsEntryLocalService, userId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId());

		blogsEntry = blogsEntryLocalService.updateStatus(
			statusByUserId, blogsEntry.getEntryId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext,
			new HashMap<String, Serializable>());

		return blogsEntry;
	}

}