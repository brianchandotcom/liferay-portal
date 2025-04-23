/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.headless.admin.site.dto.v1_0.ColumnPageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageElementDefinition;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "dto.class.name=com.liferay.layout.util.structure.ColumnLayoutStructureItem",
	service = DTOConverter.class
)
public class ColumnPageElementDefinitionDTOConverter
	implements DTOConverter
		<ColumnLayoutStructureItem, ColumnPageElementDefinition> {

	@Override
	public String getContentType() {
		return ColumnPageElementDefinition.class.getSimpleName();
	}

	@Override
	public ColumnPageElementDefinition toDTO(
			DTOConverterContext dtoConverterContext,
			ColumnLayoutStructureItem columnLayoutStructureItem)
		throws Exception {

		return new ColumnPageElementDefinition() {
			{
				setSize(columnLayoutStructureItem::getSize);
				setType(PageElementDefinition.Type.COLUMN);
			}
		};
	}

}