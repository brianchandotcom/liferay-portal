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

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.uad.constants.AnnouncementsUADConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.user.associated.data.display.UADEntityDisplay;

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
public class AnnouncementsEntryUADEntityDisplay
	implements UADEntityDisplay<AnnouncementsEntry> {

	public String getApplicationName() {
		return AnnouncementsUADConstants.APPLICATION_NAME;
	}

	public String[] getDisplayFieldNames() {
		return _announcementsEntryUADEntityDisplayHelper.getDisplayFieldNames();
	}

	public String getEditURL(
			AnnouncementsEntry announcementsEntry,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		return _announcementsEntryUADEntityDisplayHelper.
			getAnnouncementsEntryEditURL(
				announcementsEntry, liferayPortletRequest,
				liferayPortletResponse);
	}

	public String getKey() {
		return AnnouncementsUADConstants.CLASS_NAME_ANNOUNCEMENTS_ENTRY;
	}

	@Override
	public Map<String, Object> getNonanonymizableFieldValues(
		AnnouncementsEntry announcementsEntry) {

		return _announcementsEntryUADEntityDisplayHelper.
			getUADEntityNonanonymizableFieldValues(announcementsEntry);
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

}