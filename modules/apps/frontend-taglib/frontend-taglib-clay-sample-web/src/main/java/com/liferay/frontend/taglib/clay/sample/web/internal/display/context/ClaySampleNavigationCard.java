/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.sample.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.NavigationCard;

/**
 * @author Marko Cikos
 */
public class ClaySampleNavigationCard implements NavigationCard {

	@Override
	public String getCssClass() {
		return "custom-navigation-card-css-class";
	}

	@Override
	public String getDescription() {
		return "choose-a-display-page-template";
	}

	@Override
	public String getHref() {
		return "#custom-navigation-card-href";
	}

	@Override
	public String getId() {
		return "customNavigationCardId";
	}

	@Override
	public String getImageAlt() {
		return "private-page";
	}

	@Override
	public String getImageSrc() {
		return "https://images.unsplash.com/photo-1502290822284-9538ef1f1291";
	}

	@Override
	public String getTitle() {
		return "asset-title";
	}

}