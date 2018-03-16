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

import com.liferay.source.formatter.checks.comparator.ElementComparator;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLDDLStructuresFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.endsWith("structures.xml")) {
			_checkDDLStructuresXML(fileName, content);
		}

		return content;
	}

	private void _checkDDLStructuresXML(String fileName, String content)
		throws Exception {

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		checkElementOrder(
			fileName, rootElement, "structure", null,
			new StructureElementComparator());

		List<Element> structureElements = rootElement.elements("structure");

		for (Element structureElement : structureElements) {
			Element structureRootElement = structureElement.element("root");

			checkElementOrder(
				fileName, structureRootElement, "dynamic-element", "root",
				new ElementComparator());

			List<Element> dynamicElementElements =
				structureRootElement.elements("dynamic-element");

			for (Element dynamicElementElement : dynamicElementElements) {
				Element metaDataElement = dynamicElementElement.element(
					"meta-data");

				checkElementOrder(
					fileName, metaDataElement, "entry", "meta-data",
					new ElementComparator());
			}
		}
	}

	private static class StructureElementComparator extends ElementComparator {

		@Override
		public String getElementName(Element element) {
			Element nameElement = element.element(getNameAttribute());

			return nameElement.getText();
		}

	}

}