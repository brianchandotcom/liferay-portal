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

package com.liferay.hot.deploy.jmx.listener.mbean.manager;

import com.liferay.hot.deploy.jmx.listener.statistics.PluginStatisticsManager;

import java.util.List;

import javax.management.DynamicMBean;
import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	immediate = true,
	property = {
		"jmx.objectname=com.liferay.portal.monitoring:classification=plugin_statistics,name=PluginsManager",
		"jmx.objectname.cache.key=PluginsManager"
	},
	service = DynamicMBean.class
)
public class PluginManager extends StandardMBean implements PluginMBeanManager {

	public PluginManager() throws NotCompliantMBeanException {
		super(PluginMBeanManager.class);
	}

	@Override
	public List<String> listLegacyPlugins() {
		return _pluginStatisticsManager.getRegisteredLegacyPlugins();
	}

	@Reference
	protected void setPluginStatistics(
		PluginStatisticsManager pluginStatisticsManager) {

		_pluginStatisticsManager = pluginStatisticsManager;
	}

	private PluginStatisticsManager _pluginStatisticsManager;

}