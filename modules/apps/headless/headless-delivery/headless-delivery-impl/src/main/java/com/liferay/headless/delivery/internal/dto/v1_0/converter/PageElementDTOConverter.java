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
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.LayoutStructureItemMapperTracker;
import com.liferay.headless.delivery.internal.dto.v1_0.util.PageElementUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 * @author Javier de Arcos
 */
@Component(
	property = "dto.class.name=com.liferay.layout.util.structure.LayoutStructureItem",
	service = {DTOConverter.class, PageElementDTOConverter.class}
)
public class PageElementDTOConverter
	implements DTOConverter<LayoutStructureItem, PageElement> {

	@Override
	public String getContentType() {
		return PageElement.class.getSimpleName();
	}

	@Override
	public PageElement toDTO(
			DTOConverterContext dtoConverterContext,
			LayoutStructureItem layoutStructureItem)
		throws Exception {

		Object groupIdObject = dtoConverterContext.getAttribute("groupId");

		if (groupIdObject == null) {
			throw new IllegalArgumentException(
				"GroupId not defined for layout structure item " +
					layoutStructureItem.getItemId());
		}

		long groupId = GetterUtil.getLong(groupIdObject);

		LayoutStructure layoutStructure = Optional.ofNullable(
			dtoConverterContext.getAttribute("layoutStructure")
		).map(
			LayoutStructure.class::cast
		).orElseThrow(
			() -> new IllegalArgumentException(
				"Layout structure not defined for layout structure item " +
					layoutStructureItem.getItemId())
		);

		boolean saveInlineContent = GetterUtil.getBoolean(
			dtoConverterContext.getAttribute("saveInlineContent"), true);

		boolean saveMappingConfiguration = GetterUtil.getBoolean(
			dtoConverterContext.getAttribute("saveMappingConfiguration"), true);

		return PageElementUtil.toPageElement(
			groupId, layoutStructure, layoutStructureItem,
			_layoutStructureItemMapperTracker, saveInlineContent,
			saveMappingConfiguration);
	}

	@Reference
	private LayoutStructureItemMapperTracker _layoutStructureItemMapperTracker;

}