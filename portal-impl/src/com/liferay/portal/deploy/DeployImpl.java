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

package com.liferay.portal.deploy;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Deploy;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.deploy.BaseDeployer;
import com.liferay.portal.tools.deploy.ExtDeployer;
import com.liferay.portal.tools.deploy.HookDeployer;
import com.liferay.portal.tools.deploy.LayoutTemplateDeployer;
import com.liferay.portal.tools.deploy.PortletDeployer;
import com.liferay.portal.tools.deploy.ThemeDeployer;
import com.liferay.portal.tools.deploy.WebDeployer;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.SystemProperties;
import com.liferay.util.ant.DeleteTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * @author Brian Wing Shun Chan
 */
public class DeployImpl implements Deploy {

	public String getDeployDir() throws Exception {
		return getAutoDeployDestDir();
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

		if (serverId.equals(ServerDetector.TOMCAT_ID)) {
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

	public static String getResourcePath(String resource)
		throws Exception {

		return _instance._getResourcePath(resource);
	}

	public void deploy(File source) throws Exception {
		deploy(source, null);
	}

	public void deploy(File source, String context) throws Exception {
		String deployType = null;

		if (context == null) {
			// deployType is file name without extension

			String fileName = source.getName();
			int dotIndex = fileName.lastIndexOf(".");
			deployType = (dotIndex > 0) ? fileName.substring(0, dotIndex) : fileName;
		} else {
			deployType = context;
		}

		BaseDeployer deployer = null;
		List<String> jars = new ArrayList<String>();
		List<String> wars = new ArrayList<String>();

		wars.add(source.getAbsolutePath());

		if (deployType.endsWith("-ext")) {
			deployer = new ExtDeployer();

			deployer.addRequiredJar(jars, "util-java.jar");
		} else if (deployType.endsWith("-hook")) {
			deployer = new HookDeployer();

			deployer.addExtJar(jars, "ext-util-java.jar");
			deployer.addRequiredJar(jars, "util-java.jar");
		} else if (deployType.endsWith("-layouttpl")) {
			deployer = new LayoutTemplateDeployer();
		} else if (deployType.endsWith("-portlet")) {
			deployer = new PortletDeployer();

			deployer.setAuiTaglibDTD(getResourcePath("liferay-aui.tld"));
			deployer.setPortletTaglibDTD(getResourcePath("liferay-portlet.tld"));
			deployer.setPortletExtTaglibDTD(getResourcePath("liferay-portlet-ext.tld"));
			deployer.setSecurityTaglibDTD(getResourcePath("liferay-security.tld"));
			deployer.setThemeTaglibDTD(getResourcePath("liferay-theme.tld"));
			deployer.setUiTaglibDTD(getResourcePath("liferay-ui.tld"));
			deployer.setUtilTaglibDTD(getResourcePath("liferay-util.tld"));
		} else if (deployType.endsWith("-theme")) {
			deployer = new ThemeDeployer();

			deployer.setThemeTaglibDTD(getResourcePath("liferay-theme.tld"));
			deployer.setUiTaglibDTD(getResourcePath("liferay-ui.tld"));

			deployer.addExtJar(jars, "ext-util-java.jar");
			deployer.addExtJar(jars, "ext-util-taglib.jar");
			deployer.addRequiredJar(jars, "util-java.jar");
			deployer.addRequiredJar(jars, "util-taglib.jar");
		} else if (deployType.endsWith("-web")) {
			deployer = new WebDeployer();

			deployer.addExtJar(jars, "ext-util-java.jar");
			deployer.addRequiredJar(jars, "util-java.jar");
		}

		if (deployer == null) {
			return;
		}

		// baseDir isn't used but checkArguments wants it to be set. We might
		// be able to avoid setting this by not calling checkArguments.
		deployer.setBaseDir(PrefsPropsUtil.getString(
			PropsKeys.AUTO_DEPLOY_DEPLOY_DIR,
			PropsValues.AUTO_DEPLOY_DEPLOY_DIR));
		deployer.setDestDir(getAutoDeployDestDir());
		deployer.setAppServerType(ServerDetector.getServerId());
		deployer.setUnpackWar(PrefsPropsUtil.getBoolean(
			PropsKeys.AUTO_DEPLOY_UNPACK_WAR,
			PropsValues.AUTO_DEPLOY_UNPACK_WAR));
		deployer.setFilePattern(StringPool.BLANK);
		deployer.setJbossPrefix(PrefsPropsUtil.getString(
			PropsKeys.AUTO_DEPLOY_JBOSS_PREFIX,
			PropsValues.AUTO_DEPLOY_JBOSS_PREFIX));
		deployer.setTomcatLibDir(PrefsPropsUtil.getString(
			PropsKeys.AUTO_DEPLOY_TOMCAT_LIB_DIR,
			PropsValues.AUTO_DEPLOY_TOMCAT_LIB_DIR));

		deployer.setJars(jars);
		deployer.checkArguments();

		deployer.setWars(wars);

		System.out.println("DEBUG: deploying file: " + source.getAbsolutePath());

		deployer.deployFile(source, context);
	}

	public void redeploy(String context) throws Exception {
		if (ServerDetector.isJetty()) {
			hotDeployJetty(context);
		} else if (ServerDetector.isTomcat()) {
			File webXml = new File(getDeployDir(), "/WEB-INF/web.xml");
			FileUtils.touch(webXml);
		}
	}

	public void undeploy(String context)
		throws Exception {

		File deployDirectory = new File(getAutoDeployDestDir(), context);

		undeploy(ServerDetector.getServerId(), deployDirectory);
	}

	public static void undeploy(String appServerType, File deployDir)
		throws Exception {

		boolean undeployEnabled = PrefsPropsUtil.getBoolean(
			PropsKeys.HOT_UNDEPLOY_ENABLED, PropsValues.HOT_UNDEPLOY_ENABLED);

		if (!undeployEnabled) {
			return;
		}

		if (!appServerType.startsWith(ServerDetector.JBOSS_ID) &&
			!appServerType.startsWith(ServerDetector.JETTY_ID) &&
			!appServerType.equals(ServerDetector.TOMCAT_ID)) {

			return;
		}

		File webXml = new File(deployDir + "/WEB-INF/web.xml");

		if (!webXml.exists()) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Undeploy " + deployDir);
		}

		FileUtil.delete(deployDir + "/WEB-INF/web.xml");

		DeleteTask.deleteDirectory(deployDir);

		if (ServerDetector.isJetty()) {
			String context = deployDir.getName();
			File contextDirectory = new File(System.getProperty("jetty.home"), "contexts");
			File contextXml = new File(contextDirectory, context + ".xml");

			FileUtils.deleteQuietly(contextXml);
		}

		int undeployInterval = PrefsPropsUtil.getInteger(
			PropsKeys.HOT_UNDEPLOY_INTERVAL,
			PropsValues.HOT_UNDEPLOY_INTERVAL);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Wait " + undeployInterval +
					" ms to allow the plugin time to fully undeploy");
		}

