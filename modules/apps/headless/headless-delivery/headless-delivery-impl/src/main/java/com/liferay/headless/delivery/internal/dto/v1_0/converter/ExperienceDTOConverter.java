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

package com.liferay.headless.delivery.internal.dto.v1_0.converter;

import com.liferay.headless.admin.user.dto.v1_0.Segment;
import com.liferay.headless.delivery.dto.v1_0.Experience;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsEntryService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "dto.class.name=com.liferay.segments.model.SegmentsExperience",
	service = {DTOConverter.class, ExperienceDTOConverter.class}
)
public class ExperienceDTOConverter
	implements DTOConverter<SegmentsExperience, Experience> {

	@Override
	public String getContentType() {
		return Experience.class.getSimpleName();
	}

	@Override
	public Experience toDTO(
		DTOConverterContext dtoConverterContext,
		SegmentsExperience segmentsExperience) {

		return new Experience() {
			{
				key = segmentsExperience.getSegmentsExperienceKey();
				name = segmentsExperience.getName(
					dtoConverterContext.getLocale());
				name_i18n = LocalizedMapUtil.getI18nMap(
					dtoConverterContext.isAcceptAllLanguages(),
					segmentsExperience.getNameMap());

				setSegments(
					() -> {
						SegmentsEntry segmentsEntry =
							_segmentsEntryService.getSegmentsEntry(
								segmentsExperience.getSegmentsEntryId());

						if (segmentsEntry == null) {
							return null;
						}

						DTOConverterRegistry dtoConverterRegistry =
							dtoConverterContext.getDTOConverterRegistry();

						DTOConverter<SegmentsEntry, Segment> dtoConverter =
							(DTOConverter<SegmentsEntry, Segment>)
								dtoConverterRegistry.getDTOConverter(
									SegmentsEntry.class.getName());

						if (dtoConverter == null) {
							return null;
						}

						return new Segment[] {
							dtoConverter.toDTO(segmentsEntry)
						};
					});
			}
		};
	}

	@Reference
	private SegmentsEntryService _segmentsEntryService;

}