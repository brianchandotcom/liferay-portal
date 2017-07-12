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

package com.liferay.gradle.plugins.test.integration.tasks;

import com.liferay.gradle.plugins.test.integration.internal.util.GradleUtil;
import com.liferay.gradle.plugins.test.integration.internal.util.StringUtil;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.Validator;
import com.liferay.gradle.util.copy.ExcludeExistingFileAction;
import com.liferay.gradle.util.copy.StripPathSegmentsAction;

import groovy.xml.DOMBuilder;
import groovy.xml.XmlUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.CopySpec;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import org.gradle.util.VersionNumber;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Andrea Di Giorgi
 */
public class SetUpTestableTomcatTask
	extends DefaultTask
	implements JmxRemotePortSpec, ManagerSpec, ModuleFrameworkBaseDirSpec {

	public SetUpTestableTomcatTask() {
		_zipUrl = new Callable<String>() {

			@Override
			public String call() throws Exception {
				File dir = getDir();

				String dirName = dir.getName();

				int start = StringUtil.indexOfDigit(dirName);

				if (start < 0) {
					return null;
				}

				VersionNumber versionNumber = VersionNumber.parse(
					dirName.substring(start));

				if (versionNumber == VersionNumber.UNKNOWN) {
					return null;
				}

				StringBuilder sb = new StringBuilder();

				sb.append("http://archive.apache.org/dist/tomcat/tomcat-");
				sb.append(versionNumber.getMajor());
				sb.append("/v");
				sb.append(versionNumber);
				sb.append("/bin/apache-tomcat-");
				sb.append(versionNumber);
				sb.append(".zip");

				return sb.toString();
			}

		};
	}

	@Input
	@Optional
	public String getAspectJAgent() {
		return GradleUtil.toString(_aspectJAgent);
	}

	@Input
	@Optional
	public String getAspectJConfiguration() {
		return GradleUtil.toString(_aspectJConfiguration);
	}

	public File getBinDir() {
		return new File(getDir(), "bin");
	}

	@Input
	public File getDir() {
		return GradleUtil.toFile(getProject(), _dir);
	}

	@Input
	@Override
	public int getJmxRemotePort() {
		return GradleUtil.toInteger(_jmxRemotePort);
	}

	@Input
	@Override
	public String getManagerPassword() {
		return GradleUtil.toString(_managerPassword);
	}

	@Input
	@Override
	public String getManagerUserName() {
		return GradleUtil.toString(_managerUserName);
	}

	@Input
	@Override
	public File getModuleFrameworkBaseDir() {
		return GradleUtil.toFile(getProject(), _moduleFrameworkBaseDir);
	}

	@Input
	public String getZipUrl() {
		return GradleUtil.toString(_zipUrl);
	}

	@Input
	public boolean isDebugLogging() {
		return _debugLogging;
	}

	@Input
	public boolean isJmxRemoteAuthenticate() {
		return _jmxRemoteAuthenticate;
	}

	@Input
	public boolean isJmxRemoteSsl() {
		return _jmxRemoteSsl;
	}

	@Input
	public boolean isOverwriteTestModules() {
		return _overwriteTestModules;
	}

	public void setAspectJAgent(Object aspectJAgent) {
		_aspectJAgent = aspectJAgent;
	}

	public void setAspectJConfiguration(Object aspectJConfiguration) {
		_aspectJConfiguration = aspectJConfiguration;
	}

	public void setDebugLogging(boolean debugLogging) {
		_debugLogging = debugLogging;
	}

	public void setDir(Object dir) {
		_dir = dir;
	}

	public void setJmxRemoteAuthenticate(boolean jmxRemoteAuthenticate) {
		_jmxRemoteAuthenticate = jmxRemoteAuthenticate;
	}

	@Override
	public void setJmxRemotePort(Object jmxRemotePort) {
		_jmxRemotePort = jmxRemotePort;
	}

	public void setJmxRemoteSsl(boolean jmxRemoteSsl) {
		_jmxRemoteSsl = jmxRemoteSsl;
	}

	@Override
	public void setManagerPassword(Object managerPassword) {
		_managerPassword = managerPassword;
	}

	@Override
	public void setManagerUserName(Object managerUserName) {
		_managerUserName = managerUserName;
	}

	@Override
	public void setModuleFrameworkBaseDir(Object moduleFrameworkBaseDir) {
		_moduleFrameworkBaseDir = moduleFrameworkBaseDir;
	}

	public void setOverwriteTestModules(boolean overwriteTestModules) {
		_overwriteTestModules = overwriteTestModules;
	}

	@TaskAction
	public void setUpTestableTomcat() throws Exception {
		_setUpLogging();
		_setUpManager();
		_setUpOsgiModules();
		_setUpSetEnv();
	}

	public void setZipUrl(Object zipUrl) {
		_zipUrl = zipUrl;
	}

	private boolean _contains(String fileName, String s) throws IOException {
		File file = new File(getDir(), fileName);

		String fileContent = new String(Files.readAllBytes(file.toPath()));

		if (fileContent.contains(s)) {
			return true;
		}

		return false;
	}

	private PrintWriter _getAppendPrintWriter(String fileName)
		throws IOException {

		File file = new File(getDir(), fileName);

		return new PrintWriter(
			Files.newBufferedWriter(
				file.toPath(), StandardCharsets.UTF_8,
				StandardOpenOption.APPEND, StandardOpenOption.WRITE));
	}

	private String _getJmxOptions() {
		StringBuilder sb = new StringBuilder();

		sb.append("-Dcom.sun.management.jmxremote ");
		sb.append("-Dcom.sun.management.jmxremote.authenticate=");
		sb.append(isJmxRemoteAuthenticate());
		sb.append(" -Dcom.sun.management.jmxremote.port=");
		sb.append(getJmxRemotePort());
		sb.append(" -Dcom.sun.management.jmxremote.ssl=");
		sb.append(isJmxRemoteSsl());

		return sb.toString();
	}

	private void _setUpAspectJ() throws IOException {
		String aspectJAgent = getAspectJAgent();

		if (Validator.isNotNull(aspectJAgent) &&
			!_contains("bin/setenv.sh", aspectJAgent)) {

			try (PrintWriter printWriter = _getAppendPrintWriter(
					"bin/setenv.sh")) {

				printWriter.println();

				printWriter.print("CATALINA_OPTS=\"${CATALINA_OPTS} ");
				printWriter.print(aspectJAgent);
				printWriter.print(
					" -Dorg.aspectj.weaver.loadtime.configuration=");

				String aspectJConfiguration = getAspectJConfiguration();

				if (Validator.isNotNull(aspectJConfiguration)) {
					printWriter.print(aspectJConfiguration);
				}

				printWriter.println("\"");
			}
		}
	}

	private void _setUpJmx() throws IOException {
		String jmxOptions = _getJmxOptions();

		if (!_contains("bin/setenv.bat", jmxOptions)) {
			try (PrintWriter printWriter = _getAppendPrintWriter(
					"bin/setenv.bat")) {

				printWriter.println();

				printWriter.print("set \"JMX_OPTS=");
				printWriter.print(jmxOptions);
				printWriter.println('\"');

				printWriter.println();

				printWriter.println(
					"set \"CATALINA_OPTS=%CATALINA_OPTS% %JMX_OPTS%\"");
			}
		}

		if (!_contains("bin/setenv.sh", jmxOptions)) {
			try (PrintWriter printWriter = _getAppendPrintWriter(
					"bin/setenv.sh")) {

				printWriter.println();

				printWriter.print("JMX_OPTS=\"");
				printWriter.print(jmxOptions);
				printWriter.println('\"');

				printWriter.println();

				printWriter.println(
					"CATALINA_OPTS=\"${CATALINA_OPTS} ${JMX_OPTS}\"");
			}
		}
	}

	private void _setUpJpda() throws IOException {
		if (!_contains("bin/setenv.sh", "JPDA_ADDRESS")) {
			try (PrintWriter printWriter = _getAppendPrintWriter(
					"bin/setenv.sh")) {

				printWriter.println();

				printWriter.println("JPDA_ADDRESS=\"8000\"");
			}
		}
	}

	private void _setUpLogging() throws IOException {
		if (!isDebugLogging() ||
			_contains("conf/Logging.properties", "org.apache.catalina.level")) {

			return;
		}

		try (PrintWriter printWriter = _getAppendPrintWriter(
				"conf/Logging.properties")) {

			printWriter.println("org.apache.catalina.level=ALL");

			printWriter.println();

			printWriter.println(
				"org.apache.catalina.loader.WebappClassLoader.level=INFO");
			printWriter.println(
				"org.apache.catalina.loader.WebappLoader.level=INFO");
			printWriter.println(
				"org.apache.catalina.startup.ClassLoaderFactory.level=INFO");
		}
	}

	private void _setUpManager() throws Exception {
		final File managerDir = new File(getDir(), "webapps/manager");

		if (!managerDir.exists()) {
			final Project project = getProject();

			final File zipFile = FileUtil.get(project, getZipUrl());

			project.copy(
				new Action<CopySpec>() {

					@Override
					public void execute(CopySpec copySpec) {
						copySpec.eachFile(new StripPathSegmentsAction(2));
						copySpec.from(project.zipTree(zipFile));
						copySpec.include(
							"apache-tomcat-*/webapps/manager/**/*");
						copySpec.into(managerDir.getParentFile());
						copySpec.setIncludeEmptyDirs(false);
					}

				});
		}

		Document document = null;

		final File tomcatUsersXmlFile = new File(
			getDir(), "conf/tomcat-users.xml");

		try (InputStreamReader inputStreamReader = new InputStreamReader(
				new FileInputStream(tomcatUsersXmlFile))) {

			document = DOMBuilder.parse(inputStreamReader);
		}

		Element tomcatUsersElement = document.getDocumentElement();

		Set<String> existentRoleNames = new HashSet<>();
		boolean tomcatManagerUserExists = false;

		NodeList nodeList = tomcatUsersElement.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);

			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}

			Element element = (Element)node;

			String elementName = element.getNodeName();

			if (elementName.equals("role")) {
				String roleName = element.getAttribute("rolename");

				existentRoleNames.add(roleName);
			}
			else if (elementName.equals("user")) {
				String userName = element.getAttribute("username");

				if (userName.equals(getManagerUserName())) {
					tomcatManagerUserExists = true;
				}
			}
		}

		boolean tomcatUsersXmlFileModified = false;

		for (String roleName : _TOMCAT_USERS_ROLE_NAMES) {
			if (!existentRoleNames.contains(roleName)) {
				Element element = document.createElement("role");

				element.setAttribute("rolename", roleName);

				tomcatUsersElement.appendChild(element);

				tomcatUsersXmlFileModified = true;
			}
		}

		if (!tomcatManagerUserExists) {
			Element element = document.createElement("user");

			element.setAttribute("password", getManagerPassword());
			element.setAttribute(
				"roles",
				"tomcat,manager-gui,manager-script,manager-jmx,manager-status");
			element.setAttribute("username", getManagerUserName());

			tomcatUsersElement.appendChild(element);

			tomcatUsersXmlFileModified = true;
		}

		if (tomcatUsersXmlFileModified) {
			Path timestampTomcatUserXmlFilePath = Paths.get(
				tomcatUsersXmlFile.toString() + "." +
					_timestampDateFormat.format(new Date()));

			Files.copy(
				tomcatUsersXmlFile.toPath(), timestampTomcatUserXmlFilePath);

			try (FileOutputStream fileOutputStream = new FileOutputStream(
					tomcatUsersXmlFile)) {

				XmlUtil.serialize(tomcatUsersElement, fileOutputStream);
			}
		}
	}

	private void _setUpOsgiModules() {
		Project project = getProject();

		project.copy(
			new Action<CopySpec>() {

				@Override
				public void execute(CopySpec copySpec) {
					File moduleFrameworkBaseDir = getModuleFrameworkBaseDir();

					File modulesDir = new File(
						moduleFrameworkBaseDir, "modules");

					if (!isOverwriteTestModules()) {
						copySpec.eachFile(
							new ExcludeExistingFileAction(modulesDir));
					}

					copySpec.from(new File(moduleFrameworkBaseDir, "test"));
					copySpec.into(modulesDir);
				}

			});
	}

	private void _setUpSetEnv() throws IOException {
		_setUpAspectJ();
		_setUpJmx();
		_setUpJpda();
	}

	private static final String[] _TOMCAT_USERS_ROLE_NAMES = {
		"manager-gui", "manager-jmx", "manager-script", "manager-status",
		"tomcat"
	};

	private Object _aspectJAgent;
	private Object _aspectJConfiguration;
	private boolean _debugLogging;
	private Object _dir;
	private boolean _jmxRemoteAuthenticate;
	private Object _jmxRemotePort;
	private boolean _jmxRemoteSsl;
	private Object _managerPassword;
	private Object _managerUserName;
	private Object _moduleFrameworkBaseDir;
	private boolean _overwriteTestModules;
	private final DateFormat _timestampDateFormat = new SimpleDateFormat(
		"yyyyMMddkkmmssSSS");
	private Object _zipUrl;

}