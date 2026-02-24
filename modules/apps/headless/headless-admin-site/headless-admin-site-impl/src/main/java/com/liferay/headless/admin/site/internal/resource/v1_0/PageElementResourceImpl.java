/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.DTOConverterContextUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.GroupUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.LayoutStructureUtil;
import com.liferay.headless.admin.site.resource.v1_0.PageElementResource;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.LayoutServiceContextHelper;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItemUtil;
import com.liferay.layout.util.structure.exception.NoSuchLayoutStructureItemException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rubén Pulido
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/page-element.properties",
	scope = ServiceScope.PROTOTYPE, service = PageElementResource.class
)
public class PageElementResourceImpl extends BasePageElementResourceImpl {

	@Override
	@Tags({@Tag(description = "[DEV]", name = "PageElement")})
	public void deleteSitePageSpecificationPageExperiencePageElement(
			String siteExternalReferenceCode,
			String pageSpecificationExternalReferenceCode,
			String pageExperienceExternalReferenceCode,
			String pageElementExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-74328")) {

			throw new UnsupportedOperationException();
		}

		long groupId = GroupUtil.getStagingAwareGroupId(
			contextCompany.getCompanyId(), siteExternalReferenceCode);

		Layout layout = _layoutLocalService.fetchLayoutByExternalReferenceCode(
			pageSpecificationExternalReferenceCode, groupId);

		if (layout == null) {
			throw new UnsupportedOperationException();
		}

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.
				fetchSegmentsExperienceByExternalReferenceCode(
					pageExperienceExternalReferenceCode, groupId);

