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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.converter;

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.LayoutStructureItemExporter;
import com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.LayoutStructureItemExporterTracker;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = PageElementDTOConverter.class)
public class PageElementDTOConverter {

	public PageElement toDTO(
		Layout layout, boolean saveInlineContent,
		boolean saveMappingConfiguration, long segmentsExperienceId) {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), _portal.getClassNameId(Layout.class),
					layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(segmentsExperienceId));

		LayoutStructureItem mainLayoutStructureItem =
			layoutStructure.getMainLayoutStructureItem();

		List<PageElement> mainPageElements = new ArrayList<>();

		for (String childItemId :
				mainLayoutStructureItem.getChildrenItemIds()) {

			mainPageElements.add(
				toDTO(
					layout.getGroupId(), layoutStructure,
					layoutStructure.getLayoutStructureItem(childItemId),
					saveInlineContent, saveMappingConfiguration,
					segmentsExperienceId));
		}

		PageElement pageElement = toDTO(
			layout.getGroupId(), mainLayoutStructureItem, saveInlineContent,
			saveMappingConfiguration);

		if (!mainPageElements.isEmpty()) {
			pageElement.setPageElements(
				mainPageElements.toArray(new PageElement[0]));
		}

		return pageElement;
	}

	public PageElement toDTO(
		long groupId, LayoutStructure layoutStructure,
		LayoutStructureItem layoutStructureItem, boolean saveInlineContent,
		boolean saveMappingConfiguration, long segmentsExperienceId) {

		List<PageElement> pageElements = new ArrayList<>();

		List<String> childrenItemIds = layoutStructureItem.getChildrenItemIds();

		for (String childItemId : childrenItemIds) {
			LayoutStructureItem childLayoutStructureItem =
				layoutStructure.getLayoutStructureItem(childItemId);

			List<String> grandChildrenItemIds =
				childLayoutStructureItem.getChildrenItemIds();

			if (grandChildrenItemIds.isEmpty()) {
				pageElements.add(
					toDTO(
						groupId, childLayoutStructureItem, saveInlineContent,
						saveMappingConfiguration));
			}
			else {
				pageElements.add(
					toDTO(
						groupId, layoutStructure, childLayoutStructureItem,
						saveInlineContent, saveMappingConfiguration,
						segmentsExperienceId));
			}
		}

		PageElement pageElement = toDTO(
			groupId, layoutStructureItem, saveInlineContent,
			saveMappingConfiguration);

		if (!pageElements.isEmpty()) {
			pageElement.setPageElements(
				pageElements.toArray(new PageElement[0]));
		}

		return pageElement;
	}

	public PageElement toDTO(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		Class<?> clazz = layoutStructureItem.getClass();

		LayoutStructureItemExporter layoutStructureItemExporter =
			_layoutStructureItemExporterTracker.getLayoutStructureItemExporter(
				clazz.getName());

		if (layoutStructureItemExporter == null) {
			return null;
		}

		return layoutStructureItemExporter.getPageElement(
			groupId, layoutStructureItem, saveInlineContent,
			saveMappingConfiguration);
	}

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutStructureItemExporterTracker
		_layoutStructureItemExporterTracker;

	@Reference
	private Portal _portal;

}