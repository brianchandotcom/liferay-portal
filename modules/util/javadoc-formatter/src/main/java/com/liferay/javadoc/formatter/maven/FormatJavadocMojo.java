/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.javadoc.formatter.maven;

import com.liferay.javadoc.formatter.JavadocFormatter;
import com.liferay.javadoc.formatter.JavadocFormatterArgs;
import com.liferay.javadoc.formatter.JavadocFormatterInvoker;

import java.io.File;

import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Runs Liferay Javadoc Formatter to format files.
 *
 * @author Andrea Di Giorgi
 * @goal format
 */
public class FormatJavadocMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			@SuppressWarnings("rawtypes")
			Map pluginContext = getPluginContext();

			JavadocFormatter javadocFormatter = JavadocFormatterInvoker.invoke(
				baseDir, _javadocFormatterArgs);

			pluginContext.put(
				JavadocFormatterArgs.OUTPUT_KEY_MODIFIED_FILES,
				javadocFormatter.getModifiedFileNames());
		}
		catch (Exception exception) {
			throw new MojoExecutionException(exception.getMessage(), exception);
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