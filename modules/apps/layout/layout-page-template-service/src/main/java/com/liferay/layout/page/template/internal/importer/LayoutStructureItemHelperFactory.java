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

package com.liferay.layout.page.template.internal.importer;

import com.liferay.headless.delivery.dto.v1_0.PageElement;

/**
 * @author Jürgen Kappler
 */
public class LayoutStructureItemHelperFactory {

	public static LayoutStructureItemHelperFactory getInstance() {
		return _layoutStructureItemHelperFactory;
	}

	public LayoutStructureItemHelper getLayoutStructureItemHelper(
		PageElement.Type pageElementType) {

		if (pageElementType == PageElement.Type.SECTION) {
			return new ContainerLayoutStructureItemHelper();
		}

		if (pageElementType == PageElement.Type.ROW) {
			return new RowLayoutStructureItemHelper();
		}

		if (pageElementType == PageElement.Type.COLUMN) {
			return new ColumnLayoutStructureItemHelper();
		}

		return null;
	}

	private LayoutStructureItemHelperFactory() {
	}

	private static final LayoutStructureItemHelperFactory
		_layoutStructureItemHelperFactory =
			new LayoutStructureItemHelperFactory();

}