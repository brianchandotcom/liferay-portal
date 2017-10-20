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

package com.liferay.site.navigation.type.controller.impl;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.type.controller.SiteNavigationMenuItemTypeController;

/**
 * @author Pavel Savinov
 */
public abstract class BaseSiteNavigationMenuItemTypeControllerImpl
	implements SiteNavigationMenuItemTypeController {

	@Override
	public String getIcon() {
		return "control-panel";
	}

	@Override
	public String getLabel(SiteNavigationMenuItem siteNavigationMenuItem) {
		UnicodeProperties properties = getTypeSettingsProperties(
			siteNavigationMenuItem);

		return properties.getProperty("label");
	}

	@Override
	public String getType() {
		return StringPool.BLANK;
	}

	protected UnicodeProperties getTypeSettingsProperties(
		SiteNavigationMenuItem siteNavigationMenuItem) {

		UnicodeProperties properties = new UnicodeProperties(true);

		properties.fastLoad(siteNavigationMenuItem.getTypeSettings());

		return properties;
	}

}