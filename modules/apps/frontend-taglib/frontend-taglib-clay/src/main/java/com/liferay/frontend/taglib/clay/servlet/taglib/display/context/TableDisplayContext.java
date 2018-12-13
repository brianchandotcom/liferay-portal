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

package com.liferay.frontend.taglib.clay.servlet.taglib.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.table.Schema;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.table.Size;

import java.util.Collection;

/**
 * @author Iván Zaera Avellón
 */
public interface TableDisplayContext {

	public default String getActionsMenuVariant() {
		return null;
	}

	public default Collection<String> getDependencies() {
		return null;
	}

	public default String getElementClasses() {
		return null;
	}

	public default String getId() {
		return null;
	}

	public default Collection<?> getItems() {
		return null;
	}

	public default Schema getSchema() {
		return null;
	}

	public default Size getSize() {
		return null;
	}

	public default String getSpritemap() {
		return null;
	}

	public default String getTableClasses() {
		return null;
	}

	public default Boolean isSelectable() {
		return null;
	}

	public default Boolean isShowActionsMenu() {
		return null;
	}

	public default Boolean isUseDefaultClasses() {
		return null;
	}

	public default Boolean isWrapTable() {
		return null;
	}

}