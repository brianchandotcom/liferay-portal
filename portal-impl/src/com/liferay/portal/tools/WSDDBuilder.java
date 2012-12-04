/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.InitUtil;
import com.liferay.util.ant.Java2WsddTask;

import java.io.File;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

		String content = _getContent(_fileName);

		Document document = SAXReaderUtil.read(content, true);

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

	private void _addElements(Element element, Map<String, Element> elements) {
		for (Map.Entry<String, Element> entry : elements.entrySet()) {
			Element childElement = entry.getValue();

			element.add(childElement);
		}
	}

	private String _getContent(String fileName) throws Exception {
		Document document = _getContentDocument(fileName);

		Element rootElement = document.getRootElement();

		Element authorElement = null;
		Element namespaceElement = null;
		Map<String, Element> entityElements = new TreeMap<String, Element>();
		Map<String, Element> exceptionElements = new TreeMap<String, Element>();

		for (Element element : rootElement.elements()) {
			String elementName = element.getName();

			if (elementName.equals("author")) {
				element.detach();

				if (authorElement != null) {
					throw new IllegalArgumentException(
						"There can only be one author element");
				}

				authorElement = element;
			}
			else if (elementName.equals("namespace")) {
				element.detach();

				if (namespaceElement != null) {
					throw new IllegalArgumentException(
						"There can only be one namespace element");
				}

				namespaceElement = element;
			}
			else if (elementName.equals("entity")) {
				element.detach();

				String name = element.attributeValue("name");

				entityElements.put(name.toLowerCase(), element);
			}
			else if (elementName.equals("exceptions")) {
				element.detach();

				for (Element exceptionElement : element.elements("exception")) {
					exceptionElement.detach();

					exceptionElements.put(
						exceptionElement.getText(), exceptionElement);
				}
			}
		}

		if (authorElement != null) {
			rootElement.add(authorElement);
		}

		if (namespaceElement == null) {
			throw new IllegalArgumentException(
				"The namespace element is required");
		}
		else {
			rootElement.add(namespaceElement);
		}

		_addElements(rootElement, entityElements);

		if (!exceptionElements.isEmpty()) {
			Element exceptionsElement = rootElement.addElement("exceptions");

			_addElements(exceptionsElement, exceptionElements);
		}

		return document.asXML();
	}

	private Document _getContentDocument(String fileName) throws Exception {
		String content = FileUtil.read(new File(fileName));

		Document document = SAXReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		for (Element element : rootElement.elements()) {
			String elementName = element.getName();

			if (!elementName.equals("service-builder-import")) {
				continue;
			}

			element.detach();

			String dirName = fileName.substring(
				0, fileName.lastIndexOf(StringPool.SLASH) + 1);
			String serviceBuilderImportFileName = element.attributeValue(
				"file");

			Document serviceBuilderImportDocument = _getContentDocument(
				dirName + serviceBuilderImportFileName);

			Element serviceBuilderImportRootElement =
				serviceBuilderImportDocument.getRootElement();

			for (Element serviceBuilderImportElement :
					serviceBuilderImportRootElement.elements()) {

				serviceBuilderImportElement.detach();

				rootElement.add(serviceBuilderImportElement);
			}
		}

		return document;
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