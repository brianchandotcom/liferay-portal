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

package com.liferay.gradle.plugins.tasks;

import aQute.bnd.osgi.Constants;

import com.liferay.gradle.plugins.LiferayJavaPlugin;
import com.liferay.gradle.plugins.LiferayPlugin;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.extensions.LiferayOSGiExtension;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;

import groovy.util.Node;
import groovy.util.NodeList;
import groovy.util.XmlParser;

import java.io.File;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import nebula.plugin.extraconfigurations.OptionalBasePlugin;
import nebula.plugin.extraconfigurations.ProvidedBasePlugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Brian Wing Shun Chan
 * @author Chas Austin
 * @author Andrea Di Giorgi
 */
public class InitGradleTask extends DefaultTask {

	public static final String[] SOURCE_FILE_NAMES = {
		"build.xml", "docroot/WEB-INF/liferay-plugin-package.properties",
		"ivy.xml"
	};

	public InitGradleTask() {
		portalDependencyNotation("antlr2.jar", "antlr", "antlr", "2.7.7");
		portalDependencyNotation(
			"axis.jar", "com.liferay", "org.apache.axis",
			"1.4.LIFERAY-PATCHED-1");
		portalDependencyNotation(
			"backport-concurrent.jar", "backport-util-concurrent",
			"backport-util-concurrent", "3.1");
		portalDependencyNotation("bsf.jar", "bsf", "bsf", "2.4.0");
		portalDependencyNotation(
			"commons-beanutils.jar", "commons-beanutils", "commons-beanutils",
			"1.8.2");
		portalDependencyNotation(
			"commons-chain.jar", "commons-chain", "commons-chain", "1.2");
		portalDependencyNotation(
			"commons-codec.jar", "commons-codec", "commons-codec", "1.9");
		portalDependencyNotation(
			"commons-collections.jar", "commons-collections",
			"commons-collections", "3.2.1");
		portalDependencyNotation(
			"commons-digester.jar", "commons-digester", "commons-digester",
			"1.8");
		portalDependencyNotation(
			"commons-discovery.jar", "commons-discovery", "commons-discovery",
			"0.4");
		portalDependencyNotation(
			"commons-fileupload.jar", "commons-fileupload",
			"commons-fileupload", "1.3.1");
		portalDependencyNotation(
			"commons-httpclient.jar", "commons-httpclient",
			"commons-httpclient", "3.1");
		portalDependencyNotation(
			"commons-io.jar", "commons-io", "commons-io", "2.1");
		portalDependencyNotation(
			"commons-lang.jar", "commons-lang", "commons-lang", "2.6");
		portalDependencyNotation(
			"commons-logging.jar", "commons-logging", "commons-logging",
			"1.1.3");
		portalDependencyNotation(
			"commons-math.jar", "org.apache.commons", "commons-math", "2.0");
		portalDependencyNotation(
			"commons-validator.jar", "commons-validator", "commons-validator",
			"1.3.1");
		portalDependencyNotation("dom4j.jar", "dom4j", "dom4j", "1.6.1");
		portalDependencyNotation(
			"hibernate-core.jar", "org.hibernate", "hibernate-core",
			"3.6.10.Final");
		portalDependencyNotation(
			"ical4j.jar", "org.mnode.ical4j", "ical4j", "1.0.2");
		portalDependencyNotation(
			"jabsorb.jar", "org.jabsorb", "jabsorb", "1.3.1");
		portalDependencyNotation("jaxrpc.jar", "javax.xml", "jaxrpc", "1.1");
		portalDependencyNotation("jcommon.jar", "jfree", "jcommon", "1.0.16");
		portalDependencyNotation(
			"jdom.jar", "org.jopendocument", "jdom", "1.1.1");
		portalDependencyNotation(
			"jericho-html.jar", "net.htmlparser.jericho", "jericho-html",
			"3.1");
		portalDependencyNotation(
			"jfreechart.jar", "jfree", "jfreechart", "1.0.13");
		portalDependencyNotation("jruby.jar", "org.jruby", "jruby", "1.6.5");
		portalDependencyNotation("json-java.jar", "org.json", "json", "+");
		portalDependencyNotation(
			"jstl-api.jar", "javax.servlet.jsp.jstl", "jstl-api", "1.2");
		portalDependencyNotation(
			"jstl-impl.jar", "org.glassfish.web", "jstl-impl", "1.2");
		portalDependencyNotation(
			"jutf7.jar", "com.beetstra.jutf7", "jutf7", "0.9.0");
		portalDependencyNotation(
			"nekohtml.jar", "net.sourceforge.nekohtml", "nekohtml", "1.9.14");
		portalDependencyNotation("oro.jar", "oro", "oro", "2.0.8");
		portalDependencyNotation(
			"portals-bridges.jar", "org.apache.portals.bridges",
			"portals-bridges-common", "1.0");
		portalDependencyNotation("rhino.jar", "org.mozilla", "rhino", "1.7R4");
		portalDependencyNotation("rome.jar", "rome", "rome", "1.0");
		portalDependencyNotation(
			"saaj-api.jar", "javax.xml.soap", "saaj-api", "1.3");
		portalDependencyNotation(
			"saaj-impl.jar", "com.sun.xml.messaging.saaj", "saaj-impl", "1.3");
		portalDependencyNotation(
			"slf4j-api.jar", "org.slf4j", "slf4j-api", "1.7.2");
		portalDependencyNotation(
			"struts-core.jar", "org.apache.struts", "struts-core", "1.3.10");
		portalDependencyNotation(
			"struts-extras.jar", "org.apache.struts", "struts-extras",
			"1.3.10");
		portalDependencyNotation(
			"struts-taglib.jar", "org.apache.struts", "struts-taglib",
			"1.3.10");
		portalDependencyNotation(
			"struts-tiles.jar", "org.apache.struts", "struts-tiles", "1.3.10");
		portalDependencyNotation(
			"util-slf4j.jar", "com.liferay.portal", "util-slf4j", "default");
		portalDependencyNotation("wsdl4j.jar", "wsdl4j", "wsdl4j", "1.6.1");
		portalDependencyNotation(
			"xercesImpl.jar", "xerces", "xercesImpl", "2.11.0");
		portalDependencyNotation(
			"xml-apis.jar", "xml-apis", "xml-apis", "1.4.01");
		portalDependencyNotation(
			"xmlsec.jar", "org.apache.santuario", "xmlsec", "1.4.5");
	}

