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

package com.liferay.portal.reports.engine.console.internal.configuration.definition;

import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;
import com.liferay.portal.reports.engine.console.configuration.ReportsGroupServiceEmailConfiguration;
import com.liferay.portal.reports.engine.console.constants.ReportsEngineConsoleConstants;

import org.osgi.service.component.annotations.Component;

/**
 * @author Prathima Shreenath
 */
@Component(immediate = true, service = ConfigurationPidMapping.class)
public class ReportsGroupServiceConfigurationPidMapping
	implements ConfigurationPidMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return ReportsGroupServiceEmailConfiguration.class;
	}

	@Override
	public String getConfigurationPid() {
		return ReportsEngineConsoleConstants.SERVICE_NAME;
	}

}