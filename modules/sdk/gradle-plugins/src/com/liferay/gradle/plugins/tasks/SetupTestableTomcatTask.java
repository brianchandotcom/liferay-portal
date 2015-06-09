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

import com.liferay.gradle.util.FileUtil;

import groovy.lang.Closure;

import groovy.xml.DOMBuilder;
import groovy.xml.XmlUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.CopySpec;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.TaskAction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Andrea Di Giorgi
 */
public class SetupTestableTomcatTask extends DefaultTask {

	@Input
	public int getJmxRemotePort() {
		return _jmxRemotePort;
	}

	@InputDirectory
	public File getModuleFrameworkBaseDir() {
		return _moduleFrameworkBaseDir;
	}

	@InputDirectory
	public File getTomcatDir() {
		return _tomcatDir;
	}

	@Input
	public String getTomcatManagerPassword() {
		return _tomcatManagerPassword;
	}

	@Input
	public String getTomcatManagerUserName() {
		return _tomcatManagerUserName;
	}

	@Input
	public String getTomcatZipUrl() {
		return _tomcatZipUrl;
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

	public void setDebugLogging(boolean debugLogging) {
		_debugLogging = debugLogging;
	}

	public void setJmxRemoteAuthenticate(boolean jmxRemoteAuthenticate) {
		_jmxRemoteAuthenticate = jmxRemoteAuthenticate;
	}

	public void setJmxRemotePort(int jmxRemotePort) {
		_jmxRemotePort = jmxRemotePort;
	}

	public void setJmxRemoteSsl(boolean jmxRemoteSsl) {
		_jmxRemoteSsl = jmxRemoteSsl;
	}

	public void setModuleFrameworkBaseDir(File moduleFrameworkBaseDir) {
		_moduleFrameworkBaseDir = moduleFrameworkBaseDir;
	}

	public void setTomcatDir(File tomcatDir) {
		_tomcatDir = tomcatDir;
	}

	public void setTomcatManagerPassword(String tomcatManagerPassword) {
		_tomcatManagerPassword = tomcatManagerPassword;
	}

	public void setTomcatManagerUserName(String tomcatManagerUserName) {
		_tomcatManagerUserName = tomcatManagerUserName;
	}

	public void setTomcatZipUrl(String tomcatZipUrl) {
		_tomcatZipUrl = tomcatZipUrl;
	}

	@TaskAction
	public void setupTestableTomcat() throws Exception {
		setupJmx();
		setupLogging();
		setupManager();
		setupOsgiModules();
	}

	protected boolean contains(String fileName, String s) throws Exception {
		File file = new File(getTomcatDir(), fileName);

		String fileContent = new String(Files.readAllBytes(file.toPath()));

		if (fileContent.contains(s)) {
			return true;
		}

		return false;
	}

	protected PrintWriter getAppendPrintWriter(String fileName)
		throws Exception {

		File file = new File(getTomcatDir(), fileName);

		return new PrintWriter(
			Files.newBufferedWriter(
				file.toPath(), StandardCharsets.UTF_8,
				StandardOpenOption.APPEND, StandardOpenOption.WRITE));
	}

	protected String getJmxOptions() {
		StringBuilder sb = new StringBuilder();

		sb.append("-Dcom.sun.management.jmxremote");
		sb.append(" -Dcom.sun.management.jmxremote.authenticate=");
		sb.append(isJmxRemoteAuthenticate());
		sb.append(" -Dcom.sun.management.jmxremote.port=");
		sb.append(getJmxRemotePort());
		sb.append(" -Dcom.sun.management.jmxremote.ssl=");
		sb.append(isJmxRemoteSsl());

		return sb.toString();
	}

	protected void setupJmx() throws Exception {
		String jmxOptions = getJmxOptions();

		if (!contains("bin/setenv.bat", jmxOptions)) {
			try (PrintWriter printWriter = getAppendPrintWriter(
					"bin/setenv.bat")) {

				printWriter.print("set \"JMX_OPTS=");
				printWriter.print(jmxOptions);
				printWriter.println('\"');

				printWriter.println();

				printWriter.println(
					"set \"CATALINA_OPTS=%CATALINA_OPTS% %JMX_OPTS%\"");
			}
		}

		if (!contains("bin/setenv.sh", jmxOptions)) {
			try (PrintWriter printWriter = getAppendPrintWriter(
					"bin/setenv.sh")) {

				printWriter.print("JMX_OPTS=\"");
				printWriter.print(jmxOptions);
				printWriter.println('\"');

				printWriter.println();

				printWriter.println(
					"CATALINA_OPTS=\"${CATALINA_OPTS} ${JMX_OPTS}\"");
			}
		}
	}

	protected void setupLogging() throws Exception {
		if (!isDebugLogging() ||
			contains("conf/Logging.properties", "org.apache.catalina.level")) {

			return;
		}

		try (PrintWriter printWriter = getAppendPrintWriter(
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

	protected void setupManager() throws Exception {
		Project project = getProject();

		File managerDir = new File(getTomcatDir(), "webapps/manager");

		if (!managerDir.exists()) {
			File tomcatZipFile = new File(getTemporaryDir(), "tomcat.zip");

			FileUtil.get(project, getTomcatZipUrl(), tomcatZipFile);

			FileUtil.unzip(
				project, tomcatZipFile, managerDir.getParentFile(), 2, null,
				new String[] {"apache-tomcat-*/webapps/manager/**/*"});

			project.delete(tomcatZipFile);
		}

		Document document;

		final File tomcatUsersXmlFile = new File(
			getTomcatDir(), "conf/tomcat-users.xml");

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

				if (userName.equals(getTomcatManagerUserName())) {
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

			element.setAttribute("password", getTomcatManagerPassword());
			element.setAttribute(
				"roles",
				"tomcat,manager-gui,manager-script,manager-jmx,manager-status");
			element.setAttribute("username", getTomcatManagerUserName());

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

	protected void setupOsgiModules() {
		Project project = getProject();

		Closure<Void> closure = new Closure<Void>(null) {

			@SuppressWarnings("unused")
			public void doCall(CopySpec copySpec) {
				File moduleFrameworkBaseDir = getModuleFrameworkBaseDir();

				copySpec.from(new File(moduleFrameworkBaseDir, "modules"));
				copySpec.into(new File(moduleFrameworkBaseDir, "test"));
			}

		};

		project.copy(closure);
	}

	private static final String[] _TOMCAT_USERS_ROLE_NAMES = {
		"manager-gui", "manager-jmx", "manager-script", "manager-status",
		"tomcat"
	};

	private boolean _debugLogging;
	private boolean _jmxRemoteAuthenticate;
	private int _jmxRemotePort;
	private boolean _jmxRemoteSsl;
	private File _moduleFrameworkBaseDir;
	private final DateFormat _timestampDateFormat = new SimpleDateFormat(
		"yyyyMMddkkmmssSSS");
	private File _tomcatDir;
	private String _tomcatManagerPassword;
	private String _tomcatManagerUserName;
	private String _tomcatZipUrl;

}