	public String[] getPortalDependencyNotation(String fileName) {
		return _portalDependencyNotations.get(fileName);
	}

	@TaskAction
	public void initGradle() throws Exception {
		_project = getProject();

		File buildGradleFile = _project.file("build.gradle");

		if (!isOverwrite() && buildGradleFile.exists() &&
			(buildGradleFile.length() > 0)) {

			_logger.error(
				"Unable to automatically upgrade build.gradle in \"" +
					_project.getPath() + "\"");

			return;
		}

		_liferayExtension = GradleUtil.getExtension(
			_project, LiferayExtension.class);
		_pluginPackageProperties = FileUtil.readProperties(
			_project, "docroot/WEB-INF/liferay-plugin-package.properties");

		XmlParser xmlParser = new XmlParser();

		xmlParser.setFeature(
			"http://apache.org/xml/features/disallow-doctype-decl", false);

		_buildXmlNode = readXml(xmlParser, "build.xml");
		_ivyXmlNode = readXml(xmlParser, "ivy.xml");

		List<String> contents = new ArrayList<>();

		addContents(contents, getBuildGradleDependencies());
		addContents(contents, getBuildGradleDeploy());
		addContents(contents, getBuildGradleLiferay());
		addContents(contents, getBuildGradleProperties());

		FileUtil.write(buildGradleFile, contents);
	}

	public boolean isIgnoreMissingDependencies() {
		return _ignoreMissingDependencies;
	}

