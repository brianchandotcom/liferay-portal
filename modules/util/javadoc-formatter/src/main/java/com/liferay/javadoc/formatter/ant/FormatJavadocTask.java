/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.javadoc.formatter.ant;

import com.liferay.javadoc.formatter.JavadocFormatter;
import com.liferay.javadoc.formatter.JavadocFormatterArgs;
import com.liferay.javadoc.formatter.JavadocFormatterInvoker;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * @author Andrea Di Giorgi
 */
public class FormatJavadocTask extends Task {

	@Override
	public void execute() throws BuildException {
		try {
			Project project = getProject();

			JavadocFormatter javadocFormatter = JavadocFormatterInvoker.invoke(
				project.getBaseDir(), _javadocFormatterArgs);

			project.addIdReference(
				JavadocFormatterArgs.OUTPUT_KEY_MODIFIED_FILES,
				javadocFormatter.getModifiedFileNames());
		}
		catch (Exception exception) {
			throw new BuildException(exception);
		}
	}

	public void setAuthor(String author) {
		_javadocFormatterArgs.setAuthor(author);
	}

	public void setGenerateXml(boolean generateXml) {
		_javadocFormatterArgs.setGenerateXml(generateXml);
	}

	public void setInitializeMissingJavadocs(
		boolean initializeMissingJavadocs) {

		_javadocFormatterArgs.setInitializeMissingJavadocs(
			initializeMissingJavadocs);
	}

	public void setInputDirName(String inputDirName) {
		_javadocFormatterArgs.setInputDirName(inputDirName);
	}

	public void setLimits(String limits) {
		_javadocFormatterArgs.setLimits(limits);
	}

	public void setOutputFilePrefix(String outputFilePrefix) {
		_javadocFormatterArgs.setOutputFilePrefix(outputFilePrefix);
	}

	public void setUpdateJavadocs(boolean updateJavadocs) {
		_javadocFormatterArgs.setUpdateJavadocs(updateJavadocs);
	}

	private final JavadocFormatterArgs _javadocFormatterArgs =
		new JavadocFormatterArgs();

}