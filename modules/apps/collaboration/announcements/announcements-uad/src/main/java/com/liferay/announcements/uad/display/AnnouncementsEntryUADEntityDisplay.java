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

package com.liferay.announcements.uad.display;

import com.liferay.announcements.uad.constants.AnnouncementsUADConstants;
import com.liferay.announcements.uad.entity.AnnouncementsEntryUADEntity;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.entity.UADEntity;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author William Newbury
 */
@Component(
	immediate = true,
	property = "model.class.name=" + AnnouncementsUADConstants.CLASS_NAME_ANNOUNCEMENTS_ENTRY,
	service = UADEntityDisplay.class
)
public class AnnouncementsEntryUADEntityDisplay implements UADEntityDisplay {

	public String getApplicationName() {
		return AnnouncementsUADConstants.UAD_ENTITY_SET_NAME;
	}

	public String[] getDisplayFieldNames() {
		return _announcementsEntryUADEntityDisplayHelper.getDisplayFieldNames();
	}

	public String getEditURL(
			UADEntity uadEntity, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		AnnouncementsEntryUADEntity announcementsEntryUADEntity =
			(AnnouncementsEntryUADEntity)uadEntity;

		return _announcementsEntryUADEntityDisplayHelper.
			getAnnouncementsEntryEditURL(
				announcementsEntryUADEntity.getAnnouncementsEntry(),
				liferayPortletRequest, liferayPortletResponse);
	}

	public String getKey() {
		return AnnouncementsUADConstants.CLASS_NAME_ANNOUNCEMENTS_ENTRY;
	}

	public Map<String, Object> getUADEntityNonanonymizableFieldValues(
		UADEntity uadEntity) {

		AnnouncementsEntryUADEntity announcementsEntryUADEntity =
			(AnnouncementsEntryUADEntity)uadEntity;

		return _announcementsEntryUADEntityDisplayHelper.
			getUADEntityNonanonymizableFieldValues(
				announcementsEntryUADEntity.getAnnouncementsEntry());
	}

	public String getUADEntityTypeDescription() {
		return "Announcements posted by the user";
	}

	public String getUADEntityTypeName() {
		return "AnnouncementsEntry";
	}

	@Reference
	private AnnouncementsEntryUADEntityDisplayHelper
		_announcementsEntryUADEntityDisplayHelper;

	@Reference(
		target = "(model.class.name=" + AnnouncementsUADConstants.CLASS_NAME_ANNOUNCEMENTS_ENTRY + ")"
	)
	private UADEntityAnonymizer _uadEntityAnonymizer;

}