/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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