/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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