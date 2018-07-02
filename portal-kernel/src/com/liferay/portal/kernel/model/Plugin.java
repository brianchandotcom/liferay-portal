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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.plugin.PluginPackage;

/**
 * @author Jorge Ferrer
 */
public interface Plugin {

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	public static final String TYPE_EXT = "ext";

	public static final String TYPE_HOOK = "hook";

	public static final String TYPE_LAYOUT_TEMPLATE = "layouttpl";

	public static final String TYPE_PORTLET = "portlet";

	public static final String TYPE_THEME = "theme";

	public static final String TYPE_WEB = "web";

	public PluginSetting getDefaultPluginSetting();

	public PluginSetting getDefaultPluginSetting(long companyId);

	public String getPluginId();

	public PluginPackage getPluginPackage();

	public String getPluginType();

	public void setDefaultPluginSetting(PluginSetting pluginSetting);

	public void setPluginPackage(PluginPackage pluginPackage);

}