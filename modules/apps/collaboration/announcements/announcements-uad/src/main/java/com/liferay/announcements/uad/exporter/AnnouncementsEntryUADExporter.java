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

package com.liferay.announcements.uad.exporter;

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.kernel.service.AnnouncementsEntryLocalService;
import com.liferay.announcements.uad.constants.AnnouncementsUADConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.user.associated.data.exporter.DynamicQueryUADExporter;
import com.liferay.user.associated.data.exporter.UADExporter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noah Sherrill
 */
@Component(
	immediate = true,
	property = "model.class.name=" + AnnouncementsUADConstants.CLASS_NAME_ANNOUNCEMENTS_ENTRY,
	service = UADExporter.class
)
public class AnnouncementsEntryUADExporter
	extends DynamicQueryUADExporter<AnnouncementsEntry> {

	@Override
	protected ActionableDynamicQuery doGetActionableDynamicQuery() {
		return _announcementsEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return AnnouncementsUADConstants.
			USER_ID_FIELD_NAMES_ANNOUNCEMENTS_ENTRY;
	}

	@Override
	protected String toXmlString(AnnouncementsEntry announcementsEntry) {
		StringBundler sb = new StringBundler(24);

		sb.append("<model><model-name>");
		sb.append("com.liferay.announcements.kernel.model.AnnouncementsEntry");
		sb.append("</model-name>");

		sb.append("<column><column-name>entryId</column-name>");
		sb.append("<column-value><![CDATA[");
		sb.append(announcementsEntry.getEntryId());
		sb.append("]]></column-value></column>");

		sb.append("<column><column-name>userId</column-name>");
		sb.append("<column-value><![CDATA[");
		sb.append(announcementsEntry.getUserId());
		sb.append("]]></column-value></column>");

		sb.append("<column><column-name>userName</column-name>");
		sb.append("<column-value><![CDATA[");
		sb.append(announcementsEntry.getUserName());
		sb.append("]]></column-value></column>");

		sb.append("<column><column-name>title</column-name>");
		sb.append("<column-value><![CDATA[");
		sb.append(announcementsEntry.getTitle());
		sb.append("]]></column-value></column>");

		sb.append("<column><column-name>content</column-name>");
		sb.append("<column-value><![CDATA[");
		sb.append(announcementsEntry.getContent());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	@Reference
	private AnnouncementsEntryLocalService _announcementsEntryLocalService;

}