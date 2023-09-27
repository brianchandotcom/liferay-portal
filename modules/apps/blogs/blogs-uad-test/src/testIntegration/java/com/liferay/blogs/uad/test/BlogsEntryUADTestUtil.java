/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.uad.test;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
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

		return blogsEntryLocalService.addEntry(
			userId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), calendar.getTime(),
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId()));
	}

	public static BlogsEntry addBlogsEntryWithStatusByUserId(
			BlogsEntryLocalService blogsEntryLocalService, long userId,
			long statusByUserId)
		throws Exception {

		BlogsEntry blogsEntry = addBlogsEntry(blogsEntryLocalService, userId);

		return blogsEntryLocalService.updateStatus(
			statusByUserId, blogsEntry.getEntryId(),
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId()),
			new HashMap<String, Serializable>());
	}

}