/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.deploy;

import com.liferay.portal.deploy.DeployUtil;
import com.liferay.portal.kernel.deploy.Deployer;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.License;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.servlet.filters.invoker.InvokerFilter;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portal.tools.WebXMLBuilder;
import com.liferay.portal.util.ExtRegistry;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.ant.CopyTask;
import com.liferay.util.ant.DeleteTask;
import com.liferay.util.ant.ExpandTask;
import com.liferay.util.ant.UpToDateTask;
import com.liferay.util.ant.WarTask;
import com.liferay.util.xml.XMLFormatter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.oro.io.GlobFilenameFilter;

/**
 * @author Brian Wing Shun Chan
 * @author Sandeep Soni
 */
public class BaseDeployer implements Deployer {

	public static final String DEPLOY_TO_PREFIX = "DEPLOY_TO__";

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		List<String> wars = new ArrayList<String>();
		List<String> jars = new ArrayList<String>();

		for (String arg : args) {
			String fileName = arg.toLowerCase();

			if (fileName.endsWith(".war")) {
				wars.add(arg);
			}
			else if (fileName.endsWith(".jar")) {
				jars.add(arg);
			}
		}

		new BaseDeployer(wars, jars);
	}

	public BaseDeployer() {
	}

	public BaseDeployer(List<String> wars, List<String> jars) {
		baseDir = System.getProperty("deployer.base.dir");
		destDir = System.getProperty("deployer.dest.dir");
		appServerType = System.getProperty("deployer.app.server.type");
		auiTaglibDTD = System.getProperty("deployer.aui.taglib.dtd");
		portletTaglibDTD = System.getProperty("deployer.portlet.taglib.dtd");
		portletExtTaglibDTD = System.getProperty(
			"deployer.portlet.ext.taglib.dtd");
		securityTaglibDTD = System.getProperty("deployer.security.taglib.dtd");
		themeTaglibDTD = System.getProperty("deployer.theme.taglib.dtd");
		uiTaglibDTD = System.getProperty("deployer.ui.taglib.dtd");
		utilTaglibDTD = System.getProperty("deployer.util.taglib.dtd");
		unpackWar = GetterUtil.getBoolean(
			System.getProperty("deployer.unpack.war"), true);
		filePattern = System.getProperty("deployer.file.pattern");
		jbossPrefix = GetterUtil.getString(
			System.getProperty("deployer.jboss.prefix"));
		tomcatLibDir = System.getProperty("deployer.tomcat.lib.dir");
		this.wars = wars;
		this.jars = jars;

		checkArguments();

		String context = System.getProperty("deployer.context");

		try {
			deploy(context);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addExtJar(List<String> jars, String resource)
		throws Exception {

		Set<String> servletContextNames = ExtRegistry.getServletContextNames();

		for (String servletContextName : servletContextNames) {
			String extResource =
				"ext-" + servletContextName + resource.substring(3);

			String path = DeployUtil.getResourcePath(extResource);

			if (_log.isDebugEnabled()) {
				if (path == null) {
					_log.debug("Resource " + extResource + " is not available");
				}
				else {
					_log.debug(
						"Resource " + extResource + " is available at " + path);
				}
			}

			if (path != null) {
				jars.add(path);
			}
		}
	}

	public void addRequiredJar(List<String> jars, String resource)
		throws Exception {

		String path = DeployUtil.getResourcePath(resource);

		if (path == null) {
			throw new RuntimeException(
				"Resource " + resource + " does not exist");
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Resource " + resource + " is available at " + path);
		}

		jars.add(path);
	}

	public void checkArguments() {
		if (Validator.isNull(baseDir)) {
			throw new IllegalArgumentException(
				"The system property deployer.base.dir is not set");
		}

		if (Validator.isNull(destDir)) {
			throw new IllegalArgumentException(
				"The system property deployer.dest.dir is not set");
		}

		if (Validator.isNull(appServerType)) {
			throw new IllegalArgumentException(
				"The system property deployer.app.server.type is not set");
		}

		if (!appServerType.equals(ServerDetector.GERONIMO_ID) &&
			!appServerType.equals(ServerDetector.GLASSFISH_ID) &&
			!appServerType.equals(ServerDetector.JBOSS_ID) &&
			!appServerType.equals(ServerDetector.JONAS_ID) &&
			!appServerType.equals(ServerDetector.JETTY_ID) &&
			!appServerType.equals(ServerDetector.OC4J_ID) &&
			!appServerType.equals(ServerDetector.RESIN_ID) &&
			!appServerType.equals(ServerDetector.TOMCAT_ID) &&
			!appServerType.equals(ServerDetector.WEBLOGIC_ID) &&
			!appServerType.equals(ServerDetector.WEBSPHERE_ID)) {

			throw new IllegalArgumentException(
				appServerType + " is not a valid application server type");
		}

		if (appServerType.equals(ServerDetector.GLASSFISH_ID) ||
			appServerType.equals(ServerDetector.WEBLOGIC_ID)) {

			unpackWar = false;
		}

		if (Validator.isNotNull(jbossPrefix) &&
			!Validator.isNumber(jbossPrefix)) {

			jbossPrefix = "1";
		}
	}

	public void copyDependencyXml(String fileName, String targetDir)
		throws Exception {

		copyDependencyXml(fileName, targetDir, null);
	}

	public void copyDependencyXml(
			String fileName, String targetDir, Map<String, String> filterMap)
		throws Exception {

		copyDependencyXml(fileName, targetDir, filterMap, false);
	}

	public void copyDependencyXml(
			String fileName, String targetDir, Map<String, String> filterMap,
			boolean overwrite)
		throws Exception {

		DeployUtil.copyDependencyXml(
			fileName, targetDir, fileName, filterMap, overwrite);
	}

	public void copyJars(File srcFile, PluginPackage pluginPackage)
		throws Exception {

		for (int i = 0; i < jars.size(); i++) {
			String jarFullName = jars.get(i);

			String jarName = jarFullName.substring(
				jarFullName.lastIndexOf("/") + 1);

			if ((!appServerType.equals(ServerDetector.TOMCAT_ID)) ||
				(appServerType.equals(ServerDetector.TOMCAT_ID) &&
				 !jarFullName.equals("util-java.jar"))) {

				FileUtil.copyFile(
					jarFullName, srcFile + "/WEB-INF/lib/" + jarName, true);
			}
		}

		FileUtil.delete(srcFile + "/WEB-INF/lib/util-jsf.jar");
	}

	public void copyPortalDependencies(File srcFile) throws Exception {
		Properties properties = getPluginPackageProperties(srcFile);

		if (properties == null) {
			return;
		}

		// jars

		String[] portalJars = StringUtil.split(
			properties.getProperty(
				"portal-dependency-jars",
				properties.getProperty("portal.dependency.jars")));

		for (int i = 0; i < portalJars.length; i++) {
			String portalJar = portalJars[i].trim();

			portalJar = fixPortalDependencyJar(portalJar);

			if (_log.isDebugEnabled()) {
				_log.debug("Copy portal JAR " + portalJar);
			}

			try {
				String portalJarPath = PortalUtil.getPortalLibDir() + portalJar;

				FileUtil.copyFile(
					portalJarPath, srcFile + "/WEB-INF/lib/" + portalJar, true);
			}
			catch (Exception e) {
				_log.error("Unable to copy portal JAR " + portalJar, e);
			}
		}

		// tlds

		String[] portalTlds = StringUtil.split(
			properties.getProperty(
				"portal-dependency-tlds",
				properties.getProperty("portal.dependency.tlds")));

		for (int i = 0; i < portalTlds.length; i++) {
			String portalTld = portalTlds[i].trim();

			if (_log.isDebugEnabled()) {
				_log.debug("Copy portal TLD " + portalTld);
			}

			try {
				String portalTldPath = DeployUtil.getResourcePath(portalTld);

				FileUtil.copyFile(
					portalTldPath, srcFile + "/WEB-INF/tld/" + portalTld, true);
			}
			catch (Exception e) {
				_log.error("Unable to copy portal TLD " + portalTld, e);
			}
		}

		// commons-logging*.jar

		File pluginLibDir = new File(srcFile + "/WEB-INF/lib/");

		if (PropsValues.AUTO_DEPLOY_COPY_COMMONS_LOGGING) {
			String[] commonsLoggingJars = pluginLibDir.list(
				new GlobFilenameFilter("commons-logging*.jar"));

			if ((commonsLoggingJars == null) ||
				(commonsLoggingJars.length == 0)) {

				String portalJarPath =
					PortalUtil.getPortalLibDir() + "commons-logging.jar";

				FileUtil.copyFile(
					portalJarPath, srcFile + "/WEB-INF/lib/commons-logging.jar",
					true);
			}
		}

		// log4j*.jar

		if (PropsValues.AUTO_DEPLOY_COPY_LOG4J) {
			String[] log4jJars = pluginLibDir.list(
				new GlobFilenameFilter("log4j*.jar"));

			if ((log4jJars == null) || (log4jJars.length == 0)) {
				String portalJarPath =
					PortalUtil.getPortalLibDir() + "log4j.jar";

				FileUtil.copyFile(
					portalJarPath, srcFile + "/WEB-INF/lib/log4j.jar", true);
			}
		}
	}

	public void copyProperties(File srcFile, PluginPackage pluginPackage)
		throws Exception {

		if (PropsValues.AUTO_DEPLOY_COPY_COMMONS_LOGGING) {
			copyDependencyXml(
				"logging.properties", srcFile + "/WEB-INF/classes");
		}

		if (PropsValues.AUTO_DEPLOY_COPY_LOG4J) {
			copyDependencyXml("log4j.properties", srcFile + "/WEB-INF/classes");
		}

		File servicePropertiesFile = new File(
			srcFile.getAbsolutePath() + "/WEB-INF/classes/service.properties");

		if (servicePropertiesFile.exists()) {
			File portletPropertiesFile = new File(
				srcFile.getAbsolutePath() +
					"/WEB-INF/classes/portlet.properties");

			if (!portletPropertiesFile.exists()) {
				String pluginPackageName = null;

				if (pluginPackage != null) {
					pluginPackageName = pluginPackage.getName();
				}
				else {
					pluginPackageName = srcFile.getName();
				}

				FileUtil.write(
					portletPropertiesFile,
					"plugin.package.name=" + pluginPackageName);
			}
		}
	}

	public void copyTlds(File srcFile, PluginPackage pluginPackage)
		throws Exception {

		if (Validator.isNotNull(auiTaglibDTD)) {
			FileUtil.copyFile(
				auiTaglibDTD, srcFile + "/WEB-INF/tld/aui.tld", true);
		}

		if (Validator.isNotNull(portletTaglibDTD)) {
			FileUtil.copyFile(
				portletTaglibDTD, srcFile + "/WEB-INF/tld/liferay-portlet.tld",
				true);
		}

		if (Validator.isNotNull(portletExtTaglibDTD)) {
			FileUtil.copyFile(
				portletExtTaglibDTD,
				srcFile + "/WEB-INF/tld/liferay-portlet-ext.tld", true);
		}

		if (Validator.isNotNull(securityTaglibDTD)) {
			FileUtil.copyFile(
				securityTaglibDTD,
				srcFile + "/WEB-INF/tld/liferay-security.tld", true);
		}

		if (Validator.isNotNull(themeTaglibDTD)) {
			FileUtil.copyFile(
				themeTaglibDTD, srcFile + "/WEB-INF/tld/liferay-theme.tld",
				true);
		}

		if (Validator.isNotNull(uiTaglibDTD)) {
			FileUtil.copyFile(
				uiTaglibDTD, srcFile + "/WEB-INF/tld/liferay-ui.tld", true);
		}

		if (Validator.isNotNull(utilTaglibDTD)) {
			FileUtil.copyFile(
				utilTaglibDTD, srcFile + "/WEB-INF/tld/liferay-util.tld", true);
		}
	}

	public void copyXmls(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		if (appServerType.equals(ServerDetector.GERONIMO_ID)) {
			copyDependencyXml("geronimo-web.xml", srcFile + "/WEB-INF");
		}
		else if (appServerType.equals(ServerDetector.JBOSS_ID)) {
			copyDependencyXml(
				"jboss-deployment-structure.xml", srcFile + "/WEB-INF");
		}
		else if (appServerType.equals(ServerDetector.WEBLOGIC_ID)) {
			copyDependencyXml("weblogic.xml", srcFile + "/WEB-INF");
		}
		else if (appServerType.equals(ServerDetector.WEBSPHERE_ID)) {
			copyDependencyXml("ibm-web-ext.xmi", srcFile + "/WEB-INF");
		}

		copyDependencyXml("web.xml", srcFile + "/WEB-INF");
	}

	public void deploy(String context) throws Exception {
		try {
			File baseDirFile = new File(baseDir);

			File[] files = baseDirFile.listFiles();

			if (files == null) {
				return;
			}

			files = FileUtil.sortFiles(files);

			for (int i = 0; i < files.length; i++) {
				File srcFile = files[i];

				String fileName = srcFile.getName().toLowerCase();

				boolean deploy = false;

				if (fileName.endsWith(".war") || fileName.endsWith(".zip")) {
					deploy = true;

					if (wars.size() > 0) {
						if (!wars.contains(srcFile.getName())) {
							deploy = false;
						}
					}
					else if (Validator.isNotNull(filePattern)) {
						if (!StringUtil.matchesIgnoreCase(
								fileName, filePattern)) {

							deploy = false;
						}
					}
				}

				if (deploy) {
					deployFile(srcFile, context);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deployDirectory(
			File srcFile, File mergeDir, File deployDir, String displayName,
			boolean overwrite, PluginPackage pluginPackage)
		throws Exception {

		rewriteFiles(srcFile);

		mergeDirectory(mergeDir, srcFile);

		processPluginPackageProperties(srcFile, displayName, pluginPackage);

		copyJars(srcFile, pluginPackage);
		copyProperties(srcFile, pluginPackage);
		copyTlds(srcFile, pluginPackage);
		copyXmls(srcFile, displayName, pluginPackage);
		copyPortalDependencies(srcFile);

		updateGeronimoWebXml(srcFile, displayName, pluginPackage);

		File webXml = new File(srcFile + "/WEB-INF/web.xml");

		updateWebXml(webXml, srcFile, displayName, pluginPackage);

		File extLibGlobalDir = new File(
			srcFile.getAbsolutePath() + "/WEB-INF/ext-lib/global");

		if (extLibGlobalDir.exists()) {
			File globalLibDir = new File(PortalUtil.getGlobalLibDir());

			CopyTask.copyDirectory(
				extLibGlobalDir, globalLibDir, "*.jar", StringPool.BLANK,
				overwrite, true);
		}

		File extLibPortalDir = new File(
			srcFile.getAbsolutePath() + "/WEB-INF/ext-lib/portal");

		if (extLibPortalDir.exists()) {
			File portalLibDir = new File(PortalUtil.getPortalLibDir());

			CopyTask.copyDirectory(
				extLibPortalDir, portalLibDir, "*.jar", StringPool.BLANK,
				overwrite, true);
		}

		if ((deployDir == null) || baseDir.equals(destDir)) {
			return;
		}

		updateDeployDirectory(srcFile);

		String excludes = StringPool.BLANK;

		if (appServerType.equals(ServerDetector.JBOSS_ID)) {
			excludes += "**/WEB-INF/lib/log4j.jar,";
		}
		else if (appServerType.equals(ServerDetector.TOMCAT_ID)) {
			String[] libs = FileUtil.listFiles(tomcatLibDir);

			for (int i = 0; i < libs.length; i++) {
				excludes += "**/WEB-INF/lib/" + libs[i] + ",";
			}

			File contextXml = new File(srcFile + "/META-INF/context.xml");

			if (contextXml.exists()) {
				String content = FileUtil.read(contextXml);

				if (content.indexOf(_PORTAL_CLASS_LOADER) != -1) {
					excludes += "**/WEB-INF/lib/util-bridges.jar,";
					excludes += "**/WEB-INF/lib/util-java.jar,";
					excludes += "**/WEB-INF/lib/util-taglib.jar,";
				}
			}

			try {

				// LEP-2990

				Class.forName("javax.el.ELContext");

				excludes += "**/WEB-INF/lib/el-api.jar,";
			}
			catch (ClassNotFoundException cnfe) {
			}
		}

		// LPS-11268

		Properties properties = getPluginPackageProperties(srcFile);

		if (properties != null) {
			String deployExcludes = properties.getProperty("deploy-excludes");

			if (deployExcludes != null) {
				excludes += deployExcludes.trim();

				if (!excludes.endsWith(",")) {
					excludes += ",";
				}
			}

			deployExcludes = properties.getProperty(
				"deploy-excludes-" + appServerType);

			if (deployExcludes != null) {
				excludes += deployExcludes.trim();

				if (!excludes.endsWith(",")) {
					excludes += ",";
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Excludes " + excludes);
		}

		if (!unpackWar || appServerType.equals(ServerDetector.WEBSPHERE_ID)) {
			File tempDir = new File(
				SystemProperties.get(SystemProperties.TMP_DIR) +
					File.separator + Time.getTimestamp());

			excludes += "**/WEB-INF/web.xml";

			WarTask.war(srcFile, tempDir, excludes, webXml);

			if (isJEEDeploymentEnabled()) {
				File tempWarDir = new File(
					tempDir.getParent(), deployDir.getName());

				if (tempWarDir.exists()) {
					tempWarDir.delete();
				}

				if (!tempDir.renameTo(tempWarDir)) {
					tempWarDir = tempDir;
				}

				DeploymentHandler deploymentHandler = getDeploymentHandler();

				deploymentHandler.deploy(tempWarDir, displayName);

				deploymentHandler.releaseDeploymentManager();

				DeleteTask.deleteDirectory(tempWarDir);
			}
			else {
				if (!tempDir.renameTo(deployDir)) {
					WarTask.war(srcFile, deployDir, excludes, webXml);
				}

				DeleteTask.deleteDirectory(tempDir);
			}
		}
		else {

			// The deployer might only copy files that have been modified.
			// However, the deployer always copies and overwrites web.xml after
			// the other files have been copied because application servers
			// usually detect that a WAR has been modified based on the web.xml
			// timestamp.

			excludes += "**/WEB-INF/web.xml";

			CopyTask.copyDirectory(
				srcFile, deployDir, StringPool.BLANK, excludes, overwrite,
				true);

			CopyTask.copyDirectory(
				srcFile, deployDir, "**/WEB-INF/web.xml", StringPool.BLANK,
				true, false);

			if (appServerType.equals(ServerDetector.TOMCAT_ID)) {

				// See org.apache.catalina.startup.HostConfig to see how Tomcat
				// checks to make sure that web.xml was modified 5 seconds after
				// WEB-INF

				File deployWebXml = new File(deployDir + "/WEB-INF/web.xml");

				deployWebXml.setLastModified(
					System.currentTimeMillis() + (Time.SECOND * 6));
			}
		}

		if (appServerType.equals(ServerDetector.JETTY_ID)) {
			DeployUtil.redeployJetty(displayName);
		}
	}

	public void deployDirectory(
			File srcFile, String displayName, boolean override,
			PluginPackage pluginPackage)
		throws Exception {

		deployDirectory(
			srcFile, null, null, displayName, override, pluginPackage);
	}

	public boolean deployFile(
			File srcFile, File mergeDir, File deployDir, String displayName,
			boolean overwrite, PluginPackage pluginPackage)
		throws Exception {

		boolean undeployOnRedeploy = false;

		try {
			undeployOnRedeploy = PrefsPropsUtil.getBoolean(
				PropsKeys.HOT_UNDEPLOY_ON_REDEPLOY,
				PropsValues.HOT_UNDEPLOY_ON_REDEPLOY);
		}
		catch (Exception e) {

			// This will only happen when running the deploy tool in Ant in the
			// classical way where the WAR file is actually massaged and
			// packaged.

		}

		if (undeployOnRedeploy) {
			DeployUtil.undeploy(appServerType, deployDir);
		}

		if (!overwrite && UpToDateTask.isUpToDate(srcFile, deployDir)) {
			if (_log.isInfoEnabled()) {
				_log.info(deployDir + " is already up to date");
			}

			return false;
		}

		File tempDir = new File(
			SystemProperties.get(SystemProperties.TMP_DIR) + File.separator +
				Time.getTimestamp());

		ExpandTask.expand(srcFile, tempDir);

		deployDirectory(
			tempDir, mergeDir, deployDir, displayName, overwrite,
			pluginPackage);

		DeleteTask.deleteDirectory(tempDir);

		return true;
	}

	public void deployFile(File srcFile, String specifiedContext)
		throws Exception {

		PluginPackage pluginPackage = readPluginPackage(srcFile);

		if (_log.isInfoEnabled()) {
			_log.info("Deploying " + srcFile.getName());
		}

		String deployDir = null;
		String displayName = specifiedContext;
		boolean overwrite = false;
		String preliminaryContext = specifiedContext;

		// The order of priority of the context is: 1.) the specified context,
		// 2.) if the file name starts with DEPLOY_TO_PREFIX, use the file name
		// after the prefix, or 3.) the recommended deployment context as
		// specified in liferay-plugin-package.properties, or 4.) the file name.

		if ((specifiedContext != null) &&
			srcFile.getName().startsWith(DEPLOY_TO_PREFIX)) {

			displayName = srcFile.getName().substring(
				DEPLOY_TO_PREFIX.length(), srcFile.getName().length() - 4);

			overwrite = true;
			preliminaryContext = displayName;
		}

		if (preliminaryContext == null) {
			preliminaryContext = getDisplayName(srcFile);
		}

		if (pluginPackage != null) {
			if (!PluginPackageUtil.isCurrentVersionSupported(
					pluginPackage.getLiferayVersions())) {

				throw new AutoDeployException(
					srcFile.getName() +
						" does not support this version of Liferay");
			}

			if (displayName == null) {
				displayName = pluginPackage.getRecommendedDeploymentContext();
			}

			if (Validator.isNull(displayName)) {
				displayName = getDisplayName(srcFile);
			}

			pluginPackage.setContext(displayName);

			PluginPackageUtil.updateInstallingPluginPackage(
				preliminaryContext, pluginPackage);
		}

		if (Validator.isNotNull(displayName)) {
			deployDir = displayName + ".war";
		}
		else {
			deployDir = srcFile.getName();
			displayName = getDisplayName(srcFile);
		}

		if (appServerType.equals(ServerDetector.JBOSS_ID)) {
			deployDir = jbossPrefix + deployDir;
		}
		else if (appServerType.equals(ServerDetector.JETTY_ID) ||
				 appServerType.equals(ServerDetector.OC4J_ID) ||
				 appServerType.equals(ServerDetector.RESIN_ID) ||
				 appServerType.equals(ServerDetector.TOMCAT_ID)) {

			if (unpackWar) {
				deployDir = deployDir.substring(0, deployDir.length() - 4);
			}
		}

		deployDir = destDir + "/" + deployDir;

		File deployDirFile = new File(deployDir);

		try {
			PluginPackage previousPluginPackage = readPluginPackage(
				deployDirFile);

			if ((pluginPackage != null) && (previousPluginPackage != null)) {
				if (_log.isInfoEnabled()) {
					String name = pluginPackage.getName();
					String previousVersion = previousPluginPackage.getVersion();
					String version = pluginPackage.getVersion();

					_log.info(
						"Updating " + name + " from version " +
							previousVersion + " to version " + version);
				}

				if (pluginPackage.isLaterVersionThan(previousPluginPackage)) {
					overwrite = true;
				}
			}

			File mergeDirFile = new File(
				srcFile.getParent() + "/merge/" + srcFile.getName());

			if (srcFile.isDirectory()) {
				deployDirectory(
					srcFile, mergeDirFile, deployDirFile, displayName,
					overwrite, pluginPackage);
			}
			else {
				boolean deployed = deployFile(
					srcFile, mergeDirFile, deployDirFile, displayName,
					overwrite, pluginPackage);

				if (!deployed) {
					String context = preliminaryContext;

					if (pluginPackage != null) {
						context = pluginPackage.getContext();
					}

					PluginPackageUtil.endPluginPackageInstallation(context);
				}
				else {
					if (appServerType.equals(ServerDetector.JBOSS_ID)) {
						File doDeployFile = new File(deployDir + ".dodeploy");

						FileUtil.write(doDeployFile, StringPool.BLANK);
					}
				}
			}
		}
		catch (Exception e) {
			if (pluginPackage != null) {
				PluginPackageUtil.endPluginPackageInstallation(
					pluginPackage.getContext());
			}

			throw e;
		}
	}

	public String downloadJar(String jar) throws Exception {
		String tmpDir = SystemProperties.get(SystemProperties.TMP_DIR);

		File file = new File(
			tmpDir + "/liferay/com/liferay/portal/deploy/dependencies/" + jar);

		if (!file.exists()) {
			synchronized (this) {
				String url = PropsUtil.get(
					PropsKeys.LIBRARY_DOWNLOAD_URL + jar);

				if (_log.isInfoEnabled()) {
					_log.info("Downloading library from " + url);
				}

				byte[] bytes = HttpUtil.URLtoByteArray(url);

				FileUtil.write(file, bytes);
			}
		}

		return FileUtil.getAbsolutePath(file);
	}

	public String fixPortalDependencyJar(String portalJar) {
		if (portalJar.equals("antlr.jar")) {
			portalJar = "antlr2.jar";
		}

		return portalJar;
	}

	public DeploymentHandler getDeploymentHandler() {
		String prefix = "auto.deploy." + ServerDetector.getServerId() + ".jee.";

		String dmId = PropsUtil.get(prefix + "dm.id");
		String dmUser = PropsUtil.get(prefix + "dm.user");
		String dmPassword = PropsUtil.get(prefix + "dm.passwd");
		String dfClassName = PropsUtil.get(prefix + "df.classname");

		return new DeploymentHandler(dmId, dmUser, dmPassword, dfClassName);
	}

	public String getDisplayName(File srcFile) {
		String displayName = srcFile.getName();

		if (StringUtil.endsWith(displayName, ".war") ||
			StringUtil.endsWith(displayName, ".xml")) {

			displayName = displayName.substring(0, displayName.length() - 4);
		}

		if (appServerType.equals(ServerDetector.JBOSS_ID) &&
			Validator.isNotNull(jbossPrefix) &&
			displayName.startsWith(jbossPrefix)) {

			displayName = displayName.substring(1);
		}

		return displayName;
	}

	public String getExtraContent(
			double webXmlVersion, File srcFile, String displayName)
		throws Exception {

		StringBundler sb = new StringBundler();

		sb.append("<display-name>");
		sb.append(displayName);
		sb.append("</display-name>");

		if (webXmlVersion < 2.4) {
			sb.append("<context-param>");
			sb.append("<param-name>liferay-invoker-enabled</param-name>");
			sb.append("<param-value>false</param-value>");
			sb.append("</context-param>");
		}

		sb.append("<listener>");
		sb.append("<listener-class>");
		sb.append("com.liferay.portal.kernel.servlet.");
		sb.append("SerializableSessionAttributeListener");
		sb.append("</listener-class>");
		sb.append("</listener>");

		File serviceXml = new File(srcFile + "/WEB-INF/service.xml");

		if (serviceXml.exists()) {
			sb.append("<listener>");
			sb.append("<listener-class>");
			sb.append("com.liferay.portal.kernel.spring.context.");
			sb.append("PortletContextLoaderListener");
			sb.append("</listener-class>");
			sb.append("</listener>");
		}

		File serverConfigWsdd = new File(
			srcFile + "/WEB-INF/server-config.wsdd");

		if (serverConfigWsdd.exists()) {
			File webXml = new File(srcFile + "/WEB-INF/web.xml");

			String content = FileUtil.read(webXml);

			if (!content.contains("axis.servicesPath")) {
				String remotingContent = FileUtil.read(
					DeployUtil.getResourcePath("remoting-web.xml"));

				sb.append(remotingContent);
			}
		}

		sb.append("<servlet>");
		sb.append("<servlet-name>");
		sb.append("Set Portlet Class Loader Servlet");
		sb.append("</servlet-name>");
		sb.append("<servlet-class>");
		sb.append("com.liferay.portal.kernel.servlet.");
		sb.append("SetPortletClassLoaderServlet");
		sb.append("</servlet-class>");
		sb.append("<load-on-startup>0</load-on-startup>");
		sb.append("</servlet>");

		boolean hasTaglib = false;

		if (Validator.isNotNull(auiTaglibDTD) ||
			Validator.isNotNull(portletTaglibDTD) ||
			Validator.isNotNull(portletExtTaglibDTD) ||
			Validator.isNotNull(securityTaglibDTD) ||
			Validator.isNotNull(themeTaglibDTD) ||
			Validator.isNotNull(uiTaglibDTD) ||
			Validator.isNotNull(utilTaglibDTD)) {

			hasTaglib = true;
		}

		if (hasTaglib && (webXmlVersion > 2.3)) {
			sb.append("<jsp-config>");
		}

		if (Validator.isNotNull(auiTaglibDTD)) {
			sb.append("<taglib>");
			sb.append("<taglib-uri>http://liferay.com/tld/aui</taglib-uri>");
			sb.append("<taglib-location>");
			sb.append("/WEB-INF/tld/aui.tld");
			sb.append("</taglib-location>");
			sb.append("</taglib>");
		}

		if (Validator.isNotNull(portletTaglibDTD)) {
			sb.append("<taglib>");
			sb.append(
				"<taglib-uri>http://java.sun.com/portlet_2_0</taglib-uri>");
			sb.append("<taglib-location>");
			sb.append("/WEB-INF/tld/liferay-portlet.tld");
			sb.append("</taglib-location>");
			sb.append("</taglib>");
		}

		if (Validator.isNotNull(portletExtTaglibDTD)) {
			sb.append("<taglib>");
			sb.append("<taglib-uri>");
			sb.append("http://liferay.com/tld/portlet");
			sb.append("</taglib-uri>");
			sb.append("<taglib-location>");
			sb.append("/WEB-INF/tld/liferay-portlet-ext.tld");
			sb.append("</taglib-location>");
			sb.append("</taglib>");
		}

		if (Validator.isNotNull(securityTaglibDTD)) {
			sb.append("<taglib>");
			sb.append("<taglib-uri>");
			sb.append("http://liferay.com/tld/security");
			sb.append("</taglib-uri>");
			sb.append("<taglib-location>");
			sb.append("/WEB-INF/tld/liferay-security.tld");
			sb.append("</taglib-location>");
			sb.append("</taglib>");
		}

		if (Validator.isNotNull(themeTaglibDTD)) {
			sb.append("<taglib>");
			sb.append("<taglib-uri>http://liferay.com/tld/theme</taglib-uri>");
			sb.append("<taglib-location>");
			sb.append("/WEB-INF/tld/liferay-theme.tld");
			sb.append("</taglib-location>");
			sb.append("</taglib>");
		}

		if (Validator.isNotNull(uiTaglibDTD)) {
			sb.append("<taglib>");
			sb.append("<taglib-uri>http://liferay.com/tld/ui</taglib-uri>");
			sb.append("<taglib-location>");
			sb.append("/WEB-INF/tld/liferay-ui.tld");
			sb.append("</taglib-location>");
			sb.append("</taglib>");
		}

		if (Validator.isNotNull(utilTaglibDTD)) {
			sb.append("<taglib>");
			sb.append("<taglib-uri>http://liferay.com/tld/util</taglib-uri>");
			sb.append("<taglib-location>");
			sb.append("/WEB-INF/tld/liferay-util.tld");
			sb.append("</taglib-location>");
			sb.append("</taglib>");
		}

		if (hasTaglib && (webXmlVersion > 2.3)) {
			sb.append("</jsp-config>");
		}

		return sb.toString();
	}

	public String getExtraFiltersContent(double webXmlVersion, File srcFile)
		throws Exception {

		if (webXmlVersion > 2.3) {
			return getSessionFiltersContent();
		}
		else {
			return StringPool.BLANK;
		}
	}

	public String getIgnoreFiltersContent(File srcFile) throws Exception {
		boolean ignoreFiltersEnabled = true;

		Properties properties = getPluginPackageProperties(srcFile);

		if (properties != null) {
			ignoreFiltersEnabled = GetterUtil.getBoolean(
				properties.getProperty("ignore-filters-enabled"), true);
		}

		if (ignoreFiltersEnabled) {
			String ignoreFiltersContent = FileUtil.read(
				DeployUtil.getResourcePath("ignore-filters-web.xml"));

			return ignoreFiltersContent;
		}
		else {
			return StringPool.BLANK;
		}
	}

	public String getInvokerFilterContent() {
		StringBundler sb = new StringBundler(4);

		sb.append(getInvokerFilterContent("ERROR"));
		sb.append(getInvokerFilterContent("FORWARD"));
		sb.append(getInvokerFilterContent("INCLUDE"));
		sb.append(getInvokerFilterContent("REQUEST"));

		return sb.toString();
	}

	public String getInvokerFilterContent(String dispatcher) {
		StringBundler sb = new StringBundler(23);

		sb.append("<filter>");
		sb.append("<filter-name>Invoker Filter - ");
		sb.append(dispatcher);
		sb.append("</filter-name>");
		sb.append("<filter-class>");
		sb.append(InvokerFilter.class.getName());
		sb.append("</filter-class>");
		sb.append("<init-param>");
		sb.append("<param-name>dispatcher</param-name>");
		sb.append("<param-value>");
		sb.append(dispatcher);
		sb.append("</param-value>");
		sb.append("</init-param>");
		sb.append("</filter>");

		sb.append("<filter-mapping>");
		sb.append("<filter-name>Invoker Filter - ");
		sb.append(dispatcher);
		sb.append("</filter-name>");
		sb.append("<url-pattern>/*</url-pattern>");
		sb.append("<dispatcher>");
		sb.append(dispatcher);
		sb.append("</dispatcher>");
		sb.append("</filter-mapping>");

		return sb.toString();
	}

	public String getPluginPackageLicensesXml(List<License> licenses) {
		if (licenses.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(5 * licenses.size() + 2);

		for (int i = 0; i < licenses.size(); i++) {
			License license = licenses.get(i);

			if (i == 0) {
				sb.append("\r\n");
			}

			sb.append("\t\t<license osi-approved=\"");
			sb.append(license.isOsiApproved());
			sb.append("\">");
			sb.append(license.getName());
			sb.append("</license>\r\n");

			if ((i + 1) == licenses.size()) {
				sb.append("\t");
			}
		}

		return sb.toString();
	}

	public String getPluginPackageLiferayVersionsXml(
		List<String> liferayVersions) {

		if (liferayVersions.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(liferayVersions.size() * 3 + 2);

		for (int i = 0; i < liferayVersions.size(); i++) {
			String liferayVersion = liferayVersions.get(i);

			if (i == 0) {
				sb.append("\r\n");
			}

			sb.append("\t\t<liferay-version>");
			sb.append(liferayVersion);
			sb.append("</liferay-version>\r\n");

			if ((i + 1) == liferayVersions.size()) {
				sb.append("\t");
			}
		}

		return sb.toString();
	}

	public Properties getPluginPackageProperties(File srcFile)
		throws Exception {

		File propertiesFile = new File(
			srcFile + "/WEB-INF/liferay-plugin-package.properties");

		if (!propertiesFile.exists()) {
			return null;
		}

		String propertiesString = FileUtil.read(propertiesFile);

		return PropertiesUtil.load(propertiesString);
	}

	public String getPluginPackageTagsXml(List<String> tags) {
		if (tags.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(tags.size() * 3 + 2);

		for (int i = 0; i < tags.size(); i++) {
			String tag = tags.get(i);

			if (i == 0) {
				sb.append("\r\n");
			}

			sb.append("\t\t<tag>");
			sb.append(tag);
			sb.append("</tag>\r\n");

			if ((i + 1) == tags.size()) {
				sb.append("\t");
			}
		}

		return sb.toString();
	}

	public Map<String, String> getPluginPackageXmlFilterMap(
		PluginPackage pluginPackage) {

		List<String> pluginTypes = pluginPackage.getTypes();

		String pluginType = pluginTypes.get(0);

		if (!pluginType.equals(getPluginType())) {
			return null;
		}

		Map<String, String> filterMap = new HashMap<String, String>();

		filterMap.put("module_group_id", pluginPackage.getGroupId());
		filterMap.put("module_artifact_id", pluginPackage.getArtifactId());
		filterMap.put("module_version", pluginPackage.getVersion());

		filterMap.put("plugin_name", pluginPackage.getName());
		filterMap.put("plugin_type", pluginType);
		filterMap.put(
			"plugin_type_name",
			TextFormatter.format(pluginType, TextFormatter.J));

		filterMap.put("tags", getPluginPackageTagsXml(pluginPackage.getTags()));
		filterMap.put("short_description", pluginPackage.getShortDescription());
		filterMap.put("long_description", pluginPackage.getLongDescription());
		filterMap.put("change_log", pluginPackage.getChangeLog());
		filterMap.put("page_url", pluginPackage.getPageURL());
		filterMap.put("author", pluginPackage.getAuthor());
		filterMap.put(
			"licenses",
			getPluginPackageLicensesXml(pluginPackage.getLicenses()));
		filterMap.put(
			"liferay_versions",
			getPluginPackageLiferayVersionsXml(
				pluginPackage.getLiferayVersions()));

		return filterMap;
	}

	public String getPluginType() {
		return null;
	}

	public String getServletContextIncludeFiltersContent(
			double webXmlVersion, File srcFile)
		throws Exception {

		boolean servletContextIncludeFiltersEnabled = true;

		Properties properties = getPluginPackageProperties(srcFile);

		if (properties != null) {
			servletContextIncludeFiltersEnabled = GetterUtil.getBoolean(
				properties.getProperty(
					"servlet-context-include-filters-enabled"), true);
		}

		if (servletContextIncludeFiltersEnabled) {
			String servletContextIncludeFiltersContent = FileUtil.read(
				DeployUtil.getResourcePath(
					"servlet-context-include-filters-web.xml"));

			if (webXmlVersion < 2.4) {
				int x = servletContextIncludeFiltersContent.indexOf(
					"<dispatcher>");
				int y = servletContextIncludeFiltersContent.indexOf(
					"</filter-mapping>");

				if (x != -1) {
					if (_log.isWarnEnabled()) {
						_log.warn("Please update web.xml to at least 2.4");
					}

					servletContextIncludeFiltersContent =
						servletContextIncludeFiltersContent.substring(0, x) +
							servletContextIncludeFiltersContent.substring(y);
				}
			}

			return servletContextIncludeFiltersContent;
		}
		else {
			return StringPool.BLANK;
		}
	}

	public String getSessionFiltersContent() throws Exception {
		String sessionFiltersContent = FileUtil.read(
			DeployUtil.getResourcePath("session-filters-web.xml"));

		return sessionFiltersContent;
	}

	public String getSpeedFiltersContent(File srcFile) throws Exception {
		boolean speedFiltersEnabled = true;

		Properties properties = getPluginPackageProperties(srcFile);

		if (properties != null) {
			speedFiltersEnabled = GetterUtil.getBoolean(
				properties.getProperty("speed-filters-enabled"), true);
		}

		if (speedFiltersEnabled) {
			String speedFiltersContent = FileUtil.read(
				DeployUtil.getResourcePath("speed-filters-web.xml"));

			return speedFiltersContent;
		}
		else {
			return StringPool.BLANK;
		}
	}

	public boolean isJEEDeploymentEnabled() {
		return GetterUtil.getBoolean(PropsUtil.get(
			"auto.deploy." + ServerDetector.getServerId() +
				".jee.deployment.enabled"));
	}

	public void mergeDirectory(File mergeDir, File targetDir) {
		if ((mergeDir == null) || (!mergeDir.exists())) {
			return;
		}

		CopyTask.copyDirectory(mergeDir, targetDir, null, null, true, false);
	}

	public Map<String, String> processPluginPackageProperties(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		if (pluginPackage == null) {
			return null;
		}

		Properties properties = getPluginPackageProperties(srcFile);

		if ((properties == null) || (properties.size() == 0)) {
			return null;
		}

		Map<String, String> filterMap = getPluginPackageXmlFilterMap(
			pluginPackage);

		if (filterMap == null) {
			return null;
		}

		copyDependencyXml(
			"liferay-plugin-package.xml", srcFile + "/WEB-INF", filterMap,
			true);

		return filterMap;
	}

	public PluginPackage readPluginPackage(File file) {
		if (!file.exists()) {
			return null;
		}

		InputStream is = null;
		ZipFile zipFile = null;

		try {
			boolean parseProps = false;

			if (file.isDirectory()) {
				String path = file.getPath();

				File pluginPackageXmlFile = new File(
					file.getParent() + "/merge/" + file.getName() +
						"/WEB-INF/liferay-plugin-package.xml");

				if (pluginPackageXmlFile.exists()) {
					is = new FileInputStream(pluginPackageXmlFile);
				}
				else {
					pluginPackageXmlFile = new File(
						path + "/WEB-INF/liferay-plugin-package.xml");

					if (pluginPackageXmlFile.exists()) {
						is = new FileInputStream(pluginPackageXmlFile);
					}
				}

				File pluginPackagePropertiesFile = new File(
					file.getParent() + "/merge/" + file.getName() +
						"/WEB-INF/liferay-plugin-package.properties");

				if ((is == null) && pluginPackagePropertiesFile.exists()) {
					is = new FileInputStream(pluginPackagePropertiesFile);

					parseProps = true;
				}
				else {
					pluginPackagePropertiesFile = new File(
						path + "/WEB-INF/liferay-plugin-package.properties");

					if ((is == null) && pluginPackagePropertiesFile.exists()) {
						is = new FileInputStream(pluginPackagePropertiesFile);

						parseProps = true;
					}
				}
			}
			else {
				zipFile = new ZipFile(file);

				File pluginPackageXmlFile = new File(
					file.getParent() + "/merge/" + file.getName() +
						"/WEB-INF/liferay-plugin-package.xml");

				if (pluginPackageXmlFile.exists()) {
					is = new FileInputStream(pluginPackageXmlFile);
				}
				else {
					ZipEntry zipEntry = zipFile.getEntry(
						"WEB-INF/liferay-plugin-package.xml");

					if (zipEntry != null) {
						is = zipFile.getInputStream(zipEntry);
					}
				}

				File pluginPackagePropertiesFile = new File(
					file.getParent() + "/merge/" + file.getName() +
						"/WEB-INF/liferay-plugin-package.properties");

				if ((is == null) && pluginPackagePropertiesFile.exists()) {
					is = new FileInputStream(pluginPackagePropertiesFile);

					parseProps = true;
				}
				else {
					ZipEntry zipEntry = zipFile.getEntry(
						"WEB-INF/liferay-plugin-package.properties");

					if ((is == null) && (zipEntry != null)) {
						is = zipFile.getInputStream(zipEntry);

						parseProps = true;
					}
				}
			}

			if (is == null) {
				if (_log.isInfoEnabled()) {
					_log.info(
						file.getPath() + " does not have a " +
							"WEB-INF/liferay-plugin-package.xml or " +
								"WEB-INF/liferay-plugin-package.properties");
				}

				return null;
			}

			if (parseProps) {
				String displayName = getDisplayName(file);

				String propertiesString = StringUtil.read(is);

				Properties properties = PropertiesUtil.load(propertiesString);

				return PluginPackageUtil.readPluginPackageProperties(
					displayName, properties);
			}
			else {
				String xml = StringUtil.read(is);

				xml = XMLFormatter.fixProlog(xml);

				return PluginPackageUtil.readPluginPackageXml(xml);
			}
		}
		catch (Exception e) {
			_log.error(file.getPath() + ": " + e.toString());
		}
		finally {
			if (is != null) {
				try {
					is.close();
				}
				catch (IOException ioe) {
				}
			}

			if (zipFile != null) {
				try {
					zipFile.close();
				}
				catch (IOException ioe) {
				}
			}
		}

		return null;
	}

	public void rewriteFiles(File srcDir) throws Exception {
		String[] files = FileUtil.listFiles(srcDir + "/WEB-INF/");

		for (int i = 0; i < files.length; i++) {
			String fileName = GetterUtil.getString(
				FileUtil.getShortFileName(files[i]));

			// LEP-6415

			if (fileName.equalsIgnoreCase("mule-config.xml")) {
				continue;
			}

			String ext = GetterUtil.getString(FileUtil.getExtension(files[i]));

			if (!ext.equalsIgnoreCase("xml")) {
				continue;
			}

			// Make sure to rewrite any XML files to include external entities
			// into same file. See LEP-3142.

			File file = new File(srcDir + "/WEB-INF/" + files[i]);

			try {
				Document doc = SAXReaderUtil.read(file);

				String content = doc.formattedString(StringPool.TAB, true);

				FileUtil.write(file, content);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to format " + file + ": " + e.getMessage());
				}
			}
		}
	}

	public void setAppServerType(String appServerType) {
		this.appServerType = appServerType;
	}

	public void setAuiTaglibDTD(String auiTaglibDTD) {
		this.auiTaglibDTD = auiTaglibDTD;
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public void setDestDir(String destDir) {
		this.destDir = destDir;
	}

	public void setFilePattern(String filePattern) {
		this.filePattern = filePattern;
	}

	public void setJars(List<String> jars) {
		this.jars = jars;
	}

	public void setJbossPrefix(String jbossPrefix) {
		this.jbossPrefix = jbossPrefix;
	}

	public void setPortletExtTaglibDTD(String portletExtTaglibDTD) {
		this.portletExtTaglibDTD = portletExtTaglibDTD;
	}

	public void setPortletTaglibDTD(String portletTaglibDTD) {
		this.portletTaglibDTD = portletTaglibDTD;
	}

	public void setSecurityTaglibDTD(String securityTaglibDTD) {
		this.securityTaglibDTD = securityTaglibDTD;
	}

	public void setThemeTaglibDTD(String themeTaglibDTD) {
		this.themeTaglibDTD = themeTaglibDTD;
	}

	public void setTomcatLibDir(String tomcatLibDir) {
		this.tomcatLibDir = tomcatLibDir;
	}

	public void setUiTaglibDTD(String uiTaglibDTD) {
		this.uiTaglibDTD = uiTaglibDTD;
	}

	public void setUnpackWar(boolean unpackWar) {
		this.unpackWar = unpackWar;
	}

	public void setUtilTaglibDTD(String utilTaglibDTD) {
		this.utilTaglibDTD = utilTaglibDTD;
	}

	public void setWars(List<String> wars) {
		this.wars = wars;
	}

	public void updateDeployDirectory(File srcFile) throws Exception {
	}

	public void updateGeronimoWebXml(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		if (!appServerType.equals(ServerDetector.GERONIMO_ID)) {
			return;
		}

		File geronimoWebXml = new File(srcFile + "/WEB-INF/geronimo-web.xml");

		Document doc = SAXReaderUtil.read(geronimoWebXml);

		Element root = doc.getRootElement();

		Element environmentEl = root.element("environment");

		Element moduleIdEl = environmentEl.element("moduleId");

		Element artifactIdEl = moduleIdEl.element("artifactId");

		artifactIdEl.setText(displayName);

		Element versionEl = moduleIdEl.element("version");

		versionEl.setText(pluginPackage.getVersion());

		String content = doc.formattedString();

		FileUtil.write(geronimoWebXml, content);

		if (_log.isInfoEnabled()) {
			_log.info("Modifying Geronimo " + geronimoWebXml);
		}
	}

	public String updateLiferayWebXml(
			double webXmlVersion, File srcFile, String webXmlContent)
		throws Exception {

		boolean liferayWebXmlEnabled = true;

		Properties properties = getPluginPackageProperties(srcFile);

		if (properties != null) {
			liferayWebXmlEnabled = GetterUtil.getBoolean(
				properties.getProperty("liferay-web-xml-enabled"), true);
		}

		webXmlContent = WebXMLBuilder.organizeWebXML(webXmlContent);

		int x = webXmlContent.indexOf("<filter>");
		int y = webXmlContent.lastIndexOf("</filter-mapping>");

		String webXmlFiltersContent = StringPool.BLANK;

		if ((x == -1) || (y == -1)) {
			x = webXmlContent.lastIndexOf("</display-name>") + 15;
			y = x;
		}
		else {
			if (liferayWebXmlEnabled && (webXmlVersion > 2.3)) {
				webXmlFiltersContent = webXmlContent.substring(x, y + 17);

				y = y + 17;
			}
			else {
				x = y + 17;
				y = y + 17;
			}
		}

		if (webXmlVersion < 2.4) {
			webXmlContent =
				webXmlContent.substring(0, x) +
					getExtraFiltersContent(webXmlVersion, srcFile) +
						webXmlContent.substring(y);

			return webXmlContent;
		}

		String filtersContent =
			webXmlFiltersContent +
				getExtraFiltersContent(webXmlVersion, srcFile);

		String liferayWebXmlContent = FileUtil.read(
			DeployUtil.getResourcePath("web.xml"));

		int z = liferayWebXmlContent.indexOf("</web-app>");

		liferayWebXmlContent =
			liferayWebXmlContent.substring(0, z) + filtersContent +
				liferayWebXmlContent.substring(z);

		liferayWebXmlContent = WebXMLBuilder.organizeWebXML(
			liferayWebXmlContent);

		FileUtil.write(
			srcFile + "/WEB-INF/liferay-web.xml", liferayWebXmlContent);

		webXmlContent =
			webXmlContent.substring(0, x) + getInvokerFilterContent() +
				webXmlContent.substring(y);

		return webXmlContent;
	}

	public void updateWebXml(
			File webXml, File srcFile, String displayName,
			PluginPackage pluginPackage)
		throws Exception {

		String content = FileUtil.read(webXml);

		int x = content.indexOf("<display-name>");

		if (x != -1) {
			int y = content.indexOf("</display-name>", x);

			y = content.indexOf(">", y) + 1;

			content = content.substring(0, x) + content.substring(y);
		}

		double webXmlVersion = 2.3;

		Document webXmlDoc = SAXReaderUtil.read(content);

		Element webXmlRoot = webXmlDoc.getRootElement();

		webXmlVersion = GetterUtil.getDouble(
			webXmlRoot.attributeValue("version"), webXmlVersion);

		// Merge extra content

		String extraContent = getExtraContent(
			webXmlVersion, srcFile, displayName);

		int pos = content.indexOf("</web-app>");

		String newContent =
			content.substring(0, pos) + extraContent +
				content.substring(pos, content.length());

		// Replace old package names

		newContent = StringUtil.replace(
			newContent, "com.liferay.portal.shared.",
			"com.liferay.portal.kernel.");

		// Update liferay-web.xml

		newContent = updateLiferayWebXml(webXmlVersion, srcFile, newContent);

		// Update web.xml

		newContent = WebXMLBuilder.organizeWebXML(newContent);

		FileUtil.write(webXml, newContent, true);

		if (_log.isInfoEnabled()) {
			_log.info("Modifying Servlet " + webXmlVersion + " " + webXml);
		}
	}

	protected String appServerType;
	protected String auiTaglibDTD;
	protected String baseDir;
	protected String destDir;
	protected String filePattern;
	protected List<String> jars;
	protected String jbossPrefix;
	protected String portletExtTaglibDTD;
	protected String portletTaglibDTD;
	protected String securityTaglibDTD;
	protected String themeTaglibDTD;
	protected String tomcatLibDir;
	protected String uiTaglibDTD;
	protected boolean unpackWar;
	protected String utilTaglibDTD;
	protected List<String> wars;

	private static final String _PORTAL_CLASS_LOADER =
		"com.liferay.support.tomcat.loader.PortalClassLoader";

	private static Log _log = LogFactoryUtil.getLog(BaseDeployer.class);

}