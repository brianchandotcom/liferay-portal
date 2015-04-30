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

import com.liferay.gradle.plugins.LiferayPlugin;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.extensions.LiferayThemeExtension;
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

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Brian Wing Shun Chan
 * @author Chas Austin
 * @author Andrea Di Giorgi
 */
public class InitGradleTask extends DefaultTask {

	@TaskAction
	public void initGradle() throws Exception {
		_project = getProject();

		_liferayExtension = GradleUtil.getExtension(
			_project, LiferayExtension.class);
		_pluginPackageProperties = FileUtil.readProperties(
			_project, "docroot/WEB-INF/liferay-plugin-package.properties");

		XmlParser xmlParser = new XmlParser();

		xmlParser.setFeature(
			"http://apache.org/xml/features/disallow-doctype-decl", false);

		_buildXmlNode = readXml(xmlParser, "build.xml");
		_ivyXmlNode = readXml(xmlParser, "ivy.xml");

		File buildGradleFile = _project.file("build.gradle");

		List<String> contents = new ArrayList<>();

		addContents(contents, getBuildGradleDependencies());
		addContents(contents, getBuildGradleLiferay());
		addContents(contents, getBuildGradleProperties());

		FileUtil.write(buildGradleFile, contents);
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

	protected List<String> getBuildDependenciesCompile() {
		List<String> contents = new ArrayList<>();

		if (_ivyXmlNode != null) {
			Node dependenciesNode = getNode(_ivyXmlNode, "dependencies");

			if (dependenciesNode != null) {
				Iterator<Node> iterator = dependenciesNode.iterator();

				while (iterator.hasNext()) {
					Node dependencyNode = iterator.next();

					String conf = (String)dependencyNode.attribute("conf");

					if (Validator.isNotNull(conf) && !conf.equals("default")) {
						continue;
					}

					String group = (String)dependencyNode.attribute("org");
					String name = (String)dependencyNode.attribute("name");
					String version = (String)dependencyNode.attribute("rev");

					contents.add(wrapDependency(group, name, version));
				}
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
					contents.add(wrapServiceJarDependency(serviceJarFileName));
				}
			}
		}

		String importShared = getBuildXmlProperty("import.shared");

		if (Validator.isNotNull(importShared)) {
			Map<String, String> projectNamePathMap = new HashMap<>();

			Project rootProject = _project.getRootProject();

			for (Project project : rootProject.getAllprojects()) {
				projectNamePathMap.put(project.getName(), project.getPath());
			}

			String[] importSharedArray = importShared.split(",");

			for (String projectName : importSharedArray) {
				String projectPath = projectNamePathMap.get(projectName);

				if (Validator.isNull(projectPath)) {
					throw new GradleException(
						"Unable to find project dependency " + projectName);
				}

				contents.add(wrapProjectDependency(projectPath));
			}
		}

		return wrapContents(
			contents, 1, "(", JavaPlugin.COMPILE_CONFIGURATION_NAME, ")", true);
	}

	protected List<String> getBuildDependenciesProvidedCompile() {
		List<String> contents = new ArrayList<>();

		String portalDependencyJars = _pluginPackageProperties.getProperty(
			"portal-dependency-jars");

		if (Validator.isNotNull(portalDependencyJars)) {
			String[] portalDependencyJarsArray = portalDependencyJars.split(
				",");

			for (String fileName : portalDependencyJarsArray) {
				String[] portalDependencyNotation =
					_portalDependencyNotations.get(fileName);

				if (portalDependencyNotation == null) {
					System.out.println(
						"Unable to find portal dependency " + fileName);
				}
				else {
					String group = portalDependencyNotation[0];
					String name = portalDependencyNotation[1];
					String version = portalDependencyNotation[2];

					contents.add(wrapDependency(group, name, version));
				}
			}
		}

		return wrapContents(
			contents, 1, "(", WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
			")", true);
	}

