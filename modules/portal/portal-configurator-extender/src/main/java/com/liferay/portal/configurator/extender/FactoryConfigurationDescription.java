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

package com.liferay.portal.configurator.extender;

import java.util.Dictionary;

/**
* @author Carlos Sierra Andrés
*/
public final class FactoryConfigurationDescription
	extends ConfigurationDescription {

	public FactoryConfigurationDescription(
		String factoryPid, String pid,
		Dictionary<String, Object> properties) {

		_factoryPid = factoryPid;
		_pid = pid;
		_properties = properties;
	}

	public String getFactoryPid() {
		return _factoryPid;
	}

	public String getPid() {
		return _pid;
	}

	public Dictionary<String, Object> getProperties() {
		return _properties;
	}

	@Override
	public String toString() {
		return "FactoryConfigurationDescription {factoryPid='" +
			_factoryPid + "\', pid='" + _pid + '\'' + '}';
	}

	private final String _factoryPid;
	private final String _pid;
	private final Dictionary<String, Object> _properties;

}