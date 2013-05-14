/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.model.Plugin;
import com.liferay.portal.model.PluginSetting;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jorge Ferrer
 */
public abstract class PluginBaseImpl implements Plugin {

	@Override
	public PluginPackage getPluginPackage() {
		return _pluginPackage;
	}

	@Override
	public void setPluginPackage(PluginPackage pluginPackage) {
		_pluginPackage = pluginPackage;
	}

	@Override
	public PluginSetting getDefaultPluginSetting() {
		return _defaultPluginSetting;
	}

	@Override
	public PluginSetting getDefaultPluginSetting(long companyId) {
		PluginSetting setting = _defaultPluginSettings.get(companyId);

		if (setting == null) {
			setting = new PluginSettingImpl(_defaultPluginSetting);

			setting.setCompanyId(companyId);

			_defaultPluginSettings.put(companyId, setting);
		}

		return setting;
	}

	@Override
	public void setDefaultPluginSetting(PluginSetting pluginSetting) {
		_defaultPluginSetting = pluginSetting;
	}

	private PluginPackage _pluginPackage;
	private PluginSetting _defaultPluginSetting;
	private Map<Long, PluginSetting> _defaultPluginSettings =
		new HashMap<Long, PluginSetting>();

}