/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Kyle Miho
 */
public class UpgradeJavaServiceImplCheck
	extends BaseAddComponentAnnotationCheck {

	@Override
	protected String getAnnotationContent(
		String absolutePath, String className, String content,
		JavaClass javaClass) {

		return joinLines(
			"@Component(", "\tproperty = {",
			String.format(
				"\t\t\"json.web.service.context.name=%s\",",
				_getContextName(javaClass, absolutePath)),
			String.format(
				"\t\t\"json.web.service.context.path=%s\"",
				_getContextPath(className)),
			"\t},", "\tservice = AopService.class", ")");
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {
			"com.liferay.portal.aop.AopService",
			"org.osgi.service.component.annotations.Component"
		};
	}

	@Override
	protected boolean isValidClassName(String className) {
		if (className.contains("LocalServiceBaseImpl")) {
			return false;
		}

		return className.contains("ServiceBaseImpl");
	}

	private String _getContextName(JavaClass javaClass, String absolutePath) {
		File serviceXMLFile = _getServiceXML(absolutePath);

		if (serviceXMLFile != null) {
			Document document = SourceUtil.readXML(
				FileUtil.read(serviceXMLFile));

			return StringUtil.toLowerCase(
				_getPortletShortName(document.getRootElement()));
		}

		String contextName = StringUtil.extractFirst(
			javaClass.getPackageName(), ".service.impl");

		return StringUtil.extractLast(contextName, ".");
	}

	private String _getContextPath(String baseImplName) {
		return StringUtil.extractFirst(baseImplName, "ServiceBaseImpl");
	}

	private String _getPortletShortName(Element rootElement) {
		Element portletElement = rootElement.element("portlet");

		if (portletElement != null) {
			return portletElement.attributeValue("short-name");
		}

		Element namespaceElement = rootElement.element("namespace");

		return namespaceElement.getText();
	}

	private File _getServiceXML(String absolutePath) {
		Path serviceXmlPath = Paths.get(absolutePath);

		do {
			serviceXmlPath = serviceXmlPath.getParent();
		}
		while (!serviceXmlPath.endsWith("src"));

		serviceXmlPath = serviceXmlPath.getParent();

		serviceXmlPath = serviceXmlPath.resolve("service.xml");

		File file = serviceXmlPath.toFile();

		if (!file.exists()) {
			return null;
		}

		return file;
	}

}