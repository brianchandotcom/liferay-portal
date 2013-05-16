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

package com.liferay.portal.deploy.auto;

import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.tools.deploy.BaseDeployer;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * @author Miguel Pastor
 */
public class ModuleAutoDeployer extends BaseDeployer {

	@Override
	public int deployFile(AutoDeploymentContext autoDeploymentContext)
		throws Exception {

		copyToModuleFolder(autoDeploymentContext);

		return AutoDeployer.CODE_DEFAULT;
	}

	protected void copyToModuleFolder(
			AutoDeploymentContext autoDeploymentContext)
		throws AutoDeployException {

		String destDir = PropsValues.MODULE_FRAMEWORK_AUTO_DEPLOY_DIRS[0];

		if (!FileUtil.exists(destDir)) {
			FileUtil.mkdirs(destDir);
		}

		try {
			FileUtils.copyFileToDirectory(
				autoDeploymentContext.getFile(), new File(destDir));
		}
		catch (IOException ioe) {
			throw new AutoDeployException(ioe);
		}
	}

}