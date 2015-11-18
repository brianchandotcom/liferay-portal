/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.tools.servicebuilder.ServiceBuilder;
import com.liferay.portal.util.InitUtil;
import com.liferay.util.ant.Java2WsddTask;

import java.io.File;

import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class WSDDBuilder {

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		InitUtil.initWithSpring();

		WSDDBuilder wsddBuilder = new WSDDBuilder();

		wsddBuilder._fileName = arguments.get("wsdd.input.file");
		wsddBuilder._outputPath = arguments.get("wsdd.output.path");
		wsddBuilder._serverConfigFileName = arguments.get(
			"wsdd.server.config.file");
		wsddBuilder._serviceNamespace = arguments.get("wsdd.service.namespace");

		wsddBuilder.build();
	}

	public void build() throws Exception {
		if (!FileUtil.exists(_serverConfigFileName)) {
			ClassLoader classLoader = getClass().getClassLoader();

			String serverConfigContent = StringUtil.read(
				classLoader,
				"com/liferay/portal/tools/dependencies/server-config.wsdd");

			FileUtil.write(_serverConfigFileName, serverConfigContent);
		}

		String content = ServiceBuilder.getContent(_fileName);

		Document document = UnsecureSAXReaderUtil.read(content, true);

		Element rootElement = document.getRootElement();

		String packagePath = rootElement.attributeValue("package-path");

		Element portletElement = rootElement.element("portlet");
		Element namespaceElement = rootElement.element("namespace");

		if (portletElement != null) {
			_portletShortName = portletElement.attributeValue("short-name");
		}
		else {
			_portletShortName = namespaceElement.getText();
		}

		_outputPath +=
			StringUtil.replace(packagePath, ".", "/") + "/service/http";

		_packagePath = packagePath;

		List<Element> entityElements = rootElement.elements("entity");

		for (Element entityElement : entityElements) {
			String entityName = entityElement.attributeValue("name");

			boolean remoteService = GetterUtil.getBoolean(
				entityElement.attributeValue("remote-service"), true);

			if (remoteService) {
				_createServiceWSDD(entityName);

				WSDDMerger.merge(
					_outputPath + "/" + entityName + "Service_deploy.wsdd",
					_serverConfigFileName);
			}
		}
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

	private void _createServiceWSDD(String entityName) throws Exception {
		String className =
			_packagePath + ".service.http." + entityName + "ServiceSoap";

		String serviceName = StringUtil.replace(_portletShortName, " ", "_");

		if (!_portletShortName.equals("Portal")) {
			serviceName = _serviceNamespace + "_" + serviceName;
		}

		serviceName += ("_" + entityName + "Service");

		String[] wsdds = Java2WsddTask.generateWsdd(className, serviceName);

		FileUtil.write(
			new File(_outputPath + "/" + entityName + "Service_deploy.wsdd"),
			wsdds[0], true);

		FileUtil.write(
			new File(_outputPath + "/" + entityName + "Service_undeploy.wsdd"),
			wsdds[1], true);
	}

	private String _fileName;
	private String _outputPath;
	private String _packagePath;
	private String _portletShortName;
	private String _serverConfigFileName;
	private String _serviceNamespace;

}