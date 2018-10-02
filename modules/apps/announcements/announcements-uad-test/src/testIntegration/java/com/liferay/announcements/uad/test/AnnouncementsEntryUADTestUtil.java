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

package com.liferay.announcements.uad.test;

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.kernel.service.AnnouncementsEntryLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Noah Sherrill
 */
public class AnnouncementsEntryUADTestUtil {

	public static AnnouncementsEntry addAnnouncementsEntry(
			AnnouncementsEntryLocalService announcementsEntryLocalService,
			ClassNameLocalService classNameLocalService, long userId)
		throws Exception {

		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.add(Calendar.DATE, 1);

		Date displayDate = calendar.getTime();

		calendar.add(Calendar.DATE, 1);

		return announcementsEntryLocalService.addEntry(
			userId, classNameLocalService.getClassNameId(Group.class),
			TestPropsValues.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), "http://localhost", "general",
			displayDate, calendar.getTime(), 1, false);
	}

}