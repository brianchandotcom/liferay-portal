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

package com.liferay.portal.configuration.upgrade.util;

/**
 * @author Drew Brokke
 */
public class PropertiesToConfigurationUpgradeKey {

	public PropertiesToConfigurationUpgradeKey(
		String propertyKey, String configurationMethodName) {

		this(propertyKey, configurationMethodName, PropertyDataType.STRING);
	}

	public PropertiesToConfigurationUpgradeKey(
		String propertyKey, String configurationMethodName,
		PropertyDataType type) {

		_propertyKey = propertyKey;
		_configurationMethodName = configurationMethodName;
		_type = type;
	}

	public String getConfigurationMethodName() {
		return _configurationMethodName;
	}

	public String getPropertyKey() {
		return _propertyKey;
	}

	public PropertyDataType getType() {
		return _type;
	}

	private final String _configurationMethodName;
	private final String _propertyKey;
	private final PropertyDataType _type;

}