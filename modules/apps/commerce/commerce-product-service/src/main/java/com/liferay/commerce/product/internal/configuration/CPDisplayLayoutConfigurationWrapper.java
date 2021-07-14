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

package com.liferay.commerce.product.internal.configuration;

import com.liferay.commerce.product.configuration.CPDisplayLayoutConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Alec Sloan
 */
@Component(
	configurationPid = "com.liferay.commerce.product.configuration.CPDisplayLayoutConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, enabled = false,
	immediate = true, service = CPDisplayLayoutConfigurationWrapper.class
)
public class CPDisplayLayoutConfigurationWrapper {

	public String getAssetCategoryLayoutUuid() {
		return _cpDisplayLayoutConfiguration.assetCategoryLayoutUuid();
	}

	public String getProuctLayoutUuid() {
		return _cpDisplayLayoutConfiguration.productLayoutUuid();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_cpDisplayLayoutConfiguration = ConfigurableUtil.createConfigurable(
			CPDisplayLayoutConfiguration.class, properties);
	}

	private volatile CPDisplayLayoutConfiguration _cpDisplayLayoutConfiguration;

}