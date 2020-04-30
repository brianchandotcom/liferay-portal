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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure;

import com.liferay.headless.delivery.dto.v1_0.PageColumnDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItem;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemExporter.class)
public class ColumnLayoutStructureItemExporter
	implements LayoutStructureItemExporter {

	@Override
	public String getClassName() {
		return ColumnLayoutStructureItem.class.getName();
	}

	@Override
	public PageElement getPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		ColumnLayoutStructureItem columnLayoutStructureItem =
			(ColumnLayoutStructureItem)layoutStructureItem;

		return new PageElement() {
			{
				definition = new PageColumnDefinition() {
					{
						size = columnLayoutStructureItem.getSize();
					}
				};
				type = PageElement.Type.COLUMN;
			}
		};
	}

}