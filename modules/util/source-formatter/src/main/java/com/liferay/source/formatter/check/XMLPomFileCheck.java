/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.comparator.ElementComparator;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class XMLPomFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!absolutePath.contains("/maven/") &&
			fileName.endsWith("/pom.xml") && Validator.isNotNull(content)) {

			_checkOrder(fileName, content);
		}

		return content;
	}

	private void _checkOrder(String fileName, String content) {
		Document document = SourceUtil.readXML(content);

		if (document == null) {
			return;
		}

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