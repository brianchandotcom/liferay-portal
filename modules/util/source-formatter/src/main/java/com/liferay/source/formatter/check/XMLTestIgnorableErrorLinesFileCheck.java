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
public class XMLTestIgnorableErrorLinesFileCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith("/test-ignorable-error-lines.xml")) {
			_checkTestIgnorableErrorLinesXml(fileName, content);
		}

		return content;
	}

	private void _checkTestIgnorableErrorLinesXml(
		String fileName, String content) {

		Document document = SourceUtil.readXML(content);

		if (document == null) {
			return;
		}

		IgnoreErrorElementComparator ignoreErrorElementComparator =
			new IgnoreErrorElementComparator();

		Element rootElement = document.getRootElement();

		List<Element> javascriptElements = rootElement.elements("javascript");

		for (Element javascriptElement : javascriptElements) {
			checkElementOrder(
				fileName, javascriptElement, "ignore-error", null,
				ignoreErrorElementComparator);
		}

		List<Element> logElements = rootElement.elements("log");

		for (Element logElement : logElements) {
			checkElementOrder(
				fileName, logElement, "ignore-error", null,
				ignoreErrorElementComparator);
		}
	}

	private class IgnoreErrorElementComparator extends ElementComparator {

		@Override
		public int compare(Element element1, Element element2) {
			String description1 = element1.attributeValue("description");
			String description2 = element2.attributeValue("description");

			if (!description1.equals(description2)) {
				return super.compare(description1, description2);
			}

			String contains1 = getTagValue(element1, "contains");
			String contains2 = getTagValue(element2, "contains");

			if (!contains1.equals(contains2)) {
				return super.compare(contains1, contains2);
			}

			String matches1 = getTagValue(element1, "matches");
			String matches2 = getTagValue(element2, "matches");

			return super.compare(matches1, matches2);
		}

		@Override
		public String getElementName(Element element) {
			return StringBundler.concat(
				"{description=", element.attributeValue("description"),
				", contains=", getTagValue(element, "contains"), ", matches=",
				getTagValue(element, "matches"), "}");
		}

	}

}