	public boolean isOverwrite() {
		return _overwrite;
	}

	public void portalDependencyNotation(
		String fileName, String group, String name, String version) {

		_portalDependencyNotations.put(
			fileName, new String[] {group, name, version});
	}

	public void setIgnoreMissingDependencies(
		boolean ignoreMissingDependencies) {

		_ignoreMissingDependencies = ignoreMissingDependencies;
	}

	public void setOverwrite(boolean overwrite) {
		_overwrite = overwrite;
	}

	protected void addContents(List<String> contents1, List<String> contents2) {
		if (contents2.isEmpty()) {
			return;
		}

		if (!contents1.isEmpty()) {
			contents1.add("");
		}

		contents1.addAll(contents2);
	}

	protected String convertBuildPropertyValue(String value) {
		value = value.replace(
			"${app.server.deploy.dir}", "${appServerDeployDir}");
		value = value.replace("${app.server.dir}", "${appServerDir}");
		value = value.replace(
			"${app.server.lib.global.dir}", "${appServerLibGlobalDir}");
		value = value.replace(
			"${app.server.parent.dir}", "${appServerParentDir}");
		value = value.replace("${auto.deploy.dir}", "${deployDir}");
		value = value.replace("${liferay.home}", "${liferayHome}");

		if (_liferayExtension instanceof LiferayOSGiExtension) {
			value = value.replace(
				"${plugin.name}",
				"${bundle.instructions['" + Constants.BUNDLE_SYMBOLICNAME +
					"']}");
		}
		else {
			value = value.replace("${plugin.name}", "${project.name}");
		}

		return value;
	}

	protected List<String> getBuildDependenciesCompile() {
		List<String> contents = new ArrayList<>();

		Iterator<Node> iterator = getIvyXmlDependenciesIterator();

		if (iterator != null) {
			while (iterator.hasNext()) {
				Node dependencyNode = iterator.next();

				String conf = (String)dependencyNode.attribute("conf");

				if (Validator.isNotNull(conf) && !conf.startsWith("default") &&
					!conf.startsWith("internal")) {

					continue;
				}

				String group = (String)dependencyNode.attribute("org");
				String name = (String)dependencyNode.attribute("name");

				boolean optional = false;
				boolean transitive = getNodeAttribute(
					dependencyNode, "transitive", true);

				if (Validator.isNotNull(conf)) {
					if (conf.startsWith("default")) {
						transitive = false;
					}
					else if (conf.startsWith("internal")) {
						optional = true;
					}
				}

				String version = (String)dependencyNode.attribute("rev");

				contents.add(
					wrapDependency(
						JavaPlugin.COMPILE_CONFIGURATION_NAME, group, name,
						optional, transitive, version));
			}
		}

		String requiredDeploymentContexts =
			_pluginPackageProperties.getProperty(
				"required-deployment-contexts");

		if (Validator.isNotNull(requiredDeploymentContexts)) {
			String[] requiredDeploymentContextsArray =
				requiredDeploymentContexts.split(",");

			for (String deploymentContext : requiredDeploymentContextsArray) {
				String serviceJarFileName = getServiceJarFileName(
					deploymentContext);

				if (FileUtil.exists(_project, serviceJarFileName)) {
					contents.add(
						wrapServiceJarDependency(
							JavaPlugin.COMPILE_CONFIGURATION_NAME,
							serviceJarFileName));
				}
			}
		}

		String importShared = getBuildXmlProperty("import.shared");

		if (Validator.isNotNull(importShared)) {
			Map<String, String> projectNamePathMap = new HashMap<>();

			Project rootProject = _project.getRootProject();

			for (Project project : rootProject.getSubprojects()) {
				if (!FileUtil.exists(project, "build.gradle")) {
					continue;
				}

				File dir = project.getProjectDir();

				projectNamePathMap.put(dir.getName(), project.getPath());
			}

			String[] importSharedArray = importShared.split(",");

			for (String projectFileName : importSharedArray) {
				projectFileName = projectFileName.trim();

				String projectName = projectFileName;

				int pos = projectName.lastIndexOf('/');

				if (pos != -1) {
					projectName = projectName.substring(pos + 1);
				}

				String projectPath = projectNamePathMap.get(projectName);

				if (Validator.isNull(projectPath)) {
					String message =
						"Unable to find project dependency " + projectFileName;

					if (isIgnoreMissingDependencies()) {
						_logger.error(message);

						continue;
					}
					else {
						throw new GradleException(message);
					}
				}

				contents.add(
					wrapProjectDependency(
						JavaPlugin.COMPILE_CONFIGURATION_NAME, projectPath));
			}
		}

		Collections.sort(contents);

		return contents;
	}

