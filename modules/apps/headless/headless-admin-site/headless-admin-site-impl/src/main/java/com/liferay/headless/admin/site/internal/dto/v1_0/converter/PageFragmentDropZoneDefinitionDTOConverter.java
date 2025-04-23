/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.headless.admin.site.dto.v1_0.PageDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageFragmentDropZoneDefinition;
import com.liferay.layout.util.structure.FragmentDropZoneLayoutStructureItem;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "dto.class.name=com.liferay.layout.util.structure.FragmentDropZoneLayoutStructureItem",
	service = DTOConverter.class
)
public class PageFragmentDropZoneDefinitionDTOConverter
	implements DTOConverter
		<FragmentDropZoneLayoutStructureItem, PageFragmentDropZoneDefinition> {

	@Override
	public String getContentType() {
		return PageFragmentDropZoneDefinition.class.getSimpleName();
	}

	@Override
	public PageFragmentDropZoneDefinition toDTO(
			DTOConverterContext dtoConverterContext,
			FragmentDropZoneLayoutStructureItem
				fragmentDropZoneLayoutStructureItem)
		throws Exception {

		return new PageFragmentDropZoneDefinition() {
			{
				setFragmentDropZoneId(
					fragmentDropZoneLayoutStructureItem::getFragmentDropZoneId);
				setType(PageDefinition.Type.FRAGMENT_DROP_ZONE);
			}
		};
	}

}