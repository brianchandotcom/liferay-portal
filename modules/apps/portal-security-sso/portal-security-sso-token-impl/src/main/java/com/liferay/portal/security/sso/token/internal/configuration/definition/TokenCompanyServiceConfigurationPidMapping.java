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

package com.liferay.portal.security.sso.token.internal.configuration.definition;

import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;
import com.liferay.portal.security.sso.token.configuration.TokenConfiguration;
import com.liferay.portal.security.sso.token.constants.TokenConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mika Koivisto
 */
@Component(service = ConfigurationPidMapping.class)
public class TokenCompanyServiceConfigurationPidMapping
	implements ConfigurationPidMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return TokenConfiguration.class;
	}

	@Override
	public String getConfigurationPid() {
		return TokenConstants.SERVICE_NAME;
	}

}