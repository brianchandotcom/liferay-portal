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

package com.liferay.xml.formatter.maven;

import com.liferay.xml.formatter.XMLFormatterArgs;
import com.liferay.xml.formatter.XMLFormatterInvoker;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Invoke Liferay XML Formatter to format files.
 *
 * @author Andrea Di Giorgi
 * @goal format-xml
 */
public class FormatXMLMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			XMLFormatterInvoker.invoke(baseDir, _xmlFormatterArgs);
		}
		catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	/**
	 * @parameter
	 */
	public void setFileName(String fileName) {
		_xmlFormatterArgs.setFileName(fileName);
	}

	/**
	 * @parameter
	 */
	public void setStripComments(boolean stripComments) {
		_xmlFormatterArgs.setStripComments(stripComments);
	}

	/**
	 * @parameter default-value="${project.basedir}"
	 * @readonly
	 */
	protected File baseDir;

	private final XMLFormatterArgs _xmlFormatterArgs = new XMLFormatterArgs();

}