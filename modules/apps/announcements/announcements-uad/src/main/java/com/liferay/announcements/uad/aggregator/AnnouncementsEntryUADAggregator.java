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

package com.liferay.announcements.uad.aggregator;

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.kernel.service.AnnouncementsEntryLocalService;
import com.liferay.announcements.uad.constants.AnnouncementsUADConstants;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.user.associated.data.aggregator.UADAggregator;

import java.io.Serializable;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noah Sherrill
 */
@Component(
	immediate = true,
	property = "model.class.name=" + AnnouncementsUADConstants.CLASS_NAME_ANNOUNCEMENTS_ENTRY,
	service = UADAggregator.class
)
public class AnnouncementsEntryUADAggregator
	extends BaseAnnouncementsUADAggregator<AnnouncementsEntry> {

	@Override
	public AnnouncementsEntry get(Serializable primaryKey) throws Exception {
		return _announcementsEntryLocalService.getAnnouncementsEntry(
			Long.valueOf(primaryKey.toString()));
	}

	@Override
	protected long doCount(DynamicQuery dynamicQuery) {
		return _announcementsEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	protected DynamicQuery doGetDynamicQuery() {
		return _announcementsEntryLocalService.dynamicQuery();
	}

	@Override
	protected List<AnnouncementsEntry> doGetRange(
		DynamicQuery dynamicQuery, int start, int end) {

		return _announcementsEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return AnnouncementsUADConstants.
			USER_ID_FIELD_NAMES_ANNOUNCEMENTS_ENTRY;
	}

	@Reference
	private AnnouncementsEntryLocalService _announcementsEntryLocalService;

}