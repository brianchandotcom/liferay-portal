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

package com.liferay.redirect.internal.configuration;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.redirect.configuration.RedirectPatternConfigurationProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	immediate = true, service = RedirectPatternConfigurationProvider.class
)
public class RedirectPatternConfigurationProviderImpl
	implements RedirectPatternConfigurationProvider {

	public String[] getPatterns(long groupId) throws ConfigurationException {
		RedirectPatternConfiguration redirectPatternConfiguration =
			_configurationProvider.getGroupConfiguration(
				RedirectPatternConfiguration.class, groupId);

		return redirectPatternConfiguration.patterns();
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}