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

	public List<DropdownItem> getActionDropdownItems();

	public String getComponentId();

	public Map<String, String> getData();

	public String getDefaultEventHandler();

	public String getElementClasses();

	public String getGroupName();

	public String getHref();

	public String getId();

	public String getInputName();

	public String getInputValue();

	public String getSpritemap();

	public boolean isDisabled();

	public boolean isSelectable();

	public boolean isSelected();

}