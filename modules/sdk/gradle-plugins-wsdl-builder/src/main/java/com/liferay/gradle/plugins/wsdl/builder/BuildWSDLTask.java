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

package com.liferay.gradle.plugins.wsdl.builder;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.SkipWhenEmpty;

/**
 * @author Andrea Di Giorgi
 */
public class BuildWSDLTask extends DefaultTask {

	public File getDestinationDir() {
		Project project = getProject();

		return project.file(_destinationDir);
	}

	public File getInputDir() {
		Project project = getProject();

		return project.file(_inputDir);
	}

	@InputFiles
	@SkipWhenEmpty
	public FileCollection getInputFiles() {
		Project project = getProject();

		File wsdlDir = getInputDir();

		if (wsdlDir == null) {
			return project.files();
		}

		Map<String, Object> args = new HashMap<>();

		args.put("dir", wsdlDir);
		args.put("include", "*.wsdl");

		return project.fileTree(args);
	}

	@Input
	public boolean isIncludeSource() {
		return _includeSource;
	}

	public void setDestinationDir(Object destinationDir) {
		_destinationDir = destinationDir;
	}

	public void setIncludeSource(boolean includeSource) {
		_includeSource = includeSource;
	}

	public void setInputDir(Object inputDir) {
		_inputDir = inputDir;
	}

	private Object _destinationDir = "lib";
	private boolean _includeSource = true;
	private Object _inputDir = "wsdl";

}