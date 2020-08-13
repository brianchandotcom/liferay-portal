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

package com.liferay.portal.security.sso.facebook.connect.internal.configuration.definition;

import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;
import com.liferay.portal.security.sso.facebook.connect.configuration.FacebookConnectConfiguration;
import com.liferay.portal.security.sso.facebook.connect.constants.FacebookConnectConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mika Koivisto
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Component(service = ConfigurationPidMapping.class)
@Deprecated
public class FacebookConnectCompanyServiceConfigurationPidMapping
	implements ConfigurationPidMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return FacebookConnectConfiguration.class;
	}

	@Override
	public String getConfigurationPid() {
		return FacebookConnectConstants.SERVICE_NAME;
	}

}