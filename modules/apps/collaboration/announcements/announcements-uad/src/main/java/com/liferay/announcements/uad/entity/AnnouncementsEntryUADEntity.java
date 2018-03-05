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

package com.liferay.announcements.uad.entity;

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.uad.constants.AnnouncementsUADConstants;
import com.liferay.user.associated.data.entity.BaseUADEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Noah Sherrill
 */
public class AnnouncementsEntryUADEntity extends BaseUADEntity {

	public AnnouncementsEntryUADEntity(
		long userId, String uadEntityId,
		AnnouncementsEntry announcementsEntry) {

		super(
			userId, uadEntityId,
			AnnouncementsUADConstants.CLASS_NAME_ANNOUNCEMENTS_ENTRY);

		_announcementsEntry = announcementsEntry;
	}

	public AnnouncementsEntry getAnnouncementsEntry() {
		return _announcementsEntry;
	}

	@Override
	public Map<String, Object> getUADEntityNonanonymizableFieldValues() {
		Map<String, Object> uadEntityNonanonymizableFieldValues =
			new HashMap<>();

		uadEntityNonanonymizableFieldValues.put(
			"content", _announcementsEntry.getContent());
		uadEntityNonanonymizableFieldValues.put(
			"title", _announcementsEntry.getTitle());

		return uadEntityNonanonymizableFieldValues;
	}

	private final AnnouncementsEntry _announcementsEntry;

}