	protected List<String> getBuildDependenciesProvided() {
		List<String> contents = new ArrayList<>();

		Iterator<Node> iterator = getIvyXmlDependenciesIterator();

		if (iterator != null) {
			while (iterator.hasNext()) {
				Node dependencyNode = iterator.next();

				String conf = (String)dependencyNode.attribute("conf");

				if (Validator.isNull(conf) || !conf.startsWith("provided")) {
					continue;
				}

				String group = (String)dependencyNode.attribute("org");
				String name = (String)dependencyNode.attribute("name");
				String version = (String)dependencyNode.attribute("rev");

				contents.add(
					wrapDependency(
						ProvidedBasePlugin.getPROVIDED_CONFIGURATION_NAME(),
						group, name, false, version));
			}
		}

		Collections.sort(contents);

		return contents;
	}

	protected List<String> getBuildDependenciesProvidedCompile() {
		List<String> contents = new ArrayList<>();

		String portalDependencyJars = _pluginPackageProperties.getProperty(
			"portal-dependency-jars");

		if (Validator.isNotNull(portalDependencyJars)) {
			String[] portalDependencyJarsArray = portalDependencyJars.split(
				",");

			String configurationName =
				WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME;

			if (FileUtil.exists(_project, "bnd.bnd")) {
				configurationName =
					ProvidedBasePlugin.getPROVIDED_CONFIGURATION_NAME();
			}

			for (String fileName : portalDependencyJarsArray) {
				String[] portalDependencyNotation = getPortalDependencyNotation(
					fileName);

				if (portalDependencyNotation == null) {
					_logger.error(
						"Unable to find portal dependency " + fileName);
				}
				else {
					String group = portalDependencyNotation[0];
					String name = portalDependencyNotation[1];
					String version = portalDependencyNotation[2];

					contents.add(
						wrapDependency(
							configurationName, group, name, false, version));
				}
			}
		}

		Collections.sort(contents);

		return contents;
	}

	protected List<String> getBuildDependenciesTestCompile() {
		List<String> contents = new ArrayList<>();

		Iterator<Node> iterator = getIvyXmlDependenciesIterator();

		if (iterator != null) {
			while (iterator.hasNext()) {
				Node dependencyNode = iterator.next();

				String conf = (String)dependencyNode.attribute("conf");

				if (Validator.isNull(conf) || !conf.contains("test")) {
					continue;
				}

				String group = (String)dependencyNode.attribute("org");
				String name = (String)dependencyNode.attribute("name");
				boolean transitive = getNodeAttribute(
					dependencyNode, "transitive", true);
				String version = (String)dependencyNode.attribute("rev");

				contents.add(
					wrapDependency(
						JavaPlugin.TEST_COMPILE_CONFIGURATION_NAME, group, name,
						transitive, version));
			}
		}

		Collections.sort(contents);

		return contents;
	}

	protected List<String> getBuildGradleDependencies() {
		List<String> contents = new ArrayList<>();

		addContents(contents, getBuildDependenciesCompile());
		addContents(contents, getBuildDependenciesProvided());
		addContents(contents, getBuildDependenciesProvidedCompile());
		addContents(contents, getBuildDependenciesTestCompile());

		return wrapContents(contents, 0, " {", "dependencies", "}", false);
	}

