/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;

import java.util.Collections;
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

	public default String getCssClass() {
		return null;
	}

	public default Map<String, String> getData() {
		return null;
	}

	public default String getDefaultEventHandler() {
		return null;
	}

	public default Map<String, String> getDynamicAttributes() {
		return Collections.emptyMap();
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