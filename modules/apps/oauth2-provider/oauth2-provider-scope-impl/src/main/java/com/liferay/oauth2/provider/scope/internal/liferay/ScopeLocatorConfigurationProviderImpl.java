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

package com.liferay.oauth2.provider.scope.internal.liferay;

import com.liferay.oauth2.provider.scope.internal.configuration.ScopeLocatorConfiguration;
import com.liferay.oauth2.provider.scope.internal.liferay.ScopeLocatorImpl.ScopeLocatorConfigurationProvider;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * @author Stian Sigvartsen
 */
@Component(
	configurationPid = "com.liferay.oauth2.provider.scope.internal.configuration.ScopeLocatorConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	service = ScopeLocatorConfigurationProvider.class
)
public class ScopeLocatorConfigurationProviderImpl
	implements ScopeLocatorConfigurationProvider {

	public ScopeLocatorConfiguration getScopeLocatorConfiguration() {
		return _scopeLocatorConfiguration;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_scopeLocatorConfiguration = ConfigurableUtil.createConfigurable(
			ScopeLocatorConfiguration.class, properties);
	}

	private ScopeLocatorConfiguration _scopeLocatorConfiguration;

}