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

package com.liferay.portal.deploy.auto;

import com.liferay.portal.kernel.deploy.auto.AutoDeployer;
import com.liferay.portal.kernel.deploy.auto.BaseAutoDeployListener;

import java.io.File;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of Judson (7.1.x)
 */
@Deprecated
public class ExtAutoDeployListener extends BaseAutoDeployListener {

	@Override
	protected AutoDeployer buildAutoDeployer() {
		return new ThreadSafeAutoDeployer(new ExtAutoDeployer());
	}

	@Override
	protected String getPluginPathInfoMessage(File file) {
		return "Copying extension environment plugin for " + file.getPath();
	}

	@Override
	protected String getSuccessMessage(File file) {
		return "Extension environment for " + file.getPath() +
			" copied successfully";
	}

	@Override
	protected boolean isDeployable(File file) {
		PluginAutoDeployListenerHelper pluginAutoDeployListenerHelper =
			new PluginAutoDeployListenerHelper(file);

		return pluginAutoDeployListenerHelper.isExtPlugin();
	}

}