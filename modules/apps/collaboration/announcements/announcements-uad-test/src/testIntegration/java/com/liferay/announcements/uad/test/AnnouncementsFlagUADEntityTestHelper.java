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

import com.liferay.announcements.kernel.model.AnnouncementsFlag;
import com.liferay.announcements.kernel.model.AnnouncementsFlagConstants;
import com.liferay.announcements.kernel.service.AnnouncementsFlagLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noah Sherrill
 */
@Component(
	immediate = true, service = AnnouncementsFlagUADEntityTestHelper.class
)
public class AnnouncementsFlagUADEntityTestHelper {

	public AnnouncementsFlag addAnnouncementsFlag(long userId)
		throws Exception {

		return _announcementsFlagLocalService.addFlag(
			userId, RandomTestUtil.randomLong(),
			AnnouncementsFlagConstants.UNREAD);
	}

	@Reference
	private AnnouncementsFlagLocalService _announcementsFlagLocalService;

}