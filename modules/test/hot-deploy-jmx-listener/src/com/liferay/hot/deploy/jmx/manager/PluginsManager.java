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

package com.liferay.hot.deploy.jmx.manager;

import com.liferay.hot.deploy.jmx.statistics.PluginStatistics;

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
		"object-name=com.liferay.portal.monitoring:classification=plugin_statistics,name=PluginsManager",
		"object-name-cache-key=PluginsManager"
	},
	service = DynamicMBean.class
)
public class PluginsManager extends StandardMBean
	implements PluginsManagerMBean {

	public PluginsManager() throws NotCompliantMBeanException {
		super(PluginsManagerMBean.class);
	}

	@Override
	public List<String> listLegacyPlugins() {
		return _pluginStatistics.getDeployedLegacyPlugins();
	}

	@Reference
	protected void setPluginStatistics(PluginStatistics pluginStatistics) {
		_pluginStatistics = pluginStatistics;
	}

	private PluginStatistics _pluginStatistics;

}