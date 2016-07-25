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

package com.liferay.frontend.js.spa.web.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Bruno Basto
 */
@Component(
	configurationPid = "com.liferay.frontend.js.spa.web.configuration.SPAConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {
		"cacheExpirationTime=-1", "requestTimeout=0",
		"userNotificationTimeout=30"
	},
	service = SPAConfigurationActivator.class
)
public class SPAConfigurationActivator {

	public SPAConfiguration getSPAConfiguration() {
		return _spaConfiguration;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_spaConfiguration = ConfigurableUtil.createConfigurable(
			SPAConfiguration.class, properties);
	}

	@Deactivate
	protected void deactivate() {
		_spaConfiguration = null;
	}

	private volatile SPAConfiguration _spaConfiguration;

}