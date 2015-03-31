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

package com.liferay.hot.deploy.jmx.statistics;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;

/**
 * @author Cristina González
 */
@Component(
	immediate = true, service = PluginStatisticsManager.class
)
public class PluginStatisticsManagerImpl implements PluginStatisticsManager {

	@Override
	public List<String> getRegisteredLegacyPlugins() {
		return Collections.unmodifiableList(_servletContextNames);
	}

	@Override
	public void registerLegacyPlugin(String servletContextName) {
		_servletContextNames.add(servletContextName);
	}

	@Override
	public void unregisterLegacyPlugin(String servletContextName) {
		_servletContextNames.remove(servletContextName);
	}

	private final List<String> _servletContextNames =
		new CopyOnWriteArrayList<>();

}