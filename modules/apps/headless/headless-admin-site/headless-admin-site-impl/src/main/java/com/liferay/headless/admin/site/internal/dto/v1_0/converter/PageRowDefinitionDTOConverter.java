/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.headless.admin.site.dto.v1_0.PageDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageRowDefinition;
import com.liferay.layout.util.structure.RowStyledLayoutStructureItem;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "dto.class.name=com.liferay.layout.util.structure.RowStyledLayoutStructureItem",
	service = DTOConverter.class
)
public class PageRowDefinitionDTOConverter
	implements DTOConverter<RowStyledLayoutStructureItem, PageRowDefinition> {

	@Override
	public String getContentType() {
		return PageRowDefinition.class.getSimpleName();
	}

	@Override
	public PageRowDefinition toDTO(
			DTOConverterContext dtoConverterContext,
			RowStyledLayoutStructureItem rowStyledLayoutStructureItem)
		throws Exception {

		return new PageRowDefinition() {
			{
				setCssClasses(
					() -> {
						if (SetUtil.isEmpty(
								rowStyledLayoutStructureItem.getCssClasses())) {

							return null;
						}

						return ArrayUtil.toStringArray(
							rowStyledLayoutStructureItem.getCssClasses());
					});
				setCustomCSS(rowStyledLayoutStructureItem::getCustomCSS);
				setGutters(rowStyledLayoutStructureItem::isGutters);
				setIndexed(rowStyledLayoutStructureItem::isIndexed);
				setModulesPerRow(
					rowStyledLayoutStructureItem::getModulesPerRow);
				setName(rowStyledLayoutStructureItem::getName);
				setNumberOfColumns(
					rowStyledLayoutStructureItem::getNumberOfColumns);
				setReverseOrder(rowStyledLayoutStructureItem::isReverseOrder);
				setType(PageDefinition.Type.ROW);
			}
		};
	}

}