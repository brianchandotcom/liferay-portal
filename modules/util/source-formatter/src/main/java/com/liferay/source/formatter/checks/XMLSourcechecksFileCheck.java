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
import com.liferay.source.formatter.checks.comparator.ElementComparator;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.io.IOException;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
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
			String fileName, String absolutePath, String content)
		throws DocumentException, IOException {

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

	private void _checkSourcechecksXML(String fileName, String content)
		throws DocumentException, IOException {

		Document document = SourceUtil.readXML(content);

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