	protected List<String> getBuildGradleDeploy() {
		String osgiRuntimeDependencies = getBuildXmlProperty(
			"osgi.runtime.dependencies");

		if (Validator.isNull(osgiRuntimeDependencies)) {
			return Collections.emptyList();
		}

		List<String> contents = new ArrayList<>();

		String[] osgiRuntimeDependenciesArray = osgiRuntimeDependencies.split(
			",");

		for (String osgiRuntimeDependency : osgiRuntimeDependenciesArray) {
			contents.add("\t\tinclude \"" + osgiRuntimeDependency + "\"");
		}

		contents = wrapContents(contents, 1, " {", "from(\"lib\")", "}", true);

		return wrapContents(
			contents, 0, " {", LiferayJavaPlugin.DEPLOY_TASK_NAME, "}", false);
	}

	protected List<String> getBuildGradleLiferay() {
		List<String> contents = new ArrayList<>();

		String autoDeployDirName = getBuildXmlProperty("auto.deploy.dir");

		if (Validator.isNotNull(autoDeployDirName)) {
			autoDeployDirName = convertBuildPropertyValue(autoDeployDirName);

			contents.add(wrapPropertyFile("deployDir", autoDeployDirName));
		}

		if (_liferayExtension instanceof LiferayOSGiExtension) {
			String autoUpdateXml = getBuildXmlProperty("osgi.auto.update.xml");

			if (Validator.isNotNull(autoUpdateXml)) {
				contents.add(
					wrapProperty(1, "autoUpdateXml", false, autoUpdateXml));
			}
		}

		if (!contents.isEmpty()) {
			contents = wrapContents(
				contents, 0, " {", LiferayPlugin.PLUGIN_NAME, "}", true);
		}

		return contents;
	}

	protected List<String> getBuildGradleProperties() {
		List<String> contents = new ArrayList<>();

		String javacSource = getBuildXmlProperty("javac.source");

		if (Validator.isNotNull(javacSource)) {
			contents.add(wrapProperty(0, "sourceCompatibility", javacSource));
		}

		String javacTarget = getBuildXmlProperty("javac.target");

		if (Validator.isNotNull(javacSource)) {
			contents.add(wrapProperty(0, "targetCompatibility", javacTarget));
		}

		String pluginVersion = getBuildXmlProperty("plugin.version");

		if (Validator.isNotNull(pluginVersion)) {
			if (!contents.isEmpty()) {
				contents.add("");
			}

			contents.add(wrapProperty(0, "version", pluginVersion));
		}

		return contents;
	}

	protected String getBuildXmlProperty(String key) {
		return getBuildXmlProperty(key, null);
	}

	protected String getBuildXmlProperty(String key, String defaultValue) {
		NodeList propertyNodeList = (NodeList)_buildXmlNode.get("property");

		Iterator<Node> iterator = propertyNodeList.iterator();

		while (iterator.hasNext()) {
			Node propertyNode = iterator.next();

			String propertyNodeName = (String)propertyNode.attribute("name");

			if (key.equals(propertyNodeName)) {
				return (String)propertyNode.attribute("value");
			}
		}

		return defaultValue;
	}

	protected Iterator<Node> getIvyXmlDependenciesIterator() {
		if (_ivyXmlNode == null) {
			return null;
		}

		Node dependenciesNode = getNode(_ivyXmlNode, "dependencies");

		if (dependenciesNode == null) {
			return null;
		}

		return dependenciesNode.iterator();
	}

	protected Node getNode(Node parentNode, String name) {
		NodeList nodeList = (NodeList)parentNode.get(name);

		if (nodeList.isEmpty()) {
			return null;
		}

		return (Node)nodeList.get(0);
	}

	protected boolean getNodeAttribute(
		Node node, String name, boolean defaultValue) {

		String value = (String)node.attribute(name);

		if (Validator.isNull(value)) {
			return defaultValue;
		}

		return Boolean.parseBoolean(value);
	}

