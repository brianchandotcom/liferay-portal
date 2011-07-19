/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.deploy.auto;

import com.liferay.portal.kernel.deploy.DeployManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 */
public abstract class BaseAutoDeployListener implements AutoDeployListener {

	public boolean isExtPlugin(File file) {
		return DeployManagerUtil.isExtPlugin(file);
	}

	public boolean isHookPlugin(File file) throws AutoDeployException {
		return DeployManagerUtil.isHookPlugin(file);
	}

	public boolean isLayoutTemplatePlugin(File file) throws AutoDeployException {
		return DeployManagerUtil.isLayoutTemplatePlugin(file);
	}

	public boolean isMatchingFile(File file, String checkXmlFile)
		throws AutoDeployException {

		return DeployManagerUtil.isMatchingFile(file, checkXmlFile);
	}

	public boolean isMatchingFileExtension(File file) {
		return DeployManagerUtil.isMatchingFileExtension(file);
	}

	public boolean isThemePlugin(File file) throws AutoDeployException {
		return DeployManagerUtil.isThemePlugin(file);
	}

	public boolean isWebPlugin(File file) throws AutoDeployException {
		return DeployManagerUtil.isWebPlugin(file);
	}

	private static Log _log = LogFactoryUtil.getLog(
		BaseAutoDeployListener.class);

}