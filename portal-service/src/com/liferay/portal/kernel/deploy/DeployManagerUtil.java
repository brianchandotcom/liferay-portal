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
 * @author Ryan Park
 */
public class DeployManagerUtil {

	public static void deploy(File source) throws Exception {
		getDeployManager().deploy(source);
	}

	public static void deploy(File source, String context) throws Exception {
		getDeployManager().deploy(source, context);
	}

	public static String getDeployDir() throws Exception {
		return getDeployManager().getDeployDir();
	}

	public static DeployManager getDeployManager() {
		return _deployManager;
	}

	public static List<PluginPackage> getInstalledPlugins()
		throws PortalException, SystemException {

		return getDeployManager().getInstalledPlugins();
	}

	public static boolean isExtPlugin(File file) {
		return getDeployManager().isExtPlugin(file);
	}

	public static boolean isHookPlugin(File file) throws AutoDeployException {
		return getDeployManager().isHookPlugin(file);
	}

	public static boolean isLayoutTemplatePlugin(File file)
		throws AutoDeployException {

		return getDeployManager().isLayoutTemplatePlugin(file);
	}

	public static boolean isMatchingFile(File file, String checkXmlFile)
		throws AutoDeployException {

		return getDeployManager().isMatchingFile(file, checkXmlFile);
	}

	public static boolean isMatchingFileExtension(File file) {
		return getDeployManager().isMatchingFileExtension(file);
	}

	public static boolean isMvcPortletPlugin(File file)
		throws AutoDeployException {

		return getDeployManager().isMvcPortletPlugin(file);
	}

	public static boolean isPhpPortletPlugin(File file)
		throws AutoDeployException {

		return getDeployManager().isPhpPortletPlugin(file);
	}

	public static boolean isPortletPlugin(File file)
		throws AutoDeployException {

		return getDeployManager().isPortletPlugin(file);
	}

	public static boolean isWaiPortletPlugin(File file)
		throws AutoDeployException {

		return getDeployManager().isWaiPortletPlugin(file);
	}

	public static boolean isThemePlugin(File file) throws AutoDeployException {
		return getDeployManager().isThemePlugin(file);
	}

	public static boolean isWebPlugin(File file) throws AutoDeployException {
		return getDeployManager().isWebPlugin(file);
	}

	public static void redeploy(String context) throws Exception {
		getDeployManager().redeploy(context);
	}

	public static void undeploy(String context) throws Exception {
		getDeployManager().undeploy(context);
	}

	public void setDeployManager(DeployManager deployManager) {
		_deployManager = deployManager;
	}

	private static DeployManager _deployManager;

}