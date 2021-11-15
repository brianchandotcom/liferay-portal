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

package com.liferay.portal.tools.deploy;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.xml.DocUtil;
import com.liferay.petra.xml.XMLUtil;
import com.liferay.portal.deploy.DeployUtil;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployer;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.License;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.servlet.PluginContextListener;
import com.liferay.portal.kernel.servlet.PortalClassLoaderServlet;
import com.liferay.portal.kernel.servlet.PortalDelegateServlet;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.servlet.SecurePluginContextListener;
import com.liferay.portal.kernel.servlet.SecureServlet;
import com.liferay.portal.kernel.servlet.SerializableSessionAttributeListener;
import com.liferay.portal.kernel.servlet.filters.invoker.InvokerFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portal.tools.WebXMLBuilder;
import com.liferay.portal.tools.deploy.extension.DeploymentExtension;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.webserver.DynamicResourceServlet;
import com.liferay.util.ant.CopyTask;
import com.liferay.util.ant.DeleteTask;
import com.liferay.util.ant.ExpandTask;
import com.liferay.util.ant.UpToDateTask;
import com.liferay.util.ant.WarTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.oro.io.GlobFilenameFilter;

/**
 * @author Brian Wing Shun Chan
 * @author Sandeep Soni
 */
public class BaseAutoDeployer implements AutoDeployer {

	public static final String DEPLOY_TO_PREFIX = "DEPLOY_TO__";

	public BaseAutoDeployer() {
		ServiceLoader<DeploymentExtension> serviceLoader = ServiceLoader.load(
			DeploymentExtension.class, BaseAutoDeployer.class.getClassLoader());

		for (DeploymentExtension deploymentExtension : serviceLoader) {
			_deploymentExtensions.add(deploymentExtension);
		}
	}

	@Override
	public void addRequiredJar(List<String> jars, String resource)
		throws Exception {

		String path = DeployUtil.getResourcePath(tempDirPaths, resource);

		if (path == null) {
			throw new RuntimeException(
				"Resource " + resource + " does not exist");
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Resource ", resource, " is available at ", path));
		}

		jars.add(path);
	}

	@Override
	public int autoDeploy(AutoDeploymentContext autoDeploymentContext)
		throws AutoDeployException {

		try {
			return _deployFile(autoDeploymentContext);
		}
		catch (Exception exception) {
			throw new AutoDeployException(exception);
		}
	}

	@Override
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

		if (!ServerDetector.isSupported(appServerType)) {
			throw new IllegalArgumentException(
				appServerType + " is not a valid application server type");
		}

		if (appServerType.equals(ServerDetector.WEBSPHERE_ID)) {
			unpackWar = false;
		}

		if (Validator.isNotNull(jbossPrefix) &&
			!Validator.isNumber(jbossPrefix)) {

			jbossPrefix = "1";
		}

