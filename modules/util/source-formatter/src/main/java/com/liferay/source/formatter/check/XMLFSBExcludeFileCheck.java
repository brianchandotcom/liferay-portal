/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.tools.ImportPackage;
import com.liferay.source.formatter.check.comparator.ElementComparator;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLFSBExcludeFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws DocumentException {

		if (fileName.endsWith("/fsb-exclude.xml")) {
			_checkFSBExcludeXML(fileName, content);
		}

		return content;
	}

	private void _checkFSBExcludeXML(String fileName, String content) {
		Document document = SourceUtil.readXML(content);

		if (document == null) {
			return;
		}

		Element rootElement = document.getRootElement();

		checkElementOrder(
			fileName, rootElement, "Match", null,
			new FSBExcludeMatchComparator());

		for (Element matchElement :
				(List<Element>)rootElement.elements("Match")) {

			for (Element orElement :
					(List<Element>)matchElement.elements("Or")) {

				checkElementOrder(
					fileName, orElement, "Bug", null,
					new ElementComparator("pattern"));
				checkElementOrder(
					fileName, orElement, "Class", null,
					new FSBExcludeClassComparator());
				checkElementOrder(
					fileName, orElement, "Method", null,
					new ElementComparator());
			}
		}
	}

	private class FSBExcludeClassComparator extends ElementComparator {

		@Override
		public int compare(Element element1, Element element2) {
			String elementName1 = getElementName(element1);
			String elementName2 = getElementName(element2);

			if ((elementName1 == null) || (elementName2 == null)) {
				return 0;
			}

			ImportPackage importPackage1 = new ImportPackage(
				elementName1, false, elementName1);
			ImportPackage importPackage2 = new ImportPackage(
				elementName2, false, elementName2);

			return importPackage1.compareTo(importPackage2);
		}

	}

	private class FSBExcludeMatchComparator extends FSBExcludeClassComparator {

		@Override
		public String getElementName(Element element) {
			Element classElement = element.element("Class");

			if (classElement == null) {
				return null;
			}

			return classElement.attributeValue("name");
		}

	}

}