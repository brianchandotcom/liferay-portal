/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.internal.resource.util.GroupUtil;
import com.liferay.headless.admin.site.resource.v1_0.PageElementResource;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItemUtil;
import com.liferay.layout.util.structure.exception.NoSuchLayoutStructureItemException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Map;

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
	public void deleteSiteSiteByExternalReferenceCodePageElement(
			String siteExternalReferenceCode,
			String pageSpecificationExternalReferenceCode,
			String pageExperienceExternalReferenceCode,
			String pageElementExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		long groupId = GroupUtil.getGroupId(
			false, contextCompany.getCompanyId(), siteExternalReferenceCode);

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
				layout.getGroupId(), layout.getPlid(),
				layoutStructure.toString());
	}

	@Override
	public PageElement getSiteSiteByExternalReferenceCodePageElement(
			String siteExternalReferenceCode,
			String pageSpecificationExternalReferenceCode,
			String pageExperienceExternalReferenceCode,
			String pageElementExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		long groupId = GroupUtil.getGroupId(
			false, contextCompany.getCompanyId(), siteExternalReferenceCode);

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

		return _pageElementDTOConverter.toDTO(layoutStructureItem);
	}

	@Override
	public Page<PageElement>
			getSiteSiteByExternalReferenceCodePageElementPageElementsPage(
				String siteExternalReferenceCode,
				String pageSpecificationExternalReferenceCode,
				String pageExperienceExternalReferenceCode,
				String pageElementExternalReferenceCode, Boolean flatten)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		long groupId = GroupUtil.getGroupId(
			false, contextCompany.getCompanyId(), siteExternalReferenceCode);

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

		return Page.of(
			transform(
				LayoutStructureItemUtil.getChildrenItemIds(
					layoutStructureItem.getItemId(), layoutStructure),
				itemId -> _pageElementDTOConverter.toDTO(
					layoutStructure.getLayoutStructureItem(itemId))));
	}

	@Override
	public Page<PageElement>
			getSiteSiteByExternalReferenceCodePageExperiencePageElementsPage(
				String siteExternalReferenceCode,
				String pageSpecificationExternalReferenceCode,
				String pageExperienceExternalReferenceCode, Boolean flatten)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		long groupId = GroupUtil.getGroupId(
			false, contextCompany.getCompanyId(), siteExternalReferenceCode);

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

		return Page.of(
			transform(
				LayoutStructureItemUtil.getChildrenItemIds(
					layoutStructure.getMainItemId(), layoutStructure),
				itemId -> _pageElementDTOConverter.toDTO(
					layoutStructure.getLayoutStructureItem(itemId))));
	}

	@Override
	public PageElement
			postSiteSiteByExternalReferenceCodePageExperiencePageElement(
				String siteExternalReferenceCode,
				String pageSpecificationExternalReferenceCode,
				String pageExperienceExternalReferenceCode,
				PageElement pageElement)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		long groupId = GroupUtil.getGroupId(
			false, contextCompany.getCompanyId(), siteExternalReferenceCode);

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
			layoutStructure.addLayoutStructureItem(
				pageElement.getExternalReferenceCode(),
				_externalToInternalValuesMap.get(pageElement.getType()),
				pageElement.getParentExternalReferenceCode(),
				pageElement.getPosition());

		_addChildPageElements(layoutStructure, pageElement);

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				layout.getGroupId(), layout.getPlid(),
				layoutStructure.toString());

		return _pageElementDTOConverter.toDTO(layoutStructureItem);
	}

	private void _addChildPageElements(
		LayoutStructure layoutStructure, PageElement pageElement) {

		for (PageElement childPageElement : pageElement.getPageElements()) {
			layoutStructure.addLayoutStructureItem(
				_externalToInternalValuesMap.get(childPageElement.getType()),
				childPageElement.getParentExternalReferenceCode(),
				childPageElement.getPosition());

			_addChildPageElements(layoutStructure, childPageElement);
		}
	}

	private static final Map<PageElement.Type, String>
		_externalToInternalValuesMap = HashMapBuilder.put(
			PageElement.Type.COLLECTION,
			LayoutDataItemTypeConstants.TYPE_COLLECTION
		).put(
			PageElement.Type.COLLECTION_ITEM,
			LayoutDataItemTypeConstants.TYPE_COLLECTION_ITEM
		).put(
			PageElement.Type.COLUMN, LayoutDataItemTypeConstants.TYPE_COLUMN
		).put(
			PageElement.Type.CONTAINER,
			LayoutDataItemTypeConstants.TYPE_CONTAINER
		).put(
			PageElement.Type.DROP_ZONE,
			LayoutDataItemTypeConstants.TYPE_DROP_ZONE
		).put(
			PageElement.Type.FORM, LayoutDataItemTypeConstants.TYPE_FORM
		).put(
			PageElement.Type.FRAGMENT, LayoutDataItemTypeConstants.TYPE_FRAGMENT
		).put(
			PageElement.Type.FRAGMENT_DROP_ZONE,
			LayoutDataItemTypeConstants.TYPE_FRAGMENT_DROP_ZONE
		).put(
			PageElement.Type.ROW, LayoutDataItemTypeConstants.TYPE_ROW
		).build();

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference(
		target = "(component.name=com.liferay.headless.admin.site.internal.dto.v1_0.converter.PageElementDTOConverter)"
	)
	private DTOConverter<LayoutStructureItem, PageElement>
		_pageElementDTOConverter;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}