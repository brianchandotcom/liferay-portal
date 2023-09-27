/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.test.integration.task;

import com.liferay.gradle.plugins.test.integration.internal.util.GradleUtil;
import com.liferay.gradle.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.gradle.api.DefaultTask;
import org.gradle.api.Task;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
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
	@PathSensitive(PathSensitivity.RELATIVE)
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