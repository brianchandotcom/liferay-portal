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

package com.liferay.frontend.js.a11y.web.internal.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Matuzalem Teles
 */
@Component(
	configurationPid = "com.liferay.frontend.js.a11y.web.internal.configuration.FFA11yConfiguration",
	immediate = true, service = FFA11yConfigurationUtil.class
)
public class FFA11yConfigurationUtil {

	public static boolean getEnable() {
		return _ffA11yConfiguration.enable();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ffA11yConfiguration = ConfigurableUtil.createConfigurable(
			FFA11yConfiguration.class, properties);
	}

	private static volatile FFA11yConfiguration _ffA11yConfiguration;

}