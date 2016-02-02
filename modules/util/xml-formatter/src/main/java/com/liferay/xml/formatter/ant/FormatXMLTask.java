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

package com.liferay.xml.formatter.ant;

import com.liferay.xml.formatter.XMLFormatterArgs;
import com.liferay.xml.formatter.XMLFormatterInvoker;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * @author Andrea Di Giorgi
 */
public class FormatXMLTask extends Task {

	@Override
	public void execute() throws BuildException {
		try {
			Project project = getProject();

			XMLFormatterInvoker.invoke(project.getBaseDir(), _xmlFormatterArgs);
		}
		catch (Exception e) {
			throw new BuildException(e);
		}
	}

	public void setFileName(String fileName) {
		_xmlFormatterArgs.setFileName(fileName);
	}

	public void setStripComments(boolean stripComments) {
		_xmlFormatterArgs.setStripComments(stripComments);
	}

	private final XMLFormatterArgs _xmlFormatterArgs = new XMLFormatterArgs();

}