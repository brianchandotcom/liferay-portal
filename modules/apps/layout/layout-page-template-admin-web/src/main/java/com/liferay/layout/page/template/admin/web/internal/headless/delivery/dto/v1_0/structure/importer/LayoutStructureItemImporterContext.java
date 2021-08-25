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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.importer;

import com.liferay.portal.kernel.model.Layout;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutStructureItemImporterContext {

	public LayoutStructureItemImporterContext(
		Layout layout, double pageDefinitionVersion, String parentItemId,
		int position) {

		_layout = layout;
		_pageDefinitionVersion = pageDefinitionVersion;
		_parentItemId = parentItemId;
		_position = position;
	}

	public Layout getLayout() {
		return _layout;
	}

	public double getPageDefinitionVersion() {
		return _pageDefinitionVersion;
	}

	public String getParentItemId() {
		return _parentItemId;
	}

	public int getPosition() {
		return _position;
	}

	private final Layout _layout;
	private final double _pageDefinitionVersion;
	private final String _parentItemId;
	private final int _position;

}