		if (undeployInterval > 0) {
			Thread.sleep(undeployInterval);
		}
	}

	public void hotDeployJetty(String context)
		throws IOException {

		File contextDirectory =
			new File(System.getProperty("jetty.home"), "contexts");
		File contextXml = new File(contextDirectory, context + ".xml");

		if (contextXml.exists()) {
			FileUtils.touch(contextXml);
		} else {
			try {
				Writer writer = new FileWriter(contextXml);

				String lineSeparator = System.getProperty("line.separator");

				writer.write("<?xml version=\"1.0\"  encoding=\"UTF-8\"?>" +
					lineSeparator);
				writer.append("<!DOCTYPE Configure PUBLIC \"-//Mort Bay" +
					" Consulting//DTD Configure//EN\" \"http://jetty.mortbay" +
					".org/configure.dtd\">" + lineSeparator);
				writer.append("<Configure class=\"org.mortbay.jetty.webapp." +
					"WebAppContext\">" + lineSeparator);
				writer.append("\t<Set name=\"contextPath\">/" + context +
					"</Set>" + lineSeparator);
				writer.append("\t<Set name=\"war\"><SystemProperty" +
					" name=\"jetty.home\" default=\".\"/>/webapps/" + context +
					"</Set>" + lineSeparator);
				writer.append("</Configure>" + lineSeparator);

				writer.flush();
			} catch (IOException e) {
				_log.warn(
					"Could not write the Jetty context file (" +
						contextXml.getAbsolutePath() + ") so " +
						context + " will not be automatically deployed." +
						" Restart Jetty in order to deploy " +
						context + ".", e);
			}
		}
	}

	private DeployImpl() {
	}

	private String _getResourcePath(String resource) throws IOException {
		InputStream is = getClass().getResourceAsStream(
			"dependencies/" + resource);

		if (is == null) {
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

			StreamUtil.transfer(is, new FileOutputStream(file));
		//}

		return FileUtil.getAbsolutePath(file);
	}

	private static Log _log = LogFactoryUtil.getLog(DeployImpl.class);

	private static DeployImpl _instance = new DeployImpl();

}
