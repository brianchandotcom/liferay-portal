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

package com.liferay.gradle.plugins.poshi.runner;

import java.io.File;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gradle.api.Project;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.JavaExec;
import org.gradle.process.JavaExecSpec;

/**
 * @author Andrea Di Giorgi
 */
public class PoshiRunnerTask extends JavaExec {

	@Override
	public JavaExecSpec args(Iterable<?> args) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec args(Object... args) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void exec() {
		super.setArgs(getArgs());
		super.setClasspath(getClasspath());
		super.setSystemProperties(getSystemProperties());

		super.exec();
	}

	@Override
	public List<String> getArgs() {
		return Collections.singletonList(
			"com.liferay.poshi.runner.PoshiRunner");
	}

	@InputDirectory
	public File getBaseDir() {
		return _baseDir;
	}

	@Override
	public FileCollection getClasspath() {
		Project project = getProject();

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		return configurationContainer.getByName(
			PoshiRunnerPlugin.POSHI_RUNNER_CONFIGURATION_NAME);
	}

	@Override
	public String getMain() {
		return "org.junit.runner.JUnitCore";
	}

	public Map<String, Object> getPoshiProperties() {
		return _poshiProperties;
	}

	@Override
	public Map<String, Object> getSystemProperties() {
		Map<String, Object> systemProperties = new HashMap<>();

		systemProperties.putAll(getPoshiProperties());

		systemProperties.put("test.basedir", getBaseDir());
		systemProperties.put("test.name", getTestName());

		return systemProperties;
	}

	public String getTestName() {
		return _testName;
	}

	public void poshiProperty(String key, Object value) {
		_poshiProperties.put(key, value);
	}

	@Override
	public JavaExec setArgs(Iterable<?> applicationArgs) {
		throw new UnsupportedOperationException();
	}

	public void setBaseDir(File baseDir) {
		_baseDir = baseDir;
	}

	@Override
	public JavaExec setClasspath(FileCollection classpath) {
		throw new UnsupportedOperationException();
	}

	public void setPoshiProperties(Map<String, ?> poshiProperties) {
		_poshiProperties.clear();

		_poshiProperties.putAll(poshiProperties);
	}

	@Override
	public void setSystemProperties(Map<String, ?> properties) {
		throw new UnsupportedOperationException();
	}

	public void setTestName(String testName) {
		_testName = testName;
	}

	@Override
	public JavaExec systemProperties(Map<String, ?> properties) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec systemProperty(String name, Object value) {
		throw new UnsupportedOperationException();
	}

	private File _baseDir;
	private final Map<String, Object> _poshiProperties = new HashMap<>();
	private String _testName;

}