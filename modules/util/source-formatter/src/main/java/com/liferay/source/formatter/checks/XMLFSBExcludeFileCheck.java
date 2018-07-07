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

package com.liferay.source.formatter.checks;

import com.liferay.portal.tools.ImportPackage;
import com.liferay.source.formatter.checks.comparator.ElementComparator;
import com.liferay.source.formatter.checks.util.SourceUtil;

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

	private void _checkFSBExcludeXML(String fileName, String content)
		throws DocumentException {

		Document document = SourceUtil.readXML(content);

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