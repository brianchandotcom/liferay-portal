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

package com.liferay.portal.deploy.sandbox;

import com.liferay.portal.kernel.deploy.Deployer;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.ant.CopyTask;

import java.io.File;

/**
 * @author Igor Spasic
 * @author Brian Wing Shun Chan
 */
public class ThemeSandboxHandler extends BaseSandboxHandler {

	public ThemeSandboxHandler(Deployer deployer) {
		super(deployer);
	}

	@Override
	protected void clonePlugin(File dir, PluginPackage pluginPackage)
		throws Exception {

		String portalWebDir = PortalUtil.getPortalWebDir();

		CopyTask.copyDirectory(
			new File(portalWebDir, "/classic"), dir, null, "/_diffs/**", true,
			true);
	}

	@Override
	protected String getPluginType() {
		return _PLUGIN_TYPE;
	}

	private static final String _PLUGIN_TYPE = "theme";

}