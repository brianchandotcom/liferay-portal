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
import com.liferay.announcements.uad.entity.AnnouncementsFlagUADEntity;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.display.BaseUADEntityDisplay;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.entity.UADEntity;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author William Newbury
 */
@Component(
	immediate = true,
	property = "model.class.name=" + AnnouncementsUADConstants.CLASS_NAME_ANNOUNCEMENTS_FLAG,
	service = UADEntityDisplay.class
)
public class AnnouncementsFlagUADEntityDisplay extends BaseUADEntityDisplay {

	@Override
	public String getEditURL(
			UADEntity uadEntity, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		AnnouncementsFlagUADEntity announcementsFlagUADEntity =
			(AnnouncementsFlagUADEntity)uadEntity;

		return _announcementsFlagUADEntityDisplayHelper.
			getAnnouncementsFlagEditURL(
				announcementsFlagUADEntity.getAnnouncementsFlag(),
				liferayPortletRequest, liferayPortletResponse);
	}

	@Override
	public String getUADEntityTypeDescription() {
		return "A flag indicating the status of an announcements entry";
	}

	@Override
	public String getUADEntityTypeName() {
		return "AnnouncementsFlag";
	}

	@Override
	public List<String> getUADEntityTypeNonanonymizableFieldNamesList() {
		return _uadEntityAnonymizer.getUADEntityNonanonymizableFieldNames();
	}

	@Reference
	protected Portal portal;

	@Reference
	private AnnouncementsFlagUADEntityDisplayHelper
		_announcementsFlagUADEntityDisplayHelper;

	@Reference(
		target = "(model.class.name=" + AnnouncementsUADConstants.CLASS_NAME_ANNOUNCEMENTS_FLAG + ")"
	)
	private UADEntityAnonymizer _uadEntityAnonymizer;

}