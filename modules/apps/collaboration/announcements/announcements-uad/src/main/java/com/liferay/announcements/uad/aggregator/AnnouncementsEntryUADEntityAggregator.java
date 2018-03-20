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
import com.liferay.announcements.uad.entity.AnnouncementsEntryUADEntity;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.util.UADDynamicQueryHelper;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noah Sherrill
 */
@Component(
	immediate = true,
	property = {"model.class.name=" + AnnouncementsUADConstants.CLASS_NAME_ANNOUNCEMENTS_ENTRY},
	service = UADEntityAggregator.class
)
public class AnnouncementsEntryUADEntityAggregator
	extends BaseAnnouncementsUADEntityAggregator {

	@Override
	public int count(long userId) {
		return (int)_announcementsEntryLocalService.dynamicQueryCount(
			_getDynamicQuery(userId));
	}

	@Override
	public List<UADEntity> getUADEntities(long userId, int start, int end) {
		List<AnnouncementsEntry> announcementsEntries =
			_announcementsEntryLocalService.getUserEntries(userId, start, end);

		List<UADEntity> uadEntities = new ArrayList<>(
			announcementsEntries.size());

		for (AnnouncementsEntry announcementsEntry : announcementsEntries) {
			uadEntities.add(
				_createAnnouncementsEntryUADEntity(announcementsEntry));
		}

		return uadEntities;
	}

	@Override
	public UADEntity getUADEntity(String uadEntityId) throws PortalException {
		AnnouncementsEntry announcementsEntry =
			_announcementsEntryLocalService.getAnnouncementsEntry(
				Long.parseLong(uadEntityId));

		return _createAnnouncementsEntryUADEntity(announcementsEntry);
	}

	private AnnouncementsEntryUADEntity _createAnnouncementsEntryUADEntity(
		AnnouncementsEntry announcementsEntry) {

		return new AnnouncementsEntryUADEntity(
			announcementsEntry.getUserId(),
			String.valueOf(announcementsEntry.getEntryId()),
			announcementsEntry);
	}

	private DynamicQuery _getDynamicQuery(long userId) {
		return _uadDynamicQueryHelper.addDynamicQueryCriteria(
			_announcementsEntryLocalService.dynamicQuery(),
			AnnouncementsUADConstants.USER_ID_FIELD_NAMES_ANNOUNCEMENTS_ENTRY,
			userId);
	}

	@Reference
	private AnnouncementsEntryLocalService _announcementsEntryLocalService;

	@Reference
	private UADDynamicQueryHelper _uadDynamicQueryHelper;

}