	protected List<String> getBuildDependenciesTestCompile() {
		List<String> contents = new ArrayList<>();

		if (_ivyXmlNode != null) {
			Node dependenciesNode = getNode(_ivyXmlNode, "dependencies");

			if (dependenciesNode != null) {
				Iterator<Node> iterator = dependenciesNode.iterator();

				while (iterator.hasNext()) {
					Node dependencyNode = iterator.next();

					String conf = (String)dependencyNode.attribute("conf");

					if (Validator.isNull(conf) || !conf.contains("test")) {
						continue;
					}

					String group = (String)dependencyNode.attribute("org");
					String name = (String)dependencyNode.attribute("name");
					String version = (String)dependencyNode.attribute("rev");

					contents.add(wrapDependency(group, name, version));
				}
			}
		}

		return wrapContents(
			contents, 1, "(", JavaPlugin.TEST_COMPILE_CONFIGURATION_NAME, ")",
			true);
	}

	protected List<String> getBuildGradleDependencies() {
		List<String> contents = new ArrayList<>();

		addContents(contents, getBuildDependenciesCompile());
		addContents(contents, getBuildDependenciesProvidedCompile());
		addContents(contents, getBuildDependenciesTestCompile());

		return wrapContents(contents, 0, " {", "dependencies", "}", false);
	}

	protected List<String> getBuildGradleLiferay() {
		List<String> contents = new ArrayList<>();

		if (_liferayExtension instanceof LiferayThemeExtension) {
			String themeParent = getBuildXmlProperty("theme.parent");

			if (Validator.isNotNull(themeParent)) {
				contents.add(wrapProperty("themeParent", themeParent));
			}

			String themeType = getBuildXmlProperty("theme.type", "vm");

			contents.add(wrapProperty("themeType", themeType));
		}

		if (!contents.isEmpty()) {
			contents = wrapContents(
				contents, 0, " {", LiferayPlugin.PLUGIN_NAME, "}", true);
		}

		return contents;
	}

