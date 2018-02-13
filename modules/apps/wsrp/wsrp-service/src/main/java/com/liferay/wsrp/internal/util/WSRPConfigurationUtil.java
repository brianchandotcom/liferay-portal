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

package com.liferay.wsrp.internal.util;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.wsrp.configuration.WSRPGroupServiceConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Peter Fellwock
 */
@Component(
	configurationPid = "com.liferay.wsrp.web.configuration.WSRPConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = WSRPConfigurationUtil.class
)
public class WSRPConfigurationUtil {

	public static WSRPGroupServiceConfiguration getWSRPConfiguration() {
		return _getInstance()._wsrpGroupServiceConfiguration;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_wsrpGroupServiceConfiguration = ConfigurableUtil.createConfigurable(
			WSRPGroupServiceConfiguration.class, properties);
	}

	private static WSRPConfigurationUtil _getInstance() {
		return _instance;
	}

	private static final WSRPConfigurationUtil _instance =
		new WSRPConfigurationUtil();

	private static volatile WSRPGroupServiceConfiguration
		_wsrpGroupServiceConfiguration;

}