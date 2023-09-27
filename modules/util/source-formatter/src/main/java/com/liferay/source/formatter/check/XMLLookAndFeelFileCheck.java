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
public class XMLLookAndFeelFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith("-look-and-feel.xml")) {
			_checkLookAndFeelXML(fileName, content);
		}

		return content;
	}

	private void _checkLookAndFeelXML(String fileName, String content) {
		Document document = SourceUtil.readXML(content);

		if (document == null) {
			return;
		}

		Element rootElement = document.getRootElement();

		List<Element> themeElements = rootElement.elements("theme");

		for (Element themeElement : themeElements) {
			checkElementOrder(
				fileName, themeElement, "portlet-decorator", null,
				new ElementComparator("id"));

			Element settingsElement = themeElement.element("settings");

			checkElementOrder(
				fileName, settingsElement, "setting", null,
				new ElementComparator("key"));
		}
	}

}