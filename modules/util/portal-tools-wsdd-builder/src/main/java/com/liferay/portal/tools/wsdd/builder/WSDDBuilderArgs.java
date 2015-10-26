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

	public static final String FILE_NAME = "service.xml";

	public static final String OUTPUT_PATH = "src";

	public static final String SERVER_CONFIG_FILE_NAME = "server-config.wsdd";

	public static final String SERVICE_NAMESPACE = "Plugin";

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
	private String _fileName = FILE_NAME;
	private String _outputPath = OUTPUT_PATH;
	private String _serverConfigFileName = SERVER_CONFIG_FILE_NAME;
	private String _serviceNamespace = SERVICE_NAMESPACE;

}