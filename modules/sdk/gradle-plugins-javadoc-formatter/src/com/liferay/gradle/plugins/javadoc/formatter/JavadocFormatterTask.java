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

package com.liferay.gradle.plugins.javadoc.formatter;

import com.liferay.javadoc.formatter.JavadocFormatterArgs;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Project;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.JavaExec;
import org.gradle.process.JavaExecSpec;

/**
 * @author Andrea Di Giorgi
 */
public class JavadocFormatterTask extends JavaExec {

	@Override
	public JavaExecSpec args(Iterable<?> args) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec args(Object... args) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec classpath(Object... paths) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void exec() {
		super.setArgs(getArgs());
		super.setClasspath(getClasspath());
		super.setWorkingDir(getWorkingDir());

		super.exec();
	}

	@Override
	public List<String> getArgs() {
		List<String> args = new ArrayList<>();

		Project project = getProject();

		ExtensionContainer extensionContainer = project.getExtensions();

		JavadocFormatterArgs javadocFormatterArgs =
			extensionContainer.getByType(JavadocFormatterArgs.class);

		args.add("javadoc.author=" + javadocFormatterArgs.getAuthor());
		args.add(
			"javadoc.init=" +
				javadocFormatterArgs.isInitializeMissingJavadocs());
		args.add("javadoc.input.dir=" + javadocFormatterArgs.getInputDir());
		args.add("javadoc.limit=" + _merge(javadocFormatterArgs.getLimits()));
		args.add(
			"javadoc.lowest.supported.java.version=" +
				javadocFormatterArgs.getLowestSupportedJavaVersion());
		args.add(
			"javadoc.output.file.prefix=" +
				javadocFormatterArgs.getOutputFilePrefix());
		args.add("javadoc.update=" + javadocFormatterArgs.isUpdateJavadocs());

		return args;
	}

	@Override
	public FileCollection getClasspath() {
		Project project = getProject();

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		return configurationContainer.getByName(
			JavadocFormatterPlugin.CONFIGURATION_NAME);
	}

	@Override
	public String getMain() {
		return "com.liferay.javadoc.formatter.JavadocFormatter";
	}

	@Override
	public File getWorkingDir() {
		Project project = getProject();

		return project.getProjectDir();
	}

	@Override
	public JavaExec setArgs(Iterable<?> applicationArgs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec setClasspath(FileCollection classpath) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setWorkingDir(Object dir) {
		throw new UnsupportedOperationException();
	}

	private String _merge(String[] array) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]);

			if ((i + 1) < array.length) {
				sb.append(',');
			}
		}

		return sb.toString();
	}

}