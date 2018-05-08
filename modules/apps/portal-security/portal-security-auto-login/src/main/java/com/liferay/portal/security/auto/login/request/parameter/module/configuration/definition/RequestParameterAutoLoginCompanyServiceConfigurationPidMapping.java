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

package com.liferay.portal.security.auto.login.request.parameter.module.configuration.definition;

import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;
import com.liferay.portal.security.auto.login.internal.request.parameter.constants.RequestParameterAutoLoginConstants;
import com.liferay.portal.security.auto.login.request.parameter.module.configuration.RequestParameterAutoLoginConfiguration;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tomas Polesovsky
 */
@Component
public class RequestParameterAutoLoginCompanyServiceConfigurationPidMapping
	implements ConfigurationPidMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return RequestParameterAutoLoginConfiguration.class;
	}

	@Override
	public String getConfigurationPid() {
		return RequestParameterAutoLoginConstants.SERVICE_NAME;
	}

}