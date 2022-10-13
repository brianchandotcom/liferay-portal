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

package com.liferay.commerce.media.internal.configuration.definition;

import com.liferay.commerce.media.constants.CommerceMediaConstants;
import com.liferay.commerce.media.internal.configuration.CommerceMediaDefaultImageConfiguration;
import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alec Sloan
 */
@Component(service = ConfigurationPidMapping.class)
public class CommerceMediaDefaultImageConfigurationPidMapping
	implements ConfigurationPidMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return CommerceMediaDefaultImageConfiguration.class;
	}

	@Override
	public String getConfigurationPid() {
		return CommerceMediaConstants.SERVICE_NAME;
	}

}