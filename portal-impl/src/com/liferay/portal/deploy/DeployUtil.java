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

package com.liferay.portal.deploy;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.context.PortalContextLoaderListener;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.ant.CopyTask;
import com.liferay.util.ant.DeleteTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.apache.commons.io.FileUtils;

/**
 * @author Brian Wing Shun Chan
 */
public class DeployUtil {

	public static void copyDependencyXml(
			String fileName, String targetDir, String targetFileName,
			Map<String, String> filterMap, boolean overwrite)
		throws Exception {

		File file = new File(getResourcePath(fileName));
		File targetFile = new File(targetDir, targetFileName);

		if (!targetFile.exists()) {
			CopyTask.copyFile(
				file, new File(targetDir), targetFileName, filterMap, overwrite,
				true);
		}
	}

	public static String getAutoDeployDestDir() throws Exception {
		String destDir = PrefsPropsUtil.getString(
			PropsKeys.AUTO_DEPLOY_DEST_DIR, PropsValues.AUTO_DEPLOY_DEST_DIR);

		if (Validator.isNull(destDir)) {
			destDir = getAutoDeployServerDestDir();
		}

		return destDir;
	}

	public static String getAutoDeployServerDestDir() throws Exception {
		String destDir = null;

		String serverId = GetterUtil.getString(ServerDetector.getServerId());

		if (serverId.equals(ServerDetector.JBOSS_ID) &&
			ServerDetector.isJBoss5()) {

			String name = "auto.deploy." + serverId + ".dest.dir";

			PortletPreferences preferences = PrefsPropsUtil.getPreferences();

			String value = PropsUtil.get(name, new Filter("5"));

			destDir = preferences.getValue(name, value);
		}
		else if (serverId.equals(ServerDetector.TOMCAT_ID)) {
			destDir = PrefsPropsUtil.getString(
				PropsKeys.AUTO_DEPLOY_TOMCAT_DEST_DIR,
				PropsValues.AUTO_DEPLOY_TOMCAT_DEST_DIR);
		}
		else {
			destDir = PrefsPropsUtil.getString(
				"auto.deploy." + serverId + ".dest.dir");
		}

		if (Validator.isNull(destDir)) {
			destDir = PrefsPropsUtil.getString(
				PropsKeys.AUTO_DEPLOY_DEFAULT_DEST_DIR,
				PropsValues.AUTO_DEPLOY_DEFAULT_DEST_DIR);
		}

		destDir = StringUtil.replace(
			destDir, CharPool.BACK_SLASH, CharPool.SLASH);

		return destDir;
	}

	public static String getResourcePath(String resource) throws Exception {
		return _instance._getResourcePath(resource);
	}

	public static void redeployJetty(String context) throws Exception {
		String contextsDirName = System.getProperty("jetty.home") + "/contexts";

		if (_isPortalContext(context)) {
			throw new UnsupportedOperationException(
				"This method is meant for redeploying plugins, not the portal");
		}

		File contextXml = new File(contextsDirName, context + ".xml");

		if (contextXml.exists()) {
			FileUtils.touch(contextXml);
		}
		else {
			Map<String, String> filterMap = new HashMap<String, String>();

			filterMap.put("context", context);

			copyDependencyXml(
				"jetty-context-configure.xml", contextXml.getParent(),
				contextXml.getName(), filterMap, true);
		}
	}

	public static void redeployTomcat(String context) throws Exception {
		if (_isPortalContext(context)) {
			throw new UnsupportedOperationException(
				"This method is meant for redeploying plugins, not the portal");
		}

		File webXml = new File(
			getAutoDeployDestDir(), context + "/WEB-INF/web.xml");

		FileUtils.touch(webXml);
	}

	public static void undeploy(String appServerType, File deployDir)
		throws Exception {

		boolean undeployEnabled = PrefsPropsUtil.getBoolean(
			PropsKeys.HOT_UNDEPLOY_ENABLED, PropsValues.HOT_UNDEPLOY_ENABLED);

		if (!undeployEnabled) {
			return;
		}

		if (!appServerType.equals(ServerDetector.GLASSFISH_ID) &&
			!appServerType.equals(ServerDetector.JBOSS_ID) &&
			!appServerType.equals(ServerDetector.JETTY_ID) &&
			!appServerType.equals(ServerDetector.TOMCAT_ID) &&
			!appServerType.equals(ServerDetector.WEBLOGIC_ID)) {

			return;
		}

		if (!deployDir.exists()) {
			String deployDirPath = deployDir.getAbsolutePath();

			if (StringUtil.endsWith(deployDirPath, ".war")) {
				deployDirPath = deployDirPath.substring(
					0, deployDirPath.length() - 4);
			}
			else {
				deployDirPath = deployDirPath.concat(".war");
			}

			deployDir = new File(deployDirPath);
		}

		if (!deployDir.exists()) {
			return;
		}

		if (deployDir.isFile()) {
			FileUtil.delete(deployDir);
		}
		else {
			File webXml = new File(deployDir + "/WEB-INF/web.xml");

			if (!webXml.exists()) {
				return;
			}

			if (_log.isInfoEnabled()) {
				_log.info("Undeploy " + deployDir);
			}

			FileUtil.delete(deployDir + "/WEB-INF/web.xml");

			DeleteTask.deleteDirectory(deployDir);
		}

		if (appServerType.equals(ServerDetector.JETTY_ID)) {
			FileUtil.delete(
				System.getProperty("jetty.home") + "/contexts/" +
					deployDir.getName() + ".xml");
		}

		if (appServerType.equals(ServerDetector.JBOSS_ID)) {
			File deployedFile = new File(
				deployDir.getParent(), deployDir.getName() + ".deployed");

			FileUtil.delete(deployedFile);
		}

		int undeployInterval = PrefsPropsUtil.getInteger(
			PropsKeys.HOT_UNDEPLOY_INTERVAL, PropsValues.HOT_UNDEPLOY_INTERVAL);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Wait " + undeployInterval +
					" ms to allow the plugin time to fully undeploy");
		}

		if (undeployInterval > 0) {
			Thread.sleep(undeployInterval);
		}
	}

	private static boolean _isPortalContext(String context) {
		if (Validator.isNull(context) || context.equals(StringPool.SLASH) ||
			context.equals(
				PortalContextLoaderListener.getPortalServletContextPath())) {

			return true;
		}

		return false;
	}

	private DeployUtil() {
	}

	private String _getResourcePath(String resource) throws IOException {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + resource);

		if (inputStream == null) {
			return null;
		}

		String tmpDir = SystemProperties.get(SystemProperties.TMP_DIR);

		File file = new File(
			tmpDir + "/liferay/com/liferay/portal/deploy/dependencies/" +
				resource);

		//if (!file.exists() || resource.startsWith("ext-")) {
			File parentFile = file.getParentFile();

			if (parentFile != null) {
				parentFile.mkdirs();
			}

			StreamUtil.transfer(inputStream, new FileOutputStream(file));
		//}

		return FileUtil.getAbsolutePath(file);
	}

	private static Log _log = LogFactoryUtil.getLog(DeployUtil.class);

	private static DeployUtil _instance = new DeployUtil();

}