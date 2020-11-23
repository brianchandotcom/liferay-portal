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

package com.liferay.frontend.taglib.clay.sample.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.NavigationCard;

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