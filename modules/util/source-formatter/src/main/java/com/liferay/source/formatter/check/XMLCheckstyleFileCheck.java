/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.source.formatter.check.comparator.ElementComparator;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLCheckstyleFileCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith("/checkstyle.xml") ||
			fileName.endsWith("/checkstyle-jsp.xml")) {

			_checkCheckstyleXML(fileName, content);
		}

		return content;
	}

	private void _checkCheckstyleXML(String fileName, String content) {
		Document document = SourceUtil.readXML(content);

		if (document == null) {
			return;
		}

		_checkElement(fileName, document.getRootElement());
	}

	private void _checkElement(String fileName, Element element) {
		checkElementOrder(
			fileName, element, "module", null, new ElementComparator());

		String moduleName = element.attributeValue("name");

		checkElementOrder(
			fileName, element, "message", moduleName,
			new ElementComparator("key"));
		checkElementOrder(
			fileName, element, "property", moduleName, new ElementComparator());

		List<Element> childModuleElements = (List<Element>)element.elements(
			"module");

		if (childModuleElements.isEmpty()) {
			if (!moduleName.endsWith("Check")) {
				addMessage(
					fileName,
					StringBundler.concat(
						"Name of class '", moduleName,
						"' should end with 'Check'"));
			}

			_checkMissingProperty(fileName, element, moduleName, "category");
			_checkMissingProperty(fileName, element, moduleName, "description");
		}

		for (Element moduleElement : childModuleElements) {
			_checkElement(fileName, moduleElement);
		}
	}

	private void _checkMissingProperty(
		String fileName, Element moduleElement, String moduleName,
		String propertyName) {

		for (Element propertyElement :
				(List<Element>)moduleElement.elements("property")) {

			if (propertyName.equals(propertyElement.attributeValue("name"))) {
				return;
			}
		}

		addMessage(
			fileName,
			StringBundler.concat(
				"Missing property '", propertyName, "' for check '", moduleName,
				"'"));
	}

}