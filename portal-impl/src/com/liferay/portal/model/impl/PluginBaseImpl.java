/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.model.Plugin;
import com.liferay.portal.kernel.model.PluginSetting;
import com.liferay.portal.kernel.plugin.PluginPackage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jorge Ferrer
 */
public abstract class PluginBaseImpl implements Plugin {

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
	public PluginPackage getPluginPackage() {
		return _pluginPackage;
	}

	@Override
	public void setDefaultPluginSetting(PluginSetting pluginSetting) {
		_defaultPluginSetting = pluginSetting;
	}

	@Override
	public void setPluginPackage(PluginPackage pluginPackage) {
		_pluginPackage = pluginPackage;
	}

	private PluginSetting _defaultPluginSetting;
	private final Map<Long, PluginSetting> _defaultPluginSettings =
		new HashMap<>();
	private PluginPackage _pluginPackage;

}