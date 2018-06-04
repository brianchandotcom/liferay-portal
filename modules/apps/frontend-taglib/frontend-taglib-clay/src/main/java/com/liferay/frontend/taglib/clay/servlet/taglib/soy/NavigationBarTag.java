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

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.base.BaseClayTag;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;

import java.util.List;

/**
 * @author Chema Balsas
 */
public class NavigationBarTag extends BaseClayTag {

	@Override
	public int doStartTag() {
		setComponentBaseName("ClayNavigationBar");
		setHydrate(true);
		setModuleBaseName("navigation-bar");

		return super.doStartTag();
	}

	public void setInverted(Boolean inverted) {
		putValue("inverted", inverted);
	}

	public void setNavigationItems(List<NavigationItem> navigationItems) {
		putValue("items", navigationItems);
	}

}