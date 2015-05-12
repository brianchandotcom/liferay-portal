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

package com.liferay.site.navigation.menu.web.settings.internal;

import com.liferay.portal.kernel.settings.definition.SettingsIdMapping;
import com.liferay.site.navigation.menu.web.configuration.NavigationMenuPortletInstanceConfiguration;
import com.liferay.site.navigation.menu.web.constants.NavigationMenuPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Juergen Kappler
 */
@Component
public class NavigationMenuPortletInstanceSettingsIdMapping
	implements SettingsIdMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return NavigationMenuPortletInstanceConfiguration.class;
	}

	@Override
	public String getSettingsId() {
		return NavigationMenuPortletKeys.NAVIGATION;
	}

}