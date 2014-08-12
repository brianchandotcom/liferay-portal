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

package com.liferay.portal.deploy;

import com.liferay.portal.events.GlobalStartupAction;
import com.liferay.portal.kernel.deploy.DeployManager;
import com.liferay.portal.kernel.deploy.auto.AutoDeployDir;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.plugin.RequiredPluginPackageException;
import com.liferay.portal.kernel.plugin.Version;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.plugin.PluginPackageUtil;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 */
@DoPrivileged
public class DeployManagerImpl implements DeployManager {

	public DeployManagerImpl() {
		for (int i = 1; i < 9; i++) {
			String levelRequiredDeploymentWARFileNamesString = StringPool.BLANK;

			try {
				Class<?> clazz = getClass();

				InputStream inputStream = clazz.getResourceAsStream(
					"dependencies/plugins" + i + "/wars.txt");

				if (inputStream == null) {
					return;
				}

				levelRequiredDeploymentWARFileNamesString = StringUtil.read(
					inputStream);
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Level " + i + " required deployment WAR file names " +
						levelRequiredDeploymentWARFileNamesString);
			}

			String[] levelRequiredDeploymentWARFileNames = StringUtil.split(
				levelRequiredDeploymentWARFileNamesString);

			_levelsRequiredDeploymentWARFileNames.add(
				levelRequiredDeploymentWARFileNames);

			String[] levelRequiredDeploymentContexts =
				new String[levelRequiredDeploymentWARFileNames.length];

			_levelsRequiredDeploymentContexts.add(
				levelRequiredDeploymentContexts);

			for (int j = 0; j < levelRequiredDeploymentWARFileNames.length;
					j++) {

				String warFileName = levelRequiredDeploymentWARFileNames[j];

				Version version = Version.getInstance(ReleaseInfo.getVersion());

				StringBundler sb = new StringBundler(4);

				sb.append(StringPool.DASH);
				sb.append(version.getMajor());
				sb.append(StringPool.PERIOD);
				sb.append(version.getMinor());

				int index = warFileName.indexOf(sb.toString());

				levelRequiredDeploymentContexts[j] = warFileName.substring(
					0, index);
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Level " + i + " required deployment contexts " +
						StringUtil.merge(levelRequiredDeploymentContexts));
			}
		}
	}

	@Override
	public void deploy(AutoDeploymentContext autoDeploymentContext)
		throws Exception {

		AutoDeployDir.deploy(
			autoDeploymentContext,
			GlobalStartupAction.getAutoDeployListeners(false));
	}

	@Override
	public String getDeployDir() throws Exception {
		return DeployUtil.getAutoDeployDestDir();
	}

	@Override
	public String getInstalledDir() throws Exception {
		if (ServerDetector.isGlassfish()) {
			File file = new File(
				System.getProperty("com.sun.aas.instanceRoot"), "autodeploy");

			return file.getAbsolutePath();
		}

		return DeployUtil.getAutoDeployDestDir();
	}

	@Override
	public PluginPackage getInstalledPluginPackage(String context) {
		return PluginPackageUtil.getInstalledPluginPackage(context);
	}

	@Override
	public List<PluginPackage> getInstalledPluginPackages() {
		return PluginPackageUtil.getInstalledPluginPackages();
	}

	@Override
	public List<String[]> getLevelsRequiredDeploymentContexts() {
		return _levelsRequiredDeploymentContexts;
	}

	@Override
	public List<String[]> getLevelsRequiredDeploymentWARFileNames() {
		return _levelsRequiredDeploymentWARFileNames;
	}

	@Override
	public boolean isDeployed(String context) {
		return PluginPackageUtil.isInstalled(context);
	}

	@Override
	public boolean isRequiredDeploymentContext(String context) {
		for (String[] levelsRequiredDeploymentContexts :
				_levelsRequiredDeploymentContexts) {

			if (ArrayUtil.contains(levelsRequiredDeploymentContexts, context)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public PluginPackage readPluginPackageProperties(
		String displayName, Properties properties) {

		return PluginPackageUtil.readPluginPackageProperties(
			displayName, properties);
	}

	@Override
	public PluginPackage readPluginPackageXml(String xml) throws Exception {
		return PluginPackageUtil.readPluginPackageXml(xml);
	}

	@Override
	public void redeploy(String context) throws Exception {
		if (ServerDetector.isJetty()) {
			DeployUtil.redeployJetty(context);
		}
		else if (ServerDetector.isTomcat()) {
			DeployUtil.redeployTomcat(context);
		}
	}

	@Override
	public void undeploy(String context) throws Exception {
		if (isRequiredDeploymentContext(context)) {
			RequiredPluginPackageException rppe =
				new RequiredPluginPackageException();

			rppe.setContext(context);

			throw rppe;
		}

		File deployDir = new File(getDeployDir(), context);

		DeployUtil.undeploy(ServerDetector.getServerId(), deployDir);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeployManagerImpl.class);

	private final List<String[]> _levelsRequiredDeploymentContexts =
		new ArrayList<String[]>();
	private final List<String[]> _levelsRequiredDeploymentWARFileNames =
		new ArrayList<String[]>();

}