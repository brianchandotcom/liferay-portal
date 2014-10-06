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

package com.liferay.typeclasses;

import aQute.bnd.annotation.metatype.Configurable;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

import java.util.Map;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	immediate = true,
	configurationPid = "com.liferay.managedservice"
)
public class LiferayServiceImpl implements LiferayService {

	private LiferayServiceConfiguration _configuration;

	@Activate
	public void activate(Map<String, Object> properties) {
		_configuration = Configurable.createConfigurable(
			LiferayServiceConfiguration.class, properties);
	}
	
	@Modified
	public void modified(Map<String, Object> properties) {
		System.out.println("MODIFIED: " + properties);

		_configuration = Configurable.createConfigurable(
			LiferayServiceConfiguration.class, properties);
	}

	@Override
	public String toString() {
		return "LiferayServiceImpl{" +
			"_configuration=" + _configuration.getCompany() +
			'}';
	}
}
