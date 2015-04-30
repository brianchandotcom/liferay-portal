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

package com.liferay.portal.tools.wsdd.builder;

/**
 * @author Andrea Di Giorgi
 */
public class WSDDBuilderArgs {

	public String getClassPath() {
		return _classPath;
	}

	public String getFileName() {
		return _fileName;
	}

	public String getOutputPath() {
		return _outputPath;
	}

	public String getServerConfigFileName() {
		return _serverConfigFileName;
	}

	public String getServiceNamespace() {
		return _serviceNamespace;
	}

	public void setClassPath(String classPath) {
		_classPath = classPath;
	}

	public void setFileName(String fileName) {
		_fileName = fileName;
	}

	public void setOutputPath(String outputPath) {
		_outputPath = outputPath;
	}

	public void setServerConfigFileName(String serverConfigFileName) {
		_serverConfigFileName = serverConfigFileName;
	}

	public void setServiceNamespace(String serviceNamespace) {
		_serviceNamespace = serviceNamespace;
	}

	private String _classPath;
	private String _fileName = "service.xml";
	private String _outputPath = "src";
	private String _serverConfigFileName = "server-config.wsdd";
	private String _serviceNamespace = "Plugin";

}