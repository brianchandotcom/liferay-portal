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

package com.liferay.headless.admin.user.internal.dto.v1_0.converter;

import com.liferay.headless.admin.user.dto.v1_0.Segment;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.segments.model.SegmentsEntry;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "dto.class.name=com.liferay.segments.model.SegmentsEntry",
	service = {DTOConverter.class, SegmentDTOConverter.class}
)
public class SegmentDTOConverter
	implements DTOConverter<SegmentsEntry, Segment> {

	@Override
	public String getContentType() {
		return SegmentsEntry.class.getSimpleName();
	}

	@Override
	public Segment toDTO(SegmentsEntry segmentsEntry) throws Exception {
		return new Segment() {
			{
				active = segmentsEntry.isActive();
				criteria = segmentsEntry.getCriteria();
				dateCreated = segmentsEntry.getCreateDate();
				dateModified = segmentsEntry.getModifiedDate();
				id = segmentsEntry.getSegmentsEntryId();
				name = segmentsEntry.getName(
					segmentsEntry.getDefaultLanguageId());
				siteId = segmentsEntry.getGroupId();
				source = segmentsEntry.getSource();
			}
		};
	}

}