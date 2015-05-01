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

import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class WSDLBuilderExtension {

	public WSDLBuilderExtension(Project project) {
		_project = project;
	}

	public File getDestinationDir() {
		return _project.file(_destinationDir);
	}

	public File getWSDLDir() {
		return _project.file(_wsdlDir);
	}

	public boolean isIncludeSource() {
		return _includeSource;
	}

	public void setDestinationDir(Object destinationDir) {
		_destinationDir = destinationDir;
	}

	public void setIncludeSource(boolean includeSource) {
		_includeSource = includeSource;
	}

	public void setWSDLDir(Object wsdlDir) {
		_wsdlDir = wsdlDir;
	}

	private Object _destinationDir = "lib";
	private boolean _includeSource = true;
	private final Project _project;
	private Object _wsdlDir = "wsdl";

}