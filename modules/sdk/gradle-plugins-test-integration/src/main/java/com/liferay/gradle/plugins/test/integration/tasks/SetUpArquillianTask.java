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
import com.liferay.gradle.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.gradle.api.DefaultTask;
import org.gradle.api.Task;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
public class SetUpArquillianTask
	extends DefaultTask implements JmxRemotePortSpec, ManagerSpec {

	public SetUpArquillianTask() {
		onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					File outputFile = _getOutputFile();

					if (outputFile.exists()) {
						return false;
					}

					return true;
				}

			});
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
	public File getOutputDir() {
		return GradleUtil.toFile(getProject(), _outputDir);
	}

	@Override
	public void setJmxRemotePort(Object jmxRemotePort) {
		_jmxRemotePort = jmxRemotePort;
	}

	@Override
	public void setManagerPassword(Object managerPassword) {
		_managerPassword = managerPassword;
	}

	@Override
	public void setManagerUserName(Object managerUserName) {
		_managerUserName = managerUserName;
	}

	public void setOutputDir(Object outputDir) {
		_outputDir = outputDir;
	}

	@TaskAction
	public void setUpArquillian() throws IOException {
		File outputFile = _getOutputFile();

		String xml = FileUtil.read(
			"com/liferay/gradle/plugins/test/integration/dependencies" +
				"/arquillian.xml");

		xml = xml.replace(
			"${app.server.tomcat.manager.password}", getManagerPassword());
		xml = xml.replace(
			"${app.server.tomcat.manager.user}", getManagerUserName());
		xml = xml.replace(
			"${jmx.remote.port}", String.valueOf(getJmxRemotePort()));

		Files.write(outputFile.toPath(), xml.getBytes(StandardCharsets.UTF_8));
	}

	private File _getOutputFile() {
		return new File(getOutputDir(), "arquillian.xml");
	}

	private Object _jmxRemotePort;
	private Object _managerPassword;
	private Object _managerUserName;
	private Object _outputDir;

}