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
public class XMLSourcechecksFileCheck extends BaseFileCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith("/sourcechecks.xml")) {
			_checkSourcechecksXML(fileName, content);
		}

		return content;
	}

	private void _checkMissingTag(
		String fileName, Element checkElement, String checkName,
		String tagName) {

		Element tagElement = checkElement.element(tagName);

		if (tagElement == null) {
			addMessage(
				fileName,
				StringBundler.concat(
					"Missing tag '", tagName, "' for check '", checkName, "'"));
		}
	}

	private void _checkSourcechecksXML(String fileName, String content) {
		Document document = SourceUtil.readXML(content);

		if (document == null) {
			return;
		}

		Element rootElement = document.getRootElement();

		checkElementOrder(
			fileName, rootElement, "source-processor", null,
			new ElementComparator());

		for (Element sourceProcessorElement :
				(List<Element>)rootElement.elements("source-processor")) {

			checkElementOrder(
				fileName, sourceProcessorElement, "check",
				sourceProcessorElement.attributeValue("name"),
				new ElementComparator());

			for (Element checkElement :
					(List<Element>)sourceProcessorElement.elements("check")) {

				String checkName = checkElement.attributeValue("name");

				if (!checkName.endsWith("Check")) {
					addMessage(
						fileName,
						StringBundler.concat(
							"Name of class '", checkName,
							"' should end with 'Check'"));
				}

				_checkMissingTag(fileName, checkElement, checkName, "category");
				_checkMissingTag(
					fileName, checkElement, checkName, "description");

				checkElementOrder(
					fileName, checkElement, "property", checkName,
					new ElementComparator());
			}
		}
	}

}