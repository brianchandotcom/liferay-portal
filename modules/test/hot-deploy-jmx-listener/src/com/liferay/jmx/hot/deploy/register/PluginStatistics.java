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

package com.liferay.jmx.hot.deploy.register;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Cristina González
 */
@Component(
	immediate = true, service = PluginStatistics.class
)
public class PluginStatistics {

	public void addDeployedLegacyPlugins(String deployedLegacyPlugin) {
		getDeployedLegacyPlugins().add(deployedLegacyPlugin);
	}

	public List<String> getDeployedLegacyPlugins() {
		if (deployedLegacyPlugins == null) {
			deployedLegacyPlugins = new ArrayList<>();
		}

		return deployedLegacyPlugins;
	}

	public void removeDeployedLegacyPlugins(String deployedLegacyPlugin) {
		getDeployedLegacyPlugins().remove(deployedLegacyPlugin);
	}

	private List<String> deployedLegacyPlugins;

}