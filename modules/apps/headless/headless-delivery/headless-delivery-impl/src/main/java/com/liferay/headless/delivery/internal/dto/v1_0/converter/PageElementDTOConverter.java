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

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.internal.dto.v1_0.exporter.LayoutStructureItemExporter;
import com.liferay.headless.delivery.internal.dto.v1_0.exporter.LayoutStructureItemExporterTracker;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.model.Layout;

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
		Layout layout, String layoutStructureItemId, boolean saveInlineContent,
		boolean saveMappingConfiguration, long segmentsExperienceId) {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(segmentsExperienceId));

		return _toPageElement(
			layout.getGroupId(), layoutStructure,
			layoutStructure.getLayoutStructureItem(layoutStructureItemId),
			saveInlineContent, saveMappingConfiguration);
	}

	private PageElement _toPageElement(
		long groupId, LayoutStructure layoutStructure,
		LayoutStructureItem layoutStructureItem, boolean saveInlineContent,
		boolean saveMappingConfiguration) {

		List<PageElement> pageElements = new ArrayList<>();

		List<String> childrenItemIds = layoutStructureItem.getChildrenItemIds();

		for (String childItemId : childrenItemIds) {
			LayoutStructureItem childLayoutStructureItem =
				layoutStructure.getLayoutStructureItem(childItemId);

			List<String> grandChildrenItemIds =
				childLayoutStructureItem.getChildrenItemIds();

			if (grandChildrenItemIds.isEmpty()) {
				pageElements.add(
					_toPageElement(
						groupId, childLayoutStructureItem, saveInlineContent,
						saveMappingConfiguration));
			}
			else {
				pageElements.add(
					_toPageElement(
						groupId, layoutStructure, childLayoutStructureItem,
						saveInlineContent, saveMappingConfiguration));
			}
		}

		PageElement pageElement = _toPageElement(
			groupId, layoutStructureItem, saveInlineContent,
			saveMappingConfiguration);

		if (!pageElements.isEmpty()) {
			pageElement.setPageElements(
				pageElements.toArray(new PageElement[0]));
		}

		return pageElement;
	}

	private PageElement _toPageElement(
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

}