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

package com.liferay.product.navigation.product.menu.web.internal.util;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.product.navigation.product.menu.web.internal.configuration.FFProductMenuWebConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	configurationPid = "com.liferay.product.navigation.product.menu.web.internal.configuration.FFProductMenuWebConfiguration",
	immediate = true, service = {}
)
public class FFProductMenuWebConfigurationUtil {

	public static boolean isSiteNavigationMenuEnabled() {
		return _ffProductMenuWebConfiguration.isSiteNavigationMenuEnabled();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ffProductMenuWebConfiguration = ConfigurableUtil.createConfigurable(
			FFProductMenuWebConfiguration.class, properties);
	}

	private static volatile FFProductMenuWebConfiguration
		_ffProductMenuWebConfiguration;

}