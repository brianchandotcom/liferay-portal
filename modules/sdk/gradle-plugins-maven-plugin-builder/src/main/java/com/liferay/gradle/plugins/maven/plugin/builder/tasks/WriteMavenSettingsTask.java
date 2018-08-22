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

package com.liferay.gradle.plugins.maven.plugin.builder.tasks;

import com.liferay.gradle.plugins.maven.plugin.builder.internal.util.GradleUtil;
import com.liferay.gradle.plugins.maven.plugin.builder.internal.util.XMLUtil;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Andrea Di Giorgi
 */
public class WriteMavenSettingsTask extends DefaultTask {

	@Input
	@Optional
	public File getLocalRepositoryDir() {
		return GradleUtil.toFile(getProject(), _localRepositoryDir);
	}

	@Input
	@Optional
	public String getNonProxyHosts() {
		return GradleUtil.toString(_nonProxyHosts);
	}

	@OutputFile
	public File getOutputFile() {
		return GradleUtil.toFile(getProject(), _outputFile);
	}

	@Input
	@Optional
	public String getProxyHost() {
		return GradleUtil.toString(_proxyHost);
	}

	@Input
	@Optional
	public String getProxyPassword() {
		return GradleUtil.toString(_proxyPassword);
	}

	@Input
	@Optional
	public Integer getProxyPort() {
		return GradleUtil.toInteger(_proxyPort);
	}

	@Input
	@Optional
	public String getProxyUser() {
		return GradleUtil.toString(_proxyUser);
	}

	@Input
	@Optional
	public String getRepositoryUrl() {
		return GradleUtil.toString(_repositoryUrl);
	}

	public void setLocalRepositoryDir(Object localRepositoryDir) {
		_localRepositoryDir = localRepositoryDir;
	}

	public void setNonProxyHosts(Object nonProxyHosts) {
		_nonProxyHosts = nonProxyHosts;
	}

	public void setOutputFile(Object outputFile) {
		_outputFile = outputFile;
	}

	public void setProxyHost(Object proxyHost) {
		_proxyHost = proxyHost;
	}

	public void setProxyPassword(Object proxyPassword) {
		_proxyPassword = proxyPassword;
	}

	public void setProxyPort(Object proxyPort) {
		_proxyPort = proxyPort;
	}

	public void setProxyUser(Object proxyUser) {
		_proxyUser = proxyUser;
	}

	public void setRepositoryUrl(Object repositoryUrl) {
		_repositoryUrl = repositoryUrl;
	}

	@TaskAction
	public void writeMavenSettingsFile() throws Exception {
		Document document = XMLUtil.createDocument();

		Element settingsElement = document.createElementNS(
			"http://maven.apache.org/SETTINGS/1.0.0", "settings");

		document.appendChild(settingsElement);

		File localRepositoryDir = getLocalRepositoryDir();

		if (localRepositoryDir != null) {
			XMLUtil.appendElement(
				document, settingsElement, "localRepository",
				FileUtil.getAbsolutePath(localRepositoryDir));
		}

		String repositoryUrl = getRepositoryUrl();

		if (Validator.isNotNull(repositoryUrl)) {
			Element mirrorsElement = document.createElement("mirrors");

			settingsElement.appendChild(mirrorsElement);

			Element mirrorElement = document.createElement("mirror");

			mirrorsElement.appendChild(mirrorElement);

			XMLUtil.appendElement(document, mirrorElement, "mirrorOf", "*");
			XMLUtil.appendElement(
				document, mirrorElement, "url", repositoryUrl);
		}

		String proxyHost = getProxyHost();
		Integer proxyPort = getProxyPort();

		if (Validator.isNotNull(proxyHost) && (proxyPort != null)) {
			Element proxiesElement = document.createElement("proxies");

			settingsElement.appendChild(proxiesElement);

			Element proxyElement = document.createElement("proxy");

			proxiesElement.appendChild(proxyElement);

			XMLUtil.appendElement(document, proxyElement, "host", proxyHost);
			XMLUtil.appendElement(
				document, proxyElement, "port", proxyPort.toString());

			_appendNonnullElement(
				document, proxyElement, "username", getProxyUser());
			_appendNonnullElement(
				document, proxyElement, "password", getProxyPassword());
			_appendNonnullElement(
				document, proxyElement, "nonProxyHosts", getNonProxyHosts());
		}

		XMLUtil.write(document, getOutputFile());
	}

	private static void _appendNonnullElement(
		Document document, Element parentElement, String name, String text) {

		if (Validator.isNull(text)) {
			return;
		}

		XMLUtil.appendElement(document, parentElement, name, text);
	}

	private Object _localRepositoryDir;
	private Object _nonProxyHosts;
	private Object _outputFile;
	private Object _proxyHost;
	private Object _proxyPassword;
	private Object _proxyPort;
	private Object _proxyUser;
	private Object _repositoryUrl;

}