		if (Validator.isNotNull(wildflyPrefix) &&
			!Validator.isNumber(wildflyPrefix)) {

			wildflyPrefix = "1";
		}
	}

	@Override
	public void close() throws IOException {
		UnsafeConsumer.accept(
			tempDirPaths, DeployUtil::deletePath, IOException.class);
	}

	@Override
	public void copyDependencyXml(String fileName, String targetDir)
		throws Exception {

		copyDependencyXml(fileName, targetDir, null);
	}

	@Override
	public void copyDependencyXml(
			String fileName, String targetDir, Map<String, String> filterMap)
		throws Exception {

		copyDependencyXml(fileName, targetDir, filterMap, false);
	}

	@Override
	public void copyDependencyXml(
			String fileName, String targetDir, Map<String, String> filterMap,
			boolean overwrite)
		throws Exception {

		DeployUtil.copyDependencyXml(
			fileName, targetDir, fileName, filterMap, overwrite);
	}

	public void copyTomcatContextXml(File targetDir) throws Exception {
		if (!appServerType.equals(ServerDetector.TOMCAT_ID)) {
			return;
		}

		File targetFile = new File(targetDir, "META-INF/context.xml");

		if (targetFile.exists()) {
			return;
		}

		String contextPath = DeployUtil.getResourcePath(
			tempDirPaths, "context.xml");

		String content = FileUtil.read(contextPath);

		if (!PropsValues.AUTO_DEPLOY_UNPACK_WAR) {
			content = StringUtil.removeSubstring(
				content, "antiResourceLocking=\"true\"");
		}

		FileUtil.write(targetFile, content);
	}

	@Override
	public void copyXmls(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		if (appServerType.equals(ServerDetector.JBOSS_ID) ||
			appServerType.equals(ServerDetector.WILDFLY_ID)) {

			File file = new File(PropsValues.LIFERAY_WEB_PORTAL_DIR);

			copyDependencyXml(
				"jboss-all.xml", srcFile + "/WEB-INF",
				Collections.singletonMap("root_war", file.getName()), true);

			copyDependencyXml(
				"jboss-deployment-structure.xml", srcFile + "/WEB-INF");
		}

		for (DeploymentExtension deploymentExtension : _deploymentExtensions) {
			if (Objects.equals(
					appServerType, deploymentExtension.getServerId())) {

				deploymentExtension.copyXmls(this, srcFile);
			}
		}

		copyDependencyXml("web.xml", srcFile + "/WEB-INF");
	}

	public void deployDirectory(
			File srcFile, String displayName, boolean override,
			PluginPackage pluginPackage)
		throws Exception {

		_deployDirectory(
			srcFile, null, null, displayName, override, pluginPackage);
	}

	public String getExtraContent(
			double webXmlVersion, File srcFile, String displayName)
		throws Exception {

		if (displayName.startsWith(StringPool.FORWARD_SLASH)) {
			displayName = displayName.substring(1);
		}

		StringBundler sb = new StringBundler(70);

		sb.append("<display-name>");
		sb.append(displayName);
		sb.append("</display-name>");

		if (webXmlVersion < 2.4) {
			sb.append("<context-param>");
			sb.append("<param-name>liferay-invoker-enabled</param-name>");
			sb.append("<param-value>false</param-value>");
			sb.append("</context-param>");
		}

		if (PropsValues.SESSION_VERIFY_SERIALIZABLE_ATTRIBUTE) {
			sb.append("<listener>");
			sb.append("<listener-class>");
			sb.append(SerializableSessionAttributeListener.class.getName());
			sb.append("</listener-class>");
			sb.append("</listener>");
		}

		sb.append(_getDynamicResourceServletContent());

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
			sb.append("/WEB-INF/tld/liferay-aui.tld");
			sb.append("</taglib-location>");
			sb.append("</taglib>");
		}

		if (Validator.isNotNull(portletTaglibDTD)) {
			sb.append("<taglib>");
			sb.append("<taglib-uri>http://java.sun.com/portlet_2_0");
			sb.append("</taglib-uri>");
			sb.append("<taglib-location>");
			sb.append("/WEB-INF/tld/liferay-portlet_2_0.tld");
			sb.append("</taglib-location>");
			sb.append("</taglib>");
			sb.append("<taglib>");
			sb.append("<taglib-uri>");
			sb.append("http://xmlns.jcp.org/portlet_3_0");
			sb.append("</taglib-uri>");
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

		return _getSessionFiltersContent();
	}

	public String getIgnoreFiltersContent(File srcFile) throws Exception {
		boolean ignoreFiltersEnabled = true;

		Properties properties = _getPluginPackageProperties(srcFile);

		if (properties != null) {
			ignoreFiltersEnabled = GetterUtil.getBoolean(
				properties.getProperty("ignore-filters-enabled"), true);
		}

		if (ignoreFiltersEnabled) {
			return FileUtil.read(
				DeployUtil.getResourcePath(
					tempDirPaths, "ignore-filters-web.xml"));
		}

		return StringPool.BLANK;
	}

	public String getPluginType() {
		return null;
	}

	public String getServletContextIncludeFiltersContent(
			double webXmlVersion, File srcFile)
		throws Exception {

		if (webXmlVersion < 2.4) {
			return StringPool.BLANK;
		}

		Properties properties = _getPluginPackageProperties(srcFile);

		if ((properties == null) ||
			!GetterUtil.getBoolean(
				properties.getProperty(
					"servlet-context-include-filters-enabled"),
				true)) {

			return StringPool.BLANK;
		}

		return FileUtil.read(
			DeployUtil.getResourcePath(
				tempDirPaths, "servlet-context-include-filters-web.xml"));
	}

	public String getSpeedFiltersContent(File srcFile) throws Exception {
		boolean speedFiltersEnabled = true;

		Properties properties = _getPluginPackageProperties(srcFile);

		if (properties != null) {
			speedFiltersEnabled = GetterUtil.getBoolean(
				properties.getProperty("speed-filters-enabled"), true);
		}

		if (speedFiltersEnabled) {
			return FileUtil.read(
				DeployUtil.getResourcePath(
					tempDirPaths, "speed-filters-web.xml"));
		}

		return StringPool.BLANK;
	}

	@Override
	public Map<String, String> processPluginPackageProperties(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		if (pluginPackage == null) {
			return null;
		}

		Properties properties = _getPluginPackageProperties(srcFile);

		if ((properties == null) || properties.isEmpty()) {
			return null;
		}

		Map<String, String> filterMap = _getPluginPackageXmlFilterMap(
			pluginPackage);

		if (filterMap == null) {
			return null;
		}

		copyDependencyXml(
			"liferay-plugin-package.xml", srcFile + "/WEB-INF", filterMap,
			true);

		return filterMap;
	}

	public void updateDeployDirectory(File srcFile) throws Exception {
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
	protected final Set<Path> tempDirPaths = new HashSet<>();
	protected String themeTaglibDTD;
	protected String tomcatLibDir;
	protected String uiTaglibDTD;
	protected boolean unpackWar;
	protected String utilTaglibDTD;
	protected String wildflyPrefix;

	private void _copyJars(File srcFile) throws Exception {
		for (String jarFullName : jars) {
			String jarName = jarFullName.substring(
				jarFullName.lastIndexOf("/") + 1);

			if (!FileUtil.exists(jarFullName)) {
				DeployUtil.getResourcePath(tempDirPaths, jarName);
			}

			FileUtil.copyFile(
				jarFullName, srcFile + "/WEB-INF/lib/" + jarName, false);
		}
	}

	private void _copyPortalDependencies(File srcFile) throws Exception {
		Properties properties = _getPluginPackageProperties(srcFile);

		if (properties == null) {
			return;
		}

		// jars

		String[] portalJars = StringUtil.split(
			properties.getProperty(
				"portal-dependency-jars",
				properties.getProperty("portal.dependency.jars")));

		for (String portalJar : portalJars) {
			portalJar = portalJar.trim();

			portalJar = _fixPortalDependencyJar(portalJar);

			if (_log.isDebugEnabled()) {
				_log.debug("Copy portal JAR " + portalJar);
			}

			try {
				String portalJarPath = PortalUtil.getPortalLibDir() + portalJar;

				FileUtil.copyFile(
					portalJarPath, srcFile + "/WEB-INF/lib/" + portalJar, true);
			}
			catch (Exception exception) {
				_log.error("Unable to copy portal JAR " + portalJar, exception);
			}
		}

		// tlds

		String[] portalTlds = StringUtil.split(
			properties.getProperty(
				"portal-dependency-tlds",
				properties.getProperty("portal.dependency.tlds")));

		for (String portalTld : portalTlds) {
			portalTld = portalTld.trim();

			if (_log.isDebugEnabled()) {
				_log.debug("Copy portal TLD " + portalTld);
			}

			try {
				String portalTldPath = DeployUtil.getResourcePath(
					tempDirPaths, portalTld);

				FileUtil.copyFile(
					portalTldPath, srcFile + "/WEB-INF/tld/" + portalTld, true);
			}
			catch (Exception exception) {
				_log.error("Unable to copy portal TLD " + portalTld, exception);
			}
		}

		// commons-logging*.jar

		File pluginLibDir = new File(srcFile + "/WEB-INF/lib/");

		if (PropsValues.AUTO_DEPLOY_COPY_COMMONS_LOGGING) {
			String[] commonsLoggingJars = pluginLibDir.list(
				new GlobFilenameFilter("commons-logging*.jar"));

			if (ArrayUtil.isEmpty(commonsLoggingJars)) {
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

			if (ArrayUtil.isEmpty(log4jJars)) {
				String portalJarPath =
					PortalUtil.getPortalLibDir() + "log4j-api.jar";

				FileUtil.copyFile(
					portalJarPath, srcFile + "/WEB-INF/lib/log4j-api.jar",
					true);

				portalJarPath =
					PortalUtil.getPortalLibDir() + "log4j-1.2-api.jar";

				FileUtil.copyFile(
					portalJarPath, srcFile + "/WEB-INF/lib/log4j-1.2-api.jar",
					true);

				portalJarPath = PortalUtil.getPortalLibDir() + "log4j-core.jar";

				FileUtil.copyFile(
					portalJarPath, srcFile + "/WEB-INF/lib/log4j-core.jar",
					true);
			}
		}
	}

	private void _copyProperties(File srcFile, PluginPackage pluginPackage)
		throws Exception {

		if (PropsValues.AUTO_DEPLOY_COPY_COMMONS_LOGGING) {
			copyDependencyXml(
				"logging.properties", srcFile + "/WEB-INF/classes");
		}

		if (PropsValues.AUTO_DEPLOY_COPY_LOG4J) {
			copyDependencyXml(
				"log4j2.properties", srcFile + "/WEB-INF/classes");
		}

		File servicePropertiesFile = new File(
			srcFile.getAbsolutePath() + "/WEB-INF/classes/service.properties");

		if (!servicePropertiesFile.exists()) {
			return;
		}

		File portletPropertiesFile = new File(
			srcFile.getAbsolutePath() + "/WEB-INF/classes/portlet.properties");

		if (portletPropertiesFile.exists()) {
			return;
		}

		String pluginPackageName = null;

		if (pluginPackage != null) {
			pluginPackageName = pluginPackage.getName();
		}
		else {
			pluginPackageName = srcFile.getName();
		}

		FileUtil.write(
			portletPropertiesFile, "plugin.package.name=" + pluginPackageName);
	}

	private void _copyTlds(File srcFile) throws Exception {
		if (Validator.isNotNull(auiTaglibDTD)) {
			FileUtil.copyFile(
				auiTaglibDTD, srcFile + "/WEB-INF/tld/liferay-aui.tld", true);
		}

		if (Validator.isNotNull(portletTaglibDTD)) {
			FileUtil.copyFile(
				portletTaglibDTD, srcFile + "/WEB-INF/tld/liferay-portlet.tld",
				true);
			FileUtil.copyFile(
				DeployUtil.getResourcePath(
					tempDirPaths, "liferay-portlet_2_0.tld"),
				srcFile + "/WEB-INF/tld/liferay-portlet_2_0.tld", true);
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

	private void _deployDirectory(
			File srcFile, File mergeDir, File deployDir, String displayName,
			boolean overwrite, PluginPackage pluginPackage)
		throws Exception {

		_rewriteFiles(srcFile);

		_mergeDirectory(mergeDir, srcFile);

		processPluginPackageProperties(srcFile, displayName, pluginPackage);

		_copyJars(srcFile);
		_copyProperties(srcFile, pluginPackage);
		_copyTlds(srcFile);
		copyXmls(srcFile, displayName, pluginPackage);
		_copyPortalDependencies(srcFile);

		File webXml = new File(srcFile + "/WEB-INF/web.xml");

		_updateWebXml(webXml, srcFile, displayName);

		if ((deployDir == null) || baseDir.equals(destDir)) {
			return;
		}

		updateDeployDirectory(srcFile);

		String excludes = StringPool.BLANK;

		if (appServerType.equals(ServerDetector.JBOSS_ID) ||
			appServerType.equals(ServerDetector.WILDFLY_ID)) {

			excludes = StringBundler.concat(
				excludes, "**/WEB-INF/lib/log4j-1.2-api.jar,",
				"**/WEB-INF/lib/log4j-api.jar,**/WEB-INF/lib/log4j-core.jar,");
		}
		else if (appServerType.equals(ServerDetector.TOMCAT_ID)) {
			String[] libs = FileUtil.listFiles(tomcatLibDir);

			for (String lib : libs) {
				excludes += "**/WEB-INF/lib/" + lib + ",";
			}

			File contextXml = new File(srcFile + "/META-INF/context.xml");

			if (contextXml.exists()) {
				String content = FileUtil.read(contextXml);

				if (content.contains(_PORTAL_CLASS_LOADER)) {
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
			catch (ClassNotFoundException classNotFoundException) {
				if (_log.isDebugEnabled()) {
					_log.debug(classNotFoundException, classNotFoundException);
				}
			}
		}

		// LPS-11268

		Properties properties = _getPluginPackageProperties(srcFile);

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

		if (!unpackWar) {
			Path tempDirPath = Files.createTempDirectory(
				Paths.get(SystemProperties.get(SystemProperties.TMP_DIR)),
				null);

			File tempDir = tempDirPath.toFile();

			excludes += "/WEB-INF/web.xml";

			File tempFile = new File(tempDir, displayName + ".war");

			WarTask.war(srcFile, tempFile, excludes, webXml);

			if (_isJEEDeploymentEnabled()) {
				File tempWarDir = new File(
					tempDir.getParent(), deployDir.getName());

				if (tempWarDir.exists()) {
					tempWarDir.delete();
				}

				if (!tempDir.renameTo(tempWarDir)) {
					tempWarDir = tempDir;
				}

				DeploymentHandler deploymentHandler = _getDeploymentHandler();

				deploymentHandler.deploy(tempWarDir, displayName);

				deploymentHandler.releaseDeploymentManager();

				DeleteTask.deleteDirectory(tempWarDir);
			}
			else {
				if (!tempFile.renameTo(deployDir)) {
					WarTask.war(srcFile, deployDir, excludes, webXml);
				}

				if (tempDir.isDirectory()) {
					DeleteTask.deleteDirectory(tempDir);
				}
				else {
					tempDir.delete();
				}
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
	}

	private int _deployFile(AutoDeploymentContext autoDeploymentContext)
		throws Exception {

		File srcFile = autoDeploymentContext.getFile();

		PluginPackage pluginPackage = autoDeploymentContext.getPluginPackage();

		if (pluginPackage == null) {
			pluginPackage = _readPluginPackage(srcFile);

			autoDeploymentContext.setPluginPackage(pluginPackage);
		}

		if (_log.isInfoEnabled()) {
			_log.info("Deploying " + srcFile.getName());
		}

		String specifiedContext = autoDeploymentContext.getContext();

		String displayName = specifiedContext;

		boolean overwrite = false;
		String preliminaryContext = specifiedContext;

		// The order of priority of the context is: 1.) the specified context,
		// 2.) if the file name starts with DEPLOY_TO_PREFIX, use the file name
		// after the prefix, or 3.) the recommended deployment context as
		// specified in liferay-plugin-package.properties, or 4.) the file name.

		String srcFileName = srcFile.getName();

		if (Validator.isNull(specifiedContext) &&
			srcFileName.startsWith(DEPLOY_TO_PREFIX)) {

			displayName = srcFileName.substring(
				DEPLOY_TO_PREFIX.length(), srcFileName.length() - 4);

			overwrite = true;
			preliminaryContext = displayName;
		}

		if (preliminaryContext == null) {
			preliminaryContext = _getDisplayName(srcFile);
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
				displayName = _getDisplayName(srcFile);
			}

			pluginPackage.setContext(displayName);

			PluginPackageUtil.updateInstallingPluginPackage(
				preliminaryContext, pluginPackage);
		}

		String deployDir = null;

		if (Validator.isNotNull(displayName)) {
			deployDir = displayName + ".war";
		}
		else {
			deployDir = srcFile.getName();
			displayName = _getDisplayName(srcFile);
		}

		if (appServerType.equals(ServerDetector.JBOSS_ID)) {
			deployDir = jbossPrefix + deployDir;
		}
		else if (appServerType.equals(ServerDetector.WILDFLY_ID)) {
			deployDir = GetterUtil.getString(wildflyPrefix) + deployDir;
		}
		else if (appServerType.equals(ServerDetector.TOMCAT_ID) ||
				 appServerType.equals(ServerDetector.WEBLOGIC_ID)) {

			if (unpackWar) {
				deployDir = deployDir.substring(0, deployDir.length() - 4);
			}
		}

		String destDir = this.destDir;

		if (autoDeploymentContext.getDestDir() != null) {
			destDir = autoDeploymentContext.getDestDir();
		}

		File deployDirFile = new File(destDir + "/" + deployDir);

		try {
			PluginPackage previousPluginPackage = _readPluginPackage(
				deployDirFile);

			if ((pluginPackage != null) && (previousPluginPackage != null)) {
				String name = pluginPackage.getName();
				String previousVersion = previousPluginPackage.getVersion();
				String version = pluginPackage.getVersion();

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Updating ", name, " from version ",
							previousVersion, " to version ", version));
				}

				if (pluginPackage.isPreviousVersionThan(
						previousPluginPackage)) {

					if (_log.isInfoEnabled()) {
						_log.info(
							StringBundler.concat(
								"Not updating ", name, " because version ",
								previousVersion, " is newer than version ",
								version));
					}

					return AutoDeployer.CODE_SKIP_NEWER_VERSION;
				}

				overwrite = true;
			}

			File mergeDirFile = new File(
				srcFile.getParent() + "/merge/" + srcFile.getName());

			if (srcFile.isDirectory()) {
				_deployDirectory(
					srcFile, mergeDirFile, deployDirFile, displayName,
					overwrite, pluginPackage);
			}
			else {
				boolean deployed = _deployFile(
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
					_postDeploy(destDir, deployDir);
				}
			}

			return AutoDeployer.CODE_DEFAULT;
		}
		catch (Exception exception) {
			if (pluginPackage != null) {
				PluginPackageUtil.endPluginPackageInstallation(
					pluginPackage.getContext());
			}

			throw exception;
		}
	}

	private boolean _deployFile(
			File srcFile, File mergeDir, File deployDir, String displayName,
			boolean overwrite, PluginPackage pluginPackage)
		throws Exception {

		if (!overwrite && UpToDateTask.isUpToDate(srcFile, deployDir)) {
			if (_log.isInfoEnabled()) {
				_log.info(deployDir + " is already up to date");
			}

			return false;
		}

		Path tempDirPath = Files.createTempDirectory(
			Paths.get(SystemProperties.get(SystemProperties.TMP_DIR)), null);

		File tempDir = tempDirPath.toFile();

		ExpandTask.expand(srcFile, tempDir);

		_deployDirectory(
			tempDir, mergeDir, deployDir, displayName, overwrite,
			pluginPackage);

		DeleteTask.deleteDirectory(tempDir);

		return true;
	}

	private String _fixPortalDependencyJar(String portalJar) {
		if (portalJar.equals("antlr.jar")) {
			portalJar = "antlr2.jar";
		}

		return portalJar;
	}

	private DeploymentHandler _getDeploymentHandler() {
		String prefix = "auto.deploy." + ServerDetector.getServerId() + ".jee.";

		String dmId = PropsUtil.get(prefix + "dm.id");
		String dmUser = PropsUtil.get(prefix + "dm.user");
		String dmPassword = PropsUtil.get(prefix + "dm.passwd");
		String dfClassName = PropsUtil.get(prefix + "df.classname");

		return new DeploymentHandler(dmId, dmUser, dmPassword, dfClassName);
	}

	private String _getDisplayName(File srcFile) {
		String displayName = srcFile.getName();

		if (StringUtil.endsWith(displayName, ".war") ||
			StringUtil.endsWith(displayName, ".xml")) {

			displayName = displayName.substring(0, displayName.length() - 4);
		}

		if ((appServerType.equals(ServerDetector.JBOSS_ID) &&
			 Validator.isNotNull(jbossPrefix) &&
			 displayName.startsWith(jbossPrefix)) ||
			(appServerType.equals(ServerDetector.WILDFLY_ID) &&
			 Validator.isNotNull(wildflyPrefix) &&
			 displayName.startsWith(wildflyPrefix))) {

			displayName = displayName.substring(1);
		}

		return displayName;
	}

	private String _getDynamicResourceServletContent() {
		StringBundler sb = new StringBundler();

		sb.append("<servlet>");
		sb.append("<servlet-name>");
		sb.append("Dynamic Resource Servlet");
		sb.append("</servlet-name>");
		sb.append("<servlet-class>");
		sb.append(PortalClassLoaderServlet.class.getName());
		sb.append("</servlet-class>");
		sb.append("<init-param>");
		sb.append("<param-name>");
		sb.append("servlet-class");
		sb.append("</param-name>");
		sb.append("<param-value>");
		sb.append(DynamicResourceServlet.class.getName());
		sb.append("</param-value>");
		sb.append("</init-param>");
		sb.append("<load-on-startup>1</load-on-startup>");
		sb.append("</servlet>");

		for (String allowedPath :
				PropsValues.DYNAMIC_RESOURCE_SERVLET_ALLOWED_PATHS) {

			sb.append("<servlet-mapping>");
			sb.append("<servlet-name>");
			sb.append("Dynamic Resource Servlet");
			sb.append("</servlet-name>");
			sb.append("<url-pattern>");
			sb.append(allowedPath);

			if (!allowedPath.endsWith(StringPool.SLASH)) {
				sb.append(StringPool.SLASH);
			}

			sb.append("*</url-pattern>");
			sb.append("</servlet-mapping>");
		}

		return sb.toString();
	}

	private String _getInvokerFilterContent() {
		return StringBundler.concat(
			_getInvokerFilterContent("ASYNC"),
			_getInvokerFilterContent("ERROR"),
			_getInvokerFilterContent("FORWARD"),
			_getInvokerFilterContent("INCLUDE"),
			_getInvokerFilterContent("REQUEST"));
	}

	private String _getInvokerFilterContent(String dispatcher) {
		StringBundler sb = new StringBundler(24);

		sb.append("<filter>");
		sb.append("<filter-name>Invoker Filter - ");
		sb.append(dispatcher);
		sb.append("</filter-name>");
		sb.append("<filter-class>");
		sb.append(InvokerFilter.class.getName());
		sb.append("</filter-class>");
		sb.append("<async-supported>true</async-supported>");
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

	private String _getPluginPackageLicensesXml(List<License> licenses) {
		if (licenses.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler((5 * licenses.size()) + 2);

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

	private String _getPluginPackageLiferayVersionsXml(
		List<String> liferayVersions) {

		if (liferayVersions.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler((liferayVersions.size() * 3) + 2);

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

	private Properties _getPluginPackageProperties(File srcFile)
		throws Exception {

		File propertiesFile = new File(
			srcFile + "/WEB-INF/liferay-plugin-package.properties");

		if (!propertiesFile.exists()) {
			return null;
		}

		String propertiesString = FileUtil.read(propertiesFile);

		return PropertiesUtil.load(propertiesString);
	}

	private String _getPluginPackageRequiredDeploymentContextsXml(
		List<String> requiredDeploymentContexts) {

		if (requiredDeploymentContexts.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(
			(requiredDeploymentContexts.size() * 3) + 2);

		for (int i = 0; i < requiredDeploymentContexts.size(); i++) {
			String requiredDeploymentContext = requiredDeploymentContexts.get(
				i);

			if (i == 0) {
				sb.append("\r\n");
			}

			sb.append("\t\t<required-deployment-context>");
			sb.append(requiredDeploymentContext);
			sb.append("</required-deployment-context>\r\n");

			if ((i + 1) == requiredDeploymentContexts.size()) {
				sb.append("\t");
			}
		}

		return sb.toString();
	}

	private String _getPluginPackageTagsXml(List<String> tags) {
		if (tags.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler((tags.size() * 3) + 2);

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

	private Map<String, String> _getPluginPackageXmlFilterMap(
		PluginPackage pluginPackage) {

		List<String> pluginTypes = pluginPackage.getTypes();

		String pluginType = pluginTypes.get(0);

		if (!pluginType.equals(getPluginType())) {
			return null;
		}

		return HashMapBuilder.put(
			"author", _wrapCDATA(pluginPackage.getAuthor())
		).put(
			"change_log", _wrapCDATA(pluginPackage.getChangeLog())
		).put(
			"licenses",
			_getPluginPackageLicensesXml(pluginPackage.getLicenses())
		).put(
			"liferay_versions",
			_getPluginPackageLiferayVersionsXml(
				pluginPackage.getLiferayVersions())
		).put(
			"long_description", _wrapCDATA(pluginPackage.getLongDescription())
		).put(
			"module_artifact_id", pluginPackage.getArtifactId()
		).put(
			"module_group_id", pluginPackage.getGroupId()
		).put(
			"module_version", pluginPackage.getVersion()
		).put(
			"page_url", pluginPackage.getPageURL()
		).put(
			"plugin_name", _wrapCDATA(pluginPackage.getName())
		).put(
			"plugin_type", pluginType
		).put(
			"plugin_type_name",
			TextFormatter.format(pluginType, TextFormatter.J)
		).put(
			"recommended_deployment_context",
			pluginPackage.getRecommendedDeploymentContext()
		).put(
			"required_deployment_contexts",
			_getPluginPackageRequiredDeploymentContextsXml(
				pluginPackage.getRequiredDeploymentContexts())
		).put(
			"short_description", _wrapCDATA(pluginPackage.getShortDescription())
		).put(
			"tags", _getPluginPackageTagsXml(pluginPackage.getTags())
		).build();
	}

	private String _getSessionFiltersContent() throws Exception {
		return FileUtil.read(
			DeployUtil.getResourcePath(
				tempDirPaths, "session-filters-web.xml"));
	}

	private boolean _isJEEDeploymentEnabled() {
		return GetterUtil.getBoolean(
			PropsUtil.get(
				"auto.deploy." + ServerDetector.getServerId() +
					".jee.deployment.enabled"));
	}

	private void _mergeDirectory(File mergeDir, File targetDir) {
		if ((mergeDir == null) || !mergeDir.exists()) {
			return;
		}

		CopyTask.copyDirectory(mergeDir, targetDir, null, null, true, false);
	}

	private void _postDeploy(String destDir, String deployDir)
		throws Exception {

		if (appServerType.equals(ServerDetector.JBOSS_ID)) {
			_postDeployJBoss(destDir, deployDir);
		}
		else if (appServerType.equals(ServerDetector.WILDFLY_ID)) {
			_postDeployWildfly(destDir, deployDir);
		}

		for (DeploymentExtension deploymentExtension : _deploymentExtensions) {
			if (Objects.equals(
					appServerType, deploymentExtension.getServerId())) {

				deploymentExtension.postDeploy(destDir, deployDir);
			}
		}
	}

	private void _postDeployJBoss(String destDir, String deployDir)
		throws Exception {

		FileUtil.write(
			StringBundler.concat(destDir, "/", deployDir, ".dodeploy"),
			StringPool.BLANK);
	}

	private void _postDeployWildfly(String destDir, String deployDir)
		throws Exception {

		FileUtil.write(
			StringBundler.concat(destDir, "/", deployDir, ".dodeploy"),
			StringPool.BLANK);
	}

	private PluginPackage _readPluginPackage(File file) {
		if (!file.exists()) {
			return null;
		}

		InputStream inputStream = null;
		ZipFile zipFile = null;

		try {
			boolean parseProps = false;

			if (file.isDirectory()) {
				String path = file.getPath();

				File pluginPackageXmlFile = new File(
					StringBundler.concat(
						file.getParent(), "/merge/", file.getName(),
						"/WEB-INF/liferay-plugin-package.xml"));

				if (pluginPackageXmlFile.exists()) {
					inputStream = new FileInputStream(pluginPackageXmlFile);
				}
				else {
					pluginPackageXmlFile = new File(
						path + "/WEB-INF/liferay-plugin-package.xml");

					if (pluginPackageXmlFile.exists()) {
						inputStream = new FileInputStream(pluginPackageXmlFile);
					}
				}

				File pluginPackagePropertiesFile = new File(
					StringBundler.concat(
						file.getParent(), "/merge/", file.getName(),
						"/WEB-INF/liferay-plugin-package.properties"));

				if ((inputStream == null) &&
					pluginPackagePropertiesFile.exists()) {

					inputStream = new FileInputStream(
						pluginPackagePropertiesFile);

					parseProps = true;
				}
				else {
					pluginPackagePropertiesFile = new File(
						path + "/WEB-INF/liferay-plugin-package.properties");

					if ((inputStream == null) &&
						pluginPackagePropertiesFile.exists()) {

						inputStream = new FileInputStream(
							pluginPackagePropertiesFile);

						parseProps = true;
					}
				}
			}
			else {
				zipFile = new ZipFile(file);

				File pluginPackageXmlFile = new File(
					StringBundler.concat(
						file.getParent(), "/merge/", file.getName(),
						"/WEB-INF/liferay-plugin-package.xml"));

				if (pluginPackageXmlFile.exists()) {
					inputStream = new FileInputStream(pluginPackageXmlFile);
				}
				else {
					ZipEntry zipEntry = zipFile.getEntry(
						"WEB-INF/liferay-plugin-package.xml");

					if (zipEntry != null) {
						inputStream = zipFile.getInputStream(zipEntry);
					}
				}

				File pluginPackagePropertiesFile = new File(
					StringBundler.concat(
						file.getParent(), "/merge/", file.getName(),
						"/WEB-INF/liferay-plugin-package.properties"));

				if ((inputStream == null) &&
					pluginPackagePropertiesFile.exists()) {

					inputStream = new FileInputStream(
						pluginPackagePropertiesFile);

					parseProps = true;
				}
				else {
					ZipEntry zipEntry = zipFile.getEntry(
						"WEB-INF/liferay-plugin-package.properties");

					if ((inputStream == null) && (zipEntry != null)) {
						inputStream = zipFile.getInputStream(zipEntry);

						parseProps = true;
					}
				}
			}

			if (inputStream == null) {
				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							file.getPath(), " does not have a ",
							"WEB-INF/liferay-plugin-package.xml or ",
							"WEB-INF/liferay-plugin-package.properties"));
				}

				return null;
			}

			if (parseProps) {
				String propertiesString = StringUtil.read(inputStream);

				Properties properties = PropertiesUtil.load(propertiesString);

				return PluginPackageUtil.readPluginPackageProperties(
					_getDisplayName(file), properties);
			}

			String xml = StringUtil.read(inputStream);

			xml = XMLUtil.fixProlog(xml);

			return PluginPackageUtil.readPluginPackageXml(xml);
		}
		catch (Exception exception) {
			_log.error(file.getPath() + ": " + exception.toString(), exception);
		}
		finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				}
				catch (IOException ioException) {
					if (_log.isDebugEnabled()) {
						_log.debug(ioException, ioException);
					}
				}
			}

			if (zipFile != null) {
				try {
					zipFile.close();
				}
				catch (IOException ioException) {
					if (_log.isDebugEnabled()) {
						_log.debug(ioException, ioException);
					}
				}
			}
		}

		return null;
	}

	private void _rewriteFiles(File srcDir) throws Exception {
		String[] fileNames = FileUtil.listFiles(srcDir + "/WEB-INF/");

		for (String fileName : fileNames) {
			String shortFileName = GetterUtil.getString(
				FileUtil.getShortFileName(fileName));

			// LEP-6415

			if (StringUtil.equalsIgnoreCase(shortFileName, "mule-config.xml")) {
				continue;
			}

			String ext = GetterUtil.getString(FileUtil.getExtension(fileName));

			if (!StringUtil.equalsIgnoreCase(ext, "xml")) {
				continue;
			}

			// Make sure to rewrite any XML files to include external entities
			// into same file. See LEP-3142.

			File file = new File(srcDir + "/WEB-INF/" + fileName);

			try {
				Document doc = UnsecureSAXReaderUtil.read(file);

				String content = doc.formattedString(StringPool.TAB, true);

				FileUtil.write(file, content);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Unable to format ", file, ": ",
							exception.getMessage()));
				}
			}
		}
	}

	private String _secureWebXml(
			String content, boolean hasCustomServletListener,
			boolean securityManagerEnabled)
		throws Exception {

		if (!hasCustomServletListener && !securityManagerEnabled) {
			return content;
		}

		Document document = UnsecureSAXReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		List<String> listenerClasses = new ArrayList<>();

		List<Element> listenerElements = rootElement.elements("listener");

		for (Element listenerElement : listenerElements) {
			String listenerClass = GetterUtil.getString(
				listenerElement.elementText("listener-class"));

			if (listenerClass.equals(PluginContextListener.class.getName()) ||
				listenerClass.equals(
					SecurePluginContextListener.class.getName())) {

				continue;
			}

			listenerClasses.add(listenerClass);

			listenerElement.detach();
		}

		Element contextParamElement = rootElement.addElement("context-param");

		DocUtil.add(contextParamElement, "param-name", "portalListenerClasses");
		DocUtil.add(
			contextParamElement, "param-value",
			StringUtil.merge(listenerClasses));

		if (!securityManagerEnabled) {
			return document.compactString();
		}

		List<Element> servletElements = rootElement.elements("servlet");

		for (Element servletElement : servletElements) {
			Element servletClassElement = servletElement.element(
				"servlet-class");

			String servletClass = GetterUtil.getString(
				servletClassElement.getText());

			if (servletClass.equals(PortalClassLoaderServlet.class.getName()) ||
				servletClass.equals(PortalDelegateServlet.class.getName()) ||
				servletClass.equals(PortletServlet.class.getName())) {

				continue;
			}

			servletClassElement.setText(SecureServlet.class.getName());

			Element initParamElement = servletElement.addElement("init-param");

			DocUtil.add(initParamElement, "param-name", "servlet-class");
			DocUtil.add(initParamElement, "param-value", servletClass);
		}

		return document.compactString();
	}

	private String _updateLiferayWebXml(
			double webXmlVersion, File srcFile, String webXmlContent)
		throws Exception {

		boolean liferayWebXmlEnabled = true;

		Properties properties = _getPluginPackageProperties(srcFile);

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
			return webXmlContent.substring(0, x) +
				getExtraFiltersContent(webXmlVersion, srcFile) +
					webXmlContent.substring(y);
		}

		String filtersContent =
			webXmlFiltersContent +
				getExtraFiltersContent(webXmlVersion, srcFile);

		String liferayWebXmlContent = FileUtil.read(
			DeployUtil.getResourcePath(tempDirPaths, "web.xml"));

		int z = liferayWebXmlContent.indexOf("</web-app>");

		liferayWebXmlContent =
			liferayWebXmlContent.substring(0, z) + filtersContent +
				liferayWebXmlContent.substring(z);

		liferayWebXmlContent = WebXMLBuilder.organizeWebXML(
			liferayWebXmlContent);

		FileUtil.write(
			srcFile + "/WEB-INF/liferay-web.xml", liferayWebXmlContent);

		return webXmlContent.substring(0, x) + _getInvokerFilterContent() +
			webXmlContent.substring(y);
	}

	private void _updateWebXml(File webXml, File srcFile, String displayName)
		throws Exception {

		// Check version

		String content = FileUtil.read(webXml);

		content = WebXMLBuilder.organizeWebXML(content);

		int x = content.indexOf("<display-name>");

		if (x != -1) {
			int y = content.indexOf("</display-name>", x);

			y = content.indexOf(">", y) + 1;

			content = content.substring(0, x) + content.substring(y);
		}

		Document document = UnsecureSAXReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		double webXmlVersion = GetterUtil.getDouble(
			rootElement.attributeValue("version"), 2.3);

		if (webXmlVersion <= 2.3) {
			throw new AutoDeployException(
				webXml.getName() +
					" must be updated to the Servlet 2.4 specification");
		}

		// Plugin context listener

		StringBundler sb = new StringBundler(5);

		sb.append("<listener>");
		sb.append("<listener-class>");

		boolean hasCustomServletListener = false;

		List<Element> listenerElements = rootElement.elements("listener");

		for (Element listenerElement : listenerElements) {
			String listenerClass = GetterUtil.getString(
				listenerElement.elementText("listener-class"));

			if (listenerClass.equals(PluginContextListener.class.getName()) ||
				listenerClass.equals(
					SerializableSessionAttributeListener.class.getName()) ||
				listenerClass.equals(
					SecurePluginContextListener.class.getName())) {

				continue;
			}

			hasCustomServletListener = true;

			break;
		}

		boolean securityManagerEnabled = false;

		Properties properties = _getPluginPackageProperties(srcFile);

		if (properties != null) {
			securityManagerEnabled = GetterUtil.getBoolean(
				properties.getProperty("security-manager-enabled"));
		}

		if (hasCustomServletListener || securityManagerEnabled) {
			sb.append(SecurePluginContextListener.class.getName());
		}
		else {
			sb.append(PluginContextListener.class.getName());
		}

		sb.append("</listener-class>");
		sb.append("</listener>");

		String pluginContextListenerContent = sb.toString();

		// Merge content

		String extraContent = getExtraContent(
			webXmlVersion, srcFile, displayName);

		int pos = content.indexOf("<listener>");

		if (pos == -1) {
			pos = content.indexOf("</web-app>");
		}

		String newContent = StringBundler.concat(
			content.substring(0, pos), pluginContextListenerContent,
			extraContent, content.substring(pos));

		// Update liferay-web.xml

		newContent = _updateLiferayWebXml(webXmlVersion, srcFile, newContent);

		// Update web.xml

		newContent = _secureWebXml(
			newContent, hasCustomServletListener, securityManagerEnabled);

		newContent = WebXMLBuilder.organizeWebXML(newContent);

		FileUtil.write(webXml, newContent, true);

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Modifying Servlet ", webXmlVersion, " ", webXml));
		}
	}

	private String _wrapCDATA(String string) {
		return StringPool.CDATA_OPEN + string + StringPool.CDATA_CLOSE;
	}

	private static final String _PORTAL_CLASS_LOADER =
		"com.liferay.support.tomcat.loader.PortalClassLoader";

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAutoDeployer.class);

	private final List<DeploymentExtension> _deploymentExtensions =
		new ArrayList<>();

}