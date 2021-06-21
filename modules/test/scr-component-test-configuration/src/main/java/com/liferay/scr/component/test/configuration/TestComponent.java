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

package com.liferay.scr.component.test.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(
	configurationPid = {
		"com.liferay.scr.component.test.configuration.FirstConfiguration",
		"com.liferay.scr.component.test.configuration.SecondConfiguration"
	},
	configurationPolicy = ConfigurationPolicy.OPTIONAL, enabled = false,
	immediate = true, service = TestComponent.class
)
public class TestComponent {

	public String getFirst() {
		return _firstConfiguration.first();
	}

	public String getSecond() {
		return _secondConfiguration.second();
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_firstConfiguration = ConfigurableUtil.createConfigurable(
			FirstConfiguration.class, properties);
		_secondConfiguration = ConfigurableUtil.createConfigurable(
			SecondConfiguration.class, properties);
	}

	private FirstConfiguration _firstConfiguration;
	private SecondConfiguration _secondConfiguration;

}