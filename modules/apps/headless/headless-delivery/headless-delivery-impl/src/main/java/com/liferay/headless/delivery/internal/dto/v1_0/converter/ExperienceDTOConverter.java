/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.converter;

import com.liferay.headless.admin.user.dto.v1_0.Segment;
import com.liferay.headless.delivery.dto.v1_0.Experience;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.segments.constants.SegmentsEntryConstants;
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
	service = DTOConverter.class
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
						if (segmentsExperience.getSegmentsEntryId() ==
								SegmentsEntryConstants.ID_DEFAULT) {

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

						SegmentsEntry segmentsEntry =
							_segmentsEntryService.getSegmentsEntry(
								segmentsExperience.getSegmentsEntryId());

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