	protected String getServiceJarFileName(String deploymentContext) {
		StringBuilder sb = new StringBuilder();

		sb.append("../../");
		sb.append(deploymentContext.replaceFirst(".+-", ""));
		sb.append("s/");
		sb.append(deploymentContext);
		sb.append("/docroot/WEB-INF/lib/");
		sb.append(deploymentContext);
		sb.append("-service.jar");

		return sb.toString();
	}

	protected Node readXml(XmlParser xmlParser, String fileName)
		throws Exception {

		File file = _project.file(fileName);

		if (!file.exists()) {
			return null;
		}

		return xmlParser.parse(file);
	}

	protected List<String> wrapContents(
		List<String> contents, int indentCount, String leftClose, String name,
		String rightClose, boolean sort) {

		if (contents.isEmpty()) {
			return contents;
		}

		if (sort) {
			Collections.sort(contents);
		}

		String indent = StringUtil.repeat('\t', indentCount);

		contents.add(0, indent + name + leftClose);

		String content = contents.get(contents.size() - 1);

		if (content.endsWith(",")) {
			contents.remove(contents.size() - 1);

			contents.add(content.substring(0, content.length() - 1));
		}

		contents.add(indent + rightClose);

		return contents;
	}

	protected String wrapDependency(
		String configurationName, String group, String name, boolean optional,
		boolean transitive, String version) {

		StringBuilder sb = new StringBuilder();

		sb.append('\t');
		sb.append(configurationName);
		sb.append(" group: \"");
		sb.append(group);
		sb.append("\", name: \"");
		sb.append(name);
		sb.append('\"');

		if (optional) {
			sb.append(", ");
			sb.append(OptionalBasePlugin.getOPTIONAL_IDENTIFIER());
		}

		if (!transitive) {
			sb.append(", transitive: false");
		}

		sb.append(", version: \"");
		sb.append(version);
		sb.append('\"');

		return sb.toString();
	}

	protected String wrapDependency(
		String configurationName, String group, String name, boolean transitive,
		String version) {

		return wrapDependency(
			configurationName, group, name, false, transitive, version);
	}

	protected String wrapProjectDependency(
		String configurationName, String projectPath) {

		StringBuilder sb = new StringBuilder();

		sb.append('\t');
		sb.append(configurationName);
		sb.append(" project(\"");
		sb.append(projectPath);
		sb.append("\")");

		return sb.toString();
	}

	protected String wrapProperty(
		int indentCount, String name, boolean quoteValue, String value) {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < indentCount; i++) {
			sb.append('\t');
		}

		sb.append(name);
		sb.append(" = ");

		if (quoteValue) {
			sb.append('\"');
		}

		sb.append(value);

		if (quoteValue) {
			sb.append('\"');
		}

		return sb.toString();
	}

	protected String wrapProperty(int indentCount, String name, String value) {
		return wrapProperty(indentCount, name, true, value);
	}

	protected String wrapPropertyFile(String name, String value) {
		StringBuilder sb = new StringBuilder();

		sb.append('\t');
		sb.append(name);
		sb.append(" = file(\"");
		sb.append(value);
		sb.append("\")");

		return sb.toString();
	}

	protected String wrapServiceJarDependency(
		String configurationName, String serviceJarFileName) {

		StringBuilder sb = new StringBuilder();

		sb.append('\t');
		sb.append(configurationName);
		sb.append(" files(\"");
		sb.append(serviceJarFileName);
		sb.append("\")");

		return sb.toString();
	}

	private static final Logger _logger = Logging.getLogger(
		InitGradleTask.class);

	private Node _buildXmlNode;
	private boolean _ignoreMissingDependencies;
	private Node _ivyXmlNode;
	private LiferayExtension _liferayExtension;
	private boolean _overwrite;
	private Properties _pluginPackageProperties;
	private final Map<String, String[]> _portalDependencyNotations =
		new HashMap<>();
	private Project _project;

}