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
public class XMLFriendlyURLRoutesFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith("routes.xml")) {
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

		List<Element> routeElements = rootElement.elements("route");

		ElementComparator elementComparator = new ElementComparator();

		for (Element routeElement : routeElements) {
			checkElementOrder(
				fileName, routeElement, "ignored-parameter", null,
				elementComparator);
			checkElementOrder(
				fileName, routeElement, "implicit-parameter", null,
				elementComparator);
			checkElementOrder(
				fileName, routeElement, "overridden-parameter", null,
				elementComparator);
		}
	}

}