	protected List<String> getBuildGradleProperties() {
		List<String> contents = new ArrayList<>();

		String pluginVersion = getBuildXmlProperty("plugin.version");

		if (Validator.isNotNull(pluginVersion)) {
			contents.add("version = \"" + pluginVersion + "\"");
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

	protected Node getNode(Node parentNode, String name) {
		NodeList nodeList = (NodeList)parentNode.get(name);

		if (nodeList.isEmpty()) {
			return null;
		}

		return (Node)nodeList.get(0);
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

	protected String wrapDependency(String group, String name, String version) {
		StringBuilder sb = new StringBuilder();

		sb.append("\t\t[group: \"");
		sb.append(group);
		sb.append("\", name: \"");
		sb.append(name);
		sb.append("\", version: \"");
		sb.append(version);
		sb.append("\"],");

		return sb.toString();
	}

	protected String wrapProjectDependency(String projectPath) {
		return "\t\tproject(\"" + projectPath + "\"),";
	}

	protected String wrapProperty(String name, String value) {
		StringBuilder sb = new StringBuilder();

		sb.append('\t');
		sb.append(name);
		sb.append(" = \"");
		sb.append(value);
		sb.append("\"");

		return sb.toString();
	}

	protected String wrapServiceJarDependency(String serviceJarFileName) {
		return "\t\tfiles(\"" + serviceJarFileName + "\"),";
	}

	private static final PortalDependencyNotations _portalDependencyNotations =
		new PortalDependencyNotations();

	private Node _buildXmlNode;
	private Node _ivyXmlNode;
	private LiferayExtension _liferayExtension;
	private Properties _pluginPackageProperties;
	private Project _project;

	private static class PortalDependencyNotations {

		public PortalDependencyNotations() {
			_put("antlr2.jar", "antlr", "antlr", "2.7.7");
			_put("axis.jar", "axis", "axis", "1.4");
			_put(
				"backport-concurrent.jar", "backport-util-concurrent",
				"backport-util-concurrent", "3.1");
			_put("bsf.jar", "bsf", "bsf", "2.4.0");
			_put(
				"commons-beanutils.jar", "commons-beanutils",
				"commons-beanutils", "1.8.2");
			_put("commons-codec.jar", "commons-codec", "commons-codec", "1.9");
			_put(
				"commons-collections.jar", "commons-collections",
				"commons-collections", "3.2.1");
			_put(
				"commons-digester.jar", "commons-digester", "commons-digester",
				"1.8");
			_put(
				"commons-discovery.jar", "commons-discovery",
				"commons-discovery", "0.4");
			_put(
				"commons-fileupload.jar", "commons-fileupload",
				"commons-fileupload", "1.3.1");
			_put(
				"commons-httpclient.jar", "commons-httpclient",
				"commons-httpclient", "3.1");
			_put("commons-io.jar", "commons-io", "commons-io", "2.1");
			_put("commons-lang.jar", "commons-lang", "commons-lang", "2.6");
			_put(
				"commons-logging.jar", "commons-logging", "commons-logging",
				"1.1.3");
			_put(
				"commons-math.jar", "org.apache.commons", "commons-math",
				"2.0");
			_put(
				"commons-validator.jar", "commons-validator",
				"commons-validator", "1.3.1");
			_put("dom4j.jar", "dom4j", "dom4j", "1.6.1");
			_put(
				"hibernate-core.jar", "org.hibernate", "hibernate-core",
				"3.6.10.Final");
			_put("ical4j.jar", "org.mnode.ical4j", "ical4j", "1.0.2");
			_put("jabsorb.jar", "org.jabsorb", "jabsorb", "1.3.1");
			_put("jaxrpc.jar", "javax.xml", "jaxrpc", "1.1");
			_put("jcommon.jar", "jfree", "jcommon", "1.0.16");
			_put("jdom.jar", "org.jopendocument", "jdom", "1.1.1");
			_put(
				"jericho-html.jar", "net.htmlparser.jericho", "jericho-html",
				"3.1");
			_put("jfreechart.jar", "jfree", "jfreechart", "1.0.13");
			_put("jruby.jar", "org.jruby", "jruby", "1.6.5");
			_put(
				"json-java.jar", "com.liferay", "com.liferay.org.json",
				"1.0.0");
			_put("jstl-api.jar", "javax.servlet.jsp.jstl", "jstl-api", "1.2");
			_put("jstl-impl.jar", "org.glassfish.web", "jstl-impl", "1.2");
			_put("jutf7.jar", "com.beetstra.jutf7", "jutf7", "0.9.0");
			_put(
				"nekohtml.jar", "net.sourceforge.nekohtml", "nekohtml",
				"1.9.14");
			_put("oro.jar", "oro", "oro", "2.0.8");
			_put(
				"portals-bridges.jar", "org.apache.portals.bridges",
				"portals-bridges-common", "1.0");
			_put("rhino.jar", "org.mozilla", "rhino", "1.7R4");
			_put("rome.jar", "rome", "rome", "1.0");
			_put("saaj-api.jar", "javax.xml.soap", "saaj-api", "1.3");
			_put(
				"saaj-impl.jar", "com.sun.xml.messaging.saaj", "saaj-impl",
				"1.3");
			_put("slf4j-api.jar", "org.slf4j", "slf4j-api", "1.7.2");
			_put(
				"struts.jar", "com.liferay", "org.apache.struts",
				"1.2.9.LIFERAY-PATCHED-1");
			_put(
				"util-slf4j.jar", "com.liferay.portal", "util-slf4j",
				"default");
			_put("wsdl4j.jar", "wsdl4j", "wsdl4j", "1.6.1");
			_put("xercesImpl.jar", "xerces", "xercesImpl", "2.11.0");
			_put("xml-apis.jar", "xml-apis", "xml-apis", "1.4.01");
			_put("xmlsec.jar", "org.apache.santuario", "xmlsec", "1.4.5");
		}

		public String[] get(String fileName) {
			return _portalDependencyNotations.get(fileName);
		}

		private void _put(
			String fileName, String group, String name, String version) {

			_portalDependencyNotations.put(
				fileName, new String[] {group, name, version});
		}

		private final Map<String, String[]> _portalDependencyNotations =
			new HashMap<>();

	}

}