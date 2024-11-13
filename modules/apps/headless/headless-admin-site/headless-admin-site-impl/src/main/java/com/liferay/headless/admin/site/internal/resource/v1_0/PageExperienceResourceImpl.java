/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.PageExperience;
import com.liferay.headless.admin.site.resource.v1_0.PageExperienceResource;
import com.liferay.headless.common.spi.service.context.ServiceContextBuilder;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
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

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		_segmentsExperienceService.deleteSegmentsExperience(
			pageExperienceExternalReferenceCode, group.getGroupId());
	}

	@Override
	public PageExperience getSiteSiteByExternalReferenceCodePageExperience(
			String siteExternalReferenceCode,
			String pageExperienceExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		return _pageExperienceDTOConverter.toDTO(
			_segmentsExperienceService.
				getSegmentsExperienceByExternalReferenceCode(
					pageExperienceExternalReferenceCode, group.getGroupId()));
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

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		Layout layout = _layoutLocalService.fetchLayoutByExternalReferenceCode(
			pageSpecificationExternalReferenceCode, group.getGroupId());

		if (layout == null) {
			return Page.of(Collections.emptyList());
		}

		return Page.of(
			transform(
				_segmentsExperienceService.getSegmentsExperiences(
					group.getGroupId(), layout.getPlid(), true,
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

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		Layout layout = _layoutLocalService.fetchLayoutByExternalReferenceCode(
			pageSpecificationExternalReferenceCode, group.getGroupId());

		if (layout == null) {
			throw new UnsupportedOperationException();
		}

		return _addSegmentsExperience(group, layout, pageExperience);
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

		Group group = groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceService.
				fetchSegmentsExperienceByExternalReferenceCode(
					pageExperienceExternalReferenceCode, group.getGroupId());

		if (segmentsExperience == null) {
			Layout layout =
				_layoutLocalService.fetchLayoutByExternalReferenceCode(
					pageExperience.getSitePageExternalReferenceCode(),
					group.getGroupId());

			if (layout == null) {
				throw new UnsupportedOperationException();
			}

			return _addSegmentsExperience(group, layout, pageExperience);
		}

		if ((pageExperience.getPriority() != null) &&
			(segmentsExperience.getPriority() !=
				pageExperience.getPriority())) {

			segmentsExperience =
				_segmentsExperienceService.updateSegmentsExperiencePriority(
					segmentsExperience.getSegmentsExperienceId(),
					GetterUtil.getInteger(pageExperience.getPriority()));
		}

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(
				group.getGroupId(),
				pageExperience.getSegmentExternalReferenceCode());

		if (segmentsEntry == null) {
			throw new UnsupportedOperationException();
		}

		return _pageExperienceDTOConverter.toDTO(
			_segmentsExperienceService.updateSegmentsExperience(
				segmentsExperience.getSegmentsExperienceId(),
				segmentsEntry.getSegmentsEntryId(),
				LocalizedMapUtil.getLocalizedMap(pageExperience.getName_i18n()),
				true,
				UnicodePropertiesBuilder.create(
					true
				).build()));
	}

	private PageExperience _addSegmentsExperience(
			Group group, Layout layout, PageExperience pageExperience)
		throws Exception {

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(
				group.getGroupId(),
				pageExperience.getSegmentExternalReferenceCode());

		if (segmentsEntry == null) {
			throw new UnsupportedOperationException();
		}

		return _pageExperienceDTOConverter.toDTO(
			_segmentsExperienceService.addSegmentsExperience(
				pageExperience.getExternalReferenceCode(), group.getGroupId(),
				segmentsEntry.getSegmentsEntryId(), pageExperience.getKey(),
				layout.getPlid(),
				LocalizedMapUtil.getLocalizedMap(pageExperience.getName_i18n()),
				GetterUtil.getInteger(pageExperience.getPriority()), true,
				UnicodePropertiesBuilder.create(
					true
				).build(),
				ServiceContextBuilder.create(
					group.getGroupId(), contextHttpServletRequest, null
				).build()));
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