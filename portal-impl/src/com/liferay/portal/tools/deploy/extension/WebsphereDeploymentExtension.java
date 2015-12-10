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

package com.liferay.portal.tools.deploy.extension;

import com.liferay.portal.deploy.DeployUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.deploy.BaseDeployer;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class WebsphereDeploymentExtension implements DeploymentExtension {

	@Override
	public void copyXmls(BaseDeployer baseDeployer, File srcFile)
		throws Exception {

		baseDeployer.copyDependencyXml("ibm-web-ext.xmi", srcFile + "/WEB-INF");
	}

	@Override
	public String getServerId() {
		return ServerDetector.WEBSPHERE_ID;
	}

	@Override
	public void postDeploy(String destDir, String deployDir) throws Exception {
		if (Validator.isNull(
				PropsValues.AUTO_DEPLOY_WEBSPHERE_WSADMIN_APP_MANAGER_QUERY)) {

			if (_log.isInfoEnabled()) {
				StringBundler sb = new StringBundler();

				sb.append("Do not install the plugin with wsadmin since the ");
				sb.append("property \"");
				sb.append(
					PropsKeys.AUTO_DEPLOY_WEBSPHERE_WSADMIN_APP_MANAGER_QUERY);
				sb.append("\"is not configured");

				_log.info(sb.toString());
			}

			return;
		}

		String wsadminContent = FileUtil.read(
			DeployUtil.getResourcePath("wsadmin.py"));

		String adminAppListOptions =
			PropsValues.AUTO_DEPLOY_WEBSPHERE_WSADMIN_APP_MANAGER_LIST_OPTIONS;

		if (Validator.isNotNull(adminAppListOptions)) {
			adminAppListOptions =
				StringPool.APOSTROPHE + adminAppListOptions +
					StringPool.APOSTROPHE;
		}

		wsadminContent = StringUtil.replace(
			wsadminContent,
			new String[] {
				"${auto.deploy.websphere.wsadmin.app.manager.install.options}",
				"${auto.deploy.websphere.wsadmin.app.manager.list.options}",
				"${auto.deploy.websphere.wsadmin.app.manager.query}",
				"${auto.deploy.websphere.wsadmin.app.manager.update.options}"
			},
			new String[] {
				PropsValues.
					AUTO_DEPLOY_WEBSPHERE_WSADMIN_APP_MANAGER_INSTALL_OPTIONS,
				adminAppListOptions,
				PropsValues.AUTO_DEPLOY_WEBSPHERE_WSADMIN_APP_MANAGER_QUERY,
				PropsValues.
					AUTO_DEPLOY_WEBSPHERE_WSADMIN_APP_MANAGER_UPDATE_OPTIONS
			});

		String pluginServletContextName = deployDir.substring(
			0, deployDir.length() - 4);

		String pluginApplicationName = pluginServletContextName;

		if (Validator.isNotNull(
				PropsValues.AUTO_DEPLOY_WEBSPHERE_WSADMIN_APP_NAME_SUFFIX)) {

			pluginApplicationName +=
				PropsValues.AUTO_DEPLOY_WEBSPHERE_WSADMIN_APP_NAME_SUFFIX;
		}

		wsadminContent = StringUtil.replace(
			wsadminContent,
			new String[] {
				"${auto.deploy.dest.dir}",
				"${auto.deploy.websphere.wsadmin.app.name}",
				"${plugin.servlet.context.name}"
			},
			new String[] {
				destDir, pluginApplicationName, pluginServletContextName
			});

		String wsadminFileName = FileUtil.createTempFileName("py");

		FileUtil.write(wsadminFileName, wsadminContent);

		String webSphereHome = System.getenv("WAS_HOME");

		List<String> commands = new ArrayList<>();

		if (OSDetector.isWindows()) {
			commands.add(webSphereHome + "\\bin\\wsadmin.bat");
		}
		else {
			commands.add(webSphereHome + "/bin/wsadmin.sh");
		}

		if (Validator.isNotNull(
				PropsValues.AUTO_DEPLOY_WEBSPHERE_WSADMIN_PROPERTIES_FILE)) {

			commands.add("-p");
			commands.add(
				PropsValues.AUTO_DEPLOY_WEBSPHERE_WSADMIN_PROPERTIES_FILE);
		}

		commands.add("-f");
		commands.add(wsadminFileName);

		if (_log.isInfoEnabled()) {
			StringBundler sb = new StringBundler(commands.size() + 1);

			sb.append("Installing plugin by executing");

			for (String command : commands) {
				sb.append(StringPool.SPACE);
				sb.append(command);
			}

			_log.info(sb.toString());
		}

		ProcessBuilder processBuilder = new ProcessBuilder(commands);

		processBuilder.redirectErrorStream(true);

		Process process = processBuilder.start();

		if (_log.isInfoEnabled()) {
			InputStream inputStream = process.getInputStream();

			String output = StringUtil.read(inputStream);

			for (String line : StringUtil.split(output, CharPool.NEW_LINE)) {
				_log.info("Process output: " + line);
			}

			try {
				int exitValue = process.exitValue();

				if (exitValue == 0) {
					_log.info(
						"Successfully executed command with an exit value of " +
							exitValue);
				}
				else {
					_log.info(
						"Unsuccessfully executed command with an exit value " +
							"of " + exitValue);
				}
			}
			catch (IllegalThreadStateException itse) {
				_log.info("Process did not terminate");
			}
		}

		FileUtil.delete(wsadminFileName);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WebsphereDeploymentExtension.class);

}