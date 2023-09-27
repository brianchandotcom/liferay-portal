/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.check.comparator.ElementComparator;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class TLDElementOrderCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		_checkElementOrder(fileName, content);

		return content;
	}

	private void _checkElementOrder(String fileName, String content) {
		Document document = SourceUtil.readXML(content);

		if (document == null) {
			return;
		}

		Element rootElement = document.getRootElement();

		List<Element> tagElements = rootElement.elements("tag");

		for (Element tagElement : tagElements) {
			Element nameElement = tagElement.element("name");

			checkElementOrder(
				fileName, tagElement, "attribute", nameElement.getText(),
				new TagElementComparator());
		}

		checkElementOrder(
			fileName, rootElement, "tag", null, new TagElementComparator());
	}

	private static class TagElementComparator extends ElementComparator {

		@Override
		public String getElementName(Element element) {
			return getTagValue(element);
		}

	}

}