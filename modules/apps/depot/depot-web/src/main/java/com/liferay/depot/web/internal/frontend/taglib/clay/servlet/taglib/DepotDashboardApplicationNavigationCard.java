/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.servlet.taglib.NavigationCard;

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