		if (segmentsExperience == null) {
			throw new UnsupportedOperationException();
		}

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(
				segmentsExperience.getSegmentsExperienceKey()));

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(
				pageElementExternalReferenceCode);

		if (layoutStructureItem == null) {
			throw new NoSuchLayoutStructureItemException();
		}

		layoutStructure.deleteLayoutStructureItem(
			pageElementExternalReferenceCode);

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				contextUser.getUserId(), layout.getGroupId(), layout.getPlid(),
				layoutStructure.toString());
	}

	@Override
	public PageElement getSitePageSpecificationPageExperiencePageElement(
			String siteExternalReferenceCode,
			String pageSpecificationExternalReferenceCode,
			String pageExperienceExternalReferenceCode,
			String pageElementExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-74328")) {

			throw new UnsupportedOperationException();
		}

		long groupId = GroupUtil.getStagingAwareGroupId(
			contextCompany.getCompanyId(), siteExternalReferenceCode);

		Layout layout = _layoutLocalService.fetchLayoutByExternalReferenceCode(
			pageSpecificationExternalReferenceCode, groupId);

		if (layout == null) {
			throw new UnsupportedOperationException();
		}

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.
				fetchSegmentsExperienceByExternalReferenceCode(
					pageExperienceExternalReferenceCode, groupId);

		if (segmentsExperience == null) {
			throw new UnsupportedOperationException();
		}

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(
				segmentsExperience.getSegmentsExperienceKey()));

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(
				pageElementExternalReferenceCode);

		if (layoutStructureItem == null) {
			throw new NoSuchLayoutStructureItemException();
		}

		return _pageElementDTOConverter.toDTO(
			_getDTOConverterContext(
				layoutPageTemplateStructure.getCompanyId(),
				layoutStructureItem.getItemId(), layout.getPlid(),
				layoutStructure, groupId),
			layoutStructureItem);
	}

	@Override
	public Page<PageElement>
			getSitePageSpecificationPageExperiencePageElementPageElementsPage(
				String siteExternalReferenceCode,
				String pageSpecificationExternalReferenceCode,
				String pageExperienceExternalReferenceCode,
				String pageElementExternalReferenceCode, Boolean flatten)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-74328")) {

			throw new UnsupportedOperationException();
		}

		long groupId = GroupUtil.getStagingAwareGroupId(
			contextCompany.getCompanyId(), siteExternalReferenceCode);

		Layout layout = _layoutLocalService.fetchLayoutByExternalReferenceCode(
			pageSpecificationExternalReferenceCode, groupId);

		if (layout == null) {
			throw new UnsupportedOperationException();
		}

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.
				fetchSegmentsExperienceByExternalReferenceCode(
					pageExperienceExternalReferenceCode, groupId);

		if (segmentsExperience == null) {
			throw new UnsupportedOperationException();
		}

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(
				segmentsExperience.getSegmentsExperienceKey()));

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(
				pageElementExternalReferenceCode);

		DTOConverterContext dtoConverterContext = _getDTOConverterContext(
			layoutPageTemplateStructure.getCompanyId(), null, layout.getPlid(),
			layoutStructure, groupId);

		return Page.of(
			transform(
				LayoutStructureItemUtil.getChildrenItemIds(
					layoutStructureItem.getItemId(), layoutStructure),
				itemId -> _pageElementDTOConverter.toDTO(
					dtoConverterContext,
					layoutStructure.getLayoutStructureItem(itemId))));
	}

	@Override
	public Page<PageElement>
			getSitePageSpecificationPageExperiencePageElementsPage(
				String siteExternalReferenceCode,
				String pageSpecificationExternalReferenceCode,
				String pageExperienceExternalReferenceCode, Boolean flatten)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-74328")) {

			throw new UnsupportedOperationException();
		}

		long groupId = GroupUtil.getStagingAwareGroupId(
			contextCompany.getCompanyId(), siteExternalReferenceCode);

		Layout layout = _layoutLocalService.fetchLayoutByExternalReferenceCode(
			pageSpecificationExternalReferenceCode, groupId);

		if (layout == null) {
			throw new UnsupportedOperationException();
		}

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.
				fetchSegmentsExperienceByExternalReferenceCode(
					pageExperienceExternalReferenceCode, groupId);

		if (segmentsExperience == null) {
			throw new UnsupportedOperationException();
		}

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(
				segmentsExperience.getSegmentsExperienceId()));

		DTOConverterContext dtoConverterContext = _getDTOConverterContext(
			layoutPageTemplateStructure.getCompanyId(), null, layout.getPlid(),
			layoutStructure, groupId);

		return Page.of(
			transform(
				LayoutStructureItemUtil.getChildrenItemIds(
					layoutStructure.getMainItemId(), layoutStructure),
				itemId -> _pageElementDTOConverter.toDTO(
					dtoConverterContext,
					layoutStructure.getLayoutStructureItem(itemId))));
	}

	@Override
	public PageElement postSitePageSpecificationPageExperiencePageElement(
			String siteExternalReferenceCode,
			String pageSpecificationExternalReferenceCode,
			String pageExperienceExternalReferenceCode, PageElement pageElement)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-74328")) {

			throw new UnsupportedOperationException();
		}

		long groupId = GroupUtil.getStagingAwareGroupId(
			contextCompany.getCompanyId(), siteExternalReferenceCode);

		Layout layout = _layoutLocalService.fetchLayoutByExternalReferenceCode(
			pageSpecificationExternalReferenceCode, groupId);

		if (layout == null) {
			throw new UnsupportedOperationException();
		}

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.
				fetchSegmentsExperienceByExternalReferenceCode(
					pageExperienceExternalReferenceCode, groupId);

		if (segmentsExperience == null) {
			throw new UnsupportedOperationException();
		}

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(
				segmentsExperience.getSegmentsExperienceId()));

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(
				pageElement.getExternalReferenceCode());

		if (layoutStructureItem != null) {
			throw new UnsupportedOperationException();
		}

		return _addOrUpdatePageElement(
			groupId, layout, layoutStructure, pageElement,
			segmentsExperience.getSegmentsExperienceId());
	}

	@Override
	public PageElement putSitePageSpecificationPageExperiencePageElement(
			String siteExternalReferenceCode,
			String pageSpecificationExternalReferenceCode,
			String pageExperienceExternalReferenceCode,
			String pageElementExternalReferenceCode, PageElement pageElement)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-74328")) {

			throw new UnsupportedOperationException();
		}

		long groupId = GroupUtil.getStagingAwareGroupId(
			contextCompany.getCompanyId(), siteExternalReferenceCode);

		Layout layout = _layoutLocalService.fetchLayoutByExternalReferenceCode(
			pageSpecificationExternalReferenceCode, groupId);

		if (layout == null) {
			throw new UnsupportedOperationException();
		}

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.
				fetchSegmentsExperienceByExternalReferenceCode(
					pageExperienceExternalReferenceCode, groupId);

		if (segmentsExperience == null) {
			throw new UnsupportedOperationException();
		}

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		return _addOrUpdatePageElement(
			groupId, layout,
			LayoutStructure.of(
				layoutPageTemplateStructure.getData(
					segmentsExperience.getSegmentsExperienceId())),
			pageElement, segmentsExperience.getSegmentsExperienceId());
	}

	private PageElement _addOrUpdatePageElement(
			long groupId, Layout layout, LayoutStructure layoutStructure,
			PageElement pageElement, long segmentsExperienceId)
		throws Exception {

		try (AutoCloseable autoCloseable =
				_layoutServiceContextHelper.getServiceContextAutoCloseable(
					layout, contextUser)) {

			LayoutStructureItem layoutStructureItem =
				LayoutStructureUtil.addLayoutStructureItem(
					layoutStructure,
					new LayoutStructureItemImporterContext(
						contextCompany.getCompanyId(),
						_fragmentEntryProcessorRegistry, groupId,
						_infoItemServiceRegistry, layout, segmentsExperienceId,
						contextUser),
					pageElement);

			_layoutPageTemplateStructureLocalService.
				updateLayoutPageTemplateStructureData(
					contextUser.getUserId(), layout.getGroupId(),
					layout.getPlid(), layoutStructure.toString());

			return _pageElementDTOConverter.toDTO(
				_getDTOConverterContext(
					layout.getCompanyId(), layoutStructureItem.getItemId(),
					layout.getPlid(), layoutStructure, groupId),
				layoutStructureItem);
		}
	}

	private DTOConverterContext _getDTOConverterContext(
		long companyId, String itemId, long layoutPlid,
		LayoutStructure layoutStructure, long scopeGroupId) {

		return DTOConverterContextUtil.getDTOConverterContext(
			contextAcceptLanguage,
			HashMapBuilder.<String, Object>put(
				LayoutStructure.class.getName(), layoutStructure
			).put(
				"companyId", companyId
			).put(
				"layoutPlid", layoutPlid
			).put(
				"scopeGroupId", scopeGroupId
			).build(),
			_dtoConverterRegistry, contextHttpServletRequest, itemId,
			contextUriInfo, contextUser);
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutServiceContextHelper _layoutServiceContextHelper;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.PageElementDTOConverter)"
	)
	private DTOConverter<LayoutStructureItem, PageElement>
		_pageElementDTOConverter;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}