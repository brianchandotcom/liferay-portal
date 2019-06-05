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

package com.liferay.frontend.taglib.clay.servlet.taglib.soy;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;

import java.util.List;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public interface BaseClayCard {

	public default List<DropdownItem> getActionDropdownItems() {
		return null;
	}

	public default String getAspectRatioCssClasses() {
		return null;
	}

	public default String getComponentId() {
		return null;
	}

	public default Map<String, String> getData() {
		return null;
	}

	public default String getDefaultEventHandler() {
		return null;
	}

	public default String getElementClasses() {
		return null;
	}

	public default String getGroupName() {
		return null;
	}

	public default String getHref() {
		return null;
	}

	public default String getId() {
		return null;
	}

	public default String getInputName() {
		return null;
	}

	public default String getInputValue() {
		return null;
	}

	public default String getSpritemap() {
		return null;
	}

	public default boolean isDisabled() {
		return false;
	}

	public default boolean isSelectable() {
		return true;
	}

	public default boolean isSelected() {
		return false;
	}

}