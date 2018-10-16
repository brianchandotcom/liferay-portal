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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.checks.comparator.ElementComparator;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLPomFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws DocumentException {

		if (!absolutePath.contains("/maven/") &&
			fileName.endsWith("/pom.xml") && Validator.isNotNull(content)) {

			_checkOrder(fileName, content);
		}

		return content;
	}

	private void _checkOrder(String fileName, String content)
		throws DocumentException {

		//System.out.println(content);

		Document document = SourceUtil.readXML(content);

		Element rootElement = document.getRootElement();

		List<Element> dependenciesElements = rootElement.elements(
			"dependencies");

		PomDependencyElementComparator pomDependencyElementComparator =
			new PomDependencyElementComparator();

		for (Element dependenciesElement : dependenciesElements) {
			checkElementOrder(
				fileName, dependenciesElement, "dependency", null,
				pomDependencyElementComparator);
		}
	}

	private class PomDependencyElementComparator extends ElementComparator {

		@Override
		public int compare(
			Element dependencyElement1, Element dependencyElement2) {

			String groupId1 = getTagValue(dependencyElement1, "groupId");
			String groupId2 = getTagValue(dependencyElement2, "groupId");

			if (!groupId1.equals(groupId2)) {
				return groupId1.compareTo(groupId2);
			}

			String artifactId1 = getTagValue(dependencyElement1, "artifactId");
			String artifactId2 = getTagValue(dependencyElement2, "artifactId");

			return artifactId1.compareTo(artifactId2);
		}

		@Override
		public String getElementName(Element element) {
			return StringBundler.concat(
				"{groupId=", getTagValue(element, "groupId"), ",artifactId=",
				getTagValue(element, "artifactId"), "}");
		}

	}

}