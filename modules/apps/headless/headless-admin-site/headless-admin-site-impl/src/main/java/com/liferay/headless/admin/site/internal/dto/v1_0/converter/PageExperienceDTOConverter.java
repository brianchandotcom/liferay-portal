/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.dto.v1_0.PageExperience;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "dto.class.name=com.liferay.segments.model.SegmentsExperience",
	service = DTOConverter.class
)
public class PageExperienceDTOConverter
	implements DTOConverter<SegmentsExperience, PageExperience> {

	@Override
	public String getContentType() {
		return PageExperience.class.getSimpleName();
	}

	@Override
	public PageExperience toDTO(
			DTOConverterContext dtoConverterContext,
			SegmentsExperience segmentsExperience)
		throws Exception {

		Layout layout = _layoutLocalService.getLayout(
			segmentsExperience.getPlid());

		return new PageExperience() {
			{
				setExternalReferenceCode(
					segmentsExperience::getExternalReferenceCode);
				setKey(segmentsExperience::getSegmentsExperienceKey);
				setName_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						true, segmentsExperience.getNameMap()));
				setPageElements(
					() -> new PageElement[] {
						_pageElementDTOConverter.toDTO(layout),
						_pageElementDTOConverter.toDTO(
							layout.fetchDraftLayout())
					});
				setPriority(segmentsExperience::getPriority);
				setSegmentExternalReferenceCode(
					() -> {
						SegmentsEntry segmentsEntry =
							_segmentsEntryLocalService.fetchSegmentsEntry(
								segmentsExperience.getSegmentsEntryId());

						if (segmentsEntry == null) {
							return null;
						}

						return segmentsEntry.getSegmentsEntryKey();
					});
				setSitePageExternalReferenceCode(
					layout::getExternalReferenceCode);
			}
		};
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.PageElementDTOConverter)"
	)
	private DTOConverter<Layout, PageElement> _pageElementDTOConverter;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

}