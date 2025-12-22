/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.util;

import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.dto.v1_0.PageExperience;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.ItemScopeUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsEntryLocalServiceUtil;
import com.liferay.segments.service.SegmentsExperienceServiceUtil;

import java.util.Objects;

/**
 * @author Lourdes Fernández Besada
 */
public class SegmentsExperienceUtil {

	public static SegmentsExperience addSegmentsExperience(
			FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry,
			InfoItemServiceRegistry infoItemServiceRegistry, Layout layout,
			PageExperience pageExperience, int priority,
			ServiceContext serviceContext)
		throws Exception {

		if (!Objects.equals(layout.getType(), LayoutConstants.TYPE_CONTENT)) {
			throw new UnsupportedOperationException();
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(layout.getPlid());

		if (layoutPageTemplateEntry != null) {
			throw new UnsupportedOperationException();
		}

		SegmentsExperience segmentsExperience =
			SegmentsExperienceServiceUtil.addSegmentsExperience(
				pageExperience.getExternalReferenceCode(), layout.getGroupId(),
				_getSegmentsEntryId(
					layout.getCompanyId(), layout.getGroupId(),
					pageExperience.getSegmentItemExternalReference()),
				pageExperience.getKey(), layout.getPlid(),
				LocalizedMapUtil.getLocalizedMap(pageExperience.getName_i18n()),
				priority, true,
				UnicodePropertiesBuilder.create(
					true
				).build(),
				serviceContext);

		LayoutLocalServiceUtil.updateLayoutContent(
			_getData(
				fragmentEntryProcessorRegistry, infoItemServiceRegistry, layout,
				pageExperience, segmentsExperience.getSegmentsExperienceId(),
				serviceContext),
			layout, segmentsExperience.getSegmentsExperienceId());

		return segmentsExperience;
	}

	public static SegmentsExperience addSegmentsExperience(
			FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry,
			InfoItemServiceRegistry infoItemServiceRegistry, Layout layout,
			PageExperience pageExperience, ServiceContext serviceContext)
		throws Exception {

		return addSegmentsExperience(
			fragmentEntryProcessorRegistry, infoItemServiceRegistry, layout,
			pageExperience, GetterUtil.getInteger(pageExperience.getPriority()),
			serviceContext);
	}

	public static String getDefaultSegmentsExperienceExternalReferenceCode(
		PageExperience[] pageExperiences) {

		if (ArrayUtil.isEmpty(pageExperiences)) {
			throw new UnsupportedOperationException();
		}

		for (PageExperience pageExperience : pageExperiences) {
			if (Objects.equals(
					pageExperience.getKey(),
					SegmentsExperienceConstants.KEY_DEFAULT)) {

				return pageExperience.getExternalReferenceCode();
			}
		}

		throw new UnsupportedOperationException();
	}

	public static SegmentsExperience updateSegmentsExperience(
			FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry,
			InfoItemServiceRegistry infoItemServiceRegistry, Layout layout,
			PageExperience pageExperience, int priority,
			SegmentsExperience segmentsExperience,
			ServiceContext serviceContext)
		throws Exception {

		LayoutLocalServiceUtil.updateLayoutContent(
			_getData(
				fragmentEntryProcessorRegistry, infoItemServiceRegistry, layout,
				pageExperience, segmentsExperience.getSegmentsExperienceId(),
				serviceContext),
			layout, segmentsExperience.getSegmentsExperienceId());

		if (priority != segmentsExperience.getPriority()) {
			segmentsExperience =
				SegmentsExperienceServiceUtil.updateSegmentsExperiencePriority(
					segmentsExperience.getSegmentsExperienceId(), priority);
		}

		return SegmentsExperienceServiceUtil.updateSegmentsExperience(
			segmentsExperience.getSegmentsExperienceId(),
			_getSegmentsEntryId(
				layout.getCompanyId(), layout.getGroupId(),
				pageExperience.getSegmentItemExternalReference()),
			LocalizedMapUtil.getLocalizedMap(pageExperience.getName_i18n()),
			true,
			UnicodePropertiesBuilder.create(
				true
			).build());
	}

	public static SegmentsExperience updateSegmentsExperience(
			FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry,
			InfoItemServiceRegistry infoItemServiceRegistry, Layout layout,
			PageExperience pageExperience,
			SegmentsExperience segmentsExperience,
			ServiceContext serviceContext)
		throws Exception {

		int priority = segmentsExperience.getPriority();

		if (pageExperience.getPriority() != null) {
			priority = pageExperience.getPriority();
		}

		return updateSegmentsExperience(
			fragmentEntryProcessorRegistry, infoItemServiceRegistry, layout,
			pageExperience, priority, segmentsExperience, serviceContext);
	}

	private static String _getData(
			FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry,
			InfoItemServiceRegistry infoItemServiceRegistry, Layout layout,
			PageExperience pageExperience, long segmentsExperienceId,
			ServiceContext serviceContext)
		throws Exception {

		LayoutStructure layoutStructure = new LayoutStructure();

		layoutStructure.addRootLayoutStructureItem();

		LayoutStructureItemImporterContext layoutStructureItemImporterContext =
			new LayoutStructureItemImporterContext(
				serviceContext.getCompanyId(), fragmentEntryProcessorRegistry,
				layout.getGroupId(), infoItemServiceRegistry, layout,
				segmentsExperienceId, serviceContext.getUserId());

		for (PageElement pageElement : pageExperience.getPageElements()) {
			LayoutStructureUtil.addLayoutStructureItem(
				layoutStructure, layoutStructureItemImporterContext,
				pageElement);
		}

		return layoutStructure.toString();
	}

	private static long _getSegmentsEntryId(
		long companyId, long scopeGroupId,
		ItemExternalReference segmentItemExternalReference) {

		if (segmentItemExternalReference == null) {
			return 0;
		}

		Long itemGroupId = ItemScopeUtil.getItemGroupId(
			companyId, segmentItemExternalReference.getScope(), scopeGroupId);

		if (itemGroupId == null) {
			throw new UnsupportedOperationException();
		}

		SegmentsEntry segmentsEntry =
			SegmentsEntryLocalServiceUtil.
				fetchSegmentsEntryByExternalReferenceCode(
					segmentItemExternalReference.getExternalReferenceCode(),
					itemGroupId);

		if (segmentsEntry == null) {
			throw new UnsupportedOperationException();
		}

		return segmentsEntry.getSegmentsEntryId();
	}

}