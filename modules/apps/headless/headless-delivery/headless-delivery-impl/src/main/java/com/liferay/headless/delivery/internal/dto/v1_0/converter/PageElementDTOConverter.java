/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.converter;

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.LayoutStructureItemMapperRegistry;
import com.liferay.headless.delivery.internal.dto.v1_0.util.PageElementUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

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
				"Group ID is not defined for layout structure item " +
					layoutStructureItem.getItemId());
		}

		LayoutStructure layoutStructure =
			(LayoutStructure)dtoConverterContext.getAttribute(
				"layoutStructure");

		if (layoutStructure == null) {
			throw new IllegalArgumentException(
				"Layout structure is not defined for layout structure item " +
					layoutStructureItem.getItemId());
		}

		long groupId = GetterUtil.getLong(groupIdObject);
		boolean saveInlineContent = GetterUtil.getBoolean(
			dtoConverterContext.getAttribute("saveInlineContent"), true);
		boolean saveMappingConfiguration = GetterUtil.getBoolean(
			dtoConverterContext.getAttribute("saveMappingConfiguration"), true);

		return PageElementUtil.toPageElement(
			groupId, layoutStructure, layoutStructureItem,
			_layoutStructureItemMapperRegistry, saveInlineContent,
			saveMappingConfiguration);
	}

	@Reference
	private LayoutStructureItemMapperRegistry
		_layoutStructureItemMapperRegistry;

}