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

package com.liferay.depot.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.NavigationCard;

/**
 * @author Adolfo Pérez
 */
public class DepotDashboardApplicationNavigationCard implements NavigationCard {

	public DepotDashboardApplicationNavigationCard(
		String href, String icon, Boolean small, String title) {

		_href = href;
		_icon = icon;
		_small = small;
		_title = title;
	}

	@Override
	public String getHref() {
		return _href;
	}

	@Override
	public String getIcon() {
		return _icon;
	}

	@Override
	public String getTitle() {
		return _title;
	}

	@Override
	public Boolean isSmall() {
		return _small;
	}

	private final String _href;
	private final String _icon;
	private final Boolean _small;
	private final String _title;

}