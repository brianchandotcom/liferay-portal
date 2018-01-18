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

package com.liferay.announcements.uad.anonymizer;

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.kernel.service.AnnouncementsEntryLocalService;
import com.liferay.announcements.uad.constants.AnnouncementsUADConstants;
import com.liferay.announcements.uad.entity.AnnouncementsEntryUADEntity;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.anonymizer.BaseUADEntityAnonymizer;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.exception.UADEntityException;
import com.liferay.user.associated.data.util.UADAnonymizerHelper;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noah Sherrill
 */
@Component(
	immediate = true,
	property = {"model.class.name=" + AnnouncementsUADConstants.ANNOUNCEMENTS_ENTRY},
	service = UADEntityAnonymizer.class
)
public class AnnouncementsEntryUADEntityAnonymizer
	extends BaseUADEntityAnonymizer {

	@Override
	public void autoAnonymize(UADEntity uadEntity) throws PortalException {
		AnnouncementsEntry announcementsEntry = _getAnnouncementsEntry(
			uadEntity);

		User anonymousUser = _uadAnonymizerHelper.getAnonymousUser();

		announcementsEntry.setUserId(anonymousUser.getUserId());
		announcementsEntry.setUserName(anonymousUser.getFullName());

		_announcementsEntryLocalService.updateAnnouncementsEntry(
			announcementsEntry);
	}

	@Override
	public void delete(UADEntity uadEntity) throws PortalException {
		AnnouncementsEntry announcementsEntry = _getAnnouncementsEntry(
			uadEntity);

		_announcementsEntryLocalService.deleteEntry(announcementsEntry);
	}

	@Override
	public List<String> getEntityNonAnonymizableFieldNames() {
		if (_nonAnonymizableFieldNames.isEmpty()) {
			_nonAnonymizableFieldNames.add("title");
			_nonAnonymizableFieldNames.add("content");
			_nonAnonymizableFieldNames.add("url");
		}

		return _nonAnonymizableFieldNames;
	}

	@Override
	protected List<UADEntity> getUADEntities(long userId) {
		return _uadEntityAggregator.getUADEntities(userId);
	}

	private AnnouncementsEntry _getAnnouncementsEntry(UADEntity uadEntity)
		throws PortalException {

		_validate(uadEntity);

		AnnouncementsEntryUADEntity announcementsEntryUADEntity =
			(AnnouncementsEntryUADEntity)uadEntity;

		return announcementsEntryUADEntity.getAnnouncementsEntry();
	}

	private void _validate(UADEntity uadEntity) throws PortalException {
		if (!(uadEntity instanceof AnnouncementsEntryUADEntity)) {
			throw new UADEntityException();
		}
	}

	@Reference
	private AnnouncementsEntryLocalService _announcementsEntryLocalService;

	private final List<String> _nonAnonymizableFieldNames = new ArrayList<>();

	@Reference
	private UADAnonymizerHelper _uadAnonymizerHelper;

	@Reference(
		target = "(model.class.name=" + AnnouncementsUADConstants.ANNOUNCEMENTS_ENTRY + ")"
	)
	private UADEntityAggregator _uadEntityAggregator;

}