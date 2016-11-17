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

package com.liferay.javadoc.formatter.maven;

import com.liferay.javadoc.formatter.JavadocFormatter;
import com.liferay.javadoc.formatter.JavadocFormatterArgs;
import com.liferay.javadoc.formatter.JavadocFormatterInvoker;

import java.io.File;

import java.util.Map;
import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Runs Liferay Javadoc Formatter to format files.
 *
 * @author Andrea Di Giorgi
 * @goal format-javadoc
 */
public class FormatJavadocMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			@SuppressWarnings("rawtypes")
			Map pluginContext = getPluginContext();

			JavadocFormatter javadocFormatter = JavadocFormatterInvoker.invoke(
				baseDir, _javadocFormatterArgs);

			Set<String> modifiedFileNames =
				javadocFormatter.getModifiedFileNames();

			pluginContext.put(
				JavadocFormatterArgs.OUTPUT_KEY_MODIFIED_FILES,
				modifiedFileNames);
		}
		catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	/**
	 * @parameter
	 */
	public void setAuthor(String author) {
		_javadocFormatterArgs.setAuthor(author);
	}

	/**
	 * @parameter
	 */
	public void setGenerateXml(boolean generateXml) {
		_javadocFormatterArgs.setGenerateXml(generateXml);
	}

	/**
	 * @parameter
	 */
	public void setInitializeMissingJavadocs(
		boolean initializeMissingJavadocs) {

		_javadocFormatterArgs.setInitializeMissingJavadocs(
			initializeMissingJavadocs);
	}

	/**
	 * @parameter
	 */
	public void setInputDirName(String inputDirName) {
		_javadocFormatterArgs.setInputDirName(inputDirName);
	}

	/**
	 * @parameter
	 */
	public void setLimits(String limits) {
		_javadocFormatterArgs.setLimits(limits);
	}

	/**
	 * @parameter
	 */
	public void setOutputFilePrefix(String outputFilePrefix) {
		_javadocFormatterArgs.setOutputFilePrefix(outputFilePrefix);
	}

	/**
	 * @parameter
	 */
	public void setUpdateJavadocs(boolean updateJavadocs) {
		_javadocFormatterArgs.setUpdateJavadocs(updateJavadocs);
	}

	/**
	 * @parameter default-value="${project.basedir}
	 * @readonly
	 */
	protected File baseDir;

	private final JavadocFormatterArgs _javadocFormatterArgs =
		new JavadocFormatterArgs();

}