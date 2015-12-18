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

package com.liferay.gradle.plugins;

import com.liferay.gradle.plugins.wsdd.builder.WSDDBuilderPlugin;

/**
 * @author Andrea Di Giorgi
 */
public class WSDDBuilderDefaultsPlugin
	extends BasePortalToolDefaultsPlugin<WSDDBuilderPlugin> {

	@Override
	protected Class<WSDDBuilderPlugin> getPluginClass() {
		return WSDDBuilderPlugin.class;
	}

	@Override
	protected String getPortalToolConfigurationName() {
		return WSDDBuilderPlugin.CONFIGURATION_NAME;
	}

	@Override
	protected String getPortalToolName() {
		return _PORTAL_TOOL_NAME;
	}

	private static final String _PORTAL_TOOL_NAME =
		"com.liferay.portal.tools.wsdd.builder";

}