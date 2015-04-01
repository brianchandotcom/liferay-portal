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
import com.liferay.gradle.plugins.util.FileUtil;
import com.liferay.gradle.plugins.util.StringUtil;
import com.liferay.gradle.plugins.util.Validator;

import groovy.util.Node;
import groovy.util.NodeList;
import groovy.util.XmlParser;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
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

		ExtensionContainer extensionContainer = _project.getExtensions();

		_liferayExtension = extensionContainer.getByType(
			LiferayExtension.class);

		XmlParser xmlParser = new XmlParser();

		xmlParser.setFeature(
			"http://apache.org/xml/features/disallow-doctype-decl", false);

		_buildXmlNode = _loadXml(xmlParser, "build.xml");
		_ivyXmlNode = _loadXml(xmlParser, "ivy.xml");

		List<String> contents = new ArrayList<>();

		contents.addAll(getBuildGradleDependencies());
		contents.addAll(getBuildGradleLiferay());

		File buildGradleFile = _project.file("build.gradle");

		FileUtil.write(buildGradleFile, contents);
	}

	@SuppressWarnings("unchecked")
	protected List<String> getBuildDependenciesCompile() {
		List<String> contents = new ArrayList<>();

		if (_ivyXmlNode != null) {
			Node dependenciesNode = _getNode(_ivyXmlNode, "dependencies");

			if (dependenciesNode != null) {
				Iterator<Node> itr = dependenciesNode.iterator();

				while (itr.hasNext()) {
					Node dependencyNode = itr.next();

					String conf = (String)dependencyNode.attribute("conf");

					if (Validator.isNotNull(conf) && !conf.equals("default")) {
						continue;
					}

					String group = (String)dependencyNode.attribute("org");
					String name = (String)dependencyNode.attribute("name");
					String version = (String)dependencyNode.attribute("rev");

					contents.add(_wrapDependency(group, name, version));
				}
			}
		}

		String requiredDeploymentContexts =
			_liferayExtension.getPluginPackageProperty(
				"required-deployment-contexts");

		if (Validator.isNotNull(requiredDeploymentContexts)) {
			String[] requiredDeploymentContextsArray =
				requiredDeploymentContexts.split(",");

			for (String deploymentContext : requiredDeploymentContextsArray) {
				String serviceJarFileName = _getServiceJarFileName(
					deploymentContext);

				if (FileUtil.exists(_project, serviceJarFileName)) {
					contents.add(_wrapServiceJarDependency(serviceJarFileName));
				}
			}
		}

		String importShared = _getBuildXmlProperty("import.shared");

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

				contents.add(_wrapProjectDependency(projectPath));
			}
		}

		return _wrapContents(
			contents, 1, "(", JavaPlugin.COMPILE_CONFIGURATION_NAME, ")");
	}

	protected List<String> getBuildDependenciesProvidedCompile() {
		List<String> contents = new ArrayList<>();

		String portalDependencyJars =
			_liferayExtension.getPluginPackageProperty(
				"portal-dependency-jars");

		if (Validator.isNotNull(portalDependencyJars)) {
			String[] portalDependencyJarsArray = portalDependencyJars.split(
				",");

			for (String fileName : portalDependencyJarsArray) {
				String[] portalDependency = _portalDependencies.get(fileName);

				if (portalDependency == null) {
					System.out.println(
						"Unable to find portal dependency " + fileName);
				}
				else {
					String portalDependencyGroup = portalDependency[0];
					String portalDependencyName = portalDependency[1];
					String portalDependencyVersion = portalDependency[2];

					contents.add(
						_wrapDependency(
							portalDependencyGroup, portalDependencyName,
							portalDependencyVersion));
				}
			}
		}

		return _wrapContents(
			contents, 1, "(", WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
			")");
	}

	@SuppressWarnings("unchecked")
	protected List<String> getBuildDependenciesTestCompile() {
		List<String> contents = new ArrayList<>();

		if (_ivyXmlNode != null) {
			Node dependenciesNode = _getNode(_ivyXmlNode, "dependencies");

			if (dependenciesNode != null) {
				Iterator<Node> itr = dependenciesNode.iterator();

				while (itr.hasNext()) {
					Node dependencyNode = itr.next();

					String conf = (String)dependencyNode.attribute("conf");

					if (Validator.isNull(conf) || !conf.contains("test")) {
						continue;
					}

					String group = (String)dependencyNode.attribute("org");
					String name = (String)dependencyNode.attribute("name");
					String version = (String)dependencyNode.attribute("rev");

					contents.add(_wrapDependency(group, name, version));
				}
			}
		}

		return _wrapContents(
			contents, 1, "(", JavaPlugin.TEST_COMPILE_CONFIGURATION_NAME, ")");
	}

	protected List<String> getBuildGradleDependencies() {
		List<String> contents = new ArrayList<>();

		contents.addAll(getBuildDependenciesCompile());
		contents.addAll(getBuildDependenciesProvidedCompile());
		contents.addAll(getBuildDependenciesTestCompile());

		return _wrapContents(contents, 0, " {", "dependencies", "}");
	}

	protected List<String> getBuildGradleLiferay() {
		List<String> contents = new ArrayList<>();

		String pluginType = _liferayExtension.getPluginType();

		if (pluginType.equals("theme")) {
			String themeParent = _getBuildXmlProperty("theme.parent");

			if (Validator.isNotNull(themeParent)) {
				contents.add(_wrapProperty("themeParent", themeParent));
			}

			String themeType = _getBuildXmlProperty("theme.type", "vm");

			contents.add(_wrapProperty("themeType", themeType));
		}

		if (!contents.isEmpty()) {
			contents = _wrapContents(
				contents, 0, " {", LiferayPlugin.EXTENSION_NAME, "}");
		}

		return contents;
	}

	private String _getBuildXmlProperty(String key) {
		return _getBuildXmlProperty(key, null);
	}

	@SuppressWarnings("unchecked")
	private String _getBuildXmlProperty(String key, String defaultValue) {
		NodeList propertyNodeList = (NodeList)_buildXmlNode.get("property");

		Iterator<Node> itr = propertyNodeList.iterator();

		while (itr.hasNext()) {
			Node propertyNode = itr.next();

			String propertyNodeName = (String)propertyNode.attribute("name");

			if (key.equals(propertyNodeName)) {
				return (String)propertyNode.attribute("value");
			}
		}

		return defaultValue;
	}

	private Node _getNode(Node parentNode, String name) {
		NodeList nodeList = (NodeList)parentNode.get(name);

		if (nodeList.isEmpty()) {
			return null;
		}

		return (Node)nodeList.get(0);
	}

	private String _getServiceJarFileName(String deploymentContext) {
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

	private Node _loadXml(XmlParser xmlParser, String fileName)
		throws Exception {

		File file = _project.file(fileName);

		if (!file.exists()) {
			return null;
		}

		return xmlParser.parse(file);
	}

	private List<String> _wrapContents(
		List<String> contents, int indentCount, String leftClose, String name,
		String rightClose) {

		if (contents.isEmpty()) {
			return contents;
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

	private String _wrapDependency(String group, String name, String version) {
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

	private String _wrapProjectDependency(String projectPath) {
		return "\t\tproject(\"" + projectPath + "\"),";
	}

	private String _wrapProperty(String name, String value) {
		StringBuilder sb = new StringBuilder();

		sb.append('\t');
		sb.append(name);
		sb.append(" = \"");
		sb.append(value);
		sb.append("\"");

		return sb.toString();
	}

	private String _wrapServiceJarDependency(String serviceJarFileName) {
		return "\t\tfiles(\"" + serviceJarFileName + "\"),";
	}

	private static final Map<String, String[]> _portalDependencies =
		new HashMap<>();

	static {
		_portalDependencies.put(
			"antlr2.jar", new String[] {"antlr", "antlr", "2.7.7"});
		_portalDependencies.put(
			"axis.jar", new String[] {"axis", "axis", "1.4"});
		_portalDependencies.put(
			"backport-concurrent.jar",
			new String[] {
				"backport-util-concurrent", "backport-util-concurrent", "3.1"
			});
		_portalDependencies.put(
			"bsf.jar", new String[] {"bsf", "bsf", "2.4.0"});
		_portalDependencies.put(
			"commons-beanutils.jar",
			new String[] {"commons-beanutils", "commons-beanutils", "1.8.2"});
		_portalDependencies.put(
			"commons-codec.jar",
			new String[] {"commons-codec", "commons-codec", "1.9"});
		_portalDependencies.put(
			"commons-collections.jar",
			new String[] {
				"commons-collections", "commons-collections", "3.2.1"
			});
		_portalDependencies.put(
			"commons-digester.jar",
			new String[] {"commons-digester", "commons-digester", "1.8"});
		_portalDependencies.put(
			"commons-discovery.jar",
			new String[] {"commons-discovery", "commons-discovery", "0.4"});
		_portalDependencies.put(
			"commons-fileupload.jar",
			new String[] {"commons-fileupload", "commons-fileupload", "1.3.1"});
		_portalDependencies.put(
			"commons-httpclient.jar",
			new String[] {"commons-httpclient", "commons-httpclient", "3.1"});
		_portalDependencies.put(
			"commons-io.jar", new String[] {"commons-io", "commons-io", "2.1"});
		_portalDependencies.put(
			"commons-lang.jar",
			new String[] {"commons-lang", "commons-lang", "2.6"});
		_portalDependencies.put(
			"commons-logging.jar",
			new String[] {"commons-logging", "commons-logging", "1.1.3"});
		_portalDependencies.put(
			"commons-math.jar",
			new String[] {"org.apache.commons", "commons-math", "2.0"});
		_portalDependencies.put(
			"commons-validator.jar",
			new String[] {"commons-validator", "commons-validator", "1.3.1"});
		_portalDependencies.put(
			"dom4j.jar", new String[] {"dom4j", "dom4j", "1.6.1"});
		_portalDependencies.put(
			"hibernate-core.jar",
			new String[] {"org.hibernate", "hibernate-core", "3.6.10.Final"});
		_portalDependencies.put(
			"ical4j.jar", new String[] {"org.mnode.ical4j", "ical4j", "1.0.2"});
		_portalDependencies.put(
			"jabsorb.jar", new String[] {"org.jabsorb", "jabsorb", "1.3.1"});
		_portalDependencies.put(
			"jaxrpc.jar", new String[] {"javax.xml", "jaxrpc", "1.1"});
		_portalDependencies.put(
			"jcommon.jar", new String[] {"jfree", "jcommon", "1.0.16"});
		_portalDependencies.put(
			"jdom.jar", new String[] {"org.jopendocument", "jdom", "1.1.1"});
		_portalDependencies.put(
			"jericho-html.jar",
			new String[] {"net.htmlparser.jericho", "jericho-html", "3.1"});
		_portalDependencies.put(
			"jfreechart.jar", new String[] {"jfree", "jfreechart", "1.0.13"});
		_portalDependencies.put(
			"jruby.jar", new String[] {"org.jruby", "jruby", "1.6.5"});
		_portalDependencies.put(
			"json-java.jar",
			new String[] {"com.liferay", "com.liferay.org.json", "1.0.0"});
		_portalDependencies.put(
			"jstl-api.jar",
			new String[] {"javax.servlet.jsp.jstl", "jstl-api", "1.2"});
		_portalDependencies.put(
			"jstl-impl.jar",
			new String[] {"org.glassfish.web", "jstl-impl", "1.2"});
		_portalDependencies.put(
			"jutf7.jar", new String[] {"com.beetstra.jutf7", "jutf7", "0.9.0"});
		_portalDependencies.put(
			"nekohtml.jar",
			new String[] {"net.sourceforge.nekohtml", "nekohtml", "1.9.14"});
		_portalDependencies.put(
			"oro.jar", new String[] {"oro", "oro", "2.0.8"});
		_portalDependencies.put(
			"portals-bridges.jar",
			new String[] {
				"org.apache.portals.bridges", "portals-bridges-common", "1.0"
			});
		_portalDependencies.put(
			"rhino.jar", new String[] {"org.mozilla", "rhino", "1.7R4"});
		_portalDependencies.put(
			"rome.jar", new String[] {"rome", "rome", "1.0"});
		_portalDependencies.put(
			"saaj-api.jar", new String[] {"javax.xml.soap", "saaj-api", "1.3"});
		_portalDependencies.put(
			"saaj-impl.jar",
			new String[] {"com.sun.xml.messaging.saaj", "saaj-impl", "1.3"});
		_portalDependencies.put(
			"slf4j-api.jar", new String[] {"org.slf4j", "slf4j-api", "1.7.2"});
		_portalDependencies.put(
			"struts.jar",
			new String[] {
				"com.liferay", "org.apache.struts", "1.2.9.LIFERAY-PATCHED-1"
			});
		_portalDependencies.put(
			"util-slf4j.jar",
			new String[] {
				"com.liferay.portal", "util-slf4j", "7.0.0-SNAPSHOT"
			});
		_portalDependencies.put(
			"wsdl4j.jar", new String[] {"wsdl4j", "wsdl4j", "1.6.1"});
		_portalDependencies.put(
			"xercesImpl.jar", new String[] {"xerces", "xercesImpl", "2.11.0"});
		_portalDependencies.put(
			"xml-apis.jar", new String[] {"xml-apis", "xml-apis", "1.4.01"});
		_portalDependencies.put(
			"xmlsec.jar",
			new String[] {"org.apache.santuario", "xmlsec", "1.4.5"});
	}

	private Node _buildXmlNode;
	private Node _ivyXmlNode;
	private LiferayExtension _liferayExtension;
	private Project _project;

}