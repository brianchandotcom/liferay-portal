/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.PageExperience;
import com.liferay.headless.admin.site.internal.resource.util.GroupUtil;
import com.liferay.headless.admin.site.resource.v1_0.PageExperienceResource;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceService;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rubén Pulido
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/page-experience.properties",
	scope = ServiceScope.PROTOTYPE, service = PageExperienceResource.class
)
public class PageExperienceResourceImpl extends BasePageExperienceResourceImpl {

	@Override
	public void deleteSiteSiteByExternalReferenceCodePageExperience(
			String siteExternalReferenceCode,
			String pageExperienceExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		_segmentsExperienceService.deleteSegmentsExperience(
			pageExperienceExternalReferenceCode,
			GroupUtil.getGroupId(
				false, contextCompany.getCompanyId(),
				siteExternalReferenceCode));
	}

	@Override
	public PageExperience getSiteSiteByExternalReferenceCodePageExperience(
			String siteExternalReferenceCode,
			String pageExperienceExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		return _pageExperienceDTOConverter.toDTO(
			_segmentsExperienceService.
				getSegmentsExperienceByExternalReferenceCode(
					pageExperienceExternalReferenceCode,
					GroupUtil.getGroupId(
						true, contextCompany.getCompanyId(),
						siteExternalReferenceCode)));
	}

	@Override
	public Page<PageExperience>
			getSiteSiteByExternalReferenceCodePageSpecificationPageExperiencesPage(
				String siteExternalReferenceCode,
				String pageSpecificationExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Layout layout = _layoutLocalService.fetchLayoutByExternalReferenceCode(
			pageSpecificationExternalReferenceCode,
			GroupUtil.getGroupId(
				true, contextCompany.getCompanyId(),
				siteExternalReferenceCode));

		if (layout == null) {
			return Page.of(Collections.emptyList());
		}

		return Page.of(
			transform(
				_segmentsExperienceService.getSegmentsExperiences(
					layout.getGroupId(), layout.getPlid(), true,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null),
				segmentsExperience -> _pageExperienceDTOConverter.toDTO(
					segmentsExperience)));
	}

	@Override
	public PageExperience
			postSiteSiteByExternalReferenceCodePageSpecificationPageExperience(
				String siteExternalReferenceCode,
				String pageSpecificationExternalReferenceCode,
				PageExperience pageExperience)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Layout layout = _layoutLocalService.fetchLayoutByExternalReferenceCode(
			pageSpecificationExternalReferenceCode,
			GroupUtil.getGroupId(
				false, contextCompany.getCompanyId(),
				siteExternalReferenceCode));

		if (layout == null) {
			throw new UnsupportedOperationException();
		}

		return _addSegmentsExperience(layout, pageExperience);
	}

	@Override
	public PageExperience putSiteSiteByExternalReferenceCodePageExperience(
			String siteExternalReferenceCode,
			String pageExperienceExternalReferenceCode,
			PageExperience pageExperience)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		long groupId = GroupUtil.getGroupId(
			false, contextCompany.getCompanyId(), siteExternalReferenceCode);

		SegmentsExperience segmentsExperience =
			_segmentsExperienceService.
				fetchSegmentsExperienceByExternalReferenceCode(
					pageExperienceExternalReferenceCode, groupId);

		if (segmentsExperience == null) {
			Layout layout =
				_layoutLocalService.fetchLayoutByExternalReferenceCode(
					pageExperience.getSitePageExternalReferenceCode(), groupId);

			if (layout == null) {
				throw new UnsupportedOperationException();
			}

			return _addSegmentsExperience(layout, pageExperience);
		}

		if ((pageExperience.getPriority() != null) &&
			(segmentsExperience.getPriority() !=
				pageExperience.getPriority())) {

			segmentsExperience =
				_segmentsExperienceService.updateSegmentsExperiencePriority(
					segmentsExperience.getSegmentsExperienceId(),
					GetterUtil.getInteger(pageExperience.getPriority()));
		}

		return _pageExperienceDTOConverter.toDTO(
			_segmentsExperienceService.updateSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId(),
				_getSegmentsEntryId(
					groupId, pageExperience.getSegmentExternalReferenceCode()),
				LocalizedMapUtil.getLocalizedMap(pageExperience.getName_i18n()),
				true,
				UnicodePropertiesBuilder.create(
					true
				).build()));
	}

	private PageExperience _addSegmentsExperience(
			Layout layout, PageExperience pageExperience)
		throws Exception {

		return _pageExperienceDTOConverter.toDTO(
			_segmentsExperienceService.addSegmentsExperience(
				pageExperience.getExternalReferenceCode(), layout.getGroupId(),
				_getSegmentsEntryId(
					layout.getGroupId(),
					pageExperience.getSegmentExternalReferenceCode()),
				pageExperience.getKey(), layout.getPlid(),
				LocalizedMapUtil.getLocalizedMap(pageExperience.getName_i18n()),
				GetterUtil.getInteger(pageExperience.getPriority()), true,
				UnicodePropertiesBuilder.create(
					true
				).build(),
				ServiceContextBuilder.create(
					layout.getGroupId(), contextHttpServletRequest, null
				).build()));
	}

	private long _getSegmentsEntryId(
		long groupId, String segmentExternalReferenceCode) {

		if (Validator.isNull(segmentExternalReferenceCode)) {
			return 0;
		}

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(
				groupId, segmentExternalReferenceCode);

		if (segmentsEntry == null) {
			throw new UnsupportedOperationException();
		}

		return segmentsEntry.getSegmentsEntryId();
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.PageExperienceDTOConverter)"
	)
	private DTOConverter<SegmentsExperience, PageExperience>
		_pageExperienceDTOConverter;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private SegmentsExperienceService _segmentsExperienceService;

}