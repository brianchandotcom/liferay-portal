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

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;

/**
 * @author Adolfo Pérez
 */
public class DepotDashboardApplicationVerticalCard implements VerticalCard {

	public DepotDashboardApplicationVerticalCard(
		String href, String icon, String title) {

		_href = href;
		_icon = icon;
		_title = title;
	}

	@Override
	public String getElementClasses() {
		return "card-interactive card-interactive-primary card-type-template " +
			"template-card";
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
	public boolean isSelectable() {
		return false;
	}

	private final String _href;
	private final String _icon;
	private final String _title;

}