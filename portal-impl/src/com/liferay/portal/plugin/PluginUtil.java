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

package com.liferay.portal.plugin;

import com.liferay.portal.kernel.model.Plugin;
import com.liferay.portal.kernel.model.PluginSetting;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.PluginSettingLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PluginUtil {

	public static <P extends Plugin> List<P> restrictPlugins(
		List<P> plugins, long companyId, long userId) {

		List<P> visiblePlugins = new ArrayList<>(plugins.size());

		for (P plugin : plugins) {
			PluginSetting pluginSetting =
				PluginSettingLocalServiceUtil.getPluginSetting(
					companyId, plugin.getPluginId(), plugin.getPluginType());

			if (pluginSetting.isActive() &&
				pluginSetting.hasPermission(userId)) {

				visiblePlugins.add(plugin);
			}
		}

		return visiblePlugins;
	}

	public static <P extends Plugin> List<P> restrictPlugins(
		List<P> plugins, User user) {

		return restrictPlugins(plugins, user.getCompanyId(), user.getUserId());
	}

}