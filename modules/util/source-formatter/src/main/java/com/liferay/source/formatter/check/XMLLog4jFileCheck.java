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
public class XMLLog4jFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith("-log4j-ext.xml") ||
			fileName.endsWith("-log4j.xml")) {

			_checkLog4jXML(fileName, content);
		}

		return content;
	}

	private void _checkLog4jXML(String fileName, String content) {
		Document document = SourceUtil.readXML(content);

		if (document == null) {
			return;
		}

		Element rootElement = document.getRootElement();

		checkElementOrder(
			fileName, rootElement, "category", null,
			new ElementComparator(true));

		for (Element loggerElement :
				(List<Element>)rootElement.elements("Loggers")) {

			checkElementOrder(
				fileName, loggerElement, "Logger", null,
				new ElementComparator(true));
		}
	}

}