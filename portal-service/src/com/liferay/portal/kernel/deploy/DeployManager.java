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

package com.liferay.portal.kernel.deploy;

import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.plugin.PluginPackage;

import java.io.File;
import java.util.List;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public interface DeployManager {

	public void deploy(File source) throws Exception;

	public void deploy(File source, String context) throws Exception;

	public String getDeployDir() throws Exception;

	public List<PluginPackage> getInstalledPlugins()
		throws PortalException, SystemException;

	public boolean isExtPlugin(File file);

	public boolean isHookPlugin(File file) throws AutoDeployException;

	public boolean isLayoutTemplatePlugin(File file) throws AutoDeployException;

	public boolean isMatchingFile(File file, String checkXmlFile)
		throws AutoDeployException;

	public boolean isMatchingFileExtension(File file);

	public boolean isMvcPortletPlugin(File file) throws AutoDeployException;

	public boolean isPhpPortletPlugin(File file) throws AutoDeployException;

	public boolean isPortletPlugin(File file) throws AutoDeployException;

	public boolean isWaiPortletPlugin(File file) throws AutoDeployException;

	public boolean isThemePlugin(File file) throws AutoDeployException;

	public boolean isWebPlugin(File file) throws AutoDeployException;

	public void redeploy(String context) throws Exception;

	public void undeploy(String context) throws Exception;

}